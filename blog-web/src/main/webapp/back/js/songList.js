/**
 * Created by Exotic on 2016/12/16.
 */
layui.use(['layer','jquery','form'],function () {
    var layer = layui.layer;
    var $ = layui.jquery;
    var form = layui.form();
    var saveUrl = "../../song/modify/list";
    var mp3Url = "../../song/get";
    var mp3Param = {page:1,rows:10,order:0,field:4,dr:0};
    var drUrl = "../../song/dr";
    songList.initList($,form,mp3Url,mp3Param,saveUrl,drUrl);//初始化播放列表
    songList.regSaveBtn($,saveUrl);//监听提交按钮

});
var songList = {

    regXIcon:function ($,form,mp3Url,mp3Param,saveUrl,drUrl) {
        $('.songUl>li>blockquote>a').off('click').on('click',function(e){
            e.preventDefault();
            $(this).children('i').animate({fontSize:'30px'}).animate({fontSize:'16px'});
            var dataForm = {ids:[$(this).attr('data-id')],dr:1};
            var index = layer.load(2,{shade:0.5});
            var li = $(this).parent('blockquote').parent('li');
            $.ajax({
                url:drUrl,
                type:'post',
                data:dataForm,
                dataType:'json',
                success:function (res,state) {
                    if(res.code==200){
                        //删除操作
                        $(li).fadeOut('slow');
                        $(li).empty().remove();
                        parent.layer.msg(res.data.toString(),{icon:1,shade:0.5,time:1000});
                    }else{
                        parent.layer.msg(res.msg,{icon:2,shade:0.5});
                    }
                    layer.close(index);
                },
                error:function (xhr,state,error) {
                    layer.close(index);
                    parent.layer.msg('HTTP请求失败！',{icon:2,shade:0.5});
                }
            });
        });
    },
    regSaveBtn:function ($,saveUrl) {
        $('#save').off('click').on('click',function () {
            var list = [];
            $('.songUl>li').each(function (index,item) {
                list.push({id:$(this).find('a').attr('data-id'),lv:index+1});
            })
            var daraForm={list:list};
            alert(JSON.stringify(daraForm))
            var index = layer.load(2,{shade:0.5});
            $.ajax({
                url:saveUrl,
                type:'post',
                contentType:'application/json;charset=utf-8',
                data:JSON.stringify(daraForm),
                dataType:'json',
                success:function (res,state) {
                    if(res.code==200){
                        layer.msg(res.data,{icon:1,shade:0.5});
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
        })
    },
    initList:function ($,form,mp3Url,mp3Param,saveUrl,drUrl) {
        $(function () {
        var index = layer.load(2,{shade:0.5});
            $.ajax({
                url:mp3Url,
                type:'post',
                data:mp3Param,
                dataType:'json',
                success:function (res,state) {
                    if(res.code==200){
                        var lis='';
                        $.each(res.data,function (index,item) {
                            lis += '<li>'+
                                '<blockquote class="layui-elem-quote">'+
                                '<i class="layui-icon" style="color: #1b7e5a">&#xe62e;</i>'+
                                '<span> '+item.name+' --- '+item.author+'</span>'+
                                '<a href="#" data-id="'+item.id+'" style="float:right;margin-top: 2px;"><i class="layui-icon">&#x1007;</i></a>'+
                                '</blockquote>'+
                                '</li>';
                        })
                        $(lis).appendTo('.songUl').fadeIn('slow');
                        songList.loadMp3($,form,mp3Url);//加载select框中数据
                        songList.regXIcon($,form,mp3Url,mp3Param,saveUrl,drUrl);//监听撤销事件
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
        })
    },
    regChooseBtn:function ($) {
        $('#chooseBtn').off('click').on('click',function () {
            var id = $('select[name="mp3"]').val();
            var name =  $('dd[lay-value="'+id+'"]').text();
            //点击确定时检测是否有相同的歌曲存在
            if (!songList.checkUl($,id,name)){
                parent.layer.msg("播放列表已存在此歌曲！",{icon:2,shade:0.5});
                return;
            }
            var li = '<li>'+
                '<blockquote class="layui-elem-quote">'+
                '<i class="layui-icon" style="color: #1b7e5a">&#xe62e;</i>'+
                '<span> '+name+'</span>'+
                '<a href="#" data-id="'+id+'" style="float:right;margin-top: 2px;"><i class="layui-icon">&#x1007;</i></a>'+
                '</blockquote>'+
                '</li>';
            $(li).appendTo('.songUl');
        })
    },
    checkUl:function ($,id,name) {
        $('.songUl>li>blockquote>a').each(function (index,item) {
            var dataId = $(this).attr('data-id');
            if(dataId==id){
                return false;
            }
        })
        return true;
    },
    loadMp3:function ($,form,mp3Url) {
      $(function () {
          var index = layer.load(2,{shade:0.5});
          $.ajax({
              url:mp3Url,
              type:'post',
              data:{order:1,field:3,dr:0},
              dataType:'json',
              success:function (res,state) {
                  if(res.code==200){
                      var opts = '';
                      $.each(res.data,function (index,item) {
                          opts +='<option value="'+item.id+'">'+item.name+' --- '+item.author+'</option>';
                      })
                      $(opts).appendTo('select[name="mp3"]');
                      form.render('select');
                      songList.regChooseBtn($,form);//注册确定按钮
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
      })
    }
}