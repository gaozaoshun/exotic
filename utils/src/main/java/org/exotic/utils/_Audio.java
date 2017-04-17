package org.exotic.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 音频上传工具
 * @author 高灶顺
 * @date 2016/12/29
 */
public class _Audio {
    public static String saveFile(String url, MultipartFile[] files) throws IOException {
        StringBuffer fileURI = new StringBuffer();
        if (files == null || files.length < 1) {
            return null;
        }
        // 创建文件夹
        String dateStr = new SimpleDateFormat("yyyy_MM_dd").format(new Date());
        url = url + "/" + dateStr;
        File parentPathDir = new File(url);
        if (!parentPathDir.exists()) {
            parentPathDir.mkdirs();// ../upload/用户名/2016_12_06
        }
        for (int i = 0; i < files.length; i++) {
            String fileName = files[i].getOriginalFilename();
            // 保存原图文件到 ../upload/用户名/2016_12_06/11_28_35_1.jpg
            String timeStr = new SimpleDateFormat("HH_mm_ss_").format(new Date());
            String newFileName = (timeStr + (i + 1) + ".") + _Image.getSuffix(fileName);
            File toFile = new File(parentPathDir, newFileName);
            files[i].transferTo(toFile);
            String uri = toFile.getPath().replace("\\","/");
            System.out.println("测试："+uri);
            fileURI.append( uri.substring(uri.indexOf("/upload/")));
            if (i != files.length - 1) {
                fileURI.append(",");
            }
        }
        System.out.println("测试："+fileURI.toString());
        return fileURI.toString();
    }
}
