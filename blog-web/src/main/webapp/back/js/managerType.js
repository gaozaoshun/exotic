/**
 * Created by Exotic on 2016-12-15.
 */
layui.use(['layer','jquery','form', 'laydate','tree'],function () {
    var layer = layui.layer;
    var $ = layui.jquery;
    var form = layui.form();
    var laydate = layui.laydate;
    var typeUrl = "../../blogs/type?dr=0";
    var unTypeUrl = "../../blogs/type";
    var modifyUrl = "../../types/modify";
    var delUrl = "../../types/delete";
    managerType.initUI($,layer,form,laydate,typeUrl,unTypeUrl,modifyUrl,delUrl);//初始化界面
});
var managerType = {
    initUI:function ($,layer,form,laydate,typeUrl,unTypeUrl,modifyUrl,delUrl) {
        $(function () {
            managerType.initTypeTree($,form,laydate,typeUrl,modifyUrl);//初始化文章类型树且加载第一条记录
            managerType.initUnTypeTree($,form,laydate,unTypeUrl);//初始化未启用类型树
            managerType.regModifyBtn($,form,laydate,typeUrl,unTypeUrl,modifyUrl);//监听更改按钮
            managerType.regAddBtn($,form,laydate,typeUrl,modifyUrl);//监听添加按钮
            managerType.regDelBtn($,form,laydate,typeUrl,modifyUrl,delUrl);//监听删除按钮
        })
    },
    initUnTypeTree:function ($,form,laydate,unTypeUrl) {
        var index = parent.layer.load(2,{shade:0.5});
        $('#untype-tree').empty();//清空树形菜单
        setTimeout(function () {
            $.ajax({
                url:unTypeUrl,
                type:'post',
                data:{dr:1},
                dataType:'json',
                success:function (res,state) {
                    if(res.code==200){
                        layui.tree({
                            elem: '#untype-tree' ,
                            nodes:res.data,
                            click:function (node) {//监听类型树节点
                                managerType.loadContent($,form,laydate,node);//加载指定节点文章类型信息
                                managerType.addIdToDelBtn($,node.id);//把当前节点的id添加到删除按钮的data-id属性上
                            }
                        });
                        managerType.changeProp($);//监听类型树节点改变属性
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
    regDelBtn:function ($,form,laydate,typeUrl,modifyUrl,delUrl) {
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
                            data:{id:$('#delBtn').attr('data-id')},
                            dataType:'json',
                            success:function (res,state) {
                                if(res.code==200){
                                    parent.layer.msg(res.data.toString(), {icon: 1,shade:0.5});
                                    managerType.initTypeTree($,form,laydate,typeUrl,modifyUrl);
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
    regAddBtn:function ($,form,laydate,typeUrl,modifyUrl) {
        $('#addBtn').on('click',function (e) {
            e.preventDefault();
            //弹出框
            parent.layer.open({
                type: 2,
                title:'添加文章类型',
                area: ['360px', '300px'],
                skin: 'layui-layer-rim', //加上边框
                content: ['menus/addType.html', 'no'],
                end:function () {//关闭时刷新类型树
                    managerType.initTypeTree($,form,laydate,typeUrl,modifyUrl);
                }
            });
        })
    },

    initTypeTree:function ($,form,laydate,typeUrl) {
        var index = parent.layer.load(2,{shade:0.5});
        $('#type-tree').empty();//清空树形菜单
        setTimeout(function () {
            $.ajax({
                url:typeUrl,
                type:'get',
                dataType:'json',
                success:function (res,state) {
                    if(res.code==200){
                        layui.tree({
                            elem: '#type-tree' ,
                            nodes:res.data,
                            click:function (node) {//监听类型树节点
                                managerType.loadContent($,form,laydate,node);//加载指定节点文章类型信息
                                managerType.addIdToDelBtn($,node.id);//把当前节点的id添加到删除按钮的data-id属性上
                            }
                        });
                        managerType.changeProp($);//监听类型树节点改变属性
                        managerType.loadContent($,form,laydate,res.data[0]);//加载第一个文章类型信息
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
    regModifyBtn:function ($,form,laydate,typeUrl,unTypeUrl,modifyUrl) {
        $('#modify').on('click',function () {
            var index = parent.layer.load(2,{shade:0.5});
            setTimeout(function () {
                $.ajax({
                    url:modifyUrl,
                    type:'post',
                    data:{id:$('input[name="id"]').val(),
                            name:$('input[name="name"]').val(),
                            lv:$('input[name="lv"]').val(),
                            dr:$('input[name="dr"]').is(':checked')?0:1},
                    dataType:'json',
                    success:function (res,state) {
                        if(res.code==200){
                            parent.layer.msg(res.data.toString(), {icon: 1,shade:0.5});
                            managerType.initTypeTree($,form,laydate,typeUrl,modifyUrl);//刷新文章类型树
                            managerType.initUnTypeTree($,form,laydate,unTypeUrl);//刷新文章类型树
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
    loadContent:function ($,form,laydate,type) {
        $('input[name="id"]').val(type.id);
        $('input[name="name"]').val(type.name);
        $('input[name="lv"]').val(type.lv);
        $('input[name="createTime_no"]').val(laydate.now(type.createTime,'YYYY-MM-DD hh:mm:ss'));
        $('input[name="createUser_no"]').val(type.sysUser.nickname);
        $('input[name="ts"]').val(laydate.now(type.ts,'YYYY-MM-DD hh:mm:ss'));
        if (type.dr==0){
            $('#dr').html('<input type="checkbox" name="dr" lay-skin="switch" checked>');
        }else{
            $('#dr').html('<input type="checkbox" name="dr" lay-skin="switch">');
        }
        form.render('checkbox');
    }
}
