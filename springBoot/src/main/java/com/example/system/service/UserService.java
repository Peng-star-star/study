package com.example.system.service;

import java.util.List;
import java.util.Map;

import com.example.system.entity.User;

/**
 * 用户
 * @author pl
 *
 */
public interface UserService {
	
	/**
	 * 获取用户
	 * @param params
	 * @return
	 */
	List<User> getUsers(Map<String, Object> params);
	
	/**
	 * 获取用户
	 * @param id
	 * @return
	 */
	User getUser(String id);
	
	/**
	 * 获取用户包含角色
	 * @param params
	 * @return
	 */
	List<User> getUsersAndRole(Map<String, Object> params);
	
	/**
	 * 获取用户包含角色
	 * @param id
	 * @return
	 */
	User getUserAndRole(String id);
	
	void addUser(User user);
	
	void updateUser(User user);
	
	void deleteUser(String id);
	
	void deleteUsers(String[] ids);
}
