package com.cxstock.action.ziliao;


import com.cxstock.action.BaseAction;
import com.cxstock.biz.ziliao.GysBiz;
import com.cxstock.biz.ziliao.dto.GysDTO;
import com.cxstock.utils.pubutil.Page;

@SuppressWarnings("serial")
public class GysAction extends BaseAction  {
	
	private GysBiz gysBiz;
	
	private Integer gysid;
	private String name;
	private String lxren;
	private String lxtel;
	private String address;
	private String bz;
	
	/** 
	 * 分页查询供应商列表 
	 */
	public String findPageGys() {
		try {
			Page page = new Page();
			page.setStart(this.getStart());
			page.setLimit(this.getLimit());
			gysBiz.findPageGys(page);
			this.outPageString(page);
		} catch (Exception e) {
			e.printStackTrace();
			this.outError();
		}
		return null;
	}	

	/**
	 * 保存/修改供应商
	 */
	public String saveOrUpdateGys() {
		try {
			GysDTO dto = new GysDTO(gysid,name,lxren,lxtel,address,bz);
			gysBiz.saveOrUpdateGys(dto);
			if(gysid==null){
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
	 * 删除供应商
	 */
	public String deleteGys() {
		try {
			gysBiz.deleteGys(gysid);
			this.outString("{success:true}");
		} catch (Exception e) {
			e.printStackTrace();
			this.outError();
		}
		return null;
	}
	
	/** 
	 * 供应商下拉列表
	 */
	public String findGysComb() {
		try {
			this.outListString(gysBiz.findGysComb());
		} catch (Exception e) {
			e.printStackTrace();
			this.outError();
		}
		return null;
	}

	public void setGysBiz(GysBiz gysBiz) {
		this.gysBiz = gysBiz;
	}

	public void setGysid(Integer gysid) {
		this.gysid = gysid;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLxren(String lxren) {
		this.lxren = lxren;
	}

	public void setLxtel(String lxtel) {
		this.lxtel = lxtel;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}
	
	


}
