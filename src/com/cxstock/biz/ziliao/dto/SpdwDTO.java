package com.cxstock.biz.ziliao.dto;

public class SpdwDTO {
	
	private Integer dwid;
	private String dwname;
	
	public SpdwDTO() {
		super();
	}

	public SpdwDTO(Integer dwid, String dwname) {
		super();
		this.dwid = dwid;
		this.dwname = dwname;
	}

	public Integer getDwid() {
		return dwid;
	}

	public void setDwid(Integer dwid) {
		this.dwid = dwid;
	}

	public String getDwname() {
		return dwname;
	}

	public void setDwname(String dwname) {
		this.dwname = dwname;
	}

}
