<%@ page import="java.util.Date"%>
<%@ page import="com.cndym.utils.Utils"%>
<%@ page import="com.cndym.utils.SpringUtils"%>
<%@ page import="com.cndym.utils.ConfigUtils"%>
<%@ page import="com.cndym.service.ITicketService"%>
<%@ page import="com.cndym.service.IMainIssueService"%>
<%@ page import="com.cndym.bean.tms.MainIssue"%>
<%@ page import="com.cndym.constant.Constants"%>
<%@ page contentType="text/html;charset=utf8"%>
<%
String lotteryCode = request.getParameter("lotteryCode");
String issue = request.getParameter("issue");
ITicketService ticketService = (ITicketService)SpringUtils.getBean("ticketServiceImpl");
IMainIssueService mainIssueService = (IMainIssueService)SpringUtils.getBean("mainIssueServiceImpl");


out.println("<br/>lotteryCode="+lotteryCode);
out.println("<br/>issue="+issue);
if (Utils.isNotEmpty(lotteryCode) && Utils.isNotEmpty(issue)) {
	MainIssue mainIssue = mainIssueService.getMainIssueByLotteryIssue(lotteryCode, issue);
    if (null == mainIssue) {
        return;
    }
    if (Constants.OPERATORS_AWARD_COMPLETE != mainIssue.getOperatorsAward()) {
        return;
    }
    ticketService.doBonusAmountToAccount(lotteryCode, issue);
    out.println("<br/>OK");
} else {
	out.println("NO");
}

%>