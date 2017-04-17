package org.blog.dao;

import org.apache.ibatis.annotations.Param;
import org.blog.model.Friendlink;

import java.util.Date;
import java.util.List;

public interface FriendlinkMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Friendlink record);

    int insertSelective(Friendlink record);

    Friendlink selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Friendlink record);

    int updateByPrimaryKey(Friendlink record);

    /**
     * 查询友情链接
     * @param page 当前页
     * @param rows  每页记录数
     * @param order 0：ASC 1:DESC
     * @param field
     *              4:lv
     *              3:create_time
     *              5:ts
     * @param dr 0:未删除 1:已删除
     * @return
     */
    List<Friendlink> query(@Param("page") Integer page,@Param("rows") Integer rows, @Param("order") Integer order,@Param("field") Integer field, @Param("dr") Integer dr);

    int add(Friendlink friendlink);

    int update(Friendlink friendlink);

    int updateDr(@Param("ids") Integer[] ids, @Param("dr") Integer dr,@Param("ts") Date ts);

    int delete(@Param("ids")Integer[] ids);

}