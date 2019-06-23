package com.cndym.admin.servlet.manages;

import com.cndym.constant.Constants;
import com.cndym.bean.admin.AlertAmountTable;
import com.cndym.bean.user.Member;
import com.cndym.admin.service.IAdminManagesService;
import com.cndym.servlet.ElTagUtils;
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
 * User: liangguili Date: 14-09-23 Time: 上午11:10
 */
public class AdminManagesServlet extends BaseManagesServlet {

    private Logger logger = Logger.getLogger(getClass());

    private static final String ALERT_AMOUNT_LIST = "alertAmountList";
    
    private static final String ADD_ALERT_AMOUNT = "addAlertAmount";
    
    private static final String EDIT_ALERT_AMOUNT = "editAlertAmount";
    
    private static final String MEMBER_LIST = "memberList";
    
    private static final String USED_ALERT_AMOUNT = "usedAlertAmount";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = Utils.formatStr(request.getParameter("action"));
        response.setContentType("text/html");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        IAdminManagesService postTicketService = (IAdminManagesService) SpringUtils.getBean("adminManagesServiceImpl");

        Integer page = Utils.formatInt(request.getParameter("page"), 1);
        Integer pageSize = Utils.formatInt(request.getParameter("pageSize"), Constants.PAGE_SIZE_50);
        request.setAttribute("page", page);
        request.setAttribute("pageSize", pageSize);

        if (ALERT_AMOUNT_LIST.equals(action)) {
        	List<AlertAmountTable> alertAmountList = postTicketService.getAlertAmountList();
        	Map<String, String> codeBal = new HashMap<String, String>();
        	List<Map<String, String>> lstQueBan = ElTagUtils.queryBanlance();
        	for(Map<String, String> map : lstQueBan){
        		codeBal.put(map.get("code"),map.get("balance"));
        	}
        	
        	if (Utils.isNotEmpty(alertAmountList)) {
        		for(AlertAmountTable alertAmount : alertAmountList){
            		String balance = codeBal.get(alertAmount.getPostCode());
            		if(balance == null || balance.equals("")){
            			balance = "--";
            		}
            		alertAmount.setBalance(balance);
            	} 
        		request.setAttribute("alertAmountList", alertAmountList);
        	} else {
        		request.setAttribute("msg", "取警报金额失败或者警报金额表是空!");
        	}
        	request.getRequestDispatcher("/admin/manages/alertAmount.jsp").forward(request, response);
        	return;
        }
        
        if(ADD_ALERT_AMOUNT.equals(action)){
        	AlertAmountTable alertAmountBean = new AlertAmountTable();
        	String postCode = Utils.formatStr(request.getParameter("postCode1"));
        	String postName = Utils.formatStr(request.getParameter("postName1"));
        	String status = Utils.formatStr(request.getParameter("status"));
        	String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
        	String alertAmount = Utils.formatStr(request.getParameter("alertAmount"));
            String type = Utils.formatStr(request.getParameter("type"));
            
            if (Utils.isNotEmpty(postCode)) {
            	alertAmountBean.setPostCode(postCode);
            }
            if (Utils.isNotEmpty(postName)) {
            	alertAmountBean.setPostName(postName);
            }
            if (Utils.isNotEmpty(status)) {
            	alertAmountBean.setStatus(status);
            }
            if (Utils.isNotEmpty(lotteryCode)) {
            	alertAmountBean.setLotteryCode(lotteryCode);
            }
            if (Utils.isNotEmpty(alertAmount)) {
            	alertAmountBean.setAlertAmount(Double.parseDouble(alertAmount));
            }
            if(type.equals("edit")){
            	setManagesLog(request, action, postCode + "出票口，修改了警报额度<span style=\"color:#f00\">" + alertAmount + "</span>元或支持的彩种<span style=\"color:#f00\">"+lotteryCode+"</span>", Constants.MANAGER_LOG_REMIND_MESSAGE);
            	postTicketService.updateAlertAmount(alertAmountBean);
            	request.getRequestDispatcher("/manages/adminManages?action=alertAmountList").forward(request, response);
            }
            return;
        }
        
        if(EDIT_ALERT_AMOUNT.equals(action)){
        	String postCode = Utils.formatStr(request.getParameter("postCode"));
        	String postName = new String(request.getParameter("postName").getBytes("ISO-8859-1"), "UTF-8");
        	String status = Utils.formatStr(request.getParameter("status"));
        	String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
        	String alertAmount = Utils.formatStr(request.getParameter("alertAmount"));
        	
        	if (Utils.isNotEmpty(postCode)) {
                request.setAttribute("postCode", postCode);
            }
            if (Utils.isNotEmpty(postName)) {
                request.setAttribute("postName", postName);
            }
            if (Utils.isNotEmpty(status)) {
                request.setAttribute("status", status);
            }
            if (Utils.isNotEmpty(lotteryCode)) {
                request.setAttribute("lotteryCode", lotteryCode);
            }
            if (Utils.isNotEmpty(alertAmount)) {
                request.setAttribute("alertAmount", alertAmount);
            }
            request.setAttribute("type", "edit");
        	request.getRequestDispatcher("/admin/manages/alertAmountInsert.jsp").forward(request, response);
        	return;
        }
        
        if (USED_ALERT_AMOUNT.equals(action)){
        	String status = Utils.formatStr(request.getParameter("status"));
        	
        	String myCheckbox[] = request.getParameterValues("myCheckbox");
        	
        	if (Utils.isNotEmpty(myCheckbox)) {
        		String statusDesc = status.equals("0") ? "无效" :"有效";
        		setManagesLog(request, action, "<span style=\"color:#f00\">"+java.util.Arrays.toString(myCheckbox) + "</span>出票口，修改为<span style=\"color:#f00\">" + statusDesc + "</span>", Constants.MANAGER_LOG_REMIND_MESSAGE);
        		postTicketService.updateAlertAmountStatus(myCheckbox,status);
        		request.getRequestDispatcher("/manages/adminManages?action=alertAmountList").forward(request, response);
        	}
        	return;
        }
        
        if (MEMBER_LIST.equals(action)) {
            String sid = Utils.formatStr(request.getParameter("sid"));
            String name = Utils.formatStr(request.getParameter("name"));
            String status = Utils.formatStr(request.getParameter("status"));

            Member member = new Member();
            if (Utils.isNotEmpty(sid)) {
                member.setSid(sid);
                request.setAttribute("sid", sid);
            }
            if (Utils.isNotEmpty(name)) {
                member.setName(name);
                request.setAttribute("name", name);
            }
            if (Utils.isNotEmpty(status)) {
                member.setStatus(new Integer(status));
                request.setAttribute("status", status);
            }
            Map<String,Object> map = new HashMap<String,Object>();
            PageBean pageBean = postTicketService.getPageBeanToMember(member, page, pageSize,map);
            
            //map = postTicketService.getSumToMember();
            request.setAttribute("sumAmount", map.get("AMOUNT"));
            request.setAttribute("pageBean", pageBean);
            request.getRequestDispatcher("/admin/manages/memberList.jsp").forward(request, response);
            return;
        }
    }
}
