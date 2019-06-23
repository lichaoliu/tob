<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>彩期查询</title>
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
        function onOpen(url) {
            window.open(url);
        }
    </script>
</head>
<body>
<c:set var="lotteryList" value="${myf:findNonJingCaiLotteryCode()}"></c:set>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
    <tr>
        <td class="title" style="text-align:left;">
            彩期管理 >> 普通彩期查询
        </td>
    </tr>
    <tr>
        <td style="padding: 4px;">


            <form name="form1" method="post"
                  action="${pageContext.request.contextPath}/manages/issueManagesServlet?action=zqIssueQuery">
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

                        <td style="width:5%">销售状态</td>
                        <td style="width:10%">
                            <select name="status">
                                <option value="" <c:if test="${'' == status}">selected</c:if>>全部</option>
                                <option value="0" <c:if test="${'0' == status}">selected</c:if>>预售</option>
                                <option value="1" <c:if test="${'1' == status}">selected</c:if>>销售中</option>
                                <option value="3" <c:if test="${'3' == status}">selected</c:if>>结期</option>
                            </select>
                        </td>
                        <td style="width:5%">自主算奖</td>
                        <td style="width:10%">
                            <select name="operatorsAward">
                                <option value="" <c:if test="${'' == operatorsAward }">selected</c:if>>全部</option>
                                <c:forEach var="award" items="${myf:getOperatorsAwardMap()}">
                                    <option value="${award.key}"
                                            <c:if test="${award.key == operatorsAward}">selected</c:if> >${award.value}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td style="width:5%">中心算奖</td>
                        <td style="width:10%">
                            <select name="bonusStatus">
                                <option value="" <c:if test="${'' == bonusStatus}">selected</c:if>>全部</option>
                                <option value="0" <c:if test="${'0' == bonusStatus}">selected</c:if>>未返奖</option>
                                <option value="1" <c:if test="${'1' == bonusStatus}">selected</c:if>>已返奖</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td style="width:5%">彩期范围</td>
                        <td style="width:15%">
                            <input type="text" name="issueNameStart" value="${issueNameStart}" class="input"
                                   style="width:45%">-
                            <input type="text" name="issueNameEnd" value="${issueNameEnd}" class="input"
                                   style="width:45%">
                        </td>
                        <td style="width:5%">开期时间</td>
                        <td style="width:15%">
                            <input style="width: 83px;" type="text" name="startTime" value="${startTime}"
                                   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:30%" readonly="readonly">-
                            <input style="width: 83px;" type="text" name="endTime"
                                   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                                   value="${endTime}" style="width:30%" readonly="readonly">
                        </td>
                        <td colspan="4" align="right">
                            <input type="submit" name="Submit" class="submit" value="查询"
                                   style="width: 64px; border: none">
                        </td>


                    </tr>
                </table>
            </form>
            <table width="100%" border="1" cellpadding="4" cellspacing="0" class="content">
                <c:if test="${pageBean.pageContent!=null}">
                    <tr>
                        <td align="left" colspan="11">
                            <span style="color:#0033cc;font-weight: bold;">[本次查询结果共${pageBean.itemTotal }条记录]</span>
                        </td>
                    </tr>
                </c:if>
                <tr id="one">
                    <td width="3%">序号</td>
                    <td width="10%">彩种</td>
                    <td width="13%">官方时间</td>
                    <td width="13%">止售时间</td>
                    <td width="13%">开奖时间</td>
                    <td>开奖号码</td>
                    <td width="7%">销售状态</td>
                    <td width="7%">自主算奖</td>
                    <td width="7%">中心算奖</td>
                    <td width="8%">操作</td>
                </tr>
                <c:if test="${pageBean.pageContent==null}">
                    <tr>
                        <td colspan="10" align="center">请在上面填写筛选条件，并点击查询按钮来查询数据！</td>
                    </tr>
                </c:if>
                <c:if test="${pageBean.pageContent!=null}">
                    <c:forEach var="issueList" items="${pageBean.pageContent}" varStatus="cont">
                        <tr>
                            <td align="center">
                                    ${(page-1) * requestScope.pageSize + cont.count}
                            </td>
                            <td>
                                彩种:${myf:getLotteryChinaName(issueList.lotteryCode)}
                                <br/>
                                期次:<a
                                    href="${pageContext.request.contextPath}/manages/issueManagesServlet?action=issueDetailInfo&lotteryCode=${issueList.lotteryCode}&issue=${issueList.name}"
                                    target="_black">${issueList.name}</a>
                            </td>
                            <td>
                                开期:<fmt:formatDate value="${issueList.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                <br/>
                                结期:<fmt:formatDate value="${issueList.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                            </td>
                            <td>
                                单式:<fmt:formatDate value="${issueList.simplexTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                <br/>
                                复式:<fmt:formatDate value="${issueList.duplexTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                            </td>
                            <td>
                                开奖:<fmt:formatDate value="${issueList.bonusTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                <br/>
                                兑奖:<fmt:formatDate value="${myf:operationDate(issueList.bonusTime,'d',60)}"
                                                   pattern="yyyy-MM-dd HH:mm:ss"/>
                            </td>
                                <%--<td align="right">${issueList.prizePool}</td>--%>
                            <td>${issueList.bonusNumber}</td>
                            <td align="center">
                                    ${myf:getIssueStatus(issueList.status)}
                            </td>
                            <td align="center">
                                    ${myf:getOperatorsAward(issueList.operatorsAward)}
                            </td>
                            <td align="center">
                                <c:if test="${issueList.status!=3}">
                                    --
                                </c:if>
                                <c:if test="${issueList.status==3}">
                                    <c:if test="${issueList.bonusStatus==0}">
                                        未返奖
                                    </c:if>
                                    <c:if test="${issueList.bonusStatus==1}">
                                        已返奖
                                    </c:if>
                                </c:if>
                            </td>


                            <td align="center">
                                <c:if test="${issueList.status == 3 && issueList.lotteryCode != '006'&& issueList.lotteryCode != '107'&& issueList.lotteryCode != '106'}">
                                    <a href="${pageContext.request.contextPath}/manages/issueManagesServlet?action=getIssueMsg&lotteryCode=${issueList.lotteryCode}&issue=${issueList.name}"
                                       target="_black">编辑公告</a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="11" align="right">
                            <page:pageTag page="${requestScope.page}" pageSize="${requestScope.pageSize}"
                                          total="${pageBean.itemTotal}"
                                          gotoURI="${pageContext.request.contextPath}/manages/issueManagesServlet?action=zqIssueQuery"/>
                        </td>
                    </tr>
                </c:if>
            </table>
        </td>
    </tr>
</table>
</body>
</html>
