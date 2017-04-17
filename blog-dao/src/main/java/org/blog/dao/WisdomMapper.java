package org.blog.dao;

import org.apache.ibatis.annotations.Param;
import org.blog.model.Wisdom;

import java.util.Date;
import java.util.List;

public interface WisdomMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Wisdom record);

    int insertSelective(Wisdom record);

    Wisdom selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Wisdom record);

    int updateByPrimaryKey(Wisdom record);

    Wisdom queryOrderByLv(@Param("order") Integer order, @Param("dr") Integer dr);

    List<Wisdom> query(@Param("page")Integer page, @Param("rows") Integer rows, @Param("order") Integer order, @Param("field") Integer field, @Param("dr") Integer dr,@Param("title") String title,@Param("id") Integer id);

    int modify(Wisdom wisdom);

    int updateDr(@Param("ids") Integer[] ids,@Param("dr") Integer dr, @Param("ts") Date ts );

    int delete(@Param("ids")Integer[] ids);

    Integer getCount(@Param("page")Integer page, @Param("rows")Integer rows, @Param("order")Integer order, @Param("field")Integer field, @Param("dr")Integer dr, @Param("title")String title);
}