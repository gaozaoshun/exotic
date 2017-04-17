/**
 * Created by Exotic on 2016-12-12.
 */
layui.use(['form', 'layedit', 'laydate','upload','laytpl'], function(){
    var form = layui.form()
        ,layer = layui.layer
        ,layedit = layui.layedit
        ,laydate = layui.laydate
        ,$ = layui.jquery;
    var uploadUrl = "../../admin/upload/image";
    var modifyUrl = "../../blogs/modify";
    var typeUrl = "../../blogs/type?dr=0";
    var getBlogUrl = "../../blogs/get?id="+editBlog.getId();
    editBlog.uploadImg($,uploadUrl);//配置上传预览图
    var editIndex = editBlog.createEditor(layedit);//创建一个编辑器
    editBlog.initData($,form,layedit,editIndex,typeUrl,getBlogUrl);//初始化数据
    editBlog.validateInput(form,layedit,editIndex);//配置校验规则
    editBlog.modifyAction($,form,layer,modifyUrl);//监听修改按钮事件
    editBlog.cancelAction($,layer);//监听取消按钮事件
});
var editBlog = {
    cancelAction:function ($,layer) {
        $('#cancel').on('click',function () {
            layer.closeAll();
        })
    },
    getId:function () {//获取当前URL的参数id值
        var url = window.location.href;
        return url.substring(url.indexOf("?id=")+4);
    },
    renderType:function ($,form,typeUrl,index,typeId) {
        $.ajax({
            url:typeUrl,
            type:'get',
            dataType:'json',
            success:function (res,state) {
                if(res.code==200){
                    var topOpts = '';
                    $.each(res.data,function (index,item) {
                        if( typeId == item.id)
                            topOpts +='<option value="'+item.id+'" selected>'+item.name+'</option>';
                        else
                            topOpts +='<option value="'+item.id+'">'+item.name+'</option>';
                    })
                    $(topOpts).appendTo('#typeTpl');
                    form.render('select');//重新渲染所有select框
                }else{
                    layer.msg(res.msg,{icon:2,shade:0.5});
                }
                parent.layer.close(index);
            },
            error:function (xhr,state,error) {
                parent.layer.close(index);
                layer.msg('HTTP请求失败！',{icon:2,shade:0.5});
            }
        });
    },
    initBlog:function ($,form,layedit,editIndex,typeUrl,getBlogUrl) {
        var index = parent.layer.load(2,{shade:0.5});
        setTimeout(function () {
            $.ajax({
                url:getBlogUrl,
                dataType:'json',
                success:function (res,state) {
                    if(res.code==200){
                        editBlog.innerPage($,form,layedit,editIndex,typeUrl,res.data,index);//数据拼接到页面
                    }else{
                        layer.msg(res.msg,{icon:2,shade:0.5});
                    }
                },
                error:function (xhr,state,error) {
                    parent.layer.close(index);
                    layer.msg('HTTP请求失败！',{icon:2,shade:0.5});
                }
            });
        },100);
    },
    innerPage:function ($,form,layedit,editIndex,typeUrl,blog,index) {
        $('input[name="id"]').val(blog.id);
        $('input[name="title"]').val(blog.title);
        $('textarea[name="summary"]').html(blog.summary);
        editBlog.setContent($,layedit,editIndex,blog.content);//设置content到编辑器
        $('input[name="cover"]').val(blog.cover);
        $('#preview').attr('src',"../.."+blog.cover);
        $('input[name="keyWords"]').val(blog.keyWords);
        editBlog.renderType($,form,typeUrl,index,blog.typeId);//加载文章类型并渲染
    },
    setContent:function ($,layedit,editIndex,content) {
        $('#LAY_layedit_1').contents().find('body').html(content);
        layedit.sync(editIndex);//同步编辑器内容到textarea
    },
    initData:function ($,form,layedit,editIndex,typeUrl,getBlogUrl) {
        $(function () {
            editBlog.initBlog($,form,layedit,editIndex,typeUrl,getBlogUrl);//初始化文章
        });
    },
    modifyAction:function ($,form,layer,modifyUrl) {
        form.on('submit(modify)', function(data){//监听修改按钮
            var index = layer.load(2,{shade:0.5});
            setTimeout(function () {
                $.ajax({
                    url:modifyUrl,
                    type:'post',
                    data:data.field,
                    dataType:'json',
                    success:function (res,state) {
                        layer.close(index);
                        if(res.code==200){
                            layer.msg(res.data.toString(), {icon: 1,shade:0.5,time:3,end:function () {
                                window.location.reload();
                            }});
                        }else{
                            layer.msg(res.msg,{icon:2,shade:0.5});
                        }
                    },
                    error:function (xhr,state,error) {
                        layer.close(index);
                        layer.msg('HTTP请求失败！',{icon:2,shade:0.5});
                    }
                });
            },1500);
            return false;
        });
    },
    validateInput:function (form,layedit,editIndex) {
        form.verify({
            title: function(value){
                if(value.length < 5){
                    return '标题至少得5个字符啊';
                }
            }
            ,pass: [/(.+){6,12}$/, '密码必须6到12位']
            ,content: function(value){
                layedit.sync(editIndex);
            }
        });
    },
    createEditor:function (layedit) {
        return layedit.build('editor',{height:600});
    },
    uploadImg:function ($,url) {
        var index = "";
        layui.upload({
            url: url,
            elem: '#images' ,
            before:function () {
                index = layer.load(2,{shade:0.5});
            },
            success: function(res){
                var url = res.data;
                preview.src = url;
                $('input[name="cover"]').val(url.substring(url.indexOf("/upload/")));
                layer.close(index);
            }
        });
    }
}