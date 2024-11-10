package com.cxstock.biz.ziliao;

import com.cxstock.biz.ziliao.dto.KcDTO;
import com.cxstock.utils.pubutil.Page;

public interface KcBiz {
	
	/**
	 * 分页查询库存列表
	 */
	public void findPageKc(Page page);
	
	/**
	 * 保存/修改库存
	 */
	public boolean saveOrUpdateKc(KcDTO dto);
	
	/**
	 * 删除库存
	 */
	public boolean deleteKc(String spid);


}
