package com.cndym.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.cndym.bean.tms.Ticket;
import com.cndym.sendClient.ISendClientOperator;
import com.cndym.service.ITicketService;
import com.cndym.servlet.manages.BaseManagesServlet;
import com.cndym.utils.ConfigUtils;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import com.cndym.utils.xml.parse.ByteCodeUtil;

public class ReloadConfigServlet extends BaseManagesServlet {

	private static final long serialVersionUID = 7952449949274955272L;

	private Logger logger = Logger.getLogger(getClass());

	private final static String CONFIG = "config";// 系统配置
	private final static String TICKET_HAND_FAILED = "thf";// 手工失败票

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = Utils.formatStr(request.getParameter("type"));

		try {
			if (CONFIG.equals(type)) {
				ConfigUtils.forInstance();
			} else if (TICKET_HAND_FAILED.equals(type)) {
				String ticketId = request.getParameter("ticketId");
				if (Utils.isNotEmpty(ticketId)) {
					ITicketService ticketService = (ITicketService) SpringUtils.getBean("ticketServiceImpl");
					ISendClientOperator postOrderOperator = (ISendClientOperator) SpringUtils.getBean("postOrderOperator");
					Ticket ticket = ticketService.getTicketByTicketId(ticketId);
					StringBuffer ticketXml = new StringBuffer("<tickets>");
					ticketXml.append("<ticket ticketId=\"");
					ticketXml.append(ticket.getTicketId());
					ticketXml.append("\" status=\"");
					ticketXml.append(ticket.getTicketStatus());
					ticketXml.append("\" saleCode=\"");
					ticketXml.append(ticket.getSaleCode());
					ticketXml.append("\" errCode=\"");
					ticketXml.append(ticket.getErrCode());
					ticketXml.append("\" errMsg=\"");
					ticketXml.append(ticket.getErrMsg());
					ticketXml.append("\"/>");
					ticketXml.append("</tickets>");
					postOrderOperator.execute(ByteCodeUtil.xmlToObject(ticketXml.toString()));
				}
			} else {
				logger.error("配置文件不存在");
				request.setAttribute("msg", "配置文件不存在，重载失败!");
			}

			request.setAttribute("msg", "重载成功.");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());

			request.setAttribute("msg", "异常错误，重载失败!");
		}

		request.getRequestDispatcher("/manages/system/reloadConfig.jsp").forward(request, response);
		return;
	}
}
