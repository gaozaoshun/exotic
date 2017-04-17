package org.blog.service;

import org.blog.model.Friendlink;
import org.blog.model.Wisdom;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 高灶顺
 * @date 2016-11-26
 */
public interface WisdomService {
    /**
     * 获取每日一句
     * @return
     */
    Wisdom getWisdom() throws Exception;

    List<Wisdom> get(Integer page, Integer rows, Integer order, Integer field, Integer dr,String title, Integer id,HttpSession session) throws Exception;

    int add(Wisdom wisdom, HttpSession session)throws Exception;

    int modify(Wisdom wisdom, HttpSession session)throws Exception;

    int updateDr(Integer[] ids, Integer dr, HttpSession session)throws Exception;

    int delete(Integer[] ids, HttpSession session)throws Exception;

    Integer getPages(Integer page, Integer rows, Integer order, Integer field, Integer dr, String title, HttpSession session)throws Exception;
}
