<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>游戏查询</title>
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
<c:set var="lotteryList" value="${myf:findAllLotteryCode()}"></c:set>
<c:set var="pageSize" value="20"></c:set>

<table width="100%" border="0" cellpadding="0" cellspacing="0"
       class="table">
    <tr>
        <td class="title" style="text-align:left;">
            彩期管理 >> 彩期控制
        </td>
        <td class="title" style="text-align:right">
            [<a href="${pageContext.request.contextPath}/manages/lottery/saveIssue.jsp">录入彩期</a>]&nbsp;&nbsp; [<a href="${pageContext.request.contextPath}/manages/lottery/batchSaveIssue.jsp">批量录入彩期</a>]
        </td>
    </tr>
    <tr>
        <td colspan="2" style="padding: 4px;">
            <table width="100%" border="1" cellpadding="4" cellspacing="0" class="content">
                <tr style="display: none;"></tr>
                <tr id="one">
                    <td width="5%">序号</td>
                    <td width="10%">游戏名称</td>
                    <td width="12%">官方历史期</td>
                    <td width="12%">官方预售期</td>
                    <td width="12%">销售中彩期</td>
                    <td width="10%">销售状态</td>
                    <td width="10%">出票状态</td>
                    <td width="15%">彩期操作</td>
                    <td>待开奖彩期</td>
                </tr>
                <c:if test="${requestScope.lotteryList==null}">
                    <tr>
                        <td colspan="6" align="center">请在上面填写筛选条件，并点击查询按钮来查询数据！</td>
                    </tr>
                </c:if>
                <c:set var="splitLine" value="1"/>
                <c:set var="indexTag" value="0"/>
                <c:set var="indexTemp" value="0"/>
                <c:forEach var="lottery" items="${requestScope.lotteryList}" varStatus="cont">
                    <c:if test="${myf:getLotteryChinaName(lottery.lotteryCode) == null}">
                        <c:set var="indexTag" value="${indexTag + 1}"/>
                    </c:if>
                    <c:if test="${myf:getLotteryChinaName(lottery.lotteryCode) != null}">
                        <c:set var="indexTemp" value="${cont.count - indexTag}"/>
                        <tr>
                            <td align="center">${cont.count - indexTag}</td>
                            <td align="center">
                            <a href="javascript:void(0)" onclick="editLotteryId('${lottery.lotteryCode}');">${myf:getLotteryChinaName(lottery.lotteryCode)}</a>
                            </td>

                            <td align="center">
                                共 <a
                                    href="${pageContext.request.contextPath}/manages/issueManagesServlet?action=zqIssueQuery&lotteryCode=${lottery.lotteryCode}&status=3&order=desc"
                                    target="_blank">${myf:fullTag(lottery.historyTotal,8)}</a> 期
                            </td>
                            <td align="center">
                                共 <a
                                    href="${pageContext.request.contextPath}/manages/issueManagesServlet?action=zqIssueQuery&lotteryCode=${lottery.lotteryCode}&status=0&order=asc"
                                    target="_blank">${myf:fullTag(lottery.nextTotal,8)}</a> 期
                            </td>
                            <td align="center">
                                <c:forEach var="sell" items="${lottery.sellIssue}">
                                    第 <a
                                        href="${pageContext.request.contextPath}/manages/issueManagesServlet?action=issueDetailInfo&lotteryCode=${lottery.lotteryCode}&issue=${sell["name"]}"
                                        target="_blank">${sell["name"]}</a> 期<br/>
                                </c:forEach>
                            </td>
                            <td align="center">
                                <c:if test="${myf:lotteryControl(lottery.lotteryCode)==0}">
                                    <span style="color: #00f">销售中</span>
                                </c:if>
                                <c:if test="${myf:lotteryControl(lottery.lotteryCode)==1}">
                                    <span style="color: #f00">已暂停</span>
                                </c:if>
                            </td>
                            <td align="center">
                                <c:if test="${myf:lotterySendControl(lottery.lotteryCode)==0}">
                                    <span style="color: #00f">出票中</span>
                                </c:if>
                                <c:if test="${myf:lotterySendControl(lottery.lotteryCode)==1}">
                                    <span style="color: #f00">已暂停</span>
                                </c:if>
                            </td>
                            <td align="center">
                                <c:if test="${myf:lotteryControl(lottery.lotteryCode)==0}">
                                    <a href="javascript:openWin('${pageContext.request.contextPath}/manages/issueManagesServlet?action=lotteryControl&lotteryCode=${lottery.lotteryCode}',this);">暂停销售</a>
                                </c:if>
                                <c:if test="${myf:lotteryControl(lottery.lotteryCode)==1}">
                                    <a href="${pageContext.request.contextPath}/manages/issueManagesServlet?action=lotteryControl&lotteryCode=${lottery.lotteryCode}">恢复销售</a>
                                </c:if>
                                |
                                <c:if test="${myf:lotterySendControl(lottery.lotteryCode)==0}">
                                    <a href="javascript:openWin('${pageContext.request.contextPath}/manages/issueManagesServlet?action=lotterySendControl&lotteryCode=${lottery.lotteryCode}',this);">暂停出票</a>
                                </c:if>
                                <c:if test="${myf:lotterySendControl(lottery.lotteryCode)==1}">
                                    <a href="${pageContext.request.contextPath}/manages/issueManagesServlet?action=lotterySendControl&lotteryCode=${lottery.lotteryCode}">恢复出票</a>
                                </c:if>
                            </td>
                            <td align="center">
                                <c:forEach var="preSell" items="${lottery.preSellIssue}">
                                    ${preSell["name"]}期<br/>
                                </c:forEach>
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>

                <tr>
                    <td align="center">${indexTemp+1}</td>
                    <td align="center"><a href="javascript:void(0)" onclick="editLotteryId(200);">竞彩足球</a></td>

                    <td align="center">
                        --
                    </td>
                    <td align="center">
                        --
                    </td>
                    <td align="center">
                        --
                    </td>
                    <td align="center">
                        <c:if test="${myf:lotteryControl(200)==0}">
                            <span style="color: #00f">销售中</span>
                        </c:if>
                        <c:if test="${myf:lotteryControl(200)==1}">
                            <span style="color: #f00">已暂停</span>
                        </c:if>
                    </td>
                    <td align="center">
                        <c:if test="${myf:lotterySendControl(200)==0}">
                            <span style="color: #00f">出票中</span>
                        </c:if>
                        <c:if test="${myf:lotterySendControl(200)==1}">
                            <span style="color: #f00">已暂停</span>
                        </c:if>
                    </td>
                    <td align="center">
                        <c:if test="${myf:lotteryControl(200)==0}">
                            <a href="javascript:openWin('${pageContext.request.contextPath}/manages/issueManagesServlet?action=lotteryControl&lotteryCode=200',this);">暂停销售</a>
                        </c:if>
                        <c:if test="${myf:lotteryControl(200)==1}">
                            <a href="${pageContext.request.contextPath}/manages/issueManagesServlet?action=lotteryControl&lotteryCode=200">恢复销售</a>
                        </c:if>

                        |
                        <c:if test="${myf:lotterySendControl(201)==0}">
                            <a href="javascript:openWin('${pageContext.request.contextPath}/manages/issueManagesServlet?action=lotterySendControl&lotteryCode=200',this);">暂停出票</a>
                        </c:if>
                        <c:if test="${myf:lotterySendControl(201)==1}">
                            <a href="${pageContext.request.contextPath}/manages/issueManagesServlet?action=lotterySendControl&lotteryCode=200">恢复出票</a>
                        </c:if>
                    </td>
                    <td align="center">
                        --
                    </td>
                </tr>

                <tr>
                    <td align="center">${indexTemp+2}</td>
                    <td align="center"><a href="javascript:void(0)" onclick="editLotteryId(201);">竞彩篮球</a></td>

                    <td align="center">
                        --
                    </td>
                    <td align="center">
                        --
                    </td>
                    <td align="center">
                        --
                    </td>
                    <td align="center">
                        <c:if test="${myf:lotteryControl(201)==0}">
                            <span style="color: #00f">销售中</span>
                        </c:if>
                        <c:if test="${myf:lotteryControl(201)==1}">
                            <span style="color: #f00">已暂停</span>
                        </c:if>
                    </td>
                    <td align="center">
                        <c:if test="${myf:lotterySendControl(201)==0}">
                            <span style="color: #00f">出票中</span>
                        </c:if>
                        <c:if test="${myf:lotterySendControl(201)==1}">
                            <span style="color: #f00">已暂停</span>
                        </c:if>
                    </td>
                    <td align="center">
                        <c:if test="${myf:lotteryControl(201)==0}">
                            <a href="javascript:openWin('${pageContext.request.contextPath}/manages/issueManagesServlet?action=lotteryControl&lotteryCode=201',this);">暂停销售</a>
                        </c:if>
                        <c:if test="${myf:lotteryControl(201)==1}">
                            <a href="${pageContext.request.contextPath}/manages/issueManagesServlet?action=lotteryControl&lotteryCode=201">恢复销售</a>
                        </c:if>
                        |
                        <c:if test="${myf:lotterySendControl(201)==0}">
                            <a href="javascript:openWin('${pageContext.request.contextPath}/manages/issueManagesServlet?action=lotterySendControl&lotteryCode=201',this);">暂停出票</a>
                        </c:if>
                        <c:if test="${myf:lotterySendControl(201)==1}">
                            <a href="${pageContext.request.contextPath}/manages/issueManagesServlet?action=lotterySendControl&lotteryCode=201">恢复出票</a>
                        </c:if>
                    </td>
                    <td align="center">
                        --
                    </td>
                </tr>

                <tr>
                    <td align="center">${indexTemp+3}</td>
                    <td align="center">北京单场</td>

                    <td align="center">
                        --
                    </td>
                    <td align="center">
                        --
                    </td>
                    <td align="center">
                        --
                    </td>
                    <td align="center">
                        <c:if test="${myf:lotteryControl(400)==0}">
                            <span style="color: #00f">销售中</span>
                        </c:if>
                        <c:if test="${myf:lotteryControl(400)==1}">
                            <span style="color: #f00">已暂停</span>
                        </c:if>
                    </td>
                    <td align="center">
                        <c:if test="${myf:lotterySendControl(400)==0}">
                            <span style="color: #00f">出票中</span>
                        </c:if>
                        <c:if test="${myf:lotterySendControl(400)==1}">
                            <span style="color: #f00"> 已暂停</span>
                        </c:if>
                    </td>
                    <td align="center">
                        <c:if test="${myf:lotteryControl(400)==0}">
                            <a href="javascript:openWin('${pageContext.request.contextPath}/manages/issueManagesServlet?action=lotteryControl&lotteryCode=400',this);">暂停销售</a>
                        </c:if>
                        <c:if test="${myf:lotteryControl(400)==1}">
                            <a href="${pageContext.request.contextPath}/manages/issueManagesServlet?action=lotteryControl&lotteryCode=400">恢复销售</a>
                        </c:if>
                        |
                        <c:if test="${myf:lotterySendControl(400)==0}">
                            <a href="javascript:openWin('${pageContext.request.contextPath}/manages/issueManagesServlet?action=lotterySendControl&lotteryCode=400',this);">暂停出票</a>
                        </c:if>
                        <c:if test="${myf:lotterySendControl(400)==1}">
                            <a href="${pageContext.request.contextPath}/manages/issueManagesServlet?action=lotterySendControl&lotteryCode=400">恢复出票</a>
                        </c:if>

                    </td>
                    <td align="center">
                        --
                    </td>
                </tr>

            </table>
        </td>
    </tr>
</table>
</body>
</html>
<script type="text/javascript">
    function openWin(url, obj) {
//        obj.target="_blank";
        obj.href = url;
        if (confirm("确定暂停？")) {
//            obj.click();
            window.location.href = url;
        }
    }
    function editLotteryId(lotteryCode){
        //window.showModalDialog("${pageContext.request.contextPath}/manages/memberManagesServlet?action=editLotteryId&sid="+sid,new Array(),"dialogHeight:360px;dialogWidth:640px");
        var iHeight = 350;
    	var iWidth = 300;
    	var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
    	var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
    	window.open('<%=request.getContextPath()%>/manages/issueManagesServlet?action=memberQueryByLotteryCode&lotteryCode='+lotteryCode,'newwindow','height="+iHeight+",width="+iWidth+",top="+iTop+",left="+iLeft+",toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no, status=no');
    }

</script>