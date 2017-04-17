/**
 * Created by Exotic on 2016/12/16.
 */
layui.use(['layer','jquery','form'],function () {
    var layer = layui.layer;
    var $ = layui.jquery;
    var form = layui.form();
    var addUrl = "../../types/add";
    addtype.regYesBtn($,form,addUrl);
});
var addtype = {
    regYesBtn:function ($,form,addUrl) {
        $(function () {
            $('#yes').on('click',function () {
                var index = layer.load(2,{shade:0.5});
                setTimeout(function () {
                    $.ajax({
                        url:addUrl,
                        type:'post',
                        data:{name:$('input[name="name"]').val(),
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