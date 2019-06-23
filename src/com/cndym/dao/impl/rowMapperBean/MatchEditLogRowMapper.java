package com.cndym.dao.impl.rowMapperBean;

import com.cndym.bean.tms.MatchEditLog;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: mengjingyi
 * Date: 12-2-14
 * Time: 下午6:02
 */
public class MatchEditLogRowMapper implements RowMapper<MatchEditLog> {

    @Override
    public MatchEditLog mapRow(ResultSet resultSet, int i) throws SQLException {
        MatchEditLog macthEditLog = new MatchEditLog();
        macthEditLog.setId(resultSet.getLong("id"));
        macthEditLog.setLotteryCode(resultSet.getString("lottery_code"));
        macthEditLog.setPlayCode(resultSet.getString("play_code"));
        macthEditLog.setPollCode(resultSet.getString("poll_code"));
        macthEditLog.setIssue(resultSet.getString("issue"));
        macthEditLog.setSn(resultSet.getString("sn"));
        macthEditLog.setCreateTime(resultSet.getTimestamp("create_time"));
        macthEditLog.setStatus(resultSet.getInt("status"));
        return macthEditLog;
    }
}
