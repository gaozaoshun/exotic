package org.admin.controller;

import org.blog.constant.RouterConstant;
import org.blog.service.*;
import org.exotic.utils._Http;
import org.exotic.utils._JSON;
import org.exotic.utils._SystemInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

/**
 * @author 高灶顺
 * @date 2017/1/27
 */
@Controller
public class FlushController {
    @Autowired
    private MenuService menuService;
    @Autowired
    private FriendlinkService friendlinkService;
    @Autowired
    private WisdomService wisdomService;
    @Autowired
    private BlogerService blogerService;
    @Autowired
    private SongsService songsService;
    @Autowired
    private PhotosService photosService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private RecommendService recommendService;
    @Autowired
    private CommentService commentService;
    /**
     * 更新前端数据
     * @param response
     * @param request
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.ADMIN_DATA_INIT)
    public void initCompoment(
            HttpServletResponse response,
            HttpServletRequest request,
            HttpSession session
    ) throws Exception{
        ServletContext application = request.getServletContext();
        Class clazz = Class.forName("org.blog.init.InitComponent");
        Method initData = clazz.getDeclaredMethod("initData",
                ServletContext.class,
                MenuService.class,
                FriendlinkService.class,
                WisdomService.class,
                BlogerService.class,
                SongsService.class,
                PhotosService.class,
                BlogService.class,
                RecommendService.class,
                CommentService.class
                );
        initData.setAccessible(true);
        initData.invoke(clazz.newInstance(),application,menuService,friendlinkService,wisdomService,blogerService,songsService,photosService,blogService,recommendService,commentService);
        _Http.send(response, _JSON.success("刷新成功！"));
    }

    /**
     * 获取系统信息+服务器信息
     * @param request
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.ADMIN_SYSTEM_INFO)
    public void getSystemInfo(
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session
    ) throws Exception{
        ServletContext application = request.getServletContext();
        Object systemInfoObj = application.getAttribute("systemInfo");
        if (systemInfoObj==null){
            systemInfoObj = _SystemInfo.getInstance(request);
        }
        _Http.send(response,_JSON.success(systemInfoObj));
    }
}
