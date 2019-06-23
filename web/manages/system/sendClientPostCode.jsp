<%@page import="com.cndym.utils.Utils"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>联盟列表</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/validator.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/page.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/tool.jquery.cookie.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/page.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/manages.css">
    <script type="text/javascript">
    	function jsSelectIsExitItem(){
    		 document.getElementById("item2").options.length=0;
    		 <%
    		 	String flag="LotteryType";
    		 %>
    		 
    	}
    </script>
</head>

<body>
<c:if test="${requestScope.msg != null}">
    <script type="text/javascript">
        alert('${requestScope.msg}');
        window.close();
    </script>
</c:if>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
    <tr>
        <td class="title" style="text-align:left;">
            出票商-彩种管理
        </td>
    </tr>
    <tr>
        <td colspan="2" style="padding: 4px;">
            <form name="form1" id="form1" method="post"
                  action="${pageContext.request.contextPath}/manages/memberManagesServlet?action=memberList">
                <input type="hidden" name="startPage" id="startPage"/>
                <input type="hidden" name="endPage" id="endPage"/>
                <table width="100%" border="1" cellpadding="4" cellspacing="0" class="seach">
                    <tr>
                        <td>
                            <select name="item1" id="item1" onchange="jsSelectIsExitItem();">
                                <option value="1">出票口</option>
                                <option value="2">彩种</option>
                            </select>
                        </td>
                        <td>
                            <select name="item2" id="item2">
                            <c:forEach items="${myf:getMemberList()}" var="member">
                                    <option value="${member.sid}"
                                            <c:if test="${member.sid == sid}">selected</c:if>>${member.name}</option>
                            </c:forEach>
                            		<!--  
                            	<c:forEach items="${myf:findAllLotteryCode()}" var="entry">
                            		<c:forEach items="${entry.value}" var="lottery">
                                    	<option value="${lottery.code}">${lottery.name}</option>
                            		</c:forEach>
                            	</c:forEach>
                            		-->
                            </select>
                        </td>

                        <td align="right"><input type="submit" name="Submit" class="submit" value="查询"
                                                 style="width: 64px; border: none"></td>


                    </tr>

                </table>
            </form>
            <table width="100%" border="1" cellpadding="4" cellspacing="0" class="content">
                <tr id="one">
                    <td align="center" width="5%">序号</td>
                    <td>出票商名称</td>
                    <td>彩种</td>
                    <td>操作</td>
                </tr>
                      <c:forEach items="${postMap}" var="entry" varStatus="cont">
                        <tr>
                            <td align="center">
                                   ${cont.count}
                            </td>
                            <td align="center">
                                   	${entry.key}
                            </td>
                            <td align="center">
                            	<c:if test="${not empty entry.value}">
                            		<c:forEach items="${entry.value}" var="postCode">
                            			 <input style="width: 30px;border: none;vertical-align:middle;" type="checkbox"
                                       		name="postCode" id="check${cont.count}"
                                       		value="${postCode.lotteryCode}"
                                       <c:if test="${postCode.status == 1}">checked</c:if>>
                            			${myf:getLotteryChinaName(postCode.lotteryCode)}
                            		</c:forEach>
                            	</c:if>
                            </td>
                            <td align="center">
                                    ${myf:getLotteryChinaName('007')}
                            </td>
                            
                        </tr>
                      </c:forEach>
            </table>
        </td>
    </tr>
</table>
<script>
    function ifLink(str) {
        var bln = confirm(str);
        return bln;
    }
</script>
</body>
</html>
