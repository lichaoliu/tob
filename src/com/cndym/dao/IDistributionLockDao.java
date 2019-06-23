package com.cndym.dao;

import com.cndym.bean.sys.DistributionLock;

import java.util.List;

/**
 * User: 邓玉明
 * Date: 11-3-27 下午10:24
 */
public interface IDistributionLockDao extends IGenericDao<DistributionLock> {
    public int startLock(DistributionLock distributionLock);

    public int endLock(DistributionLock distributionLock);

    public List<DistributionLock> getDistributionLockList(DistributionLock distributionLock);

    public int updateStatus(String name);

    public int forInstance();
    
    public int updateStatus(String name, int status);
    
    public int updateStop(String name, int stop);
}
