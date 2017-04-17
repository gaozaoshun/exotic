package org.blog.serviceImpl;

import org.admin.model.SysUser;
import org.blog.constant.TableField;
import org.blog.dao.WisdomMapper;
import org.blog.model.Wisdom;
import org.blog.service.WisdomService;
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
public class WisdomServiceImpl implements WisdomService {
    @Autowired
    private WisdomMapper wisdomDao;

    public Wisdom getWisdom() throws Exception{
        return wisdomDao.queryOrderByLv(TableField.ORDER_ASC, TableField.DR_UNDELETE);
    }

    @Override
    public List<Wisdom> get(Integer page, Integer rows, Integer order, Integer field, Integer dr, String title,Integer id,HttpSession session) throws Exception {
        if (page!=null && rows!=null){
            page = page == 0?1:page;
            page = (page-1)*rows;
        }
        return wisdomDao.query(page,rows,order,field,dr,title,id);
    }

    @Override
    public int add(Wisdom wisdom, HttpSession session) throws Exception {
        SysUser user = (SysUser) session.getAttribute("cur_user");
        wisdom.setCreateUser(user.getId());
        wisdom.setDr(TableField.DR_UNDELETE);
        wisdom.setTs(new Date());
        wisdom.setCreateTime(new Date());
        return wisdomDao.insert(wisdom);
    }

    @Override
    public int modify(Wisdom wisdom, HttpSession session) throws Exception {
        wisdom.setTs(new Date());
        return wisdomDao.modify(wisdom);
    }

    @Override
    public int updateDr(Integer[] ids, Integer dr, HttpSession session) throws Exception {
        return wisdomDao.updateDr(ids,dr,new Date());
    }

    @Override
    public int delete(Integer[] ids, HttpSession session) throws Exception {
        return wisdomDao.delete(ids);
    }

    @Override
    public Integer getPages(Integer page, Integer rows, Integer order, Integer field, Integer dr, String title, HttpSession session) throws Exception {
        if (page!=null&&rows!=null){
            page = page == 0?1:page;
            page = (page-1)*rows;
        }
        Integer count = wisdomDao.getCount(page,rows,order,field,dr,title);
        if (count!=null){
            return count%rows==0?count/rows:count/rows+1;
        }
        return 0;
    }
}
