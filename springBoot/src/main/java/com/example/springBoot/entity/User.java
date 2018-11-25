package com.example.springBoot.entity;

/**
 * 用户信息
 * 
 * @author swh
 * @date 2017-05-23
 * 
 */
public class User {

	/** 用户ID */
	private String userId;
	/** 用户名 */
	private String userShort;
	/** 用户名 */
	private String userName;
	/** 用户类型 */
	private Integer userType;
	/** 部门ID */
	private String deptId;
	/** 手机号码 */
	private String phone;
	/** 系统帐号 */
	private String loginName;
	/** 用户密码 */
	private String password;

	/** 记录ID */
	transient private String id;
	/** 部门名称 */
	transient private String deptName;
	/** 所属局-职能级别 （1公司、2局 、3行政单位） */
	transient private Integer dzn;
	/** 所属局 */
	transient private String dcode;
	/** 所属局名称 */
	transient private String dname;
	/** 角色ID */
	transient private String rid;
	/** 角色名称 */
	transient private String rname;
	/** 角色-职能级别 */
	transient private Integer rgrade;
	/** 行政级别为4的部门名称*/
	transient private String deptNameFour;

	public User() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the userShort
	 */
	public String getUserShort() {
		return userShort;
	}

	/**
	 * @param userShort
	 *            the userShort to set
	 */
	public void setUserShort(String userShort) {
		this.userShort = userShort;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the userType
	 */
	public Integer getUserType() {
		return userType;
	}

	/**
	 * @param userType
	 *            the userType to set
	 */
	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	/**
	 * @return the deptId
	 */
	public String getDeptId() {
		return deptId;
	}

	/**
	 * @param deptId
	 *            the deptId to set
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the departName
	 */
	public String getDeptName() {
		return deptName;
	}

	/**
	 * @param deptName
	 *            the deptName to set
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getRname() {
		return rname;
	}

	public void setRname(String rname) {
		this.rname = rname;
	}

	public Integer getRgrade() {
		return rgrade;
	}

	public void setRgrade(Integer grade) {
		this.rgrade = grade;
	}

	public Integer getDzn() {
		return dzn;
	}

	public void setDzn(Integer dzn) {
		this.dzn = dzn;
	}

	public String getDcode() {
		return dcode;
	}

	public void setDcode(String dcode) {
		this.dcode = dcode;
	}

	public String getDname() {
		return dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}
	
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getDeptNameFour() {
		return deptNameFour;
	}

	public void setDeptNameFour(String deptNameFour) {
		this.deptNameFour = deptNameFour;
	}

}
