<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>修改密码</title>
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
        function checkPassword() {
            if (form1.oldPassword.value == "") {
                alert("请输入原密码");
                return false;
            } else if (form1.password.value == form1.oldPassword.value) {
                alert("原密码和新密码一样");
                return false;
            } else if (form1.qrNewPassword.value != form1.password.value) {
                alert("新密码和确认密码不一致，请重新输入");
                return false;
            }
            return true;
        }
        function checkPasswordAdmin() {
            if (form1.qrNewPassword.value == "") {
                alert("请输入新密码");
                return false;
            } else if (form1.qrNewPassword.value != form1.password.value) {
                alert("新密码和确认密码不一致，请重新输入");
                return false;
            }
            return true;
        }
    </script>
</head>

<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
    <tr>
        <td class="title">
            修改密码
        </td>
    </tr>
    <tr>
        <td style="padding: 4px;">
            <form name="form1" method="post"
                  action="${pageContext.request.contextPath}/coop/cooperationServlet?action=updateManages">
                <table width="100%" border="1" cellpadding="4" cellspacing="0" class="seach">
                    <input type="hidden" name="type" value="xg"/>
                    <input type="hidden" name="adminId" value="${adminUser.id }"/>
                    <tr>
                        <td width="20%" align="right">
                            原 密 码:
                        </td>
                        <td>
                            <input type="password" value="" name="oldPassword"/>
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            新 密 码:
                        </td>
                        <td>
                            <input type="password" value="" name="password"/>
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            确认密码:
                        </td>
                        <td>
                            <input type="password" value="" name="qrNewPassword"/>
                        </td>
                    </tr>
                    <tr>
                        <td style="padding: 4px;" colspan="2" align="center">
                            <span style="color:#0033cc;font-weight: bold;">${msg }</span><input type="submit"
                                                                                                id="subBtn" value="修改"
                                                                                                class="submit"
                                                                                                style="width: 64px; border: none"
                                                                                                onclick="return checkPassword()">
                        </td>
                    </tr>
                </table>
            </form>
        </td>
    </tr>
</table>
</body>
</html>