package org.blog.dao;

import org.apache.ibatis.annotations.Param;
import org.blog.model.Songs;

import java.util.Date;
import java.util.List;

public interface SongsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Songs record);

    int insertSelective(Songs record);

    Songs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Songs record);

    int updateByPrimaryKey(Songs record);

    Songs queryOrderByLv(@Param("order") Integer order,@Param("dr") Integer dr);

    List<Songs> query(@Param("page")Integer page, @Param("rows") Integer rows, @Param("order") Integer order, @Param("field") Integer field, @Param("dr") Integer dr, @Param("name") String name);

    int modify(Songs song);

    int updateDr(@Param("ids")Integer[] ids,@Param("dr") Integer dr, @Param("ts")Date ts);

    int delete(@Param("ids")Integer[] ids);

    Integer getCount(@Param("dr")Integer dr);

    Integer orderLV(@Param("id")Integer id, @Param("lv")Integer lv,@Param("ts") Date ts);
}