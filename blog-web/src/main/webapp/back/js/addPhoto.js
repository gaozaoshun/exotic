/**
 * Created by Exotic on 2016/12/16.
 */
layui.use(['layer','jquery','form','upload'],function () {
    var layer = layui.layer;
    var $ = layui.jquery;
    var form = layui.form();
    var addUrl = "../../photos/add";
    var uploadUrl = "../../admin/upload/image";
    addPhoto.uploadImg($,uploadUrl);
    addPhoto.regYesBtn($,form,addUrl);
});
var addPhoto = {
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
                $('input[name="url"]').val(url.substring(url.indexOf("/upload/")));
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
                            url:$('input[name="url"]').val(),
                            lv:$('input[name="lv"]').val()
                          },
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