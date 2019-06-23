<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/jstl.jsp" %>
<html>
<head>
    <title>出票口玩法管理</title>
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
    
	    function addPlays() {
	    	var weightId = document.getElementById("weightId").value;
	    	var lotteryCode = document.getElementById("lotteryCode").value;
	    	document.form1.action = "/manages/postManagesServlet?action=pre_add_weight_play&weightId=" + weightId + "&lotteryCode=" + lotteryCode;
            form1.submit();
	    }
	    
	    function delPlay(id){
	    	if (confirm("确定要删除此玩法配置？")) {
		    	var weightId = document.getElementById("weightId").value;
		    	var lotteryCode = document.getElementById("lotteryCode").value;
		    	document.form1.action = "/manages/postManagesServlet?action=delete_weight_play&playId=" + id + "&weightId=" + weightId + "&lotteryCode=" + lotteryCode;
	            form1.submit();
	    	}
	    }
	    
        function backToLast() {
            document.form1.action = "/manages/postManagesServlet?action=reload_db_weight";
            form1.submit();
        }
    </script>
</head>

<body>
<input type="hidden" id="weightId" value="${weightId}">
<input type="hidden" id="lotteryCode" value="${lotteryCode}">
<c:if test="${msg != null}">
    <script type="text/javascript">
        alert('${msg}');
    </script>
</c:if>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
    <tr>
        <td class="title" style="text-align:left;">系统参数设置 >> 出票口管理>>玩法管理</td>
    </tr>
    <tr>
		<td align="left" colspan="11">
			<span style="color:#0033cc;font-weight: bold;">
				<input type="button" value="添加" onclick="return addPlays()" class="submit" style="width: 64px; border: none">
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
	                <c:if test="${weightPlayList != null && fn:length(weightPlayList) != 0}">
	                    <tr id=one>
	                    	<td width="5%">序号</td>
	                        <td width="50%">玩法</td>
	                        <td width="20%">选号方式</td>
	                        <td width="25%">配置</td>
	                    </tr>
	            		<c:forEach items="${weightPlayList}" var="weightPlay" varStatus="cont">
		                    <tr>
		                    	<td align="center">${cont.count}</td>
		                        <td align="center">
									<c:forEach var="playBean" items="${lotteryPlayList}">
											<c:if test="${fn:contains(weightPlay.playCodes, playBean.code)}">
												${playBean.name}
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</c:if>
			            			</c:forEach>							
								</td>
								<td align="center">
									<%Integer flag = 0; %>
									<c:forEach var="playBean" items="${lotteryPlayList}">
										<%if(flag == 0){ %>
											<c:if test="${fn:contains(weightPlay.playCodes, playBean.code)}">
												 <%flag = 1; %>
												<c:forEach var="pollBean" items="${playBean.lotteryPollList}">
			            							<c:if test="${fn:contains(weightPlay.pollCodes, pollBean.code)}">
				            							${pollBean.name} 
				            							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									            	</c:if>
							            		</c:forEach>
											</c:if>
										<%} %>
			            			</c:forEach>							
								</td>
		                        <td  align="center">
		                        	<a href="/manages/postManagesServlet?action=pre_modify_weight_play&playId=${weightPlay.id}">修改</a>
		                        	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		                        	<a href="javascript:void(0)" onClick="delPlay(${weightPlay.id})">删除</a>
		                        	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		                        	<a href="/manages/postManagesServlet?action=pre_modify_weight_sid&weightId=${weightId}&playId=${weightPlay.id}&lotteryCode=${lotteryCode}">接入商配置</a>
		                        	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		                        	<a href="/manages/postManagesServlet?action=load_weight_time&weightId=${weightId}&playId=${weightPlay.id}&lotteryCode=${lotteryCode}">时间配置</a>
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