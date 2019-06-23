package com.cndym.jms.consumer;

import com.cndym.control.ControlCenter;
import com.cndym.control.ThreadFactory;
import com.cndym.utils.ConfigUtils;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class GateWayOrderMessageListener implements MessageListener {

	private Logger logger = Logger.getLogger(getClass());

	public final String ORDER_ID = "ORDER_ID";// 批次ID
	public final String LOTTERY_CLASS_CODE = "LOTTERY_CLASS_CODE";
	public final String SID = "SID";
	public final String ORDER_AMOUNT = "ORDER_AMOUNT";

	@Override
	public void onMessage(Message message) {
		long time = new Date().getTime();

		try {
			if (message instanceof MapMessage) {
				MapMessage oMsg = (MapMessage) message;
				if (oMsg instanceof ActiveMQMapMessage) {
					ActiveMQMapMessage aMsg = (ActiveMQMapMessage) oMsg;
					try {
						final Map<String, String> para = new HashMap<String, String>();
						String threadCon = ConfigUtils.getValue("THREAD_CONTROL");
						String lotteyCode = aMsg.getString(LOTTERY_CLASS_CODE);
						para.put("orderId", aMsg.getString(ORDER_ID));
						para.put("lotteryCode", lotteyCode);
						para.put("sid", aMsg.getString(SID));

						if (threadCon.indexOf(lotteyCode) > -1) {
							ThreadFactory.sendTicketThreadPool.execute(new Runnable() {
								@Override
								public void run() {
									try {
										ControlCenter.executeFormJms(para);
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							});
						} else {
							ThreadFactory.sendTicketThreadPool1.execute(new Runnable() {
								@Override
								public void run() {
									try {
										ControlCenter.executeFormJms(para);
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							});
						}
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("Message:[" + message + "] is not a instance of IssueInfo.");
						throw new JMSException("Message:[" + message + "] is not a instance of IssueInfo.");
					}
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
		logger.info("收单MQ执行时间(" + (new Date().getTime() - time) + ")毫秒");
	}
}
