/**
 * Created by Exotic on 2016-12-13.
 */
layui.use(['layer','jquery','form','laydate','tree'],function () {
    var layer = layui.layer;
    var $ = layui.jquery;
    var form = layui.form();
    var laydate = layui.laydate;
    var menuUrl = "../../sys/menu/get";
    var addRoleUrl = "../../sys/role/add";
    addSysAutho.initUI($,form,laydate,menuUrl,addRoleUrl);//初始化界面
});
var addSysAutho = {
    initUI:function ($,form,laydate,menuUrl,addRoleUrl) {
        $(function () {
            addSysAutho.loadMenuIcon($,form);//加载菜单图标
            addSysAutho.regSwitch($,form);//监听启用开关
            addSysAutho.regSaveBtn($,form,addRoleUrl);//监听保存按钮
        })
    },
    regSaveBtn:function ($,form,addRoleUrl) {
        $('#save').off('click').on('click',function () {
            var dataForm = {
                name: $('input[name="name"]').val(),
                icon: $('input[name="icon"]').val(),
                descp: $('textarea[name="descp"]').val(),
                dr: $('input[name="dr"]').val()
            };
            var index = layer.load(2,{shade:0.5});
            setTimeout(function () {
                $.ajax({
                    url:addRoleUrl,
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