package com.antifraud.controller;

import java.util.List;

import javax.xml.ws.soap.Addressing;

import org.apache.ibatis.annotations.Param;
import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.antifraud.entity.Popedom;
import com.antifraud.entity.Role;
import com.antifraud.service.PopedomService;
import com.antifraud.utils.JsonUtil;
import com.antifraud.utils.ResultInfo;

/**
 * @ClassName: PopedomController
 * @description 权限管理控制器,用户权限相关文件
 * @author ZhaoSong
 * @createDate 2018年11月2日
 */
@Controller
@RequestMapping("popedom")
public class PopedomController {
	
	@Autowired
	private PopedomService popedomService;
	
	/**
	 * @Title: findPopedomByUserId
	 * @description 根据用户id查询所有权限.
	 * @param id 用户id
	 * @return String JSON字符串返回权限集合
	 * @author ZhaoSong
	 * @createDate 2018年11月5日
	 */
	@RequestMapping(value="/popedomList",produces = "application/json; charset=utf-8")
	@ResponseBody
	public String findPopedomByUserId(Long id){
		int code=0;
		String msg=null;
		List<Popedom> popedoms = popedomService.findPopedomByUserId(id);
		if(popedoms!=null){
			code=0;
			msg="查询成功";
		}
		if(popedoms==null){
			code=-1;
			msg="查询数据为空";
		}
		return JsonUtil.getResponseJson(code, msg, popedoms.size(), popedoms);
	}
	
	
	
	/**
	 * @Title: updatePopedomByUserId
	 * @description 根据用户id修改权限
	 * @param id 用户id
	 * @param popedoms 权限编号数组
	 * @return ResultInfo 结果   
	 * @author ZhaoSong
	 * @createDate 2018年11月5日
	 */
	@RequestMapping("/updatePopedom")
	@ResponseBody
	public ResultInfo updatePopedomByUserId(Long id,int[] popedoms){
		ResultInfo result = new ResultInfo();
		int row = popedomService.addPopedomByUserId(id, popedoms);
		if(row !=0){
			result.code=1;
			result.msg="添加成功";
		}
		if(row == 0){
			result.code=-1;
			result.msg="添加失败";
		}
		return result;
	}
}




















