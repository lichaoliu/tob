package com.cndym.jms.action;

import com.cndym.bean.tms.MainIssue;
import com.cndym.constant.Constants;
import com.cndym.service.IMainIssueService;
import com.cndym.utils.ConfigUtils;
import com.cndym.utils.HttpClientUtils;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.JMSException;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者：邓玉明
 * 时间：11-6-26 上午11:21
 * QQ：757579248
 * email：cndym@163.com
 */
@Component
public class BonusInfoDataOutAction implements IDataOutAction {
    private Logger logger = Logger.getLogger(getClass());

    @Resource
    private IMainIssueService mainIssueService;

    @Override
    public void execute(ActiveMQMapMessage mapMessage) {
        logger.info("收到开奖信息外发数据");
        try {
            logger.info("开奖信息外发数据:" + mapMessage.getMapNames());
            final String lotteryCode = (String) mapMessage.getObject("lotteryCode");
            final String issue = (String) mapMessage.getObject("issue");
            final String bonusNumber = (String) mapMessage.getObject("bonusNumber");
            final String bonusTime = (String) mapMessage.getObject("bonusTime");
            String bonusInfoSendUrl = ConfigUtils.getValue("BONUS_INFO_SEND_URL");
            if (!Utils.isNotEmpty(bonusInfoSendUrl)) {
                return;
            }
            for (String sendUrl : bonusInfoSendUrl.split(",")) {
                final String url = sendUrl;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendBonusInfo(url, lotteryCode, issue, bonusNumber, bonusTime);
                    }
                }).start();
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private void sendBonusInfo(String url, String lotteryCode, String issue, String bonusNumber, String bonusTime) {
        /** 发送中奖信息  2016-09-18 注释代码（这个目前不需要外发）
    	HttpClientUtils httpClientUtils = new HttpClientUtils(url);
        Map<String, String> map = new HashMap<String, String>();
        map.put("operator", "save");
        map.put("lotteryCode", lotteryCode);
        map.put("issue", issue);
        map.put("bonusNumber", bonusNumber);
        map.put("bonusTime", bonusTime);
        httpClientUtils.setPara(map);
        httpClientUtils.httpClientRequest();
        */
    }
}
