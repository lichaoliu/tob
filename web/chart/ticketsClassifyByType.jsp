<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../jstl.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<c:set var="exportMaxPage" value="${myf:getExportMaxPage()}"/>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>彩票百分比</title>
	<script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery-1.5.2.min.js"></script>
	<script type="text/javascript" src="chart/js/highcharts.js"></script>
	<script type="text/javascript" src="chart/js/highcharts-3d.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/style/page.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/manages.css">
	<script type="text/javascript">
		function showChart(){
			var sid=$('#sid').val();
			var lotteryCode=$('#lotteryCode').val();
			var ticketStatus=$('#ticketStatus').val();
			var postCode=$('#postCode').val();
			var createStartTime=$('#createStartTime').val();
			var createEndTime=$('#createEndTime').val();
			var sendStartTime=$('#sendStartTime').val();
			var sendEndTime=$('#sendEndTime').val();
			var bonusStartTime=$('#bonusStartTime').val();
			var bonusEndTime=$('#bonusEndTime').val();
			var returnStartTime=$('#returnStartTime').val();
			var returnEndTime=$('#returnEndTime').val();
			var bonusStatus=$('#bonusStatus').val();
			var issueBonusStatus=$('#issueBonusStatus').val();
			var operatorsAward=$('#operatorsAward').val();
			var bigBonus=$('#bigBonus').val();
			var issueStatus=$('#issueStatus').val();
			
			var actin="action=ticketsClassifyByType&sid="+sid
				+"&lotteryCode="+lotteryCode
				+"&ticketStatus="+ticketStatus
				+"&postCode="+postCode
				+"&createStartTime="+createStartTime
				+"&createEndTime="+createEndTime
				+"&sendStartTime="+sendStartTime
				+"&sendEndTime="+sendEndTime
				+"&bonusStartTime="+bonusStartTime
				+"&bonusEndTime="+bonusEndTime
				+"&returnStartTime="+returnStartTime
				+"&returnEndTime="+returnEndTime
				+"&bonusStatus="+bonusStatus
				+"&issueBonusStatus="+issueBonusStatus
				+"&operatorsAward="+operatorsAward
				+"&bigBonus="+bigBonus
				+"&issueStatus="+issueStatus
			;
			 
			$('#container').highcharts({
				chart:{
					type:'pie',
					options3d:{
						enabled: true,
						alpha: 45
					}
				},
				title:{
					text:'中彩汇彩票'
				},
				subtitle:{
					text:'分类统计图(<b>票量太小,在左侧显示<b>)'
				},
				plotOptions:{
					pie:{
						innerSize: 100,
                		depth: 45
					}
				},
				tooltip: {
                	pointFormat: '{series.name}: <b>{point.percentage:.2f}%</b><br>彩票数量 <b>{point.y}张</b>'
            	},
            	
				series: [{
		            name: '票量百分比',
					data: (function() {
						var data = [];
						$.ajax({
						  type: "POST",
						   url: "ChartServlet",
						   data: actin,
						   dataType:"json",
						   async: false,
						   success: function(msg){
						   var json=msg;
						     for(var i=0;i<json.length;i++){
						     	var array=[];
						     	array.push(json[i]['CODE']);
						     	array.push(json[i]['PERCENT']);
						     	data.push(array);
						    }
						   }
						});
						return data;
					 })()
		        }],
		         credits:{
	                text:"中彩汇",
	                href:"http://www.zch168.com/",
	                style:{
	                    color:"red",
	                    fontSize: "15px",
	                    cursor: "pointer"
	                }
	            }
			});
		};
	</script>

  </head>
  
  <body>
  	<table width="100%" border="1" cellpadding="4" cellspacing="0" class="seach">
<tr>
    <td>接入商名称</td>
    <td>
        <select name="sid" id="sid">
            <option value="">全部</option>
            <c:forEach items="${myf:getMemberList()}" var="member">
                <option value="${member.sid}" <c:if test="${member.sid == sid}">selected</c:if>>${member.name}</option>
            </c:forEach>
        </select>
    </td>
    <td>期次状态</td>
    <td align="left">
        <select name="issueStatus" id="issueStatus" >
            <option value=""
                    <c:if test="${issueStatus == ''}">selected</c:if>>全部
            </option>
            <option value="0"
                    <c:if test="${issueStatus == 0}">selected</c:if>>预售
            </option>
            <option value="1"
                    <c:if test="${issueStatus == 1}">selected</c:if>>开售
            </option>
            <option value="2"
                    <c:if test="${issueStatus == 2}">selected</c:if>>暂停
            </option>
            <option value="3"
                    <c:if test="${issueStatus == 3}">selected</c:if>>结期
            </option>
        </select>
    </td>
    <td>彩票状态</td>
    <td>
        <select id="ticketStatus" name="ticketStatus">
            <option value=""
                    <c:if test="${'' == ticketStatus }">selected</c:if>>全部
            </option>
            <option value="0"
                    <c:if test="${0 == ticketStatus }">selected</c:if>>未送票
            </option>
            <option value="1"
                    <c:if test="${1 == ticketStatus }">selected</c:if>>调度中
            </option>
            <option value="2"
                    <c:if test="${2 == ticketStatus }">selected</c:if>>送票未回执
            </option>
            <option value="3"
                    <c:if test="${3 == ticketStatus }">selected</c:if>>出票成功
            </option>
            <option value="4"
                    <c:if test="${4 == ticketStatus }">selected</c:if>>出票失败
            </option>
            <option value="5"
                    <c:if test="${5 == ticketStatus }">selected</c:if>>系统取消
            </option>
            <option value="6"
                    <c:if test="${6 == ticketStatus }">selected</c:if>>重发
            </option>
        </select>
    </td>
    <td>出票口</td>
    <td align="left">
        <select id="postCode" name="postCode">
            <option value=""
                    <c:if test="${postCode == ''}">selected</c:if>>全部
            </option>
            <c:forEach var="postBean" items="${myf:getPostMaps()}">
                <option value="${postBean.code}"
                        <c:if test="${postCode == postBean.code}">selected</c:if>>${postBean.name}
                </option>
            </c:forEach>
            <option value="00"
                    <c:if test="${postCode == '00'}">selected</c:if>>待分配
            </option>
        </select>
    </td>
</tr>
<tr>

    <td>接收时间</td>
    <td>
        <input style="width: 83px;" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
               name="createStartTime" id="createStartTime" value="${createStartTime }" readonly/>
        - <input style="width: 83px;" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                 name="createEndTime" id="createEndTime" value="${createEndTime }" readonly/>
    </td>
    <td>送票时间</td>
    <td>
        <input style="width: 83px;" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
               name="sendStartTime" id="sendStartTime" value="${sendStartTime}" readonly/>
        - <input style="width: 83px;" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                 name="sendEndTime" id="sendEndTime" value="${sendEndTime}" readonly/>
    </td>
    <td>算奖时间</td>
    <td>
        <input style="width: 83px;" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
               name="bonusStartTime" id="bonusStartTime" value="${bonusStartTime}" readonly/>
        - <input style="width: 83px;" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                 name="bonusEndTime" id="bonusEndTime" value="${bonusEndTime}" readonly/>
    </td>
    <td>回执时间</td>
    <td>
        <input style="width: 83px;" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
               name="returnStartTime" id="returnStartTime" value="${returnStartTime }" readonly/>
        - <input style="width: 83px;" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                 name="returnEndTime" id="returnEndTime" value="${returnEndTime }" readonly/>
    </td>
</tr>
<tr>
    <td>中奖状态</td>
    <td>
        <select id="bonusStatus" name="bonusStatus">
            <option value=""
                    <c:if test="${'' == bonusStatus }">selected</c:if>>全部
            </option>
            <option value="0"
                    <c:if test="${'0' == bonusStatus }">selected</c:if>>未开奖
            </option>
            <option value="1"
                    <c:if test="${'1' == bonusStatus }">selected</c:if>>中奖
            </option>
            <option value="2"
                    <c:if test="${'2' == bonusStatus }">selected</c:if>>未中奖
            </option>
        </select>
    </td>
    <td>返奖状态</td>
    <td align="left">
        <select id="issueBonusStatus" name="issueBonusStatus">
            <option value=""
                    <c:if test="${issueBonusStatus == ''}">selected</c:if>>全部
            </option>
            <option value="0"
                    <c:if test="${issueBonusStatus == 0}">selected</c:if>>未返奖
            </option>
            <option value="1"
                    <c:if test="${issueBonusStatus == 1}">selected</c:if>>已返奖
            </option>
        </select>
    </td>
    <td>算奖状态</td>
    <td align="left">
        <select id="operatorsAward" name="operatorsAward">
            <option value=""
                    <c:if test="${operatorsAward == ''}">selected</c:if>>全部
            </option>
            <option value="0"
                    <c:if test="${operatorsAward == 0}">selected</c:if>>等待算奖
            </option>
            <option value="1"
                    <c:if test="${operatorsAward == 1}">selected</c:if>>算奖中
            </option>
            <option value="2"
                    <c:if test="${operatorsAward == 2}">selected</c:if>>算奖完成
            </option>
        </select>
    </td>
    <td>大小奖</td>
    <td align="left">
        <select id="bigBonus" name="bigBonus">
            <option value=""
                    <c:if test="${bigBonus == ''}">selected</c:if>>全部
            </option>
            <option value="0"
                    <c:if test="${bigBonus == 0}">selected</c:if>>小奖
            </option>
            <option value="1"
                    <c:if test="${bigBonus == 1}">selected</c:if>>大奖
            </option>
        </select>
    </td>
</tr>
<tr>
    <td></td>
    <td>
    </td>
    <td></td>
    <td>
        
    </td>
    <td align="center" colspan="4">
        <input type="button" name="Submit" onclick="showChart();" class="submit" value="查询" style="width: 64px; border: none">
    </td>
</tr>
</table>
  	<div id="container" style="min-width:800px;height:400px"></div>
  </body>
</html>
