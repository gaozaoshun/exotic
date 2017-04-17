package org.blog.controller;

import org.admin.model.SysUser;
import org.blog.constant.RouterConstant;
import org.blog.model.Bloger;
import org.blog.service.BlogerService;
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
 * @date 2016/12/18
 */
@Controller
public class BlogerController {
    @Autowired
    private BlogerService blogerService;

    /**
     * 查询博主信息
     * @param page
     * @param rows
     * @param order
     * @param field
     * @param bloger
     *              id
     *              name
     *              job
     *              school
     *              intro
     *              qq
     *              wechat
     *              weibo
     *              createUser
     *              dr
     * @param beginEndTime
     *                    cBeginTime 创建时间【最小】
     *                    cEndTime 创建时间【最大】
     *                    tBeginTime 更新时间【最小】
     *                    tEndTime 更新时间【最大】
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.BLOGER_GET)
    public void getOwnBloger(
            @RequestParam(name="page",required = false) Integer page,
            @RequestParam(name="rows",required = false) Integer rows,
            Integer order,
            Integer field,
            Bloger bloger,
            BeginEndTime beginEndTime,
            HttpServletResponse response,
            HttpSession session) throws Exception {
        List<Bloger> blogers = blogerService.query(page,rows,order,field,bloger,beginEndTime,session);
        _Http.send(response, _JSON.success(blogers));
    }

    /**
     * 添加博主信息
     *
     * @param bloger
     *                 headimg:头像
     *                 name:名称
     *                 job:工作
     *                 school:学校
     *                 intro:简介
     *                 qq:QQ
     *                 wechat:微信
     *                 weibo:微博
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.BLOGER_ADD)
    public void addBloger(Bloger bloger, HttpServletResponse response, HttpSession session) throws Exception {
        int flag = blogerService.add(bloger, session);
        _Http.send(response, _JSON.success(flag + "条博主信息添加成功！"));
    }

    /**
     * 修改博主信息
     *
     * @param bloger   id：主键
     *                 headimg：头像
     *                 name：名称
     *                 job：工作
     *                 school：学校
     *                 intro：简介
     *                 qq：QQ
     *                 wechat：微信
     *                 weibo：微博
     *                 dr：删除标志
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.BLOGER_MODIFY)
    public void modifyBloger(Bloger bloger, HttpServletResponse response, HttpSession session) throws Exception {

        Integer flag = blogerService.modify(bloger, session);
        _Http.send(response, _JSON.success(flag + "条博主信息修改成功！"));
    }

    /**
     * 删除博主信息-软删除
     *
     * @param id       博主ID
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.BLOGER_DELETE_FACK)
    public void deleteFack(Integer id, HttpServletResponse response, HttpSession session) throws Exception {
        Integer flag = blogerService.deleteFack(id, session);
        _Http.send(response, _JSON.success(flag + "条博主信息撤销成功！"));
    }

    /**
     * 删除博主信息-数据库删除
     *
     * @param ids       博主ID
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.BLOGER_DELETE_REAL)
    public void deleteReal(
            @RequestParam("ids[]") Integer[] ids,
            HttpServletResponse response,
            HttpSession session) throws Exception {
        Integer flag = blogerService.deleteReal(ids, session);
        _Http.send(response, _JSON.success(flag + "条博主信息删除成功！"));
    }
}
