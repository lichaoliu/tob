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
    
	    function save() {
	    	 var j = 0;
		        var myc = document.getElementsByName("sidCheckBox");
		        for (i = 0; i < myc.length; i++) {
		            if (myc[i].checked) {
		                j++;
		            }
		        }
		        if (j <= 0) {
		            alert("请选中要修改出票口的接入商");
		            return false;
		        }
		
		        if (confirm("确定要修改出票口？")) {
		        	var weightId = document.getElementById("weightId").value;
		        	var playId = document.getElementById("playId").value;
			    	var lotteryCode = document.getElementById("lotteryCode").value;
		            document.form1.action = "/manages/postManagesServlet?action=modify_weight_sid&weightId=" + weightId + "&playId=" + playId + "&lotteryCode=" + lotteryCode;
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
        <td class="title" style="text-align:left;">系统参数设置 >> 出票口管理>>玩法管理>>接入商出票口管理</td>
    </tr>
    <tr>
		<td align="left" colspan="11">
			<span style="color:#0033cc;font-weight: bold;">
				<input type="button" value="保存" onclick="return save()" class="submit" style="width: 64px; border: none">
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
	                    <tr id=one>
	                    	<td width="1%"></td>
	                    	<td width="5%">序号</td>
	                        <td width="20%">接入商</td>
	                        <td width="20%">接入编号</td>
	                        <td width="10%">出票口编码</td>
	                        <td width="20%">出票口名称</td>
	                        <td width="20%">选择出票口</td>
	                    </tr>
	            		<c:forEach items="${myf:getMemberList()}" var="member" varStatus="cont">
		                    <tr>
		                    	<td align="center"><input type="checkbox" name="sidCheckBox" value="${member.sid}"></td>
		                    	<td align="center">${cont.count}</td>
		                        <td align="center">
									${member.name}						
								</td>
								<td align="center">
									${member.sid}						
								</td>
								<td align="center">
									<%Integer flag1 = 0; %>
									<c:forEach var="sidBean" items="${weightSidList}"  varStatus="cout">
										<c:if test="${sidBean.sid == member.sid}">
											${sidBean.postCode}
											<%flag1 = 1; %>
										</c:if>
			            			</c:forEach>	
			            			<%if(flag1 == 0){ %>
												默认
									<%} %>						
								</td>
		                        <td  align="center">
		                        	<%Integer flag2 = 0; %>
		                        	<c:forEach var="sidBean" items="${weightSidList}"  varStatus="cout">
										<c:if test="${sidBean.sid == member.sid}">
											<c:forEach var="postBean" items="${myf:getPostMaps()}">
								                 <c:if test="${sidBean.postCode == postBean.code}">${postBean.name}</c:if>
								            </c:forEach>
								            <%flag2 = 1; %>
										</c:if>
			            			</c:forEach>	
			            			<%if(flag2 == 0){ %>
											默认
									<%} %>
		                        </td>
		                        <td>
		                        	<select name="postCode${member.sid}">
											<option value="00">全部</option>
						                	<c:forEach var="postLotteryBean" items="${postLotteryList}">
						                		<%Integer flag = 0; %>
						                		<c:forEach var="sidBean" items="${weightSidList}"  varStatus="cout">
						                			<c:if test="${sidBean.sid == member.sid && sidBean.postCode == postLotteryBean.postCode}">
														<% flag = 1; %>
														<option value="${postLotteryBean.postCode}" selected>${postLotteryBean.postName}</option>
													</c:if>
						                		</c:forEach>
						                		<%if(flag == 0){%>
						                			<option value="${postLotteryBean.postCode}">${postLotteryBean.postName}</option>
						                		<%} %>
		            						</c:forEach>
			            		    </select>
		                        </td>
		                    </tr>
	            		</c:forEach>
                </table>
            </form>
        </td>
    </tr>
</table>
</body>
</html>