package com.cndym.dao.impl.rowMapperBean;


import com.cndym.bean.sys.PurviewGroup;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 作者：邓玉明
 * 时间：11-7-1 下午5:24
 * QQ：757579248
 * email：cndym@163.com
 */
public class PurviewGroupRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        PurviewGroup purviewGroup = new PurviewGroup();
        purviewGroup.setCreateTime(resultSet.getTimestamp("create_time"));
        purviewGroup.setName(resultSet.getString("name"));
        purviewGroup.setPurviewGroupCode(resultSet.getString("purview_group_code"));
        purviewGroup.setId(resultSet.getLong("id"));
        return purviewGroup;
    }
}
