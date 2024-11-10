package com.cxstock.pojo;

/**
 * Thdsp entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Thdsp implements java.io.Serializable {

	// Fields

	private Integer id;
	private Thd thd;
	private String spid;
	private String spname;
	private String spdw;
	private String spxinghao;
	private Integer lbid;
	private String lbname;
	private Double dj;
	private Integer sl;
	private Double zj;

	// Constructors

	/** default constructor */
	public Thdsp() {
	}

	/** full constructor */
	public Thdsp(Thd thd, String spid, String spname, String spdw,
			String spxinghao, Integer lbid, String lbname, 
			Double dj, Integer sl, Double zj) {
		this.thd = thd;
		this.spid = spid;
		this.spname = spname;
		this.spdw = spdw;
		this.spxinghao = spxinghao;
		this.lbid = lbid;
		this.lbname = lbname;
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

	public Thd getThd() {
		return this.thd;
	}

	public void setThd(Thd thd) {
		this.thd = thd;
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
	
	public Integer getLbid() {
		return lbid;
	}

	public void setLbid(Integer lbid) {
		this.lbid = lbid;
	}

	public String getLbname() {
		return lbname;
	}

	public void setLbname(String lbname) {
		this.lbname = lbname;
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