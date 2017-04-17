package org.admin.serviceImpl;
import org.admin.dao.SysUserMapper;
import org.admin.model.SysRole;
import org.admin.model.SysUser;
import org.admin.service.SysUserService;
import org.blog.constant.RouterConstant;
import org.blog.constant.TableField;
import org.common.model.BeginEndTime;
import org.exotic.utils._Map;
import org.exotic.utils._Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 高灶顺
 * @date 2016-11-30
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper userDao;
    private SysUser curUser;//当前登录用户
    @Override
    public int login(String username, String password)  throws Exception {
        SysUser user = null;
        try {
            user = userDao.queryByUserName(username);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        if (user!=null){
            String pwd = user.getPassword();
            if (!pwd.isEmpty()&&pwd.equals(password)){
                this.curUser = user;
                return 0;//校验通过
            }else {
                return 2;//密码错误
            }
        }else {
            return 1;//用户名不存在
        }
    }

    @Override
    public SysUser loginUser() throws Exception  {
        return curUser;
    }

    @Override
    public SysUser modifyUser(SysUser sysUser, HttpSession session) throws Exception {
        System.out.println("****"+sysUser);
        SysUser user = (SysUser) session.getAttribute("cur_user");
        if (user.getId() == sysUser.getId()){
            sysUser.setTs(new Date());
            int flag = userDao.modifyUser(sysUser);
            if (flag>0){
                SysUser curUser = userDao.queryByUserName(user.getUsername());
                session.removeAttribute("cur_user");
                session.setAttribute("cur_user",curUser);
                return  curUser;
            }
            return null;
        }else{
            return null;
        }
    }

    @Override
    public String logoutUser(HttpServletRequest request, HttpSession session)  throws Exception{
        session.removeAttribute("cur_user");
        String loginUrl = _Path.getRootURL(request)+ RouterConstant.ADMIN_PAGE_LOGIN;
        return loginUrl;
    }

    @Override
    public List<SysUser> query(Integer page, Integer rows, Integer order, Integer field, SysUser user, BeginEndTime beginEndTime, String q,HttpSession session) throws Exception {
        Map<String,Object> map = _Map.toMap(user,beginEndTime);
        if (page!=null&&rows!=null){
            page = page == 0 ? 1:page;
            page = (page-1)*rows ;
        }
        map.put("page",page);
        map.put("rows",rows);
        map.put("q",q);
        map.put("order",order);
        map.put("field",field);
        return userDao.query(map);
    }

    @Override
    public int add(SysUser user, HttpSession session) throws Exception {
        user.setTs(new Date());
        user.setCreateTime(new Date());
        user.setDr(TableField.DR_UNDELETE);
        return userDao.insert(user);
    }

    @Override
    public int modify(SysUser user, HttpSession session) throws Exception {
        user.setTs(new Date());
        return userDao.modify(user);
    }

    @Override
    public int updateDr(Integer[] ids, Integer dr, HttpSession session) throws Exception {
        return userDao.updateDr(ids,dr,new Date());
    }

    @Override
    public int delete(Integer[] ids, HttpSession session) throws Exception {
        return userDao.delete(ids);
    }

    @Override
    public Integer getPages(Integer rows, Integer dr, String q, HttpSession session) throws Exception {
        Integer count = userDao.getCount(dr,q);
        if (count!=null){
            return count%rows==0?count/rows:count/rows+1;
        }
        return 0;
    }

    @Override
    public int updateDrV2(SysUser list, HttpSession session) throws Exception {
        int count = 0;
        for (SysUser user : list.getUsers()){
            user.setTs(new Date());
            count += userDao.modifyDr(user);
        }
        return count;
    }

    @Override
    public List<SysRole> getCurRoles(Integer userId, Integer page, Integer rows, HttpSession session) throws Exception {
        if (userId==null){
            SysUser user = (SysUser) session.getAttribute("cur_user");
            userId = user.getId();
        }
        if (page!=null && rows!=null){
            page = page == 0 ? 1:page;
            page = (page-1) * rows;
        }
        return userDao.queryRolesById(page,rows,userId);
    }

    @Override
    public Integer saveUserRoles(Integer userId, Integer[] ids, HttpSession session) throws Exception {
        if (userId==null){
            SysUser user = (SysUser)session.getAttribute("cur_user");
            userId = user.getId();
        }
        userDao.deleteUserRoles(userId);
        return userDao.saveUserRoles(userId,ids);
    }

    @Override
    public Integer delUserRoles(Integer userId, Integer roleId, HttpSession session) throws Exception {
        return userDao.deleteRole(userId,roleId);
    }
}
