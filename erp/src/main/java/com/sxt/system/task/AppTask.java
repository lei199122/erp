package com.sxt.system.task;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sxt.system.utils.FileUploadAndDownUtil;

/**
 * 定时任务
 * @author LW
 *
 */
@EnableScheduling
@Component
public class AppTask {
	
	@Value("${upload.upload-root-path}")
	private String uploadRootPath="E:upload";
	
	@Scheduled(cron="0 30 23 * * ?")
	public void cleanImageRemoveTemp() {
		String dirName = FileUploadAndDownUtil.getDirNameByCurrentDate();
		//确定要扫描的文件
		File file = new File(uploadRootPath+"/"+dirName);
		if(file.exists()) {
			File[] files = file.listFiles();
			for (File file2 : files) {
				if(file2.getName().endsWith("_temp")) {
					file2.delete();
				}
			}
		}
	}
}
