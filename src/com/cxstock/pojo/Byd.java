package com.cxstock.pojo;

import java.util.Date;

/**
 * Byd entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Byd implements java.io.Serializable {

	// Fields

	private String djid;
	private Date riqi;
	private Integer userid;
	private String username;
	private String bz;

	// Constructors

	/** default constructor */
	public Byd() {
	}

	/** minimal constructor */
	public Byd(Date riqi) {
		this.riqi = riqi;
	}

	/** full constructor */
	public Byd(Date riqi, Integer userid, String username, String bz) {
		this.riqi = riqi;
		this.userid = userid;
		this.username = username;
		this.bz = bz;
	}

	// Property accessors

	public String getDjid() {
		return this.djid;
	}

	public void setDjid(String djid) {
		this.djid = djid;
	}

	public Date getRiqi() {
		return this.riqi;
	}

	public void setRiqi(Date riqi) {
		this.riqi = riqi;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

}