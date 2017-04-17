package org.blog.dao;

import org.apache.ibatis.annotations.Param;
import org.blog.model.Recommend;

import java.util.Date;
import java.util.List;

public interface RecommendMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Recommend record);

    int insertSelective(Recommend record);

    Recommend selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Recommend record);

    int updateByPrimaryKey(Recommend record);

    List<Recommend> query(@Param("page")Integer page, @Param("rows") Integer rows, @Param("order") Integer order, @Param("field") Integer field, @Param("dr") Integer dr);

    int modify(Recommend recommend);

    int updateDr(@Param("ids") Integer[] ids, @Param("dr") Integer dr, @Param("ts") Date ts);

    int delete(@Param("ids") Integer[] ids);

    Integer getCount(Integer dr);

    Integer batchDeleteByBlogId(@Param("ids") Integer[] ids);
}