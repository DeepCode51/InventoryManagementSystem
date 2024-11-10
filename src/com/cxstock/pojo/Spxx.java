package com.cxstock.pojo;

/**
 * Spxx entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Spxx implements java.io.Serializable {

	// Fields

	private String spid;
	private String spname;
	private String xinghao;
	private Integer lbid;
	private String lbname;
	private String dw;
	private Double jhprice;
	private Double chprice;
	private Double scjj;
	private Integer kcsl;
	private Double kczj;
	private Integer minnum;
	private String csname;
	private String state;
	private String bz;

	// Constructors

	/** default constructor */
	public Spxx() {
	}
	
	public Spxx(String spid) {
		this.spid = spid;
	}

	/** minimal constructor */
	public Spxx(String spname, Integer lbid, String lbname) {
		this.spname = spname;
		this.lbid = lbid;
		this.lbname = lbname;
	}
	
	/** full constructor */
	public Spxx(String spid,String spname, String xinghao, Integer lbid, String lbname,
			String dw, Double jhprice, Double chprice, Double scjj,
			Integer kcsl, Double kczj, Integer minnum, String csname,
			String state, String bz) {
		this.spid = spid;
		this.spname = spname;
		this.xinghao = xinghao;
		this.lbid = lbid;
		this.lbname = lbname;
		this.dw = dw;
		this.jhprice = jhprice;
		this.chprice = chprice;
		this.scjj = scjj;
		this.kcsl = kcsl;
		this.kczj = kczj;
		this.minnum = minnum;
		this.csname = csname;
		this.state = state;
		this.bz = bz;
	}

	// Property accessors

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

	public String getXinghao() {
		return this.xinghao;
	}

	public void setXinghao(String xinghao) {
		this.xinghao = xinghao;
	}

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

	public String getDw() {
		return this.dw;
	}

	public void setDw(String dw) {
		this.dw = dw;
	}

	public Double getJhprice() {
		return this.jhprice;
	}

	public void setJhprice(Double jhprice) {
		this.jhprice = jhprice;
	}

	public Double getChprice() {
		return this.chprice;
	}

	public void setChprice(Double chprice) {
		this.chprice = chprice;
	}

	public Double getScjj() {
		return this.scjj;
	}

	public void setScjj(Double scjj) {
		this.scjj = scjj;
	}

	public Integer getKcsl() {
		return this.kcsl;
	}

	public void setKcsl(Integer kcsl) {
		this.kcsl = kcsl;
	}

	public Double getKczj() {
		return this.kczj;
	}

	public void setKczj(Double kczj) {
		this.kczj = kczj;
	}

	public Integer getMinnum() {
		return this.minnum;
	}

	public void setMinnum(Integer minnum) {
		this.minnum = minnum;
	}

	public String getCsname() {
		return this.csname;
	}

	public void setCsname(String csname) {
		this.csname = csname;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

}