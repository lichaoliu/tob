package com.cndym.servlet;

import com.cndym.service.subIssue.ISubIssueOperator;
import com.cndym.utils.FileUtils;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 作者：邓玉明
 * email：cndym@163.com
 * QQ：757579248
 * 时间: 12-2-1 下午4:13
 */
public class SubIssueForJingCaiServlet extends HttpServlet {
    private Logger logger = Logger.getLogger(getClass());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.addHeader("cache-control", "no-cache");
        response.addHeader("expires", "thu,  01 jan   1970 00:00:01 gmt");
        PrintWriter out = response.getWriter();
        String lottery = Utils.formatStr(request.getParameter("lottery"));
        String msg = Utils.formatStr(request.getParameter("msg"));
        FileUtils.writeFile("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + msg, request.getSession().getServletContext().getRealPath("/") + lottery + ".xml", false, "utf-8");
        //logger.info("收到场次通知：" + msg);
        //TODO 后期确定是否需要进行加密
        String digest = Utils.formatStr(request.getParameter("digest"));
        ISubIssueOperator subIssueOperator = (ISubIssueOperator) SpringUtils.getBean("lottery" + lottery + "SubIssueOperator");
        if (null != subIssueOperator) {
            try {
                subIssueOperator.operator(msg);
                out.print("success");
                return;
            } catch (Exception e) {
            	logger.error("", e);
                out.print("failure");
                return;
            }
        }
        out.print("failure");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
