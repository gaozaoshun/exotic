/**
 * Created by Exotic on 2016-12-13.
 */
layui.use(['layer','jquery','form','laydate','tree'],function () {
    var layer = layui.layer;
    var $ = layui.jquery;
    var form = layui.form();
    var laydate = layui.laydate;
    var superMenuUrl = "../../sys/menu/getSuper";
    var addMenuUrl = "../../sys/menu/add";
    var delMenuUrl = "../../sys/menu/del";
    addSysMenu.initUI($,form,laydate,superMenuUrl,addMenuUrl,delMenuUrl);//初始化界面
});
var addSysMenu = {
    initUI:function ($,form,laydate,superMenuUrl,addMenuUrl,delMenuUrl) {
        $(function () {
            addSysMenu.loadMenuIcon($,form);//加载菜单图标
            addSysMenu.loadSuperMenus($,form,superMenuUrl);//加载父菜单
            addSysMenu.regSwitch($,form);//监听启用开关
            addSysMenu.regSaveBtn($,form,addMenuUrl);//监听保存按钮
        })
    },
    regSaveBtn:function ($,form,addMenuUrl) {
        $('#save').off('click').on('click',function () {
            var dataForm = {
                name: $('input[name="name"]').val(),
                icon: $('input[name="icon"]').val(),
                url: $('input[name="url"]').val(),
                descp: $('textarea[name="descp"]').val(),
                superId: $('select[name="superId"]').val(),
                dr: $('input[name="dr"]').is(':checked')?0:1
            };
            var index = layer.load(2,{shade:0.5});
            setTimeout(function () {
                $.ajax({
                    url:addMenuUrl,
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

        });
    },
    loadSuperMenus:function ($,form,superMenuUrl) {
        var index = layer.load(2,{shade:0.5});
        setTimeout(function () {
            $.ajax({
                url:superMenuUrl,
                type:'post',
                data:{order:1,field:3},
                dataType:'json',
                success:function (res,state) {
                    if(res.code==200){
                        var opts = '';
                        $.each(res.data,function (index,item) {
                            opts += '<option value="'+item.id+'">'+item.name+'</option>';
                        })
                        $(opts).appendTo('select[name="superId"]');
                        form.render('select');
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
    },
    initTree:function ($,form,laydate,menus) {
        layui.tree({
            elem: '#tree',
            nodes:menus,
            click:function (node) {
                //点击显示菜单内容
                addSysMenu.showContent($,form,laydate,node);
            }
        });
    },
    showContent:function ($,form,laydate,menu) {
        $('input[name="id"]').val(menu.id);
        $('input[name="name"]').val(menu.name);
        $('input[name="icon"]').val(menu.icon);
        $('input[name="url"]').val(menu.url);
        $('textarea[name="descp"]').val(menu.descp);
        $('select[name="superMenu"]').val(menu.superId);
        form.render('select');
        $('input[name="createTime"]').val(laydate.now(menu.createTime,"YYYY-MM-DD hh:mm:ss"));
        $('input[name="createUser"]').val(menu.sysUser.nickname);
        $('input[name="ts"]').val(laydate.now(menu.ts,"YYYY-MM-DD hh:mm:ss"));
        if (menu.dr==0){
            $('input[name="dr"]').attr('checked','checked');
            form.render('checkbox');
        }
    }
}