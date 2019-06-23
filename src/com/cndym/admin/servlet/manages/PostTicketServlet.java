package com.cndym.admin.servlet.manages;

import com.cndym.bean.query.TicketQueryBean;
import com.cndym.bean.tms.Ticket;
import com.cndym.constant.Constants;
import com.cndym.admin.service.IPostTicketService;
import com.cndym.servlet.manages.BaseManagesServlet;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import com.cndym.utils.hibernate.PageBean;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * User: liangguili Date: 14-04-14 Time: 上午11:10
 */
public class PostTicketServlet extends BaseManagesServlet {

    private Logger logger = Logger.getLogger(getClass());

    private final static String POST_TICKET_LIST = "postTicketList";
    private final static String MEMBER_TICKET_LIST = "memberTicketList";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = Utils.formatStr(request.getParameter("action"));
        response.setContentType("text/html");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        IPostTicketService postTicketService = (IPostTicketService) SpringUtils.getBean("postTicketServiceImpl");

        Integer page = Utils.formatInt(request.getParameter("page"), 1);
        Integer pageSize = Utils.formatInt(request.getParameter("pageSize"), Constants.PAGE_SIZE_50);
        request.setAttribute("page", page);
        request.setAttribute("pageSize", pageSize);

        if (POST_TICKET_LIST.equals(action)) {
            String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
            String postCode = Utils.formatStr(request.getParameter("postCode"));
            String createStartTime = Utils.formatStr(request.getParameter("createStartTime"));
            String createEndTime = Utils.formatStr(request.getParameter("createEndTime"));
            String type = Utils.formatStr(request.getParameter("type"));

            TicketQueryBean queryBean = new TicketQueryBean();
            Ticket ticket = new Ticket();
            
            if (Utils.isNotEmpty(createStartTime)) {
                queryBean.setCreateStartTime(Utils.formatDate(createStartTime + " 00:00:00"));
                request.setAttribute("createStartTime", createStartTime);
            }else if (Utils.isEmpty(type)) {
                queryBean.setCreateStartTime(Utils.formatDate(Utils.today("yyyy-MM-dd") + " 00:00:00"));
                request.setAttribute("createStartTime", Utils.today("yyyy-MM-dd"));
            }
            
            if (Utils.isNotEmpty(createEndTime)) {
                queryBean.setCreateEndTime(Utils.formatDate(createEndTime + " 23:59:59"));
                request.setAttribute("createEndTime", createEndTime);
            }else if (Utils.isEmpty(type)) {
                queryBean.setCreateStartTime(Utils.formatDate(Utils.today("yyyy-MM-dd") + " 00:00:00"));
                request.setAttribute("createEndTime", Utils.today("yyyy-MM-dd"));
            }

            ticket.setLotteryCode(lotteryCode);
            ticket.setPostCode(postCode);

            request.setAttribute("lotteryCode", lotteryCode);
            request.setAttribute("postCode", postCode);

            queryBean.setTicket(ticket);
            
            Map<String,Object> map = new HashMap<String,Object>();
            PageBean pageBean = postTicketService.getPageBeanByPara(queryBean, page, pageSize, map);
            request.setAttribute("sumAmount", map.get("AMOUNT"));
            request.setAttribute("pageBean", pageBean);
            request.getRequestDispatcher("/admin/manages/postTicketList.jsp").forward(request, response);
        }
        
        if(MEMBER_TICKET_LIST.equals(action)) {
            String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
            String sid = Utils.formatStr(request.getParameter("sid"));
            String createStartTime = Utils.formatStr(request.getParameter("createStartTime"));
            String createEndTime = Utils.formatStr(request.getParameter("createEndTime"));
            String type = Utils.formatStr(request.getParameter("type"));

            TicketQueryBean queryBean = new TicketQueryBean();
            Ticket ticket = new Ticket();
            
            if (Utils.isNotEmpty(createStartTime)) {
                queryBean.setCreateStartTime(Utils.formatDate(createStartTime + " 00:00:00"));
                request.setAttribute("createStartTime", createStartTime);
            }else if (Utils.isEmpty(type)) {
                queryBean.setCreateStartTime(Utils.formatDate(Utils.today("yyyy-MM-dd") + " 00:00:00"));
                request.setAttribute("createStartTime", Utils.today("yyyy-MM-dd"));
            }
            
            if (Utils.isNotEmpty(createEndTime)) {
                queryBean.setCreateEndTime(Utils.formatDate(createEndTime + " 23:59:59"));
                request.setAttribute("createEndTime", createEndTime);
            }else if (Utils.isEmpty(type)) {
                queryBean.setCreateStartTime(Utils.formatDate(Utils.today("yyyy-MM-dd") + " 00:00:00"));
                request.setAttribute("createEndTime", Utils.today("yyyy-MM-dd"));
            }

            ticket.setLotteryCode(lotteryCode);
            ticket.setSid(sid);

            request.setAttribute("lotteryCode", lotteryCode);
            request.setAttribute("sid", sid);

            queryBean.setTicket(ticket);
            Map<String,Object> map = new HashMap<String,Object>();
            PageBean pageBean = postTicketService.getPageBeanByParaToMember(queryBean, page, pageSize,map);
            request.setAttribute("sumAmount", map.get("AMOUNT"));
            request.setAttribute("pageBean", pageBean);
            request.getRequestDispatcher("/admin/manages/memberTicketList.jsp").forward(request, response);
        }

    }
}
