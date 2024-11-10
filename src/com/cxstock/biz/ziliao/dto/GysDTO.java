package com.cxstock.biz.ziliao.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.cxstock.pojo.Gys;

public class GysDTO {

	private Integer gysid;
	private String name;
	private String lxren;
	private String lxtel;
	private String address;
	private String bz;

	public GysDTO() {
		super();
	}

	public GysDTO(Integer gysid, String name, String lxren, String lxtel,
			String address, String bz) {
		super();
		this.gysid = gysid;
		this.name = name;
		this.lxren = lxren;
		this.lxtel = lxtel;
		this.address = address;
		this.bz = bz;
	}

	public static GysDTO createDto(Gys pojo) {
		GysDTO dto = null;
		if (pojo != null) {
			dto = new GysDTO(pojo.getGysid(), pojo.getName(),
					pojo.getLxren(), pojo.getLxtel(),
					pojo.getAddress(),pojo.getBz());
		}
		return dto;
	}

	@SuppressWarnings("unchecked")
	public static List createDtos(Collection pojos) {
		List<GysDTO> list = new ArrayList<GysDTO>();
		if (pojos != null) {
			Iterator iterator = pojos.iterator();
			while (iterator.hasNext()) {
				list.add(createDto((Gys) iterator.next()));
			}
		}
		return list;
	}

	public Integer getGysid() {
		return gysid;
	}

	public void setGysid(Integer gysid) {
		this.gysid = gysid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLxren() {
		return lxren;
	}

	public void setLxren(String lxren) {
		this.lxren = lxren;
	}

	public String getLxtel() {
		return lxtel;
	}

	public void setLxtel(String lxtel) {
		this.lxtel = lxtel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

}
