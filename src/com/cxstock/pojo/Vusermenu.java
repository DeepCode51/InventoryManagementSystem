package com.cxstock.pojo;

/**
 * VusermenuId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Vusermenu implements java.io.Serializable {

	// Fields

	private Integer userid;
	private String logincode;
	private String username;
	private Integer menuid;
	private String menuname;
	private Integer pid;
	private String menuurl;
	private Integer menutype;
	private Integer ordernum;
	private String icon;

	// Constructors

	/** default constructor */
	public Vusermenu() {
	}

	/** minimal constructor */
	public Vusermenu(Integer userid, String logincode, String username,
			Integer menuid, String menuname, Integer menutype) {
		this.userid = userid;
		this.logincode = logincode;
		this.username = username;
		this.menuid = menuid;
		this.menuname = menuname;
		this.menutype = menutype;
	}

	/** full constructor */
	public Vusermenu(Integer userid, String logincode, String username,
			Integer menuid, String menuname, Integer pid, String menuurl,
			Integer menutype, Integer ordernum, String icon) {
		this.userid = userid;
		this.logincode = logincode;
		this.username = username;
		this.menuid = menuid;
		this.menuname = menuname;
		this.pid = pid;
		this.menuurl = menuurl;
		this.menutype = menutype;
		this.ordernum = ordernum;
		this.icon = icon;
	}

	// Property accessors

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getLogincode() {
		return this.logincode;
	}

	public void setLogincode(String logincode) {
		this.logincode = logincode;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getMenuid() {
		return this.menuid;
	}

	public void setMenuid(Integer menuid) {
		this.menuid = menuid;
	}

	public String getMenuname() {
		return this.menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}

	public Integer getPid() {
		return this.pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getMenuurl() {
		return this.menuurl;
	}

	public void setMenuurl(String menuurl) {
		this.menuurl = menuurl;
	}

	public Integer getMenutype() {
		return this.menutype;
	}

	public void setMenutype(Integer menutype) {
		this.menutype = menutype;
	}

	public Integer getOrdernum() {
		return this.ordernum;
	}

	public void setOrdernum(Integer ordernum) {
		this.ordernum = ordernum;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}