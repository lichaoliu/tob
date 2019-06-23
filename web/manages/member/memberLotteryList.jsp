<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>渠道彩种</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/validator.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/page.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/page.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/manages.css">
</head>

<body>
<c:if test="${requestScope.msg != null}">
    <script type="text/javascript">
        alert('${requestScope.msg}');
        window.close();
    </script>
</c:if>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
<form name="form1" method="post" action="/manages/memberManagesServlet?action=saveLotteryId">
    <tr>
        <td class="title" style="text-align:left;">
            渠道管理 >> 代理商彩种管理
        </td>
        <td class="title" style="text-align:right">
        </td>
    </tr>
    <tr>
		<td align="left" colspan="2">
			<span style="color:#0033cc;font-weight: bold;">
				&nbsp;&nbsp;
				<input type="submit" value="修 改" onclick="flushlotteryId()" class="submit" style="width: 64px; border: none" />
				&nbsp;&nbsp;
				<!-- <input type="button" value="取 消" onclick="javascript:windowClose();" class="submit" style="width: 64px; border: none"> -->
			</span>
		</td>		
	</tr>
    <tr>
        <td colspan="2" style="padding: 4px;">
        
           <input type="hidden" name="sid" id="sid" value="${sid}">
            <table width="80%" border="1" cellpadding="4" cellspacing="0" class="content">
                <tr id="one">
                    <td align="center" width="5%"><input name="allCheckbox" type="checkbox" id="allCheckbox" onclick="checkNewCode('myCheckbox')"/></td>
                    <td>彩种编码</td>
                    <td>彩种名称</td>
                </tr>
                <c:if test="${lstLottery != null}">
                    <c:forEach var="weight" items="${lstLottery}" varStatus="cont">
                        <tr>
                            <td align="center"><input name="myCheckbox" type="checkbox" id="check${cont.count}" value="${weight.lotteryCode}" ${weight.defaultPostCode == 0 ? "" : "checked='checked'"}   ></td>
	                    	<td align="center">${weight.lotteryCode}</td>
	                        <td align="center">${weight.name}</td>
                        </tr>
                    </c:forEach>
                </c:if>
            </table>
            
        </td>
    </tr>
    </form>
</table>
<script>
    function ifLink(str) {
        var bln = confirm(str);
        return bln;
    }
    function flushlotteryId() {
        var j = 0;
        var myc = document.getElementsByName("myCheckbox");
        for (i = 0; i < myc.length; i++) {
            if (myc[i].checked) {
               j++;
            }
         }
         if (confirm("确定要修改代理商彩种？")) {
             document.form1.action = "/manages/memberManagesServlet?action=saveLotteryId";
             document.form1.submit();
             window.close();
         }
    }
    function checkNewCode(checkName) {
        var allCheckbox = document.getElementById("allCheckbox");
        var field = document.getElementsByName(checkName);
        if (allCheckbox.checked == true) {
            for (i = 0; i < field.length; i++) {
                field[i].checked = true;
            }
        } else {
            for (i = 0; i < field.length; i++) {
                field[i].checked = false;
            }
        }
    }
</script>
</body>
</html>
