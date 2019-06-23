<%@page import="org.json.simple.JSONObject"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="com.google.gson.JsonArray"%>
<%@page import="com.cndym.sendClient.yt.bean.Json"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="org.apache.log4j.Logger"%>
<%@ page import="java.io.PrintWriter"%>
<%@page import="com.cndym.utils.bonusClass.BonusClassManager"%>
<%@ page import="com.cndym.service.*"%>
<%@ page import="com.cndym.bean.tms.*"%>
<%@ page import="org.json.simple.parser.JSONParser"%>
<%@ page import="com.cndym.utils.Utils"%>
<%@ page import="com.cndym.utils.SpringUtils"%>
<%@ page import="com.cndym.utils.ConfigUtils"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.cndym.utils.SpringUtils"%>
<%@ page contentType="text/html;charset=gbk"%>
<%
	String lotteryCode = "109";
	//String lotteryCode = request.getParameter("lotteryCode");
	String issue = request.getParameter("issue");
	String amount = request.getParameter("amount");
	
	IMainIssueService issueService = (IMainIssueService) SpringUtils.getBean("mainIssueServiceImpl");
	MainIssue mainIssue = issueService.getMainIssueByLotteryIssue(lotteryCode, issue);
	if(mainIssue != null){
		String bonusClass = mainIssue.getBonusClass();
		if(Utils.isNotEmpty(bonusClass)){
			out.println("bonusClass:" + bonusClass + "<br/>");
			bonusClass = bonusClass.replace("[","").replace("]","").replace("\'","\"");
			JSONParser parser = new JSONParser();  
			JSONObject jsonObject = (JSONObject)parser.parse(bonusClass);
			String newJson = "[{'n':" + jsonObject.get("n") + ",'m':" + jsonObject.get("m") + ",'c':" + amount + ",'a':" + jsonObject.get("a") + ",'t':'" + jsonObject.get("t") + "'}]";
			out.println("new bonusClass:" + newJson + "<br/>");
			mainIssue.setBonusClass(newJson);
			issueService.updateIssue(mainIssue);
		}
	}
	
%>
