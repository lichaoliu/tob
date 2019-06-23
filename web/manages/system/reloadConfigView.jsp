<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/jstl.jsp" %>
<% request.setAttribute("host", request.getServerName());%>
<html>
<head>
    <title>出票业务管理系统参数配置文件加载</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="Cache-Control" content="max-age=0" forua="true"/>
    <meta http-equiv="Cache-Control" content="no-cache" forua="true"/>
    <meta http-equiv="Cache-Control" content="must-revalidate" forua="true"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/validator.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/page.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/page.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/manages.css">
</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
    <tr>
        <td class="title" style="text-align:left;">系统参数设置 >> 配置文件管理</td>
    </tr>
    <c:if test="${msg != null}">
	    <tr>
	        <td class="title">${msg}</td>
	    </tr>
    </c:if>
	<tr>
		<td style="padding: 4px;">
			<form name="form1" method="post" action="${pageContext.request.contextPath}/manages/reloadConfig">
				<table width="100%" border="1" cellpadding="4" cellspacing="0" class="seach">
					<tr>
						<td width="10%" align="center">配置文件:</td>
                        <td width="10%" align="center">
							<select name="name">
								<option value="">请选择</option>
								<option value="utils.ConfigUtils" <c:if test="${'utils.ConfigUtils' == name}">selected</c:if>>系统配置文件</option>
								<option value="sendClient.sl.SlSendClientConfig" <c:if test="${'sendClient.sl.SlSendClientConfig' == name}">selected</c:if>>天津思乐福彩出票口</option>
								<option value="sendClient.fcbynew.FcbySendClientConfig" <c:if test="${'sendClient.fcbynew.FcbySendClientConfig' == name}">selected</c:if>>丰彩博雅出票口</option>
								<option value="sendClient.ahtc.AhtcSendClientConfig" <c:if test="${'sendClient.ahtc.AhtcSendClientConfig' == name}">selected</c:if>>安徽宣城出票口</option>
								<option value="sendClient.whtc.WhtcSendClientConfig" <c:if test="${'sendClient.whtc.WhtcSendClientConfig' == name}">selected</c:if>>湖北武汉出票口</option>
								<option value="sendClient.hp.HpSendClientConfig" <c:if test="${'sendClient.hp.HpSendClientConfig' == name}">selected</c:if>>恒朋福彩出票口</option>
								<option value="sendClient.rctc.RctcSendClientConfig" <c:if test="${'sendClient.rctc.RctcSendClientConfig' == name}">selected</c:if>>瑞彩体彩出票口</option>
								<option value="sendClient.hc.HcSendClientConfig" <c:if test="${'sendClient.hc.HcSendClientConfig' == name}">selected</c:if>>华彩出票口</option>
								<option value="sendClient.hlj.HljSendClientConfig" <c:if test="${'sendClient.hlj.HljSendClientConfig' == name}">selected</c:if>>正好博大(黑龙江)出票口</option>
								<option value="sendClient.hb.HbSendClientConfig" <c:if test="${'sendClient.hb.HbSendClientConfig' == name}">selected</c:if>>鸿博出票口</option>
								<option value="sendClient.rlyg.RlygSendClientConfig" <c:if test="${'sendClient.rlyg.RlygSendClientConfig' == name}">selected</c:if>>睿朗阳光出票口</option>
								<option value="sendClient.lhc.LhcSendClientConfig" <c:if test="${'sendClient.lhc.LhcSendClientConfig' == name}">selected</c:if>>乐和彩出票口</option>
								<option value="sendClient.yc.YcSendClientConfig" <c:if test="${'sendClient.yc.YcSendClientConfig' == name}">selected</c:if>>粤彩出票口</option>
                             </select>
                        </td>
						<td width="10%" align="center">服务器：</td>
                        <td width="10%" align="center">
							<select name="host">
		                        <c:choose>
		                            <c:when test="${'192.168.0.220' == host}">
										<option value="192.168.0.220" <c:if test="${'192.168.0.220' == host}">selected</c:if>>220</option>
		                            </c:when>
		                            <c:when test="${'119.254.92.201' == host}">
										<option value="119.254.92.201" <c:if test="${'119.254.92.201' == host}">selected</c:if>>201</option>
		                            </c:when>
		                            <c:otherwise>
										<option value="172.16.208.209" <c:if test="${'172.16.208.209' == host}">selected</c:if>>209</option>
										<option value="172.16.208.210" <c:if test="${'172.16.208.210' == host}">selected</c:if>>210</option>
		                            </c:otherwise>
		                        </c:choose>
                             </select>
                        </td>
                        <td width="30%" align="left">
                        	<input type="radio" name="action" checked value="viewValue" >显示<input type="radio" name="action" value="reloadFile" >重载
						</td>
                        <td align="center">
                            <input type="submit" id="subBtn" value="确定" onclick="return ifLink('确认要重载选择的配置文件？')" class="submit" style="width: 64px; border: none">
                        </td>
					</tr>
                    <c:if test="${keyMessage != null}">
	                    <tr>
	                        <td colspan="6" align="left">
								${keyMessage}
	                        </td>
	                    </tr>
                    </c:if>
                </table>
            </form>
        </td>
    </tr>
</table>
<script>
    function ifLink(str) {
        if (form1.name.value == "") {
            alert("请选择配置文件");
            return false;
        }
        
        var operate = document.all.action;
        for(var i=0;i<operate.length;i++){
            if(operate[i].value == "reloadFile" && true == operate[i].checked){
				var bln = confirm(str);
				return bln;
			}
		}
    }
</script>
</body>
</html>