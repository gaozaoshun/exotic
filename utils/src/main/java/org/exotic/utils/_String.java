package org.exotic.utils;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * @author 高灶顺
 * @date 2016-11-16
 */
public class _String {
    /**
     * String[] -> List<String>
     * @param strs
     * @return
     */
    public static List<String> toList(String[] strs){
        List<String> list = new ArrayList<String>(0);
        if (strs == null && strs.length < 1)
            return list;
        for (int i = 0; i < strs.length; i++)
            list.add(strs[i]);
        return  list;
    }
    public static String[] split2Strs(String str,String delimiter){
        return str.split(delimiter);
    }
    public static List<String> split2List(String str,String delimiter){
        return toList(str.split(delimiter));
    }

}
