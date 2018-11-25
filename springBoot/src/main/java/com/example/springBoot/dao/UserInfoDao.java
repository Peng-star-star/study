package com.example.springBoot.dao;

import org.apache.ibatis.annotations.Param;
import com.example.springBoot.entity.User;

/**
 * 用户信息管理服务
 * 
 * @author swh
 * @date 2017-05-23
 * 
 */
public interface UserInfoDao {

	/**
	 * 新增用户
	 *
	 * @param user
	 *            用户对象
	 * @return
	 */
	void add(User user);

	/**
	 * 修改用户
	 *
	 * @param user
	 *            用户对象
	 * @return
	 */
	void update(User user);

	/**
	 * 删除用户
	 *
	 * @param ids
	 *            用户ID数组
	 * @return
	 */
	void delete(@Param("ids") String[] ids);

	/**
	 * 获取用户
	 *
	 * @param id
	 *            用户ID
	 * @return 用户对象
	 */
	User getById(@Param("id") String id);
}
