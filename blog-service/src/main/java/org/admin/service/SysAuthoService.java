package org.admin.service;

import org.admin.model.SysAutho;
import org.admin.model.SysUser;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 高灶顺
 * @date 2016-11-30
 */
public interface SysAuthoService {
    List<SysAutho> getAuthos(SysUser user) throws Exception;

    List<SysAutho> query(String q, Integer id, Integer page, Integer rows, Integer order, Integer field, Integer dr, Integer menuId, HttpSession session) throws Exception;

    int add(SysAutho sysAutho, HttpSession session)throws Exception;

    int modify(SysAutho sysAutho, HttpSession session)throws Exception;

    int updateDr(SysAutho sysAutho, HttpSession session)throws Exception;

    int delete(Integer[] ids, HttpSession session)throws Exception;
}
