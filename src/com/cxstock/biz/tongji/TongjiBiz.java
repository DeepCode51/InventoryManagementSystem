package com.cxstock.biz.tongji;

import java.util.List;

@SuppressWarnings("unchecked")
public interface TongjiBiz {
	/**
	 * 供应商统计
	 * @param string 统计条件
	 * @return
	 */
	public List findGysTj( String wheres);

	/**
	 * 客户统计
	 * @param string 统计条件
	 * @return
	 */
	public List findKhTj(String string);

	/**
	 * 商品采购统计
	 * @param string 统计条件
	 * @return
	 */
	public List findSpcgTj(String string);

	/**
	 * 商品销售统计
	 * @param string 统计条件
	 * @return
	 */
	public List findSpxstj(String string);

	/**
	 * 按日统计分析
	 * @param string 统计条件
	 * @param dates 日期集合
	 * @return
	 */
	public String findTjfxRi(String string, List<String> dates);

	/**
	 * 按月统计分析
	 * @param string 统计条件
	 * @param dates 日期集合
	 * @return
	 */
	public String findTjfxYue(String string, List<String> dates);


}
