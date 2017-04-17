package org.blog.service;

import org.blog.model.Type;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 高灶顺
 * @date 2016-11-26
 */
public interface TypeService {
    List<Type> getTypes(Integer dr) throws Exception;
    int modify(Type type)throws Exception;

    int add(Type type, HttpSession session)throws Exception;

    int del(Integer id)throws Exception;
}
