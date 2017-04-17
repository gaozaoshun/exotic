package org.exotic.utils;

import java.util.HashMap;
import java.util.Map;
/**
 * 前台接收 JSON字符串 格式
 * @author GZS
 */
public class _JSON {
	private static String CODE = "code";
	private static String MESSAGE = "msg";
	private static String DATA = "data";
	/**
	 * 自定义
	 * @return
	 */
	public static Map<String, Object> definedReturn(int code,String msg,Object data) {
		Map<String, Object> map = new HashMap<>();
		map.put(CODE, code);
		map.put(MESSAGE, msg);
		map.put(DATA, data);
		return map;
	}
	/**
	 * 请求成功
	 * @return {code:200}
	 */
	public static Map<String, Object> success() {
		Map<String, Object> map = new HashMap<>();
		map.put(CODE, 200);
		return map;
	}
	/**
	 * 请求成功(带有数据)
	 * @param data
	 * @return {code:200,data:{***}}
	 */
	public static Map<String, Object> success(Object data) {
		Map<String, Object> map = success();
		map.put(DATA, data);
		return map;
	}
	/**
	 * 请求错误
	 * @return {code:**}
	 */
	public static Map<String, Object> error(int code) {
		Map<String, Object> map = new HashMap<>();
		map.put(CODE, code);
		return map;
	}
	/**
	 * 请求失败
	 * @return {code:**,msg:***}
	 */
	public static Map<String, Object> error(int code,String msg) {
		Map<String, Object> map = error(code);
		map.put(MESSAGE, msg);
		return map;
	}
	/**
	 * 后台异常
	 * @param msg
	 * @return {code:500,msg:***}
	 */
	public static Map<String, Object> exception(String msg) {
		return error(500,msg);
	}
	/**
	 * 参数出错
	 * @param msg
	 * @return {code:404,msg:***}
	 */
	public static Map<String, Object> errorParam(String msg) {
		return error(404,msg);
	}
	/**
	 * 未授权
	 * @return {code:401,msg:***}
	 */
	public static Map<String, Object> noAutho(String msg) {
		return error(401,msg);
	}
	/**
	 * 未登陆
	 * @return {code:402,msg:***}
	 */
	public static Map<String, Object> noLogin(String url) {
		return error(402,url);
	}

}
