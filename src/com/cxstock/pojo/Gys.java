package com.cxstock.pojo;

import java.util.HashSet;
import java.util.Set;

/**
 * Gys entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Gys implements java.io.Serializable {

	// Fields

	private Integer gysid;
	private String name;
	private String lxren;
	private String lxtel;
	private String address;
	private String bz;
	private Set thds = new HashSet(0);
	private Set jhds = new HashSet(0);

	// Constructors

	/** default constructor */
	public Gys() {
	}

	/** full constructor */
	public Gys(String name, String lxren, String lxtel, String address,
			String bz, Set thds, Set jhds) {
		this.name = name;
		this.lxren = lxren;
		this.lxtel = lxtel;
		this.address = address;
		this.bz = bz;
		this.thds = thds;
		this.jhds = jhds;
	}

	// Property accessors

	public Integer getGysid() {
		return this.gysid;
	}

	public void setGysid(Integer gysid) {
		this.gysid = gysid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Set getThds() {
		return this.thds;
	}

	public void setThds(Set thds) {
		this.thds = thds;
	}

	public Set getJhds() {
		return this.jhds;
	}

	public void setJhds(Set jhds) {
		this.jhds = jhds;
	}

}