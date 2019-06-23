<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>管理员个人用户信息</title>
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
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
    <tr>
        <td class="title" style="text-align:left;">
            系统参数设置 >> 个人管理
        </td>
        <td class="title" style="text-align:right">
            [<a href="${pageContext.request.contextPath}/manages/systemMonitoring/updateAdminPassword.jsp">密码修改</a>]
        </td>
    </tr>
    <tr>
        <td colspan="2" style="padding: 4px;">
            <form name="form1" method="post"
                  action="${pageContext.request.contextPath}/manages/systemManagesServlet?action=updateManages">
                <table width="100%" border="1" cellpadding="4" cellspacing="0" class="seach">
                    <input type="hidden" name="adminId" value="${adminUser.id }"/>
                    <input type="hidden" name="type" value="xg"/>
                    <tr>
                        <td width="20%" align="right">
                            用户名:
                        </td>
                        <td>
                            ${adminUser.userName }
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            姓名:
                        </td>
                        <td>
                            <input type="text" value="${adminUser.realName }" name="realName"/>
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            座 机:
                        </td>
                        <td>
                            <input type="text" value="${adminUser.phone }" name="phone"/>
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            手 机:
                        </td>
                        <td>
                            <input type="text" value="${adminUser.mobile }" name="mobile"/>
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            部 门:
                        </td>
                        <td>
                            <input type="text" value="${adminUser.departments }" name="departments"/>
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            职 位:
                        </td>
                        <td>
                            <input type="text" value="${adminUser.post }" name="post"/>
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            邮 箱:
                        </td>
                        <td>
                            <input type="text" value="${adminUser.email }" name="email"/>
                        </td>
                    </tr>
                    <tr>
                    <tr>
                        <td style="padding: 4px;" colspan="2" align="center">
                            <span style="color:#0033cc;font-weight: bold;">${msg }</span><input type="submit"
                                                                                                id="subBtn" value="修改"
                                                                                                class="submit"
                                                                                                style="width: 64px; border: none"
                                                                                                onclick="return ifLink('确认修改？')">
                            <input type="button" name="Submit" value="返回" class="submit"
                                   style="width: 64px; border: none" onclick="javascript:history.go(-1);"/>
                        </td>
                    </tr>
                </table>
            </form>
        </td>
    </tr>
</table>
<script>
    function ifLink(str) {
        if (form1.realName.value != "") {
            if (!/^[\u4E00-\u9FFF]+$/.test(form1.realName.value)) {
                alert("姓名只能输入中文");
                return false;
            }

        }

        if(form1.departments.value != ""){
            if (!/^[\u4E00-\u9FFF|0-9|\w|\-|\_|\.]+$/.test(form1.departments.value)) {
                alert("部门只能输入英文、中文、数字和“.”、“-”、“_”！");
                return false;
            }
        }

        if(form1.post.value != ""){
            if (!/^[\u4E00-\u9FFF|0-9|\w|\-|\_|\.]+$/.test(form1.post.value)) {
                alert("职位只能输入英文、中文、数字和“.”、“-”、“_”！");
                return false;
            }
        }
        if (form1.mobile.value != "") {
            if (!/^1{1}[3|5|8]{1}\d{9}$/.test(form1.mobile.value)) {
                alert("手机号码格式错误！");
                return false;
            }
        }
        
        if (form1.email.value != "") {
            if (!/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/g.test(form1.email.value)) {
                alert("邮箱格式错误！");
                return false;
            }
        }


        var bln = confirm(str);
        return bln;
    }
</script>
</body>
</html>