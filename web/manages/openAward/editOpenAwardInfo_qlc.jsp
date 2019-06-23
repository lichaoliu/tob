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
                if(!regSubmit($("#bonusNumber").val())){
                    alert("录入的开奖号码格式不对，请按举例格式录入");
                    return;
                }

                var str = "七乐彩第${issue.name}期";
                if (isNotNull($("#bonusNumber").val()))
                    str += "\n开奖号码为" + $("#bonusNumber").val();

                if (isNotNull($("#lv01").val()))
                    str += "\n一等奖金" + $("#lv01").val() + "元";
                if (isNotNull($("#lv02").val()))
                    str += "\n二等奖金" + $("#lv02").val() + "元";
                if (isNotNull($("#lv03").val()))
                    str += "\n三等奖金" + $("#lv03").val() + "元";


                var bln = confirm(str + "\n是否确认保存开奖信息?");
                if (bln) {
                    $("#awardForm").submit();
                } else {
//                    history.go(-1);
                    return;
                }
            });
        $("#bonusNumber").keyup(regOpenNumber);

            $("#prizePool").keyup(regNumber);
            $("#globalSaleTotal").keyup(regNumber);
            for(var i = 1;i <= 3;i++){
                $("#lv0"+i).keyup(regNumber);
            }
            for(var i = 1;i <= 7;i++){
                $("#lv0"+i+"zu").keyup(regInteger);
            }
        });

         function regNumber(){
            var reg = /^\d*\.{0,1}\d*$/;
            if(!reg.test(this.value)){
                this.value="";
            }
        }
        function regInteger(){
            var reg = /^\d*$/;
            if(!reg.test(this.value)){
                this.value="";
            }
        }

        function regOpenNumber() {
            var reg = /^(\d|,|#){0,30}$/;
            if (!reg.test(this.value)) {
                this.value = "";//parseInt(this.value.substring(0, maxLen));
            }
        }

         function regSubmit(bonusNumber){
            var reg = /^(\d{2},){6}\d{2}#\d{2}$/;
            if(reg.test(bonusNumber)){
                return true;
            }else{
                return false;
            }
        }
    </script>
</head>

<body>
<table width="900" border="0" align="center" cellpadding="0" cellspacing="0" class="table table1">
    <tr>
        <td class="title" style="text-align:left;">
            七乐彩开奖信息录入
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
                        <td colspan="2" align="center">七乐彩第${issue.name}期开奖信息录入</td>
                    </tr>
                    <tr>
                        <td width="45%" align="right">
                            彩种：
                        </td>
                        <td>
                            七乐彩
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
                            <br/><b>1.开奖号码</b>
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            开奖号码
                        </td>
                        <td><input type="text" value="${bonusNumber}" id="bonusNumber" name="bonusNumber" size="22"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" width="20%" align="center">
                            录入格式举例：开奖号码为01,02,03,04,05,06,07，特别号码为08则录入01,02,03,04,05,06,07#08
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
                        <td>
                            <input type="text" value="${lv01}" id="lv01" name="lv01" size="22"/>元
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            二等奖金
                        </td>
                        <td>
                           <input type="text" value="${lv02}" id="lv02" name="lv02" size="22"/>元
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            三等奖金
                        </td>
                        <td>
                           <input type="text" value="${lv03}" id="lv03" name="lv03" size="22"/>元
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" colspan="2" align="center">
                            四等奖金200元
                        </td>
                        <td>
                            &nbsp;
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" colspan="2" align="center">
                            五等奖金50元
                        </td>
                        <td>
                            &nbsp;
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" colspan="2" align="center">
                            六等奖金10元
                        </td>
                        <td>
                            &nbsp;
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" colspan="2" align="center">
                            七等奖金5元
                        </td>
                        <td>
                            &nbsp;
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
                        <td><input type="text" value="${issue.prizePool}" name="prizePool" id="prizePool" size="22"/>元
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            销售总额：
                        </td>
                        <td><input type="text" value="${issue.globalSaleTotal}" name="globalSaleTotal" id="globalSaleTotal" size="22"/>元
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            一等奖中奖注数：
                        </td>
                        <td>
                            <input type="text" value="${lv01zu}" name="lv01zu" id="lv01zu" size="22"/>注
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            二等奖中奖注数：
                        </td>
                        <td>
                            <input type="text" value="${lv02zu}" name="lv02zu" id="lv02zu" size="22"/>注
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            三等奖中奖注数：
                        </td>
                        <td>
                            <input type="text" value="${lv03zu}" name="lv03zu" id="lv03zu" size="22"/>注
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            四等奖中奖注数：
                        </td>
                        <td>
                            <input type="text" value="${lv04zu}" name="lv04zu" id="lv04zu" size="22"/>注
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            五等奖中奖注数：
                        </td>
                        <td>
                            <input type="text" value="${lv05zu}" name="lv05zu" id="lv05zu" size="22"/>注
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            六等奖中奖注数：
                        </td>
                        <td>
                            <input type="text" value="${lv06zu}" name="lv06zu" id="lv06zu" size="22"/>注
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            七等奖中奖注数：
                        </td>
                        <td>
                            <input type="text" value="${lv07zu}" name="lv07zu" id="lv07zu" size="22"/>注
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