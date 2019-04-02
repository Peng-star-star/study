package com.example.common.shiro;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.example.system.entity.Menu;
import com.example.system.entity.Role;
import com.example.system.entity.User;
import com.example.system.service.MenuService;
import com.example.system.service.UserService;
import com.example.system.service.impl.MenuServiceImpl;
import com.example.system.service.impl.UserServiceImpl;

public class ShiroRealm extends AuthorizingRealm {

	private UserService service = new UserServiceImpl();
	private MenuService menuService = new MenuServiceImpl();

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		String userId = user.getId();
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		User userAndRole = service.getUserAndRole(userId);
		Set<String> roleNames = new HashSet<String>();
		Set<String> permissionNames = new HashSet<String>();
		Map<String, Object> menuParams = new HashMap<>();
		for (Role role : userAndRole.getRoles()) {
			roleNames.add(role.getRoleName());
			//查询菜单
			menuParams.put("roleId", role.getId());
			for (Menu permission : menuService.getMenus(menuParams)) {
				permissionNames.add(permission.getPerms());
			}
		}
		simpleAuthorizationInfo.setRoles(roleNames);
		simpleAuthorizationInfo.setStringPermissions(permissionNames);
		return simpleAuthorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
		// 获取用户输入的用户名和密码
		String userName = (String) arg0.getPrincipal();
		String password = new String((char[]) arg0.getCredentials());

		// 通过用户名到数据库查询用户信息
		Map<String, Object> params = new HashMap<>();
		params.put("userName", userName);
		List<User> users = service.getUsers(params);
		if (users == null) {
			throw new UnknownAccountException("用户名或密码错误！");
		}
		if (users.size() == 0) {
			throw new UnknownAccountException("用户名或密码错误！");
		}
		if (users.size() > 1) {
			throw new UnknownAccountException("错误，有多个相同用户名！");
		}

		User user = users.get(0);

		if (!password.equals(user.getPassword())) {
			throw new IncorrectCredentialsException("用户名或密码错误！");
		}
		/*
		 * if (user.getStatus()){ throw new
		 * LockedAccountException("账号已被锁定,请联系管理员！"); }
		 */
		return new SimpleAuthenticationInfo(user, password, getName());
	}

}
