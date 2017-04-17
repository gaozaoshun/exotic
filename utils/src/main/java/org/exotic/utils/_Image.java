package org.exotic.utils;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 图片处理工具类-适合SpringMVC
 * @author 高灶顺
 * @date 2016-11-16
 */
public class _Image {

	/**
	 * 保存原图、缩略图
	 * @param URI	文件保存位置 ../upload/用户名
	 * @param files	 文件数组
	 * @param baseSize	 图片压缩基数  500
	 * @return 图片保存路径 /upload/用户名/2016_12_06/15_12_55_1_max.jpg
	 * @throws IOException
	 */
	public static String saveFile(String URI, MultipartFile[] files, int baseSize) throws IOException {
		StringBuffer fileURI = new StringBuffer();
		if (files == null || files.length < 1) {
			return null;
		}
		// 创建文件夹
		String dateStr = new SimpleDateFormat("yyyy_MM_dd").format(new Date());
		URI = URI + "/" + dateStr;
		File parentPathDir = new File(URI);
		if (!parentPathDir.exists()) {
			parentPathDir.mkdirs();// ../upload/用户名/2016_12_06
		}
		for (int i = 0; i < files.length; i++) {
            String fileName = files[i].getOriginalFilename();
			// 保存原图文件到 ../upload/用户名/2016_12_06/11_28_35_1.jpg
			String timeStr = new SimpleDateFormat("HH_mm_ss_").format(new Date());
			String newFileName = (timeStr + (i + 1) + "_max.") + _Image.getSuffix(fileName);
			File toFile = new File(parentPathDir, newFileName);
            files[i].transferTo(toFile);
			// 创建缩略图 : URI-缩略图保存路径 newFileName-文件名 files-源文件 450-压缩基数
			String minPhotoName = _Image.createMinPhotos(URI, newFileName, toFile, baseSize);
            String uri = URI.replace("\\","/");
            fileURI.append( uri.substring(uri.indexOf("/upload/"))+"/"+ minPhotoName );
			if (i != files.length - 1) {
				fileURI.append(",");
			}
		}
		return fileURI.toString();
	}

	/**
	 * 压缩图片
	 * @param savePath 缩略图保存路径 ../upload/用户名/2016_12_06
	 * @param fileName 文件名 14_24_55_1_max.jpg
	 * @param file 源文件
	 * @param baseSize 压缩基数 缩略图的高
	 * @return 缩略图文件名 
	 * @throws IOException
	 * @author 高灶顺
	 * @date 2016-11-16
	 */
	public static String createMinPhotos(String savePath, String fileName, File file, int baseSize) throws IOException {
        Image srcFile = ImageIO.read(file);
		int old_w = srcFile.getWidth(null); // 得到源图宽
		int old_h = srcFile.getHeight(null); // 得到源图高
		int new_w = old_w; // 新图宽
		int new_h = old_h; // 新图高
        /* 缩略图宽高算法 *//*图片小于100KB不压缩*/
		if ((file.length()/1024 > 100)&&(old_w > baseSize || old_h > baseSize)) {
			float scale = ((float) old_w) / old_h;//图片比例
			if (scale < 1) { // 计算新图长宽
				new_h =  baseSize;
				new_w = (old_w * new_h) / old_h;
			} else {
				new_w =  baseSize;
				new_h = (old_h * new_w) / old_w;
			}
		}
		BufferedImage minImg = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
		//drawImage(图像文件，x轴起始位置，y轴起始位置，x轴最终位置，y轴最终位置，观察者对象)
		minImg.getGraphics().drawImage(srcFile, 0, 0, new_w, new_h, null); // 绘制缩小后的图
		// 创建目标保存压缩图文件
		File minPhoto = new File(savePath, fileName.replace("max", "min"));
		// 创建输出流
		OutputStream os = new FileOutputStream(minPhoto);
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
		encoder.encode(minImg); // 近JPEG编码
		os.flush();
		os.close();
		return minPhoto.getName();
	}

	/**
	 * 获取文件后缀名
	 * @return 例如"jpg"
	 * @author lwh
	 * @date 2016-1-15
	 */
	public static String getSuffix(String fileName) {
		String suffix = null;
		if (fileName == null) {
			return null;
		}
		int index = 0;
		if (-1 != (index = fileName.lastIndexOf('.'))) {
			suffix = fileName.substring(index + 1);
		}
		return suffix;
	}
	
	/**
	 * 获取文件后缀名 jpg
	 * @author GZS
	 * @return String[]
	 * @date 2016-4-12
	 */
	
	public static String[] getChangeableSuffix(String...fileNames){
		String[] filesContentType = new String[ fileNames.length ];
		for( int i = 0 ; i < fileNames.length; i++ ){
			filesContentType[i] = _Image.getSuffix( fileNames[i] );
		}
		return filesContentType;
	}
	
	/**
	 * 获取文件后缀名 jpg
	 * @return String[]
	 * @author GZS
	 * @date 2016-1-15
	 */
	public static String[] getArraySuffix(String[] fileNames){
		String[] filesContentType = new String[ fileNames.length ];
		for( int i = 0 ; i < fileNames.length; i++ ){
			filesContentType[i] = _Image.getSuffix( fileNames[i] );
		}
		return filesContentType;
	}

	/**
	 *  文件过滤
	 * @param files		文件数组
	 * @param contentTypes	 文件MIME类型数组
	 * @param allowContentTypes			允许的MIME类型数组
	 * @param allowOneMaxByte		允许单文件的最大字节
	 * @param allowSize		允许上传文件的数量
	 * @return 描述："上传文件为空"。。
	 */
	public static String fileFilter(File[] files,String[] contentTypes, String[] allowContentTypes, int allowOneMaxByte, int allowSize ) {
		
		//上传文件不能为空
		if( ! (files != null && files.length > 0) ){
			return "上传文件为空";
		}
		
		//上传文件类型限制
		List<String> allowContentTypeList = new ArrayList<String>(0);
		Collections.addAll(allowContentTypeList, allowContentTypes);
		
		List<String>  contentTypesList = new ArrayList<String>(0);
		Collections.addAll(contentTypesList, contentTypes);
		
		if( !allowContentTypeList.containsAll(contentTypesList) ){
			return "上传文件类型不允许";
		}
		
		//上传文件大小限制
		for( File file : files ){
			if( file.length() > allowOneMaxByte ){
				return "存在上传文件过大.";
			}
		}
		
		//上传文件数量限制
		if( files.length > allowSize ){
			return "上传图片张数超限.";
		}
		return null;
	}
	/**
	 * 测试工具
	 */
	public static void main(String[] args) {

    }


}
