<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>场次修改</title>
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
    <link href="${pageContext.request.contextPath}/css/kjgg.css" rel="stylesheet" type="text/css"/>
    <style type="text/css">
        body, td, th {
            font-family: Arial, "Times New Roman", "宋体";
        }
    </style>
    <script type="text/javascript">
        function windowClose() {
            window.close();
        }
        function matchDo(type) {
            if (confirm('确定要执行此修改?')) {
                $("#" + type + "").submit();
            } else {
                history.go(-1);
            }
        }
    </script>
</head>

<body>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="table table1">
<tr>
    <td class="title" style="text-align:left;">
        彩期管理 >> ${subIssue.lotteryCode == 200 ? "竞彩足球" : (subIssue.lotteryCode == 201 ? "竞彩篮球" : "北京单场")}查询 >> 对阵修改
    </td>
</tr>
<tr>
    <td style="padding: 4px;">
        <table width="100%" border="0" cellpadding="4" cellspacing="0" class="seach_none">
            <tr>
                <td colspan="2" align="center">
                    <b>${subIssue.mainTeam} VS ${subIssue.guestTeam}</b><br/><br/>
                </td>
            </tr>
            <tr>
                <td width="50%" align="right" class="bold">${subIssue.lotteryCode == "400" ? "彩期" : "日期"}:</td>
                <td>
                    ${subIssue.issue}
                </td>
            </tr>
            <tr>
                <td align="right" class="bold">${subIssue.lotteryCode == "400" ? "场次" : "编号"}:</td>
                <td>${subIssue.week}${subIssue.sn}</td>
            </tr>
            <tr>
                <td align="right" class="bold">主客队:</td>
                <td>
                    ${subIssue.mainTeam} VS ${subIssue.guestTeam}
                </td>
            </tr>
            <tr>
                <td align="right" class="bold">联赛:</td>
                <td>
                    ${subIssue.matchName}
                </td>
            </tr>
            <tr>
                <td align="right" class="bold">止售时间:</td>
                <td>
                    <fmt:formatDate value="${subIssue.endFuShiTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
            </tr>
            <tr>
                <td align="right" class="bold">${subIssue.lotteryCode == "201" ? "让分" : "让球"}:</td>
                <td>
                    ${subIssue.letBall}
                </td>
            </tr>
            <tr>
                <td align="right" class="bold">状态:</td>
                <td>
                    <c:if test="${subIssue.endOperator == 0}">销售中</c:if>
                    <c:if test="${subIssue.endOperator == 1}">期结</c:if>
                    <c:if test="${subIssue.endOperator == 2}">已取消</c:if>
                    ,${myf:getOperatorsAward(subIssue.operatorsAward)}
                </td>
            </tr>
            <tr>
                <td align="right" class="bold"><br/></td>
                <td>
                    <br/>
                </td>
            </tr>

            <c:if test="${showType == null || '' == showType}">
                <tr>
                    <td align="right" class="bold">设置开赛时间:</td>
                    <td>
                        <fmt:formatDate value="${subIssue.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        &nbsp;&nbsp;<a
                            href="${pageContext.request.contextPath}/manages/issueManagesServlet?action=issueMatchEdit&lotteryCode=${subIssue.lotteryCode}&id=${subIssue.id}&showType=matchTime">修改</a>
                    </td>
                </tr>
                <tr>
                    <td align="right" class="bold">设置更新方式:</td>
                    <td>
                            ${subIssue.backup1 == '1' ? '不自动更新':'自动更新'}
                        &nbsp;&nbsp;<a
                            href="${pageContext.request.contextPath}/manages/issueManagesServlet?action=issueMatchEdit&lotteryCode=${subIssue.lotteryCode}&id=${subIssue.id}&showType=autoFlush">修改</a>
                    </td>
                </tr>
                <tr>
                    <td align="right" class="bold">是否取消本场比赛:</td>
                    <td>
                            ${subIssue.endOperator == 2 ? '已取消':'未取消'}
                            <%--&nbsp;&nbsp;<c:if test="${subIssue.endOperator != 2}"><a href="${pageContext.request.contextPath}/manages/issueManagesServlet?action=cancelOrRecoveryMatch&type=cancel&lotteryCode=${subIssue.lotteryCode}&issue=${subIssue.issue}&sn=${subIssue.sn}&id=${subIssue.id}">取消对阵</a></c:if>--%>
                        &nbsp;&nbsp;<c:if test="${subIssue.endOperator != 2}"><a
                            href="${pageContext.request.contextPath}/manages/issueManagesServlet?action=issueMatchEdit&lotteryCode=${subIssue.lotteryCode}&id=${subIssue.id}&showType=cancel">取消对阵</a></c:if>
                    </td>
                </tr>
                <tr>
                    <td align="right" class="bold">是否隐藏比赛:</td>
                    <td>
                            ${subIssue.backup2 == '1' ? '隐藏':'不隐藏'}
                        &nbsp;&nbsp;<a
                            href="${pageContext.request.contextPath}/manages/issueManagesServlet?action=issueMatchEdit&lotteryCode=${subIssue.lotteryCode}&id=${subIssue.id}&showType=hide">修改</a>
                    </td>
                </tr>
                <tr>
                    <td align="right" class="bold">赛事各玩法禁售:</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/manages/issueManagesServlet?action=matchEdit&lotteryCode=${subIssue.lotteryCode}&sn=${subIssue.sn}&issue=${subIssue.issue}">修改</a>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center"><br/>
                        <input type="button" class="submit" value="返回" style="width: 64px; border: none"
                               onclick="javascript:location.href='/manages/issueManagesServlet?action=${subIssue.lotteryCode=='200' ? 'footballMatchList':(subIssue.lotteryCode=='201' ? 'basketballMatchList':'beiDanMatchList')}';"/>
                    </td>
                </tr>
            </c:if>

            <c:if test="${showType == 'matchTime'}">
                <form id="matchTime" method="post"
                      action="${pageContext.request.contextPath}/manages/issueManagesServlet?action=issueMatchEditDo&lotteryCode=${subIssue.lotteryCode}&id=${subIssue.id}&type=matchTime">
                    <tr>
                        <td align="right" class="bold">设置开赛时间:</td>
                        <td>
                            <input style="width:140px;" type="text" name="endTime" value="${subIssue.endTime}"
                                   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:30%"
                                   readonly="readonly">
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center"><br/>
                            <input type="button" class="submit" value="确定" style="width: 64px; border: none"
                                   onclick="matchDo('matchTime');"/>
                            <input type="button" class="submit" value="取消" style="width: 64px; border: none"
                                   onclick="javascript:history.go(-1);"/>
                        </td>
                    </tr>
                </form>
            </c:if>

            <c:if test="${showType == 'cancel'}">
            <form id="cancel" method="post"
                  action="${pageContext.request.contextPath}/manages/issueManagesServlet?action=issueMatchEditDo&lotteryCode=${subIssue.lotteryCode}&id=${subIssue.id}&type=cancel">
                <tr>
                    <td colspan="2" align="center">
                        对阵取消后不能恢复，您要取消本场对阵吗？
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center"><br/>
                        <input type="button" class="submit" value="是" style="width: 64px; border: none"
                               onclick="matchDo('cancel');"/>
                        <input type="button" class="submit" value="否" style="width: 64px; border: none"
                               onclick="javascript:history.go(-1);"/>
                    </td>
                </tr>
                </c:if>

                <c:if test="${showType == 'autoFlush'}">
                <form id="autoFlush" method="post"
                      action="${pageContext.request.contextPath}/manages/issueManagesServlet?action=issueMatchEditDo&lotteryCode=${subIssue.lotteryCode}&id=${subIssue.id}&type=autoFlush">
                    <tr>
                        <td align="right" class="bold">是否自动更新对阵数据:</td>
                        <td>
                            <input name="isFlush" type="radio"
                                   value="0" ${subIssue.backup1 == 1 ? "" : "checked='checked'"}/>自动更新
                            <input name="isFlush" type="radio"
                                   value="1" ${subIssue.backup1 == 1 ? "checked='checked'" : ""}/>不自动更新
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center"><br/>
                            <input type="button" class="submit" value="确定" style="width: 64px; border: none"
                                   onclick="matchDo('autoFlush');"/>
                            <input type="button" class="submit" value="取消" style="width: 64px; border: none"
                                   onclick="javascript:history.go(-1);"/>
                        </td>
                    </tr>
                </form>
                </c:if>

                <c:if test="${showType == 'hide'}">
                <form id="hide" method="post"
                      action="${pageContext.request.contextPath}/manages/issueManagesServlet?action=issueMatchEditDo&lotteryCode=${subIssue.lotteryCode}&id=${subIssue.id}&type=hide">
                    <tr>
                        <td align="right" class="bold">是否隐藏对阵:</td>
                        <td>
                            <input name="isHide" type="radio"
                                   value="0" ${subIssue.backup2 == 1 ? "" : "checked='checked'"}/>显示
                            <input name="isHide" type="radio"
                                   value="1" ${subIssue.backup2 == 1 ? "checked='checked'" : ""}/>隐藏
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                            说明：隐藏后在售彩页面不显示该对阵
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center"><br/>
                            <input type="button" class="submit" value="确定" style="width: 64px; border: none"
                                   onclick="matchDo('hide');"/>
                            <input type="button" class="submit" value="取消" style="width: 64px; border: none"
                                   onclick="javascript:history.go(-1);"/>
                        </td>
                    </tr>
                </form>
                </c:if>

        </table>
    </td>
</tr>
</table>
</div>
</body>
</html>