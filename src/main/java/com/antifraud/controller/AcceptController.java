package com.antifraud.controller;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.antifraud.dao.LoanInfomationDao;
import com.antifraud.dao.ManagementAfterLoanDao;
import com.antifraud.entity.LoanInformation;
import com.antifraud.entity.TManagementAfterLoan;
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

	/*@Autowired
	private static LoanService loanService;*/
	@Autowired
	private ManagementAfterLoanDao managementAfterLoanDao;

	/*@RequestMapping("Accept")
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
			 int star = (page - 1) * limit; 
			for (int i = 0; i < json.size(); i++) {
				if (i == json.size()) {
					break;
				}
				String str = json.get(i).toString();
				JSONObject jsonObject = JSONObject.fromObject(str);
				LoanInformation stu = (LoanInformation) JSONObject.toBean(jsonObject, LoanInformation.class);
				System.out.println("stu" + stu);
				String res;
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				long lt = new Long(stu.getRepayment_time());
				Date date = new Date(lt);
				res = simpleDateFormat.format(date);
				stu.setRepayment_time(res);
				System.out.println("stu" + stu);
				int a = loanService.AddLoanInformation(stu);
				System.out.println("aaaaaaaaaaa" + a);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(arrayList + "********************");

		map.put("code", 1);
		map.put("msg", "查询成功");
		map.put("data", arrayList);
		map.put("count", json.size());

		return null;
	}
*/
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
	public String findByPhone(String mobile, Integer page, Integer limit) {
		int code;
		int count = 0;
		String msg;
		List<TManagementAfterLoan> data = new ArrayList<>();
	
		
		try {
			mobile="%"+mobile+"%";
			Long Newest = managementAfterLoanDao.selectBigestNewest();
			data = managementAfterLoanDao.selectByNewestbyphone(mobile, Newest, page, limit);
			
			count = data.size();
			code = 1;
			 msg = "成功";
		} catch (Exception e) {
			e.printStackTrace();
			code = -1;
			msg = "系统异常";
		}
		
		
		return JsonUtil.getResponseJson(code, msg, count, data);
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
		List<TManagementAfterLoan> data = new ArrayList<>();
		try {
			Long Newest = managementAfterLoanDao.selectBigestNewest();
			data = managementAfterLoanDao.selectByNewestbyoverdue(is_overdue, Newest, page, limit);
			count = data.size();
		} catch (Exception e) {
			e.printStackTrace();
			code = -1;
			msg = "系统异常";
		}
		/** 处理数据分页 *//*
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
		}*/

		return JsonUtil.getResponseJson(code, msg, count, data);
	}

	
/**
 * 
*
 * @Title: dingshi

 * @description 定时将每月的贷后管理数据存入数据库
*
 * @param @return 
   
 * @return String    

 *
 * @author lishaozhang
 * @createDate 2019年1月15日
 */
	@Scheduled(cron="10 2 1 1 * ?")
//	@RequestMapping("ttteee")
	public void dingshi() {
		JSONArray jsonArray = new JSONArray();
		String loadJson = null;
		try {
			//调用接口获取普惠传来的数据
			loadJson = AcceptUntils.loadJson("http://192.168.1.102:80/loanTrack");
		//	System.out.println("loadJson" + loadJson);
		} catch (Exception e) {
			//获取失败代表这个接口无法获取到数据  原因 1 服务器关闭 2 访问路径错误
		}
		try {
			//获取我们需要的有用的数据
			loadJson = loadJson.substring(loadJson.indexOf("["), loadJson.lastIndexOf("]") + 1);
		} catch (Exception e) {
			//返回参数中没有我们需要的数据
		}

		// System.out.println(loadJson);
		//将我们需要的数据转化为json数组
		JSONArray json = (JSONArray) JSONArray.parse(loadJson);
		
		//获取数据库内最新一期数据的期数
         Long Newest = managementAfterLoanDao.selectBigestNewest();
         
        try {
        	//不存在触发空指针
        if(null ==Newest || 0 == Newest){
        	Newest = 1l ;
        }else {
        	//存在 +1
        	Newest ++;
		}	
        	
		} catch (Exception e) {
			//如果数据库没有数据 selectBigestNewest 会空指针 此时将selectBigestNewest设置为1
			Newest = 1l ;
		}
		 
		//遍历数组将json数据转化为实体类
		for (Object o : json) {
			JSONObject jsonObject2 = JSONObject.fromObject(o);
			TManagementAfterLoan TMAL = (TManagementAfterLoan) JSONObject.toBean(jsonObject2,
					TManagementAfterLoan.class);
			TMAL.setNewest(Newest);
			
			//将实体保存到数据库
			Integer insert = managementAfterLoanDao.insert(TMAL);
			if(insert>=1){
			}else {
			}
        
		}


	}
	/***
	 * 
	*
	 * @Title: findnewInfo
	
	 * @description 查询最新一期数据 每月1日1点2分10秒执行
	*
	 * @param @param star
	 * @param @param limit
	 * @param @return 
	   
	 * @return String    
	
	 *
	 * @author lishaozhang
	 * @createDate 2019年1月15日
	 */
	@RequestMapping(value="/findnewInfo" , produces = "application/json; charset=utf-8")
	@ResponseBody
	public Object findnewInfo(Integer page, Integer limit){
		Integer star = (page - 1) * limit;
		try {				
		//查询最新一期期数
		Long Newest = managementAfterLoanDao.selectBigestNewest();System.out.println("Newest"+Newest);
		//查询最新一期数据
		List<TManagementAfterLoan> selectByNewest = managementAfterLoanDao.selectByNewest(Newest, star, limit);
		
		return JsonUtil.getResponseJson(1, "查询成功", managementAfterLoanDao.selectByNewestCount(Newest), selectByNewest);
		
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.getResponseJson(1, "暂无数据", null, null);
		}
	}
	/**
	 * 
	*
	 * @Title: updateRemaker
	
	 * @description 修改备注
	*
	 * @param @param id
	 * @param @param remaker
	 * @param @return 
	   
	 * @return String    
	
	 *
	 * @author lishaozhang
	 * @createDate 2019年1月16日
	 */
	@RequestMapping("updateRemaker")
	@ResponseBody
	public String updateRemaker(Long id, String remaker){
		System.out.println("id"+id+"remaker"+remaker);
		try {
			
		
		Integer result = managementAfterLoanDao.updateById(remaker,id);
		System.out.println("result"+result);
		if (result >= 1) {
			return JsonUtil.getResponseJson(1, "修改成功", null, null);	
		}else {
			return JsonUtil.getResponseJson(1, "修改失败", null, null);	
		}
		
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.getResponseJson(-1, "系统异常", null, null);
		}					
	}	
	
	/**
	 * 
	*
	 * @Title: SelectHistory
	
	 * @description 查询这个报单的所有还款记录
	*
	 * @param @param entry_number
	 * @param @param limit
	 * @param @param page
	 * @param @return 
	   
	 * @return String    
	
	 *
	 * @author lishaozhang
	 * @createDate 2019年1月16日
	 */
	@RequestMapping("SelectHistory")
	@ResponseBody
	public Object SelectHistory(String entry_number){
		try {
					
	//	Integer star = (page - 1) * limit;
		
		
		List<TManagementAfterLoan> selectByEntry_number = managementAfterLoanDao.selectByEntry_number(entry_number);
		
		if(selectByEntry_number.size() >= 1){
			HashMap<Object, Object> hashMap = new HashMap<>();
			hashMap.put("count", 1);
			hashMap.put("msg", "请求成功");
			hashMap.put("data", selectByEntry_number);
			hashMap.put("count", managementAfterLoanDao.selectByEntry_numberCount(entry_number));
			return  hashMap;
		//	JSON.toJSON(JsonUtil.getResponseJson(1, "查询成功", managementAfterLoanDao.selectByEntry_numberCount(entry_number), selectByEntry_number))
		}else {
			return  JsonUtil.getResponseJson(1, "暂无数据", null, null);
		}
		} catch (Exception e) {
			e.printStackTrace();
			return  JsonUtil.getResponseJson(-1, "系统异常", null, null);
		}
		
	}
	/**
	 * 
	*
	 * @Title: findOne
	
	 * @description 查询某一条还款记录
	*
	 * @param @param id
	 * @param @return 
	   
	 * @return String    
	
	 *
	 * @author lishaozhang
	 * @createDate 2019年1月16日
	 */
	@RequestMapping("findOne")
	@ResponseBody
	public String findOne (Long id){
		try {		
		TManagementAfterLoan findOne = managementAfterLoanDao.findOne(id);
		return  JsonUtil.getResponseJson(1, "查询成功", null, findOne);
		} catch (Exception e) {
			return  JsonUtil.getResponseJson(-1, "系统异常", null, null);
		
		}
	}
	
}
