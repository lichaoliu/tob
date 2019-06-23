package com.cndym.dao.impl.rowMapperBean;

import com.cndym.bean.tms.SubTicket;
import com.cndym.bean.tms.Ticket;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: 邓玉明 Date: 11-4-5 下午9:22
 */
public class SubTicketRowMapper implements RowMapper<SubTicket> {
    @Override
    public SubTicket mapRow(ResultSet resultSet, int i) throws SQLException {
        SubTicket ticket = new SubTicket();
        ticket.setId(resultSet.getLong("id"));
        ticket.setTicketId(resultSet.getString("ticket_id"));
        ticket.setSubTicketId(resultSet.getString("sub_ticket_id"));
        ticket.setLotteryCode(resultSet.getString("lottery_code"));
        ticket.setPlayCode(resultSet.getString("play_code"));
        ticket.setPollCode(resultSet.getString("poll_code"));
        ticket.setIssue(resultSet.getString("issue"));
        ticket.setItem(resultSet.getInt("item"));
        ticket.setMultiple(resultSet.getInt("multiple"));
        ticket.setNumberInfo(resultSet.getString("number_info"));
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
        ticket.setFixBonusAmount(resultSet.getDouble("fix_bonus_amount"));
        ticket.setSendTime(resultSet.getTimestamp("send_time"));
        ticket.setReturnTime(resultSet.getTimestamp("return_time"));
        ticket.setBonusTime(resultSet.getTimestamp("bonus_time"));
        ticket.setSaleInfo(resultSet.getString("sale_info"));
        ticket.setBigBonus(resultSet.getInt("big_bonus"));
        ticket.setBackup1(resultSet.getString("backup1"));
        ticket.setBackup2(resultSet.getString("backup2"));
        ticket.setBackup3(resultSet.getString("backup3"));
        return ticket;
    }
}
