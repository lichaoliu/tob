package com.cndym.dao.impl.rowMapperBean;

import com.cndym.bean.sys.Manages;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * User: 邓玉明
 * Date: 11-4-14 下午11:21
 */
public class SysManagesrRowMapper implements RowMapper<Manages> {
    @Override
    public Manages mapRow(ResultSet resultSet, int i) throws SQLException {
    	Manages manages = new Manages();
        manages.setBody(resultSet.getString("body"));
        manages.setCreateTime(resultSet.getTimestamp("create_time"));
        manages.setDepartments(resultSet.getString("departments"));
        manages.setEmail(resultSet.getString("email"));
        manages.setId(resultSet.getLong("id"));
        manages.setLoginIp(resultSet.getString("login_ip"));
        manages.setLoginTime(resultSet.getTimestamp("login_time"));
        manages.setMobile(resultSet.getString("mobile"));
        manages.setPassWord(resultSet.getString("pass_word"));
        manages.setPermissions(resultSet.getString("permissions"));
        manages.setPhone(resultSet.getString("phone"));
        manages.setPost(resultSet.getString("post"));
        manages.setStatus(resultSet.getInt("status"));
        manages.setUserName(resultSet.getString("user_name"));
        manages.setRealName(resultSet.getString("real_name"));
        manages.setType(resultSet.getInt("type"));
        return manages;
    }
}
