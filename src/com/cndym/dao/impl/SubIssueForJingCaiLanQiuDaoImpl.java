package com.cndym.dao.impl;

import com.cndym.bean.tms.SubIssueForJingCaiLanQiu;
import com.cndym.constant.Constants;
import com.cndym.dao.ISubIssueForJingCaiLanQiuDao;
import com.cndym.dao.impl.rowMapperBean.SubIssueForJingCaiLanQiuRowMapper;
import com.cndym.utils.DateUtils;
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
 * User: 邓玉明 Date: 11-3-27 下午10:30
 */
@Repository
public class SubIssueForJingCaiLanQiuDaoImpl extends GenericDaoImpl<SubIssueForJingCaiLanQiu> implements ISubIssueForJingCaiLanQiuDao {
    @Resource
    private SessionFactory sessionFactoryTemp;
    @Resource
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void sessionFactoryInit() {
        super.setSessionFactory(sessionFactoryTemp);
    }

    @Override
    public SubIssueForJingCaiLanQiu getSubIssueForJingCaiLanQiuByIssueSn(String issue, String sn) {
        String sql = "From SubIssueForJingCaiLanQiu where issue=? and sn=? and pollCode=?";
        List<SubIssueForJingCaiLanQiu> subIssueForJingCaiLanQiuList = find(sql, new Object[]{issue, sn, "02"});
        if (!subIssueForJingCaiLanQiuList.isEmpty()) {
            return subIssueForJingCaiLanQiuList.get(0);
        }
        return null;
    }

    public SubIssueForJingCaiLanQiu getSubIssueForJingCaiLanQiuByIssueSn(String issue, String sn, String playCode, String pollCode) {
        String sql = "From SubIssueForJingCaiLanQiu where issue=? and sn=? and pollCode=? and playCode=?";
        List<SubIssueForJingCaiLanQiu> subIssueForJingCaiLanQiuList = find(sql, new Object[]{issue, sn, pollCode, playCode});
        if (!subIssueForJingCaiLanQiuList.isEmpty()) {
            return subIssueForJingCaiLanQiuList.get(0);
        }
        return null;
    }

    @Override
    public List<SubIssueForJingCaiLanQiu> getSubIssueForJingCaiLanQiuListByDate(String date) {
        String sql = "From SubIssueForJingCaiLanQiu where issue=?";
        return find(sql, new Object[]{date});
    }

    @Override
    public List<SubIssueForJingCaiLanQiu> getSubIssueForJingCaiLanQiuListByPera(String date, String endTime, String playCode, String pollCode) {
        StringBuffer sql = new StringBuffer("From SubIssueForJingCaiLanQiu where 1=1");
        List<Object> peras = new ArrayList<Object>();

        if (Utils.isNotEmpty(date)) {
            sql.append(" and issue=?");
            peras.add(date);
        }

        if (Utils.isNotEmpty(endTime)) {
            sql.append(" and endTime>=?");
            peras.add(Utils.formatDate(endTime + " 00:00:00"));
            sql.append(" and endTime<?");
            Calendar calendar = Calendar.getInstance();
            Date endT = DateUtils.formatStr2Date(endTime + " 00:00:00");
            calendar.setTime(endT);
            calendar.add(Calendar.DATE, 1);
            peras.add(calendar.getTime());
        }

        if (Utils.isNotEmpty(playCode)) {
            sql.append(" and playCode=?");
            peras.add(playCode);
        }

        if (Utils.isNotEmpty(pollCode)) {
            sql.append(" and pollCode=?");
            peras.add(pollCode);
        }
        return find(sql.toString(), peras.toArray());
    }

    @Override
    public List getSubIssueForJingCaiLanQiuListByDateBonusOperator(Integer status) {
        String sql = "select jc.id id,jc.issue issue,jc.week week,jc.sn sn from tms_sub_issue_for_jclq jc left join tms_bonus_jc_log jclog on jc.id = jclog.id where jclog.sub_issue_for_id is null and jc.play_code='00' and jc.bonus_operator=" + status;
        return jdbcTemplate.queryForList(sql);
    }

    @Override
    public PageBean getPageBeanForJob(Integer page, Integer pageSize) {
        String sql = "select new SubIssueForJingCaiLanQiu(issue, sn, endDanShiTime, endFuShiTime) From SubIssueForJingCaiLanQiu where endOperator=? order by endTime asc";
        return getPageBean(sql, new Object[]{Constants.SUB_ISSUE_END_OPERATOR_DEFAULT}, page, pageSize);
    }

    @Override
    public int updateForJob(String issue, String sn, Integer endOperator) {
        String sql = "update tms_sub_issue_for_jclq t set t.end_operator = ? where t.issue = ? and t.sn=?";
        return jdbcTemplate.update(sql, new Object[]{endOperator, issue, sn});
    }

    @Override
    public int updateEndOperator() {
        String sql = "update tms_sub_issue_for_jclq t set t.end_operator = ? where t.end_operator = ?";
        return jdbcTemplate.update(sql, new Object[]{Constants.SUB_ISSUE_END_OPERATOR_CANCEL, Constants.SUB_ISSUE_END_OPERATOR_DEFAULT});
    }

    @Override
    public SubIssueForJingCaiLanQiu getSubIssueForJingCaiLanQiuForUpdate(String issue, String sn) {
        String sql = "select * from tms_sub_issue_for_jclq t where t.issue=? and t.sn=? and t.poll_code=?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{issue, sn, "02"}, new SubIssueForJingCaiLanQiuRowMapper());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> getSortListByTicketId(String ticketId) {
        StringBuffer sql = new StringBuffer(
                "select s.issue as issue,s.sn as sn,s.end_time from tms_sub_issue_for_jclq s right join tms_ticket_re_code p on s.issue=substr(p.match_id,1,8) and s.sn=substr(p.match_id,9,3) where 1=1 and s.play_code='00' ");
        sql.append(" and p.ticket_id=?");
        sql.append(" order by s.end_time,(s.sn+0)");
        return jdbcTemplate.queryForList(sql.toString(), new Object[]{ticketId});
    }

    @Override
    public PageBean getSubIssueForJingCaiLanQiuListByParaDesc(SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiu, Integer page, Integer pageSize) {
        StringBuilder sql = new StringBuilder("From SubIssueForJingCaiLanQiu where 1=1");
        List<HibernatePara> hibernateParas = new ArrayList<HibernatePara>();
        if (Utils.isNotEmpty(subIssueForJingCaiLanQiu)) {
            if (Utils.isNotEmpty(subIssueForJingCaiLanQiu.getEndOperator())) {
                if (Constants.SUB_ISSUE_END_OPERATOR_NON_DEFAULT == subIssueForJingCaiLanQiu.getEndOperator().intValue()) {
                    sql.append(" and endOperator<>?");// 非销售中的场次查询条件
                    hibernateParas.add(new HibernatePara(Constants.SUB_ISSUE_END_OPERATOR_DEFAULT));
                } else {
                    sql.append(" and endOperator=?");
                    hibernateParas.add(new HibernatePara(subIssueForJingCaiLanQiu.getEndOperator()));
                }
            }
            if (Utils.isNotEmpty(subIssueForJingCaiLanQiu.getIssue())) {
                sql.append(" and issue=?");
                hibernateParas.add(new HibernatePara(subIssueForJingCaiLanQiu.getIssue()));
            }
            if (Utils.isNotEmpty(subIssueForJingCaiLanQiu.getSn())) {
                sql.append(" and sn=?");
                hibernateParas.add(new HibernatePara(subIssueForJingCaiLanQiu.getSn()));
            }
            if (Utils.isNotEmpty(subIssueForJingCaiLanQiu.getBonusOperator())) {
                sql.append(" and bonusOperator=?");
                hibernateParas.add(new HibernatePara(subIssueForJingCaiLanQiu.getBonusOperator()));
            }
            // 赛事编号(周三301)
            if (Utils.isNotEmpty(subIssueForJingCaiLanQiu.getMatchColor())) {
                sql.append(" and CONCAT(week,sn) like ?");
                hibernateParas.add(new HibernatePara("%" + subIssueForJingCaiLanQiu.getMatchColor() + "%"));
            }
            if (Utils.isNotEmpty(subIssueForJingCaiLanQiu.getMainTeam())) {
                sql.append(" and mainTeam like ?");
                hibernateParas.add(new HibernatePara("%" + subIssueForJingCaiLanQiu.getMainTeam() + "%"));
            }
            if (Utils.isNotEmpty(subIssueForJingCaiLanQiu.getGuestTeam())) {
                sql.append(" and guestTeam like ?");
                hibernateParas.add(new HibernatePara("%" + subIssueForJingCaiLanQiu.getGuestTeam() + "%"));
            }
            if (Utils.isNotEmpty(subIssueForJingCaiLanQiu.getMatchName())) {
                sql.append(" and matchName like ?");
                hibernateParas.add(new HibernatePara("%" + subIssueForJingCaiLanQiu.getMatchName() + "%"));
            }
            if (Utils.isNotEmpty(subIssueForJingCaiLanQiu.getOperatorsAward())) {
                sql.append(" and operatorsAward = ?");
                hibernateParas.add(new HibernatePara(subIssueForJingCaiLanQiu.getOperatorsAward()));
            }
            if (Utils.isNotEmpty(subIssueForJingCaiLanQiu.getUpdateTime())) {
                sql.append(" and endTime >= ?");
                hibernateParas.add(new HibernatePara(subIssueForJingCaiLanQiu.getUpdateTime()));
            }
            if (Utils.isNotEmpty(subIssueForJingCaiLanQiu.getEndTime())) {
                sql.append(" and endTime < ?");
                hibernateParas.add(new HibernatePara(subIssueForJingCaiLanQiu.getEndTime()));
            }
            if (Utils.isNotEmpty(subIssueForJingCaiLanQiu.getPlayCode())) {
                sql.append(" and playCode = ?");
                hibernateParas.add(new HibernatePara(subIssueForJingCaiLanQiu.getPlayCode()));
            }
            if (Utils.isNotEmpty(subIssueForJingCaiLanQiu.getPollCode())) {
                sql.append(" and pollCode = ?");
                hibernateParas.add(new HibernatePara(subIssueForJingCaiLanQiu.getPollCode()));
            }
        }
        sql.append(" order by issue desc,sn desc");
        return getPageBeanByPara(sql.toString(), hibernateParas, page, pageSize);
    }

    @Override
    public PageBean getSubIssueForJingCaiLanQiuListByPara(SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiu, Integer page, Integer pageSize) {
        StringBuilder sql = new StringBuilder("From SubIssueForJingCaiLanQiu where 1=1");
        List<HibernatePara> hibernateParas = new ArrayList<HibernatePara>();
        if (Utils.isNotEmpty(subIssueForJingCaiLanQiu)) {
            if (Utils.isNotEmpty(subIssueForJingCaiLanQiu.getIssue())) {
                sql.append(" and issue=?");
                hibernateParas.add(new HibernatePara(subIssueForJingCaiLanQiu.getIssue()));
            }
            if (Utils.isNotEmpty(subIssueForJingCaiLanQiu.getSn())) {
                sql.append(" and sn=?");
                hibernateParas.add(new HibernatePara(subIssueForJingCaiLanQiu.getSn()));
            }
            if (Utils.isNotEmpty(subIssueForJingCaiLanQiu.getEndOperator())) {
                if (Constants.SUB_ISSUE_END_OPERATOR_NON_DEFAULT == subIssueForJingCaiLanQiu.getEndOperator()) {
                    sql.append(" and endOperator<>?");// 非销售中的场次查询条件
                    hibernateParas.add(new HibernatePara(Constants.SUB_ISSUE_END_OPERATOR_DEFAULT));
                } else {
                    sql.append(" and endOperator=?");
                    hibernateParas.add(new HibernatePara(subIssueForJingCaiLanQiu.getEndOperator()));
                }
            }
            if (Utils.isNotEmpty(subIssueForJingCaiLanQiu.getBonusOperator())) {
                sql.append(" and bonusOperator=?");
                hibernateParas.add(new HibernatePara(subIssueForJingCaiLanQiu.getBonusOperator()));
            }
            // 赛事编号(周三301)
            if (Utils.isNotEmpty(subIssueForJingCaiLanQiu.getMatchColor())) {
                sql.append(" and CONCAT(week,sn) like ?");
                hibernateParas.add(new HibernatePara("%" + subIssueForJingCaiLanQiu.getMatchColor() + "%"));
            }
            if (Utils.isNotEmpty(subIssueForJingCaiLanQiu.getMainTeam())) {
                sql.append(" and mainTeam like ?");
                hibernateParas.add(new HibernatePara("%" + subIssueForJingCaiLanQiu.getMainTeam() + "%"));
            }
            if (Utils.isNotEmpty(subIssueForJingCaiLanQiu.getGuestTeam())) {
                sql.append(" and guestTeam like ?");
                hibernateParas.add(new HibernatePara("%" + subIssueForJingCaiLanQiu.getGuestTeam() + "%"));
            }
            if (Utils.isNotEmpty(subIssueForJingCaiLanQiu.getMatchName())) {
                sql.append(" and matchName like ?");
                hibernateParas.add(new HibernatePara("%" + subIssueForJingCaiLanQiu.getMatchName() + "%"));
            }
            if (Utils.isNotEmpty(subIssueForJingCaiLanQiu.getOperatorsAward())) {
                sql.append(" and operatorsAward = ?");
                hibernateParas.add(new HibernatePara(subIssueForJingCaiLanQiu.getOperatorsAward()));
            }
            if (Utils.isNotEmpty(subIssueForJingCaiLanQiu.getUpdateTime())) {
                sql.append(" and endTime >= ?");
                hibernateParas.add(new HibernatePara(subIssueForJingCaiLanQiu.getUpdateTime()));
            }
            if (Utils.isNotEmpty(subIssueForJingCaiLanQiu.getEndTime())) {
                sql.append(" and endTime <= ?");
                hibernateParas.add(new HibernatePara(subIssueForJingCaiLanQiu.getEndTime()));
            }
            if (Utils.isNotEmpty(subIssueForJingCaiLanQiu.getPlayCode())) {
                sql.append(" and playCode = ?");
                hibernateParas.add(new HibernatePara(subIssueForJingCaiLanQiu.getPlayCode()));
            }
            if (Utils.isNotEmpty(subIssueForJingCaiLanQiu.getPollCode())) {
                sql.append(" and pollCode = ?");
                hibernateParas.add(new HibernatePara(subIssueForJingCaiLanQiu.getPollCode()));
            }
            if (Utils.isNotEmpty(subIssueForJingCaiLanQiu.getBackup2())) {
                if ("nonHide".equals(subIssueForJingCaiLanQiu.getBackup2())) {
                    sql.append(" and (backup2 = ? or backup2 is null)");// 不查询出隐藏的对阵
                    hibernateParas.add(new HibernatePara("0"));
                } else {
                    sql.append(" and backup2 = ?");
                    hibernateParas.add(new HibernatePara(subIssueForJingCaiLanQiu.getBackup2()));
                }
            }
        }
        sql.append(" order by endTime asc,sn asc");
        return getPageBeanByPara(sql.toString(), hibernateParas, page, pageSize);
    }

    @Override
    public SubIssueForJingCaiLanQiu getSubIssueForJingCaiLanQiuBySnDate(String sn, String date, String playCode, String pollCode) {
        String sql = "From SubIssueForJingCaiLanQiu where sn=? and issue=? and playCode=? and pollCode=?";
        List<SubIssueForJingCaiLanQiu> subIssueForJingCaiLanQiuList = find(sql, new Object[]{sn, date, playCode, pollCode});
        if (!subIssueForJingCaiLanQiuList.isEmpty()) {
            return subIssueForJingCaiLanQiuList.get(0);
        }
        return null;
    }

    @Override
    public SubIssueForJingCaiLanQiu getSubIssueForJingCaiLanQiu(Long id) {
        String sql = "From SubIssueForJingCaiLanQiu where id = ?";
        List<SubIssueForJingCaiLanQiu> subIssueForJingCaiLanQiuList = find(sql, new Object[]{id});
        if (Utils.isNotEmpty(subIssueForJingCaiLanQiuList)) {
            return subIssueForJingCaiLanQiuList.get(0);
        }
        return null;
    }

    @Override
    public int doUpdateOperatorForSn(String issue, String sn, Integer operator) {
        if (Utils.isEmpty(issue) || Utils.isEmpty(sn) || null == operator) {
            throw new IllegalArgumentException("更新场次操作状态不成功");
        }
        String sql = "update tms_sub_issue_for_jclq t set t.end_operator = ? where t.issue = ? and t.sn = ?";
        return jdbcTemplate.update(sql, new Object[]{operator, issue, sn});
    }

    @Override
    public SubIssueForJingCaiLanQiu getSubIssueForJingCaiLanQiuBySnDate(String sn, String date, String playCode, String pollCode, int endOperator) {
        String sql = "From SubIssueForJingCaiLanQiu where sn=? and issue=? and playCode=? and pollCode=? and endOperator= ? ";
        List<SubIssueForJingCaiLanQiu> subIssueForJingCaiLanQiuList = find(sql, new Object[]{sn, date, playCode, pollCode, endOperator});
        if (!subIssueForJingCaiLanQiuList.isEmpty()) {
            return subIssueForJingCaiLanQiuList.get(0);
        }
        return null;
    }
}
