package com.cxstock.biz.chushou;

import java.util.List;

import com.cxstock.pojo.Ckd;
import com.cxstock.pojo.Tkd;

public interface XsBiz {
	
	/**
	 * 生成单据编号
	 * @param tab	要查询的对象
	 * @param ymd	年-月-日
	 * @return	单据编号
	 */
	public String getDjCode(String tab, String ymd);

	/**
	 * 保存/修改销售单
	 * @param pojo		销售单
	 * @param jhdsps	销售单商品
	 */
	public void saveOrUpdateCkd(Ckd pojo, String ckdsps);

	/**
	 * 保存/修改退货单
	 * @param pojo		退货单
	 * @param jhdsps	退货单商品
	 */
	public void saveOrUpdateTkd(Tkd pojo, String thdsps);

	/**
	 * 按条件查询单据
	 * @param tab		要查询的对象
	 * @param string	查询的条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List findDjByParams(String tab, String wheres);

	/**
	 * 删除销售单
	 * @param djid
	 */
	public void deleteCkd(String djid);

	/**
	 * 删除退货单
	 * @param djid
	 */
	public void deleteTkd(String djid);


}
