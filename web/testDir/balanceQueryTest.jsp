<%@ page import="org.apache.log4j.Logger"%>
<%@ page import="com.cndym.sms.SmsClient"%>
<%@ page import="com.cndym.utils.HttpClientUtils" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="com.cndym.utils.Utils" %>
<%@ page import="com.cndym.email.MailEngine" %>
<%@ page import="com.cndym.utils.SpringUtils" %>
<%@ page import="com.cndym.utils.ConfigUtils" %>
<%@ page import="com.cndym.email.SmsEngine" %>
<%@ page import="com.cndym.admin.service.IAdminManagesService" %>
<%@ page import="com.cndym.bean.admin.AlertAmountTable" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
Logger logger = Logger.getLogger(getClass());
logger.info("start");
double amount = 0d;
Double defaultAmount = 50000d;

String mobile = Utils.formatStr(request.getParameter("mobile"));
String email = Utils.formatStr(request.getParameter("email"));

DecimalFormat decimalFormat = new DecimalFormat("##############.##");
decimalFormat.setMinimumFractionDigits(2);
decimalFormat.setMinimumIntegerDigits(1);

try {
	IAdminManagesService service = (IAdminManagesService) SpringUtils.getBean("adminManagesServiceImpl");
	List<AlertAmountTable> alertList = service.getAlertAmountList();
	List<Map<String, String>> mapList = com.cndym.servlet.ElTagUtils.queryBanlance();

	StringBuffer buffer = new StringBuffer();
	for (AlertAmountTable amountTable : alertList) {
		String code = amountTable.getPostCode();
		double alertAmount = Utils.formatDouble(amountTable.getAlertAmount(), defaultAmount);
		amount = alertAmount;
		for (Map<String, String> stringMap : mapList) {
	        amount = Utils.formatDouble(stringMap.get("balance"), 0d);
	        if (stringMap.get("code").equals(code)) {
	        	break;
	        }
		}

        if (amount < alertAmount) {
            StringBuffer result = new StringBuffer("截至");
            result.append(com.cndym.utils.Utils.today("MM-dd HH:mm:ss"));
            result.append(amountTable.getPostName()).append("的余额为");
            result.append(decimalFormat.format(amount));
            result.append("元。");

            String body = result.toString();
            if (Utils.isNotEmpty(body)) {
                final String memo = amountTable.getPostName() + "余额不足";
                MailEngine mailEngine = (MailEngine) SpringUtils.getBean("mailEngine");
                if (Utils.isNotEmpty(email)) {
                    String[] emailArr = email.split(",");
                    try {
                        mailEngine.sendMessage(emailArr, ConfigUtils.getValue("MAIL.DEFAULT.FROM"), null, body, memo, null);
                    } catch (Exception e) {
                        logger.error("", e);
                    }
                }
                if (Utils.isNotEmpty(mobile)) {
                    try {
                        SmsClient.sendSMS(mobile, body);
                    } catch (Exception e) {
                    	logger.error("", e);
                    }
                }
            }
            buffer.append(body);
        }
    }
    if (Utils.isNotEmpty(buffer)) {
        out.print(buffer.toString());
    } else {
        out.print("true");
    }
    logger.info("end");
} catch (Exception e) {
	logger.error("", e);
}
%>