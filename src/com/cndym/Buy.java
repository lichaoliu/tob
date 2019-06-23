package com.cndym;

import com.cndym.serviceMap.ServiceList;
import com.cndym.utils.OrderIdBuildUtils;
import com.cndym.xmlObject.BuilderXml;
import com.cndym.xmlObject.Header;

import javax.servlet.http.HttpServletRequest;

/**
 * User: mcs
 * Date: 12-10-28
 * Time: 上午10:00
 */
public class Buy {

    public String createXml(HttpServletRequest request) {

        String sid = "8001";
        String userInfo = "梅传颂$411503198810084307$15801343759";

        String lotteryCode = request.getParameter("lotteryId");
        String playCode = request.getParameter("playId");
        String issue = request.getParameter("issue");
        String ticket = request.getParameter("ticket");
        String orderId = OrderIdBuildUtils.buildOrderId();

        StringBuffer order = new StringBuffer();
        order.append("<order userInfo=\"").append(userInfo).append("\"");
        order.append(" lotteryId=\"").append(lotteryCode).append("\"");
        order.append(" issue=\"").append(issue).append("\"");
        order.append(" playId=\"").append(playCode).append("\"");
        order.append(" orderId=\"").append(orderId).append("\">");
        order.append(ticket);
        order.append("</order>");

        return BuilderXml.returnUnionXml(new Header(sid, ServiceList.ORDER), order.toString()).toXml();

    }

}
