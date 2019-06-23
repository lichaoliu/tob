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

                var str = "超级大乐透第${issue.name}期";
                if (isNotNull($("#bonusNumber").val()))
                    str += "\n开奖号码为" + $("#bonusNumber").val();

                if (isNotNull($("#lv01").val()))
                    str += "\n一等奖金" + $("#lv01").val() + "元";
                if (isNotNull($("#lv11").val()))
                    str += "\n一等追加奖金" + $("#lv11").val() + "元";
                if (isNotNull($("#lv02").val()))
                    str += "\n二等奖金" + $("#lv02").val() + "元";
                if (isNotNull($("#lv12").val()))
                    str += "\n二等追加奖金" + $("#lv12").val() + "元";
                if (isNotNull($("#lv03").val()))
                    str += "\n三等奖金" + $("#lv03").val() + "元";
                if (isNotNull($("#lv13").val()))
                    str += "\n三等追加奖金" + $("#lv13").val() + "元";
                if (isNotNull($("#lv10").val()))
                    str += "\n生肖乐一奖金" + $("#lv10").val() + "元";

                var bln = confirm(str + "\n是否确认保存开奖信息?");
                if (bln) {
                    $("#awardForm").submit();
                } else {
//                    history.go(-1);
                    return;
                }
            });

            $("#bonusNumber").keyup(regOpenNumber);
            
            $("#plsSaleTotal").keyup(regNumber);
            $("#globalSaleTotal").keyup(regNumber);
            $("globalSaleTotal10").keyup(regNumber);
            for(var i = 1;i <= 3;i++){
                $("#lv0"+i).keyup(regNumber);
                $("#lv1"+i).keyup(regNumber);
            }
            $("#lv10").keyup(regNumber);
            for(var i = 1;i <= 8;i++){
                $("#lv0"+i+"zu").keyup(regInteger);
                $("#lv1"+i+"zu").keyup(regInteger);
            }
            $("#lv10zu").keyup(regInteger);
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
            var reg = /^(\d|,|#){0,20}$/;
            if (!reg.test(this.value)) {
                this.value = "";//parseInt(this.value.substring(0, maxLen));
            }
        }

        function regSubmit(bonusNumber){
            var reg = /^(\d{2},){4}\d{2}#\d{2},\d{2}$/;
            if(reg.test(bonusNumber)){
                return true;
            }else{
                return false;
            }
        }
    </script>
</head>

<body>
<table width="700" border="0" align="center" cellpadding="0" cellspacing="0" class="table table1">
<tr>
    <td class="title" style="text-align:left;">
            大乐透开奖信息录入
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
    <td colspan="2" align="center">超级大乐透第${issue.name}期开奖信息录入</td>
</tr>
<tr>
    <td width="45%" align="right">
        彩种：
    </td>
    <td>
        超级大乐透
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
        录入格式举例：开奖号码的前区为01,02,03,12,17;后区为01,10
        <br/>则录入01,02,03,12,17#01,10
    </td>
</tr>
<tr>
    <td colspan="2">
        <br/><b>2.奖金等级</b>
    </td>
</tr>
<tr>
    <td width="20%" align="right">
        一等奖金<input type="text" value="${lv01}" id="lv01" name="lv01" size="10"/>元
    </td>

    <td>
        追加<input type="text" value="${lv11}" id="lv11" name="lv11" size="10"/>元
    </td>
</tr>
<tr>
    <td width="20%" align="right">
        二等奖金<input type="text" value="${lv02}" id="lv02" name="lv02" size="10"/>元
    </td>

    <td>
        追加<input type="text" value="${lv12}" id="lv12" name="lv12" size="10"/>元
    </td>
</tr>
<tr>
    <td width="20%" align="right">
        三等奖金<input type="text" value="${lv03}" id="lv03" name="lv03" size="10"/>元
    </td>

    <td>
        追加<input type="text" value="${lv13}" id="lv13" name="lv13" size="10"/>元
    </td>
</tr>
<tr>
    <td width="20%" align="right">
        四等奖金<input type="text" value="${lv04}" id="lv04" name="lv04" size="10"/>元
    </td>

    <td>
        追加<input type="text" value="${lv14}" id="lv14" name="lv14" size="10"/>元
    </td>
</tr>
<tr>
    <td width="20%" align="right">
        五等奖金<input type="text" value="${lv05}" id="lv05" name="lv05" size="10"/>元
    </td>

    <td>
        追加<input type="text" value="${lv15}" id="lv15" name="lv15" size="10"/>元
    </td>
</tr>
<tr>
    <td width="20%" align="right">
        六等奖金<input type="text" value="${lv06}" id="lv06" name="lv06" size="10"/>元
    </td>
</tr>
<!-- 
<tr>
    <td width="20%" align="right">
        七等奖金10元
    </td>

    <td>
        追加5元
    </td>
</tr>
<tr>
    <td width="20%" align="right">
        八等奖金5元
    </td>
    <td>
        &nbsp;
    </td>
</tr>

<tr>
    <td width="20%" colspan="2" align="center">
        生肖乐一等奖<input type="text" value="${lv10}" id="lv10" name="lv10" size="10"/>元
    </td>
    <td>
        &nbsp;
    </td>
</tr>
 -->
<tr>
    <td colspan="2">
        <br/><b>3.奖池和中奖情况</b>
    </td>
</tr>
<tr>
    <td width="20%" align="right">
        奖池滚存：
    </td>
    <td><input type="text" value="${issue.prizePool}" name="prizePool" id="prizePool" size="10"/>元
    </td>
</tr>
<tr>
    <td width="20%" align="right">
        销售总额：
    </td>
    <td><input type="text" value="${issue.globalSaleTotal}" name="globalSaleTotal" id="globalSaleTotal" size="10"/>元
    </td>
</tr>
<!-- 
<tr>
    <td width="20%" align="right">
        生肖乐销售总额：
    </td>
    <td><input type="text" value="${issue.backup2}" name="globalSaleTotal10" id="globalSaleTotal10" size="10"/>元
    </td>
</tr>
 -->
<tr>
    <td width="20%" align="right">
        一等奖中奖注数：<input type="text" value="${lv01zu}" name="lv01zu" id="lv01zu" size="5"/>注
    </td>
    <td>
        追加<input type="text" value="${lv11zu}" name="lv11zu" id="lv11zu" size="5"/>注
    </td>
</tr>
<tr>
    <td width="20%" align="right">
        二等奖中奖注数：<input type="text" value="${lv02zu}" name="lv02zu" id="lv02zu" size="5"/>注
    </td>
    <td>
        追加<input type="text" value="${lv12zu}" name="lv12zu" id="lv12zu" size="5"/>注
    </td>
</tr>
<tr>
    <td width="20%" align="right">
        三等奖中奖注数：<input type="text" value="${lv03zu}" name="lv03zu" id="lv03zu" size="5"/>注
    </td>
    <td>
        追加<input type="text" value="${lv13zu}" name="lv13zu" id="lv13zu" size="5"/>注
    </td>
</tr>
<tr>
    <td width="20%" align="right">
        四等奖中奖注数：<input type="text" value="${lv04zu}" name="lv04zu" id="lv04zu" size="5"/>注
    </td>
    <td>
        追加<input type="text" value="${lv14zu}" name="lv14zu" id="lv14zu" size="5"/>注
    </td>
</tr>
<tr>
    <td width="20%" align="right">
        五等奖中奖注数：<input type="text" value="${lv05zu}" name="lv05zu" id="lv05zu" size="5"/>注
    </td>
    <td>
        追加<input type="text" value="${lv15zu}" name="lv15zu" id="lv15zu" size="5"/>注
    </td>
</tr>
<tr>
    <td width="20%" align="right">
        六等奖中奖注数：<input type="text" value="${lv06zu}" name="lv06zu" id="lv06zu" size="5"/>注
    </td>
</tr>
<!-- 
<tr>
    <td width="20%" align="right">
        七等奖中奖注数：<input type="text" value="${lv07zu}" name="lv07zu" id="lv07zu" size="5"/>注
    </td>
    <td>
        追加<input type="text" value="${lv17zu}" name="lv17zu" id="lv17zu" size="5"/>注
    </td>
</tr>
<tr>
    <td width="20%" align="right">
        八等奖中奖注数：<input type="text" value="${lv08zu}" name="lv08zu" id="lv08zu" size="5"/>注
    </td>
    <td>
        &nbsp;
    </td>
</tr>

<tr>
    <td width="20%" align="right">
        生肖乐一等奖注数：<input type="text" value="${lv10zu}" name="lv10zu" id="lv10zu" size="5"/>注
    </td>
    <td>
        &nbsp;
    </td>
</tr>
 -->
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