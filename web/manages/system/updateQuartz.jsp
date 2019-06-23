<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>编辑JOB信息</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="Cache-Control" content="max-age=0" forua="true"/>
    <meta http-equiv="Cache-Control" content="no-cache" forua="true"/>
    <meta http-equiv="Cache-Control" content="must-revalidate" forua="true"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/validator.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/page.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/tool.jquery.cookie.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/idcard.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/page.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/manages.css">
    <script type="text/javascript">
        $(document).ready(function() {
            $("#subBtn").click(function() {
                var code = $("#code").val();
                if (code == '') {
                    alert("请输入名称");
                    return;
                }

                var name = $("#name").val();
                if (name == "") {
                    alert("请输入编码");
                    return;
                }
                $("#form1").submit();
            });
        });
    </script>
</head>

<body>
<table width="900" border="0" align="center" cellpadding="0" cellspacing="0" class="table table1">
    <tr>
        <td class="title" style="text-align:left;">
            系统状态监控 >> 系统配置
        </td>
    </tr>
    <tr>
        <td style="padding: 4px;">
            <form name="form1" id="form1" method="post"
                  action="${pageContext.request.contextPath}/manages/systemManagesServlet?action=updateLock">
                <table width="100%" border="0" cellpadding="4" cellspacing="0" class="seach_none">
                    <tr>
                        <input type="hidden" value="${distributionLock.id}" name="id"/>
                        <td width="45%" align="right">
                            名称:
                        </td>
                        <td>
                            <input type="text" value="${distributionLock.code}" name="code" id="code"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            编码:
                        </td>
                        <td>
                            <input type="text" value="${distributionLock.name}" name="name" id="name"/></td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                            <input type="button" id="subBtn" value="确定" class="submit"
                                   style="width: 64px; border: none">
                            <input type="button" name="Submit" value="返回" class="submit"
                                   style="width: 64px; border: none"
                                   onclick="javascript:history.go(-1);">
                        </td>
                    </tr>
                </table>
            </form>
        </td>
    </tr>
</table>
</body>
</html>