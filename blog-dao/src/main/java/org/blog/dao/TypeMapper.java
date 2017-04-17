package org.blog.dao;

import org.apache.ibatis.annotations.Param;
import org.blog.model.Type;

import java.util.List;

public interface TypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Type record);

    int insertSelective(Type record);

    Type selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Type record);

    int updateByPrimaryKey(Type record);

    List<Type> query(@Param("page")Integer page, @Param("rows") Integer rows, @Param("order") Integer order, @Param("field") Integer field, @Param("dr") Integer dr);

    List<Type> queryAll(@Param("order") Integer order, @Param("field") Integer field, @Param("dr") Integer dr);

    int modify(Type type);

    int add(Type type);
}