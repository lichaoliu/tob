package com.cndym.dao.impl.rowMapperBean;

import com.cndym.bean.tms.MainIssue;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * User: 邓玉明
 * Date: 11-4-14 下午11:21
 */
public class MainIssueRowMapper implements RowMapper<MainIssue> {
    @Override
    public MainIssue mapRow(ResultSet resultSet, int i) throws SQLException {
        MainIssue issue = new MainIssue();
        issue.setId(resultSet.getLong("id"));
        issue.setLotteryCode(resultSet.getString("lottery_code"));
        issue.setName(resultSet.getString("name"));
        issue.setStartTime(resultSet.getTimestamp("start_time"));
        issue.setSimplexTime(resultSet.getTimestamp("simplex_Time"));
        issue.setDuplexTime(resultSet.getTimestamp("duplex_time"));
        issue.setEndTime(resultSet.getTimestamp("end_time"));
        issue.setStatus(resultSet.getInt("status"));
        issue.setSendStatus(resultSet.getInt("send_status"));
        issue.setBonusTime(resultSet.getTimestamp("bonus_time"));
        issue.setBonusStatus(resultSet.getInt("bonus_status"));
        issue.setBonusNumber(resultSet.getString("bonus_number"));
        issue.setPrizePool(resultSet.getString("prize_pool"));
        issue.setBonusClass(resultSet.getString("bonus_class"));
        issue.setBonusTotal(resultSet.getDouble("bonus_total"));
        issue.setSaleTotal(resultSet.getString("sale_total"));
        issue.setGlobalSaleTotal(resultSet.getString("global_sale_total"));
        issue.setBackup1(resultSet.getString("backup1"));
        issue.setBackup2(resultSet.getString("backup2"));
        issue.setBackup3(resultSet.getString("backup3"));
        issue.setOperatorsAward(resultSet.getInt("operators_award"));
        issue.setCenterBonusStatus(resultSet.getInt("center_bonus_status"));
        return issue;
    }
}
