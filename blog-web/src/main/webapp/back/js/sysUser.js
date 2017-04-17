/**
 * Created by Exotic on 2016-12-11.
 */
layui.use(['form','layer','jquery','laydate','upload'], function(){
    var form = layui.form();
    var layer = layui.layer;
    var $ = layui.jquery;
    var laydate = layui.laydate;
    var uploadUrl="../../admin/upload/image";
    var userUrl = "../../sysUser/logined";
    var saveUrl = "../../sysUser/modify";
    sysUser.validateInput(form);//配置校验规则
    sysUser.initData(layer,laydate,$,userUrl,uploadUrl);//初始化用户信息
    sysUser.modifyAction($,form,layer,laydate,saveUrl,userUrl,uploadUrl);//提交
});
var sysUser = {
    modifyAction:function ($,form,layer,laydate,saveUrl,userUrl,uploadUrl) {
        $('#save').on('click',function () {
            var index = parent.layer.load(2,{shade:0.5});
            setTimeout(function () {
                var dataForm = {id:$('input[name="id"]').val(),
                    nickname:$('input[name="nickname"]').val(),
                    username:$('input[name="username"]').val(),
                    headimg:$('input[name="headimg"]').val(),
                    password:$('input[name="password"]').val()};
                console.log(dataForm)
                $.ajax({
                    url:saveUrl,
                    type:'post',
                    data:dataForm,
                    dataType:'json',
                    success:function (res,state) {
                        parent.layer.close(index);
                        if(res.code==200){
                            parent.layer.msg("您的个人信息修改成功！", {icon: 1,shade:0.5,time:1000,end:function () {
                                window.location.reload();
                            }});
                        }else{
                            parent.layer.msg(res.msg.toString(),{icon:2,shade:0.5});
                        }
                    },
                    error:function (xhr,state,error) {
                        parent.layer.close(index);
                        parent.layer.msg('HTTP请求失败！',{icon:2,shade:0.5});
                    }
                });
            },500);
        })
    },
    validateInput:function (form) {
        form.verify({
            nickname: function(value){
                if(!(/^[\u4E00-\u9FFF]{1,5}$/.test(value))){
                    return '用户名需要1~5个中文字符';
                }
            }
        });
    },
    uploadImg:function ($,layer,uploadUrl) {
        var index = "";
        layui.upload({
            elem:'#headImg',
            url: uploadUrl,
            title:'上传头像',
            before:function (input) {
                index = parent.layer.load(2,{shade:0.5});
            },
            success: function(res){
                parent.layer.close(index);
                if (res.code==200){
                    var url = res.data;
                    $('#preview').attr('src',url);
                    $('input[name="headimg"]').val(url.substring(url.indexOf("/upload/")));
                }else{
                    parent.layer.msg(res.msg);
                }
            }
        });
    },
    getCurUser:function (layer,laydate,$,userUrl,uploadUrl,index) {
        $.ajax({
            url:userUrl,
            type:'get',
            dataType:'json',
            success:function (res) {
                parent.layer.close(index);
                if(res.code==200){
                    var url = res.data.headimg;
                    $('#preview').attr('src',url);
                    $('input[name="headimg"]').val(url.substring(url.indexOf("/upload/")));
                    $('input[name="id"]').val(res.data.id);
                    $('input[name="nickname"]').val(res.data.nickname);
                    $('input[name="username"]').val(res.data.username);
                    $('input[name="password"]').val(res.data.password);
                    $('input[name="createTime"]').val(laydate.now(res.data.createTime, 'YYYY-MM-DD hh:mm:ss'));
                    sysUser.uploadImg($,layer,uploadUrl);//注册上传图片事件
                }else{
                    parent.layer.msg(res.msg);
                }
            },
            error:function (res) {
                parent.layer.msg(JSON.stringify(res));
            }
        })
    },
    initData:function (layer,laydate,$,userUrl,uploadUrl) {
      $(function () {
          var index = parent.layer.load(1,{shade:0.5});
          sysUser.getCurUser(layer,laydate,$,userUrl,uploadUrl,index);//获取当前用户
      })
    },
    save:function ($,form,layer,laydate,saveUrl,userUrl,uploadUrl) {
        form.on('submit(modifyAction)', function(data){
            var index = parent.layer.load(2,{shade:0.5});
            setTimeout(function () {
                var dataForm = {id:data.field.id,nickname:data.field.nickname,username:data.field.username,password:data.field.password,headimg:data.field.headimg}
                $.ajax({
                    url:saveUrl,
                    type:'post',
                    data:dataForm,
                    dataType:'json',
                    success:function (res) {
                        parent.layer.close(index);
                        if(res.code==200){
                            parent.layer.msg('用户信息更新成功！', {icon: 1,shade:0.5});
                            sysUser.initData(layer,laydate,$,userUrl,uploadUrl);
                        }else{
                            parent.layer.msg(res.msg,{icon:2,shade:0.5});
                        }

                    },
                    error:function (res) {
                        parent.layer.close(index);
                        parent.layer.msg(JSON.stringify(res));
                    }
                });
            },1500);
            return false;
        });
    }
}