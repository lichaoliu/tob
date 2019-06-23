package com.cndym.dao.impl;

import com.cndym.bean.sys.Purview;
import com.cndym.dao.IPurviewDao;
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
public class PurviewDaoImpl extends GenericDaoImpl<Purview> implements IPurviewDao {
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
    public Purview getPurview(String adminName, String code) {
        return null;
    }

    @Override
	public PageBean getPurviewList(Purview purview){
		try {
			StringBuffer sql = new StringBuffer("From Purview where 1=1 ");
			List<HibernatePara> hibernateParas = new ArrayList<HibernatePara>();
			if(Utils.isNotEmpty(purview)){
				if(Utils.isNotEmpty(purview.getManagerId())){
					sql.append(" and managerId=?");
					hibernateParas.add(new HibernatePara(purview.getManagerId()));
				}
				if(Utils.isNotEmpty(purview.getPurviewCode())){
					sql.append(" and purviewCode=?");
					hibernateParas.add(new HibernatePara(purview.getPurviewCode()));
				}
			}
			return getPageBeanByPara(sql.toString(), hibernateParas, 1, 100);
		} catch (Exception e) {
			return null;
		}
	}


	@Override
	public PageBean getAdminPurviewUrlList(Purview purview) {
		try {
			StringBuffer sql = new StringBuffer("From Purview p,PurviewUrl pu where p.purviewCode=pu.code ");
			List<HibernatePara> hibernateParas = new ArrayList<HibernatePara>();
			if(Utils.isNotEmpty(purview)){
				if(Utils.isNotEmpty(purview.getManagerId())){
					sql.append(" and p.managerId=?");
					hibernateParas.add(new HibernatePara(purview.getManagerId()));
				}
				if(Utils.isNotEmpty(purview.getPurviewCode())){
					sql.append(" and p.purviewCode=?");
					hibernateParas.add(new HibernatePara(purview.getPurviewCode()));
				}
			}
			return getPageBeanByPara(sql.toString(), hibernateParas, 1, 100);
		} catch (Exception e) {
			return null;
		}
	}


	@Override
	public boolean deleteByAdminId(String adminId) {
		try {                                         
			String sql = "delete sp from sys_purview sp where sp.manager_id='" + adminId + "'";
			int i = jdbcTemplate.update(sql);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}


	@Override
	public boolean deleteByAdminIdAndIndex(String adminId, String codeFather) {
		try {
			StringBuffer sql = new StringBuffer("delete sp from sys_purview sp,sys_purview_url spu where spu.code=sp.purview_code ");
			if(Utils.isNotEmpty(adminId)){
				sql.append(" and sp.manager_id='" + adminId + "'");
			}
			if(Utils.isNotEmpty(codeFather)){
				sql.append(" and spu.code_father='" + codeFather + "'");
			}
			int i = jdbcTemplate.update(sql.toString());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
}
