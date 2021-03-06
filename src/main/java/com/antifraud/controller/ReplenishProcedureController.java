package com.antifraud.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.antifraud.entity.FiduciaryLoan;
import com.antifraud.entity.HousePropertyMortgage;
import com.antifraud.entity.PostLoanManage;
import com.antifraud.entity.ReplenishProcedure;
import com.antifraud.entity.ReplenishProcedureImage;
import com.antifraud.entity.VehicleMortgage;
import com.antifraud.entityVO.HousePropertyMortgageVo;
import com.antifraud.entityVO.PostLoanManageVo;
import com.antifraud.service.FiduciaryLoanService;
import com.antifraud.service.HousePropertyMortgageService;
import com.antifraud.service.ReplenishProcedureImageService;
import com.antifraud.service.ReplenishProcedureService;
import com.antifraud.service.VehicleMortgageService;
import com.antifraud.service.impl.ReplenishProcedureImageServiceImpl;
import com.antifraud.utils.JsonUtil;

/**
 * 
 *
 * @ClassName: ReplenishProcedureController

 * @description 补充手续表Controller层
 *
 * @author zhangkai
 * @createDate 2018年10月30日
 */
@Controller
@RequestMapping("ReplenishProcedure")
public class ReplenishProcedureController {	
	@Resource
	private ReplenishProcedureService  replenishProcedureService;
	
	@Autowired
	private HousePropertyMortgageService housePropertyMortgageService;
	
	@Autowired
	private VehicleMortgageService vehicleMortgageService;
	
	@Autowired
	private FiduciaryLoanService fiduciaryLoanService;
	
	
/**
 * 
*
 * @Title: updateReplenishProcedureByRecordId

 * @description 修改数据
*
 * @param @param replenishProcedure
 * @param @return
 * @param @throws Exception 
   
 * @return String    

 *
 * @author zhangkai
 * @createDate 2018年11月14日
 */
	 @RequestMapping(value="updateReplenishProcedureByRecordId")
		@ResponseBody
		public String updateReplenishProcedureByRecordId(ReplenishProcedure replenishProcedure) throws Exception{
			
			int code;
			String msg;	
			int result;
			try{				
				result= replenishProcedureService.updateReplenishProcedureByRecordId(replenishProcedure);					
				code = 1;
				msg = "修改成功";
			    }catch(Exception e){
			    	e.printStackTrace();
			    	code = -1;
					msg = "修改失败";
			    }
		
			return JsonUtil.getResponseJson(code, msg,null, null);
		}
		
	 
	 
	 /**
	  * 
	 *
	  * @Title: addReplenishProcedure
	 
	  * @description 增加数据
	 *
	  * @param @param replenishProcedure
	  * @param @return
	  * @param @throws Exception 
	    
	  * @return String    
	 
	  *
	  * @author zhangkai
	  * @createDate 2018年11月14日
	  */
	 @RequestMapping(value="addReplenishProcedure")
		@ResponseBody
		public String addReplenishProcedure(ReplenishProcedure replenishProcedure) throws Exception{
			
			int code;
			String msg;	
			int result;
			try{				
				result= replenishProcedureService.addReplenishProcedure(replenishProcedure);					
				code = 1;
				msg = "增加成功";
			    }catch(Exception e){
			    	e.printStackTrace();
			    	code = -1;
					msg = "增加失败";
			    }
		
			return JsonUtil.getResponseJson(code, msg,null, null);
		}
	 
	 
	 

	 /**
	  * 
	 *
	  * @Title: findReplenishProcedureByRecordId
	 
	  * @description 通过报单编号查询
	 *
	  * @param @param id
	  * @param @return
	  * @param @throws Exception 
	    
	  * @return String    
	 
	  *
	  * @author zhangkai
	  * @createDate 2018年11月14日
	  */
	    @RequestMapping(value="findReplenishProcedureByRecordId")
		@ResponseBody
		public String findReplenishProcedureByRecordId(String id) throws Exception{
			
			int code;
			String msg;
			ReplenishProcedure result = null;			
			try{				
				result= replenishProcedureService.findReplenishProcedureByRecordId(id);
				if(result!=null){				
				code = 1;
				msg = "查询成功";	
				}else{code = 1;
				msg = "查询无结果";
				         }	
			    }catch(Exception e){
			    	e.printStackTrace();
			    	code = -1;
					msg = "查询失败";
			    }
		
			return JsonUtil.getResponseJson(code, msg, null, result);
		}
		
	    /**
	     * 
	     *
	     * @Title: showInfoByHouseId
	     *
	     * @description 房产抵押显示借款人身份信息
	     *
	     * @param @param id
	     * @param @return
	     * @param @throws Exception 
	     * 
	     * @return String    
	     *
	     * @author yaozijun
	     *
	     * @createDate 2018年11月22日
	     */
	    @RequestMapping(value="showInfoByHouseId", method = RequestMethod.POST, produces = "text/html; charset=UTF-8")
		@ResponseBody
	    public String showInfoByHouseId (Long id) throws Exception {
	    	int code;
			String msg;
			HousePropertyMortgage result = null;			
			try{				
				result= housePropertyMortgageService.selectById(id);
				if(result!=null){				
				code = 1;
				msg = "显示成功";	
				}else{code = 1;
				msg = "显示无结果";
				         }	
			    }catch(Exception e){
			    	e.printStackTrace();
			    	code = -1;
					msg = "显示失败";
				}
		
			return JsonUtil.getResponseJson(code, msg, null, result);
	    }
	    
	    
	    /**
	     * 
	     *
	     * @Title: showInfoByVehicleId
	     *
	     * @description 车辆质押抵押显示借款人身份信息
	     *
	     * @param @param id
	     * @param @return
	     * @param @throws Exception 
	     * 
	     * @return String    
	     *
	     * @author yaozijun
	     *
	     * @createDate 2018年11月22日
	     */
	    @RequestMapping(value="showInfoByVehicleId", method = RequestMethod.POST, produces = "text/html; charset=UTF-8")
		@ResponseBody
	    public String showInfoByVehicleId (Long id) throws Exception {
	    	int code;
			String msg;
			VehicleMortgage result = null;			
			try{				
				result= vehicleMortgageService.selectIdVehicleMortgage(id);
				if(result!=null){				
				code = 1;
				msg = "显示成功";	
				}else{code = 1;
				msg = "显示无结果";
				         }	
			    }catch(Exception e){
			    	e.printStackTrace();
			    	code = -1;
					msg = "显示失败";
				}
		
			return JsonUtil.getResponseJson(code, msg, null, result);
	    }
	    
	    /**
	     * 
	     *
	     * @Title: showInfoByFiduciaryId
	     *
	     * @description 信用贷款显示借款人身份信息
	     *
	     * @param @param id
	     * @param @return
	     * @param @throws Exception 
	     * 
	     * @return String    
	     *
	     * @author yaozijun
	     *
	     * @createDate 2018年11月22日
	     */
	    @RequestMapping(value="showInfoByFiduciaryId", method = RequestMethod.POST, produces = "text/html; charset=UTF-8")
		@ResponseBody
	    public String showInfoByFiduciaryId (Long id) throws Exception {
	    	int code;
			String msg;
			FiduciaryLoan result = null;			
			try{				
				result= fiduciaryLoanService.findById(id);
				if(result!=null){				
				code = 1;
				msg = "显示成功";	
				}else{code = 1;
				msg = "显示无结果";
				         }	
			    }catch(Exception e){
			    	e.printStackTrace();
			    	code = -1;
					msg = "显示失败";
				}
		
			return JsonUtil.getResponseJson(code, msg, null, result);
	    }
		
}













	
	
	
	
	
	
	
	
	

