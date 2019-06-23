package com.cndym.service.impl;

import com.cndym.bean.user.ManagesLog;
import com.cndym.constant.Constants;
import com.cndym.dao.IManagesLogDao;
import com.cndym.service.IManagesLogService;
import com.cndym.utils.hibernate.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * User: 邓玉明
 * Date: 11-3-27 下午11:02
 */
@Service
public class ManagesLogServiceImpl extends GenericServiceImpl<ManagesLog, IManagesLogDao> implements IManagesLogService {
    @Autowired
    private IManagesLogDao managesLogDao;


    @Override
    public boolean save(ManagesLog managesLog) {
        return managesLogDao.save(managesLog);
    }

    @Override
    public PageBean findManagesLogLogList(ManagesLog managesLog, Date startTime, Date endTime, Integer page, Integer pageSize) {
        return managesLogDao.findManagesLogList(managesLog, startTime, endTime, page, pageSize);
    }

    @Override
    public PageBean findCooperationLogLogList(ManagesLog managesLog, Date startTime, Date endTime, Integer page, Integer pageSize) {
        return null;
    }

    @Override
    public int updateManagesLog(ManagesLog managesLog) {
        return managesLogDao.updateManagesLog(managesLog);
    }

    @Override
    public ManagesLog getManagesLogById(String managesLogId) {
        return managesLogDao.getManagesLogDaoById(managesLogId);
    }

    @Override
    public int addManagesLog(String des, Integer type) {
        try {
            ManagesLog managesLog = new ManagesLog();
            managesLog.setCreateTime(new Date());
            managesLog.setIp("localhost");
            managesLog.setType("create");
            managesLog.setAdminName("system");
            managesLog.setDescription(des);
            managesLog.setOperatingType(type);
            managesLogDao.save(managesLog);
        } catch (Exception e) {

        }
        return 0;
    }
}
