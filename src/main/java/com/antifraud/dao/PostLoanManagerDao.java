package com.antifraud.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.antifraud.entity.PostLoanManage;
import com.antifraud.entityVO.HousePropertyMortgageVo;
import com.antifraud.entityVO.PostLoanManageVo;

/**
 * 
 *
 * @ClassName: PostLoanManagerDao

 * @description 贷后管理表Dao层
 *
 * @author zhangkai
 * @createDate 2018年10月11日
 */
public interface PostLoanManagerDao {

	/**
	 * 
	*
	 * @Title: updatePostLoanManage
	
	 * @description 详情页修改数据
	*
	 * @param @param postLoanManage
	 * @param @return 
	   
	 * @return int    
	
	 *
	 * @author zhangkai
	 * @createDate 2018年11月8日
	 */
	public int updatePostLoanManage(PostLoanManage postLoanManage);
	
	/**
	 * 
	*
	 * @Title: findPostLoanManageByRecordId
	
	 * @description 详情页通过报单编号查询
	*
	 * @param @param id
	 * @param @return 
	   
	 * @return PostLoanManage    
	
	 *
	 * @author zhangkai
	 * @createDate 2018年11月7日
	 */
	public PostLoanManageVo findPostLoanManageByRecordId(String id);
	
/**
 * 
*
 * @Title: addPostLoanManage

 * @description 详情页增加数据
*
 * @param @param postLoanManage
 * @param @return 
   
 * @return int    

 *
 * @author zhangkai
 * @createDate 2018年11月8日
 */
	public int addPostLoanManage(PostLoanManage postLoanManage);
	
	/**
	 * 
	*
	 * @Title: findHousePropertyByPhone
	
	 * @description 主页通过电话查询房产报单申请表 
	*
	 * @param @param phone
	 * @param @return 
	   
	 * @return List<PostLoanManageVo>    
	
	 *
	 * @author zhangkai
	 * @createDate 2018年11月7日
	 */
	public List<PostLoanManageVo> findHousePropertyByPhone(@Param("phone")String phone);
	
	/**
	 * 
	*
	 * @Title: findAllHouseProperty
	
	 * @description 主页列表查询房产报单申请表 
	*
	 * @param @return 
	   
	 * @return List<PostLoanManageVo>    
	
	 *
	 * @author zhangkai
	 * @createDate 2018年11月7日
	 */
	public List<PostLoanManageVo> findAllHouseProperty();
	
	/**
	 * 
	*
	 * @Title: findVehicleByPhone
	
	 * @description 主页通过电话查询车辆报单申请表 
	*
	 * @param @param phone
	 * @param @return 
	   
	 * @return List<PostLoanManageVo>    
	
	 *
	 * @author zhangkai
	 * @createDate 2018年11月7日
	 */
	public List<PostLoanManageVo> findVehicleByPhone(@Param("phone")String phone);
	
	/**
	 * 
	*
	 * @Title: findAllVehicle
	
	 * @description 主页列表查询车辆报单申请表
	*
	 * @param @return 
	   
	 * @return List<PostLoanManageVo>    
	
	 *
	 * @author zhangkai
	 * @createDate 2018年11月7日
	 */
	public List<PostLoanManageVo> findAllVehicle();
	
	/**
	 * 
	*
	 * @Title: findFiduciaryByPhone
	
	 * @description 主页通过电话查询信用报单申请表
	*
	 * @param @param phone
	 * @param @return 
	   
	 * @return List<PostLoanManageVo>    
	
	 *
	 * @author zhangkai
	 * @createDate 2018年11月7日
	 */
	public List<PostLoanManageVo> findFiduciaryByPhone(@Param("phone")String phone);
	
	/**
	 * 
	*
	 * @Title: findAllFiduciary
	
	 * @description 主页列表查询信用报单申请表
	*
	 * @param @return 
	   
	 * @return List<PostLoanManageVo>    
	
	 *
	 * @author zhangkai
	 * @createDate 2018年11月7日
	 */
	public List<PostLoanManageVo> findAllFiduciary();

	/**
	 * 
	*
	 * @Title: updateHousePropertyStatus
	
	 * @description 详情页修改房产报单申请表状态
	*
	 * @param @param id
	 * @param @return 
	   
	 * @return int    
	
	 *
	 * @author zhangkai
	 * @createDate 2018年11月9日
	 */
	public int updateHousePropertyStatus(String id);
	
	/**
	 * 
	*
	 * @Title: updateVehicleStatus
	
	 * @description 详情页修改车辆报单申请表状态
	*
	 * @param @param id
	 * @param @return 
	   
	 * @return int    
	
	 *
	 * @author zhangkai
	 * @createDate 2018年11月9日
	 */
	public int updateVehicleStatus(String id);
	
	/**
	 * 
	*
	 * @Title: updateFiduciaryStatus
	
	 * @description 详情页修改信用报单申请表状态
	*
	 * @param @param id
	 * @param @return 
	   
	 * @return int    
	
	 *
	 * @author zhangkai
	 * @createDate 2018年11月9日
	 */
	public int updateFiduciaryStatus(String id);
	
}

























