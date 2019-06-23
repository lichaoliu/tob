package com.cndym.servlet;

import com.cndym.service.SubIssueBonus.ISubIssueBonusOperator;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * 作者：邓玉明 email：cndym@163.com QQ：757579248 时间: 12-2-16 上午2:19
 */
public class BonusNoticeServlet extends HttpServlet {
	private static final long serialVersionUID = -2481084112238008535L;
	private Logger logger = Logger.getLogger(getClass());

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.addHeader("cache-control", "no-cache");
		response.addHeader("expires", "thu,  01 jan   1970 00:00:01 gmt");
		PrintWriter out = response.getWriter();
		String lottery = Utils.formatStr(request.getParameter("lottery"));
		String issue = Utils.formatStr(request.getParameter("issue"));
		String sn = Utils.formatStr(request.getParameter("sn"));

		logger.info("!!!!!BonusNoticeServlet start !!!!!!!");
		logger.info("lotteryCode" + lottery + ",issue" + issue);

		if (Utils.isEmpty(sn)) {
			if (Utils.isNotEmpty(lottery) && Utils.isNotEmpty(issue)) {
				if ("006".equals(lottery) || "107".equals(lottery)) {
					ISubIssueBonusOperator noticeBonusOperator = (ISubIssueBonusOperator) SpringUtils.getBean("noticeBonusOperator");
					noticeBonusOperator.noticeBonusGp(lottery, issue);
				}
			}
		} else {

		}

		logger.info("!!!!!BonusNoticeServlet end !!!!!!!");
		out.print("success");
		out.flush();
		out.close();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

}
