package org.admin.serviceImpl;

import org.admin.dao.SysAuthoMapper;
import org.admin.dao.SysMenuMapper;
import org.admin.model.SysAutho;
import org.admin.model.SysMenu;
import org.admin.model.SysUser;
import org.admin.service.SysMenuService;
import org.blog.constant.TableField;
import org.blog.model.Menu;
import org.exotic.utils._Map;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author 高灶顺
 * @date 2016-11-30
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    private SysMenuMapper menuDao;
    @Override
    public List<Map<String, Object>> getMenus(Integer id) throws Exception  {
        List<SysMenu> menus = menuDao.queryByUserId(id, TableField.DR_UNDELETE);
        if (menus!=null&&menus.size()>0){
            return adaptMenu(menus);
        }
        return null;
    }
    //TODO 组合菜单
    public List<Map<String,Object>> adaptMenu(List<SysMenu> menus) throws Exception  {
        List<Map<String,Object>> list = new ArrayList<>(0);
        for (SysMenu menu : menus){
            if (menu.getSuperId()!=null && menu.getSuperId()!=0){
                //判断list中已存在此父菜单
                boolean flag = false;
                for (Map<String,Object> parent :list){
                    if ((Integer)parent.get("id")==menu.getSuperId()){
                        List<Map<String,Object>> children = (List<Map<String, Object>>) parent.get("children");
                        children.add(toChildren(menu));
                        flag = true;
                        break;
                    }
                }
                //判断list中不存在此父菜单，则查询菜单对应的父菜单并插入到list中，添加key=children的空List对象，用于存储子菜单
                if (!flag){
                    SysMenu db_parent = menuDao.queryBySuperId(menu.getSuperId(),TableField.DR_UNDELETE);
                    Map<String,Object> parent = toParent(db_parent);
                    List<Map<String,Object>> children = (List<Map<String, Object>>) parent.get("children");
                    children.add(toChildren(menu));
                    list.add(parent);
                }
            }else{
                //最高级菜单
                list.add(toParent(menu));
            }
        }
        return list;
    }

    public Map<String,Object> toParent(SysMenu menu) throws Exception {
        Map<String,Object> parent = _Map.toMap(menu);
        parent.put("children",new ArrayList<Map<String,Object>>(0));
        return parent;
    }

    public Map<String,Object> toChildren(SysMenu menu) throws Exception {
        if (menu==null){
            System.out.println("菜单为空............");
        }
        return _Map.toMap(menu);
    }

    public int add(SysMenu sysMenu, HttpSession session) throws Exception {
        SysUser user = (SysUser) session.getAttribute("cur_user");
        sysMenu.setCreateUser(user.getId());
        sysMenu.setTs(new Date());
        sysMenu.setCreateTime(new Date());
        sysMenu.setDr(TableField.DR_UNDELETE);
        return menuDao.insert(sysMenu);
    }

    @Override
    public int modify(SysMenu sysMenu, HttpSession session) throws Exception {
        SysUser user = (SysUser) session.getAttribute("cur_user");
        sysMenu.setTs(new Date());
        return menuDao.modify(sysMenu);
    }

    @Override
    public int updateDr(Integer[] ids, Integer dr, HttpSession session) throws Exception {
        return menuDao.updateDr(ids,dr,new Date());
    }

    @Override
    public int delete(Integer[] ids, HttpSession session) throws Exception {
        return menuDao.delete(ids);
    }

    @Override
    public List<Map<String, Object>> query(Integer page, Integer rows, Integer order, Integer field, Integer dr, Integer superId, HttpSession session) throws Exception {
        if (page!=null && rows!=null){
            page = page ==0?1:page;
            page = (page-1)*rows;
        }
        List<SysMenu> menus = menuDao.query(page,rows,order,field,dr,superId);
        return adaptMenuV2(menus);
    }

    private List<Map<String,Object>> adaptMenuV2(List<SysMenu> menus) throws Exception{
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        for (SysMenu menu : menus){
            //添加父菜单
            if (menu.getSuperId()==null || menu.getSuperId()==0){
                Map<String,Object> parent = _Map.toMap(menu);
                List<SysMenu> childrenMap = new ArrayList<SysMenu>(0);
                for (SysMenu children : menus){
                    //添加对应子菜单
                    if (children.getSuperId()!=null && children.getSuperId()==menu.getId()){
                        childrenMap.add(children);
                    }
                }
                parent.put("children",childrenMap);
                list.add(parent);
            }
        }
        return list;
    }

    @Override
    public Integer getPages(Integer rows, String q, HttpSession session) throws Exception {
        Integer count = menuDao.getCount(q);
        if (count!=null){
            return count%rows==0?count/rows:count/rows+1;
        }
        return 0;
    }

    @Override
    public List<SysMenu> getSuper(Integer order, Integer field, Integer dr) throws Exception {
        return menuDao.getSuper(order,field,dr);
    }
}
