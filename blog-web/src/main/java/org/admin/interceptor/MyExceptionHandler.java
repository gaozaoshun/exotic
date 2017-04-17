package org.admin.interceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.exotic.utils._Http;
import org.exotic.utils._JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
/**
 * 全局异常拦截
 * @author GZS
 */
public class MyExceptionHandler implements HandlerExceptionResolver{
	private Logger logger = LoggerFactory.getLogger(MyExceptionHandler.class);
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object arg2,
			Exception exception) {
		logger.error(exception.getMessage());
		//TODO 全局异常处理
		_Http.send(response, _JSON.exception("系统后台统一异常抛出！"));
		return null;
	}

}
