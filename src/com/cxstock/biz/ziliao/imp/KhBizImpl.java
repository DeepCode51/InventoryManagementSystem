package com.cxstock.biz.ziliao.imp;

import java.util.ArrayList;
import java.util.List;

import com.cxstock.biz.ziliao.KhBiz;
import com.cxstock.biz.ziliao.dto.KhDTO;
import com.cxstock.dao.BaseDAO;
import com.cxstock.pojo.Kh;
import com.cxstock.utils.pubutil.ComboData;
import com.cxstock.utils.pubutil.Page;

public class KhBizImpl implements KhBiz {
	
	private BaseDAO baseDao;
	public void setBaseDao(BaseDAO baseDao) {
		this.baseDao = baseDao;
	}
	
	/*
	 * 分页查询客户列表
	 */
	@SuppressWarnings("unchecked")
	public void findPageKh(Page page) {
		List list = baseDao.listAll("Kh", page.getStart(), page.getLimit());
		List dtoList = KhDTO.createDtos(list);
		int total = baseDao.countAll("Kh");
		page.setRoot(dtoList);
		page.setTotal(total);
	}
	/*
	 * 保存/修改客户
	 */
	public void saveOrUpdateKh(KhDTO dto) {
		Kh kh = new Kh();
		if(dto.getKhid()!=null){
			kh = (Kh)baseDao.loadById(Kh.class, dto.getKhid());
		}
		kh.setKhname(dto.getKhname());
		kh.setLxren(dto.getLxren());
		kh.setLxtel(dto.getLxtel());
		kh.setAddress(dto.getAddress());
		kh.setBz(dto.getBz());
		baseDao.saveOrUpdate(kh);
	}
	
	/*
	 * 删除客户
	 */
	public void deleteKh(Integer khid) {
		baseDao.deleteById(Kh.class, khid);	
	}
	
	/*
	 * 客户下拉列表
	 */
	@SuppressWarnings("unchecked")
	public List findKhComb() {
		List<ComboData> list = new ArrayList<ComboData>();
		List<Kh> gysList = baseDao.listAll("Kh");
		for (Kh kh : gysList) {
			ComboData comb = new ComboData();
			comb.setValue(kh.getKhid().toString());
			comb.setText(kh.getKhname());
			list.add(comb);
		}
		return list;
	}

}
