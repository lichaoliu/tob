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
            渠道管理 >> 代理商管理
        </td>
        <td class="title" style="text-align:right">
            [<a href="${pageContext.request.contextPath}/manages/memberManagesServlet?action=preAddMember">新建接入商</a>]
        </td>
    </tr>
    <tr>
        <td colspan="2" style="padding: 4px;">
            <form name="form1" id="form1" method="post"
                  action="${pageContext.request.contextPath}/manages/adminManages?action=memberList">
                <input type="hidden" name="startPage" id="startPage"/>
                <input type="hidden" name="endPage" id="endPage"/>
                <table width="100%" border="1" cellpadding="4" cellspacing="0" class="seach">
                    <tr>
                        <td>接入商名称</td>
                        <td>
                            <select name="sid" id="sid">
                                <option value="">全部</option>
                                <c:forEach items="${myf:getMemberList()}" var="member">
                                    <option value="${member.sid}"
                                            <c:if test="${member.sid == sid}">selected</c:if>>${member.name}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>接入商状态</td>
                        <td>
                            <select name="status">
                                <option value="" <c:if test="${status eq ''}">selected</c:if>>全部</option>
                                <option value="1" <c:if test="${status == 1}">selected</c:if>>开通</option>
                                <option value="2" <c:if test="${status == 2}">selected</c:if>>冻结</option>
                            </select>
                        </td>

                        <td align="right"><input type="submit" name="Submit" class="submit" value="查询" style="width: 64px; border: none"></td>


                    </tr>

                </table>
            </form>
            <table width="100%" border="1" cellpadding="4" cellspacing="0" class="content">
                <c:if test="${pageBean.pageContent!=null}">
                    <tr>
                        <td align="left" colspan="6">
                            <span style="color:#0033cc;font-weight: bold;">[本次查询结果共${pageBean.itemTotal }条记录;共计金额<fmt:formatNumber value="${requestScope.sumAmount}" pattern="0.00"/>元]</span>
                        </td>
                    </tr>
                </c:if>
                <tr id="one">
                    <td align="center" width="5%">序号</td>
                    <td>接入商名称</td>
                    <td>接入编号</td>
                    <td>帐户(元)</td>
                    <td>状态</td>
                </tr>
                <c:if test="${pageBean.pageContent==null}">
                    <tr>
                        <td colspan="6" align="center">请在上面填写筛选条件，并点击查询按钮来查询数据！</td>
                    </tr>
                </c:if>
                <c:if test="${pageBean.pageContent!=null}">
                    <c:forEach var="cooperation" items="${pageBean.pageContent}" varStatus="cont">
                        <tr>
                            <td align="center">
                                    ${(page-1) * requestScope.pageSize + cont.count}
                            </td>
                            <td align="center">
                                    ${cooperation[0].name}
                            </td>
                            <td align="center">
                                    ${cooperation[0].sid}
                            </td>
                            <td>
                                                                                    可用额度:<c:if test="${(cooperation[1].rechargeAmount + cooperation[1].bonusAmount) >= 10000}"><font color="blue"></c:if>
                                <c:if test="${(cooperation[1].rechargeAmount + cooperation[1].bonusAmount) < 10000}"><font color="red"></c:if>
                                    <fmt:formatNumber value="${cooperation[1].rechargeAmount + cooperation[1].bonusAmount}" pattern="0.00"/></font>
                            </td>
                            <td align="center">
                                <c:if test="${cooperation[0].status == 2}">冻结</c:if>
                                <c:if test="${cooperation[0].status == 1}">开通</c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="6" align="right">
                            <page:pageTag page="${requestScope.page}" pageSize="${requestScope.pageSize}"
                                          total="${pageBean.itemTotal}"
                                          gotoURI="${pageContext.request.contextPath}/manages/adminManages?action=memberList"/>
                        </td>
                    </tr>
                </c:if>
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
