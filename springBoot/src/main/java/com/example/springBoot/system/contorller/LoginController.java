package com.example.springBoot.system.contorller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.springBoot.common.entity.ResponseBo;
import com.example.springBoot.common.util.vcode.Captcha;
import com.example.springBoot.common.util.vcode.GifCaptcha;

@Controller
public class LoginController {

	//验证码
	private static final String CODE_KEY = "_code";
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseBo login(@RequestParam String username, @RequestParam String password, String code, Boolean rememberMe) {
		//
		if (!StringUtils.isNotBlank(code)) {
			return ResponseBo.warn("验证码不能为空！");
		}
		Subject subject = SecurityUtils.getSubject();
		String sessionCode = (String)subject.getSession().getAttribute(CODE_KEY);
		if (!code.equalsIgnoreCase(sessionCode)) {
			return ResponseBo.warn("验证码错误！");
        }
		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
		try {
			subject.login(usernamePasswordToken);
		} catch (UnknownAccountException | IncorrectCredentialsException | LockedAccountException e) {
			return ResponseBo.error(e.getMessage());
		} catch (AuthenticationException e) {
			return ResponseBo.error("认证失败！");
		}
		return ResponseBo.ok();
	}

	@RequestMapping("/")
	public String redirectIndex() {
		return "redirect:/index";
	}

	@RequestMapping("/gifCode")
	public void getGifCode(HttpServletResponse response, HttpServletRequest request) {
		try {
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("image/gif");

			Captcha captcha = new GifCaptcha(146, 33, 4);
			HttpSession session = request.getSession(true);
			captcha.out(response.getOutputStream());
			session.removeAttribute(CODE_KEY);
			session.setAttribute(CODE_KEY, captcha.text().toLowerCase());
		} catch (Exception e) {

		}
	}
}
