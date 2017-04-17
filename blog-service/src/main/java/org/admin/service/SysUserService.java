package org.admin.service;

import org.admin.model.SysRole;
import org.admin.model.SysUser;
import org.common.model.BeginEndTime;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 高灶顺
 * @date 2016-11-30
 */
public interface SysUserService {
    int login(String username, String password) throws Exception;
    SysUser loginUser() throws Exception;

    SysUser modifyUser(SysUser sysUser, HttpSession session) throws Exception;

    String logoutUser(HttpServletRequest request, HttpSession session) throws Exception;

    List<SysUser> query(Integer page, Integer rows, Integer order, Integer field, SysUser user, BeginEndTime beginEndTime, String q,HttpSession session) throws Exception;

    int add(SysUser user, HttpSession session)throws Exception;

    int modify(SysUser user, HttpSession session)throws Exception;

    int updateDr(Integer[] ids, Integer dr, HttpSession session)throws Exception;

    int delete(Integer[] ids, HttpSession session)throws Exception;

    Integer getPages(Integer page, Integer rows, String q, HttpSession session)throws Exception;

    int updateDrV2(SysUser list, HttpSession session)throws Exception;

    List<SysRole> getCurRoles(Integer userId, Integer page, Integer rows, HttpSession session)throws Exception;

    Integer saveUserRoles(Integer userId, Integer[] ids, HttpSession session)throws Exception;

    Integer delUserRoles(Integer userId, Integer roleId, HttpSession session)throws Exception;
}
