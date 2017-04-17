<!DOCTYPE html>
<html lang="en">
<head>
    <#--common js、css-->
    <#include "component/head-common.ftl" />
    <#--日期处理工具 -->
    <#import "utils/dateUtil.ftl" as dateUtil/>
    <link href="${ctx}/blog/css/animation.css" rel="stylesheet">
</head>
<body>
    <#--菜单-->
    <#include "component/head.ftl" />
    <div id="mainbody">
        <div class="info">
            <#--每日一句-->
            <#include "component/wisdom.ftl" />
            <#--我的名片-->
            <#include "component/bloger.ftl" />
        </div>
        <div class="blank"></div>
        <div class="blogs">
            <#--最新文章-->
            <#include "component/latest-blog.ftl"/>
            <aside>
                <#--搜索栏-->
                <#include "component/search.ftl" />
                <#--推荐文章-->
                <#include "component/recommed.ftl" />
                <#--图文并茂-->
                <#include "component/image-text.ftl" />
                <#--热门点击-->
                <#include "component/hotclick.ftl" />
                <#--电台-->
                <#include "component/song.ftl" />
            </aside>
        </div>
    </div>
    <#include "component/foot.ftl" />
</body>
</html>