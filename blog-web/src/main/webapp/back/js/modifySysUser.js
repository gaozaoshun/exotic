/**
 * Created by Exotic on 2016/12/16.
 */
layui.use(['layer','jquery','form','upload'],function () {
    var layer = layui.layer;
    var $ = layui.jquery;
    var form = layui.form();
    var getUrl = "../../sys/user/get";
    var addUrl = "../../sys/user/modify";
    var uploadUrl = "../../admin/upload/image";
    modifySysUser.uploadImg($,uploadUrl);
    modifySysUser.getData($,form,getUrl);
    modifySysUser.regYesBtn($,form,addUrl);
});
var modifySysUser = {
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
    getId:function () {
        var url = window.location.href;
        var id = url.substring(url.indexOf('?id=')+4);
        if (id==null){
            parent.layer.alert("此用户可能已被删除！",{icon:2,shade:0.5});
        }else{
            return id;
        }
    },
    getData:function ($,form,getUrl) {
        $(function () {
            var index = layer.load(2,{shade:0.5});
            setTimeout(function () {
                $.ajax({
                    url:getUrl,
                    type:'post',
                    data:{id:modifySysUser.getId(),order:1,field:3},
                    dataType:'json',
                    success:function (res,state) {
                        if(res.code==200){
                            var wisdom = res.data[0];
                            if(wisdom==null){
                                parent.layer.alert("不用户此名言！",{icon:2,shade:0.5});
                            }else {
                                $('input[name="id"]').val(wisdom.id);
                                $('#preview').attr("src",wisdom.headimg);
                                $('input[name="headimg"]').val(wisdom.headimg);
                                $('input[name="username"]').val(wisdom.username);
                                $('input[name="password"]').val(wisdom.password);
                                $('input[name="nickname"]').val(wisdom.nickname);
                                $('input[name="state"]').val(wisdom.state);
                                $('input[name="type"]').val(wisdom.type);
                                if (wisdom.dr == 0){
                                    $('input[name="dr"]').attr("checked","checked");
                                    form.render('checkbox');
                                }
                            }
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
    regYesBtn:function ($,form,addUrl) {
        $(function () {
            $('#yes').on('click',function () {
                var index = layer.load(2,{shade:0.5});
                setTimeout(function () {
                    $.ajax({
                        url:addUrl,
                        type:'post',
                        data:{id:$('input[name="id"]').val(),
                                headimg:$('input[name="headimg"]').val(),
                                username:$('input[name="username"]').val(),
                                password:$('input[name="password"]').val(),
                                nickname:$('input[name="nickname"]').val(),
                                state:$('input[name="state"]').val(),
                                type:$('input[name="type"]').val(),
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