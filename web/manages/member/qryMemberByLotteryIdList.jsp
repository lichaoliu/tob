<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>联盟列表</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="Cache-Control" content="max-age=0" forua="true"/>
    <meta http-equiv="Cache-Control" content="no-cache" forua="true"/>
    <meta http-equiv="Cache-Control" content="must-revalidate" forua="true"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/validator.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/page.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/tool.jquery.cookie.js"></script>
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
    <tr>
        <td class="title" style="text-align:left;">
            彩期管理  >> 彩期控制 >> 彩种和代理商
        </td>
        <td class="title" style="text-align:right">
        </td>
    </tr>
    <tr>
        <td colspan="2" style="padding: 4px;">
            <table width="100%" border="1" cellpadding="4" cellspacing="0" class="content">
                <c:if test="${lstMember!=null}">
                    <tr>
                        <td align="left" colspan="4">
                            <span style="color:#0033cc;font-weight: bold;">[本次查询结果,共${lstMemberSize}个代理商可以出&lt;&lt;${myf:getLotteryChinaName(lotteryCode)}&gt;&gt;]</span>
                        </td>
                    </tr>
                </c:if>
                <tr>
                    <td align="left"><input name="allCheckbox" type="checkbox" id="allCheckbox" onclick="checkNewCode('myCheckbox')"  title="全选/全不选" /></td>
                    <td align="left" colspan="3">
                    <span style="color:#0033cc;font-weight: bold;">&nbsp;&nbsp;
				         <input type="button" value="更 新" onclick="return flushlotteryId()" class="submit" style="width: 64px; border: none" />
			        </span>
                    </td>
                </tr>
                <c:if test="${lstMember==null}">
                    <tr>
                        <td colspan="4" align="center">请在上面填写筛选条件，并点击查询按钮来查询数据！</td>
                    </tr>
                </c:if>
                <form name="form1" method="post" action="/manages/memberManagesServlet?action=saveMemberByLotteryId">
                <input type="hidden" name="lotteryCode" id="lotteryCode" value="${lotteryCode}">
                <c:if test="${lstMember!=null}">
                        <tr>
                        <c:forEach var="cooperation" items="${lstMember}" varStatus="cont" step="1">
                               <td>
                               <input name="myCheckbox" type="checkbox" id="check${cont.count}" value="${cooperation.sid}" > ${cooperation.sid}|${cooperation.name} &nbsp;&nbsp;
                               </td>
                               <c:if test="${(cont.count % 4) eq '0'}"></tr></c:if>
                        </c:forEach> <tr>
                    </tr>
                </c:if>
                </form>
            </table>
        </td>
    </tr>
</table>
<script>
    function ifLink(str) {
        var bln = confirm(str);
        return bln;
    }
    function editLotteryId(sid){
        //window.showModalDialog("${pageContext.request.contextPath}/manages/memberManagesServlet?action=editLotteryId&sid="+sid,new Array(),"dialogHeight:360px;dialogWidth:640px");
        var iHeight = 350;
    	var iWidth = 300;
    	var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
    	var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
    	window.open('<%=request.getContextPath()%>/manages/memberManagesServlet?action=editLotteryId&sid='+sid,'newwindow','height="+iHeight+",width="+iWidth+",top="+iTop+",left="+iLeft+",toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
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
    
    function flushlotteryId() {
        var j = 0;
        var myc = document.getElementsByName("myCheckbox");
        for (i = 0; i < myc.length; i++) {
            if (myc[i].checked) {
               j++;
            }
         }
         if (confirm("确定去除这些代理商出该彩种？")) {
             document.form1.action = "/manages/memberManagesServlet?action=saveMemberByLotteryId";
             form1.submit();
             //window.close();
         }
    }
</script>
</body>
</html>
