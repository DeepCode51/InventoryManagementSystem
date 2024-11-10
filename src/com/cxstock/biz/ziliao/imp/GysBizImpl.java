package com.cxstock.biz.ziliao.imp;

import java.util.ArrayList;
import java.util.List;

import com.cxstock.biz.ziliao.GysBiz;
import com.cxstock.biz.ziliao.dto.GysDTO;
import com.cxstock.dao.BaseDAO;
import com.cxstock.pojo.Gys;
import com.cxstock.utils.pubutil.ComboData;
import com.cxstock.utils.pubutil.Page;

@SuppressWarnings("unchecked")
public class GysBizImpl implements GysBiz {
	
	private BaseDAO baseDao;
	public void setBaseDao(BaseDAO baseDao) {
		this.baseDao = baseDao;
	}
	
	/*
	 * 分页查询供应商列表
	 */
	public void findPageGys(Page page) {
		List list = baseDao.listAll("Gys", page.getStart(), page.getLimit());
		List dtoList = GysDTO.createDtos(list);
		int total = baseDao.countAll("Gys");
		page.setRoot(dtoList);
		page.setTotal(total);
	}
	
	/*
	 * 保存/修改供应商
	 */
	public void saveOrUpdateGys(GysDTO dto) {
		Gys gys = new Gys();
		if(dto.getGysid()!=null){
			gys = (Gys)baseDao.loadById(Gys.class, dto.getGysid());
		}
		gys.setName(dto.getName());
		gys.setLxren(dto.getLxren());
		gys.setLxtel(dto.getLxtel());
		gys.setAddress(dto.getAddress());
		gys.setBz(dto.getBz());
		baseDao.saveOrUpdate(gys);
	}
	
	/*
	 * 删除供应商
	 */
	public void deleteGys(Integer gysid) {
		baseDao.deleteById(Gys.class, gysid);	
	}

	/*
	 *  供应商下拉拉列表
	 */
	public List<ComboData> findGysComb() {
		List<ComboData> list = new ArrayList<ComboData>();
		List<Gys> gysList = baseDao.listAll("Gys");
		for (Gys gys : gysList) {
			ComboData comb = new ComboData();
			comb.setValue(gys.getGysid().toString());
			comb.setText(gys.getName());
			list.add(comb);
		}
		return list;
	}

}
