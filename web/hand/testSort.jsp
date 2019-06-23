<%@ page import="java.util.*"%>
<%@ page import="com.cndym.utils.Utils"%>
<%@ page import="com.cndym.utils.SpringUtils"%>
<%@ page import="com.cndym.utils.ConfigUtils"%>
<%@ page import="com.cndym.service.ITicketService"%>
<%@ page import="com.cndym.dao.IMainIssueDao"%>
<%@ page import="com.cndym.dao.IBonusLogDao"%>
<%@ page import="com.cndym.dao.IBonusTicketDao"%>
<%@ page import="com.cndym.service.IAccountService"%>
<%@ page import="com.cndym.bean.tms.Ticket"%>
<%@ page import="com.cndym.bean.tms.BonusLog"%>
<%@ page import="com.cndym.bean.tms.MainIssue"%>
<%@ page import="com.cndym.bean.tms.BonusTicket"%>
<%@ page import="com.cndym.constant.Constants"%>
<%@ page import="com.cndym.bean.tms.SubIssueForJingCaiZuQiu"%>
<%@ page import="com.cndym.adapter.tms.bean.MatchTimeInfo"%>
<%@ page import="com.cndym.adapter.tms.bean.NumberInfo"%>
<%@ page import="com.cndym.service.subIssue.ISubIssueOperator"%>
<%@ page import="com.cndym.service.subIssue.bean.SubIssueComm"%>
<%@ page import="com.cndym.service.ISubIssueForJingCaiZuQiuService"%>
<%@ page contentType="text/html;charset=gbk"%>
<%
String ticketId = request.getParameter("id");

ISubIssueForJingCaiZuQiuService subIssueForJingCaiZuQiuService = (ISubIssueForJingCaiZuQiuService)SpringUtils.getBean("subIssueForJingCaiZuQiuServiceImpl");
ITicketService ticketService = (ITicketService)SpringUtils.getBean("ticketServiceImpl");
Ticket ticket = ticketService.getTicketByTicketId(ticketId);
ISubIssueOperator subIssueOperator = (ISubIssueOperator) SpringUtils.getBean("comm" + ticket.getLotteryCode() + "SubIssueOperator");
String numberInfo = ticket.getNumberInfo();
String[] arr = numberInfo.split("\\|");
if (arr.length != 2) {
    out.println("<br/>1");
}
String[] matchIdArr = arr[0].split(";");
String guoGuan = arr[1];
String pollCode = ticket.getPollCode();
if (guoGuan.equals("1*1")) {
    pollCode = "01";
}

List<MatchTimeInfo> matchList = new ArrayList<MatchTimeInfo>();
for (String number : matchIdArr) {
    String[] subArr = number.split(":");
    if ((!"10".equals(ticket.getPlayCode()) && subArr.length != 2) || ("10".equals(ticket.getPlayCode()) && subArr.length != 3)) {
    	out.println("<br/>1");
    }
    MatchTimeInfo matchTimeInfo = new MatchTimeInfo();
    String palyCode = "";
    if ("10".equals(ticket.getPlayCode())) {
        palyCode = subArr[1];
    } else {
        palyCode = ticket.getPlayCode();
    }
    //SubIssueComm subIssueComm = subIssueOperator.getSubIssueComm(ticket.getIssue(), subArr[0], palyCode, pollCode);
	out.println("<br/>"+subArr[0].substring(8)+","+subArr[0].substring(0,8)+","+palyCode+","+pollCode);
    SubIssueForJingCaiZuQiu subIssueForJingCaiZuQiu = subIssueForJingCaiZuQiuService.getSubIssueForJingCaiZuQiuBySnDate(subArr[0].substring(8), subArr[0].substring(0,8), "00", pollCode, 1);
if(Utils.isNotEmpty(subIssueForJingCaiZuQiu)) {
    matchTimeInfo.setMatchId(subArr[0]);
    matchTimeInfo.setIssue(subIssueForJingCaiZuQiu.getIssue());
    matchTimeInfo.setTime(subIssueForJingCaiZuQiu.getEndTime());
    matchTimeInfo.setDanShiEndTime(subIssueForJingCaiZuQiu.getEndDanShiTime());
    matchTimeInfo.setFuShiEndTime(subIssueForJingCaiZuQiu.getEndFuShiTime());
    matchList.add(matchTimeInfo);
}
}

Collections.sort(matchList);
if(Utils.isNotEmpty(matchList)) {
MatchTimeInfo start = matchList.get(0);
MatchTimeInfo end = matchList.get(matchList.size() - 1);
out.println("<br/>start="+start.getMatchId());
out.println("<br/>end="+end.getMatchId());
}
%>












