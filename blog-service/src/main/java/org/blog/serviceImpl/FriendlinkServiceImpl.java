package org.blog.serviceImpl;

import org.admin.model.SysUser;
import org.blog.constant.TableField;
import org.blog.dao.FriendlinkMapper;
import org.blog.model.Friendlink;
import org.blog.service.FriendlinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @author 高灶顺
 * @date 2016-11-26
 */
@Service
public class FriendlinkServiceImpl implements FriendlinkService {
    @Autowired
    private FriendlinkMapper friendlinkDao;

    public List<Friendlink> getFriendlink() throws Exception {
        return friendlinkDao.query(0,9,TableField.ORDER_ASC,TableField.LV, TableField.DR_UNDELETE);
    }

    @Override
    public int add(Friendlink friendlink, HttpSession session) throws Exception {
        SysUser user = (SysUser)session.getAttribute("cur_user");
        friendlink.setDr(TableField.DR_UNDELETE);
        friendlink.setTs(new Date());
        friendlink.setCreateTime(new Date());
        friendlink.setCreateUser(user.getId());
        return friendlinkDao.add(friendlink);
    }

    @Override
    public List<Friendlink> query(Integer page, Integer rows, Integer order, Integer field, Integer dr, HttpSession session)throws Exception{
        SysUser user = (SysUser)session.getAttribute("cur_user");
        if (page != null) {
            page = page==0?1:page;
            page = (page-1)*rows;
        }
        return friendlinkDao.query(page,rows,order,field,dr);
    }

    @Override
    public int modify(Friendlink friendlink, HttpSession session)throws Exception {
        friendlink.setTs(new Date());
        return friendlinkDao.update(friendlink);
    }

    @Override
    public int updateDr(Integer[] ids, Integer dr, HttpSession session)throws Exception {
        SysUser user = (SysUser)session.getAttribute("cur_user");
        return friendlinkDao.updateDr(ids,dr,new Date());
    }

    @Override
    public int delete(Integer[] ids, HttpSession session) throws Exception {
        SysUser user = (SysUser)session.getAttribute("cur_user");
        return friendlinkDao.delete(ids);
    }
}
