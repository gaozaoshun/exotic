<div class="tuijian">
    <h2 style="margin-top:0">推荐文章</h2>
    <ol>
        <#list recommendBlogs as recommend>
            <li>
                <span><strong>${recommend_index+1}</strong></span>
                <a href="${ctx}/blogs/detail?id=${recommend.blog.id}">${recommend.blog.title}</a>
            </li>
        </#list>
    </ol>
</div>