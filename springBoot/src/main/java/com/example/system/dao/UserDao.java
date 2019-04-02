package com.example.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.example.system.entity.User;

/**
 * 用户Dao
 * 
 * @author pl
 * @date 2018-12-04
 * 
 */
public interface UserDao {
	/**
	 * 获取用户
	 * 
	 * @param params
	 *            查询参数
	 * @return 用户列表
	 */
	List<User> listUser(@Param("params") Map<String, Object> params);

	/**
	 * 新增用户
	 * 
	 * @param user
	 *            用户
	 */
	void insertUser(User user);

	/**
	 * 更新用户
	 * 
	 * @param user
	 *            用户
	 */
	void updateUser(User user);

	/**
	 * 删除用户
	 * 
	 * @param ids
	 *            用户ID
	 */
	void deleteUsers(@Param("ids") String[] ids);
}
