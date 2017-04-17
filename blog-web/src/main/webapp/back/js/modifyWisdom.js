/**
 * Created by Exotic on 2016/12/16.
 */
layui.use(['layer','jquery','form','upload'],function () {
    var layer = layui.layer;
    var $ = layui.jquery;
    var form = layui.form();
    var getUrl = "../../wisdom/get";
    var addUrl = "../../wisdom/modify";
    var uploadUrl = "../../admin/upload/image";
    modifyWisdom.uploadImg($,uploadUrl);
    modifyWisdom.getData($,form,getUrl);
    modifyWisdom.regYesBtn($,form,addUrl);
});
var modifyWisdom = {
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
    getId:function () {
        var url = window.location.href;
        var id = url.substring(url.indexOf('?id=')+4);
        if (id==null){
            parent.layer.alert("此名言可能已被删除！",{icon:2,shade:0.5});
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
                    data:{id:modifyWisdom.getId(),order:1,field:3},
                    dataType:'json',
                    success:function (res,state) {
                        if(res.code==200){
                            var wisdom = res.data[0];
                            if(wisdom==null){
                                parent.layer.alert("不存在此名言！",{icon:2,shade:0.5});
                            }else {
                                $('input[name="id"]').val(wisdom.id);
                                $('#preview').attr("src",wisdom.img);
                                $('input[name="img"]').val(wisdom.img);
                                $('input[name="title"]').val(wisdom.title);
                                $('textarea[name="text"]').val(wisdom.text);
                                $('input[name="lv"]').val(wisdom.lv);
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
                                img:$('input[name="img"]').val(),
                                title:$('input[name="title"]').val(),
                                text:$('textarea[name="text"]').val(),
                                lv:$('input[name="lv"]').val(),
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