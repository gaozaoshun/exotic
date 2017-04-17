package org.blog.service;

import org.blog.model.Friendlink;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 高灶顺
 * @date 2016-11-26
 */
public interface FriendlinkService {
    List<Friendlink> getFriendlink() throws Exception;

    int add(Friendlink friendlink, HttpSession session)throws Exception;

    List<Friendlink> query(Integer page, Integer rows, Integer order, Integer field,  Integer dr, HttpSession session)throws Exception;;

    int modify(Friendlink friendlink, HttpSession session)throws Exception;

    int updateDr(Integer[] ids, Integer dr, HttpSession session)throws Exception;

    int delete(Integer[] ids, HttpSession session)throws Exception;
}
