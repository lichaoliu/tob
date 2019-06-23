<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>日志管理</title>
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
<c:set var="action" value="${action}"></c:set>
<c:set var="name" value="${issue}"></c:set>
<table width="100%" border="0" cellpadding="0" cellspacing="0"
       class="table">
    <tr>
        <td class="title" style="text-align:left;">
            系统状态监控 >> 日志管理
        </td>
    </tr>
    <tr>
        <td style="padding: 4px;">
            <form name="form1" method="post"
                  action="${pageContext.request.contextPath}/manages/systemManagesServlet?action=getLogList">
                <table width="100%" border="1" cellpadding="4" cellspacing="0" class="seach">
                    <tr>
                        <td>
                            操作者
                        </td>
                        <td><input type="text" name="adminName" value="${requesScope.adminName}" style="width:200px">
                        </td>
                        <td>操作类型</td>
                        <td>
                            <select name="operatingType">
                                <option value="">全部</option>
                                <option value="1" <c:if test="${operatingType == 1}">selected</c:if>>登录退出</option>
                                <option value="2" <c:if test="${operatingType == 2}">selected</c:if>>日终处理</option>
                                <option value="3" <c:if test="${operatingType == 3}">selected</c:if>>会员管理</option>
                                <option value="4" <c:if test="${operatingType == 4}">selected</c:if>>投注管理</option>
                                <option value="5" <c:if test="${operatingType == 5}">selected</c:if>>派奖处理</option>
                                <option value="6" <c:if test="${operatingType == 6}">selected</c:if>>提醒管理</option>
                                <option value="7" <c:if test="${operatingType == 7}">selected</c:if>>系统管理</option>
                                <option value="8" <c:if test="${operatingType == 8}">selected</c:if>>录入足彩彩期</option>
                                <option value="9" <c:if test="${operatingType == 9}">selected</c:if>>启动算奖</option>
                                <option value="10" <c:if test="${operatingType == 10}">selected</c:if>>录入开奖号码</option>
                                <option value="11" <c:if test="${operatingType == 11}">selected</c:if>>录入体彩开奖公告</option>
                                <option value="12" <c:if test="${operatingType == 12}">selected</c:if>>接入代理管理</option>
                                <option value="13" <c:if test="${operatingType == 13}">selected</c:if>>批量录入彩期</option>
                                <option value="14" <c:if test="${operatingType == 14}">selected</c:if>>德彩福彩出票口</option>
                                <option value="15" <c:if test="${operatingType == 15}">selected</c:if>>德彩体彩出票口</option>
                                <option value="16" <c:if test="${operatingType == 16}">selected</c:if>>北京单场出票口</option>
                                <option value="17" <c:if test="${operatingType == 17}">selected</c:if>>竞彩彩期管理</option>
                            </select>
                        </td>
                        <td>
                            描述
                        </td>
                        <td>
                            <input type="text" name="descriptionMsg" value="${descriptionMsg}" style="width:260px"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            操作日期
                        </td>
                        <td>
                            <input type="text" onClick="WdatePicker()" name="startTime" value="${startTime }" readonly
                                   style="width:83px"/>
                            - <input class="zj_time" type="text" onClick="WdatePicker()" name="endTime"
                                     value="${endTime }" readonly style="width:83px"/>
                        </td>
                        <td>
                            操作者ip地址
                        </td>
                        <td>
                            <input type="text" name="ip" value="${ip}" style="width:200px"/>
                        </td>
                        <td align="right" colspan="2"><input type="submit" name="Submit" class="submit" value="查询"
                                                             style="width: 64px; border: none">
                        </td>
                    </tr>
                </table>
            </form>
            <table width="100%" border="1" cellpadding="2" cellspacing="0" class="content">
                <c:if test="${pageBean.pageContent!=null}">
                    <tr>
                        <td align="left" colspan="5">
                            <span style="color:#0033cc;font-weight: bold;">[本次查询结果共${pageBean.itemTotal }条记录]</span>
                        </td>
                    </tr>
                </c:if>
                <tr id="one">
                    <td width="5%">
                        序号
                    </td>
                    <td width="10%">
                        操作者
                    </td>
                    <td width="12%">
                        操作类型
                    </td>
                    <td>
                        描述
                    </td>
                    <td width="22%">
                        操作时间
                    </td>
                </tr>
                <c:if test="${pageBean.pageContent==null}">
                    <tr>
                        <td colspan="6" align="center">请在上面填写筛选条件，并点击查询按钮来查询数据！</td>
                    </tr>
                </c:if>
                <c:if test="${pageBean.pageContent!=null}">
                    <c:forEach var="objectList" items="${pageBean.pageContent}" varStatus="count">
                        <tr>
                            <td align="center">
                                    ${(requestScope.pageSize*(pageBean.pageId-1))+count.count}
                            </td>
                            <td align="center">
                                    ${objectList.adminName }
                            </td>
                            <td align="center">
                                    ${myf:getManagesLogType(objectList.operatingType)}
                            </td>
                            <td>
                                    ${objectList.description }
                            </td>
                            <td>
                                时间:<fmt:formatDate value="${objectList.createTime }"
                                                   pattern="yyyy-MM-dd HH:mm:ss"/><br/>
                                IP:${objectList.ip }
                            </td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="5" align="right">
                            <page:pageTag page="${requestScope.page}" pageSize="${requestScope.pageSize}"
                                          total="${pageBean.itemTotal}"
                                          gotoURI="${pageContext.request.contextPath}/manages/systemManagesServlet?action=getLogList"/>
                        </td>
                    </tr>
                </c:if>
            </table>
        </td>
    </tr>
</table>
</body>
<script>
    function ifLink(str) {
        var bln = confirm(str);
        return bln;
    }
</script>

<script type="text/javascript">
</script>


</html>