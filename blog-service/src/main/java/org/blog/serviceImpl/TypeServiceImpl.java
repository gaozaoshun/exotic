package org.blog.serviceImpl;

import org.admin.model.SysUser;
import org.blog.constant.TableField;
import org.blog.dao.TypeMapper;
import org.blog.model.Type;
import org.blog.service.TypeService;
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
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeMapper typeDao;

    @Override
    public List<Type> getTypes(Integer dr) throws Exception {
        return typeDao.queryAll(TableField.ORDER_ASC,TableField.LV,dr);
    }

    @Override
    public int modify(Type type)throws Exception {
        type.setTs(new Date());
        return typeDao.modify(type);
    }

    @Override
    public int add(Type type, HttpSession session)throws Exception {
        SysUser user = (SysUser) session.getAttribute("cur_user");
        type.setCreateUser(user.getId());
        type.setCreateTime(new Date());
        type.setTs(new Date());
        return typeDao.add(type);
    }

    @Override
    public int del(Integer id)throws Exception {
        return typeDao.deleteByPrimaryKey(id);
    }
}
