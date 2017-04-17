package org.exotic.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * 服务器请求路径 (适用含springmvc服务器)
 * @author GZS
 */
public class _Path {
	
	//从请求监听中获取request
//	private static HttpServletRequest request = ((ServletRequestAttributes)
//			RequestContextHolder.getRequestAttributes()).getRequest();
	
	/**
	 * 获取服务器URL
	 * @return 
	 * eg：http://112.74.54.18:8080
	 */
	public static String getServerURL(HttpServletRequest request){
		String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
		return url;
	}
	/**
	 * 获取服务器项目根目录
	 * @return
	 * eg：http://112.74.54.18:8080/ZQ_hxb
	 */
	public static String getRootURL(HttpServletRequest request){
		String root = request.getRequestURI();
		if (root.isEmpty()){
			int index = root.indexOf("/", 4);
			if (index!=-1){
				return getServerURL(request)+root.substring(0,index);
			}
		}
		return getServerURL(request);
	}
	/**
	 * 获取客户端的IP地址
	 * @return
	 *   eg：172.18.1.4
	 */
	public static String getUserIP(HttpServletRequest request){
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip.indexOf("0:") != -1) {
			ip = "本地";
		}
		return ip;
	}
	/**
	 * 获取项目物理路径
	 * @return
	 * eg：D:\项目\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\ZQ_hxb\
	 */
	public static String getRealURL(HttpServletRequest request){
		@SuppressWarnings("deprecation")
		String real = request.getRealPath("/");
		return real;
	}
}
