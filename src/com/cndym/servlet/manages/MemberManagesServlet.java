package com.cndym.servlet.manages;

import com.cndym.bean.user.Account;
import com.cndym.bean.user.Member;
import com.cndym.constant.Constants;
import com.cndym.control.bean.Weight;
import com.cndym.lottery.lotteryInfo.LotteryList;
import com.cndym.lottery.lotteryInfo.bean.LotteryClass;
import com.cndym.service.IAccountService;
import com.cndym.service.IMemberService;
import com.cndym.utils.CooperationIdBuildUtils;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import com.cndym.utils.hibernate.PageBean;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * User: mcs
 * Date: 12-10-29
 * Time: 下午10:27
 */
public class MemberManagesServlet extends BaseManagesServlet {

    private Logger logger = Logger.getLogger(getClass());

    private static final String MEMBER_LIST = "memberList";

    private static final String PRE_ADD_MEMBER = "preAddMember";

    private static final String ADD_MEMBER = "addMember";

    private static final String PRE_EDIT_MEMBER = "preEditMember";

    private static final String EDIT_MEMBER = "editMember";

    private static final String DETAIL_MEMBER = "detailMember";

    private static final String EDIT_STATUS = "editStatus";
    
    private static final String EDIT_LOTTERYID = "editLotteryId";
    
    private static final String SAVE_LOTTERYID = "saveLotteryId";
    
    private static final String SAVE_LOTTERYID_MEMBER = "saveMemberByLotteryId";


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

        IMemberService memberService = (IMemberService) SpringUtils.getBean("memberServiceImpl");

        Integer page = Utils.formatInt(request.getParameter("page"), 1);
        Integer pageSize = Utils.formatInt(request.getParameter("pageSize"), Constants.PAGE_SIZE_50);
        request.setAttribute("page", page);
        request.setAttribute("pageSize", pageSize);

        if (MEMBER_LIST.equals(action)) {
            String sid = Utils.formatStr(request.getParameter("sid"));
            String name = Utils.formatStr(request.getParameter("name"));
            String status = Utils.formatStr(request.getParameter("status"));
            String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
            String lotteryStatus = Utils.formatStr(request.getParameter("lotteryStatus"));

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
            
            if (Utils.isNotEmpty(lotteryCode)) {
            	member.setBackup1(lotteryCode);
                request.setAttribute("lotteryCode", lotteryCode);
            }
            if (Utils.isNotEmpty(lotteryStatus)) {
            	member.setBackup2(lotteryStatus);
                request.setAttribute("lotteryStatus", lotteryStatus);
            }
            
            PageBean pageBean = memberService.getPageBeanByPara(member, page, pageSize);
            request.setAttribute("pageBean", pageBean);
            request.getRequestDispatcher("/manages/member/memberList.jsp").forward(request, response);
        }

        if (PRE_ADD_MEMBER.equals(action)) {
            String sid = memberService.getMaxCooperationSidOfMask(CooperationIdBuildUtils.COOPERATION_SID_MASK);
            String privateKey = "";
            do {
                sid = CooperationIdBuildUtils.buildCode(sid);
                privateKey = CooperationIdBuildUtils.buildPrivateKey();
            } while (Utils.isNotEmpty(memberService.getMemberBySid(sid)));

            request.setAttribute("privateKey", privateKey);
            request.setAttribute("sid", sid);
            request.getRequestDispatcher("/manages/member/addMember.jsp").forward(request, response);
        }

        if (ADD_MEMBER.equals(action)) {
            String sid = Utils.formatStr(request.getParameter("sid"));
            String name = Utils.formatStr(request.getParameter("name"));
            String privateKey = Utils.formatStr(request.getParameter("privateKey"));
            String companyName = Utils.formatStr(request.getParameter("companyName"));
            String contactPerson = Utils.formatStr(request.getParameter("contactPerson"));
            String companyAddress = Utils.formatStr(request.getParameter("address"));
            String cardCode = Utils.formatStr(request.getParameter("cardCode"));
            String mobile = Utils.formatStr(request.getParameter("mobile"));
            String email = Utils.formatStr(request.getParameter("email"));

            Member member = new Member();
            member.setSid(sid);
            member.setName(name);
            member.setPrivateKey(privateKey);
            member.setCompanyName(companyName);
            member.setCompanyAddress(companyAddress);
            member.setContactPerson(contactPerson);
            member.setMobile(mobile);
            member.setEmail(email);
            member.setCardCode(cardCode);

            int result = memberService.doSaveMember(member);
            if (result == 1) {
                setManagesLog(request, action, "添加接入商" + sid + "成功", Constants.MANAGER_LOG_USER_MESSAGE);
            } else {
                setManagesLog(request, action, "添加接入商" + sid + "失败", Constants.MANAGER_LOG_USER_MESSAGE);
            }
            response.sendRedirect("/manages/memberManagesServlet?action=memberList");
        }

        if (PRE_EDIT_MEMBER.equals(action)) {
            String sid = Utils.formatStr(request.getParameter("sid"));
            Member member = memberService.getMemberBySid(sid);

            request.setAttribute("member", member);
            request.getRequestDispatcher("/manages/member/editMember.jsp").forward(request, response);
        }

        if (EDIT_MEMBER.equals(action)) {
            String sid = Utils.formatStr(request.getParameter("sid"));
            String contactPerson = Utils.formatStr(request.getParameter("contactPerson"));
            String companyAddress = Utils.formatStr(request.getParameter("address"));
            String cardCode = Utils.formatStr(request.getParameter("cardCode"));
            String mobile = Utils.formatStr(request.getParameter("mobile"));
            String email = Utils.formatStr(request.getParameter("email"));


            Member member = new Member();
            member.setSid(sid);
            member.setCompanyAddress(companyAddress);
            member.setContactPerson(contactPerson);
            member.setCardCode(cardCode);
            member.setMobile(mobile);
            member.setEmail(email);

            int result = memberService.doUpdateMember(member);
            if (result == 1) {
                setManagesLog(request, action, "修改接入商" + sid + "信息成功", Constants.MANAGER_LOG_USER_MESSAGE);
            } else {
                setManagesLog(request, action, "修改接入商" + sid + "信息失败", Constants.MANAGER_LOG_USER_MESSAGE);
            }
            response.sendRedirect("/manages/memberManagesServlet?action=memberList");
        }

        if (DETAIL_MEMBER.equals(action)) {
            IAccountService accountService = (IAccountService) SpringUtils.getBean("accountServiceImpl");
            String sid = Utils.formatStr(request.getParameter("sid"));
            String type = Utils.formatStr(request.getParameter("type"));
            Member member = memberService.getMemberBySid(sid);
            Account account = accountService.getAccountBySid(sid);
            request.setAttribute("type", type);
            request.setAttribute("member", member);
            request.setAttribute("account", account);
            request.getRequestDispatcher("/manages/member/detailMember.jsp").forward(request, response);
        }

        if (EDIT_STATUS.equals(action)) {
            String sid = Utils.formatStr(request.getParameter("sid"));
            Integer status = Utils.formatInt(request.getParameter("status"), Constants.MEMBER_STATUS_LIVE);
            int result = memberService.doUpdateMember(sid, status);
            if (result == 1) {
                if (status.intValue() == Constants.MEMBER_STATUS_LIVE) {
                    setManagesLog(request, action, "激活接入商" + sid + "信息成功", Constants.MANAGER_LOG_USER_MESSAGE);
                } else {
                    setManagesLog(request, action, "锁定接入商" + sid + "信息成功", Constants.MANAGER_LOG_USER_MESSAGE);
                }
            } else {
                if (status.intValue() == Constants.MEMBER_STATUS_LIVE) {
                    setManagesLog(request, action, "激活接入商" + sid + "信息失败", Constants.MANAGER_LOG_USER_MESSAGE);
                } else {
                    setManagesLog(request, action, "锁定接入商" + sid + "信息失败", Constants.MANAGER_LOG_USER_MESSAGE);
                }
            }
            response.sendRedirect("/manages/memberManagesServlet?action=memberList");
        }
        
        if (EDIT_LOTTERYID.equals(action)) {
        	String sid = Utils.formatStr(request.getParameter("sid"));
        	Member member = memberService.getMemberBySid(sid);
        	String lotteryCode = member.getBackup1();
        	List<LotteryClass> lstLotteryClass = LotteryList.getLotteryClassList();
        	
        	List<Weight> lstLottery = new ArrayList<Weight>();
        	for(LotteryClass lotteryClass : lstLotteryClass){
        		Weight weight = new Weight();
        		weight.setLotteryCode(lotteryClass.getCode());
        		weight.setName(lotteryClass.getName());
        		if(Utils.isNotEmpty(lotteryCode)){
        			weight.setDefaultPostCode(lotteryCode.contains(lotteryClass.getCode()) ? "1" : "0");
        		}else{
        			weight.setDefaultPostCode("0");
        		}
        		lstLottery.add(weight);
        	}
        	
        	//排序
        	Collections.sort(lstLottery, new Comparator<Weight>() {
                public int compare(Weight arg0, Weight arg1) {
                    String code0 = arg0.getLotteryCode();
                    String code2 = arg1.getLotteryCode();
                   return code0.compareTo(code2);
                }
            });
        	
            request.setAttribute("lstLottery", lstLottery);
            request.setAttribute("sid", sid);
            request.getRequestDispatcher("/manages/member/memberLotteryList.jsp").forward(request, response);
        }
        
        if (SAVE_LOTTERYID.equals(action)) {
        	String myCheckbox[] = request.getParameterValues("myCheckbox");
        	String sid = Utils.formatStr(request.getParameter("sid"));
        	String lotteryCode = "";
        	if (Utils.isNotEmpty(myCheckbox)) {
        		for(int i = 0 ; i < myCheckbox.length; i++){
        			if(i != myCheckbox.length - 1){
        				lotteryCode += myCheckbox[i] +",";
        			}else{
        				lotteryCode += myCheckbox[i];
        			}
        		}
        	}
        	memberService.doUpdateMemberLotteryCode(sid,lotteryCode);
        } else if (SAVE_LOTTERYID_MEMBER.equals(action)){
        	String myCheckbox[] = request.getParameterValues("myCheckbox");
        	String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
        	String sid = "";
        	if (Utils.isNotEmpty(myCheckbox)) {
        		for(int i = 0 ; i < myCheckbox.length; i++){
        			if(i != myCheckbox.length - 1){
        				sid += myCheckbox[i] +",";
        			}else{
        				sid += myCheckbox[i];
        			}
        		}
        	}
        	request.setAttribute("lotteryCode", lotteryCode);
        	int rtn = memberService.doUpdateLotteryCodeToMember(lotteryCode, sid);
        	response.sendRedirect("/manages/issueManagesServlet?action=memberQueryByLotteryCode&lotteryCode="+lotteryCode);
        }
    }
}
