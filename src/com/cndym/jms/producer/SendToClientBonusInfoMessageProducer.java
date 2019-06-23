package com.cndym.jms.producer;

import com.cndym.bean.tms.MainIssue;
import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.*;

/**
 * Created by IntelliJ IDEA.
 * User: cndym
 * Date: 11-8-25
 * Time: 上午12:52
 */
@Component
public class SendToClientBonusInfoMessageProducer {
    private Logger logger = Logger.getLogger(getClass());

    @Resource(name = "send_to_client_bonus_info_messages")
    private Destination destination;
    @Resource
    private JmsTemplate jmsTemplate;

    public void sendMessage(final MainIssue mainIssue) {

        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage message = session.createMapMessage();
                StringBuffer xmlBean = new StringBuffer();
                xmlBean.append("<bonusInfo bonusCode=\"");
                xmlBean.append(mainIssue.getBonusNumber());
                xmlBean.append("\" bonusClass=\"");
                xmlBean.append(mainIssue.getBonusClass());
                xmlBean.append("\" globalSaleTotal=\"");
                xmlBean.append(mainIssue.getGlobalSaleTotal());
                xmlBean.append("\" lottery=\"");
                xmlBean.append(mainIssue.getLotteryCode());
                xmlBean.append("\" issue=\"");
                xmlBean.append(mainIssue.getName());
                xmlBean.append("\" prizePool=\"");
                xmlBean.append(mainIssue.getPrizePool());
                xmlBean.append("\"/>");
                message.setString("msg", xmlBean.toString());
                return message;
            }
        });
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
