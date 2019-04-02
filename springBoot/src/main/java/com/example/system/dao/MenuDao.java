package com.example.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.example.system.entity.Menu;

/**
 * 权限（菜单）
 * 
 * @author pl
 * @date 2018-12-08
 * 
 */
public interface MenuDao {
	/**
	 * 获取菜单
	 * 
	 * @param params
	 *            查询参数
	 * @return 菜单列表
	 */
	List<Menu> listMenu(@Param("params") Map<String, Object> params);

	/**
	 * 新增菜单
	 * 
	 * @param menu
	 *            菜单
	 */
	void insertMenu(Menu menu);

	/**
	 * 更新菜单
	 * 
	 * @param menu
	 *            菜单
	 */
	void updateMenu(Menu menu);

	/**
	 * 删除菜单
	 * 
	 * @param ids
	 *            菜单ID数组
	 */
	void deleteMenus(@Param("ids") String[] ids);
}
