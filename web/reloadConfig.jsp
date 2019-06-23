<%@page import="com.cndym.cache.XMemcachedClientWrapper"%>
<%@page import="com.cndym.utils.ConfigUtils"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.cndym.utils.Utils"%>
<%@page import="java.lang.reflect.Method"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String keys = "POST_LOTTERY_CODE,POST_CODE_LOTTERY,POST_GP_LOTTERY_CODE,POST_ORDER_LOTTERY_CODE,POST_CODE_OEDER_LOTTERY,POST_CODE_ORDER_LOTTERY,POST_CODE_GP_LOTTERY,POST_CODE_GP_OEDER_LOTTERY,POST_CODE_GP_ORDER_LOTTERY,ALLOW_OUT_BONUS_LOTTERY,ALLOW_UPDATE_MAIN_ISSUE_LOTTERY,ALLOW_CANCEL_RESEND_ORDER_LOTTERY";
Class<?> instance = null;
Method forInstance = null;
Method getValue = null;
PrintWriter printWriter = response.getWriter();
Logger logger = Logger.getLogger(getClass());
StringBuffer sb = new StringBuffer();
Map<String, String> oldMap = new HashMap<String, String>();
Map<String, String> newMap = new HashMap<String, String>();
try {
	String load = Utils.formatStr(request.getParameter("load"), null);
	String className = Utils.formatStr(request.getParameter("name"), null);
	String keyArray[] = keys.split(",");
	if (Utils.isNotEmpty(className)) {
		instance = Class.forName(className);
		if (Utils.isNotEmpty(instance)) {
			getValue = instance.getMethod("getValue", new Class[]{java.lang.String.class});
			forInstance = instance.getMethod("forInstance", new Class[]{});
			for (String key : keyArray) {
				try {
					Object object = getValue.invoke(null, new Object[]{key});
					if (object != null) {
						oldMap.put(key, String.valueOf(object));
					}
				} catch (Exception e){}
			}

			if (Utils.isNotEmpty(load) && "1".equals(load)) {
				forInstance.invoke(null, new Object[]{});
				for (String key : keyArray) {
					try {
						Object object = getValue.invoke(null, new Object[]{key});
						if (object != null) {
							newMap.put(key, String.valueOf(object));
						}
					} catch (Exception e){}
				}
			}
		}
	}
} catch (ClassNotFoundException cnfe) {
	sb.append("ClassNotFoundException=" + cnfe.getMessage());
	logger.error(null, cnfe);
} catch (Exception e) {
	sb.append("Exception="+e.getMessage());
	logger.error(null, e);
}

sb.append("**********load before**************<br/><br/>");
int newSize = newMap.size();
for (String key : oldMap.keySet()) {
	if (newSize > 0 && !oldMap.get(key).equals(newMap.get(key))) {
		sb.append(key + "=<span style=\"color:#f00\">" + oldMap.get(key) + "</span>");
	} else {
		sb.append(key + "=" + oldMap.get(key));
	}
	sb.append("<br/><br/>");
}

if (newSize > 0) {
	sb.append("<br/>");
	sb.append("**********load after**************<br/><br/>");
	for (String key : newMap.keySet()) {
		if (!oldMap.get(key).equals(newMap.get(key))) {
			sb.append(key + "=<span style=\"color:#f00\">" + newMap.get(key) + "</span>");
		} else {
			sb.append(key + "=" + newMap.get(key));
		}
		sb.append("<br/><br/>");
	}
}

printWriter.print(sb.toString());
printWriter.flush();
printWriter.close();
%>
