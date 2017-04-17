/**
 * Created by Exotic on 2016-12-13.
 */
layui.use(['layer','jquery','form','laydate','tree'],function () {
    var layer = layui.layer;
    var $ = layui.jquery;
    var form = layui.form();
    var laydate = layui.laydate;
    var menuTreeUrl = "../../sys/menu/get";
    var superMenuUrl = "../../sys/menu/getSuper";
    var updateMenuUrl = "../../sys/menu/modify";
    var delMenuUrl = "../../sys/menu/delete";
    managerSysMenu.initUI($,form,laydate,menuTreeUrl,superMenuUrl,updateMenuUrl,delMenuUrl);//初始化界面
});
var managerSysMenu = {
    initUI:function ($,form,laydate,menuTreeUrl,superMenuUrl,updateMenuUrl,delMenuUrl) {
        $(function () {
            managerSysMenu.loadMenuTree($,form,laydate,menuTreeUrl);//加载系统菜单树+监听点击事件
            managerSysMenu.loadMenuIcon($,form);//加载菜单图标
            managerSysMenu.loadSuperMenus($,form,superMenuUrl);//加载父菜单
            managerSysMenu.regSwitch($,form);//监听开关-启用/加入权限表
            managerSysMenu.regSaveBtn($,form,updateMenuUrl);//监听保存按钮
            managerSysMenu.regAddBtn($,form);//监听添加按钮
            managerSysMenu.regDelBtn($,form,delMenuUrl);//监听删除按钮
            managerSysMenu.regGiveBtn($,form);//监听授权按钮
        })
    },
    regGiveBtn:function($,form){
        $('#giveBtn').off('click').on('click',function (e) {
            e.preventDefault();
            parent.layer.open({
                type:2,
                title:'菜单授权',
                content:'menus/addSysAutho.html',
                area:['400px','500px'],
                end:function () {
                    window.location.reload();
                }
            })
        })
    },
    regDelBtn:function ($,form,delMenuUrl) {
        $('#delBtn').off('click').on('click',function (e) {
            e.preventDefault();
            var id = $('input[name="id"]').val();
            if (id==null){
                parent.layer.alert('请选择一个菜单！',{icon:0,shade:0.5});
                return;
            }
            var index = layer.load(2,{shade:0.5});
            setTimeout(function () {
                $.ajax({
                    url:delMenuUrl,
                    type:'post',
                    data:{ids:[id]},
                    dataType:'json',
                    success:function (res,state) {
                        if(res.code==200){
                            layer.msg(res.data,{icon:1,shade:0.5,time:1000,end:function () {
                                window.location.reload();
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
    regAddBtn:function ($,form) {
      $('#addBtn').off('click').on('click',function (e) {
          e.preventDefault();
          parent.layer.open({
              type:2,
              title:'添加系统菜单',
              content:'menus/addSysMenu.html',
              area:['400px','450px'],
              end:function () {
                  window.location.reload();
              }
          })
      })
    },
    regSaveBtn:function ($,form,updateMenuUrl) {
        $('#save').off('click').on('click',function () {
            var dataForm = {
                id:$('input[name="id"]').val(),
                name: $('input[name="name"]').val(),
                icon: $('input[name="icon"]').val(),
                url: $('input[name="url"]').val(),
                descp: $('textarea[name="descp"]').val(),
                superId: $('select[name="superMenu"]').val(),
                dr: $('input[name="dr"]').is(':checked')?0:1
            };
            var index = layer.load(2,{shade:0.5});
            setTimeout(function () {
                $.ajax({
                    url:updateMenuUrl,
                    type:'post',
                    data:dataForm,
                    dataType:'json',
                    success:function (res,state) {
                        if(res.code==200){
                            layer.msg(res.data,{icon:1,shade:0.5,time:1000,end:function () {
                                window.location.reload();
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
            data.elem.checked?$('input[name="dr"]').val(0):$('input[name="dr"]').val(1);
        });
    },
    loadSuperMenus:function ($,form,superMenuUrl) {
        var index = layer.load(2,{shade:0.5});
        setTimeout(function () {
            $.ajax({
                url:superMenuUrl,
                type:'post',
                data:{dr:0,order:1,field:3},//只查询dr=0按创建时间排序
                dataType:'json',
                success:function (res,state) {
                    if(res.code==200){
                        var opts = '';
                        $.each(res.data,function (index,item) {
                            opts += '<option value="'+item.id+'">'+item.name+'</option>';
                        })
                        $(opts).appendTo('select[name="superMenu"]');
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

    loadMenuTree:function ($,form,laydate,menuTreeUrl) {
        var index = layer.load(2,{shade:0.5});
        setTimeout(function () {
            $.ajax({
                url:menuTreeUrl,
                type:'post',
                data:{order:0,field:3},
                dataType:'json',
                success:function (res,state) {
                    if(res.code==200){
                        //初始化菜单树
                        managerSysMenu.initTree($,form,laydate,res.data);
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
    initTree:function ($,form,laydate,menus) {
        layui.tree({
            elem: '#tree',
            nodes:menus,
            click:function (node) {
                //点击显示菜单内容
                managerSysMenu.showContent($,form,laydate,node);
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