<div class="card">
    <p>昵称：${bloger.name}</p>
    <p>职业：${bloger.job}</p>
    <p>学校：${bloger.school}</p>
    <p>WeChat：${bloger.wechat}</p>
    <p>QQ：${bloger.qq}</p>
    <p>weibo：${bloger.weibo}</p>
    <ul class="linkmore">
        <li><a href="/" class="talk" title="给我留言"></a></li>
        <li><a href="/" class="address" title="联系地址"></a></li>
        <li><a href="/" class="email" title="给我写信"></a></li>
        <li><a href="/" class="photos" title="生活照片"></a></li>
        <li><a href="/" class="heart" title="关注我"></a></li>
    </ul>
    <img src="${ctx+bloger.headimg}" style="position: absolute;width: 140px;height: 165px;top: 28px;right: 20px"/>
</div>