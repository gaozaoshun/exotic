/**
 * Created by Exotic on 2016-12-10.
 */
layui.use(['layer', 'form'], function() {
    var layer = layui.layer,
        $ = layui.jquery,
        form = layui.form();
    //TODO 验证码
    $('#code').unbind().bind('click',function () {
        var url = $(this).attr('src')+"?ts="+new Date().getTime();
        $(this).attr('src',url);
    })
    //TODO 登陆操作
    form.on('submit(login)',function(data){
        var index = layer.load(1,{shade:0.5});
        //TODO 延迟加载
        setTimeout(function () {
            $.ajax({
                url:'../admin/login',
                type:'post',
                data:data.field,
                dataType:'json',
                success:function (res) {
                    layer.close(index);
                    if(res.code==200){
                        window.location.href=res.data;
                    }else{
                        //TODO 清空密码和验证码
                        layer.msg(res.msg);
                        $('input[name="password"]').val("");
                        $('input[name="code"]').val("");
                    }
                },
                error:function (res) {
                    layer.msg(JSON.stringify(res));
                    $('input[name="password"]').val("");
                    $('input[name="code"]').val("");
                }
            })
        },100)
        return false;
    });
    //TODO 校验规则
    form.verify({
        username: function(value){
            if(!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)){
                return '用户名不能有特殊字符';
            }
            if(/(^\_)|(\__)|(\_+$)/.test(value)){
                return '用户名首尾不能出现下划线\'_\'';
            }
            // if(/^\d+\d+\d$/.test(value)){
            //     return '用户名不能全为数字';
            // }
        }
        //我们既支持上述函数式的方式，也支持下述数组的形式
        //数组的两个值分别代表：[正则匹配、匹配不符时的提示文字]
        // ,password: [
        //     /^[\S]{6,12}$/
        //     ,'密码必须6到12位，且不能出现空格'
        // ]
    });
});