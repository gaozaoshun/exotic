package org.blog.controller;

import org.admin.model.TempSong;
import org.blog.constant.RouterConstant;
import org.blog.constant.TableField;
import org.blog.model.Recommend;
import org.blog.model.Songs;
import org.blog.service.RecommendService;
import org.blog.service.SongsService;
import org.exotic.utils._Http;
import org.exotic.utils._JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 高灶顺
 * @date 2016/12/19
 */
@Controller
public class SongController {
    @Autowired
    private SongsService songsService;

    /**
     * 获取歌曲总页数
     * @param rows
     * @param dr
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.SONG_PAGES)
    public void pages(
            Integer rows,
            @RequestParam(name = "dr",required = false) Integer dr,
            HttpServletResponse response,
            HttpSession session) throws Exception{
        Integer pages = songsService.getPages(rows,dr,session);
        _Http.send(response,_JSON.success(pages));
    }
    /**
     * 获取歌曲
     * @param page 当前页
     * @param rows  每页记录数
     * @param order  0:ASC 1:DESC
     * @param field  排序字段
     * @param dr    删除标志
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.SONG_GET)
    public  void get(
            @RequestParam(name = "page",required = false) Integer page,
            @RequestParam(name = "rows",required = false) Integer rows,
            Integer order,
            Integer field,
            @RequestParam(name = "dr",required = false) Integer dr,
            @RequestParam(name = "title",required = false) String title,
            HttpServletResponse response,
            HttpSession session) throws Exception{
        List<Songs> songses = songsService.get(page,rows,order,field,dr,title,session);
        _Http.sendWithCloseRepeat(response, _JSON.success(songses));
    }

    /**
     * 添加歌曲
     * @param song
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.SONG_ADD)
    public  void add(
            Songs song,
            HttpServletResponse response,
            HttpSession session) throws Exception{
        int flag = songsService.add(song,session);
        _Http.send(response, _JSON.success(flag+"条歌曲添加成功！"));
    }

    /**
     * 修改歌曲
     * @param song
     *             id
     *             img
     *             name
     *             author
     *             lv
     *             special
     *             likeNum
     *             url
     *             dr
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.SONG_MODIFY)
    public  void modify(
            Songs song,
            HttpServletResponse response,
            HttpSession session) throws Exception{
        int flag = songsService.modify(song,session);
        _Http.send(response, _JSON.success(flag+"条歌曲修改成功！"));
    }
    @RequestMapping(RouterConstant.SONG_MODIFY_LIST)
    @ResponseBody
    public  void modifylist(
            @RequestBody TempSong tempSong,
            HttpServletResponse response,
            HttpSession session) throws Exception{
        List<TempSong> list = tempSong.getList();
        Integer flag = songsService.modifyList(tempSong.getList());
        _Http.send(response,_JSON.success(flag+"条歌曲添加到播放列表！"));
    }
    /**
     * 撤销/恢复  歌曲
     * @param ids
     * @param dr
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.SONG_UPDATE_DR)
    public  void updateDr(
            @RequestParam("ids[]") Integer[] ids,
            Integer dr,
            HttpServletResponse response,
            HttpSession session) throws Exception{
        int flag = songsService.updateDr(ids,dr,session);
        if(dr == TableField.DR_UNDELETE)
            _Http.send(response, _JSON.success(flag+"条歌曲恢复成功！"));
        else
            _Http.send(response, _JSON.success(flag+"条歌曲撤销成功！"));
    }

    /**
     * 删除歌曲
     * @param ids
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping(RouterConstant.SONG_DELETE_REAL)
    public  void delete(
            @RequestParam("ids[]") Integer[] ids,
            HttpServletResponse response,
            HttpSession session) throws Exception{
        int flag = songsService.delete(ids,session);
        _Http.send(response, _JSON.success(flag+"条歌曲删除成功！"));
    }
}
