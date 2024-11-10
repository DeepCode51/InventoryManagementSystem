package com.cxstock.action.ziliao;


import com.cxstock.action.BaseAction;
import com.cxstock.biz.ziliao.SpxxBiz;
import com.cxstock.pojo.Spxx;
import com.cxstock.utils.pubutil.Page;

@SuppressWarnings("serial")
public class SpxxAction extends BaseAction  {
	
	private SpxxBiz spxxBiz;
	
	private String spid;
	private String spname;
	private String xinghao;
	private String dw;
	private Double jhprice;
	private Double chprice;
	private Integer minnum;
	private String csname;
	private String bz;
	
	private Integer lbid;
	private String lbname;
	
	private String search;
	private String addupdate;
	
	/*
	 * 商品编号
	 */
	@SuppressWarnings("unchecked")
	public String getSpxxCode() {
		try {
			String code = spxxBiz.getSpxxCode();
			this.outString(code);
		} catch (Exception e) {
			e.printStackTrace();
			this.outError();
		}
		return null;
	}
	
	/** 
	 * 分页查询商品信息列表 
	 */
	public String findPageSpxx() {
		try {
			Page page = new Page();
			page.setStart(this.getStart());
			page.setLimit(this.getLimit());
			if(lbid!=null&&lbid!=0){
				page.setWheres(" where t.lbid="+lbid);
			}else if(search!=null&&!"".equals(search)){
				StringBuffer buf = new StringBuffer(" where t.spid like '%");
				buf.append(search);
				buf.append("%' or t.spname like '%");
				buf.append(search);
				buf.append("%'");
				page.setWheres(buf.toString());
			}
			spxxBiz.findPageSpxx(page);
			this.outPageString(page);
		} catch (Exception e) {
			e.printStackTrace();
			this.outError();
		}
		return null;
	}	
	
	/** 
	 * 期初库存备选商品列表
	 */
	public String findKcPageSpxx() {
		try {
			Page page = new Page();
			page.setStart(this.getStart());
			page.setLimit(this.getLimit());
			StringBuffer buf = new StringBuffer();
			if(search!=null&&!"".equals(search)){
				buf = new StringBuffer(" where t.spid like '%");
				buf.append(search);
				buf.append("%' or t.spname like '%");
				buf.append(search);
				buf.append("%'");
				page.setWheres(buf.toString());
			}else{
				buf = new StringBuffer(" where t.state=0");
			}
			page.setWheres(buf.toString());
			spxxBiz.findPageSpxx(page);
			this.outPageString(page);
		} catch (Exception e) {
			e.printStackTrace();
			this.outError();
		}
		return null;
	}

	/**
	 * 保存/修改商品信息
	 */
	public String saveOrUpdateSpxx() {
		try {
			Spxx spxx = new Spxx(spid, spname, xinghao, lbid, lbname, dw, 
					jhprice, chprice, jhprice, 0, 0d, minnum, csname, "0", bz);
			if("add".equals(addupdate)){
				if(jhprice==null){
					spxx.setJhprice(0d);
					spxx.setScjj(0d);
				}
				if(chprice==null)
					spxx.setChprice(0d);
				if(minnum==null)
					spxx.setMinnum(0);
				spxxBiz.save(spxx);
				this.outString("{success:true,message:'保存成功!'}");
			}else{
				spxxBiz.updateSpxx(spxx);
				this.outString("{success:true,message:'修改成功!'}");
			}
		} catch (Exception e) {
			 e.printStackTrace();
			 this.outError();
		}
		return null;
	}
    
	/**
	 * 删除商品信息
	 */
	public String deleteSpxx() {
		try {
			boolean bool = spxxBiz.deleteSpxx(spid);
			if(bool){
				this.outString("{success:true}");
			}else{
				this.outString("{success:false,error:'该商品已经发生单据，不能删除。'}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.outError();
		}
		return null;
	}

	public void setSpxxBiz(SpxxBiz spxxBiz) {
		this.spxxBiz = spxxBiz;
	}

	public void setSpid(String spid) {
		this.spid = spid;
	}

	public void setSpname(String spname) {
		this.spname = spname;
	}

	public void setXinghao(String xinghao) {
		this.xinghao = xinghao;
	}

	public void setDw(String dw) {
		this.dw = dw;
	}

	public void setJhprice(Double jhprice) {
		this.jhprice = jhprice;
	}

	public void setChprice(Double chprice) {
		this.chprice = chprice;
	}

	public void setMinnum(Integer minnum) {
		this.minnum = minnum;
	}

	public void setCsname(String csname) {
		this.csname = csname;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public void setLbid(Integer lbid) {
		this.lbid = lbid;
	}

	public void setLbname(String lbname) {
		this.lbname = lbname;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public void setAddupdate(String addupdate) {
		this.addupdate = addupdate;
	}

}
