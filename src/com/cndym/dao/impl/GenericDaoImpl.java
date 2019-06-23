package com.cndym.dao.impl;

import com.cndym.dao.IGenericDao;
import com.cndym.utils.hibernate.HibernateUtil;
import com.cndym.utils.hibernate.PageBean;

import java.io.Serializable;
import java.util.List;


public class GenericDaoImpl<T extends Object> extends HibernateUtil implements IGenericDao<T> {
	public List<T> find(String select, Object[] paramValues) {
		return super.findList(select, paramValues);
	}

	public T findById(Class<T> c, Serializable id) {
		return (T) super.load(c, id);
	}

	public boolean delete(T t) {
		return super.deleteObject(t);
	}

	public PageBean dynamicQuery(T t, int pageId, int pageSize) {
		return super.dynamicQueryObject(t, pageId, pageSize);
	}

	public boolean save(T t) {
		return super.saveObject(t);
	}

	public boolean update(T t) {
		return super.updateObject(t);
	}

	public boolean merge(T t) {
		return super.mergeObject(t);
	}

	public List findSqlList(String select) {
		return super.findSqlList(select);
	}

    @Override
    public void updateColumn(T t) {
    }
}
