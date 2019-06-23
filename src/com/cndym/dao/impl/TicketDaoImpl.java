package com.cndym.dao.impl;

import com.cndym.bean.query.TicketMainIssueBean;
import com.cndym.bean.query.TicketQueryBean;
import com.cndym.bean.tms.BonusLog;
import com.cndym.bean.tms.MainIssue;
import com.cndym.bean.tms.Ticket;
import com.cndym.bean.user.Member;
import com.cndym.constant.Constants;
import com.cndym.control.PostMap;
import com.cndym.dao.ITicketDao;
import com.cndym.dao.impl.rowMapperBean.TicketRowMapper;
import com.cndym.utils.JdbcPageUtil;
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
 * User: 邓玉明 Date: 11-3-27 下午10:53
 */
@Repository
public class TicketDaoImpl extends GenericDaoImpl<Ticket> implements ITicketDao {
    @Resource
    private SessionFactory sessionFactoryTemp;
    @Resource
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void sessionFactoryInit() {
        super.setSessionFactory(sessionFactoryTemp);
    }

    @Override
    public Ticket getTicketByTicketIdForUpdate(String ticketId) {
        String sql = "select * from tms_ticket t where t.ticket_id=? for update";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{ticketId}, new TicketRowMapper());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int getTicketCountByIssue(String lotteryCode, String issue) {
        String sql = "select count(*) from tms_ticket t where t.lottery_code = ? and t.issue = ? ";
        try {
            return jdbcTemplate.queryForInt(sql, new Object[]{lotteryCode, issue});
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public Map<String, Object> getTicketMainIssueCountByIssue(TicketMainIssueBean ticketMainIssueBean) {
        List<Object> paramList = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select sum(t.amount) totalAmount,sum(t.fix_bonus_amount) totalFixBonuxAmount,sum(t.bonus_amount) totalBonuxAmount" +
                " from tms_ticket t left join tms_main_issue m on t.lottery_code = m.lottery_code and t.issue = m.name where 1=1");
        if (Utils.isNotEmpty(ticketMainIssueBean)) {
            Ticket ticket = ticketMainIssueBean.getTicket();
            MainIssue mainIssue = ticketMainIssueBean.getMainIssue();
            if (Utils.isNotEmpty(ticket)) {
                if (Utils.isNotEmpty(ticket.getSid())) {
                    sql.append(" and t.sid=?");
                    paramList.add(ticket.getSid());
                }
                if (Utils.isNotEmpty(ticket.getTicketStatus())) {
                    sql.append(" and t.ticket_status=?");
                    paramList.add(ticket.getTicketStatus());
                }
                if (Utils.isNotEmpty(ticket.getLotteryCode())) {
                    sql.append(" and t.lottery_code=?");
                    paramList.add(ticket.getLotteryCode());
                }
            }
            if (Utils.isNotEmpty(mainIssue)) {
                if (Utils.isNotEmpty(mainIssue.getStatus())) {
                    sql.append(" and m.status=?");
                    paramList.add(mainIssue.getStatus());
                }
            }
            if (Utils.isNotEmpty(ticketMainIssueBean.getEndTimeStart())) {
                sql.append(" and m.end_time>=?");
                paramList.add(ticketMainIssueBean.getEndTimeStart());
            }
            if (Utils.isNotEmpty(ticketMainIssueBean.getEndTimeEnd())) {
                sql.append(" and m.end_time<?");
                paramList.add(ticketMainIssueBean.getEndTimeEnd());
            }
        }
        List<Map<String, Object>> dataList = jdbcTemplate.queryForList(sql.toString(), paramList.toArray());
        if (Utils.isNotEmpty(dataList)) {
            return dataList.get(0);
        }
        return null;
    }

    @Override
    public Ticket getTicketByTicketId(String ticketId) {
        String sql = "From Ticket t where ticketId=?";
        List<Ticket> ticketList = find(sql, new Object[]{ticketId});
        if (ticketList.size() > 0) {
            return ticketList.get(0);
        }
        return null;
    }
    
    @Override
    public Ticket getTicketById(long id) {
    	String sql = "From Ticket t where id=?";
        List<Ticket> ticketList = find(sql, new Object[]{id});
        if (ticketList.size() > 0) {
            return ticketList.get(0);
        }
        return null;
    }

    @Override
    public Ticket getTicketByBackup1(String backup1, String postCode) {
        String sql = "From Ticket t where backup1=? and postCode=?";
        List<Ticket> ticketList = find(sql, new Object[]{backup1, postCode});
        if (ticketList.size() > 0) {
            return ticketList.get(0);
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> countBonusAmount(String lotteryCode, String issue, String sn) {
        if (Utils.isNotEmpty(sn)) {
            String sql = "select SUM(t.bonus_amount) as total,t.user_code as userCode,t.sid from tms_ticket t where t.lottery_code=? and t.end_game_id=? and ticket_status = ? and bonus_status = ? group by t.user_code,t.sid";
            List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql, new Object[]{lotteryCode, issue + sn, Constants.TICKET_STATUS_SUCCESS, Constants.BONUS_STATUS_YES});
            if (null == mapList) {
                mapList = new ArrayList<Map<String, Object>>();
            }
            return mapList;
        } else {
            String sql = "select SUM(t.bonus_amount) as total,t.user_code as userCode,t.sid from tms_ticket t where t.lottery_code=? and t.issue=? and ticket_status = ? and bonus_status = ? group by t.user_code,t.sid";
            List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql, new Object[]{lotteryCode, issue, Constants.TICKET_STATUS_SUCCESS, Constants.BONUS_STATUS_YES});
            if (null == mapList) {
                mapList = new ArrayList<Map<String, Object>>();
            }
            return mapList;
        }
    }

    @Override
    public Map getTicketCount(Ticket ticket, Member member) {
        return null;
    }

    @Override
    public List<Ticket> getTicketByOrderId(String orderId, String sid, Integer ticketStatus) {
        String sql = "from Ticket where orderId = ? and sid = ? and ticketStatus= ? ";
        return find(sql, new Object[]{orderId, sid, ticketStatus});
    }

    @Override
    public Ticket getTicketBySaleCode(String lotteryCode, String issue, String saleCode, String postCode) {
        String sql = "from Ticket where lotteryCode = ? and issue = ? and saleCode= ? and postCode=?";
        List<Ticket> ticketlist = find(sql, new Object[]{lotteryCode, issue, saleCode, postCode});
        if (Utils.isNotEmpty(ticketlist)) {
            return ticketlist.get(0);
        }
        return null;
    }

    @Override
    public List<Ticket> getTicketByOrderId(String orderId, String sid) {
        String sql = "from Ticket where orderId = ? and sid = ?";
        return find(sql, new Object[]{orderId, sid});
    }

    @Override
    public List<Ticket> getTicketByOutTicketId(List<String> ticketIdList, String sid) {
        StringBuffer sql = new StringBuffer("from Ticket where sid = ? and ( ");
        List<Object> param = new ArrayList<Object>();
        param.add(sid);
        StringBuffer buffer = new StringBuffer();
        for (String ticketId : ticketIdList) {
            buffer.append("or ").append("outTicketId = ? ");
            param.add(ticketId);
        }
        sql.append(buffer.substring(2)).append(")");
        return find(sql.toString(), param.toArray());
    }

    @Override
    public int updateTicketStatusForOrderId(Integer ticketStatus, String ticketId, Integer oldTicketStatus, String postCode) {
        String sql = "update tms_ticket t set t.ticket_status=?,t.send_time = ?,t.post_code=? where t.ticket_id=? and t.ticket_status=?";
        return jdbcTemplate.update(sql, new Object[]{ticketStatus, new Date(), postCode, ticketId, oldTicketStatus});
    }

    @Override
    public int updateTicketStatusForOrderIdForNoSend(Integer ticketStatus, String ticketId, Integer oldTicketStatus, String postCode) {
        String sql = "update tms_ticket t set t.ticket_status=?,t.send_time = ?,t.post_code=?,t.return_time=? where t.ticket_id=? and t.ticket_status=?";
        return jdbcTemplate.update(sql, new Object[]{ticketStatus, new Date(), postCode, new Date(), ticketId, oldTicketStatus});
    }

    @Override
    public int updateForSubIssueUpdate(String programsOrderId, String sn, Date date) {
        return 0;
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
        StringBuffer sql = new StringBuffer("From Ticket t where  t.bonusStatus = " + Constants.BONUS_STATUS_YES + " and t.ticketStatus = " + Constants.TICKET_STATUS_SUCCESS);
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
     * 查询发送中的票
     *
     * @param lotteryCode
     * @param postCode
     * @param page
     * @param pageSize
     * @return
     */
    public PageBean getSendedTicket(String lotteryCode, String postCode, int page, int pageSize) {
        StringBuffer sql = new StringBuffer("From Ticket t where t.ticketStatus = " + Constants.TICKET_STATUS_SENDING);
        List<HibernatePara> hibernateParas = new ArrayList<HibernatePara>();
        sql.append(" and t.createTime >= sysdate - 2 ");//接口请求，加上时间段；
        sql.append(" and t.lotteryCode=? ");
        hibernateParas.add(new HibernatePara(lotteryCode));
        sql.append(" and t.postCode=? ");
        hibernateParas.add(new HibernatePara(postCode));
        sql.append(" order by t.id ");
        return getPageBeanByPara(sql.toString(), hibernateParas, page, pageSize);
    }
    
    /**
     * 查询发送中的票(发送时间和创建时间限定向前推迟)
     *
     * @param lotteryCode
     * @param postCode
     * @param page
     * @param pageSize
     * @return
     */
    public PageBean getSendedDelayTicket(String lotteryCode, String postCode,Date sendTime,Date createTime, int page, int pageSize) {
        StringBuffer sql = new StringBuffer("From Ticket t where t.ticketStatus = " + Constants.TICKET_STATUS_SENDING);
        List<HibernatePara> hibernateParas = new ArrayList<HibernatePara>();
        sql.append(" and t.createTime >= sysdate - 2 ");//接口请求，加上时间段；
        sql.append(" and t.lotteryCode=? ");
        hibernateParas.add(new HibernatePara(lotteryCode));
        sql.append(" and t.postCode=? ");
        hibernateParas.add(new HibernatePara(postCode));
        sql.append(" and t.createTime>=? ");
        hibernateParas.add(new HibernatePara(createTime));
        sql.append(" and t.sendTime<=? ");
        hibernateParas.add(new HibernatePara(sendTime));
        sql.append(" order by t.id ");
        return getPageBeanByPara(sql.toString(), hibernateParas, page, pageSize);
    }
    
    @Override
    public int updateTicketForWait(String ticketId) {
        String sql = "update tms_ticket set ticket_status = ? where ticket_id = ? and ticket_status <> 3";
        return jdbcTemplate.update(sql, new Object[]{Constants.TICKET_STATUS_WAIT, ticketId});
    }

    /**
     * 查询发送中的票
     *
     * @param lotteryCode
     * @param postCode
     * @param page
     * @param pageSize
     * @param desc
     * @return
     */
    @Override
    public PageBean getSendedTicket(String lotteryCode, String postCode, int page, int pageSize, String desc) {
        StringBuffer sql = new StringBuffer("From Ticket t where t.ticketStatus = " + Constants.TICKET_STATUS_SENDING);
        List<HibernatePara> hibernateParas = new ArrayList<HibernatePara>();
        sql.append(" and t.createTime >= sysdate - 2 ");//接口请求，加上时间段；
        sql.append(" and t.lotteryCode=? ");
        hibernateParas.add(new HibernatePara(lotteryCode));
        sql.append(" and t.postCode=? ");
        hibernateParas.add(new HibernatePara(postCode));
        sql.append(" order by t.id ");
        sql.append(desc + " ");
        return getPageBeanByPara(sql.toString(), hibernateParas, page, pageSize);
    }

    @Override
    public PageBean getSendedTicketOrderByIssue(String lotteryCode, String postCode, int page, int pageSize) {
        StringBuffer sql = new StringBuffer("From Ticket t where t.ticketStatus = " + Constants.TICKET_STATUS_SENDING);
        List<HibernatePara> hibernateParas = new ArrayList<HibernatePara>();
        sql.append(" and t.createTime >= sysdate - 2 ");//接口请求，加上时间段；
        sql.append(" and t.lotteryCode=? ");
        hibernateParas.add(new HibernatePara(lotteryCode));
        sql.append(" and t.postCode=? ");
        hibernateParas.add(new HibernatePara(postCode));
        sql.append(" order by t.issue, t.id ");
        return getPageBeanByPara(sql.toString(), hibernateParas, page, pageSize);
    }

    @Override
    public PageBean getSendedTicketEXEX(String lotteryCode, String postCode, String issue, int page, int pageSize) {
        StringBuffer sql = new StringBuffer("From Ticket t where t.ticketStatus = " + Constants.TICKET_STATUS_SENDING);
        List<HibernatePara> hibernateParas = new ArrayList<HibernatePara>();
        sql.append(" and t.lotteryCode=? ");
        hibernateParas.add(new HibernatePara(lotteryCode));
        sql.append(" and t.postCode=? ");
        hibernateParas.add(new HibernatePara(postCode));
        sql.append(" and t.issue=? ");
        hibernateParas.add(new HibernatePara(issue));
        sql.append(" order by t.id ");
        return getPageBeanByPara(sql.toString(), hibernateParas, page, pageSize);
    }

    /**
     * @param lotteryCode
     * @param postCode
     * @param sendTime
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public PageBean getSendedTicketEx(String lotteryCode, String postCode, Date sendTime, int page, int pageSize) {
        StringBuffer sql = new StringBuffer("From Ticket t where t.ticketStatus = " + Constants.TICKET_STATUS_SENDING);
        List<HibernatePara> hibernateParas = new ArrayList<HibernatePara>();
        sql.append(" and t.lotteryCode=? ");
        hibernateParas.add(new HibernatePara(lotteryCode));
        sql.append(" and t.postCode=? ");
        hibernateParas.add(new HibernatePara(postCode));
        sql.append(" and t.sendTime<=? ");
        hibernateParas.add(new HibernatePara(sendTime));
        sql.append(" order by t.id ");
        return getPageBeanByPara(sql.toString(), hibernateParas, page, pageSize);
    }

    /**
     * 查询发送中的票
     *
     * @param lotteryCode
     * @param postCode
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public PageBean getSendedTicket(String lotteryCode, String playCode, String pollCode, String postCode, int page, int pageSize) {
        StringBuffer sql = new StringBuffer("From Ticket t where t.ticketStatus = " + Constants.TICKET_STATUS_SENDING);
        List<HibernatePara> hibernateParas = new ArrayList<HibernatePara>();
        sql.append(" and t.lotteryCode=? ");
        hibernateParas.add(new HibernatePara(lotteryCode));
        sql.append(" and t.playCode=? ");
        hibernateParas.add(new HibernatePara(playCode));
        sql.append(" and t.postCode=? ");
        hibernateParas.add(new HibernatePara(postCode));
        sql.append(" and t.pollCode=? ");
        hibernateParas.add(new HibernatePara(pollCode));
        sql.append(" order by t.id ");
        return getPageBeanByPara(sql.toString(), hibernateParas, page, pageSize);
    }

    /**
     * 查询发送中的票
     *
     * @param
     * @param postCode
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public PageBean getSendedTicket(String postCode, int page, int pageSize) {
        StringBuffer sql = new StringBuffer("From Ticket t where t.ticketStatus = " + Constants.TICKET_STATUS_SENDING);
        List<HibernatePara> hibernateParas = new ArrayList<HibernatePara>();
        sql.append(" and t.postCode=? ");
        hibernateParas.add(new HibernatePara(postCode));
        return getPageBeanByPara(sql.toString(), hibernateParas, page, pageSize);
    }

    @Override
    public int doNoBonus(String lotteryCode, String issue) {
        String sql = "update tms_ticket set bonus_status = ?,bonus_time = ? where lottery_code = ? and issue = ? and ticket_status = ? and bonus_status = ? ";
        return jdbcTemplate.update(sql, new Object[]{Constants.BONUS_STATUS_NO, new Date(), lotteryCode, issue, Constants.TICKET_STATUS_SUCCESS, Constants.BONUS_STATUS_WAIT});
    }

    @Override
    public int doNoBonus(String lotteryCode, String issue, String sn) {
        String sql = "update tms_ticket set bonus_status = ?,bonus_time = ? where lottery_code = ? and issue = ? and ticket_status = ? and bonus_status = ? and end_game_id = ? ";
        return jdbcTemplate.update(sql, new Object[]{Constants.BONUS_STATUS_NO, new Date(), lotteryCode, issue, Constants.TICKET_STATUS_SUCCESS, Constants.BONUS_STATUS_WAIT, sn});
    }

    @Override
    public int updateTicketForDuiJiang(Ticket ticket) {
        String sql = "update tms_ticket set dui_jiang_status = ?,dui_jiang_time = ? where ticket_id = ? ";
        return jdbcTemplate.update(sql, new Object[]{ticket.getDuiJiangStatus(), new Date(), ticket.getTicketId()});
    }

    @Override
    public List<Ticket> getNoDuiJiangTicket(String lotteryCode, String postCode) {
        StringBuffer sql = new StringBuffer("select new Ticket(ticketId,lotteryCode,issue,bonusAmount,returnTime) from Ticket where duiJiangStatus = ? ");
        List<HibernatePara> hibernateParas = new ArrayList<HibernatePara>();
        hibernateParas.add(new HibernatePara(Constants.DUI_JIANG_STATUS_NO));
        sql.append(" and t.lotteryCode=? ");
        hibernateParas.add(new HibernatePara(lotteryCode));

        sql.append(" and t.postCode=? ");
        hibernateParas.add(new HibernatePara(postCode));
        return findList(sql.toString(), hibernateParas);
    }

    @Override
    public int updateSaleInfo(String ticketId, String saleInfo) {
        String sql = "update tms_ticket set sale_info = ? where ticket_id = ? ";
        return jdbcTemplate.update(sql, new Object[]{saleInfo, ticketId});
    }

    @Override
    public PageBean getPageBeanByTicket(TicketQueryBean ticketQueryBean, Integer page, Integer pageSize) {
        String xs = "select ll.issue issue,ll.lottery_code lotteryCode, sum(ll.bonus_amount) tAmount, sum(ll.fix_bonus_amount) tFixAmount from ";
        StringBuffer sql = new StringBuffer(xs);
        sql.append("tms_ticket ll where 1=1 ");
        StringBuffer where = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();
        if (Utils.isNotEmpty(ticketQueryBean)) {
            Ticket ticket = ticketQueryBean.getTicket();
            if (Utils.isNotEmpty(ticket)) {
                if (Utils.isNotEmpty(ticket.getLotteryCode())) {
                    where.append(" and ll.lottery_code = ? ");
                    paramList.add(ticket.getLotteryCode());
                }
                if (Utils.isNotEmpty(ticket.getPostCode())) {
                    where.append(" and ll.post_code = ? ");
                    paramList.add(ticket.getPostCode());
                }
            }
            if (Utils.isNotEmpty(ticketQueryBean.getIssueStart())) {
                where.append(" and ll.issue >= ? ");
                paramList.add(ticketQueryBean.getIssueStart());
            }
            if (Utils.isNotEmpty(ticketQueryBean.getIssueEnd())) {
                where.append(" and ll.issue <= ? ");
                paramList.add(ticketQueryBean.getIssueEnd());
            }
            int len = paramList.size();
            for (int i = 0; i < len; i++) {
                paramList.add(paramList.get(i));
            }

        }
        String selectStr = "select aa.issue issue,bb.issue bissue, aa.lotteryCode lotteryCode, bb.lotteryCode blotteryCode, aa.tAmount atAmount, aa.tFixAmount  atfAmont,bb.tAmount btAmount, bb.tFixAmount  btfAmount from(";
        String groupBy = "group by ll.issue, ll.lottery_code";
        sql.append(where);
        sql.append(groupBy);
        sql.append(")aa left join (");
        sql.append(xs);
        sql.append("tms_bonus_log ll where 1=1 ");
        sql.append(where);
        sql.append(groupBy);
        sql.append(")bb on aa.issue = bb.issue and aa.lotteryCode = bb.lotteryCode where aa.tFixAmount not in 0 and bb.tFixAmount is not null ");
        String str = "SELECT COUNT(*) from (" + selectStr + sql.toString() + ")";
        logger.info(str);
        int count = jdbcTemplate.queryForInt(str, paramList.toArray());
        int pageTotal = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;

        PageBean pageBean = new PageBean();
        List resultList = new ArrayList();
        if (page <= pageTotal) {
            StringBuffer select = new StringBuffer(selectStr);
            select.append(sql);
            select.append("order by aa.lotteryCode, aa.issue");
            resultList = jdbcTemplate.queryForList(JdbcPageUtil.getOraclePageSQL(select.toString(), page, pageSize), paramList.toArray());
        }
        pageBean.setPageId(page);
        pageBean.setPageTotal(pageTotal);
        pageBean.setItemTotal(new Long(count));
        pageBean.setPageContent(resultList);
        return pageBean;
    }

    @Override
    public PageBean getIssueLotteryCount(TicketMainIssueBean ticketMainIssueBean, Integer page, Integer pageSize) {
        List<Object> paramList = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("from tms_ticket t left join tms_main_issue m on t.lottery_code = m.lottery_code and t.issue = m.name where 1=1");
        if (Utils.isNotEmpty(ticketMainIssueBean)) {
            Ticket ticket = ticketMainIssueBean.getTicket();
            MainIssue mainIssue = ticketMainIssueBean.getMainIssue();
            if (Utils.isNotEmpty(ticket)) {
                if (Utils.isNotEmpty(ticket.getSid())) {
                    sql.append(" and t.sid=?");
                    paramList.add(ticket.getSid());
                }
                if (Utils.isNotEmpty(ticket.getTicketStatus())) {
                    sql.append(" and t.ticket_status=?");
                    paramList.add(ticket.getTicketStatus());
                }
                if (Utils.isNotEmpty(ticket.getLotteryCode())) {
                    sql.append(" and t.lottery_code=?");
                    paramList.add(ticket.getLotteryCode());
                }
            }
            if (Utils.isNotEmpty(mainIssue)) {
                if (Utils.isNotEmpty(mainIssue.getStatus())) {
                    sql.append(" and m.status=?");
                    paramList.add(mainIssue.getStatus());
                }
            }
            if (Utils.isNotEmpty(ticketMainIssueBean.getEndTimeStart())) {
                sql.append(" and m.end_time>=?");
                paramList.add(ticketMainIssueBean.getEndTimeStart());
            }
            if (Utils.isNotEmpty(ticketMainIssueBean.getEndTimeEnd())) {
                sql.append(" and m.end_time<?");
                paramList.add(ticketMainIssueBean.getEndTimeEnd());
            }
        }
        sql.append(" group by t.lottery_code,t.issue ");
        sql.append(" order by t.lottery_code,t.issue ");

        logger.info("SELECT COUNT(*) " + sql.toString());
        int count = jdbcTemplate.queryForInt("SELECT COUNT(*) from（SELECT COUNT(*) " + sql.toString() + ")", paramList.toArray());
        int pageTotal = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;

        PageBean pageBean = new PageBean();
        List resultList = new ArrayList();
        if (page <= pageTotal) {
            StringBuffer select = new StringBuffer("select t.lottery_code lotteryCode,t.issue issue,sum(t.amount) amount,sum(t.fix_bonus_amount) fixBonusAmount,sum(t.bonus_amount) bonusAmonnt ");
            select.append(sql);
            resultList = jdbcTemplate.queryForList(JdbcPageUtil.getOraclePageSQL(select.toString(), page, pageSize), paramList.toArray());
        }
        pageBean.setPageId(page);
        pageBean.setPageTotal(pageTotal);
        pageBean.setItemTotal(new Long(count));
        pageBean.setPageContent(resultList);
        return pageBean;
    }

    @Override
    public int updateSendingTicketToSuccess(String lotteryCode, String issue) {
        String sql = "update tms_ticket set ticket_status = ?,return_time = ?,err_code = ? where lottery_code = ? and issue = ? and ticket_status = ? ";
        return jdbcTemplate.update(sql, new Object[]{Constants.TICKET_STATUS_SUCCESS, new Date(), Constants.TICKET_ERROR_CODE, lotteryCode, issue, Constants.TICKET_STATUS_SENDING});
    }

    @Override
    public int updateBonusAmount(BonusLog bonusLog) {
        String sql = "update tms_ticket t set t.bonus_status=?,t.bonus_amount=?,t.bonus_time=?,t.fix_bonus_amount=?,t.big_bonus=?,t.bonus_class=? where t.ticket_id=?";
        int a = jdbcTemplate.update(
                sql,
                new Object[]{Constants.BONUS_STATUS_YES, bonusLog.getBonusAmount(), new Date(), bonusLog.getFixBonusAmount(), bonusLog.getBigBonus(), bonusLog.getBonusClass(),
                        bonusLog.getTicketId()});
        return a;
    }

    @Override
    public int updateForSubIssueUpdate(Ticket ticket) {
        String sql = "update tms_ticket t set t.start_game_id=?,t.game_start_time=?,t.end_game_id=?,t.game_end_time=? where t.ticket_id=?";
        return jdbcTemplate.update(sql, new Object[]{ticket.getStartGameId(), ticket.getGameStartTime(), ticket.getEndGameId(), ticket.getGameEndTime(), ticket.getTicketId()});
    }

    @Override
    public Ticket getTicketByTikcetId(String ticketId) {
        String sql = "from Ticket where ticketId= ?";
        List<Ticket> ticketList = find(sql, new Object[]{ticketId});
        if (Utils.isNotEmpty(ticketList)) {
            return ticketList.get(0);
        }
        return null;
    }

    @Override
    public List<Ticket> getTicketsForEndIssue(String lotteryCode, String issue) {
        String sql = "select new Ticket(ticketId) From Ticket where lotteryCode=? and issue=? and (ticketStatus = ? or ticketStatus =? or ticketStatus =? or ticketStatus=?)";
        return find(sql, new Object[]{lotteryCode, issue, Constants.TICKET_STATUS_SENDING, Constants.TICKET_STATUS_WAIT, Constants.TICKET_STATUS_RESEND, Constants.TICKET_STATUS_DOING});
    }

    @Override
    public List<Ticket> getTicketsForEndIssue() {
        String sql = "select new Ticket(ticketId) From Ticket where gameStartTime<? and ticketStatus<?";
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 1);
        return find(sql, new Object[]{calendar.getTime(), Constants.TICKET_STATUS_SENDING});
    }

    @Override
    public int updateSpInfo(String ticketId, String spInfo) {
        String sql = "update tms_ticket t set t.sale_info=? where t.ticket_id=?";
        return jdbcTemplate.update(sql, new Object[]{spInfo, ticketId});
    }

    @Override
    public List<Map<String, Object>> getSendTicket(String lotteryCode, String issue) {
        String sql = "select * from (select N.*,ROWNUM as R from (select distinct t.order_id as orderId,t.lottery_code as lotteryCode,t.sid from tms_ticket t where t.lottery_code=? and t.issue=? and t.ticket_status=?) N where rownum <=50) where R>0";
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql, new Object[]{lotteryCode, issue, Constants.TICKET_STATUS_WAIT});
        if (null == mapList) {
            mapList = new ArrayList<Map<String, Object>>();
        }
        return mapList;
    }

    @Override
    public List<Map<String, Object>> getSendTicket(String lotteryCode) {
        String sql = "select * from (select N.*,ROWNUM as R from (select distinct t.order_id as orderId,t.lottery_code as lotteryCode,t.sid from tms_ticket t where t.lottery_code=? and t.game_start_time>? and t.ticket_status=?) N where rownum <=50) where R>0";
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 1);
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql, new Object[]{lotteryCode, calendar.getTime(), Constants.TICKET_STATUS_WAIT});
        if (null == mapList) {
            mapList = new ArrayList<Map<String, Object>>();
        }
        return mapList;
    }

    @Override
    public PageBean getTicketForBonus(String lotteryCode, String issue, String sn, int pageSize, int page) {
        StringBuilder sql = new StringBuilder("From Ticket where 1=1 ");
        List<HibernatePara> hibernateParas = new ArrayList<HibernatePara>();
        if (Utils.isNotEmpty(lotteryCode)) {
            sql.append(" and lotteryCode=?");
            hibernateParas.add(new HibernatePara(lotteryCode));
        }
        if (Utils.isNotEmpty(issue)) {
            sql.append(" and issue=?");
            hibernateParas.add(new HibernatePara(issue));
        }
        if (Utils.isNotEmpty(sn)) {
            sql.append(" and endGameId=?");
            hibernateParas.add(new HibernatePara(sn));
        }
        sql.append(" and ticketStatus=?");
        sql.append(" and bonusStatus=?");
        hibernateParas.add(new HibernatePara(Constants.TICKET_STATUS_SUCCESS));
        hibernateParas.add(new HibernatePara(Constants.BONUS_STATUS_YES));
        return getPageBeanByPara(sql.toString(), hibernateParas, page, pageSize);
    }

    /**
     * 统计日出票详细
     *
     * @param cond
     * @return
     */
    public List<Map<String, Object>> getTicketCountByDay(Map<String, Object> cond) {
        String lotteryCode = (String) cond.get("lotteryCode");
        String postCode = (String) cond.get("postCode");
        String startTime = (String) cond.get("startTime");
        String endTime = (String) cond.get("endTime");
        Integer start = (Integer) cond.get("start");
        Integer end = (Integer) cond.get("end");

        StringBuffer sql = new StringBuffer();
        sql.append("select * from (select f.*, rownum rn from (select a.day,a.post_code,b.fa,b.fnum,c.sa,c.snum,d.wa,d.wnum,e.ba,e.fba,e.bnum from ");
        sql.append("(select to_char(t.create_time,'yyyy-mm-dd') day,t.post_code,sum(t.amount) aa from tms_ticket t ");
        sql.append("where 1=1 ");
        if (Utils.isNotEmpty(lotteryCode)) {
            sql.append("and t.lottery_code='" + lotteryCode + "' ");
        }
        if (Utils.isNotEmpty(postCode)) {
            sql.append("and t.post_code='" + postCode + "' ");
        }
        if (Utils.isNotEmpty(startTime)) {
            sql.append("and t.create_time >= to_date('" + startTime + "','yyyy-mm-dd hh24:mi:ss') ");
        }
        if (Utils.isNotEmpty(endTime)) {
            sql.append("and t.create_time <= to_date('" + endTime + "','yyyy-mm-dd hh24:mi:ss') ");
        }
        sql.append("group by to_char(t.create_time,'yyyy-mm-dd'),t.post_code) a ");

        sql.append("left join ");
        sql.append("(select to_char(t.create_time,'yyyy-mm-dd') day,t.post_code,sum(t.amount) fa,count(t.ticket_id) fnum from tms_ticket t ");
        sql.append("where t.ticket_status = " + Constants.TICKET_STATUS_FAILURE + " ");
        if (Utils.isNotEmpty(lotteryCode)) {
            sql.append("and t.lottery_code='" + lotteryCode + "' ");
        }
        if (Utils.isNotEmpty(postCode)) {
            sql.append("and t.post_code='" + postCode + "' ");
        }
        if (Utils.isNotEmpty(startTime)) {
            sql.append("and t.create_time >= to_date('" + startTime + "','yyyy-mm-dd hh24:mi:ss') ");
        }
        if (Utils.isNotEmpty(endTime)) {
            sql.append("and t.create_time <= to_date('" + endTime + "','yyyy-mm-dd hh24:mi:ss') ");
        }
        sql.append("group by to_char(t.create_time,'yyyy-mm-dd'),t.post_code) b ");
        sql.append("on a.day = b.day and a.post_code = b.post_code ");

        sql.append("left join ");
        sql.append("(select to_char(t.create_time,'yyyy-mm-dd') day,t.post_code,sum(t.amount) sa,count(t.ticket_id) snum from tms_ticket t ");
        sql.append("where t.ticket_status = " + Constants.TICKET_STATUS_SUCCESS + " ");
        if (Utils.isNotEmpty(lotteryCode)) {
            sql.append("and t.lottery_code='" + lotteryCode + "' ");
        }
        if (Utils.isNotEmpty(postCode)) {
            sql.append("and t.post_code='" + postCode + "' ");
        }
        if (Utils.isNotEmpty(startTime)) {
            sql.append("and t.create_time >= to_date('" + startTime + "','yyyy-mm-dd hh24:mi:ss') ");
        }
        if (Utils.isNotEmpty(endTime)) {
            sql.append("and t.create_time <= to_date('" + endTime + "','yyyy-mm-dd hh24:mi:ss') ");
        }
        sql.append("group by to_char(t.create_time,'yyyy-mm-dd'),t.post_code) c ");
        sql.append("on a.day = c.day and a.post_code = c.post_code ");

        sql.append("left join ");
        sql.append("(select to_char(t.create_time,'yyyy-mm-dd') day,t.post_code,sum(t.amount) wa,count(t.ticket_id) wnum from tms_ticket t ");
        sql.append("where (t.ticket_status = " + Constants.TICKET_STATUS_SENDING + " or t.ticket_status = " + Constants.TICKET_STATUS_WAIT + ") ");
        if (Utils.isNotEmpty(lotteryCode)) {
            sql.append("and t.lottery_code='" + lotteryCode + "' ");
        }
        if (Utils.isNotEmpty(postCode)) {
            sql.append("and t.post_code='" + postCode + "' ");
        }
        if (Utils.isNotEmpty(startTime)) {
            sql.append("and t.create_time >= to_date('" + startTime + "','yyyy-mm-dd hh24:mi:ss') ");
        }
        if (Utils.isNotEmpty(endTime)) {
            sql.append("and t.create_time <= to_date('" + endTime + "','yyyy-mm-dd hh24:mi:ss') ");
        }
        sql.append("group by to_char(t.create_time,'yyyy-mm-dd'),t.post_code) d ");
        sql.append("on a.day = d.day and a.post_code = d.post_code ");

        sql.append("left join ");
        sql.append("(select to_char(t.create_time,'yyyy-mm-dd') day,t.post_code, sum(t.bonus_amount) ba,sum(t.fix_bonus_amount) fba,count(t.ticket_id) bnum from tms_ticket t ");
        sql.append("where t.bonus_status =" + Constants.BONUS_STATUS_YES + " and t.ticket_status =" + Constants.TICKET_STATUS_SUCCESS + " ");
        if (Utils.isNotEmpty(lotteryCode)) {
            sql.append("and t.lottery_code='" + lotteryCode + "' ");
        }
        if (Utils.isNotEmpty(postCode)) {
            sql.append("and t.post_code='" + postCode + "' ");
        }
        if (Utils.isNotEmpty(startTime)) {
            sql.append("and t.create_time >= to_date('" + startTime + "','yyyy-mm-dd hh24:mi:ss') ");
        }
        if (Utils.isNotEmpty(endTime)) {
            sql.append("and t.create_time <= to_date('" + endTime + "','yyyy-mm-dd hh24:mi:ss') ");
        }
        sql.append("group by to_char(t.create_time,'yyyy-mm-dd'),t.post_code) e ");
        sql.append("on a.day = e.day and a.post_code = e.post_code  order by a.day desc,a.post_code) f ");
        sql.append("where rownum <= " + end + ") where rn > " + start);

        List<Map<String, Object>> dataList = jdbcTemplate.queryForList(sql.toString());

        if (dataList != null) {
            for (Map<String, Object> map : dataList) {
                String postCodestr = (String) map.get("POST_CODE");
                String postName = "未分配出票口";
                try {
                    postName = PostMap.getPost(postCodestr).getName();
                } catch (Exception e) {

                }
                map.put("POST_NAME", postName);
            }
        }

        return dataList;
    }

    /**
     * 统计日出票详细
     *
     * @param cond
     * @return
     */
    public int getTicketCountByDayCount(Map<String, Object> cond) {
        String lotteryCode = (String) cond.get("lotteryCode");
        String postCode = (String) cond.get("postCode");
        String startTime = (String) cond.get("startTime");
        String endTime = (String) cond.get("endTime");

        StringBuffer sql = new StringBuffer();
        sql.append("select count(*)  from ");
        // 每日汇总
        sql.append("(select to_char(t.create_time,'yyyy-mm-dd') day,t.post_code,sum(t.amount) aa from tms_ticket t ");
        sql.append("where 1=1 ");
        if (Utils.isNotEmpty(lotteryCode)) {
            sql.append("and t.lottery_code='" + lotteryCode + "' ");
        }
        if (Utils.isNotEmpty(postCode)) {
            sql.append("and t.post_code='" + postCode + "' ");
        }
        if (Utils.isNotEmpty(startTime)) {
            sql.append("and t.create_time >= to_date('" + startTime + "','yyyy-mm-dd hh24:mi:ss') ");
        }
        if (Utils.isNotEmpty(endTime)) {
            sql.append("and t.create_time <= to_date('" + endTime + "','yyyy-mm-dd hh24:mi:ss') ");
        }
        sql.append("group by to_char(t.create_time,'yyyy-mm-dd'),t.post_code) a ");
        // 每日失败
        sql.append("left join ");
        sql.append("(select to_char(t.create_time,'yyyy-mm-dd') day,t.post_code,sum(t.amount) fa from tms_ticket t ");
        sql.append("where t.ticket_status = " + Constants.TICKET_STATUS_FAILURE + " ");
        if (Utils.isNotEmpty(lotteryCode)) {
            sql.append("and t.lottery_code='" + lotteryCode + "' ");
        }
        if (Utils.isNotEmpty(postCode)) {
            sql.append("and t.post_code='" + postCode + "' ");
        }
        if (Utils.isNotEmpty(startTime)) {
            sql.append("and t.create_time >= to_date('" + startTime + "','yyyy-mm-dd hh24:mi:ss') ");
        }
        if (Utils.isNotEmpty(endTime)) {
            sql.append("and t.create_time <= to_date('" + endTime + "','yyyy-mm-dd hh24:mi:ss') ");
        }
        sql.append("group by to_char(t.create_time,'yyyy-mm-dd'),t.post_code) b ");
        sql.append("on a.day = b.day and a.post_code = b.post_code ");
        // 每日成功
        sql.append("left join ");
        sql.append("(select to_char(t.create_time,'yyyy-mm-dd') day,t.post_code,sum(t.amount) sa from tms_ticket t ");
        sql.append("where t.ticket_status = " + Constants.TICKET_STATUS_SUCCESS + " ");
        if (Utils.isNotEmpty(lotteryCode)) {
            sql.append("and t.lottery_code='" + lotteryCode + "' ");
        }
        if (Utils.isNotEmpty(postCode)) {
            sql.append("and t.post_code='" + postCode + "' ");
        }
        if (Utils.isNotEmpty(startTime)) {
            sql.append("and t.create_time >= to_date('" + startTime + "','yyyy-mm-dd hh24:mi:ss') ");
        }
        if (Utils.isNotEmpty(endTime)) {
            sql.append("and t.create_time <= to_date('" + endTime + "','yyyy-mm-dd hh24:mi:ss') ");
        }
        sql.append("group by to_char(t.create_time,'yyyy-mm-dd'),t.post_code) c ");
        sql.append("on a.day = c.day and a.post_code = c.post_code ");

        // 每日待发送
        sql.append("left join ");
        sql.append("(select to_char(t.create_time,'yyyy-mm-dd') day,t.post_code,sum(t.amount) wa from tms_ticket t ");
        sql.append("where (t.ticket_status = " + Constants.TICKET_STATUS_SENDING + " or t.ticket_status = " + Constants.TICKET_STATUS_WAIT + ") ");
        if (Utils.isNotEmpty(lotteryCode)) {
            sql.append("and t.lottery_code='" + lotteryCode + "' ");
        }
        if (Utils.isNotEmpty(postCode)) {
            sql.append("and t.post_code='" + postCode + "' ");
        }
        if (Utils.isNotEmpty(startTime)) {
            sql.append("and t.create_time >= to_date('" + startTime + "','yyyy-mm-dd hh24:mi:ss') ");
        }
        if (Utils.isNotEmpty(endTime)) {
            sql.append("and t.create_time <= to_date('" + endTime + "','yyyy-mm-dd hh24:mi:ss') ");
        }
        sql.append("group by to_char(t.create_time,'yyyy-mm-dd'),t.post_code) d ");
        sql.append("on a.day = d.day and a.post_code = d.post_code ");

        // 每日返奖
        sql.append("left join ");
        sql.append("(select to_char(t.create_time,'yyyy-mm-dd') day,t.post_code, sum(t.bonus_amount) bonus_amount from tms_ticket t ");
        sql.append("where t.bonus_status =" + Constants.BONUS_STATUS_YES + " and t.ticket_status =" + Constants.TICKET_STATUS_SUCCESS + " ");
        if (Utils.isNotEmpty(lotteryCode)) {
            sql.append("and t.lottery_code='" + lotteryCode + "' ");
        }
        if (Utils.isNotEmpty(postCode)) {
            sql.append("and t.post_code='" + postCode + "' ");
        }
        if (Utils.isNotEmpty(startTime)) {
            sql.append("and t.create_time >= to_date('" + startTime + "','yyyy-mm-dd hh24:mi:ss') ");
        }
        if (Utils.isNotEmpty(endTime)) {
            sql.append("and t.create_time <= to_date('" + endTime + "','yyyy-mm-dd hh24:mi:ss') ");
        }
        sql.append("group by to_char(t.create_time,'yyyy-mm-dd'),t.post_code) e ");
        sql.append("on a.day = e.day and a.post_code = e.post_code ");

        int rs = jdbcTemplate.queryForInt(sql.toString());

        return rs;
    }

    @Override
    public PageBean getPageBeanByParaSending(Ticket ticket, Integer page, Integer pageSize) {
        StringBuffer sql = new StringBuffer("select distinct t.backup1 as message_id from tms_ticket t where t.ticket_status = " + Constants.TICKET_STATUS_SENDING);

        List<Object> paramList = new ArrayList<Object>();
        if (Utils.isNotEmpty(ticket)) {
            if (Utils.isNotEmpty(ticket.getLotteryCode())) {
                sql.append(" and t.lottery_code = ? ");
                paramList.add(ticket.getLotteryCode());
            }
            if (Utils.isNotEmpty(ticket.getIssue())) {
                sql.append(" and t.issue = ? ");
                paramList.add(ticket.getIssue());
            }

            if (Utils.isNotEmpty(ticket.getPostCode())) {
                sql.append(" and t.post_code = ? ");
                paramList.add(ticket.getPostCode());
            }

            if (Utils.isNotEmpty(ticket.getSendTime())) {
                sql.append(" and t.send_time <= ? ");
                paramList.add(ticket.getSendTime());
            }
        }

        // logger.info("SELECT COUNT(*) FROM (" + sql.toString() + ")");
        int count = jdbcTemplate.queryForInt("SELECT COUNT(*) FROM (" + sql.toString() + ")", paramList.toArray());
        int pageTotal = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;

        PageBean pageBean = new PageBean();
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        if (page <= pageTotal) {
            StringBuffer select = new StringBuffer();
            select.append(sql);
            resultList = jdbcTemplate.queryForList(JdbcPageUtil.getOraclePageSQL(select.toString(), page, pageSize), paramList.toArray());
        }
        pageBean.setPageId(page);
        pageBean.setPageTotal(pageTotal);
        pageBean.setItemTotal(new Long(count));
        pageBean.setPageContent(resultList);
        return pageBean;
    }

    @Override
    public List<Map<String, Object>> findNoSendTicket(Integer ticketStatus, String postCode) {
        StringBuffer sql = new StringBuffer();
        sql.append("select t.post_code,t.lottery_code,t.issue,count(t.ticket_id) tts,to_char(min(t.create_time),'yyyy-mm-dd hh24:mi:ss') create_time,to_char(min(t.send_time),'yyyy-mm-dd hh24:mi:ss') send_time from tms_ticket t where 1=1 ");

        List<Object> paramList = new ArrayList<Object>();
        if (ticketStatus != null) {
            sql.append(" and t.ticket_status = ? ");
            paramList.add(ticketStatus);
        }

        if (Utils.isNotEmpty(postCode)) {
            sql.append(" and t.post_code = ? ");
            paramList.add(postCode);
        }
        sql.append("group by t.post_code,t.lottery_code,t.issue ");
        sql.append("order by t.post_code,t.lottery_code,t.issue ");

        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql.toString(), paramList.toArray());
        return resultList;
    }

    @Override
    public PageBean getPageBeanByParaLottery(TicketQueryBean queryBean) {
        StringBuffer sql = new StringBuffer("from tms_ticket t where 1=1 ");
        List<Object> paramList = new ArrayList<Object>();
        if (Utils.isNotEmpty(queryBean)) {
            Ticket ticket = queryBean.getTicket();
            if (Utils.isNotEmpty(ticket)) {
                if (Utils.isNotEmpty(ticket.getLotteryCode())) {
                    sql.append(" and t.lottery_code = ? ");
                    paramList.add(ticket.getLotteryCode());
                }
                if (Utils.isNotEmpty(ticket.getSid())) {
                    sql.append(" and t.sid = ? ");
                    paramList.add(ticket.getSid());
                }
                if (Utils.isNotEmpty(ticket.getPostCode())) {
                    sql.append(" and t.post_code = ? ");
                    paramList.add(ticket.getPostCode());
                }
                if (Utils.isNotEmpty(ticket.getTicketStatus())) {
                    if (ticket.getTicketStatus() == Constants.TICKET_STATUS_FAILURE) {
                        sql.append(" and (t.ticket_status = ? or t.ticket_status = ? ) ");
                        paramList.add(Constants.TICKET_STATUS_FAILURE);
                        paramList.add(Constants.TICKET_STATUS_CANCEL);
                    } else {
                        sql.append(" and t.ticket_status = ? ");
                        paramList.add(ticket.getTicketStatus());
                    }

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
            if (Utils.isNotEmpty(queryBean.getIssueStart())) {
                sql.append(" and t.issue >= ? ");
                paramList.add(queryBean.getIssueStart());
            }
            if (Utils.isNotEmpty(queryBean.getIssueEnd())) {
                sql.append(" and t.issue <= ? ");
                paramList.add(queryBean.getIssueEnd());
            }
        }
        sql.append(" group by t.lottery_code ");

        PageBean pageBean = new PageBean();
        List resultList = new ArrayList();
        StringBuffer select = new StringBuffer("select t.lottery_code as lotteryCode,sum(t.amount) as amount,sum(t.fix_bonus_amount) as fixBonusAmount,sum(t.bonus_amount) as bonusAmount ");
        select.append(sql);
        logger.info(select);
        resultList = jdbcTemplate.queryForList(select.toString(), paramList.toArray());
        pageBean.setPageContent(resultList);
        return pageBean;
    }

    @Override
    public List<Map<String, Object>> findNoSendTicketList(Integer ticketStatus, Date createStartTime, Date createEndTime, String postCode) {
        StringBuffer sql = new StringBuffer();
        sql.append("select t.post_code,t.lottery_code,t.issue,count(t.ticket_id) tts,to_char(min(t.create_time),'yyyy-mm-dd hh24:mi:ss') create_time,to_char(min(t.send_time),'yyyy-mm-dd hh24:mi:ss') send_time from tms_ticket t where 1=1 ");

        List<Object> paramList = new ArrayList<Object>();
        if (ticketStatus != null) {
            sql.append(" and t.ticket_status = ? ");
            paramList.add(ticketStatus);
        }

        if (Utils.isNotEmpty(createStartTime)) {
            sql.append(" and t.create_time >= ? ");
            paramList.add(createStartTime);
        }

        if (Utils.isNotEmpty(createEndTime)) {
            sql.append(" and t.create_time < ? ");
            paramList.add(createEndTime);
        }

        if (Utils.isNotEmpty(postCode)) {
            sql.append(" and t.post_code= ? ");
            paramList.add(postCode);
        }
        sql.append("group by t.post_code,t.lottery_code,t.issue ");

        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql.toString(), paramList.toArray());
        return resultList;
    }

    /**
     * 判断某彩种是否处于停售期间
     * @param lotteryCode
     * @return
     */
    private boolean isStopSale(String lotteryCode){
    	Calendar calendar = Calendar.getInstance();
    	int week = calendar.get(Calendar.DAY_OF_WEEK);
    	int hour = calendar.get(Calendar.HOUR_OF_DAY);
    	if("200".equals(lotteryCode)){
    		if(week >= 2 && week <= 6){
    			if(!(hour >= 9 && hour <= 23)){
    				return true;
    			}
    		}else {
				if(hour >=1 && hour <= 9){
					return true;
				}
			}
    	}
    	
    	if("201".equals(lotteryCode)){
    		if(week == 2 || week == 3 || week == 6) {
    			if(!(hour >= 9 && hour <= 23)){
    				return true;
    			}
    		}
    		if(week == 4 || week == 5) {
    			
    			if(hour == 7){
    				int min = calendar.get(Calendar.MINUTE);
    				if(min < 30){
    					return true;
    				}
    			}else if(!(hour >=8 && hour <= 23)){
					return true;
				}
			}
    		if(week == 7 || week == 1) {
				if(hour >=1 && hour <= 9){
					return true;
				}
			}
    	}
    	return false;
    }
    
    public static void main(String[] args){
    	System.out.println(Utils.getWeekByDate(new Date()));
    	System.out.println(Utils.getWeekNumber(Utils.getWeekByDate(new Date())));
    }

	@Override
    public List<Ticket> getTicketForHandSend(String lotteryCode, String issue, Integer ticketStatus) {
        String sql = "select new Ticket(ticketId) From Ticket where lotteryCode = ? and issue = ? and ticketStatus = ? order by createTime";
        return find(sql, new Object[]{lotteryCode, issue, ticketStatus});
    }

    @Override
    public PageBean getDateOrIssueCount(TicketQueryBean queryBean, String type, Integer page, Integer pageSize) {
        StringBuffer sql = new StringBuffer("from tms_ticket t where 1=1 ");
        List<Object> paramList = new ArrayList<Object>();
        if (Utils.isNotEmpty(queryBean)) {
            Ticket ticket = queryBean.getTicket();
            if (Utils.isNotEmpty(ticket)) {
                if (Utils.isNotEmpty(ticket.getLotteryCode())) {
                    sql.append(" and t.lottery_code = ? ");
                    paramList.add(ticket.getLotteryCode());
                }
                if (Utils.isNotEmpty(ticket.getSid())) {
                    sql.append(" and t.sid = ? ");
                    paramList.add(ticket.getSid());
                }
                if (Utils.isNotEmpty(ticket.getPostCode())) {
                    sql.append(" and t.post_code = ? ");
                    paramList.add(ticket.getPostCode());
                }
                if (Utils.isNotEmpty(ticket.getTicketStatus())) {
                    if (ticket.getTicketStatus() == Constants.TICKET_STATUS_FAILURE) {
                        sql.append(" and (t.ticket_status = ? or t.ticket_status = ? ) ");
                        paramList.add(Constants.TICKET_STATUS_FAILURE);
                        paramList.add(Constants.TICKET_STATUS_CANCEL);
                    } else {
                        sql.append(" and t.ticket_status = ? ");
                        paramList.add(ticket.getTicketStatus());
                    }

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
            if (Utils.isNotEmpty(queryBean.getIssueStart())) {
                sql.append(" and t.issue >= ? ");
                paramList.add(queryBean.getIssueStart());
            }
            if (Utils.isNotEmpty(queryBean.getIssueEnd())) {
                sql.append(" and t.issue <= ? ");
                paramList.add(queryBean.getIssueEnd());
            }
        }
        if ("1".equals(type)) {
            sql.append(" group by to_char(t.send_time,'yyyy-MM-dd') ");
        } else {
            sql.append(" group by t.issue ");
        }

        PageBean pageBean = new PageBean();
        List resultList = new ArrayList();
        StringBuffer select = new StringBuffer("select sum(t.amount) as amount,sum(t.fix_bonus_amount) as fixBonusAmount,sum(t.bonus_amount) as bonusAmount ");
        if ("1".equals(type)) {
            select.append(",to_char(t.send_time,'yyyy-MM-dd') as time ");
        } else {
            select.append(",issue ");
        }
        select.append(sql);
        logger.info(select);
        resultList = jdbcTemplate.queryForList(select.toString(), paramList.toArray());
        pageBean.setPageId(page);
        int count = jdbcTemplate.queryForInt("SELECT COUNT(*) FROM (" + select.toString() + ")", paramList.toArray());
        int pageTotal = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;

        if (page <= pageTotal) {
            StringBuffer select1 = new StringBuffer();
            select1.append(select);
            resultList = jdbcTemplate.queryForList(JdbcPageUtil.getOraclePageSQL(select1.toString(), page, pageSize), paramList.toArray());
        }
        pageBean.setPageTotal(pageTotal);
        pageBean.setItemTotal(new Long(count));
        pageBean.setPageContent(resultList);
        return pageBean;
    }

    @Override
    public List<Map<String, Object>> getDateOrIssueCountByMsg(TicketQueryBean queryBean, String type) {
        StringBuffer sql = new StringBuffer("select sum(t.amount) as orderAmount,sum(t.bonus_amount) as bonusAmount,sum(t.fix_bonus_amount) as fixBonusAmount from tms_ticket t where 1=1 ");
        List<Object> paramList = new ArrayList<Object>();
        if (Utils.isNotEmpty(queryBean)) {
            Ticket ticket = queryBean.getTicket();
            if (Utils.isNotEmpty(ticket)) {
                if (Utils.isNotEmpty(ticket.getLotteryCode())) {
                    sql.append(" and t.lottery_code = ? ");
                    paramList.add(ticket.getLotteryCode());
                }
                if (Utils.isNotEmpty(ticket.getSid())) {
                    sql.append(" and t.sid = ? ");
                    paramList.add(ticket.getSid());
                }
                if (Utils.isNotEmpty(ticket.getPostCode())) {
                    sql.append(" and t.post_code = ? ");
                    paramList.add(ticket.getPostCode());
                }
                if (Utils.isNotEmpty(ticket.getTicketStatus())) {
                    if (ticket.getTicketStatus() == Constants.TICKET_STATUS_FAILURE) {
                        sql.append(" and (t.ticket_status = ? or t.ticket_status = ? ) ");
                        paramList.add(Constants.TICKET_STATUS_FAILURE);
                        paramList.add(Constants.TICKET_STATUS_CANCEL);
                    } else {
                        sql.append(" and t.ticket_status = ? ");
                        paramList.add(ticket.getTicketStatus());
                    }

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
            if (Utils.isNotEmpty(queryBean.getIssueStart())) {
                sql.append(" and t.issue >= ? ");
                paramList.add(queryBean.getIssueStart());
            }
            if (Utils.isNotEmpty(queryBean.getIssueEnd())) {
                sql.append(" and t.issue <= ? ");
                paramList.add(queryBean.getIssueEnd());
            }
        }
        if ("1".equals(type)) {
            sql.append(" group by to_char(t.send_time,'yyyy-MM-dd') ");
        } else {
            sql.append(" group by t.issue ");
        }
        List<Map<String, Object>> dataList = jdbcTemplate.queryForList(sql.toString(), paramList.toArray());
        if (Utils.isNotEmpty(dataList)) {
            return dataList;
        }
        return null;
    }

    public SessionFactory getSessionFactoryTemp() {
        return sessionFactoryTemp;
    }

    public void setSessionFactoryTemp(SessionFactory sessionFactoryTemp) {
        this.sessionFactoryTemp = sessionFactoryTemp;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
