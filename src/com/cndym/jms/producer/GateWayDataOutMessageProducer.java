package com.cndym.jms.producer;


import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.*;
import java.util.Map;


@Component
public class GateWayDataOutMessageProducer {

    private Logger logger = Logger.getLogger(getClass());
    private final String ACTION = "action";


    @Resource(name = "gateway_date_out_messages")
    private Destination destination;

    @Resource
    private JmsTemplate jmsTemplate;

    public void sendMessage(final String action, final Map<String, Object> map) {

        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage message = session.createMapMessage();
                message.setString(ACTION, action);
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    message.setObject(entry.getKey(), entry.getValue());
                }
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
