package com.cxstock.action.power;


import com.cxstock.action.BaseAction;
import com.cxstock.biz.power.RoleBiz;
import com.cxstock.biz.power.dto.RoleDTO;
import com.cxstock.utils.pubutil.Page;

@SuppressWarnings("serial")
public class RoleAction extends BaseAction  {
	
	private RoleBiz roleBiz;
	
	private Integer roleid;
	private String rolename;
	private String bz;
	private String menuids;

	/**
	 * 分页查询角色
	 */
	public String findPageRole(){
		try {
			Page page = new Page();
			page.setStart(this.getStart());
			page.setLimit(this.getLimit());
			roleBiz.findPageRole(page);
			this.outPageString(page);
		} catch (Exception e) {
			e.printStackTrace();
			this.outError();
		}
		return null;
	}

	/**
	 * 保存/修改角色
	 */
	public String saveOrUpdateRole() {
		try {
			RoleDTO dto = new RoleDTO(roleid,rolename,bz);
			roleBiz.saveOrUpdateRole(dto);
			if(roleid==null){
				this.outString("{success:true,message:'保存成功!'}");
			}else{
				this.outString("{success:true,message:'修改成功!'}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.outError();
		}
		return null;
	}
    
	/**
	 * 删除角色
	 */
	public String deleteRole() {
		try {
			boolean b = roleBiz.deleteRole(roleid);
			if(b){
				this.outString("{success:true}");
			}else{
				this.outString("{success:false,error:'该角色已被使用，不能删除'}");
			}
		} catch (Exception e) {
			 this.outString("{success:false,error:'该角色已被使用，不能删除'}");
		}
		return null;
	}
	
	/**
	 * 角色下拉数据
	 */
	public String findRoleType() {
		try {
			this.outListString(roleBiz.findRoleType());
		} catch (Exception e) {
			 e.printStackTrace();
			 this.outError();
		}
		return null;
	}
	
	/**
	 * 角色权限菜单
	 */
	public String findRoleMenu() {
		try {
			this.outTreeJsonList(roleBiz.findRoleMenu(roleid));
		} catch (Exception e) {
			 e.printStackTrace();
			 this.outError();
		}
		return null;
	}
	
	/**
	 * 保存角色权限
	 */
	public String saveRoleMenu() {
		try {
			roleBiz.saveRoleMenu(roleid,menuids);
			this.outString("保存成功!");
		} catch (Exception e) {
			 e.printStackTrace();
			 this.outError();
		}
		return null;
	}
	
	public void setRoleBiz(RoleBiz roleBiz) {
		this.roleBiz = roleBiz;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public void setMenuids(String menuids) {
		this.menuids = menuids;
	}
}
