<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>联盟列表</title>
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
<c:if test="${requestScope.msg != null}">
    <script type="text/javascript">
        alert('${requestScope.msg}');
        window.close();
    </script>
</c:if>
<c:if test="${pageBean.pageContent!=null}">
	 <script type="text/javascript">
		 var json=null;
			$.ajax({
			  type: "POST",
			   url: "/ChartServlet",
			   data: "action=ticketsNum30Days",
			   dataType:"json",
			   async: false,
			   success: function(msg){
			   	json=msg
			   }
			});
			<%
			String str="code";
			%>
	 	 <c:forEach var="cooperation" items="${pageBean.pageContent}" varStatus="cont">
	 	 	$(function () {
			    $('#${cooperation[0].sid}').highcharts({
			        chart: {
			            type: 'line'
			        },
			        title: {
			            text: '<b>${cooperation[0].name}</b>'
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
				    		
				    		if(json.<%=str%>${cooperation[0].sid}){
				    			data=json.<%=str%>${cooperation[0].sid};
				    			return data;
				    		}else{
				    			return data;
				    		}
				    		
					 })()
			        }],
			        credits:{
	                text:"彩种详情",
	                href:"/chart/ticketsFor30daysDetail.jsp?sid="+${cooperation[0].sid},
	                style:{
	                    color:"red",
	                    fontSize: "15px",
	                    cursor: "pointer"
	                }
	            }
			    });
			});				
	 	 </c:forEach>
	 </script>
</c:if>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
    <tr>
        <td class="title" style="text-align:left;">
            图表管理 >> 投注钱款(商户)
        </td>
    </tr>
    <tr>
        <td colspan="2" style="padding: 4px;">
            <form name="form1" id="form1" method="post"
                  action="${pageContext.request.contextPath}/ChartServlet?action=memberList">
                <input type="hidden" name="startPage" id="startPage"/>
                <input type="hidden" name="endPage" id="endPage"/>
                <table width="100%" border="1" cellpadding="4" cellspacing="0" class="seach">
                    <tr>
                        <td>接入商名称</td>
                        <td>
                            <select name="sid" id="sid">
                                <option value="">全部</option>
                                <c:forEach items="${myf:getMemberList()}" var="member">
                                    <option value="${member.sid}"
                                            <c:if test="${member.sid == sid}">selected</c:if>>${member.name}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>接入商状态</td>
                        <td>
                            <select name="status">
                                <option value="" <c:if test="${status eq ''}">selected</c:if>>全部</option>
                                <option value="1" <c:if test="${status == 1}">selected</c:if>>开通</option>
                                <option value="2" <c:if test="${status == 2}">selected</c:if>>冻结</option>
                            </select>
                        </td>

                        <td align="right"><input type="submit" name="Submit" class="submit" value="查询"
                                                 style="width: 64px; border: none"></td>


                    </tr>

                </table>
            </form>
            <table width="100%" border="1" cellpadding="4" cellspacing="0" class="content">
                <c:if test="${pageBean.pageContent!=null}">
                    <tr>
                        <td align="left" colspan="6">
                            <span style="color:#0033cc;font-weight: bold;">[本次查询结果共${pageBean.itemTotal }条记录]</span>
                        </td>
                    </tr>
                </c:if>
                <tr id="one" height="10">
                    <td align="center" width="5%">序号</td>
                    <td>商户30天投注金额</td>
                </tr>
                <c:if test="${pageBean.pageContent==null}">
                    <tr>
                        <td colspan="6" align="center">请在上面填写筛选条件，并点击查询按钮来查询数据！</td>
                    </tr>
                </c:if>
                <c:if test="${pageBean.pageContent!=null}">
                    <c:forEach var="cooperation" items="${pageBean.pageContent}" varStatus="cont">
                        <tr>
                            <td align="center" width='5%'>
                                    ${(page-1) * requestScope.pageSize + cont.count}
                            </td>
                            <td>
                            	<div id="${cooperation[0].sid}" style="height:200px"></div>
                            </td>
                        </tr>
                        <tr height="20">
                        	<td></td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="6" align="right">
                            <page:pageTag page="${requestScope.page}" pageSize="${requestScope.pageSize}"
                                          total="${pageBean.itemTotal}"
                                          gotoURI="${pageContext.request.contextPath}/ChartServlet?action=memberList"/>
                        </td>
                    </tr>
                </c:if>
            </table>
        </td>
    </tr>
</table>
<script>
    function ifLink(str) {
        var bln = confirm(str);
        return bln;
    }
</script>
</body>
</html>
