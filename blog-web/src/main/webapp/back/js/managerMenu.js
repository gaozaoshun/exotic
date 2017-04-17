/**
 * Created by Exotic on 2016-12-15.
 */
layui.use(['layer','jquery','form', 'laydate','tree'],function () {
    var layer = layui.layer;
    var $ = layui.jquery;
    var form = layui.form();
    var laydate = layui.laydate;
    var menuUrl = "../../menu/get?order=0&field=4&dr=0";//创建时间降序且未删除的菜单
    var unMenuUrl = "../../menu/get?order=0&field=4&dr=1";//默认创建时间降序且撤销的菜单
    var modifyUrl = "../../menu/modify";
    var delUrl = "../../menu/delete";
    managerMenu.initUI($,form,laydate,menuUrl,modifyUrl,delUrl,unMenuUrl);//初始化界面
});
var managerMenu= {
    initUI:function ($,form,laydate,menuUrl,modifyUrl,delUrl,unMenuUrl) {
        $(function () {
            managerMenu.initMenuTree($,form,laydate,menuUrl);//初始化菜单树且加载第一条记录
            managerMenu.initUnMenuTree($,form,laydate,unMenuUrl);//初始化未启用菜单树
            managerMenu.regModifyBtn($,form,laydate,modifyUrl,menuUrl,unMenuUrl);//监听更改按钮
            managerMenu.regAddBtn($,form,laydate,menuUrl,modifyUrl);//监听添加按钮
            managerMenu.regDelBtn($,form,laydate,menuUrl,modifyUrl,delUrl);//监听删除按钮
        })
    },
    initUnMenuTree:function ($,form,laydate,unMenuUrl) {
        var index = parent.layer.load(2,{shade:0.5});
        $('#unmenu-tree').empty();//清空树形菜单
        setTimeout(function () {
            $.ajax({
                url:unMenuUrl,
                type:'get',
                dataType:'json',
                success:function (res,state) {
                    if(res.code==200){
                        layui.tree({
                            elem: '#unmenu-tree' ,
                            nodes:res.data,
                            click:function (node) {//监听节点
                                managerMenu.loadContent($,form,laydate,node);//加载指定节点信息
                                managerMenu.addIdToDelBtn($,node.id);//把当前节点的id添加到删除按钮的data-id属性上
                            }
                        });
                        managerMenu.changeProp($);//监听节点改变属性
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
    initMenuTree:function ($,form,laydate,menuUrl) {
        var index = parent.layer.load(2,{shade:0.5});
        $('#type-tree').empty();//清空树形菜单
        setTimeout(function () {
            $.ajax({
                url:menuUrl,
                type:'get',
                dataType:'json',
                success:function (res) {
                    if(res.code==200){
                        layui.tree({
                            elem: '#type-tree' ,
                            nodes:res.data,
                            click:function (node) {//监听节点
                                managerMenu.loadContent($,form,laydate,node);//加载指定节点信息
                                managerMenu.addIdToDelBtn($,node.id);//把当前节点的id添加到删除按钮的data-id属性上
                            }
                        });
                        managerMenu.changeProp($);//监听类型树节点改变属性
                        managerMenu.loadContent($,form,laydate,res.data[0]);//加载第一个节点信息
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
    regDelBtn:function ($,form,laydate,menuUrl,modifyUrl,delUrl) {
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
                                    managerMenu.initMenuTree($,form,laydate,menuUrl,modifyUrl);
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
    regAddBtn:function ($,form,laydate,menuUrl,modifyUrl) {
        $('#addBtn').on('click',function (e) {
            e.preventDefault();
            //弹出框
            parent.layer.open({
                type: 2,
                title:'添加菜单项',
                area: ['360px', '485px'],
                skin: 'layui-layer-rim', //加上边框
                content: ['menus/addMenu.html', 'no'],
                end:function () {//关闭时刷新菜单树
                    managerMenu.initMenuTree($,form,laydate,menuUrl,modifyUrl);
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
    regModifyBtn:function ($,form,laydate,modifyUrl,menuUrl,unMenuUrl) {
        $('#modify').on('click',function () {
            var index = parent.layer.load(2,{shade:0.5});
            setTimeout(function () {
                $.ajax({
                    url:modifyUrl,
                    type:'post',
                    data:{id:$('input[name="id"]').val(),
                            name:$('input[name="name"]').val(),
                            url:$('input[name="url"]').val(),
                            descp:$('textarea[name="descp"]').val(),
                            lv:$('input[name="lv"]').val(),
                            dr:$('input[name="dr"]').is(':checked')?0:1},
                    dataType:'json',
                    success:function (res,state) {
                        if(res.code==200){
                            parent.layer.msg(res.data.toString(), {icon: 1,shade:0.5});
                            managerMenu.initMenuTree($,form,laydate,menuUrl,modifyUrl);//刷新菜单树
                            managerMenu.initUnMenuTree($,form,laydate,unMenuUrl);//刷新菜单树
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
    loadContent:function ($,form,laydate,menu) {
        $('input[name="id"]').val(menu.id);
        $('input[name="name"]').val(menu.name);
        $('input[name="url"]').val(menu.url);
        $('textarea[name="descp"]').val(menu.descp);
        $('input[name="lv"]').val(menu.lv);
        $('input[name="createTime_no"]').val(laydate.now(menu.createTime,'YYYY-MM-DD hh:mm:ss'));
        $('input[name="createUser_no"]').val(menu.sysUser.nickname);
        $('input[name="ts"]').val(laydate.now(menu.ts,'YYYY-MM-DD hh:mm:ss'));
        if (menu.dr==0){
            $('#dr').html('<input type="checkbox" name="dr" lay-skin="switch" checked>');
        }else{
            $('#dr').html('<input type="checkbox" name="dr" lay-skin="switch">');
        }
        form.render('checkbox');
    }
}
