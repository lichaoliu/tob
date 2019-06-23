package com.cndym.dao.impl.rowMapperBean;

import com.cndym.bean.user.Account;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: 邓玉明 Date: 11-4-5 下午9:22
 */
public class AccountRowMapper implements RowMapper<Account> {
    @Override
    public Account mapRow(ResultSet resultSet, int i) throws SQLException {

        Account account = new Account();
        account.setId(resultSet.getLong("id"));
        account.setUserCode(resultSet.getString("user_code"));
        account.setBonusAmount(resultSet.getDouble("bonus_amount"));
        account.setRechargeAmount(resultSet.getDouble("recharge_amount"));
        account.setPresentAmount(resultSet.getDouble("present_amount"));
        account.setFreezeAmount(resultSet.getDouble("freeze_amount"));
        account.setBonusTotal(resultSet.getDouble("bonus_total"));
        account.setRechargeTotal(resultSet.getDouble("recharge_total"));
        account.setPresentTotal(resultSet.getDouble("present_total"));
        account.setPayTotal(resultSet.getDouble("pay_total"));
        account.setDrawTotal(resultSet.getDouble("draw_total"));
        account.setScore(resultSet.getInt("score"));
        return account;
    }
}
