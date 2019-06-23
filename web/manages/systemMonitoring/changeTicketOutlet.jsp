<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>出票口切换</title>
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
        function doConfirm(str1,str2,url){
            var info = "当前为"+str1+"出票口,将更换到"+str2+"出票口,您要执行此切换吗?";
            if(confirm(info)){
                window.location.href = url;
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
        <td class="title">
            出票口切换
        </td>
    </tr>
    <tr>
        <td style="padding: 4px;">
            <form name="form1" method="post"
                  action="${pageContext.request.contextPath}/manages/systemManagesServlet?action=addAdmin">
                <table width="100%" border="1" cellpadding="4" cellspacing="0" class="seach">
                    <tr>
                        <td align="center">
                            竞彩篮球和竞彩足球的当前出票口是 <font color="red">${myf:getPostCodeName(weight.postList[0].code)}</font>,点
                            <c:if test="${weight.postList[0].code == '02'}"><a onclick="javascript:doConfirm('${myf:getPostCodeName(weight.postList[0].code)}','${myf:getPostCodeName("03")}','/manages/systemManagesServlet?action=changeTicketOutlet&event=JC2To3')" href="#">切换到${myf:getPostCodeName("03")}</a></c:if>
                            <c:if test="${weight.postList[0].code == '03'}"><a onclick="javascript:doConfirm('${myf:getPostCodeName(weight.postList[0].code)}','${myf:getPostCodeName("02")}','/manages/systemManagesServlet?action=changeTicketOutlet&event=JC3To2')" href="#">切换到${myf:getPostCodeName("02")}</a></c:if>
                            可更换出票口。
                        </td>
                    </tr>
                </table>
            </form>
        </td>
    </tr>
</table>
</body>
</html>