package org.blog.init;
import org.blog.model.*;
import org.blog.service.*;
import org.exotic.utils._SystemInfo;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 初始化ServletContext存储通用数据
 * @author 高灶顺
 * @date 2016-11-25
 */
public class InitComponent implements ServletContextListener,ApplicationContextAware{
    private static ApplicationContext applicationContext;
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //application存储域对象
        ServletContext application=servletContextEvent.getServletContext();
        //菜单
        MenuService menuService = (MenuService) applicationContext.getBean(MenuService.class);
        //友情链接
        FriendlinkService friendlinkService = (FriendlinkService) applicationContext.getBean(FriendlinkService.class);
        //每日一句
        WisdomService wisdomService = (WisdomService) applicationContext.getBean(WisdomService.class);
        //名片
        BlogerService blogerService = (BlogerService) applicationContext.getBean(BlogerService.class);
        //电台
        SongsService songsService = (SongsService) applicationContext.getBean(SongsService.class);
        //摄影作品
        PhotosService photosService = (PhotosService) applicationContext.getBean(PhotosService.class);
        //最新文章
        BlogService blogService = (BlogService) applicationContext.getBean(BlogService.class);
        //热门点击
        //推荐文章
        RecommendService recommendService = (RecommendService) applicationContext.getBean(RecommendService.class);
        //最新评论
        CommentService commentService = (CommentService) applicationContext.getBean(CommentService.class);
        this.initData(application,menuService,friendlinkService,wisdomService,blogerService,songsService,photosService,blogService,recommendService,commentService);
    }
    private void initData(
            ServletContext application,
            MenuService menuService,
            FriendlinkService friendlinkService,
            WisdomService wisdomService,
            BlogerService blogerService,
            SongsService songsService,
            PhotosService photosService,
            BlogService blogService,
            RecommendService recommendService,
            CommentService commentService
            ){
        try {
            //菜单
            List<Menu> menus = menuService.getMenu();
            application.setAttribute("menus",menus);
            //友情链接
            List<Friendlink> links = friendlinkService.getFriendlink();
            application.setAttribute("links",links);
            //每日一句
            Wisdom wisdom = wisdomService.getWisdom();
            application.setAttribute("wisdom",wisdom);
            //名片
            Bloger bloger = blogerService.getBloger();
            application.setAttribute("bloger",bloger);
            //电台
            Songs song = songsService.getSong();
            application.setAttribute("song",song);
            //摄影作品
            List<Photos> photoses = photosService.getPhotos();
            application.setAttribute("photos",photoses);
            //最新文章
            List<Blog> latesdBlogs = blogService.getLatesdBlogs();
            application.setAttribute("latesdBlogs",latesdBlogs);
            //热门点击
            List<Blog> hotBlogs = blogService.getHotBlogs();
            application.setAttribute("hotBlogs",hotBlogs);
            //推荐文章
            List<Recommend> recommendBlogs = recommendService.getRecommendBlogs();
            application.setAttribute("recommendBlogs",recommendBlogs);
            //最新评论
            List<Comment> latestComments = commentService.getLatestComments();
            application.setAttribute("latestComments",latestComments);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void contextDestroyed(ServletContextEvent sce) {

    }

}