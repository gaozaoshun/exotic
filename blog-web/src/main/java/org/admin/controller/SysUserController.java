package org.admin.controller;
import org.admin.model.SysRole;
import org.admin.model.SysUser;
import org.admin.service.SysUserService;
import org.blog.constant.RouterConstant;
import org.blog.constant.TableField;
import org.common.model.BeginEndTime;
import org.exotic.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * @author 高灶顺
 * @date 2016-11-30
 */
@Controller
public class SysUserController {
    @Autowired
    private SysUserService userService;

    /**
     * 输出流-图片验证码
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.ADMIN_CODE)
    public void code(HttpServletResponse response, HttpSession session)throws Exception {
        Object[] img = _VerifyCode.getCaptchaImage(112, 38, 35, 10, 10, true,true, _VerifyCode.ComplexLevel.SIMPLE);
        String code = (String) session.getAttribute("code");
        if(!_ValidateParam.isEmpty(code)){
            session.removeAttribute("code");
        }
        String genCode = (String) img[1];
        session.setAttribute("code",genCode);
        BufferedImage stream = (BufferedImage) img[0];
        ImageIO.write(stream,"JPEG",response.getOutputStream());
    }

    /**
     * 登陆操作
     * @param session
     * @param username 用户名
     * @param password 密码
     * @param code 验证码
     * @return
     * @throws Exception
     */
    @RequestMapping(RouterConstant.ADMIN_LOGIN)
    public void login(HttpSession session,
                      String username,
                      String password,
                      String code,
                      HttpServletRequest request,
                      HttpServletResponse response) throws Exception {
        Object session_code = session.getAttribute("code");
        if (!_ValidateParam.isEmpty(session_code)&&session_code instanceof String){
            String sessionCode = (String) session_code;
            if (!sessionCode.equalsIgnoreCase(code)){
                _Http.send(response, _JSON.errorParam("验证码错误"));
            }
        }else {
            _Http.send(response, _JSON.errorParam("验证码异常"));
        }
        int flag = userService.login(username,password);
        if (flag==0){
            session.setAttribute("cur_user",userService.loginUser());
            //登陆成功跳转的路径
            String url = _Path.getRootURL(request)+RouterConstant.ADMIN_PAGE_INDEX;
            _Http.send(response, _JSON.success(url));
        }else if (flag==1){
            _Http.send(response, _JSON.errorParam("用户名不存在"));
        }else {
            _Http.send(response, _JSON.errorParam("密码错误"));
        }
    }
    /**
     * 当前登陆用户信息
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.SYS_CUR_USER)
    public void loginUser(
            HttpServletResponse response,
            HttpSession session)throws Exception{
        SysUser user = (SysUser) session.getAttribute("cur_user");
        _Http.send(response,_JSON.success(user));
    }

    /**
     * 修改登录用户信息
     * @param sysUser
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.SYS_CUR_USER_MODIFY)
    public void modifyUser(
            SysUser sysUser,
            HttpServletResponse response,
            HttpSession session)throws Exception{
        SysUser user = userService.modifyUser(sysUser,session);
        if (user==null){
            _Http.send(response,_JSON.errorParam("用户信息更新失败！"));
        }
        _Http.send(response,_JSON.success(user));
    }

    /**
     * 注销登录用户
     * @param request
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.SYS_CUR_USER_LOGOUT)
    public void logoutUser(
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session)throws Exception{
        String loginUrl = userService.logoutUser(request,session);
        _Http.send(response,_JSON.success(loginUrl));
    }

    /**
     * 获取总页数
     * @param dr
     * @param q  1.用户名 2.昵称
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.SYS_USER_PAGES)
    public void pages(
            Integer rows,
            @RequestParam(name = "dr",required = false) Integer dr,
            @RequestParam(name = "q",required = false) String q,
            HttpServletResponse response,
            HttpSession session)throws Exception{
        Integer pages = userService.getPages(rows,dr,q,session);
        _Http.send(response,_JSON.success(pages));
    }
    /**
     * 查询后台用户
     * @param page 当前页
     * @param rows  每页记录数
     * @param order 0：ASC 1：DESC
     * @param field 排序字段
     * @param user 查询字段
     *             id
     *             username
     *             nickname
     *             state
     *             type
     *             dr
     * @param beginEndTime 查询字段
     *              cBeginTime 创建时间【最小】
     *              cEndTime 创建时间【最大】
     *              tBeginTime 更新时间【最小】
     *              tEndTime 更新时间【最大】
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.SYS_USER_GET)
    public void get(
            @RequestParam(name = "page",required = false) Integer page,
            @RequestParam(name = "rows",required = false) Integer rows,
            @RequestParam(name = "q",required = false) String q,//查询字段
            Integer order,
            Integer field,
            SysUser user,
            BeginEndTime beginEndTime,
            HttpServletResponse response,
            HttpSession session)throws Exception{
        List<SysUser> users = userService.query(page,rows,order,field,user,beginEndTime,q,session);
        _Http.send(response,_JSON.success(users));
    }

    /**
     * 添加后台用户
     * @param user
     *              username
     *              password
     *              nickname
     *              headimg
     *              state
     *              type
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.SYS_USER_ADD)
    public void add(
            SysUser user,
            HttpServletResponse response,
            HttpSession session
    ) throws Exception{
        int flag = userService.add(user,session);
        _Http.send(response,_JSON.success(flag+"条后台用户添加成功"));
    }

    /**
     * 修改后台用户
     * @param user
     *              id
     *              username
     *              password
     *              nickname
     *              headimg
     *              state
     *              type
     *              dr
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.SYS_USER_MODIFY)
    public void modify(
            SysUser user,
            HttpServletResponse response,
            HttpSession session
    ) throws Exception{
        int flag = userService.modify(user,session);
        _Http.send(response,_JSON.success(flag+"条后台用户修改成功"));
    }

    /**
     * 撤销/恢复 后台用户
     * @param ids
     * @param dr
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.SYS_USER_UPDATE_DR)
    public void updateDr(
            @RequestParam("ids[]") Integer[] ids,
            Integer dr,
            HttpServletResponse response,
            HttpSession session
    ) throws Exception{
        int flag = userService.updateDr(ids,dr,session);
        if (dr == TableField.DR_UNDELETE)
            _Http.send(response, _JSON.success(flag + "条后台用户恢复成功"));
        else
            _Http.send(response, _JSON.success(flag+"条后台用户撤销成功"));
    }

    @RequestMapping(RouterConstant.SYS_USER_UPDATE_DR_V2)
    @ResponseBody
    public void updateDrV2(
            @RequestBody SysUser user,
            HttpServletResponse response,
            HttpSession session
    ) throws Exception{
        int flag = userService.updateDrV2(user,session);
        _Http.send(response, _JSON.success(flag+"条记录启用状态更新成功"));
    }
    /**
     * 删除后台用户
     * @param ids
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.SYS_USER_DELETE_REAL)
    public void delete(
            @RequestParam("ids[]") Integer[] ids,
            HttpServletResponse response,
            HttpSession session
    ) throws Exception{
        int flag = userService.delete(ids,session);
        _Http.send(response, _JSON.success(flag+"条后台用户删除成功！"));
    }

    /**
     * 获取指定用户角色列表
     * @param id
     * @param page
     * @param rows
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.SYS_USER_ROLES_GET)
    public void getRoles(
            Integer id,
            @RequestParam(name = "page",required = false) Integer page,
            @RequestParam(name = "rows",required = false) Integer rows,
            HttpServletResponse response,
            HttpSession session
    )throws Exception{
        List<SysRole> roles = userService.getCurRoles(id,page,rows,session);
        _Http.send(response,_JSON.success(roles));
    }

    /**
     * 保存用户指定角色
     * @param userId
     * @param ids
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.SYS_USER_ROLES_SAVE)
    public void saveRoles(
            @RequestParam(name = "userId",required = false)Integer userId,
            @RequestParam(name = "ids[]") Integer[] ids,
            HttpServletResponse response,
            HttpSession session
    )throws Exception{
        Integer flag = userService.saveUserRoles(userId,ids,session);
        _Http.send(response,_JSON.success(flag+"条记录保存成功！"));
    }
    @RequestMapping(RouterConstant.SYS_USER_ROLES_DELETE)
    public void delRoles(
            Integer userId,
            Integer roleId,
            HttpServletResponse response,
            HttpSession session
    )throws Exception{
        Integer flag = userService.delUserRoles(userId,roleId,session);
        _Http.send(response,_JSON.success(flag+"条记录删除成功！"));
    }

}
