<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>编辑开奖信息</title>
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
        function onOpen(url) {
            window.open(url);
        }
    </script>
</head>

<body>
<table width="900" border="0" align="center" cellpadding="0" cellspacing="0" class="table table1">
    <tr>
        <td class="title">
            开奖管理 >> 开奖信息录入 >> 修改开奖信息
        </td>
    </tr>
    <tr>
        <td style="padding: 4px;">
            <form name="form1" id="form1" method="post"
                  action="${pageContext.request.contextPath}/manages/cooperationManagesServlet?action=addCooperation">
                <input type="hidden" value="${issue.name}" name="name"/>
                <table width="100%" border="0" cellpadding="4" cellspacing="0" class="seach_none">
                    <tr>
                        <td colspan="2" align="center">足球胜负彩第${issue.name}期开奖信息录入</td>
                    </tr>
                    <tr>
                        <td width="45%" align="right">
                            彩种：
                        </td>
                        <td>
                            足球胜负彩
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            彩期：
                        </td>
                        <td>
                            ${issue.name}期
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            开售时间：
                        </td>
                        <td>
                            ${issue.startTime}
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            止售时间：
                        </td>
                        <td>
                            ${issue.endTime}
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            最近开奖
                        </td>
                        <td>${lastIssue.name}期 开奖号码${lastIssue.bonusNumber}
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <br/><b>1.比赛结果（胜3，平1，负0）</b>
                        </td>
                    </tr>
                    <c:forEach var="subGame" items="${subGameList}" varStatus="cont">
                        <tr>
                            <td width="20%" align="right">
                                    ${cont.count}.${subGame.masterName}VS${subGame.guestName} =
                            </td>
                            <td><input type="text" value="${subGame.result}" name="result" size="10"/>
                            </td>
                        </tr>
                    </c:forEach>

                    <tr>
                        <td colspan="2">
                            <br/><b>2.奖金等级</b>
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            一等奖金
                        </td>
                        <td><input type="text" value="${lv01}" name="lv01"
                                   size="22"/>元
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            二等奖金
                        </td>
                        <td><input type="text" value="${lv02}" name="lv02" size="22"/>元
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <br/><b>3.奖池和中奖情况</b>
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            奖池滚存：
                        </td>
                        <td><input type="text" value="${issue.prizePool}" name="prizePool"
                                   size="22"/>元
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            销售总额：
                        </td>
                        <td><input type="text" value="${issue.saleTotal}" name="saleTotal" size="22"/>元
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            一等奖中奖人数：
                        </td>
                        <td>
                            <input type="text" value="${lv01men}" name="lv01men" size="22"/>注
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            二等奖中奖人数：
                        </td>
                        <td>
                            <input type="text" value="${lv02men}" name="lv02men" size="22"/>注
                        </td>
                    </tr>

                    <tr>
                        <td colspan="2" align="center">
                            <input type="button" id="subBtn" value="确定" class="submit"
                                   style="width: 64px; border: none">
                            <input type="button" name="Submit" value="返回" class="submit"
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