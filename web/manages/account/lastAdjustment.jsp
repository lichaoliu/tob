<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>额度调整</title>
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
    <style type="text/css">
        body {
            margin: 0px;
            padding: 0px;
            font-size: 12px;
        }

        .co1 td {
            border-bottom: 1px dotted #C0D8F1;
            text-align: left;
            padding: 8px 8px 8px 15px;
            font-size: 12px;
            color: #666666;
            background: url(/img/dian.gif) no-repeat 5px center;
        }

        .co1 .ttt {
            border-bottom: 1px solid #C0D8F1;
            color: #0070c0;
            font-size: 14px;
            font-weight: bold;
        }

        .t1 {
            color: #0070c0;
        }

        .t2 {
            color: #ff0000;
        }

        .t3 {
            color: #0070c0;
            font-size: 14px;
            font-weight: bold;
            border-bottom: 1px solid #C0D8F1;
        }
    </style>

</head>

<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
    <tr>
        <td class="title" style="text-align:left;">
            账务管理 >> 额度调整
        </td>
    </tr>
    <tr>
        <td style="padding: 4px;">
            <form name="form1" method="post"
                  action="${pageContext.request.contextPath}/manages/accountManagesServlet?action=getAccountBySid">
                <table width="100%" border="0" cellpadding="4" cellspacing="0" class="seach">
                    <tr>
                        <td>接入商名称</td>
                        <td>
                            <select name="sid" id="sid">
                                <option value="">请选择</option>
                                <c:forEach items="${myf:getMemberList()}" var="member">
                                    <option value="${member.sid}">${member.name}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" align="left"><input type="submit" name="Submit" class="submit" value="选择帐户"
                                                            style="width: 64px; border: none"
                                                            onclick="return panNull()"/>
                    </tr>
                </table>
            </form>
        </td>
    </tr>
</table>
</body>
</html>
