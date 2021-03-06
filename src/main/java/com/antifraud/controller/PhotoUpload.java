package com.antifraud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.antifraud.utils.DateUtil;

import java.util.UUID;

/**
 * 
 *
 * @ClassName: PhotoUpload
 * @description 图片上传类
 * @author qixiyao
 * @createDate 2018年10月24日-下午1:35:31
 */
@Controller
@RequestMapping("/photo")
public class PhotoUpload {
	
	
	/**
	 * 
	 * @Title: savePhoto
	 * @description 上传图片中
	 * @param @param photo
	 * @param @param type
	 * @param @param path
	 * @param @return  
	 * @return String    
	 * @author qixiyao
	 * @createDate 2018年10月24日-下午1:35:44
	 */
	//@RequestMapping("/savePhoto")
	public static String savePhoto(MultipartFile photo, String path){
		try{	
			//上传图片
			String ffile = DateUtil.getDays(), fileName = "";
			String filePath = path +"/"+ ffile; // 文件上传路径
			//图标
			if (null != photo && !photo.isEmpty()) {
				fileName =FileUpload.fileUp(photo, filePath, get32UUID());
				return ffile + "/" + fileName;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "fileName";
	}
	
	/**
	 * 
	 * @Title: get32UUID
	 * @description 得到32位的uuid
	 * @param @return  
	 * @return String    
	 * @author qixiyao
	 * @createDate 2018年10月24日-下午1:35:57
	 */
	public static String get32UUID() {
		String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
		return uuid;
	}
}
