<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>系统配置-->修改</title>
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
<table width="100%" border="0" cellpadding="0" cellspacing="0"
       class="table">
    <tr>
        <td class="title">修改期次</td>
    </tr>
    <tr>

        <td style="padding: 4px;">
            <form name="form1" method="post"
                  action="${pageContext.request.contextPath}/manages/issueManagesServlet?action=updateSubGame&gameType=xg"
                  onsubmit="return Validator.Validate(this,3)">
                <table width="100%" border="1" cellpadding="4" cellspacing="0"
                       bordercolor="#bbbbbb" bgcolor="#F1F1F1" class="mainTab"
                       style="margin-top: 5px;">
                    <span align="center"><span style="color:#0033cc;font-weight: bold;">[${success}</font></span>
                    <tr>
                        <input type="hidden" name="id" value="${subGame.id }"/>
                        <input type="hidden" name="issue" value="${subGame.issue }"/>
                        <td width="30%" align="right">
                            类型：
                        </td>
                        <td style="color: #f00">
                            <select name="lotteryCode">
                                <option value="300" <c:if test="${subGame.lotteryCode=='300'}">selected</c:if>> 胜负彩
                                </option>
                                <option value="301"
                                        <c:if test="${subGame.lotteryCode=='301'}">selected</c:if> > 半全场
                                </option>
                                <option value="301"
                                        <c:if test="${subGame.lotteryCode=='302'}">selected</c:if> > 进球彩
                                </option>
                            </select>
                        </td>
                    </tr>
                    <tr>

                        <td width="30%" align="right">
                            比赛类型：
                        </td>
                        <td>
                            <input type="text" name="leageName" value="${subGame.leageName }" class="input"
                                   style="width:50%">
                        </td>
                    </tr>


                    <tr>
                        <td width="30%" align="right">
                            客场：
                        </td>
                        <td>
                            <input type="text" name="guestName" value="${subGame.guestName}" class="input"
                                   style="width:50%">
                        </td>
                    </tr>
                    <tr>

                        <td width="30%" align="right">
                            主场：
                        </td>
                        <td>
                            <input type="text" name="hostName" value="${subGame.masterName}" class="input"
                                   style="width:50%">
                        </td>
                    </tr>


                    <tr>
                        <td width="30%" align="right">
                            开始时间：
                        </td>
                        <td>

                            <input type="text" name="startTime"
                                   value="<fmt:formatDate value="${subGame.startTime}" pattern="yyyy-MM-dd HH:mm:ss" />"
                                   class="input" style="width:50%">
                        </td>
                    </tr>

                    <tr>
                        <td width="30%" align="right">
                            结束时间：
                        </td>
                        <td>

                            <input type="text" name="endTime"
                                   value="<fmt:formatDate value="${subGame.endTime}" pattern="yyyy-MM-dd HH:mm:ss" />"
                                   class="input" style="width:50%">
                        </td>
                    </tr>

                    <tr>

                        <td width="30%" align="right">
                            赛果：
                        </td>
                        <td>
                            <input type="text" name="result" value="${subGame.result}" class="input" style="width:50%">
                        </td>
                    </tr>

                    <tr>

                        <td width="30%" align="right">
                            双方比分：
                        </td>
                        <td>
                            <input type="text" name="resultDes" value="${subGame.resultDes}" class="input"
                                   style="width:50%">
                        </td>
                    </tr>

                    <tr>

                        <td width="30%" align="right">
                            上半场比分：
                        </td>
                        <td>
                            <input type="text" name="scoreAtHalf" value="${subGame.scoreAtHalf}" class="input"
                                   style="width:50%">
                        </td>
                    </tr>

                    <tr>

                        <td width="30%" align="right">
                            下半场比分：
                        </td>
                        <td>
                            <input type="text" name="secondHalfTheScore" value="${subGame.secondHalfTheScore}"
                                   class="input" style="width:50%">
                        </td>
                    </tr>

                    <tr>

                        <td width="30%" align="right">
                            最终比分：
                        </td>
                        <td>
                            <input type="text" name="finalScore" value="${subGame.finalScore}" class="input"
                                   style="width:50%">
                        </td>
                    </tr>


                    <tr>
                        <td colspan="2" align="center">
                            <input type="submit" name="button" id="button" value="提交">
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                            <span style="color:#0033cc;font-weight: bold;">[${msg}</font>
                        </td>
                    </tr>
                </table>
            </form>
        </td>
    </tr>
</table>
${errorMsg}
</body>
</html>