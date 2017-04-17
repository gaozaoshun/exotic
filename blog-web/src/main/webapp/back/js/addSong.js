/**
 * Created by Exotic on 2016/12/16.
 */
layui.use(['layer','jquery','form','upload'],function () {
    var layer = layui.layer;
    var $ = layui.jquery;
    var form = layui.form();
    var addUrl = "../../song/add";
    var uploadImgUrl = "../../admin/upload/image";
    var uploadAudioUrl = "../../admin/upload/audio";
    addSong.uploadImg($,uploadImgUrl);
    addSong.uploadSong($,uploadAudioUrl);
    addSong.regYesBtn($,form,addUrl);
});
var addSong = {
    getId:function () {
        var url = window.location.href;
        var id = url.substring(url.indexOf("?id=")+4);
        if (id==null){
            parent.layer.msg("不存在此歌曲",{icon:2,shade:0.5});
            return;
        }
        return id;
    },
    uploadSong:function ($,uploadUrl) {
        var index = "";
        layui.upload({
            url: uploadUrl,
            elem: '#song-upload',
            before:function () {
                index = layer.load(2,{shade:0.5});
            },
            success: function(res){
                var url = res.data;
                $('input[name="url"]').val(url.substring(url.indexOf("/upload/")));
                layer.close(index);
            }
        });
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
                $('input[name="img"]').val(url.substring(url.indexOf("/upload/")));
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
                            img:$('input[name="img"]').val(),
                            author:$('input[name="author"]').val(),
                            lv:$('input[name="lv"]').val(),
                            special:$('input[name="special"]').val(),
                            likeNum:$('input[name="likeNum"]').val(),
                            url:$('input[name="url"]').val()},
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