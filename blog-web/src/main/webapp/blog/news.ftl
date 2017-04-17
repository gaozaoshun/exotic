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
    <#include "component/head.ftl" />
    <div id="mainbody">
        <div class="blogs">
            <div class="newlist">
                <h2>
                    <span>
                        <a href="#domestic">国内新闻</a>
                        <a href="#world">国际新闻</a>
                        <a href="#ent">娱乐新闻</a>
                        <a href="#sports">体育新闻</a>
                    </span>
                    您当前的位置：
                    <a href="/index.html">首页</a>
                    &nbsp;>&nbsp;
                    <a href="/news/">新闻中心</a>
                </h2>
                <ul>
                    <h3 id="domestic">国内新闻</h3>
                    <li>
                        <span>2012-12-10</span>
                        <a href="/news/china/2012-12-10/69.html" title="广东丹霞山发现巨型 青铜剑(组图)">广东丹霞山发现巨型"青铜剑"(组图)</a>
                    </li>
                    <li>
                        <span>2012-12-10</span>
                        <a href="/news/china/2012-12-10/68.html" title="驻日大使崔天凯:胡锦涛主席访日有三点值得关注">驻日大使崔天凯:胡锦涛主席访日有三点值得</a>
                    </li>
                    <li>
                        <span>2012-12-10</span>
                        <a href="/news/china/2012-12-10/67.html" title="杭州湾跨海大桥日均车流量逾10万(组图)">杭州湾跨海大桥日均车流量逾10万(组图)</a>
                    </li>
                    <li>
                        <span>2012-12-10</span>
                        <a href="/news/china/2012-12-10/66.html" title="广东省物价局：粮价节后上涨不可信">广东省物价局：粮价节后上涨不可信</a>
                    </li>
                    <li>
                        <span>2012-12-10</span>
                        <a href="/news/china/2012-12-10/65.html" title="柏杨葬礼将于14日举行 骨灰抛撒绿岛海面">柏杨葬礼将于14日举行
                        骨灰抛撒绿岛海面</a>
                    </li>
                    <h3 id="world">国际新闻</h3>
                    <li>
                        <span>2012-12-10</span>
                        <a href="/news/world/2012-12-10/72.html" title="中国紧急援助物资运抵缅甸仰光(组图)">中国紧急援助物资运抵缅甸仰光(组图)</a>
                    </li>
                    <li>
                        <span>2012-12-10</span>
                        <a href="/news/world/2012-12-10/70.html" title="俄罗斯第三任总统梅德韦杰夫宣誓就职">俄罗斯第三任总统梅德韦杰夫宣誓就职</a>
                    </li>
                    <li>
                        <span>2012-12-10</span>
                        <a href="/news/world/2012-12-10/15.html" title="中国紧急援助物资运抵缅甸仰光(组图)">中国紧急援助物资运抵缅甸仰光(组图)</a>
                    </li>
                    <li>
                        <span>2012-12-10</span>
                        <a href="/news/world/2012-12-10/14.html" title="印度成功试射一枚“烈火－3”型导弹">印度成功试射一枚“烈火－3”型导弹</a>
                    </li>
                    <li>
                        <span>2012-12-10</span>
                        <a href="/news/world/2012-12-10/13.html" title="马来红新月会宣布将向缅甸派出救灾小组">马来红新月会宣布将向缅甸派出救灾小组</a>
                    </li>
                    <h3 id="ent">娱乐新闻</h3>
                    <li>
                        <span>2012-12-10</span>
                        <a href="/news/ent/2012-12-10/76.html" title="“最美清洁工”原是《赤壁》宫女">“最美清洁工”原是《赤壁》宫女</a>
                    </li>
                    <li>
                        <span>2012-12-10</span>
                        <a href="/news/ent/2012-12-10/75.html" title="尹馨大胆亮相《男人装》 嫩肤美腿勾人魂">尹馨大胆亮相《男人装》
                        嫩肤美腿勾人魂</a>
                    </li>
                    <li>
                        <span>2012-12-10</span>
                        <a href="/news/ent/2012-12-10/74.html" title="传张艺谋因执导奥运身价涨5倍">传张艺谋因执导奥运身价涨5倍</a>
                    </li>
                    <li>
                        <span>2012-12-10</span>
                        <a href="/news/ent/2012-12-10/73.html" title="张曼玉广告写真花絮曝光 流露优雅从容">张曼玉广告写真花絮曝光
                        流露优雅从容</a></li>
                    <h3 id="sports">体育新闻</h3>
                    <li>
                        <span>2012-12-10</span>
                        <a href="/news/sports/2012-12-10/78.html" title="中国男乒第16次捧起斯韦思林杯">中国男乒第16次捧起斯韦思林杯</a>
                    </li>
                    <li>
                        <span>2012-12-10</span>
                        <a href="/news/sports/2012-12-10/77.html" title="科比专为大场面而生">科比专为大场面而生</a>
                    </li>
                    <li>
                        <span>2012-12-10</span>
                        <a href="/news/sports/2012-12-10/71.html" title="奥运圣火成功登顶珠峰">奥运圣火成功登顶珠峰</a>
                    </li>
                </ul>
            </div>

            <aside>
            <#--搜索栏-->
            <#include "component/search.ftl" />
            <#--菜单-->
            <#include "component/menu.ftl" />
            <#--电台-->
            <#include "component/song.ftl" />
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