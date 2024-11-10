package com.cxstock.biz.kucun.imp;

import java.util.List;

import com.cxstock.biz.kucun.SearchBiz;
import com.cxstock.dao.DqkcDAO;

@SuppressWarnings("unchecked")
public class SearchBizImpl implements SearchBiz {
	
	private DqkcDAO dqkcDao;
	public void setDqkcDao(DqkcDAO dqkcDao) {
		this.dqkcDao = dqkcDao;
	}

	/*
	 * 库存查询
	 */
	public List findKcByParams(Integer kfid,Integer lbid,String search) {
		return dqkcDao.getDqkcByParams(kfid, lbid, search);
	}

	/*
	 * 库存报警
	 */
	public List findBaoJingSpxx() {
		String hql = "from Spxx as t where t.kcsl<=t.minnum";
		return dqkcDao.findByHql(hql);
	}
	


}
