package com.cndym.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.cndym.bean.tms.Ticket;
import com.cndym.constant.Constants;
import com.cndym.sendClient.ISendClientOperator;
import com.cndym.service.ITicketService;
import com.cndym.servlet.manages.BaseManagesServlet;
import com.cndym.utils.HttpClientUtils;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import com.cndym.utils.xml.parse.ByteCodeUtil;
import com.cndym.utils.xml.parse.XmlBean;

public class HandTicketFailedServlet extends BaseManagesServlet {

	private static final long serialVersionUID = -5285819288661345175L;
	private final static String TICKET_HAND_FAILED = "thf";// 手工失败票
	private final static String HAND_BONUS_FCBY = "hbf";// 手工查询中奖
	private Logger logger = Logger.getLogger(getClass());

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.addHeader("cache-control", "no-cache");
		response.addHeader("expires", "thu,  01 jan   1970 00:00:01 gmt");
		PrintWriter out = response.getWriter();

		String type = Utils.formatStr(request.getParameter("type"));
		try {
			if (TICKET_HAND_FAILED.equals(type)) {
				String ticketId = request.getParameter("ticketId");
				if (Utils.isNotEmpty(ticketId)) {
					ITicketService ticketService = (ITicketService) SpringUtils.getBean("ticketServiceImpl");
					ticketService.doHhandTicketFailed(ticketId);
				} else {
					out.print("票不存在");
				}
			} else {
				logger.error("方法不存在");
				out.print("方法不存在");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		out.print("OK");
		out.flush();
		out.close();
		return;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
}
