/**
 * Created by Exotic on 2016/12/16.
 */
layui.use(['layer','jquery','form','upload'],function () {
    var layer = layui.layer;
    var $ = layui.jquery;
    var form = layui.form();
    var addUrl = "../../sys/user/add";
    var uploadUrl = "../../admin/upload/image";
    addSysUser.uploadImg($,uploadUrl);
    addSysUser.regYesBtn($,form,addUrl);
});
var addSysUser = {
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
                        data:{
                            headimg:$('input[name="headimg"]').val(),
                            username:$('input[name="username"]').val(),
                            password:$('input[name="password"]').val(),
                            nickname:$('input[name="nickname"]').val(),
                            state:$('input[name="state"]').val(),
                            type:$('input[name="type"]').val()},
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