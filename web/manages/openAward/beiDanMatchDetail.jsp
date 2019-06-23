<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>北京单场场次详情</title>
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

        function isNotNull(str) {
            if (str == null || str == "") {
                return false;
            } else {
                return true;
            }
        }
        function setText(objName, str) {
            document.getElementById(objName).innerHTML = str;
        }
        $(document).ready(function() {
            $("#subBtn").click(function() {
                var bln = confirm("\n确认保存需要信息?");
                if (bln) {
                    $("#awardForm").submit();
                } else {
//                    history.go(-1);
                    return;
                }
            });

            $("#calc").click(function() {
                var main = parseInt($("#mainTeamScore").val());
                var guest = parseInt($("#guestTeamScore").val());
                var mainH = parseInt($("#mainTeamHalfScore").val());
                var guestH = parseInt($("#guestTeamHalfScore").val());
                var letBall = parseInt($("#letBall").val());
                if (isNaN(main) || isNaN(guest) || isNaN(mainH) || isNaN(guestH)) {
                    alert('比分不全或比分填写错误');
                    return;
                }
                if (isNaN(letBall)) {
                    letBall = 0;
                }

                if ((main + letBall) > guest) {
                    setText("spfSpan", "胜");
                } else if ((main + letBall) < guest) {
                    setText("spfSpan", "负");
                } else {
                    setText("spfSpan", "平");
                }

                var sxdsTemp = "";
                if (main + guest >= 3) {
                    sxdsTemp += "上";
                } else {
                    sxdsTemp += "下";
                }
                if ((main + guest) % 2 == 1) {
                    sxdsTemp += "单";
                } else {
                    sxdsTemp += "双";
                }
                setText("sxdsSpan", sxdsTemp);

                if (main + guest >= 7) {
                    setText("zjqSpan", "7+");
                } else {
                    setText("zjqSpan", (main + guest));
                }

                var bqcTemp = "";
                if (mainH > guestH) {
                    bqcTemp += "胜";
                } else if (mainH < guestH) {
                    bqcTemp += "负";
                } else {
                    bqcTemp += "平";
                }
                if (main > guest) {
                    bqcTemp += "胜";
                } else if (main < guest) {
                    bqcTemp += "负";
                } else {
                    bqcTemp += "平";
                }
                setText("bqcSpan", bqcTemp);

                var bfTemp = "";
                if (main + guest > 6 || main > 4 || guest > 4) {
                    if (main > guest) {
                        bfTemp = "胜其它";
                    }
                    if (main < guest) {
                        bfTemp = "负其它";
                    }
                    if (main == guest) {
                        bfTemp = "平其它";
                    }
                } else {
                    bfTemp = main + "" + guest;
                }
                setText("bfSpan", bfTemp);


            });
        });
    </script>
</head>

<body>
<form id="awardForm" method="post"
      action="${pageContext.request.contextPath}/manages/openAward?action=doEditBeiDan">
    <input type="hidden" name="id" value="${beiDan.id}"/>
    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="table table1">
        <tr>
            <td class="title">
                <%--北京单场赛事${beiDan.issue}${beiDan.week}${beiDan.sn}赛事详情--%>
                ${beiDan.mainTeam} VS ${beiDan.guestTeam} 北京单场对阵详情
            </td>
        </tr>
        <tr>
            <td style="padding: 4px;">
                <table width="100%" border="0" cellpadding="4" cellspacing="0" class="seach_none">
                    <tr>
                        <td align="right" class="bold">比赛编号:</td>
                        <td>
                            ${beiDan.issue}
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">比赛序号:</td>
                        <td>${beiDan.week}${beiDan.sn}</td>
                    </tr>
                    <%--<tr>--%>
                    <%--<td align="right" class="bold">对阵状态:</td>--%>
                    <%--<td>--%>
                    <%--<c:if test="${beiDan.endOperator == 0}">--%>
                    <%--销售中--%>
                    <%--[<a href="${pageContext.request.contextPath}/manages/openAward?action=cancelOrRecoveryMatch&type=cancel&lotteryCode=400&issue=${beiDan.issue}&sn=${beiDan.sn}&id=${beiDan.id}">取消本场比赛</a>]--%>
                    <%--[<a href="${pageContext.request.contextPath}/manages/openAward?action=cancelOrRecoveryMatch&type=end&lotteryCode=400&issue=${beiDan.issue}&sn=${beiDan.sn}&id=${beiDan.id}">设置比赛结束</a>]--%>
                    <%--</c:if>--%>
                    <%--<c:if test="${beiDan.endOperator == 1}">--%>
                    <%--比赛结束--%>
                    <%--[<a href="${pageContext.request.contextPath}/manages/openAward?action=cancelOrRecoveryMatch&type=cancel&lotteryCode=400&issue=${beiDan.issue}&sn=${beiDan.sn}&id=${beiDan.id}">取消本场比赛</a>]--%>
                    <%--[<a href="${pageContext.request.contextPath}/manages/openAward?action=cancelOrRecoveryMatch&type=recovery&lotteryCode=400&issue=${beiDan.issue}&sn=${beiDan.sn}&id=${beiDan.id}">设置为销售</a>]--%>
                    <%--</c:if>--%>
                    <%--<c:if test="${beiDan.endOperator == 2}">--%>
                    <%--已取消--%>
                    <%--[<a href="${pageContext.request.contextPath}/manages/openAward?action=cancelOrRecoveryMatch&type=recovery&lotteryCode=400&issue=${beiDan.issue}&sn=${beiDan.sn}&id=${beiDan.id}">设置为销售</a>]--%>
                    <%--[<a href="${pageContext.request.contextPath}/manages/openAward?action=cancelOrRecoveryMatch&type=end&lotteryCode=400&issue=${beiDan.issue}&sn=${beiDan.sn}&id=${beiDan.id}">设置比赛结束</a>]--%>
                    <%--</c:if>--%>
                    <%--</td>--%>
                    <%--</tr>--%>
                    <tr>
                        <td align="right" class="bold">主队:</td>
                        <td>
                            <input style="width: 85px;" type="text" name="mainTeam" value="${beiDan.mainTeam}"
                                   class="input">
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">客队:</td>
                        <td>
                            <input style="width: 85px;" type="text" name="guestTeam" value="${beiDan.guestTeam}"
                                   class="input">
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">联赛:</td>
                        <td>
                            <input style="width: 85px;" type="text" name="matchName" value="${beiDan.matchName}"
                                   class="input">
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">&nbsp;</td>
                        <td>
                            &nbsp;
                        </td>
                    </tr>
                    <%--<tr>--%>
                    <%--<td align="right" class="bold">是否不自动更新:</td>--%>
                    <%--<td>--%>
                    <%--<input type="checkbox" name="isFlush" ${beiDan.backup1=="1" ? "checked" : ""}/>--%>
                    <%--</td>--%>
                    <%--</tr>--%>
                    <tr>
                        <td align="right" class="bold">比赛时间:</td>
                        <td>
                            <%--<fmt:formatDate value="${beiDan.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>--%>
                            <input style="width:140px;" type="text" name="endTime" value="${beiDan.endTime}"
                                   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:30%"
                                   readonly="readonly" disabled="disabled">
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">让球数:</td>
                        <td>
                            <input style="width: 85px;" type="text" id="letBall" name="letBall"
                                   value="${beiDan.letBall}" class="input" disabled="disabled">
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">对阵状态:</td>
                        <td>
                            <%--<select name="endOperator">--%>
                            <%--<option value="3" <c:if test="${'3' == beiDan.endOperator}">selected</c:if>>预售</option>--%>
                            <%--<option value="0" <c:if test="${'0' == beiDan.endOperator}">selected</c:if>>销售中</option>--%>
                            <%--<option value="1" <c:if test="${'1' == beiDan.endOperator}">selected</c:if>>期结</option>--%>
                            <%--<option value="2" <c:if test="${'2' == beiDan.endOperator}">selected</c:if>>已取消</option>--%>
                            <%--</select>--%>
                            <c:if test="${'3' == beiDan.endOperator}">预售</c:if>
                            <c:if test="${'0' == beiDan.endOperator}">销售中</c:if>
                            <c:if test="${'1' == beiDan.endOperator}">期结</c:if>
                            <c:if test="${'2' == beiDan.endOperator}">已取消</c:if>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">全场比分:</td>
                        <td>
                            <%--<c:if test="${beiDan.mainTeamScore != null}">${beiDan.mainTeamScore} : ${beiDan.guestTeamScore}</c:if>--%>
                            <input style="width: 40px;" type="text" id="mainTeamScore" name="mainTeamScore"
                                   value="${beiDan.mainTeamScore}" class="input">:
                            <input style="width: 40px;" type="text" id="guestTeamScore" name="guestTeamScore"
                                   value="${beiDan.guestTeamScore}" class="input">
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">半场比分:</td>
                        <td>
                            <%--<c:if test="${beiDan.mainTeamHalfScore != null}">${beiDan.mainTeamHalfScore} : ${beiDan.guestTeamHalfScore}</c:if>--%>
                            <input style="width: 40px;" type="text" id="mainTeamHalfScore" name="mainTeamHalfScore"
                                   value="${beiDan.mainTeamHalfScore}" class="input">:
                            <input style="width: 40px;" type="text" id="guestTeamHalfScore" name="guestTeamHalfScore"
                                   value="${beiDan.guestTeamHalfScore}" class="input">
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">&nbsp;</td>
                        <td align="left">[<a id="calc" href="#">点此处由比分生成部分赛果信息</a>]</td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">&nbsp;</td>
                        <td>
                            &nbsp;
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">赛果SP:</td>
                        <td>
                            <!-- 胜平负 -->
                            胜平负:<span id="spfSpan" name="spfSpan">${spfResult}</span>;SP
                            <!-- 浮动奖金 -->
                            <%--<c:if test="${requestScope.spfFloatAmount != null}">SP:<fmt:formatNumber--%>
                            <%--value="${requestScope.spfFloatAmount}" pattern="0.00"/></c:if>--%>
                            <input style="width: 85px;" type="text" name="winOrNegaSp" value="${beiDan.winOrNegaSp}"
                                   class="input">
                            <br/>
                            上下单双:<span id="sxdsSpan" name="sxdsSpan">${sxdsResult}</span>;SP
                            <!-- 浮动奖金 -->
                            <%--<c:if test="${requestScope.sxdsFloatAmount != null}">SP:<fmt:formatNumber--%>
                            <%--value="${requestScope.sxdsFloatAmount}" pattern="0.00"/></c:if>--%>
                            <input style="width: 85px;" type="text" name="shangXiaPanSp" value="${beiDan.shangXiaPanSp}"
                                   class="input">
                            <br/>
                            <!-- 总进球数 -->
                            总进球数:<span id="zjqSpan" name="zjqSpan">${zjqsResult}</span>;SP
                            <!-- 浮动奖金 -->
                            <%--<c:if test="${requestScope.zjqsFloatAmount != null}">SP:<fmt:formatNumber--%>
                            <%--value="${requestScope.zjqsFloatAmount}" pattern="0.00"/></c:if>--%>
                            <input style="width: 85px;" type="text" name="totalGoalSp" value="${beiDan.totalGoalSp}"
                                   class="input">
                            <br/>
                            <!-- 半全场胜平负 -->
                            半全场胜平负:<span id="bqcSpan" name="bqcSpan">${bqcspfResult}</span>;SP
                            <!-- 浮动奖金 -->
                            <%--<c:if test="${requestScope.bqcspfFloatAmount != null}">SP:<fmt:formatNumber--%>
                            <%--value="${requestScope.bqcspfFloatAmount}" pattern="0.00"/></c:if>--%>
                            <input style="width: 85px;" type="text" name="halfCourtSp" value="${beiDan.halfCourtSp}"
                                   class="input">
                            <br/>
                            <!-- 比分 -->
                            比分:<span id="bfSpan" name="bfSpan">${bfResult}</span>;SP
                            <!-- 浮动奖金 -->
                            <%--<c:if test="${requestScope.bfFloatAmount != null}">SP:<fmt:formatNumber--%>
                            <%--value="${requestScope.bfFloatAmount}" pattern="0.00"/></c:if>--%>
                            <input style="width: 85px;" type="text" name="scoreSp" value="${beiDan.scoreSp}"
                                   class="input">
                        </td>
                    </tr>
                    <tr>
                        <%--<td colspan="2" align="center">--%>
                        <%--<input type="button" class="submit" value="关闭" style="width: 64px; border: none"--%>
                        <%--onclick="javascript:window.close();"/>--%>
                        <%--</td>--%>
                        <td colspan="2" align="center">
                            <input type="button" class="submit" value="保存" id="subBtn"
                                   style="width: 100px; border: none"/>

                            <input type="button" class="submit" value="取消" style="width: 64px; border: none"
                                   onclick="javascript:history.go(-1);"/>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</form>
</div>
</body>
</html>