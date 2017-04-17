package org.admin.dao;

import org.admin.model.SysAutho;
import org.admin.model.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SysRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    List<SysRole> query(Map<String, Object> whereField);

    int modify(SysRole role);

    int updateDr(@Param("ids") Integer[] ids,@Param("dr") Integer dr,@Param("ts") Date ts);

    int delete(@Param("ids")Integer[] ids);

    int getCount(@Param("q") String q);

    int updateDrV2(SysRole srole);

    int saveAuthos(@Param("roleId") Integer roleId, @Param("ids") Integer[] ids);
}