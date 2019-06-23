<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>编辑接入商信息</title>
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

                var name = $("#name").val();
                if (name == '') {
                    alert("请输入接入商名称");
                    return;
                }

                var contactPerson = $("#contactPerson").val();
                if (contactPerson != "") {
                    if (!/^[\u4E00-\u9FFF]+$/.test(contactPerson)) {
                        alert("联系人只能输入中文");
                        return;
                    }
                }

                var cardCode = $("#cardCode").val();
                if (cardCode != "" && !IdCardValidate(cardCode)) {
                    alert("身份证号码格式错误！");
                    return;
                }

                var email = $("#email").val();
                if (email != "") {
                    if (!/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/g.test(email)) {
                        alert("邮箱格式错误！");
                        return;
                    }
                }

                var mobile = $("#mobile").val();
                if (mobile != "") {
                    if (!/^1{1}[3|5|8]{1}\d{9}$/g.test(mobile)) {
                        alert("手机号码格式错误！");
                        return;
                    }
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
            渠道管理 >> 新建接入商
        </td>
    </tr>
    <tr>
        <td style="padding: 4px;">
            <form name="form1" id="form1" method="post"
                  action="${pageContext.request.contextPath}/manages/memberManagesServlet?action=addMember">
                <table width="100%" border="0" cellpadding="4" cellspacing="0" class="seach_none">
                    <tr>
                        <td width="45%" align="right">
                            代理编号:
                        </td>
                        <td>
                            <input type="text" value="${requestScope.sid}" name="sid" readonly="readonly"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            代理名称:
                        </td>
                        <td>
                            <input type="text" name="name" size="20" id="name"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            公司名称:
                        </td>
                        <td>
                            <input type="text" name="companyName" size="20" id="companyName"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            联系人:
                        </td>
                        <td><input type="text" name="contactPerson" size="20" id="contactPerson"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            身份证号码:
                        </td>
                        <td><input type="text" name="cardCode" size="20" id="cardCode"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            联系手机:
                        </td>
                        <td><input type="text" name="mobile" size="20" id="mobile"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            邮箱:
                        </td>
                        <td><input type="text" name="email" size="20" id="email"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            公司地址:
                        </td>
                        <td>
                            <textarea rows="3" cols="30" name="address">${cooperation.companyAddress}</textarea>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            加密key:
                        </td>
                        <td><input type="text" name="privateKey" size="20" value="${requestScope.privateKey}" readonly="readonly"/>
                        </td>
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