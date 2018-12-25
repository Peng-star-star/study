package com.example.springBoot.system;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.example.springBoot.system.entity.Role;
import com.example.springBoot.system.entity.User;
import com.example.springBoot.system.service.RoleService;
import com.example.springBoot.system.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class RoleServiceTest {
	
	@Autowired
	private RoleService service;
	
	@Test
	public void testGetRoles(){
		List<Role> roles = service.getRoles(new HashMap<String,Object>());
		System.err.println(JSON.toJSONString(roles));
	}
	
	@Test
	public void testGetRole(){
		Role role = service.getRole("001");
		System.err.println(JSON.toJSONString(role));
	}
	
	@Test
	public void testGetRolesAndMenu(){
		List<Role> roles = service.getRolesAndMenu(new HashMap<String,Object>());
		System.err.println(JSON.toJSONString(roles));
	}
	
	@Test
	public void testGetRoleAndMenu(){
		Role role = service.getRoleAndMenu("001");
		System.err.println(JSON.toJSONString(role));
	}
	
	@Test
	public void testAddRole(){
		//需加入@Transactional
		//默认回滚，若想提交加入注解@Rollback(false)
		Role role = new Role();
		role.setRoleName("测试角色");
		service.addRole(role);
	}
	
	@Test
	public void testUpdateRole(){
		//默认回滚，若想提交加入注解@Rollback(false)
		Role role = new Role();
		role.setRoleName("测试角色");
		service.updateRole(role);
	}
	
	@Test
	public void testDeleteRole(){
		service.deleteRole("001");
	}
	
	@Test
	public void testDeleteRoles(){
		service.deleteRoles(new String[]{"001","002"});
	}
}
