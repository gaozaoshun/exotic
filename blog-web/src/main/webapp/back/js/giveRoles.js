/**
 * Created by Exotic on 2016/12/16.
 */
layui.use(['layer','jquery','form','upload','flow'],function () {
    var layer = layui.layer;
    var $ = layui.jquery;
    var form = layui.form();
    var flow = layui.flow;
    var userRoleUrl = "../../sys/user/role/get";//加载用户已有角色
    var rolesUrl = "../../sys/role/get";//加载角色
    var saveUrl = "../../sys/user/role/save";//保存用户角色
    var delUrl = "../../sys/user/role/del";//删除用户角色
    $(function () {
        giveRoles.initData($,form,flow,userRoleUrl,rolesUrl,saveUrl,delUrl);//初始化数据
    })
});
var giveRoles = {
    getId:function(){
        var url = window.location.href;
        var id = url.substring(url.indexOf('?id=')+4);
        if (id!=null){
            return id;
        }else {
            parent.layer.alert('不存在此用户ID！',{icon:2,shade:0.5});
            return;
        }
    },
    initData:function ($,form,flow,userRoleUrl,rolesUrl,saveUrl,delUrl) {
        giveRoles.loadUser($,form,flow,userRoleUrl);//加载用户已有角色
        giveRoles.regSearch($,form,userRoleUrl,rolesUrl);//监听搜索按钮
        giveRoles.regYesBtn($,form,saveUrl);//保存当前用户的角色
        giveRoles.regDelBtn($,form,delUrl);//删除当前用户的角色
    },
    regDelBtn:function ($,form,delUrl) {
        $('#roleToDel').off('click').on('click',function () {
            var id = null;
            $('#userRoles>li').each(function (index,item) {
                var bg = $(item).attr('class');
                if (bg!=null&&bg.indexOf('exotic-bg-color')!=-1){
                    id = $(item).attr('data-id');
                }
            });
            if(id==null){
                parent.layer.alert('此角色ID为空！',{icon:2,shade:0.5});
                return;
            }
            var index = layer.load(2,{shade:0.5});
            setTimeout(function () {
                $.ajax({
                    url:delUrl,
                    type:'post',
                    data:{userId:giveRoles.getId(),roleId:id},
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
    regYesBtn:function ($,form,saveUrl) {
        $('#yes').off('click').on('click',function (e) {
            var index = layer.load(2,{shade:0.5});
            var ids = new Array();
            $('#userRoles>li').each(function (index,item) {
                ids.push($(item).attr('data-id'))
            })
            setTimeout(function () {
                $.ajax({
                    url:saveUrl,
                    type:'post',
                    data:{userId:giveRoles.getId(),ids:ids},
                    dataType:'json',
                    success:function (res,state) {
                        if(res.code==200){
                            layer.msg(res.data,{icon:1,shade:0.5});
                        }else{
                            layer.msg(res.msg,{icon:2,shade:0.5});
                        }
                        parent.layer.closeAll();
                    },
                    error:function (xhr,state,error) {
                        layer.close(index);
                        layer.msg('HTTP请求失败！',{icon:2,shade:0.5});
                    }
                });
            },100);
        })
    },
    regSearch:function ($,form,userRoleUrl,rolesUrl) {
        $('#searchBtn').off('click').on('click',function () {
            var q = $('input[name="searchQ"]').val();
            if (q==null || q.trim()==''){
                parent.layer.alert('搜索字段不能为空！',{icon:2,shade:0.5});
            }else {
                giveRoles.loadRoles($,form,rolesUrl,q);
            }
        })
    },
    loadRoles:function ($,form,rolesUrl,q) {
        var index = layer.load(2,{shade:0.5});
        $('#searchRoles').empty();
        setTimeout(function () {
            $.ajax({
                url:rolesUrl,
                type:'post',
                data:{q:q,order:1,field:3},
                dataType:'json',
                success:function (res,state) {
                    if(res.code==200){
                        var lis = '';
                        $.each(res.data,function (index,item) {
                            lis += '<li data-id="'+item.id+'"><a href="#" data-descp="'+item.descp+'"><i class="layui-icon">'+item.icon+'</i> '+item.name+'</a></li>';
                        });
                        $(lis).appendTo('#searchRoles');
                        giveRoles.regLicheck($);
                        //给用户授予角色
                        $('#roleToUser').off('click').on('click',function () {
                            $('#searchRoles>li').each(function (index,item) {
                                var bg = $(item).attr('class');
                                if (bg!=null && bg.indexOf('exotic-bg-color')!=-1){
                                    //检查用户是否已存在此角色
                                    if(!giveRoles.checkRoles($,$(item).attr('data-id'))){
                                        parent.layer.alert('当前用户已存在角色['+$(item).find('a').text()+']',{icon:2,shade:0.5});
                                        return;
                                    }
                                    $($(item)[0].outerHTML).appendTo('#userRoles');
                                    $('#userRoles>div').insertAfter('#userRoles>li:last-child');
                                    giveRoles.regLicheck($);
                                }
                            })
                        })
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
    checkRoles:function ($,id) {
        var flag = true;
        $('#userRoles>li').each(function (index,item) {
            var dataId = $(item).attr('data-id');
            if(dataId!=null && dataId==id){
                flag = false;
            }
        })
        return flag;
    },
    loadUser:function ($,form,flow,userRoleUrl) {
        var index = layer.load(2,{shade:0.5});
        flow.load({
            elem: '#userRoles'
            ,isAuto:true
            ,done: function(page, next){ //到达临界点（默认滚动触发），触发下一页
                var lis = [];
                //以jQuery的Ajax请求为例，请求下一页数据（注意：page是从2开始返回）
                $.get(userRoleUrl+'?page='+page+'&rows=10&id='+giveRoles.getId(), function(res){
                    layui.each(res.data, function(index, item){
                        lis.push('<li data-id="'+item.id+'"><a href="#" data-descp="'+item.descp+'" ><i class="layui-icon">'+item.icon+'</i> '+item.name+'</a></li>');
                    });
                    next(lis.join(''),true);
                    giveRoles.regLicheck($);
                    layer.close(index);
                });
            }
        });
    },
    regLicheck:function ($) {
        //注册li点击事件
        $('#userRoles>li>a').off('click').on('click',function (e) {
            e.preventDefault();
            layer.tips($(this).attr('data-descp'), this);
            $(this).parent('li').parent('ul').find('li').removeClass('exotic-bg-color');
            $(this).parent('li').addClass('exotic-bg-color');
        })
        $('#searchRoles>li>a').off('click').on('click',function (e) {
            e.preventDefault();
            layer.tips($(this).attr('data-descp'), this);
            $(this).parent('li').parent('ul').find('li').removeClass('exotic-bg-color');
            $(this).parent('li').addClass('exotic-bg-color');
        })
    }
};