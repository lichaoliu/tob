package com.cndym.servlet;

import com.cndym.GlobalAdapter;
import com.cndym.utils.Utils;
import com.cndym.utils.xml.parse.XmlBean;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class AppRequest extends javax.servlet.http.HttpServlet {
    private static final long serialVersionUID = -6098695675329865655L;
    private Logger logger = Logger.getLogger(getClass());

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.addHeader("cache-control", "no-cache");
        response.addHeader("expires", "thu,  01 jan   1970 00:00:01 gmt");
        request.setCharacterEncoding("utf-8");
        long time = new Date().getTime();
        PrintWriter out = response.getWriter();
        String msg = Utils.formatStr(request.getParameter("msg"));
        logger.info("ip=" + getIpAddress(request) + ",method:" + request.getMethod()+",msg=" + msg);
        GlobalAdapter globalAdapter = new GlobalAdapter();
        XmlBean bean = null;
        try {
            bean = globalAdapter.execute(msg);
            logger.info(bean.toXml());
            out.print("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + bean.toXml());
        } catch (Exception e) {
            logger.info("", e);
            out.print("9999");
        }
        long subTime = new Date().getTime() - time;
        if (subTime > 2000) {
            logger.info("runtime（" + (subTime) + "）millis is msg:" + msg);
            if (null != bean) {
                logger.info("runtime（" + (subTime) + "）millis is xml:" + bean.toXml());
            }
        }
        out.flush();
        out.close();
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doPost(request, response);
    }

    // 获取客户端IP地址，支持代理服务器
    public static String getIpAddress(javax.servlet.http.HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        String localIP = "127.0.0.1";
        if ((ip == null) || (ip.length() == 0) || (ip.equalsIgnoreCase(localIP)) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("http_x_forwarded_for");
        }
        if ((ip == null) || (ip.length() == 0) || (ip.equalsIgnoreCase(localIP)) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if ((ip == null) || (ip.length() == 0) || (ip.equalsIgnoreCase(localIP)) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if ((ip == null) || (ip.length() == 0) || (ip.equalsIgnoreCase(localIP)) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.contains(",")) {
            return ip.split(",")[0].trim();
        } else {
            return ip;
        }
    }
}
