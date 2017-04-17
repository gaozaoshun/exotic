/**
 * Created by Exotic on 2016-12-13.
 */
layui.use(['layer','jquery','form','laydate','tree'],function () {
    var layer = layui.layer;
    var $ = layui.jquery;
    var form = layui.form();
    var laydate = layui.laydate;
    var menuUrl = "../../sys/menu/get";
    var getMenuUrl = "../../sys/autho/get";
    var modifyMenuUrl = "../../sys/autho/modify";
    editSysAutho.initUI($,form,laydate,menuUrl,getMenuUrl,modifyMenuUrl);//初始化界面
});
var editSysAutho = {
    initUI:function ($,form,laydate,menuUrl,getMenuUrl,modifyMenuUrl) {
        $(function () {
            editSysAutho.loadMenuIcon($,form);//加载菜单图标
            editSysAutho.loadMenus($,form,menuUrl);//加载父菜单
            editSysAutho.regSwitch($,form);//监听启用开关
            editSysAutho.initData($,form,laydate,getMenuUrl);//初始化数据
            editSysAutho.regSaveBtn($,form,modifyMenuUrl);//监听保存按钮
        })
    },
    getId:function () {
        var url = window.location.href;
        var id = url.substring(url.indexOf('?id=')+4);
        if (id==null){
            parent.layer.msg("ID为空！",{icon:2,shade:0.5,time:1000});
            return;
        }
        return id;
    },
    initData:function ($,form,laydate,getMenuUrl) {
        var index = layer.load(2,{shade:0.5});
        setTimeout(function () {
            $.ajax({
                url:getMenuUrl,
                type:'post',
                data:{id:editSysAutho.getId(),order:1,field:3},
                dataType:'json',
                success:function (res,state) {
                    if(res.code==200){
                        editSysAutho.appendAutho($,form,laydate,res.data[0]);//拼接到弹出框上
                    }else{
                        layer.msg(res.msg,{icon:2,shade:0.5});
                    }
                    layer.close(index);
                },
                error:function (xhr,state,error) {
                    layer.close(index);
                    layer.msg('HTTP请求失败！',{icon:2,shade:0.5});
                }
            });
        },100);
    },
    appendAutho:function ($,form,laydate,autho) {
        $('input[name="id"]').val(autho.id);
        $('input[name="name"]').val(autho.name);
        $('input[name="icon"]').val(autho.icon);
        $('input[name="uri"]').val(autho.uri);
        $('textarea[name="descp"]').val(autho.descp);
        $('select[name="menuId"]').val(autho.menuId);
        $('input[name="createTime"]').val(laydate.now(autho.createTime,'YYYY-MM-DD hh:mm:ss'));
        $('input[name="createUser"]').val(autho.sysUser.nickname);
        $('input[name="ts"]').val(laydate.now(autho.ts,'YYYY-MM-DD hh:mm:ss'));
        $('input[name="dr"]').val(autho.dr);
        if(autho.dr==1){
            $('input[name="dr"]').removeProp('checked');
        }
        form.render();
    },
    regSaveBtn:function ($,form,getMenuUrl) {
        $('#save').off('click').on('click',function () {
            var dataForm = {
                id:$('input[name="id"]').val(),
                name: $('input[name="name"]').val(),
                icon: $('input[name="icon"]').val(),
                uri: $('input[name="uri"]').val(),
                descp: $('textarea[name="descp"]').val(),
                menuId: $('select[name="menuId"]').val(),
                dr: $('input[name="dr"]').val()
            };
            var index = layer.load(2,{shade:0.5});
            setTimeout(function () {
                $.ajax({
                    url:getMenuUrl,
                    type:'post',
                    data:dataForm,
                    dataType:'json',
                    success:function (res,state) {
                        if(res.code==200){
                            layer.msg(res.data,{icon:1,shade:0.5,time:1000,end:function () {
                                parent.layer.closeAll();
                            }});
                        }else{
                            layer.msg(res.msg,{icon:2,shade:0.5});
                        }
                        layer.close(index);
                    },
                    error:function (xhr,state,error) {
                        layer.close(index);
                        layer.msg('HTTP请求失败！',{icon:2,shade:0.5});
                    }
                });
            },100);
        })
    },
    regSwitch:function ($,form) {
        form.on('switch(dr)', function(data){
            if (data.elem.checked){
                $('input[name="dr"]').val(0);
            }else{
                $('input[name="dr"]').val(1);
            }
        });
    },
    loadMenus:function ($,form,menuUrl) {
        var index = layer.load(2,{shade:0.5});
        setTimeout(function () {
            $.ajax({
                url:menuUrl,
                type:'post',
                data:{order:1,field:3},
                dataType:'json',
                success:function (res,state) {
                    if(res.code==200){
                      editSysAutho.appendMenus($,form,res.data);
                    }else{
                        layer.msg(res.msg,{icon:2,shade:0.5});
                    }
                    layer.close(index);
                },
                error:function (xhr,state,error) {
                    layer.close(index);
                    layer.msg('HTTP请求失败！',{icon:2,shade:0.5});
                }
            });
        },100);
    },
    appendMenus:function ($,form,menus) {
        var opts = '';
        $.each(menus,function (index,item) {
            if (item.superId == 0){
                opts += '<option value="'+item.id+'">'+item.name+'</option>';
                $.each(item.children,function (index,item) {
                    opts += '<option value="'+item.id+'">|--- '+item.name+'</option>';
                });
            }
        })
        $(opts).appendTo('select[name="menuId"]');
        form.render('select');
    },
    loadMenuIcon:function ($,form) {
        $('#menuIcon').off('click').on('click',function () {
            parent.layer.open({
                type:2,
                title:'菜单图标',
                content:'menus/menuIcon.html',
                shade:0.5,
                area:['400px','450px'],
                btn:['确定','取消'],
                yes:function (index,dom) {
                    var icon = $(dom).find('.layui-layer-title').attr('data-icon');
                    $('input[name="icon"]').val(icon);
                    parent.layer.close(index);
                }
            })
        })
    }
}