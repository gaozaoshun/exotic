<ul class="bloglist">
<#list latesdBlogs as blog>
    <li>
        <div class="arrow_box">
            <div class="ti"></div><#--三角形-->
            <div class="ci"></div><#--圆形-->
            <h2 class="title"><a href="${ctx}/blogs/detail?id=${blog.id}">${blog.title}</a></h2>
            <ul class="textinfo">
                <a href="${ctx}/blogs/detail?id=${blog.id}"><img src="${blog.cover}"></a>
                <p>${blog.summary}</p>
            </ul>
            <ul class="details">
                <li class="likes"><a href="#">${blog.likeNum}</a></li>
                <li class="comments"><a href="#">${blog.talkNum}</a></li>
                <li class="icon-time"><a href="#">${blog.createTime?string("yyyy-MM-dd HH:mm:ss")}</a></li>
            </ul>
        </div>
    </li>
</#list>
</ul>