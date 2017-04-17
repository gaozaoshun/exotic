<!DOCTYPE html>
<html lang="en">
<head>
    <#--common js、css-->
    <#include "component/head-common.ftl" />
    <#--日期处理工具 -->
    <#import "utils/dateUtil.ftl" as dateUtil/>
    <link href="${ctx}/blog/css/view.css" rel="stylesheet">
</head>
<body>
    <#--head begin-->
    <#include "component/head.ftl" />
    <#--head end-->
    <div id="mainbody">
        <div class="blogs">
            <div class="newlist">
                <h2>
                    您当前的位置：<a href="/">首页</a>
                    &nbsp;>&nbsp;<a href="${ctx}/blogs/list?typeId=0&page=0&rows=6">个人博客</a>
                    <#if typeId!=0 && isHave=0>
                        &nbsp;>&nbsp;<a href="${ctx}/blogs/list?typeId=${blogs[0].typeId}&page=0&rows=6">${blogs[0].type.name}</a>
                    </#if>
                </h2>
                <#--文章分类列表 begin-->
                <#if isHave=0>
                    <ul>
                    <#list blogs as blog>
                        <p class="ptit">
                            <b>
                                <a href="${ctx}/blogs/detail?id=${blog.id}" title="${blog.title}"> ${blog.title}</a>
                            </b>
                        </p>
                        <p class="ptime">
                            发布时间：${blog.createTime?string("yyyy-MM-dd")} 作者：${blog.sysUser.nickname}  分类：${blog.type.name}
                        </p>
                        <a href="${ctx}/blogs/detail?id=${blog.id}" title="${blog.title}">
                            <img src="${ctx+blog.cover}" class="imgdow" alt="${blog.title}">
                        </a>
                        <p class="pcon">
                        ${blog.summary}
                            <a href="${ctx}/blogs/detail?id=${blog.id}" title="${blog.title}">
                                详细信息
                            </a>
                        </P>
                        <div class="line"></div>
                    </#list>
                    <#--分页-->
                    <nav>
                        <ul class="pagination pagination-sm">
                        ${pagebar}
                        </ul>
                    </nav>
                </ul>
                <#else >
                <ul style="height: 500px">
                    <span style="font-size:30px;color:red;margin-left: 173px;line-height: 500px">不存在此类别的文章！</span>
                </ul>
                </#if>
                <#--文章分类列表 end-->
            </div>
            <aside>
                <#--搜索栏-->
                <#include "component/search.ftl" />
                <#--菜单-->
                <#include "component/menu.ftl" />
                <#--推荐文章-->
                <#include "component/recommed.ftl" />
                 <#--图文并茂-->
                <#include "component/image-text.ftl" />
                <#--热门点击-->
                <#include "component/hotclick.ftl" />
            </aside>
        </div>
    </div>

    <#--foot begin-->
    <#include "component/foot.ftl" />
    <#--foot end-->
</body>
</html>