/**
 * Created by Exotic on 2016/12/18.
 */
layui.use(['flow','layedit','layer'], function(){
    var layer = layui.layer;
    var $ = layui.jquery; //不用额外加载jQuery，flow模块本身是有依赖jQuery的，直接用即可。
    var flow = layui.flow;
    var layedit = layui.layedit;
    var commentUrl = "../comments/get";
    var publishUrl = "../comments/publish";
    var formData = {page:1,rows:3,order:1,field:3,blogId:detail.getId(),dr:0};
    detail.initUI($,flow,layedit,commentUrl,publishUrl,formData);
});
var detail = {
    getId:function () {
        var url = window.location.href;
        return url.substring(url.indexOf("?id=")+4);
    },
    initUI:function ($,flow,layedit,commentUrl,publishUrl,formData){
        var index = this.initEditor($,layedit); //初始化编辑器
        this.regPublishBtn($,flow,layedit,index,publishUrl,commentUrl,formData);
        this.initFlow($,flow,commentUrl,formData);//流加载评论区
    },
    regPublishBtn:function($,flow,layedit,lindex,publishUrl,commentUrl,formData) {
        $('#publish').off('click').on('click',function () {
            var index = layer.load(2,{shade:0.5});
            var blogId = $(this).attr("data-blogId");
            setTimeout(function () {
                $.ajax({
                    url:publishUrl,
                    type:'post',
                    data:{blogId:blogId,content:layedit.getContent(lindex)},
                    dataType:'json',
                    success:function (res,state) {
                        if(res.code==200){
                            alert(res.data);
                        }else{
                            alert(res.msg);
                        }
                        //刷新评论区
                        window.location.reload();
                        layer.close(index);
                    },
                    error:function (xhr,state,error) {
                        layer.close(index);
                        alert('HTTP请求失败！');
                    }
                });
            },100);
        })
    },
    initEditor:function ($,layedit) {
        var index = layedit.build('comedit',{
            height:100,
            tool: ['face'],
        });
        return index;
    },
    initFlow:function ($,flow,commentUrl,formData) {
        var floor = 0;
        flow.load({
            elem: '#comment' //指定列表容器
            ,isAuto:false
            ,done: function(page, next){ //到达临界点（默认滚动触发），触发下一页
                formData.page=page;
                var lis = [];
                //以jQuery的Ajax请求为例，请求下一页数据（注意：page是从2开始返回）
                $.ajax({
                    url:commentUrl,
                    type:'post',
                    data:formData,
                    dataType:'json',
                    success:function (res) {
                        if (res.code==200){
                            layui.each(res.data, function(index, item){
                                floor++;
                                lis.push(detail.appendComment(index,item,floor));
                            });
                            //执行下一页渲染，第二参数为：满足“加载更多”的条件，即后面仍有分页
                            //pages为Ajax返回的总页数，只有当前页小于总页数的情况下，才会继续出现加载更多
                            next(lis.join(''), page < res.pages);
                        }else{
                            layer.msg(res.msg,{icon:2,shade:0.5});
                        }
                    },
                    error:function (xhr,state,error) {
                        layer.msg('HTTP请求失败！',{icon:2,shade:0.5});
                    }
                })
            }
        });
    },
    appendComment:function (index,item,floor) {
        return '<div class="content" style="position:relative;border-top: 1px solid #EAEAEA;margin-top:35px;padding: 10px 20px;">'+
        '<div class="c_head" style="position: absolute;top: 17px;left: 15px">'+
        '<a href="#">'+
        '<img src="'+item.user.headimgurl+'" alt="头像" style="width:50px;height: 50px;border-radius: 100%;">'+
        '</a>'+
        '</div>'+
        '<div class="c_content" style="display: inline-block;">'+
        '<div class="c_user" style="display: inline-block;position: relative;top: 3px; left: 60px">'+
        '<a href="" style="font-weight: bold;color: #6d757a;vertical-align: middle;font-size: 12px;">'+item.user.nickname+
        '</a> '+
        '<a href="#" class="exotic-icon-sex-'+item.user.sex+'"></a>'+
        '</div>'+
        '<div class="c_msg" style="position: relative;top: 10px; left: 60px;width: 500px">'+item.content+
        '</div>'+
        '<div class="c_foot"  style="position: relative;top: 40px; left: 60px;width: 500px">'+
        '<span style="color: #6d757a;vertical-align: middle;font-size: 12px;">#'+floor+'   来自'+detail.changeForSrc(item.user.src)+' '+detail.changeForTime(item.createTime)+'    回复</span>'+
        '</div>'+
        '</div>'+
        '</div>';
    },
    changeForSrc:function (src) {
        if(src==0){
            return "<span style='color: #1AA094'>微信</span>";
        }
        if(src==1){
            return "<span style='color: #1E9FFF'>QQ</span>";
        }
        if(src==2){
            return "<span style='color: #F7B824'>微博</span>";
        }
        return "PC";
    },
    changeForTime:function (time) {
        var date = new Date(time);
        var now = new Date();
        var year = now.getFullYear()-date.getFullYear();
        var month = now.getMonth()-date.getMonth();
        var day = now.getDay()-date.getDay();
        var hour = now.getHours()-date.getHours();
        var minute = now.getMinutes()-date.getMinutes();
        var second = now.getSeconds()-date.getSeconds();
        if (year>0){
            return year+"年前";
        }
        if (month>0){
            return month+"个月前";
        }
        if (day>0){
            return day+"天前";
        }
        if (hour>0){
            return hour+"小时前";
        }
        if (minute>0){
            return minute+"分钟前";
        }
        if (second>0){
            return second+"秒前";
        }
    }
}