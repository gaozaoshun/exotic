package org.exotic.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class _Http {
	/**
	 * 后台封装JSON字符串并发送到前端
	 * @param response
	 * @param obj
	 */
	public static void send(HttpServletResponse response,Object obj){
		response.setContentType("application/json;charset=utf-8");
		//对外开放API
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out =null;
		try {
			out = response.getWriter();
			out.println(JSON.toJSONString(obj));
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			out.close();
		}
	}
	/**
	 * 后台封装JSON字符串并发送到前端
	 * fastjson关闭重复引用：user":{"$ref":"$.data[0].user"}
	 * @param response
	 * @param obj
	 */
	public static void sendWithCloseRepeat(HttpServletResponse response,Object obj){
		response.setContentType("application/json;charset=utf-8");
		//对外开放API
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out =null;
		try {
			out = response.getWriter();
			//关闭重复引用
			out.println(JSON.toJSONString(obj, SerializerFeature.DisableCircularReferenceDetect));
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			out.close();
		}
	}
}
