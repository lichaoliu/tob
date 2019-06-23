<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>体彩期次详情</title>
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

        $(document).ready(function() {
            $("#calAwardBtn").click(function() {
                var bln = confirm("是否确认开始算奖?");
                if (bln) {
                    $("#awardForm").submit();
                } else {
                    history.go(-1);
                }
            });
        });
    </script>
</head>

<body>
<form id="awardForm" method="post"
      action="${pageContext.request.contextPath}/manages/sendWinManagesServlet?action=sportSendWin">
    <input type="hidden" name="issue" value="${mainIssue.name}"/>
    <input type="hidden" name="lotteryCode" value="${mainIssue.lotteryCode}"/>
    <input type="hidden" name="lotteryName" value="${lotteryName}"/>

    <table width="900" border="0" align="center" cellpadding="0" cellspacing="0" class="table table1">
        <tr>
            <td class="title">
                ${lotteryName}第${mainIssue.name}派奖
            </td>
        </tr>
        <tr>
            <td style="padding: 4px;">
                <table width="100%" border="0" cellpadding="4" cellspacing="0" class="seach_none">
                    <tr>
                        <td width="40%" align="right" class="bold">彩种:</td>
                        <td>
                            ${lotteryName}
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">彩期:</td>
                        <td>
                            ${mainIssue.name}
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">开售时间:</td>
                        <td>
                            <fmt:formatDate value="${mainIssue.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">止售时间:</td>
                        <td>
                            <fmt:formatDate value="${mainIssue.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" style="height:10px;"></td>
                    </tr>

                    <tr>
                        <td align="right" class="bold">开奖号码:</td>
                        <td>
                            ${mainIssue.bonusNumber}
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" style="height:10px;"></td>
                    </tr>

                    <tr>
                        <td align="right" class="bold">本期出票总数量:</td>
                        <td>
                            ${ticketNum}
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">本期出票总金额:</td>
                        <td>
                            <fmt:formatNumber value="${orderAmount}" pattern="0.00" />
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">本期中奖总金额:</td>
                        <td>
                            <fmt:formatNumber value="${bonusAmount}" pattern="0.00" />
                        </td>
                    </tr>

                    <tr>
                        <td colspan="2" align="center">
                            <input type="button" class="submit" value="开始派奖" id="calAwardBtn"
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