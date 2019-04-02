package com.example.system.contorller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.common.entity.QueryRequest;
import com.example.common.entity.ResponseBo;
import com.example.common.entity.Tree;
import com.example.system.entity.Menu;
import com.example.system.entity.User;
import com.example.system.service.MenuService;
import com.example.system.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/user")
public class UserController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	UserService service;
	
	@RequestMapping("/list")
	@RequiresPermissions("user:list")
	public String index(){
		return "system/user/user";
	}

	@RequestMapping("/listDate")
    @RequiresPermissions("user:list")
    @ResponseBody
    public Map<String, Object> userList(QueryRequest page, User user) {
		Map<String, Object> params = new HashMap<>();
		params.put("userName", user.getUserName()== "" ? null : user.getUserName());
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
        PageInfo<?> pageInfo = new PageInfo<>(service.getUsers(params));
        PageHelper.clearPage();
        Map<String, Object> rspData = new HashMap<>();
        rspData.put("rows", pageInfo.getList());
        rspData.put("total", pageInfo.getTotal());
        return rspData;
	}
	
	@RequiresPermissions("user:list")
    @RequestMapping("/getUser")
    @ResponseBody
    public ResponseBo getUser(String id) {
        try {
        	User user = service.getUserAndRole(id);
            return ResponseBo.ok(user);
        } catch (Exception e) {
        	logger.error("获取用户失败", e);
            return ResponseBo.error("获取用户失败，请联系网站管理员！");
        }
    }
	
    @RequiresPermissions("user:add")
    @RequestMapping("/add")
    @ResponseBody
    public ResponseBo addUser(@RequestBody User user) {
        try {
            service.addUser(user);
            return ResponseBo.ok("新增用户成功！");
        } catch (Exception e) {
        	logger.error("新增用户失败", e);
            return ResponseBo.error("新增用户失败，请联系网站管理员！");
        }
    }
    
    @RequiresPermissions("user:update")
    @RequestMapping("/update")
    @ResponseBody
    public ResponseBo updateUser(@RequestBody User user) {
        try {
        	service.updateUser(user);
            return ResponseBo.ok("修改用户成功！");
        } catch (Exception e) {
        	logger.error("修改用户失败", e);
            return ResponseBo.error("修改用户失败，请联系网站管理员！");
        }
    }
    
    @RequiresPermissions("user:delete")
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseBo updateUser(String ids) {
    	 try {
    		 String[] idsArray = ids.split(",");
    		 service.deleteUsers(idsArray);
             return ResponseBo.ok("删除用户成功！");
         } catch (Exception e) {
        	 logger.error("删除用户失败", e);
             return ResponseBo.error("删除用户失败，请联系网站管理员！");
         }
    }
    
}
