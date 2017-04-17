/**
 * Created by Exotic on 2016-12-13.
 */
layui.config({
    base: '../plugins/layui/modules/'
});
layui.use(['layer','jquery','form','icheck', 'laypage','laydate'],function () {
    var layer = layui.layer;
    var $ = layui.jquery;
    var form = layui.form();
    var laypage = layui.laypage;
    var laydate = layui.laydate;
    var pagesUrl = "../../song/pages";
    var queryUrl = "../../song/get";
    var userUrl = "../../user/get";
    var drUrl = "../../song/dr";
    var delUrl = "../../song/delete";
    var dataForm = {page:1,rows:10,field:3,order:1};//查询参数
    managerSong.initUI($,layer,laypage,form,laydate,pagesUrl,queryUrl,userUrl,dataForm,drUrl,delUrl);//初始化界面
});
var managerSong = {
    initUI:function ($,layer,laypage,form,laydate,pagesUrl,queryUrl,userUrl,dataForm,drUrl,delUrl) {
        $(function () {
            managerSong.initData($,laypage,form,laydate,pagesUrl,queryUrl,dataForm,drUrl,delUrl);//初始化数据
            managerSong.regUpload($,laypage,form,laydate,pagesUrl,queryUrl,dataForm,drUrl,delUrl);//注册上传歌曲按钮
            managerSong.regLvList($,laypage,form,laydate,pagesUrl,queryUrl,dataForm,drUrl,delUrl);//注册播放列表按钮
            managerSong.regSearch($,laypage,form,laydate,pagesUrl,queryUrl,drUrl,delUrl,userUrl);//注册搜索按钮
        })
    },
    regLvList:function ($,laypage,form,laydate,pagesUrl,queryUrl,dataForm,drUrl,delUrl) {
        $('#lvBtn').on('click',function (e) {
            e.preventDefault();
            parent.layer.open({
                type:2,
                title:'音乐播放列表',
                content:'menus/songList.html',
                area:['451px','450px'],
                shade:0.5,
                end:function () {
                    window.location.reload();
                }
            })
        })
    },
    regUpload:function ($,laypage,form,laydate,pagesUrl,queryUrl,dataForm,drUrl,delUrl) {
        $('#uploadBtn').on('click',function (e) {
            e.preventDefault();
            parent.layer.open({
                type:2,
                title:'添加歌曲',
                content:'menus/addSong.html',
                area:['400px','450px'],
                shade:0.5,
                end:function () {
                    window.location.reload();
                }
            })
        })
    },
    regSearch:function ($,laypage,form,laydate,pagesUrl,queryUrl,drUrl,delUrl,userUrl) {
            $('#titleQ').on('click',function (e) {//监听普通查询事件
                var title = $('#titleInput').val();
                if (title.trim()==''){
                    parent.layer.alert("请输入标题字段！");
                }
                var dataForm = {page:1,rows:10,field:3,order:1,title:title};
                managerSong.initData($,laypage,form,laydate,pagesUrl,queryUrl,dataForm,drUrl,delUrl);
            })
    },
    regSelectBox:function ($,form,userUrl) {
        var dataForm = {order:1,field:3};
            $.ajax({
                url:userUrl,
                type:'post',
                data:dataForm,
                dataType:'json',
                success:function (res,state) {
                    if(res.code==200){
                        var topOpts = '';
                        $.each(res.data,function (index,item) {
                            topOpts +='<option value="'+item.id+'">'+item.nickname+'</option>';
                        })
                        $(topOpts).appendTo('#createUser');
                        form.render('select');//重新渲染所有select框
                    }else{
                        parent.layer.msg(res.msg,{icon:2,shade:0.5});
                    }
                },
                error:function (xhr,state,error) {
                    parent.layer.msg('HTTP请求失败！',{icon:2,shade:0.5});
                }
            });
    },
    jumpPage:function ($,laydate,form,obj,first,queryUrl,dataForm,drUrl,delUrl) {//TODO 666
        dataForm.page=obj.curr;
        if(obj.curr==1||!first) {//检测页面是否初始加载
            var index = parent.layer.load(2,{shade:0.5});
            setTimeout(function () {
                $.ajax({
                    url:queryUrl,
                    type:'post',
                    data:dataForm,
                    dataType:'json',
                    success:function (res,state) {
                        parent.layer.close(index);
                        if(res.code==200){
                            $('#tbody').empty();
                            var tr = '' ;
                            $.each(res.data,function (index,item) {
                                var minImg = item.img;
                                var maxImg = minImg.replace("min","max");
                                tr += '<tr>'+
                                    '<td><input type="checkbox" lay-filter="check" data-id="'+item.id+'"></td>'+
                                    '<td>'+item.id+'</td>'+
                                    '<td><a href="javascript:;"><img src="'+minImg+'" layer-src="'+maxImg+'" style="width: 80px;" alt="'+item.name+'"></a></td>'+
                                    '<td>'+item.name+'</td>'+
                                    '<td>'+item.author+'</td>'+
                                    '<td>'+item.lv+'</td>'+
                                    '<td>'+item.special+'</td>'+
                                    '<td>'+item.likeNum+'</td>'+
                                    // '<td><a href="../../'+item.url+'">'+item.url+'</a></td>'+
                                    '<td><audio src="../../'+item.url+'" controls="controls"></audio></td>'+
                                    '<td>'+laydate.now(item.createTime,"YYYY-MM-DD hh:mm:ss")+'</td>'+
                                    '<td>'+item.sysUser.nickname+'</td>'+
                                    '<td>'+laydate.now(item.ts,"YYYY-MM-DD hh:mm:ss")+'</td>'+
                                    '<td data-id="'+item.id+'" class="opera">';
                                if ( item.dr == 0 ){
                                    tr += '<a href="#" class="layui-btn layui-btn-danger layui-btn-mini"><i class="layui-icon">&#xe64f;</i> 撤销</a>';
                                }else{
                                    tr += '<a href="#" class="layui-btn layui-btn-primary layui-btn-mini"><i class="layui-icon">&#xe63d;</i> 恢复</a>';
                                }
                                tr += '<a href="#" class="layui-btn  layui-btn-mini"><i class="layui-icon">&#xe640;</i> 删除</a>'+
                                    '</td>'+
                                    '</tr>';
                            });
                           $(tr).appendTo('#tbody').slideDown();
                            //注册专辑图预览
                            managerSong.preview();
                            //TODO 按钮注册
                            managerSong.regCheckBox($);//注册icheck组件
                            managerSong.regRepBtn($,form,drUrl);//注册撤销按钮点击事件
                            managerSong.regRecBtn($,form,drUrl);//注册恢复按钮点击事件
                            managerSong.regDelBtn($,form,delUrl);//注册删除按钮点击事件
                        }else{
                            parent.layer.msg(res.msg,{icon:2,shade:0.5});
                        }
                    },
                    error:function (xhr,state,error) {
                        parent.layer.close(index);
                        parent.layer.msg('HTTP请求失败！',{icon:2,shade:0.5});
                    }
                });
            },100);
        }
    },
    preview:function () {
        layer.photos({
            photos: '#tbody>tr>td'
        });
    },
    regDelBtn:function ($,form,delUrl) {
        //表格删除按钮注册
        var $opera = $('#tbody').find('.opera>a:nth-child(2)')
        $($opera).unbind('click').bind('click',function (e) {
            e.preventDefault();
            var dataId = $(this).parent('td').attr('data-id');
            var $tr = $(this).parent('td').parent('tr');
            managerSong.commonAction($,delUrl,{ids:[dataId]},[$tr]);//删除事件
        }),
        //页面删除按钮注册
        $('#delBtn').unbind('click').bind('click',function (e) {
            e.preventDefault();
            var checkedList = new Array();
            var tmp = 0;
            var trs = new Array();
            $('#tbody>tr>td>div').each(function (index,item) {
                var checkeds = $(this).attr('class');
                if (checkeds.indexOf("checked")!=-1){
                    checkedList[tmp]=$(this).children('input').attr('data-id');
                    trs[tmp] = $(this).parent('td').parent('tr');
                    tmp++;
                }
            })
            if(checkedList.length<1) {
                parent.layer.alert('未选择删除的记录',{icon:0,title:'温馨提示'});
                return;
            }else{
                managerSong.commonAction($,delUrl,{ids:checkedList},trs);//删除事件
            }
        })
    },
    regRecBtn:function ($,form,drUrl) {
        //页面恢复按钮注册
        $('#recBtn').unbind('click').bind('click',function (e) {
            e.preventDefault();
            var checkedList = new Array();
            var tmp = 0;
            var trs = new Array();
            $('#tbody>tr>td>div').each(function (index,item) {
                var checkeds = $(this).attr('class');
                if (checkeds.indexOf("checked")!=-1){
                    checkedList[tmp]=$(this).children('input').attr('data-id');
                    trs[tmp]=$(this).parent('td').parent('tr');
                    tmp++;
                }
            })
            if(checkedList.length<1) {
                parent.layer.alert('未选择恢复的记录',{icon:0,title:'温馨提示'});
                return;
            }else{
                managerSong.commonAction($,drUrl,{ids:checkedList,dr:0},trs);//恢复事件
            }
        })
    },
    regRepBtn:function ($,form,drUrl) {
        //表格撤销恢复按钮注册
        var $opera = $('#tbody').find('.opera>a:nth-child(1)')
        $($opera).unbind('click').bind('click',function (e) {
            e.preventDefault();
            var dataId = $(this).parent('td').attr('data-id');
            var $tr = $(this).parent('td').parent('tr');
            if ($(this).text().indexOf("撤销")!=-1){
                managerSong.commonAction($,drUrl,{ids:[dataId],dr:1},[$tr]);//撤销事件
            }
            if ($(this).text().indexOf("恢复")!=-1){
                managerSong.commonAction($,drUrl,{ids:[dataId],dr:0},[$tr]);//恢复事件
            }
        }),
        //页面撤销按钮注册
        $('#repBtn').unbind('click').bind('click',function (e) {
            e.preventDefault();
            var checkedList = new Array();
            var tmp = 0;
            var trs = new Array();
            $('#tbody>tr>td>div').each(function (index,item) {
                var checkeds = $(this).attr('class');
                if (checkeds.indexOf("checked")!=-1){
                    checkedList[tmp]=$(this).children('input').attr('data-id');
                    trs[tmp]=$(this).parent('td').parent('tr');
                    tmp++;
                }
            })
            if(checkedList.length<1) {
                parent.layer.alert('未选择撤销的记录',{icon:0,title:'温馨提示'});
                return;
            }else{
                managerSong.commonAction($,drUrl,{ids:checkedList,dr:1},trs);//撤销事件
            }
        })
    },
    flushTable:function ($,url,params,trs) {
        if (params.dr == 1){//撤销
            for(var i=0;i<trs.length;i++){
                var aNode = trs[i].find('.opera>a:nth-child(1)');
                aNode.removeClass('layui-btn-danger').addClass('layui-btn-primary');
                aNode.html('<i class="layui-icon">&#xe63d;</i> 恢复');
            }
        }else if (params.dr == 0){//恢复
            for(var i=0;i<trs.length;i++){
                var aNode = trs[i].find('.opera>a:nth-child(1)');
                aNode.removeClass('layui-btn-primary').addClass('layui-btn-danger');
                aNode.html('<i class="layui-icon">&#xe64f;</i> 撤销');
            }
        }else if (url.indexOf('delete')!=-1){//删除
            for(var i=0;i<trs.length;i++){
                trs[i].slideUp(1000).empty();
            }
        }
    },
    commonAction:function ($, url, params,trs) {
        var index = parent.layer.load(2,{shade:0.5});
        setTimeout(function () {
            $.ajax({
                url:url,
                type:'post',
                data:params,
                dataType:'json',
                success:function (res,state) {
                    if(res.code==200){
                        managerSong.flushTable($,url,params,trs); //刷新表格
                        parent.layer.msg(res.data, {icon: 1,shade:0.5});
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
    },
    initData:function ($,laypage,form,laydate,pagesUrl,queryUrl,dataForm,drUrl,delUrl) {
        var index = parent.layer.load(2,{shade:0.5});
        setTimeout(function () {
            $.ajax({
                url:pagesUrl,
                type:'post',
                data:dataForm,
                dataType:'json',
                success:function (res) {
                    if(res.code==200){
                        laypage({
                            cont: 'page',
                            curr:1,//當前頁
                            pages: res.data,//总页数
                            groups: res.data <5 ?res.data :5, //连续显示分页数
                            prev: '<', //若不显示，设置false即可
                            next: '>',//若不显示，设置false即可
                            first:1,
                            last:res.data,
                            skip:true,
                            skin:'#000',
                            jump: function(obj, first) {
                                //跳转页码---按钮监听事件需要在数据渲染后进行注册
                                managerSong.jumpPage($,laydate,form,obj,first,queryUrl,dataForm,drUrl,delUrl);
                            }
                        });
                        parent.layer.close(index);
                    }else{
                        parent.layer.msg(res.msg,{icon:2,shade:0.5});
                        parent.layer.close(index);
                    }
                },
                error:function (xhr,state,error) {
                    parent.layer.close(index);
                    parent.layer.msg('HTTP请求失败！',{icon:2,shade:0.5});
                }
            });
        },100);
    },
    regCheckBox:function ($) {
        $('input').iCheck({//注册icheck组件
            checkboxClass: 'icheckbox_flat-green'
        });
        $('#selected-all').on('ifChanged', function(event) {//全选/取消全选
            var $input = $('.site-table tbody tr td').find('input');
            $input.iCheck(event.currentTarget.checked ? 'check' : 'uncheck');
        });
        $('.site-table tbody tr').on('click', function(event) {//表格单选
            var $this = $(this);
            var $input = $this.children('td').eq(0).find('input');
            $input.on('ifChecked', function(e) {
                $this.css('background-color', '#EEEEEE');
            });
            $input.on('ifUnchecked', function(e) {
                $this.removeAttr('style');
            });
            $input.iCheck('toggle');
        }).find('input').each(function(){
            var $this = $(this);
            $this.on('ifChecked', function(e) {
                $this.parents('tr').css('background-color', '#EEEEEE');
            });
            $this.on('ifUnchecked', function(e) {
                $this.parents('tr').removeAttr('style');
            });
        });
    }
}