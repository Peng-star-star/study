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
import com.example.system.entity.User;
import com.example.system.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTest {
	
	@Autowired
	private UserService service;
	
	@Test
	public void testGetUsers(){
		List<User> users = service.getUsers(new HashMap<String,Object>());
		System.err.println(JSON.toJSONString(users));
	}
	
	@Test
	public void testGetUser(){
		User user = service.getUser("001");
		System.err.println(JSON.toJSONString(user));
	}
	
	@Test
	public void testGetUsersAndRole(){
		List<User> users = service.getUsersAndRole(new HashMap<String,Object>());
		System.err.println(JSON.toJSONString(users));
	}
	
	@Test
	public void testGetUserAndRole(){
		User user = service.getUserAndRole("001");
		System.err.println(JSON.toJSONString(user));
	}
	
	@Test
	public void testAddUser(){
		//需加入@Transactional
		//默认回滚，若想提交加入注解@Rollback(false)
		User user = new User();
		user.setUserName("1");
		user.setName("1");
		user.setPassword("1");
		service.addUser(user);
	}
	
	@Test
	public void testUpdateUser(){
		//默认回滚，若想提交加入注解@Rollback(false)
		User user = new User();
		user.setId("001");
		user.setUserName("1");
		user.setName("1");
		user.setPassword("1");
		service.updateUser(user);
	}
	
	@Test
	public void testDeleteUser(){
		service.deleteUser("001");
	}
	
	@Test
	public void testDeleteUsers(){
		service.deleteUsers(new String[]{"001","002"});
	}
}
