package com.cndym.jms.producer;

import com.cndym.bean.tms.MainIssue;
import com.cndym.utils.Utils;
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
public class SendToClientIssueMessageProducer {
    @Resource(name = "send_to_client_issue_messages")
    private Destination destination;

    @Resource
    private JmsTemplate jmsTemplate;

    public void sendMessage(final MainIssue mainIssue) {

        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage message = session.createMapMessage();
                StringBuffer xmlBean = new StringBuffer();
                xmlBean.append("<issue lottery=\"").append(mainIssue.getLotteryCode()).append("\" ");
                xmlBean.append("name=\"").append(mainIssue.getName()).append("\" ");
                xmlBean.append("status=\"").append(mainIssue.getStatus().toString()).append("\" ");
                xmlBean.append("startTime=\"").append(Utils.formatDate2Str(mainIssue.getStartTime(), "yyyy-MM-dd HH:mm:ss")).append("\" ");
                xmlBean.append("endTime=\"").append(Utils.formatDate2Str(mainIssue.getEndTime(), "yyyy-MM-dd HH:mm:ss")).append("\"/>");
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
