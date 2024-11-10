package com.cxstock.pojo;

import java.util.Date;

/**
 * Tkd entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Tkd implements java.io.Serializable {

	// Fields

	private String djid;
	private Integer khid;
	private String khname;
	private Date riqi;
	private Double yfje;
	private Double sfje;
	private Double cbje;
	private String jystate;
	private Integer userid;
	private String username;
	private String bz;

	// Constructors

	/** default constructor */
	public Tkd() {
	}

	/** minimal constructor */
	public Tkd(Date riqi) {
		this.riqi = riqi;
	}

	/** full constructor */
	public Tkd(Integer khid, String khname, Date riqi, Double yfje,
			Double sfje, String jystate, Integer userid, String username,
			String bz) {
		this.khid = khid;
		this.khname = khname;
		this.riqi = riqi;
		this.yfje = yfje;
		this.sfje = sfje;
		this.jystate = jystate;
		this.userid = userid;
		this.username = username;
		this.bz = bz;
	}
	
	public Tkd(Integer khid, String khname, Date riqi, Double yfje,
			Double sfje, Double cbje, String jystate, Integer userid, String username,
			String bz) {
		this.khid = khid;
		this.khname = khname;
		this.riqi = riqi;
		this.yfje = yfje;
		this.sfje = sfje;
		this.cbje = cbje;
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

	public Integer getKhid() {
		return this.khid;
	}

	public void setKhid(Integer khid) {
		this.khid = khid;
	}

	public String getKhname() {
		return this.khname;
	}

	public void setKhname(String khname) {
		this.khname = khname;
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

	public Double getCbje() {
		return cbje;
	}

	public void setCbje(Double cbje) {
		this.cbje = cbje;
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