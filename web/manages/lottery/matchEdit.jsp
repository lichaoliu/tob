<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>赛事玩法禁售修改</title>
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
    <link href="${pageContext.request.contextPath}/css/kjgg.css" rel="stylesheet" type="text/css"/>
    <style type="text/css">
        body, td, th {
            font-family: Arial, "Times New Roman", "宋体";
        }
    </style>
    <script type="text/javascript">
        function windowClose() {
            window.close();
        }
        function matchDo() {
            if (confirm('确定要执行此修改?')) {
                $("#myForm").submit();
            } else {
                history.go(-1);
            }
        }
    </script>
</head>

<body>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="table table1">
    <tr>
        <td class="title" style="text-align:left;">
            彩期管理 >> ${lotteryCode == 200 ? "竞彩足球" : (lotteryCode == 201 ? "竞彩篮球" : "北京单场")}查询 >>
            赛事玩法禁售修改
        </td>
    </tr>
    <tr>
        <td style="padding: 4px;">
            <table width="100%" border="0" cellpadding="4" cellspacing="0" class="seach_none">
                <tr>
                    <td width="50%" align="right" class="bold">${lotteryCode == "400" ? "彩期" : "日期"}:</td>
                    <td>
                        ${issue}
                    </td>
                </tr>
                <tr>
                    <td align="right" class="bold">${lotteryCode == "400" ? "场次" : "编号"}:</td>
                    <td>${sn}</td>
                </tr>
                <form id="myForm" method="post"
                      action="${pageContext.request.contextPath}/manages/issueManagesServlet?action=matchEditDo&lotteryCode=${lotteryCode}&sn=${sn}&issue=${issue}">
                    <c:if test="${lotteryCode == 200}">
                        <tr>
                            <td align="right" class="bold">让球胜平负单关:</td>
                            <td><input name="playType" type="checkbox"
                                       value="01-01" ${map['0101'] == '0101' ? "checked='checked'" : ""}/>取消
                            </td>
                        </tr>
                        <tr>
                            <td align="right" class="bold">让球胜平负串关:</td>
                            <td><input name="playType" type="checkbox"
                                       value="01-02" ${map['0102'] == '0102' ? "checked='checked'" : ""}/>取消
                            </td>
                        </tr>
                        <tr>
                            <td align="right" class="bold">总进球数单关:</td>
                            <td><input name="playType" type="checkbox"
                                       value="02-01" ${map['0201'] == '0201' ? "checked='checked'" : ""}/>取消
                            </td>
                        </tr>
                        <tr>
                            <td align="right" class="bold">总进球数串关:</td>
                            <td><input name="playType" type="checkbox"
                                       value="02-02" ${map['0202'] == '0202' ? "checked='checked'" : ""}/>取消
                            </td>
                        </tr>
                        <tr>
                            <td align="right" class="bold">半全场单关:</td>
                            <td><input name="playType" type="checkbox"
                                       value="03-01" ${map['0301'] == '0301' ? "checked='checked'" : ""}/>取消
                            </td>
                        </tr>
                        <tr>
                            <td align="right" class="bold">半全场串关:</td>
                            <td><input name="playType" type="checkbox"
                                       value="03-02" ${map['0302'] == '0302' ? "checked='checked'" : ""}/>取消
                            </td>
                        </tr>
                        <tr>
                            <td align="right" class="bold">比分单关:</td>
                            <td><input name="playType" type="checkbox"
                                       value="04-01" ${map['0401'] == '0401' ? "checked='checked'" : ""}/>取消
                            </td>
                        </tr>
                        <tr>
                            <td align="right" class="bold">比分串关:</td>
                            <td><input name="playType" type="checkbox"
                                       value="04-02" ${map['0402'] == '0402' ? "checked='checked'" : ""}/>取消
                            </td>
                        </tr>
                        <tr>
                            <td align="right" class="bold">胜平负单关:</td>
                            <td><input name="playType" type="checkbox"
                                       value="05-01" ${map['0501'] == '0501' ? "checked='checked'" : ""}/>取消
                            </td>
                        </tr>
                        <tr>
                            <td align="right" class="bold">胜平负串关:</td>
                            <td><input name="playType" type="checkbox"
                                       value="05-02" ${map['0502'] == '0502' ? "checked='checked'" : ""}/>取消
                            </td>
                        </tr>
                    </c:if>
                    <c:if test="${lotteryCode == 201}">
                        <tr>
                            <td align="right" class="bold">胜负单关:</td>
                            <td><input name="playType" type="checkbox"
                                       value="01-01" ${map['0101'] == '0101' ? "checked='checked'" : ""}/>取消
                            </td>
                        </tr>
                        <tr>
                            <td align="right" class="bold">胜负串关:</td>
                            <td><input name="playType" type="checkbox"
                                       value="01-02" ${map['0102'] == '0102' ? "checked='checked'" : ""}/>取消
                            </td>
                        </tr>
                        <tr>
                            <td align="right" class="bold">让分胜负单关:</td>
                            <td><input name="playType" type="checkbox"
                                       value="02-01" ${map['0201'] == '0201' ? "checked='checked'" : ""}/>取消
                            </td>
                        </tr>
                        <tr>
                            <td align="right" class="bold">让分胜负串关:</td>
                            <td><input name="playType" type="checkbox"
                                       value="02-02" ${map['0202'] == '0202' ? "checked='checked'" : ""}/>取消
                            </td>
                        </tr>
                        <tr>
                            <td align="right" class="bold">胜分差单关:</td>
                            <td><input name="playType" type="checkbox"
                                       value="03-01" ${map['0301'] == '0301' ? "checked='checked'" : ""}/>取消
                            </td>
                        </tr>
                        <tr>
                            <td align="right" class="bold">胜分差串关:</td>
                            <td><input name="playType" type="checkbox"
                                       value="03-02" ${map['0302'] == '0302' ? "checked='checked'" : ""}/>取消
                            </td>
                        </tr>
                        <tr>
                            <td align="right" class="bold">大小分单关:</td>
                            <td><input name="playType" type="checkbox"
                                       value="04-01" ${map['0401'] == '0401' ? "checked='checked'" : ""}/>取消
                            </td>
                        </tr>
                        <tr>
                            <td align="right" class="bold">大小分串关:</td>
                            <td><input name="playType" type="checkbox"
                                       value="04-02" ${map['0402'] == '0402' ? "checked='checked'" : ""}/>取消
                            </td>
                        </tr>
                    </c:if>
                    <tr>
                        <td colspan="2" align="center"><br/>
                            <input type="button" class="submit" value="确定" style="width: 64px; border: none"
                                   onclick="matchDo();"/>
                            <input type="button" class="submit" value="取消" style="width: 64px; border: none"
                                   onclick="javascript:history.go(-1);"/>
                        </td>
                    </tr>
                </form>
            </table>
        </td>
    </tr>
</table>
</div>
</body>
</html>