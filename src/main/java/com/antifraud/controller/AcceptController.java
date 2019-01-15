package com.antifraud.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import com.antifraud.dao.LoanInfomationDao;
import com.antifraud.entity.LoanInformation;
import com.antifraud.service.LoanService;
import com.antifraud.utils.AcceptUntils;
import com.antifraud.utils.HttpClientUtil;
import com.antifraud.utils.JsonUtil;

import net.sf.json.JSONObject;

/**
 * 贷后管理
 *
 * @ClassName: AcceptController
 * 
 * @description
 *
 * @author lisaozhang
 * @createDate 2018年12月17日-上午11:23:02
 */
@Controller
@RequestMapping("AcceptController")
public class AcceptController {
	
	
	@Autowired
	 private static LoanService loanService;

	@RequestMapping("Accept")
	@ResponseBody
	public static Object loadJson(int page, int limit) {
		System.out.println("45684544****************");

		Map<Object, Object> map = new HashMap<>();

		ArrayList<LoanInformation> arrayList = new ArrayList<>();

		JSONArray jsonArray = new JSONArray();
		String loadJson = null;
		try {
			loadJson = AcceptUntils.loadJson("http://192.168.1.101:8001/loanTrack");
			System.out.println("loadJson" + loadJson);
		} catch (Exception e) {
			map.put("code", -1);
			map.put("msg", "数据获取异常");
			map.put("data", arrayList);
			map.put("count", 0);

			return null;
		}
		try {
			loadJson = loadJson.substring(loadJson.indexOf("["), loadJson.lastIndexOf("]") + 1);
		} catch (Exception e) {
			map.put("code", 1);
			map.put("msg", "数据获取异常");
			map.put("data", null);
			map.put("count", 0);
			return map;
		}

		// System.out.println(loadJson);
		JSONArray json = (JSONArray) JSONArray.parse(loadJson);
	
		try {
		/*	int star = (page - 1) * limit;*/
			for (int i =0; i < json.size(); i++) {
				if (i == json.size()) {
					break;
				}
				String str=json.get(i).toString();
				JSONObject jsonObject=JSONObject.fromObject(str);
				LoanInformation stu=(LoanInformation)JSONObject.toBean(jsonObject, LoanInformation.class);
			System.out.println("stu"+stu);
			 String res;
		        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		        long lt = new Long(stu.getRepayment_time());
		        Date date = new Date(lt);
		        res = simpleDateFormat.format(date);
			stu.setRepayment_time(res);
			System.out.println("stu"+stu);
				int a=loanService.AddLoanInformation(stu);
				System.out.println("aaaaaaaaaaa"+a);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
System.out.println(arrayList+"********************");



          map.put("code", 1);
		map.put("msg", "查询成功");
		map.put("data", arrayList);
		map.put("count", json.size());

		return null;
	}

	
	
	
	/**
	 * 通过电话搜索
	 *
	 * @Title: findByPhone
	 * 
	 * @description
	 * 
	 * @description
	 *
	 * @param phone
	 *            电话号码
	 * @param page
	 *            当前页数
	 * @param limit
	 *            每页显示条数
	 * @param @return
	 * 
	 * @return String
	 *
	 * @author lujinpeng
	 * @createDate 2018年12月18日-上午11:37:14
	 */
	@RequestMapping(value = "findByPhone", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String findByPhone(String phone, Integer page, Integer limit) {
		int code = 1;
		String msg = "成功";
		int count = 0;
		String data = null;
		JSONArray array = null;
		ArrayList<Object> arrayList = new ArrayList<>();
		try {
			data = HttpClientUtil.doGet("http://192.168.1.101:8001/loanMobile?mobile=" + phone);
			JSONObject obj = JSON.parseObject(data);
			array = obj.getJSONArray("pagemobile");
			count = array.size();
		} catch (Exception e) {
			e.printStackTrace();
			code = -1;
			msg = "系统异常";
		}
		/** 处理数据分页 */
		try {
			int star = (page - 1) * limit;
			for (int i = star; i < star + limit; i++) {
				if (i == array.size()) {
					break;
				}
				arrayList.add(array.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return JsonUtil.getResponseJson(code, msg, count, arrayList);
	}

	/**
	 * 查询是否逾期数据
	 *
	 * @Title: findLoanOverdue
	 * 
	 * @description
	 *
	 * @param is_overdue
	 *            是否逾期（0非逾期 ,1 逾期）
	 * @param page
	 *            当前页
	 * @param limit
	 *            每页显示条数
	 * @param @return
	 * 
	 * @return String
	 *
	 * @author lujinpeng
	 * @createDate 2018年12月18日-上午11:34:54
	 */
	@RequestMapping(value = "findLoanOverdue", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String findLoanOverdue(int is_overdue, Integer page, Integer limit) {
		int code = 1;
		String msg = "成功";
		int count = 0;
		String data = null;
		JSONArray array = null;
		ArrayList<Object> arrayList = new ArrayList<>();

		try {
			data = HttpClientUtil.doGet("http://192.168.1.101:8001/loanOverdue?is_overdue=" + is_overdue);
			JSONObject obj = JSON.parseObject(data);
			array = obj.getJSONArray("pageoverdue");
			count = array.size();
		} catch (Exception e) {
			e.printStackTrace();
			code = -1;
			msg = "系统异常";
		}
		/** 处理数据分页 */
		try {
			int star = (page - 1) * limit;
			for (int i = star; i < star + limit; i++) {
				if (i == array.size()) {
					break;
				}
				arrayList.add(array.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return JsonUtil.getResponseJson(code, msg, count, arrayList);
	}

}
