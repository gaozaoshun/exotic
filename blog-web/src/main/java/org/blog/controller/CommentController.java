package org.blog.controller;

import org.blog.constant.RouterConstant;
import org.blog.constant.TableField;
import org.blog.model.Comment;
import org.blog.service.CommentService;
import org.common.model.BeginEndTime;
import org.exotic.utils._Http;
import org.exotic.utils._JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author 高灶顺
 * @date 2016-11-28
 */
@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * 查询评论列表
     *
     * @param page     当前页
     * @param rows     每页记录数
     * @param order    0：ASC 1：DESC
     * @param field      排序字段
     * @param comment     查询条件
     *                    id
     *                    blogId
     *                    content
     *                    createUser
     *                    dr
     * @param beginEndTime  查询条件
     *                    cBeginTime 创建时间【最小】
     *                    cEndTime 创建时间【最大】
     *                    tBeginTime 更新时间【最小】
     *                    tEndTime 更新时间【最大】
     * @param response
     * @throws Exception
     */
    @RequestMapping(RouterConstant.COMMENT_GET)
    public void getComments(
            @RequestParam(name = "page",required = false) Integer page,
            @RequestParam(name = "rows",required = false) Integer rows,
            @RequestParam(name = "title",required = false) String title,
            Integer order,
            Integer field,
            Comment comment,
            BeginEndTime beginEndTime,
            HttpSession session,
            HttpServletResponse response) throws Exception {
        List<Comment> comments = commentService.getComments( page, rows,order,field,comment,title,beginEndTime,session);
        Integer pages = commentService.getCounts(comment.getBlogId(), rows);//总页数
        Map<String, Object> map = _JSON.success(comments);
        map.put("pages", pages);
        _Http.sendWithCloseRepeat(response, map);
    }

    /**
     * 获取文章评论总页数
     * @param blogId
     * @param rows
     * @param response
     * @throws Exception
     */
    @RequestMapping(RouterConstant.COMMENT_PAGES)
    public void pages(
            @RequestParam(name = "blogId",required = false) Integer blogId,
            Integer rows,
            HttpServletResponse response
    ) throws Exception{
        Integer pages = commentService.getCounts(blogId, rows);//总页数
        _Http.send(response, _JSON.success(pages));
    }
    /**
     * 发表评论
     *
     * @param comment  评论信息
     *                 blogId：文章ID
     *                 content：内容
     * @param response
     * @throws Exception
     */
    @RequestMapping(RouterConstant.COMMENT_PUBLISH)
    public void publish(Comment comment, HttpServletResponse response, HttpSession session) throws Exception {
        Integer flag = commentService.publish(comment, session);
        _Http.send(response, _JSON.success("您成功发表" + flag + "条评论！"));
    }

    /**
     * 撤销/恢复评论
     *
     * @param ids      评论ID集合
     * @param response
     * @throws Exception
     */
    @RequestMapping(RouterConstant.COMMENT_DR)
    public void dr(
            @RequestParam("ids[]") Integer[] ids,
            Integer dr,
            HttpServletResponse response) throws Exception {
        Integer flag = commentService.updateDr(ids, dr);
        if (dr == TableField.DR_UNDELETE)
            _Http.send(response, _JSON.success("您成功恢复" + flag + "条评论！"));
        else
            _Http.send(response, _JSON.success("您成功撤销" + flag + "条评论！"));
    }

    /**
     * 删除评论--数据库删除
     *
     * @param ids      评论ID集合
     * @param response
     * @throws Exception
     */
    @RequestMapping(RouterConstant.COMMENT_DELETE_REAL)
    public void deleteReal(@RequestParam("ids[]") Integer[] ids, HttpServletResponse response) throws Exception {
        Integer flag = commentService.batchDelete(ids);
        _Http.send(response, _JSON.success("您成功删除" + flag + "条评论！"));
    }

}
