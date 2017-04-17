package org.admin.service;

import org.admin.model.SysMenu;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author 高灶顺
 * @date 2016-11-30
 */
public interface SysMenuService {
    List<Map<String,Object>> getMenus(Integer id) throws Exception;
    List<Map<String,Object>> adaptMenu(List<SysMenu> menus) throws Exception;
    Map<String,Object> toParent(SysMenu menu) throws Exception;
    Map<String,Object> toChildren(SysMenu menu) throws Exception;

    int add(SysMenu sysMenu, HttpSession session)throws Exception;

    int modify(SysMenu sysMenu, HttpSession session)throws Exception;

    int updateDr(Integer[] ids, Integer dr, HttpSession session)throws Exception;

    int delete(Integer[] ids, HttpSession session)throws Exception;

    List<Map<String, Object>> query(Integer page, Integer rows, Integer order, Integer field, Integer dr, Integer superId, HttpSession session)throws Exception;

    Integer getPages(Integer rows, String q, HttpSession session)throws Exception;

    List<SysMenu> getSuper(Integer order, Integer field, Integer dr)throws Exception;
}
