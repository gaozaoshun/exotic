package org.blog.controller;

import org.apache.ibatis.annotations.Param;
import org.blog.constant.RouterConstant;
import org.blog.constant.TableField;
import org.blog.model.Menu;
import org.blog.model.Photos;
import org.blog.service.MenuService;
import org.blog.service.PhotosService;
import org.exotic.utils._Http;
import org.exotic.utils._JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 高灶顺
 * @date 2016/12/19
 */
@Controller
public class PhotoController {
    @Autowired
    private PhotosService photosService;
    /**
     * 添加相册
     * @param photos
     *              name
     *              url
     *              lv
     * @param response
     * @throws Exception
     */
    @RequestMapping(RouterConstant.PHOTOS_ADD)
    public void add(
            Photos photos,
            HttpSession session,
            HttpServletResponse response) throws Exception{
        int flag = photosService.add(photos,session);
        _Http.send(response, _JSON.success(flag+"条网相册添加成功！"));
    }
    /**
     * 查询相册
     * @param page 当前页
     * @param rows  每页记录数
     * @param order 0:ASC 1:DESC
     * @param field
     * @param name 查询字段
     * @param dr  0:未删除 1：删除
     * @param response
     * @throws Exception
     */
    @RequestMapping(RouterConstant.PHOTOS_GET)
    public void get(
            Integer page,
            Integer rows,
            Integer order,
            Integer field,
            Integer dr,
            @RequestParam(name = "name",required = false) String name,
            HttpSession session,
            HttpServletResponse response) throws Exception{
        List<Photos> photoses = photosService.query(page,rows,order,field,dr,name,session);
        Map<String,Object> map =  _JSON.success(photoses);
        Integer pages = photosService.getCount(dr,rows);
        map.put("pages",pages);
        _Http.send(response, map);
    }

    /**
     * 修改相册
     * @param photos
     *               id
     *               name
     *               url
     *               lv
     *               dr
     * @param response
     * @throws Exception
     */
    @RequestMapping(RouterConstant.PHOTOS_MODIFY)
    public void modify(
            Photos photos,
            HttpSession session,
            HttpServletResponse response) throws Exception{
        int flag = photosService.modify(photos,session);
        _Http.send(response, _JSON.success(flag+"条相册修改成功！"));
    }

    /**
     * 撤销/恢复 相册
     * @param ids ID集合
     * @param dr 删除标志
     * @param response
     * @throws Exception
     */
    @RequestMapping(RouterConstant.PHOTOS_UPDATE_DR)
    public void updateDr(
            @RequestParam("ids[]") Integer[] ids,
            Integer dr,
            HttpSession session,
            HttpServletResponse response) throws Exception{
        int flag = photosService.updateDr(ids,dr,new Date(),session);
        if (dr == TableField.DR_UNDELETE)
            _Http.send(response, _JSON.success(flag+"条相册恢复成功！"));
        else
            _Http.send(response, _JSON.success(flag+"条相册撤销成功！"));

    }

    /**
     * 删除相册
     * @param ids 菜单集合ID
     * @param session
     * @param response
     * @throws Exception
     */
    @RequestMapping(RouterConstant.PHOTOS_DELETE_REAL)
    public void delete(
            @RequestParam("ids[]") Integer[] ids,
            HttpSession session,
            HttpServletResponse response) throws Exception{
        int flag = photosService.delete(ids,session);
        _Http.send(response, _JSON.success(flag+"条相册删除成功！"));
    }
}
