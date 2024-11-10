package com.cxstock.action.ziliao;


import com.cxstock.action.BaseAction;
import com.cxstock.biz.ziliao.SplbBiz;
import com.cxstock.biz.ziliao.dto.SplbDTO;

@SuppressWarnings("serial")
public class SplbAction extends BaseAction  {
	
	private SplbBiz splbBiz;
	
	private Integer lbid;
	private String lbname;
	private Integer pid;
	
	/** 
	 * 商品类别树
	 */
	public String findSplbTree() {
		try {
			this.outTreeJsonList(splbBiz.findSplbTree());
		} catch (Exception e) {
			e.printStackTrace();
			this.outError();
		}
		return null;
	}	

	/**
	 * 保存/修改商品类别
	 */
	public String saveOrUpdateSplb() {
		try {
			SplbDTO dto = new SplbDTO(lbid,lbname,pid);
			int id = splbBiz.saveOrUpdateSplb(dto);
			if(lbid==null){
				this.outString("{success:true,message:"+id+"}");
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
	 * 删除商品类别
	 */
	public String deleteSplb() {
		try {
			if(splbBiz.deleteSplb(lbid)){
				this.outString("true");
			}else{
				this.outString("false");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.outError();
		}
		return null;
	}

	public void setSplbBiz(SplbBiz splbBiz) {
		this.splbBiz = splbBiz;
	}

	public void setLbid(Integer lbid) {
		this.lbid = lbid;
	}

	public void setLbname(String lbname) {
		this.lbname = lbname;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}
}
