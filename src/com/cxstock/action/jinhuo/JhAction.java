package com.cxstock.action.jinhuo;


import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import com.cxstock.action.BaseAction;
import com.cxstock.biz.jinhuo.JhBiz;
import com.cxstock.pojo.Jhd;
import com.cxstock.pojo.Thd;

@SuppressWarnings("serial")
public class JhAction extends BaseAction  {
	
	private JhBiz jhBiz;
	
	private String djid;
	private Integer gysid;
	private String gysname;
	private Date riqi;
	private Double yfje;
	private Double sfje;
	private String jystate;
	private String bz;
	
	private String tab;
	private String info;
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
			String code = jhBiz.getDjCode(tab,ymd);
			this.outString(code);
		} catch (Exception e) {
			e.printStackTrace();
			this.outError();
		}
		return null;
	}
	
	/**
	 * 保存/修改进货单
	 */
	public String saveOrUpdateJhd() {
		try {
			Jhd pojo = new Jhd();
			pojo.setDjid(djid);
			pojo.setGysid(gysid);
			pojo.setGysname(gysname);
			pojo.setSfje(sfje);
			pojo.setJystate(jystate);
			pojo.setRiqi(riqi);
			pojo.setUserid(this.getUserDTO().getUserid());
			pojo.setUsername(this.getUserDTO().getUsername());
			pojo.setYfje(yfje);
			pojo.setBz(bz);
			jhBiz.saveOrUpdateJhd(pojo,djsps);
			this.outString("{success:true}");
		} catch (Exception e) {
			 e.printStackTrace();
			 this.outError();
		}
		return null;
	}
	
	/**
	 * 保存/修改退货单
	 */
	public String saveOrUpdateThd() {
		try {
			Thd pojo = new Thd();
			pojo.setDjid(djid);
			pojo.setGysid(gysid);
			pojo.setGysname(gysname);
			pojo.setSfje(sfje);
			pojo.setJystate(jystate);
			pojo.setRiqi(riqi);
			pojo.setUserid(this.getUserDTO().getUserid());
			pojo.setUsername(this.getUserDTO().getUsername());
			pojo.setYfje(yfje);
			pojo.setBz(bz);
			jhBiz.saveOrUpdateThd(pojo,djsps);
			this.outString("{success:true}");
		} catch (Exception e) {
			 e.printStackTrace();
			 this.outError();
		}
		return null;
	}
	
    
	/**
	 * 删除进货单
	 */
	public String deleteJhd() {
		try {
			jhBiz.deleteJhd(djid);
			this.outString("{success:true}");
		} catch (Exception e) {
			e.printStackTrace();
			this.outError();
		}
		return null;
	}
	
	/**
	 * 删除退货单
	 */
	public String deleteThd() {
		try {
			jhBiz.deleteThd(djid);
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
			if(jystate!=null&&!"".equals(jystate)){
				wheres.append(" and t.jystate=");
				wheres.append(jystate);
			}
			this.outListString(jhBiz.findDjByParams(tab,wheres.toString()));
		} catch (Exception e) {
			e.printStackTrace();
			this.outError();
		}
		return null;
	}
	
	/**
	 * 查询单据商品
	 */
	@SuppressWarnings("unchecked")
	public String findDjspByParams(){
		try {
			StringBuffer wheres = new StringBuffer(" where 1=1");
			if(djid!=null){
				wheres.append(" and t."+info+".djid='");
				wheres.append(djid);
				wheres.append("'");
			}
			List list = jhBiz.findDjByParams(tab,wheres.toString());
			JSONArray jsonArray = new JSONArray();
			if(list.size()>0){
				JsonConfig config = new JsonConfig();
				// 过滤相关的属性即可
				config.setJsonPropertyFilter(new PropertyFilter() {
					public boolean apply(Object source, String name, Object value) {
						if (name.equals(info)) {
							return true;
						}
						return false;
					}
				});
				jsonArray = JSONArray.fromObject(list,config);
			}
			String jsonString = "{total:" + list.size() + ",root:"+ jsonArray.toString() + "}";
			outString(jsonString);
		} catch (Exception e) {
			e.printStackTrace();
			this.outError();
		}
		return null;
	}

	public void setJhBiz(JhBiz jhBiz) {
		this.jhBiz = jhBiz;
	}

	public void setDjid(String djid) {
		this.djid = djid;
	}

	public void setGysid(Integer gysid) {
		this.gysid = gysid;
	}

	public void setGysname(String gysname) {
		this.gysname = gysname;
	}

	public void setRiqi(Date riqi) {
		this.riqi = riqi;
	}

	public void setYfje(Double yfje) {
		this.yfje = yfje;
	}

	public void setSfje(Double sfje) {
		this.sfje = sfje;
	}

	public void setJystate(String jystate) {
		this.jystate = jystate;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public void setTab(String tab) {
		this.tab = tab;
	}

	public void setInfo(String info) {
		this.info = info;
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
