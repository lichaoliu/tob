package com.cndym.dao.impl;

import com.cndym.bean.tms.SubIssueForGjb;
import com.cndym.dao.ISubIssueForGjbDao;
import com.cndym.utils.Utils;
import com.cndym.utils.hibernate.HibernatePara;
import com.cndym.utils.hibernate.PageBean;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: luna
 * QQ: 116741034
 * Date: 14-5-20
 * Time: 下午1:39
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class SubIssueForGjbDaoImpl extends GenericDaoImpl<SubIssueForGjb> implements ISubIssueForGjbDao {
    @Resource
    private SessionFactory sessionFactoryTemp;
    @Resource
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void sessionFactoryInit() {
        super.setSessionFactory(sessionFactoryTemp);
    }

    @Override
    public List<SubIssueForGjb> getListByIssue(String issue) {
        String sql = "From SubIssueForGjb where issue=?";
        return find(sql, new Object[]{issue});
    }

    @Override
    public PageBean getSubIssueForGjbList(String issue, Integer page, Integer pageSize) {
        StringBuffer sql = new StringBuffer("From SubIssueForGjb gjb where 1=1");
        List<HibernatePara> hibernateParas = new ArrayList<HibernatePara>();
        if (Utils.isNotEmpty(issue)) {
            sql.append(" and gjb.issue=?");
            hibernateParas.add(new HibernatePara(issue));
        }
        sql.append(" order by index asc");
        return getPageBeanByPara(sql.toString(), hibernateParas, page, pageSize);
    }

    @Override
    public int updateSubIssueForGjbList(SubIssueForGjb subIssueForGjb) {
        StringBuffer sql = new StringBuffer("update tms_sub_issue_for_gjb set team='");
        sql.append(subIssueForGjb.getTeam());
        sql.append("'");
        if (Utils.isNotEmpty(subIssueForGjb.getStatus())) {
            sql.append(" ,status='");
            sql.append(subIssueForGjb.getStatus());
            sql.append("'");
        }
        if (Utils.isNotEmpty(subIssueForGjb.getSp())) {
            sql.append(" ,sp='");
            sql.append(subIssueForGjb.getSp());
            sql.append("'");
        }
        sql.append(" ,update_time=to_date('");
        sql.append(Utils.formatDate2Str(new Date(), "yyyy-MM-dd HH:mm:ss"));
        sql.append("','yyyy-MM-dd hh24:mi:ss'), end_time=to_date('");
        sql.append(Utils.formatDate2Str(subIssueForGjb.getEndTime(), "yyyy-MM-dd HH:mm:ss"));
        sql.append("','yyyy-MM-dd hh24:mi:ss') where team='");
        sql.append(subIssueForGjb.getTeam());
        sql.append("' and sn='" + subIssueForGjb.getSn());
        sql.append("' and issue='" + subIssueForGjb.getIssue());
        sql.append("' and league='" + subIssueForGjb.getLeague());
        sql.append("'");
        return jdbcTemplate.update(sql.toString());
    }

    @Override
    public boolean saveAllSubGame(List<SubIssueForGjb> subIssueForGjbList) {
        return saveAllObject(subIssueForGjbList);
    }

	@Override
	public SubIssueForGjb getSubIssueForGjbByIssue(String issue, String sn) {
		return null;
	}

	@Override
	public SubIssueForGjb getSubIssueForGjbByIssue(String issue) {
		return null;
	}
}
