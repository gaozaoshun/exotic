/**
 * Created by Exotic on 2017/1/6.
 */
layui.use(['jquery','layer'],function () {
    var $ = layui.jquery;
    var layer = layui.layer;
    $(function () {
        menuIcon.regClick($);
    })
});
var menuIcon = {
    regClick:function ($) {
        $('.menu-icon-ul>li').off('click').on('click',function () {
            $('.menu-icon-ul>li').css('background','#fff');
            $(this).css({'background':'#ccc'});
             var icon = $(this).attr('data-icon');
            $('.layui-layer-title',window.parent.document).attr('data-icon',icon);
        })
    }
}