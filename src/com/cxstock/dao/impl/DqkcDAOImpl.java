package com.cxstock.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cxstock.biz.kucun.dto.DqkcDTO;
import com.cxstock.dao.DqkcDAO;

public class DqkcDAOImpl extends BaseDAOImpl implements DqkcDAO {

	/*
	 * 当前库存查询 
	 */
	@SuppressWarnings("unchecked")
	public List getDqkcByParams(Integer kfid,Integer lbid,String search) {
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		List list = new ArrayList();
		StringBuffer sql = new StringBuffer("select s.spid,s.spname,s.lbname,s.xinghao,s.kcsl,d.xssl,s.scjj,s.jhprice,s.chprice,s.kczj,s.dw,s.csname,s.bz from (select * from spxx where 1=1");
		if(lbid!=null&&!lbid.equals(0)){
			sql.append(" and lbid=");
			sql.append(lbid);
		}
		if(search!=null&&!"".equals(search)){
			sql.append(" and spid like '%");
			sql.append(search);
			sql.append("%' or spname like '%");
			sql.append(search);
			sql.append("%'");
		}
		sql.append(") as s left join (select spid,sum(sl)as xssl from ckdsp group by spid) as d on(s.spid=d.spid)");
		try {			
			conn = getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql.toString());
			while(rs.next()){
				DqkcDTO dto = new DqkcDTO();
				dto.setSpid(rs.getString(1));
				dto.setSpname(rs.getString(2));
				dto.setLbname(rs.getString(3));
				dto.setXinghao(rs.getString(4));
				dto.setKcsl(rs.getInt(5));
				dto.setXsll(rs.getInt(6));
				dto.setScjj(rs.getDouble(7));
				dto.setJhprice(rs.getDouble(8));
				dto.setChprice(rs.getDouble(9));
				dto.setKczj(rs.getDouble(10));
				dto.setDw(rs.getString(11));
				dto.setCsname(rs.getString(12));
				dto.setBz(rs.getString(13));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
			
		} finally {
			try {
			    if(rs != null)
			       rs.close();
			    if(stm != null)
			    	stm.close();
			} catch (SQLException e) {
			    e.printStackTrace();
			    throw new RuntimeException(e.getMessage());	
			}
		}
		return list;
	}

}
