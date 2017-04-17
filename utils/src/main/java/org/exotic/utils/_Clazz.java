package org.exotic.utils;

import java.io.File;
import java.net.URL;

public class _Clazz {
    /**
     * 取得当前类所在的文件
     * @param clazz
     * @return File C:\\www\\exotic\\utils\\target\\classes\\org\\exotic\\utils\\_Clazz.class
     */
    public static File getClassFile(Class clazz){
        URL path = clazz.getResource(clazz.getName().substring(clazz.getName().lastIndexOf(".")+1)+".classs");
        if(path == null){
            String name = clazz.getName().replaceAll("[.]", "/");
            path = clazz.getResource("/"+name+".class");
        }
        return new File(path.getFile());
    }
    /**
     * 得到当前类的路径 
     * @param clazz
     * @return  C:\\www\\exotic\\utils\\target\\classes\\org\\exotic\\utils\\_Clazz.class
     */
    public static String getClassFilePath(Class clazz){
        try{
            return java.net.URLDecoder.decode(getClassFile(clazz).getAbsolutePath(),"UTF-8");
        }catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    
    /**
     * 取得当前类所在的ClassPath目录，比如tomcat下的classes路径
     * @param clazz
     * @return File C:\\www\\exotic\\utils\\target\\classes
     */
    public static File getClassPathFile(Class clazz){
        File file = getClassFile(clazz);
        for(int i=0,count = clazz.getName().split("[.]").length; i<count; i++)
            file = file.getParentFile();
        if(file.getName().toUpperCase().endsWith(".JAR!")){
            file = file.getParentFile();
        }
        return file;
    }
    /**
     * 取得当前类所在的ClassPath路径
     *
     * @param clazz
     * @return C:\\www\\exotic\\utils\\target\\classes
     */
    public static String getClassPath(Class clazz){
        try{
            return java.net.URLDecoder.decode(getClassPathFile(clazz).getAbsolutePath(),"UTF-8");
        }catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    
    public static void main(String[] args){
        System.out.println(System.currentTimeMillis());
//        System.out.println(getClassFilePath(_Clazz.class));
//        System.out.println(getClassPath(_Clazz.class));
//        File clazz = getClassFile(_Clazz.class);
//        System.out.println("class文件："+clazz);
//        File path = getClassPathFile(_Clazz.class);
//        System.out.println("编译下classes目录："+path);
    }
}