<div class="viny">
    <dl>
        <dt class="art"><img src="${song.img}" alt="专辑"></dt>
        <dd class="icon-album"><span></span>${song.name}</dd>
        <dd class="icon-song"><span></span>歌手：${song.author}</dd>
        <dd class="icon-artist"><span></span>专辑：《${song.special}》</dd>
        <dd class="icon-like"><span></span><a href="/">${song.likeNum}人喜欢</a></dd>
        <dd class="music">
            <audio src="${song.url}" controls loop autoplay></audio>
        </dd>
        <#--也可以添加loop属性 音频加载到末尾时，会重新播放-->
    </dl>
</div>