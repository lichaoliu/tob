package com.cndym.servlet;

import com.cndym.sendClient.ISendClient;
import com.cndym.utils.JsonBinder;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import com.cndym.utils.xml.parse.ByteCodeUtil;
import com.cndym.utils.xml.parse.XmlBean;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: mcs
 * Date: 12-12-5
 * Time: 下午3:41
 */
public class ZzcIssueSaleInfoServlet extends HttpServlet {

    private Logger logger = Logger.getLogger(getClass());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.addHeader("cache-control", "no-cache");
        response.addHeader("expires", "thu,  01 jan   1970 00:00:01 gmt");
        PrintWriter out = response.getWriter();

        String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
        String issue = Utils.formatStr(request.getParameter("issue"));

        if (!Utils.isNotEmpty(lotteryCode) || !Utils.isNotEmpty(issue)) {
            return;
        }

        logger.info("查询彩种【" + lotteryCode + "】彩期【" + issue + "】销售信息");

        ISendClient sendClient = (ISendClient) SpringUtils.getBean("zzcSendClientImpl");
        StringBuffer xml = new StringBuffer();
        xml.append("<body lotteryCode=\"").append(lotteryCode).append("\" ");
        xml.append("issue=\"").append(issue).append("\"/>");

        XmlBean issueSaleBean = sendClient.issueSaleQuery(ByteCodeUtil.xmlToObject(xml.toString()));

        logger.info("掌中彩return: " + issueSaleBean.toXml());

        Map<String, Double> returnMap = new HashMap<String, Double>();
        if (Utils.isNotEmpty(issueSaleBean)) {
            double totalSale = Utils.formatDouble(issueSaleBean.attribute("totalSale"), 0d);
            double totalBonus = Utils.formatDouble(issueSaleBean.attribute("totalBonus"), 0d);

            returnMap.put("totalSale", totalSale);
            returnMap.put("totalBonus", totalBonus);
        }
        out.print(JsonBinder.buildNonDefaultBinder().toJson(returnMap));
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

}
