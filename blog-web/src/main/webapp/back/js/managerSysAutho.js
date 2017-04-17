/**
 * Created by Exotic on 2016-12-13.
 */
layui.config({
    base: '../plugins/layui/modules/'
});
layui.use(['layer','jquery','form','icheck','laydate','tree'],function () {
    var layer = layui.layer;
    var $ = layui.jquery;
    var form = layui.form();
    var laydate = layui.laydate;
    var menuTreeUrl = "../../sys/menu/get";
    var superMenuUrl = "../../sys/menu/getSuper";
    var getAuthoUrl = "../../sys/autho/get";
    var updateMenuUrl = "../../sys/autho/modify";
    var delMenuUrl = "../../sys/autho/delete";
    var addAuthoUrl = "../../sys/autho/add";
    var updateDrUrl = "../../sys/autho/dr";
    managerSysAutho.initUI($,form,laydate,menuTreeUrl,superMenuUrl,updateMenuUrl,delMenuUrl,getAuthoUrl,updateDrUrl);//初始化界面
});
var managerSysAutho = {
    initUI:function ($,form,laydate,menuTreeUrl,superMenuUrl,updateMenuUrl,delMenuUrl,getAuthoUrl,updateDrUrl) {
        $(function () {
            managerSysAutho.loadMenuTree($,form,laydate,menuTreeUrl,getAuthoUrl);//加载系统菜单树+监听点击事件
            managerSysAutho.loadMenuIcon($,form);//加载菜单图标
            managerSysAutho.loadSuperMenus($,form,superMenuUrl);//加载父菜单
            managerSysAutho.regSaveBtn($,form,updateMenuUrl);//监听保存按钮
            managerSysAutho.regAddBtn($,form);//监听添加按钮
            managerSysAutho.regDelBtn($,form,delMenuUrl);//监听删除按钮
            managerSysAutho.regUpdateBtn($,form,updateDrUrl);//监听更新启用按钮
            managerSysAutho.regEditBtn($,form,updateMenuUrl);//监听编辑按钮
        })
    },
    regEditBtn:function ($,form,updateMenuUrl) {
        $('#editBtn').off('click').on('click',function (e) {
            e.preventDefault();
            var checkedList = new Array();
            var tmp = 0;
            $('#autho-tbody>tr>td:first-child>div').each(function (index,item) {
                var checkeds = $(this).attr('class');
                if (checkeds.indexOf("checked")!=-1){
                    checkedList[tmp]=$(this).children('input').attr('data-id');
                    tmp++;
                }
            })
            if (checkedList.length!=1) {
                parent.layer.alert('请选择一条记录！',{icon:0,shade:0.5});
                return;
            }
            parent.layer.open({
                type:2,
                title:'编辑权限',
                content:'menus/editSysAutho.html?id='+checkedList[0],
                area:['400px','550px'],
                end:function () {
                    window.location.reload();
                }
            })
        })
    },
    regUpdateBtn:function ($,form,updateDrUrl) {
        $('#updateBtn').off('click').on('click',function (e) {
            e.preventDefault();
            var list = new Array();
            $('#autho-tbody>tr').each(function (index,item) {
                var id = $(item).children('td:nth-child(2)').text();
                var dr = $(item).children('td:last-child').children('div').attr('class');
                list.push({id:id,dr:dr.indexOf('checked')!=-1?0:1});
            });
            var index = parent.layer.load(2,{shade:0.5});
            setTimeout(function () {
                $.ajax({
                    url:updateDrUrl,
                    type:'post',
                    data:JSON.stringify({sysAuthos:list}),
                    contentType:"application/json;charset=UTF-8",
                    dataType:'json',
                    success:function (res) {
                        if(res.code==200){
                            parent.layer.msg(res.data,{icon:1,shade:0.5,time:1000,end:function () {
                                window.location.reload();
                            }});
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
        })
    },
    regDelBtn:function ($,form,delMenuUrl) {
        $('#delBtn').off('click').on('click',function (e) {
            e.preventDefault();
            var checkedList = new Array();
            var tmp = 0;
            $('#autho-tbody>tr>td:first-child>div').each(function (index,item) {
                var checkeds = $(this).attr('class');
                if (checkeds.indexOf("checked")!=-1){
                    checkedList[tmp]=$(this).children('input').attr('data-id');
                    tmp++;
                }
            })
            parent.layer.confirm('该操作会移除角色的该权限！是否还确定删除？', {
                icon:3,
                btn: ['确定','取消'] //按钮
            }, function(){
                if(checkedList.length<1) {
                    parent.layer.alert('未选择删除的记录',{icon:0,title:'温馨提示'});
                    return;
                }else{
                    var index = parent.layer.load(2,{shade:0.5});
                    setTimeout(function () {
                        $.ajax({
                            url:delMenuUrl,
                            type:'post',
                            data:{ids:checkedList},
                            dataType:'json',
                            success:function (res,state) {
                                if(res.code==200){
                                    parent.layer.msg(res.data,{icon:1,shade:0.5,time:1000,end:function () {
                                        window.location.reload();
                                    }});
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
                }
            });
        })
    },
    regAddBtn:function ($,form) {
      $('#addBtn').off('click').on('click',function (e) {
          e.preventDefault();
          parent.layer.open({
              type:2,
              title:'为菜单添加权限',
              content:'menus/addSysAutho.html',
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
        $('#authoIcon').off('click').on('click',function () {
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

    loadMenuTree:function ($,form,laydate,menuTreeUrl,getAuthoUrl) {
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
                        managerSysAutho.initTree($,form,laydate,getAuthoUrl,res.data);
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
    initTree:function ($,form,laydate,getAuthoUrl,menus) {
        layui.tree({
            elem: '#tree',
            nodes:menus,
            click:function (node) {
                //点击触发菜单权限查询
                managerSysAutho.showContent($,form,getAuthoUrl,laydate,node.id);
            }
        });
    },
    showContent:function ($,form,getAuthoUrl,laydate,menuId) {
        if (menuId==null){
            parent.layer.alert('请选择系统菜单！',{icon:0,shade:0.5});
            return;
        }
        var index = parent.layer.load(2,{shade:0.5});
        setTimeout(function () {
            $.ajax({
                url:getAuthoUrl,
                type:'post',
                data:{menuId:menuId,order:1,field:3},
                dataType:'json',
                success:function (res,state) {
                    if(res.code==200){
                        //拼接表格+数据
                        managerSysAutho.appendTbody($,form,res.data);
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
    appendTbody:function ($,form,authos) {
        $('#autho-tbody').empty();
        var trs = '';
        $.each(authos,function (index,item) {
            trs += '<tr>'+
            '<td><input type="checkbox" lay-filter="check" data-id="'+item.id+'"></td>'+
            '<td>'+item.id+'</td>'+
            '<td>'+item.name+'</td>'+
            '<td><i class="layui-icon">'+item.icon+'</i></td>'+
            '<td>'+item.uri+'</td>'+
            '<td>'+item.descp+'</td>'+
            '<td>'+item.sysMenu.name+'</td>'+
            '<td>'+laydate.now(item.createTime,'YYYY-MM-DD hh:mm:ss')+'</td>'+
            '<td>'+item.sysUser.nickname+'</td>'+
            '<td>'+laydate.now(item.ts,'YYYY-MM-DD hh:mm:ss')+'</td>'+
            '<td>';
            if (item.dr==0){
                trs += '<input type="checkbox" lay-skin="switch" name="dr" checked value="0">';
            }else {
                trs += '<input type="checkbox" lay-skin="switch" name="dr" value="1">';
            }
            trs += '</td></tr>';
        });
        $('#autho-tbody').append(trs);
        form.render('checkbox');
        managerSysAutho.regCheckBox($);//注册icheck组件
    },
    regCheckBox:function ($) {
        $('input').iCheck({//注册icheck组件
            checkboxClass: 'icheckbox_flat-green'
        });
        $('#selected-all').on('ifChanged', function(event) {//全选/取消全选
            var $input = $('#autho-tbody tr td:first-child').find('input');
            $input.iCheck(event.currentTarget.checked ? 'check' : 'uncheck');
        });
        $('.site-table tbody tr').on('click', function(event) {//表格单选
            var $this = $(this);
            var $input = $this.children('td').eq(0).find('input');
            $input.on('ifChecked', function(e) {
                $this.css('background-color', '#EEEEEE');
            });
            $input.on('ifUnchecked', function(e) {
                $this.removeAttr('style');
            });
            $input.iCheck('toggle');
        }).find('input').each(function(){
            var $this = $(this);
            $this.on('ifChecked', function(e) {
                $this.parents('tr').css('background-color', '#EEEEEE');
            });
            $this.on('ifUnchecked', function(e) {
                $this.parents('tr').removeAttr('style');
            });
        });
    }
}