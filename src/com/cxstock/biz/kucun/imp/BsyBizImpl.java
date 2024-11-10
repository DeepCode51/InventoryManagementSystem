package com.cxstock.biz.kucun.imp;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cxstock.biz.kucun.BsyBiz;
import com.cxstock.dao.BaseDAO;
import com.cxstock.pojo.Bsd;
import com.cxstock.pojo.Bsdsp;
import com.cxstock.pojo.Byd;
import com.cxstock.pojo.Bydsp;
import com.cxstock.pojo.Spxx;
import com.cxstock.utils.system.Tools;

@SuppressWarnings("unchecked")
public class BsyBizImpl implements BsyBiz {
	
	private BaseDAO baseDao;
	public void setBaseDao(BaseDAO baseDao) {
		this.baseDao = baseDao;
	}
	
	/*
	 * 生成单据编号
	 */
	public String getDjCode(String tab, String ymd) {
		String code = ymd.replaceAll("-", "");
		String hql = "select max(t.djid) from "+tab+" as t where t.riqi between '"+ymd+" 00:00:00' and '"+ymd+" 23:59:59'";
		List list = baseDao.findByHql(hql);
		Object obj = list.get(0);
		if(obj!=null)
			return code+Tools.formatCode(obj.toString());
		return code+"0001";
	}
	
	/*
	 *  保存/修改报损单
	 */
	public void saveOrUpdateBsd(Bsd pojo, String bsdsps) {
		baseDao.saveOrUpdate(pojo);
		JSONArray jsonArray = new JSONArray();
		jsonArray = JSONArray.fromObject(bsdsps);
		List spList = new ArrayList();
		List spxxList = new ArrayList();
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jo = (JSONObject) jsonArray.get(i);
			Bsdsp bsdsp = new Bsdsp();
			bsdsp.setDjid(pojo.getDjid());
			bsdsp.setSpid(jo.getString("spid"));
			bsdsp.setSpname(jo.getString("spname"));
			bsdsp.setSpdw(jo.getString("dw"));
			bsdsp.setSpxinghao(jo.getString("xinghao"));
			bsdsp.setDj(jo.getDouble("cbj"));
			bsdsp.setSl(jo.getInt("sl"));
			bsdsp.setZj(jo.getDouble("zj"));
			spList.add(bsdsp);
			Spxx spxx = (Spxx)baseDao.loadById(Spxx.class, bsdsp.getSpid());
			spxx.setKcsl(spxx.getKcsl()-bsdsp.getSl());
			spxx.setKczj(spxx.getJhprice()*spxx.getKcsl());
			spxx.setState("2");
			spxxList.add(spxx);
		}
		baseDao.saveOrUpdateAll(spList);
		baseDao.saveOrUpdateAll(spxxList);
	}

	/*
	 * 保存/修改报溢单
	 */
	public void saveOrUpdateByd(Byd pojo, String bydsps) {
		baseDao.saveOrUpdate(pojo);
		JSONArray jsonArray = new JSONArray();
		jsonArray = JSONArray.fromObject(bydsps);
		List spList = new ArrayList();
		List spxxList = new ArrayList();
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jo = (JSONObject) jsonArray.get(i);
			Bydsp bydsp = new Bydsp();
			bydsp.setDjid(pojo.getDjid());
			bydsp.setSpid(jo.getString("spid"));
			bydsp.setSpname(jo.getString("spname"));
			bydsp.setSpdw(jo.getString("dw"));
			bydsp.setSpxinghao(jo.getString("xinghao"));
			bydsp.setDj(jo.getDouble("cbj"));
			bydsp.setSl(jo.getInt("sl"));
			bydsp.setZj(jo.getDouble("zj"));
			spList.add(bydsp);
			Spxx spxx = (Spxx)baseDao.loadById(Spxx.class, bydsp.getSpid());
			spxx.setKcsl(spxx.getKcsl()+bydsp.getSl());
			spxx.setKczj(spxx.getJhprice()*spxx.getKcsl());
			spxx.setState("2");
			spxxList.add(spxx);
		}
		baseDao.saveOrUpdateAll(spList);
		baseDao.saveOrUpdateAll(spxxList);
	}

	/*
	 * 按条件查询单据
	 */
	public List findDjByParams(String tab, String wheres) {
		StringBuffer hql = new StringBuffer("from ");
		hql.append(tab);
		hql.append(" as t");
		hql.append(wheres);
		return baseDao.findByHql(hql.toString());
	}
	


}
