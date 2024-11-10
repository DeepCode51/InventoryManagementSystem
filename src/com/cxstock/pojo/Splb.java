package com.cxstock.pojo;

import java.util.HashSet;
import java.util.Set;

/**
 * Splb entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Splb implements java.io.Serializable {

	// Fields

	private Integer lbid;
	private String lbname;
	private Integer pid;
	private Set spxxes = new HashSet(0);

	// Constructors

	/** default constructor */
	public Splb() {
	}

	public Splb(Integer lbid) {
		this.lbid = lbid;
	}

	/** full constructor */
	public Splb(String lbname, Integer pid, Set spxxes) {
		this.lbname = lbname;
		this.pid = pid;
		this.spxxes = spxxes;
	}

	// Property accessors

	public Integer getLbid() {
		return this.lbid;
	}

	public void setLbid(Integer lbid) {
		this.lbid = lbid;
	}

	public String getLbname() {
		return this.lbname;
	}

	public void setLbname(String lbname) {
		this.lbname = lbname;
	}

	public Integer getPid() {
		return this.pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Set getSpxxes() {
		return this.spxxes;
	}

	public void setSpxxes(Set spxxes) {
		this.spxxes = spxxes;
	}

}