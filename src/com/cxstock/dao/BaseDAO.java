package com.cxstock.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Collection;
import java.util.List;

/** 统一数据访问接口 */
@SuppressWarnings("unchecked")
public interface BaseDAO {
	/** 保存指定的持久化对象 */
	public void save(Object obj);
	
	/** 保存或更新指定的持久化对象 */
	public void saveOrUpdate(Object obj);
	
	/** 删除指定ID的持久化对象 */
	public void deleteById(Class clazz, Serializable id);
	
	/** 删除指定ID的持久化对象 */
	public void delete(Object obj);
	
	/** 加载指定ID的持久化对象 */
	public Object loadById(Class clazz, Serializable id);
	
	/**加载满足条件的持久化对象*/
	public Object loadObject(String hql);
	
	/** 查询指定类的满足条件的持久化对象 */
	public List findByHql(String hql);
	
	/** 装载指定类的查询结果 */
	public List findInProperty(String clazz, String propertyName, String value);
	
	/** 装载指定类的查询结果 */
	public List findLikeProperty(String clazz, String propertyName, String value);
	
	/** 装载指定类的查询结果 */
	public List findByProperty(String clazz, String propertyName, Object value);
	
	/** 装载指定类的查询结果 */
	public List findByProperty(String clazz, String[] propertyName, Object[] value);
	
	/** 装载指定类的所有持久化对象 */
	public List listAll(String clazz);
	
	/** 条件更新数据 */
	public int update(String hql);
	
	/** 统计指定类的所有持久化对象 */
	public int countAll(String clazz);

	/** 统计指定类的查询结果 */
	public int countQuery(String hql);
	
	/** 分页装载指定类的查询结果 */
	public List findInProperty(String clazz, String propertyName, String value, int start, int limit);
	
	/** 分页装载指定类的查询结果 */
	public List findLikeProperty(String clazz, String propertyName, String value, int start, int limit);
	
	/** 分页装载指定类的查询结果 */
	public List findByProperty(String clazz, String propertyName, String value, int start, int limit);
	
	/** 分页装载指定类的查询结果 */
	public List findByProperty(String clazz, String[] propertyName, Object[] value, int start, int limit);

	/** 分页装载指定类的所有持久化对象 */
	public List listAll(String clazz, int start, int limit);

	/** 分页查询指定类的满足条件的持久化对象 */
	public List findByHql(String hql, int start, int limit);

	/** 从连接池中取得一个JDBC连接 */
	public Connection getConnection();
	
	/** 批量保存、修改	 */
	public void saveOrUpdateAll(Collection collection);
	
	/** 调用存储过程 */
	public void callProcedure(String call);
}

