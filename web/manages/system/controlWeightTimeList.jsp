<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/jstl.jsp" %>
<html>
<head>
    <title>出票口玩法时间管理</title>
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
    <script type="text/javascript">
    
	    function addTime() {
	    	var playId = document.getElementById("playId").value;
	    	var weightId = document.getElementById("weightId").value;
	    	var lotteryCode = document.getElementById("lotteryCode").value;
	    	document.form1.action = "/manages/postManagesServlet?action=pre_add_weight_time&weightId=" + weightId + "&lotteryCode=" + lotteryCode + "&playId=" + playId;
            form1.submit();
	    }
	    
	    function delTime(id){
	    	if (confirm("确定要删除此时间配置？")) {
	    		var playId = document.getElementById("playId").value;
		    	var weightId = document.getElementById("weightId").value;
		    	var lotteryCode = document.getElementById("lotteryCode").value;
		    	document.form1.action = "/manages/postManagesServlet?action=delete_weight_time&timeId=" + id + "&playId=" + playId + "&weightId=" + weightId + "&lotteryCode=" + lotteryCode;
	            form1.submit();
	    	}
	    }
	    
	    function backToLast() {
        	var weightId = document.getElementById("weightId").value;
	    	var lotteryCode = document.getElementById("lotteryCode").value;
            document.form1.action = "/manages/postManagesServlet?action=load_weight_play&&lotteryCode="+lotteryCode+"&weightId=" + weightId;
            form1.submit();
        }
    </script>
</head>

<body>
<input type="hidden" id="weightId" value="${weightId}">
<input type="hidden" id="playId" value="${playId}">
<input type="hidden" id="lotteryCode" value="${lotteryCode}">
<c:if test="${msg != null}">
    <script type="text/javascript">
        alert('${msg}');
    </script>
</c:if>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
    <tr>
        <td class="title" style="text-align:left;">系统参数设置 >> 出票口管理>>玩法管理>>时间管理</td>
    </tr>
    <tr>
		<td align="left" colspan="11">
			<span style="color:#0033cc;font-weight: bold;">
				<input type="button" value="添加" onclick="return addTime()" class="submit" style="width: 64px; border: none">
				<input type="button" value="返回" onclick="return backToLast();" class="submit" style="width: 64px; border: none">
			</span>
		</td>		
	</tr>
    <c:if test="${msg != null}">
	    <tr>
	        <td class="title">${msg}</td>
	    </tr>
	</c:if>
    <tr>
        <td style="padding: 4px;">
            <form name="form1" method="post" action="/manages/postManagesServlet?action=reload_db_weight">
                <table  width="100%" border="1" cellpadding="4" cellspacing="0" class="content">
	                <c:if test="${weightTimeList != null && fn:length(weightTimeList) != 0}">
	                    <tr id=one>
	                    	<td width="5%">序号</td>
	                        <td width="30%">周配置</td>
	                        <td width="10%">开始时间</td>
	                        <td width="8%">类型</td>
	                        <td width="30%">出票口</td>
	                        <td width="10%">操作</td>
	                    </tr>
	            		<c:forEach items="${weightTimeList}" var="weightTime" varStatus="cont">
		                    <tr>
		                    	<td align="center">${cont.count}</td>
		                        <td align="center">${weightTime.weeks}</td>
								<td align="center">
									${weightTime.startTime}						
								</td>
								<td align="center">
									${weightTime.type}						
								</td>
								<td align="center">
									<c:if test="${weightTime.type == '常规'}">
										<c:forEach var="postBean" items="${myf:getPostMaps()}">
								                 <c:if test="${weightTime.weightRuleList[0].postCode == postBean.code}">${postBean.name}</c:if>
								        </c:forEach>
									</c:if>		
									<c:if test="${weightTime.type == '投注金额'}">
										<c:forEach var="ruleBean" items="${weightTime.weightRuleList}">
											${ruleBean.param}<fmt:formatNumber value="${ruleBean.amount}" pattern="0.00"/>元:
											<c:forEach var="postBean" items="${myf:getPostMaps()}">
								                 <c:if test="${ruleBean.postCode == postBean.code}">${postBean.name}</c:if>
								        	</c:forEach>
								        	<br>
										</c:forEach>
									</c:if>	
									<c:if test="${weightTime.type == '比例'}">
										<c:forEach var="ruleBean" items="${weightTime.weightRuleList}">
											${ruleBean.param}:
											<c:forEach var="postBean" items="${myf:getPostMaps()}">
								                 <c:if test="${ruleBean.postCode == postBean.code}">${postBean.name}</c:if>
								        	</c:forEach>
								        	<br>
										</c:forEach>
									</c:if>			
								</td>
		                        <td  align="center">
		                        	<a href="/manages/postManagesServlet?action=pre_modify_weight_time&weightId=${weightId}&timeId=${weightTime.id}&playId=${playId}&lotteryCode=${lotteryCode}">修改</a>
		                        	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		                        	<a href="javascript:void(0)" onClick="delTime(${weightTime.id})">删除</a>
		                        </td>
		                    </tr>
	            		</c:forEach>
	            	</c:if>
                </table>
            </form>
        </td>
    </tr>
</table>
</body>
</html>