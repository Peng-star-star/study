package com.example.springBoot.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springBoot.common.util.IdUtils;
import com.example.springBoot.system.dao.RoleDao;
import com.example.springBoot.system.dao.UserDao;
import com.example.springBoot.system.entity.User;
import com.example.springBoot.system.service.UserService;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDao dao;
	@Autowired
	private RoleDao roleDao;
	
	@Override
	public List<User> getUsers(Map<String, Object> params){
		List<User> users = dao.getUsers(params);
		return users;
	}
	
	@Override
	public User getUser(String id){
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		List<User> users = dao.getUsers(params);
		if(users.size()>0){
			return users.get(0);
		}else{
			return null;
		}
	}
	
	@Override
	public List<User> getUsersAndRole(Map<String, Object> params){
		List<User> users = dao.getUsers(params);
		Map<String, Object> roleParams = new HashMap<>();
		for(User user : users){
			roleParams.put("userId", user.getId());
			user.setRoles(roleDao.getRoles(params));
		}
		return users;
	}
	
	@Override
	public User getUserAndRole(String id){
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		List<User> users = dao.getUsers(params);
		Map<String, Object> roleParams = new HashMap<>();
		for(User user : users){
			roleParams.put("userId", user.getId());
			user.setRoles(roleDao.getRoles(params));
		}
		if(users.size()>0){
			return users.get(0);
		}else{
			return null;
		}
	}
	
	@Override
	@Transactional
	public void addUser(User user){
		user.setId(IdUtils.id32());
		dao.addUser(user);
	}
	
	@Override
	@Transactional
	public void updateUser(User user){
		dao.updateUser(user);
	}
	
	@Override
	@Transactional
	public void deleteUser(String id){
		dao.deleteUsers(new String[]{id});
	}
	
	@Override
	@Transactional
	public void deleteUsers(String[] ids){
		dao.deleteUsers(ids);
	}
}
