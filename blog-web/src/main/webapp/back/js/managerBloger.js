/**
 * Created by Exotic on 2016-12-15.
 */
layui.use(['layer','jquery','form', 'laydate','tree','upload'],function () {
    var layer = layui.layer;
    var $ = layui.jquery;
    var form = layui.form();
    var laydate = layui.laydate;
    var blogerUrl = "../../bloger/get?order=1&field=3&dr=0";//创建时间降序且dr=0的菜单
    var unblogerUrl = "../../bloger/get?order=1&field=3&dr=1";//默认创建时间降序且dr=1的菜单
    var modifyUrl = "../../bloger/modify";
    var delUrl = "../../bloger/delete/real";
    var uploadUrl = "../../admin/upload/image";
    managerBloger.uploadImg($,uploadUrl);//初始化上传组件
    managerBloger.initUI($,form,laydate,blogerUrl,modifyUrl,delUrl,unblogerUrl,uploadUrl);//初始化界面
});
var managerBloger= {
    initUI:function ($,form,laydate,blogerUrl,modifyUrl,delUrl,unblogerUrl,uploadUrl) {
        $(function () {
            managerBloger.initBlogerTree($,form,laydate,blogerUrl);//初始化菜单树且加载第一条记录
            managerBloger.initUnBlogerTree($,form,laydate,unblogerUrl);//初始化未启用菜单树
            managerBloger.regModifyBtn($,form,laydate,modifyUrl,blogerUrl,unblogerUrl);//监听更改按钮
            managerBloger.regAddBtn($,form,laydate,blogerUrl,modifyUrl);//监听添加按钮
            managerBloger.regDelBtn($,form,laydate,blogerUrl,modifyUrl,delUrl);//监听删除按钮
        })
    },
    initUnBlogerTree:function ($,form,laydate,unblogerUrl) {
        var index = parent.layer.load(2,{shade:0.5});
        $('#unmenu-tree').empty();//清空树形菜单
        setTimeout(function () {
            $.ajax({
                url:unblogerUrl,
                type:'get',
                dataType:'json',
                success:function (res,state) {
                    if(res.code==200){
                        layui.tree({
                            elem: '#unmenu-tree' ,
                            nodes:res.data,
                            click:function (node) {//监听节点
                                managerBloger.loadContent($,form,laydate,node);//加载指定节点信息
                                managerBloger.addIdToDelBtn($,node.id);//把当前节点的id添加到删除按钮的data-id属性上
                            }
                        });
                        managerBloger.changeProp($);//监听节点改变属性
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
    uploadImg:function ($,uploadUrl) {
        var index = "";
        layui.upload({
            url: uploadUrl,
            elem: '#images' ,
            before:function () {
                index = layer.load(2,{shade:0.5});
            },
            success: function(res){
                var url = res.data;
                preview.src = url;
                $('input[name="headimg"]').val(url.substring(url.indexOf("/upload/")));
                layer.close(index);
            }
        });
    },
    initBlogerTree:function ($,form,laydate,blogerUrl) {
        var index = parent.layer.load(2,{shade:0.5});
        $('#type-tree').empty();//清空树形菜单
        setTimeout(function () {
            $.ajax({
                url:blogerUrl,
                type:'get',
                dataType:'json',
                success:function (res) {
                    if(res.code==200){
                        layui.tree({
                            elem: '#type-tree' ,
                            nodes:res.data,
                            click:function (node) {//监听节点
                                managerBloger.loadContent($,form,laydate,node);//加载指定节点信息
                                managerBloger.addIdToDelBtn($,node.id);//把当前节点的id添加到删除按钮的data-id属性上
                            }
                        });
                        managerBloger.changeProp($);//监听类型树节点改变属性
                        managerBloger.loadContent($,form,laydate,res.data[0]);//加载第一个节点信息
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
    regDelBtn:function ($,form,laydate,blogerUrl,modifyUrl,delUrl) {
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
                                    managerBloger.initBlogerTree($,form,laydate,blogerUrl,modifyUrl);
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
    regAddBtn:function ($,form,laydate,blogerUrl,modifyUrl) {
        $('#addBtn').on('click',function (e) {
            e.preventDefault();
            //弹出框
            parent.layer.open({
                type: 2,
                title:'添加博主',
                area: ['360px', '485px'],
                skin: 'layui-layer-rim', //加上边框
                content: 'menus/addBloger.html',
                end:function () {//关闭时刷新菜单树
                    managerBloger.initBlogerTree($,form,laydate,blogerUrl,modifyUrl);
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
    regModifyBtn:function ($,form,laydate,modifyUrl,blogerUrl,unblogerUrl) {
        $('#modify').on('click',function () {
            var index = parent.layer.load(2,{shade:0.5});
            setTimeout(function () {
                $.ajax({
                    url:modifyUrl,
                    type:'post',
                    data:{id:$('input[name="id"]').val(),
                            name:$('input[name="name"]').val(),
                            headimg:$('input[name="headimg"]').val(),
                            job:$('input[name="job"]').val(),
                            school:$('input[name="school"]').val(),
                            intro:$('textarea[name="intro"]').val(),
                            wechat:$('input[name="wechat"]').val(),
                            qq:$('input[name="qq"]').val(),
                            weibo:$('input[name="weibo"]').val(),
                            dr:$('input[name="dr"]').is(':checked')?0:1},
                    dataType:'json',
                    success:function (res,state) {
                        if(res.code==200){
                            parent.layer.msg(res.data.toString(), {icon: 1,shade:0.5});
                            managerBloger.initBlogerTree($,form,laydate,blogerUrl,modifyUrl);//刷新菜单树
                            managerBloger.initUnBlogerTree($,form,laydate,unblogerUrl);//刷新菜单树
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
        $('input[name="name"]').val(bloger.name);
        $('input[name="headimg"]').val(bloger.headimg);
        $('#preview').attr("src",bloger.headimg);
        $('input[name="job"]').val(bloger.job);
        $('input[name="school"]').val(bloger.school);
        $('textarea[name="intro"]').val(bloger.intro);
        $('input[name="qq"]').val(bloger.qq);
        $('input[name="wechat"]').val(bloger.wechat);
        $('input[name="weibo"]').val(bloger.weibo);
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
