package com.cxstock.biz.kucun;

import java.util.List;

import com.cxstock.pojo.Bsd;
import com.cxstock.pojo.Byd;

public interface BsyBiz {
	
	/**
	 * 生成单据编号
	 * @param tab	要查询的对象
	 * @param ymd	年-月-日
	 * @return	单据编号
	 */
	public String getDjCode(String tab, String ymd);

	/**
	 * 保存/修改进货单
	 * @param pojo		报损单
	 * @param jhdsps	报损单商品
	 */
	public void saveOrUpdateBsd(Bsd pojo, String bsdsps);

	/**
	 * 保存/修改退货单
	 * @param pojo		报溢单
	 * @param jhdsps	报溢单商品
	 */
	public void saveOrUpdateByd(Byd pojo, String bydsps);

	/**
	 * 按条件查询单据
	 * @param tab		要查询的对象
	 * @param string	查询的条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List findDjByParams(String tab, String wheres);


}
