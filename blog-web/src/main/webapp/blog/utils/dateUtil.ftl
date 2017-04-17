<#macro smartDate dateTime>
    <#assign year = .now?string("yyyy")?number-dateTime?string("yyyy")?number/>
    <#assign month = .now?string("MM")?number-dateTime?string("MM")?number/>
    <#assign day = .now?string("dd")?number-dateTime?string("dd")?number/>
    <#assign hour = .now?string("HH")?number-dateTime?string("HH")?number/>
    <#assign minute = .now?string("HH")?number-dateTime?string("HH")?number/>
    <#assign second = .now?string("ss")?number-dateTime?string("ss")?number/>
    <#if (year>0)>
    ${year+"年前"}
        <#return>
    <#elseif (month>0)>
    ${month+"月前"}
        <#return>
    <#elseif (day>0)>
    ${day+"天前"}
        <#return>
    <#elseif (hour>0)>
    ${hour+"小时前"}
        <#return>
    <#elseif (minute>0)>
    ${minute+"分钟前"}
        <#return>
    <#elseif (second>0)>
    ${second+"秒前"}
        <#return>
    </#if>
</#macro>