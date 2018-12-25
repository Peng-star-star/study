package com.example.springBoot.system.dao;

import com.example.springBoot.system.entity.UserRole;

/**
 * 用户
 * 
 * @author pl
 * @date 2018-12-24
 * 
 */
public interface UserRoleDao {

	void addUserRole(UserRole userRole);
	
	void deleteUserRole(UserRole userRole);
}
