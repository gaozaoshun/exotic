package org.blog.controller;

import org.admin.model.SysUser;
import org.blog.constant.PageConstant;
import org.blog.constant.RouterConstant;

import org.blog.constant.TableField;
import org.blog.model.Blog;
import org.blog.model.Comment;
import org.blog.model.Type;
import org.blog.service.BlogService;
import org.blog.service.CommentService;
import org.blog.service.TypeService;
import org.exotic.utils._Http;
import org.exotic.utils._JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 博客文章
 *
 * @author 高灶顺
 * @date 2016-11-27
 */
@Controller
public class BlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;

    /**
     * 发布博文
     *
     * @param title    标题 TODO 限制字数
     * @param cover    预览图
     * @param summary  概要
     * @param content  文章内容
     * @param keyWords 关键字（多个用英文半角逗号隔开）TODO 可以优化
     * @param typeId   类型ID
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(RouterConstant.BLOG_PUBLISH)
    public void publish(String title,
                        String cover,
                        String summary,
                        String content,
                        String keyWords,
                        int typeId,
                        HttpServletResponse response,
                        HttpSession session) throws Exception {
        //添加博文到数据库
        int flag = blogService.add(title, cover, summary, content, keyWords, typeId, session);
        _Http.send(response, _JSON.success(flag + "条博文发布成功！"));
    }

    /**
     * 文章列表
     *
     * @param typeId 类别 0-所有
     * @param page   当前页
     * @param rows   每页记录数
     * @return
     * @throws Exception
     */
    @RequestMapping(RouterConstant.BLOG_LIST)
    public ModelAndView type(
            Integer typeId,
            int page,
            int rows,
            HttpServletRequest request
    ) throws Exception {
        ModelAndView mv = new ModelAndView("list");
        List<Blog> blogs = blogService.getBlogsByType(page, rows, typeId, request);
        mv.addObject("blogs", blogs);
        List<Type> types = typeService.getTypes(TableField.DR_UNDELETE);
        mv.addObject("types", types);
        String pagebar = blogService.getPageBar();
        mv.addObject("pagebar", pagebar);
        mv.addObject("typeId", typeId);
        //判断是否有博文
        mv.addObject("isHave", 0);
        if (blogs==null || blogs.size()<1){
            mv.addObject("isHave", 1);
        }
        return mv;
    }

    /**
     * 页面-博客详情
     *
     * @param id 文章的ID
     * @throws Exception
     */
    @Transactional
    @RequestMapping(RouterConstant.BLOG_DETAIL)
    public ModelAndView detail(int id) throws Exception {
        ModelAndView mv = new ModelAndView("detail");
        Blog blog = blogService.getDetail(id);
        mv.addObject("blog", blog);
        List<Blog> others = blogService.getAboutByType(blog.getTypeId());
        mv.addObject("others", others);
        List<Type> types = typeService.getTypes(TableField.DR_UNDELETE);
        mv.addObject("types", types);
        return mv;
    }

    /**
     * 获取指定ID的博客
     * @param id 文章ID
     * @throws Exception
     */
    @Transactional
    @RequestMapping(RouterConstant.BLOG_GET_ID)
    public void getById(int id, HttpServletResponse response, HttpSession session) throws Exception {
        Blog blog = blogService.getBlogByIdAndUser(id, session);
        if (blog == null) {
            _Http.send(response, _JSON.errorParam("ID=" + id + "的文章已被删除！"));
        } else {
            _Http.send(response, _JSON.success(blog));
        }
    }

    /**
     * 博文查询（标题）
     *
     * @param q    查询字段（标题）
     * @param page 当前页
     * @param rows 每页记录数
     * @return
     * @throws Exception
     */
    @RequestMapping(RouterConstant.BLOG_SEARCH)
    public ModelAndView search(String q,
                               @RequestParam(value = "page", required = false) Integer page,
                               @RequestParam(value = "rows", required = false) Integer rows,
                               HttpServletRequest request) throws Exception {
        page = page == null ? 0 : page;
        rows = rows == null ? PageConstant.ROWS : rows;
        ModelAndView mv = new ModelAndView("list");
        List<Blog> blogs = blogService.getSearchByQ(q, page, rows, request);
        mv.addObject("blogs", blogs);
        List<Type> types = typeService.getTypes(TableField.DR_UNDELETE);
        mv.addObject("types", types);
        String pagebar = blogService.getPageBar();
        mv.addObject("pagebar", pagebar);
        mv.addObject("typeId", 0);
        //判断是否有博文
        mv.addObject("isHave", 0);
        if (blogs==null || blogs.size()<1){
            mv.addObject("isHave", 1);
        }
        return mv;
    }

    /**
     * JSON-删除博客-可批量-【数据库删除】
     *
     * @param ids
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.BLOG_DELETE_REAL)
    public void delete_real(
            @RequestParam("ids[]") Integer[] ids,
            HttpServletResponse response,
            HttpSession session) throws Exception {
        int flag = blogService.delete(ids, session);
        _Http.send(response, _JSON.success(flag + "条数据删除成功!"));
    }

    /**
     * JSON-删除博客-可批量-【软删除】
     *
     * @param ids
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.BLOG_DELETE_FACK)
    public void delete_fake(
            @RequestParam("ids[]") Integer[] ids,
            HttpServletResponse response,
            HttpSession session) throws Exception {
        int flag = blogService.updateByDrAndUser(ids, session);
        _Http.send(response, _JSON.success(flag + "条数据撤销成功!"));
    }

    /**
     * JSON-恢复博客-可批量
     *
     * @param ids
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.BLOG_UNDELETE)
    public void delete_unfake(
            @RequestParam("ids[]") Integer[] ids,
            HttpServletResponse response,
            HttpSession session) throws Exception {
        int flag = blogService.repByDrAndUser(ids, session);
        _Http.send(response, _JSON.success(flag + "条数据恢复成功!"));
    }

    /**
     * JSON-修改博客
     *
     * @param blog
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.BLOG_MODIFY)
    public void modify_blog(
            Blog blog,
            HttpServletResponse response,
            HttpSession session) throws Exception {
        int flag = blogService.updateByBlogAndUser(blog, session);
        _Http.send(response, _JSON.success(flag + "条数据修改成功"));
    }

    /**
     * 博客查询-分页-排序
     *
     * @param blog     封装查询条件：
     *                 title：标题
     *                 typeId：文章类别
     *                 createTime：创建时间[cBeginTime,cEndTime]
     *                 dr：删除标志
     *                 ts：最后修改时间 区间[tBeginTime,tEndTime]
     *                 likeNum：喜欢量  区间[minLike,maxLike]
     *                 lookNum：浏览量  区间[minLook,maxLook]
     *                 talkNum：评论量  区间[minTalk,maxTalk]
     *                 keyWords：关键字
     * @param page     当前页
     * @param rows     每页记录数
     * @param field    排序字段 [0:id][1:createTime][2:ts][3:likeNum][4:lookNum][5:talkNum]
     * @param order    排序 [0:asc][1:desc]
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.BLOG_QUERY)
    public void search(Blog blog,
                       @RequestParam(value = "cBeginTime", required = false) String cBeginTime,
                       @RequestParam(value = "cEndTime", required = false) String cEndTime,
                       @RequestParam(value = "tBeginTime", required = false) String tBeginTime,
                       @RequestParam(value = "tEndTime", required = false) String tEndTime,
                       @RequestParam(value = "minLike", required = false) Integer minLike,
                       @RequestParam(value = "maxLike", required = false) Integer maxLike,
                       @RequestParam(value = "minLook", required = false) Integer minLook,
                       @RequestParam(value = "maxLook", required = false) Integer maxLook,
                       @RequestParam(value = "minTalk", required = false) Integer minTalk,
                       @RequestParam(value = "maxTalk", required = false) Integer maxTalk,
                       @RequestParam(value = "page", required = false) Integer page,
                       @RequestParam(value = "rows", required = false) Integer rows,
                       Integer field,
                       Integer order,
                       HttpServletResponse response,
                       HttpSession session) throws Exception {
        System.out.println("关键字："+blog.getKeyWords());
        Map<String, Object> params = this.ParamsToMap(blog, cBeginTime, cEndTime, tBeginTime, tEndTime, minLike, maxLike, minLook, maxLook, minTalk, maxTalk, page, rows, field, order, session);
        List<Blog> blogs = blogService.search(params);
        _Http.sendWithCloseRepeat(response, _JSON.success(blogs));
    }

    /**
     * 获取博客总页数
     *
     * @param blog
     * @param cBeginTime
     * @param cEndTime
     * @param tBeginTime
     * @param tEndTime
     * @param minLike
     * @param maxLike
     * @param minLook
     * @param maxLook
     * @param minTalk
     * @param maxTalk
     * @param page
     * @param rows
     * @param field
     * @param order
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.BLOG_TOTAL_PAGES)
    public void getPages(Blog blog,
                         @RequestParam(value = "cBeginTime", required = false) String cBeginTime,
                         @RequestParam(value = "cEndTime", required = false) String cEndTime,
                         @RequestParam(value = "tBeginTime", required = false) String tBeginTime,
                         @RequestParam(value = "tEndTime", required = false) String tEndTime,
                         @RequestParam(value = "minLike", required = false) Integer minLike,
                         @RequestParam(value = "maxLike", required = false) Integer maxLike,
                         @RequestParam(value = "minLook", required = false) Integer minLook,
                         @RequestParam(value = "maxLook", required = false) Integer maxLook,
                         @RequestParam(value = "minTalk", required = false) Integer minTalk,
                         @RequestParam(value = "maxTalk", required = false) Integer maxTalk,
                         @RequestParam(value = "page", required = false) Integer page,
                         @RequestParam(value = "rows", required = false) Integer rows,
                         @RequestParam(value = "field", required = false) Integer field,
                         @RequestParam(value = "order", required = false) Integer order,
                         HttpServletResponse response,
                         HttpSession session) throws Exception {
        Map<String, Object> params = this.ParamsToMap(blog, cBeginTime, cEndTime, tBeginTime, tEndTime, minLike, maxLike, minLook, maxLook, minTalk, maxTalk, page, rows, field, order, session);
        Integer pages = blogService.getPages(params);
        _Http.send(response, _JSON.success(pages % rows != 0 ? (pages / rows + 1) : (pages / rows)));

    }


    //-------------------------------------------------------------------------------------------------------
    //封装参数到Map
    private Map<String, Object> ParamsToMap(Blog blog, String cBeginTime, String cEndTime, String tBeginTime, String tEndTime, Integer minLike, Integer maxLike, Integer minLook, Integer maxLook, Integer minTalk, Integer maxTalk, Integer page, Integer rows, Integer field, Integer order, HttpSession session) throws IllegalAccessException {
        SysUser user = (SysUser) session.getAttribute("cur_user");
        Map<String, Object> map = new HashMap<>();
        map.put("userId", 1);//TODO 测试 用户ID = 1
//        map.put("userId",user.getId());
        if (page!=null&&page!=0){
            map.put("page", (page - 1) * rows);
        }
        if (rows!=null&&rows!=0){
            map.put("rows", rows);
        }
        map.put("field", field);
        map.put("order", order);
        map.put("minLike", minLike);
        map.put("maxLike", maxLike);
        map.put("minLook", minLook);
        map.put("maxLook", maxLook);
        map.put("minTalk", minTalk);
        map.put("maxTalk", maxTalk);
        map.put("typeId", blog.getTypeId());
        map.put("dr", blog.getDr());
        map.put("keyWords", blog.getKeyWords());
        //主要对接收前端数据String类型差异进行处理
        map.put("title", blog.getTitle() == null || blog.getTitle().trim().equals("") ? null : blog.getTitle());
        map.put("cBeginTime", cBeginTime == null || cBeginTime.trim().equals("") ? null : cBeginTime);
        map.put("cEndTime", cEndTime == null || cEndTime.trim().equals("") ? null : cEndTime);
        map.put("tBeginTime", tBeginTime == null || tBeginTime.trim().equals("") ? null : tBeginTime);
        map.put("tEndTime", tEndTime == null || tEndTime.trim().equals("") ? null : tEndTime);
        return map;
    }

}
