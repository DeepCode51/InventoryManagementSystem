package com.cxstock.biz.ziliao.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.cxstock.pojo.Spxx;

public class KcDTO {

	private Integer sl;
	private Double cbj;
	private Double zj;
		
	private String spid;
	private String lbname;
	private String spname;
	private String xinghao;
	private String dw;

	public KcDTO() {
		super();
	}

	public KcDTO(Integer sl, Double cbj, Double zj,
			String spid, String lbname, String spname,
			String xinghao, String dw) {
		super();
		this.sl = sl;
		this.cbj = cbj;
		this.zj = zj;
		this.spid = spid;
		this.lbname = lbname;
		this.spname = spname;
		this.xinghao = xinghao;
		this.dw = dw;
	}
	
	public static KcDTO createDto(Spxx pojo) {
		KcDTO dto = null;
		if (pojo != null) {
			dto = new KcDTO(pojo.getKcsl(),pojo.getJhprice(),
					pojo.getKczj(),pojo.getSpid(),pojo.getLbname(),
					pojo.getSpname(),pojo.getXinghao(),pojo.getDw());
		}
		return dto;
	}

	@SuppressWarnings("unchecked")
	public static List createDtos(Collection pojos) {
		List<KcDTO> list = new ArrayList<KcDTO>();
		if (pojos != null) {
			Iterator iterator = pojos.iterator();
			while (iterator.hasNext()) {
				list.add(createDto((Spxx) iterator.next()));
			}
		}
		return list;
	}

	public Integer getSl() {
		return sl;
	}

	public void setSl(Integer sl) {
		this.sl = sl;
	}

	public Double getCbj() {
		return cbj;
	}

	public void setCbj(Double cbj) {
		this.cbj = cbj;
	}

	public Double getZj() {
		return zj;
	}

	public void setZj(Double zj) {
		this.zj = zj;
	}

	public String getSpid() {
		return spid;
	}

	public void setSpid(String spid) {
		this.spid = spid;
	}

	public String getLbname() {
		return lbname;
	}

	public void setLbname(String lbname) {
		this.lbname = lbname;
	}

	public String getSpname() {
		return spname;
	}

	public void setSpname(String spname) {
		this.spname = spname;
	}

	public String getXinghao() {
		return xinghao;
	}

	public void setXinghao(String xinghao) {
		this.xinghao = xinghao;
	}

	public String getDw() {
		return dw;
	}

	public void setDw(String dw) {
		this.dw = dw;
	}

}
