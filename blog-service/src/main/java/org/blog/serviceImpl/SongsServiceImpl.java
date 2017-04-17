package org.blog.serviceImpl;
import org.admin.model.SysUser;
import org.admin.model.TempSong;
import org.blog.constant.TableField;
import org.blog.dao.SongsMapper;
import org.blog.model.Songs;
import org.blog.service.SongsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @author 高灶顺
 * @date 2016-11-26
 */
@Service
public class SongsServiceImpl implements SongsService {
    @Autowired
    private SongsMapper songsDao;

    public Songs getSong() throws Exception{
        return songsDao.queryOrderByLv(TableField.ORDER_ASC,TableField.DR_UNDELETE);
    }

    @Override
    public List<Songs> get(Integer page, Integer rows, Integer order, Integer field, Integer dr, String title, HttpSession session) throws Exception {
        if (page!=null&&rows!=null){
            page = page ==0?1:page;
            page = (page-1)*rows;
        }
        return songsDao.query(page,rows,order,field,dr,title);
    }

    @Override
    public int add(Songs song, HttpSession session) throws Exception {
        SysUser user = (SysUser) session.getAttribute("cur_user");
        song.setCreateUser(user.getId());
        song.setTs(new Date());
        song.setCreateTime(new Date());
        song.setDr(TableField.DR_UNDELETE);
        return songsDao.insert(song);
    }

    @Override
    public int modify(Songs song, HttpSession session) throws Exception {
        SysUser user = (SysUser) session.getAttribute("cur_user");
        song.setTs(new Date());
        return songsDao.modify(song);
    }

    @Override
    public int updateDr(Integer[] ids, Integer dr, HttpSession session) throws Exception {
        SysUser user = (SysUser) session.getAttribute("cur_user");

        return songsDao.updateDr(ids,dr,new Date());
    }

    @Override
    public int delete(Integer[] ids, HttpSession session) throws Exception {
        return songsDao.delete(ids);
    }

    @Override
    public Integer getPages(Integer rows, Integer dr, HttpSession session) throws Exception {
        Integer count = songsDao.getCount(dr);
        if (count!=null){
            return count%rows==0?count/rows:count/rows+1;
        }
        return 0;
    }

    @Override
    public Integer modifyList(List<TempSong> list) throws Exception {
        for(TempSong tempSong:list){
            songsDao.orderLV(tempSong.getId(),tempSong.getLv(),new Date());
        }
        return list.size();
    }
}
