package org.blog.serviceImpl;

import org.admin.model.SysUser;
import org.blog.constant.TableField;
import org.blog.dao.PhotosMapper;
import org.blog.model.Photos;
import org.blog.service.PhotosService;
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
public class PhotosServiceImpl implements PhotosService {
    @Autowired
    private PhotosMapper photosDao;

    @Override
    public List<Photos> getPhotos() throws Exception{
        return photosDao.query(0,9, TableField.ORDER_ASC,TableField.LV,TableField.DR_UNDELETE,null);
    }

    @Override
    public int add(Photos photos, HttpSession session) throws Exception {
        SysUser user = (SysUser) session.getAttribute("cur_user");
        photos.setTs(new Date());
        photos.setCreateTime(new Date());
        photos.setCreateUser(user.getId());
        photos.setDr(TableField.DR_UNDELETE);
        return photosDao.add(photos);
    }

    @Override
    public List<Photos> query(Integer page, Integer rows, Integer order, Integer field, Integer dr, String name, HttpSession session) {
        page = page==0?1:page;
        page = (page-1)*rows;
        return photosDao.query(page,rows,order,field,dr,name);
    }

    @Override
    public int modify(Photos photos, HttpSession session) throws Exception {
        photos.setTs(new Date());
        return photosDao.modify(photos);
    }

    @Override
    public int updateDr(Integer[] ids, Integer dr, Date date, HttpSession session) throws Exception{
        return photosDao.updateDr(ids,dr,date);
    }

    @Override
    public int delete(Integer[] ids, HttpSession session) throws Exception {
        return photosDao.delete(ids);
    }

    @Override
    public Integer getCount(Integer dr,Integer rows) throws Exception {
        Integer counts =  photosDao.count(dr);
        if (counts!=null&&counts!=0){
            return counts%rows==0?counts/rows:counts/rows+1;
        }
        return 0;
    }


}
