package com.antifraud.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.antifraud.entity.FiduciaryLoan;
import com.antifraud.entity.HousePropertyMortgage;
import com.antifraud.entity.User;
import com.antifraud.entity.VehicleMortgage;
import com.antifraud.service.PhotoService;
import com.antifraud.service.VehicleMortgageService;
import com.antifraud.utils.DesUtil;
import com.antifraud.utils.JsonUtil;
import com.antifraud.utils.MD5Encrypt;
import com.antifraud.utils.ResultInfo;

/**
 * 
 * 
 * @ClassName: VehicleMortgageController
 * 
 * @description 车辆质押抵押
 * 
 * @author caoyaru
 * 
 * @createDate 2018年10月8日-下午4:03:30
 */
@Controller
@RequestMapping("/VehicleMortgage")
public class VehicleMortgageController {

	private Logger logger = Logger.getLogger(VehicleMortgageController.class);

	@Autowired
	private VehicleMortgageService vehicleMortgageService;
	@Autowired
	private PhotoService photoService;

	int code = 1;
	String msg = "成功";

	/**
	 * 
	 * @Title: houseMoney
	 * @description 车贷金额
	 * @param @return
	 * @return String
	 * @author caoyaru
	 * @createDate 2018年11月26日-下午4:39:42
	 */
	@ResponseBody
	@RequestMapping(value = "/vehicleMoney", produces = "application/json; charset=utf-8")
	public String vehicleMoney() {
		BigDecimal vehicleMoney = new BigDecimal(0.00);
		try {
			vehicleMoney = vehicleMortgageService.vehicleMoney();
			if (vehicleMoney == null) {
				vehicleMoney = new BigDecimal(0.00);
			}
		} catch (Exception e) {
			code = -1;
			msg = "失败";
		}

		return JsonUtil.getResponseJson(code, msg, null, vehicleMoney);
	}

	/**
	 * 
	 * @Title: fiduciaryCount
	 * @description 统计车贷总数
	 * @param @return
	 * @return String
	 * @author caoyaru
	 * @createDate 2018年11月26日-上午10:07:49
	 */
	@ResponseBody
	@RequestMapping(value = "/fiduciaryCount", produces = "application/json; charset=utf-8")
	public String fiduciaryCount() {
		int count = 0;

		try {
			count = vehicleMortgageService.fiduciaryCount();

		} catch (Exception e) {
			code = -1;
			msg = "失败";
		}

		return JsonUtil.getResponseJson(code, msg, count, null);
	}

	

	/**
	 * 
	 * @Title: findByPhone
	 * @description 根据手机号查询
	 * @param @param
	 *            phone
	 * @param @return
	 * @return List<VehicleMortgage>
	 * @author qixiyao
	 * @createDate 2018年11月20日-下午4:21:55
	 */
	@ResponseBody
	@RequestMapping(value = "/findByPhone", produces = "application/json; charset=utf-8")
	public Map<String, Object> findByPhone(String _a, String _s, String _t) {
		List<VehicleMortgage> Phone = null;
		Map<String, Object> map = new HashMap<>();
		
		/**参数解密,后加的ZhaoSong*/
		try {
			String str = DesUtil.getrechargeMember(_a, _t);
			if (str != "") {
				String md5 = MD5Encrypt.MD5Encode(str);
				if (md5.equals(_s)) {
					Map<String, String> data = DesUtil.map(str);
					Long user_id = Long.parseLong(data.get("user_id"));
					String phone = data.get("phone");
					Phone = vehicleMortgageService.findByPhone(user_id, phone);
					if (Phone == null) {
						code = -1;
						msg = "没有查到该手机号";
					} else {
						code = 1;
						msg = "查询成功";
					}
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
			map.put("msg", "系统异常");
		}

		logger.info(msg);
		map.put("code", code);
		map.put("msg", msg);
		map.put("data", Phone);

		return map;

	}

	/**
	 * 
	 * @Title: findByUserId
	 * @description 根据user_id查询全部
	 * @param @param
	 *            user_id
	 * @param @return
	 * @return Map
	 * @author qixiyao
	 * @createDate 2018年11月7日-下午1:56:05
	 */
	@ResponseBody
	@RequestMapping(value = "/findByUserId", produces = "application/json; charset=utf-8")
	public Map<String, Object> findByUserId(String _a, String _s, String _t) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// DES解密
			String str = DesUtil.getrechargeMember(_a, _t);
			System.out.println("str : -------->" + str);
			if (str != "") {
				String st = MD5Encrypt.MD5Encode(str);
				System.out.println("st :  " + st);
				if (st.equals(_s)) {
					Map<String, String> data = DesUtil.map(str);
					Integer page = Integer.parseInt(data.get("page"));
					Long user_id = Long.parseLong(data.get("user_id"));
					Integer limit = Integer.parseInt(data.get("limit"));
					System.out.println("page : " + page + " limit : " + limit + " user_id :" + user_id);
					if (page == null || page <= 0) {
						page = 1;
					}
					page = (page - 1) * limit;
					List<VehicleMortgage> userId = null;
					userId = vehicleMortgageService.findByUserId(user_id, page, limit);
					map.put("code", 1);
					map.put("msg", "查询成功");
					map.put("data", userId);

					System.out.println("map" + map);
				}else {
					map.put("code", -1);
					map.put("msg", "签名错误，您的访问数据非法");

				}
			}else {
				map.put("code", -1);
				map.put("msg", "网络超时您的网络不行");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", -1);
			map.put("msg", "查询失败");
		}

		return map;
	}

	/**
	 *
	 * @Title: addVehicleMortgage
	 * @description 车辆质押抵押的保存方法
	 * @param @param
	 *            vehicleMortgage
	 * @param @return
	 * @return Map<String,Object>
	 * @author caoyaru
	 * @createDate 2018年10月11日-上午9:20:16
	 */
	@SuppressWarnings("null")
	@ResponseBody
	@RequestMapping(value = "/addVehicleMortgage", produces = "application/json; charset=utf-8")
	public Map<String, Object> addVehicleMortgage(String _a, String _s, String _t) {
		int code = 1;
		String msg = null;
		Map<String, Object> map = new HashMap<>();
		/**参数解密,后加的ZhaoSong*/
		try {
			String str = DesUtil.getrechargeMember(_a, _t);
			if (str != "") {
				String md5 = MD5Encrypt.MD5Encode(str);
				if (md5.equals(_s)) {
					Map<String, String> data = DesUtil.map(str);
					VehicleMortgage vehileMortgage = new VehicleMortgage();
					if (data.get("user_id") != null && !data.get("user_id").equals("")) {
						vehileMortgage.setUser_id(Long.parseLong(data.get("user_id")));
					}
					if (data.get("licence_plate") != null && !data.get("licence_plate").equals("")) {
						vehileMortgage.setLicence_plate(data.get("licence_plate"));
					}
					if (data.get("name") != null && !data.get("name").equals("")) {
						vehileMortgage.setName(data.get("name"));
					}
					if (data.get("phone") != null && !data.get("phone").equals("")) {
						vehileMortgage.setPhone(data.get("phone"));
					}
					if (data.get("id_number") != null && !data.get("id_number").equals("")) {
						vehileMortgage.setId_number(data.get("id_number"));
					}
					if (data.get("type") != null && !data.get("type").equals("")) {
						vehileMortgage.setType(Integer.parseInt(data.get("type")));
					}
					if (data.get("emergency_name") != null && !data.get("emergency_name").equals("")) {
						vehileMortgage.setEmergency_name(data.get("emergency_name"));
					}
					if (data.get("emergency_phone") != null && !data.get("emergency_phone").equals("")) {
						vehileMortgage.setEmergency_phone(data.get("emergency_phone"));
					}
					if (data.get("apply_for_limit") != null && !data.get("apply_for_limit").equals("")) {
						BigDecimal apply_for_limit = new BigDecimal(data.get("apply_for_limit"));
						System.out.println("apply_for_limit--------->>" + apply_for_limit);
						vehileMortgage.setApply_for_limit(apply_for_limit);
					}
					if (data.get("apply_for_deadline") != null && !data.get("apply_for_deadline").equals("")) {
						vehileMortgage.setApply_for_deadline(data.get("apply_for_deadline"));
					}
					if (data.get("purpose_of_loan") != null && !data.get("purpose_of_loan").equals("")) {
						vehileMortgage.setPurpose_of_loan(data.get("purpose_of_loan"));
					}
					if (data.get("gender") != null && !data.get("gender").equals("")) {
						vehileMortgage.setGender(Integer.parseInt(data.get("gender")));
					}
					if (data.get("email") != null && !data.get("email").equals("")) {
						vehileMortgage.setEmail(data.get("email"));
					}
					if (data.get("spouse_identification_name") != null
							&& !data.get("spouse_identification_name").equals("")) {
						vehileMortgage.setSpouse_identification_name(data.get("spouse_identification_name"));
					}
					if (data.get("spouse_identification_number") != null
							&& !data.get("spouse_identification_number").equals("")) {
						vehileMortgage.setSpouse_identification_number(data.get("spouse_identification_number"));
					}
					if (data.get("spousal_work_unit") != null && !data.get("spousal_work_unit").equals("")) {
						vehileMortgage.setSpousal_work_unit(data.get("spousal_work_unit"));
					}
					if (data.get("relative_contact_name") != null && !data.get("relative_contact_name").equals("")) {
						vehileMortgage.setRelative_contact_name(data.get("relative_contact_name"));
					}
					if (data.get("relative_contact_number") != null
							&& !data.get("relative_contact_number").equals("")) {
						vehileMortgage.setRelative_contact_number(data.get("relative_contact_number"));
					}
					if (data.get("account_opening_time") != null && !data.get("account_opening_time").equals("")) {
						vehileMortgage.setAccount_opening_time(Integer.parseInt(data.get("account_opening_time")));
					}
					if (data.get("emergency_relation") != null && !data.get("emergency_relation").equals("")) {
						vehileMortgage.setEmergency_relation(Integer.parseInt(data.get("emergency_relation")));
					}
					if (data.get("domestic_relation") != null && !data.get("domestic_relation").equals("")) {
						vehileMortgage.setDomestic_relation(Integer.parseInt(data.get("domestic_relation")));
					}
					if (data.get("age") != null && !data.get("age").equals("")) {
						vehileMortgage.setAge(Integer.parseInt(data.get("age")));
					}
					if (data.get("marital_status") != null && !data.get("marital_status").equals("")) {
						vehileMortgage.setMarital_status(Integer.parseInt(data.get("marital_status")));
					}
					if (data.get("education") != null && !data.get("education").equals("")) {
						vehileMortgage.setEducation(Integer.parseInt(data.get("education")));
					}
					if (data.get("diploma") != null && !data.get("diploma").equals("")) {
						vehileMortgage.setDiploma(Integer.parseInt(data.get("diploma")));
					}
					if (data.get("home_phone") != null && !data.get("home_phone").equals("")) {
						vehileMortgage.setHome_phone(data.get("home_phone"));
					}
					if (data.get("business_phone_number") != null && !data.get("business_phone_number").equals("")) {
						vehileMortgage.setBusiness_phone_number(data.get("business_phone_number"));
					}
					if (data.get("mailing_address") != null && !data.get("mailing_address").equals("")) {
						vehileMortgage.setMailing_address(data.get("mailing_address"));
					}
					if (data.get("permanent_residence_address") != null
							&& !data.get("permanent_residence_address").equals("")) {
						vehileMortgage.setPermanent_residence_address(data.get("permanent_residence_address"));
					}if (data.get("home_address") != null && !data.get("home_address").equals("")) {
						vehileMortgage.setHome_address(data.get("home_address"));
					}
					
					if(data.get("customs_type")!=null&&!data.get("customs_type").equals("")){
						vehileMortgage.setCustoms_type(Integer.parseInt(data.get("customs_type")));
					}

					Date date = new Date();
					vehileMortgage.setCreate_time(date);
					vehileMortgage.setUpdate_time(date);
					Integer customs_type = vehileMortgage.getCustoms_type();
					/*if (vehileMortgage.getType() == 0) {
						vehileMortgage.setBusiness_type(1);
						vehileMortgage.setCustoms_type(customs_type);
						map.put("code", 1);
						map.put("msg", "保存成功");
						vehicleMortgageService.addVehicleMortgage(vehileMortgage);
					}*/
				
						vehileMortgage.setBusiness_type(1);
						vehileMortgage.setCustoms_type(customs_type);

						List<User> user = vehicleMortgageService.getassessmensId();
						List<VehicleMortgage> lastid = vehicleMortgageService.lastid();
						Long[] longs = new Long[user.size()];
						for (int a = 0; a < user.size(); a++) {
							longs[a] = user.get(a).getId();
						}
						if (lastid.size() == 0 || lastid.get(0).getAssessmens() == null) {
							vehileMortgage.setAssessmens(longs[0]);
						} else {

							long id = lastid.get(0).getAssessmens();

							for (int a = 0; a < user.size(); a++) {

								if (longs[a].equals(id)) {
									if (a + 1 >= user.size()) {
										vehileMortgage.setAssessmens(longs[0]);
									} else {
										vehileMortgage.setAssessmens(longs[a + 1]);
									}

								}
							}
						}
						int result = vehicleMortgageService.addVehicleMortgage(vehileMortgage);
						System.out.println("vehicleMortgage" + vehileMortgage);
						List<VehicleMortgage> findByUserId = vehicleMortgageService
								.findByUserId(vehileMortgage.getUser_id(), null, null);
						map.put("code", 1);
						map.put("msg", "提交成功");
						map.put("data", result);
						map.put("entry_number", findByUserId.get(0).getEntry_number());
					

				}else {
					map.put("code", -1);
					map.put("msg", "签名错误，您的访问数据非法");

				}
			}else {
				map.put("code", -1);
				map.put("msg", "网络超时您的网络不行");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", -1);
			map.put("msg", "失败！！");
		}
		return map;
	}

	/**
	 * 
	 * @Title: updateVehicleMortgage
	 * @description 根据ID修改
	 * @param @param
	 *            id
	 * @return Map<String,Object>
	 * @author caoyaru
	 * @createDate 2018年10月8日-下午4:13:28
	 */
	@ResponseBody
	@RequestMapping(value = "/updateVehicleMortgage", produces = "application/json; charset=utf-8")
	public String updateVehicleMortgage(VehicleMortgage vehicleMortgage) {
		int type = vehicleMortgage.getType();
		if (type == 0) {
			vehicleMortgageService.updateVehicleMortgage(vehicleMortgage);
			code = 1;
			msg = "修改成功";
		}else if (type == 1) {
			code = -1;
			msg = "报单已提交，不可修改";
		}
		return JsonUtil.getResponseJson(code, msg, null, null);

	}

	/**
	 *
	 * @Title: findAllVehicleMortgage
	 * @description 查询全部记录
	 * @param @return
	 * @return Map<String,Object>
	 * @author caoyaru
	 * @createDate 2018年10月11日-上午9:21:01
	 */
	@ResponseBody
	@RequestMapping(value = "/findAllVehicleMortgage", produces = "application/json; charset=utf-8")
	public Map<String, Object> findAllVehicleMortgage(HttpSession session,Integer page, Integer limit) {
		User user = (User)session.getAttribute("user");
		Long user_id = (Long)session.getAttribute("user_id");
		int code = 1;
		String msg = null;
		List<VehicleMortgage> result = null;
		Map<String, Object> map = new HashMap<>();
		if (page == null || page <= 0) {
			page = 1;
		}
		page = (page - 1) * limit;
		try {
			
			if(user.getPost().equals("超级管理员")||user.getPost().equals("业务主管")){
				user_id= null;
				result = vehicleMortgageService.findByLimit(null, null, page, limit);
				if (result == null) {
					msg = "查无信息";
				} else {
					msg = "查询成功";
				}
			}else{
				result = vehicleMortgageService.findByLimit(user_id, null, page, limit);
				if (result == null) {
					msg = "查无信息";
				} else {
					msg = "查询成功";
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			code = -1;
			msg = "系统异常";
			logger.info(e);
		}
		logger.info(msg);
		map.put("code", code);
		map.put("msg", msg);
		map.put("data", result);
		map.put("count", vehicleMortgageService.getCount(user_id, null, null, null));
		return map;
	}

	/**
	 *
	 * @Title: selectIdVehicleMortgage
	 * @description 根据主键ID查询
	 * @param @param
	 *            id
	 * @param @return
	 * @return Map<String,Object>
	 * @author caoyaru
	 * @createDate 2018年10月11日-上午9:21:29
	 */
	@ResponseBody
	@RequestMapping(value = "/selectIdVehicleMortgage", produces = "application/json; charset=utf-8")
	public String selectIdVehicleMortgage(Long id) {
		int code = 1;
		String msg = null;
		VehicleMortgage result = null;
		String queryImage = null;
		try {
			result = vehicleMortgageService.selectIdVehicleMortgage(id);
			if (result == null) {
				msg = "查无信息";
			} else {
				msg = "查询成功";
			}
		} catch (Exception e) {
			code = -1;
			msg = "系统异常";
			logger.info(e);
		}
		logger.info(msg);
		return JsonUtil.getResponseJson(code, msg, null, result);
	}

	/**
	 * 
	 * @Title: deleById
	 * @description 根据id删除
	 * @param @param
	 *            id
	 * @param @return
	 * @return Map<String,Object>
	 * @author qixiyao
	 * @createDate 2018年10月31日-上午10:24:22
	 */
	@ResponseBody
	@RequestMapping(value = "/deleById", produces = "application/json; charset=utf-8")
	public String deleById(Long id) {
		int code = 1;
		String msg = null;
		try {
			int result = vehicleMortgageService.deleById(id);
			if (result > 0) {
				code = 1;
				msg = "删除成功";
			} else {
				code = -1;
				msg = "删除失败";
			}
		} catch (Exception e) {
			code = 2;
			msg = "系统异常";
			logger.info(e);
		}
		logger.info(msg);
		return JsonUtil.getResponseJson(code, msg, null, null);
	}

	/**
	 * 
	 * @Title: ResultVehicleMortgage
	 * @description 车辆评估
	 * @param @return
	 * @return Map<String,Object>
	 * @author qixiyao
	 * @createDate 2018年10月15日-下午1:56:17
	 */
	@ResponseBody
	@RequestMapping(value = "/ResultVehicleMortgage", produces = "application/json; charset=utf-8")
	public String ResultVehicleMortgage(VehicleMortgage vehicleMortgage) {

		Long id = vehicleMortgage.getId();
		Integer status = vehicleMortgage.getStatus();
		String remark = vehicleMortgage.getRemark();
		//如果备注不为空表示不通过
		if (remark != null ) {
			vehicleMortgage.setStatus(1);
			code = -1;
			msg = "资产评估不通过";
		}else{
			vehicleMortgage.setStatus(2);
			vehicleMortgage.setAffirm_time(new Date());
			vehicleMortgageService.updateTimeV(vehicleMortgage.getEntry_number());
			code = 1;
			msg = "资产评估通过";
		}
		vehicleMortgageService.updatefield(vehicleMortgage);
		return JsonUtil.getResponseJson(code, msg, null, null);
	}

	/**
	 * 车辆抵押业务反馈列表
	 *
	 * @Title: findVehicleMortgage
	 * @description
	 *
	 * @param page
	 *            当前页
	 * @param limit
	 *            每页显示条数
	 * @param @return
	 * @return String
	 *
	 * @author lujinpeng
	 * @createDate 2018年10月31日-下午4:45:48
	 */
	@RequestMapping(value = "findVehicleMortgage", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String findVehicleMortgage(HttpSession session, Integer page, Integer limit) {
		int code=1;
			String msg="成功";
		int count = 0;
		List<VehicleMortgage> vehicleMortgageList = null;
		List<VehicleMortgage> list = new ArrayList<>();
		User user = (User) session.getAttribute("user");
		if (page == null || page <= 0) {
			page = 1;
		}
		page = (page - 1) * limit;

		try {
			if (user.getPost().equals("超级管理员")) {
				/** 如果是超级管理员则查询全部 */
				vehicleMortgageList = vehicleMortgageService.findByLimit(null, null, page, limit);
			} else {
				/** 显示当前业务员所有报单数据 */
				vehicleMortgageList = vehicleMortgageService.findByLimit(user.getId(), null, page, limit);
			}

			if (vehicleMortgageList.size() > 0) {
				for (VehicleMortgage vm : vehicleMortgageList) {
					if (vm.getStatus() != null) {
						list.add(vm);
					}
				}
				count = list.size();
			}

			if (count == 0) {
				msg = "无数据";
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("系统异常");
			code = -1;
			msg = "系统异常";
		}
		return JsonUtil.getResponseJson(code, msg, count, list);

	}

	/**
	 * 车辆质押抵押业务反馈
	 *
	 * @Title: VehicleMortgageBusFeedback
	 * @description
	 *
	 * @param @param
	 *            vehicleMortgage
	 * @param @param
	 *            remark 备注
	 * @param @return
	 * @return String
	 *
	 * @author lujinpeng
	 * @createDate 2018年10月29日-上午10:28:58
	 */
	@RequestMapping(value = "VehicleMortgageBusFeedback", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String VehicleMortgageBusFeedback(VehicleMortgage vehicleMortgage, String remark) {
		System.out.println("lastid");
		int result = 0;
		/** 备注为空则视为通过 */
		if (remark == null || "".equals(remark)) {
			vehicleMortgage.setStatus(3);
			
			/**资产评估通过,将报单分配给风控内勤人员ZhaoSong*/
			// 所有内勤id 包含风控主管
			List<User> user = vehicleMortgageService.getassessmensIdV();
			System.out.println(user.size());
			// 倒数第二条报单
			List<VehicleMortgage> lastid = vehicleMortgageService.lastidV();

			Long[] longs = new Long[user.size()];
			for (int a = 0; a < user.size(); a++) {
				longs[a] = user.get(a).getId();
			}

			if (lastid.size() == 0 || lastid.get(0).getManagement() == null) {
				vehicleMortgage.setManagement(longs[0]);
			} else {

				long id = lastid.get(0).getManagement();

				for (int a = 0; a < user.size(); a++) {

					if (longs[a].equals(id)) {
						if (a + 1 >= user.size()) {
							vehicleMortgage.setManagement(longs[0]);
						} else {
							vehicleMortgage.setManagement(longs[a + 1]);
						}

					}
				}
			}

		} else {
			/** 备注不为空则视为不通过，修改进程状态为1（不通过） */
			vehicleMortgage.setStatus(1);
			vehicleMortgage.setRemark(remark);
		}

		try {
			vehicleMortgageService.updateTimeV(vehicleMortgage.getEntry_number());
			result = vehicleMortgageService.updateStatusAndRemark(vehicleMortgage.getId(), vehicleMortgage.getStatus(),
					remark);

			int row = vehicleMortgageService.updateManagementId(vehicleMortgage.getId(),
					vehicleMortgage.getManagement());
			if (result <= 0) {
				logger.info("修改失败！");
				code = result;
				msg = "修改失败";
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("信用贷款业务反馈失败！");
			code = -1;
			msg = "系统异常";
		}
		msg = "修改成功";

		return JsonUtil.getResponseJson(code, msg, null, result);
	}

	/**
	 * 通过id查询车辆抵押详细信息
	 *
	 * @Title: getDetailsById
	 * @description
	 *
	 * @param @param
	 *            id
	 * @param @return
	 * @return String
	 *
	 * @author lujinpeng
	 * @createDate 2018年11月2日-下午5:24:57
	 */
	@RequestMapping(value = "getDetailsById", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getDetailsById(Long id) {
		VehicleMortgage vehicleMortgage = null;
		try {
			vehicleMortgage = vehicleMortgageService.selectIdVehicleMortgage(id);
			if (vehicleMortgage == null) {
				msg = "无数据";
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("系统异常");
			code = -1;
			msg = "系统异常";
		}

		return JsonUtil.getResponseJson(code, msg, null, vehicleMortgage);
	}

	/**
	 * 根据手机号模糊查询，手机号为空则查询全部（业务报单，资产评估，业务反馈模块均可使用）
	 *
	 * @Title: listAllAndPhone
	 * 
	 * @description
	 *
	 * @param session
	 * @param phone
	 *            借款人电话
	 * @param temp
	 *            模块类型
	 * @param page
	 *            当前页
	 * @param limit
	 *            每页显示条数
	 * @param @return
	 * 
	 * @return String
	 *
	 * @author lujinpeng
	 * @createDate 2018年12月6日-下午4:37:05
	 */
	@ResponseBody
	@RequestMapping(value = "findAllAndPhone", produces = "application/json; charset=utf-8")
	public String findAllAndPhone(HttpSession session, @RequestParam(value = "phone", required = false) String phone,
			String temp, Integer page, Integer limit) {
		int code = 1;
		String msg= "成功";
		int count = 0;
		Integer type = null;
		Integer status = null;
		if (page == null || page <= 0) {
			page = 1;
		}
		page = (page - 1) * limit;
		List<VehicleMortgage> result = null;
		List<VehicleMortgage> list = new ArrayList<>();
		User user = (User) session.getAttribute("user");

		try {
			if (temp.equals("businessFeedback")) {
				/** 业务反馈模块 */
				if (phone == null || phone.equals("")) {
					status = null;
				}
			} else if (temp.equals("printingContract")) {
				/** 打印合同模块 */
				status = 6;
			}
			if (user.getPost().equals("超级管理员") || user.getPost().indexOf("评估师") > 0) {
				result = vehicleMortgageService.listAllAndPhone(phone, null, type, status, page, limit);
				count = vehicleMortgageService.getCount(null, status, phone, type);
			} else {
				result = vehicleMortgageService.listAllAndPhone(phone, user.getId(), type, status, page, limit);
				count = vehicleMortgageService.getCount(user.getId(), status, phone, type);
			}
			if (result.size() > 0 && temp.equals("businessFeedback")) {
				for (VehicleMortgage vm : result) {
					if (vm.getStatus() != null) {
						list.add(vm);
					}
				}
				result = list;
				count = list.size();
			}
			if (count == 0) {
				msg = "查无信息";
			}
		} catch (Exception e) {
			e.printStackTrace();
			code = -1;
			msg = "系统异常";
			logger.info(e);
		}

		return JsonUtil.getResponseJson(code, msg, count, result);
	}

	/**
	 * 
	 *
	 * @Title: findByManagement
	 *
	 * @description 根据风控人员Id(management)查询状态值(status)为3(业务反馈通过)的数据
	 *
	 * @param @param
	 *            management
	 * @param @param
	 *            page
	 * @param @param
	 *            limit
	 * @param @return
	 * 
	 * @return String
	 *
	 * @author yaozijun
	 *
	 * @createDate 2018年11月22日
	 */
	@RequestMapping(value = "findByManagement", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String findByManagement(Long management, Integer page, Integer limit) {
		List<VehicleMortgage> result = null;
		int count = 0;
		try {
			if (page == null || page <= 0) {
				page = 1;
			}
			page = (page - 1) * limit;
			result = vehicleMortgageService.findByManagement(management, page, limit);
			count = vehicleMortgageService.findByManagementCount(management);

			if (result.size() == 0) {
				msg = "查无信息";
			} else {
				msg = "查询成功";
			}
		} catch (Exception e) {
			e.printStackTrace();
			code = -1;
			msg = "系统异常";
			logger.info(e);
		}
		logger.info(msg);

		return JsonUtil.getResponseJson(code, msg, count, result);
	}

	/**
	 * @Title: updateStatus
	 * @description 修改车辆报单状态
	 * @param entry_number
	 *            车辆抵押报单编号
	 * @param status
	 *            车辆抵押报单状态
	 * @return HashMap<String,Object> 返回处理结果
	 * @author ZhaoSong
	 * @createDate 2018年11月22日
	 */

	// TODO
	@RequestMapping("/updateVehicleStatus")
	@ResponseBody
	public HashMap<String, Object> updateStatus(String _a, String _s, String _t) {
		HashMap<String, Object> result = new HashMap<String,Object>();
		try {
			String str = DesUtil.getrechargeMember(_a, _t);
			if (!str.equals("")) {
				String md5 = MD5Encrypt.MD5Encode(str);
				if (md5.equals(_s)) {
					Map<String, String> data = DesUtil.map(str);
					String entry_number = data.get("entry_number");
					Integer status = Integer.parseInt(data.get("status"));

					if (status == 3 && entry_number != null && !entry_number.equals("")) {// 如果状态为3,面审通过,将报单分配给风控外勤

						status++;
						// 根据报单编号查找到此报单
						VehicleMortgage vehicleMortgage = vehicleMortgageService.findByEntryNumber(entry_number);
						// 所有风控外勤id 包括风控主管
						List<User> user = vehicleMortgageService.getRiskManagementIdV();
						// 倒数第二条报单
						List<VehicleMortgage> lastid = vehicleMortgageService.lastidVS();

						Long[] longs = new Long[user.size()];
						for (int a = 0; a < user.size(); a++) {
							longs[a] = user.get(a).getId();
						}

						if (lastid.size() == 0 || lastid.get(0).getRisk_management() == null) {
							vehicleMortgage.setRisk_management(longs[0]);
						} else {

							long id = lastid.get(0).getRisk_management();

							for (int a = 0; a < user.size(); a++) {

								if (longs[a].equals(id)) {
									if (a + 1 >= user.size()) {
										vehicleMortgage.setRisk_management(longs[0]);
									} else {
										vehicleMortgage.setRisk_management(longs[a + 1]);
									}

								}
							}
						}

						int r = vehicleMortgageService.updateByIdStatus(vehicleMortgage.getId(), status);
						int row = vehicleMortgageService.updateRiskManagementId(vehicleMortgage.getId(),
								vehicleMortgage.getRisk_management());
						if (row > 0 && r > 0) {
							vehicleMortgageService.updateTimeV(entry_number);
							result.put("code", 1);
							result.put("msg", "已分配给风控外勤人员");
						} else {
							result.put("code", -1);
							result.put("msg", "分配失败");
						}
					} else {
						
						if (entry_number != null && entry_number != "") {
							if (vehicleMortgageService.findByEntryNumber(entry_number) != null) {
								status += 1;
								int row = vehicleMortgageService.updateStatus(entry_number, status);
								if (row != 0) {
									result.put("code", 1);
									result.put("msg", "修改状态成功");
								}
							}
						} else {
							result.put("code", -1);
							result.put("msg", "查无此报单编");
						}
					}
				}else {
					result.put("code", -1);
					result.put("msg", "签名错误，您的访问数据非法");

				}
			}else {
				result.put("code", -1);
				result.put("msg", "网络超时您的网络不行");
			}
		} catch (Exception e) {
			result.put("code", -1);
			result.put("msg", "系统异常");
		}

		return result;
	}

	/**
	 * @Title: refuseToPass
	 * @description 拒绝通过的方法(拒绝通过后,订单状态变为1,,不通过,但是订单不会删除)
	 * @param @param
	 *            entry_number 信贷报单编号
	 * @param @param
	 *            status 状态值
	 * @return HashMap<String,Object> 处理结果
	 * @author ZhaoSong
	 * @createDate 2018年11月29日
	 */
	@RequestMapping("/refuseToPassVehicle")
	@ResponseBody
	public HashMap<String, Object> refuseToPass(String _a, String _s, String _t) {

		HashMap<String, Object> result = new HashMap<String, Object>();

		String entry_number = null;
		int status = 0;
		String remark = null;

		try {
			String str = DesUtil.getrechargeMember(_a, _t);
			if (!str.equals("")) {
				String md5 = MD5Encrypt.MD5Encode(str);
				if (md5.equals(_s)) {
					Map<String, String> data = DesUtil.map(str);
					entry_number = data.get("entry_number");
					status = Integer.parseInt(data.get("status"));
					remark = data.get("remark");
				}else {
					result.put("code", -1);
					result.put("msg", "签名错误，您的访问数据非法");

				}
			}else {
				result.put("code", -1);
				result.put("msg", "网络超时您的网络不行");
			}
		} catch (Exception e) {
			result.put("code", -1);
			result.put("msg", "系统异常");
		}

		if (entry_number != null && entry_number != "") {
			if (vehicleMortgageService.findByEntryNumber(entry_number) != null) {
				status = 1;// 状态设置为1,不通过s
				int row = vehicleMortgageService.refuseToPass(entry_number, status);
				if (remark != null && !remark.equals("")) {
					// 查询到这条报单
					VehicleMortgage vehicleMortgage = vehicleMortgageService.findByEntryNumber(entry_number);
					// 将不通过理由填入此报单中
					int r = vehicleMortgageService.updateRemark(entry_number, remark);
					// 若填入成功,则返回处理结果
					if (r > 0) {
						result.put("code", 1);
						result.put("msg", "处理成功");
					}else{
						result.put("code", -1);
						result.put("msg","处理失败");
					}
				} else {
					result.put("code", -1);
					result.put("msg", "不通过理由不得为空!");
				}
				if (row > 0) {
					result.put("code", 1);
					result.put("msg", "处理完成");
				} else {
					
				}
			} else {
				result.put("code", -1);
				result.put("msg", "查无此报单");
			}

		} else {
			result.put("code", -1);
			result.put("msg", "报单编号不能为空");
		}

		return result;
	}

	
	
	
	@RequestMapping("/submitDeclarationV")
	@ResponseBody
	public ResultInfo submitDeclarationV(VehicleMortgage vehicleMortgage){
		ResultInfo result=new ResultInfo();
		vehicleMortgage.setAffirm_time(new Date());
		vehicleMortgage.setType(1);
		int row = vehicleMortgageService.submitDeclarationV(vehicleMortgage);
		if(row>0){
			result.code=1;
			result.msg="保存修改成功";
		}else{
			result.code=-1;
			result.msg="保存修改失败";
		}
		return result;
		
	}
	
	
}
