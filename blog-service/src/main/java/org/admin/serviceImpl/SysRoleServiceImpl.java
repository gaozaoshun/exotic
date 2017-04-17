package org.admin.serviceImpl;

import org.admin.dao.SysAuthoMapper;
import org.admin.dao.SysRoleMapper;
import org.admin.model.SysAutho;
import org.admin.model.SysRole;
import org.admin.model.SysUser;
import org.admin.service.SysRoleService;
import org.common.model.BeginEndTime;
import org.exotic.utils._Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 高灶顺
 * @date 2016-11-30
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleDao;
    @Autowired
    private SysAuthoMapper sysAuthoDao;
    @Override
    public List<SysRole> query(Integer id,Integer page, Integer rows, Integer order, Integer field, SysRole role, BeginEndTime beginEndTime, String q, HttpSession session) throws Exception {
        if (page!=null && rows!=null){
            page = page == 0?1:page;
            page = (page-1)*rows;
        }
        Map<String,Object> whereField = _Map.toMap(role,beginEndTime);
        whereField.put("page",page);
        whereField.put("rows",rows);
        whereField.put("order",order);
        whereField.put("field",field);
        whereField.put("q",q);
        whereField.put("id",id);
        return sysRoleDao.query(whereField);
    }

    @Override
    public int add(SysRole role, HttpSession session) throws Exception {
        SysUser user = (SysUser) session.getAttribute("cur_user");
        role.setCreateUser(user.getId());
        role.setCreateTime(new Date());
        role.setTs(new Date());
        return sysRoleDao.insert(role);
    }

    @Override
    public int modify(SysRole role, HttpSession session) throws Exception {
        role.setTs(new Date());
        return sysRoleDao.modify(role);
    }

    @Override
    public int updateDr(Integer[] ids, Integer dr, HttpSession session) throws Exception {
        return sysRoleDao.updateDr(ids,dr,new Date());
    }

    @Override
    public int delete(Integer[] ids, HttpSession session) throws Exception {
        return sysRoleDao.delete(ids);
    }

    @Override
    public int getPages(Integer rows, String q, HttpSession session) throws Exception {
        int count = sysRoleDao.getCount(q);
        if (count<1){
            return 0;
        }
        return count%rows==0?count/rows:count/rows+1;
    }

    @Override
    public int updateDrV2(SysRole sysRole, HttpSession session) throws Exception {
        List<SysRole> sysRoles = sysRole.getSysRoles();
        for (SysRole srole : sysRoles){
            srole.setTs(new Date());
            sysRoleDao.updateDrV2(srole);
        }
        return sysRoles.size();
    }

    @Override
    public List<SysAutho> getAuthos(Integer id, Integer page, Integer rows, HttpSession session) throws Exception {
        if (page!=null && rows!=null){
            page = page == 0 ? 1:page;
            page = (page-1)*rows;
        }
        return sysAuthoDao.queryByRoleId(id,page,rows);
    }

    @Override
    public int saveAuthos(Integer roleId, Integer[] ids, HttpSession session) throws Exception {
        return sysRoleDao.saveAuthos(roleId,ids);
    }

}
