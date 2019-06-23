package com.cndym.dao;

import com.cndym.bean.user.SystemMessage;
import com.cndym.utils.hibernate.PageBean;

/**
 * User: 梅传颂
 * Date: 11-6-19
 * Time: 下午6:47
 */
public interface ISystemMessageDao extends IGenericDao<SystemMessage> {
    public PageBean getSystemMessageList(SystemMessage systemMessage, String startTime, String endTime, Integer page, Integer pageSize);
}
