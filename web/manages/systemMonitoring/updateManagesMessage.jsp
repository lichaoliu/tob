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
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
    <tr>
        <td class="title">
            修改管理员信息
        </td>
    </tr>
    <tr>
        <td colspan="2" style="padding: 4px;">
            <table width="100%" border="0" cellpadding="4" cellspacing="0" class="seach">
                <form name="form1" method="post"
                      action="${pageContext.request.contextPath}/manages/systemManagesServlet?action=updateManages">
                    <input type="hidden" name="adminId" value="${manages.id }"/>
                    <input type="hidden" name="type" value="xgzl"/>
                    <input type="hidden" name="userName" value="${manages.userName }"/>
                    <tr>
                        <td width="20%" align="right">
                            姓名:
                        </td>
                        <td>
                            <input type="text" value="${manages.realName }" name="realName" style="width:50%"/>
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            用户名:
                        </td>
                        <td>
                            ${manages.userName }
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            部 门:
                        </td>
                        <td>
                            <input type="text" value="${manages.departments }" name="departments" style="width:50%"/>
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            手 机:
                        </td>
                        <td>
                            <input type="text" value="${manages.mobile }" name="mobile" style="width:50%"/>
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            座 机:
                        </td>
                        <td>
                            <input type="text" value="${manages.phone }" name="phone" style="width:50%"/>
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            邮 箱:
                        </td>
                        <td>
                            <input type="text" value="${manages.email}" name="email" style="width:50%"/>
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            角色:
                        </td>
                        <td>
                            <select name="purviewGroupCode">
                                <option value="">请选择</option>
                                <c:forEach var="purviewGroup" items="${purviewGroupList}">
                                    <option
                                            <c:if test="${purviewCode == purviewGroup.purviewGroupCode}">selected</c:if>
                                            value="${purviewGroup.purviewGroupCode}">${purviewGroup.name}</option>
                                </c:forEach>
                            </select>

                        </td>
                    </tr>
                    <tr>
                        <td style="padding: 4px;" colspan="2" align="center">
                            <span style="color:#0033cc;font-weight: bold;">${msg }</span><input type="submit"
                                                                                                id="subBtn" value="修改"
                                                                                                class="submit"
                                                                                                style="width: 64px; border: none"
                                                                                                onclick="return ifLink('确认修改？','')">
                            <input type="button" name="Submit" value="返回" class="submit"
                                   style="width: 64px; border: none" onclick="return ifLink('确认返回？','go')"/>
                        </td>
                    </tr>

                </form>
            </table>
        </td>
    </tr>
</table>
<script>
    function ifLink(str, goHistoy) {
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
        if (bln == true && goHistoy != "") {
            window.location.href="/manages/systemManagesServlet?action=getAdminList";
        }
        return bln;
    }
</script>
</body>
</html>