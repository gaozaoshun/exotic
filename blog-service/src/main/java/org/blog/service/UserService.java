package org.blog.service;

import org.blog.model.Friendlink;
import org.blog.model.User;
import org.common.model.BeginEndTime;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 高灶顺
 * @date 2016-11-26
 */
public interface UserService {

    List<User> query(Integer page, Integer rows, Integer order, Integer field, User user, BeginEndTime beginEndTime, HttpSession session) throws Exception;
}
