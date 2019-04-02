package com.example.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.example.system.entity.Role;

/**
 * 角色
 * 
 * @author pl
 * @date 2018-12-08
 * 
 */
public interface RoleDao {

	/**
	 * 获取角色
	 * 
	 * @param params
	 *            查询参数
	 * @return 角色列表
	 */
	List<Role> listRole(@Param("params") Map<String, Object> params);

	/**
	 * 新增角色
	 * 
	 * @param user
	 *            角色
	 */
	void insertRole(Role user);

	/**
	 * 更新角色
	 * 
	 * @param user
	 *            角色
	 */
	void updateRole(Role user);

	/**
	 * 删除角色
	 * 
	 * @param ids
	 *            角色ID数组
	 */
	void deleteRoles(@Param("ids") String[] ids);
}
