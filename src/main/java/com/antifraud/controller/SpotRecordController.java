package com.antifraud.controller;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.antifraud.entity.FiduciaryLoan;
import com.antifraud.entity.HousePropertyMortgage;
import com.antifraud.entity.Photo;
import com.antifraud.entity.SpotRecord;
import com.antifraud.entity.User;
import com.antifraud.entity.VehicleMortgage;
import com.antifraud.entityVO.FklistVo;
import com.antifraud.service.FiduciaryLoanService;
import com.antifraud.service.HousePropertyMortgageService;
import com.antifraud.service.PhotoService;
import com.antifraud.service.SpotRecordService;
import com.antifraud.service.VehicleMortgageService;
import com.antifraud.utils.DesUtil;
import com.antifraud.utils.HttpClientUtil;
import com.antifraud.utils.JsonUtil;
import com.antifraud.utils.MD5Encrypt;

/**
 * 
 *
 * @ClassName: SpotRecordController
 * @description 实地记录表控制层
 * @author lisahozhang
 * @createDate 2018年10月30日-下午2:50:21
 */
@Controller
@RequestMapping("SpotRecord")
public class SpotRecordController {

	@Autowired
	private SpotRecordService spotRecordService;
	@Resource
	private HousePropertyMortgageService housePropertyMortgageService;
	@Autowired
	private FiduciaryLoanService fiduciaryLoanService;
	@Autowired
	private VehicleMortgageService vehicleMortgageService;
	@Autowired
	private PhotoService photoService;
	

	/**
	 * 
	 *
	 * @Title: saveSpotRecord
	 * 
	 * @description 保存实地记录
	 *
	 * @param @param
	 *            spotRecord
	 * @param @return
	 * 
	 * @return Map
	 *
	 * 
	 * @author lishaozhang
	 * @createDate 2018年10月30日
	 */
	@RequestMapping("saveSpotRecord")
	@ResponseBody
	public Object saveSpotRecord(SpotRecord spotRecord) {

		Map map = spotRecordService.saveSpotRecord(spotRecord);
		return JSONObject.toJSON(map);
	}

	/**
	 * 
	 *
	 * @Title: updateSpotRecord
	 * 
	 * @description 修改实地记录
	 *
	 * @param @param
	 *            spotRecord
	 * @param @return
	 * 
	 * @return Map
	 *
	 * 
	 * @author lishaozhang
	 * @createDate 2018年10月30日
	 */
	@RequestMapping("updateSpotRecord")
	@ResponseBody
	public Object updateSpotRecord(SpotRecord spotRecord) {

		Map map = spotRecordService.updateSpotRecordById(spotRecord);
		return JSONObject.toJSON(map);
	}

	/**
	 * 
	 *
	 * @Title: deleteSpotRecordById
	 * 
	 * @description 通过实地人员删除实地记录
	 *
	 * @param @param
	 *            id
	 * @param @return
	 * 
	 * @return Map
	 *
	 * 
	 * @author lishaozhang
	 * @createDate 2018年10月30日
	 */
	@RequestMapping("deleteSpotRecordById")
	@ResponseBody
	public Object deleteSpotRecordById(Long id) {

		Map map = spotRecordService.deleteSpotRecordById(id);
		return JSONObject.toJSON(map);
	}

	@RequestMapping("findSpotRecordByUserId")
	@ResponseBody
	public Object findSpotRecordByUserId(Long user_id, int page, int limit) {

		Map map = spotRecordService.findSpotRecordByUserId(user_id, page, limit);
		return JSONObject.toJSON(map);
	}

	/**
	 * 
	 *
	 * @Title: findSpotRecordByReportId
	 * 
	 * @description 通过业务报单编号查询实地记录
	 *
	 * @param @param
	 *            reportId
	 * @param @return
	 * 
	 * @return Map
	 *
	 * 
	 * @author lishaozhang
	 * @createDate 2018年10月30日
	 */
	@RequestMapping("findSpotRecordByReportId")
	@ResponseBody
	public Object findSpotRecordByReportId(String report_id, int page, int limit) {

		Map map = spotRecordService.findSpotRecordByReportId(report_id, page, limit);
		return JSONObject.toJSON(map);
	}

	/**
	 * 
	 *
	 * @Title: findAllSpotRecord
	 * 
	 * @description 分页查询所有
	 *
	 * @param @param
	 *            currentPage
	 * @param @param
	 *            pageSize
	 * @param @return
	 * 
	 * @return Map
	 *
	 * 
	 * @author lishaozhang
	 * @createDate 2018年11月1日
	 */
	@RequestMapping("findAllSpotRecord")
	@ResponseBody
	public Object findAllSpotRecord(int currentPage, int pageSize) {

		Map map = spotRecordService.findAllSpotRecord(currentPage, pageSize);
		return JSONObject.toJSON(map);
	}
	
	/**
	 * 
	 *
	 * @Title: findAllSpotRecord
	 * 
	 * @description 分页查询所有
	 *
	 * @param @param
	 *            currentPage
	 * @param @param
	 *            pageSize
	 * @param @return
	 * 
	 * @return Map
	 *
	 * 
	 * @author lishaozhang
	 * @createDate 2018年11月1日
	 */
	@RequestMapping("findAllSpotRecordfd")
	@ResponseBody
	public Object findAllSpotRecordfd(int page, int limit,String cs) {
		System.out.println("cs"+cs+"page"+page+"limit"+limit);
         
		Map map = spotRecordService.findAllSpotRecordfd(page, limit,cs);
		System.out.println("map"+map);
		return JSONObject.toJSON(map);
	}

	/**
	 * 
	 *
	 * @Title: findAllSpotRecord
	 * 
	 * @description 用一句话描述这个方法的作用
	 *
	 * @param @param
	 *            currentPage
	 * @param @param
	 *            pageSize
	 * @param @return
	 * 
	 * @return Object
	 *
	 * 
	 * @author lishaozhang
	 * @createDate 2018年11月16日
	 */
	@RequestMapping("judgementResult")
	@ResponseBody
	public Object judgementResult(String reportId, int status, String remark,HttpServletRequest request, HttpServletResponse resp) {
		Map<String, Object> maps = new HashMap<String, Object>();
		System.getProperty("file.encoding");// 获取java环境默认编码
		System.setProperty("file.encoding", "utf-8");// 设置java环境默认编码
		request.getCharacterEncoding();// JSP获取客户端请求的编码
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}// JSP设置客户端请求的编码

		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		List<FklistVo> FklistVoList = null;
		List<FklistVo> FklistVoListone = null;
		List<FklistVo> FklistVoListtwo = null;
		List<FklistVo> FklistVoListAll = new ArrayList<>();
	   
	    try {
			FklistVoList = housePropertyMortgageService.findListAlls(reportId);
			FklistVoListone = fiduciaryLoanService.findListAlls(reportId);
			FklistVoListtwo = vehicleMortgageService.findListAlls(reportId);
			if (FklistVoList != null && FklistVoList.size() != 0) {
				
				FklistVoListAll.addAll(FklistVoList);
			}
			if (FklistVoListone != null && FklistVoListone.size() == 0) {
				FklistVoListAll.addAll(FklistVoListone);
			}
			if (FklistVoListtwo != null && FklistVoListtwo.size() == 0) {
				FklistVoListAll.addAll(FklistVoListtwo);
			}
			List<Photo> queryImage = photoService.queryImage(reportId);
			//String path = JSONObject.toJSONString(queryImage);
			JSONObject jsonObject = new JSONObject();
			 String substring = reportId.substring(0,2);
	            //根据报单编号查询内勤ID，根据内勤ID查询当前操作人的姓名
	            User managementName = null;
	            if(substring.equals("FW")){
	            	//房屋操作人
	            	try {
	            		  managementName = housePropertyMortgageService.selectManagement(reportId);
					} catch (Exception e) {
						map.put("code", "1");
						map.put("msg", "操作员id未记录");
						return map;
					}
	              	
	            	jsonObject.put("managementName", managementName.getName());
	            }else if(substring.equals("CL")){
	            	//车辆操作人
	            	try {
	            		managementName = vehicleMortgageService.selectManagement(reportId);
					} catch (Exception e) {
						map.put("code", 1);
						map.put("msg", "操作员id未记录");
						return map;
					}
	            	 
	    //        	jsonObject.put("managementName", managementName.getName());
	     //       	System.out.println("当前操作人=============>"+managementName.getName());
	            }else if(substring.equals("XY")){
	            	//信贷操作人
	            	try {
	            		 managementName = fiduciaryLoanService.selectManagement(reportId);
					} catch (Exception e) {
						map.put("code", 1);
						map.put("msg", "操作员id未记录");
						return map;
					}
	            	
	            	jsonObject.put("managementName", managementName.getName());
				}
	            
			jsonObject.put("data", FklistVoListAll);
			jsonObject.put("queryImage", queryImage);
			String jobStr =DesUtil.encode("yunquekj", jsonObject.toString());
            Map<String , Object> map2 = new HashMap<String , Object>();
			map2.put("params", jobStr);
		    
    //        String code = HttpClientUtil.doPost("http://192.168.1.101:8001/supervisor/risk/SuperAntiCtrl/receiveInfo",map2);
			//code为空 code应为200 date值为空 应该传？
        //    System.out.println("****"+code);
          
	//		JSONObject jsonObjectfour = (JSONObject) JSON.parse(code);

		//	String codes = jsonObjectfour.getString("code");
			String	codes = "200";
			
			if(codes.equals("200")){
				
				Map mapak = spotRecordService.judgementResult(reportId, status, remark);
			Integer str= (Integer) mapak.get("code");
			str =1;
				if(str==1){
					map.put("code", 1);
					map.put("msg", "风控报告添加成功");
					return JSONObject.toJSON(map);
				}else{
					map.put("code", -1);
					map.put("msg",mapak.get("msg") );
					return JSONObject.toJSON(map);
				}
			}else{
				map.put("code", -1);
				map.put("msg","风控报告添加不成功" );
				return JSONObject.toJSON(map);
			}
			
			
		} catch (Exception e) {
			map.put("code", -2);
			map.put("msg", "程序异常");
			e.printStackTrace();
			return JSONObject.toJSON(map);
        }

		
		
	}

	/**
	 * 
	 *
	 * @Title: fiandFiduciaryLoanByStatus
	 * 
	 * @description 查询待审核的信用贷报单
	 *
	 * @param @param
	 *            status1
	 * @param @param
	 *            status2
	 * @param @param
	 *            page
	 * @param @param
	 *            limit
	 * @param @return
	 * 
	 * @return Object
	 *
	 * 
	 * @author lishaozhang
	 * @createDate 2018年11月26日
	 */
	@RequestMapping("fiandFiduciaryLoanByStatus")
	@ResponseBody
	public Object fiandFiduciaryLoanByStatus(String _a, String _s, String _t) {
		Map map = new HashMap();
		try {
			String str = DesUtil.getrechargeMember(_a, _t);

			if (str != "") {
				String md5 = MD5Encrypt.MD5Encode(str);
				if (md5.equals(_s)) {
					Map<String, String> data = DesUtil.map(str);
					Integer status1 = Integer.parseInt(data.get("status1"));
					Integer status2 = Integer.parseInt(data.get("status2"));
					Integer page = Integer.parseInt(data.get("page"));
					Integer limit = Integer.parseInt(data.get("limit"));
					Long management = Long.parseLong(data.get("management"));
					Long risk_management = Long.parseLong(data.get("risk_management"));
					// 未加密前,Controller方法参数status1默认值为-10
					// 加密后不能为控制器层参数使用直接设定默认值注解@RequestDefult(value=-10),
					// 故使用If判断,若status1参数无值,则status1=-10
					if (status1.equals(null) || status1.equals("")) {
						status1 = -10;
					}
					map = spotRecordService.fiandFiduciaryLoanByStatus(status1, status2, page, limit, management,
							risk_management);
					System.out.println("map"+map);
				}else {
					map.put("code", -1);
					map.put("msg", "签名错误，您的访问数据非法");

				}
			}else {
				map.put("code", -1);
				map.put("msg", "网络超时您的网络不行");
			}
		} catch (Exception e) {
			map.put("code", -1);
			map.put("msg", "请求数据异常~~~");
		}

		return JSONObject.toJSON(map);

	}

	/**
	 * 
	 *
	 * @Title: fiandHousePropertyMortgageByStatus
	 * 
	 * @description 查询待审核的房屋贷报单
	 *
	 * @param @param
	 *            status1
	 * @param @param
	 *            status2
	 * @param @param
	 *            page
	 * @param @param
	 *            limit
	 * @param @return
	 * 
	 * @return Object
	 *
	 * 
	 * @author lishaozhang
	 * @createDate 2018年11月26日
	 */
	@RequestMapping("fiandHousePropertyMortgageByStatus")
	@ResponseBody
	public Object fiandHousePropertyMortgageByStatus(String _a, String _s, String _t) {
		Map map = new HashMap();
		try {
			String str = DesUtil.getrechargeMember(_a, _t);

			if (str != "") {
				String md5 = MD5Encrypt.MD5Encode(str);
				if (md5.equals(_s)) {
					Map<String, String> data = DesUtil.map(str);
					Integer status1 = Integer.parseInt(data.get("status1"));
					Integer status2 = Integer.parseInt(data.get("status2"));
					Integer page = Integer.parseInt(data.get("page"));
					Integer limit = Integer.parseInt(data.get("limit"));
					Long management = Long.parseLong(data.get("management"));
					Long risk_management = Long.parseLong(data.get("risk_management"));
					// 未加密前,Controller方法参数status1默认值为-10
					// 加密后不能为控制器层参数使用直接设定默认值注解@RequestDefult(value=-10),
					// 故使用If判断,若status1参数无值,则status1=-10
					if (status1.equals(null) || status1.equals("")) {
						status1 = -10;
					}
					map = spotRecordService.fiandHousePropertyMortgageByStatus(status1, status2, page, limit,
							management, risk_management);
				}else {
					map.put("code", -1);
					map.put("msg", "签名错误，您的访问数据非法");

				}
			}else {
				map.put("code", -1);
				map.put("msg", "网络超时您的网络不行");
			}
		} catch (Exception e) {
			map.put("code", -1);
			map.put("msg", "请求数据异常~~~");
		}

		return JSONObject.toJSON(map);

	}


	/**
	 * 
	 *
	 * @Title: fiand VehicleMortgageByStatus
	 * 
	 * @description 查询待审核的车辆贷报单
	 *
	 * @param @param
	 *            status1
	 * @param @param
	 *            status2
	 * @param @param
	 *            page
	 * @param @param
	 *            limit
	 * @param @return
	 * @return Object
	 *
	 * @author lishaozhang
	 * @createDate 2018年11月26日
	 */
	@RequestMapping("fiandVehicleMortgageByStatus")
	@ResponseBody
	public Object fiandVehicleMortgageByStatus(String _a, String _s, String _t) {
		Map map = new HashMap();
		try {
			String str = DesUtil.getrechargeMember(_a, _t);

			if (str != "") {
				String md5 = MD5Encrypt.MD5Encode(str);
				if (md5.equals(_s)) {
					Map<String, String> data = DesUtil.map(str);
					Integer status1 = Integer.parseInt(data.get("status1"));
					Integer status2 = Integer.parseInt(data.get("status2"));
					Integer page = Integer.parseInt(data.get("page"));
					Integer limit = Integer.parseInt(data.get("limit"));
					Long management = Long.parseLong(data.get("management"));
					Long risk_management = Long.parseLong(data.get("risk_management"));
					// 未加密前,Controller方法参数status1默认值为-10
					// 加密后不能为控制器层参数使用直接设定默认值注解@RequestDefult(value=-10),
					// 故使用If判断,若status1参数无值,则status1=-10
					if (status1.equals(null) || status1.equals("")) {
						status1 = -10;
					}
					map = spotRecordService.fiandVehicleMortgageByStatus(status1, status2, page, limit, management,
							risk_management);

				} else {
					map.put("code", -1);
					map.put("msg", "网络超时您的网络不行");
				}
			} else {
				map.put("code", -1);
				map.put("msg", "网络超时您的网络不行");
			}
		} catch (Exception e) {
			map.put("code", -1);
			map.put("msg", "请求数据异常~~~");
		}

		return JSONObject.toJSON(map);

	}
	
	@RequestMapping("findSpotRecordbytrid")
	@ResponseBody
	public Object findSpotRecordbytrid(int trid) {
		Map map = new HashMap();
		List<String> liststring = new ArrayList<String>();
	
		try {
			List<SpotRecord> list=spotRecordService.findSpotRecordbytrid(trid);
			long trids=list.get(0).getTrid();
			long tid=list.get(0).getTid();
			long sid=list.get(0).getSid();
			Date date=list.get(0).getCreate_time();
			long time=date.getTime()+86400000;
			long times=date.getTime();
			Date dates=new Date(time);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        String dateString=sdf.format(date);
	        String datesString=sdf.format(dates);
             String url="https://tsapi.amap.com/v1/track/terminal/trsearch?trid="+trid+"&key=0eb1233723cb120943f04c075efad846"+"&tid="+tid
            		 +"&sid="+sid+"&starttime="+times+"&endtime="+time+"&page=1&pagesize=100";
	
             String jsonStr = JsonUtil.loadJson(url);
             JSONObject jsonObjectfour = (JSONObject) JSON.parse(jsonStr);
             String form_component_values = jsonObjectfour.getString("data");
             JSONObject json = (JSONObject) JSON.parse(form_component_values);
             String pointss = json.getString("tracks");
             pointss=pointss.substring(1, pointss.length()-1);
             JSONObject jsons = (JSONObject) JSON.parse(pointss);
             String points = jsons.getString("points");
             String c = points.substring(1, points.length() - 2);
             String []str=c.split("},");
           
            String s;
             for(int i=0;i<str.length;i++){
            	 s = str[i] + "}";
            	 JSONObject jsonone = (JSONObject) JSON.parse(s);
            	String ctr=jsonone.getString("location");
            	String  sm="["+ctr+"]";
            	liststring.add(sm);
            	
             }
             map.put("location", liststring);
             System.out.println("map"+map);
     	} catch (Exception e) {
			map.put("code", -1);
			map.put("msg", "请求数据异常~~~");
		}

		return JSONObject.toJSON(map);

	}
	
	/**
	 * 通过电话或者姓名搜索房产信息
	 *
	 * @Title: findHouseByPhoneOrName
	
	 * @description 
	 *
	 * @param phone 电话
	 * @param name	抵押人姓名
	 * @param page	当前页数
	 * @param limit	每页显示条数
	 * @return 
	   
	 * String    
	 *
	 * @author lujinpeng
	 * @createDate 2018年12月24日-下午2:21:49
	 */
	@RequestMapping(value = "findHouseByPhoneOrName", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String findHouseByPhoneOrName (@RequestParam(value = "phone", required = false) String phone, @RequestParam(value = "name", required = false) String name,
			Integer page, Integer limit) {
		int code = 1, count = 0;
		String msg = "成功";
		List<HousePropertyMortgage> list = null;
		page = page <= 1 ? 0 : (page - 1) * limit;		
		User user = (User)SecurityUtils.getSubject().getPrincipal();
		Long user_id = user.getId();
		
		try {
			// 如果是超级管理员
			if (user.getId() == 3) {
				user_id = null;
			}
			list = spotRecordService.findHouseInfoByPhoneOrName(phone, name, user_id, page, limit);
			count = spotRecordService.findHouseInfoByPhoneOrName(phone, name, user_id, null, null).size();
			
		} catch (Exception e) {
			e.printStackTrace();
			code = -1;
			msg = "系统异常";
		}		
		
		return JsonUtil.getResponseJson(code, msg, count, list);
	}
	
	/**
	 * 通过电话或者姓名搜索信用贷款信息
	 *
	 * @Title: findFiduciaryLoanByPhoneOrName
	
	 * @description 
	 *
	 * @param phone 电话
	 * @param name	抵押人姓名
	 * @param page	当前页数
	 * @param limit	每页显示条数
	 * @return 
	   
	 * String    
	 *
	 * @author lujinpeng
	 * @createDate 2018年12月24日-下午2:21:49
	 */
	@RequestMapping(value = "findFiduciaryLoanByPhoneOrName", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String findFiduciaryLoanByPhoneOrName (@RequestParam(value = "phone", required = false) String phone, @RequestParam(value = "name", required = false) String name,
			Integer page, Integer limit) {
		int code = 1, count = 0;
		String msg = "成功";
		List<FiduciaryLoan> list = null;
		page = page <= 1 ? 0 : (page - 1) * limit;		
		User user = (User)SecurityUtils.getSubject().getPrincipal();
		Long user_id = user.getId();
		
		try {
			// 如果是超级管理员
			if (user.getId() == 3) {
				user_id = null;
			}
			list = spotRecordService.findFiduciaryLoanByPhoneOrName(phone, name, user_id, page, limit);
			count = spotRecordService.findFiduciaryLoanByPhoneOrName(phone, name, user_id, null, null).size();
			
		} catch (Exception e) {
			e.printStackTrace();
			code = -1;
			msg = "系统异常";
		}		
		
		return JsonUtil.getResponseJson(code, msg, count, list);
	}
	
	/**
	 * 通过电话或者姓名搜索车贷信息
	 *
	 * @Title: findVehicleMortgageByPhoneOrName
	
	 * @description 
	 *
	 * @param phone 电话
	 * @param name	抵押人姓名
	 * @param page	当前页数
	 * @param limit	每页显示条数
	 * @return 	   
	 * String    
	 *
	 * @author lujinpeng
	 * @createDate 2018年12月24日-下午2:21:49
	 */
	@RequestMapping(value = "findVehicleMortgageByPhoneOrName", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String findVehicleMortgageByPhoneOrName (@RequestParam(value = "phone", required = false) String phone, @RequestParam(value = "name", required = false) String name,
			Integer page, Integer limit) {
		int code = 1, count = 0;
		String msg = "成功";
		List<VehicleMortgage> list = null;
		page = page <= 1 ? 0 : (page - 1) * limit;		
		User user = (User)SecurityUtils.getSubject().getPrincipal();
		Long user_id = user.getId();
		
		try {
			// 如果是超级管理员
			if (user.getId() == 3) {
				user_id = null;
			}
			list = spotRecordService.findVehicleMortgageByPhoneOrName(phone, name, user_id, page, limit);
			count = spotRecordService.findVehicleMortgageByPhoneOrName(phone, name, user_id, null, null).size();
			
		} catch (Exception e) {
			e.printStackTrace();
			code = -1;
			msg = "系统异常";
		}		
		
		return JsonUtil.getResponseJson(code, msg, count, list);
	}
	
}
