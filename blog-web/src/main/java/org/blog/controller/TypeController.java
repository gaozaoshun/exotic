package org.blog.controller;

import org.admin.model.SysUser;
import org.blog.constant.RouterConstant;
import org.blog.model.Type;
import org.blog.service.TypeService;
import org.exotic.utils._Http;
import org.exotic.utils._JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 高灶顺
 * @date 2016-11-28
 */
@Controller
public class TypeController {
    @Autowired
    private TypeService typeService;

    /**
     * 获取博客类别
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.BLOG_TYPE)
    public void type(Integer dr,
                     HttpServletResponse response,
                     HttpSession session) throws Exception{
        SysUser user = (SysUser) session.getAttribute("cur_user");
        List<Type> types = typeService.getTypes(dr);
        _Http.sendWithCloseRepeat(response, _JSON.success(types));
    }

    /**
     * 修改博客类别
     * @param type
     *              id 自增主键
     *              name 文章类型名称
     *              lv 数值越低优先级越高
     *              dr 软删除标志（0：未删 1：已删除）
     * @param response
     * @throws Exception
     */
    @RequestMapping(RouterConstant.TYPE_MODIFY)
    public void modify(Type type ,HttpServletResponse response)throws Exception{
        int flag = typeService.modify(type);
        _Http.send(response,_JSON.success(flag+"条文章类型更改成功！"));
    }

    /**
     * 添加博客类型
     * @param type
     * @param session
     * @param response
     * @throws Exception
     */
    @RequestMapping(RouterConstant.TYPE_ADD)
    public void add(Type type ,HttpSession session,HttpServletResponse response)throws Exception{
        int flag = typeService.add(type,session);
        _Http.send(response,_JSON.success(flag+"条文章类型添加成功！"));
    }

    /**
     * 删除博客类型
     * @param id
     * @param session
     * @param response
     * @throws Exception
     */
    @RequestMapping(RouterConstant.TYPE_DEL)
    public void del(Integer id ,HttpSession session,HttpServletResponse response)throws Exception{
        int flag = typeService.del(id);
        _Http.send(response,_JSON.success(flag+"条文章类型删除成功！"));
    }
}
