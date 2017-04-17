package org.blog.service;

import org.admin.model.TempSong;
import org.blog.model.Songs;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 高灶顺
 * @date 2016-11-26
 */
public interface SongsService {
    /**
     * 获取一首歌
     * @return
     */
    Songs getSong() throws Exception;

    List<Songs> get(Integer page, Integer rows, Integer order, Integer field, Integer dr, String title, HttpSession session)throws Exception;

    int add(Songs song, HttpSession session)throws Exception;

    int modify(Songs song, HttpSession session)throws Exception;

    int updateDr(Integer[] ids, Integer dr, HttpSession session)throws Exception;

    int delete(Integer[] ids, HttpSession session)throws Exception;

    Integer getPages(Integer rows, Integer dr, HttpSession session)throws Exception;

    Integer modifyList(List<TempSong> list)throws Exception;
}
