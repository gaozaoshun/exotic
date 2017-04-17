package org.admin.dao;

import org.admin.model.SysAutho;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SysAuthoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysAutho record);

    int insertSelective(SysAutho record);

    SysAutho selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysAutho record);

    int updateByPrimaryKey(SysAutho record);

    List<SysAutho> queryByUserId(@Param("userId") Integer userId, @Param("dr") Integer dr);

    List<SysAutho> query(@Param("q") String q, @Param("id") Integer id, @Param("page") Integer page, @Param("rows") Integer rows, @Param("order") Integer order, @Param("field") Integer field, @Param("dr") Integer dr, @Param("menuId") Integer menuId);

    int modify(SysAutho sysAutho);

    int updateDr(SysAutho sysAutho);

    int delete(@Param("ids") Integer[] ids);

    int deleteRoleAutho(@Param("ids")Integer[] ids);

    List<SysAutho> queryByRoleId(@Param("id") Integer id, @Param("page")Integer page,@Param("rows") Integer rows);
}