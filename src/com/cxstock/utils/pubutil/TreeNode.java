package com.cxstock.utils.pubutil;

import java.util.List;

@SuppressWarnings("unchecked")
public class TreeNode {

	private String id; // 节点id

	private String text; // 节点名称

	private Boolean leaf; // 是否子节点

	private String iconCls; // 图标

	private List children; // 所拥有子节点集合

	public TreeNode() {
		super();
	}

	public TreeNode(String id, String text, Boolean leaf, String iconCls,
			List children) {
		super();
		this.id = id;
		this.text = text;
		this.leaf = leaf;
		this.iconCls = iconCls;
		this.children = children;
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

}