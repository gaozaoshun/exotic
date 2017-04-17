package org.admin.serviceImpl;

import org.admin.dao.SysAuthoMapper;
import org.admin.model.SysAutho;
import org.admin.model.SysUser;
import org.admin.service.SysAuthoService;
import org.blog.constant.TableField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @author 高灶顺
 * @date 2016-11-30
 */
@Service
public class SysAuthoServiceImpl implements SysAuthoService {
    @Autowired
    private SysAuthoMapper authoDao;

    @Override
    public List<SysAutho> getAuthos(SysUser user)  throws Exception {
        //TODO 验证用户
        return authoDao.queryByUserId(user.getId(),TableField.DR_UNDELETE);
    }

    @Override
    public List<SysAutho> query(String q, Integer id, Integer page, Integer rows, Integer order, Integer field, Integer dr, Integer menuId, HttpSession session) throws Exception {
        if (page!=null && rows!=null){
            page = page ==0?1:page;
            page = (page-1)*rows;
        }
        return authoDao.query(q,id,page,rows,order,field,dr,menuId);
    }

    @Override
    public int add(SysAutho sysAutho, HttpSession session) throws Exception {
        SysUser user = (SysUser) session.getAttribute("cur_user");
        sysAutho.setCreateUser(user.getId());
        sysAutho.setTs(new Date());
        sysAutho.setCreateTime(new Date());
        return authoDao.insert(sysAutho);
    }

    @Override
    public int modify(SysAutho sysAutho, HttpSession session) throws Exception {
        sysAutho.setTs(new Date());
        return authoDao.modify(sysAutho);
    }

    @Override
    public int updateDr(SysAutho sysAutho, HttpSession session) throws Exception {
        List<SysAutho> sysAuthos = sysAutho.getSysAuthos();
        for (SysAutho sautho : sysAuthos){
            sautho.setTs(new Date());
            authoDao.updateDr(sautho);
        }
        return sysAuthos.size();
    }
    @Transactional
    @Override
    public int delete(Integer[] ids, HttpSession session) throws Exception {
        //1.删除角色-权限表中权限ID对应的记录
        int flag = authoDao.deleteRoleAutho(ids);
        //2.删除权限表的对应的记录
        return authoDao.delete(ids);
    }

}
