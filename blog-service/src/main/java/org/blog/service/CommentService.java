package org.blog.service;

import org.blog.model.Comment;
import org.common.model.BeginEndTime;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 高灶顺
 * @date 2016-11-27
 */
public interface CommentService {
    /**
     * 获取最新评论
     * @return List<Comment>
     */
    List<Comment> getLatestComments() throws Exception;

    List<Comment> getComments(Integer page, Integer rows, Integer order, Integer field, Comment comment, String title,BeginEndTime beginEndTime, HttpSession session)throws Exception;

    Integer getCounts(Integer id,Integer rows);

    Integer publish(Comment comment, HttpSession session);

    Integer updateDr(Integer[] ids, int drDelete);

    Integer batchDelete(Integer[] ids);
}
