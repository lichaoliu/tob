<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>竞彩足球赛果录入</title>
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

                var str = "竞彩足球第${football.issue}${football.week}${football.sn}期";

                str += "\n上半场 ${football.mainTeam}:${football.guestTeam} = " + $("#mainTeamHalfScore").val() + ":" + $("#guestTeamHalfScore").val();

                str += "\n全场 ${football.mainTeam}:${football.guestTeam} = " + $("#mainTeamScore").val() + ":" + $("#guestTeamScore").val();

                str += "\n\n最终SP值";

                str += "\n让球胜平负:" + $("#winOrNegaSp").val();

                str += "\n胜平负:" + $("#spfWinOrNegaSp").val();

                str += "\n总进球:" + $("#totalGoalSp").val();

                str += "\n半场胜平负:" + $("#halfCourtSp").val();

                str += "\n比分:" + $("#scoreSp").val();

                var bln = confirm(str + "\n是否确认保存开奖信息?");
                if (bln) {
                    $("#awardForm").submit();
                } else {
//                    history.go(-1);
                    return;
                }
            });

            $("#calc").click(function() {
                var main = $("#mainTeamScore").val();
                var guest = $("#guestTeamScore").val();
                var mainH = $("#mainTeamHalfScore").val();
                var guestH = $("#guestTeamHalfScore").val();
                if (isNotNull(main) && isNotNull(guest)) {

                    if (eval(main + "${football.letBall}") > guest) {
                        setText("rqspf", "胜");
                    } else if (eval(main + "${football.letBall}") == guest) {
                        setText("rqspf", "平");
                    } else if (eval(main + "${football.letBall}") < guest) {
                        setText("rqspf", "负");
                    } else {
                        setText("rqspf", "");
                    }

                    if (main > guest) {
                        setText("spf", "胜");
                    } else if (main == guest) {
                        setText("spf", "平");
                    } else if (main < guest) {
                        setText("spf", "负");
                    } else {
                        setText("spf", "");
                    }

                    setText("jqs", eval(main + "+" + guest));
                    var bqcstr = "";
                    if (mainH > guestH) {
                        bqcstr += "胜";
                    } else if (mainH == guestH) {
                        bqcstr += "平";
                    } else if (mainH < guestH) {
                        bqcstr += "负";
                    } else {
                        bqcstr += "";
                    }
                    bqcstr += "/";
                    if (main > guest) {
                        bqcstr += "胜";
                    } else if (main == guest) {
                        bqcstr += "平";
                    } else if (main < guest) {
                        bqcstr += "负";
                    } else {
                        bqcstr += "";
                    }
                    setText("bqc", bqcstr);
                    setText("bf", mainH + ":" + guestH + "/" + main + ":" + guest);
                }
            });
            $("#mainTeamScore").keyup(regInteger2);
            $("#guestTeamScore").keyup(regInteger2);
            $("#mainTeamHalfScore").keyup(regInteger2);
            $("#guestTeamHalfScore").keyup(regInteger2);

            $("#winOrNegaSp").keyup(regFloat);
            $("#spfWinOrNegaSp").keyup(regFloat);
            $("#totalGoalSp").keyup(regFloat);
            $("#halfCourtSp").keyup(regFloat);
            $("#scoreSp").keyup(regFloat);
        });

        function regInteger2() {
            var maxLen = 2;
            var reg = /^\d{,3}$/;
            if (!reg.test(this.value)) {
                this.value = isNaN(parseInt(this.value)) ? "" : parseInt(this.value.substring(0, maxLen));
            }
        }
        function regFloat() {
            var reg = /^\d*(\.)?\d*$/;
            if (!reg.test(this.value)) {
                this.value = isNaN(parseFloat(this.value)) ? "" : parseFloat(this.value);
            }
        }

    </script>
</head>

<body>
<form id="awardForm" method="post"
      action="${pageContext.request.contextPath}/manages/openAward?action=doEditFootball">
    <input type="hidden" name="issue" value="${football.issue}"/>
    <input type="hidden" name="sn" value="${football.sn}"/>
    <input type="hidden" name="id" value="${football.id}"/>
    <input type="hidden" name="matchNo" value="${matchNo}"/>
    <input type="hidden" name="mainTeam" value="${mainTeam}"/>
    <input type="hidden" name="guestTeam" value="${guestTeam}"/>
    <input type="hidden" name="matchName" value="${matchName}"/>
    <input type="hidden" name="operatorsAward" value="${operatorsAward}"/>
    <input type="hidden" name="page" value="${page}"/>


    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="table table1">
        <tr>
            <td class="title">
                竞彩足球赛事${football.issue}${football.week}${football.sn}赛果录入
            </td>
        </tr>
        <tr>
            <td style="padding: 4px;">
                <table width="100%" border="0" cellpadding="4" cellspacing="0" class="seach_none">
                    <tr>
                        <td align="right" width="50%" class="bold">比赛日期:</td>
                        <td>
                            ${football.issue}
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">赛事编号:</td>
                        <td>${football.week}${football.sn}</td>
                    </tr>
                    <%--<tr>--%>
                    <%--<td align="right" class="bold">对阵状态:</td>--%>
                    <%--<td>--%>
                    <%--<c:if test="${football.endOperator == 0}">--%>
                    <%--销售中--%>
                    <%--[<a href="${pageContext.request.contextPath}/manages/openAward?action=cancelOrRecoveryMatch&type=cancel&lotteryCode=200&issue=${football.issue}&sn=${football.sn}&id=${football.id}">取消本场比赛</a>]--%>
                    <%--[<a href="${pageContext.request.contextPath}/manages/openAward?action=cancelOrRecoveryMatch&type=end&lotteryCode=200&issue=${football.issue}&sn=${football.sn}&id=${football.id}">设置比赛结束</a>]--%>
                    <%--</c:if>--%>
                    <%--<c:if test="${football.endOperator == 1}">--%>
                    <%--比赛结束--%>
                    <%--[<a href="${pageContext.request.contextPath}/manages/openAward?action=cancelOrRecoveryMatch&type=cancel&lotteryCode=200&issue=${football.issue}&sn=${football.sn}&id=${football.id}">取消本场比赛</a>]--%>
                    <%--[<a href="${pageContext.request.contextPath}/manages/openAward?action=cancelOrRecoveryMatch&type=recovery&lotteryCode=200&issue=${football.issue}&sn=${football.sn}&id=${football.id}">设置为销售</a>]--%>
                    <%--</c:if>--%>
                    <%--<c:if test="${football.endOperator == 2}">--%>
                    <%--已取消--%>
                    <%--[<a href="${pageContext.request.contextPath}/manages/openAward?action=cancelOrRecoveryMatch&type=recovery&lotteryCode=200&issue=${football.issue}&sn=${football.sn}&id=${football.id}">设置为销售</a>]--%>
                    <%--[<a href="${pageContext.request.contextPath}/manages/openAward?action=cancelOrRecoveryMatch&type=end&lotteryCode=200&issue=${football.issue}&sn=${football.sn}&id=${football.id}">设置比赛结束</a>]--%>
                    <%--</c:if>--%>
                    <%--</td>--%>
                    <%--</tr>--%>
                    <tr>
                        <td align="right" class="bold">主队:</td>
                        <td>
                            ${football.mainTeam}
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">客队:</td>
                        <td>
                            ${football.guestTeam}
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">联赛:</td>
                        <td>
                            ${football.matchName}
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">预计开赛时间:</td>
                        <td>
                            <fmt:formatDate value="${football.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">预计截止投注时间:</td>
                        <td>
                            <fmt:formatDate value="${football.endFuShiTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">让球:</td>
                        <td>
                            ${football.letBall}
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">上半场 ${football.mainTeam}:${football.guestTeam}</td>
                        <td><input type="text" value="${football.mainTeamHalfScore}" id="mainTeamHalfScore"
                                   name="mainTeamHalfScore" size="10"/><input type="text"
                                                                              value="${football.guestTeamHalfScore}"
                                                                              id="guestTeamHalfScore"
                                                                              name="guestTeamHalfScore" size="10"/></td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">全场 ${football.mainTeam}:${football.guestTeam}</td>
                        <td><input type="text" value="${football.mainTeamScore}" id="mainTeamScore" name="mainTeamScore"
                                   size="10"/><input type="text" value="${football.guestTeamScore}" id="guestTeamScore"
                                                     name="guestTeamScore" size="10"/></td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center"><a id="calc" href="#">由比分自动生成比赛结果</a></td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">让球胜平负:<span id="rqspf" name="rqspf"></span>;</td>
                        <td>浮动奖金:<input type="text" value="${football.winOrNegaSp}" id="winOrNegaSp" name="winOrNegaSp"
                                        size="10"/></td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">胜平负:<span id="spf" name="spf"></span>;</td>
                        <td>浮动奖金:<input type="text" value="${football.spfWinOrNegaSp}" id="spfWinOrNegaSp"
                                        name="spfWinOrNegaSp"
                                        size="10"/></td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">总进球数:<span id="jqs" name="jqs"></span>;</td>
                        <td>浮动奖金:<input type="text" value="${football.totalGoalSp}" id="totalGoalSp" name="totalGoalSp"
                                        size="10"/></td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">半全场:<span id="bqc" name="bqc"></span>;</td>
                        <td>浮动奖金:<input type="text" value="${football.halfCourtSp}" id="halfCourtSp" name="halfCourtSp"
                                        size="10"/></td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">比分:<span id="bf" name="bf"></span>;</td>
                        <td>浮动奖金:<input type="text" value="${football.scoreSp}" id="scoreSp" name="scoreSp" size="10"/>
                        </td>
                    </tr>
                    <tr>
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
</body>
</html>