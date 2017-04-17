<div class="clicks">
    <h2 style="margin-top:0">热门点击</h2>
    <ol>
        <#list hotBlogs as blog>
            <li>
                <span>
                    <a href="${ctx}/blogs/list?typeId=${blog.typeId}&page=0&rows=6">${blog.type.name}</a>
                </span>
                <a href="${ctx}/blogs/detail?id=${blog.id}">${blog.title}</a>
            </li>
        </#list>
    </ol>
</div>