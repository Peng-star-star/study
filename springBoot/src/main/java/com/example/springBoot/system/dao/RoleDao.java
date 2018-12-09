package com.example.springBoot.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.example.springBoot.system.entity.Role;

/**
 * 角色
 * 
 * @author pl
 * @date 2018-12-08
 * 
 */
public interface RoleDao {

	List<Role> getRoles(@Param("params") Map<String, Object> params);
	
	void addRole(Role user);
	
	void updateRole(Role user);
	
	void deleteRoles(@Param("ids") String[] ids);
}
