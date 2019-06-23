<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>调整额度详情</title>
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
<table width="900" border="0" align="center" cellpadding="0" cellspacing="0" class="table table1">
    <tr>
        <td class="title">
            调整额度详情
        </td>
    </tr>
    <tr>
        <td style="padding: 4px;">
            <table width="100%" border="0" cellpadding="4" cellspacing="0" class="seach_none">
                <td width="20%" align="right" class="bold">
                    接入商ID:
                </td>
                <td>${list[1].sid}</td>
                </tr>
                <tr>
                    <td width="20%" align="right" class="bold">
                        接入商名称:
                    </td>
                    <td>${list[1].name}</td>
                </tr>
                <tr>
                    <td width="20%" align="right" class="bold">
                        交易编码:
                    </td>
                    <td>${list[0].adjustId }</td>
                </tr>
                <tr>
                    <td width="20%" align="right" class="bold">
                        调整奖金:
                    </td>
                    <td>
                        <fmt:formatNumber value="${list[0].bonusAmount }" pattern="0.00"/>
                    </td>
                </tr>
                <tr>
                    <td width="20%" align="right" class="bold">
                        调整充值金:
                    </td>
                    <td><fmt:formatNumber value="${list[0].rechargeAmount }" pattern="0.00"/></td>
                </tr>
                <tr>
                    <td width="20%" align="right" class="bold">
                        调整赠金:
                    </td>
                    <td><fmt:formatNumber value="${list[0].presentAmount }" pattern="0.00"/></td>
                </tr>
                <tr>
                    <td width="20%" align="right" class="bold">
                        调整冻结金:
                    </td>
                    <td><fmt:formatNumber value="${list[0].freezeAmount }" pattern="0.00"/></td>
                </tr>
                <tr>
                    <td width="20%" align="right" class="bold">
                        调整时间:
                    </td>
                    <td><fmt:formatDate value="${list[0].createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                </tr>
                <tr>
                    <td width="10%" align="right" class="bold">
                        操作人:
                    </td>
                    <td>${list[0].operator }</td>
                </tr>
                <tr>
                    <td width="20%" align="right" class="bold">
                        说明:
                    </td>
                    <td>${list[0].body }</td>
                </tr>
                <tr align="center">
                    <td colspan="2"><input type="button" value="关闭" class="submit" style="width: 64px; border: none"
                                           onclick="javascript:window.close();"/></td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</body>
</html>