package com.cndym.service.impl;

import com.cndym.bean.sys.DistributionLock;
import com.cndym.dao.IDistributionLockDao;
import com.cndym.service.IDistributionLockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * User: 邓玉明
 * Date: 11-3-27 下午11:02
 */
@Service
public class DistributionLockServiceImpl extends GenericServiceImpl<DistributionLock, IDistributionLockDao> implements IDistributionLockService {
    @Autowired
    private IDistributionLockDao distributionLockDao;

    @PostConstruct
    public void initDao() {
        super.setDaoImpl(distributionLockDao);
    }


    @Override
    public boolean doStartLock(DistributionLock distributionLock) {
        return distributionLockDao.startLock(distributionLock) == 1 ? true : false;
    }

    @Override
    public boolean doEndLock(DistributionLock distributionLock) {
        return distributionLockDao.endLock(distributionLock) == 1 ? true : false;
    }

    @Override
    public List<DistributionLock> getDistributionLockList(DistributionLock distributionLock) {
        return distributionLockDao.getDistributionLockList(distributionLock);
    }

    public IDistributionLockDao getDistributionLockDao() {
        return distributionLockDao;
    }

    public void setDistributionLockDao(IDistributionLockDao distributionLockDao) {
        this.distributionLockDao = distributionLockDao;
    }

    public int updateStatus(String name) {
        return distributionLockDao.updateStatus(name);
    }

    public int updateStatus(String name, int status) {
        return distributionLockDao.updateStatus(name, status);
    }
    
    @Override
	public int updateStop(String name, int stop) {
		return  distributionLockDao.updateStop(name, stop);
	}

	@Override
    public int forInstance() {
        return distributionLockDao.forInstance();
    }
}
