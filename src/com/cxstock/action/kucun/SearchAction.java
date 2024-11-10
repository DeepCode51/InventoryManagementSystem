package com.cxstock.action.kucun;


import com.cxstock.action.BaseAction;
import com.cxstock.biz.kucun.SearchBiz;

@SuppressWarnings("serial")
public class SearchAction extends BaseAction  {
	
	private SearchBiz searchBiz;
	
	private Integer kfid;
	private Integer lbid;
	private String search;
	
	/**
	 * 库存查询
	 */
	public String findKcByParams(){
		try {
			this.outListString(searchBiz.findKcByParams(kfid,lbid,search));
		} catch (Exception e) {
			e.printStackTrace();
			this.outError();
		}
		return null;
	}
	
	/**
	 * 库存报警
	 * @param searchBiz
	 */
	public String findBaoJingSpxx(){
		try {
			this.outListString(searchBiz.findBaoJingSpxx());
		} catch (Exception e) {
			e.printStackTrace();
			this.outError();
		}
		return null;
	}

	public void setSearchBiz(SearchBiz searchBiz) {
		this.searchBiz = searchBiz;
	}

	public void setKfid(Integer kfid) {
		this.kfid = kfid;
	}

	public void setLbid(Integer lbid) {
		this.lbid = lbid;
	}

	public void setSearch(String search) {
		this.search = search;
	}

}
