package com.example.springBoot;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.example.springBoot.entity.Permission;
import com.example.springBoot.entity.Role;
import com.example.springBoot.entity.UserShiro;
import com.example.springBoot.service.UserShiroService;
import com.example.springBoot.service.UserShiroServiceImpl;

public class ShiroRealm extends AuthorizingRealm{
	
	private UserShiroService service = new UserShiroServiceImpl();
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		String name = (String)arg0.getPrimaryPrincipal();
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		UserShiro userShiro = service.getUserShiroByName(name);
		
		Set<String> roleNames = new HashSet<String>();
		Set<String> permissionNames = new HashSet<String>();
		for(Role role : userShiro.getRoles()){
			roleNames.add(role.getRoleName());
			for(Permission permission : role.getPermissions()){
				permissionNames.add(permission.getPermissionName());
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
        UserShiro user = service.getUserShiroByName(userName);

        if (user == null){
            throw new UnknownAccountException("用户名或密码错误！");
        }
        if (!password.equals(user.getPassword())){
            throw new IncorrectCredentialsException("用户名或密码错误！");
        }
        /*if (user.getStatus()){
            throw new LockedAccountException("账号已被锁定,请联系管理员！");
        }*/
        return new SimpleAuthenticationInfo(user, password, getName());
	}
	
}
