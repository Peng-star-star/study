package com.example.springBoot.entity;

import java.util.List;

/**
 * 角色
 * 
 * @author peng
 *
 */
public class Role {
	private String id;
	private String roleName;

	private List<Permission> permissions;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

}
