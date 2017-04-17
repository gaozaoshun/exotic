package org.blog.dao;

import org.apache.ibatis.annotations.Param;
import org.blog.model.Comment;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKeyWithBLOBs(Comment record);

    int updateByPrimaryKey(Comment record);

    List<Comment> query(Map<String,Object> map);

    Integer countAll(@Param("blogId") Integer blogId);

    Integer add(Comment comment);

    Integer updateDr(@Param("ids") Integer[] ids, @Param("dr") Integer dr,@Param("ts") Date ts);

    Integer batchDelete(@Param("ids") Integer[] ids);

    Integer batchDeleteByBlogId(@Param("ids") Integer[] ids);
}