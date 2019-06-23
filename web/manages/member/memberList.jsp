<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>联盟列表</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="Cache-Control" content="max-age=0" forua="true"/>
    <meta http-equiv="Cache-Control" content="no-cache" forua="true"/>
    <meta http-equiv="Cache-Control" content="must-revalidate" forua="true"/>
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
                  action="${pageContext.request.contextPath}/manages/memberManagesServlet?action=memberList">
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
                        
                        <td>彩票名称</td>
                        <td>
                            <select name="lotteryCode" id="lotteryCode">
                                <option value="">全部</option>
                                <c:forEach items="${myf:findAllLotteryCode()}" var="lotteryCodeMap">
                                     <option value="${lotteryCodeMap.key }"
                                         <c:if test="${lotteryCodeMap.key == lotteryCode }">selected</c:if> >
                                         ${lotteryCodeMap.value.name }
                                     </option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>彩票状态</td>
                        <td>
                            <select name="lotteryStatus">
                                <option value="" <c:if test="${lotteryStatus eq ''}">selected</c:if>>全部</option>
                                <option value="1" <c:if test="${lotteryStatus == 1}">selected</c:if>>已接入</option>
                                <option value="2" <c:if test="${lotteryStatus == 2}">selected</c:if>>未接入</option>
                            </select>
                        </td>

                        <td align="right"><input type="submit" name="Submit" class="submit" value="查询"
                                                 style="width: 64px; border: none"></td>
                    </tr>
                </table>
            </form>
            <table width="100%" border="1" cellpadding="4" cellspacing="0" class="content">
                <c:if test="${pageBean.pageContent!=null}">
                    <tr>
                        <td align="left" colspan="6">
                            <span style="color:#0033cc;font-weight: bold;">[本次查询结果共${pageBean.itemTotal }条记录]</span>
                        </td>
                    </tr>
                </c:if>
                <tr id="one">
                    <td align="center" width="5%">序号</td>
                    <td>接入商名称</td>
                    <td>接入编号</td>
                    <td>帐户(元)</td>
                    <td>状态</td>
                    <td>操作</td>
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
                                总投注额:<fmt:formatNumber value="${cooperation[1].payTotal}" pattern="0.00"/>
                                <br/>
                                总中奖额:<fmt:formatNumber value="${cooperation[1].bonusTotal}" pattern="0.00"/>
                                <br/>
                                可用充值:<fmt:formatNumber value="${cooperation[1].rechargeAmount}" pattern="0.00"/>
                                <br/>
                                可用奖金:<fmt:formatNumber value="${cooperation[1].bonusAmount}" pattern="0.00"/>
                                <br/>
                                可用额度:<c:if
                                    test="${(cooperation[1].rechargeAmount + cooperation[1].bonusAmount) >= 10000}"><font
                                    color="blue"></c:if>
                                <c:if test="${(cooperation[1].rechargeAmount + cooperation[1].bonusAmount) < 10000}"><font
                                        color="red"></c:if>
                                    <fmt:formatNumber
                                            value="${cooperation[1].rechargeAmount + cooperation[1].bonusAmount}"
                                            pattern="0.00"/></font>
                            </td>
                            <td align="center">
                                <c:if test="${cooperation[0].status == 2}">冻结</c:if>
                                <c:if test="${cooperation[0].status == 1}">开通</c:if>
                            </td>
                            <td align="center">
                                <a href="${pageContext.request.contextPath}/manages/memberManagesServlet?action=detailMember&sid=${cooperation[0].sid}"
                                   target="_blank">详细</a> |
                                <a href="${pageContext.request.contextPath}/manages/memberManagesServlet?action=preEditMember&sid=${cooperation[0].sid}">
                                    修改</a> |
                                <c:if test="${cooperation[0].status == 2}">
                                    <a href="${pageContext.request.contextPath}/manages/memberManagesServlet?action=editStatus&sid=${cooperation[0].sid}&status=1"
                                       onclick="return ifLink('确认解锁该商户？')">解锁</a>
                                </c:if>
                                <c:if test="${cooperation[0].status == 1}">
                                    <a href="${pageContext.request.contextPath}/manages/memberManagesServlet?action=editStatus&sid=${cooperation[0].sid}&status=2"
                                       onclick="return ifLink('确认锁定该商户？')">冻结</a>
                                </c:if>
                                |<a href="javascript:void(0)" onclick="editLotteryId('${cooperation[0].sid}');" >彩种</a>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="6" align="right">
                            <page:pageTag page="${requestScope.page}" pageSize="${requestScope.pageSize}"
                                          total="${pageBean.itemTotal}"
                                          gotoURI="${pageContext.request.contextPath}/manages/memberManagesServlet?action=memberList"/>
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
    function editLotteryId(sid){
        //window.showModalDialog("${pageContext.request.contextPath}/manages/memberManagesServlet?action=editLotteryId&sid="+sid,new Array(),"dialogHeight:360px;dialogWidth:640px");
        var iHeight = 350;
    	var iWidth = 300;
    	var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
    	var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
    	window.open('<%=request.getContextPath()%>/manages/memberManagesServlet?action=editLotteryId&sid='+sid,'newwindow','height="+iHeight+",width="+iWidth+",top="+iTop+",left="+iLeft+",toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
    }
</script>
</body>
</html>
