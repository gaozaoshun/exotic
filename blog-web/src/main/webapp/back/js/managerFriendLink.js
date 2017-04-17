/**
 * Created by Exotic on 2016-12-15.
 */
layui.use(['layer','jquery','form', 'laydate','tree'],function () {
    var layer = layui.layer;
    var $ = layui.jquery;
    var form = layui.form();
    var laydate = layui.laydate;
    var friendLinkUrl = "../../friendlink/get?order=0&field=4&dr=0";
    var unfriendLinkUrl = "../../friendlink/get?order=0&field=4&dr=1";
    var modifyUrl = "../../friendlink/modify";
    var delUrl = "../../friendlink/delete";
    managerFriendLink.initUI($,form,laydate,friendLinkUrl,modifyUrl,delUrl,unfriendLinkUrl);//初始化界面
});
var managerFriendLink= {
    initUI:function ($,form,laydate,friendLinkUrl,modifyUrl,delUrl,unfriendLinkUrl) {
        $(function () {
            managerFriendLink.initFriendLinkTree($,form,laydate,friendLinkUrl);//初始化菜单树且加载第一条记录
            managerFriendLink.initUnFriendLinkTree($,form,laydate,unfriendLinkUrl);//初始化未启用菜单树
            managerFriendLink.regModifyBtn($,form,laydate,modifyUrl,friendLinkUrl,unfriendLinkUrl);//监听更改按钮
            managerFriendLink.regAddBtn($,form,laydate,friendLinkUrl,modifyUrl);//监听添加按钮
            managerFriendLink.regDelBtn($,form,laydate,friendLinkUrl,modifyUrl,delUrl);//监听删除按钮
        })
    },
    initUnFriendLinkTree:function ($,form,laydate,unfriendLinkUrl) {
        var index = parent.layer.load(2,{shade:0.5});
        $('#unmenu-tree').empty();//清空树形菜单
        setTimeout(function () {
            $.ajax({
                url:unfriendLinkUrl,
                type:'get',
                dataType:'json',
                success:function (res,state) {
                    if(res.code==200){
                        layui.tree({
                            elem: '#unmenu-tree' ,
                            nodes:res.data,
                            click:function (node) {//监听节点
                                managerFriendLink.loadContent($,form,laydate,node);//加载指定节点信息
                                managerFriendLink.addIdToDelBtn($,node.id);//把当前节点的id添加到删除按钮的data-id属性上
                            }
                        });
                        managerFriendLink.changeProp($);//监听节点改变属性
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
    initFriendLinkTree:function ($,form,laydate,friendLinkUrl) {
        var index = parent.layer.load(2,{shade:0.5});
        $('#type-tree').empty();//清空树形菜单
        setTimeout(function () {
            $.ajax({
                url:friendLinkUrl,
                type:'get',
                dataType:'json',
                success:function (res) {
                    if(res.code==200){
                        layui.tree({
                            elem: '#type-tree' ,
                            nodes:res.data,
                            click:function (node) {//监听节点
                                managerFriendLink.loadContent($,form,laydate,node);//加载指定节点信息
                                managerFriendLink.addIdToDelBtn($,node.id);//把当前节点的id添加到删除按钮的data-id属性上
                            }
                        });
                        managerFriendLink.changeProp($);//监听类型树节点改变属性
                        managerFriendLink.loadContent($,form,laydate,res.data[0]);//加载第一个节点信息
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
    regDelBtn:function ($,form,laydate,friendLinkUrl,modifyUrl,delUrl) {
        $('#delBtn').on('click',function (e) {
            e.preventDefault();
            //弹出框
            parent.layer.msg('你确定删除么？', {
                time: 0 ,//不自动关闭
                shade:0.5,
                btn: ['必须的', '那就不了']
                ,yes: function(index){
                    layer.close(index);
                    //获取灰色背景的节点
                    var index = parent.layer.load(2,{shade:0.5});
                    setTimeout(function () {
                        $.ajax({
                            url:delUrl,
                            type:'post',
                            data:{ids:[$('#delBtn').attr('data-id')]},
                            dataType:'json',
                            success:function (res,state) {
                                if(res.code==200){
                                    parent.layer.msg(res.data.toString(), {icon: 1,shade:0.5});
                                    managerFriendLink.initFriendLinkTree($,form,laydate,friendLinkUrl,modifyUrl);
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
    regAddBtn:function ($,form,laydate,friendLinkUrl,modifyUrl) {
        $('#addBtn').on('click',function (e) {
            e.preventDefault();
            //弹出框
            parent.layer.open({
                type: 2,
                title:'添加友情链接',
                area: ['360px', '290px'],
                skin: 'layui-layer-rim', //加上边框
                content: 'menus/addFriendLink.html',
                end:function () {//关闭时刷新菜单树
                    managerFriendLink.initFriendLinkTree($,form,laydate,friendLinkUrl,modifyUrl);
                }
            });
        })
    },
    addIdToDelBtn:function ($,id) {
      $('#delBtn').attr('data-id',id);
    },
    changeProp:function ($) {
        $('#type-tree>li>a').on('click',function (e) {
            e.preventDefault();
            $('#type-tree>li').css({'background':'#fff'})
            $(this).parent('li').css({'background':'#666'});
        })
    },
    regModifyBtn:function ($,form,laydate,modifyUrl,friendLinkUrl,unfriendLinkUrl) {
        $('#modify').on('click',function () {
            var index = parent.layer.load(2,{shade:0.5});
            setTimeout(function () {
                $.ajax({
                    url:modifyUrl,
                    type:'post',
                    data:{id:$('input[name="id"]').val(),
                            descp:$('input[name="descp"]').val(),
                            url:$('input[name="url"]').val(),
                            lv:$('input[name="lv"]').val(),
                            dr:$('input[name="dr"]').is(':checked')?0:1},
                    dataType:'json',
                    success:function (res,state) {
                        if(res.code==200){
                            parent.layer.msg(res.data.toString(), {icon: 1,shade:0.5});
                            managerFriendLink.initFriendLinkTree($,form,laydate,friendLinkUrl,modifyUrl);//刷新菜单树
                            managerFriendLink.initUnFriendLinkTree($,form,laydate,unfriendLinkUrl);//刷新菜单树
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
    loadContent:function ($,form,laydate,bloger) {
        $('input[name="id"]').val(bloger.id);
        $('input[name="descp"]').val(bloger.descp);
        $('input[name="headimg"]').val(bloger.headimg);
        $('input[name="url"]').val(bloger.url);
        $('input[name="lv"]').val(bloger.lv);
        $('input[name="createTime_no"]').val(laydate.now(bloger.createTime,'YYYY-MM-DD hh:mm:ss'));
        $('input[name="createUser_no"]').val(bloger.sysUser.nickname);
        $('input[name="ts"]').val(laydate.now(bloger.ts,'YYYY-MM-DD hh:mm:ss'));
        if (bloger.dr==0){
            $('#dr').html('<input type="checkbox" name="dr" lay-skin="switch" checked>');
        }else{
            $('#dr').html('<input type="checkbox" name="dr" lay-skin="switch">');
        }
        form.render('checkbox');
    }
}
