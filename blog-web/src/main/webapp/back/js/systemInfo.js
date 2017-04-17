/**
 * Created by Exotic on 2016-12-13.
 */
layui.use(['jquery','layer','form','laydate'],function () {
    var $ = layui.jquery;
    var layer = layui.layer;
    var form = layui.form();
    var laydate = layui.laydate;
    var systemInfoUrl = "../../admin/system/info";
    systemInfo.initUI($,layer,form,laydate,systemInfoUrl);//初始化
});
var systemInfo = {
    initUI:function ($,layer,form,laydate,systemInfoUrl) {
        $(function () {
            //初始化数据
            systemInfo.initData($,layer,form,laydate,systemInfoUrl);
        })
    },
    initData:function ($,layer,form,laydate,systemInfoUrl) {
        var index = layer.load(2,{shade:0.5});
        setTimeout(function () {
            $.ajax({
                url:systemInfoUrl,
                type:'get',
                dataType:'json',
                success:function (res,state) {
                    if(res.code==200){
                        systemInfo.appendTable($,res.data);
                    }else{
                        layer.msg(res.msg,{icon:2,shade:0.5});
                    }
                    layer.close(index);
                },
                error:function (xhr,state,error) {
                    layer.close(index);
                    layer.alert('HTTP请求失败！',{icon:2,shade:0.5});
                }
            });
        },100);
    },
    appendTable:function ($,systemInfo) {
        var systemtrs = "<tr><td>系统名</td><td>"+systemInfo.os_name+"</td></tr>"+
            "<tr><td>系统架构</td><td>"+systemInfo.os_arch+"</td></tr>"+
            "<tr><td>系统IP</td><td>"+systemInfo.os_ip+"</td></tr>"+
            "<tr><td>系统MAC地址</td><td>"+systemInfo.os_mac+"</td></tr>"+
            "<tr><td>系统CPU个数</td><td>"+systemInfo.os_cpus+"</td></tr>"+
            "<tr><td>系统用户名</td><td>"+systemInfo.os_user_name+"</td></tr>"+
            "<tr><td>Java的运行环境版本</td><td>"+systemInfo.java_version+"</td></tr>"+
            "<tr><td>Java平台</td><td>"+systemInfo.sun_desktop+"</td></tr>"+
            "<tr><td>JVM内存总量(MB)</td><td>"+systemInfo.jvm_totalmemory+"</td></tr>"+
            "<tr><td>JVM空闲内存量(MB)</td><td>"+systemInfo.jvm_freememory+"</td></tr>"+
            "<tr><td>JVM使用最大内存量(MB)</td><td>"+systemInfo.jvm_maxmemory+"</td></tr>";
        $(systemtrs).appendTo('#systemInfo');

        var servertrs = "<tr><td>服务器上下文</td><td>"+systemInfo.server_context+"</td></tr>"+
            "<tr><td>服务器名</td><td>"+systemInfo.server_name+"</td></tr>"+
            "<tr><td>服务器端口</td><td>"+systemInfo.server_port+"</td></tr>"+
            "<tr><td>服务器地址</td><td>"+systemInfo.server_addr+"</td></tr>"+
            "<tr><td>服务协议</td><td>"+systemInfo.server_protocol+"</td></tr>"+
            "<tr><td>服务器类型</td><td>"+systemInfo.server_type+"</td></tr>";
        $(servertrs).appendTo('#serverInfo');
    }
}