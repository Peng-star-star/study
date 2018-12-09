package com.example.springBoot.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.example.springBoot.system.entity.Menu;

/**
 * 权限（菜单）
 * 
 * @author pl
 * @date 2018-12-08
 * 
 */
public interface MenuDao {

	List<Menu> getMenus(@Param("params") Map<String, Object> params);
	
	void addMenu(Menu user);
	
	void updateMenu(Menu user);
	
	void deleteMenus(@Param("ids") String[] ids);
}
