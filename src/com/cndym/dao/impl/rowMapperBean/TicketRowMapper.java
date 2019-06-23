package com.cndym.dao.impl.rowMapperBean;

import com.cndym.bean.tms.Ticket;
import com.cndym.bean.user.Account;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: 邓玉明 Date: 11-4-5 下午9:22
 */
public class TicketRowMapper implements RowMapper<Ticket> {
    @Override
    public Ticket mapRow(ResultSet resultSet, int i) throws SQLException {
        Ticket ticket = new Ticket();
        ticket.setId(resultSet.getLong("id"));
        ticket.setOutTicketId(resultSet.getString("out_ticket_id"));
        ticket.setSid(resultSet.getString("sid"));
        ticket.setUserCode(resultSet.getString("user_code"));
        ticket.setOrderId(resultSet.getString("order_id"));
        ticket.setTicketId(resultSet.getString("ticket_id"));
        ticket.setLotteryCode(resultSet.getString("lottery_code"));
        ticket.setPlayCode(resultSet.getString("play_code"));
        ticket.setPollCode(resultSet.getString("poll_code"));
        ticket.setIssue(resultSet.getString("issue"));
        ticket.setItem(resultSet.getInt("item"));
        ticket.setMultiple(resultSet.getInt("multiple"));
        ticket.setNumberInfo(resultSet.getString("number_info"));
        ticket.setUserInfo(resultSet.getString("user_info"));
        ticket.setSaleCode(resultSet.getString("sale_code"));
        ticket.setErrCode(resultSet.getString("err_code"));
        ticket.setErrMsg(resultSet.getString("err_msg"));
        ticket.setPostCode(resultSet.getString("post_code"));
        ticket.setAmount(resultSet.getDouble("amount"));
        ticket.setTicketStatus(resultSet.getInt("ticket_status"));
        ticket.setBonusStatus(resultSet.getInt("bonus_status"));
        ticket.setBonusAmount(resultSet.getDouble("bonus_amount"));
        ticket.setCreateTime(resultSet.getTimestamp("create_time"));
        ticket.setAcceptTime(resultSet.getTimestamp("accept_time"));
        ticket.setBonusClass(resultSet.getString("bonus_class"));
        ticket.setFixBonusAmount(resultSet.getDouble("fix_bonus_amount"));
        ticket.setStartGameId(resultSet.getString("start_game_id"));
        ticket.setEndGameId(resultSet.getString("end_game_id"));
        ticket.setGameEndTime(resultSet.getTimestamp("game_end_time"));
        ticket.setGameStartTime(resultSet.getTimestamp("game_start_time"));
        ticket.setSendTime(resultSet.getTimestamp("send_time"));
        ticket.setReturnTime(resultSet.getTimestamp("return_time"));
        ticket.setBonusTime(resultSet.getTimestamp("bonus_time"));
        ticket.setSaleInfo(resultSet.getString("sale_info"));
        ticket.setBigBonus(resultSet.getInt("big_bonus"));
        ticket.setSidOutTicketId(resultSet.getString("sid_out_ticket_id"));
        ticket.setDuiJiangStatus(resultSet.getInt("dui_jiang_status"));
        ticket.setDuiJiangTime(resultSet.getTimestamp("dui_jiang_time"));
        ticket.setBackup1(resultSet.getString("backup1"));
        ticket.setBackup2(resultSet.getString("backup2"));
        ticket.setBackup3(resultSet.getString("backup3"));
        ticket.setSumTicket(resultSet.getInt("sum_ticket"));
        ticket.setSuccessTicket(resultSet.getInt("success_ticket"));
        ticket.setFailureTicket(resultSet.getInt("failure_ticket"));
        ticket.setSuccessAmount(resultSet.getDouble("success_amount"));
        ticket.setFailureAmount(resultSet.getDouble("failure_amount"));
        return ticket;
    }
}
