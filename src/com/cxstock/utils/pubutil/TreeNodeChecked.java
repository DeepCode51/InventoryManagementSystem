package com.cxstock.utils.pubutil;

import java.util.List;

@SuppressWarnings("unchecked")
public class TreeNodeChecked {

	private String id; // 节点id

	private String text; // 节点名称

	private Boolean leaf; // 是否子节点

	private String iconCls; // 图标

	private List children; // 所拥有子节点集合

	private Boolean expanded = false; // 是否展开

	private boolean checked; //是否选中

	public TreeNodeChecked() {
		super();
	}

	public TreeNodeChecked(String id, String text, Boolean leaf, String iconCls,
			List children, Boolean expanded, boolean checked) {
		super();
		this.id = id;
		this.text = text;
		this.leaf = leaf;
		this.iconCls = iconCls;
		this.children = children;
		this.expanded = expanded;
		this.checked = checked;
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

	public Boolean getExpanded() {
		return expanded;
	}

	public void setExpanded(Boolean expanded) {
		this.expanded = expanded;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	



}