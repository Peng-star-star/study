package com.example.system.service;

import java.util.List;
import java.util.Map;

import com.example.common.entity.Tree;
import com.example.system.entity.Menu;
import com.example.system.entity.Role;

/**
 * 菜单
 * @author pl
 *
 */
public interface MenuService {
	
	/**
	 * 获取菜单
	 * @param params
	 * @return
	 */
	List<Menu> getMenus(Map<String, Object> params);
	
	/**
	 * 获取菜单
	 * @param id
	 * @return
	 */
	Menu getMenu(String id);
	
	/**
	 * 获取菜单树
	 * @return
	 */
	Tree<Menu> getMenuTree();
	
	void addMenu(Menu menu);
	
	void updateMenu(Menu menu);
	
	void deleteMenu(String id);
	
	void deleteMenus(String[] ids);
}
