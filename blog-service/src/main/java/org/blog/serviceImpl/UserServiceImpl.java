package org.blog.serviceImpl;

import org.blog.dao.UserMapper;
import org.blog.model.User;
import org.blog.service.UserService;
import org.common.model.BeginEndTime;
import org.exotic.utils._Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author 高灶顺
 * @date 2016-11-26
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public List<User> query(Integer page, Integer rows, Integer order, Integer field, User user, BeginEndTime beginEndTime, HttpSession session) throws Exception {
        if (page!=null){
            page = page ==0?1:page;
            page = (page-1)*rows;
        }
        Map<String,Object> map = _Map.toMap(user,beginEndTime);
        map.put("page",page);
        map.put("rows",rows);
        map.put("order",order);
        map.put("field",field);
        return userMapper.query(map);
    }
}
