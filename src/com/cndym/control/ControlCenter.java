package com.cndym.control;

import com.cndym.constant.Constants;
import com.cndym.service.ITicketService;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：邓玉明 时间：11-5-28 下午12:33 QQ：757579248 email：cndym@163.com
 */
public class ControlCenter {
    public static Logger logger = Logger.getLogger(ControlCenter.class);

    public static void executeFormJms(Map<String, String> para) {
        String lotteryCode = para.get("lotteryCode");
        String orderId = para.get("orderId");
        String sid = para.get("sid");
        logger.info("进入jms发单 sid:" + sid + " 彩种:" + lotteryCode + " 批次: " + orderId);
        ITicketService ticketService = (ITicketService) SpringUtils.getBean("ticketServiceImpl");
        if (!Utils.isNotEmpty(orderId)) {
            return;
        }
        if (!Utils.isNotEmpty(sid)) {
            return;
        }
        ticketService.doSendTicket(orderId, sid, Constants.TICKET_STATUS_DOING);
    }

    public static void executeFormDateBase(Map<String, String> para) {
        String lotteryCode = para.get("lotteryCode");
        String orderId = para.get("orderId");
        String sid = para.get("sid");
        logger.info("进入datebase发单 sid:" + sid + " 彩种:" + lotteryCode + " 批次: " + orderId);
        ITicketService ticketService = (ITicketService) SpringUtils.getBean("ticketServiceImpl");
        if (!Utils.isNotEmpty(orderId)) {
            return;
        }
        if (!Utils.isNotEmpty(sid)) {
            return;
        }
        ticketService.doSendTicket(orderId, sid, Constants.TICKET_STATUS_WAIT);
    }

    //重发使用
    public static void executeFormReSend(Map<String, String> para) {
        String lotteryCode = para.get("lotteryCode");
        String orderId = para.get("orderId");
        String sid = para.get("sid");
        logger.info("进入订单重新发单 sid:" + sid + " 彩种:" + lotteryCode + " 批次: " + orderId);
        ITicketService ticketService = (ITicketService) SpringUtils.getBean("ticketServiceImpl");
        if (!Utils.isNotEmpty(orderId)) {
            return;
        }
        if (!Utils.isNotEmpty(sid)) {
            return;
        }
        ticketService.doSendTicket(orderId, sid, Constants.TICKET_STATUS_RESEND);
    }


    public static void main(String[] args) {
        Map<String, String> para = new HashMap<String, String>();

        para.put("lotteryCode", "113");
        para.put("orderId", "80000220130129113003");
        para.put("sid", "800002");

        executeFormJms(para);


    }
}
