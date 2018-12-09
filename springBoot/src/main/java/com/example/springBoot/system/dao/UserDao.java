package com.example.springBoot.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.example.springBoot.system.entity.User;

/**
 * 用户
 * 
 * @author pl
 * @date 2018-12-04
 * 
 */
public interface UserDao {

	List<User> getUsers(@Param("params") Map<String, Object> params);
	
	void addUser(User user);
	
	void updateUser(User user);
	
	void deleteUsers(@Param("ids") String[] ids);
}
