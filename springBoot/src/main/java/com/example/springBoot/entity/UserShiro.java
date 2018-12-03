package com.example.springBoot.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 用户
 * 
 * @author peng
 *
 */
public class UserShiro implements Serializable {

	private static final long serialVersionUID = -4852732617765810959L;
	private String id;
	private String name;
	private String password;

	private List<Role> roles;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}
