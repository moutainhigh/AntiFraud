package com.antifraud.controller;

import org.apache.http.client.fluent.Content;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.antifraud.utils.LinkfaceAntiFraud;

/**
 * Linkface 金融云 API
 *
 * @ClassName: LinkfaceAntiFraudController

 * @description 
 *
 * @author lujinpeng
 * @createDate 2018年11月20日-上午11:38:13
 */
public class LinkfaceAntiFraudController {
	/**
	 * 通过姓名、身份证号验证该用户是否有在多家借贷平台进行过贷款申请行为
	 *
	 * @Title: LongBorrowing
	
	 * @description 
	 *
	 * @param  name 借贷人姓名
	 * @param  id_number 借贷人身份证号
	 * @param @return 
	   
	 * @return Content    
	 *
	 * @author lujinpeng
	 * @createDate 2018年11月20日-上午11:38:21
	 */
	@RequestMapping("LongBorrowing")
	@ResponseBody
	public Content LongBorrowing (String name, String id_number){
		
		return LinkfaceAntiFraud.LongBorrowing(name, id_number);
	}
	
	
}
