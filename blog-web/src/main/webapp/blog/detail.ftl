<html lang="en">
<head>
    <#--common js、css-->
    <#include "component/head-common.ftl" />
    <#--日期处理工具 -->
    <#import "utils/dateUtil.ftl" as dateUtil/>
    <link href="${ctx}/blog/css/view.css" rel="stylesheet">
</head>
<body>
    <#include "component/head.ftl" />
    <div id="mainbody">
        <div class="blogs">
            <div id="index_view">
                <h2 class="t_nav">
                    <a href="${ctx}/blogs/list?typeId=0&page=0&rows=6">个人博客</a>
                    <a href="${ctx}/blogs/list?typeId=${blog.typeId}&page=0&rows=6">${blog.type.name}</a>
                </h2>
                <h1 class="c_titile">${blog.title}</h1>
                <p class="box">发布时间：${blog.createTime?string("yyyy-MM-dd HH:mm:ss")}<span>编辑：${blog.sysUser.nickname}</span>阅读（${blog.lookNum}）</p>
                <ul>${blog.content}</ul>
                <div class="share">
                    <!-- Baidu Button BEGIN -->
                    <div id="bdshare" class="bdshare_t bds_tools get-codes-bdshare"> <span class="bds_more">分享到：</span> <a class="bds_qzone"></a> <a class="bds_tsina"></a> <a class="bds_tqq"></a> <a class="bds_renren"></a> <a class="bds_t163"></a> <a class="shareCount"></a> </div>
                    <script type="text/javascript" id="bdshare_js" data="type=tools&amp;uid=6574585" ></script>
                    <script type="text/javascript" id="bdshell_js"></script>
                    <script type="text/javascript">
                        document.getElementById("bdshell_js").src = "http://bdimg.share.baidu.com/static/js/shell_v2.js?cdnversion=" + Math.ceil(new Date()/3600000)
                    </script>
                    <!-- Baidu Button END -->
                </div>
                <div class="otherlink">
                    <h2>相关文章</h2>
                    <ul>
                        <#list others as blog>
                            <li><a href="${ctx}/blogs/detail?id=${blog.id}" title="${blog.title}">${blog.title}</a></li>
                        </#list>
                    </ul>
                </div>
                <#--评论区begin-->
                <div class="comment">
                    <div class="head" style="margin-bottom: 10px">
                        <span>评论区</span>
                    </div>
                    <div class="send" style="">
                        <textarea id="comedit" style="display: none;"></textarea>
                        <button id="publish" data-blogId="${blog.id}" class="layui-btn layui-btn-small" style="float: right;margin-top: 3px">发表评论</button>
                    </div>
                    <div style="margin-top: 50px;margin-bottom: -30px"><span style="">最新留言</span></div>
                    <div id="comment" style="background: #fff">

                    </div>
                </div>
                <script src="${ctx}/blog/js/detail.js"></script>
                <#--评论区end-->
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
    <#include "component/foot.ftl" />
</body>
</html>