<%@page import="java.util.Calendar"%>
<%@ page import="java.sql.*"%>
<%@ page import="com.cndym.utils.Utils"%>
<%@ page import="com.cndym.utils.ConfigUtils"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page contentType="text/html;charset=gbk"%>
<%!
public static double getRound(int index, double number) {
    if (number == 0)
        return 0;
    if (index < 0)
        throw new IllegalArgumentException("error param");

    String temp = String.valueOf(number);
    BigDecimal b = new BigDecimal(temp);
    BigDecimal one = new BigDecimal(1);

    return b.divide(one, index, BigDecimal.ROUND_HALF_EVEN).doubleValue();
}
%>
<%

final String driverName = ConfigUtils.getValue("ORACLE.DATASOURCE.DRIVERCLASSNAME");
final String url = ConfigUtils.getValue("ORACLE.DATASOURCE.URL");
final String username = ConfigUtils.getValue("ORACLE.DATASOURCE.USERNAME");
final String password = ConfigUtils.getValue("ORACLE.DATASOURCE.PASSWORD");

Connection conn = null;
Statement statement = null;
ResultSet resultSet = null;
try {
   	Calendar calendar = Calendar.getInstance();

	Class.forName(driverName);
	conn = DriverManager.getConnection(url, username, password);
	StringBuilder result = new StringBuilder();
	String sql = "select ticket_id,number_info,sale_info,item,multiple from tms_ticket where create_time>= to_date('20140601','yyyymmdd') and lottery_code='202' and ticket_status=3 and issue='201407141001' and number_info ='201407141001:03'";
	out.println("<br/>sql="+sql);
	statement = conn.createStatement();
	resultSet = statement.executeQuery(sql);
	int index = 1;
    while (resultSet.next()) {
       	String ticket_id = resultSet.getString("ticket_id");
       	String sale_info = resultSet.getString("sale_info");
       	int multiple = Utils.formatInt(resultSet.getString("multiple"), 0);
        Double odds = getRound(2,Utils.formatDouble(sale_info.substring(4), 0D));
        out.println("<br/>"+index+"¡¢"+ticket_id+","+multiple+","+odds+"="+getRound(2,odds*multiple*2));
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
