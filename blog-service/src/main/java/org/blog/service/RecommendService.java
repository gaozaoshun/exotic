package org.blog.service;

import org.blog.model.Blog;
import org.blog.model.Friendlink;
import org.blog.model.Recommend;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 高灶顺
 * @date 2016-11-26
 */
public interface RecommendService {
    List<Recommend> getRecommendBlogs() throws Exception;

    List<Recommend> get(Integer page, Integer rows, Integer order, Integer field, Integer dr,HttpSession session) throws Exception;

    int add(Recommend recommend, HttpSession session)throws Exception;

    int modify(Recommend recommend, HttpSession session)throws Exception;

    int updateDr(Integer[] ids, Integer dr, HttpSession session)throws Exception;

    int delete(Integer[] ids, HttpSession session)throws Exception;

    Integer getPages(Integer dr, Integer rows, HttpSession session)throws Exception;
}
