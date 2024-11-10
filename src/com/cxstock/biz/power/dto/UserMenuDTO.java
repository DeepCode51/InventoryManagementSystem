package com.cxstock.biz.power.dto;

import java.util.ArrayList;
import java.util.List;

import com.cxstock.pojo.Vusermenu;

@SuppressWarnings("unchecked")
public class UserMenuDTO {

	// Fields

	private String id; //节点id

	private String text; //节点名称

	private String url; //连接地址

	private Boolean leaf; //是否子节点

	private String iconCls; //图标

	private List children; //所拥有子节点集合

	/**  
	 * 通过递归生成tree结构  
	 * @param List childrenlist 用户菜单集合
	 * @param Integer id 节点（父节点id）
	 */
	public List getTree(Integer id,List childrenlist) {
		List resultlist = new ArrayList();

		//当前级菜单集合
		List list = this.getChildrens(childrenlist, id);
		for (int i = 0; i < list.size(); i++) {
			Vusermenu umenu = (Vusermenu) list.get(i);

			UserMenuDTO userFunc = new UserMenuDTO();
			userFunc.setId(umenu.getMenuid().toString());
			userFunc.setText(umenu.getMenuname());
			userFunc.setUrl(umenu.getMenuurl());
			userFunc.setIconCls(umenu.getIcon());
			//子菜单
			List children = this.getChildrens(childrenlist, umenu.getMenuid());
			if (1==umenu.getMenutype() && children.size() > 0) {//是父节点并且有子菜单集合
				userFunc.setLeaf(false);
				userFunc.setChildren(getTree(umenu.getMenuid(),childrenlist)); //递归调   
			} else {//该节点为叶子    
				userFunc.setLeaf(true);
			}
			resultlist.add(userFunc);
		}
		return resultlist;
	}

	/**
	 * 从menus集合中找出父节点id为pid的菜单集合
	 * @param List menus 菜单集合
	 * @param Integer pid 父节点id
	 * return List
	 */
	public List getChildrens(List menus, Integer pid) {
		List resultList = new ArrayList();
		Vusermenu menu = null;
		for (Object obj : menus) {
			menu = (Vusermenu) obj;
			if (menu.getPid()!=null&&menu.getPid().equals(pid)) {//父节点id
				resultList.add(menu);
			}
		}
		return resultList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public List getChildren() {
		return children;
	}

	public void setChildren(List children) {
		this.children = children;
	}

}
