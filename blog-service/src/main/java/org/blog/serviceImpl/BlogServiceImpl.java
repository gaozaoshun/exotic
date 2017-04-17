package org.blog.serviceImpl;
import org.admin.model.SysUser;
import org.blog.constant.PageConstant;
import org.blog.constant.TableField;
import org.blog.dao.BlogMapper;
import org.blog.dao.CommentMapper;
import org.blog.dao.RecommendMapper;
import org.blog.model.Blog;
import org.blog.service.BlogService;
import org.exotic.utils.PageUtil;
import org.exotic.utils._Map;
import org.exotic.utils._Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 高灶顺
 * @date 2016-11-26
 */
@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogMapper blogDao;
    @Autowired
    private CommentMapper commentDao;
    @Autowired
    private RecommendMapper recommendDao;

    private String pageBar = null;

//    @Cacheable("getLatesdBlogs")
    @Override
    public List<Blog> getLatesdBlogs() throws Exception {
        return blogDao.query(0,5,TableField.ORDER_DESC,TableField.CreateTime,TableField.DR_UNDELETE);
    }
    @Override
    public List<Blog> getHotBlogs() throws Exception {
        return blogDao.query(0,9,TableField.ORDER_DESC,TableField.Blog_LookNum,TableField.DR_UNDELETE);
    }
//    @Cacheable("getDetail")
    @Override
    public Blog getDetail(int id)  throws Exception {
        Blog blog = blogDao.queryById(id,TableField.DR_UNDELETE);
        if (blog!=null){
            blog.setLookNum(blog.getLookNum()+1);
            blogDao.updateByPrimaryKey(blog);
        }
        return blog;
    }
//    @Cacheable("getAboutByType")
    @Override
    public List<Blog> getAboutByType(Integer typeId) throws Exception  {
        return blogDao.queryByType(0,6,typeId,TableField.ORDER_DESC,TableField.CreateTime,TableField.DR_UNDELETE);
    }
//    @Cacheable("getBlogsByType")
    @Override
    public List<Blog> getBlogsByType(int page, int rows, int typeId, HttpServletRequest request) throws Exception {
        System.out.println("-------测试getBlogsByType------");
        Integer count = blogDao.countByType(typeId);
        PageUtil pageUtil = new PageUtil(count,page,rows);
        String url = _Path.getRootURL(request)+"/blogs/list?typeId="+typeId+"&rows=6&page=";
        this.pageBar = appendPageBar(pageUtil,url);
        return blogDao.queryByType(pageUtil.getStartPos(),pageUtil.getPageSize(),typeId,TableField.ORDER_DESC,TableField.CreateTime,TableField.DR_UNDELETE);
    }
//    @Cacheable("getSearchByQ")
    @Override
    public List<Blog> getSearchByQ(String q, int page, int rows, HttpServletRequest request) throws Exception {
        Integer count = blogDao.countByQ(q);
        PageUtil pageUtil = new PageUtil(count,page,rows);
        String url = _Path.getRootURL(request)+"/blogs/search?q="+q+"&rows=6&page=";
        this.pageBar = appendPageBar(pageUtil,url);
        return blogDao.queryByQ(q,pageUtil.getStartPos(),pageUtil.getPageSize(),TableField.ORDER_DESC,TableField.CreateTime,TableField.DR_UNDELETE);
    }
    @Override
    public List<Blog> search(Map<String, Object> params) throws Exception {
        return blogDao.queryByAll(params);
    }

//    @Cacheable("getBlogsByPage")
    @Override
    public List<Blog> getBlogsByPage(SysUser user, Map<String, Object> pageMsg)  throws Exception {
        Integer index = ((Integer)(pageMsg.get("curr"))-1)*(Integer)pageMsg.get("pages");
        String title = pageMsg.get("title")==null?null:(String) pageMsg.get("title");
        String beginTime = pageMsg.get("beginTime")==null?null:(String)pageMsg.get("beginTime");
        String endTime = pageMsg.get("endTime")==null?null:(String)pageMsg.get("endTime");
        Integer dr = pageMsg.get("dr")==null?null:(Integer)pageMsg.get("dr");
        Integer typeId = pageMsg.get("typeId")==null?null:(Integer)pageMsg.get("typeId");
        return blogDao.queryByWhereAndUser(
                user.getId(),
                index,
                (Integer)pageMsg.get("pages"),
                title,
                beginTime,
                endTime,
                dr,
                typeId);
    }
//    @Cacheable("getPages")
    @Override
    public Integer getPages(Map<String, Object> params) throws Exception{
        return blogDao.countAll(params);
    }
//    @Cacheable("getBlogByIdAndUser")
    @Override
    public Blog getBlogByIdAndUser(int id, HttpSession session) {
        SysUser user = (SysUser) session.getAttribute("cur_user");
        //TODO 测试
        Blog blog = blogDao.queryByIdAndUser(id,1,TableField.DR_UNDELETE);
        return blog;
    }
//    @CacheEvict(value = { "getLatesdBlogs", "getDetail","getAboutByType","getBlogsByType","getSearchByQ","getBlogsByPage","search","getPages","getBlogByIdAndUser"}, allEntries = true)
    @Override
    public int add(String title, String cover, String summary,String content, String keyWords, int typeId, HttpSession session)  throws Exception {
        Blog blog = this.processBlog(title,cover,summary,content,keyWords,typeId,session);
        return blogDao.insert(blog);
    }
//    @CacheEvict(value = { "getLatesdBlogs", "getDetail","getAboutByType","getBlogsByType","getSearchByQ","getBlogsByPage","search","getPages","getBlogByIdAndUser"}, allEntries = true)
    @Override
    public int delete(Integer[] ids, HttpSession session) throws Exception {
        SysUser user = (SysUser) session.getAttribute("cur_user");
        //删除当前登陆用户的博客
        Integer flagA = blogDao.deleteByPksAndUserId(ids,user.getId());
        //删除Comment表对应的记录
        Integer flagB = commentDao.batchDeleteByBlogId(ids);
        //删除Recommend表对应的记录
        Integer flagC = recommendDao.batchDeleteByBlogId(ids);
        return flagA;
    }
//    @CacheEvict(value = { "getLatesdBlogs", "getDetail","getAboutByType","getBlogsByType","getSearchByQ","getBlogsByPage","search","getPages","getBlogByIdAndUser"}, allEntries = true)
    @Override
    public int updateByDrAndUser(Integer[] ids, HttpSession session) throws Exception {
        SysUser user = (SysUser) session.getAttribute("cur_user");
        //更新属于自己的博客的删除标志
        Integer flag = blogDao.updateByDrAndUser(ids,TableField.DR_DELETE,user.getId(), new Date());
        //更新记录数
        return flag;
    }
//    @CacheEvict(value = { "getLatesdBlogs", "getDetail","getAboutByType","getBlogsByType","getSearchByQ","getBlogsByPage","search","getPages","getBlogByIdAndUser"}, allEntries = true)
    @Override
    public int updateByBlogAndUser(Blog blog, HttpSession session)  throws Exception{
        SysUser user = (SysUser) session.getAttribute("cur_user");
        blog.setTs(new Date());//更新时间戳
        blog.setCreateUser(user.getId());//用于SQL验证是否为当前用户的博文
        int flag = blogDao.updateByBlogAndUser(blog);
        //更新记录数
        return flag;
    }
//    @CacheEvict(value = { "getLatesdBlogs", "getDetail","getAboutByType","getBlogsByType","getSearchByQ","getBlogsByPage","search","getPages","getBlogByIdAndUser"}, allEntries = true)
    @Override
    public int repByDrAndUser(Integer[] ids, HttpSession session) {
        SysUser user = (SysUser) session.getAttribute("cur_user");
        //更新属于自己的博客的删除标志
        Integer flag = blogDao.updateByDrAndUser(ids,TableField.DR_UNDELETE,user.getId(), new Date());
        //更新记录数
        return flag;
    }

    //------------------类内方法---------------------
    @Override
    public Map<String, Object> queryPageMsg(Integer curr, SysUser user, String title, String beginTime, String endTime, Integer dr, Integer typeId) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        title = (title==null||title.trim().equals(""))?null:title;
        beginTime = (beginTime==null||beginTime.trim().equals(""))?null:beginTime;
        endTime = (endTime==null||endTime.trim().equals(""))?null:endTime;
        Integer last = blogDao.countByWhereAndUser(user.getId(),title,beginTime,endTime,dr,typeId);
        map.put("pages", PageConstant.ADMIN_ROWS);//分页数
        map.put("curr",curr==null?1:curr);//当前页
        map.put("groups",last>PageConstant.ADMIN_GROUPS?PageConstant.ADMIN_GROUPS:last);//连续分页数
        map.put("last",last);//总页数
        map.put("title",title);//查询条件
        map.put("beginTime",beginTime);//查询条件
        map.put("endTime",endTime);//查询条件
        map.put("dr",dr);//查询条件
        map.put("typeId",typeId);//查询条件
        return map;
    }
    /**
     * 分页导航栏
     * 最多显示5页
     * @param page_util 处理分页信息
     */
    private String appendPageBar(PageUtil page_util, String url) throws Exception{
        StringBuffer pagebar = new StringBuffer();
        Integer maxpage = page_util.getTotalPageCount();
        Integer page_index = page_util.getPageNow();
        //判断是否为首页
        if (page_index==0)
            pagebar.append("<li class='disabled'><a href='javascript:(0);'>&laquo;</a></li>");
        else
            pagebar.append("<li><a href='"+(url+(page_index-1))+"'>&laquo;</a></li>");
        //中间部分-最多显示5页
        if (maxpage<5) {
            for(int i=0;i<maxpage;i++){
                if(i==page_index)
                    pagebar.append("<li class='active'><a href='"+(url+i)+"'>"+(i+1)+"</a></li>");
                else
                    pagebar.append("<li><a href='"+(url+i)+"'>"+(i+1)+"</a></li>");
            }
        }else{
            int start=page_index-2;
            int end=start+4;
            if (start<0) {
                start=0;
                end=start+4;
            }
            if ((maxpage-1)<end)
                end=(maxpage-1);
            for(int i=start;i<=end;i++){
                if(i==page_index)
                    pagebar.append("<li class='active'><a href='"+(url+i)+"'>"+(i+1)+"</a></li>");
                else
                    pagebar.append("<li><a href='"+(url+i)+"'>"+(i+1)+"</a></li>");
            }
        }
        //判断是否为尾页
        if (page_index==(maxpage-1))
            pagebar.append("<li class='disabled'><a href='javascript:(0);'>&raquo;</a></li> ");
        else
            pagebar.append("<li><a href='"+(url+(page_index+1))+"'>&raquo;</a></li> ");
        return pagebar.toString();
    }
    //获取分页栏HTML代码
    public String getPageBar() throws Exception {
        return this.pageBar;
    }
    //封装Blog对象
    private Blog processBlog(String title, String cover, String summary, String content, String keyWords, int typeId, HttpSession session) {
        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setCover(cover);
        blog.setSummary(summary);
        blog.setContent(content);
        blog.setLikeNum(0);
        blog.setLookNum(0);
        blog.setTalkNum(0);
        blog.setKeyWords(keyWords);
        blog.setTypeId(typeId);
        blog.setCreateUser(((SysUser)session.getAttribute("cur_user")).getId());
        blog.setCreateTime(new Date());
        blog.setDr(TableField.DR_UNDELETE);
        blog.setTs(new Date());
        return blog;
    }

}
