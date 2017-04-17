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
    var pagesUrl = "../../blogs/pages";
    var queryUrl = "../../blogs/query";
    var typeUrl = "../../blogs/type";
    var repUrl = "../../blogs/delete/fack";
    var recUrl = "../../blogs/undelete";
    var delUrl = "../../blogs/delete/real";
    var previewUrl = "../../blogs/get";
    var dataForm = {page:1,rows:10,field:1,order:1};
    managerBlog.initUI($,layer,laypage,form,laydate,pagesUrl,queryUrl,typeUrl,dataForm,repUrl,recUrl,delUrl,previewUrl);//初始化界面
});
var managerBlog = {
    initUI:function ($,layer,laypage,form,laydate,pagesUrl,queryUrl,typeUrl,dataForm,repUrl,recUrl,delUrl,previewUrl) {
        $(function () {
            managerBlog.initData($,laypage,form,laydate,pagesUrl,queryUrl,dataForm,repUrl,recUrl,delUrl,previewUrl);//初始化数据
            managerBlog.regSearch($,laypage,form,laydate,pagesUrl,queryUrl,repUrl,recUrl,delUrl,typeUrl,previewUrl);//注册搜索按钮
        })
    },
    regSearch:function ($,laypage,form,laydate,pagesUrl,queryUrl,repUrl,recUrl,delUrl,typeUrl,previewUrl) {
            managerBlog.showHighQ($);//注册高级搜索栏显示事件
            managerBlog.regSelectBox($,form,typeUrl);//加载文章类型---高级搜索栏
            $('#titleQ').on('click',function (e) {//监听普通查询事件
                var title = $('#titleInput').val();
                if (title.trim()==''){
                    parent.layer.alert("请输入标题字段！");
                }
                var dataForm = {page:1,rows:10,field:1,order:1,title:title};
                managerBlog.initData($,laypage,form,laydate,pagesUrl,queryUrl,dataForm,repUrl,recUrl,delUrl,previewUrl);
            })
            //TODO 监听高级查询事件
            $('#highSearch').on('click',function () {
                var dataForm = {page:1,rows:10,field:1,order:1,
                    title:$('input[name="qtitle"]').val(),
                    typeId:$('select[name="qtypeId"]').val(),
                    keyWords:$('input[name="qkeyWords"]').val(),
                    dr:$('select[name="qdr"]').val(),
                    cBeginTime:$('input[name="qcBeginTime"]').val(),
                    cEndTime:$('input[name="qcEndTime"]').val(),
                    tBeginTime:$('input[name="qtBeginTime"]').val(),
                    tEndTime:$('input[name="qtEndTime"]').val(),
                    minLook:$('input[name="qminLook"]').val(),
                    maxLook:$('input[name="qmaxLook"]').val(),
                    minLike:$('input[name="qminLike"]').val(),
                    maxLike:$('input[name="qmaxLike"]').val(),
                    minTalk:$('input[name="qminTalk"]').val(),
                    maxTalk:$('input[name="qmaxTalk"]').val()};
                managerBlog.initData($,laypage,form,laydate,pagesUrl,queryUrl,dataForm,repUrl,recUrl,delUrl,previewUrl);
            })
    },
    regSelectBox:function ($,form,typeUrl) {
            $.ajax({
                url:typeUrl,
                type:'post',
                dataType:'json',
                success:function (res,state) {
                    if(res.code==200){
                        var topOpts = '';
                        $.each(res.data,function (index,item) {
                            topOpts +='<option value="'+item.id+'">'+item.name+'</option>';
                        })
                        $(topOpts).appendTo('#type');
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
    jumpPage:function ($,laydate,form,obj,first,queryUrl,dataForm,repUrl,recUrl,delUrl,previewUrl) {
        dataForm.page=obj.curr;
        if(obj.curr==1 || !first) {//过滤
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
                                tr += '<tr>'+
                                    '<td><input type="checkbox" lay-filter="check" data-id="'+item.id+'"></td>'+
                                    '<td>'+item.id+'</td>'+
                                    '<td>'+
                                    '<a href="#">'+item.title+'</a>'+
                                    '</td>'+
                                    '<td>'+item.sysUser.nickname+'</td>'+
                                    '<td>'+laydate.now(item.createTime,"YYYY-MM-DD hh:mm:ss")+'</td>'+
                                    '<td>'+laydate.now(item.ts,"YYYY-MM-DD hh:mm:ss")+'</td>'+
                                    '<td>'+item.lookNum+'</td>'+
                                    '<td>'+item.likeNum+'</td>'+
                                    '<td>'+item.talkNum+'</td>'+
                                    '<td data-typeId="'+item.typeId+'">'+item.type.name+'</td>'+
                                    '<td data-id="'+item.id+'" class="opera">'+
                                    '<a href="#" class="layui-btn layui-btn-normal layui-btn-mini"><i class="layui-icon">&#xe643;</i> 预览</a>'+
                                    '<a href="#" class="layui-btn layui-btn-warm layui-btn-mini"><i class="layui-icon">&#xe642;</i> 编辑</a>';
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
                            //TODO 按钮注册
                            managerBlog.regCheckBox($);//注册icheck组件
                            managerBlog.regEditBtn($,form);//注册编辑按钮点击事件
                            managerBlog.regRepBtn($,form,repUrl,recUrl);//注册撤销按钮点击事件
                            managerBlog.regRecBtn($,form,recUrl);//注册恢复按钮点击事件
                            managerBlog.regDelBtn($,form,delUrl);//注册删除按钮点击事件
                            managerBlog.regPreviewBtn($,form,previewUrl);//注册预览按钮点击事件
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
    regPreviewBtn:function ($,form,previewUrl) {
        //TODO 表格预览按钮注册
        var $opera = $('#tbody').find('.opera>a:nth-child(1)')
        $opera.unbind('click').bind('click',function (e) {
            e.preventDefault();
            var dataId = $(this).parent('td').attr('data-id');
            parent.layer.open({//弹出框
                title:'预览文章',
                type: 2,
                area: ['780px', '630px'],
                skin: 'layui-layer-rim', //加上边框
                content: 'menus/previewBlog.html?id='+dataId,
            });
        })
    },
    regDelBtn:function ($,form,delUrl) {
        //表格删除按钮注册
        var $opera = $('#tbody').find('.opera>a:nth-child(4)')
        $($opera).unbind('click').bind('click',function (e) {
            e.preventDefault();
            var dataId = $(this).parent('td').attr('data-id');
            var $tr = $(this).parent('td').parent('tr');
            managerBlog.commonAction($,delUrl,{ids:[dataId]},[$tr]);//删除事件
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
                managerBlog.commonAction($,delUrl,{ids:checkedList},trs);//删除事件
            }
        })
    },
    regRecBtn:function ($,form,recUrl) {
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
                managerBlog.commonAction($,recUrl,{ids:checkedList},trs);//恢复事件
            }
        })
    },
    regRepBtn:function ($,form,repUrl,recUrl) {
        //表格撤销恢复按钮注册
        var $opera = $('#tbody').find('.opera>a:nth-child(3)')
        $($opera).unbind('click').bind('click',function (e) {
            e.preventDefault();
            var dataId = $(this).parent('td').attr('data-id');
            var $tr = $(this).parent('td').parent('tr');
            if ($(this).text().indexOf("撤销")!=-1){
                managerBlog.commonAction($,repUrl,{ids:[dataId]},[$tr]);//撤销事件
            }
            if ($(this).text().indexOf("恢复")!=-1){
                managerBlog.commonAction($,recUrl,{ids:[dataId]},[$tr]);//恢复事件
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
                managerBlog.commonAction($,repUrl,{ids:checkedList},trs);//撤销事件
            }
        })
    },
    flushTable:function ($,url,trs) {
        if (url.indexOf('delete/fack')!=-1){//撤销
            for(var i=0;i<trs.length;i++){
                var aNode = trs[i].find('.opera>a:nth-child(3)');
                aNode.removeClass('layui-btn-danger').addClass('layui-btn-primary');
                aNode.html('<i class="layui-icon">&#xe63d;</i> 恢复');
            }
        }else if (url.indexOf('undelete')!=-1){//恢复
            for(var i=0;i<trs.length;i++){
                var aNode = trs[i].find('.opera>a:nth-child(3)');
                aNode.removeClass('layui-btn-primary').addClass('layui-btn-danger');
                aNode.html('<i class="layui-icon">&#xe64f;</i> 撤销');
            }
        }else if (url.indexOf('delete/real')!=-1){//删除
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
                        managerBlog.flushTable($,url,trs); //刷新表格
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
    regEditBtn:function ($,form) {
        //表格编辑按钮注册
        var $opera = $('#tbody').find('.opera>a:nth-child(2)')
        $($opera).unbind('click').bind('click',function (e) {
            e.preventDefault();
            var dataId = $(this).parent('td').attr('data-id');
            managerBlog.popEditPage(dataId);
        }),
        //页面编辑按钮注册
        $('#editBtn').unbind('click').bind('click',function (e) {
            e.preventDefault();
            var checkedList = new Array();
            var tmp = 0;
            $('#tbody>tr>td>div').each(function (index,item) {
                var checkeds = $(this).attr('class');
                if (checkeds.indexOf("checked")!=-1){
                    checkedList[tmp]=$(this).children('input').attr('data-id');
                    tmp++;
                }
            })
            if(checkedList.length<1) {
                parent.layer.alert('未选择编辑的记录',{icon:0,title:'温馨提示'});
                return;
            }else if (checkedList.length>1){
                parent.layer.alert('不能选择多条记录！',{icon:0,title:'温馨提示'});
                return;
            }else{
                managerBlog.popEditPage(checkedList[0]);//弹出编辑页
            }
        })
    },
    popEditPage:function (id) {
        var index = parent.layer.open({
            type: 2,
            title:'编辑文章',
            content: 'menus/editBlog.html?id='+id,
            area: ['1100px', '530px'],
            maxmin: true,
            end:function () {
                window.location.reload();
            }
        });
        parent.layer.full(index);
    },
    initData:function ($,laypage,form,laydate,pagesUrl,queryUrl,dataForm,repUrl,recUrl,delUrl,previewUrl) {
        var index = parent.layer.load(2,{shade:0.5});
        setTimeout(function () {
            $.ajax({
                url:pagesUrl,
                type:'post',
                data:dataForm,//TODO 适应后台参数处理
                dataType:'json',
                success:function (res,state) {
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
                                managerBlog.jumpPage($,laydate,form,obj,first,queryUrl,dataForm,repUrl,recUrl,delUrl,previewUrl);
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
    },
    showHighQ:function ($) {
        $('#highQ').on('click', function() {
            $('#hideQ').slideToggle();
        });
    }
}