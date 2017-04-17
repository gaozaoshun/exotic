package org.exotic.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Model转Map
 * @author 高灶顺
 * @date 2016-12-1
 */
public class _Map {

    public static Map<String,Object> toMap(Object ...models) throws IllegalAccessException {
        Map<String,Object> map = new HashMap<String, Object>(0);
        for (Object model : models){
            Field[] fields = model.getClass().getDeclaredFields();
            for(Field field:fields){
                //设置private属性值为可访问
                field.setAccessible(true);
                String key = field.getName();
                Object value = field.get(model);
                if (value==null) continue;
                map.put(key,value);
            }
        }
        return  map;
    }


}
