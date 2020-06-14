package com.sxt.system.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sxt.system.utils.DataGridView;
import com.sxt.system.utils.FileUploadAndDownUtil;

/**
 * 文件上传的接口
 * @author LW
 *
 */

@Controller
@RequestMapping("file")
public class FileController {
	
	@Value("${upload.upload-root-path}")
	private String uploadRootPath="E:/upload";
	
	/**
	 * 上传图片
	 * @param mf
	 * @return
	 */
	@RequestMapping("uploadFile")
	@ResponseBody
	public DataGridView uploadFile(MultipartFile mf) {
		//得到文件名
		String oldName = mf.getOriginalFilename();
		//根据当前时间得到文件夹的名字
		String dirName = FileUploadAndDownUtil.getDirNameByCurrentDate();
		//判断上传目录 里面有没有当前文件夹
		File dirFile = new File(uploadRootPath,dirName);
		if(!dirFile.exists()) {
			dirFile.mkdirs();
		}
		//生成文件的新名字
		String newFileName = FileUploadAndDownUtil.getNewFileNameUserUUID(oldName)+"_temp";
		//上传文件
		File file = new File(dirFile, newFileName);
		try {
			mf.transferTo(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String,Object> src = new  HashMap<>();
		src.put("src", "/"+dirName+"/"+newFileName);
		return new DataGridView(src);
	}
	
	/**
	 * 显示图片的方法
	 * @param filePath
	 * @return
	 */
	@RequestMapping("showImage")
	public ResponseEntity<Object> showImage(String filePath){
		return FileUploadAndDownUtil.showImage(this.uploadRootPath, filePath);
		
	}
}
