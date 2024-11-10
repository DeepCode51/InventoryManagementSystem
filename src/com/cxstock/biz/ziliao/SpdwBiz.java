package com.cxstock.biz.ziliao;

import java.util.List;

import com.cxstock.biz.ziliao.dto.SpdwDTO;

public interface SpdwBiz {
	
	/**
	 * 单位列表 
	 */
	@SuppressWarnings("unchecked")
	public List findAllSpdw();
	
	/**
	 * 保存/修改单位
	 */
	public void saveOrUpdateSpdw(SpdwDTO dto);
	
	/**
	 * 删除单位
	 */
	public void deleteSpdw(Integer dwid);




}
