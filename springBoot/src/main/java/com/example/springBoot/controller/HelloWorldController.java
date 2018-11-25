package com.example.springBoot.controller;

import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloWorldController {
	
	@RequestMapping("/index")
	public String index(ModelMap map){
		map.addAttribute("host", "http://blog.didispace.com");
		return "index";
	}
	
	@RequestMapping("/hello")
	public String hello() throws Exception{
		throw new Exception("出现异常");
	}
    
    @RequestMapping(value = "/success/index")
    public String success(){
    	return "/success/index";
    }
    
    //登出
    @RequestMapping(value = "/logout")
    public String logout(){
        return "logout";
    }
    
    //错误页面展示
    @RequestMapping(value = "/error",method = RequestMethod.POST)
    public String error(){
        return "error ok!";
    }
}
