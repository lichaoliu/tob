<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/jstl.jsp" %>
<html>
<head>
    <title>出票口管理</title>
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
    
	    function flushDatabase() {
	        var j = 0;
	        var myc = document.getElementsByName("myCheckbox");
	        for (i = 0; i < myc.length; i++) {
	            if (myc[i].checked) {
	                j++;
	            }
	        }
	        if (j <= 0) {
	            alert("请选中要修改出票口彩种");
	            return false;
	        }
	
	        if (confirm("确定要修改数据库出票口？")) {
	            document.form1.action = "/manages/postManagesServlet?action=reload_db_weight";
	            form1.submit();
	        }
	    }
	    
	    function releasePostConfig() {
	
	        if (confirm("确定要将数据库出票口配置发布成文件？")) {
	            document.form1.action = "/manages/postManagesServlet?action=release_control_weight";
	            form1.submit();
	        }
	    }
	    
        function loadFile() {
            if (confirm("确定要加载出票口配置文件？")) {
                document.form1.action = "/manages/postManagesServlet?action=reload_file_weight";
                form1.submit();
            }
        }
    </script>
</head>

<body>

<c:if test="${msg != null}">
    <script type="text/javascript">
        alert('${msg}');
    </script>
</c:if>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
    <tr>
        <td class="title" style="text-align:left;">系统参数设置 >> 出票口管理</td>
    </tr>
    <tr>
		<td align="left" colspan="11">
			<span style="color:#0033cc;font-weight: bold;">
				<input type="button" value="修改数据" onclick="return flushDatabase()" class="submit" style="width: 64px; border: none">
				<input type="button" value="发布配置" onclick="return releasePostConfig()" class="submit" style="width: 64px; border: none">
				<input type="button" value="加载文件" onclick="return loadFile();" class="submit" style="width: 64px; border: none">
			</span>
			<br/><hr/>
			<span style="color:#0033cc;">
				修改数据:给选中的彩种,更新出票口到数据库;<br/>
				发布配置:读取数据库的出票口配置到配置文件;<br/>
				加载文件:加载配置文件,到缓存中,并从数据库里读取出票口信息在列表里显示;
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
            <form name="form1" method="post" action="/manages/postManagesServlet?action=reload_db_weight">
                <table width="100%" border="1" cellpadding="4" cellspacing="0" class="content">
                    <tr id=one>
						<td width="3%"></td>
                    	<td width="3%">序号</td>
                        <td width="10%">彩种编码</td>
                        <td width="10%">彩种名称</td>
                        <td width="10%">出票口编码</td>
                        <td width="20%">出票口名称</td>
                        <td width="20%">选择出票口</td>
                        <td>玩法配置</td>
                    </tr>
            		<c:forEach items="${controlWeightList}" var="weight" varStatus="cont">
	                    <tr>
							<td><input name="myCheckbox" type="checkbox" id="check${cont.count}" value="${weight.lotteryCode}"></td>
	                    	<td align="center">${cont.count}</td>
	                        <td align="center">${weight.lotteryCode}</td>
	                        <td>${weight.lotteryName}</td>
	                        <td align="center">${weight.postCode}</td>
	                        <td>${myf:getPostCodeName(weight.postCode)}</td>
	                        <td>
		                        <select name="postCode${weight.lotteryCode}">
			                        <c:forEach var="postBean" items="${weight.postLotteryList}">
						                <option value="${postBean.postCode}" <c:if test="${weight.postCode == postBean.postCode}">selected</c:if>>${postBean.postName}</option>
		            				</c:forEach>
	            				</select>
            				</td>
            				<td>
            				<a href="/manages/postManagesServlet?action=load_weight_play&lotteryCode=${weight.lotteryCode}&weightId=${weight.id}">玩法配置</a>
            				</td>
	                    </tr>
            		</c:forEach>
                </table>
            </form>
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