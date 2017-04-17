/**
 * Created by Exotic on 2016/12/23.
 */
layui.use(['layer','jquery','form','upload','flow'],function () {
    var layer = layui.layer;
    var $ = layui.jquery;
    var form = layui.form();
    var flow = layui.flow;
    var photosUrl = "../../photos/get";
    var photosParam={page:1,rows:10,order:1,field:5,dr:0};
    var regUrl = "../../photos/dr";
    var regParam = {dr:1};
    var delUrl = "../../photos/delete";
    var delParam = {};
    managerPhoto.regUpload($,flow,photosUrl,photosParam,regUrl,regParam);
    managerPhoto.regSearch($,flow,photosUrl,photosParam,regUrl,regParam);
    managerPhoto.regRubbishBtn($,flow,photosUrl,photosParam,delUrl,delParam);
    managerPhoto.regReloadBtn($);
    managerPhoto.initPhotos($,flow,photosUrl,photosParam,regUrl,regParam);
});
var managerPhoto = {
    regReloadBtn:function () {
        $(function () {
            $('#setBtn').on('click',function (e) {
                e.preventDefault();
                window.location.reload();
            })
        })
    },
    regRubbishBtn:function ($,flow,photosUrl,photosParam,delUrl,delParam) {
        $(function () {
            $('#rubbishBtn').on('click',function (e) {
                e.preventDefault();
                photosParam.dr = 1;
                managerPhoto.initPhotos($,flow,photosUrl,photosParam,delUrl,delParam);
            })
        })
    },
    regSearch:function ($,flow,photosUrl,photosParam,delUrl) {
      $(function () {
          $('#titleQ').on('click',function (e) {
              e.preventDefault();
              photosParam.name = $('#titleInput').val();
              managerPhoto.initPhotos($,flow,photosUrl,photosParam,delUrl);
          })
      })
    },
    regUpload:function ($,flow,photosUrl,photosParam,delUrl) {
        $(function () {
            $('#editBtn').on('click', function (e) {
                e.preventDefault();
                parent.layer.open({
                    type:2,
                    title:'上传相册',
                    content:'menus/addPhoto.html',
                    area:['417px','500px'],
                    end:function () {
                        managerPhoto.initPhotos($,flow,photosUrl,photosParam,delUrl);
                    }
                })
            });
        })
    },
    pageLoad:function ($,flow,photosUrl,photosParam,regUrl,regParam) {
        $('#lazy-load-img').empty();
        $('#lazy-load-img').off();//关闭元素上的触发事件
        var index = parent.layer.load(2,{shade:0.5});
        flow.load({
            elem: '#lazy-load-img' ,//流加载容器
            scrollElem:'#img-scoll',
            isLazyimg:true,//图片懒加载
            isAuto:false,
            done: function(page, next){ //执行下一页的回调
                photosParam.page = page;
                var lis = [];
                $.ajax({
                    url:photosUrl,
                    type:'post',
                    data:photosParam,
                    dataType:'json',
                    success:function (res) {
                        if(res.code==200){
                            layer.closeAll();
                            var photos = res.data;
                            var imgs = [];
                            layui.each(photos,function (index,photo){
                                var minUrl = photo.url;
                                var maxUrl = minUrl.replace("min","max");
                                imgs.push('<li  class="exotic-img-css" style="position: relative"><img lay-src="'+minUrl+'" layer-src="'+maxUrl+'" alt="'+photo.name+'">' +
                                    '<div style="width:157px;height:20px;background: rgba(255,255,255,0.5);text-align:center;position: absolute;top: 138px;left: 0px;">' +
                                    '<span>'+photo.name+'</span><a href="#"  data-id="'+photo.id+'" ><i class="layui-icon" style="float: right">&#xe640;</i></a></div></li>');
                            })
                            next(imgs.join(''), page < res.pages);
                            layer.photos({//预览图片
                                photos: '#lazy-load-img'
                            });
                            managerPhoto.regDelBtn($,flow,photosUrl,photosParam,regUrl,regParam);
                        }else{
                            parent.layer.msg(res.msg,{icon:2,shade:0.5});
                        }
                        parent.layer.close(index);
                    },
                    error:function (xhr,state,error) {
                        parent.layer.close(index);
                        parent.layer.msg('HTTP请求失败！',{icon:2,shade:0.5});
                    }
                });
            }
        });
    },
    regDelBtn:function ($,flow,photosUrl,photosParam,regUrl,regParam) {
        $('#lazy-load-img>li>div>a').on('click',function (e) {
            e.preventDefault();
            var dataId = $(this).attr('data-id');
            regParam.ids=[dataId];
            var index = parent.layer.load(2,{shade:0.5});
            setTimeout(function () {
                $.ajax({
                    url:regUrl,
                    type:'post',
                    data:regParam,
                    dataType:'json',
                    success:function (res,state) {
                        if(res.code==200){
                            parent.layer.msg(res.data,{icon:1,shade:0.5,time:1000,end:function () {
                                managerPhoto.initPhotos($,flow,photosUrl,photosParam,regUrl,regParam);
                            }})
                        }else{
                            parent.layer.msg(res.msg,{icon:2,shade:0.5});
                        }
                        parent.layer.close(index);
                    },
                    error:function (xhr,state,error) {
                        parent.layer.close(index);
                        parent.layer.msg('HTTP请求失败！',{icon:2,shade:0.5});
                    }
                });
            },100);
        })
    },
    initPhotos:function ($,flow,photosUrl,photosParam,regUrl,regParam) {
        $(function () {
            managerPhoto.pageLoad($,flow,photosUrl,photosParam,regUrl,regParam);//初始化图片
        })
    }
}