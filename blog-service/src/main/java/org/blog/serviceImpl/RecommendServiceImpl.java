package org.blog.serviceImpl;

import org.admin.model.SysUser;
import org.blog.constant.TableField;
import org.blog.dao.RecommendMapper;
import org.blog.model.Blog;
import org.blog.model.Recommend;
import org.blog.service.RecommendService;
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
public class RecommendServiceImpl implements RecommendService {
    @Autowired
    private RecommendMapper recommendDao;
    @Override
    public List<Recommend> getRecommendBlogs() throws Exception {
        return recommendDao.query(0,9,TableField.ORDER_ASC,TableField.LV,TableField.DR_UNDELETE);
    }

    @Override
    public List<Recommend> get(Integer page, Integer rows, Integer order, Integer field,Integer dr, HttpSession session) throws Exception {
        if (page!=null && rows!=null){
            page = page==0?1:page;
            page = (page-1)*rows;
        }
        return recommendDao.query(page,rows,order,field,dr);
    }

    @Override
    public int add(Recommend recommend, HttpSession session) throws Exception{
        SysUser user = (SysUser)session.getAttribute("cur_user");
        recommend.setCreateUser(user.getId());
        recommend.setDr(TableField.DR_UNDELETE);
        recommend.setTs(new Date());
        recommend.setCreateTime(new Date());
        return recommendDao.insert(recommend);
    }

    @Override
    public int modify(Recommend recommend, HttpSession session) throws Exception {
        SysUser user = (SysUser)session.getAttribute("cur_user");
        recommend.setTs(new Date());
        return recommendDao.modify(recommend);
    }

    @Override
    public int updateDr(Integer[] ids, Integer dr, HttpSession session) throws Exception {
        SysUser user = (SysUser)session.getAttribute("cur_user");
        return recommendDao.updateDr(ids,dr,new Date());
    }

    @Override
    public int delete(Integer[] ids, HttpSession session)throws Exception {
        SysUser user = (SysUser)session.getAttribute("cur_user");
        return recommendDao.delete(ids);
    }

    @Override
    public Integer getPages(Integer dr, Integer rows, HttpSession session) throws Exception {
        Integer count = recommendDao.getCount(dr);
        if (count!=null&&count!=0){
            return count%rows==0?(count/rows):((count/rows)+1);
        }
        return  0;
    }
}
