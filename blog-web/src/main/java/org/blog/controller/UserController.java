package org.blog.controller;

import org.blog.constant.RouterConstant;
import org.blog.model.User;
import org.blog.service.UserService;
import org.common.model.BeginEndTime;
import org.exotic.utils._Http;
import org.exotic.utils._JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 高灶顺
 * @date 2016-12-9
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 查询用户
     * @param page 当前页
     * @param rows  每页记录数
     * @param order 0:ASC 1:DESC
     * @param field 排序字段
     * @param user  查询条件
     *              id
     *              openid
     *              nickname
     *              sex
     *              province
     *              city
     *              country
     *              src
     *              dr
     * @param beginEndTime 查询条件
     *                    cBeginTime 创建时间【最小】
     *                    cEndTime 创建时间【最大】
     *                    tBeginTime 更新时间【最小】
     *                    tEndTime 更新时间【最大】
     * @param session
     * @param response
     * @throws Exception
     */
    @RequestMapping(RouterConstant.USER_GET)
    public void get(
            @RequestParam(name = "page",required = false) Integer page,
            @RequestParam(name = "rows",required = false) Integer rows,
            Integer order,
            Integer field,
            User user,
            BeginEndTime beginEndTime,
            HttpSession session,
            HttpServletResponse response
    ) throws Exception{
        List<User> users = userService.query(page,rows,order,field,user,beginEndTime,session);
        _Http.send(response, _JSON.success(users));
    }
}
