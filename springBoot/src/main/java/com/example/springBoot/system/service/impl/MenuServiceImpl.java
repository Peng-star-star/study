package com.example.springBoot.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springBoot.common.util.IdUtils;
import com.example.springBoot.system.dao.MenuDao;
import com.example.springBoot.system.dao.RoleDao;
import com.example.springBoot.system.entity.Menu;
import com.example.springBoot.system.service.MenuService;

@Service
@Transactional(readOnly = true)
public class MenuServiceImpl implements MenuService{
	
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private MenuDao menuDao;
	
	@Override
	public List<Menu> getMenus(Map<String, Object> params){
		List<Menu> Menus = menuDao.getMenus(params);
		return Menus;
	}
	
	@Override
	public Menu getMenu(String id){
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		List<Menu> menus = menuDao.getMenus(params);
		if(menus.size()>0){
			return menus.get(0);
		}else{
			return null;
		}
	}
	
	@Override
	public void addMenu(Menu menu){
		menu.setId(IdUtils.id32());
		menuDao.addMenu(menu);
	}
	
	@Override
	public void updateMenu(Menu menu){
		menuDao.updateMenu(menu);
	}
	
	@Override
	public void deleteMenu(String id){
		menuDao.deleteMenus(new String[]{id});
	}
	
	@Override
	public void deleteMenus(String[] ids){
		menuDao.deleteMenus(ids);
	}
}
