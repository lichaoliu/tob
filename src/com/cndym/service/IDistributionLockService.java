package com.cndym.service;

import com.cndym.bean.sys.DistributionLock;

import java.util.List;

/**
 * User: 邓玉明
 * Date: 11-3-27 下午11:00
 */
public interface IDistributionLockService extends IGenericeService<DistributionLock> {
    public boolean doStartLock(DistributionLock distributionLock);

    public boolean doEndLock(DistributionLock distributionLock);

    public List<DistributionLock> getDistributionLockList(DistributionLock distributionLock);

    public int updateStatus(String name);

    public int forInstance();
    
    public int updateStatus(String name, int status);
    
    public int updateStop(String name, int stop);
}

