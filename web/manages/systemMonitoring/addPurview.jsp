<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>添加权限内容</title>
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
            新建权限
        </td>
    </tr>
    <tr>
        <td style="padding: 4px;">
            <form name="form1" method="post"
                  action="${pageContext.request.contextPath}/manages/systemManagesServlet?action=addPurview">
                <table width="100%" border="1" cellpadding="4" cellspacing="0" class="seach">
                    <tr>
                        <td width="20%" align="right">
                            <span style="color: red">*</span>父类权限
                        </td>
                        <td>
                            <select name="fatherName">
                                <option value="1-渠道管理">渠道管理</option>
                                <option value="2-财务管理">财务管理</option>
                                <option value="3-统计报表">统计报表</option>
                                <option value="4-彩期管理">彩期管理</option>
                                <option value="5-开奖管理">开奖管理</option>
                                <option value="6-算奖管理">算奖管理</option>
                                <option value="7-派奖管理">派奖管理</option>
                                <option value="8-彩票管理">彩票管理</option>
                                <option value="9-系统状态监控">系统状态监控</option>
                                <option value="10-系统参数设置">系统参数设置</option>
                                <option value="11-充值卡管理">充值卡管理</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            <span style="color: red">*</span>&nbsp;&nbsp;权限名:
                        </td>
                        <td><input type="text" name="name"/>
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            <span style="color: red">*</span>&nbsp;&nbsp;权限code:
                        </td>
                        <td><input type="text" name="code"/>[5位数字]
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                            <input type="submit" id="subBtn" value="添加" onclick="return ifLink('确认添加？')" class="submit"
                                   style="width: 64px; border: none">
                        </td>
                    </tr>
                </table>
            </form>
        </td>
    </tr>
</table>
<script>
    function ifLink(str) {
        if (form1.name.value == "") {
            alert("请输入权限名");
            return false;
        }
        if (form1.code.value == "") {
            alert("请输入权限码");
            return false;
        } else {
            if (form1.code.value.length != 5) {
                alert("权限码只能是5位");
                return false;
            } else if (!/^[0-9]+$/.test(form1.code.value)) {
                alert("权限码只能输入数字");
                return false;
            }
        }
        var bln = confirm(str);
        return bln;
    }
</script>
</body>
</html>