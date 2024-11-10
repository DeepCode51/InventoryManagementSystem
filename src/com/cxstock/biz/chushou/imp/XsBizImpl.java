package com.cxstock.biz.chushou.imp;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cxstock.biz.chushou.XsBiz;
import com.cxstock.dao.BaseDAO;
import com.cxstock.pojo.Ckd;
import com.cxstock.pojo.Ckdsp;
import com.cxstock.pojo.Spxx;
import com.cxstock.pojo.Tkd;
import com.cxstock.pojo.Tkdsp;
import com.cxstock.utils.system.Tools;

@SuppressWarnings("unchecked")
public class XsBizImpl implements XsBiz {
	
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
	 *  保存/修改销售单
	 */
	public void saveOrUpdateCkd(Ckd pojo, String jhdsps) {
		baseDao.saveOrUpdate(pojo);
		JSONArray jsonArray = new JSONArray();
		jsonArray = JSONArray.fromObject(jhdsps);
		List spList = new ArrayList();
		List spxxList = new ArrayList();
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jo = (JSONObject) jsonArray.get(i);
			Ckdsp ckdsp = new Ckdsp();
			ckdsp.setCkd(pojo);
			ckdsp.setSpid(jo.getString("spid"));
			ckdsp.setSpname(jo.getString("spname"));
			ckdsp.setSpdw(jo.getString("dw"));
			ckdsp.setSpxinghao(jo.getString("xinghao"));
			ckdsp.setLbid(jo.getInt("lbid"));
			ckdsp.setLbname(jo.getString("lbname"));
			ckdsp.setDj(jo.getDouble("cbj"));
			ckdsp.setSl(jo.getInt("sl"));
			ckdsp.setZj(jo.getDouble("zj"));
			spList.add(ckdsp);
			Spxx spxx = (Spxx)baseDao.loadById(Spxx.class, ckdsp.getSpid());
			spxx.setKcsl(spxx.getKcsl()-ckdsp.getSl());
			spxx.setKczj(spxx.getJhprice()*spxx.getKcsl());
			spxx.setState("2");
			spxxList.add(spxx);
		}
		baseDao.saveOrUpdateAll(spList);
		baseDao.saveOrUpdateAll(spxxList);
	}

	/*
	 * 保存/修改退货单
	 */
	public void saveOrUpdateTkd(Tkd pojo, String thdsps) {
		baseDao.saveOrUpdate(pojo);
		JSONArray jsonArray = new JSONArray();
		jsonArray = JSONArray.fromObject(thdsps);
		List spList = new ArrayList();
		List spxxList = new ArrayList();
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jo = (JSONObject) jsonArray.get(i);
			Tkdsp tkdsp = new Tkdsp();
			tkdsp.setTkd(pojo);
			tkdsp.setSpid(jo.getString("spid"));
			tkdsp.setSpname(jo.getString("spname"));
			tkdsp.setSpdw(jo.getString("dw"));
			tkdsp.setSpxinghao(jo.getString("xinghao"));
			tkdsp.setLbid(jo.getInt("lbid"));
			tkdsp.setLbname(jo.getString("lbname"));
			tkdsp.setDj(jo.getDouble("cbj"));
			tkdsp.setSl(jo.getInt("sl"));
			tkdsp.setZj(jo.getDouble("zj"));
			spList.add(tkdsp);
			Spxx spxx = (Spxx)baseDao.loadById(Spxx.class, tkdsp.getSpid());
			spxx.setKcsl(spxx.getKcsl()+tkdsp.getSl());
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

	public void deleteCkd(String djid) {
		baseDao.update("delete Ckdsp where djid='"+djid+"'");
		baseDao.deleteById(Ckd.class, djid);
	}

	public void deleteTkd(String djid) {
		baseDao.update("delete Tkdsp where djid='"+djid+"'");
		baseDao.deleteById(Tkd.class, djid);
	}
	


}
