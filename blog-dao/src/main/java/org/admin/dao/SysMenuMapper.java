package org.admin.dao;

import org.admin.model.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SysMenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    SysMenu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysMenu record);

    int updateByPrimaryKey(SysMenu record);

    List<SysMenu> queryByUserId(@Param("userId") Integer id, @Param("dr") Integer dr);

    SysMenu queryBySuperId(@Param("superId") Integer superId, @Param("dr") Integer dr);

    int modify(SysMenu sysMenu);

    int updateDr(@Param("ids") Integer[] ids, @Param("dr") Integer dr, @Param("ts") Date ts);

    int delete(@Param("ids")Integer[] ids);

    List<SysMenu> query(@Param("page") Integer page, @Param("rows") Integer rows,@Param("order") Integer order, @Param("field") Integer field, @Param("dr") Integer dr, @Param("superId") Integer superId);

    Integer getCount(@Param("q")String q);

    List<SysMenu> getSuper(@Param("order") Integer order, @Param("field") Integer field, @Param("dr") Integer dr);
}