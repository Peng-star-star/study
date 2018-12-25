package com.example.springBoot.system.contorller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.springBoot.common.entity.QueryRequest;
import com.example.springBoot.common.entity.ResponseBo;
import com.example.springBoot.common.entity.Tree;
import com.example.springBoot.system.entity.Menu;
import com.example.springBoot.system.entity.Role;
import com.example.springBoot.system.entity.User;
import com.example.springBoot.system.service.MenuService;
import com.example.springBoot.system.service.RoleService;
import com.example.springBoot.system.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/role")
public class RoleController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	RoleService service;
	
	@RequestMapping("/list")
	@RequiresPermissions("role:list")
	public String index(){
		return "system/role/role";
	}

	
	@RequestMapping("/listDate")
    @RequiresPermissions("role:list")
    @ResponseBody
    public Map<String, Object> roleList(QueryRequest page, Role role) {
		Map<String, Object> params = new HashMap<>();
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
        PageInfo<?> pageInfo = new PageInfo<>(service.getRoles(params));
        PageHelper.clearPage();
        Map<String, Object> rspData = new HashMap<>();
        rspData.put("rows", pageInfo.getList());
        rspData.put("total", pageInfo.getTotal());
        return rspData;
	}
}
