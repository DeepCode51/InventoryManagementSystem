package com.cxstock.action.ziliao;


import com.cxstock.action.BaseAction;
import com.cxstock.biz.ziliao.KcBiz;
import com.cxstock.biz.ziliao.dto.KcDTO;
import com.cxstock.utils.pubutil.Page;

@SuppressWarnings("serial")
public class KcAction extends BaseAction  {
	
	private KcBiz kcBiz;
	
	private Integer sl;
	private Double cbj;
	private Double zj;
	
	private String spid;
	private String lbname;
	private String spname;
	private String xinghao;
	private String dw;
	
	private String addupdate;
	
	/** 分页查询期初库存列表 */
	public String findPageKc() {
		try {
			Page page = new Page();
			page.setStart(this.getStart());
			page.setLimit(this.getLimit());
			kcBiz.findPageKc(page);
			this.outPageString(page);
		} catch (Exception e) {
			e.printStackTrace();
			this.outError();
		}
		return null;
	}	

	/**
	 * 保存/修改期初库存
	 */
	public String saveOrUpdateKc() {
		try {
			if(sl!=null&&cbj!=null)
				zj = sl*cbj;
			KcDTO dto = new KcDTO(sl, cbj, zj, 
					spid, lbname, spname, xinghao, dw);
			boolean bool = kcBiz.saveOrUpdateKc(dto);
			if("add".equals(addupdate)){
				if(bool){
					this.outString("{success:true,message:'保存成功!'}");
				}else{
					this.outString("{success:false,errors:'仓库中已存在该商品。'}");
				}
			}else{
				if(bool){
					this.outString("{success:true,message:'修改成功!'}");
				}else{
					this.outString("{success:false,errors:'该商品已经发生单据，不能修改。'}");
				}
			}
		} catch (Exception e) {
			 e.printStackTrace();
			 this.outError();
		}
		return null;
	}
    
	/**
	 * 删除期初库存
	 */
	public String deleteKc() {
		try {
			boolean bool = kcBiz.deleteKc(spid);
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

	public void setKcBiz(KcBiz kcBiz) {
		this.kcBiz = kcBiz;
	}

	public void setSl(Integer sl) {
		this.sl = sl;
	}

	public void setCbj(Double cbj) {
		this.cbj = cbj;
	}

	public void setZj(Double zj) {
		this.zj = zj;
	}

	public void setSpid(String spid) {
		this.spid = spid;
	}

	public void setLbname(String lbname) {
		this.lbname = lbname;
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

	public void setAddupdate(String addupdate) {
		this.addupdate = addupdate;
	}
}
