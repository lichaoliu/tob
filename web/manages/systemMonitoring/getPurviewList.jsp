<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>权限管理</title>
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
<c:set var="pageSize" value="20"></c:set>
<form name="form1" method="post"
      action="${pageContext.request.contextPath}/manages/systemManagesServlet?action=getPurviewUrlList">
    <table width="100%" border="0" cellpadding="0" cellspacing="0"
           class="table">
        <tr>
            <td class="title">
                权限列表
            </td>
        </tr>
        <tr>
            <td style="padding: 4px;">

                <table width="100%" border="1" cellpadding="4" cellspacing="0"
                       bordercolor="#bbbbbb" bgcolor="#F1F1F1" class="mainTab"
                       style="margin-top: 5px;">
                    <tr>
                        <td>
                            用户:${manages.userName}
                        </td>
                    </tr>
                </table>

            </td>
        </tr>
    </table>
    <table width="100%" border="0" cellpadding="0" cellspacing="0"
           class="table" style="margin-top:5px;">
        <!--<tr>
        <td class="title">
            	所选用户权限
        </td>
         
    </tr>
    <c:if test="${purviewUrlBean.pageContent!=null}">
         	<c:forEach var="purviewBeanList" items="${purviewUrlBean.pageContent}">
         		<tr>
         			<td><input type="checkbox" name="codes" checked="checked" value="${purviewBeanList[1].code }"/>${purviewBeanList[1].name }</td>
         		</tr>
         	</c:forEach>
   </c:if>
    -->
        <tr>
            <td class="title">
                管理员权限
            </td>
        </tr>
        <c:forEach var="puf" items="${purviewUrlFatherList}">
            <tr>
                <td>
                    <a href="${pageContext.request.contextPath}/manages/systemManagesServlet?action=getPurviewUrlList&adminId=${adminId }&codeIndex1=${puf.code_father }">${puf.name_father }</a>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td style="padding: 4px;">
                <table width="100%" border="1" cellpadding="2" cellspacing="0"
                       bordercolor="#bbbbbb" bgcolor="#F1F1F1" class="mainTab"
                       style="margin-top: 5px;">
                    <c:if test="${pageBean.pageContent!=null}">
                        <tr>
                            <td>
                                <input type="checkbox" name="box1" onclick="checkNewCode('newCodes')"/>全选
                            </td>
                        </tr>
                        <c:set value="1" var="itemm"/>
                        <c:forEach var="objectList" items="${pageBean.pageContent}">
                            <c:if test="${itemm == 1}">
                                ${objectList.nameFather }
                                <c:set value="2" var="itemm"/>
                            </c:if>
                            <tr>
                                <td>
                                    <c:set value="1" var="myCode"/>
                                    <c:if test="${purviewUrlBean.pageContent!=null}">
                                        <c:forEach var="purviewBeanList" items="${purviewUrlBean.pageContent}">
                                            <c:if test="${purviewBeanList[1].code == objectList.code}">
                                                <input type="checkbox" name="newCodes" checked="checked"
                                                       value="${objectList.code }"/>${objectList.name }
                                                <c:set value="${objectList.code }" var="myCode"/>
                                            </c:if>
                                        </c:forEach>
                                    </c:if>
                                    <c:if test="${myCode != objectList.code}">
                                        <input type="checkbox" name="newCodes"
                                               value="${objectList.code }"/>${objectList.name }
                                    </c:if>

                                </td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td>
                                <input type="button" value="添加权限"
                                       onclick="forSubmit('${pageContext.request.contextPath}/manages/systemManagesServlet?action=updatePurview&adminId=${adminId }')"/>
                            </td>
                        </tr>
                    </c:if>

                </table>
            </td>
        </tr>
    </table>
</form>
<script type="text/javascript">
    var checkflag = "false";
    function checkNewCode(checkName) {
        var field = document.getElementsByName(checkName);
        if (checkflag == "false") {
            for (i = 0; i < field.length; i++) {
                field[i].checked = true;
            }
            checkflag = "true";
            return "Uncheck All";
        } else {
            for (i = 0; i < field.length; i++) {
                field[i].checked = false;
            }
            checkflag = "false";
            return "Check All";
        }
    }

    function forSubmit(url) {
        document.all.form1.action = url;
        document.all.form1.submit();
    }
</script>
</body>
</html>