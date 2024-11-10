package com.cxstock.action.power;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.cxstock.action.BaseAction;
import com.cxstock.utils.system.DataCopy;

@SuppressWarnings("serial")
public class DataCopyAction extends BaseAction  {
	
	private String datapath;
	private String datafile;
	private String delstate;
	
	/**
	 * 备份数据库
	 */
	public String backup(){
		try {
			SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm");
			String d = bartDateFormat.format(new Date());
			DataCopy.backup(datapath+"data_"+d+".sql");
			this.outString("true");
		} catch (Exception e) {
			e.printStackTrace();
			this.outString("false");
		}
		return null;
	}

	/**
	 * 恢复数据库
	 */
	public String load() {
		try {
			SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm");
			String d = bartDateFormat.format(new Date());
			DataCopy.backup("D:\\MyStockData\\old_"+d+".sql");
			DataCopy.load(datafile);
			this.outString("true");
		} catch (Exception e) {
			e.printStackTrace();
			this.outString("false");
		}
		return null;
	}
	
	/**
	 * 系统初始化（删除所有营业数据）
	 */
	public String delete(){
		try {
			SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm");
			String d = bartDateFormat.format(new Date());
			DataCopy.backup("D:\\MyStockData\\del_"+d+".sql");
			DataCopy.delete(delstate);
			this.outString("true");
		} catch (Exception e) {
			e.printStackTrace();
			this.outString("false");
		}
		return null;
	}

	public void setDatapath(String datapath) {
		this.datapath = datapath;
	}

	public void setDatafile(String datafile) {
		this.datafile = datafile;
	}

	public void setDelstate(String delstate) {
		this.delstate = delstate;
	}
	
	
}
