/**
 * Created by Exotic on 2016-12-13.
 */
layui.use(['layer','jquery','form','laydate','tree'],function () {
    var layer = layui.layer;
    var $ = layui.jquery;
    var form = layui.form();
    var laydate = layui.laydate;
    var menuUrl = "../../sys/menu/get";
    var modifyRoleUrl = "../../sys/role/modify";
    var getRoleUrl = "../../sys/role/get";
    modifySysRole.initUI($,form,laydate,menuUrl,modifyRoleUrl,getRoleUrl);//初始化界面
});
var modifySysRole = {
    initUI:function ($,form,laydate,menuUrl,modifyRoleUrl,getRoleUrl) {
        $(function () {
            modifySysRole.loadMenuIcon($,form);//加载菜单图标
            modifySysRole.regSwitch($,form);//监听启用开关
            modifySysRole.initData($,form,laydate,getRoleUrl);//初始化数据
            modifySysRole.regSaveBtn($,form,modifyRoleUrl);//监听保存按钮
        })
    },
    getId:function () {
        var url = window.location.href;
        var id = url.substring(url.indexOf('?id=')+4);
        if (id==null){
            parent.layer.msg('ID不能为空！',{icon:2,time:1000,shade:0.5});
            return;
        }
        return id;
    },
    initData:function ($,form,laydate,getRoleUrl) {
        var index = parent.layer.load(2,{shade:0.5});
        setTimeout(function () {
            $.ajax({
                url:getRoleUrl,
                type:'post',
                data:{id:modifySysRole.getId(),field:3,order:1},
                dataType:'json',
                success:function (res,state) {
                    if(res.code==200){
                        modifySysRole.appendRole($,form,laydate,res.data[0]);
                    }else{
                        parent.layer.msg(res.msg,{icon:2,shade:0.5});
                    }
                    parent.layer.close(index);
                },
                error:function (xhr,state,error) {
                    parent.layer.close(index);
                    parent.layer.msg('HTTP请求失败！',{icon:2,shade:0.5});
                }
            });
        },100);
    },
    appendRole:function ($,form,laydate,role) {
        $('input[name="id"]').val(role.id);
        $('input[name="name"]').val(role.name);
        $('input[name="icon"]').val(role.icon);
        $('textarea[name="descp"]').val(role.descp);
        $('input[name="createTime"]').val(laydate.now(role.createTime,'YYYY-MM-DD hh:mm:ss'));
        $('input[name="createUser"]').val(role.sysUser.nickname);
        $('input[name="ts"]').val(laydate.now(role.ts,'YYYY-MM-DD hh:mm:ss'));
        if (role.dr==0){
            $('input[name="dr"]').attr('checked','checked');
            $('input[name="dr"]').val(0);
        }
          form.render();
    },
    regSaveBtn:function ($,form,modifyRoleUrl) {
        $('#save').off('click').on('click',function () {
            var dataForm = {
                id:$('input[name="id"]').val(),
                name: $('input[name="name"]').val(),
                icon: $('input[name="icon"]').val(),
                descp: $('textarea[name="descp"]').val(),
                dr: $('input[name="dr"]').val()
            };
            var index = layer.load(2,{shade:0.5});
            setTimeout(function () {
                $.ajax({
                    url:modifyRoleUrl,
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