package com.cndym.servlet.manages;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.cndym.constant.Constants;
import com.cndym.servlet.manages.bulidIssue.IBulidIssueFun;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;

public class BulidIssueServlet extends BaseManagesServlet {

	private static final long serialVersionUID = 4767720177903943958L;
	private Logger logger = Logger.getLogger(getClass());

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.addHeader("cache-control", "no-cache");
		response.addHeader("expires", "thu,  01 jan   1970 00:00:01 gmt");
		request.setCharacterEncoding("utf-8");

		String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
		String date = Utils.formatStr(request.getParameter("startTime"));
		int days = Utils.formatInt(request.getParameter("days"), 0);

		if (Utils.isNotEmpty(lotteryCode) && Utils.isNotEmpty(date)) {
			IBulidIssueFun bulidIssueFun = (IBulidIssueFun) SpringUtils.getBean("l" + lotteryCode + "BulidIssueFunImpl");
			int result = bulidIssueFun.bulid(lotteryCode, date, days);

			String description = "";
			if (result == 1) {
				request.setAttribute("msg", "添加彩期成功");
				description = "批量添加lotteryCode=" + lotteryCode + ",date=" + date + ",days=" + days + "成功";
			} else {
				request.setAttribute("msg", "添加彩期失败");
				description = "批量添加lotteryCode=" + lotteryCode + ",date=" + date + ",days=" + days + "成功";
			}
			setManagesLog(request, "BatchAddIssue", description, Constants.MANAGER_LOG_BATCH_EDIT_SIMPLE_ISSUE);
			request.getRequestDispatcher("lottery/batchSaveIssue.jsp").forward(request, response);
			return;
		}
	}

	protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
		doPost(request, response);
	}
}
