package com.example.springBoot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.springBoot.dao.UserInfoDao;
import com.example.springBoot.entity.User;
import com.example.springBoot.service.UserInfoService;

/**
 * 用户管理服务实现
 * 
 * @table T_USER_INFO
 * @author pl
 * @date 2017-05-23
 * 
 */
@Service
@Transactional(readOnly = true)
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private UserInfoDao dao;

	@Override
	public User getById(String id) {
		User user = dao.getById(id);
		if (user != null) {
			user.setId(user.getUserId());
		}
		return user;
	}

	@Override
	@Transactional
	public void save(User user) {
		if (user.getId()!=null && "".equals(user.getId())) {
			dao.update(user);
		} else {
			user.setId("1111");
			dao.add(user);
		}
	}

	@Override
	@Transactional
	public void delete(String[] ids) {
		if (null != ids && ids.length > 0) {
			dao.delete(ids);
		}
	}
}
