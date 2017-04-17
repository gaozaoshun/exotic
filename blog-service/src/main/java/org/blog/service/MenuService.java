package org.blog.service;
import org.blog.model.Menu;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
/**
 * @author 高灶顺
 * @date 2016-11-25
 */
public interface MenuService {
    /**
     * 获取菜单
     * @return menus List<Menu>
     * @throws Exception
     */
    List<Menu> getMenu() throws Exception;

    int add(Menu menu, HttpSession session)throws Exception;

    List<Menu> query(Integer page, Integer rows, Integer order, Integer field,Integer dr, HttpSession session)throws Exception;

    int modify(Menu menu, HttpSession session)throws Exception;

    int updateDr(Integer[] ids, Integer dr, Date date, HttpSession session)throws Exception;

    int delete(Integer[] ids, HttpSession session)throws Exception;
}
