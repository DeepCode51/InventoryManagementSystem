package com.cxstock.biz.power.imp;

import java.util.ArrayList;
import java.util.List;

import com.cxstock.biz.power.RoleBiz;
import com.cxstock.biz.power.dto.RoleDTO;
import com.cxstock.dao.BaseDAO;
import com.cxstock.pojo.Menu;
import com.cxstock.pojo.Role;
import com.cxstock.pojo.Rolemenu;
import com.cxstock.pojo.RolemenuId;
import com.cxstock.utils.pubutil.ComboData;
import com.cxstock.utils.pubutil.Page;
import com.cxstock.utils.pubutil.TreeNodeChecked;

@SuppressWarnings("unchecked")
public class RoleBizImpl implements RoleBiz {
	
	private BaseDAO baseDao;
	public void setBaseDao(BaseDAO baseDao) {
		this.baseDao = baseDao;
	}
	/*
	 * 分页查询角色列表
	 * @see com.cxstock.biz.power.RoleBiz#findPageRole(com.cxstock.utils.system.Page)
	 */
	public void findPageRole(Page page) {
		String hql = "from Role order by roleid";
		List list = baseDao.findByHql(hql, page.getStart(), page.getLimit());
		List dtoList = RoleDTO.createDtos(list);
		int total = baseDao.countAll("Role");
		page.setRoot(dtoList);
		page.setTotal(total);
	}
	
	/*
	 * 保存/修改角色
	 * @see com.cxstock.biz.power.RoleBiz#saveOrUpdateRole(com.cxstock.biz.power.dto.RoleDTO)
	 */
	public void saveOrUpdateRole(RoleDTO dto) {
		Role role = new Role();
		if(dto.getRoleid()!=null){
			role = (Role)baseDao.loadById(Role.class, dto.getRoleid());
		}
		role.setRolename(dto.getRolename());
		role.setBz(dto.getBz());
		baseDao.saveOrUpdate(role);
	}
	
	/*
	 * 删除角色
	 * @see com.cxstock.biz.power.RoleBiz#deleteRole(java.lang.Integer)
	 */
	public boolean deleteRole(Integer roleid) {
		String hql = "select count(userid) from Users where role.roleid ="+roleid;
		int count = baseDao.countQuery(hql);
		if(count>0){
			return false;
		}else{
			baseDao.deleteById(Role.class, roleid);
			return true;
		}
	}
	
	/*
	 * 角色下拉数据
	 * @see com.cxstock.biz.power.RoleBiz#deleteRole(java.lang.Integer)
	 */
	public List findRoleType() {
		List list = new ArrayList();
		List<Role> roleList = baseDao.listAll("Role");
		for (Role role : roleList) {
			ComboData dto = new ComboData();
			dto.setValue(role.getRoleid().toString());
			dto.setText(role.getRolename());
			list.add(dto);
		}
		return list;
	}
	
	/*
	 * 查询角色菜单
	 * @see com.cxstock.biz.power.RoleBiz#findRoleMenu()
	 */
	public List findRoleMenu(Integer roleid) {
		String hql = "from Menu order by ordernum";
		List menuList = baseDao.findByHql(hql);
		hql = "select t.menu from Rolemenu as t where t.role.roleid="+roleid;
		List roleMenuList = baseDao.findByHql(hql);
		List treeNodeList = this.getTreeNode(0,menuList,roleMenuList);
		return treeNodeList;
	}
	
	//功能树
	private List getTreeNode(Integer menuid,List listFunc,List listRoleFunc){
		List resultList = new ArrayList();
		//当前级菜单集合
		List list = this.getChildrens(listFunc, menuid);
		for (Object obj : list) {
			Menu menu = (Menu) obj;
			TreeNodeChecked treeNodeChecked = new TreeNodeChecked();
			treeNodeChecked.setText(menu.getMenuname());
			treeNodeChecked.setId(menu.getMenuid().toString());
			treeNodeChecked.setIconCls(menu.getIcon()==null?"":menu.getIcon());
			if(listRoleFunc.contains(menu)){
				treeNodeChecked.setChecked(true);
			}else{
				treeNodeChecked.setChecked(false);
			}
			treeNodeChecked.setChildren(getTreeNode(menu.getMenuid(),listFunc,listRoleFunc));//递归
			resultList.add(treeNodeChecked);
		}
		return resultList;
	}
	//子集合
	private List getChildrens(List funcs, Integer menuid) {
		List resultList = new ArrayList();
		Menu func = null;
		for (Object obj : funcs) {
			func = (Menu) obj;
			if (func.getPid().equals(menuid)) {//父节点id
				resultList.add(func);
			}
		}
		return resultList;
	}
	
	/*
	 * 保存角色权限
	 * @see com.cxstock.biz.power.RoleBiz#saveRoleMenu(java.lang.Integer, java.lang.String)
	 */
	public void saveRoleMenu(Integer roleid, String menuids) {
		String hql = "select t.menu from Rolemenu as t where t.role.roleid="+roleid;
		//数据库角色权限
		List<Menu> rmList= baseDao.findByHql(hql);
		
		//页面勾选权限id
		String [] mids = menuids.split(",");
		
		//页面存在数据库不存在就添加
		if(mids.length>0){				
			for (String menuid : mids) {
				if(this.isContain(rmList,menuid)){
					Rolemenu rolemenu = new Rolemenu();
					rolemenu.setId(new RolemenuId(roleid,Integer.valueOf(menuid)));
					baseDao.saveOrUpdate(rolemenu);
				}
			} 
		}
		
		//数据库存在而页面不存在就删除
		for (Menu menu : rmList) {
			String menuid = menu.getMenuid().toString();				
			if(this.isContain(mids,menuid)){
				baseDao.deleteById(Rolemenu.class, new RolemenuId(roleid,menu.getMenuid()));
			}
		}
	}
	
	private boolean isContain(String [] mids,String menuid){
		for (String mid : mids) {
			if(menuid.equals(mid))
				return false;
		}
		return true;
	}
	
	private boolean isContain(List<Menu> menuList,String menuid){
		for (Menu menu : menuList) {
			if(menuid.equals(menu.getMenuid().toString()))
				return false;
		}
		return true;
	}

}
