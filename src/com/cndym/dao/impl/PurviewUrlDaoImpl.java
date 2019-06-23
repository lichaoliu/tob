package com.cndym.dao.impl;

import com.cndym.bean.sys.PurviewUrl;
import com.cndym.dao.IPurviewUrlDao;
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
import java.util.Map;

/**
 * User: MengJingyi
 * Date: 11-6-20 下午11:19
 */

@Repository
public class PurviewUrlDaoImpl extends GenericDaoImpl<PurviewUrl> implements IPurviewUrlDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Resource
    private SessionFactory sessionFactoryTemp;

    @PostConstruct
    private void sessionFactoryInit() {
        super.setSessionFactory(sessionFactoryTemp);
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


    @Override
    public PurviewUrl getPurviewUrl(PurviewUrl purviewUrl) {
        return null;
    }

    @Override
    public PageBean getPurviewUrlList(PurviewUrl purviewUrl) {
        try {
            StringBuffer sql = new StringBuffer("From PurviewUrl where 1=1 ");
            List<HibernatePara> hibernateParas = new ArrayList<HibernatePara>();
            if (Utils.isNotEmpty(purviewUrl)) {
                if (Utils.isNotEmpty(purviewUrl.getActionName())) {
                    sql.append(" and actionName=?");
                    hibernateParas.add(new HibernatePara(purviewUrl.getActionName()));
                }
                if (Utils.isNotEmpty(purviewUrl.getCode())) {
                    sql.append(" and code=?");
                    hibernateParas.add(new HibernatePara(purviewUrl.getCode()));
                }
                if (Utils.isNotEmpty(purviewUrl.getName())) {
                    sql.append(" and name=?");
                    hibernateParas.add(new HibernatePara(purviewUrl.getName()));
                }
                if (Utils.isNotEmpty(purviewUrl.getServletName())) {
                    sql.append(" and servletName=?");
                    hibernateParas.add(new HibernatePara(purviewUrl.getServletName()));
                }
                if (Utils.isNotEmpty(purviewUrl.getUrl())) {
                    sql.append(" and url=?");
                    hibernateParas.add(new HibernatePara(purviewUrl.getUrl()));
                }
            }
            return getPageBeanByPara(sql.toString(), hibernateParas, 1, 100);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public PageBean getPurviewUrlList(String codeIndex1) {
        try {
            StringBuffer sql = new StringBuffer("From PurviewUrl where 1=1 ");
            List<HibernatePara> hibernateParas = new ArrayList<HibernatePara>();
            if (Utils.isNotEmpty(codeIndex1)) {
                sql.append(" and codeFather=?");
                hibernateParas.add(new HibernatePara(codeIndex1));
            }
            return getPageBeanByPara(sql.toString(), hibernateParas, 1, 100);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    @Override
    public List getPurviewUrlFatherList() {
        String sql = "select t.sort,t.code_father,t.name_father from (select p.code_father,p.sort,p.name_father from sys_purview_url p group by p.code_father,p.sort,p.name_father) t order by t.sort";
        return jdbcTemplate.queryForList(sql);
    }

    @Override
    public List<PurviewUrl> getPurviewUrlList() {
        return find("From PurviewUrl order by codeFather desc", null);
    }


    public List getPurviewUrlListByGroup(String groupCode) {
        String sql = "select q.name_father,q.code_father as fcode,q.code,q.name,T.purview_group_code,q.sort as sort1 from sys_purview_url q left join (select p.purview_code,p.purview_group_code from sys_purview_group_to_purview p where p.purview_group_code=?) T on q.code=T.purview_code order by q.sort ";
        return jdbcTemplate.queryForList(sql, new String[]{groupCode});
    }


    public List getPurviewUrlListByGroupNew(String groupCode) {
        String sql = "select q.code_father,q.name_father,q.code_father as fcode,q.code,q.name,T.purview_group_code,q.sort as sort1 from sys_purview_url q left join (select p.purview_code,p.purview_group_code from sys_purview_group_to_purview p where p.purview_group_code=?) T on q.code=T.purview_code order by q.sort ";
        return jdbcTemplate.queryForList(sql, new String[]{groupCode});
    }


    public Map<String, String> getPurviewUrlBycode(String code) {
        String sql = "select * from sys_purview_url t where t.code=?";
        List list = jdbcTemplate.queryForList(sql, new String[]{code});
        if (Utils.isNotEmpty(list)) {
            return (Map<String, String>) list.get(0);
        }
        return null;
    }
}
