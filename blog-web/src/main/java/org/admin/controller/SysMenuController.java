package org.admin.controller;

import org.admin.model.SysMenu;
import org.admin.model.SysUser;
import org.admin.service.SysMenuService;
import org.blog.constant.RouterConstant;
import org.blog.constant.TableField;
import org.exotic.utils._Http;
import org.exotic.utils._JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author 高灶顺
 * @date 2016-11-30
 */
@Controller
public class SysMenuController {
    private Logger logger = LoggerFactory.getLogger(SysMenuController.class);
    @Autowired
    private SysMenuService menuService;
    /**
     * JSON-初始化菜单栏
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.ADMIN_MENUS_INIT)
    public void indexMenu(HttpServletResponse response,HttpSession session) throws Exception {
        logger.debug("#开始初始化菜单#");
        SysUser user = (SysUser) session.getAttribute("cur_user");
        //获取用户对应权限的所有菜单
        List<Map<String,Object>> menus = menuService.getMenus(user.getId());
        _Http.send(response,_JSON.success(menus));
        logger.debug("#结束初始化菜单#");
    }
    @RequestMapping(RouterConstant.SYS_MENU_PAGES)
    public void pages(
            Integer rows,
            String q,
            HttpServletResponse response,
            HttpSession session)throws Exception{
        Integer pages = menuService.getPages(rows,q,session);
        _Http.send(response,_JSON.success(pages));
    }
    /**
     * 查询系统菜单
     * @param page 当前页
     * @param rows  每页记录数
     * @param order 0：ASC 1：DESC
     * @param field 排序字段
     * @param superId 父级菜单ID
     * @param dr 删除标志
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.SYS_MENU_GET)
    public void get(
            @RequestParam(name = "page",required = false) Integer page,
            @RequestParam(name = "rows",required = false) Integer rows,
            Integer order,
            Integer field,
            @RequestParam(name = "superId",required = false) Integer superId,
            @RequestParam(name = "dr",required = false) Integer dr,
            HttpServletResponse response,
            HttpSession session) throws Exception{
        List<Map<String,Object>> sysMenus = menuService.query(page,rows,order,field,dr,superId,session);
        _Http.sendWithCloseRepeat(response, _JSON.success(sysMenus));
    }

    /**
     * 获取父菜单
     * @param order
     * @param field
     * @param dr
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.SYS_MENU_GETSUPPER)
    public void getSuper(
            Integer order,
            Integer field,
            @RequestParam(name = "dr",required = false) Integer dr,
            HttpServletResponse response,
            HttpSession session)throws Exception{
        List<SysMenu> supers =  menuService.getSuper(order,field,dr);
        _Http.sendWithCloseRepeat(response,_JSON.success(supers));
    }
    /**
     * 添加系统菜单
     * @param sysMenu
     *              name
     *              icon
     *              url
     *              desc
     *              superId
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.SYS_MENU_ADD)
    public void add(
            SysMenu sysMenu,
            HttpServletResponse response,
            HttpSession session) throws Exception{
        int flag = menuService.add(sysMenu,session);
        _Http.send(response, _JSON.success(flag+"条系统菜单添加成功！"));
    }

    /**
     * 修改系统菜单
     * @param sysMenu
     *                  id
     *                  name
     *                  icon
     *                  url
     *                  desc
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.SYS_MENU_MODIFY)
    public void modify(
            SysMenu sysMenu,
            HttpServletResponse response,
            HttpSession session) throws Exception{
        int flag = menuService.modify(sysMenu,session);
        _Http.send(response, _JSON.success(flag+"条系统菜单修改成功！"));
    }

    /**
     * 撤销/恢复 系统菜单
     * @param ids
     * @param dr
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.SYS_MENU_UPDATE_DR)
    public void updateDr(
            @RequestParam("ids[]") Integer[] ids,
            Integer dr,
            HttpServletResponse response,
            HttpSession session) throws Exception{
        int flag = menuService.updateDr(ids,dr,session);
        if (dr == TableField.DR_UNDELETE)
            _Http.send(response, _JSON.success(flag+"条系统菜单恢复成功！"));
        else
            _Http.send(response, _JSON.success(flag+"条系统菜单撤销成功！"));
    }

    /**
     * 删除系统菜单
     * @param ids
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.SYS_MENU_DELETE_REAL)
    public void delete(
            @RequestParam("ids[]") Integer[] ids,
            HttpServletResponse response,
            HttpSession session) throws Exception{
        int flag = menuService.delete(ids,session);
        _Http.send(response, _JSON.success(flag+"条系统菜单删除成功！"));
    }
}
