<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>添加角色</title>
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
            $("#subBtn").click(function() {
                if ($("#name").val()!='') {
                    if (!/^[\u4E00-\u9FFF|0-9|\w|\-|\_|\.]+$/.test($("#name").val())) {
                        alert("角色名只能只能输入英文、中文、数字和“.”、“-”、“_”！");
                        return ;
                    }
                    $("#form1").submit();
                }else{
                    alert("角色名不能为空！");
                }
            });
        });

    </script>
</head>

<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
    <tr>
        <td class="title">
            添加角色
        </td>
    </tr>
    <tr>
        <td style="padding: 4px;">
            <form id="form1" method="post"
                  action="${pageContext.request.contextPath}/manages/purviewGroupServlet?action=add">
                <table width="100%" border="1" cellpadding="4" cellspacing="0" class="seach">
                    <tr>
                        <td width="20%" align="right">
                            角色名
                        </td>
                        <td><input type="text" id="name" name="name"/></td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                            <input type="button" id="subBtn" value="添加" class="submit"
                                   style="width: 64px; border: none"/>
                            <input type="button" name="Submit" value="返回" class="submit"
                                   style="width: 64px; border: none" onclick="javascript:history.go(-1);">
                        </td>
                    </tr>
                </table>
            </form>
        </td>
    </tr>
</table>
</body>
</html>