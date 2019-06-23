<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>系统配置-->添加</title>
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
<form name="form1" method="post" action="<%=request.getContextPath()%>/manages/issueManagesServlet"
      onsubmit="return Validator.Validate(this,3)">
    <input type="hidden" name="action" value="${action == null ? param.action : action }"/>
    <table width="100%" border="0" cellpadding="0" cellspacing="0"
           class="table">
        <tr>
            <td class="title">期次管理</td>
        </tr>
        <tr>

            <td style="padding: 4px;">
                <table width="100%" border="1" cellpadding="4" cellspacing="0"
                       bordercolor="#bbbbbb" bgcolor="#F1F1F1" class="mainTab"
                       style="margin-top: 5px;">
                    <span align="center"><span style="color:#0033cc;font-weight: bold;">[${success}</span>
                    <tr>
                        <input type="hidden" name="issueId" value="${issue.id }"/>
                        <td width="30%" align="right">
                            彩种：
                        </td>
                        <td style="color: #f00">
                            <select name="lotteryCode">
                                <option value="" <c:if test="${'' == issue.lotteryCode }">selected</c:if>>
                                    请选择
                                </option>
                                <c:forEach items="${myf:findAllLotteryCode()}" var="lotteryCodeMap">
                                    <option value="${lotteryCodeMap.key }"
                                            <c:if test="${lotteryCodeMap.key == issue.lotteryCode }">selected</c:if> >
                                            ${lotteryCodeMap.value.name }
                                    </option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td width="30%" align="right">
                            期次
                        </td>
                        <td>
                            <input type="text" name="issueName" value="${issue.name }" dataType="Integer"
                                   msg="确认已输入正确的期次，并且首尾不能有空格" style="width:50%">
                        </td>
                    </tr>

                    <tr>
                        <td width="30%" align="right">
                            开 期 时 间 ：
                        </td>
                        <td>
                            <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" name="startTime"
                                   value="<fmt:formatDate  value="${issue.startTime}"  pattern="yyyy-MM-dd HH:mm:ss"/> "
                                   dataType="Require" msg="请输入正确的时间" style="width:50%"><span style="color:#0033cc;font-weight: bold;">[格式 :2010-01-01
                            20:42:53</span>
                        </td>
                    </tr>
                    <tr>
                        <td width="30%" align="right">
                            官方结期时间：
                        </td>
                        <td>
                            <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" name="endTime"
                                   value="<fmt:formatDate  value="${issue.endTime}"  pattern="yyyy-MM-dd HH:mm:ss"/> "
                                   dataType="Require" msg="请输入正确的时间" style="width:50%"><span style="color:#0033cc;font-weight: bold;">[格式 :2010-01-01
                            20:42:53</span>
                        </td>
                    </tr>

                    <tr>
                        <td width="30%" align="right">
                            单式结期时间：
                        </td>
                        <td>
                            <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" name="simplexTime"
                                   value="<fmt:formatDate  value="${issue.simplexTime}"  pattern="yyyy-MM-dd HH:mm:ss"/> "
                                   dataType="Require" msg="请输入正确的时间" style="width:50%"><span style="color:#0033cc;font-weight: bold;">[格式 :2010-01-01
                            20:42:53</span>
                        </td>
                    </tr>
                    <tr>
                        <td width="30%" align="right">
                            复式结期时间：
                        </td>
                        <td>
                            <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" name="duplexTime"
                                   value="<fmt:formatDate  value="${issue.duplexTime}"  pattern="yyyy-MM-dd HH:mm:ss"/> "
                                   dataType="Require" msg="请输入正确的时间" style="width:50%"><span style="color:#0033cc;font-weight: bold;">[格式 :2010-01-01
                            20:42:53</span>
                        </td>
                    </tr>
                    <tr>
                        <td width="30%" align="right">
                            开 奖 时 间 ：
                        </td>
                        <td>
                            <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" name="kjTime"
                                   value="<fmt:formatDate  value="${issue.bonusTime}"  pattern="yyyy-MM-dd HH:mm:ss"/> "
                                   dataType="Require" msg="请输入正确的时间" style="width:50%"><span style="color:#0033cc;font-weight: bold;">[格式 :2010-01-01
                            20:42:53</span>
                        </td>
                    </tr>
                    <tr>
                        <td width="30%" align="right">
                            期 次 状 态 ：
                        </td>
                        <td>
                            <select name="issueStatus">
                                <option value="" <c:if test="${'' == issue.status}">selected</c:if>>
                                    全部
                                </option>
                                <option value="0" <c:if test="${'0' == issue.status}">selected</c:if>>
                                    预约期
                                </option>
                                <option value="1" <c:if test="${'1' == issue.status}">selected</c:if>>
                                    当前期
                                </option>
                                <option value="2" <c:if test="${'2' == issue.status}">selected</c:if>>
                                    暂停
                                </option>
                                <option value="3" <c:if test="${'3' == issue.status}">selected</c:if>>
                                    已截止
                                </option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td width="30%" align="right">
                            开 奖 号 码 ：
                        </td>
                        <td>
                            <input type="text" name="bonusNumber" value="${issue.bonusNumber }">
                        </td>
                    </tr>
                    <tr>
                        <td width="30%" align="right">
                            下期奖池信息：
                        </td>
                        <td>
                            <input type="text" name="prizePool" value="${issue.prizePool }">
                        </td>
                    </tr>
                    <tr>
                        <td width="30%" align="right">
                            是 否 派 奖 :
                        </td>
                        <td>
                            <select name="bonusStatus">
                                <option value="0" <c:if test="${issue.bonusStatus == '1' }">selected</c:if>>是</option>
                                <option value="1"
                                        <c:if test="${issue.bonusStatus == '0' }">selected</c:if> >否
                                </option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td width="30%" align="right">
                            是 否 发 票 :
                        </td>
                        <td>
                            <select name="sendStatus">
                                <option value="0" <c:if test="${issue.sendStatus == '0' }">selected</c:if>>不发票</option>
                                <option value="1"
                                        <c:if test="${issue.sendStatus == '1' }">selected</c:if> >发票
                                </option>

                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                            <input type="submit" name="button" id="button" value="提交">
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                            <span style="color:#0033cc;font-weight: bold;">[${msg}</span>
                        </td>
                    </tr>
                </table>

            </td>
        </tr>
    </table>
</form>
${errorMsg}
</body>
</html>