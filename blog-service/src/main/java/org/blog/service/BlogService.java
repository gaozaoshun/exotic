package org.blog.service;

import org.admin.model.SysUser;
import org.blog.model.Blog;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * @author 高灶顺
 * @date 2016-11-26
 */
public interface BlogService {
    List<Blog> getLatesdBlogs() throws Exception;

    List<Blog> getHotBlogs() throws Exception;

    Blog getDetail(int id) throws Exception;

    List<Blog> getAboutByType(Integer typeId) throws Exception;

    List<Blog> getBlogsByType(int page, int rows, int typeId, HttpServletRequest request) throws Exception;

    String getPageBar() throws Exception;

    List<Blog> getSearchByQ(String q, int page, int rows, HttpServletRequest request) throws Exception;

    int add(String title, String cover,String summary, String content, String key_words, int typeId, HttpSession session) throws Exception;

    Map<String,Object> queryPageMsg(Integer curr, SysUser user, String title, String beginTime, String endTime, Integer dr, Integer typeId) throws ParseException, Exception;

    List<Blog> getBlogsByPage(SysUser user, Map<String, Object> pageMsg) throws Exception;

    int delete(Integer[] ids, HttpSession session) throws Exception;

    int updateByDrAndUser(Integer[] ids, HttpSession session) throws Exception;

    int updateByBlogAndUser(Blog blog, HttpSession session) throws Exception;

    List<Blog> search(Map<String, Object> params) throws Exception;

    Integer getPages(Map<String, Object> params) throws Exception;

    Blog getBlogByIdAndUser(int id, HttpSession session);

    int repByDrAndUser(Integer[] ids, HttpSession session);
}
