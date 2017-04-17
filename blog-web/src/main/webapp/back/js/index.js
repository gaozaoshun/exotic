layui.config({
	base: 'js/'
}).use(['element', 'layer', 'navbar', 'tab'], function() {
	var element = layui.element;
	var layer = layui.layer;
	var navbar = layui.navbar();
	var $ = layui.jquery;
	var tab = layui.tab({elem: '.admin-nav-card'}); //设置选项卡容器
	func.adaptIframe($);//iframe自适应
	func.menuAnim($);//菜单动画
	func.adaptPhone($);//手机设备的简单适配
	var userUrl = "../sysUser/logined";
	var menuUrl = "../admin/init_menu";
	var logoutUrl = "../sysUser/logout";
	var initDataUrl = "../admin/init_data";
	func.initData($,navbar,layer,tab,userUrl,menuUrl,logoutUrl);//页面数据初始化
	func.initFrontData($,initDataUrl);//前端页面数据刷新
	func.clearCache($);//TODO 清除缓存
});
var func = {
	initFrontData:function ($,initDataUrl) {
		$(function () {
			$('#initData').off('click').on('click',function (e) {
				e.preventDefault();
				var index = layer.load(2,{shade:0.5});
				setTimeout(function () {
					$.ajax({
						url:initDataUrl,
						type:'get',
						dataType:'json',
						success:function (res,state) {
							if(res.code==200){
								layer.alert(res.data, {icon: 1,shade:0.5});
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
	},
	regLogout:function ($,layer,logoutUrl) {
		$('#logout').click(function (e) {
			e.preventDefault();
			layer.msg('你确定废了自己么？', {
				shade:0.5,
				time: 0 ,
				btn: ['必须的', '我再想想'],
				yes: function(index){
					layer.close(index);
					var loadIndex = layer.load(2,{shade:0.5});
					$.ajax({
						url:logoutUrl,
						type:'get',
						dataType:'json',
						success:function (res) {
							layer.close(loadIndex);
							if(res.code==200){
								window.location.href=res.data;
							}else{
								layer.msg(res.msg,{icon:2,shade:0.5});
							}
						},
						error:function (xhr,state,error) {
							layer.close(loadIndex);
							layer.msg('HTTP请求失败！',{icon:2,shade:0.5});
						}
					})
				}
			});

		})
	},
	loadMenu:function ($,navbar,layer,tab,index,url,logoutUrl) {
		navbar.set({
			elem: '#admin-navbar-side',
			url: url
		});
		navbar.render();//渲染navbar
		navbar.on('click(side)', function(data) {//监听点击事件
			tab.tabAdd(data.field);
		});
		layer.close(index);
        func.regUserMsgClick($,layer);//注册个人信息点击事件
		func.regLogout($,layer,logoutUrl);//注册注销用户点击事件
	},
    regUserMsgClick:function ($,layer) {
        $('#curUserMsg').click(function (e) {
            e.preventDefault();
			if (!($('a[data-id="1"]').parent("li").attr('class').indexOf("layui-nav-itemed")>0)){
				$('a[data-id="1"]').click();
			}
            $('a[data-id="12"]').click();//跳转到个人信息菜单
        })
    },
	loadUser:function ($,navbar,layer,tab,index,userUrl,menuUrl,logoutUrl) {
		$.ajax({
			url:userUrl,
			type:'POST',
			dataType:'json',
			success:function (res) {
				if(res.code==200){
					$('#headimg').attr('src',res.data.headimg);
					$('#nickname').html(res.data.nickname);
					func.loadMenu($,navbar,layer,tab,index,menuUrl,logoutUrl);//加载菜单信息
				}else if(res.code==402){
					window.location.href = res.msg;//未登录跳转到登陆页
				}else{
					layer.msg(res.msg);
				}
			},
			error:function (res) {
				layer.msg('HTTP请求失败！',{icon:2,shade:0.5});
			}
		});
	},
	initData:function ($,navbar,layer,tab,userUrl,menuUrl,logoutUrl) {
		$(function () {
			var index = layer.msg('Fuck Loading...', {icon: 16,shade: 0.5});
            setTimeout(function () {
                func.loadUser($,navbar,layer,tab,index,userUrl,menuUrl,logoutUrl);//加载用户信息+菜单信息
            },100);
		})
	},
	clearCache:function ($) {
		$('#clearCache').one('click',function (e) {
			e.preventDefault();
			//获取当前页的URL加上TS
			var oldurl = window.location.href;
			if(oldurl.indexOf("ts")>0){
				window.location.href = oldurl.substring(0,oldurl.indexOf("ts=")+3)+new Date().getTime();
			}else {
				window.location.href = oldurl+"?ts="+new Date().getTime();
			}
		})
	},
	adaptIframe:function ($) {
		$(window).on('resize', function() {
			var $content = $('.admin-nav-card .layui-tab-content');
			$content.height($(this).height() - 147);
			$content.find('iframe').each(function() {
				$(this).height($content.height());
			});
		}).resize();
	},
	menuAnim:function($){
		$('.admin-side-toggle').on('click', function() {
			var sideWidth = $('#admin-side').width();
			if(sideWidth === 200) {
				$('#admin-body').animate({left: '0'});//admin-footer
				$('#admin-footer').animate({left: '0'});
				$('#admin-side').animate({width:'0'});
			} else {
				$('#admin-body').animate({left: '200px'});
				$('#admin-footer').animate({left: '200px'});
				$('#admin-side').animate({width:'200px'});
			}
		});
	},
	adaptPhone:function($){
		var treeMobile = $('.site-tree-mobile'),
			shadeMobile = $('.site-mobile-shade');
		treeMobile.on('click', function() {
			$('body').addClass('site-mobile');
		});
		shadeMobile.on('click', function() {
			$('body').removeClass('site-mobile');
		});
	}
}