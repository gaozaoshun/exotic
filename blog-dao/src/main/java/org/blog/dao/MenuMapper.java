package org.blog.dao;

import org.apache.ibatis.annotations.Param;
import org.blog.model.Menu;

import java.util.Date;
import java.util.List;

public interface MenuMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(Menu record);
    int insertSelective(Menu record);
    Menu selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(Menu record);
    int updateByPrimaryKey(Menu record);

    List<Menu> query(@Param("page") Integer page,@Param("rows") Integer rows,@Param("order") Integer order,@Param("field") Integer field,@Param("dr") Integer dr);

    int add(Menu menu);

    int modify(Menu menu);

    int updateDr(@Param("ids") Integer[] ids, @Param("dr") Integer dr,@Param("ts") Date ts);

    int delete(@Param("ids") Integer[] ids);
}