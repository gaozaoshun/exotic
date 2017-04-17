package org.blog.controller;

import org.blog.constant.RouterConstant;
import org.blog.constant.TableField;
import org.blog.model.Songs;
import org.blog.model.Wisdom;
import org.blog.service.SongsService;
import org.blog.service.WisdomService;
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
public class WisdomController {
    @Autowired
    private WisdomService wisdomService;
    @RequestMapping(RouterConstant.WISDOM_PAGES)
    public  void pages(
            @RequestParam(name = "page",required = false) Integer page,
            @RequestParam(name = "rows",required = false) Integer rows,
            Integer order,
            Integer field,
            @RequestParam(name = "dr",required = false) Integer dr,
            @RequestParam(name = "title",required = false) String title,
            HttpServletResponse response,
            HttpSession session) throws Exception{
        Integer pages = wisdomService.getPages(page,rows,order,field,dr,title,session);
        _Http.send(response, _JSON.success(pages));
    }
    /**
     * 获取名言
     * @param page 当前页
     * @param rows  每页记录数
     * @param order  0:ASC 1:DESC
     * @param field  排序字段
     * @param dr    删除标志
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.WISDOM_GET)
    public  void get(
            @RequestParam(name="page",required = false)Integer page,
            @RequestParam(name="rows",required = false)Integer rows,
            Integer order,
            Integer field,
            @RequestParam(name="dr",required = false) Integer dr,
            @RequestParam(name="title",required = false) String title,
            @RequestParam(name="id",required = false) Integer id,
            HttpServletResponse response,
            HttpSession session) throws Exception{
        List<Wisdom> wisdoms = wisdomService.get(page,rows,order,field,dr,title,id,session);
        _Http.sendWithCloseRepeat(response, _JSON.success(wisdoms));
    }

    /**
     * 添加名言
     * @param wisdom
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.WISDOM_ADD)
    public  void add(
            Wisdom wisdom,
            HttpServletResponse response,
            HttpSession session) throws Exception{
        int flag = wisdomService.add(wisdom,session);
        _Http.send(response, _JSON.success(flag+"条名言添加成功！"));
    }

    /**
     * 修改名言
     * @param wisdom
     *             id
     *             title
     *             text
     *             img
     *             lv
     *             dr
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.WISDOM_MODIFY)
    public  void modify(
            Wisdom wisdom,
            HttpServletResponse response,
            HttpSession session) throws Exception{
        int flag = wisdomService.modify(wisdom,session);
        _Http.send(response, _JSON.success(flag+"条名言修改成功！"));
    }

    /**
     * 撤销/恢复  名言
     * @param ids
     * @param dr
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.WISDOM_UPDATE_DR)
    public  void updateDr(
            @RequestParam("ids[]") Integer[] ids,
            Integer dr,
            HttpServletResponse response,
            HttpSession session) throws Exception{
        int flag = wisdomService.updateDr(ids,dr,session);
        if(dr == TableField.DR_UNDELETE)
            _Http.send(response, _JSON.success(flag+"条名言恢复成功！"));
        else
            _Http.send(response, _JSON.success(flag+"条名言撤销成功！"));
    }

    /**
     * 删除名言
     * @param ids
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.WISDOM_DELETE_REAL)
    public  void delete(
            @RequestParam("ids[]") Integer[] ids,
            HttpServletResponse response,
            HttpSession session) throws Exception{
        int flag = wisdomService.delete(ids,session);
        _Http.send(response, _JSON.success(flag+"条名言删除成功！"));
    }
}
