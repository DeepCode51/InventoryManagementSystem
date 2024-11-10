package com.cxstock.pojo;

import java.util.Date;

/**
 * Thd entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Thd implements java.io.Serializable {

	// Fields

	private String djid;
	private Integer gysid;
	private String gysname;
	private Date riqi;
	private Double yfje;
	private Double sfje;
	private String jystate;
	private Integer userid;
	private String username;
	private String bz;

	// Constructors

	/** default constructor */
	public Thd() {
	}

	/** minimal constructor */
	public Thd(Date riqi) {
		this.riqi = riqi;
	}

	/** full constructor */
	public Thd(Integer gysid, String gysname, Date riqi, Double yfje,
			Double sfje, String jystate, Integer userid, String username,
			String bz) {
		this.gysid = gysid;
		this.gysname = gysname;
		this.riqi = riqi;
		this.yfje = yfje;
		this.sfje = sfje;
		this.jystate = jystate;
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

	public Integer getGysid() {
		return this.gysid;
	}

	public void setGysid(Integer gysid) {
		this.gysid = gysid;
	}

	public String getGysname() {
		return this.gysname;
	}

	public void setGysname(String gysname) {
		this.gysname = gysname;
	}

	public Date getRiqi() {
		return this.riqi;
	}

	public void setRiqi(Date riqi) {
		this.riqi = riqi;
	}

	public Double getYfje() {
		return this.yfje;
	}

	public void setYfje(Double yfje) {
		this.yfje = yfje;
	}

	public Double getSfje() {
		return this.sfje;
	}

	public void setSfje(Double sfje) {
		this.sfje = sfje;
	}

	public String getJystate() {
		return this.jystate;
	}

	public void setJystate(String jystate) {
		this.jystate = jystate;
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