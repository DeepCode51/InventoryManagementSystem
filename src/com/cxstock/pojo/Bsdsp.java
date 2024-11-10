package com.cxstock.pojo;

/**
 * Bsdsp entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Bsdsp implements java.io.Serializable {

	// Fields

	private Integer id;
	private String djid;
	private String spid;
	private String spname;
	private String spdw;
	private String spxinghao;
	private Double dj;
	private Integer sl;
	private Double zj;

	// Constructors

	/** default constructor */
	public Bsdsp() {
	}

	/** minimal constructor */
	public Bsdsp(String djid, String spid) {
		this.djid = djid;
		this.spid = spid;
	}

	/** full constructor */
	public Bsdsp(String djid, String spid, String spname, String spdw,
			String spxinghao, Double dj, Integer sl, Double zj) {
		this.djid = djid;
		this.spid = spid;
		this.spname = spname;
		this.spdw = spdw;
		this.spxinghao = spxinghao;
		this.dj = dj;
		this.sl = sl;
		this.zj = zj;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDjid() {
		return this.djid;
	}

	public void setDjid(String djid) {
		this.djid = djid;
	}

	public String getSpid() {
		return this.spid;
	}

	public void setSpid(String spid) {
		this.spid = spid;
	}

	public String getSpname() {
		return this.spname;
	}

	public void setSpname(String spname) {
		this.spname = spname;
	}

	public String getSpdw() {
		return this.spdw;
	}

	public void setSpdw(String spdw) {
		this.spdw = spdw;
	}

	public String getSpxinghao() {
		return this.spxinghao;
	}

	public void setSpxinghao(String spxinghao) {
		this.spxinghao = spxinghao;
	}

	public Double getDj() {
		return this.dj;
	}

	public void setDj(Double dj) {
		this.dj = dj;
	}

	public Integer getSl() {
		return this.sl;
	}

	public void setSl(Integer sl) {
		this.sl = sl;
	}

	public Double getZj() {
		return this.zj;
	}

	public void setZj(Double zj) {
		this.zj = zj;
	}

}