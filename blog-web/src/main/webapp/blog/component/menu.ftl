<div class="sunnav">
    <ul>
        <#list types as type>
            <li><a href="${ctx}/blogs/list?page=0&rows=6&typeId=${type.id}" title="${type.name}">${type.name}</a></li>
        </#list>
    </ul>
</div>