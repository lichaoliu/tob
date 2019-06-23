package com.cndym.jms.consumer;

import com.cndym.jms.action.IDataOutAction;
import com.cndym.utils.SpringUtils;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

@Component
public class GateWayDataOutMessageListener implements MessageListener {

    private Logger logger = Logger.getLogger(getClass());

    public final String ACTION = "action";

    @Override
    public void onMessage(Message message) {
        logger.info("Receive JMS manages: " + message);
        try {
            if (message instanceof MapMessage) {
                MapMessage oMsg = (MapMessage) message;
                if (oMsg instanceof ActiveMQMapMessage) {
                    ActiveMQMapMessage aMsg = (ActiveMQMapMessage) oMsg;
                    try {
                        String action = aMsg.getString(ACTION);
                        logger.info("收到数据外发消息，action=" + action + "");
                        IDataOutAction dataOutAction = (IDataOutAction) SpringUtils.getBean(action + "DataOutAction");
                        dataOutAction.execute(aMsg);
                    } catch (Exception e) {
                        logger.error("", e);
                    }
                } else {
                    logger.error("Message:[" + message + "] is not a instance of ActiveMQMapMessage.");
                }
            } else {
                logger.error("Message:[" + message + "] is not a instance of MapMessage.");
            }
        } catch (Exception e) {
            logger.error("处理消息时发生异常.", e);
        }
    }
}
