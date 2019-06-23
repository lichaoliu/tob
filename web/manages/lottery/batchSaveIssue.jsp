<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>普通彩期批量录入</title>
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
                    var startTime = $("#startTime").val();
                    var startDate = new Date(startTime.replace(/\-/g, "/"));

                    if (lotteryCode == '') {
                        alert("请选择彩种!");
                        return;
                    }

                    if (startTime == '') {
                        alert("请输入开始日期!");
                        return;
                    }

                    var lotteryName = $("lotteryCode").find("option:selected").text();
                    var str = "你确认保存吗?";
                    var bln = confirm(str);
                    if (bln) {
                        $("#editForm").submit();
                    } else {
                        windowClose();
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
<form id="editForm" action="${pageContext.request.contextPath}/manages/bulidIssueServlet"
      method="post">
    <table width="800" border="0" align="center" cellpadding="0" cellspacing="0" class="table table1">
        <tr>
            <td class="title" style="text-align:left;">
                彩期管理 >> 批量录入彩期
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
                                <option value="006">重庆时时彩</option>
                                <option value="009">江西时时彩</option>
                                <option value="010">安徽快三</option>
                                <option value="011">江苏快三</option>
                                <option value="013">江西快三</option>
                                <option value="014">广西快三</option>
                                <option value="007">湖北快三</option>
                                <option value="012">甘肃快三</option>
                                <option value="107">山东11选5</option>
                                <option value="106">安徽11选5</option>
                                <option value="105">广东11选5</option>
                                <option value="104">重庆11选5</option>
                                <option value="101">江西11选5</option>
                                <option value="102">黑龙江11选5</option>
                                <option value="103">新疆11选5</option>
                                <option value="111">陕西11选5</option>
                                <option value="112">青海11选5</option>
                                <option value="114">山东快乐扑克</option>
                            </select>
                            【<font color="red">*</font>号为必填项】
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold"><font color="red">*</font>开始日期:</td>
                        <td>
                            <input style="width: 150px;" type="text" id="startTime" name="startTime" reg="^.+$"
                                   tip="开始日期不能为空"
                                   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly">
                            【<font color="red">*</font>号为必填项】
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right" class="bold">天&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数:</td>
                        <td>
                            <select name="days" id="days">
                                <option value="" selected="selected">请选择</option>
                                <option value="1">一天</option>
                                <option value="3">三天</option>
                                <option value="7">一周</option>
                            </select>
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