package com.antifraud.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.antifraud.dao.FiduciaryLoanDao;
import com.antifraud.dao.HousePropertyMortgageDao;
import com.antifraud.dao.SpotRecordDao;
import com.antifraud.dao.VehicleMortgageDao;
import com.antifraud.entity.FiduciaryLoan;
import com.antifraud.entity.HousePropertyMortgage;
import com.antifraud.entity.SpotRecord;
import com.antifraud.entity.VehicleMortgage;
import com.antifraud.service.SpotRecordService;

@Service
public class SpotRecordServiceImpl implements SpotRecordService {

	@Autowired
	private SpotRecordDao spotRecordDao;

	@Autowired
	private FiduciaryLoanDao fiduciaryLoanDao;

	@Autowired
	private HousePropertyMortgageDao housePropertyMortgageDao;

	@Autowired
	private VehicleMortgageDao vehicleMortgageDao;

	@Override
	public Map saveSpotRecord(SpotRecord spotRecord) {

		spotRecord.setCreate_time(new Date());
		spotRecord.setSpot_time(new Date());
		Map map = new HashMap();

		/*
		 * List<SpotRecord> findSpotRecordByReportId =
		 * spotRecordDao.findSpotRecordByReportId(spotRecord.getReport_id());
		 * 
		 * if(findSpotRecordByReportId.size() >= 0){
		 * 
		 * map.put("code", -1); map.put("msg", "这项任务已经被办理过了");
		 * 
		 * return map;
		 * 
		 * }
		 */

		int result = spotRecordDao.insertSpotRecord(spotRecord);
		// 保存成功返回成功的map
		if (result >= 1) {
			map.put("code", 1);
			map.put("msg", "保存成功");

			return map;
		}
		// 失败返会失败的map
		map.put("code", -1);
		map.put("msg", "保存失败");
		return map;

	}

	@Override
	public Map updateSpotRecordById(SpotRecord spotRecord) {
		Map map = new HashMap();
		// 增加修改时间
		spotRecord.setUpdate_time(new Date());
		int result = spotRecordDao.updateSpotRecordById(spotRecord);
		if (result >= 1) {
			map.put("code", 1);
			map.put("msg", "修改成功");

			return map;
		}
		// 失败返会失败的map
		map.put("code", -1);
		map.put("msg", "修改失败");
		return map;

	}

	@Override
	public Map deleteSpotRecordById(Long id) {
		Map map = new HashMap();

		int result = spotRecordDao.deleteSpotRecordById(id);

		if (result >= 1) {
			map.put("code", 1);
			map.put("msg", "删除成功");

			return map;
		}
		// 失败返会失败的map
		map.put("code", -1);
		map.put("msg", "没有这条记录");
		return map;
	}

	@Override
	public Map findSpotRecordByUserId(Long user_id, int page, int limit) {
		Map map = new HashMap();
		int star = (page - 1) * limit;
		List<SpotRecord> spotRecordList = spotRecordDao.findSpotRecordByUserId(user_id, star, limit);
		Integer findSpotRecordByUserIdCount = spotRecordDao.findSpotRecordByUserIdCount();
		if (spotRecordList.size() >= 1) {
			map.put("code", 1);
			map.put("msg", "查询成功");
			map.put("count", findSpotRecordByUserIdCount);
			map.put("data", spotRecordList);
			return map;
		}
		// 失败返会失败的map
		map.put("code", -1);
		map.put("msg", "请确认该实地人员存在");
		return map;
	}

	@Override
	public Map findSpotRecordByReportId(String reportId, int page, int limit) {
		Map map = new HashMap();
		int star = (page - 1) * limit;
		List<SpotRecord> spotRecordList = spotRecordDao.findSpotRecordByReportId(reportId, star, limit);
		// 查询成功
		if (spotRecordList.size() >= 1) {
			map.put("code", 1);
			map.put("msg", "查询成功");
			map.put("count", spotRecordDao.findSpotRecordByReportIdCount());
			map.put("data", spotRecordList);
			return map;
		}else{
			map.put("code", -1);
			map.put("msg", "请核对报单编号是否正确");
			return map;
		}
		// 失败返会失败的map
	
	}

	// 分页查询
	@Override
	public Map findAllSpotRecord(int currentPage, int pageSize) {

		int recordCount = spotRecordDao.findSpotRecordCount();

		int beginPageIndex = ((currentPage - 1) * pageSize);

		List<SpotRecord> rows = spotRecordDao.findAllSpotRecord(beginPageIndex, pageSize);

		Map map = new HashMap<>();

		map.put("code", 1);
		map.put("msg", "查询成功");
		map.put("count", recordCount);
		map.put("data", rows);

		return map;
	}

	@Override
	public Map judgementResult(String reportId, int status, String remark) {
		
		Map map = new HashMap<>();
		
		if(reportId.startsWith("XY")){
			Integer rualt = fiduciaryLoanDao.updateFiduciaryLoanStatus(reportId, status,remark);
			
			if(rualt>=1){
				map.put("code", 1);
				map.put("msg", "修改成功");
			}else {
				map.put("code", -1);
				map.put("msg", "报单编号错误");
			}
			return map;
			
		}
		
		
		
		if(reportId.startsWith("FW")){
			Integer rualt = housePropertyMortgageDao.updateHousePropertyMortgageStatus(reportId, status,remark);

			if(rualt>=1){
				map.put("code", 1);
				map.put("msg", "修改成功");
			}else {
				map.put("code", -1);
				map.put("msg", "报单编号错误");
			}
			return map;
			
		}
		
		
		
        if(reportId.startsWith("CL")){
        	
        	Integer rualt = vehicleMortgageDao.updateVehicleMortgageStatus(reportId, status,remark);
			if(rualt>=1){
				map.put("code", 1);
				map.put("msg", "修改成功");
			}else {
				map.put("code", -1);
				map.put("msg", "报单编号错误");
			}
			return map;
			
		}
		
        map.put("code", -1);
		map.put("msg", "报单编号错误");
		return map;
	}

	@Override
	public Map fiandFiduciaryLoanByStatus(int status1, int status2, int page, int limit, long management, long risk_management) {
		int star = (page - 1) * limit;
		List<FiduciaryLoan> fiduciaryList = spotRecordDao.fiandFiduciaryLoanByStatus(status1, status2, star, limit, management, risk_management);
		Map map =new HashMap<>();
		
		if(fiduciaryList.size()>=1){
			map.put("code", 1);
			map.put("msg", "查询成功");
			map.put("count", spotRecordDao.fiandFiduciaryLoanByStatusCount(status1, status2, management, risk_management));
			map.put("data", fiduciaryList);
			return map;
		}else {
			map.put("code", 1);
			map.put("msg", "数据库中目前没有符合条件的数据");
			map.put("count", spotRecordDao.fiandFiduciaryLoanByStatusCount(status1, status2, management, risk_management));
			map.put("data", fiduciaryList);
			return map;
		}
	}

	@Override
	public Map fiandHousePropertyMortgageByStatus(int status1, int status2, int page, int limit, long management, long risk_management) {
		int star = (page - 1) * limit;
		List<HousePropertyMortgage> housePropertyMortgageList = spotRecordDao.fiandHousePropertyMortgageByStatus(status1, status2, star, limit,  management, risk_management);
		
        Map map =new HashMap<>();
		
		if(housePropertyMortgageList.size()>=1){
			map.put("code", 1);
			map.put("msg", "查询成功");
			map.put("count", spotRecordDao.fiandHousePropertyMortgageByStatusCount(status1, status2,  management, risk_management));
			map.put("data", housePropertyMortgageList);
			return map;
		}else {
			map.put("code", 1);
			map.put("msg", "数据库中目前没有符合条件的数据");
			map.put("count", spotRecordDao.fiandHousePropertyMortgageByStatusCount(status1, status2,  management, risk_management));
			map.put("data", housePropertyMortgageList);
			return map;
		}
	}

	@Override
	public Map fiandVehicleMortgageByStatus(int status1, int status2, int page, int limit, long management, long risk_management) {
		int star = (page - 1) * limit;
		  List<VehicleMortgage> VehicleMortgageList = spotRecordDao.fiandVehicleMortgageByStatus(status1, status2, star, limit, management,  risk_management);
		
      Map map =new HashMap<>();
		
		if(VehicleMortgageList.size()>=1){
			map.put("code", 1);
			map.put("msg", "查询成功");
			map.put("count", spotRecordDao.fiandVehicleMortgageByStatusCount(status1, status2, management,  risk_management));
			map.put("data", VehicleMortgageList);
			return map;
		}else {
			map.put("code", 1);
			map.put("msg", "数据库中目前没有符合条件的数据");
			map.put("count", spotRecordDao.fiandVehicleMortgageByStatusCount(status1, status2, management,  risk_management));
			map.put("data", VehicleMortgageList);
			return map;
		}
	}

	@Override
	public Map findAllSpotRecordfd(int beginPageIndex, int endPageIndex, String cs) {
		int recordCount = spotRecordDao.findSpotRecordCountfd(cs);

		int beginPage = ((beginPageIndex - 1) * endPageIndex);

		List<SpotRecord> rows = spotRecordDao.findAllSpotRecordfd(beginPage, endPageIndex,cs);

		Map map = new HashMap<>();

		map.put("code", 1);
		map.put("msg", "查询成功");
		map.put("count", recordCount);
		map.put("data", rows);

		return map;
	}

	@Override
	public List<SpotRecord> findSpotRecordbytrid(int trid) {
		
		return spotRecordDao.findSpotRecordbytrid(trid);
	}

	/**
	 * 通过电话或者姓名查询面审实地模块房产信息
	 */
	@Override
	public List<HousePropertyMortgage> findHouseInfoByPhoneOrName(String phone, String name, Long user_id, Integer page, Integer limit) {
		
		return spotRecordDao.findHouseInfoByPhoneOrName(phone, name, user_id, page, limit);
	}

	/**
	 * 通过电话或者姓名查询面审实地模块信贷信息
	 */
	@Override
	public List<FiduciaryLoan> findFiduciaryLoanByPhoneOrName(String phone, String name, Long user_id, Integer page,
			Integer limit) {
		
		return spotRecordDao.findFiduciaryLoanByPhoneOrName(phone, name, user_id, page, limit);
	}

	/**
	 * 通过电话或者姓名查询面审实地模块车贷信息
	 */
	@Override
	public List<VehicleMortgage> findVehicleMortgageByPhoneOrName(String phone, String name, Long user_id, Integer page,
			Integer limit) {
		
		return spotRecordDao.findVehicleMortgageByPhoneOrName(phone, name, user_id, page, limit);
	}

}
