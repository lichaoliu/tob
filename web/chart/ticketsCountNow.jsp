<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'ticketsCount.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery-1.5.2.min.js"></script>
	<script type="text/javascript" src="chart/js/highcharts.js"></script>
  	<script type="text/javascript">
		$(function () { 
         $('#container').highcharts({
            chart: {
                type: 'spline',
                marginRight: 10,
                events: {
                    //监听图表加载完成事件
                    load: function() {    
                        // 间隔一秒添加一个数据点 这里可以换成Ajax异步获取数据
                        var series = this.series[0];
                        setInterval(function() {
                        	$.ajax({
							   type: "POST",
							   url: "ChartServlet",
							    dataType:"json",
							   data: "action=ticketsCount_5",
							   async: false,
							   success: function(msg){
							   var json=msg;
							   	var time=(new Date()).getTime()+8*60*60*1000;
							   	console.info(time);
		                        var x =time, // 当前时间
		                           	y = json[0]['COUNT'];
		                        series.addPoint([x, y], true, true);
							   }
							});
                        }, 1000*60*0.5);
                    }
                }
            },
            title: {
                text: '中彩汇时实彩票数量监控'
            },
            subtitle:{
                text:"数量(张)"
            },
            xAxis: {
                type: 'datetime',//X轴类型               
                tickPixelInterval: 150 //刻度间隔像素值px 这个主要用于让多余的刻度自动隐藏掉
            },
            yAxis: {
                title: {
                    text: '数据值' //Y轴标题
                }
            },
            tooltip: {
                //数据提示格式化
                formatter: function() {
                	return '<b>'+ this.series.name +'</b><br>'+ Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) +'<br>'+ Highcharts.numberFormat(this.y, 0); 
                }
            },
            legend: {
                enabled: false
            },
            exporting: {
                enabled: false
            },
            credits:{
                text:"中彩汇",
                href:"http://www.zch168.com/",
                style:{
                    color:"red",
                    fontSize: "15px"
                },
                position:{
					align: 'right',
					x: -10,
					verticalAlign: 'bottom',
					y: -3
				}
            },
            series: [{
                name: '实时票量',
                data: (function() {
                    // 第一次加载的数据
                    var data = [];
					var time =(new Date).getTime()+8*60*60*1000;
                     $.ajax({
					  type: "POST",
					   url: "ChartServlet",
					   data: "action=ticketsCount_20",
					   dataType:"json",
					   async: false,
					   success: function(msg){
					   var json=msg;
					     for(var i=json.length-1;i>=0;i--){
					     	data.push({
	                            x: time-(json[i]['TIME']-1)*0.5*60*1000,
	                            y: json[i]['COUNT']
                        	});
					    }
					   }
					});
                    return data;
                })()
            }]
        });
	});
	</script>

  </head>
  
  <body>
    <div id="container" style="min-width:800px;height:400px"></div>
  </body>
</html>
