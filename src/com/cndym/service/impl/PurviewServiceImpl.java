package com.cndym.service.impl;

import com.cndym.bean.sys.Purview;
import com.cndym.dao.IPurviewDao;
import com.cndym.service.IPurviewService;
import com.cndym.utils.hibernate.PageBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: 邓玉明
 * Date: 11-3-27 下午11:02
 */
@Service
public class PurviewServiceImpl extends GenericServiceImpl<Purview, IPurviewDao> implements IPurviewService {
    @Autowired
    private IPurviewDao purviewDao;

    public IPurviewDao getPurviewDao() {
        return purviewDao;
    }

    public void setPurviewDao(IPurviewDao purviewDao) {
        this.purviewDao = purviewDao;
    }

	@Override
	public Purview getPurview(String adminName, String code) {
		return purviewDao.getPurview(adminName, code);
	}

	@Override
	public PageBean getPurviewList(Purview purview) {
		return purviewDao.getPurviewList(purview);
	}

	@Override
	public PageBean getAdminPurviewUrlList(Purview purview) {
		return purviewDao.getAdminPurviewUrlList(purview);
	}

	@Override
	public boolean deleteByAdminId(String adminId) {
		return purviewDao.deleteByAdminId(adminId);
	}

	@Override
	public boolean deleteByAdminIdAndIndex(String adminId, String codeFather) {
		return purviewDao.deleteByAdminIdAndIndex(adminId, codeFather);
	}
}
