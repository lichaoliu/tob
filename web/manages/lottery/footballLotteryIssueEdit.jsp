<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>足彩期次录入</title>
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
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/easyValidator/js/validate.pack.js"></script>
    <link href="${pageContext.request.contextPath}/js/easyValidator/css/validate.css" rel="stylesheet" type="text/css"/>
    <style type="text/css">
        body, td, th {
            font-family: Arial, "Times New Roman", "宋体";
        }
    </style>


    <script type="text/javascript">
        $(document).ready(function() {
            $("#saveBtn").click(function() {
                var matchNum = '${requestScope.matchNum}';
                var isSubmit = true;
                $("input[reg]").each(function() {
                    if (!validate($(this), "input")) {
                        isSubmit = false;
                    }
                });
                if (isSubmit) {
                    var issue = $("#issue").val();
                    var startTime = $("#startTime").val();
                    var endTime = $("#endTime").val();

                    var startDate = new Date(startTime.replace(/\-/g, "/"));
                    var endDate = new Date(endTime.replace(/\-/g, "/"));

                    var startSecond = Date.parse(startDate);
                    var endSecond = Date.parse(endDate);

                    if (issue == '') {
                        alert("期次不能为空!");
                        return;
                    }

                    if (startSecond >= endSecond) {
                        alert("开售时间不能小于止售时间!");
                        return;
                    } else {
                        var lotteryName = '${requestScope.lotteryName}';
                        var issue = '${requestScope.issue}';
                        var str = lotteryName + "第" + issue + "期,开售时间" + startTime + ",止售时间" + endTime + ";";
                        str = str + "对阵为 ";
                        var leageName = "";
                        var mainTeam = "";
                        var guestTeam = "";
                        for (var i = 1; i <= parseInt(matchNum); i++) {
                            str = str + i + $("#leageName" + i).val() + ":" + $("#mainTeam" + i).val() + " v " + $("#guestTeam" + i).val() + ",比赛编号是 " + $("#leageNo" + i).val() + ".";
                        }
                        str = str + "你确认保存吗?";
                        var bln = confirm(str);
                        if (bln) {
                            $("#editForm").submit();
                        } else {
                            windowClose();
                        }
                    }
                } else {
                    alert("请完善信息！");
                }
            });
        });
        function windowClose() {
            window.location.href = "${pageContext.request.contextPath}/manages/issueManagesServlet?action=footballIssueList";
        }
    </script>
</head>

<body>
<form id="editForm" action="${pageContext.request.contextPath}/manages/issueManagesServlet?action=footballIssueEdit"
      method="post">
    <input type="hidden" name="lotteryCode" value="${requestScope.lotteryCode}"/>
    <input type="hidden" name="lotteryName" value="${requestScope.lotteryName}"/>
    <input type="hidden" name="matchNum" value="${requestScope.matchNum}"/>
    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="table table1">
        <tr>
            <td class="title">
                足球${requestScope.lotteryName}${mainIssue.name}录入
            </td>
        </tr>
        <tr>
            <td style="padding: 4px;">
                <table width="100%" border="0" cellpadding="4" cellspacing="0" class="seach_none">
                    <tr>
                        <td width="20%" align="right" class="bold">彩&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;种:</td>
                        <td>
                            <span style="color:blue;"><b>${requestScope.lotteryName == "胜负彩" ? "胜负彩(14场/任九场)" : requestScope.lotteryName}</b></span>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">彩&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;期:</td>
                        <td class="three">
                            <input style="width: 150px;" type="text" id="issue" name="issue" value="${mainIssue.name}"
                                   reg="^\d+$"
                                   tip="彩期不能为空">
                            【<font color="red">*</font>号为必填项】
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold"><font color="red">*</font>开售时间:</td>
                        <td>
                            <input style="width: 150px;" type="text" id="startTime" name="startTime"
                                   value="${startTime}" reg="^.+$"
                                   tip="开售时间不能为空"
                                   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd 20:00:00'})">
                            【<font color="red">*</font>号为必填项】
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold"><font color="red">*</font>止售时间:</td>
                        <td>
                            <input style="width: 150px;" type="text" id="endTime" name="endTime" value="${endTime}"
                                   reg="^.+$"
                                   tip="止售时间不能为空"
                                   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:00'})">
                            【<font color="red">*</font>号为必填项】
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold"><font color="red">*</font>开奖时间:</td>
                        <td>
                            <input style="width: 150px;" type="text" id="bonusTime" name="bonusTime"
                                   value="${bonusTime}" reg="^.+$"
                                   tip="开奖时间不能为空"
                                   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd 10:00:00'})">
                            【<font color="red">*</font>号为必填项】
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">&nbsp;</td>
                    </tr>
                    <tr>
                        <td colspan="2" style="letter-spacing: 0px;">
                            <table style="padding-left: 20px;" width="100%" border="0" cellpadding="0" cellspacing="0">
                                <c:forEach begin="1" end="${requestScope.matchNum}" step="1" varStatus="cont">
                                    <tr>
                                        <td>${cont.count}</td>
                                        <td>
                                            <font color="red">*</font>联赛
                                            <input type="text" style="width:80px" id="leageName${cont.count}"
                                                   name="leageName${cont.count}" reg="^.+$" tip="赛事名称不能为空"
                                                   value="${subGameList[cont.index-1].leageName}"/>
                                            &nbsp;编号<input type="text" style="width:80px" id="leageNo${cont.count}"
                                                           name="leageNo${cont.count}"
                                                           value="${cont.count}"/>
                                            &nbsp;<font color="red">*</font>主队
                                            <input type="text" style="width:80px" id="mainTeam${cont.count}"
                                                   name="mainTeam${cont.count}" reg="^.+$" tip="主队名称不能为空"
                                                   value="${subGameList[cont.index-1].masterName}"/>
                                            <font color="red">*</font>客队
                                            <input type="text" style="width:80px" id="guestTeam${cont.count}"
                                                   name="guestTeam${cont.count}" reg="^.+$" tip="客队名称不能为空"
                                                   value="${subGameList[cont.index-1].guestName}"/>
                                            &nbsp;<font color="red">*</font>
                                            比赛时间<input style="width: 140px;" type="text" id="playTime${cont.count}"
                                                       name="playTime${cont.count}" reg="^.+$"
                                                       tip="比赛时间不能为空"
                                                       onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:00'})"
                                                       value="${subGameList[cont.index-1].startTime}">
                                        </td>
                                    </tr>

                                </c:forEach>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                            <input type="button" class="submit" id="saveBtn" value="保存"
                                   style="width: 64px; border: none"/>

                            <input type="button" class="submit" value="取消" style="width: 64px; border: none"
                                   onclick="javascript:windowClose();"/>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</form>
</body>
</html>