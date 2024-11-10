package com.cxstock.biz.ziliao.imp;

import java.util.ArrayList;
import java.util.List;

import com.cxstock.biz.ziliao.SplbBiz;
import com.cxstock.biz.ziliao.dto.SplbDTO;
import com.cxstock.dao.BaseDAO;
import com.cxstock.pojo.Splb;
import com.cxstock.utils.pubutil.TreeNode;

@SuppressWarnings("unchecked")
public class SplbBizImpl implements SplbBiz {
	
	private BaseDAO baseDao;
	public void setBaseDao(BaseDAO baseDao) {
		this.baseDao = baseDao;
	}
	
	/*
	 * 保存/修改商品类别
	 */
	public Integer saveOrUpdateSplb(SplbDTO dto) {
		Splb splb = new Splb();
		if(dto.getLbid()!=null){
			splb = (Splb)baseDao.loadById(Splb.class, dto.getLbid());
		}else{
			splb.setPid(dto.getPid());
		}
		splb.setLbname(dto.getLbname());
		baseDao.saveOrUpdate(splb);
		return splb.getLbid();
	}
	
	/*
	 * 删除商品类别
	 */
	public boolean deleteSplb(Integer lbid) {
		int count = baseDao.countQuery("select count(*) from Spxx where lbid = "+lbid);
		if(count>0){
			return false;
		}
		baseDao.deleteById(Splb.class, lbid);
		return true;
	}
	
	/*
	 * 商品类别树
	 */
	public List findSplbTree() {
		List list = baseDao.listAll("Splb");
		return this.getTree(0, list);
	}
	
	/**  
	 * 通过递归生成tree结构  
	 * @param List childrenlist 商品类别集合
	 * @param Integer id 节点（父节点id）
	 */
	private List getTree(Integer id,List childrenlist) {
		List resultlist = new ArrayList();

		//当前级菜单集合
		List list = this.getChildrens(childrenlist, id);
		for (Object obj : list) {
			Splb splb = (Splb) obj;

			TreeNode treeNode = new TreeNode();
			treeNode.setId(splb.getLbid().toString());
			treeNode.setText(splb.getLbname());
			treeNode.setIconCls("menu-folder");
			//子菜单
			List children = this.getChildrens(childrenlist, splb.getLbid());
			if (children.size() > 0) {//有子类别集合
				treeNode.setLeaf(false);
				treeNode.setChildren(getTree(splb.getLbid(),childrenlist)); //递归调   
			} else {//该节点为叶子    
				treeNode.setLeaf(true);
			}
			resultlist.add(treeNode);
		}
		return resultlist;
	}

	/**
	 * 从funcs集合中找出父节点id为pid的类别集合
	 * @param List menus 类别集合
	 * @param Integer pid 父节点id
	 * return List
	 */
	private List getChildrens(List splbs, Integer pid) {
		List resultList = new ArrayList();
		Splb splb = null;
		for (Object obj : splbs) {
			splb = (Splb) obj;
			if (splb.getPid()!=null&&splb.getPid().equals(pid)) {//父节点id
				resultList.add(splb);
			}
		}
		return resultList;
	}

}
