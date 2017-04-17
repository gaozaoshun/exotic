/**
 * Created by Exotic on 2016/12/16.
 */
layui.use(['layer','jquery','form','upload','flow'],function () {
    var layer = layui.layer;
    var $ = layui.jquery;
    var form = layui.form();
    var flow = layui.flow;
    var roleAuthoUrl = "../../sys/role/autho/get";//加载角色已有权限
    var authosUrl = "../../sys/autho/get";//加载权限
    var saveUrl = "../../sys/role/autho/save";//保存角色的权限
    var delUrl = "../../sys/user/role/del";//删除用户角色
    $(function () {
        giveAutho.initData($,form,flow,roleAuthoUrl,authosUrl,saveUrl,delUrl);//初始化数据
    })
});
var giveAutho = {
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
    initData:function ($,form,flow,roleAuthoUrl,authosUrl,saveUrl,delUrl) {
        giveAutho.loadRole($,form,flow,roleAuthoUrl);//加载角色已有权限
        giveAutho.regSearch($,form,roleAuthoUrl,authosUrl);//监听搜索按钮
        giveAutho.regYesBtn($,form,saveUrl);//保存当前用户的角色
        giveAutho.regDelBtn($,form,delUrl);//删除当前用户的角色
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
                    data:{userId:giveAutho.getId(),roleId:id},
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
                    data:{id:giveAutho.getId(),ids:ids},
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
    regSearch:function ($,form,roleAuthoUrl,authosUrl) {
        $('#searchBtn').off('click').on('click',function () {
            var q = $('input[name="searchQ"]').val();
            if (q==null || q.trim()==''){
                parent.layer.alert('搜索字段不能为空！',{icon:2,shade:0.5});
            }else {
                giveAutho.loadAuthos($,form,authosUrl,q);
            }
        })
    },
    loadAuthos:function ($,form,authosUrl,q) {
        var index = layer.load(2,{shade:0.5});
        $('#searchRoles').empty();
        setTimeout(function () {
            $.ajax({
                url:authosUrl,
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
                        giveAutho.regLicheck($);
                        //给用户授予角色
                        $('#roleToUser').off('click').on('click',function () {
                            $('#searchRoles>li').each(function (index,item) {
                                var bg = $(item).attr('class');
                                if (bg!=null && bg.indexOf('exotic-bg-color')!=-1){
                                    //检查用户是否已存在此角色
                                    if(!giveAutho.checkRoles($,$(item).attr('data-id'))){
                                        parent.layer.alert('当前用户已存在角色['+$(item).find('a').text()+']',{icon:2,shade:0.5});
                                        return;
                                    }
                                    $($(item)[0].outerHTML).appendTo('#userRoles');
                                    $('#userRoles>div').insertAfter('#userRoles>li:last-child');
                                    giveAutho.regLicheck($);
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
    loadRole:function ($,form,flow,roleAuthoUrl) {
        var index = layer.load(2,{shade:0.5});
        flow.load({
            elem: '#userRoles'
            ,isAuto:false
            ,done: function(page, next){ //到达临界点（默认滚动触发），触发下一页
                var lis = [];
                //以jQuery的Ajax请求为例，请求下一页数据（注意：page是从2开始返回）
                $.get(roleAuthoUrl+'?page='+page+'&rows=15&id='+giveAutho.getId(), function(res){
                    layui.each(res.data, function(index, item){
                        lis.push('<li data-id="'+item.id+'"><a href="#" data-descp="'+item.descp+'" ><i class="layui-icon">'+item.icon+'</i> '+item.name+'</a></li>');
                    });
                    next(lis.join(''),true);
                    giveAutho.regLicheck($);
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