<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>普通彩期录入</title>
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
                    var lotteryCode = $("#lotteryCode").val();
                    var issue = $("#issue").val();
                    var startTime = $("#startTime").val();
                    var endTime = $("#endTime").val();

                    var startDate = new Date(startTime.replace(/\-/g, "/"));
                    var endDate = new Date(endTime.replace(/\-/g, "/"));

                    var startSecond = Date.parse(startDate);
                    var endSecond = Date.parse(endDate);

                    if (lotteryCode == '') {
                        alert("请选择彩种!");
                        return;
                    }

                    if (issue == '') {
                        alert("请输入彩期!");
                        return;
                    }

                    if (startSecond >= endSecond) {
                        alert("开售时间不能小于止售时间!");
                        return;
                    } else {
                        var lotteryName = $("lotteryCode").find("option:selected").text();
                        ;
                        var str = lotteryName + "第" + issue + "期,开售时间" + startTime + ",止售时间" + endTime + ";";
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
            window.location.href = "${pageContext.request.contextPath}/manages/issueManagesServlet?action=lotteryTotalIssueQuery";
        }
    </script>
</head>

<body>
<form id="editForm" action="${pageContext.request.contextPath}/manages/issueManagesServlet?action=saveIssue"
      method="post">
    <table width="800" border="0" align="center" cellpadding="0" cellspacing="0" class="table table1">
        <tr>
            <td class="title" style="text-align:left;">
                彩期管理 >> 录入彩期
            </td>
        </tr>
        <tr>
            <td style="padding: 4px;">
                <table width="100%" border="0" cellpadding="4" cellspacing="0" class="seach_none">
                    <tr>
                        <td width="20%" align="right" class="bold">彩&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;种:</td>
                        <td>
                            <select name="lotteryCode" id="lotteryCode">
                                <option value="" selected="selected">请选择</option>
                                <option value="001">双色球</option>
                                <option value="002">3D</option>
                                <option value="004">七乐彩</option>
                                <option value="108">排列三</option>
                                <option value="109">排列五</option>
                                <option value="110">七星彩</option>
                                <option value="113">大乐透</option>
                                <option value="202">竞彩冠军</option>
                            </select>
                            【<font color="red">*</font>号为必填项】
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">彩&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;期:</td>
                        <td class="three">
                            <input style="width: 150px;" type="text" id="issue" name="issue" reg="^\d+$"
                                   tip="彩期不能为空">
                            【<font color="red">*</font>号为必填项】
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold"><font color="red">*</font>开售时间:</td>
                        <td>
                            <input style="width: 150px;" type="text" id="startTime" name="startTime" reg="^.+$"
                                   tip="开售时间不能为空"
                                   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:00'})">
                            【<font color="red">*</font>号为必填项】
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold"><font color="red">*</font>止售时间:</td>
                        <td>
                            <input style="width: 150px;" type="text" id="endTime" name="endTime" reg="^.+$"
                                   tip="止售时间不能为空"
                                   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:00'})">
                            【<font color="red">*</font>号为必填项】
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold"><font color="red">*</font>开奖时间:</td>
                        <td>
                            <input style="width: 150px;" type="text" id="bonusTime" name="bonusTime" reg="^.+$"
                                   tip="开奖时间不能为空"
                                   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd 22:00:00'})">
                            【<font color="red">*</font>号为必填项】
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2"><font color="red">${requestScope.msg}</font></td>
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