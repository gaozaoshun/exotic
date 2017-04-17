package org.blog.service;

import org.blog.model.Photos;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @author 高灶顺
 * @date 2016-11-26
 */
public interface PhotosService {
    List<Photos> getPhotos() throws Exception;

    int add(Photos photos, HttpSession session)throws Exception;

    List<Photos> query(Integer page, Integer rows, Integer order, Integer field, Integer dr, String name, HttpSession session)throws Exception;

    int modify(Photos photos, HttpSession session)throws Exception;

    int updateDr(Integer[] ids, Integer dr, Date ts, HttpSession session)throws Exception;

    int delete(Integer[] ids, HttpSession session)throws Exception;

    Integer getCount(Integer dr,Integer rows)throws Exception;
}
