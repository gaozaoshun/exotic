package org.blog.controller;

import org.blog.constant.RouterConstant;
import org.blog.constant.TableField;
import org.blog.model.Friendlink;
import org.blog.service.FriendlinkService;
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
 * @date 2016/12/19
 */
@Controller
public class FriendLinkController {
    @Autowired
    private FriendlinkService friendlinkService;

    /**
     * 添加友情链接
     *
     * @param friendlink 友情链接信息
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.FriendLink_ADD)
    public void add(
            Friendlink friendlink,
            HttpServletResponse response,
            HttpSession session) throws Exception {
        int flag = friendlinkService.add(friendlink, session);
        _Http.send(response, _JSON.success(flag + "条友情链接添加成功!"));
    }

    /**
     * 查询友情链接
     *
     * @param page     当前页
     * @param rows     每页记录数
     * @param order    0：asc 1:desc
     * @param field    参照TableField
     * @param dr       删除标志
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.FriendLink_GET)
    public void get(
            Integer page,
            Integer rows,
            Integer order,
            Integer field,
            Integer dr,
            HttpServletResponse response,
            HttpSession session) throws Exception {
        List<Friendlink> friendlinks = friendlinkService.query(page, rows, order, field, dr, session);
        _Http.sendWithCloseRepeat(response, _JSON.success(friendlinks));
    }

    /**
     * 修改友情链接
     *
     * @param friendlink
     *                  id
     *                  descp
     *                   url
     *                   lv
     *                   dr
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.FriendLink_MODIFY)
    public void modify(
            Friendlink friendlink,
            HttpServletResponse response,
            HttpSession session) throws Exception {
        int flag = friendlinkService.modify(friendlink, session);
        _Http.send(response, _JSON.success(flag + "条友情链接修改成功！"));
    }

    /**
     * 撤销/恢复  友情链接
     *
     * @param ids      ID集合
     * @param dr       0:未删除  1:已删除
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.FriendLink_UPDATE_DR)
    public void updateDr(
            @RequestParam("ids[]") Integer[] ids,
            Integer dr,
            HttpServletResponse response,
            HttpSession session) throws Exception {
        int flag = friendlinkService.updateDr(ids, dr, session);
        if (dr == TableField.DR_UNDELETE)
            _Http.send(response, _JSON.success(flag + "条友情链接恢复成功！"));
        else
            _Http.send(response, _JSON.success(flag + "条友情链接撤销成功！"));

    }

    /**
     * 删除友情链接
     *
     * @param ids      ID集合
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.FriendLink_DELETE_REAL)
    public void delete(
            @RequestParam("ids[]") Integer[] ids,
            HttpServletResponse response,
            HttpSession session) throws Exception {
        int flag = friendlinkService.delete(ids, session);
        _Http.send(response, _JSON.success(flag + "条友情链接恢复成功！"));
    }

}
