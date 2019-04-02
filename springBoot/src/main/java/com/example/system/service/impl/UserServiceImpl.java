package com.example.system.service.impl;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.util.IdUtils;
import com.example.common.util.MD5Utils;
import com.example.system.dao.RoleDao;
import com.example.system.dao.UserDao;
import com.example.system.dao.UserRoleDao;
import com.example.system.entity.Role;
import com.example.system.entity.User;
import com.example.system.entity.UserRole;
import com.example.system.service.UserService;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao dao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private UserRoleDao userRoleDao;

	@Override
	public List<User> getUsers(Map<String, Object> params) {
		List<User> users = dao.listUser(params);
		return users;
	}

	@Override
	public User getUser(String id) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		List<User> users = dao.listUser(params);
		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<User> getUsersAndRole(Map<String, Object> params) {
		List<User> users = dao.listUser(params);
		Map<String, Object> roleParams = new HashMap<>();
		for (User user : users) {
			roleParams.put("userId", user.getId());
			user.setRoles(roleDao.listRole(params));
		}
		return users;
	}

	@Override
	public User getUserAndRole(String id) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		List<User> users = dao.listUser(params);
		Map<String, Object> roleParams = new HashMap<>();
		for (User user : users) {
			roleParams.put("userId", user.getId());
			user.setRoles(roleDao.listRole(roleParams));
		}
		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public void addUser(User user) {
		user.setId(IdUtils.id32());
		user.setPassword(MD5Utils.encrypt(user.getUserName(), user.getPassword()));
		dao.insertUser(user);
		addUserRole(user);
	}

	@Override
	@Transactional
	public void updateUser(User user) {
		dao.updateUser(user);
		addUserRole(user);
	}

	private void addUserRole(User user) {
		if (user.getRoles() != null) {
			for (Role role : user.getRoles()) {
				UserRole userRole = new UserRole();
				userRole.setUserId(user.getId());
				if (role == null) {
					continue;
				}
				userRole.setRoleId(role.getId());
				userRoleDao.deleteUserRole(userRole);
				userRoleDao.insertUserRole(userRole);
			}
		}
	}

	@Override
	@Transactional
	public void deleteUser(String id) {
		dao.deleteUsers(new String[] { id });
	}

	@Override
	@Transactional
	public void deleteUsers(String[] ids) {
		dao.deleteUsers(ids);
		deleteUserRole(ids);
	}
	
	private void deleteUserRole(String[] userIds){
		for(String userId : userIds){
			UserRole userRole = new UserRole();
			userRole.setUserId(userId);
			userRoleDao.deleteUserRole(userRole);
		}
	}
}
