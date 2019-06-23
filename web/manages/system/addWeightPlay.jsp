<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/jstl.jsp" %>
<html>
<head>
    <title>添加出票口玩法</title>
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
	        var myc = document.getElementsByName("myCheckbox");
	        var plays = "";
	        var polls = "";
	        for (i = 0; i < myc.length; i++) {
	            if (myc[i].checked) {
	                var playCode = myc[i].value;
	                if(plays == ""){
	                	plays = plays + playCode;
	                }else{
	                	plays = plays + "," + playCode;
	                }
	                
	                var pollc = document.getElementsByName("pollCheckbox" + playCode);
	                
	                for(k = 0; k < pollc.length; k ++){
	                	if(pollc[k].checked){
	                		var pollCode = pollc[k].value;
	                		if(polls.indexOf(pollCode) == -1){
	                			if(polls == ""){
	                				polls = polls + pollCode;
	                			}else{
	                				polls = polls + "," + pollCode;
	                			}
	                		}
	                	}
	                }
	            	j++;
	            }
	        }
	       // alert(plays + "---" + polls);
	        if (j <= 0) {
	            alert("请选中要添加的玩法");
	            return false;
	        }
	
	        if (confirm("确定要保存此玩法配置？")) {
	        	var weightId = document.getElementById("weightId").value;
		    	var lotteryCode = document.getElementById("lotteryCode").value;
	            document.form1.action = "/manages/postManagesServlet?action=add_weight_play&playCodes=" + plays + "&pollCodes=" + polls + "&weightId=" + weightId + "&lotteryCode=" + lotteryCode;
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
<input type="hidden" id="weightId" name="weightId" value="${weightId}">
<input type="hidden" id="lotteryCode" name="lotteryCode" value="${lotteryCode}">
<c:if test="${msg != null}">
    <script type="text/javascript">
        alert('${msg}');
    </script>
</c:if>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
    <tr>
        <td class="title" style="text-align:left;">系统参数设置 >> 出票口管理>>添加玩法</td>
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
            <form name="form1" method="post" action="/manages/postManagesServlet?action=add_weight_play">
                <table  width="100%" border="1" cellpadding="4" cellspacing="0" class="content">
	                <c:if test="${lotteryPlayList != null && fn:length(lotteryPlayList) != 0}">
	                    <tr id=one>
							<td width="1%"></td>
	                        <td width="50%">玩法</td>
	                        <td width="20%">选号方式</td>
	                    </tr>
	            		<c:forEach items="${lotteryPlayList}" var="lotteryPlay" varStatus="cont">
	            			<c:if test="${!fn:contains(plays, lotteryPlay.code)}">
		            			<tr>
									<td><input name="myCheckbox" type="checkbox" id="check${lotteryPlay.code}" value="${lotteryPlay.code}"></td>
			                        <td align="center">
										${lotteryPlay.name}						
									</td>
									<td>
			            				<c:forEach var="pollBean" items="${lotteryPlay.lotteryPollList}">
			            					<input name="pollCheckbox${lotteryPlay.code}" type="checkbox" id="check${pollBean.code}" value="${pollBean.code}">${pollBean.name}
							            </c:forEach>
									</td>
			                    </tr>
							</c:if>
	            		</c:forEach>
	            	</c:if>
                </table>
            </form>
        </td>
    </tr>
</table>
</body>
</html>