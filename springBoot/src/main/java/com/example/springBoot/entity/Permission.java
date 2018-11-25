package com.example.springBoot.entity;

/**
 * 权限
 * 
 * @author peng
 *
 */
public class Permission {
	private String id;
	private String permissionName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

}
