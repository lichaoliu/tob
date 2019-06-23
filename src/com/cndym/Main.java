package com.cndym;

import com.cndym.bean.tms.MainIssue;
import com.cndym.bean.tms.Ticket;
import com.cndym.bean.user.Account;
import com.cndym.bean.user.Member;
import com.cndym.constant.Constants;
import com.cndym.sendClient.ISendClient;
import com.cndym.service.IAccountService;
import com.cndym.service.IMainIssueService;
import com.cndym.service.IMemberService;
import com.cndym.service.ITicketService;
import com.cndym.utils.*;
import com.cndym.utils.hibernate.PageBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

//        MainIssue mainIssue = new MainIssue();
//        mainIssue.setLotteryCode("001");
//        mainIssue.setName("2012127");
//        mainIssue.setStartTime(Utils.formatDate("2012-10-26 21:35:00", "yyyy-MM-dd HH:mm:ss"));
//        mainIssue.setEndTime(Utils.formatDate("2012-10-28 20:00:00", "yyyy-MM-dd HH:mm:ss"));
//        mainIssue.setSimplexTime(Utils.formatDate("2012-10-28 19:30:00", "yyyy-MM-dd HH:mm:ss"));
//        mainIssue.setDuplexTime(Utils.formatDate("2012-10-28 19:45:00", "yyyy-MM-dd HH:mm:ss"));
//        mainIssue.setSendStatus(Constants.ISSUE_SALE_WAIT);
//        mainIssue.setStatus(Constants.ISSUE_STATUS_WAIT);
//        mainIssue.setBonusStatus(Constants.ISSUE_BONUS_STATUS_NO);
//
//        IMainIssueService mainIssueService = (IMainIssueService) SpringUtils.getBean("mainIssueServiceImpl");
//        mainIssueService.save(mainIssue);


//        Member member = new Member();
//        member.setUserCode(UserCodeBuildUtils.userCode());
//        member.setUserName("15801343758");
//        member.setPassWord(Md5.Md5("111111"));
//        member.setSid("8001");
//        member.setPrivateKey("123456");
//        member.setCompanyName("测试");
//        member.setStatus(Constants.MEMBER_STATUS_LIVE);
//        member.setCreateTime(new Date());
//        member.setLoginTime(new Date());
//
//        IMemberService memberService = (IMemberService) SpringUtils.getBean("memberServiceImpl");
//        memberService.save(member);


//        IMemberService memberService = (IMemberService) SpringUtils.getBean("memberServiceImpl");
//        Member member = memberService.getMemberBySid("8001");
//        Account account = new Account();
//        IAccountService accountService = (IAccountService) SpringUtils.getBean("accountServiceImpl");
//
//        account.setUserCode(member.getUserCode());
//        account.setBonusAmount(Constants.DOUBLE_ZERO);
//        account.setRechargeAmount(Constants.DOUBLE_ZERO);
//        account.setPresentAmount(Constants.DOUBLE_ZERO);
//        account.setFreezeAmount(Constants.DOUBLE_ZERO);
//        account.setBonusTotal(Constants.DOUBLE_ZERO);
//        account.setRechargeTotal(Constants.DOUBLE_ZERO);
//        account.setPresentTotal(Constants.DOUBLE_ZERO);
//        account.setPayTotal(Constants.DOUBLE_ZERO);
//        account.setDrawTotal(Constants.DOUBLE_ZERO);
//        accountService.save(account);


        String postCode = "06";
        ISendClient sendClient = (ISendClient) SpringUtils.getBean("cqSendClientImpl");
        ITicketService ticketService = (ITicketService) SpringUtils.getBean("ticketServiceImpl");
        String[] lotteryCodeArr = {"001"};
        for (String lotteryCode : lotteryCodeArr) {
            int page = 1;
            PageBean pageBean = ticketService.getSendedTicket(lotteryCode, postCode, page, 50);

            List<Ticket> ticketList = pageBean.getPageContent();
            if (Utils.isNotEmpty(ticketList)) {
                sendClient.orderQuery(ticketList);
            }

            int pageTotal = pageBean.getPageTotal();
            while (pageTotal > page) {
                page++;
                pageBean = ticketService.getSendedTicket(lotteryCode, postCode, page, 50);
                ticketList = pageBean.getPageContent();
                if (Utils.isNotEmpty(ticketList)) {
                    sendClient.orderQuery(ticketList);
                }
            }
        }
    }
}
