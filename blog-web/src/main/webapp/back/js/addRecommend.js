/**
 * Created by Exotic on 2016/12/16.
 */
layui.use(['layer','jquery','form'],function () {
    var layer = layui.layer;
    var $ = layui.jquery;
    var form = layui.form();
    var addUrl = "../../recommend/add";
    var blogUrl = "../../blogs/query";
    addRecommend.loadBlog($,form,blogUrl,addUrl);

});
var addRecommend = {
    loadBlog:function ($,form,blogUrl,addUrl) {
      $(function () {
          var index = layer.load(2,{shade:0.5});
          setTimeout(function () {
              $.ajax({
                  url:blogUrl,
                  type:'post',
                  data:{order:1,field:3,dr:0},
                  dataType:'json',
                  success:function (res,state) {
                      if(res.code==200){
                          var opts = '';
                          $.each(res.data,function (index,item) {
                              opts +='<option value="'+item.id+'">'+item.title+'</option>';
                          })
                          $(opts).appendTo('select[name="blogId"]');
                          form.render('select');
                          //注册提交按钮
                          addRecommend.regYesBtn($,form,addUrl);
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
                        data:{blogId:$('select[name="blogId"]').val(),
                            lv:$('input[name="lv"]').val()},
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