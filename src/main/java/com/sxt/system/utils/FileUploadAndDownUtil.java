package com.sxt.system.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.sxt.system.constant.Constant;

/**
 * 文件上传下载工具类
 * @author LW
 *
 */
public class FileUploadAndDownUtil {
	
	 public static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	 
	 
	 /**
	  * 根据当前时间得到文件夹的名称
	  * @return
	  */
	public static String getDirNameByCurrentDate(){
		return sdf1.format(new Date());
	}
	
	
	/**
	 * 根据文件名字生成新名字
	 * @param oldName
	 * @return
	 */
	public static String getNewFileNameUserUUID(String oldName) {
		String end = oldName.substring(oldName.lastIndexOf("."), oldName.length());
		return UUID.randomUUID().toString().replace("-", "").toUpperCase()+end;
	}
	
	/**
	 * 文件下载
	 * @param uploadRootPath
	 * @param filePath
	 * @return
	 */
	public static ResponseEntity<Object> showImage(String uploadRootPath,String filePath){
		
		File file = new File(uploadRootPath,filePath);
		if(file.exists()) {
			//将下载的文件，封装byte[]
			byte[] bytes = null;
			try {
				bytes = FileUtils.readFileToByteArray(file);
			}catch (IOException e) {
				e.printStackTrace();
			}
			//创建封装响应头信息的对象
			HttpHeaders header = new HttpHeaders();
			//封装响应内容类型
			header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			//设置下载的文件的名称
			header.setContentDispositionFormData("attachment", "123.jpg");
			//创建ResponseEntity对象
			ResponseEntity<Object> entity = new ResponseEntity<Object>(bytes ,header,HttpStatus.CREATED);
			return entity;
		}else {
			return null;
		}
	}
	
	/**
	 * 修改文件
	 * @param uploadRootPath
	 * @param imgpath
	 * @return
	 */
	public static String changeFileName(String uploadRootPath,String imgpath) {
		String path = imgpath.replace("_temp", "");
		File file = new File(uploadRootPath,imgpath);
		File dest = new File(uploadRootPath,path);
		file.renameTo(dest);
		return path;
	}
	
	/**
	 * 根据路径 删除文件
	 * @param uploadRootPath
	 * @param imgpath
	 */
	public static void deleteFile(String uploadRootPath , String imgpath) {
		if(!imgpath.equals(Constant.USER_DEFAULT_IMAGE)) {
			File file = new File(uploadRootPath,imgpath);
			if(file.exists()) {
				file.delete();
			}
		}
	}
}
