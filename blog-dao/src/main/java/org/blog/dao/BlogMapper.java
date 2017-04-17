package org.blog.dao;

import org.apache.ibatis.annotations.Param;
import org.blog.model.Blog;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface BlogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Blog record);

    int insertSelective(Blog record);

    Blog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Blog record);

    int updateByPrimaryKey(Blog record);

    /**
     * 查询
     * @param page 页码 0-第一页
     * @param rows 记录数
     * @param order 0-ASD 1-DESC
     * @param field 排序字段
     * @param dr 0-未删 1-已删
     * @return List<Blog>
     */
    List<Blog> query(@Param("page")Integer page,@Param("rows") Integer rows, @Param("order") Integer order, @Param("field") Integer field,@Param("dr") Integer dr);

    Blog queryById(@Param("id") Integer id, @Param("dr") Integer dr);

    List<Blog> queryByType(@Param("page")Integer page,@Param("rows") Integer rows,@Param("typeId") Integer typeId, @Param("order") Integer order, @Param("field") Integer field,@Param("dr") Integer dr);

    Integer countByType(@Param("typeId") Integer typeId);

    Integer countByQ(@Param("q") String q);

    List<Blog> queryByQ(@Param("q")String q, @Param("page")Integer page,@Param("rows") Integer rows, @Param("order") Integer order, @Param("field") Integer field,@Param("dr") Integer dr);

    Integer countByWhereAndUser(@Param("userId") Integer userId, @Param("title") String title, @Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("dr") Integer dr, @Param("typeId") Integer typeId);

    List<Blog> queryByWhereAndUser(@Param("userId") Integer id, @Param("page")Integer index, @Param("rows")Integer pages, @Param("title") String title, @Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("dr")Integer dr, @Param("typeId")Integer typeId);

    Integer deleteByPksAndUserId(@Param("ids") Integer[] ids,@Param("userId")Integer id);

    Integer updateByDrAndUser(@Param("ids") Integer[] ids,@Param("dr")Integer dr,@Param("userId") Integer id,@Param("ts") Date ts);

    int updateByBlogAndUser(Blog blog);

    List<Blog> queryByAll(Map<String, Object> map);

    Integer countAll(Map<String, Object> params);

    Blog queryByIdAndUser(@Param("id") Integer id,@Param("userId")  Integer userId, @Param("dr") Integer dr);
}