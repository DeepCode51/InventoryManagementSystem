package com.cxstock.biz.ziliao.imp;

import java.util.List;

import com.cxstock.biz.ziliao.SpxxBiz;
import com.cxstock.dao.BaseDAO;
import com.cxstock.pojo.Spxx;
import com.cxstock.utils.pubutil.Page;

public class SpxxBizImpl implements SpxxBiz {
	
	private BaseDAO baseDao;
	public void setBaseDao(BaseDAO baseDao) {
		this.baseDao = baseDao;
	}
	
	/*
	 * 商品编号
	 */
	@SuppressWarnings("unchecked")
	public String getSpxxCode() {
		String hql = "select max(spid) from Spxx";
		List list = baseDao.findByHql(hql);
		Object obj = list.get(0);
		if(obj!=null){
			Integer code = Integer.valueOf(obj.toString())+1;
			String codes = code.toString();
			int length = codes.length();
			for (int i = 4; i > length; i--) {
				codes = "0"+codes;
			}
			return codes;
		}else{
			return "0001";
		}
	}
	
	/*
	 * 分页查询商品列表
	 */
	@SuppressWarnings("unchecked")
	public void findPageSpxx(Page page) {
		String hql = "from Spxx as t";
		if(page.getWheres()!=null){
			hql+=page.getWheres();
		}
		List list = baseDao.findByHql(hql, page.getStart(), page.getLimit());
		int total = baseDao.countQuery("select count(*) "+hql.toString());
		page.setRoot(list);
		page.setTotal(total);
	}
	
	/*
	 * 保存商品
	 */
	public void save(Spxx dto) {
		baseDao.save(dto);
	}
	
	/*
	 * 修改商品
	 */
	public void updateSpxx(Spxx dto) {
		Spxx spxx = (Spxx)baseDao.loadById(Spxx.class, dto.getSpid());
		spxx.setSpid(dto.getSpid());
		spxx.setSpname(dto.getSpname());
		spxx.setXinghao(dto.getXinghao());
		spxx.setDw(dto.getDw());
		if(dto.getJhprice()!= null)
			spxx.setJhprice(dto.getJhprice());
		if(dto.getChprice()!= null)
			spxx.setChprice(dto.getChprice());
		if(dto.getMinnum()!=null)
			spxx.setMinnum(dto.getMinnum());
		spxx.setCsname(dto.getCsname());
		spxx.setBz(dto.getBz());
		spxx.setLbid(dto.getLbid());
		spxx.setLbname(dto.getLbname());
		baseDao.save(spxx);
	}
	
	/*
	 * 删除商品
	 */
	public boolean deleteSpxx(String spid) {
		Spxx spxx = (Spxx)baseDao.loadById(Spxx.class, spid);
		if("2".equals(spxx.getState())){
			return false;
		}
		baseDao.delete(spxx);	
		return true;
	}

}
