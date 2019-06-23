package com.cndym.service;

import com.cndym.bean.user.SystemMessage;
import com.cndym.utils.hibernate.PageBean;

/**
 * User: 梅传颂
 * Date: 11-6-19
 * Time: 下午10:23
 */
public interface ISystemMessageService extends IGenericeService<SystemMessage> {

    public PageBean getSystemMessageList(SystemMessage systemMessage, String startTime, String endTime, Integer page, Integer pageSize);
}
