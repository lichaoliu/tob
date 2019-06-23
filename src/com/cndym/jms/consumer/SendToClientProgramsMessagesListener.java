package com.cndym.jms.consumer;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * 作者：邓玉明
 * 时间：11-7-17 下午7:31
 * QQ：757579248
 * email：cndym@163.com
 * <p/>
 * <p/>
 * send.to.client.programs
 */
@Component
public class SendToClientProgramsMessagesListener implements MessageListener {
    private Logger logger = Logger.getLogger(getClass());

    @Override
    public void onMessage(Message message) {
        logger.info("Receive JMS manages: " + message);
        try {
            if (message instanceof MapMessage) {
                MapMessage oMsg = (MapMessage) message;
                ActiveMQMapMessage aMsg;
                if (oMsg instanceof ActiveMQMapMessage) {
                    aMsg = (ActiveMQMapMessage) oMsg;
                } else {
                    logger.error("Message:[" + message + "] is not a instance of ActiveMQObjectMessage[IssueInfo].");
                    throw new JMSException("Message:[" + message + "] is not a instance of ActiveMQObjectMessage[IssueInfo].");
                }
            } else {
                logger.error("Message:[" + message + "] is not a instance of ObjectMessage.");
                throw new JMSException("Message:[" + message + "] is not a instance of ObjectMessage.");
            }

        } catch (Exception e) {
            logger.error("处理消息时发生异常.", e);
        }
    }
}
