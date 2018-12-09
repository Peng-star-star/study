package com.example.springBoot.system.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 角色
 * 
 * @author peng
 *
 */
public class Role implements Serializable {

	private static final long serialVersionUID = -7659881266931703499L;
	private String id;
	private String roleName;
	private String createTime;
	private String updateTime;

	private List<Menu> menus;

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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}
}
