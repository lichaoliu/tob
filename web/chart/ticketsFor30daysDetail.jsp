<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <title>彩期查询</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="Cache-Control" content="max-age=0" forua="true"/>
    <meta http-equiv="Cache-Control" content="no-cache" forua="true"/>
    <meta http-equiv="Cache-Control" content="must-revalidate" forua="true"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery-1.5.2.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/chart/js/highcharts.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/chart/js/highcharts-3d.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/validator.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/page.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/page.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/manages.css">
    <script type="text/javascript">
    	$(function () {
    	<%
    	String sid=request.getParameter("sid");
    	%>
		    $('#container').highcharts({
		        chart: {
		            type: 'line'
		        },
		        title: {
		            text: '<b>金额详情</b>'
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
		                text: '钱款 (元)'
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
		        series: (function() {
						var data = [];
						$.ajax({
						  type: "POST",
						   url: "/ChartServlet",
						   data: "action=ticketsFor30daysDetail&sid="+<%=sid%>,
						   dataType:"json",
						   async: false,
						   success: function(msg){
							   var json=msg;
							   data=json
						   }
						});
						return data;
					 })(),
				credits:{
	                enabled:false
	                }
		    });
		});				
    </script>
</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
    <tr>
        <td class="title" style="text-align:left;">
            	彩票票量详细
        </td>
    </tr>
    <tr>
        <td style="padding: 4px;">
           <div id="container" style="min-width:1000px;height:400px"></div>
        </td>
    </tr>
</table>
</body>
</html>