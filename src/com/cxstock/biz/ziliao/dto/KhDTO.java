package com.cxstock.biz.ziliao.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.cxstock.pojo.Kh;

public class KhDTO {

	private Integer khid;
	private String khname;
	private String lxren;
	private String lxtel;
	private String address;
	private String bz;

	public KhDTO() {
		super();
	}

	public KhDTO(Integer khid, String khname, String lxren, String lxtel,
			String address, String bz) {
		super();
		this.khid = khid;
		this.khname = khname;
		this.lxren = lxren;
		this.lxtel = lxtel;
		this.address = address;
		this.bz = bz;
	}

	public static KhDTO createDto(Kh pojo) {
		KhDTO dto = null;
		if (pojo != null) {
			dto = new KhDTO(pojo.getKhid(),pojo.getKhname(),
					pojo.getLxren(),pojo.getLxtel(),
					pojo.getAddress(),pojo.getBz());
		}
		return dto;
	}

	@SuppressWarnings("unchecked")
	public static List createDtos(Collection pojos) {
		List<KhDTO> list = new ArrayList<KhDTO>();
		if (pojos != null) {
			Iterator iterator = pojos.iterator();
			while (iterator.hasNext()) {
				list.add(createDto((Kh) iterator.next()));
			}
		}
		return list;
	}

	public Integer getKhid() {
		return khid;
	}

	public void setKhid(Integer khid) {
		this.khid = khid;
	}

	public String getKhname() {
		return khname;
	}

	public void setKhname(String khname) {
		this.khname = khname;
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
