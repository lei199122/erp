package com.sxt.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sxt.system.cache.data.DataProvider;
import com.sxt.system.utils.DataGridView;
import com.sxt.system.utils.ResultObj;

@RestController
@RequestMapping("cache")
public class CacheController {
	
	private Log log = LogFactory.getLog(CacheController.class);
	
	/**
	 * 全全查询
	 * @return
	 */
	@RequestMapping("loadAllCache")
	public DataGridView loadAllCache() {
		List<Map<String,Object>> data = new ArrayList<>();
		Set<Entry<String,Object>> entrySet = DataProvider.DATA_CACHE.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("key", entry.getKey());
			map.put("value", entry.getValue());
			data.add(map);
		}
		return new DataGridView(data);
	}
	
	/**
	 * 删除
	 * @param key
	 * @return
	 */
	@RequestMapping("deleteCache")
	public ResultObj deleteCache(String key) {
		try {
			DataProvider.DATA_CACHE.remove(key);
			return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			log.error("删除失败");
			return ResultObj.DELETE_ERROR;
		}
		
	}
	/**
	 * 清空
	 * @return
	 */
	@RequestMapping("deleteAllCache")
	public ResultObj deleteAllCache() {
		try {
			DataProvider.DATA_CACHE.clear();
			return ResultObj.CLEAR_SUCCESS;
		} catch (Exception e) {
			log.error("清空失败");
			return ResultObj.CLEAR_ERROR;
		}
	}
	/**
	 * 清空
	 * @param keys
	 * @return
	 */
	@RequestMapping("batchDeleteCache")
	public  ResultObj deleteAllCache(String[] keys) {
		try {
			for (String key : keys) {
				DataProvider.DATA_CACHE.remove(key);
			}
			return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			log.error("删除失败");
			return ResultObj.DELETE_ERROR;
		}
	}
}
