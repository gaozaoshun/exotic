package org.blog.serviceImpl;

import org.admin.model.SysUser;
import org.blog.constant.TableField;
import org.blog.dao.MenuMapper;
import org.blog.model.Menu;
import org.blog.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @author 高灶顺
 * @date 2016-11-25
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuDao;

    @Override
    public List<Menu> getMenu() throws Exception {
        //查询 dr=0 且 按lv升序 的10条记录
        List<Menu> menus = menuDao.query(0,9,TableField.ORDER_ASC,TableField.LV,TableField.DR_UNDELETE);
        return menus;
    }

    @Override
    public int add(Menu menu, HttpSession session) throws Exception {
        SysUser user = (SysUser) session.getAttribute("cur_user");
        menu.setTs(new Date());
        menu.setCreateTime(new Date());
        menu.setCreateUser(user.getId());
        menu.setDr(TableField.DR_UNDELETE);
        return menuDao.add(menu);
    }

    @Override
    public List<Menu> query(Integer page, Integer rows, Integer order, Integer field, Integer dr, HttpSession session) throws Exception{
        return  menuDao.query(page,rows,order,field,dr);
    }

    @Override
    public int modify(Menu menu, HttpSession session) throws Exception{
        SysUser user = (SysUser) session.getAttribute("cur_user");
        menu.setCreateUser(user.getId());
        menu.setTs(new Date());
        return menuDao.modify(menu);
    }

    @Override
    public int updateDr(Integer[] ids, Integer dr, Date date, HttpSession session) throws Exception {
        SysUser user = (SysUser) session.getAttribute("cur_user");
        return menuDao.updateDr(ids,dr,date);
    }

    @Override
    public int delete(Integer[] ids, HttpSession session) throws Exception {
        SysUser user = (SysUser) session.getAttribute("cur_user");
        return menuDao.delete(ids);
    }
}
