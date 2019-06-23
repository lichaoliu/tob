<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../jstl.jsp" %>
<html>
<head>
    <title>彩票投注接入商查询系统</title>
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
            /*background-image: url("images/logo.png");*/
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
            arr[110] = "/coop/member/updateAdminPassword.jsp";
            arr[111] = "/coop/cooperationServlet?action=detailMember";
            arr[117] = "/coopBalanceServlet?action=get";
            arr[112] = "/coop/cooperationServlet?action=getAccountLogList";
            arr[113] = "/coop/cooperationServlet?action=saleTable";
            arr[114] = "/coop/cooperationServlet?action=ticketList";
            arr[115] = "/coop/cooperationServlet?action=bonusList";
            arr[116] = "/coop/cooperationServlet?action=handworkBonusQuery";
            arr[119] = "/coop/cooperationServlet?action=preUpdateCooperation";
            arr[120] = "/coop/member/updateAdminPassword.jsp";
            arr[121] = "/coop/cooperationServlet?action=exit";

            document.getElementById("win").src = arr[n];
        }
    </script>
</head>
<body>
<c:set var="pm" value="${purviewMapUnion }"></c:set>
<table width="100%" height="50" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
        <td height="50" colspan="2" class="h1">
            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0"
                   background="images/gl_topbg.jpg">
                <tr>
                    <td width="65%" height="50">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img
                            src="images/gl_logo.gif"></td>
                    <td width="35%" align="right">

                        <div style="color:#891821">
                            <span>
                            <script type="text/javascript">
                                var nowDate = new Date();
                                document.write("今天是：" + nowDate.getFullYear() + "年" + (nowDate.getMonth() + 1) + "月" + nowDate.getDate() + "日" + "&nbsp;&nbsp;星期" + ("日一二三四五六").charAt(nowDate.getDay()));
                            </script>
                        </span> &nbsp;<a
                                href="javascript:win(110)" target="_parent"
                                style="color:#891821">修改密码</a> | <a
                                href="${pageContext.request.contextPath}/coop/cooperationServlet?action=exit"
                                class="" style="color:#891821"> 退出系统</a>
                        </div>
                    </td>
                    <td width="3%" align="right">&nbsp;</td>
                </tr>
            </table>
        </td>
    </tr>
    <tr style="display:none">
        <td width="170"><img src="images/top_11.png" border="0" style="cursor:pointer;"
                             onmouseup="with(findObj('menubar').style){display=display=='none'?'':'none';}"></td>
        <td style="padding: 2px 2px 2px 8px; color: #f1f1f1">${adminUser.userName } 您好，欢迎使用本系统！您上次登录的时间是：<fmt:formatDate
                value="${adminUser.loginTime }" pattern="yy-MM-dd HH:mm:ss"/>，IP地址是：${adminUser.loginIp }</td>
        <%--<td style="padding: 2px 2px 2px 8px; color: #f1f1f1">admin
            您好，欢迎使用本系统！您上次登录的时间是：2011年06月20日，IP地址是：192.168.1.100
        </td>--%>
    </tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
        <td width="170" height="100%" valign="top" id="menubar" background="images/menu_bg.jpg">
            <table width="100%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td width="170" height="600" valign="top"
                        background="images/top_33.png"
                        style="padding: 2px 4px 0 0;">
                        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                            <tr>
                                <td class="collapsedFolder"
                                    onmouseup="with(findObj('home').style){display=display=='none'?'':'none';}">
                                    接入商资料
                                </td>
                            </tr>
                            <tr>
                                <td class="submenu" id="home">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="3">
                                        <tr>
                                            <td class="b"><a href="javascript:win(111)">基本信息</a></td>
                                        </tr>
                                        <tr>
                                            <td class="b"><a href="javascript:win(117)">余额报警查询</a></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                            <tr>
                                <td class="collapsedFolder"
                                    onmouseup="with(findObj('user').style){display=display=='none'?'':'none';}">
                                    业务查询
                                </td>
                            </tr>
                            <tr>
                                <td class="submenu" id="user">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="3">
                                        <tr>
                                            <td><a href="javascript:win(114)">投注记录</a></td>
                                        </tr>
                                        <tr>
                                            <td class="b"><a href="javascript:win(112)">账务明细</a></td>
                                        </tr>
                                        <tr>
                                            <td class="b"><a href="javascript:win(113)">销售报表</a></td>
                                        </tr>
                                        <%--<tr>--%>
                                        <%--<td class="b"><a href="javascript:win(115)">中奖查询</a></td>--%>
                                        <%--</tr>--%>
                                        <tr>
                                            <td class="b"><a href="javascript:win(116)">派奖查询</a></td>
                                        </tr>
                                        </td>
                                    </table>
                                </td>
                            </tr>
                        </table>
                        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                            <tr>
                                <td class="collapsedFolder"
                                    onmouseup="with(findObj('cooperation').style){display=display=='none'?'':'none';}">
                                    安全管理
                                </td>
                            </tr>
                            <tr>
                                <td class="submenu" id="cooperation">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="3">
                                        <c:if test="${sessionScope.sid != null}">
                                            <tr>
                                                <td><a href="javascript:win(119)">资料修改</a></td>
                                            </tr>
                                        </c:if>
                                        <tr>
                                            <td><a href="javascript:win(120)">修改密码</a></td>
                                        </tr>
                                        <tr>
                                            <td class="b"><a href="javascript:win(121)">安全退出</a></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                        <div style=" height: 120px;"></div>
                    </td>
                </tr>
            </table>
        </td>
        <td align="center" valign="top" style="background-image: url(images/bg.png);">


            <iframe id="win" name="win" src="/coop/cooperationServlet?action=detailMember" scrolling="no"
                    frameborder="0"
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
