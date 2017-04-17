package org.blog.service;

import org.blog.model.Bloger;
import org.common.model.BeginEndTime;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 高灶顺
 * @date 2016-11-26
 */
public interface BlogerService {
    /**
     * 获取博主信息
     * @return
     */
    Bloger getBloger() throws Exception;

    List<Bloger> query(Integer page, Integer rows, Integer order, Integer field, Bloger bloger, BeginEndTime beginEndTime, HttpSession session) throws Exception;

    Integer modify(Bloger bloger,HttpSession session)throws Exception;

    Integer deleteFack(Integer id, HttpSession session)throws Exception;

    Integer deleteReal(Integer[] ids, HttpSession session)throws Exception;

    int add(Bloger bloger,HttpSession session)throws Exception;
}
