<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>竞彩篮球赛果录入</title>
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

                var str = "竞彩篮球第${basketball.issue}${basketball.week}${basketball.sn}期";

                str += "\n上半场 ${basketball.mainTeam}:${basketball.guestTeam} = " + $("#mainTeamHalfScore").val() + ":" + $("#guestTeamHalfScore").val();

                str += "\n全场 ${basketball.mainTeam}:${basketball.guestTeam} = " + $("#mainTeamScore").val() + ":" + $("#guestTeamScore").val();

                str += "\n\n最终SP值";
                str += "\n胜负:" + $("#winOrNegaSp").val();

                str += "\n让分胜负:" + $("#letWinOrNegaSp").val();

                str += "\n胜分差:" + $("#winNegaDiffSp").val();

                str += "\n大小分:" + $("#bigOrLittleSp").val();

                var bln = confirm(str + "\n是否确认保存开奖信息?");
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
                if (isNotNull(main) && isNotNull(guest)) {
                    if (main > guest) {
                        setText("sf", "胜");
                    } else if (main < guest) {
                        setText("sf", "负");
                    } else {
                        setText("sf", "");
                    }

                    if (eval(main + "${basketball.letBall}") > guest) {
                        setText("rfsf", "胜");
                    } else if (eval(main + "${basketball.letBall}") < guest) {
                        setText("rfsf", "负");
                    } else {
                        setText("rfsf", "");
                    }
                    if (main + guest > "${basketball.preCast}") {
                        setText("dxf", "大");
                    } else if (main + guest < "${basketball.preCast}") {
                        setText("dxf", "小");
                    } else {
                        setText("dxf", "");
                    }
                    setText("sfc", main - guest);
                }
            });
            $("#mainTeamScore").keyup(regInteger3);
            $("#guestTeamScore").keyup(regInteger3);
            $("#mainTeamHalfScore").keyup(regInteger3);
            $("#guestTeamHalfScore").keyup(regInteger3);

            $("#winOrNegaSp").keyup(regFloat);
            $("#letWinOrNegaSp").keyup(regFloat);
            $("#winNegaDiffSp").keyup(regFloat);
            $("#bigOrLittleSp").keyup(regFloat);
        });

        function regInteger3() {
            var maxLen = 3;
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
      action="${pageContext.request.contextPath}/manages/openAward?action=doEditBasketball">
    <input type="hidden" name="issue" value="${basketball.issue}"/>
    <input type="hidden" name="sn" value="${basketball.sn}"/>
    <input type="hidden" name="id" value="${basketball.id}"/>
    <input type="hidden" name="matchNo" value="${matchNo}"/>
    <input type="hidden" name="mainTeam" value="${mainTeam}"/>
    <input type="hidden" name="guestTeam" value="${guestTeam}"/>
    <input type="hidden" name="matchName" value="${matchName}"/>
    <input type="hidden" name="operatorsAward" value="${operatorsAward}"/>
    <input type="hidden" name="page" value="${page}"/>
    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="table table1">
        <tr>
            <td class="title">
                竞彩篮球赛事${basketball.issue}${basketball.week}${basketball.sn}赛果录入
            </td>
        </tr>
        <tr>
            <td style="padding: 4px;">
                <table width="100%" border="0" cellpadding="4" cellspacing="0" class="seach_none">
                    <tr>
                        <td align="right" width="50%" class="bold">比赛日期:</td>
                        <td>
                            ${basketball.issue}
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">赛事编号:</td>
                        <td>${basketball.week}${basketball.sn}</td>
                    </tr>
                    <%--<tr>--%>
                        <%--<td align="right" class="bold">对阵状态:</td>--%>
                        <%--<td>--%>
                            <%--<c:if test="${basketball.endOperator == 0}">--%>
                                <%--销售中--%>
                                <%--[<a href="${pageContext.request.contextPath}/manages/openAward?action=cancelOrRecoveryMatch&type=cancel&lotteryCode=201&issue=${basketball.issue}&sn=${basketball.sn}&id=${basketball.id}">取消本场比赛</a>]--%>
                                <%--[<a href="${pageContext.request.contextPath}/manages/openAward?action=cancelOrRecoveryMatch&type=end&lotteryCode=201&issue=${basketball.issue}&sn=${basketball.sn}&id=${basketball.id}">设置比赛结束</a>]--%>
                            <%--</c:if>--%>
                            <%--<c:if test="${basketball.endOperator == 1}">--%>
                                <%--比赛结束--%>
                                <%--[<a href="${pageContext.request.contextPath}/manages/openAward?action=cancelOrRecoveryMatch&type=cancel&lotteryCode=201&issue=${basketball.issue}&sn=${basketball.sn}&id=${basketball.id}">取消本场比赛</a>]--%>
                                <%--[<a href="${pageContext.request.contextPath}/manages/openAward?action=cancelOrRecoveryMatch&type=recovery&lotteryCode=201&issue=${basketball.issue}&sn=${basketball.sn}&id=${basketball.id}">设置为销售</a>]--%>
                            <%--</c:if>--%>
                            <%--<c:if test="${basketball.endOperator == 2}">--%>
                                <%--已取消--%>
                                <%--[<a href="${pageContext.request.contextPath}/manages/openAward?action=cancelOrRecoveryMatch&type=recovery&lotteryCode=201&issue=${basketball.issue}&sn=${basketball.sn}&id=${basketball.id}">设置为销售</a>]--%>
                                <%--[<a href="${pageContext.request.contextPath}/manages/openAward?action=cancelOrRecoveryMatch&type=end&lotteryCode=201&issue=${basketball.issue}&sn=${basketball.sn}&id=${basketball.id}">设置比赛结束</a>]--%>
                            <%--</c:if>--%>
                        <%--</td>--%>
                    <%--</tr>--%>
                    <tr>
                        <td align="right" class="bold">主队:</td>
                        <td>
                            ${basketball.mainTeam}
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">客队:</td>
                        <td>
                            ${basketball.guestTeam}
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">联赛:</td>
                        <td>
                            ${basketball.matchName}
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">预计开赛时间:</td>
                        <td>
                            <fmt:formatDate value="${basketball.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">预计截止投注时间:</td>
                        <td>
                            <fmt:formatDate value="${basketball.endFuShiTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">让分:</td>
                        <td>
                            ${basketball.letBall}
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">预设总分:</td>
                        <td>
                            ${basketball.preCast}
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">上半场 ${basketball.mainTeam}:${basketball.guestTeam}</td>
                        <td><input type="text" value="${basketball.mainTeamHalfScore}" id="mainTeamHalfScore"
                                   name="mainTeamHalfScore" size="10"/><input type="text"
                                                                              value="${basketball.guestTeamHalfScore}"
                                                                              id="guestTeamHalfScore"
                                                                              name="guestTeamHalfScore" size="10"/></td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">全场 ${basketball.mainTeam}:${basketball.guestTeam}</td>
                        <td><input type="text" value="${basketball.mainTeamScore}" id="mainTeamScore"
                                   name="mainTeamScore" size="10"/><input type="text"
                                                                          value="${basketball.guestTeamScore}"
                                                                          id="guestTeamScore" name="guestTeamScore"
                                                                          size="10"/></td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center"><a id="calc" href="#">由比分自动生成比赛结果</a></td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">胜负:<span id="sf" name="sf"></span>;</td>
                        <td>浮动奖金:<input type="text" value="${basketball.winOrNegaSp}" id="winOrNegaSp"
                                        name="winOrNegaSp" size="10"/></td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">让分胜负:<span id="rfsf" name="rfsf"></span>;</td>
                        <td>浮动奖金:<input type="text" value="${basketball.letWinOrNegaSp}" id="letWinOrNegaSp"
                                        name="letWinOrNegaSp" size="10"/></td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">胜分差:<span id="sfc" name="sfc"></span>;</td>
                        <td>浮动奖金:<input type="text" value="${basketball.winNegaDiffSp}" id="winNegaDiffSp"
                                        name="winNegaDiffSp" size="10"/></td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">大小分:<span id="dxf" name="dxf"></span>;</td>
                        <td>浮动奖金:<input type="text" value="${basketball.bigOrLittleSp}" id="bigOrLittleSp"
                                        name="bigOrLittleSp" size="10"/></td>
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