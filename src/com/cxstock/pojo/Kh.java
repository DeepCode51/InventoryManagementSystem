package com.cxstock.pojo;

import java.util.HashSet;
import java.util.Set;

/**
 * Kh entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Kh implements java.io.Serializable {

	// Fields

	private Integer khid;
	private String khname;
	private String lxren;
	private String lxtel;
	private String address;
	private String bz;
	private Set ckds = new HashSet(0);
	private Set tkds = new HashSet(0);

	// Constructors

	/** default constructor */
	public Kh() {
	}

	/** full constructor */
	public Kh(String khname, String lxren, String lxtel, String address,
			String bz, Set ckds, Set tkds) {
		this.khname = khname;
		this.lxren = lxren;
		this.lxtel = lxtel;
		this.address = address;
		this.bz = bz;
		this.ckds = ckds;
		this.tkds = tkds;
	}

	// Property accessors

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

	public String getLxren() {
		return this.lxren;
	}

	public void setLxren(String lxren) {
		this.lxren = lxren;
	}

	public String getLxtel() {
		return this.lxtel;
	}

	public void setLxtel(String lxtel) {
		this.lxtel = lxtel;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public Set getCkds() {
		return this.ckds;
	}

	public void setCkds(Set ckds) {
		this.ckds = ckds;
	}

	public Set getTkds() {
		return this.tkds;
	}

	public void setTkds(Set tkds) {
		this.tkds = tkds;
	}

}