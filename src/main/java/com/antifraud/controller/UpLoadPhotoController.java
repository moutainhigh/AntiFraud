package com.antifraud.controller;

import java.util.HashMap;
import java.util.Map;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.antifraud.entity.Photo;
import com.antifraud.utils.ResultInfo;

/**
 * 
 *
 * @ClassName: UpLoadPhotoController
 * @description 上传图片/批量上传
 * @author qixiyao
 * @createDate 2018年10月25日-下午4:39:22
 */
@RestController
@RequestMapping("/UpLoadPhotoController")
public class UpLoadPhotoController {
	
	
	@PostMapping("/down")
	private Object down(@RequestParam("photo")MultipartFile[] files){
		Photo photo = new Photo();
		String code="1";
		String msg="成功";
		Map<Object, Object> map2 = new HashMap<Object, Object>();		
		String url = "F:/imgs";
		ResultInfo resultInfo = new ResultInfo();
		String pp="";
		if (files != null && files.length > 0) {
			for (int i = 0; i < files.length; i++) {
				MultipartFile file = files[i];
				 pp = PhotoUpload.savePhoto(file, url);
				// i 是第几张图  pp是路径+名字
				map2.put(i, pp);
			}
	}else{
		code="2";
		msg="失败";
	}
		for (int i = 0; i < map2.size(); i++) {
		}
		//返回结果
		Map <String ,Object > map = new HashMap <String ,Object>();
		map.put("code", code);
		map.put("msg", msg);
		map.put("data", map2);
		map.put("img_url", url+"/"+pp);
		return map;
}
}
