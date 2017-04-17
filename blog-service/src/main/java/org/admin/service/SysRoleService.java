package org.admin.service;

import org.admin.model.SysAutho;
import org.admin.model.SysRole;
import org.common.model.BeginEndTime;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 高灶顺
 * @date 2016-11-30
 */
public interface SysRoleService {
    List<SysRole> query(Integer id,Integer page, Integer rows, Integer order, Integer field, SysRole role, BeginEndTime beginEndTime, String q, HttpSession session) throws  Exception;

    int add(SysRole role, HttpSession session) throws  Exception;

    int modify(SysRole role, HttpSession session) throws  Exception;

    int updateDr(Integer[] ids, Integer dr, HttpSession session)throws  Exception;

    int delete(Integer[] ids, HttpSession session)throws  Exception;

    int getPages(Integer rows, String q, HttpSession session)throws  Exception;

    int updateDrV2(SysRole sysRole, HttpSession session)throws  Exception;

    List<SysAutho> getAuthos(Integer id, Integer page, Integer rows, HttpSession session)throws  Exception;

    int saveAuthos(Integer roleId, Integer[] ids, HttpSession session)throws  Exception;
}
