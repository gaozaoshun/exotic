/**
 * Created by Exotic on 2016/12/16.
 */
layui.use(['layer','jquery','form','upload'],function () {
    var layer = layui.layer;
    var $ = layui.jquery;
    var form = layui.form();
    var addUrl = "../../bloger/add";
    var uploadUrl = "../../admin/upload/image";
    addtype.uploadImg($,uploadUrl);
    addtype.regYesBtn($,form,addUrl);
});
var addtype = {
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
    regYesBtn:function ($,form,addUrl) {
        $(function () {
            $('#yes').on('click',function () {
                var index = layer.load(2,{shade:0.5});
                setTimeout(function () {
                    $.ajax({
                        url:addUrl,
                        type:'post',
                        data:{name:$('input[name="name"]').val(),
                            headimg:$('input[name="headimg"]').val(),
                            job:$('input[name="job"]').val(),
                            school:$('input[name="school"]').val(),
                            intro:$('textarea[name="intro"]').val(),
                            qq:$('input[name="qq"]').val(),
                            wechat:$('input[name="wechat"]').val(),
                            weibo:$('input[name="weibo"]').val(),
                            dr:$('input[name="dr"]').is(':checked')?0:1},
                        dataType:'json',
                        success:function (res,state) {
                            if(res.code==200){
                                layer.msg(res.data,{icon:1,shade:0.5,end:function () {
                                    parent.layer.closeAll();
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
        })
    }
}