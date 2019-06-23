<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>编辑开奖信息</title>
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
        $(document).ready(function(){
            window.onbeforeunload = function (event) {
                var event = event || window.event;
                // 兼容IE8和Firefox 4之前的版本
                if (event) {
                    event.returnValue = "确定要关闭窗口吗？";
                }
                // Chrome, Safari, Firefox 4+, Opera 12+ , IE 9+
                return '确定要关闭窗口吗>现代浏览器？';
            }

        });
    </script>
    <script type="text/javascript">
        function isNotNull(str) {
            if (str == null || str == "") {
                return false;
            } else {
                return true;
            }
        }
        $(document).ready(function() {
            $("#subBtn").click(function() {
                var bonusNumbers = document.getElementsByName("bonusNumber");
                var scoreAtHalfz = document.getElementsByName("scoreAtHalfz");
                var scoreAtHalfk = document.getElementsByName("scoreAtHalfk");
                var secondHalfTheScorez = document.getElementsByName("secondHalfTheScorez");
                var secondHalfTheScorek = document.getElementsByName("secondHalfTheScorek");
                for (i = 0; i < scoreAtHalfz.length; i++) {
                    var s = scoreAtHalfz[i].value;
                    if ('' != s) {
                        if (!/^[0-9]+$/.test(s)) {
                            alert("录入上半场进球数主队进球数不正确");
                            return;
                        }
                    }
                }

                for (i = 0; i < scoreAtHalfk.length; i++) {
                    var s = scoreAtHalfk[i].value;
                    if ('' != s) {
                        if (!/^[0-9]+$/.test(s)) {
                            alert("录入上半场进球数客队进球数不正确");
                            return;
                        }
                    }
                }

                for (i = 0; i < secondHalfTheScorez.length; i++) {
                    var s = secondHalfTheScorez[i].value;
                    if ('' != s) {
                        if (!/^[0-9]+$/.test(s)) {
                            alert("录入全场进球数主队进球数不正确");
                            return;
                        }
                    }
                }

                for (i = 0; i < secondHalfTheScorek.length; i++) {
                    var s = secondHalfTheScorek[i].value;
                    if ('' != s) {
                        if (!/^[0-9]+$/.test(s)) {
                            alert("录入全场进球数客队进球数不正确");
                            return;
                        }
                    }
                }
                var bonusNumberStr = "";
                for (i = 0; i < bonusNumbers.length; i++) {
                    if (!checkNumberOfOne(bonusNumbers[i].value)) {
                        alert("录入的开奖号码格式不对");
                        return;
                    }
                    bonusNumberStr += bonusNumbers[i].value + "*";
                }

                var str = "足球进球数第${issue.name}期，开奖号码为" + bonusNumberStr.substring(0, bonusNumberStr.length - 1) + "\n";
                if (isNotNull($("#lv01").val()))
                    str += "\n一等奖金" + $("#lv01").val() + "元";

                var bln = confirm(str + "\n是否确认保存开奖信息?");
                if (bln) {
                    $("#awardForm").submit();
                } else {
//                    history.go(-1);
                    return;
                }
            });
            //开奖号码输入格式验证
            var bnList = document.getElementsByName("bonusNumber");
            for (i = 0; i < bnList.length; i++) {
                bnList[i].onkeyup = regOpenNumber;
            }
            $("#lv01").keyup(regMoney);
            $("#lv01zu").keyup(regInteger);
            $("#prizePool").keyup(regMoney);
            $("#globalSaleTotal").keyup(regMoney);
        });

        function checkNumberOfOne(num) {
            if (/^[01239]$/.test(num) || num == null || num == "") {
                return true;
            } else {
                return false;
            }
        }

        function regOpenNumber() {
            var maxLen = 1;
            var reg = /^[01239]$/;
            if (!reg.test(this.value)) {
                this.value = "";//isNaN(parseInt(this.value)) ? "" : parseInt(this.value.substring(0, maxLen));
            }
        }
        function regInteger() {
            var reg = /^\d{,3}$/;
            if (!reg.test(this.value)) {
                this.value = isNaN(parseInt(this.value)) ? "" : parseInt(this.value);
            }
        }
        function regMoney() {
            var reg = /^\d*(\.)?\d*$/;
            if (!reg.test(this.value)) {
                this.value = isNaN(parseFloat(this.value)) ? "" : parseFloat(this.value);
            }
        }
    </script>
</head>

<body>
<table width="700" border="0" align="center" cellpadding="0" cellspacing="0" class="table table1">
    <tr>
        <td class="title" style="text-align:left;">
            进球数开奖信息录入
        </td>
    </tr>
    <tr>
        <td style="padding: 4px;">
            <form name="awardForm" id="awardForm" method="post"
                  action="${pageContext.request.contextPath}/manages/openAward?action=doEditOpenInfo">
                <input type="hidden" value="${issue.name}" name="name"/>
                <input type="hidden" value="${issue.lotteryCode}" name="lotteryCode"/>
                <table width="100%" border="0" cellpadding="4" cellspacing="0" class="seach_none">
                    <tr>
                        <td colspan="2" align="center">足彩进球数第${issue.name}期开奖信息录入</td>
                    </tr>
                    <tr>
                        <td width="45%" align="right">
                            彩种：
                        </td>
                        <td>
                            足彩进球数
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            彩期：
                        </td>
                        <td>
                            ${issue.name}期
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            开售时间：
                        </td>
                        <td>
                            ${issue.startTime}
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            止售时间：
                        </td>
                        <td>
                            ${issue.endTime}
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            最近开奖
                        </td>
                        <td>${lastIssue.name}期 开奖号码${lastIssue.bonusNumber}
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <br/><b>1.比赛结果（录入队伍的进球数0,1,2,3，超过3个球录入3 注：取消的场次填入9）</b>
                        </td>
                    </tr>
                    <c:if test="${subGameList == null || fn:length(subGameList) == 0}">
                        <tr>
                            <td colspan="2">
                                <span style="color:red;text-align: center;">该期次还没有场次数据</span>
                            </td>
                        </tr>
                    </c:if>
                    <td colspan="2" align="center">
                        <table width="75%" border="0" cellpadding="4" cellspacing="0" class="sub">
                            <tr>
                                <th colspan="2">主、客队</th>
                                <th>进球数</th>
                                <th>上半场进球数<br/>(填入数字)</th>
                                <th>全场进球数<br/>(填入数字)</th>
                            </tr>
                            <c:forEach var="subGame" items="${subGameList}" varStatus="cont">
                                <tr>
                                    <td width="20">&nbsp;</td>
                                    <td align="left">${cont.count * 2 - 1}、${subGame.masterName}</td>
                                    <td align="center">
                                        主 : <input type="text" value="${bonusNumber[(cont.count - 1)*2]}"
                                               id="bonusNumber"
                                               name="bonusNumber" size="10" reg="^\d+$" tip="进球数不能为空"/>
                                    </td>
                                    <td align="center">主 : <input type="text" value="${scoreAtHalfz[cont.count - 1]}"
                                                              id="scoreAtHalfz"
                                                              name="scoreAtHalfz" size="3"/>
                                    </td>
                                    <td align="center">主 : <input type="text" value="${secondHalfTheScorez[cont.count - 1]}"
                                                              id="secondHalfTheScorez" name="secondHalfTheScorez"
                                                              size="3"/></td>
                                </tr>
                                <tr>
                                    <td width="30">&nbsp;&nbsp;</td>
                                    <td align="left">${cont.count * 2}、${subGame.guestName}</td>
                                    <td align="center">
                                        客 : <input type="text" value="${bonusNumber[cont.count * 2 - 1]}"
                                               id="bonusNumber"
                                               name="bonusNumber" size="10" reg="^\d+$" tip="进球数不能为空"/>
                                    </td>
                                    <td align="center">客 : <input type="text" value="${scoreAtHalfk[cont.count - 1]}"
                                                              id="scoreAtHalfk"
                                                              name="scoreAtHalfk" size="3"/>
                                    </td>
                                    <td align="center">客 : <input type="text" value="${secondHalfTheScorek[cont.count - 1]}"
                                                              id="secondHalfTheScorek" name="secondHalfTheScorek"
                                                              size="3"/></td>
                                </tr>
                            </c:forEach>
                        </table>
                    </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <br/><b>2.奖金等级</b>
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            一等奖金
                        </td>
                        <td><input type="text" value="${lv01}" id="lv01" name="lv01"
                                   size="22"/>元
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <br/><b>3.奖池和中奖情况</b>
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            奖池滚存：
                        </td>
                        <td><input type="text" value="${issue.prizePool}" id="prizePool" name="prizePool"
                                   size="22"/>元
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            销售总额：
                        </td>
                        <td><input type="text" value="${issue.globalSaleTotal}" id="globalSaleTotal"
                                   name="globalSaleTotal" size="22"/>元
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            一等奖中奖注数：
                        </td>
                        <td>
                            <input type="text" value="${lv01zu}" id="lv01zu" name="lv01zu" size="22"/>注
                        </td>
                    </tr>

                    <tr>
                        <td colspan="2" align="center">
                            <input type="button" id="subBtn" value="确定" class="submit"
                                   style="width: 64px; border: none">
                            <input type="button" class="submit" value="取消" style="width: 64px; border: none"
                                   onclick="javascript:history.go(-1);"/>
                        </td>
                    </tr>
                </table>
            </form>
        </td>
    </tr>
</table>
</body>
</html>