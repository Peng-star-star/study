package com.example.springBoot.system.service;

import java.util.List;
import java.util.Map;

import com.example.springBoot.system.entity.Role;

/**
 * 角色
 * @author pl
 *
 */
public interface RoleService {
	
	/**
	 * 获取角色
	 * @param params
	 * @return
	 */
	List<Role> getRoles(Map<String, Object> params);
	
	/**
	 * 获取角色
	 * @param id
	 * @return
	 */
	Role getRole(String id);
	
	/**
	 * 获取角色包含菜单
	 * @param params
	 * @return
	 */
	List<Role> getRolesAndMenu(Map<String, Object> params);
	
	/**
	 * 获取角色包含菜单
	 * @param id
	 * @return
	 */
	Role getRoleAndMenu(String id);
	
	void addRole(Role role);
	
	void updateRole(Role role);
	
	void deleteRole(String id);
	
	void deleteRoles(String[] ids);
}
