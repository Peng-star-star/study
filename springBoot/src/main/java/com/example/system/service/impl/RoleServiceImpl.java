package com.example.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.util.IdUtils;
import com.example.system.dao.MenuDao;
import com.example.system.dao.RoleDao;
import com.example.system.dao.UserDao;
import com.example.system.entity.Role;
import com.example.system.service.RoleService;

@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private MenuDao menuDao;
	
	@Override
	public List<Role> getRoles(Map<String, Object> params){
		List<Role> roles = roleDao.listRole(params);
		return roles;
	}
	
	@Override
	public Role getRole(String id){
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		List<Role> roles = roleDao.listRole(params);
		if(roles.size()>0){
			return roles.get(0);
		}else{
			return null;
		}
	}
	
	@Override
	public List<Role> getRolesAndMenu(Map<String, Object> params){
		List<Role> roles = roleDao.listRole(params);
		Map<String, Object> menuParams = new HashMap<>();
		for(Role role : roles){
			menuParams.put("roleId", role.getId());
			role.setMenus(menuDao.listMenu(menuParams));
		}
		return roles;
	}
	
	@Override
	public Role getRoleAndMenu(String id){
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		List<Role> roles = roleDao.listRole(params);
		Map<String, Object> menuParams = new HashMap<>();
		for(Role role : roles){
			menuParams.put("roleId", role.getId());
			role.setMenus(menuDao.listMenu(menuParams));
		}
		if(roles.size()>0){
			return roles.get(0);
		}else{
			return null;
		}
	}
	
	@Override
	public void addRole(Role role){
		role.setId(IdUtils.id32());
		roleDao.insertRole(role);
	}
	
	@Override
	public void updateRole(Role role){
		roleDao.updateRole(role);
	}
	
	@Override
	public void deleteRole(String id){
		roleDao.deleteRoles(new String[]{id});
	}
	
	@Override
	public void deleteRoles(String[] ids){
		roleDao.deleteRoles(ids);
	}
}
