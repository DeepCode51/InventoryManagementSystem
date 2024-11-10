package com.cxstock.biz.ziliao.dto;

public class SplbDTO {
	
	private Integer lbid;
	private String lbname;
	private Integer pid;
	
	public SplbDTO() {
		super();
	}

	public SplbDTO(Integer lbid, String lbname, Integer pid) {
		super();
		this.lbid = lbid;
		this.lbname = lbname;
		this.pid = pid;
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

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

}
