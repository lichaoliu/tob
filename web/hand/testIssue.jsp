<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="com.cndym.dao.ITicketDao" %>
<%@ page import="com.cndym.utils.SpringUtils" %>
<%@ page import="java.util.List" %>
<%@ page import="com.cndym.bean.tms.Ticket" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.cndym.bean.tms.BonusTicket" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=gbk" %>
<%
    PrintWriter printWriter = response.getWriter();
    try {
        ITicketDao ticketDao = (ITicketDao) SpringUtils.getBean("ticketDaoImpl");
        String sql = "select tt.sid sid,tt.user_code uc,tt.order_id orderId,tt.ticket_id tId,tt.lottery_code lottery,tt.play_code play,tt.poll_code poll," +
                "tt.issue issue,tt.post_code post,tt.amount amount,tt.bonus_amount bonusAmount,tt.fix_bonus_amount fixBonusA,tt.out_ticket_id outTicket," +
                "tt.send_time sendTime,tt.return_time returnTime,tt.bonus_time bonusTime,tt.big_bonus big,tt.backup1 backup1" +
                " from tms_ticket tt where tt.lottery_code='010' and issue='140417001' and tt.bonus_status=1 and ticket_id not in " +
                "(select t.ticket_id from tms_bonus_ticket t where t.lottery_code='010' and issue='140417001' )";
        List<Map> list = ticketDao.findSqlList(sql);
        printWriter.println("kaishi~~~~~~~~~~~~~~~~~~~~");
        BonusTicket bt = null;
        List<BonusTicket> bonusTickets = new ArrayList<BonusTicket>();
        for (Map map : list) {
            bt = new BonusTicket();
            String sid = (String) map.get("SID");
            String userCode = (String) map.get("UC");
            String orderId = (String) map.get("ORDERID");
            String tId = (String) map.get("TID");
            String lottery = (String) map.get("LOTTERY");
            String play = (String) map.get("PLAY");
            String poll = (String) map.get("POLL");
            String issue = (String) map.get("ISSUE");
            Double amount = new Double(map.get("AMOUNT").toString());
            printWriter.println("amount~~~~~~~~~~~~~~~~~~~~" + amount);
            Double bonusAmount = new Double(map.get("BONUSAMOUNT").toString());
            printWriter.println("bonusAmount~~~~~~~~~~~~~~~~~~~~" + bonusAmount);
            Double fixBonusA = new Double(map.get("FIXBONUSA").toString());
            printWriter.println("fixBonusA~~~~~~~~~~~~~~~~~~~~" + fixBonusA);
            String outTicket = (String) map.get("OUTTICKET");
            printWriter.println("outTicket~~~~~~~~~~~~~~~~~~~~" + outTicket);
            Date sendTime = (Date) map.get("SENDTIME");
            printWriter.println("sendTime~~~~~~~~~~~~~~~~~~~~" + sendTime);
            Date returnTime = (Date) map.get("RETURNTIME");
            printWriter.println("returnTime~~~~~~~~~~~~~~~~~~~~" + returnTime);
            Date bonusTime = (Date) map.get("BONUSTIME");
            printWriter.println("bonusTime~~~~~~~~~~~~~~~~~~~~" + bonusTime);
            Integer big = new Integer(map.get("BIG").toString());
            printWriter.println("big~~~~~~~~~~~~~~~~~~~~" + big);
            String backup1 = (String) map.get("BACKUP1");
            printWriter.println("backup1~~~~~~~~~~~~~~~~~~~~" + backup1);
            bt.setSid(sid);
            bt.setUserCode(userCode);
            bt.setOrderId(orderId);
            bt.setTicketId(tId);
            bt.setLotteryCode(lottery);
            bt.setPlayCode(play);
            bt.setPollCode(poll);
            bt.setIssue(issue);
            bt.setAmount(amount);
            bt.setBonusAmount(bonusAmount);
            bt.setFixBonusAmount(fixBonusA);
            bt.setOutTicketId(outTicket);
            bt.setSendTime(sendTime);
            bt.setReturnTime(returnTime);
            bt.setBonusTime(bonusTime);
            bt.setCreateTime(new Date());
            bt.setBigBonus(big);
            bt.setBackup1(backup1);

            bonusTickets.add(bt);
        }
        boolean b = ticketDao.saveAllObject(bonusTickets);
        printWriter.println(b + "ok~~~~~~~~~~~~~~~~~~~~" + bonusTickets.size());
    } catch (Exception e) {
        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }
%>
