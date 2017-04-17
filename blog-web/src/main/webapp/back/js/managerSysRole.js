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
    var pagesUrl = "../../sys/role/pages";
    var queryUrl = "../../sys/role/get";
    var addUrl = "../../sys/role/add";
    var userUrl = "../../sys/role/get";
    var drUrl = "../../sys/role/dr/v2";
    var delUrl = "../../sys/role/delete";
    var recomUrl = "../../sys/role/modify";
    var giveUrl = "../../sys/role/give";
    var dataForm = {page:1,rows:10,field:3,order:1};//查询参数
    managerSysRole.initUI($,layer,laypage,form,laydate,pagesUrl,queryUrl,userUrl,dataForm,drUrl,delUrl,addUrl,recomUrl,giveUrl);//初始化界面
});
var managerSysRole = {
    initUI:function ($,layer,laypage,form,laydate,pagesUrl,queryUrl,userUrl,dataForm,drUrl,delUrl,addUrl,recomUrl,giveUrl) {
        $(function () {
            managerSysRole.initData($,laypage,form,laydate,pagesUrl,queryUrl,dataForm,drUrl,delUrl,addUrl,recomUrl,giveUrl);//初始化数据
            managerSysRole.regSearch($,laypage,form,laydate,pagesUrl,queryUrl,drUrl,delUrl,userUrl,addUrl,recomUrl,giveUrl);//注册搜索按钮
        })
    },
    regSearch:function ($,laypage,form,laydate,pagesUrl,queryUrl,drUrl,delUrl,userUrl,addUrl,giveUrl) {
            $('#titleQ').on('click',function (e) {//监听普通查询事件
                var title = $('#titleInput').val();
                if (title.trim()==''){
                    parent.layer.alert("请输入搜索字段！");
                }
                var dataForm = {page:1,rows:10,field:3,order:1,q:title};
                managerSysRole.initData($,laypage,form,laydate,pagesUrl,queryUrl,dataForm,drUrl,delUrl,addUrl,giveUrl);
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
    jumpPage:function ($,laydate,form,obj,first,queryUrl,dataForm,drUrl,delUrl,addUrl,recomUrl,giveUrl) {//TODO 666
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
                                    '<td>'+item.name+'</td>'+
                                    '<td><i class="layui-icon">'+item.icon+'</i></td>'+
                                    '<td>'+item.descp+'</td>'+
                                    '<td>'+laydate.now(item.createTime,"YYYY-MM-DD hh:mm:ss")+'</td>'+
                                    '<td>'+item.sysUser.nickname+'</td>'+
                                    '<td>'+laydate.now(item.ts,"YYYY-MM-DD hh:mm:ss")+'</td>'+
                                    '<td>';
                                if (item.dr==0){
                                    tr+='<input type="checkbox" name="dr" lay-skin="switch" lay-filter="dr" checked>';
                                }else {
                                    tr+='<input type="checkbox" name="dr" lay-skin="switch" lay-filter="dr">';
                                }
                            });
                           $(tr).appendTo('#tbody');
                            //重新渲染
                            form.render('checkbox');
                            //背景图预览
                            managerSysRole.preview();
                            //TODO 按钮注册
                            managerSysRole.regCheckBox($);//注册icheck组件
                            managerSysRole.regAddBtn($,form,addUrl);//添加
                            managerSysRole.regEditBtn($,form,recomUrl);//编辑
                            managerSysRole.regDrBtn($,form,drUrl);//更新
                            managerSysRole.regDelBtn($,form,delUrl);//删除
                            managerSysRole.regGiveBtn($,form,giveUrl);//授予权限
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
    regGiveBtn:function ($,form,giveUrl) {
        $('#giveBtn').off('click').on('click',function (e) {
            e.preventDefault();
            var checkedList = new Array();
            var tmp = 0;
            var trs = new Array();
            $('#tbody>tr>td:first-child>div').each(function (index,item) {
                var checkeds = $(this).attr('class');
                if (checkeds.indexOf("checked")!=-1){
                    checkedList[tmp]=$(this).children('input').attr('data-id');
                    trs[tmp] = $(this).parent('td').parent('tr');
                    tmp++;
                }
            })
            if(checkedList.length!=1) {
                parent.layer.alert('请选择一条记录！',{icon:0,title:'温馨提示'});
                return;
            }else {
                parent.layer.open({
                    type: 2,
                    title: '授予权限',
                    area: ['450px', '420px'],
                    content: 'menus/giveAutho.html?id=' + checkedList[0],
                    shade: 0.5,
                    end: function () {
                        //刷新表格
                        window.location.reload();
                    }
                })
            }
        })
    },
    regDrBtn:function ($,form,drUrl) {
        $('#saveBtn').off('click').on('click',function (e) {
            e.preventDefault();
            var params = new Array();
            $('#tbody>tr').each(function (index,item){
                var id =$(this).find("td:nth-child(2)").text();
                var dr = $(this).find("td:last-child>div").attr("class").indexOf('checked')!=-1?0:1;
                params.push({id:id,dr:dr});
            });
            var index = layer.load(2,{shade:0.5});
            setTimeout(function () {
                $.ajax({
                    url:drUrl,
                    type:'post',
                    data:JSON.stringify({sysRoles:params}),
                    contentType:"application/json;charset=UTF-8",
                    dataType:'json',
                    success:function (res,state) {
                        if(res.code==200){
                            parent.layer.msg(res.data.toString(),{icon:1,shade:0.5,time:1000,end:function () {
                                window.location.reload();
                            }})
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
        });
    },
    regEditBtn:function ($,form,recomUrl) {
        $('#editBtn').off('click').on('click',function (e) {
            e.preventDefault();
            var checkedList = new Array();
            var tmp = 0;
            var trs = new Array();
            $('#tbody>tr>td:first-child>div').each(function (index,item) {
                var checkeds = $(this).attr('class');
                if (checkeds.indexOf("checked")!=-1){
                    checkedList[tmp]=$(this).children('input').attr('data-id');
                    trs[tmp] = $(this).parent('td').parent('tr');
                    tmp++;
                }
            })
            if(checkedList.length!=1) {
                parent.layer.alert('请选择一条记录！',{icon:0,title:'温馨提示'});
                return;
            }else{
                //修改当前选中记录
                parent.layer.open({
                    type:2,
                    title:'编辑当前角色',
                    area:['400px','500px'],
                    content:'menus/modifySysRole.html?id='+checkedList[0],
                    shade:0.5,
                    end:function () {
                        //刷新表格
                        window.location.reload();
                    }
                })
            }
        })
    },
    preview:function () {
        layer.photos({
            photos: '#tbody>tr>td'
        });
    },
    regAddBtn:function ($,form,addUrl){
        //注册添加按钮
        $('#addBtn').off('click').on('click',function (e) {
            e.preventDefault();
            //弹出框
            parent.layer.open({
                type:2,
                title:'添加系统角色',
                area:['400px','400px'],
                content:'menus/addSysRole.html',
                shade:0.5,
                end:function () {
                    //刷新表格
                    window.location.reload();
                }
            })
        })
    },
    regDelBtn:function ($,form,delUrl) {
        //页面删除按钮注册
        $('#delBtn').unbind('click').bind('click',function (e) {
            e.preventDefault();
            var checkedList = new Array();
            var tmp = 0;
            var trs = new Array();
            $('#tbody>tr>td:first-child>div').each(function (index,item) {
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
                managerSysRole.commonAction($,delUrl,{ids:checkedList},trs);//删除事件
            }
        })
    },

    flushTable:function ($,url,params,trs) {
         if (url.indexOf('delete')!=-1){//删除
            for(var i=0;i<trs.length;i++){
                trs[i].slideUp(1000).empty();
            }
        }else {
            window.location.reload();
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
                        managerSysRole.flushTable($,url,params,trs); //刷新表格
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

    initData:function ($,laypage,form,laydate,pagesUrl,queryUrl,dataForm,drUrl,delUrl,addUrl,recomUrl,giveUrl) {
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
                                managerSysRole.jumpPage($,laydate,form,obj,first,queryUrl,dataForm,drUrl,delUrl,addUrl,recomUrl,giveUrl);
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
            var $input = $('.site-table tbody tr td:first-child').find('input');
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