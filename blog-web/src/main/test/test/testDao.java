//package test;
//import java.util.List;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.blog.Dao.AdminMapper;
//import com.blog.Dao.Admin_AuthcMapper;
//import com.blog.Dao.Admin_MenuMapper;
//import com.blog.Dao.Admin_RoleMapper;
//import com.blog.Dao.AuthcMapper;
//import com.blog.Dao.BlogMapper;
//import com.blog.Dao.MenuMapper;
//import com.blog.Dao.Role_AuthcMapper;
//import com.blog.Dao.Role_MenuMapper;
//import com.blog.Dao.TokenMapper;
//import com.blog.Dao.TypeMapper;
//import com.blog.Dao.UserMapper;
//import com.blog.entity.Admin;
//import com.blog.entity.Authc;
//import com.blog.entity.BlogWithBLOBs;
//import com.blog.entity.Menu;
//import com.blog.entity.SysUser;
///*
// * DAO层单元测试
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"classpath:spring-mybatis.xml"})
//public class testDao {
//
//	@Autowired
//	private UserMapper userDao;
//	@Autowired
//	private TypeMapper typeDao;
//	@Autowired
//	private BlogMapper blogDao;
//	@Autowired
//	private TokenMapper tokenDao;
//	@Autowired
//	private AdminMapper adminDao;
//	@Autowired
//	private MenuMapper menuDao;
//	@Autowired
//	private Admin_AuthcMapper admin_AuthcMapper;
//	@Autowired
//	private Admin_RoleMapper admin_RoleMapper;
//	@Autowired
//	private AuthcMapper authcMapper;
//	@Autowired
//	private Role_MenuMapper role_MenuMapper;
//	@Autowired
//	private Role_AuthcMapper role_AuthcMapper;
//	@Autowired
//	private Admin_MenuMapper admin_MenuMapper;
//
//
//	/**
//	 * 测试MySQL存储过程
//	 */
//	@Test
//	public void testProc(){
//		List<BlogWithBLOBs> blogs = blogDao.callP3();
//		for (BlogWithBLOBs blog : blogs) {
//			System.out.println(blog);
//		}
//	}
//	@Test
//	public void t1() {
////		System.out.println(adminDao.selectByPrimaryKey(1));
////		System.out.println(menuDao.selectByPrimaryKey(1));
////		System.out.println(authcMapper.selectByPrimaryKey(1));
////		System.out.println(admin_AuthcMapper.selectByPrimaryKey(1));
////		System.out.println(admin_RoleMapper.selectByPrimaryKey(1));
////		System.out.println(role_MenuMapper.selectByPrimaryKey(1));
//		System.out.println(role_AuthcMapper.selectByPrimaryKey(1));
////		System.out.println(admin_MenuMapper.selectByPrimaryKey(1));
//	}
//
//}
