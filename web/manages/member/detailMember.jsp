<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>编辑接入商信息</title>
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
<table width="900" border="0" align="center" cellpadding="0" cellspacing="0" class="table table1">
    <tr>
        <td class="title" style="text-align:left;">
            接入商详情
        </td>
    </tr>
    <tr>
        <td style="padding: 4px;">
            <form action="${pageContext.request.contextPath}/manages/memberManagesServlet?action=editMember"
                  method="post">
                <table width="100%" border="0" cellpadding="4" cellspacing="0" class="seach_none">
                    <tr>
                        <td width="45%" align="right">
                            代理编号:
                        </td>
                        <td>
                            ${member.sid}
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            代理名称:
                        </td>
                        <td>
                            ${member.name}
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            公司名称:
                        </td>
                        <td>
                            ${member.companyName}
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            联系人:
                        </td>
                        <td>
                            ${member.contactPerson}
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            身份证号码:
                        </td>
                        <td>
                            ${member.cardCode}
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            联系手机:
                        </td>
                        <td>
                            ${member.mobile}
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            邮箱:
                        </td>
                        <td>
                            ${member.email}
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            公司地址:
                        </td>
                        <td>
                            ${member.companyAddress}
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            加密key:
                        </td>
                        <td>${member.privateKey}</td>
                    </tr>
                    <tr>
                        <td align="right">
                            投注总额:
                        </td>
                        <td><fmt:formatNumber value="${account.payTotal}" pattern="0.00"/></td>
                    </tr>
                    <tr>
                        <td align="right">
                            中奖总额:
                        </td>
                        <td><fmt:formatNumber value="${account.bonusTotal}" pattern="0.00"/></td>
                    </tr>
                    <tr>
                        <td align="right">
                            账户可用额度:
                        </td>
                        <td>
                        <c:if test="${(account.rechargeAmount + account.bonusAmount) >= 10000}"><font color="blue"></c:if>
                        <c:if test="${(account.rechargeAmount + account.bonusAmount) < 10000}"><font color="red"></c:if>
                            <fmt:formatNumber value="${account.rechargeAmount + account.bonusAmount}" pattern="0.00"/>
                            </font>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            接入时间:
                        </td>
                        <td>
                            <fmt:formatDate value="${member.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            状态:
                        </td>
                        <td>
                            ${member.status == 1 ? "开通" : "锁定"}
                        </td>
                    </tr>
                    <tr>
                        <td align="center" colspan="2">
                            <input type="button" name="Submit" value="关闭" class="submit"
                                   style="width: 64px; border: none"
                                   onclick="javascript:window.close();">
                        </td>
                    </tr>
                </table>
            </form>
        </td>
    </tr>
</table>
</body>
</html>