package com.cxstock.biz.power.imp;

import java.util.List;

import net.sf.json.JSONArray;

import com.cxstock.biz.power.UserBiz;
import com.cxstock.biz.power.dto.UserDTO;
import com.cxstock.biz.power.dto.UserMenuDTO;
import com.cxstock.dao.BaseDAO;
import com.cxstock.pojo.Role;
import com.cxstock.pojo.Users;
import com.cxstock.utils.pubutil.Page;

public class UserBizImpl implements UserBiz {
	
	private BaseDAO baseDao;
	public void setBaseDao(BaseDAO baseDao) {
		this.baseDao = baseDao;
	}
	
	/*
	 * 登录验证
	 * @see com.cxstock.biz.power.UserBiz#login(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public UserDTO login(String code, String pass) {
		String hql="from Users as t where t.logincode='"+code+"' and t.password='"+pass+"'";
		Users user = (Users)baseDao.loadObject(hql);
		if(user!=null){
			UserDTO dto = UserDTO.createDto(user);
			hql = "from Vusermenu as t where t.userid="+user.getUserid();
			List list = baseDao.findByHql(hql);
			JSONArray jsong = JSONArray.fromObject(new UserMenuDTO().getTree(0,list));
			dto.setUsermenu(jsong.toString());
			return dto;
		}
		return null;
	}
	
	/*
	 * 分页查询用户列表
	 * @see com.cxstock.biz.power.UserBiz#findPageUser(com.cxstock.utils.system.Page)
	 */
	@SuppressWarnings("unchecked")
	public void findPageUser(Page page) {
		String hql = "from Users as t left join fetch t.role order by t.userid";
		List list = baseDao.findByHql(hql, page.getStart(), page.getLimit());
		List dtoList = UserDTO.createDtos(list);
		int total = baseDao.countAll("Users");
		page.setRoot(dtoList);
		page.setTotal(total);
	}
	/*
	 * 保存/修改用户
	 * @see com.cxstock.biz.power.UserBiz#saveOrUpdateUser(com.cxstock.biz.power.dto.UserDTO)
	 */
	public boolean saveOrUpdateUser(UserDTO dto) {
		Users user = new Users();
		if(dto.getUserid()!=null){
			user = (Users)baseDao.loadById(Users.class, dto.getUserid());
		}else{
			Users u = (Users)baseDao.loadObject("from Users where logincode='"+dto.getLogincode()+"'");
			if(u!=null){
				return false;
			}
			user.setLogincode(dto.getLogincode());
			user.setState(0);
		}
		user.setPassword(dto.getPassword());
		user.setUsername(dto.getUsername());
		user.setRole(new Role(dto.getRoleid()));
		user.setBz(dto.getBz());
		baseDao.saveOrUpdate(user);
		return true;
	}
	
	/*
	 * 删除用户
	 * @see com.cxstock.biz.power.UserBiz#deleteUser(java.lang.String)
	 */
	public void deleteUser(Integer userid) {
		baseDao.deleteById(Users.class, userid);	
	}

}
