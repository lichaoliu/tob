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
<%@ page import="com.cndym.service.ISubIssueForJingCaiZuQiuService"%>
<%@ page contentType="text/html;charset=gbk"%>
<%
ISubIssueForJingCaiZuQiuService subIssueForJingCaiZuQiuService = (ISubIssueForJingCaiZuQiuService)SpringUtils.getBean("subIssueForJingCaiZuQiuServiceImpl");
//SubIssueForJingCaiZuQiu aa = subIssueForJingCaiZuQiuService.getSubIssueForJingCaiZuQiu(64279L);
//out.println("<br/>="+aa.getBackup1()+"=");
//out.println("<br/>="+aa.getEndOperator()+"=");
List<SubIssueForJingCaiZuQiu> subIssueForJingCaiZuQiuList = subIssueForJingCaiZuQiuService.getSubIssueForJingCaiZuQiuListByDate("20140417");
Map<String, SubIssueForJingCaiZuQiu> jingCaiZuQiuMap = new HashMap<String, SubIssueForJingCaiZuQiu>();
for (SubIssueForJingCaiZuQiu aa : subIssueForJingCaiZuQiuList) {
	if ("1".equals(aa.getBackup1()))
		out.println("<br/>a="+aa.getBackup1()+"="+aa.getEndOperator()+"="+aa.getPlayCode()+","+aa.getPollCode()+","+aa.getIssue()+","+aa.getSn()+","+aa.getUpdateTime());
	else
		out.println("<br/>b="+aa.getBackup1()+"="+aa.getEndOperator()+"="+aa.getPlayCode()+","+aa.getPollCode()+","+aa.getIssue()+","+aa.getSn()+","+aa.getUpdateTime());
}
%>