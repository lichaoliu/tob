package com.cndym.jms.producer;


import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.*;


@Component
public class GateWayOrderMessageProducer {

    private Logger logger = Logger.getLogger(getClass());


    @Resource(name = "gateway_to_control_order")
    private Destination destination;

    @Resource
    private JmsTemplate jmsTemplate;

    public void sendMessage(final String lotteryClassCode, final String orderId, final String sid) {

        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage message = session.createMapMessage();
                message.setString("LOTTERY_CLASS_CODE", lotteryClassCode);
                message.setString("ORDER_ID", orderId);
                message.setString("SID", sid);
                return message;
            }
        });
    }


    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }
}
