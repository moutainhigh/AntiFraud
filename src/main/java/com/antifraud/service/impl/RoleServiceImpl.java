package com.antifraud.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.antifraud.dao.RoleDao;
import com.antifraud.entity.Role;
import com.antifraud.entity.User;
import com.antifraud.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	private RoleDao roleDao;
	
	@Override
	public List<Role> findByUser(User user) {
		// 基于用户查询角色，admin具有所有角色
		if(user.getUser_name().equals("admin")){
			return roleDao.findAll();
		}else{
			return roleDao.findByUser(user.getId());
		}
	}
}
