package com.cxstock.action.ziliao;


import com.cxstock.action.BaseAction;
import com.cxstock.biz.ziliao.SpdwBiz;
import com.cxstock.biz.ziliao.dto.SpdwDTO;

@SuppressWarnings("serial")
public class SpdwAction extends BaseAction  {
	
	private SpdwBiz spdwBiz;
	
	private Integer dwid;
	private String dwname;
	
	/** 
	 * 单位列表 
	 */
	public String findAllSpdw() {
		try {
			this.outListString(spdwBiz.findAllSpdw());
		} catch (Exception e) {
			e.printStackTrace();
			this.outError();
		}
		return null;
	}

	/**
	 * 保存/修改单位
	 */
	public String saveOrUpdateSpdw() {
		try {
			SpdwDTO dto = new SpdwDTO(dwid,dwname);
			spdwBiz.saveOrUpdateSpdw(dto);
			if(dwid==null){
				this.outString("{success:true,message:'保存成功!'}");
			}else{
				this.outString("{success:true,message:'修改成功!'}");
			}
		} catch (Exception e) {
			 e.printStackTrace();
			 this.outError();
		}
		return null;
	}
    
	/**
	 * 删除单位
	 */
	public String deleteSpdw() {
		try {
			spdwBiz.deleteSpdw(dwid);
			this.outString("{success:true}");
		} catch (Exception e) {
			e.printStackTrace();
			this.outError();
		}
		return null;
	}

	public void setSpdwBiz(SpdwBiz spdwBiz) {
		this.spdwBiz = spdwBiz;
	}

	public void setDwid(Integer dwid) {
		this.dwid = dwid;
	}

	public void setDwname(String dwname) {
		this.dwname = dwname;
	}
}
