package com.example.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.entity.Tree;
import com.example.common.util.IdUtils;
import com.example.common.util.TreeUtils;
import com.example.system.dao.MenuDao;
import com.example.system.dao.RoleDao;
import com.example.system.entity.Menu;
import com.example.system.service.MenuService;

@Service
@Transactional(readOnly = true)
public class MenuServiceImpl implements MenuService{
	
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private MenuDao menuDao;
	
	@Override
	public List<Menu> getMenus(Map<String, Object> params){
		List<Menu> Menus = menuDao.listMenu(params);
		return Menus;
	}
	
	@Override
	public Menu getMenu(String id){
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		List<Menu> menus = menuDao.listMenu(params);
		if(menus.size()>0){
			return menus.get(0);
		}else{
			return null;
		}
	}
	
	@Override
	public Tree<Menu> getMenuTree(){
		Map<String, Object> params = new HashMap<>();
		List<Menu> menus =  menuDao.listMenu(params);
		List<Tree<Menu>> trees = new ArrayList<>();
		for(Menu menu: menus){
			 Tree<Menu> tree = new Tree<>();
	         tree.setId(menu.getId());
	         tree.setParentId(menu.getParentId().toString());
	         tree.setText(menu.getMenuName());
	         tree.setIcon(menu.getIcon());
	         tree.setUrl(menu.getUrl());
	         trees.add(tree);
		}
		return TreeUtils.build(trees);
	}
	
	@Override
	public void addMenu(Menu menu){
		menu.setId(IdUtils.id32());
		menuDao.insertMenu(menu);
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
