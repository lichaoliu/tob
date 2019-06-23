package com.cndym.jms.action;

import org.apache.activemq.command.ActiveMQMapMessage;

/**
 * 作者：邓玉明
 * 时间：11-6-26 上午11:23
 * QQ：757579248
 * email：cndym@163.com
 */
public interface IDataOutAction {
    public void execute(ActiveMQMapMessage mapMessage);
}
