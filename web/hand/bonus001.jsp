<%@page import="java.util.Calendar"%>
<%@ page import="java.sql.*"%>
<%@ page import="com.cndym.utils.Utils"%>
<%@ page import="com.cndym.utils.ConfigUtils"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page contentType="text/html;charset=gbk"%>
<%!

public String poll01(String aa)
{
	return "";
}

%>
<%
/**
6+1
6+0
5+1
5+0;4+1
4+0;3+1
2+1;1+1;0+1
*/

final String driverName = ConfigUtils.getValue("ORACLE.DATASOURCE.DRIVERCLASSNAME");
final String url = ConfigUtils.getValue("ORACLE.DATASOURCE.URL");
final String username = ConfigUtils.getValue("ORACLE.DATASOURCE.USERNAME");
final String password = ConfigUtils.getValue("ORACLE.DATASOURCE.PASSWORD");

Connection conn = null;
Statement statement = null;
ResultSet resultSet = null;
try {
   	String bonusNumber = "03,17,23,25,26,32#13";
   	Calendar calendar = Calendar.getInstance();
	int hour = calendar.get(Calendar.HOUR_OF_DAY);
	int minute = calendar.get(Calendar.MINUTE);

	Class.forName(driverName);
	conn = DriverManager.getConnection(url, username, password);
	StringBuilder result = new StringBuilder();
	String sql = "select sid,ticket_id,number_info,item,multiple,poll_code from tms_ticket where create_time >= to_date('20140506','yyyymmdd') and lottery_code='001' and issue='2014050' and ticket_status=4";
	out.println("sql="+sql);
	statement = conn.createStatement();
	resultSet = statement.executeQuery(sql);

	String ticket_id = null;
	String[] bonusNumberArray = bonusNumber.split("#");
	String[] bonusNumberRedArray = bonusNumberArray[0].split(",");
	int red = 0;
	int blue = 0;
    String betRed = null;
    String betBlue = null;
    int index = 1;
    while (resultSet.next()) {
       	ticket_id = resultSet.getString("ticket_id");
       	String poll_code = ticket_id = resultSet.getString("poll_code");
       	String betNumber = resultSet.getString("number_info");
       	String sid = resultSet.getString("sid");
       	String multiple = resultSet.getString("multiple");
        ///if (poll_code.equals("01")) {
        //	if (betNumber.equals(bonusNumber)) {
        //		out.println(index+"=A"+ticket_id+"="+sid+"="+betNumber+"="+"6+1="+multiple+"<br/>");
        //	}
        //} else if (poll_code.equals("02")) {
        	String[] numberInfoArray = betNumber.split(";");
	        for (String numberInfo : numberInfoArray) {
	       		red = 0;
	       		blue = 0;
	           	String[] numberArray = numberInfo.split("#");
	            betRed = numberArray[0];
	            betBlue = numberArray[1];
				for(String bonusRed : bonusNumberRedArray){
					if (betRed.contains(bonusRed)) {
						red++;
					}
				}
				if (betBlue.contains(bonusNumberArray[1])) {
					blue++;
				}
				if (blue > 0) {
					out.println(index+"=A"+ticket_id+"="+sid+"="+numberInfo+"="+red+"+"+blue+"="+multiple+"<br/>");
				} else if (red > 3){
					out.println(index+"=A"+ticket_id+"="+sid+"="+numberInfo+"="+red+"+"+blue+"="+multiple+"<br/>");
				}
			}
        //} else {
        	
        //}
		index++;
	}
} catch (Exception e) {
       e.printStackTrace();
   } finally {
       if (null != resultSet) {
           try {
               resultSet.close();
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }
       if (null != statement) {
           try {
               statement.close();
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }
       if (null != conn) {
           try {
               conn.close();
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }
   }
%>
