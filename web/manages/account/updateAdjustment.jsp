<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>调整额度</title>
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
    <script type="text/javascript">
        $(document).ready(function() {
            $("#submitBtn").click(function() {
                if (ifLink('确认调整该用户的额度？')) {
                    if (!/^(\d+|\d+\.\d{1,2})$/.test($("#amount").val())) {
                        alert("请确认录入的额度是否正确,且调整的金额只能精确到分");
                        return;
                    }
                    var amount = parseFloat($("#amount").val());
                    if (amount == 0) {
                        alert("额度调整金额不能为0");
                        return;
                    }
                    if (amount < 0) {
                        alert("额度调整金额不能小于0");
                        return;
                    }
                    var type = $("#type").val();
                    var operator = $("#operator").val();
                    var presentAmount = parseFloat($("#presentAmount").val());
                    var rechargeAmount = parseFloat($("#rechargeAmount").val());
                    var bonusAmount = parseFloat($("#bonusAmount").val());
                    var realAmount = parseFloat(operator + amount);

                    if (type == '01') {
                        if (realAmount + bonusAmount < 0) {
                            alert("调整之后的奖金不能小于0");
                            return;
                        }
                    }
                    if (type == '02') {
                        if (realAmount + rechargeAmount < 0) {
                            alert("调整之后的充值金额不能小于0");
                            return;
                        }
                    }
                    if (type == '03') {
                        if (realAmount + presentAmount < 0) {
                            alert("调整之后的赠金不能小于0");
                            return;
                        }
                    }
                    var qr = document.getElementById("submitBtn");
                    var qx = document.getElementById("submitBtnqx");
                    qr.disabled = true;
                    qx.disabled = true;

                    $("#form1").submit();
                } else {
                    return;
                }
            });
        });
    </script>
</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
    <tr>
        <td class="title">调整额度</td>
    </tr>
    <tr>

        <td style="padding: 4px;">
            <table width="100%" border="1" cellpadding="4" cellspacing="0" class="content">
                <tr style="display: none;">
                </tr>
                <tr id="f">
                    <td width="20%">
                        商户名称
                    </td>
                    <td width="15%">
                        商户ID
                    </td>
                    <td width="16%">
                        注册时间
                    </td>
                    <td width="6%">
                        状态
                    </td>
                    <td width="15%">
                        奖金余额
                    </td>
                    <td width="15%">
                        充值金余额
                    </td>
                    <td width="15%">
                        赠金余额
                    </td>
                </tr>
                <tr class="f">
                    <td align="left">
                        ${member.name}
                    </td>
                    <td>
                       ${member.sid}
                    </td>
                    <td align="center">
                        <fmt:formatDate value="${member.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </td>
                    <td align="center">
                        ${myf:userStatus(member.status)}
                    </td>
                    <td align="right">
                        <fmt:formatNumber value="${account.bonusAmount}" pattern="0.00"/>
                    </td>
                    <td align="right">
                        <fmt:formatNumber value="${account.rechargeAmount}" pattern="0.00"/>
                    </td>
                    <td align="right">
                        <fmt:formatNumber value="${account.presentAmount}" pattern="0.00"/>
                    </td>
                </tr>
            </table>


            <table width="100%" border="1" cellpadding="4" cellspacing="0" class="seach">
                <form name="form1" id="form1" method="post"
                      action="${pageContext.request.contextPath}/manages/accountManagesServlet?action=updateAccount">
                    <input type="hidden" name="userCode" value="${account.userCode }"/>
                    <input type="hidden" name="sid" value="${member.sid }"/>
                    <input type="hidden" name="bonusAmount" id="bonusAmount" value="${account.bonusAmount }"/>
                    <input type="hidden" name="rechargeAmount" id="rechargeAmount" value="${account.rechargeAmount }"/>
                    <input type="hidden" name="presentAmount" id="presentAmount" value="${account.presentAmount }"/>
                    <tr>
                        <td align="right">
                            调整额度
                        </td>
                        <td align="center" width="8%">
                            <select name="type" id="type">
                                <option value="01">奖金</option>
                                <option value="02" selected>充值金</option>
                                <option value="03">赠金</option>
                            </select>
                        </td>

                        <td align="center" width="8%">
                            <select name="operator" id="operator">
                                <option value="+">增加</option>
                                <option value="-">减少</option>
                            </select>
                        </td>
                        <td>
                            <input type="text" name="amount" id="amount" value=""
                                   onkeyup="this.value=this.value.replace(/[^\.\d]/g,'')"
                                   onafterpaste="this.value=this.value.replace(/[^\.\d]/g,'')"> 元
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            备 注
                        </td>
                        <td colspan="3">
                            <textarea type="text" name="body" value="" style="width: 80%; height: 100px;"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="4" align="center">
                            <input type="button" name="button" id="submitBtn" class="submit" value="确认"
                                   style="width: 64px; border: none">


                            <input type="button" name="button" id="submitBtnqx" onclick="javascript:history.go(-1);"
                                   class="submit" value="取消"
                                   style="width: 64px; border: none">

                        </td>
                    </tr>
                </form>
            </table>
        </td>
    </tr>
</table>
</body>
<script>
    function ifLink(str) {
        var bln = confirm(str);
        return bln;
    }

    var error = '${requestScope.error}';
    if (error != '') {
        alert(error);
    }
</script>
</html>