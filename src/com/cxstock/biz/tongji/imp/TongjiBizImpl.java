package com.cxstock.biz.tongji.imp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cxstock.biz.tongji.TongjiBiz;
import com.cxstock.biz.tongji.dto.DjmxDTO;
import com.cxstock.dao.BaseDAO;
import com.cxstock.pojo.Ckdsp;
import com.cxstock.pojo.Jhd;
import com.cxstock.pojo.Jhdsp;
import com.cxstock.pojo.Thd;
import com.cxstock.pojo.Thdsp;
import com.cxstock.pojo.Tkdsp;

@SuppressWarnings("unchecked")
public class TongjiBizImpl implements TongjiBiz {
	
	private BaseDAO baseDao;
	public void setBaseDao(BaseDAO baseDao) {
		this.baseDao = baseDao;
	}
	
	/*
	 * 供应商统计
	 */
	public List findGysTj(String wheres) {
		List list = new ArrayList();
		String hql = "from Jhd as t where 1=1"+wheres;
		String hql2 = "from Thd as t where 1=1"+wheres;
		List<Jhd> jhdList = baseDao.findByHql(hql.toString());
		for (Jhd jhd : jhdList) {
			list.add(jhd);
		}
		List<Thd> thdList = baseDao.findByHql(hql2.toString());
		for (Thd thd : thdList) {
			list.add(thd);
		}
		return list;
	}

	/*
	 * 客户统计
	 */
	public List findKhTj(String wheres) {
		String hql = "from Ckd as t where 1=1"+wheres;
		String hql2 = "from Tkd as t where 1=1"+wheres;
		List list = baseDao.findByHql(hql.toString());
		List list2 = baseDao.findByHql(hql2.toString());
		for (int i = 0; i < list2.size(); i++) {
			list.add(list2.get(i));
		}
		return list;
	}

	/*
	 * 商品采购统计
	 */
	public List findSpcgTj(String wheres) {
		List list = new ArrayList();
		String hql = "from Jhdsp as t "+wheres;
		String hql2 = "from Thdsp as t "+wheres.replace("jhd", "thd");
		List<Jhdsp> jhdList = baseDao.findByHql(hql.toString());
		for (Jhdsp jhd : jhdList) {
			list.add(DjmxDTO.createDto(jhd));
		}
		List<Thdsp> thdList = baseDao.findByHql(hql2.toString());
		for (Thdsp thd : thdList) {
			list.add(DjmxDTO.createDto(thd));
		}
		return list;
	}

	/*
	 * 商品销售统计
	 */
	public List findSpxstj(String wheres) {
		List list = new ArrayList();
		String hql = "from Ckdsp as t "+wheres;
		String hql2 = "from Tkdsp as t "+wheres.replace("ckd", "tkd");
		List<Ckdsp> ckdList = baseDao.findByHql(hql.toString());
		for (Ckdsp ckd : ckdList) {
			list.add(DjmxDTO.createDto(ckd));
		}
		List<Tkdsp> tkdList = baseDao.findByHql(hql2.toString());
		for (Tkdsp tkd : tkdList) {
			list.add(DjmxDTO.createDto(tkd));
		}
		return list;
	}

	/*
	 * 按日统计分析
	 */
	public String findTjfxRi(String wheres, List<String> dates) {
		String hql = "select DATE_FORMAT(t.riqi,'%Y-%m-%d'),sum(t.sfje),sum(t.cbje) from Ckd as t where t.jystate=1 "+wheres;
		String hql2 = "select DATE_FORMAT(t.riqi,'%Y-%m-%d'),sum(t.sfje),sum(t.cbje) from Tkd as t where t.jystate=1 "+wheres;
		
		List list = baseDao.findByHql(hql.toString());
		List list2 = baseDao.findByHql(hql2.toString());
		
		//组装flash、grid数据
		StringBuffer json = new StringBuffer("{success:'ture', fData:\"<chart caption=' ' rotateYAxisName='1' showValues='1'  decimalPrecision='0' showNames='1'  baseFontSize='12' outCnvBaseFontSiz='20' numberSuffix=''  pieSliceDepth='30' formatNumberScale='0'>");
		StringBuffer gDate = new StringBuffer("\",gData:[");
		double xszjall=0f,cbzjall=0f,ylzjall=0f;
		for (String date : dates) {
			double xszj=0f,cbzj=0f,ylzj=0f;
			for (Iterator it = list.iterator(); it.hasNext();) {
				Object[] obj = (Object[]) it.next();
				if(date.equals(obj[0].toString())){
					xszj += Double.valueOf(obj[1].toString());
					cbzj += Double.valueOf(obj[2].toString());
					it.remove();
				}
			}
			for (Iterator it = list2.iterator(); it.hasNext();) {
				Object[] obj = (Object[]) it.next();
				if(date.equals(obj[0].toString())){
					xszj -= Double.valueOf(obj[1].toString());
					cbzj -= Double.valueOf(obj[2].toString());
					it.remove();
				}
			}
			ylzj = xszj-cbzj;
			xszjall+=xszj;
			cbzjall+=cbzj;
			ylzjall+=ylzj;
			json.append("<set label='");
			json.append(date);
			json.append("' value='");
			json.append(ylzj);
			json.append("'/>");
			gDate.append("{riqi:'");
			gDate.append(date);
			gDate.append("',xszj:'");
			gDate.append(xszj);
			gDate.append("',cbzj:'");
			gDate.append(cbzj);
			gDate.append("',ylzj:'");
			gDate.append(ylzj);
			gDate.append("'},");
		}
		json.append("</chart>");
		json.append(gDate.toString());
		json.append("{riqi:'合计',xszj:'");
		json.append(xszjall);
		json.append("',cbzj:'");
		json.append(cbzjall);
		json.append("',ylzj:'");
		json.append(ylzjall);
		json.append("'}]}");
		return json.toString();
	}

	/*
	 * 按月统计分析
	 */
	public String findTjfxYue(String wheres, List<String> dates) {
		String hql = "select DATE_FORMAT(t.riqi,'%Y-%m'),sum(t.sfje),sum(t.cbje) from Ckd as t where t.jystate=1 "+wheres;
		String hql2 = "select DATE_FORMAT(t.riqi,'%Y-%m'),sum(t.sfje),sum(t.cbje) from Tkd as t where t.jystate=1 "+wheres;
		
		List list = baseDao.findByHql(hql.toString());
		List list2 = baseDao.findByHql(hql2.toString());
		
		//组装flash、grid数据
		StringBuffer json = new StringBuffer("{success:'ture', fData:\"<chart caption=' ' rotateYAxisName='1' showValues='1'  decimalPrecision='0' showNames='1'  baseFontSize='12' outCnvBaseFontSiz='20' numberSuffix=''  pieSliceDepth='30' formatNumberScale='0'>");
		StringBuffer gDate = new StringBuffer("\",gData:[");
		double xszjall=0f,cbzjall=0f,ylzjall=0f;
		for (String date : dates) {
			double xszj=0f,cbzj=0f,ylzj=0f;
			for (Iterator it = list.iterator(); it.hasNext();) {
				Object[] obj = (Object[]) it.next();
				if(date.equals(obj[0].toString())){
					xszj += Double.valueOf(obj[1].toString());
					cbzj += Double.valueOf(obj[2].toString());
					it.remove();
				}
			}
			for (Iterator it = list2.iterator(); it.hasNext();) {
				Object[] obj = (Object[]) it.next();
				if(date.equals(obj[0].toString())){
					xszj -= Double.valueOf(obj[1].toString());
					cbzj -= Double.valueOf(obj[2].toString());
					it.remove();
				}
			}
			ylzj = xszj-cbzj;
			xszjall+=xszj;
			cbzjall+=cbzj;
			ylzjall+=ylzj;
			json.append("<set label='");
			json.append(date);
			json.append("' value='");
			json.append(ylzj);
			json.append("'/>");
			gDate.append("{riqi:'");
			gDate.append(date);
			gDate.append("',xszj:'");
			gDate.append(xszj);
			gDate.append("',cbzj:'");
			gDate.append(cbzj);
			gDate.append("',ylzj:'");
			gDate.append(ylzj);
			gDate.append("'},");
		}
		json.append("</chart>");
		json.append(gDate.toString());
		json.append("{riqi:'合计',xszj:'");
		json.append(xszjall);
		json.append("',cbzj:'");
		json.append(cbzjall);
		json.append("',ylzj:'");
		json.append(ylzjall);
		json.append("'}]}");
		return json.toString();
	}


}
