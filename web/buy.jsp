<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>投注测试</title>
</head>
<body>
<form action="test.jsp" method="post">
    <table>

        <tr>
            <td>
                彩&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;种:<input name="lotteryId" value="001"/><br>
                彩&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;期:<input name="issue" value="20121109007"/><br>
                玩&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;法:<input name="playId" value="01"/><br>
                投注内容:
                <textarea rows="20" cols="100" name="ticket"></textarea>
                <input name="submit" type="submit" value="提交"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>