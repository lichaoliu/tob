<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>查询</title>
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
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
    <tr>
        <td class="title" style="text-align:left;">
            彩票管理 >> 警报额度
        </td>
    </tr>
    <tr>
        <td style="padding: 4px;">
            <form name="form1" method="post" action="/manages/adminManages?action=alertAmountList">
            <table width="100%" border="1" cellpadding="4" cellspacing="0" class="seach">
                <tr>
                    <td align="left" width="50%">
                        <div style="float:left;">
                              <a href="/manages/adminManages?action=memberList"  style="font-weight: bold;">接入商金额统计</a>
                        </div>
                    </td>
                    <td align="right" width="50%">
                        <input type="button" name="used" class="submit" value="有效" style="width: 64px; border: none" onclick="usedAlertAmount();">
                        <input type="button" name="unused" class="submit" value="无效" style="width: 64px; border: none" onclick="unusedAlertAmount();">
                        <input type="button" name="Submit" class="submit" value="查询" style="width: 64px; border: none" onclick="qurAlertAmount();">
                    </td>
                </tr>
            </table>
            <table width="100%" border="1" cellpadding="4" cellspacing="0" class="content">
                <tr id="one">
                    <td width="3%"></td>
                    <td width="5%" align="center">序号 </td>
                    <td width="20%" align="center">出票口</td>
                    <td width="10%" align="center">警报金额</td>
                    <td width="10%" align="center">余额</td>
                    <td width="10%" align="center">状态</td>
                    <td width="15%" align="center">状态时间</td>
                    <td width="20%" align="center">支持的彩种</td>
                    <td width="7%"  align="center">操作</td>
                </tr>
                
                <c:forEach items="${alertAmountList}" var="alertAmountList" varStatus="cont">
                   <tr>
                     <td><input name="myCheckbox" type="checkbox" id="check${cont.count}" value="${alertAmountList.postCode}"></td>
	                 <td align="center">${cont.count}</td>
	                 <td align="center">${alertAmountList.postCode}:${alertAmountList.postName}</td>
	                 <td align="right">
	                     <fmt:formatNumber value="${alertAmountList.alertAmount == null ? 0.00 : alertAmountList.alertAmount}"  pattern="0.00"/>
	                 </td>
	                 <td align="right">
                         <c:choose>
                           <c:when test="${alertAmountList.balance == '--'}"><font color="red">--</font></c:when>
                           <c:when test="${alertAmountList.balance != '--' && alertAmountList.balance >= alertAmountList.alertAmount}"><font color="blue"><fmt:formatNumber value="${alertAmountList.balance}" pattern="0.00"/></font></c:when>
                           <c:when test="${alertAmountList.balance != '--' &&alertAmountList.balance < alertAmountList.alertAmount}"><font color="red"><fmt:formatNumber value="${alertAmountList.balance}" pattern="0.00"/></font></c:when>
                         </c:choose>
	                 </td>
	                 <td align="center">
	                 <c:if test="${alertAmountList.status==0}">无效</c:if>
                    <c:if test="${alertAmountList.status==1}">有效</c:if>
	                 </td>
	                 <td align="center"><fmt:formatDate value="${alertAmountList.statusTime}" pattern="yy-MM-dd HH:mm:ss"/></td>
	                 <td align="center">${alertAmountList.lotteryCode}</td>
	                 <td align="center"><a href="${pageContext.request.contextPath}/manages/adminManages?action=editAlertAmount&postCode=${alertAmountList.postCode}&postName=${alertAmountList.postName}&alertAmount=${alertAmountList.alertAmount}&status=${alertAmountList.status}&lotteryCode=${alertAmountList.lotteryCode}">修改</a></td>
	               </tr>
                </c:forEach>
            </table>
            </form>
        </td>
    </tr>
</table>
</body>
</html>
<script language="JavaScript">
      function addAlertAmount() {
    	  var iHeight = 350;
    	  var iWidth = 300;
    	  var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
    	  var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
          //var flag = window.showModalDialog("<%=request.getContextPath()%>/admin/manages/alertAmountInsert.jsp",new Array(),"dialogHeight:360px;dialogWidth:640px;status:no;location=no");
    	  window.open('<%=request.getContextPath()%>/admin/manages/alertAmountInsert.jsp','newwindow','height="+iHeight+",width="+iWidth+",top="+iTop+",left="+iLeft+",toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
      }
      
      function usedAlertAmount(){
    	  var j = 0;
          var myc = document.getElementsByName("myCheckbox");
          for (i = 0; i < myc.length; i++) {
              if (myc[i].checked) {
                  j++;
              }
          }
          if (j <= 0) {
              alert("请选中使之有效的数据!");
              return false;
          }
          
          if (confirm("确定要修改成'有效'?")) {
              document.form1.action = "/manages/adminManages?action=usedAlertAmount&status=1";
              form1.submit();
          }
      }
      
      function unusedAlertAmount(){
    	  var j = 0;
          var myc = document.getElementsByName("myCheckbox");
          for (i = 0; i < myc.length; i++) {
              if (myc[i].checked) {
                  j++;
              }
          }
          if (j <= 0) {
              alert("请选中使之无效的数据!");
              return false;
          }
          
          if (confirm("确定要修改成'无效'？")) {
              document.form1.action = "/manages/adminManages?action=usedAlertAmount&status=0";
              form1.submit();
          }
      }
      
      
      
      function qurAlertAmount() {
          document.form1.action = "/manages/adminManages?action=alertAmountList";
          form1.submit();
      }
</script>