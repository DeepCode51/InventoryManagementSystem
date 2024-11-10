package com.cxstock.biz.ziliao;

import java.util.List;

import com.cxstock.biz.ziliao.dto.GysDTO;
import com.cxstock.utils.pubutil.ComboData;
import com.cxstock.utils.pubutil.Page;

public interface GysBiz {
	
	/**
	 * 分页查询供应商列表
	 */
	public void findPageGys(Page page);
	
	/**
	 * 保存/修改供应商
	 */
	public void saveOrUpdateGys(GysDTO dto);
	
	/**
	 * 删除供应商
	 */
	public void deleteGys(Integer Gysid);

	/**
	 * 供应商下拉列表
	 */
	public List<ComboData> findGysComb();


}
