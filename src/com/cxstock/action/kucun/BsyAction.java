package com.cxstock.action.kucun;


import java.util.Date;

import com.cxstock.action.BaseAction;
import com.cxstock.biz.kucun.BsyBiz;
import com.cxstock.pojo.Bsd;
import com.cxstock.pojo.Byd;

@SuppressWarnings("serial")
public class BsyAction extends BaseAction  {
	
	private BsyBiz bsyBiz;
	
	private String djid;
	private Date riqi;
	private String bz;
	
	private String tab;
	private String ymd;
	private String djsps; //单据商品集合字符串
	
	private String startdate;
	private String enddate;
	private String search;
	
	/**
	 * 生成单据编号
	 */
	public String getDjCode(){
		try {
			String code = bsyBiz.getDjCode(tab,ymd);
			this.outString(code);
		} catch (Exception e) {
			e.printStackTrace();
			this.outError();
		}
		return null;
	}
	
	/**
	 * 保存/修改报损单
	 */
	public String saveOrUpdateBsd() {
		try {
			Bsd pojo = new Bsd();
			pojo.setDjid(djid);
			pojo.setRiqi(riqi);
			pojo.setUserid(this.getUserDTO().getUserid());
			pojo.setUsername(this.getUserDTO().getUsername());
			pojo.setBz(bz);
			bsyBiz.saveOrUpdateBsd(pojo,djsps);
			this.outString("{success:true}");
		} catch (Exception e) {
			 e.printStackTrace();
			 this.outError();
		}
		return null;
	}
	
	/**
	 * 保存/修改报溢单
	 */
	public String saveOrUpdateByd() {
		try {
			Byd pojo = new Byd();
			pojo.setDjid(djid);
			pojo.setRiqi(riqi);
			pojo.setUserid(this.getUserDTO().getUserid());
			pojo.setUsername(this.getUserDTO().getUsername());
			pojo.setBz(bz);
			bsyBiz.saveOrUpdateByd(pojo,djsps);
			this.outString("{success:true}");
		} catch (Exception e) {
			 e.printStackTrace();
			 this.outError();
		}
		return null;
	}
	
    
	/**
	 * 删除报损单
	 */
	public String deleteJhd() {
		try {
			//jhdBiz.deleteJhd(kcid);
			this.outString("{success:true}");
		} catch (Exception e) {
			e.printStackTrace();
			this.outError();
		}
		return null;
	}
	
	/**
	 * 按条件查询单据
	 */
	public String findDjByParams(){
		try {
			StringBuffer wheres = new StringBuffer(" where 1=1");
			if(startdate!=null&&enddate!=null){
				wheres.append(" and t.riqi between '");
				wheres.append(startdate);
				wheres.append("' and '");
				wheres.append(enddate);
				wheres.append("'");
			}
			if(search!=null&&!"".equals(search)){
				wheres.append(" and (t.djid like '%");
				wheres.append(search);
				wheres.append("%'");
				wheres.append(" or t.gysname like '%");
				wheres.append(search);
				wheres.append("%')");
			}else if(djid!=null){
				wheres.append(" and t.djid='");
				wheres.append(djid);
				wheres.append("'");
			}
			startdate=null;
			enddate=null;
			search=null;
			djid=null;
			this.outListString(bsyBiz.findDjByParams(tab,wheres.toString()));
		} catch (Exception e) {
			e.printStackTrace();
			this.outError();
		}
		return null;
	}


	public void setBsyBiz(BsyBiz bsyBiz) {
		this.bsyBiz = bsyBiz;
	}

	public void setDjid(String djid) {
		this.djid = djid;
	}

	public void setRiqi(Date riqi) {
		this.riqi = riqi;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public void setTab(String tab) {
		this.tab = tab;
	}

	public void setYmd(String ymd) {
		this.ymd = ymd;
	}

	public void setDjsps(String djsps) {
		this.djsps = djsps;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public void setSearch(String search) {
		this.search = search;
	}
	

}
