package com.cndym.sendClient.service;

import com.cndym.bean.tms.Ticket;
import com.cndym.service.ITicketService;
import com.cndym.utils.Utils;
import com.cndym.utils.xml.parse.XmlBean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：邓玉明
 * email：cndym@163.com
 * QQ：757579248
 * 时间: 12-2-14 下午5:48
 */
@Component
public class PostNumberInfoOperator extends BasePostOperator {
    private Logger logger = Logger.getLogger(getClass());
    @Resource
    private ITicketService ticketService;


    @Override
    public void execute(XmlBean xmlBean) {
        if (null == xmlBean) return;
        logger.info("进入赛果处理:" + xmlBean.toString());
        String ticketId = xmlBean.attribute("ticketId");
        Double bonusAmount = Utils.formatDouble(xmlBean.attribute("bonusAmount"), 0d);
        String spInfo = xmlBean.attribute("text");
        if (check(ticketId, spInfo)) {
            ticketService.updateSpInfo(ticketId, spInfo);

        }
    }

    public boolean check(String ticketId, String spInfo) {
        Ticket ticket = ticketService.getTicketByTicketId(ticketId);
        Map<String, Integer> map = new HashMap<String, Integer>();
        String tNumberInfo = ticket.getNumberInfo();
        String[] tempArr = tNumberInfo.split("\\|")[0].split(";");
        for (String s : tempArr) {
            String key = s.substring(2);
            if (map.containsKey(key)) {
                map.put(key, map.get(key) + 1);
            } else {
                map.put(key, 1);
            }
        }
        String[] numArr = spInfo.split(";");
        for (String num : numArr) {
            String[] arr = num.split("\\|");
            if (arr.length != 3) return false;
            String[] subArr = arr[1].split(",");
            for (String s : subArr) {
                if (!s.contains("(")) return false;
                s = s.replace(":", "");
                s = s.replace("/", ",");
                s = s.replaceAll("\\([^\\(]+\\)", "");
                s = s.replace("-", "");
                s = s.replace("=", ":");
                String key = s;
                if (map.containsKey(key)) {
                    map.put(key, map.get(key) - 1);
                    if (map.get(key) == 0) {
                        map.remove(key);
                    }
                } else {
                    return false;
                }
            }
        }
        if (map.size() == 0) {
            return true;
        }
        return false;
    }
}
