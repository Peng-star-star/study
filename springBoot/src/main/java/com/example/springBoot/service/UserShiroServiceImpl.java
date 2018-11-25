package com.example.springBoot.service;

import java.util.ArrayList;
import java.util.List;

import com.example.springBoot.entity.Permission;
import com.example.springBoot.entity.Role;
import com.example.springBoot.entity.UserShiro;

public class UserShiroServiceImpl implements UserShiroService{
	
	private static List<UserShiro> userShiros = new ArrayList<>();
	
	static{
		//创建一些数据
		UserShiro userShiro = new UserShiro();
		userShiro.setId("01");
		userShiro.setName("test01");
		userShiro.setPassword("123");
		Role role = new Role();
		role.setId("01");
		role.setId("role01");
		Permission permission = new Permission();
		permission.setId("01");
		permission.setPermissionName("permission01");
		List<Permission> permissions = new ArrayList<Permission>();
		permissions.add(permission);
		role.setPermissions(permissions);
		List<Role> roles = new ArrayList<>();
		roles.add(role);
		userShiro.setRoles(roles);
		userShiros.add(userShiro);
	}
	
	@Override
	public UserShiro getUserShiroByName(String name) {
		for(UserShiro userShiro :userShiros){
			if(userShiro.getName().equals(name)){
				return userShiro;
			}
		}
		return null;
	}
}
