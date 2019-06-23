<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>修改管理员信息</title>
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
</head>

<body>

<c:if test="${msg != null}">
    <script type="text/javascript">
        alert('${msg}');
    </script>
</c:if>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
    <tr>
        <td class="title">
            新建管理员
        </td>
    </tr>
    <tr>
        <td style="padding: 4px;">
            <form name="form1" method="post"
                  action="${pageContext.request.contextPath}/manages/systemManagesServlet?action=addAdmin">
                <table width="100%" border="1" cellpadding="4" cellspacing="0" class="seach">
                    <input type="hidden" name="adminId" value="${manages.id }"/>
                    <input type="hidden" name="cooperationId" value="${cooperationId}"/>
                    <input type="hidden" name="type" value="xg"/>
                    <tr>
                        <td width="20%" align="right">
                            <span style="color: red">*</span>&nbsp;&nbsp;用户名:
                        </td>
                        <td><input type="text" name="adminName"/>[建议使用名字拼音的全拼做为您的登录名]
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            <span style="color: red">*</span>&nbsp;&nbsp;密 码:
                        </td>
                        <td><input type="password" name="password"/>[不少于6位数字或字母]
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            <span style="color: red">*</span>&nbsp;&nbsp;再次输入密码:
                        </td>
                        <td><input type="password" value="" name="passwordQr"/>
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            姓名:
                        </td>
                        <td><input type="text" name="realName"/>
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            部 门:
                        </td>
                        <td><input type="text" name="departments"/>
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            手 机:
                        </td>
                        <td><input type="text" name="mobile"/>
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            座 机:
                        </td>
                        <td><input type="text" name="phone"/>
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            邮 箱:
                        </td>
                        <td><input type="text" name="email"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                            <input type="submit" id="subBtn" value="添加" onclick="return ifLink('确认添加？')" class="submit"
                                   style="width: 64px; border: none">
                            <input type="button" name="Submit" value="返回" class="submit"
                                   style="width: 64px; border: none" onclick="javascript:history.go(-1);">
                        </td>
                    </tr>
                </table>
            </form>
        </td>
    </tr>
</table>
<script>
    function ifLink(str) {
        if (form1.adminName.value == "") {
            alert("管理员名不能为空！");
            return false;
        } else {
            if (!/^[\u4E00-\u9FFF|0-9|\w|\-|\_|\.]+$/.test(form1.adminName.value)) {
                alert("管理员名只能输入英文、中文、数字和“.”、“-”、“_”！");
                return false;
            }
        }
        if (form1.realName.value != "") {
            if (!/^[\u4E00-\u9FFF]+$/.test(form1.realName.value)) {
                alert("姓名只能输入中文");
                return false;
            }

        }
        if (form1.password.value == "") {
            alert("请输入密码");
            return false;
        } else if (form1.passwordQr.value != form1.password.value) {
            alert("密码和确认密码不一致，请重新输入");
            return false;
        }
        var bln = confirm(str);
        return bln;
    }
</script>
</body>
</html>