package com.cxstock.pojo;

/**
 * Users entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Users implements java.io.Serializable {

	// Fields

	private Integer userid;
	private Role role;
	private String logincode;
	private String password;
	private String username;
	private Integer state;
	private String bz;

	// Constructors

	/** default constructor */
	public Users() {
	}

	/** minimal constructor */
	public Users(Role role, String logincode, String password, String username,
			Integer state) {
		this.role = role;
		this.logincode = logincode;
		this.password = password;
		this.username = username;
		this.state = state;
	}

	/** full constructor */
	public Users(Role role, String logincode, String password, String username,
			Integer state, String bz) {
		this.role = role;
		this.logincode = logincode;
		this.password = password;
		this.username = username;
		this.state = state;
		this.bz = bz;
	}

	// Property accessors

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getLogincode() {
		return this.logincode;
	}

	public void setLogincode(String logincode) {
		this.logincode = logincode;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

}