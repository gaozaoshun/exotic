package org.admin.controller;

import org.admin.model.SysAutho;
import org.admin.model.SysRole;
import org.admin.service.SysRoleService;
import org.blog.constant.RouterConstant;
import org.blog.constant.TableField;
import org.common.model.BeginEndTime;
import org.exotic.utils._Http;
import org.exotic.utils._JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class SysRoleController {
    private Logger logger = LoggerFactory.getLogger(SysRoleController.class);
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 查询总页数
     * @param rows
     * @param q
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.SYS_ROLE_PAGES)
    public void pages(
            Integer rows,
            @RequestParam(name = "q",required = false) String q,
            HttpServletResponse response,
            HttpSession session) throws Exception{
        int flag = sysRoleService.getPages(rows,q,session);
        _Http.send(response, _JSON.success(flag));
    }
    /**
     * 查询角色
     * @param page 当前页
     * @param rows  每页记录数
     * @param order 0：ASC 1：DESC
     * @param field 排序字段
     * @param role 查询条件
     *             id
     *             name
     *             icon
     *             desc
     *             dr
     *             createUser
     *             cBeginTime
     *             cEndTime
     *             tBeginTime
     *             tEndTime
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.SYS_ROLE_GET)
    public void get(
            @RequestParam(name = "page",required = false) Integer page,
            @RequestParam(name = "rows",required = false) Integer rows,
            @RequestParam(name = "q",required = false) String q,
            @RequestParam(name = "id",required = false) Integer id,
            Integer order,
            Integer field,
            SysRole role,
            BeginEndTime beginEndTime,
            HttpServletResponse response,
            HttpSession session) throws Exception{
        List<SysRole> sysRoles = sysRoleService.query(id,page,rows,order,field,role,beginEndTime,q,session);
        _Http.sendWithCloseRepeat(response, _JSON.success(sysRoles));
    }

    /**
     * 添加角色
     * @param role
     *              name
     *              icon
     *              descp
     *              superId
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.SYS_ROLE_ADD)
    public void add(
            SysRole role,
            HttpServletResponse response,
            HttpSession session) throws Exception{
        int flag = sysRoleService.add(role,session);
        _Http.send(response, _JSON.success(flag+"条角色新增成功！"));
    }

    /**
     * 获取指定角色的所有权限
     * @param id
     * @param page
     * @param rows
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.SYS_ROLE_GETAUTHO)
    public void getAuthos(
            Integer id,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "rows", required = false) Integer rows,
            HttpServletResponse response,
            HttpSession session) throws Exception{
        List<SysAutho> sysAuthos = sysRoleService.getAuthos(id,page,rows,session);
        _Http.send(response, _JSON.success(sysAuthos));
    }

    /**
     * 给指定角色授予权限
     * @param ids
     * @param id
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.SYS_ROLE_SAVEAUTHOS)
    public void saveAuthos(
            @RequestParam(name = "ids[]") Integer[] ids,
            Integer id,
            HttpServletResponse response,
            HttpSession session
    ) throws Exception{
        int flag = sysRoleService.saveAuthos(id,ids,session);
        _Http.send(response,_JSON.success(flag+"条记录保存成功！"));
    }
    /**
     * 修改角色
     * @param role
     *                  id
     *                  name
     *                  icon
     *                  desc
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.SYS_ROLE_MODIFY)
    public void modify(
            SysRole role,
            HttpServletResponse response,
            HttpSession session) throws Exception{
        int flag = sysRoleService.modify(role,session);
        _Http.send(response, _JSON.success(flag+"条角色修改成功！"));
    }

    /**
     * 撤销/恢复 角色
     * @param ids
     * @param dr
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.SYS_ROLE_UPDATE_DR)
    public void updateDr(
            @RequestParam("ids[]") Integer[] ids,
            Integer dr,
            HttpServletResponse response,
            HttpSession session) throws Exception{
        int flag = sysRoleService.updateDr(ids,dr,session);
        if (dr == TableField.DR_UNDELETE)
            _Http.send(response, _JSON.success(flag+"条角色恢复成功！"));
        else
            _Http.send(response, _JSON.success(flag+"条角色撤销成功！"));
    }
    @RequestMapping(RouterConstant.SYS_ROLE_UPDATE_DR_V2)
    @ResponseBody
    public void updateDrV2(
            @RequestBody SysRole sysRole,
            HttpServletResponse response,
            HttpSession session) throws Exception{

        int flag = sysRoleService.updateDrV2(sysRole,session);
        _Http.send(response, _JSON.success(flag+"条角色更新成功！"));
    }

    /**
     * 删除角色
     * @param ids
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.SYS_ROLE_DELETE_REAL)
    public void delete(
            @RequestParam("ids[]") Integer[] ids,
            HttpServletResponse response,
            HttpSession session) throws Exception{
        int flag = sysRoleService.delete(ids,session);
        _Http.send(response, _JSON.success(flag+"条角色删除成功！"));
    }
}
