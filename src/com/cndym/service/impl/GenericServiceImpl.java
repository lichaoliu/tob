package com.cndym.service.impl;

import com.cndym.dao.IGenericDao;
import com.cndym.service.IGenericeService;
import com.cndym.utils.hibernate.HibernatePara;
import com.cndym.utils.hibernate.PageBean;

import java.io.Serializable;
import java.util.List;

public class GenericServiceImpl<T extends Object, E extends IGenericDao<T>> implements IGenericeService<T> {
	protected E daoImpl;

	public E getDaoImpl() {
		return daoImpl;
	}

	public void setDaoImpl(E daoImpl) {
		this.daoImpl = daoImpl;
	}

	public boolean delete(T t) {
		return daoImpl.delete(t);
	}

	public List<T> find(String select, Object[] paramValues) {
		return daoImpl.find(select, paramValues);
	}

	public T findById(Class<T> c, Serializable id) {
		return daoImpl.findById(c, id);
	}

	public boolean save(T t) {
		return daoImpl.save(t);
	}

	public boolean update(T t) {
		return daoImpl.update(t);
	}

    @Override
    public boolean merge(T t) {
        return daoImpl.merge(t);
    }

    public PageBean dynamicQuery(T t, int pageId, int pageSize) {
		return daoImpl.dynamicQuery(t, pageId, pageSize);
	}

	public boolean saveAllObject(List list) {
		return daoImpl.saveAllObject(list);
	}

	public List findSqlList(String select) {
		return daoImpl.findSqlList(select);
	}

	public PageBean getPageBeanByPara(final String select, final List<HibernatePara> para, int pageId, final int pageSize) {
		return daoImpl.getPageBeanByPara(select, para, pageId, pageSize);
	}

    @Override
    public void updateColumn(T t) {
        daoImpl.updateColumn(t);
    }
}
