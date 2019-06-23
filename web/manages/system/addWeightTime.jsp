<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/jstl.jsp" %>
<html>
<head>
    <title>添加出票口玩法时间配置</title>
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
    
	    function save() {
	    	var j = 0;
	        var myc = document.getElementsByName("week");
	        for (i = 0; i < myc.length; i++) {
	            if (myc[i].checked) {
	                j++;
	            }
	        }
	        if (j <= 0) {
	            alert("请选择要添加的周配置");
	            return false;
	        }
	        
	        var startTime = document.getElementById("startTime").value;
			if(startTime == null || startTime == ''){
				alert("请选择开始时间");
	            return false;
			}	        
			
			var checkType = document.getElementById("type");
			var type = checkType.options[checkType.options.selectedIndex].value;
			if(type == 'constant'){
				var postCheck = document.getElementById("postCodeconstant");
	    		var postconstant = postCheck.options[postCheck.options.selectedIndex].value;
	    		if(postconstant == 0){
	    			alert("请选择出票口");
		            return false;
	    		}
	    	}
			var postNum = document.getElementById("postNum").value;
			
			if(type == 'amount'){
				
				var operateCheck = document.getElementById("operate");
				var operate = operateCheck.options[operateCheck.options.selectedIndex].value;
				var amount = document.getElementById("amount").value;
				var amountpostCodeCheck = document.getElementById("amountpostCode");
				var amountpostCode = amountpostCodeCheck.options[amountpostCodeCheck.options.selectedIndex].value;
				//alert("i:" + i + "operate:" + operate + " amount:" + amount + " amountpostCode:" + amountpostCode);
				if(operate == '0' || amount == null || amount == '' || amount <=0 || amountpostCode == '0'){
					alert("请至少配置一个投注金额大于0的出票口！");
					return false;
				}
				
			}
				    	
			if(type == 'proportion'){
				var flag2 = 0;
				for(var i = 1; i <= postNum; i ++){
					var proportion = document.getElementById("proportion" + i).value;
					var proportionpostCodeCheck = document.getElementById("proportionpostCode" + i);
					var proportionpostCode = proportionpostCodeCheck.options[proportionpostCodeCheck.options.selectedIndex].value;
					//alert("i:" + i + "proportion:" + proportion + " proportionpostCodeCheck:" + proportionpostCodeCheck + " proportionpostCode:" + proportionpostCode);
					if((proportion <= 0 || proportion == '' || proportion == null) && proportionpostCode != '0'){
						alert("比例必须不为空且大于0");
						return false;
					}
					
					if((proportion != null && proportion != '' && proportion <= 0) && proportionpostCode == '0'){
						alert("比例必须不为空且大于0");
						return false;
					}
					
					if((proportion != null && proportion != '' && proportion > 0) && proportionpostCode == '0'){
						alert("请为比例选择出票口");
						return false;
					}
					
					if((proportion != null && proportion != '' && proportion > 0) && proportionpostCode != '0'){
						flag2 = 1;
					}
				}
				if(flag2 == 0){
					alert("请至少配置一个出票口！");
					return false;
				}
			}
			
	        if (confirm("确定要保存此玩法时间配置？")) {
	        	var playId = document.getElementById("playId").value;
	        	var weightId = document.getElementById("weightId").value;
		    	var lotteryCode = document.getElementById("lotteryCode").value;
	            document.form1.action = "/manages/postManagesServlet?action=add_weight_time&playId=" + playId + "&weightId=" + weightId + "&lotteryCode=" + lotteryCode;
	            form1.submit();
	        }
	    }
	    
	    function typeChange(type){
	    	
	    	var constantdiv = document.getElementById("constantdiv");	
	    	var amountdiv = document.getElementById("amountdiv");
	    	var proportiondiv = document.getElementById("proportiondiv");
	    	if(type == 'constant'){
	    		constantdiv.style.display='';
	    		amountdiv.style.display='none';
	    		proportiondiv.style.display='none';
	    	}
	    	
			if(type == 'amount'){
				constantdiv.style.display='none';
	    		amountdiv.style.display='';
	    		proportiondiv.style.display='none';	
			}
				    	
			if(type == 'proportion'){
				constantdiv.style.display='none';
	    		amountdiv.style.display='none';
	    		proportiondiv.style.display='';
			}
	    }
	    function backToLast() {
	    	var playId = document.getElementById("playId").value;
        	var weightId = document.getElementById("weightId").value;
	    	var lotteryCode = document.getElementById("lotteryCode").value;
            document.form1.action = "/manages/postManagesServlet?action=load_weight_time&weightId="+weightId+"&playId="+playId+"&lotteryCode=" + lotteryCode;
            form1.submit();
        }
    </script>
</head>

<body>
<input type="hidden" id="postNum" value="${fn:length(postLotteryList)}">
<input type="hidden" id="playId" value="${playId}">
<input type="hidden" id="weightId" name="weightId" value="${weightId}">
<input type="hidden" id="lotteryCode" name="lotteryCode" value="${lotteryCode}">
<c:if test="${msg != null}">
    <script type="text/javascript">
        alert('${msg}');
    </script>
</c:if>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
    <tr>
        <td class="title" style="text-align:left;">系统参数设置 >> 出票口管理>>玩法管理>>时间配置</td>
    </tr>
    <tr>
		<td align="left" colspan="11">
			<span style="color:#0033cc;font-weight: bold;">
				<input type="button" value="保存" onclick="return save()" class="submit" style="width: 64px; border: none">
				<input type="button" value="返回" onclick="return backToLast();" class="submit" style="width: 64px; border: none">
			</span>
		</td>		
	</tr>
    <c:if test="${msg != null}">
	    <tr>
	        <td class="title">${msg}</td>
	    </tr>
	</c:if>
    <tr>
        <td style="padding: 4px;">
            <form name="form1" method="post" action="/manages/postManagesServlet?action=add_weight_time">
                <table  width="100%" border="1" cellpadding="4" cellspacing="0" class="content">
	                    <tr>
							<td align="center" width="20%">周配置</td>
	                        <td width="80%">
	                        	<!-- <c:forEach var="weeks" items="${weeksMap}">
	                        		<input type="checkbox" name="week" value="${weeks.key}">${weeks.value}
	                        	</c:forEach> -->
	                        	<input type="checkbox" name="week" value="2">星期一
	                        	<input type="checkbox" name="week" value="3">星期二
	                        	<input type="checkbox" name="week" value="4">星期三
	                        	<input type="checkbox" name="week" value="5">星期四
	                        	<input type="checkbox" name="week" value="6">星期五
	                        	<input type="checkbox" name="week" value="7">星期六
	                        	<input type="checkbox" name="week" value="1">星期日
	                        </td>
	                    </tr>
	                    <tr>
							<td align="center" width="20%">开始时间</td>
	                        <td width="80%">
	                        	<input style="width: 83px;" type="text" id="startTime" onfocus="WdatePicker({dateFmt:'HH:mm:ss'})"
              									name="startTime" value="" readonly/>
	                        </td>
	                    </tr>
	                     <tr>
							<td align="center" width="20%">类型</td>
	                        <td width="80%">
	                        	<select name="type" id="type" onchange="typeChange(this.options[this.options.selectedIndex].value)">
									  <option value="constant" selected>常规</option>
									  <option value="amount">投注金额</option>
									  <option value="proportion">比例</option>
							    </select>
	                        </td>
	                    </tr>
	                    <tr>
							<td align="center" width="20%">出票口</td>
	                        <td width="80%">
	                        	<div id = "constantdiv">
		                        	<select name="postCodeconstant" id="postCodeconstant">
										<option value="0">全部</option>
					                	<c:forEach var="postBean" items="${postLotteryList}">
					                		<option value="${postBean.postCode}">${postBean.postName}</option>
	            						</c:forEach>
				            		</select>
			            		</div>
			            		
			            		<div id="amountdiv" style="display:none;">
				            			投注金额<select id="operate" name="operate">
				            			  <option value="0">全部</option>
										  <option value="大于">大于</option>
										  <option value="大于等于">大于等于</option>
										  <option value="小于">小于</option>
										  <option value="小于等于">小于等于</option>
										  <option value="等于">等于</option>
									    </select>
									    <input type="text" id="amount" name="amount" value="">元：
									    <select id="amountpostCode" name="amountpostCode">
											<option value="0">全部</option>
						                	<c:forEach var="postBean" items="${postLotteryList}">
						                		<option value="${postBean.postCode}">${postBean.postName}</option>
		            						</c:forEach>
					            		</select>
					            		<br>
			            		</div>
			            		
			            		<div id="proportiondiv" style="display:none;">
			            			<c:forEach var="postBean2" items="${postLotteryList}"  varStatus="nums">
				            			比例设置:<input type="text" id="proportion${nums.count}" name="proportion${nums.count}">：
			                       		<select id="proportionpostCode${nums.count}" name="proportionpostCode${nums.count}">
											<option value="0">全部</option>
						                	<c:forEach var="postBean" items="${postLotteryList}">
						                		<option value="${postBean.postCode}">${postBean.postName}</option>
		            						</c:forEach>
					            		</select>
					            		<br>
				            		</c:forEach>
			            		</div>
	                        </td>
	                    </tr>
                </table>
            </form>
        </td>
    </tr>
</table>
</body>
</html>