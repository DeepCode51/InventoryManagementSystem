package com.cxstock.biz.ziliao;

import java.util.List;

import com.cxstock.biz.ziliao.dto.KhDTO;
import com.cxstock.utils.pubutil.Page;

public interface KhBiz {
	
	/**
	 * 分页查询用户列表
	 */
	public void findPageKh(Page page);
	
	/**
	 * 保存/修改用户
	 */
	public void saveOrUpdateKh(KhDTO dto);
	
	/**
	 * 删除用户
	 */
	public void deleteKh(Integer khid);

	/**
	 *  客户下拉列表
	 */
	@SuppressWarnings("unchecked")
	public List findKhComb();


}
