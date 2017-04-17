package org.admin.dao;

import org.admin.model.SysRole;
import org.admin.model.SysUser;
import org.apache.ibatis.annotations.Param;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser queryByUserName(String username);

    int modifyUser(SysUser sysUser);

    List<SysUser> query(Map<String, Object> map);

    int modify(SysUser user);
    int modifyDr(SysUser user);

    int updateDr(@Param("ids") Integer[] ids,@Param("dr") Integer dr,@Param("ts") Date ts);

    int delete(@Param("ids") Integer[] ids);

    Integer getCount(@Param("dr") Integer dr, @Param("q") String q);

    List<SysRole> queryRolesById(@Param("page")Integer page, @Param("rows")Integer rows, @Param("id")Integer id);

    Integer saveUserRoles(@Param("userId") Integer userId, @Param("ids") Integer[] ids);

    Integer deleteUserRoles(@Param("userId") Integer userId);

    Integer deleteRole(@Param("userId") Integer userId,@Param("roleId") Integer roleId);
}