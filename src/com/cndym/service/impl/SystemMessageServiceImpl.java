package com.cndym.service.impl;

import com.cndym.bean.user.SystemMessage;
import com.cndym.dao.ISystemMessageDao;
import com.cndym.service.ISystemMessageService;
import com.cndym.utils.hibernate.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: 梅传颂
 * Date: 11-6-19
 * Time: 下午10:24
 */
@Service
public class SystemMessageServiceImpl extends GenericServiceImpl<SystemMessage, ISystemMessageDao> implements ISystemMessageService {

    @Autowired
    private ISystemMessageDao systemMessageDao;

    public ISystemMessageDao getSystemMessageDao() {
        return systemMessageDao;
    }

    public void setSystemMessageDao(ISystemMessageDao systemMessageDao) {
        this.systemMessageDao = systemMessageDao;
    }


    @Override
    public PageBean getSystemMessageList(SystemMessage systemMessage, String startTime, String endTime, Integer page, Integer pageSize) {
        return systemMessageDao.getSystemMessageList(systemMessage,startTime,endTime,page,pageSize);
    }

    @Override
    public boolean save(SystemMessage systemMessage) {
        return systemMessageDao.save(systemMessage);
    }
}
