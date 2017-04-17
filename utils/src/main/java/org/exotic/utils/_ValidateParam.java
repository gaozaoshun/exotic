package org.exotic.utils;

import java.util.*;

/**
 * @author 高灶顺
 * @date 2016-12-3
 */
public class _ValidateParam {
    public static boolean isEmpty(Object ...params){
        for (Object param:params){
            if (param == null){
                return true;
            }
            if (param instanceof String && ((String) param).trim().equals("")){
                return true;
            }
            if ((param instanceof Collection)&&((List) param).size()==0){
                return true;
            }
            if ((param instanceof Map)&&((Map) param).size()==0){
                return true;
            }
            if (param instanceof Object[] && ((Object[])param).length==0){
                return true;
            }
        }
        return false;
    }
    public static void main(String[] a){
//        List<String> list = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("key",12);
//        list.add("132");
        System.out.println(_ValidateParam.isEmpty(map));
    }
}
