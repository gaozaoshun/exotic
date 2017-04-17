/**
 * Created by Exotic on 2016/12/16.
 */
layui.use(['layer','jquery','form','upload'],function () {
    var layer = layui.layer;
    var $ = layui.jquery;
    var form = layui.form();
    var modifyUrl = "../../recommend/modify";
    chooseLV.regYesBtn($,form,modifyUrl);
});
var chooseLV = {
    getId:function () {
        var url = window.location.href;
        var id = url.substring(url.indexOf("?id=")+4);
        if (id==null){
            parent.layer.msg('不存在此推荐文章',{icon:1,shade:0.5});
        }
        return id;
    },
    regYesBtn:function ($,form,modifyUrl) {
        $(function () {
            $('#yes').on('click',function () {
                var index = layer.load(2,{shade:0.5});
                setTimeout(function () {
                    $.ajax({
                        url:modifyUrl,
                        type:'post',
                        data:{id:chooseLV.getId(),
                            lv:$('select[name="lv"]').val()},
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