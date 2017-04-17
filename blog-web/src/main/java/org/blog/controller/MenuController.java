package org.blog.controller;

import org.apache.ibatis.annotations.Param;
import org.blog.constant.RouterConstant;
import org.blog.constant.TableField;
import org.blog.model.Menu;
import org.blog.service.MenuService;
import org.exotic.utils._Http;
import org.exotic.utils._JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @author 高灶顺
 * @date 2016/12/19
 */
@Controller
public class MenuController {
    @Autowired
    private MenuService menuService;

    /**
     * 添加网站菜单
     * @param menu
     *              name
     *              desc
     *              url
     *              lv
     * @param response
     * @throws Exception
     */
    @RequestMapping(RouterConstant.MENU_ADD)
    public void add(
            Menu menu,
            HttpSession session,
            HttpServletResponse response) throws Exception{
        int flag = menuService.add(menu,session);
        _Http.send(response, _JSON.success(flag+"条网站菜单添加成功！"));
    }

    /**
     * 查询网站菜单
     * @param page 当前页  = null :默认查询全部
     * @param rows  每页记录数 = null :默认查询全部
     * @param order 0:ASC 1:DESC
     * @param field  3 4 5
     * @param dr  0:未删除 1：删除
     * @param response
     * @throws Exception
     */
    @RequestMapping(RouterConstant.MENU_GET)
    public void get(
            Integer page,
            Integer rows,
            Integer order,
            Integer field,
            Integer dr,
            HttpSession session,
            HttpServletResponse response) throws Exception{
        List<Menu> menus = menuService.query(page,rows,order,field,dr,session);
        _Http.sendWithCloseRepeat(response, _JSON.success(menus));
    }

    /**
     * 修改网站菜单
     * @param menu
     *                 id
     *                 name
     *                 desc
     *                 url
     *                 lv
     *                 dr
     * @param response
     * @throws Exception
     */
    @RequestMapping(RouterConstant.MENU_MODIFY)
    public void modify(
            Menu menu,
            HttpSession session,
            HttpServletResponse response) throws Exception{
        int flag = menuService.modify(menu,session);
        _Http.send(response, _JSON.success(flag+"条网站菜单修改成功！"));
    }

    /**
     * 撤销/恢复 网站菜单
     * @param ids ID集合
     * @param dr 删除标志
     * @param response
     * @throws Exception
     */
    @RequestMapping(RouterConstant.MENU_UPDATE_DR)
    public void updateDr(
            @Param("ids[]") Integer[] ids,
            Integer dr,
            HttpSession session,
            HttpServletResponse response) throws Exception{
        int flag = menuService.updateDr(ids,dr,new Date(),session);
        if (dr == TableField.DR_UNDELETE)
            _Http.send(response, _JSON.success(flag+"条网站菜单恢复成功！"));
        else
            _Http.send(response, _JSON.success(flag+"条网站菜单撤销成功！"));

    }

    /**
     * 删除网站菜单
     * @param ids 菜单集合ID
     * @param session
     * @param response
     * @throws Exception
     */
    @RequestMapping(RouterConstant.MENU_DELETE_REAL)
    public void delete(
            @RequestParam("ids[]") Integer[] ids,
            HttpSession session,
            HttpServletResponse response) throws Exception{
        int flag = menuService.delete(ids,session);
        _Http.send(response, _JSON.success(flag+"条网站菜单删除成功！"));
    }

}
