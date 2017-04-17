package org.blog.dao;

import org.apache.ibatis.annotations.Param;
import org.blog.model.Bloger;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface BlogerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Bloger record);

    int insertSelective(Bloger record);

    Bloger selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Bloger record);

    int updateByPrimaryKey(Bloger record);

    Bloger queryById(@Param("id")Integer id,@Param("dr") Integer dr);

    List<Bloger> query(Map<String,Object> map);

    Integer update(Bloger bloger);

    Integer updateDr(@Param("id")Integer id, @Param("userId")Integer userId, @Param("dr") Integer dr, @Param("ts")Date ts);

    Integer delete(@Param("ids") Integer[] ids);
}