package com.antifraud.service;

import java.io.IOException;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import com.antifraud.entity.FiduciaryLoan;
import com.antifraud.entity.HousePropertyMortgage;
import com.antifraud.entity.ReplenishProcedure;
import com.antifraud.entity.ReplenishProcedureImage;
import com.antifraud.entity.VehicleMortgage;
import com.antifraud.entityVO.HousePropertyMortgageVo;
import com.antifraud.entityVO.PostLoanManageVo;
import com.antifraud.entityVO.ReplenishProcedureVo;

/**
 * 
 *
 * @ClassName: ReplenishProcedureImageService
 * 
 * @description 补充手续图片表Service层
 *
 * @author zhangkai
 * @createDate 2018年10月29日
 */
public interface ReplenishProcedureImageService {

	/* 详情页多文件上传 */
	public void saveFile(MultipartFile file, String id) throws Exception;

	/* 主页通过电话查询房产报单申请表 */
	public List<PostLoanManageVo> findHousePropertyByPhone(String phone, int start, int limit);

	/* 主页通过电话查询车辆报单申请表 */
	public List<PostLoanManageVo> findVehicleByPhone(String phone, int start, int limit);

	/* 主页通过电话查询信用报单申请表 */
	public List<PostLoanManageVo> findFiduciaryByPhone(String phone, int start, int limit);

	/* 主页列表查询房产报单申请表 */
	public List<PostLoanManageVo> findAllHouseProperty(int start, int limit);

	/* 主页列表查询车辆报单申请表 */
	public List<PostLoanManageVo> findAllVehicle(int start, int limit);

	/* 主页列表查询信用报单申请表 */
	public List<PostLoanManageVo> findAllFiduciary(int start, int limit);
	
	
    public List<HousePropertyMortgage> findHousesd(int start, int limit);
	
	public List<VehicleMortgage> findVehiclesd(int start,  int limit);
	
	public List<FiduciaryLoan> findFiduciarysd( int start, int limit);

	/* 详情页修改房产报单申请表状态 */
	public int updateHousePropertyStatus(String id);

	/* 详情页修改车辆报单申请表状态 */
	public int updateVehicleStatus(String id);

	/* 详情页修改信用报单申请表状态 */
	public int updateFiduciaryStatus(String id);

	/* 详情页通过报单编号查询 */
	public ReplenishProcedureVo findReplenishProcedureByEntry_number(String id);

	/* 详情页插入补充手续信息到借款人业务信息表 */
	public int addReplenishProcedureToBusiness_massage(ReplenishProcedureVo replenishProcedureVo);

	/* 详情页插入补充手续信息到贷款人职业信息表 */
	public int addReplenishProcedureToOccupational_information(ReplenishProcedureVo replenishProcedureVo);

	/* 详情页通过报单编号修改借款人业务信息表 */
	public int updateReplenishProcedureToBusiness_massage(ReplenishProcedureVo replenishProcedureVo);

	/* 详情页通过报单编号修改贷款人职业信息表 */
	public int updateReplenishProcedureToOccupational_information(ReplenishProcedureVo replenishProcedureVo);

	/* 详情页通过报单编号修改房产报单申请表 */
	public void updateHouseProperty(ReplenishProcedureVo replenishProcedureVo);

	/* 详情页通过报单编号修改车辆报单申请表 */
	public void updateVehicle(ReplenishProcedureVo replenishProcedureVo);

	/* 详情页通过报单编号修改信用报单申请表 */
	public void updateFiduciary(ReplenishProcedureVo replenishProcedureVo);
	
    public Integer findHousesdcount();
	
	public Integer findVehiclesdcount();
	
	public Integer findFiduciarysdcount();

}
