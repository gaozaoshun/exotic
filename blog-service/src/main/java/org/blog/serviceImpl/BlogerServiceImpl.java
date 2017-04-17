package org.blog.serviceImpl;
import org.admin.model.SysUser;
import org.blog.constant.TableField;
import org.blog.dao.BlogerMapper;
import org.blog.model.Bloger;
import org.blog.service.BlogerService;
import org.common.model.BeginEndTime;
import org.exotic.utils._Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 高灶顺
 * @date 2016-11-26
 */
@Service
public class BlogerServiceImpl implements BlogerService{
    @Autowired
    private BlogerMapper blogerDao;

    public Bloger getBloger() throws Exception {
        Map<String,Object> map = new HashMap<String,Object>(0);
        map.put("page",0);
        map.put("rows",1);
        map.put("order",1);
        map.put("field",3);
        return  blogerDao.query(map).get(0);
    }

    @Override
    public List<Bloger> query(Integer page, Integer rows, Integer order, Integer field, Bloger bloger, BeginEndTime beginEndTime, HttpSession session) throws Exception {
        if (page!=null){
            page = page==0?1:page;
            page = (page-1)*rows;
        }
        Map<String,Object> map = _Map.toMap(bloger,beginEndTime);
        map.put("page",page);
        map.put("rows",rows);
        map.put("order",order);
        map.put("field",field);
        return blogerDao.query(map);
    }

    @Override
    public Integer modify(Bloger bloger,HttpSession session) throws Exception {
        SysUser user = (SysUser) session.getAttribute("cur_user");
        bloger.setCreateUser(user.getId());
        return blogerDao.update(bloger);
    }

    @Override
    public Integer deleteFack(Integer id, HttpSession session)throws Exception {
        SysUser user = (SysUser) session.getAttribute("cur_user");
        return blogerDao.updateDr(id,user.getId(),TableField.DR_DELETE,new Date());
    }

    @Override
    public Integer deleteReal(Integer[] ids, HttpSession session)throws Exception {
        SysUser user = (SysUser) session.getAttribute("cur_user");
        return blogerDao.delete(ids);
    }

    @Override
    public int add(Bloger bloger,HttpSession session)throws Exception {
        SysUser user = (SysUser) session.getAttribute("cur_user");
        bloger.setCreateTime(new Date());
        bloger.setCreateUser(user.getId());
        bloger.setDr(TableField.DR_UNDELETE);
        bloger.setTs(new Date());
        return blogerDao.insert(bloger);
    }
}
