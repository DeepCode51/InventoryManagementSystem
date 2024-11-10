package com.cxstock.pojo;


/**
 * Menu entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class Menu implements java.io.Serializable {

	// Fields

	private Integer menuid;
	private String menuname;
	private Integer pid;
	private String menuurl;
	private Integer menutype;
	private Integer ordernum;
	private String icon;

	// Constructors

	/** default constructor */
	public Menu() {
	}

	public Menu(Integer menuid) {
		super();
		this.menuid = menuid;
	}

	/** minimal constructor */
	public Menu(String menuname, Integer menutype) {
		this.menuname = menuname;
		this.menutype = menutype;
	}

	/** full constructor */
	public Menu(String menuname, Integer pid, String menuurl, Integer menutype,
			Integer ordernum, String icon) {
		this.menuname = menuname;
		this.pid = pid;
		this.menuurl = menuurl;
		this.menutype = menutype;
		this.ordernum = ordernum;
		this.icon = icon;
	}
	
	public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Menu) ) return false;
		 Menu func = (Menu) other; 
		 return this.getMenuid()==func.getMenuid();
	}

	// Property accessors

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