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
import com.example.system.entity.Menu;
import com.example.system.service.MenuService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MenuServiceTest {
	
	@Autowired
	private MenuService service;
	
	@Test
	public void testGetMenus(){
		List<Menu> menus = service.getMenus(new HashMap<String,Object>());
		System.err.println(JSON.toJSONString(menus));
	}
	
	@Test
	public void testGetMenu(){
		Menu role = service.getMenu("001");
		System.err.println(JSON.toJSONString(role));
	}
	
	
	@Test
	public void testAddMenu(){
		//需加入@Transactional
		//默认回滚，若想提交加入注解@Rollback(false)
		Menu menu = new Menu();
		menu.setParentId("");
		menu.setMenuName("测试菜单");
		menu.setUrl("/");
		menu.setPerms("PERMS");
		menu.setIcon("/");
		menu.setType("0");
		menu.setOrderNum(1);
		menu.setStatus("1");
		menu.getRemarks();
		service.addMenu(menu);
	}
	
	@Test
	public void testUpdateMenu(){
		//默认回滚，若想提交加入注解@Rollback(false)
		Menu menu = new Menu();
		menu.setMenuName("测试菜单");
		service.updateMenu(menu);
	}
	
	@Test
	public void testDeleteMenu(){
		service.deleteMenu("001");
	}
	
	@Test
	public void testDeleteMenus(){
		service.deleteMenus(new String[]{"001","002"});
	}
}
