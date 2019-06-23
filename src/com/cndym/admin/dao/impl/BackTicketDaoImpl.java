package com.cndym.admin.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cndym.admin.dao.IBackTicketDao;
import com.cndym.bean.query.TicketQueryBean;
import com.cndym.bean.tms.Ticket;
import com.cndym.constant.Constants;
import com.cndym.control.PostMap;
import com.cndym.dao.impl.GenericDaoImpl;
import com.cndym.utils.JdbcPageUtil;
import com.cndym.utils.Utils;
import com.cndym.utils.hibernate.HibernatePara;
import com.cndym.utils.hibernate.PageBean;

@Repository
public class BackTicketDaoImpl extends GenericDaoImpl<Ticket> implements IBackTicketDao {
    @Resource
    private SessionFactory sessionFactoryTemp;
    @Resource
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void sessionFactoryInit() {
        super.setSessionFactory(sessionFactoryTemp);
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
    public PageBean getPageBeanByPara(TicketQueryBean queryBean, Integer page, Integer pageSize) {
        StringBuffer sql = new StringBuffer(" from tms_ticket t ");
        sql.append(" left join tms_main_issue tmi on t.lottery_code = tmi.lottery_code and t.issue = tmi.name ");
        sql.append(" left join user_member m on m.user_code = t.user_code where 1=1 ");
        List<Object> paramList = new ArrayList<Object>();
        if (Utils.isNotEmpty(queryBean)) {
            Ticket ticket = queryBean.getTicket();
            if (Utils.isNotEmpty(ticket)) {
                if (Utils.isNotEmpty(ticket.getSid())) {
                    sql.append(" and t.sid = ? ");
                    paramList.add(ticket.getSid());
                }
                if (Utils.isNotEmpty(ticket.getUserInfo())) {
                    sql.append(" and m.name like ? ");
                    paramList.add("%" + ticket.getUserInfo() + "%");
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

                if (Utils.isNotEmpty(ticket.getSaleCode())) {
                    sql.append(" and t.sale_code = ? ");
                    paramList.add(ticket.getSaleCode());
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

                if (Utils.isNotEmpty(ticket.getBonusStatus())) {
                    sql.append(" and t.bonus_status = ? ");
                    paramList.add(ticket.getBonusStatus());
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
                sql.append(" and tmi.operators_award = ? ");
                paramList.add(queryBean.getOperatorsAward());
            }
        }

        logger.info("SELECT COUNT(*) " + sql.toString());
        int count = jdbcTemplate.queryForInt("SELECT COUNT(*) " + sql.toString(), paramList.toArray());
        int pageTotal = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;

        PageBean pageBean = new PageBean();
        List resultList = new ArrayList();
        if (page <= pageTotal) {
            StringBuffer select = new StringBuffer("select t.ticket_id as ticketId,t.order_id as orderId,t.out_ticket_id as outTicketId,t.backup1 as backup1,");
            select.append("t.lottery_code as lotteryCode,t.play_code as playCode,t.poll_code as pollCode,t.issue as issue,t.item as item,");
            select.append("t.multiple as multiple,t.sale_code as saleCode,t.post_code as postCode,t.amount as amount,t.ticket_status as ticketStatus,");
            select.append("t.bonus_status as bonusStatus,t.big_bonus as bigBonus,t.bonus_amount as bonusAmount,t.fix_bonus_amount as fixBonusAmount,");
            select.append("t.create_time as createTime,t.send_time as sendTime,t.return_time as returnTime,t.bonus_time as bonusTime,t.bonus_class as bonusClass,");
            select.append("m.sid as sid,m.name as name,t.err_code as errCode,t.err_msg as errMsg");
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
    public Map<String, Object> getTicketCount(TicketQueryBean queryBean) {
        StringBuffer sql = new StringBuffer(
                "select count(t.id) as ticketNum,sum(t.amount) as orderAmount,sum(t.bonus_amount) as bonusAmount,sum(t.fix_bonus_amount) as fixBonusAmount from tms_ticket t ");
        sql.append(" left join tms_main_issue tmi on t.lottery_code = tmi.lottery_code and t.issue = tmi.name ");
        sql.append(" left join user_member m on m.user_code = t.user_code where 1=1 ");
        List<Object> paramList = new ArrayList<Object>();
        if (Utils.isNotEmpty(queryBean)) {
            Ticket ticket = queryBean.getTicket();
            if (Utils.isNotEmpty(ticket)) {
                if (Utils.isNotEmpty(ticket.getSid())) {
                    sql.append(" and t.sid = ? ");
                    paramList.add(ticket.getSid());
                }
                if (Utils.isNotEmpty(ticket.getUserInfo())) {
                    sql.append(" and m.name like ? ");
                    paramList.add("%" + ticket.getUserInfo() + "%");
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

                if (Utils.isNotEmpty(ticket.getSaleCode())) {
                    sql.append(" and t.sale_code = ? ");
                    paramList.add(ticket.getSaleCode());
                }

                if (Utils.isNotEmpty(ticket.getPostCode())) {
                    sql.append(" and t.post_code = ? ");
                    paramList.add(ticket.getPostCode());
                }

                if (Utils.isNotEmpty(ticket.getTicketStatus())) {
                    sql.append(" and t.ticket_status = ? ");
                    paramList.add(ticket.getTicketStatus());
                }

                if (Utils.isNotEmpty(ticket.getBonusStatus())) {
                    sql.append(" and t.bonus_status = ? ");
                    paramList.add(ticket.getBonusStatus());
                }

                if (Utils.isNotEmpty(ticket.getBigBonus())) {
                    sql.append(" and t.big_bonus = ? ");
                    paramList.add(ticket.getBigBonus());
                }

                if (Utils.isNotEmpty(ticket.getEndGameId())) {
                    sql.append(" and t.end_game_id = ? ");
                    paramList.add(ticket.getEndGameId());
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
                sql.append(" and tmi.operators_award = ? ");
                paramList.add(queryBean.getOperatorsAward());
            }
        }
        List<Map<String, Object>> dataList = jdbcTemplate.queryForList(sql.toString(), paramList.toArray());
        if (Utils.isNotEmpty(dataList)) {
            return dataList.get(0);
        }
        return null;
    }

    @Override
    public Map<String, Object> getTicketCountNoSend(TicketQueryBean queryBean) {
        StringBuffer sql = new StringBuffer(
                "select count(t.id) as ticketNum,sum(t.amount) as orderAmount,sum(t.bonus_amount) as bonusAmount,sum(t.fix_bonus_amount) as fixBonusAmount from tms_ticket t ");
        sql.append(" left join tms_main_issue tmi on t.lottery_code = tmi.lottery_code and t.issue = tmi.name ");
        sql.append(" left join user_member m on m.user_code = t.user_code where 1=1 ");
        List<Object> paramList = new ArrayList<Object>();
        if (Utils.isNotEmpty(queryBean)) {
            Ticket ticket = queryBean.getTicket();
            if (Utils.isNotEmpty(ticket)) {
                if (Utils.isNotEmpty(ticket.getSid())) {
                    sql.append(" and t.sid = ? ");
                    paramList.add(ticket.getSid());
                }
                if (Utils.isNotEmpty(ticket.getUserInfo())) {
                    sql.append(" and m.name like ? ");
                    paramList.add("%" + ticket.getUserInfo() + "%");
                }

                if (Utils.isNotEmpty(ticket.getLotteryCode())) {
                    sql.append(" and t.lottery_code = ? ");
                    paramList.add(ticket.getLotteryCode());
                    if (Utils.isNotEmpty(ticket.getNumberInfo())) {
                    	sql.append(" and t.number_info like ? ");
                    	paramList.add("%" + ticket.getNumberInfo() + "%");
                    }
                } else {
                	if (Utils.isNotEmpty(ticket.getNumberInfo())) {
                		sql.append(" and t.lottery_code in ('200','201') ");
                    	sql.append(" and t.number_info like ? ");
                    	paramList.add("%" + ticket.getNumberInfo() + "%");
                    }
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

                if (Utils.isNotEmpty(ticket.getSaleCode())) {
                    sql.append(" and t.sale_code = ? ");
                    paramList.add(ticket.getSaleCode());
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
                } else {
                    sql.append(" and  (t.ticket_status = " + Constants.TICKET_STATUS_DOING + " or t.ticket_status = " + Constants.TICKET_STATUS_RESEND + " or t.ticket_status = "
                            + Constants.TICKET_STATUS_SENDING + ") ");
                }

                if (Utils.isNotEmpty(ticket.getBonusStatus())) {
                    sql.append(" and t.bonus_status = ? ");
                    paramList.add(ticket.getBonusStatus());
                }

                if (Utils.isNotEmpty(ticket.getBigBonus())) {
                    sql.append(" and t.big_bonus = ? ");
                    paramList.add(ticket.getBigBonus());
                }

                if (Utils.isNotEmpty(ticket.getEndGameId())) {
                    sql.append(" and t.end_game_id = ? ");
                    paramList.add(ticket.getEndGameId());
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
                sql.append(" and tmi.operators_award = ? ");
                paramList.add(queryBean.getOperatorsAward());
            }
        }
        List<Map<String, Object>> dataList = jdbcTemplate.queryForList(sql.toString(), paramList.toArray());
        if (Utils.isNotEmpty(dataList)) {
            return dataList.get(0);
        }
        return null;
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
    public PageBean getPageBeanByParaForNoSend(TicketQueryBean queryBean, Integer page, Integer pageSize) {
        StringBuffer sql = new StringBuffer(" from tms_ticket t ");
        sql.append(" left join tms_main_issue tmi on t.lottery_code = tmi.lottery_code and t.issue = tmi.name ");
        sql.append(" left join user_member m on m.user_code = t.user_code where 1=1 ");
        List<Object> paramList = new ArrayList<Object>();
        if (Utils.isNotEmpty(queryBean)) {
            Ticket ticket = queryBean.getTicket();
            if (Utils.isNotEmpty(ticket)) {
                if (Utils.isNotEmpty(ticket.getSid())) {
                    sql.append(" and t.sid = ? ");
                    paramList.add(ticket.getSid());
                }
                if (Utils.isNotEmpty(ticket.getUserInfo())) {
                    sql.append(" and m.name like ? ");
                    paramList.add("%" + ticket.getUserInfo() + "%");
                }

                if (Utils.isNotEmpty(ticket.getLotteryCode())) {
                    sql.append(" and t.lottery_code = ? ");
                    paramList.add(ticket.getLotteryCode());
                    if (Utils.isNotEmpty(ticket.getNumberInfo())) {
                    	sql.append(" and t.number_info like ? ");
                    	paramList.add("%" + ticket.getNumberInfo() + "%");
                    }
                } else {
                	if (Utils.isNotEmpty(ticket.getNumberInfo())) {
                		sql.append(" and t.lottery_code in ('200','201') ");
                    	sql.append(" and t.number_info like ? ");
                    	paramList.add("%" + ticket.getNumberInfo() + "%");
                    }
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

                if (Utils.isNotEmpty(ticket.getSaleCode())) {
                    sql.append(" and t.sale_code = ? ");
                    paramList.add(ticket.getSaleCode());
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
                    } else if (ticket.getTicketStatus() == Constants.TICKET_STATUS_WAIT) {
                        sql.append(" and t.ticket_status = ? ");
                        paramList.add(Constants.TICKET_STATUS_WAIT);
                    } else {
                        sql.append(" and t.ticket_status = ? ");
                        paramList.add(ticket.getTicketStatus());
                    }
                } else {
                    sql.append(" and  (t.ticket_status = " + Constants.TICKET_STATUS_DOING + " or t.ticket_status = " + Constants.TICKET_STATUS_RESEND + " or t.ticket_status = "
                            + Constants.TICKET_STATUS_SENDING + " or t.ticket_status = " + Constants.TICKET_STATUS_WAIT + ") ");
                }

                if (Utils.isNotEmpty(ticket.getBonusStatus())) {
                    sql.append(" and t.bonus_status = ? ");
                    paramList.add(ticket.getBonusStatus());
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
                sql.append(" and tmi.operators_award = ? ");
                paramList.add(queryBean.getOperatorsAward());
            }
        }

        logger.info("SELECT COUNT(*) " + sql.toString());
        int count = jdbcTemplate.queryForInt("SELECT COUNT(*) " + sql.toString(), paramList.toArray());
        int pageTotal = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;

        PageBean pageBean = new PageBean();
        List resultList = new ArrayList();
        if (page <= pageTotal) {
            StringBuffer select = new StringBuffer("select t.ticket_id as ticketId,t.order_id as orderId,t.out_ticket_id as outTicketId,");
            select.append("t.lottery_code as lotteryCode,t.play_code as playCode,t.poll_code as pollCode,t.issue as issue,t.item as item,");
            select.append("t.multiple as multiple,t.sale_code as saleCode,t.post_code as postCode,t.amount as amount,t.ticket_status as ticketStatus,");
            select.append("t.bonus_status as bonusStatus,t.big_bonus as bigBonus,t.bonus_amount as bonusAmount,t.fix_bonus_amount as fixBonusAmount,");
            select.append("t.create_time as createTime,t.send_time as sendTime,t.return_time as returnTime,t.bonus_time as bonusTime,t.bonus_class as bonusClass,");
            select.append("m.sid as sid,m.name as name,t.err_code as errCode,t.err_msg as errMsg");
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
    public List<Map<String, Object>> findNoSendTickets(Date createStartTime, Date createEndTime, String postCode) {
        StringBuffer sql = new StringBuffer();
        sql.append("select t.post_code,t.lottery_code,t.issue,to_char(min(i.duplex_time),'yyyy-mm-dd hh24:mi:ss') duplex_time,t.ticket_status status,count(t.ticket_id) tts,to_char(min(t.create_time),'yyyy-mm-dd hh24:mi:ss') create_time,to_char(min(t.send_time),'yyyy-mm-dd hh24:mi:ss') send_time " +
                "from tms_ticket t,tms_main_issue i where t.issue=i.name and t.lottery_code=i.lottery_code and t.lottery_code not in ('200','201') and t.ticket_status in(0,2,4,6) ");

        List<Object> paramList = new ArrayList<Object>();
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
        sql.append(" group by t.post_code,t.lottery_code,t.issue,t.ticket_status");
        logger.info("findNoSendTickets sql:" + sql);
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql.toString(), paramList.toArray());
        return resultList;
    }
    
    @Override
	public List<Map<String, Object>> findJcNoSendTickets(Date createStartTime,Date createEndTime, String postCode) {
    	
    	StringBuffer sql1 = new StringBuffer();
    	StringBuffer sql2 = new StringBuffer();
        sql1.append("select t.lottery_code,t.post_code,t.issue,t.ticket_status status,to_char(min(t1.end_time),'yyyy-mm-dd hh24:mi:ss') duplex_time,to_char(min(t.create_time),'yyyy-mm-dd hh24:mi:ss') create_time,to_char(min(t.send_time),'yyyy-mm-dd hh24:mi:ss') send_time,count(t.ticket_id) tts ");
        sql2.append("select t.lottery_code,t.post_code,t.issue,t.ticket_status status,to_char(min(t1.end_time),'yyyy-mm-dd hh24:mi:ss') duplex_time,to_char(min(t.create_time),'yyyy-mm-dd hh24:mi:ss') create_time,to_char(min(t.send_time),'yyyy-mm-dd hh24:mi:ss') send_time,count(t.ticket_id) tts ");
        sql1.append(" from tms_ticket t,tms_sub_issue_for_jczq t1 where t1.issue=substr(t.start_game_id,0,8) and t1.sn = substr(t.start_game_id,9) and t1.play_code = '00' ");
        if(isStopSale("200")){
        	sql1.append(" and t.ticket_status in(0,4,6)");
        }else {
        	sql1.append(" and t.ticket_status in(0,2,4,6)");
		}
        
        sql2.append(" from tms_ticket t,tms_sub_issue_for_jclq t1 where t1.issue=substr(t.start_game_id,0,8) and t1.sn = substr(t.start_game_id,9) and t1.play_code = '00' ");
        
        if(isStopSale("201")){
        	sql2.append(" and t.ticket_status in(0,4,6)");
        }else {
        	sql2.append(" and t.ticket_status in(0,2,4,6)");
		}
        
        List<Object> paramList = new ArrayList<Object>();
        if (Utils.isNotEmpty(createStartTime)) {
            sql1.append(" and t.create_time >= ? ");
            sql2.append(" and t.create_time >= ? ");
            paramList.add(createStartTime);
        }

        if (Utils.isNotEmpty(createEndTime)) {
            sql1.append(" and t.create_time < ? ");
            sql2.append(" and t.create_time < ? ");
            paramList.add(createEndTime);
        }

        if (Utils.isNotEmpty(postCode)) {
            sql1.append(" and t.post_code= ? ");
            sql2.append(" and t.post_code= ? ");
            paramList.add(postCode);
        }
        sql1.append(" group by t.lottery_code,t.post_code,t.issue,t.ticket_status");
        sql2.append(" group by t.lottery_code,t.post_code,t.issue,t.ticket_status");
        logger.info("findJcNoSendTickets sql1:" + sql1);
        logger.info("findJcNoSendTickets sql2:" + sql2);
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql1.toString(), paramList.toArray());
        List<Map<String, Object>> lqResultList = jdbcTemplate.queryForList(sql2.toString(), paramList.toArray());
        resultList.addAll(lqResultList);
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
    
    @Override
    public PageBean getPageBeanByPara(Ticket ticket, Integer page, Integer pageSize) {
        StringBuffer sql = new StringBuffer(" from tms_ticket t ");
        sql.append(" left join tms_main_issue tmi on t.lottery_code = tmi.lottery_code and t.issue = tmi.name ");
        sql.append(" left join user_member m on m.user_code = t.user_code where 1=1 ");
        List<Object> paramList = new ArrayList<Object>();
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
            if (Utils.isNotEmpty(ticket.getBonusStatus())) {
                sql.append(" and t.bonus_status = ? ");
                paramList.add(ticket.getBonusStatus());
            }
            if (Utils.isNotEmpty(ticket.getTicketStatus())) {
                sql.append(" and t.ticket_status = ? ");
                paramList.add(ticket.getTicketStatus());
            }
            if (Utils.isNotEmpty(ticket.getEndGameId())) {
                sql.append(" and t.end_game_id = ? ");
                paramList.add(ticket.getEndGameId());
            }

        }

        logger.info("SELECT COUNT(*) " + sql.toString());
        int count = jdbcTemplate.queryForInt("SELECT COUNT(*) " + sql.toString(), paramList.toArray());
        int pageTotal = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;

        PageBean pageBean = new PageBean();
        List resultList = new ArrayList();
        if (page <= pageTotal) {
            StringBuffer select = new StringBuffer("select t.ticket_id as ticketId,t.order_id as orderId,t.out_ticket_id as outTicketId,");
            select.append("t.lottery_code as lotteryCode,t.play_code as playCode,t.poll_code as pollCode,t.issue as issue,t.item as item,");
            select.append("t.multiple as multiple,t.sale_code as saleCode,t.post_code as postCode,t.amount as amount,t.ticket_status as ticketStatus,");
            select.append("t.bonus_status as bonusStatus,t.big_bonus as bigBonus,t.bonus_amount as bonusAmount,t.fix_bonus_amount as fixBonusAmount,");
            select.append("t.create_time as createTime,t.send_time as sendTime,t.return_time as returnTime,t.bonus_time as bonusTime,t.bonus_class as bonusClass,");
            select.append("m.sid as sid,m.name as name ");
            select.append(sql);
            select.append(" order by t.bonus_amount desc ");
            resultList = jdbcTemplate.queryForList(JdbcPageUtil.getOraclePageSQL(select.toString(), page, pageSize), paramList.toArray());
        }
        pageBean.setPageId(page);
        pageBean.setPageTotal(pageTotal);
        pageBean.setItemTotal(new Long(count));
        pageBean.setPageContent(resultList);
        return pageBean;
    }
    
}
