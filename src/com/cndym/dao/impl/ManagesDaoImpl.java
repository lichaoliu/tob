package com.cndym.dao.impl;

import com.cndym.bean.sys.Manages;
import com.cndym.dao.IManagesDao;
import com.cndym.dao.impl.rowMapperBean.SysManagesrRowMapper;
import com.cndym.utils.JdbcPageUtil;
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
 * User: 邓玉明
 * Date: 11-3-27 下午10:25
 */
@Repository
public class ManagesDaoImpl extends GenericDaoImpl<Manages> implements IManagesDao {
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
    public Manages getManagesById(Long id) {
        String sql = "From Manages where id=?";
        List<Manages> manageses = find(sql, new Object[]{id});
        if (manageses.isEmpty()) {
            return null;
        }
        return manageses.get(0);
    }

    @Override
    public boolean getManagsForKey(Manages manages) {
        StringBuffer sql = new StringBuffer("From Manages m where 1=1 ");
        List<HibernatePara> hibernateParas = new ArrayList<HibernatePara>();
        if (Utils.isNotEmpty(manages)) {
            if (Utils.isNotEmpty(manages.getMobile())) {
                sql.append(" and m.mobile=?");
                hibernateParas.add(new HibernatePara(manages.getMobile()));
            }
            if (Utils.isNotEmpty(manages.getEmail())) {
                sql.append(" and m.email=?");
                hibernateParas.add(new HibernatePara(manages.getEmail()));
            }
            if (Utils.isNotEmpty(manages.getUserName())) {
                sql.append(" and m.userName=?");
                hibernateParas.add(new HibernatePara(manages.getUserName()));
            }
        }
        sql.append(" order by m.createTime desc");
        List managesList = findList(sql.toString(), hibernateParas);
        if (Utils.isNotEmpty(managesList)) {
            return true;
        }
        return false;
    }


    public Manages getManagesByUserName(String userName) {
        String sql = "From Manages where userName = ?";
        List<Manages> dataList = find(sql, new Object[]{userName});
        if (Utils.isNotEmpty(dataList)) {
            return dataList.get(0);
        }
        return null;
    }

    @Override
    public String getUserNameById(String managesId) {
        try {
            String sql = "select user_name from sys_manages where id=? ";
            Map managesMap = jdbcTemplate.queryForMap(sql, new Object[]{managesId});
            return managesMap.get("user_name") + "";
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public PageBean getAdminList(Manages manages, Integer page, Integer pageSize) {
        StringBuffer sql = new StringBuffer("From Manages m where 1=1 ");
        List<HibernatePara> hibernateParas = new ArrayList<HibernatePara>();
        if (Utils.isNotEmpty(manages)) {
            if (Utils.isNotEmpty(manages.getId())) {
                sql.append(" and m.id=?");
                hibernateParas.add(new HibernatePara(manages.getId()));
            }
            if (Utils.isNotEmpty(manages.getMobile())) {
                sql.append(" and m.mobile=?");
                hibernateParas.add(new HibernatePara(manages.getMobile()));
            }
            if (Utils.isNotEmpty(manages.getEmail())) {
                sql.append(" and m.email=?");
                hibernateParas.add(new HibernatePara(manages.getEmail()));
            }
            if (Utils.isNotEmpty(manages.getPost())) {
                sql.append(" and m.post=?");
                hibernateParas.add(new HibernatePara(manages.getPost()));
            }
            if (Utils.isNotEmpty(manages.getUserName())) {
                sql.append(" and m.userName like ?");
                hibernateParas.add(new HibernatePara("%" + manages.getUserName() + "%"));
            }
            if (Utils.isNotEmpty(manages.getRealName())) {
                sql.append(" and m.realName like ?");
                hibernateParas.add(new HibernatePara("%" + manages.getRealName() + "%"));
            }
            if (Utils.isNotEmpty(manages.getStatus())) {
                sql.append(" and m.status=?");
                hibernateParas.add(new HibernatePara(manages.getStatus()));
            }
        }
        sql.append(" order by m.createTime desc");
        return getPageBeanByPara(sql.toString(), hibernateParas, page, pageSize);
    }


    @Override
    public int updateAdmin(Manages manages) {
        StringBuffer sql = new StringBuffer("update sys_manages set id=" + manages.getId());
        if (Utils.isNotEmpty(manages)) {
            if (Utils.isNotEmpty(manages.getUserName())) {
                sql.append(" ,user_name='" + manages.getUserName() + "'");
            }
            if (Utils.isNotEmpty(manages.getMobile())) {
                sql.append(" ,mobile='" + manages.getMobile() + "'");
            }
            if (Utils.isNotEmpty(manages.getEmail())) {
                sql.append(" ,email='" + manages.getEmail() + "'");
            }
            if (Utils.isNotEmpty(manages.getStatus())) {
                sql.append(" ,status=" + manages.getStatus());
            }
            if (Utils.isNotEmpty(manages.getRealName())) {
                sql.append(" ,real_name='" + manages.getRealName() + "'");
            }
            if (Utils.isNotEmpty(manages.getPhone())) {
                sql.append(" ,phone='" + manages.getPhone() + "'");
            }
            if (Utils.isNotEmpty(manages.getBody())) {
                sql.append(" ,body='" + manages.getBody() + "'");
            }
            if (Utils.isNotEmpty(manages.getDepartments())) {
                sql.append(" ,departments='" + manages.getDepartments() + "'");
            }
            if (Utils.isNotEmpty(manages.getPassWord())) {
                sql.append(" ,pass_word='" + manages.getPassWord() + "'");
            }
            if (Utils.isNotEmpty(manages.getPermissions())) {
                sql.append(" , permissions='" + manages.getPermissions() + "'");
            }
            if (Utils.isNotEmpty(manages.getPost())) {
                sql.append(" ,post='" + manages.getPost() + "'");
            }
            if (Utils.isNotEmpty(manages.getType())) {
                sql.append(" ,type='" + manages.getType() + "'");
            }
            if (Utils.isNotEmpty(manages.getId())) {
                sql.append(" where id=" + manages.getId());
            }
        }
        return jdbcTemplate.update(sql.toString());

    }

    @Override
    public PageBean getManagesList(Manages manages, Integer page, Integer pageSize, String purviewCode) {
        StringBuffer from = new StringBuffer("From sys_manages m left join sys_manages_to_purview_group mtpg " +
                "on m.id = mtpg.manager_id " +
                "left join sys_purview_group pg " +
                "on mtpg.purview_group_code = pg.purview_group_code where m.user_name != 'ycadministrator' ");
        List<Object> paras = new ArrayList<Object>();
        if (Utils.isNotEmpty(manages)) {
            if (Utils.isNotEmpty(manages.getUserName())) {
                from.append(" and m.user_name=?");
                paras.add(manages.getUserName());
            }
            if (Utils.isNotEmpty(manages.getRealName())) {
                from.append(" and m.real_name=?");
                paras.add(manages.getRealName());
            }
            if (Utils.isNotEmpty(manages.getMobile())) {
                from.append(" and m.mobile=?");
                paras.add(manages.getMobile());
            }
            if (Utils.isNotEmpty(manages.getStatus())) {
                from.append(" and m.status=?");
                paras.add(manages.getStatus());
            }
            if (Utils.isNotEmpty(manages.getPost())) {
                from.append(" and m.post=?");
                paras.add(manages.getPost());
            }
            if (Utils.isNotEmpty(manages.getDepartments())) {
                from.append(" and m.departments=?");
                paras.add(manages.getDepartments());
            }
            if (Utils.isNotEmpty(purviewCode)) {
                from.append(" and pg.purview_group_code=?");
                paras.add(purviewCode);
            }
        }
        Object[] obj = new Object[paras.size()];
        for (int i = 0; i < paras.size(); i++) {
            obj[i] = paras.get(i);
        }
        logger.info("SELECT COUNT(*) " + from.toString());
        int count = jdbcTemplate.queryForInt("SELECT COUNT(*) " + from.toString(), obj);
        int pageTotal = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;

        PageBean pageBean = new PageBean();
        List resultList = new ArrayList();
        if (page <= pageTotal) {
            StringBuffer select = new StringBuffer("select m.id as id,m.user_name as userName,m.mobile as mobile,m.departments as departments," +
                    "m.email as email,m.post as post,m.status as status,m.phone as phone,m.type as type,m.real_name as realName,pg.name as name,m.create_time ");
            select.append(from);
            select.append(" order by m.create_time desc ");
            logger.info("" + select.toString());

            resultList = jdbcTemplate.queryForList(JdbcPageUtil.getOraclePageSQL(select.toString(), page, pageSize), obj);
        }

        pageBean.setPageId(page);
        pageBean.setPageTotal(pageTotal);
        pageBean.setItemTotal(new Long(count));
        pageBean.setPageContent(resultList);
        return pageBean;
    }

    @Override
    public Manages getAdminById(String adminId) {
        try {
            String sql = "select * from sys_manages where id=? ";
            return jdbcTemplate.queryForObject(sql, new Long[]{new Long(adminId)}, new SysManagesrRowMapper());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Manages adminLogin(String adminName, String password) {
        try {
            String sql = "select * from sys_manages where user_name=? and pass_word=? and status <> ? and status <> ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{adminName, password, 2, 3}, new SysManagesrRowMapper());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Manages adminLoginByType(String adminName, String password, String adminType) {
        try {
            StringBuffer sql = new StringBuffer("select * from sys_manages where user_name=? and pass_word=? ");
            if (Utils.isNotEmpty(adminType)) {
                sql.append(" and type in (" + adminType + ") ");
            }
            return jdbcTemplate.queryForObject(sql.toString(), new Object[]{adminName, password}, new SysManagesrRowMapper());
        } catch (Exception e) {
            return null;
        }
    }
}
