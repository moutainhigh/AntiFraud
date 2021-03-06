


package com.antifraud.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.antifraud.entity.QuestionAnswer;
import com.antifraud.service.QuestionAnswerService;
import com.antifraud.utils.JsonUtil;

@Controller
@RequestMapping("/QuestionAnswer")
public class QuestionAnswerController {
	
	private Logger logger = Logger.getLogger(QuestionAnswerController.class);

	@Autowired
	private QuestionAnswerService questionAnswerService;
	int code = 1;
	String msg = "成功";
	
	/**
	 * 
	 * @Title: addAnswer
	 * @description 面审随机出题的添加答案
	 * @param @return  
	 * @return Map<String,Object>    
	 * @author qixiyao
	 * @createDate 2018年10月17日-下午4:09:49
	 */
	@ResponseBody
	@RequestMapping("/addAnswer")
	public String addAnswer(QuestionAnswer questionAnswer) {
		int result = 0;
		
		try {
			result = questionAnswerService.addAnswer(questionAnswer);
			
			if (result > 0) {
				code = 1;
				msg = "答案添加成功";
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e);
			code = -1;
			msg = "系统异常";
		}
		return JsonUtil.getResponseJson(code, msg, null, result);
	}

}
