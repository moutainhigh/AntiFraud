package com.antifraud.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.Code;
import org.junit.runners.Parameterized.Parameter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.antifraud.entity.FiduciaryLoan;
import com.antifraud.entity.HousePropertyMortgage;
import com.antifraud.entity.User;
import com.antifraud.entity.VehicleMortgage;
import com.antifraud.entityVO.FiduciaryLoanVo;
import com.antifraud.service.FiduciaryLoanService;
import com.antifraud.utils.DesUtil;
import com.antifraud.utils.JsonUtil;
import com.antifraud.utils.MD5Encrypt;
import com.antifraud.utils.ResultInfo;

import net.sf.jmimemagic.Magic;

/**
 * 
 *
 * @ClassName: FiduciaryLoanController
 * 
 * @description 信用贷款
 *
 * @author qixiyao
 * @createDate 2018年10月8日-下午3:49:23
 */
@Controller
@RequestMapping("/FiduciaryLoan")
public class FiduciaryLoanController {

	private Logger logger = Logger.getLogger(FiduciaryLoanController.class);

	@Autowired
	private FiduciaryLoanService fiduciaryLoanService;

	int code = 1;
	String msg = "成功";

	/**
	 * 
	 * @Title: houseMoney
	 * @description 信贷金额
	 * @param @return
	 * @return String
	 * @author caoyaru
	 * @createDate 2018年11月26日-下午4:49:20
	 */
	@ResponseBody
	@RequestMapping(value = "/fiduciaryMoney", produces = "application/json; charset=utf-8")
	public String fiduciaryMoney() {

		BigDecimal fiduciaryMoney = new BigDecimal(0.00);
		try {
			fiduciaryMoney = fiduciaryLoanService.fiduciaryMoney();
			if (fiduciaryMoney == null) {
				fiduciaryMoney = new BigDecimal(0.00);
			}
		} catch (Exception e) {
			code = -1;
			msg = "失败";
		}

		return JsonUtil.getResponseJson(code, msg, null, fiduciaryMoney);
	}

	/**
	 * 
	 * @Title: fiduciaryCount
	 * @description 统计信用贷款总数
	 * @param @return
	 * @return String
	 * @author caoyaru
	 * @createDate 2018年11月26日-上午10:03:07
	 */
	@ResponseBody
	@RequestMapping(value = "/fiduciaryCount", produces = "application/json; charset=utf-8")
	public String fiduciaryCount() {
		int count = 0;

		try {
			count = fiduciaryLoanService.fiduciaryCount();

		} catch (Exception e) {
			code = -1;
			msg = "失败";
		}

		return JsonUtil.getResponseJson(code, msg, count, null);
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
	 * @createDate 2018年11月7日-上午11:24:22
	 */
	@ResponseBody
	@RequestMapping(value = "/findByUserId", produces = "application/json; charset=utf-8")
	public Map<String, Object> findByUserId(String _a, String _s, String _t) {
		
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			// DES解密
			String str = DesUtil.getrechargeMember(_a, _t);
			if (str != "") {
				String md5 = MD5Encrypt.MD5Encode(str);
				if (md5.equals(_s)) {
					Map<String, String> data = DesUtil.map(str);
					Integer page = Integer.parseInt(data.get("page"));
					Integer limit = Integer.parseInt(data.get("limit"));
					Long user_id = Long.parseLong(data.get("user_id"));
					System.out.println("page : " + page + " limit : " + limit + " user_id :" + user_id);

					Integer a = new Integer(1);
					List<FiduciaryLoan> userId = null;
					if (page == null || page <= 0) {
						page = 1;
					}
					page = (page - 1) * limit;

					userId = fiduciaryLoanService.findByUserId(user_id, page, limit);
					map.put("code", 1);
					map.put("msg", "查询成功");
					map.put("data", userId);

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

		/*
		 * for (HousePropertyMortgage fiduciaryLoan : userId) {
		 * System.out.println(fiduciaryLoan); }
		 */
		/*
		 * if (page == null || page <= 0) { page = 1; } if (userId.size() > 0) {
		 * msg = "查询成功"; } else { msg = "查无信息"; }
		 */

		return map;
	}

	/**
	 * 
	 * @Title: findAll
	 * @description 查询全部
	 * @param @return
	 * @return Map
	 * @author qixiyao
	 * @createDate 2018年11月6日-上午9:55:20
	 */
	@ResponseBody
	@RequestMapping(value = "/findAll", produces = "application/json; charset=utf-8")
	public Map<String, Object> findAll(HttpSession session ,Integer page, Integer limit) {
		User user = (User)session.getAttribute("user");
		Long user_id = (Long) session.getAttribute("user_id");
		List<FiduciaryLoan> result = null;
		Map<String, Object> map = new HashMap<>();
		if (page == null || page <= 0) {
			page = 1;
		}
		page = (page - 1) * limit;
		try {
			if(user.getPost().equals("超级管理员")||user.getPost().equals("业务主管")){
				user_id=null;
				result = fiduciaryLoanService.findByLimit(null, null, page, limit);
				if(result!=null){
					code=1;
					msg = "查询成功";
				}else{
					code=-1;
					msg="无数据";
				}
			}else{
				result = fiduciaryLoanService.findByLimit(user_id, null, page, limit);
				if(result!=null){
					code=1;
					msg = "查询成功";
				}else{
					code=-1;
					msg="无数据";
				}
			}
			
			code=1;
			msg = "查询成功";
		} catch (Exception e) {
			e.printStackTrace();
			code = -1;
			msg = "系统异常";
		}
		map.put("code", code);
		map.put("msg", msg);
		map.put("data", result);
		map.put("count", fiduciaryLoanService.getCount(user_id, null, null, null));
		return map;
	}

	/**
	 * 
	 * @Title: saveFiduciaryLoan
	 * @description 信用贷款表保存全部
	 * @param @param
	 *            fiduciaryLoan
	 * @param @return
	 * @return List<FiduciaryLoan>
	 * @author qixiyao
	 * @createDate 2018年10月10日-下午2:02:42
	 */

	@RequestMapping(value = "/saveFiduciaryLoan", produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<Object, Object> saveFiduciaryLoan(String _a, String _s, String _t) {
		Map<Object, Object> map = new HashMap<>();
		
		/**参数解密,后加ZhaoSong*/
		try {
			String str = DesUtil.getrechargeMember(_a, _t);
			if (str != "") {
				String md5 = MD5Encrypt.MD5Encode(str);
				if (md5.equals(_s)) {
					Map<String, String> data = DesUtil.map(str);
					FiduciaryLoan fiduciaryLoan = new FiduciaryLoan();
					if (data.get("user_id") != null && !data.get("user_id").equals("")) {
						fiduciaryLoan.setUser_id(Long.parseLong(data.get("user_id")));
					}
					if (data.get("name") != null && !data.get("name").equals("")) {
						fiduciaryLoan.setName(data.get("name"));
					}
					if (data.get("phone") != null && !data.get("phone").equals("")) {
						fiduciaryLoan.setPhone(data.get("phone"));
					}
					if (data.get("id_number") != null && !data.get("id_number").equals("")) {
						fiduciaryLoan.setId_number(data.get("id_number"));
					}
					if (data.get("type") != null && !data.get("type").equals("")) {
						fiduciaryLoan.setType(Integer.parseInt(data.get("type")));
					}
					if (data.get("emergency_name") != null && !data.get("emergency_name").equals("")) {
						fiduciaryLoan.setEmergency_name(data.get("emergency_name"));
					}
					if (data.get("emergency_phone") != null && !data.get("emergency_phone").equals("")) {
						fiduciaryLoan.setEmergency_phone(data.get("emergency_phone"));
					}
					if (data.get("apply_for_limit") != null && !data.get("apply_for_limit").equals("")) {
						BigDecimal apply_for_limit = new BigDecimal(data.get("apply_for_limit"));
						fiduciaryLoan.setApply_for_limit(apply_for_limit);
					}
					if (data.get("apply_for_deadline") != null && !data.get("apply_for_deadline").equals("")) {
						fiduciaryLoan.setApply_for_deadline(data.get("apply_for_deadline"));
					}
					if (data.get("purpose_of_loan") != null && !data.get("purpose_of_loan").equals("")) {
						fiduciaryLoan.setPurpose_of_loan(data.get("purpose_of_loan"));
					}
					if (data.get("gender") != null && !data.get("gender").equals("")) {
						fiduciaryLoan.setGender(Integer.parseInt(data.get("gender")));
					}
					if (data.get("email") != null && !data.get("email").equals("")) {
						fiduciaryLoan.setEmail(data.get("email"));
					}
					if (data.get("spouse_identification_name") != null
							&& !data.get("spouse_identification_name").equals("")) {
						fiduciaryLoan.setSpouses_name(data.get("spouse_identification_name"));
					}
					if (data.get("spouse_identification_number") != null
							&& !data.get("spouse_identification_number").equals("")) {
						fiduciaryLoan.setSpouse_identification_number(data.get("spouse_identification_number"));
					}
					if (data.get("spousal_work_unit") != null && !data.get("spousal_work_unit").equals("")) {
						fiduciaryLoan.setSpousal_work_unit(data.get("spousal_work_unit"));
					}
					if (data.get("relative_contact_name") != null && !data.get("relative_contact_name").equals("")) {
						fiduciaryLoan.setRelative_contact_name(data.get("relative_contact_name"));
					}
					if (data.get("relative_contact_number") != null
							&& !data.get("relative_contact_number").equals("")) {
						fiduciaryLoan.setRelative_contact_number(data.get("relative_contact_number"));
					}
					if (data.get("account_opening_time") != null && !data.get("account_opening_time").equals("")) {
						fiduciaryLoan.setAccount_opening_time(Integer.parseInt(data.get("account_opening_time")));
					}
					if (data.get("emergency_relation") != null && !data.get("emergency_relation").equals("")) {
						fiduciaryLoan.setEmergency_relation(Integer.parseInt(data.get("emergency_relation")));
					}
					if (data.get("domestic_relation") != null && !data.get("domestic_relation").equals("")) {
						fiduciaryLoan.setDomestic_relation(Integer.parseInt(data.get("domestic_relation")));
					}
					if (data.get("age") != null && !data.get("age").equals("")) {
						fiduciaryLoan.setAge(Integer.parseInt(data.get("age")));
					}
					if (data.get("marital_status") != null && !data.get("marital_status").equals("")) {
						fiduciaryLoan.setMarital_status(Integer.parseInt(data.get("marital_status")));
					}
					if (data.get("education") != null && !data.get("education").equals("")) {
						fiduciaryLoan.setEducation(Integer.parseInt(data.get("education")));
					}
					if (data.get("diploma") != null && !data.get("diploma").equals("")) {
						fiduciaryLoan.setDiploma(Integer.parseInt(data.get("diploma")));
					}
					if (data.get("home_phone") != null && !data.get("home_phone").equals("")) {
						fiduciaryLoan.setHome_phone(data.get("home_phone"));
					}
					if (data.get("business_phone_number") != null && !data.get("business_phone_number").equals("")) {
						fiduciaryLoan.setBusiness_phone_number(data.get("business_phone_number"));
					}
					if (data.get("mailing_address") != null && !data.get("mailing_address").equals("")) {
						fiduciaryLoan.setMailing_address(data.get("mailing_address"));
					}
					if (data.get("permanent_residence_address") != null
							&& !data.get("permanent_residence_address").equals("")) {
						fiduciaryLoan.setPermanent_residence_address(data.get("permanent_residence_address"));
					}
					if (data.get("home_address") != null && !data.get("home_address").equals("")) {
						fiduciaryLoan.setHome_address(data.get("home_address"));
					}
					if(data.get("customs_type")!=null&&!data.get("customs_type").equals("")){
						fiduciaryLoan.setCustoms_type(Integer.parseInt(data.get("customs_type")));
					}
					
					Date date = new Date();
					fiduciaryLoan.setUpdate_time(date);
					fiduciaryLoan.setCreate_time(date);
					Integer customs_type = fiduciaryLoan.getCustoms_type();
					
					
				/*	if (fiduciaryLoan.getType() == 0) {
						fiduciaryLoan.setCustoms_type(customs_type);
						fiduciaryLoan.setBusiness_type(2);
						
						map.put("code", 1);
						map.put("msg", "保存成功");
						fiduciaryLoanService.saveFiduciaryLoan(fiduciaryLoan);
					} else */
				
						fiduciaryLoan.setCustoms_type(customs_type);
						fiduciaryLoan.setBusiness_type(2);

						List<User> user = fiduciaryLoanService.getassessmensId();
						List<FiduciaryLoan> lastid = fiduciaryLoanService.lastid();
						Long[] longs = new Long[user.size()];
						for (int a = 0; a < user.size(); a++) {
							longs[a] = user.get(a).getId();
						}
						if (lastid.size() == 0 || lastid.get(0).getAssessmens() == null) {
							fiduciaryLoan.setAssessmens(longs[0]);
						} else {

							long id = lastid.get(0).getAssessmens();
							for (int a = 0; a < user.size(); a++) {

								if (longs[a].equals(id)) {
									if (a + 1 >= user.size()) {
										fiduciaryLoan.setAssessmens(longs[0]);
									} else {
										fiduciaryLoan.setAssessmens(longs[a + 1]);
									}

								}
							}
						}

						int result = fiduciaryLoanService.saveFiduciaryLoan(fiduciaryLoan);
						List<FiduciaryLoan> findByUserId = fiduciaryLoanService.findByUserId(fiduciaryLoan.getUser_id(),
								null, null);
						map.put("code", 1);
						map.put("msg", "提交成功");
						map.put("data", result);
						map.put("entry_number", findByUserId.get(0).getEntry_number());
					
				}else {
					map.put("code", -1);
					map.put("msg", "签名错误，您的访问数据非法");

				}
			} else {
				map.put("code", -1);
				map.put("msg", "网络超时您的网络不行");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", -1);
			map.put("msg", "系统异常");
		}

		return map;

	}

	/**
	 * 
	 * @Title: deleteFiduciaryLoanById
	 * @description 根据id删除
	 * @param @param
	 *            request
	 * @param @param
	 *            response
	 * @param @param
	 *            session
	 * @param @param
	 *            id
	 * @param @return
	 * @return Map<String,Object>
	 * @author qixiyao
	 * @createDate 2018年10月10日-下午2:00:40
	 */
	@RequestMapping(value = "/deleteFiduciaryLoanById", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String deleteFiduciaryLoanById(Long id) {
		try {
			int ids = fiduciaryLoanService.deleteFiduciaryLoanById(id);
			if (ids == 1) {
				msg = "删除成功";
			} else {
				msg = "删除失败";
			}
		} catch (Exception e) {
			e.printStackTrace();
			code = -1;
			msg = "系统异常";
		}
		return JsonUtil.getResponseJson(code, msg, null, null);
	}

	/**
	 * 
	 * @Title: updateFiduciaryLoanById
	 * @description 根据id修改
	 * @param @param
	 *            id
	 * @param @return
	 * @return int
	 * @author qixiyao
	 * @createDate 2018年10月10日-下午2:03:27
	 */
	@RequestMapping(value = "/updateFiduciaryLoanById", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String updateFiduciaryLoanById(FiduciaryLoan fiduciaryLoan) {
		
		
		
		
		System.out.println("修改");
		Integer type = fiduciaryLoan.getType();
		if (type == 0) {
			//报单保存后的修改方法
			int update = fiduciaryLoanService.updateFiduciaryLoanById(fiduciaryLoan);
			
		}else if (type == 1) {
			System.out.println("报单已提交，不可修改");
			code = -1;
			msg = "报单已提交，不可修改";
		}
		
		return JsonUtil.getResponseJson(code, msg, null, null);
	}

	/**
	 * 
	 * @Title: findById
	 * @description 根据id查询
	 * @param @param
	 *            id
	 * @param @return
	 * @return List<FiduciaryLoan>
	 * @author qixiyao
	 * @createDate 2018年10月10日-下午2:03:39
	 */
	@ResponseBody
	@RequestMapping(value = "/findById", produces = "application/json; charset=utf-8")
	public String findById(Long id) {
		logger.info("id" + id);
		FiduciaryLoan result = null;
		try {
			result = fiduciaryLoanService.findById(id);
			if (result == null) {
				msg = "查无此id";
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
	 * @Title: findByPhone
	 * @description 根据手机号查询
	 * @param @param
	 *            phone
	 * @param @return
	 * @return List<FiduciaryLoan>
	 * @author qixiyao
	 * @createDate 2018年10月10日-下午2:05:44
	 */
	@ResponseBody
	@RequestMapping(value = "/findByPhone", produces = "application/json; charset=utf-8")
	public Map<String, Object> findByPhone(String _a, String _s, String _t) {
		String msg = null;
		Map<String, Object> map = new HashMap<>();
		List<FiduciaryLoan> Phone = null;
		/**参数解密,后加ZhaoSong*/
		try {
			String str = DesUtil.getrechargeMember(_a, _t);
			if (str != "") {
				String md5 = MD5Encrypt.MD5Encode(str);
				if (md5.equals(_s)) {
					Map<String, String> data = DesUtil.map(str);
					Long user_id = Long.parseLong(data.get("user_id"));
					String phone = data.get("phone");
					Phone = fiduciaryLoanService.findByPhone(user_id, phone);
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
			code = -1;
			msg = "系统异常";
		}

		logger.info(msg);
		map.put("code", code);
		map.put("msg", msg);
		map.put("data", Phone);

		return map;
	}

	/**
	 * 
	 * @Title: ResultFiduciaryLoan
	 * @description 信用借贷评估
	 * @param @return
	 * @return Map<String,Object>
	 * @author qixiyao
	 * @createDate 2018年10月15日-上午11:52:52
	 */
	@ResponseBody
	@RequestMapping(value = "/ResultFiduciaryLoan", produces = "application/json; charset=utf-8")
	public String ResultFiduciaryLoan(FiduciaryLoan fiduciaryLoan) {
		Long id = fiduciaryLoan.getId();
		Integer status = fiduciaryLoan.getStatus();
		String remark = fiduciaryLoan.getRemark();
		//如果备注不为空表示不通过
		if (remark != null ) {
			fiduciaryLoan.setStatus(1);
			code = -1;
			msg = "资产评估不通过";
		}else{
			fiduciaryLoan.setStatus(2);
			//TODO
			fiduciaryLoan.setAffirm_time(new Date());
			fiduciaryLoanService.updateTimeF(fiduciaryLoan.getEntry_number());
			code = 1;
			msg = "资产评估通过";
		}
		fiduciaryLoanService.updateFiduciaryLoanById(fiduciaryLoan);
		return JsonUtil.getResponseJson(code, msg, null, null);
	}

	/**
	 * 信贷业务反馈报单列表
	 *
	 * @Title: findFiduciaryLoan
	 * @description
	 *
	 * @param page
	 *            当前页
	 * 
	 * @param limit
	 *            每页显示条数
	 * 
	 * @param @return
	 * @return String
	 *
	 * @author lujinpeng
	 * @createDate 2018年10月31日-下午4:11:19
	 */
	@RequestMapping(value = "findFiduciaryLoan", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String findFiduciaryLoan(HttpSession session, Integer page, Integer limit) {
		List<FiduciaryLoan> fiduciaryLoanList = null;
		List<FiduciaryLoan> list = new ArrayList<>();
		User user = (User) session.getAttribute("user");
		int count = 0;
		if (page == null || page <= 0) {
			page = 1;
		}
		page = (page - 1) * limit;

		try {
			if (user.getPost().equals("超级管理员")) {
				/** 如果是超级管理员则查询全部 */
				fiduciaryLoanList = fiduciaryLoanService.findByLimit(null, null, page, limit);
			} else {
				/** 显示当前业务员所有报单数据 */
				fiduciaryLoanList = fiduciaryLoanService.findByLimit(user.getId(), null, page, limit);
			}

			if (fiduciaryLoanList.size() > 0) {
				for (FiduciaryLoan fl : fiduciaryLoanList) {
					if (fl.getStatus() != null) {
						list.add(fl);
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
	 * 信用贷款业务反馈
	 *
	 * @Title: fiduciaryLoanBusFeedback
	 * @description
	 *
	 * @param fiduciaryLoan
	 * @param remark
	 *            备注
	 * 
	 * @param id
	 *            报单id
	 * 
	 * @param @return
	 * @return String
	 *
	 * @author lujinpeng
	 * @createDate 2018年10月24日-下午4:57:38
	 */
	@ResponseBody
	@RequestMapping(value = "/fiduciaryLoanBusFeedback", produces = "application/json; charset=utf-8")
	public String fiduciaryLoanBusFeedback(FiduciaryLoan fiduciaryLoan, String remark) {

		int result = 0;
		/** 备注为空则视为通过 */
		if (remark == null || "".equals(remark)) {
			fiduciaryLoan.setStatus(3);
			/**业务反馈通过,分配给风控内勤ZhaoSong*/
			// 所有内勤id 包含风控主管
			List<User> user = fiduciaryLoanService.getassessmensIdF();
			// 倒数第二条报单
			List<FiduciaryLoan> lastid = fiduciaryLoanService.lastidF();

			Long[] longs = new Long[user.size()];
			for (int a = 0; a < user.size(); a++) {
				longs[a] = user.get(a).getId();
			}

			if (lastid.size() == 0 || lastid.get(0).getManagement() == null) {
				fiduciaryLoan.setManagement(longs[0]);
			} else {

				long id = lastid.get(0).getManagement();

				for (int a = 0; a < user.size(); a++) {

					if (longs[a].equals(id)) {
						if (a + 1 >= user.size()) {
							fiduciaryLoan.setManagement(longs[0]);
						} else {
							fiduciaryLoan.setManagement(longs[a + 1]);
						}

					}
				}
			}
		} else {
			/** 备注不为空则视为不通过，修改进程状态为1（不通过） */
			fiduciaryLoan.setStatus(1);
			fiduciaryLoan.setRemark(remark);
		}

		try {
			fiduciaryLoanService.updateTimeF(fiduciaryLoan.getEntry_number());
			result = fiduciaryLoanService.updateStatusAndRemark(fiduciaryLoan.getId(), fiduciaryLoan.getStatus(),
					remark);
			System.out.println(fiduciaryLoan.getId());
			int row = fiduciaryLoanService.updateManagementId(fiduciaryLoan.getId(), fiduciaryLoan.getManagement());
			System.out.println("row : " + row);

			if (result <= 0) {
				logger.info("信用贷款业务反馈失败！");
				code = result;
				msg = "信用贷款业务反馈失败！";
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
	 * 通过id查询信贷详细信息
	 *
	 * @Title: getDetailsById
	 * @description
	 *
	 * @param id
	 *            报单id
	 * 
	 * @param @return
	 * @return String
	 *
	 * @author lujinpeng
	 * @createDate 2018年11月2日-下午5:21:25
	 */
	@RequestMapping(value = "getDetailsById", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getDetailsById(Long id) {
		FiduciaryLoan fiduciaryLoan = null;
		try {
			fiduciaryLoan = fiduciaryLoanService.findById(id);
			if (fiduciaryLoan == null) {
				msg = "无数据";
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("系统异常");
			code = -1;
			msg = "系统异常";
		}

		return JsonUtil.getResponseJson(code, msg, null, fiduciaryLoan);
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
	@RequestMapping(value = "listAllAndPhone", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String listAllAndPhone(HttpSession session, @RequestParam(value = "phone", required = false) String phone,
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
		List<FiduciaryLoan> result = null;
		List<FiduciaryLoan> list = new ArrayList<>();
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
				result = fiduciaryLoanService.listAllAndPhone(phone, null, type, status, page, limit);
				count = fiduciaryLoanService.getCount(null, status, phone, type);
			} else {
				result = fiduciaryLoanService.listAllAndPhone(phone, user.getId(), type, status, page, limit);
				count = fiduciaryLoanService.getCount(user.getId(), status, phone, type);
			}
			if (result.size() > 0 && temp.equals("businessFeedback")) {
				for (FiduciaryLoan fl : result) {
					if (fl.getStatus() != null) {
						list.add(fl);
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
	 * @Title: updateStatus
	 * @description 修改信贷报单状态
	 * @param entry_number
	 *            信贷抵押报单编号
	 * @param status
	 *            信贷抵押报单状态
	 * @return HashMap<String,Object> 返回处理结果
	 * @author ZhaoSong
	 * @createDate 2018年11月22日
	 */
	@RequestMapping("/updateFiduciaryLoanStatus")
	@ResponseBody
	public HashMap<String, Object> updateStatus(String _a, String _s, String _t) {
		HashMap<String, Object> result = new HashMap<String,Object>();
		int status = 0;
		String entry_number = null;
		try {
			String str = DesUtil.getrechargeMember(_a, _t);
			if (!str.equals("")) {
				String md5 = MD5Encrypt.MD5Encode(str);
				if (md5.equals(_s)) {
					Map<String, String> data = DesUtil.map(str);
					status = Integer.parseInt(data.get("status"));
					entry_number = data.get("entry_number");
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
		

		if (status == 3 && entry_number != null && !entry_number.equals("")) {// 如果状态为3,面审通过,将报单分配给风控外勤
			status++;
			// 根据报单编号查找到此报单
			FiduciaryLoan fiduciaryLoan = fiduciaryLoanService.findByEntryNumber(entry_number);
			// 所有风控外勤id 包括风控主管
			List<User> user = fiduciaryLoanService.getRiskManagementIdF();
			// 倒数第二条已通过面审报单
			List<FiduciaryLoan> lastid = fiduciaryLoanService.lastidFS();

			Long[] longs = new Long[user.size()];
			for (int a = 0; a < user.size(); a++) {
				longs[a] = user.get(a).getId();
			}

			if (  lastid.size() == 0||lastid.get(0).getRisk_management() == null) {
				fiduciaryLoan.setRisk_management(longs[0]);
			} else {

				long id = lastid.get(0).getRisk_management();

				for (int a = 0; a < user.size(); a++) {

					if (longs[a].equals(id)) {
						if (a + 1 >= user.size()) {
							fiduciaryLoan.setRisk_management(longs[0]);
						} else {
							fiduciaryLoan.setRisk_management(longs[a + 1]);
						}

					}
				}
			}

			int r = fiduciaryLoanService.updateStatus(entry_number, status);
			int row = fiduciaryLoanService.updateRiskManagementId(fiduciaryLoan.getId(),
					fiduciaryLoan.getRisk_management());
			if (row > 0 && r > 0) {
				fiduciaryLoanService.updateTimeF(entry_number);
				result.put("code", 1);
				result.put("msg", "已分配给风控外勤人员");
			} else {
				result.put("code", -1);
				result.put("msg", "分配失败");
			}
		} else {
			
			if (entry_number != null && !entry_number.equals("")) {
				if (fiduciaryLoanService.findByEntryNumber(entry_number) != null) {
					status += 1;
					int row = fiduciaryLoanService.updateStatus(entry_number, status);
					if (row != 0) {
						result.put("code", 1);
						result.put("msg", "修改状态成功");
					}
				}
			} else {
				result.put("code", -1);
				result.put("msg", "查无此报单");
			}
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
	@RequestMapping("/refuseToPassFiduciaryLoan")
	@ResponseBody
	public HashMap<String, Object> refuseToPass(String _a, String _s, String _t) {

		HashMap<String, Object> result = new HashMap<String, Object>();

		String entry_number = null;
		int status = 0;
		String remark = null;
		//参数解密
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
		//判断报单编号是否为空,若不为空则继续向下执行
		if (entry_number != null && entry_number != "") {
			//通过报单编号查询报单是否存在.若有查询结果,说明报单存在则继续向下执行
			if (fiduciaryLoanService.findByEntryNumber(entry_number) != null) {
				// 状态设置为1,不通过s
				status = 1;
				//将此报单的状态改为1,返回生效行数
				int row = fiduciaryLoanService.refuseToPass(entry_number, status);
				//将不通过意见保存至数据库,返回生效行数
				int r = fiduciaryLoanService.updateRemark(entry_number, remark);
				//若生效行数都大于0.说明操作成功,否则操作失败,将处理结果返回
				if (row > 0&& r>0) {
					result.put("code", 1);
					result.put("msg", "处理完成");
				} else {
					result.put("code", -1);
					result.put("msg", "处理失败");
				}
			} else {
				//若报单编号错误 ,则返回 查无此报单
				result.put("code", -1);
				result.put("msg", "查无此报单");
			}

		} else {
			//若报单编号为空,则返回报单编号不能为空
			result.put("code", -1);
			result.put("msg", "报单编号不能为空");
		}

		return result;
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
		List<FiduciaryLoan> result = null;
		int count = 0;
		try {
			if (page == null || page <= 0) {
				page = 1;
			}
			page = (page - 1) * limit;
			result = fiduciaryLoanService.findByManagement(management, page, limit);
			count = fiduciaryLoanService.findByManagementCount(management);

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
	 * @Title: submitDeclaration
	 * @description 修改并保存信贷报单
	 * @param @param housePropertyMortgage 参数对象
	 * @return ResultInfo  处理结果  
	 * @author ZhaoSong
	 * @createDate 2018年12月24日
	 */
	@RequestMapping("/submitDeclarationF")
	@ResponseBody
	public ResultInfo submitDeclaration(FiduciaryLoan fiduciaryLoan){
		ResultInfo result=new ResultInfo();
		fiduciaryLoan.setAffirm_time(new Date());
		fiduciaryLoan.setType(1);
		int row = fiduciaryLoanService.submitDeclarationF(fiduciaryLoan);
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
