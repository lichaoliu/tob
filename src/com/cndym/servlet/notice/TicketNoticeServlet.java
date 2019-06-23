package com.cndym.servlet.notice;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.cndym.servlet.manages.ticketNotice.IManageTicketNotice;
import com.cndym.utils.CommonUtils;
import com.cndym.utils.PostCodeIPConfig;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;

public class TicketNoticeServlet extends javax.servlet.http.HttpServlet {
	private static final long serialVersionUID = 1230242892747341464L;
	private static Logger logger = Logger.getLogger(TicketNoticeServlet.class);

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
		String forWardedIp = CommonUtils.getIpAddress(request);
		String charset = PostCodeIPConfig.getValue(forWardedIp);
		
		if(charset == null || "".equals(charset)){
			logger.info("接收到来自出票口的通知,forWardedIp="+forWardedIp +",该IP未录入!");
			return;
		}
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.addHeader("cache-control", "no-cache");
		response.addHeader("expires", "thu,  01 jan   1970 00:00:01 gmt");
		request.setCharacterEncoding(charset);
		String postCode = request.getParameter("postCode");
		
		logger.info("接收到来自出票口[" + postCode + "]的通知,charset="+charset+",forWardedIp="+forWardedIp);
		String outMsg = "";
		if(Utils.isEmpty(postCode)){
			outMsg = "用户传入的出票口编码为空";
		}else {
			IManageTicketNotice manageTicketNotice = (IManageTicketNotice) SpringUtils.getBean("p" + postCode + "ManageTicketNoticeImpl");
			if(manageTicketNotice == null){
				outMsg = "找不到出票口为[" + postCode + "]对应的处理类";
				logger.error(outMsg);
			}else {
				outMsg = manageTicketNotice.manageTicketNotice(request);
			}
		}
		if(Utils.isNotEmpty(outMsg)){//如果实现类返回的结果不为空或出错，则向推送接口返回结果信息
			PrintWriter out = response.getWriter();
			out.print(outMsg);
			out.flush();
			out.close();
		}
	}

	protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
		doPost(request, response);
	}

}
