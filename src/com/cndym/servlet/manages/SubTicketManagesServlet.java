package com.cndym.servlet.manages;

import com.cndym.bean.tms.SubTicket;
import com.cndym.constant.Constants;
import com.cndym.service.ISubTicketService;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: MengJingyi
 * QQ: 116741034
 * Date: 13-5-30
 * Time: 下午1:11
 * To change this template use File | Settings | File Templates.
 */
public class SubTicketManagesServlet extends BaseManagesServlet {


    private Logger logger = Logger.getLogger(getClass());
    private final String SUB_TICKET_LIST = "subTicketList";

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = Utils.formatStr(request.getParameter("action"));
        response.setContentType("text/html");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");


        Integer page = Utils.formatInt(request.getParameter("page"), 1);
        Integer pageSize = Utils.formatInt(request.getParameter("pageSize"), Constants.PAGE_SIZE_50);
        request.setAttribute("page", page);
        request.setAttribute("pageSize", pageSize);

        if (SUB_TICKET_LIST.equals(action)) {
            String ticketId = Utils.formatStr(request.getParameter("ticketId"));
            request.setAttribute("ticketId", ticketId);
            ISubTicketService subTicketService = (ISubTicketService) SpringUtils.getBean("subTicketServiceImpl");
            List<SubTicket> subTicketList = subTicketService.findSubTicketListEx(ticketId);
            if (Utils.isNotEmpty(subTicketList) && subTicketList.size() > 0) {
                request.setAttribute("subTicketList", subTicketList);
                request.getRequestDispatcher("/manages/ticket/subTicketList.jsp").forward(request, response);
                return;
            } else {
                request.setAttribute("notSubTickert", "notSubTickert");
                request.getRequestDispatcher("/manages/ticket/subTicketList.jsp").forward(request, response);
                return;
            }
        }
    }
}
