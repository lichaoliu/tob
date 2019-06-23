package com.cndym.service;

import com.cndym.bean.user.ManagesLog;
import com.cndym.utils.hibernate.PageBean;

import java.util.Date;

/**
 * User: 邓玉明
 * Date: 11-3-27 下午11:00
 */
public interface IManagesLogService extends IGenericeService<ManagesLog> {
    public PageBean findManagesLogLogList(ManagesLog accountLog, Date startTime, Date endTime, Integer page, Integer pageSize);

    public PageBean findCooperationLogLogList(ManagesLog managesLog, Date startTime, Date endTime, Integer page, Integer pageSize);

    public int updateManagesLog(ManagesLog accountLog);

    public ManagesLog getManagesLogById(String id);

    public int addManagesLog(String des,Integer type);
}
