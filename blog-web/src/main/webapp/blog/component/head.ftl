<header>
    <nav id="nav">
        <ul>
            <#list menus as menu >
                <li><a href="${ctx+menu.url}" title="${menu.desc}">${menu.name}</a></li>
            </#list>
        </ul>
        <script src="${ctx}/blog/js/silder.js"></script>
    </nav>
</header>