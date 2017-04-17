package org.blog.controller;

import org.blog.constant.RouterConstant;
import org.blog.constant.TableField;
import org.blog.model.Recommend;
import org.blog.service.RecommendService;
import org.exotic.utils._Http;
import org.exotic.utils._JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 高灶顺
 * @date 2016/12/19
 */
@Controller
public class RecommendController {
    @Autowired
    private RecommendService recommendService;
    @RequestMapping(RouterConstant.RECOMMEND_PAGES)
    public void pages(
            Integer dr,
            Integer rows,
            HttpServletResponse response,
            HttpSession session) throws Exception{
        Integer pages = recommendService.getPages(dr,rows,session);
        _Http.sendWithCloseRepeat(response,_JSON.success(pages));
    }
    /**
     * 获取推荐博文
     * @param page 当前页
     * @param rows  每页记录数
     * @param order 0:ASC 1:DESC
     * @param field 排序字段
     * @param dr    删除标志
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.RECOMMEND_GET)
    public  void get(
            @RequestParam(name = "page",required = false) Integer page,
            @RequestParam(name = "rows",required = false) Integer rows,
            Integer order,
            Integer field,
            @RequestParam(name = "dr",required = false) Integer dr,
            HttpServletResponse response,
            HttpSession session) throws Exception{
        List<Recommend> recommends = recommendService.get(page,rows,order,field,dr,session);
        _Http.sendWithCloseRepeat(response, _JSON.success(recommends));
    }

    /**
     * 添加推荐博文
     * @param recommend
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.RECOMMEND_ADD)
    public  void add(
            Recommend recommend,
            HttpServletResponse response,
            HttpSession session) throws Exception{
        int flag = recommendService.add(recommend,session);
        _Http.send(response, _JSON.success(flag+"条推荐博文添加成功！"));
    }

    /**
     * 修改推荐博文
     * @param recommend
     *              id
     *              blogId
     *              lv
     *              dr
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.RECOMMEND_MODIFY)
    public  void modify(
            Recommend recommend,
            HttpServletResponse response,
            HttpSession session) throws Exception{
        int flag = recommendService.modify(recommend,session);
        _Http.send(response, _JSON.success(flag+"条推荐博文修改成功！"));
    }

    /**
     * 撤销/恢复  推荐博文
     * @param ids
     * @param dr
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.RECOMMEND_UPDATE_DR)
    public  void updateDr(
            @RequestParam("ids[]") Integer[] ids,
            Integer dr,
            HttpServletResponse response,
            HttpSession session) throws Exception{
        int flag = recommendService.updateDr(ids,dr,session);
        if(dr == TableField.DR_UNDELETE)
            _Http.send(response, _JSON.success(flag+"条推荐博文恢复成功！"));
        else
            _Http.send(response, _JSON.success(flag+"条推荐博文修改成功！"));
    }

    /**
     * 删除推荐博文
     * @param ids
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.RECOMMEND_DELETE_REAL)
    public  void delete(
            @RequestParam("ids[]") Integer[] ids,
            HttpServletResponse response,
            HttpSession session) throws Exception{
        int flag = recommendService.delete(ids,session);
        _Http.send(response, _JSON.success(flag+"条推荐博文删除成功！"));
    }
}
