package com.cndym.dao.impl;

import com.cndym.bean.sys.ManagesToPurviewGroup;
import com.cndym.bean.sys.PurviewGroup;
import com.cndym.dao.IManagesToPurviewGroupDao;
import com.cndym.dao.impl.rowMapperBean.PurviewGroupRowMapper;
import com.cndym.utils.Utils;
import com.cndym.utils.hibernate.HibernatePara;
import com.cndym.utils.hibernate.PageBean;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * User: 邓玉明
 * Date: 11-3-27 下午10:25
 */
@Repository
public class ManagesToPurviewGroupDaoImpl extends GenericDaoImpl<ManagesToPurviewGroup> implements IManagesToPurviewGroupDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Resource
    private SessionFactory sessionFactoryTemp;

    @PostConstruct
    private void sessionFactoryInit() {
        super.setSessionFactory(sessionFactoryTemp);
    }

    public PageBean getPageBeanByPara(ManagesToPurviewGroup managesToPurviewGroup, Integer page, Integer pageSize) {
        StringBuffer sql = new StringBuffer("From ManagesToPurviewGroup m,PurviewGroup p where m.purviewGroupCode=p.purviewGroupCode");
        List<HibernatePara> list = new ArrayList<HibernatePara>();
        if (Utils.isNotEmpty(managesToPurviewGroup)) {
            if (Utils.isNotEmpty(managesToPurviewGroup.getManagerId())) {
                sql.append(" and m.managerId=?");
                list.add(new HibernatePara(managesToPurviewGroup.getManagerId()));
            }
        }
        return getPageBeanByPara(sql.toString(), list, page, pageSize);

    }

    @Override
    public List getPageBeanByPara(ManagesToPurviewGroup managesToPurviewGroup) {
        StringBuffer sql = new StringBuffer("From ManagesToPurviewGroup m,PurviewGroup p where m.purviewGroupCode=p.purviewGroupCode");
        List<HibernatePara> list = new ArrayList<HibernatePara>();
        if (Utils.isNotEmpty(managesToPurviewGroup)) {
            if (Utils.isNotEmpty(managesToPurviewGroup.getManagerId())) {
                sql.append(" and m.managerId=?");
                list.add(new HibernatePara(managesToPurviewGroup.getManagerId()));
            }
        }
        return findList(sql.toString(), list);
    }

    @Override
    public List getPageBeanByPara(String code) {
        StringBuffer sql = new StringBuffer("From ManagesToPurviewGroup mtp,Manages m where m.id=mtp.managerId");
        List<HibernatePara> list = new ArrayList<HibernatePara>();
        if (Utils.isNotEmpty(code)) {
            sql.append(" and mtp.purviewGroupCode=?");
            list.add(new HibernatePara(code));
        }
        return findList(sql.toString(), list);
    }

    @Override
    public List<PurviewGroup> getPurviewGroupNo(Long memberId) {
        String sql = "select * from sys_purview_group where purview_group_code not in (select purview_group_code from sys_manages_to_purview_group where manager_id=" + memberId + ")";
        return jdbcTemplate.query(sql, new PurviewGroupRowMapper());
    }

    @Override
    public List<PurviewGroup> getPurviewGroupList() {
        String sql = "select * from sys_purview_group ";
        return jdbcTemplate.query(sql, new PurviewGroupRowMapper());
    }

    @Override
    public void deleteByPurviewGroupCode(String purviewGroupCode) {
        String sql = "delete from sys_manages_to_purview_group where purview_group_code='" + purviewGroupCode + "'";
        jdbcTemplate.execute(sql);
    }

    @Override
    public void deleteForId(Long id) {
        String sql = "delete from sys_manages_to_purview_group where id=" + id + "";
        jdbcTemplate.execute(sql);
    }

    @Override
    public void deleteForManagesId(Long managesId) {
        String sql = "delete from sys_manages_to_purview_group where manager_id=" + managesId + "";
        jdbcTemplate.execute(sql);
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public SessionFactory getSessionFactoryTemp() {
        return sessionFactoryTemp;
    }

    public void setSessionFactoryTemp(SessionFactory sessionFactoryTemp) {
        this.sessionFactoryTemp = sessionFactoryTemp;
    }


}
