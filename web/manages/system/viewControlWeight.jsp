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
        function flushCache() {
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

            if (confirm("确定要修改出票口缓冲？")) {
                document.form1.action = "/manages/controlWeight?action=reload_cache_weight";
                form1.submit();
            }
        }

        function loadFile() {
            if (confirm("确定要加载出票口配置文件？")) {
                document.form1.action = "/manages/controlWeight?action=reload_file_weight";
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
				<input type="button" value="修改缓冲" onclick="return flushCache()" class="submit" style="width: 64px; border: none">
				<input type="button" value="加载文件" onclick="return loadFile();" class="submit" style="width: 64px; border: none">
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
            <form name="form1" method="post" action="/manages/controlWeight?action=reload_cache_weight">
                <table width="100%" border="1" cellpadding="4" cellspacing="0" class="content">
                    <tr id=one>
						<td width="3%"></td>
                    	<td width="3%">序号</td>
                        <td width="10%">彩种编码</td>
                        <td width="10%">彩种名称</td>
                        <td width="10%">出票口编码</td>
                        <td width="20%">出票口名称</td>
                        <td>切换出票口</td>
                    </tr>
            		<c:forEach items="${weightList}" var="weight" varStatus="cont">
	                    <tr>
							<td><input name="myCheckbox" type="checkbox" id="check${cont.count}" value="${weight.lotteryCode}"></td>
	                    	<td align="center">${cont.count}</td>
	                        <td align="center">${weight.lotteryCode}</td>
	                        <td>${weight.name}</td>
	                        <td align="center">${weight.defaultPostCode}</td>
	                        <td>${myf:getPostCodeName(weight.defaultPostCode)}</td>
	                        <td>
		                        <select name="postCode${weight.lotteryCode}">
		                        	<c:forEach items="${alertAmountList}" var="alertAmount">
		                        		<c:if test="${fn:contains(alertAmount.lotteryCode, weight.lotteryCode)}">
					                		<option value="${alertAmount.postCode}" <c:if test="${weight.defaultPostCode == alertAmount.postCode}">selected</c:if>>${alertAmount.postName}</option>
					                	</c:if>
					                </c:forEach>
	            				</select>
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