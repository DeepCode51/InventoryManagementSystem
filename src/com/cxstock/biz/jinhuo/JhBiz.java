package com.cxstock.biz.jinhuo;

import java.util.List;

import com.cxstock.pojo.Jhd;
import com.cxstock.pojo.Thd;

public interface JhBiz {
	
	/**
	 * 生成进货单据编号
	 * @param tab	要查询的对象
	 * @param ymd	年-月-日
	 * @return	单据编号
	 */
	public String getDjCode(String tab, String ymd);

	/**
	 * 保存/修改进货单
	 * @param pojo		进货单
	 * @param jhdsps	进货单商品
	 */
	public void saveOrUpdateJhd(Jhd pojo, String jhdsps);

	/**
	 * 保存/修改退货单
	 * @param pojo		退货单
	 * @param jhdsps	退货单商品
	 */
	public void saveOrUpdateThd(Thd pojo, String thdsps);

	/**
	 * 按条件查询单据
	 * @param tab		要查询的对象
	 * @param string	查询的条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List findDjByParams(String tab, String wheres);

	/**
	 *  删除进货单
	 */
	public void deleteJhd(String djid);

	/**
	 *  删除退货单
	 */
	public void deleteThd(String djid);


}
