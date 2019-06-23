<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>出票口查询</title>
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
<c:set var="lotteryList" value="${myf:findAllLotteryCode()}"></c:set>
<c:set var="pageSize" value="20"></c:set>

<table width="100%" border="0" cellpadding="0" cellspacing="0"
       class="table">
    <tr>
        <td class="title" style="text-align:left;" >
            彩期管理 >> 出票口彩期查询
        </td>
    </tr>
    <tr>
        <td style="padding: 4px;">
              <form name="form1" method="post"
                  action="${pageContext.request.contextPath}/manages/issueManagesServlet?action=postIssueQuery">
                <table width="100%" border="1" cellpadding="4" cellspacing="0" class="seach">
                    <tr>
                        <td style="width:5%">彩种</td>
                        <td style="width:10%">
                            <select name="lotteryCode">
                                <option value=""
                                        <c:if test="${'' == lotteryIssue.lotteryCode }">selected</c:if>>
                                                                                                                       全部
                                </option>
                                <c:forEach var="lottery" items="${lotteryList}">
                                    <option value="${lottery.key}"
                                            <c:if test="${lottery.key == lotteryCode}">selected</c:if> >
                                            ${lottery.value.name }
                                    </option>
                                </c:forEach>
                            </select>
                        </td>
                        
                        <td align="right">出票口</td>
    					<td align="left">
        					<select name="postCode">
            					<option value=""
                    				<c:if test="${postCode == ''}">selected</c:if>>全部
            					</option>
            					<c:forEach var="postBean" items="${myf:getPostMaps()}">
                				<option value="${postBean.code}"
                        			<c:if test="${postCode == postBean.code}">selected</c:if>>${postBean.name}
                				</option>
            					</c:forEach>
            					<option value="00"
                    				<c:if test="${postCode == '00'}">selected</c:if>>待分配
            					</option>
        					</select>
    					</td>
                        
                        <td colspan="4" align="right">
                            <input type="submit" name="Submit" class="submit" value="查询"
                                   style="width: 64px; border: none">
                        </td>
                    </tr>
                </table>
            </form>
            <table width="100%" border="1" cellpadding="4" cellspacing="0" class="content">
                <tr style="display: none;"></tr>
                <tr id="one">
                    <td width="5%">序号</td>
                    <td width="10%">游戏名称</td>
                    <td width="20%">出票口</td>
                    <td width="15%">期次</td>
                    <td width="10%">期次状态</td>
                    <td width="20%">官方时间</td>
                    <td width="20%">时间</td>
                </tr>
                <c:if test="${requestScope.postIssueList==null}">
                    <tr>
                        <td colspan="6" align="center">请在上面填写筛选条件，并点击查询按钮来查询数据！</td>
                    </tr>
                </c:if>
                <c:set var="splitLine" value="1"/>
                <c:set var="indexTag" value="0"/>
                <c:set var="indexTemp" value="0"/>
                <c:forEach var="lottery" items="${requestScope.postIssueList}" varStatus="cont">
                    <c:if test="${myf:getLotteryChinaName(lottery.lotteryCode) == null}">
                        <c:set var="indexTag" value="${indexTag + 1}"/>
                    </c:if>
                    <c:if test="${myf:getLotteryChinaName(lottery.lotteryCode) != null}">
                        <c:set var="indexTemp" value="${cont.count - indexTag}"/>
                        <tr>
                            <td align="center">${cont.count - indexTag}</td>
                            <td align="center">${myf:getLotteryChinaName(lottery.lotteryCode)}</td>
                            <td align="center">${myf:getPostCodeName(lottery.postCode)}</td>
                            
                            <td align="left">
                                	本地期次:${lottery.name}期
                            		<br/>
                            		缓存期次:${lottery.memcachedIssue}
                            		<c:if test="${lottery.memcachedIssue != null && lottery.memcachedIssue != ''}">期</c:if>
                            </td>
                            <td  align="center">
                            
                            <c:if test="${lottery.status==0}">未开售 </c:if>
                            <c:if test="${lottery.status==1}">已开售 </c:if>
                            <c:if test="${lottery.status==2}">已取消</c:if>
                            <c:if test="${lottery.status==3}">已结期</c:if>
                            
                            </td>
                            
                            <td align="left">
                            开售时间:<fmt:formatDate value="${lottery.startTime}" pattern="yy-MM-dd HH:mm:ss"/>
                            <br/>
                            结期时间:<fmt:formatDate value="${lottery.endTime}" pattern="yy-MM-dd HH:mm:ss"/>
                            </td>
                            <td align="left">
                             接收时间:${lottery.backup1}
                  </td>          
                        </tr>
                    </c:if>
                </c:forEach>
            </table>
        </td>
    </tr>
</table>
</body>
</html>
