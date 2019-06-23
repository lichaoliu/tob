package com.cndym.dao.impl;

import com.cndym.bean.sys.PurviewGroupToPurview;
import com.cndym.dao.IPurviewGroupToPurviewDao;
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
public class PurviewGroupToPurviewDaoImpl extends GenericDaoImpl<PurviewGroupToPurview> implements IPurviewGroupToPurviewDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Resource
    private SessionFactory sessionFactoryTemp;

    @PostConstruct
    private void sessionFactoryInit() {
        super.setSessionFactory(sessionFactoryTemp);
    }

    public PageBean getPageBeanByPara(PurviewGroupToPurview purviewGroupToPurview, Integer page, Integer pageSize) {
        StringBuffer sql = new StringBuffer("From PurviewGroupToPurview p,PurviewUrl u where p.purviewCode=u.code");
        List<HibernatePara> paras = new ArrayList<HibernatePara>();
        if (Utils.isNotEmpty(purviewGroupToPurview)) {
            if (Utils.isNotEmpty(purviewGroupToPurview.getPurviewGroupCode())) {
                sql.append(" and p.purviewGroupCode=?");
                paras.add(new HibernatePara(purviewGroupToPurview.getPurviewGroupCode()));
            }
            if (Utils.isNotEmpty(purviewGroupToPurview.getPurviewCode())) {
                sql.append(" and p.purviewCode=?");
                paras.add(new HibernatePara(purviewGroupToPurview.getPurviewGroupCode()));
            }
        }
        sql.append(" order by u.codeFather desc ");
        return getPageBeanByPara(sql.toString(), paras, page, pageSize);

    }

    @Override
    public List<PurviewGroupToPurview> getPageBeanByPara(PurviewGroupToPurview purviewGroupToPurview) {
        StringBuffer sql = new StringBuffer("From PurviewGroupToPurview where 1=1");
        List<HibernatePara> paras = new ArrayList<HibernatePara>();
        if (Utils.isNotEmpty(purviewGroupToPurview)) {
            if (Utils.isNotEmpty(purviewGroupToPurview.getPurviewGroupCode())) {
                sql.append(" and purviewGroupCode=?");
                paras.add(new HibernatePara(purviewGroupToPurview.getPurviewGroupCode()));
            }
            if (Utils.isNotEmpty(purviewGroupToPurview.getPurviewCode())) {
                sql.append(" and purviewCode=?");
                paras.add(new HibernatePara(purviewGroupToPurview.getPurviewGroupCode()));
            }
        }
        return findList(sql.toString(), paras);
    }

    @Override
    public void deleteByPurviewGroupCode(String purviewGroupCode) {
        String sql = "delete from sys_purview_group_to_purview  where purview_group_code='" + purviewGroupCode + "'";
        jdbcTemplate.execute(sql);
    }


    @Override
    public void deleteById(Long id) {
        String sql = "delete from sys_purview_group_to_purview  where id=" + id + "";
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
