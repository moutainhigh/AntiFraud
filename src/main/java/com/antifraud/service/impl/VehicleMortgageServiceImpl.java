package com.antifraud.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.antifraud.dao.VehicleMortgageDao;
import com.antifraud.entity.User;
import com.antifraud.entity.VehicleMortgage;
import com.antifraud.entityVO.FklistVo;
import com.antifraud.entityVO.VehicleMortgageVo;
import com.antifraud.service.VehicleMortgageService;

/**
 * 

 * @ClassName: VehicleMortgageServiceImpl

 * @description 车辆质押抵押的实现类

 * @author caoyaru

 * @createDate 2018年10月8日-下午4:08:03
 */
@Service
public class VehicleMortgageServiceImpl implements VehicleMortgageService{
	
	@Autowired
	private VehicleMortgageDao vehicleMortgageDao;
	
	/**
	 * 保存,并生成报单编号
	 */
	@Override
	public int addVehicleMortgage(VehicleMortgage vehicleMortgage) {
		
		
		String lastEntry_number = null;
	try {
		     VehicleMortgage lastEntryNumber = vehicleMortgageDao.findLastEntryNumber();
			 lastEntry_number = lastEntryNumber.getEntry_number();
		
		} catch (Exception e) {
			lastEntry_number = "CL000000001";
			
		}
		String substring = lastEntry_number.substring(2);
		int number = Integer.parseInt(substring);
		number+=1;
		String zero ="";
		for (int i = 0; i < 9 - String.valueOf(number).length(); i++) {
			zero += "0";
		} 
		String entry_number = "CL"+zero+number;
		vehicleMortgage.setEntry_number(entry_number);
		int add = vehicleMortgageDao.addVehicleMortgage(vehicleMortgage);
		return add;
		
	}
	/**
	 * 根据ID修改
	 */
	@Override
	public int updateVehicleMortgage(VehicleMortgage vehicleMortgage) {
		return vehicleMortgageDao.updateVehicleMortgage(vehicleMortgage);
		
	}
	/**
	 * 查询全部记录
	 */
	@Override
	public List<VehicleMortgage> findAllVehicleMortgage() {
		
		return vehicleMortgageDao.findAllVehicleMortgage();
	}
	/**
	 * 根据主键ID查询
	 */
	@Override
	public VehicleMortgage selectIdVehicleMortgage(Long id) {
		return vehicleMortgageDao.selectIdVehicleMortgage(id);
	}
	/**
	 * 根据电话查询(业务报单，资产评估，业务反馈)
	 */
	@Override
	public List<VehicleMortgage> listAllAndPhone(String phone, Long user_id, Integer type, Integer status, Integer page, Integer size) {
		
		return vehicleMortgageDao.listAllAndPhone(phone, user_id, type, status, page, size);
	}
	/**
	 * 根据id删除
	 */
	@Override
	public int deleById(Long id) {
	
		return vehicleMortgageDao.deleById(id);
	}
	
	/**
	 * 选择性修改数据
	 */
	@Override
	public int updateVehicleMortgageSelective(VehicleMortgage vehicleMortgage) {
		
		return vehicleMortgageDao.updateVehicleMortgageSelective(vehicleMortgage);
	}
	
	/** 通过报单编号查询*/
	@Override
	public VehicleMortgage findByEntryNumber(String entry_number) {
		
		return vehicleMortgageDao.findByEntryNumber(entry_number);
	}
	
	/** 分页查询 */
	@Override
	public List<VehicleMortgage> findByLimit(Long user_id, Integer status, Integer page, Integer size) {
		
		return vehicleMortgageDao.findByLimit(user_id, status, page, size);
	}
	
	/** 查询总数 */
	@Override
	public int getCount(Long user_id, Integer status, String phone, Integer type) {

		return vehicleMortgageDao.getCount(user_id, status, phone, type);
	}
	/**
	 * 资产评估
	 */
	@Override
	public VehicleMortgage ResultVehicleMortgage(Long status) {
		return vehicleMortgageDao.ResultVehicleMortgage(status);
	}
	
	/**
	 * 通过id查询车辆抵押详细信息
	 */
	@Override
	public VehicleMortgageVo getDetailsById(Long id) {
		
		return vehicleMortgageDao.getDetailsById(id);
	}
	/**
	 * 根据user_id查询全部
	 */
	@Override
	public List<VehicleMortgage> findByUserId(Long user_id,Integer page, Integer size) {
		return vehicleMortgageDao.findByUserId(user_id, page, size);
	}
	/**
	 * 查询上标所需要的字段
	 */
	@Override
	public List<FklistVo> findListAlls(String entry_number) {
		
		return vehicleMortgageDao.findListAlls(entry_number);
	}
	/**
	 * 根据手机号查询
	 */
	@Override
	public List<VehicleMortgage> findByPhone(Long user_id,String phone) {
		return vehicleMortgageDao.findByPhone(user_id,phone);
	}
	
	/** 根据风控人员Id(management)查询状态值(status)为3(业务反馈通过)的数据 */
	@Override
	public List <VehicleMortgage> findByManagement(Long management, Integer page, Integer size) {
		
		return vehicleMortgageDao.findByManagement(management, page, size);
	}
	
	/** 统计风控人员Id(management)查询状态值(status)为3(业务反馈通过)的数据的总条数 */
	@Override
	public int findByManagementCount(Long management) {
		
		return vehicleMortgageDao.findByManagementCount(management);
	}
	/**
	 * 根据车辆报单编号修改状态值
	 */
	@Override
	public int updateStatus(String entry_number, int status) {
		int row = vehicleMortgageDao.updateStatus(entry_number, status);
		return row;
	}
	
	
	/** 统计车贷总数 */
	@Override
	public int fiduciaryCount() {
		return vehicleMortgageDao.fiduciaryCount();
	}
	
	/** 车贷金额 */
	@Override
	public BigDecimal vehicleMoney() {
		return vehicleMortgageDao.vehicleMoney();
	}
	/** 根据ID修改状态 */
	@Override
	public int updateByIdStatus(Long id, Integer status) {
		return vehicleMortgageDao.updateByIdStatus(id,status);
		
	}
	/** 修改状态和备注 */
	@Override
	public int updateStatusAndRemark(Long id, Integer status, String remark) {

		return vehicleMortgageDao.updateStatusAndRemark(id, status, remark);
	}
	/**
	 * 查询所有风控外勤人员
	 */
	@Override
	public List<User> getassessmensIdV() {
		return vehicleMortgageDao.getassessmensIdV();
	}
	/**
	 * 查询最后一条报单的风控内勤id
	 */
	@Override
	public List<VehicleMortgage> lastidV() {
		return vehicleMortgageDao.lastidV();
	}
	/**
	 * 修改风控内勤id
	 */
	@Override
	public int updateManagementId(Long id, Long managemnt) {
		return	vehicleMortgageDao.updateManagementId(id, managemnt);
	}
	/**
	 * 查询最后一条报单的风控内勤id
	 */
	@Override
	public List<VehicleMortgage> lastid() {
		// TODO Auto-generated method stub
		return vehicleMortgageDao.lastid();
	}
	/**
	 * 查询房屋评估师id
	 */
	@Override
	public List<User> getassessmensId() {
		return vehicleMortgageDao.getassessmensId();
	}
	/**
	 * 拒绝通过的方法(拒绝通过后,订单状态变为1,不通过,但是订单不会删除)
	 */
	@Override
	public int refuseToPass(String entry_number, int status) {
		return vehicleMortgageDao.refuseToPass(entry_number, status);
	}
	/**
	 * 查询所有风控外勤人员
	 */
	@Override
	public List<User> getRiskManagementIdV() {
		return vehicleMortgageDao.getRiskManagementIdV();
	}
	/**
	 * 修改风控外勤id
	 */
	@Override
	public int updateRiskManagementId(Long id, Long risk_management) {
		return vehicleMortgageDao.updateRiskManagementId(id, risk_management);
	}
	/**
	 * 查询最后一条已通过面审的风控外勤id
	 */
	@Override
	public List<VehicleMortgage> lastidVS() {
		return vehicleMortgageDao.lastidVS();
	}
	/**
	 * 通过报单编号修改备注信息
	 */
	@Override
	public int updateRemark(String entry_number, String remark) {
		return vehicleMortgageDao.updateRemark(entry_number, remark);
	}
	/**
	 * 根据报单编号修改更新时间
	 */
	@Override
	public int updateTimeV(String entry_number) {
		return vehicleMortgageDao.updateTimeV(entry_number);
	}
	/**
	 * 根据报单查询当前操作人
	 */
	@Override
	public User selectManagement(String entry_number) {
		return vehicleMortgageDao.selectManagement(entry_number);
	}
	/**
	 * 资产评估的修改字段
	 */
	@Override
	public int updatefield(VehicleMortgage vehicleMortgage) {
		// TODO Auto-generated method stub
		return vehicleMortgageDao.updatefield(vehicleMortgage);
	}
	@Override
	public int submitDeclarationV(VehicleMortgage vehicleMortgage) {
		return vehicleMortgageDao.submitDeclarationV(vehicleMortgage);
	}
	@Override
	public BigDecimal findByDate(String year, String month, String day) {
		BigDecimal bigDecimal = vehicleMortgageDao.findByDate(year, month, day);
		if(bigDecimal!=null){
			return bigDecimal;
		}else{
			return null;
		}
	}
	
	
	@Override
	public Integer findByCount(String year, String month, String day) {
		Integer count = vehicleMortgageDao.findByCount(year, month, day);
		return count;
	}
	
	
	

}
