package com.cxstock.biz.kucun;

import java.util.List;

@SuppressWarnings("unchecked")
public interface SearchBiz {

	/**
	 * 库存查询
	 * @param wheres  查询条件
	 * @return
	 */
	public List findKcByParams(Integer kfid,Integer lbid,String search);

	/**
	 * 库存报警
	 */
	public List findBaoJingSpxx();


}
