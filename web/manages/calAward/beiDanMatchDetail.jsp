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
        $(document).ready(function() {
            $("#calAwardBtn").click(function() {
                var bln = confirm("是否确认开始算奖?");
                if (bln) {
                    $("#awardForm").submit();
                } else {
//                    history.go(-1);
                }
            });
        });
    </script>
</head>

<body>
<form id="awardForm" method="post"
      action="${pageContext.request.contextPath}/manages/calculateAwardServlet?action=beiDanCalculateAward">
    <input type="hidden" name="id" value="${beiDan.id}"/>
    <table width="900" border="0" align="center" cellpadding="0" cellspacing="0" class="table table1">
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
                    <tr>
                        <td align="right" class="bold">主队:</td>
                        <td>
                            ${beiDan.mainTeam}
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">客队:</td>
                        <td>
                            ${beiDan.guestTeam}
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">联赛:</td>
                        <td>
                            ${beiDan.matchName}
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">比赛时间:</td>
                        <td>
                            <fmt:formatDate value="${beiDan.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">让球数:</td>
                        <td>
                            ${beiDan.letBall}
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">全场比分:</td>
                        <td>
                            <c:if test="${beiDan.mainTeamScore != null}">${beiDan.mainTeamScore} : ${beiDan.guestTeamScore}</c:if>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">半场比分:</td>
                        <td>
                            <c:if test="${beiDan.mainTeamHalfScore != null}">${beiDan.mainTeamHalfScore} : ${beiDan.guestTeamHalfScore}</c:if>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">赛果SP:</td>
                        <td>
                            <!-- 胜平负 -->
                            胜平负:
                            <!-- 赛果 -->
                            ${spfResult}
                            <!-- 浮动奖金 -->
                            <c:if test="${requestScope.spfFloatAmount != null}">SP:<fmt:formatNumber
                                    value="${requestScope.spfFloatAmount}" pattern="0.00"/></c:if>
                            <br/>
                            上下单双:
                            <!-- 赛果 -->
                            ${sxdsResult}
                            <!-- 浮动奖金 -->
                            <c:if test="${requestScope.sxdsFloatAmount != null}">SP:<fmt:formatNumber
                                    value="${requestScope.sxdsFloatAmount}" pattern="0.00"/></c:if>
                            <br/>
                            <!-- 总进球数 -->
                            总进球数:
                            <!-- 赛果 -->
                            ${zjqsResult}
                            <!-- 浮动奖金 -->
                            <c:if test="${requestScope.zjqsFloatAmount != null}">SP:<fmt:formatNumber
                                    value="${requestScope.zjqsFloatAmount}" pattern="0.00"/></c:if>
                            <br/>
                            <!-- 半全场胜平负 -->
                            半全场胜平负:
                            <!-- 赛果 -->
                            ${bqcspfResult}
                            <!-- 浮动奖金 -->
                            <c:if test="${requestScope.bqcspfFloatAmount != null}">SP:<fmt:formatNumber
                                    value="${requestScope.bqcspfFloatAmount}" pattern="0.00"/></c:if>
                            <br/>
                            <!-- 比分 -->
                            比分:
                            <!-- 赛果 -->
                            ${bfResult}
                            <!-- 浮动奖金 -->
                            <c:if test="${requestScope.bfFloatAmount != null}">SP:<fmt:formatNumber
                                    value="${requestScope.bfFloatAmount}" pattern="0.00"/></c:if>
                        </td>
                    </tr>

                    <tr>
                        <td colspan="2" align="center">
                            <input type="button" class="submit" value="开始算奖" id="calAwardBtn"
                                   style="width: 100px; border: none"/>

                            <input type="button" class="submit" value="取消" style="width: 64px; border: none"
                                   onclick="javascript:history.go(-1);"/>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
    </div>
</form>
</body>
</html>