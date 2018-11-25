package com.example.springBoot.service;

import com.example.springBoot.entity.User;

/**
 * 用户管理服务
 * 
 * @table T_USER_INFO
 * @author pl
 * @date 2018-10-12
 * 
 */
public interface UserInfoService {

	/**
	 * 保存用户
	 *
	 * @param user
	 *			用户对象
	 * @return 
	 */
	void save(User user);
	
	/**
	 * 删除用户
	 *
	 * @param ids
	 *			用户ID数组
	 * @return 
	 */
	void delete(String[] ids);
	
	/**
	 * 根据ID查询用户
	 * 
	 * @param id
	 *            用户ID
	 * @return 用户实体对象
	 */
	User getById(String id);
}
