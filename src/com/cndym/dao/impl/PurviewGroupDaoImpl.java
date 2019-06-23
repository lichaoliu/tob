package com.cndym.dao.impl;

import com.cndym.bean.sys.PurviewGroup;
import com.cndym.bean.sys.PurviewUrl;
import com.cndym.dao.IManagesToPurviewGroupDao;
import com.cndym.dao.IPurviewGroupDao;
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
public class PurviewGroupDaoImpl extends GenericDaoImpl<PurviewGroup> implements IPurviewGroupDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Resource
    private SessionFactory sessionFactoryTemp;
    @Resource
    private IManagesToPurviewGroupDao managesToPurviewGroupDao;
    @Resource
    private IPurviewGroupToPurviewDao purviewGroupToPurviewDao;

    @PostConstruct
    private void sessionFactoryInit() {
        super.setSessionFactory(sessionFactoryTemp);
    }

    public PageBean getPageBeanByPara(PurviewGroup purviewGroup, Integer page, Integer pageSize) {
        StringBuffer sql = new StringBuffer("From PurviewGroup where 1=1");
        List<HibernatePara> list = new ArrayList<HibernatePara>();
        if (Utils.isNotEmpty(purviewGroup)) {
            if (Utils.isNotEmpty(purviewGroup.getName())) {
                sql.append(" and name=?");
                list.add(new HibernatePara(purviewGroup.getName()));
            }
            if (Utils.isNotEmpty(purviewGroup.getPurviewGroupCode())) {
                sql.append(" and purviewGroupCode=?");
            }
        }
        sql.append(" order by createTime desc");
        return getPageBeanByPara(sql.toString(), list, page, pageSize);
    }

    @Override
    public void deletePurviewGroupById(Long id) {
        PurviewGroup purviewGroup = findPurviewGroup(id);
        if (Utils.isNotEmpty(purviewGroup)) {
            managesToPurviewGroupDao.deleteByPurviewGroupCode(purviewGroup.getPurviewGroupCode());
            purviewGroupToPurviewDao.deleteByPurviewGroupCode(purviewGroup.getPurviewGroupCode());
            delete(purviewGroup);
        }
    }

    @Override
    public void deletePurviewGroupByPurviewGroupCode(String purviewGroupCode) {
        PurviewGroup purviewGroup = findPurviewGroupByCode(purviewGroupCode);
        if (Utils.isNotEmpty(purviewGroup)) {
            managesToPurviewGroupDao.deleteByPurviewGroupCode(purviewGroup.getPurviewGroupCode());
            purviewGroupToPurviewDao.deleteByPurviewGroupCode(purviewGroup.getPurviewGroupCode());
            delete(purviewGroup);
        }
    }

    @Override
    public PurviewGroup findPurviewGroup(Long id) {
        List<PurviewGroup> purviewGroups = find("From PurviewGroup where id=?", new Object[]{id});
        if (!purviewGroups.isEmpty()) {
            return purviewGroups.get(0);
        }
        return null;
    }

    @Override
    public String findPurviewGroupBuyCode(String purviewGroupCode) {
        List<PurviewGroup> purviewGroups = find("From PurviewGroup where purviewGroupCode=?", new Object[]{purviewGroupCode});
        if (!purviewGroups.isEmpty()) {
            PurviewGroup purviewGroup = purviewGroups.get(0);
            if (Utils.isNotEmpty(purviewGroup)) {
                return purviewGroup.getName();
            }
        }
        return null;
    }

    @Override
    public PurviewGroup findPurviewGroupByCode(String code) {
        List<PurviewGroup> purviewGroups = find("From PurviewGroup where purviewGroupCode=?", new Object[]{code});
        if (!purviewGroups.isEmpty()) {
            return purviewGroups.get(0);
        }
        return null;
    }

    @Override
    public List<PurviewUrl> findPurviewUrlNo(Long id) {
        return null;
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
