/**
 * Created by Exotic on 2016-12-15.
 */
layui.use(['jquery','layer','laydate','form'],function () {
    var layer = layui.layer;
    var $ = layui.jquery;
    var form = layui.form();
    var laydate = layui.laydate;
    var getUrl = "../../blogs/get";
    previewBlog.initData($,layer,form,laydate,getUrl);//初始化博客信息
})

var previewBlog = {
    initData:function ($,layer,form,laydate,getUrl) {
        $(function () {
            previewBlog.getBlog($,layer,form,laydate,getUrl);
        });
    },
    getUrl:function (getUrl) {
        var url = window.location.href;
        var param = url.substring(url.indexOf('?id=')+4);
        if (param.trim() == ''){
            parent.layer.alert("文章ID不能为空！",{icon:2,shade:0.5});
            return;
        }
        return getUrl+"?id="+param;
    },
    getBlog:function ($,layer,form,laydate,getUrl) {//获取博文信息
            var index = layer.load(2,{shade:0.5});
            $.ajax({
                url:previewBlog.getUrl(getUrl),
                type:'get',
                dataType:'json',
                success:function (res) {
                    if(res.code==200){
                        $('#title').html(res.data.title);
                        $('#createTime').html(laydate.now(res.data.createTime,'YYYY-MM-DD hh:mm:ss'));
                        $('#createUser').html(res.data.sysUser.nickname);
                        $('#lookNum').html(res.data.lookNum);
                        $('#likeNum').html(res.data.likeNum);
                        $('#talkNum').html(res.data.talkNum);
                        $('#content').html(res.data.content);
                    }else{
                        layer.msg(res.msg,{icon:2,shade:0.5});
                    }
                    layer.close(index);
                },
                error:function (xhr,state,error) {
                    layer.close(index);
                    layer.msg('HTTP请求失败！',{icon:2,shade:0.5});
                }
            })

    }
}