package org.admin.interceptor;

import org.admin.model.SysAutho;
import org.admin.model.SysUser;
import org.admin.service.SysAuthoService;
import org.blog.constant.RouterConstant;
import org.exotic.utils._Http;
import org.exotic.utils._JSON;
import org.exotic.utils._Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 用户权限拦截
 * @author 高灶顺
 * @date 2016-12-4
 */
public class LoginInterceptor extends HandlerInterceptorAdapter{
    private static final Logger log = LoggerFactory.getLogger(LoginInterceptor.class);
    @Autowired
    private SysAuthoService authoService;
    //处理请求之前被调用
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        log.debug("LoginInterceptor >>> "+uri);
        //TODO Session验证
        HttpSession session = request.getSession();
        Object user = session.getAttribute("cur_user");
        if(user == null){
            log.debug("无权限：跳转到Login页面");
            String url = _Path.getRootURL(request)+ RouterConstant.ADMIN_PAGE_LOGIN;
            _Http.send(response, _JSON.noLogin(url));
            return false;
        }
        //TODO 权限验证
        List<SysAutho> authos = authoService.getAuthos((SysUser) user);
        if (!(authos!=null&&authos.size()>0)){
            _Http.send(response, _JSON.noAutho("您没有权限访问"+uri));
            log.debug("<<<用户权限列表为空！>>>");
            return false;
        }
        for (SysAutho autho : authos){
            if (uri.equalsIgnoreCase((autho.getUri()==null?"":autho.getUri()).trim())){
                log.debug("<<<用户拥有权限>>>"+uri);
                return true;
            }
        }
        log.debug("<<<用户无此权限>>>"+uri);
        _Http.send(response, _JSON.noAutho("您没有权限访问"+uri));
        return false;
    }
    //处理请求执行完成后,生成视图之前执行
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }
    //完全处理完请求后被调用,可用于清理资源等
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

}
