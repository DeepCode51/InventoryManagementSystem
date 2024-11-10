package com.cxstock.action.tongji;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.cxstock.action.BaseAction;
import com.cxstock.biz.tongji.TongjiBiz;

@SuppressWarnings("serial")
public class TongjiAction extends BaseAction  {
	
	private TongjiBiz tongjiBiz;
	
	private String startdate;
	private String enddate;
	private String gysid;
	private String khid;
	private String lbid;
	private String search;
	private String jystate;
	
		
	/**
	 * 供应商统计
	 */
	public String findGysTj(){
		try {
			StringBuffer wheres = new StringBuffer();
			if(startdate!=null&&enddate!=null){
				wheres.append(" and t.riqi between '");
				wheres.append(startdate);
				wheres.append("' and '");
				wheres.append(enddate);
				wheres.append("'");
			}
			if(gysid!=null&&!"".equals(gysid)){
				wheres.append(" and t.gysid=");
				wheres.append(gysid);
			}
			if(jystate!=null&&!"".equals(jystate)){
				wheres.append(" and t.jystate=");
				wheres.append(jystate);
			}
			this.outListString(tongjiBiz.findGysTj(wheres.toString()));
		} catch (Exception e) {
			e.printStackTrace();
			this.outError();
		}
		return null;
	}
	
	/**
	 * 客户统计
	 */
	public String findKhTj(){
		try {
			StringBuffer wheres = new StringBuffer();
			if(startdate!=null&&enddate!=null){
				wheres.append(" and t.riqi between '");
				wheres.append(startdate);
				wheres.append("' and '");
				wheres.append(enddate);
				wheres.append("'");
			}
			if(khid!=null&&!"".equals(khid)){
				wheres.append(" and t.khid=");
				wheres.append(khid);
			}
			if(jystate!=null&&!"".equals(jystate)){
				wheres.append(" and t.jystate=");
				wheres.append(jystate);
			}
			this.outListString(tongjiBiz.findKhTj(wheres.toString()));
		} catch (Exception e) {
			e.printStackTrace();
			this.outError();
		}
		return null;
	}
	
	/**
	 * 商品采购统计
	 */
	public String findSpcgTj(){
		try {
			StringBuffer wheres = new StringBuffer("where 1=1");
			if(startdate!=null&&enddate!=null){
				wheres.append(" and t.jhd.riqi between '");
				wheres.append(startdate);
				wheres.append("' and '");
				wheres.append(enddate);
				wheres.append("'");
			}
			if(lbid!=null&&!"".equals(lbid)&&!"0".equals(lbid)){
				wheres.append(" and t.lbid=");
				wheres.append(lbid);
			}
			if(search!=null&&!"".equals(search)){
				wheres.append(" and (t.spid like '%");
				wheres.append(search);
				wheres.append("%' or t.spname like '%");
				wheres.append(search);
				wheres.append("%')");
			}
			this.outListString(tongjiBiz.findSpcgTj(wheres.toString()));
		} catch (Exception e) {
			e.printStackTrace();
			this.outError();
		}
		return null;
	}
	
	/**
	 * 商品销售统计
	 */
	public String findSpxstj(){
		try {
			StringBuffer wheres = new StringBuffer("where 1=1");
			if(startdate!=null&&enddate!=null){
				wheres.append(" and t.ckd.riqi between '");
				wheres.append(startdate);
				wheres.append("' and '");
				wheres.append(enddate);
				wheres.append("'");
			}
			if(lbid!=null&&!"".equals(lbid)&&!"0".equals(lbid)){
				wheres.append(" and t.lbid=");
				wheres.append(lbid);
			}
			if(search!=null&&!"".equals(search)){
				wheres.append(" and (t.spid like '%");
				wheres.append(search);
				wheres.append("%' or t.spname like '%");
				wheres.append(search);
				wheres.append("%')");
			}
			this.outListString(tongjiBiz.findSpxstj(wheres.toString()));
		} catch (Exception e) {
			e.printStackTrace();
			this.outError();
		}
		return null;
	}
	
	/**
	 * 按日统计分析
	 */
	public String findTjfxRi(){
		try {
			StringBuffer wheres = new StringBuffer();
			if(startdate!=null&&enddate!=null){
				wheres.append(" and t.riqi between '");
				wheres.append(startdate);
				wheres.append("' and '");
				wheres.append(enddate);
				wheres.append("' group by t.riqi");
			}
			
			String[] s = startdate.split("-");
			String[] e = enddate.split("-");
			Calendar cs = Calendar.getInstance();
			Calendar ce = Calendar.getInstance();
			cs.set(Integer.parseInt(s[0]),Integer.parseInt(s[1])-1,Integer.parseInt(s[2]));
			ce.set(Integer.parseInt(e[0]),Integer.parseInt(e[1])-1,Integer.parseInt(e[2])+1);
			List<String> dates = new ArrayList<String>();
			while (cs.before(ce)) {
				String year = String.valueOf(cs.get(Calendar.YEAR));
				String month = String.valueOf(cs.get(Calendar.MONTH) + 1);
				String day = String.valueOf(cs.get(Calendar.DAY_OF_MONTH));
				if(month.length()==1)
					month = "0"+month;
				if(day.length()==1)
					day = "0"+day;
				String date = year + "-" + month + "-" + day;
				dates.add(date);
				cs.add(5, 1);
			}
			this.outString(tongjiBiz.findTjfxRi(wheres.toString(),dates));
		} catch (Exception e) {
			e.printStackTrace();
			this.outError();
		}
		return null;
	}
	
	/**
	 * 按月统计分析
	 */
	public String findTjfxYue(){
		try {
			StringBuffer wheres = new StringBuffer();
			if(startdate!=null&&enddate!=null){
				wheres.append(" and t.riqi between '");
				wheres.append(startdate);
				wheres.append("' and '");
				wheres.append(enddate);
				wheres.append("' group by DATE_FORMAT(t.riqi,'%Y-%m')");
			}
			
			String[] s = startdate.split("-");
			String[] e = enddate.split("-");
			Calendar cs = Calendar.getInstance();
			Calendar ce = Calendar.getInstance();
			cs.set(Integer.parseInt(s[0]),Integer.parseInt(s[1])-1,1);
			ce.set(Integer.parseInt(e[0]),Integer.parseInt(e[1]),1);
			List<String> dates = new ArrayList<String>();
			while (cs.before(ce)) {
				String year = String.valueOf(cs.get(Calendar.YEAR));
				String month = String.valueOf(cs.get(Calendar.MONTH) + 1);
				if(month.length()==1)
					month = "0"+month;
				String date = year + "-" + month;
				dates.add(date);
				cs.add(2, 1);
			}
			this.outString(tongjiBiz.findTjfxYue(wheres.toString(),dates));
		} catch (Exception e) {
			e.printStackTrace();
			this.outError();
		}
		return null;
	}

	public void setTongjiBiz(TongjiBiz tongjiBiz) {
		this.tongjiBiz = tongjiBiz;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public void setGysid(String gysid) {
		this.gysid = gysid;
	}

	public void setKhid(String khid) {
		this.khid = khid;
	}

	public void setLbid(String lbid) {
		this.lbid = lbid;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public void setJystate(String jystate) {
		this.jystate = jystate;
	}

	
}
