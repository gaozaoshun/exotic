package org.blog.serviceImpl;

import org.blog.constant.TableField;
import org.blog.dao.CommentMapper;
import org.blog.model.Comment;
import org.blog.model.User;
import org.blog.service.CommentService;
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
 * @date 2016-11-27
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentDao;
    //获取前三条评论数据
    public List<Comment> getLatestComments() throws Exception {
        Map<String,Object> map = new HashMap<String,Object>(0);
        map.put("page",0);
        map.put("rows",3);
        map.put("order",1);
        map.put("field",3);
        map.put("dr",0);
        return commentDao.query(map);
    }

    @Override
    public List<Comment> getComments(Integer page, Integer rows, Integer order, Integer field, Comment comment, String title,BeginEndTime beginEndTime, HttpSession session) throws Exception {
        if (page!=null){
            page = page == 0 ? 1:page;
            page = (page - 1)*rows;
        }
        Map<String,Object> map = _Map.toMap(comment,beginEndTime);
        map.put("page",page);
        map.put("rows",rows);
        map.put("order",order);
        map.put("field",field);
        map.put("title",title);
        return commentDao.query(map);
    }

    @Override
    public Integer getCounts(Integer id,Integer rows) {
        Integer count = commentDao.countAll(id);
        return count%rows==0?count/rows:count/rows+1;
    }

    @Override
    public Integer publish(Comment comment, HttpSession session) {
        User user = (User) session.getAttribute("user");
        comment.setCreateTime(new Date());
        comment.setCreateUser(1);//TODO 测试
        comment.setDr(TableField.DR_UNDELETE);
        comment.setTs(new Date());
        return commentDao.add(comment);
    }

    @Override
    public Integer updateDr(Integer[] ids, int dr) {
        return commentDao.updateDr(ids,dr,new Date());
    }

    @Override
    public Integer batchDelete(Integer[] ids) {
        return commentDao.batchDelete(ids);
    }
}
