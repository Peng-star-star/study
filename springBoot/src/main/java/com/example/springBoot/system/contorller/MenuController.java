package com.example.springBoot.system.contorller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.springBoot.common.entity.ResponseBo;
import com.example.springBoot.common.entity.Tree;
import com.example.springBoot.system.entity.Menu;
import com.example.springBoot.system.service.MenuService;

@Controller
@RequestMapping("/menu")
public class MenuController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	MenuService service;

	@ResponseBody
	@PostMapping("/getMenuTree")
	public ResponseBo getMenuTree() {
		try{
			Tree<Menu> tree = service.getMenuTree();
			return ResponseBo.ok(tree);
		}catch(Exception e){
			logger.error("获取用户菜单失败", e);
            return ResponseBo.error("获取用户菜单失败！");
		}
	}
}
