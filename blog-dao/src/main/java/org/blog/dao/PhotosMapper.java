package org.blog.dao;

import org.apache.ibatis.annotations.Param;
import org.blog.model.Photos;

import java.util.Date;
import java.util.List;

public interface PhotosMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Photos record);

    int insertSelective(Photos record);

    Photos selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Photos record);

    int updateByPrimaryKey(Photos record);

    List<Photos> query(@Param("page")Integer page,@Param("rows") Integer rows, @Param("order") Integer order, @Param("field") Integer field,@Param("dr") Integer dr,@Param("name") String name);

    int add(Photos photos);

    int modify(Photos photos);

    int updateDr(@Param("ids") Integer[] ids, @Param("dr") Integer dr, @Param("ts") Date ts);

    int delete(@Param("ids")Integer[] ids);

    Integer count(Integer dr);
}