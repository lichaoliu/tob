package com.cndym.dao;

import com.cndym.utils.hibernate.HibernatePara;
import com.cndym.utils.hibernate.PageBean;

import java.io.Serializable;
import java.util.List;

public interface IGenericOperation<T extends Object> {
	public boolean update(T t);

    public boolean merge(T t);

	public boolean delete(T t);

	public boolean save(T t);

	public T findById(Class<T> c, Serializable id);

	public List<T> find(String select, Object paramValues[]);

	public PageBean dynamicQuery(T t, int pageId, int pageSize);

	public boolean saveAllObject(List list);

	public List findSqlList(String select);

	public PageBean getPageBeanByPara(final String select, final List<HibernatePara> para, int pageId, final int pageSize);

    public void updateColumn(T t);
    
}
