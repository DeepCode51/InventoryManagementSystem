package com.cxstock.biz.jinhuo.imp;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cxstock.biz.jinhuo.JhBiz;
import com.cxstock.dao.BaseDAO;
import com.cxstock.pojo.Jhd;
import com.cxstock.pojo.Jhdsp;
import com.cxstock.pojo.Spxx;
import com.cxstock.pojo.Thd;
import com.cxstock.pojo.Thdsp;
import com.cxstock.utils.system.Tools;

@SuppressWarnings("unchecked")
public class JhBizImpl implements JhBiz {
	
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
	 *  保存/修改进货单
	 */
	public void saveOrUpdateJhd(Jhd pojo, String jhdsps) {
		baseDao.saveOrUpdate(pojo);
		JSONArray jsonArray = new JSONArray();
		jsonArray = JSONArray.fromObject(jhdsps);
		List spList = new ArrayList();
		List spxxList = new ArrayList();
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jo = (JSONObject) jsonArray.get(i);
			//单据商品
			Jhdsp jhdsp = new Jhdsp();
			jhdsp.setJhd(pojo);
			jhdsp.setSpid(jo.getString("spid"));
			jhdsp.setSpname(jo.getString("spname"));
			jhdsp.setSpdw(jo.getString("dw"));
			jhdsp.setSpxinghao(jo.getString("xinghao"));
			jhdsp.setLbid(jo.getInt("lbid"));
			jhdsp.setLbname(jo.getString("lbname"));
			jhdsp.setDj(jo.getDouble("cbj"));
			jhdsp.setSl(jo.getInt("sl"));
			jhdsp.setZj(jo.getDouble("zj"));
			spList.add(jhdsp);
			Spxx spxx = (Spxx)baseDao.loadById(Spxx.class, jhdsp.getSpid());
			Double kczj = spxx.getKczj()+jhdsp.getZj();
			Integer kcsl = spxx.getKcsl()+jhdsp.getSl();
			spxx.setJhprice(kczj/kcsl);
			spxx.setScjj(jhdsp.getDj());
			spxx.setKcsl(kcsl);
			spxx.setKczj(kczj);
			spxx.setState("2");
			spxxList.add(spxx);
		}
		baseDao.saveOrUpdateAll(spList);
		baseDao.saveOrUpdateAll(spxxList);
		
	}

	/*
	 * 保存/修改退货单
	 */
	public void saveOrUpdateThd(Thd pojo, String thdsps) {
		baseDao.saveOrUpdate(pojo);
		JSONArray jsonArray = new JSONArray();
		jsonArray = JSONArray.fromObject(thdsps);
		List spList = new ArrayList();
		List spxxList = new ArrayList();
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jo = (JSONObject) jsonArray.get(i);
			Thdsp thdsp = new Thdsp();
			thdsp.setThd(pojo);
			thdsp.setSpid(jo.getString("spid"));
			thdsp.setSpname(jo.getString("spname"));
			thdsp.setSpdw(jo.getString("dw"));
			thdsp.setSpxinghao(jo.getString("xinghao"));
			thdsp.setLbid(jo.getInt("lbid"));
			thdsp.setLbname(jo.getString("lbname"));
			thdsp.setDj(jo.getDouble("cbj"));
			thdsp.setSl(jo.getInt("sl"));
			thdsp.setZj(jo.getDouble("zj"));
			spList.add(thdsp);
			Spxx spxx = (Spxx)baseDao.loadById(Spxx.class, thdsp.getSpid());
			spxx.setKcsl(spxx.getKcsl()-thdsp.getSl());
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

	public void deleteJhd(String djid) {
		baseDao.update("delete Jhdsp where djid='"+djid+"'");
		baseDao.deleteById(Jhd.class, djid);
	}

	public void deleteThd(String djid) {
		baseDao.update("delete Thdsp where djid='"+djid+"'");
		baseDao.deleteById(Thd.class, djid);
	}
	


}
