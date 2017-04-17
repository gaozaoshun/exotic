package org.exotic.utils;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
/**
 * @author 高灶顺
 * @date 2016-12-2
 */
public class _Cookie {
    //保存cookie时的cookieName
    private final static String cookieDomainName = "blog";
    //加密cookie自定码
    private final static String webKey = "123456";
    //设置cookie有效期
    private final static long cookieMaxAge = 60 * 60 * 24 * 7 * 2;
    //保存Cookie到客户端
    public static void saveCookie(String username, String password, HttpServletResponse response) {
        //cookie的有效期
        long validTime = System.currentTimeMillis() + (cookieMaxAge * 5000);
        //MD5加密用户详细信息
        String cookieValueWithMd5 = getMD5(username + ":" + password + ":" + validTime + ":" + webKey);
        //将要被保存的完整的Cookie值
        String cookieValue = username + ":" + validTime + ":" + cookieValueWithMd5;
        //再一次对Cookie的值进行BASE64编码
        String cookieValueBase64 = new String(_Base64.encode(cookieValue.getBytes()));
        //开始保存Cookie
        Cookie cookie = new Cookie(cookieDomainName, cookieValueBase64);
        //存两年(这个值应该大于或等于validTime)
        cookie.setMaxAge(60 * 60 * 24 * 365 * 2);
        //cookie有效路径是网站根目录
        cookie.setPath("/");
        //向客户端写入
        response.addCookie(cookie);
    }
    //读取Cookie
    public static Map<String,Object> readCookie(HttpServletRequest request) throws IOException, ServletException{
        Map<String,Object> map = new HashMap<String,Object>();
        //根据cookieName取cookieValue
        Cookie cookies[] = request.getCookies();
        String cookieValue = null;
        if (cookies != null) {
            for (int i = 0; i < cookies.length;i ++){
                if (cookieDomainName.equals(cookies[i].getName())) {
                    cookieValue = cookies[i].getValue();
                    break;
                }
            }
        }
        //如果cookieValue为空,返回,
        if(cookieValue==null){
            map.put("code",0);
            return map;
        }
        //如果cookieValue不为空,才执行下面的代码
        //先得到的CookieValue进行Base64解码
        String cookieValueAfterDecode = new String(_Base64.decode(cookieValue), "utf-8");
        //对解码后的值进行分拆,得到一个数组,如果数组长度不为3,就是非法登陆
        String cookieValues[] = cookieValueAfterDecode.split(":");
        if(cookieValues.length!=3) {
            map.put("code",1);//非法登陆
            return map;
        }
        //判断是否在有效期内,过期就删除Cookie
        long validTimeInCookie = new Long(cookieValues[1]);
        if(validTimeInCookie<System.currentTimeMillis()){
            map.put("code",2);//你的Cookie已经失效, 请重新登陆
            return map;
        }
        //取出cookie中的用户名,并到数据库中检查这个用户名,
        String username = cookieValues[0];
        String md5ValueInCookie = cookieValues[2];
        //如果user返回不为空,就取出密码,使用用户名+密码+有效时间+ webSiteKey进行MD5加密
        if(!username.isEmpty()&&!md5ValueInCookie.isEmpty()){
            map.put("code",4);
            map.put("username",username);
            map.put("md5",md5ValueInCookie);
            return map;
        }else{
            map.put("code",3);
            return map;
        }
    }
    //用户注销时,清除Cookie,在需要时可随时调用-----------------------------------------------------
    public static void clearCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(cookieDomainName, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    //获取Cookie组合字符串的MD5码的字符串----------------------------------------------------------------
    public static String getMD5(String value) {
        String result = null;
        try {
            byte[] valueByte = value.getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(valueByte);
            result = toHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

    //将传递进来的字节数组转换成十六进制的字符串形式并返回
    private static String toHex(byte[] buffer) {
        StringBuffer sb = new StringBuffer(buffer.length * 2);
        for (int i = 0; i < buffer.length; i++) {
            sb.append(Character.forDigit((buffer[i] & 0xf0) >> 4, 16));
            sb.append(Character.forDigit(buffer[i] & 0x0f, 16));
        }
        return sb.toString();
    }
}

