/**
 * Created by Exotic on 2016-12-12.
 */
layui.use(['form', 'layedit', 'laydate','upload','laytpl'], function(){
    var form = layui.form()
        ,layer = layui.layer
        ,layedit = layui.layedit
        ,laydate = layui.laydate
        ,laytpl = layui.laytpl
        ,$ = layui.jquery;
    var uploadUrl = "../../admin/upload/image";
    var saveUrl = "../../blogs/publish";
    var typeUrl = "../../blogs/type";
    publishBlog.uploadImg($,uploadUrl);//配置上传预览图
    var editIndex = publishBlog.createEditor(layedit);//创建一个编辑器
    publishBlog.initData($,laytpl,form,typeUrl);//初始化数据
    publishBlog.validateInput(form,layedit,editIndex);//配置校验规则
    publishBlog.save($,form,layer,saveUrl);//监听提交事件
});
var publishBlog = {
    typeTPL:function ($,laytpl,form,typeUrl) {
        $.ajax({
            url:typeUrl,
            type:'post',
            data:{dr:0},
            dataType:'json',
            success:function (res,state) {
                if(res.code==200){
                    var topOpts = '';
                    $.each(res.data,function (index,item) {
                        topOpts +='<option value="'+item.id+'">'+item.name+'</option>';
                    })
                    $(topOpts).appendTo('#typeTpl');
                    form.render('select');//重新渲染所有select框
                }else{
                    layer.msg(res.msg,{icon:2,shade:0.5});
                }
            },
            error:function (xhr,state,error) {
                layer.msg('HTTP请求失败！',{icon:2,shade:0.5});
            }
        });
    },
    initData:function ($,laytpl,form,typeUrl) {
        $(function () {
            publishBlog.typeTPL($,laytpl,form,typeUrl);//初始化文章类型模板
        });
    },
    save:function ($,form,layer,saveUrl) {
        form.on('submit(save)', function(data){//监听提交
            var index = layer.load(2,{shade:0.5});
            setTimeout(function () {
                $.ajax({
                    url:saveUrl,
                    type:'post',
                    data:data.field,
                    dataType:'json',
                    success:function (res,state) {
                        layer.close(index);
                        if(res.code==200){
                            layer.msg(res.data.toString(), {icon: 1,shade:0.5,end:function () {
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
        layedit.set({
            uploadImage: {
                url: '/admin/upload/layedit' //接口url
                ,type: 'post' //默认post
            }
        });
        return layedit.build('editor');
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