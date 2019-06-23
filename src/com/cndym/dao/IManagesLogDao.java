package com.cndym.dao;

import com.cndym.bean.user.ManagesLog;
import com.cndym.utils.hibernate.PageBean;

import java.util.Date;

/**
 * User: 邓玉明
 * Date: 11-3-27 下午10:26
 */
public interface IManagesLogDao extends IGenericDao<ManagesLog>{

    public PageBean findManagesLogList(ManagesLog accountLog, Date startTime, Date endTime, Integer page, Integer pageSize);

    public PageBean findCooperationLogLogList(ManagesLog managesLog,Date startTime,Date endTime,Integer page,Integer pageSize);


    public int updateManagesLog(ManagesLog accountLog);

    public ManagesLog getManagesLogDaoById(String managesLogId);


}
