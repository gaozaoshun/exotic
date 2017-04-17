package org.blog.constant;

/**
 * HTTP路由
 * @author 高灶顺
 * @date 2016-12-8
 */
public class RouterConstant {
    //--------前台---ModelAndView---Freemarker----------------//
    //MV-博客首页
    public static final String BLOG_INDEX = "blogs/index";
    //MV-显示博文列表页
    public static final String BLOG_LIST = "blogs/list";
    //MV-显示博客详情
    public static final String BLOG_DETAIL = "blogs/detail";
    //MV-博文查询（标题）
    public static final String BLOG_SEARCH = "blogs/search";
    //-------后台管理---Restful API 返回JSON --- 单页面--------//
    //----------------用户登陆---------------------//
    //Page-后台登陆页面
    public static final String ADMIN_PAGE_LOGIN = "/back/login.html";
    //OutputStream-图片验证码
    public static final String ADMIN_CODE = "admin/code";
    //JSON-登陆操作
    public static final String ADMIN_LOGIN = "admin/login";
    //----------------后台首页---------------------//
    //Page-后台首页路径
    public static final String ADMIN_PAGE_INDEX = "/back/index.html";
    //JSON-初始化菜单
    public static final String ADMIN_MENUS_INIT = "admin/init_menu";
    //JSON-更新前端数据（initCompoment）
    public static final String ADMIN_DATA_INIT = "admin/init_data";
    //JSON-系统信息初始化
    public static final String ADMIN_SYSTEM_INFO = "admin/system/info";
    //----------------博客管理---------------------//
    //JSON-多图上传
    public static final String UPLOAD = "admin/upload/image";
    //JSON-多图上传
    public static final String LAYEDIT = "admin/upload/layedit";
    //JSON-音频上传
    public static final String UPLOAD_AUDIO = "admin/upload/audio";
    //JSON-发布博文
    public static final String BLOG_PUBLISH = "blogs/publish";
    //JSON-获取博客类别
    public static final String BLOG_TYPE = "blogs/type";
    //JSON-获取博客总页数
    public static final String BLOG_TOTAL_PAGES = "blogs/pages";
    //JSON-查询博客--所有查询条件--分页
    public static final String BLOG_QUERY = "blogs/query";
    //JSON-获取指定ID的博客
    public static final String BLOG_GET_ID = "blogs/get";
    //JSON-修改博客
    public static final String BLOG_MODIFY = "blogs/modify";
    //JSON-删除博客-可批量--数据库删除
    public static final String BLOG_DELETE_REAL = "blogs/delete/real";
    //JSON-删除博客-可批量--软删除
    public static final String BLOG_DELETE_FACK = "blogs/delete/fack";
    //JSON-恢复博客-可批量--软删除
    public static final String BLOG_UNDELETE = "blogs/undelete";
    //-------------当前登录用户信息-------------------
    //JSON-当前登录用户信息
    public static final String SYS_CUR_USER = "sysUser/logined";
    //JSON-修改登陆用户信息
    public static final String SYS_CUR_USER_MODIFY = "sysUser/modify";
    //JSON-注销登录用户
    public static final String SYS_CUR_USER_LOGOUT= "sysUser/logout";
    //-------------博客类别-------------------
    //JSON-修改博客类型
    public static final String TYPE_MODIFY = "types/modify";
    //JSON-添加博客类型
    public static final String TYPE_ADD = "types/add";
    //JSON-删除博客类型
    public static final String TYPE_DEL ="types/delete";
    //------------- 文章评论-------------------
    //JSON-指定文章的评论列表总页数
    public static final String COMMENT_PAGES = "comments/pages";
    //JSON-获取指定文章评论
    public static final String COMMENT_GET = "comments/get";
    //JSON-发表评论
    public static final String COMMENT_PUBLISH = "comments/publish";
    //JSON-删除评论--数据库删除
    public static final String COMMENT_DELETE_REAL = "comments/delete/real";
    //JSON-撤销/恢复评论
    public static final String COMMENT_DR = "comments/dr";
    //------------- 博主信息----------------------
    //JSON-获取当前用户的博主信息
    public static final String BLOGER_GET = "bloger/get";
    //JSON-添加当前用户博主信息
    public static final String BLOGER_ADD = "bloger/add";
    //JSON-更改博主信息
    public static final String BLOGER_MODIFY= "bloger/modify";
    //JSON-删除博主信息--软删除
    public static final String BLOGER_DELETE_FACK = "bloger/delete/fack";
    //JSON-删除博主信息--数据库删除
    public static final String BLOGER_DELETE_REAL = "bloger/delete/real";
    //------------- 友情链接----------------------
    //JSON-获取友情链接
    public static final String FriendLink_GET = "friendlink/get";
    //JSON-添加友情链接
    public static final String FriendLink_ADD = "friendlink/add";
    //JSON-更改友情链接
    public static final String FriendLink_MODIFY = "friendlink/modify";
    //JSON-撤销/恢复 友情链接
    public static final String FriendLink_UPDATE_DR  = "friendlink/dr";
    //JSON-删除友情链接
    public static final String FriendLink_DELETE_REAL  = "friendlink/delete";
    //------------- 网站菜单----------------------
    //JSON-获取网站菜单
    public static final String MENU_GET = "menu/get";
    //JSON-添加网站菜单
    public static final String MENU_ADD = "menu/add";
    //JSON-更改网站菜单
    public static final String MENU_MODIFY = "menu/modify";
    //JSON-撤销/恢复网站菜单
    public static final String MENU_UPDATE_DR  = "menu/dr";
    //JSON-删除网站菜单
    public static final String MENU_DELETE_REAL  = "menu/delete";
    //------------- 相册管理----------------------
    //JSON-获取相册
    public static final String PHOTOS_GET = "photos/get";
    //JSON-添加相册
    public static final String PHOTOS_ADD = "photos/add";
    //JSON-更改相册
    public static final String PHOTOS_MODIFY = "photos/modify";
    //JSON-撤销/恢复相册
    public static final String PHOTOS_UPDATE_DR  = "photos/dr";
    //JSON-删除相册
    public static final String PHOTOS_DELETE_REAL  = "photos/delete";
    //-------------推荐博文----------------------
    //JSON-获取推荐博文总页数
    public static final String RECOMMEND_PAGES = "recommend/pages";
    //JSON-获取推荐博文
    public static final String RECOMMEND_GET = "recommend/get";
    //JSON-添加推荐博文
    public static final String RECOMMEND_ADD = "recommend/add";
    //JSON-更改推荐博文
    public static final String RECOMMEND_MODIFY = "recommend/modify";
    //JSON-撤销/恢复推荐博文
    public static final String RECOMMEND_UPDATE_DR  = "recommend/dr";
    //JSON-删除推荐博文
    public static final String RECOMMEND_DELETE_REAL  = "recommend/delete";
    //------------ 歌曲管理----------------------
    //JSON-获取歌曲总页数
    public static final String SONG_PAGES = "song/pages";
    //JSON-获取歌曲
    public static final String SONG_GET = "song/get";
    //JSON-添加歌曲
    public static final String SONG_ADD = "song/add";
    //JSON-更改歌曲
    public static final String SONG_MODIFY = "song/modify";
    //JSON-歌曲排序
    public static final String SONG_MODIFY_LIST = "song/modify/list";
    //JSON-撤销/恢复歌曲
    public static final String SONG_UPDATE_DR  = "song/dr";
    //JSON-删除歌曲
    public static final String SONG_DELETE_REAL  = "song/delete";
    //------------- 名言管理----------------------
    //JSON-名言总页数
    public static final String WISDOM_PAGES = "wisdom/pages";
    //JSON-获取名言
    public static final String WISDOM_GET = "wisdom/get";
    //JSON-添加名言
    public static final String WISDOM_ADD = "wisdom/add";
    //JSON-更改名言
    public static final String WISDOM_MODIFY = "wisdom/modify";
    //JSON-撤销/恢复名言
    public static final String WISDOM_UPDATE_DR  = "wisdom/dr";
    //JSON-删除名言
    public static final String WISDOM_DELETE_REAL  = "wisdom/delete";
    //------------- 权限URI----------------------
    //JSON-获取权限
    public static final String SYS_AUTHO_GET = "sys/autho/get";
    //JSON-添加权限
    public static final String SYS_AUTHO_ADD = "sys/autho/add";
    //JSON-更改权限
    public static final String SYS_AUTHO_MODIFY = "sys/autho/modify";
    //JSON-撤销/恢复 权限
    public static final String SYS_AUTHO_UPDATE_DR  = "sys/autho/dr";
    //JSON-删除权限
    public static final String SYS_AUTHO_DELETE_REAL  = "sys/autho/delete";
    //------------- 系统菜单----------------------
    //JSON-获取系统菜单的总页数
    public static final String SYS_MENU_PAGES = "sys/menu/pages";
    //JSON-获取顶级系统菜单
    public static final String SYS_MENU_GETSUPPER = "sys/menu/getSuper";
    //JSON-获取系统菜单
    public static final String SYS_MENU_GET = "sys/menu/get";
    //JSON-添加系统菜单
    public static final String SYS_MENU_ADD = "sys/menu/add";
    //JSON-更改系统菜单
    public static final String SYS_MENU_MODIFY = "sys/menu/modify";
    //JSON-撤销/恢复 系统菜单
    public static final String SYS_MENU_UPDATE_DR  = "sys/menu/dr";
    //JSON-删除系统菜单
    public static final String SYS_MENU_DELETE_REAL  = "sys/menu/delete";
    //------------- 角色管理----------------------
    //JSON-获取查询总页数
    public static final String SYS_ROLE_PAGES = "sys/role/pages";
    //JSON-获取角色
    public static final String SYS_ROLE_GET = "sys/role/get";
    //JSON-添加角色
    public static final String SYS_ROLE_ADD = "sys/role/add";
    //JSON-更改角色
    public static final String SYS_ROLE_MODIFY = "sys/role/modify";
    //JSON-撤销/恢复 角色
    public static final String SYS_ROLE_UPDATE_DR  = "sys/role/dr";
    public static final String SYS_ROLE_UPDATE_DR_V2 = "sys/role/dr/v2";
    //JSON-删除角色
    public static final String SYS_ROLE_DELETE_REAL  = "sys/role/delete";
    //JSON-获取角色对应的权限
    public static final String SYS_ROLE_GETAUTHO = "sys/role/autho/get";
    //JSON-保存指定角色的权限
    public static final String SYS_ROLE_SAVEAUTHOS = "sys/role/autho/save";
    //------------- 后台用户管理----------------------
    //JSON-获取后台用户的总页数
    public static final String SYS_USER_PAGES = "sys/user/pages";
    //JSON-获取后台用户
    public static final String SYS_USER_GET = "sys/user/get";
    //JSON-添加后台用户
    public static final String SYS_USER_ADD = "sys/user/add";
    //JSON-更改后台用户
    public static final String SYS_USER_MODIFY = "sys/user/modify";
    //JSON-撤销/恢复 后台用户
    public static final String SYS_USER_UPDATE_DR  = "sys/user/dr";
    //JSON-撤销/恢复 后台用户-V2
    public static final String SYS_USER_UPDATE_DR_V2  = "sys/user/dr/v2";
    //JSON-删除后台用户
    public static final String SYS_USER_DELETE_REAL  = "sys/user/delete";
    //JSON-获取指定用户的角色
    public static final String SYS_USER_ROLES_GET = "sys/user/role/get";
    //JSON-保存指定用户的角色
    public static final String SYS_USER_ROLES_SAVE = "sys/user/role/save";
    //JSON-删除指定用户的角色
    public static final String SYS_USER_ROLES_DELETE = "sys/user/role/del";


    //-----------------------用户管理---------------------------
    //JSON-查询用户
    public static final String USER_GET = "user/get";



}
