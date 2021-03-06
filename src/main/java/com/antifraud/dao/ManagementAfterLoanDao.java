package com.antifraud.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.antifraud.entity.TManagementAfterLoan;

/**
 * 
 *
 * @ClassName: TManagementAfterLoanMapper

 * @description 贷后管理dao
 *
 * @author lishaozhang
 * @createDate 2019年1月15日
 */
public interface ManagementAfterLoanDao {
   /**
    * 插入
    * */
    Integer insert(TManagementAfterLoan record);
    /***
     * 查询数据库中的最新一期期数
     */
    Long selectBigestNewest();
    /**
     * 根据Newest查询数据
     * @param limit 
     * @param star 
     * */
   List<TManagementAfterLoan> selectByNewest(@Param("newest")long newest, @Param("star")Integer star,@Param("limit")Integer limit);
   /***
    * 查询新数据数量
    * */
   Integer selectByNewestCount(long newest);
   
   /**
    * 修改
    * */
   Integer updateById(@Param("remaker")String remaker, @Param("id")Long id);
   
   
	 List<TManagementAfterLoan> selectByEntry_number(@Param("entry_number") String entry_number);
	
	 Integer selectByEntry_numberCount(String entry_number);
	 
	 TManagementAfterLoan findOne(Long id);
	 /**
	  * 模糊电话查询
	  * 
	  * 
	  */
	List<TManagementAfterLoan> selectByNewestbyphone(@Param("mobile")String moblie,@Param("newest")long newest, @Param("star")Integer star,@Param("limit")Integer limit);
    /**
     * 根据预期与否查询
     * 
     */
	List<TManagementAfterLoan> selectByNewestbyoverdue(@Param("overdue")int overdue,@Param("newest")long newest, @Param("star")Integer star,@Param("limit")Integer limit);


}