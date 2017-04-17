package org.admin.controller;

import org.admin.model.SysMenu;
import org.admin.model.SysRole;
import org.admin.model.SysUser;
import org.blog.constant.RouterConstant;
import org.exotic.utils.*;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author 高灶顺
 * @date 2016-12-6
 */
@Controller
public class UploadController {
    /**
     * 多图上传
     * @param images 文件名
     * @throws Exception
     */
    @RequestMapping(RouterConstant.UPLOAD)
    public void uploadImg(MultipartFile[] images,
                       HttpServletRequest request,
                       HttpServletResponse response,
                       HttpSession session) throws Exception {
        SysUser user = (SysUser) session.getAttribute("cur_user");
        //服务器对应用户的文件保存路径
        String URI = _Path.getRealURL(request)+"upload/"+user.getUsername();
        //上传
        String minPath = _Image.saveFile(URI,images,500);
        String headUrl = _Path.getRootURL(request)+minPath;
        if (minPath!=null) _Http.send(response, _JSON.success(headUrl));
        else _Http.send(response,_JSON.exception("保存图片出错！"));
    }
    /**
     * LayEdit图片上传接口
     * @param file
     * @throws Exception
     */
    @RequestMapping(RouterConstant.LAYEDIT)
    public void layeditByUpload(MultipartFile file,
                                HttpServletRequest request,
                                HttpServletResponse response,
                                HttpSession session)throws Exception{

        SysUser user = (SysUser) session.getAttribute("cur_user");
        //服务器对应用户的文件保存路径
        String URI = _Path.getRealURL(request)+"upload/"+user.getUsername();
        //上传
        String minPath = _Image.saveFile(URI,new MultipartFile[]{file},500);
        String headUrl = _Path.getRootURL(request)+minPath;

        Map<String,String> data = new HashMap<>();
        data.put("src",headUrl);
        if (minPath!=null){
            _Http.send(response, _JSON.definedReturn(0,"上传成功",data));
        } else{
            _Http.send(response, _JSON.definedReturn(1,"上传失败",data));
        }
    }
    /**
     * 音频上传
     * @param audios 音频文件
     * @throws Exception
     */
    @RequestMapping(RouterConstant.UPLOAD_AUDIO)
    public void uploadAudio(MultipartFile[] audios,
                       HttpServletRequest request,
                       HttpServletResponse response,
                       HttpSession session) throws Exception {
        SysUser user = (SysUser) session.getAttribute("cur_user");
        //服务器对应用户的文件保存路径
        String URI = _Path.getRealURL(request)+"upload/"+user.getUsername();
        //上传
        String url = _Audio.saveFile(URI,audios);
        if (url!=null)
            _Http.send(response, _JSON.success(url));
        else
            _Http.send(response,_JSON.exception("上传音频出错！"));
    }

}
