package com.example.system.entity;

import java.io.Serializable;

/**
 * 用户角色关联表
 * 
 * @author peng
 *
 */
public class UserRole implements Serializable {

	private static final long serialVersionUID = -5563923472411209784L;
	private String userId;
	private String roleId;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
}
