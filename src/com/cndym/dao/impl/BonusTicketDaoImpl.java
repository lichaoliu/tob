package com.cndym.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cndym.bean.query.BonusTicketQueryBean;
import com.cndym.bean.tms.BonusTicket;
import com.cndym.dao.IBonusTicketDao;
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

@Repository
public class BonusTicketDaoImpl extends GenericDaoImpl<BonusTicket> implements IBonusTicketDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Resource
    private SessionFactory sessionFactoryTemp;

    @PostConstruct
    private void sessionFactoryInit() {
        super.setSessionFactory(sessionFactoryTemp);
    }

    /**
     * 查询代理商数字彩一期的中奖票
     *
     * @param sid
     * @param lotteryCode
     * @param issue
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public PageBean getBounsTicketByIssue(String sid, String lotteryCode, String issue, int page, int pageSize) {
        StringBuffer sql = new StringBuffer("From BonusTicket t where 1=1 ");
        List<HibernatePara> hibernateParas = new ArrayList<HibernatePara>();

        sql.append(" and t.lotteryCode=?");
        hibernateParas.add(new HibernatePara(lotteryCode));

        sql.append(" and t.issue=?");
        hibernateParas.add(new HibernatePara(issue));

        sql.append(" and t.sid=?");
        hibernateParas.add(new HibernatePara(sid));

        return getPageBeanByPara(sql.toString(), hibernateParas, page, pageSize);
    }

    /**
     * 查询代理商竞彩一场的中奖票
     *
     * @param sid
     * @param lotteryCode
     * @param issue
     * @param sn
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public PageBean getBounsTicketByIssue(String sid, String lotteryCode, String issue, String sn, int page, int pageSize) {
        StringBuffer sql = new StringBuffer("From BonusTicket t where 1=1 ");
        List<HibernatePara> hibernateParas = new ArrayList<HibernatePara>();

        sql.append(" and t.lotteryCode=?");
        hibernateParas.add(new HibernatePara(lotteryCode));

        // sql.append(" and t.issue=?");
        // hibernateParas.add(new HibernatePara(issue));

        sql.append(" and t.endGameId=?");
        hibernateParas.add(new HibernatePara(issue + sn));

        sql.append(" and t.sid=?");
        hibernateParas.add(new HibernatePara(sid));

        return getPageBeanByPara(sql.toString(), hibernateParas, page, pageSize);
    }

    @Override
    public PageBean getPageBeanByPara(BonusTicketQueryBean queryBean, Integer page, Integer pageSize) {
        StringBuffer sql = new StringBuffer(" from tms_bonus_ticket t ");
        sql.append(" left join tms_main_issue tmi on t.lottery_code = tmi.lottery_code and t.issue = tmi.name ");
        sql.append(" left join user_member m on m.user_code = t.user_code where 1=1 ");
        List<Object> paramList = new ArrayList<Object>();
        if (Utils.isNotEmpty(queryBean)) {
            BonusTicket ticket = queryBean.getBonusTicket();
            if (Utils.isNotEmpty(ticket)) {
                if (Utils.isNotEmpty(ticket.getSid())) {
                    sql.append(" and t.sid = ? ");
                    paramList.add(ticket.getSid());
                }
                if (Utils.isNotEmpty(ticket.getBackup2())) {
                    sql.append(" and m.name like ? ");
                    paramList.add("%" + ticket.getBackup2() + "%");
                }
                if (Utils.isNotEmpty(ticket.getLotteryCode())) {
                    sql.append(" and t.lottery_code = ? ");
                    paramList.add(ticket.getLotteryCode());
                }
                if (Utils.isNotEmpty(ticket.getIssue())) {
                    sql.append(" and t.issue = ? ");
                    paramList.add(ticket.getIssue());
                }
                if (Utils.isNotEmpty(ticket.getOrderId())) {
                    sql.append(" and t.order_id = ? ");
                    paramList.add(ticket.getOrderId());
                }

                if (Utils.isNotEmpty(ticket.getOutTicketId())) {
                    sql.append(" and t.out_ticket_id = ? ");
                    paramList.add(ticket.getOutTicketId());
                }

                if (Utils.isNotEmpty(ticket.getTicketId())) {
                    sql.append(" and t.ticket_id = ? ");
                    paramList.add(ticket.getTicketId());
                }

                if (Utils.isNotEmpty(ticket.getPostCode())) {
                    sql.append(" and t.post_code = ? ");
                    paramList.add(ticket.getPostCode());
                }

                if (Utils.isNotEmpty(ticket.getBigBonus())) {
                    sql.append(" and t.big_bonus = ? ");
                    paramList.add(ticket.getBigBonus());
                }
            }
            if (Utils.isNotEmpty(queryBean.getCreateStartTime())) {
                sql.append(" and t.create_time >= ? ");
                paramList.add(queryBean.getCreateStartTime());
            }
            if (Utils.isNotEmpty(queryBean.getCreateEndTime())) {
                sql.append(" and t.create_time < ? ");
                paramList.add(queryBean.getCreateEndTime());
            }
            if (Utils.isNotEmpty(queryBean.getSendStartTime())) {
                sql.append(" and t.send_time >= ? ");
                paramList.add(queryBean.getSendStartTime());
            }
            if (Utils.isNotEmpty(queryBean.getSendEndTime())) {
                sql.append(" and t.send_time < ? ");
                paramList.add(queryBean.getSendEndTime());
            }
            if (Utils.isNotEmpty(queryBean.getReturnStartTime())) {
                sql.append(" and t.return_time >= ? ");
                paramList.add(queryBean.getReturnStartTime());
            }
            if (Utils.isNotEmpty(queryBean.getReturnEndTime())) {
                sql.append(" and t.return_time < ? ");
                paramList.add(queryBean.getReturnEndTime());
            }
            if (Utils.isNotEmpty(queryBean.getBonusStartTime())) {
                sql.append(" and t.bonus_time >= ? ");
                paramList.add(queryBean.getBonusStartTime());
            }
            if (Utils.isNotEmpty(queryBean.getBonusEndTime())) {
                sql.append(" and t.bonus_time < ? ");
                paramList.add(queryBean.getBonusEndTime());
            }
            if (Utils.isNotEmpty(queryBean.getIssueStatus())) {
                sql.append(" and tmi.status = ? ");
                paramList.add(queryBean.getIssueStatus());
            }
            if (Utils.isNotEmpty(queryBean.getIssueBonusStatus())) {
                sql.append(" and tmi.bonus_status = ? ");
                paramList.add(queryBean.getIssueBonusStatus());
            }
            if (Utils.isNotEmpty(queryBean.getOperatorsAward())) {
                sql.append(" and tmi.operator_award = ? ");
                paramList.add(queryBean.getOperatorsAward());
            }
            if (Utils.isNotEmpty(queryBean.getBonusAmountS())) {
                sql.append(" and t.bonus_amount >= ? ");
                paramList.add(queryBean.getBonusAmountS());
            }
            if (Utils.isNotEmpty(queryBean.getBonusAmountE())) {
                sql.append(" and t.bonus_amount < ? ");
                paramList.add(queryBean.getBonusAmountE());
            }
        }

        logger.info("SELECT COUNT(*) " + sql.toString());
        int count = jdbcTemplate.queryForInt("SELECT COUNT(*) " + sql.toString(), paramList.toArray());
        int pageTotal = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;

        PageBean pageBean = new PageBean();
        List resultList = new ArrayList();
        if (page <= pageTotal) {
            StringBuffer select = new StringBuffer("select t.ticket_id as ticketId,t.order_id as orderId,t.out_ticket_id as outTicketId,");
            select.append("t.lottery_code as lotteryCode,t.play_code as playCode,t.poll_code as pollCode,t.issue as issue,t.post_code as postCode,t.amount as amount,");
            select.append("t.big_bonus as bigBonus,t.bonus_class as bonusClass,t.bonus_amount as bonusAmount,t.fix_bonus_amount as fixBonusAmount,");
            select.append("t.create_time as createTime,t.send_time as sendTime,t.return_time as returnTime,t.bonus_time as bonusTime,");
            select.append("m.sid as sid,m.name as name ");
            select.append(sql);
            select.append(" order by t.create_time desc ");
            resultList = jdbcTemplate.queryForList(JdbcPageUtil.getOraclePageSQL(select.toString(), page, pageSize), paramList.toArray());
        }
        pageBean.setPageId(page);
        pageBean.setPageTotal(pageTotal);
        pageBean.setItemTotal(new Long(count));
        pageBean.setPageContent(resultList);
        return pageBean;
    }

    @Override
    public Map<String, Object> getBonusTicketCount(BonusTicketQueryBean queryBean) {
        StringBuffer sql = new StringBuffer(
                "select count(t.id) as ticketNum,sum(t.amount) as orderAmount,sum(t.bonus_amount) as bonusAmount,sum(t.fix_bonus_amount) as fixBonusAmount from tms_bonus_ticket t ");
        sql.append(" left join tms_main_issue tmi on t.lottery_code = tmi.lottery_code and t.issue = tmi.name ");
        sql.append(" left join user_member m on m.user_code = t.user_code where 1=1 ");
        List<Object> paramList = new ArrayList<Object>();
        if (Utils.isNotEmpty(queryBean)) {
            BonusTicket ticket = queryBean.getBonusTicket();
            if (Utils.isNotEmpty(ticket)) {
                if (Utils.isNotEmpty(ticket.getSid())) {
                    sql.append(" and t.sid = ? ");
                    paramList.add(ticket.getSid());
                }

                if (Utils.isNotEmpty(ticket.getLotteryCode())) {
                    sql.append(" and t.lottery_code = ? ");
                    paramList.add(ticket.getLotteryCode());
                }
                if (Utils.isNotEmpty(ticket.getIssue())) {
                    sql.append(" and t.issue = ? ");
                    paramList.add(ticket.getIssue());
                }
                if (Utils.isNotEmpty(ticket.getOrderId())) {
                    sql.append(" and t.order_id = ? ");
                    paramList.add(ticket.getOrderId());
                }

                if (Utils.isNotEmpty(ticket.getOutTicketId())) {
                    sql.append(" and t.out_ticket_id = ? ");
                    paramList.add(ticket.getOutTicketId());
                }

                if (Utils.isNotEmpty(ticket.getTicketId())) {
                    sql.append(" and t.ticket_id = ? ");
                    paramList.add(ticket.getTicketId());
                }

                if (Utils.isNotEmpty(ticket.getPostCode())) {
                    sql.append(" and t.post_code = ? ");
                    paramList.add(ticket.getPostCode());
                }

                if (Utils.isNotEmpty(ticket.getBigBonus())) {
                    sql.append(" and t.big_bonus = ? ");
                    paramList.add(ticket.getBigBonus());
                }
            }
            if (Utils.isNotEmpty(queryBean.getCreateStartTime())) {
                sql.append(" and t.create_time >= ? ");
                paramList.add(queryBean.getCreateStartTime());
            }
            if (Utils.isNotEmpty(queryBean.getCreateEndTime())) {
                sql.append(" and t.create_time < ? ");
                paramList.add(queryBean.getCreateEndTime());
            }
            if (Utils.isNotEmpty(queryBean.getSendStartTime())) {
                sql.append(" and t.send_time >= ? ");
                paramList.add(queryBean.getSendStartTime());
            }
            if (Utils.isNotEmpty(queryBean.getSendEndTime())) {
                sql.append(" and t.send_time < ? ");
                paramList.add(queryBean.getSendEndTime());
            }
            if (Utils.isNotEmpty(queryBean.getReturnStartTime())) {
                sql.append(" and t.return_time >= ? ");
                paramList.add(queryBean.getReturnStartTime());
            }
            if (Utils.isNotEmpty(queryBean.getReturnEndTime())) {
                sql.append(" and t.return_time < ? ");
                paramList.add(queryBean.getReturnEndTime());
            }
            if (Utils.isNotEmpty(queryBean.getBonusStartTime())) {
                sql.append(" and t.bonus_time >= ? ");
                paramList.add(queryBean.getBonusStartTime());
            }
            if (Utils.isNotEmpty(queryBean.getBonusEndTime())) {
                sql.append(" and t.bonus_time < ? ");
                paramList.add(queryBean.getBonusEndTime());
            }
            if (Utils.isNotEmpty(queryBean.getIssueStatus())) {
                sql.append(" and tmi.status = ? ");
                paramList.add(queryBean.getIssueStatus());
            }
            if (Utils.isNotEmpty(queryBean.getIssueBonusStatus())) {
                sql.append(" and tmi.bonus_status = ? ");
                paramList.add(queryBean.getIssueBonusStatus());
            }
            if (Utils.isNotEmpty(queryBean.getOperatorsAward())) {
                sql.append(" and tmi.operator_award = ? ");
                paramList.add(queryBean.getOperatorsAward());
            }
        }
        List<Map<String, Object>> dataList = jdbcTemplate.queryForList(sql.toString(), paramList.toArray());
        if (Utils.isNotEmpty(dataList)) {
            return dataList.get(0);
        }
        return null;
    }
}
