package com.example.system.dao;

import com.example.system.entity.UserRole;

/**
 * 用户角色
 * 
 * @author pl
 * @date 2018-12-24
 * 
 */
public interface UserRoleDao {

	/**
	 * 新增用户角色
	 * 
	 * @param userRole
	 *            用户角色
	 */
	void insertUserRole(UserRole userRole);

	/**
	 * 删除用户角色
	 * 
	 * @param userRole
	 *            用户角色
	 */
	void deleteUserRole(UserRole userRole);
}
