package com.antifraud.service;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.antifraud.entity.Log;
/**
 * 
  * @ClassName: EventSupervisorService
  * @description 日志
  * @author caoyaru
  * @createDate 2018年12月10日-下午3:30:21
 */
public interface LogService {
	/**
	 * 
	  * @Title: save
	  * @description 保存日志操作
	  * @param @param es 
	  * @return void    
	  * @author caoyaru
	  * @createDate 2018年12月10日-下午3:31:26
	 */
	void saveLog(@Param("log") Log log);
	/**
	 * 
	  * @Title: findById
	  * @description 查询全部
	  * @param  
	  * @return Log    
	  * @author caoyaru
	  * @createDate 2018年12月11日-上午11:42:19
	 */
	List<Log> findById( Integer page, Integer size);
	
	/**
	 * 
	  * @Title: getCount
	  * @description 查询总数
	  * @param @return 
	  * @return int    
	  * @author caoyaru
	  * @createDate 2018年12月12日-上午10:18:59
	 */
	int getCount();

}
