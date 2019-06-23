<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ include file="../../jstl.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>彩种列表</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery-1.5.2.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/chart/js/highcharts.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/chart/js/highcharts-3d.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/style/page.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/manages.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/validator.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/page.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/page.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/manages.css">
</head>

<body>
<c:set var="lotteryList" value="${myf:findAllLotteryCode()}"></c:set>
<c:if test="${lotteryList!=null}">
	 <script type="text/javascript">
		 var json=null;
			$.ajax({
			  type: "POST",
			   url: "/ChartServlet",
			   data: "action=ticketFor30DaysByCode",
			   dataType:"json",
			   async: false,
			   success: function(msg){
			   	json=msg
			   }
			});
			console.info(json);
			<%
			String str="code";
			%>
	 	 <c:forEach var="lottery" items="${lotteryList}" varStatus="cont">
	 	 	$(function () {
			    $('#${lottery.key}').highcharts({
			        chart: {
			            type: 'line'
			        },
			        title: {
			            text: '<b>${lottery.value.name}</b>'
			        },
			        xAxis: {
		            	categories: (function(){
		            		var data = [];
		            		var now= new Date();
		            		for(var i=30;i>0;i--){
								var day=now.getTime()-i*24*60*60*1000;
								var d=new Date(day);
								data.push(d.getMonth()+1+"-"+d.getDate());
							}
							return data;
		            	})()
			        },
			        yAxis: {
			            title: {
			                text: '钱款(元)'
			            }
			        },
			        plotOptions: {
			            line: {
			                dataLabels: {
			                    enabled: true
			                },
			                enableMouseTracking: false
			            }
			        },
			        series: [{
		            name: '投注金额',
				    data: (function() {
				    		var data=[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
				    		
				    		if(json.<%=str%>${lottery.key}){
				    			data=json.<%=str%>${lottery.key};
				    			return data;
				    		}else{
				    			return data;
				    		}
				    		
					 })()
			        }],
			        credits:{
	                enabled:false,
	            }
			    });
			});				
	 	 </c:forEach>
	 </script>
</c:if>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
    <tr>
        <td class="title" style="text-align:left;">
            图表管理 >> 投注钱款(彩种)
        </td>
    </tr>
    <tr>
        <td colspan="2" style="padding: 4px;">
            <form name="form1" id="form1" method="post"
                  action="${pageContext.request.contextPath}/ChartServlet?action=lotteryList">
                <table width="100%" border="1" cellpadding="4" cellspacing="0" class="seach">
                    <tr>
                        <td>彩种</td>
                        <td>
                            <select name="lotteryCode">
                                <option value=""
                                        <c:if test="${'' == lotteryIssue.lotteryCode }">selected</c:if>>
                                                                                                                       全部
                                </option>
                                <c:forEach var="lottery" items="${lotteryList}">
                                    <option value="${lottery.key}"
                                            <c:if test="${lottery.key == lotteryCode}">selected</c:if> >
                                            ${lottery.value.name }
                                    </option>
                                </c:forEach>
                            </select>
                        </td>
                        <td align="right"><input type="submit" name="Submit" class="submit" value="查询"
                                                 style="width: 64px; border: none"></td>


                    </tr>

                </table>
            </form>
            <table width="100%" border="1" cellpadding="4" cellspacing="0" class="content">
            	<tr>
                  	<td align="left" colspan="6">
                      	<span style="color:#0033cc;font-weight: bold;">[本次查询结果共${fn:length(lotteryList)}条记录]</span>
                  	</td>
              	</tr>
                <tr id="one" height="10">
                    <td align="center" width="5%">序号</td>
                    <td>彩种30天投注金额</td>
                </tr>
                <c:if test="${lotteryList!=null}">
                    <c:forEach var="lottery" items="${pageBean.pageContent}" varStatus="cont">
                        <tr>
                            <td align="center" width='5%'>
                                    ${cont.count}
                            </td>
                            <td>
                            	<div id="${lottery.lotteryCode}" style="height:200px"></div>
                            </td>
                        </tr>
                        <tr height="20">
                        	<td></td>
                        </tr>
                    </c:forEach>
                </c:if>
            </table>
        </td>
    </tr>
</table>
</body>
</html>
