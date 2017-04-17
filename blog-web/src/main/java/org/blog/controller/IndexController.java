package org.blog.controller;

import org.blog.constant.RouterConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 高灶顺
 * @date 2016-11-25
 */
@Controller
public class IndexController {
    /**
     * 页面-博客首页
     * @return
     * @throws Exception
     */
    @RequestMapping(RouterConstant.BLOG_INDEX)
    public String index() throws Exception  {
        //直接跳转到首页模板
        //数据从servletContext中取
        return "index";
    }
}
