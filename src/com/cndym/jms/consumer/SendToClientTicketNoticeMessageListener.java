package com.cndym.jms.consumer;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.cndym.jms.action.IDataOutAction;
import com.cndym.utils.SpringUtils;

@Component
public class SendToClientTicketNoticeMessageListener implements MessageListener {

	private Logger logger = Logger.getLogger(getClass());

	@Override
	public void onMessage(Message message) {
		try {
			logger.info(message.toString());
			if (message instanceof MapMessage) {
				MapMessage oMsg = (MapMessage) message;
				if (oMsg instanceof ActiveMQMapMessage) {
					ActiveMQMapMessage aMsg = (ActiveMQMapMessage) oMsg;
					try {
                        IDataOutAction dataOutAction = (IDataOutAction) SpringUtils.getBean("ticketNoticeAction");
                        dataOutAction.execute(aMsg);
                    } catch(Exception e) {
						logger.error("", e);
					}
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}
}
