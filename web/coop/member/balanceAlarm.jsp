<!--
Created by IntelliJ IDEA.
User: MengJingyi
QQ: 116741034
Date: 13-4-16
Time: 下午3:04
To change this template use File | Settings | File Templates.
-->
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>接入商信息查询</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="Cache-Control" content="max-age=0" forua="true"/>
    <meta http-equiv="Cache-Control" content="no-cache" forua="true"/>
    <meta http-equiv="Cache-Control" content="must-revalidate" forua="true"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/manages.css">
    <script type="text/javascript">
        function check() {
            var amount = form1.amount.value;
            if (amount == "") {
                alert("请输入报警金额");
                return false;
            } else if (!(/^(([1-9]\d*)|\d)(\.\d{1,2})?$/).test(amount)) {
                alert("只能是数字");
                return false;
            }
            var mobile = form1.mobile.value;
            if (mobile == "") {
                alert("请填写报警号码");
                return false;
            } else {
                var m = mobile.split(",");
                for (i = 0; i < m.length; i++) {
                    var reg = "^[1][3-8]\\d{9}$";
                    var re = new RegExp(reg);
                    if (!re.test(m[i])) {
                        alert("号码有误" + m[i]);
                        return false;
                    }
                }
            }
            var email = form1.email.value;
            if (email == "") {
                alert("请填写报警邮箱");
                return false;
            } else {
                var e = email.split(",");
                for (i = 0; i < e.length; i++) {
                    var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
                    var re = new RegExp(reg);
                    alert(re.test(e[i]));
                    if (!re.test(e[i])) {
                        alert("邮箱有误" + e[i]);
                        return false;
                    }
                }
            }
            return true;
        }
    </script>
</head>

<body>
<table width="900" border="0" align="center" cellpadding="0" cellspacing="0" class="table table1">
    <tr>
        <td class="title" style="text-align:left;">
            余额报警修改
        </td>
    </tr>
    <tr>
        <td style="padding: 4px;">
            <form name="form1" action="${pageContext.request.contextPath}/coopBalanceServlet?action=update"
                  method="post">
                <table width="100%" border="0" cellpadding="4" cellspacing="0" class="seach_none">
                    <tr>
                        <td width="45%" align="right">
                            报警金额:
                        </td>
                        <td>
                            <input type="text" name="amount" value="${amount}"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            报警电话:
                        </td>
                        <td>
                            <input type="text" name="mobile" value="${mobile}"/> (多个用,分隔)
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            报警邮箱:
                        </td>
                        <td>
                            <input type="text" name="email" value="${email}"/> (多个用,分隔)
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            报警内容:
                        </td>
                        <td>
                            <input type="text" name="context" value="${context}"/> (可为空)
                        </td>
                    <tr>
                        <td align="center" colspan="2">

                            <input type="submit" value="提交" onclick="return check()"/> <font color="red">${msg} </font>
                        </td>
                    </tr>
                </table>
            </form>
        </td>
    </tr>
</table>
</body>
</html>