package com.cxstock.biz.ziliao.imp;

import java.util.List;

import com.cxstock.biz.ziliao.KcBiz;
import com.cxstock.biz.ziliao.dto.KcDTO;
import com.cxstock.dao.BaseDAO;
import com.cxstock.pojo.Spxx;
import com.cxstock.utils.pubutil.Page;

public class KcBizImpl implements KcBiz {
	
	private BaseDAO baseDao;
	public void setBaseDao(BaseDAO baseDao) {
		this.baseDao = baseDao;
	}
	
	/*
	 * 分页查询库存列表
	 * @see com.cxstock.biz.power.KcBiz#findPageKc(com.cxstock.utils.system.Page)
	 */
	@SuppressWarnings("unchecked")
	public void findPageKc(Page page) {
		String hql = "from Spxx as t where t.state<>0";
		List list = baseDao.findByHql(hql, page.getStart(), page.getLimit());
		List dtoList = KcDTO.createDtos(list);
		int total = baseDao.countQuery("select count(*)" + hql.replace("inner join fetch t.spxx", ""));
		page.setRoot(dtoList);
		page.setTotal(total);
	}
	/*
	 * 保存/修改库存
	 * @see com.cxstock.biz.power.KcBiz#saveOrUpdateKc(com.cxstock.biz.power.dto.KcDTO)
	 */
	@SuppressWarnings("unchecked")
	public boolean saveOrUpdateKc(KcDTO dto) {
		Spxx spxx = (Spxx)baseDao.loadById(Spxx.class, dto.getSpid());
		if("2".equals(spxx.getState())){
			return false;
		}
		spxx.setJhprice(dto.getCbj());
		spxx.setScjj(dto.getCbj());
		spxx.setKcsl(dto.getSl());
		spxx.setKczj(dto.getZj());
		spxx.setState("1");
		baseDao.saveOrUpdate(spxx);
		return true;
	}
	
	/*
	 * 删除库存
	 * @see com.cxstock.biz.power.KcBiz#deleteKc(java.lang.String)
	 */
	public boolean deleteKc(String spid) {
		Spxx spxx = (Spxx)baseDao.loadById(Spxx.class, spid);
		if("2".equals(spxx.getState())){
			return false;
		}
		spxx.setJhprice(0d);
		spxx.setScjj(0d);
		spxx.setKcsl(0);
		spxx.setKczj(0d);
		spxx.setState("0");
		baseDao.saveOrUpdate(spxx);
		return true;
	}

}
