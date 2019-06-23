package com.cndym.dao.impl;

import com.cndym.bean.tms.SubIssueForBeiDan;
import com.cndym.constant.Constants;
import com.cndym.dao.ISubIssueForBeiDanDao;
import com.cndym.dao.impl.rowMapperBean.SubIssueForBeiDanRowMapper;
import com.cndym.utils.Utils;
import com.cndym.utils.hibernate.HibernatePara;
import com.cndym.utils.hibernate.PageBean;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

/**
 * User: 邓玉明
 * Date: 11-3-27 下午10:30
 */
@Repository
public class SubIssueForBeiDanDaoImpl extends GenericDaoImpl<SubIssueForBeiDan> implements ISubIssueForBeiDanDao {
    @Resource
    private SessionFactory sessionFactoryTemp;
    @Resource
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void sessionFactoryInit() {
        super.setSessionFactory(sessionFactoryTemp);
    }

    @Override
    public SubIssueForBeiDan getSubIssueForBeiDanByIssueSn(String issue, String sn) {
        String sql = "From SubIssueForBeiDan where issue=? and sn=?";
        List<SubIssueForBeiDan> subIssueForBeiDanList = find(sql, new Object[]{issue, sn});
        if (!subIssueForBeiDanList.isEmpty()) {
            return subIssueForBeiDanList.get(0);
        }
        return null;
    }

    @Override
    public List<SubIssueForBeiDan> getSubIssueForBeiDanListByIssue(String issue) {
        String sql = "From SubIssueForBeiDan where issue=?";
        return find(sql, new Object[]{issue});
    }

    @Override
    public PageBean getPageBeanForJob(Integer page, Integer pageSize) {
        String sql = "select new SubIssueForBeiDan(issue, sn, endDanShiTime, endFuShiTime) From SubIssueForBeiDan where endOperator=? order by endTime asc";
        return getPageBean(sql, new Object[]{Constants.SUB_ISSUE_END_OPERATOR_DEFAULT}, page, pageSize);
    }

    @Override
    public int updateForJob(String issue, String sn, Integer endOperator) {
        String sql = "update tms_sub_issue_for_bd t set t.end_operator = ? where t.issue = ? and t.sn=?";
        return jdbcTemplate.update(sql, new Object[]{endOperator, issue, sn});
    }

    @Override
    public int updateEndOperator() {
        String sql = "update tms_sub_issue_for_bd t set t.end_operator = ? where t.end_operator = ?";
        return jdbcTemplate.update(sql, new Object[]{Constants.SUB_ISSUE_END_OPERATOR_CANCEL, Constants.SUB_ISSUE_END_OPERATOR_DEFAULT});
    }


    @Override
    public SubIssueForBeiDan getSubIssueForBeiDanForUpdate(String issue, String sn) {
        String sql = "select * from tms_sub_issue_for_bd t where t.issue=? and t.sn=?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{issue, sn,}, new SubIssueForBeiDanRowMapper());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> getSortListByTicketId(String ticketId) {
        StringBuffer sql = new StringBuffer("select s.issue as issue,s.sn as sn,s.end_time from tms_sub_issue_for_bd s right join tms_ticket_re_code p on s.issue=p.issue and s.sn=p.match_id where 1=1 ");
        sql.append(" and p.ticket_id=?");
        sql.append(" order by s.end_time,(s.sn+0)");
        return jdbcTemplate.queryForList(sql.toString(), new Object[]{ticketId});
    }

    @Override
    public SubIssueForBeiDan getSubIssueForBeiDan(Long id) {
        String sql = "From SubIssueForBeiDan where id=? ";
        List<SubIssueForBeiDan> subIssueForBeiDanList = find(sql, new Object[]{id});
        if (!subIssueForBeiDanList.isEmpty()) {
            return subIssueForBeiDanList.get(0);
        }
        return null;
    }

    @Override
    public PageBean getSubIssueForBeiDanResultListByParaDesc(SubIssueForBeiDan subIssueForBeiDan, Integer page, Integer pageSize) {
        StringBuilder sql = new StringBuilder("From SubIssueForBeiDan where 1=1");
        List<HibernatePara> hibernateParas = new ArrayList<HibernatePara>();
        if (Utils.isNotEmpty(subIssueForBeiDan)) {
            if (Utils.isNotEmpty(subIssueForBeiDan.getEndOperator())) {
                if (Constants.SUB_ISSUE_END_OPERATOR_NON_DEFAULT == subIssueForBeiDan.getEndOperator()) {
                    sql.append(" and endOperator<>?");//非销售中的场次查询条件
                    hibernateParas.add(new HibernatePara(Constants.SUB_ISSUE_END_OPERATOR_DEFAULT));
                } else {
                    sql.append(" and endOperator=?");
                    hibernateParas.add(new HibernatePara(subIssueForBeiDan.getEndOperator()));
                }
            }
            if (Utils.isNotEmpty(subIssueForBeiDan.getIssueStart())) {
                sql.append(" and issue>=?");
                hibernateParas.add(new HibernatePara(subIssueForBeiDan.getIssueStart()));
            }
            if (Utils.isNotEmpty(subIssueForBeiDan.getIssueEnd())) {
                sql.append(" and issue<=?");
                hibernateParas.add(new HibernatePara(subIssueForBeiDan.getIssueEnd()));
            }
            if (Utils.isNotEmpty(subIssueForBeiDan.getIssue())) {
                sql.append(" and issue=?");
                hibernateParas.add(new HibernatePara(subIssueForBeiDan.getIssue()));
            }
            if (Utils.isNotEmpty(subIssueForBeiDan.getSn())) {
                sql.append(" and sn=?");
                hibernateParas.add(new HibernatePara(subIssueForBeiDan.getSn()));
            }
            if (Utils.isNotEmpty(subIssueForBeiDan.getBonusOperator())) {
                sql.append(" and bonusOperator=?");
                hibernateParas.add(new HibernatePara(subIssueForBeiDan.getBonusOperator()));
            }

            //赛事编号(周三301)
            if (Utils.isNotEmpty(subIssueForBeiDan.getMatchColor())) {
                sql.append(" and CONCAT(week,sn) like ?");
                hibernateParas.add(new HibernatePara("%" + subIssueForBeiDan.getMatchColor() + "%"));
            }
            if (Utils.isNotEmpty(subIssueForBeiDan.getMainTeam())) {
                sql.append(" and mainTeam like ?");
                hibernateParas.add(new HibernatePara("%" + subIssueForBeiDan.getMainTeam() + "%"));
            }
            if (Utils.isNotEmpty(subIssueForBeiDan.getGuestTeam())) {
                sql.append(" and guestTeam like ?");
                hibernateParas.add(new HibernatePara("%" + subIssueForBeiDan.getGuestTeam() + "%"));
            }
            if (Utils.isNotEmpty(subIssueForBeiDan.getMatchName())) {
                sql.append(" and matchName like ?");
                hibernateParas.add(new HibernatePara("%" + subIssueForBeiDan.getMatchName() + "%"));
            }
            if (Utils.isNotEmpty(subIssueForBeiDan.getOperatorsAward())) {
                sql.append(" and operatorsAward = ?");
                hibernateParas.add(new HibernatePara(subIssueForBeiDan.getOperatorsAward()));
            }
            if (Utils.isNotEmpty(subIssueForBeiDan.getUpdateTime())) {
                sql.append(" and endTime >= ?");
                hibernateParas.add(new HibernatePara(subIssueForBeiDan.getUpdateTime()));
            }
            if (Utils.isNotEmpty(subIssueForBeiDan.getEndTime())) {
                sql.append(" and endTime < ?");
                hibernateParas.add(new HibernatePara(subIssueForBeiDan.getEndTime()));
            }
        }
        sql.append(" order by issue desc,(sn+0) desc");
        return getPageBeanByPara(sql.toString(), hibernateParas, page, pageSize);
    }

    @Override
    public int doUpdateOperatorForSn(String issue, String sn, Integer operator) {
        if (Utils.isEmpty(issue) || Utils.isEmpty(sn) || null == operator) {
            throw new IllegalArgumentException("更新场次操作状态不成功");
        }
        String sql = "update tms_sub_issue_for_bd t set t.end_operator = ? where t.issue = ? and t.sn = ?";
        return jdbcTemplate.update(sql, new Object[]{operator, issue, sn});
    }

    @Override
    public SubIssueForBeiDan getSubIssueForBeiDanByIssueSn(String issue, String sn, int endOperator) {
        String sql = "From SubIssueForBeiDan where issue=? and sn=? and endOperator=?";
        List<SubIssueForBeiDan> subIssueForBeiDanList = find(sql, new Object[]{issue, sn, endOperator});
        if (!subIssueForBeiDanList.isEmpty()) {
            return subIssueForBeiDanList.get(0);
        }
        return null;
    }
}
