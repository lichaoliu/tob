<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../jstl.jsp" %>
<head>
    <title>首页</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="Cache-Control" content="max-age=0" forua="true"/>
    <meta http-equiv="Cache-Control" content="no-cache" forua="true"/>
    <meta http-equiv="Cache-Control" content="must-revalidate" forua="true"/>
    <link rel="stylesheet" href="/style/page.css">
    <link rel="stylesheet" href="/style/manages.css">
</head>
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
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
    <tr>
        <td class="title" style="text-align:left;"></td>
    </tr>
    <tr>
        <td height="45" align="right" style="padding: 8px; font-size:14px"></td>
    </tr>
    <tr>
        <td>
            <table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td>
                        <table width="100%" border="0" align="center" cellpadding="4" cellspacing="0" class="co1">
                            欢迎${sessionScope.managesUser.userName}!
                            您上次登录时间是<fmt:formatDate value="${sessionScope.loginTime}" pattern="yyyy年MM月dd日HH时mm分ss秒"/>
                        </table>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</body>
</html>