<#assign ctx = Request.request.contextPath />
<footer>
    <div class="footer-mid">
        <div class="links">
            <h2>友情链接</h2>
            <ul>
                <#list links as link>
                    <li><a href="${link.url}" title="${link.descp}" target="_blank">${link.descp}</a></li>
                </#list>
            </ul>
        </div>
        <div class="visitors">
            <h2>最新评论</h2>
            <#list latestComments as comment>
                <dl>
                    <dt><img src="${ctx+comment.user.headimgurl}">
                    <dt>
                    <dd>
                        ${comment.user.nickname}
                        <time><@dateUtil.smartDate dateTime = comment.createTime /></time>
                    </dd>
                    <dd>在 <a href="${ctx}/blogs/detail?id=${comment.blogId}" class="title">${comment.blog.title}</a> 中评论：</dd>
                    <dd>${comment.content}</dd>
                </dl>
            </#list>
        </div>
        <section class="flickr">
            <h2>摄影作品</h2>
            <ul id="photo">
                <#list photos as photo>
                    <li><a href="javascript:;"><img src="${ctx+photo.url}" alt="${photo.name}" layer-src="${ctx+photo.url?replace('min','max')}"></a></li>
                </#list>
            </ul>
        </section>
    </div>
    <div class="footer-bottom">
        <p>Copyright 2016 Design by <a href="${ctx}">Exotic</a></p>
    </div>
</footer>
<div id="tbox">
    <a id="togbook" href="/e/tool/gbook/?bid=1"></a>
    <a id="gotop" href="javascript:void(0)"></a>
</div>
<script>
    layui.use('layer', function(){
        var layer = layui.layer;
        layer.photos({
            photos: '#photo'
        });
    });

</script>