package com.antifraud.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.antifraud.entity.HousePropertyMortgage;
import com.antifraud.entity.User;
import com.antifraud.entityVO.FklistVo;
import com.antifraud.entityVO.HousePropertyMortgageVo;

public interface HousePropertyMortgageService {
	
	/**
	 * 通过电话模糊查询，参数为空则查询全部（业务报单，资产评估，业务反馈模块均可使用）
	 *
	 * @Title: listAllAndPhone
	 * @description 
	 *
	 * @param phone 电话号码
	 * @param user_id 用户id
	 * @param type 报单状态
	 * @param status 报单进程状态
	 * @param page 当前页数
	 * @param size 每页显示条数
	 * @param @return
	 * @param @throws Exception 
	   
	 * @return List<HousePropertyMortgage>    
	 *
	 * @author lujinpeng
	 * @createDate 2018年11月19日-下午2:36:13
	 */
	List<HousePropertyMortgage> listAllAndPhone(String phone, Long user_id, Integer type, Integer status, Integer page, Integer size)throws Exception;
	
	/**
	 * 根据电话查询，无则查询全部
	 *
	 * @Title: findByPhone
	
	 * @description 
	 *
	 * @param phone 电话号码
	 * @param @return 
	   
	 * @return List<HousePropertyMortgage>    
	
	 *
	 * @author lujinpeng
	 * @createDate 2018年11月15日-下午12:44:26
	 */
	List<HousePropertyMortgage> findByPhone (Long user_id,String phone);
	
	/**
	 * 
	 * @Title: insert
	 * @description 增加的方法
	 * @param @param housePropertyMortgage
	 * @param @return  
	 * @return int    
	 * @author qixiyao
	 * @createDate 2018年11月15日-下午2:26:32
	 */
	int insert(HousePropertyMortgage housePropertyMortgage);
	
	/**
	 * 
	 * @Title: selectById
	 * @description 根据id查询
	 * @param @param id
	 * @param @return  
	 * @return HousePropertyMortgage    
	 * @author qixiyao
	 * @createDate 2018年11月15日-下午2:38:55
	 */
	HousePropertyMortgage selectById(Long id);
	/**
	 * 
	 * @Title: findAll
	 * @description 查询全部
	 * @param @param page
	 * @param @param limit
	 * @param @return  
	 * @return List<HousePropertyMortgage>    
	 * @author qixiyao
	 * @createDate 2018年11月16日-上午10:35:07
	 */
	List<HousePropertyMortgage> findAll ();
	/**
	 * 
	 * @Title: getDetailsById
	 * @description 根据id查询房产抵押详情（包括图片）
	 * @param @param id
	 * @param @return  
	 * @return HousePropertyMortgageVo    
	 * @author qixiyao
	 * @createDate 2018年11月15日-下午3:58:03
	 */
	HousePropertyMortgageVo getDetailsById(Long id);
	
	/**
	 * 
	 * @Title: updateById
	 * @description 根据id修改
	 * @param @param id
	 * @param @return  
	 * @return HousePropertyMortgage    
	 * @author qixiyao
	 * @createDate 2018年11月15日-下午3:17:30
	 */
	 HousePropertyMortgage updateById(HousePropertyMortgage housePropertyMortgage);
	/**
	 * 
	 * @Title: ResultHousePropertyMortgage
	 * @description 资产评估
	 * @param @param log
	 * @param @return  
	 * @return int    
	 * @author qixiyao
	 * @createDate 2018年10月23日-上午11:34:22
	 */
	int ResultHousePropertyMortgage(HousePropertyMortgage housePropertyMortgage);
	
	
	 /**
	  * 
	  * @Title: deleteByPrimaryKey
	  * @description 根据主键id删除
	  * @param @param id
	  * @param @return  
	  * @return int    
	  * @author qixiyao
	  * @createDate 2018年11月13日-下午1:56:24
	  */
	  int deleteById(@Param("id") Long id);
	 /**
	  * 
	  * @Title: findByUserId
	  * @description 根据user_id查询全部
	  * @param @param user_id
	  * @param @param page
	  * @param @param limit
	  * @param @return  
	  * @return List<HousePropertyMortgage>    
	  * @author qixiyao
	  * @createDate 2018年11月13日-下午2:01:15
	  */
	 List<HousePropertyMortgage> findByUserId(Integer user_id,Integer page,Integer limit);
	
	 /**
	  * 
	  * @Title: saveAll
	  * @description 保存全部
	  * @param @return  
	  * @return List<HousePropertyMortgage>    
	  * @author qixiyao
	  * @createDate 2018年10月31日-下午3:31:17
	  */
	 int saveAll(HousePropertyMortgage housePropertyMortgage);
	/**
	 * 通过报单编号查询
	 *
	 * @Title: findByEntryNumber
	 * @description 
	 *
	 * @param entry_number 报单编号
	 * @param @return    
	 * @return HousePropertyMortgage    
	 *
	 * @author lujinpeng
	 * @createDate 2018年10月29日-下午2:12:28
	 */
	HousePropertyMortgage findByEntryNumber (String entry_number);
	
	/**
	 * 分页查询
	 *
	 * @Title: findByLimit
	
	 * @description 
	 *
	 * @param user_id 用户id
	 * @param status 报单进程状态
	 * @param page 当前页数
	 * @param size 单页显示条数
	 * @param @return 
	   
	 * @return List<HousePropertyMortgage>    
	
	 *
	 * @author lujinpeng
	 * @createDate 2018年11月19日-下午2:34:08
	 */
	List<HousePropertyMortgage> findByLimit(Long user_id, Integer status, Integer page, Integer size);
	
	/**
	 * 通过条件查询总数（参数为空则查询全部条数）
	 *
	 * @Title: getCount
	
	 * @description 
	 *
	 * @param  user_id 用户id
	 * @param  status 报单进程状态
	 * @param  phone 抵押人电话
	 * @param  type 报单提交状态
	 * @param @return 
	   
	 * @return int    
	
	 *
	 * @author lujinpeng
	 * @createDate 2018年11月16日-下午1:52:46
	 */
	int getCount (Long user_id, Integer status, String phone, Integer type);
	
    /**
	 * 
	 *
	 * @Title: findByManagement
	 *
	 * @description 根据风控人员Id(management)查询状态值(status)为3(业务反馈通过)的数据
	 *
	 * @param @param management
	 * @param @param page
	 * @param @param size
	 * @param @return 
	 * 
	 * @return List<HousePropertyMortgage>    
	 *
	 * @author 姓名全拼
	 *
	 * @createDate 2018年11月26日
	 */
	List <HousePropertyMortgage> findByManagement (Long management, Integer page, Integer size);
	
	/**
	 * 
	 *
	 * @Title: findByManagementCount
	 *
	 * @description 统计风控人员Id(management)查询状态值(status)为3(业务反馈通过)的数据的总条数
	 *
	 * @param @param management
	 * @param @return 
	 * 
	 * @return int    
	 *
	 * @author 姓名全拼
	 *
	 * @createDate 2018年11月26日
	 */
	int findByManagementCount (Long management);
	/**
	 * 
	 * @Title: findListAlls
	 * @description 查询上标所需要的字段
	 * @param @param entry_number
	 * @param @return  
	 * @return List<FklistVo>    
	 * @author lichangchun
	 * @createDate 2018年12月10日-上午11:58:59
	 */
	List<FklistVo> findListAlls(String entry_number);
	

	/**
	 * @Title: updateStatus
	 * @description 根据房屋报单编号修改状态值
	 * @param entry_number 房屋报单编号
	 * @param status 状态值
	 * @return int 受影响行数   
	 * @author ZhaoSong
	 * @createDate 2018年11月22日
	 */
	int updateStatus(String entry_number,int status);
	/**
	 * 
	 * @Title: updatefield
	 * @description 资产评估的修改字段
	 * @param @param housePropertyMortgage
	 * @param @return  
	 * @return int    
	 * @author qixiyao
	 * @createDate 2018年12月10日-下午5:26:09
	 */
	int updatefield(HousePropertyMortgage housePropertyMortgage);
	/**
	 * 
	  * @Title: fiduciaryCount
	  * @description 统计房贷总数
	  * @param @return 
	  * @return int    
	  * @author caoyaru
	  * @createDate 2018年11月26日-上午10:04:32
	 */
	int fiduciaryCount();
	/**
	 * 
	  * @Title: houseMoney
	  * @description 房贷金额
	  * @param @return 
	  * @return int    
	  * @author caoyaru
	  * @createDate 2018年11月26日-下午4:17:18
	 */
	BigDecimal houseMoney();
	
	/**
	 * 
	  * @Title: updateByIdStatus
	  * @description 根据ID修改
	  * @param @param id
	  * @param @param status
	  * @param @return 
	  * @return int    
	  * @author caoyaru
	  * @createDate 2018年11月27日-下午4:43:25
	 */
	int updateByIdStatus(Long id, Integer status);
	
	/**
	 * 修改状态和备注
	 *
	 * @Title: updateStatusAndRemark
	
	 * @description 
	 *
	 * @param id 报单id
	 * @param status 报单状态
	 * @param remark 备注
	 * @param @return 
	   
	 * @return int    
	 *
	 * @author lujinpeng
	 * @createDate 2018年11月27日-下午6:55:52
	 */
	int updateStatusAndRemark (Long id, Integer status, String remark);

	
	/**
	 *查询房屋评估师id 
	 */
	List<User> getassessmensId();
	/**
	 * 查询最后一条报单的风控内勤id
	 */
	List<HousePropertyMortgage> lastid();
	
	/**
	 * @Title: getassessmensId
	 * @description 查询所有风控内勤人员
	 * @return List<User> 所有风控内勤人员 
	 * @author ZhaoSong
	 * @createDate 2018年11月28日
	 */
	List<User> getassessmensIdH();
	
	/**
	 * @Title: getRiskManagementIdH
	 * @description 查询所有风控外勤人员
	 * @return List<User> 所有风控外勤人员   
	 * @author ZhaoSong
	 * @createDate 2018年11月30日
	 */
	List<User> getRiskManagementIdH();
	
	
	/**
	 * @Title: lastid
	 * @description 查询最后一条报单的风控内勤id
	 * @return List<HousePropertyMortgage> 表中最后一条风控内勤id  
	 * @author ZhaoSong
	 * @createDate 2018年11月28日
	 */
	List<HousePropertyMortgage> lastidH();
	
	
	/**
	 * @Title: lastidFS
	 * @description 查询最后一条已通过面审的风控外勤id
	 * @return List<HousePropertyMortgage>   已通过面审的风控外勤id 
	 * @author ZhaoSong
	 * @createDate 2018年11月30日
	 */
	List<HousePropertyMortgage> lastidHS();
	
	
	/**
	 * @Title: updateManagementId
	 * @description 修改风控内勤id
	 * @param @param id 报单id
	 * @param @param managemnt 风控内勤id
	 * @param @return   受影响行数
	 * @author ZhaoSong
	 * @createDate 2018年11月29日
	 */
	int updateManagementId(@Param("id")Long id,@Param("managemnt")Long managemnt);
	
	
	/**
	 * @Title: updateRiskManagementId
	 * @description 修改风控外勤id
	 * @param @param id 报单id
	 * @param @param risk_management 风控外勤id
	 * @return int   受影响行数
	 * @author ZhaoSong
	 * @createDate 2018年11月30日
	 */
	int updateRiskManagementId(@Param("id")Long id,@Param("risk_management")Long risk_management);
	
	
	/**
	 * @Title: refuseToPass
	 * @description 拒绝通过的方法(拒绝通过后,订单状态变为1,,不通过,但是订单不会删除)
	 * @param @param entry_number 房屋报单编号
	 * @param @param status 报单状态
	 * @return int 受影响行数   
	 * @author ZhaoSong
	 * @createDate 2018年11月29日
	 */
	int refuseToPass(@Param("entry_number")String entry_number,@Param("status")int status);
	
	
	
	/**
	 * @Title: updateRemark
	 * @description 根据报单编号修改 备注信息
	 * @param @param entry_number 报单编号
	 * @param @param remark 备注信息
	 * @return int    受影响行数
	 * @author ZhaoSong
	 * @createDate 2018年11月30日
	 */
	int updateRemark(String entry_number,String remark);
	/**
	 * 
	 * @Title: selectManagement
	 * @description 根据报单编号查询当前操作人
	 * @param @param entry_number  
	 * @return String    
	 * @author qixiyao
	 * @createDate 2018年12月6日-上午10:02:14
	 */
	User selectManagement(String entry_number);
	
	
	
	
	 /**
	  * @Title: updateTimeH
	  * @description 根据报单编号修改更新时间
	  * @param @param entry_number 报单编号
	  * @return int  受影响行数  
	  * @author ZhaoSong
	  * @createDate 2018年12月6日
	  */
	int updateTimeH(String entry_number);
	
	
	
	/**
	 * @Title: submitDeclarationH
	 * @description 修改并提交房屋报单
	 * @param housePropertyMortgage 参数对象
	 * @return int 受影响行数   
	 * @author ZhaoSong
	 * @createDate 2018年12月24日
	 */
	int  submitDeclarationH(HousePropertyMortgage housePropertyMortgage);
	
	
	
	/**
	  * @Title: findByDate
	  * @description 根据日期条件查询房屋报单总金额
	  * @param @param year 年份
	  * @param @param month 月份
	  * @param @param day 天
	  * @return BigDecimal 符合日期查询条件的房屋报单总金额  
	  * @author ZhaoSong
	  * @createDate 2019年1月23日
	 */
	BigDecimal findByDate(String year,String month,String day);
	
	
	/**
	  * @Title: findByCount
	  * @description 根据日期条件查询房屋报单数量
	  * @param @param year 年份
	  * @param @param month 月份
	  * @param @param day 天
	  * @return Integer 符合日期查询条件的房屋报单总数量   
	  * @author ZhaoSong
	  * @createDate 2019年1月23日
	 */
	Integer findByCount(String year,String month,String day);
}
