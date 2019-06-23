<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../jstl.jsp" %>
<html>
<head>
<title>出票业务管理系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta http-equiv="Cache-Control" content="max-age=0" forua="true"/>
<meta http-equiv="Cache-Control" content="no-cache" forua="true"/>
<meta http-equiv="Cache-Control" content="must-revalidate" forua="true"/>
<script type="text/javascript" src="/js/tool.jquery.js"></script>
<script>
    function findObj(theObj, theDoc) {

        var p, i, foundObj;

        if (!theDoc) theDoc = document;
        if ((p = theObj.indexOf("?")) > 0 && parent.frames.length) {
            theDoc = parent.frames[theObj.substring(p + 1)].document;
            theObj = theObj.substring(0, p);
        }
        if (!(foundObj = theDoc[theObj]) && theDoc.all) foundObj = theDoc.all[theObj];
        for (i = 0; !foundObj && i < theDoc.forms.length; i++)
            foundObj = theDoc.forms[i][theObj];
        for (i = 0; !foundObj && theDoc.layers && i < theDoc.layers.length; i++)
            foundObj = findObj(theObj, theDoc.layers[i].document);
        if (!foundObj && document.getElementById) foundObj = document.getElementById(theObj);

        return foundObj;
    }
</script>
<style type="text/css">
    body {
        margin: 0px;
        padding: 0px;
    }

    .table {
        background-color: #3C7CC3;
        border-bottom: 4px #004795 solid;
    }

    #menu div {
        margin-top: 4px;
        padding: 4px;
    }

    #menu div span {
        color: #0066FF;
        margin-right: 4px;
    }

    td {
        font-size: 12px;
    }

    .collapsedFolder, .on {
        padding-left: 54px;
        cursor: pointer;
        height: 16px;
        line-height: 16px;
        padding-top: 10px;
        font-size: 12px;
        background: url(images/bn_bg.gif) no-repeat center 0;
    }

    .on {
        background: url(images/bn_bg.gif) no-repeat center -31px;
    }

    .submenu {
    }

    .submenu td, .submenu td.b {
        padding-left: 72px;
        background: url(images/left_bg.gif) no-repeat 42px center;
    }

    .submenu td.b {
        background: url(images/left_bg1.gif) no-repeat 42px center;
    }

    .submenu a:link, .submenu a:visited {
        color: #666666;
        text-decoration: none;
    }

    .submenu a:hover, .submenu a:active {
        color: #8F2F00;
        text-decoration: underline;
    }

    a:link, a:visited {
        color: #000;
        text-decoration: none;
    }

    a:hover, a:active {
        text-decoration: underline;
    }

    a.wh:link, a.wh:visited {
        color: #fff;
        text-decoration: none;
    }

    a.wh:hover, a.wh:active {
        color: #FFF;
        text-decoration: underline;
    }

    .h1 {
        font: normal 24px "微软雅黑", "黑体";
        color: #003399;
        background-color: #fff;
        /* background-image: url("images/logo.png"); */
        background-position: left;
        background-repeat: no-repeat;
    }

    .h2 {
        font: normal 24px "微软雅黑", "黑体";
        color: #FFFFFF;
        padding-left: 90px;
    }

    img {
        vertical-align: text-top;
    }

    #rightTop div {
        padding: 4px;
        color: #FFF;
    }

    .gap {
        height: 10px;
        overflow: hidden
    }

</style>
<script language="javascript">
    function win(n) {
        var arr = new Array();
        arr[1] = "/manages/index.jsp";
        arr[2] = "/manages/systemMonitoring/updateAdminPassword.jsp";

        arr[11] = "/manages/memberManagesServlet?action=preAddMember";
        arr[12] = "/manages/memberManagesServlet?action=memberList";

        arr[21] = "/manages/accountManagesServlet?action=getAccountLogList&type=dy&startTime=${myf:addDate('d',-8)}&endTime=${myf:addDate('d',0)}";
        arr[22] = "/manages/account/lastAdjustment.jsp";
        arr[27] = "/manages/accountManagesServlet?action=getAdjustmentList";

        arr[31] = "/manages/issueManagesServlet?action=lotteryTotalIssueQuery";
        arr[32] = "/manages/issueManagesServlet?action=zqIssueQuery&status=1";
        arr[33] = "/manages/issueManagesServlet?action=basketballMatchList";
        arr[34] = "/manages/issueManagesServlet?action=footballMatchList";
        arr[35] = "/manages/issueManagesServlet?action=footballIssueList";
        arr[36] = "/manages/issueManagesServlet?action=beiDanMatchList";
        arr[37] = "/manages/issueManagesServlet?action=postIssueQuery";

        arr[41] = "/manages/openAward?action=openAwardIndex";
        arr[42] = "/manages/openAward?action=openFootball";
        arr[43] = "/manages/openAward?action=openBasketball";
        arr[44] = "/manages/openAward?action=openBeiDan";


        arr[51] = "/manages/calculateAwardServlet?action=sportIssueList";
        arr[52] = "/manages/calculateAwardServlet?action=footballMatchList";
        arr[53] = "/manages/calculateAwardServlet?action=basketballMatchList";
        arr[54] = "/manages/calculateAwardServlet?action=beiDanMatchList";
        arr[55] = "#";

        arr[61] = "/manages/sendWinManagesServlet?action=sportIssueList";
        arr[62] = "/manages/sendWinManagesServlet?action=footballMatchList";
        arr[63] = "/manages/sendWinManagesServlet?action=basketballMatchList";
        arr[64] = "/manages/sendWinManagesServlet?action=beiDanMatchList";
        arr[65] = "/manages/sendWinManagesServlet?action=bonusList";
        arr[66] = "/manages/win/handworkBonusList.jsp";

        arr[71] = "/manages/ticketManagesServlet?action=ticketList";
        arr[72] = "/manages/account/queryBalance.jsp?action=query";
        arr[73] = "/manages/ticketManagesServlet?action=noSendticketList&createStartTime=${myf:addDate('d',0)}&createEndTime=${myf:addDate('d',0)}";
        arr[74] = "/manages/ticketManagesServlet?action=sti";
        arr[75] = "/manages/ticketManagesServlet?action=errorTicketList&myType=dy";
        arr[76] = "/manages/postTicketServlet?action=postTicketList";
        arr[77] = "/manages/postTicketServlet?action=memberTicketList";
        arr[78] = "/manages/checkTicketServlet?action=checkTicketList";
        arr[79] = "/manages/adminManages?action=alertAmountList";
        
        arr[80] = "/manages/count/accountTable.jsp";
        arr[81] = "/manages/count/saleTable.jsp";
        arr[82] = "/manages/count/ticketTable.jsp";
        arr[83] = "/manages/count/daySaleTable.jsp";
        arr[84] = "/manages/count/dayTicketTable.jsp";
        arr[85] = "/manages/count/dayTicketCount.jsp";
        arr[86] = "/manages/count/lotteryCount.jsp";
        arr[87] = "/manages/count/bonusTicket.jsp";
        arr[88] = "/manages/count/lotteryIssueCount.jsp";
        arr[89] = "/manages/count/lotteryDateCount.jsp";
        arr[90] = "/manages/count/lotteryIssueCountNew.jsp";

        arr[110] = "/manages/system/sysQuartz.jsp";
        arr[111] = "/manages/system/sysQuartzManages.jsp";
        arr[112] = "/manages/systemManagesServlet?action=getLogList";

        arr[101] = "/manages/purviewGroupServlet?action=list";
        arr[102] = "coding.jsp";
        arr[103] = "/manages/systemManagesServlet?action=getAdminList";
        arr[107] = "/manages/systemMonitoring/addPurview.jsp";
        arr[104] = "systemMonitoring/updateManages.jsp";
        arr[105] = "count/saleTable.jsp";
        arr[106] = "/manages/reloadConfig";
        arr[107] = "/manages/controlWeight?action=view_cache_weight";
        arr[108] = "/SendClientServlet?action=postCodeList";
        arr[109] = "/manages/postManagesServlet?action=view_control_weight";
        arr[121]="/chart/ticketsCountNow.jsp";
        arr[122]="/chart/ticketsClassifyByType.jsp";
        arr[123]="/ChartServlet?action=memberList";
        arr[124]="/ChartServlet?action=lotteryList";

        document.getElementById("win").src = arr[n];
    }
</script>
</head>
<body>
<c:set var="pm" value="${purviewMap }"></c:set>
<table width="100%" height="50" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
        <td height="50" colspan="2" class="h1">
            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0"
                   background="images/gl_topbg.jpg">
                <tr>
                    <td width="65%" height="50">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<!-- <img
                            src="images/gl_logo.gif"> --></td>
                    <td width="30%" align="right" id="rightTop">

                        <div style="color:#891821"> <span>
                            <script type="text/javascript">
                                var nowDate = new Date();
                                document.write("今天是：" + nowDate.getFullYear() + "年" + (nowDate.getMonth() + 1) + "月" + nowDate.getDate() + "日" + "&nbsp;&nbsp;星期" + ("日一二三四五六").charAt(nowDate.getDay()));
                            </script>
                        </span> &nbsp;<a
                                href="javascript:win(2)"
                                style="color:#891821">修改密码</a> | <a
                                href="${pageContext.request.contextPath}/manages/systemManagesServlet?action=exit"
                                class="" style="color:#891821"> 退出系统</a>
                        </div>
                    </td>

                </tr>
            </table>
        </td>
    </tr>
    <tr style="display:none">
        <td width="170"><img src="images/top_11.png" border="0" style="cursor:pointer;"
                             onmouseup="with(findObj('menubar').style){display=display=='none'?'':'none';}"></td>
        <td style="padding: 2px 2px 2px 8px; color: #f1f1f1">${adminUser.userName }
            您好，欢迎使用本系统！您上次登录的时间是：<fmt:formatDate value="${adminUser.loginTime }"
                                                 pattern="yyyy-MM-dd HH:mm:ss"/>，IP地址是：${adminUser.loginIp }
        </td>
    </tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">

<tr>
<td width="170" height="100%" valign="top" id="menubar" background="/manages/images/menu_bg.jpg">
<table width="100%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
<tr>
<td width="170" height="600" valign="top" style="padding: 2px 4px 0 0;">

<c:if test="${myf:havePurview(pm,'10001') == 'true'||myf:havePurview(pm,'10002') == 'true'}">

    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <td class="collapsedFolder"
                onmouseup="with(findObj('union').style){display=display=='none'?'':'none';}">渠道管理
            </td>
        </tr>
        <tr>
            <td class="submenu" id="union">
                <table width="100%" border="0" cellspacing="0" cellpadding="3">
                    <c:if test="${myf:havePurview(pm,'10001') == 'true'}">
                        <tr>
                            <td class="b"><a href="javascript:win(11)">新建接入商</a></td>
                        </tr>
                    </c:if>
                    <c:if test="${myf:havePurview(pm,'10002') == 'true'}">
                        <tr>
                            <td class="b"><a href="javascript:win(12)">接入商管理</a></td>
                        </tr>
                    </c:if>
                </table>
            </td>
        </tr>
    </table>
</c:if>

<c:if test="${myf:havePurview(pm,'20002') == 'true'||myf:havePurview(pm,'20005') == 'true'||myf:havePurview(pm,'20006') == 'true'}">
    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <td class="collapsedFolder"
                onmouseup="with(findObj('points').style){display=display=='none'?'':'none';}"> 账务管理
            </td>
        </tr>
        <tr>
            <td class="submenu" id="points">
                <table width="100%" border="0" cellspacing="0" cellpadding="3">
                    <c:if test="${myf:havePurview(pm,'20002') == 'true'}">
                        <tr>
                            <td><a href="javascript:win(21)">进出明细</a></td>
                        </tr>
                    </c:if>
                    <c:if test="${myf:havePurview(pm,'20005') == 'true'}">
                        <tr>
                            <td class="b"><a href="javascript:win(22)">额度调整</a></td>
                        </tr>
                    </c:if>
                    <c:if test="${myf:havePurview(pm,'20006') == 'true'}">
                        <tr>
                            <td class="b"><a href="javascript:win(27)">调整明细</a></td>
                        </tr>
                    </c:if>
                </table>
            </td>
        </tr>
    </table>
</c:if>

<c:if test="${myf:havePurview(pm,'40001') == 'true'||myf:havePurview(pm,'40002') == 'true'||myf:havePurview(pm,'40003') == 'true'
||myf:havePurview(pm,'40005') == 'true'||myf:havePurview(pm,'40006') == 'true'||myf:havePurview(pm,'40007') == 'true' ||myf:havePurview(pm,'40008') == 'true' }">
    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <td class="collapsedFolder"
                onmouseup="with(findObj('top').style){display=display=='none'?'':'none';}"> 彩期管理
            </td>
        </tr>
        <tr>
            <td class="submenu" id="top">
                <table width="100%" border="0" cellspacing="0" cellpadding="3">
                    <c:if test="${myf:havePurview(pm,'40001') == 'true'}">
                        <tr>
                            <td><a href="javascript:win(31)">彩期控制</a></td>
                        </tr>
                    </c:if>
                    <c:if test="${myf:havePurview(pm,'40002') == 'true'}">
                        <tr>
                            <td class="b"><a href="javascript:win(32)">普通彩期查询</a></td>
                        </tr>
                    </c:if>
                    <c:if test="${myf:havePurview(pm,'40003') == 'true'}">
                        <tr>
                            <td class="b"><a href="javascript:win(33)">竞彩篮球查询</a></td>
                        </tr>
                    </c:if>
                    <c:if test="${myf:havePurview(pm,'40005') == 'true'}">
                        <tr>
                            <td class="b"><a href="javascript:win(34)">竞彩足球查询</a></td>
                        </tr>
                    </c:if>
                    <c:if test="${myf:havePurview(pm,'40006') == 'true'}">
                        <tr>
                            <td class="b"><a href="javascript:win(35)">足彩对阵管理</a></td>
                        </tr>
                    </c:if>
                    <c:if test="${myf:havePurview(pm,'40007') == 'true'}">
                        <tr>
                            <td class="b"><a href="javascript:win(36)">北单对阵管理</a></td>
                        </tr>
                    </c:if>
                    <c:if test="${myf:havePurview(pm,'40008') == 'true'}">
                        <tr>
                            <td class="b"><a href="javascript:win(37)">出票口彩期查询</a></td>
                        </tr>
                    </c:if>
                </table>
            </td>
        </tr>
    </table>
</c:if>

<c:if test="${myf:havePurview(pm,'50001') == 'true'||myf:havePurview(pm,'50002') == 'true'||myf:havePurview(pm,'50003') == 'true'
||myf:havePurview(pm,'50004') == 'true'}">
    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <td class="collapsedFolder"
                onmouseup="with(findObj('openAward').style){display=display=='none'?'':'none';}"> 开奖管理
            </td>
        </tr>
        <tr>
            <td class="submenu" id="openAward">
                <table width="100%" border="0" cellspacing="0" cellpadding="3">
                    <c:if test="${myf:havePurview(pm,'50001') == 'true'}">
                        <tr>
                            <td class="b"><a href="javascript:win(41)">普通彩种录入</a></td>
                        </tr>
                    </c:if>
                    <c:if test="${myf:havePurview(pm,'50002') == 'true'}">
                        <tr>
                            <td class="b"><a href="javascript:win(42)">竞彩足球录入</a></td>
                        </tr>
                    </c:if>
                    <c:if test="${myf:havePurview(pm,'50003') == 'true'}">
                        <tr>
                            <td class="b"><a href="javascript:win(43)">竞彩篮球录入</a></td>
                        </tr>
                    </c:if>
                    <c:if test="${myf:havePurview(pm,'50004') == 'true'}">
                        <tr>
                            <td class="b"><a href="javascript:win(44)">北京单场录入</a></td>
                        </tr>
                    </c:if>
                </table>
            </td>
        </tr>
    </table>
</c:if>

<c:if test="${myf:havePurview(pm,'60001') == 'true'||myf:havePurview(pm,'60002') == 'true'||myf:havePurview(pm,'60003') == 'true'
||myf:havePurview(pm,'60004') == 'true'}">
    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <td class="collapsedFolder"
                onmouseup="with(findObj('calculateAward').style){display=display=='none'?'':'none';}"> 算奖管理
            </td>
        </tr>
        <tr>
            <td class="submenu" id="calculateAward">
                <table width="100%" border="0" cellspacing="0" cellpadding="3">
                    <c:if test="${myf:havePurview(pm,'60001') == 'true'}">
                        <tr>
                            <td class="b"><a href="javascript:win(51)">普通彩种算奖</a></td>
                        </tr>
                    </c:if>
                    <c:if test="${myf:havePurview(pm,'60002') == 'true'}">
                        <tr>
                            <td class="b"><a href="javascript:win(52)">竞彩足球算奖</a></td>
                        </tr>
                    </c:if>
                    <c:if test="${myf:havePurview(pm,'60003') == 'true'}">
                        <tr>
                            <td class="b"><a href="javascript:win(53)">竞彩篮球算奖</a></td>
                        </tr>
                    </c:if>
                    <c:if test="${myf:havePurview(pm,'60004') == 'true'}">
                        <tr>
                            <td class="b"><a href="javascript:win(54)">北单算奖</a></td>
                        </tr>
                    </c:if>
                </table>
            </td>
        </tr>
    </table>
</c:if>

<c:if test="${myf:havePurview(pm,'70001') == 'true'||myf:havePurview(pm,'70002') == 'true'||myf:havePurview(pm,'70003') == 'true'
||myf:havePurview(pm,'70004') == 'true'||myf:havePurview(pm,'70005') == 'true'}">
    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <td class="collapsedFolder"
                onmouseup="with(findObj('shop').style){display=display=='none'?'':'none';}">派奖管理
            </td>
        </tr>
        <tr>
            <td class="submenu" id="shop">
                <table width="100%" border="0" cellspacing="0" cellpadding="3">

                    <c:if test="${myf:havePurview(pm,'70001') == 'true'}">
                        <tr>
                            <td class="b"><a href="javascript:win(61)">普通彩种派奖</a></td>
                        </tr>
                    </c:if>
                    <c:if test="${myf:havePurview(pm,'70002') == 'true'}">
                        <tr>
                            <td class="b"><a href="javascript:win(62)">竞彩足球派奖</a></td>
                        </tr>
                    </c:if>
                    <c:if test="${myf:havePurview(pm,'70003') == 'true'}">
                        <tr>
                            <td class="b"><a href="javascript:win(63)">竞彩篮球派奖</a></td>
                        </tr>
                    </c:if>
                    <c:if test="${myf:havePurview(pm,'70004') == 'true'}">
                        <tr>
                            <td class="b"><a href="javascript:win(64)">北单派奖</a></td>
                        </tr>
                    </c:if>
                    <c:if test="${myf:havePurview(pm,'70005') == 'true'}">
                        <%--<tr>--%>
                        <%--<td class="b"><a href="javascript:win(65)">中奖查询</a></td>--%>
                        <%--</tr>--%>
                        <tr>
                            <td class="b"><a href="javascript:win(66)">派奖查询</a></td>
                        </tr>
                    </c:if>
                </table>
            </td>
        </tr>
    </table>
</c:if>

<c:if test="${myf:havePurview(pm,'80001') == 'true'||myf:havePurview(pm,'80002') == 'true'||myf:havePurview(pm,'80003') == 'true' || myf:havePurview(pm,'80009') == 'true' 
||myf:havePurview(pm,'80004') == 'true'||myf:havePurview(pm,'80005') == 'true' || myf:havePurview(pm,'80006') == 'true' || myf:havePurview(pm,'80007') == 'true' }">
    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <td class="collapsedFolder"
                onmouseup="with(findObj('sdcp').style){display=display=='none'?'':'none';}">彩票管理
            </td>
        </tr>
        <tr>
            <td class="submenu" id="sdcp">
                <table width="100%" border="0" cellspacing="0" cellpadding="3">
                    <c:if test="${myf:havePurview(pm,'80001') == 'true'}">
                        <tr>
                            <td class="b"><a href="javascript:win(71)">彩票查询</a></td>
                        </tr>
                    </c:if>
                    <c:if test="${myf:havePurview(pm,'80003') == 'true'}">
                        <tr>
                            <td class="b"><a href="javascript:win(73)">未出彩票查询</a></td>
                        </tr>
                    </c:if>
                    <c:if test="${myf:havePurview(pm,'80006') == 'true'}">
                        <tr>
                            <td class="b"><a href="javascript:win(76)">出票商与彩种</a></td>
                        </tr>
                    </c:if>
                    <c:if test="${myf:havePurview(pm,'80007') == 'true'}">
                        <tr>
                            <td class="b"><a href="javascript:win(77)">接入商与彩种</a></td>
                        </tr>
                    </c:if>
                    <c:if test="${myf:havePurview(pm,'80005') == 'true'}">
                        <tr>
                            <td class="b"><a href="javascript:win(75)">异常出票撤单</a></td>
                        </tr>
                    </c:if>
                    <c:if test="${myf:havePurview(pm,'80002') == 'true'}">
                        <tr>
                            <td class="b"><a href="javascript:win(72)">额度查询</a></td>
                        </tr>
                    </c:if>
                    <c:if test="${myf:havePurview(pm,'80009') == 'true'}">
                        <tr>
                            <td class="b"><a href="javascript:win(79)">警报额度</a></td>
                        </tr>
                    </c:if>
                    <c:if test="${myf:havePurview(pm,'80004') == 'true'}">
                        <tr>
                            <td class="b"><a href="javascript:win(74)">出票监控</a></td>
                        </tr>
                    </c:if>
                     <c:if test="${myf:havePurview(pm,'80008') == 'true'}">
                        <tr>
                            <td class="b"><a href="javascript:win(78)">自动对账管理</a></td>
                        </tr>
                    </c:if>
                </table>
            </td>
        </tr>
    </table>
</c:if>

<c:if test="${myf:havePurview(pm,'30001') == 'true'||myf:havePurview(pm,'30002') == 'true'||myf:havePurview(pm,'30003') == 'true'
||myf:havePurview(pm,'30004') == 'true'||myf:havePurview(pm,'30005') == 'true'||myf:havePurview(pm,'30006') == 'true'
||myf:havePurview(pm,'30007') == 'true'||myf:havePurview(pm,'30008') == 'true'||myf:havePurview(pm,'30009') == 'true'
||myf:havePurview(pm,'30010') == 'true'||myf:havePurview(pm,'30011') == 'true'}">
    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <td class="collapsedFolder"
                onmouseup="with(findObj('news').style){display=display=='none'?'':'none';}"> 统计报表
            </td>
        </tr>
        <tr>
            <td class="submenu" id="news">
                <table width="100%" border="0" cellspacing="0" cellpadding="3">
                    <c:if test="${myf:havePurview(pm,'30001') == 'true'}">
                        <tr>
                            <td><a href="javascript:win(80)">帐户报表</a></td>
                        </tr>
                    </c:if>
                    <c:if test="${myf:havePurview(pm,'30002') == 'true'}">
                        <tr>
                            <td><a href="javascript:win(81)">彩期销售报表</a></td>
                        </tr>
                    </c:if>
                    <c:if test="${myf:havePurview(pm,'30003') == 'true'}">
                        <tr>
                            <td><a href="javascript:win(82)">彩期出票报表</a></td>
                        </tr>
                    </c:if>
                    <c:if test="${myf:havePurview(pm,'30004') == 'true'}">
                        <tr>
                            <td><a href="javascript:win(83)">日销售报表</a></td>
                        </tr>
                    </c:if>
                    <c:if test="${myf:havePurview(pm,'30005') == 'true'}">
                        <tr>
                            <td><a href="javascript:win(84)">日出票报表</a></td>
                        </tr>
                    </c:if>
                    <c:if test="${myf:havePurview(pm,'30006') == 'true'}">
                        <tr>
                            <td><a href="javascript:win(85)">日出票查询</a></td>
                        </tr>
                    </c:if>
                    <c:if test="${myf:havePurview(pm,'30007') == 'true'}">
                        <tr>
                            <td><a href="javascript:win(86)">彩种报表</a></td>
                        </tr>
                    </c:if>
                    <c:if test="${myf:havePurview(pm,'30008') == 'true'}">
                        <tr>
                            <td><a href="javascript:win(87)">中奖差异报表</a></td>
                        </tr>
                    </c:if>
                    <c:if test="${myf:havePurview(pm,'30009') == 'true'}">
                        <tr>
                            <td><a href="javascript:win(88)">商户期次对账报表</a></td>
                        </tr>
                    </c:if>
                    <c:if test="${myf:havePurview(pm,'30010') == 'true'}">
                        <tr>
                            <td><a href="javascript:win(89)">按天对账报表</a></td>
                        </tr>
                    </c:if>
                    <c:if test="${myf:havePurview(pm,'30011') == 'true'}">
                        <tr>
                            <td><a href="javascript:win(90)">按期对账报表</a></td>
                        </tr>
                    </c:if>
                </table>
            </td>
        </tr>
    </table>
</c:if>

<c:if test="${myf:havePurview(pm,'90001') == 'true'||myf:havePurview(pm,'90002') == 'true'||myf:havePurview(pm,'90003') == 'true'}">
    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <td class="collapsedFolder"
                onmouseup="with(findObj('club').style){display=display=='none'?'':'none';}"> 系统状态监控
            </td>
        </tr>
        <tr>
            <td class="submenu" id="club">
                <table width="100%" border="0" cellspacing="0" cellpadding="3">
                    <c:if test="${myf:havePurview(pm,'90001') == 'true'}">
                        <tr>
                            <td class="b"><a href="javascript:win(110)">JOB监控</a></td>
                        </tr>
                    </c:if>
                    <c:if test="${myf:havePurview(pm,'90003') == 'true'}">
                        <tr>
                            <td class="b"><a href="javascript:win(111)">JOB监控配置</a></td>
                        </tr>
                    </c:if>
                    <c:if test="${myf:havePurview(pm,'90002') == 'true'}">
                        <tr>
                            <td class="b"><a href="javascript:win(112)">日志管理</a></td>
                        </tr>
                    </c:if>
                </table>
            </td>
        </tr>
    </table>
</c:if>

<c:if test="${myf:havePurview(pm,'100001') == 'true'||myf:havePurview(pm,'100002') == 'true'||myf:havePurview(pm,'100003') == 'true'
||myf:havePurview(pm,'100004') == 'true'||myf:havePurview(pm,'100005') == 'true'}">
    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <td class="collapsedFolder"
                onmouseup="with(findObj('liuyan').style){display=display=='none'?'':'none';}">系统参数设置
            </td>
        </tr>
        <tr>
            <td class="submenu" id="liuyan">
                <table width="100%" border="0" cellspacing="0" cellpadding="3">
                    <c:if test="${myf:havePurview(pm,'100001') == 'true'}">
                        <tr>
                            <td><a href="javascript:win(101)">角色管理</a></td>
                        </tr>
                    </c:if>
                    <c:if test="${myf:havePurview(pm,'100005') == 'true'}">
                        <tr>
                            <td><a href="javascript:win(107)">权限添加</a></td>
                        </tr>
                    </c:if>
                    <c:if test="${myf:havePurview(pm,'100002') == 'true'}">
                        <tr>
                            <td><a href="javascript:win(103)">用户管理</a></td>
                        </tr>
                    </c:if>
                    <c:if test="${myf:havePurview(pm,'100003') == 'true'}">
                        <tr>
                            <td class="b"><a href="javascript:win(104)">个人管理</a></td>
                        </tr>
                    </c:if>
                    <c:if test="${myf:havePurview(pm,'100004') == 'true'}">
                        <tr>
                            <td class="b"><a href="javascript:win(106)">配置文件管理</a></td>
                        </tr>
                        <tr>
                            <td class="b"><a href="javascript:win(107)">出票口管理</a></td>
                        </tr>
                         <tr>
                            <td class="b"><a href="javascript:win(109)">数据库出票口管理</a></td>
                        </tr>
                        <tr>
                            <td class="b"><a href="javascript:win(108)">出票商彩种管理</a></td>
                        </tr>
                    </c:if>
                </table>
            </td>
        </tr>
    </table>
</c:if>
<c:if test="${myf:havePurview(pm,'00001') == 'true'||myf:havePurview(pm,'00002') == 'true'}">
    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <td class="collapsedFolder"
                onmouseup="with(findObj('liuyan').style){display=display=='none'?'':'none';}">图表管理
            </td>
        </tr>
        <tr>
            <td class="submenu" id="liuyan">
                <table width="100%" border="0" cellspacing="0" cellpadding="3">
                    <c:if test="${myf:havePurview(pm,'00001') == 'true'}">
                        <tr>
                            <td><a href="javascript:win(121)">实时票量</a></td>
                        </tr>
                    </c:if>
                    <c:if test="${myf:havePurview(pm,'00002') == 'true'}">
                        <tr>
                            <td><a href="javascript:win(122)">彩种百分比</a></td>
                        </tr>
                    </c:if>
                    <c:if test="${myf:havePurview(pm,'00003') == 'true'}">
                        <tr>
                            <td><a href="javascript:win(123)">投注金额(商户)</a></td>
                        </tr>
                    </c:if>
                    <c:if test="${myf:havePurview(pm,'00004') == 'true'}">
                        <tr>
                            <td><a href="javascript:win(124)">投注金额(彩种)</a></td>
                        </tr>
                    </c:if>
                </table>
            </td>
        </tr>
    </table>
</c:if>
<div style="border-bottom:1px #b5cfd9 solid; height: 120px;"></div>
</td>
</tr>
</table>
</td>
<td align="center" valign="top" >
    <iframe id="win" src="index.jsp" scrolling="no" frameborder="0"
            onload="this.height=500" width="100%"></iframe>
    <script type="text/javascript">
        function reinitIframe() {
            var iframe = document.getElementById("win");
            try {
                var bHeight = iframe.contentWindow.document.body.scrollHeight;
                var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
                var height = Math.max(bHeight, dHeight);
                iframe.height = height;
            } catch (ex) {
            }
        }
        window.setInterval("reinitIframe()", 200);
    </script>
</td>
</tr>
</table>
<script type="text/javascript">
    $(".collapsedFolder").toggle(function() {
        $(this).addClass("on")
    }, function() {
        $(this).removeClass("on")
    })
    $("#a1").click();
</script>
</body>
</html>
