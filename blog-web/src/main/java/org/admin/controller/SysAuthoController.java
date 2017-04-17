package org.admin.controller;

import javafx.scene.control.Tab;
import org.admin.model.SysAutho;
import org.admin.service.SysAuthoService;
import org.blog.constant.RouterConstant;
import org.blog.constant.TableField;
import org.exotic.utils._Http;
import org.exotic.utils._JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 高灶顺
 * @date 2016-11-30
 */
@Controller
public class SysAuthoController {
    @Autowired
    private SysAuthoService authoService;

    /**
     * 查询权限
     * @param page 当前页
     * @param rows  每页记录数
     * @param order 0：ASC 1：DESC
     * @param field 排序字段
     * @param menuId 菜单ID
     * @param dr 删除标志
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.SYS_AUTHO_GET)
    public void get(
            @RequestParam(name = "page",required = false) Integer page,
            @RequestParam(name = "rows",required = false)Integer rows,
            @RequestParam(name = "id",required = false)Integer id,
            @RequestParam(name = "q",required = false)String q,
            Integer order,
            Integer field,
            @RequestParam(name = "menuId",required = false)Integer menuId,
            @RequestParam(name = "dr",required = false)Integer dr,
            HttpServletResponse response,
            HttpSession session) throws Exception{
        List<SysAutho> sysAuthos = authoService.query(q,id,page,rows,order,field,dr,menuId,session);
        _Http.sendWithCloseRepeat(response, _JSON.success(sysAuthos));
    }

    /**
     * 添加权限
     * @param sysAutho
     *              name
     *              icon
     *              uri
     *              desc
     *              menuId
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.SYS_AUTHO_ADD)
    public void add(
            SysAutho sysAutho,
            HttpServletResponse response,
            HttpSession session) throws Exception{
        int flag = authoService.add(sysAutho,session);
        _Http.send(response, _JSON.success(flag+"条权限添加成功！"));
    }

    /**
     * 修改权限
     * @param sysAutho
     *                  id
     *                  name
     *                  icon
     *                  uri
     *                  desc
     *                  menuId
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.SYS_AUTHO_MODIFY)
    public void modify(
            SysAutho sysAutho,
            HttpServletResponse response,
            HttpSession session) throws Exception{
        int flag = authoService.modify(sysAutho,session);
        _Http.send(response, _JSON.success(flag+"条权限修改成功！"));
    }

    /**
     * 启用
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.SYS_AUTHO_UPDATE_DR)
    @ResponseBody
    public void updateDr(
            @RequestBody SysAutho sysAutho,
            HttpServletResponse response,
            HttpSession session) throws Exception{
        int flag = authoService.updateDr(sysAutho,session);
        _Http.send(response, _JSON.success(flag+"条权限更新成功！"));
    }

    /**
     * 删除权限
     * @param ids
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.SYS_AUTHO_DELETE_REAL)
    public void delete(
            @RequestParam("ids[]") Integer[] ids,
            HttpServletResponse response,
            HttpSession session) throws Exception{
        int flag = authoService.delete(ids,session);
        _Http.send(response, _JSON.success(flag+"条权限删除成功！"));
    }

}
