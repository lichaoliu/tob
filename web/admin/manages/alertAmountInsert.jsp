<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>增加</title>
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
            <td class="title" style="text-align:left;">
                                彩票管理 >> 警报额度 >> 维护警报额度
              </td>
        </tr>
        <tr>
         <td>
            <form name="form1" method="post" action="/manages/adminManages?action=addAlertAmount">
            <input type="hidden" name="type" id="type" value="${type}"/>
            <input type="hidden" name="postCode1" id="postCode1" value="${postCode}"/>
            <input type="hidden" name="postName1" id="postName1" value="${postName}"/>
            
            <table width="50%" border="1" cellpadding="4" cellspacing="0" class="seach" align="center">
                <tr>
                    <td align="center">出票口编号:</td>
                    <td align="center"><input type="text" name="postCode" value="${postCode}"  <c:if test="${type == 'edit' }">disabled="disabled"</c:if> class="input"/></td>
                </tr>
                <tr>
                    <td align="center">出票口名称:</td>
                    <td align="center"><input type="text" name="postName" value="${postName}" <c:if test="${type == 'edit' }">disabled="disabled"</c:if> class="input"/></td>
                </tr>
                <tr>
                    <td align="center">警报金额:</td>
                    <td align="center"><input type="text" name="alertAmount" value="${alertAmount}" class="input"/></td>
                </tr>
                <tr>
                    <td align="center">状态:</td>
                    <td align="center">
                    <select name="status">
                                <option value="1" <c:if test="${status == 1}">selected</c:if>>有效</option>
                                <option value="0" <c:if test="${status == 0}">selected</c:if>>无效</option>
                            </select></td>
                </tr>
                <tr>
                    <td align="center">支持彩种:</td>
                    <td align="center"><input type="text" name="lotteryCode" value="${lotteryCode}" class="input"/></td>
                </tr>
                <tr>
                    <td align="center" colspan="2">
                            <input type="button" class="submit" value="提交" id="calAwardBtn" onclick="submitFunc();"
                                   style="width: 100px; border: none"/>
                            <input type="button" class="submit" value="取消" style="width: 64px; border: none"
                                   onclick="closeWindowFunc();"/>
                    </td>
                </tr>
            </table>
            </form>
            </td>
        </tr>
    </table>
  </body>
</html>


<script>

function submitFunc(){
    document.form1.submit();  
    //top.close();
}

function closeWindowFunc(){
    //javascript:history.go(-1);
	window.returnValue="undefined";
	top.close();
}

</script>
