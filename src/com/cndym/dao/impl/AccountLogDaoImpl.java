package com.cndym.dao.impl;

import com.cndym.bean.query.AccountLogMember;
import com.cndym.bean.user.AccountLog;
import com.cndym.dao.IAccountLogDao;
import com.cndym.utils.Utils;
import com.cndym.utils.hibernate.HibernatePara;
import com.cndym.utils.hibernate.PageBean;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: 邓玉明 Date: 11-3-27 下午10:26
 */
@Repository
public class AccountLogDaoImpl extends GenericDaoImpl<AccountLog> implements IAccountLogDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private SessionFactory sessionFactoryTemp;

    @PostConstruct
    private void sessionFactoryInit() {
        super.setSessionFactory(sessionFactoryTemp);
    }

    @Override
    public AccountLog getAccountLogByOrderIdEventCode(String orderId, String sid, String eventCode) {
        List<AccountLog> list = find("From AccountLog where orderId=? and sid=? and eventCode=?", new Object[]{orderId, sid, eventCode});
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public AccountLog getAccountLogByOrderIdEventCodeForUpdate(String orderId, String sid, String eventCode) {
        String sql = "select * from user_account_log t where t.order_id=? and t.sid=? and event_code=? for update";
        List<AccountLog> list = jdbcTemplate.query(sql, new Object[]{orderId, sid, eventCode}, ParameterizedBeanPropertyRowMapper.newInstance(AccountLog.class));
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public double countAccountLogAmount(String orderId, String sid, String eventCode) {
        String sql = "select sum(t.bonus_amount+t.recharge_amount+t.present_amount) as total from user_account_log t where t.order_id=? and t.sid=? and event_code=?";
        Map map = jdbcTemplate.queryForMap(sql, new Object[]{orderId, sid, eventCode});
        BigDecimal bigDecimal = (BigDecimal) map.get("total");
        if (null != bigDecimal) {
            return bigDecimal.doubleValue();
        } else {
            return 0d;
        }
    }

    @Override
    public Map<String, Object> customSql(String sql, Object[] para) {
        return null;
    }

    public PageBean getAccountLogMemberList(AccountLogMember account, Integer page, Integer pageSize) {
        StringBuffer sql = new StringBuffer("From AccountLog account,Member m where account.userCode=m.userCode ");
        List<HibernatePara> hibernateParas = new ArrayList<HibernatePara>();
        if (Utils.isNotEmpty(account)) {
            if (Utils.isNotEmpty(account.getSid())) {
                sql.append(" and m.sid = ? ");
                hibernateParas.add(new HibernatePara(account.getSid()));
            }
            if (Utils.isNotEmpty(account.getName())) {
                sql.append(" and m.name like ? ");
                hibernateParas.add(new HibernatePara("%" + account.getName() + "%"));
            }
            if (Utils.isNotEmpty(account.getStartTime())) {
                sql.append(" and account.createTime>=?");
                hibernateParas.add(new HibernatePara(account.getStartTime()));
            }
            if (Utils.isNotEmpty(account.getEndTime())) {
                sql.append(" and account.createTime<?");
                hibernateParas.add(new HibernatePara(account.getEndTime()));
            }
            if (Utils.isNotEmpty(account.getAccountLog())) {
                if (Utils.isNotEmpty(account.getAccountLog().getEventCode())) {
                    sql.append(" and ( ");
                    String[] codeArray = account.getAccountLog().getEventCode().split(",");
                    for (int i = 0; i < codeArray.length; i++) {
                        if (i == 0) {
                            sql.append("account.eventCode=? ");
                        } else {
                            sql.append(" or account.eventCode=? ");
                        }
                        hibernateParas.add(new HibernatePara(codeArray[i]));
                    }
                    sql.append(" ) ");
                }
                if (Utils.isNotEmpty(account.getAccountLog().getUserCode())) {
                    sql.append(" and account.userCode=?");
                    hibernateParas.add(new HibernatePara(account.getAccountLog().getUserCode()));
                }
                if (Utils.isNotEmpty(account.getAccountLog().getType())) {
                    sql.append(" and account.type=?");
                    hibernateParas.add(new HibernatePara(account.getAccountLog().getType()));
                }
                if (Utils.isNotEmpty(account.getAccountLog().getEventType())) {
                    sql.append(" and account.eventType=?");
                    hibernateParas.add(new HibernatePara(account.getAccountLog().getEventType()));
                }
                if (Utils.isNotEmpty(account.getAccountLog().getOrderId())) {
                    sql.append(" and account.orderId=?");
                    hibernateParas.add(new HibernatePara(account.getAccountLog().getOrderId()));
                }
                if (Utils.isNotEmpty(account.getAccountLog().getTicketId())) {
                    sql.append(" and account.ticketId=?");
                    hibernateParas.add(new HibernatePara(account.getAccountLog().getTicketId()));
                }
            }
            if (Utils.isNotEmpty(account.getAmountc())) {
                sql.append(" and ((((abs(account.bonusAmount) + abs(account.rechargeAmount) + abs(account.presentAmount))>=?) and "
                        + "((abs(account.bonusAmount) + abs(account.rechargeAmount) + abs(account.presentAmount))!=0)) " + "or (abs(account.freezeAmount)>=? and abs(account.freezeAmount) != 0))");
                hibernateParas.add(new HibernatePara(account.getAmountc()));
                hibernateParas.add(new HibernatePara(account.getAmountc()));
            }
            if (Utils.isNotEmpty(account.getAmountd())) {
                sql.append(" and ((((abs(account.bonusAmount) + abs(account.rechargeAmount) + abs(account.presentAmount))<=?) and "
                        + "((abs(account.bonusAmount) + abs(account.rechargeAmount) + abs(account.presentAmount))!=0)) " + "or (abs(account.freezeAmount)<=? and abs(account.freezeAmount) != 0))");
                hibernateParas.add(new HibernatePara(account.getAmountd()));
                hibernateParas.add(new HibernatePara(account.getAmountd()));
            }
        }
        sql.append(" order by account.createTime desc");
        return getPageBeanByPara(sql.toString(), hibernateParas, page, pageSize);
    }

    @Override
    public List getAccountLogAmountCount(AccountLogMember account) {
        StringBuffer sql = new StringBuffer(
                "select sum(case when account.freeze_amount = 0 then abs(account.bonus_amount) else 0 end) bonusAmount,sum(abs(account.freeze_amount)) freezeAmount,sum(case when account.freeze_amount = 0 then abs(account.present_amount) else 0 end) presentAmount,sum(case when account.freeze_amount = 0 then abs(account.recharge_amount) else 0 end) rechargeAmount from user_account_log account left join user_member m on m.user_code = account.user_code where 1=1 ");
        List<Object> params = new ArrayList<Object>();
        if (Utils.isNotEmpty(account)) {
            if (Utils.isNotEmpty(account.getSid())) {
                sql.append(" and account.sid = ?");
                params.add(account.getSid());
            }
            if (Utils.isNotEmpty(account.getName())) {
                sql.append(" and m.name like ? ");
                params.add("%" + account.getName() + "%");
            }
            if (Utils.isNotEmpty(account.getStartTime())) {
                sql.append(" and account.create_time>=?");
                params.add(account.getStartTime());
            }
            if (Utils.isNotEmpty(account.getEndTime())) {
                sql.append(" and account.create_time<?");
                params.add(account.getEndTime());
            }
            if (Utils.isNotEmpty(account.getAccountLog())) {
                if (Utils.isNotEmpty(account.getAccountLog().getEventCode())) {
                    sql.append(" and ( ");
                    String[] codeArray = account.getAccountLog().getEventCode().split(",");
                    for (int i = 0; i < codeArray.length; i++) {
                        if (i == 0) {
                            sql.append("account.event_code=? ");
                        } else {
                            sql.append(" or account.event_code=? ");
                        }
                        params.add(codeArray[i]);
                    }
                    sql.append(" ) ");
                }
            }
            if (Utils.isNotEmpty(account.getAccountLog().getUserCode())) {
                sql.append(" and account.user_code=?");
                params.add(account.getAccountLog().getUserCode());
            }
            if (Utils.isNotEmpty(account.getAccountLog().getType())) {
                sql.append(" and account.type=?");
                params.add(account.getAccountLog().getType());
            }
            if (Utils.isNotEmpty(account.getAccountLog().getEventType())) {
                sql.append(" and account.event_type=?");
                params.add(account.getAccountLog().getEventType());
            }
            if (Utils.isNotEmpty(account.getAccountLog().getOrderId())) {
                sql.append(" and account.order_id=?");
                params.add(account.getAccountLog().getOrderId());
            }
            if (Utils.isNotEmpty(account.getAccountLog().getTicketId())) {
                sql.append(" and account.ticket_id=?");
                params.add(account.getAccountLog().getTicketId());
            }

            if (Utils.isNotEmpty(account.getAmountc())) {
                sql.append(" and ((((abs(account.bonus_amount) + abs(account.recharge_amount) + abs(account.present_amount))>=?) and "
                        + "((abs(account.bonus_amount) + abs(account.recharge_amount) + abs(account.present_amount))!=0)) " + "or (abs(account.freeze_amount)>=? and abs(account.freeze_amount) != 0))");
                params.add(account.getAmountc());
                params.add(account.getAmountc());
            }
            if (Utils.isNotEmpty(account.getAmountd())) {
                sql.append(" and ((((abs(account.bonus_amount) + abs(account.recharge_amount) + abs(account.present_amount))<=?) and "
                        + "((abs(account.bonus_amount) + abs(account.recharge_amount) + abs(account.present_amount))!=0)) " + "or (abs(account.freeze_amount)<=? and abs(account.freeze_amount) != 0))");
                params.add(account.getAmountd());
                params.add(account.getAmountd());
            }
        }
        return jdbcTemplate.queryForList(sql.toString(), params.toArray());
    }

    @Override
    public List getAccountLogAmountCountForAdjust(AccountLogMember accountLogMember) {
        StringBuffer sql = new StringBuffer(
                "select sum(abs(account.bonus_amount)) bonusAmount,sum(abs(account.freeze_amount)) freezeAmount,sum(abs(account.present_amount)) presentAmount,sum(abs(account.recharge_amount)) rechargeAmount from user_account_log account,user_member m,user_adjust_account uaa where m.user_code = account.user_code and account.order_id = uaa.adjust_id ");
        List<Object> params = new ArrayList<Object>();
        if (Utils.isNotEmpty(accountLogMember)) {
            if (Utils.isNotEmpty(accountLogMember.getOperator())) {
                sql.append(" and uaa.operator like ?");
                params.add("%" + accountLogMember.getOperator() + "%");
            }
            if (Utils.isNotEmpty(accountLogMember.getMember())) {
                if (Utils.isNotEmpty(accountLogMember.getMember().getName())) {
                    sql.append(" and m.name like ?");
                    params.add("%" + accountLogMember.getMember().getName() + "%");
                }
                if (Utils.isNotEmpty(accountLogMember.getMember().getSid())) {
                    sql.append(" and m.sid like ?");
                    params.add("%" + accountLogMember.getMember().getSid() + "%");
                }
            }
            if (Utils.isNotEmpty(accountLogMember.getStartTime())) {
                sql.append(" and account.create_time>=?");
                params.add(accountLogMember.getStartTime());
            }
            if (Utils.isNotEmpty(accountLogMember.getEndTime())) {
                sql.append(" and account.create_time<?");
                params.add(accountLogMember.getEndTime());
            }
            if (Utils.isNotEmpty(accountLogMember.getAccountLog())) {
                if (Utils.isNotEmpty(accountLogMember.getAccountLog().getEventCode())) {
                    sql.append(" and ( ");
                    if (accountLogMember.getAccountLog().getEventCode().equals("0")) {
                        sql.append("account.event_code = ? or account.event_code = ? or account.event_code = ? or account.event_code = ? or account.event_code = ? or account.event_code = ? or account.event_code = ?");
                        params.add("00000");
                        params.add("00200");
                        params.add("00400");
                        params.add("00101");
                        params.add("00103");
                        params.add("00100");
                        params.add("00500");
                    } else if (accountLogMember.getAccountLog().getEventCode().equals("1")) {
                        sql.append("account.event_code = ? or account.event_code = ? or account.event_code = ? or account.event_code = ? or account.event_code = ? or account.event_code = ?  or account.event_code = ? ");
                        params.add("20001");
                        params.add("10000");
                        params.add("10001");
                        params.add("10003");
                        params.add("20000");
                        params.add("10400");
                        params.add("10200");
                    } else if (accountLogMember.getAccountLog().getEventCode().equals("2")) {
                        sql.append("account.event_code = ? or account.event_code = ? or account.event_code = ? or account.event_code = ? ");
                        params.add("10300");
                        params.add("00600");
                        params.add("10301");
                        params.add("00601");
                    } else {
                        String[] codeArray = accountLogMember.getAccountLog().getEventCode().split(",");
                        for (int i = 0; i < codeArray.length; i++) {
                            if (i == 0) {
                                sql.append("account.event_code=? ");
                            } else {
                                sql.append(" or account.event_code=? ");
                            }
                            params.add(codeArray[i]);
                        }
                    }
                    sql.append(" ) ");
                }
                if (Utils.isNotEmpty(accountLogMember.getAccountLog().getUserCode())) {
                    sql.append(" and account.user_code=?");
                    params.add(accountLogMember.getAccountLog().getUserCode());
                }
                if (Utils.isNotEmpty(accountLogMember.getAccountLog().getType())) {
                    sql.append(" and account.type=?");
                    params.add(accountLogMember.getAccountLog().getType());
                }
                if (Utils.isNotEmpty(accountLogMember.getAccountLog().getEventType())) {
                    sql.append(" and account.event_type=?");
                    params.add(accountLogMember.getAccountLog().getEventType());
                }
                if (Utils.isNotEmpty(accountLogMember.getAccountLog().getOrderId())) {
                    sql.append(" and account.order_id=?");
                    params.add(accountLogMember.getAccountLog().getOrderId());
                }
                if (Utils.isNotEmpty(accountLogMember.getAccountLog().getEventCodeArr())) {
                    String[] arr = accountLogMember.getAccountLog().getEventCodeArr();
                    sql.append(" and (");
                    for (int i = 0; i < arr.length; i++) {
                        if (i == 0) {
                            sql.append("account.event_code=?");
                        } else {
                            sql.append(" or account.event_code=?");
                        }
                        params.add(arr[i]);
                    }
                    sql.append(")");
                }
                if (Utils.isNotEmpty(accountLogMember.getAccountLog().getBackup1())) {
                    if (accountLogMember.getAccountLog().getBackup1().equals("1")) {
                        sql.append(" and account.backup1 is null ");
                    } else {
                        sql.append(" and account.backup1=?");
                        params.add(accountLogMember.getAccountLog().getBackup1());
                    }
                }
            }
            if (Utils.isNotEmpty(accountLogMember.getAmountc())) {
                sql.append(" and ((((abs(account.bonus_amount) + abs(account.recharge_amount) + abs(account.present_amount))>=?) and "
                        + "((abs(account.bonus_amount) + abs(account.recharge_amount) + abs(account.present_amount))!=0)) " + "or (abs(account.freeze_amount)>=? and abs(account.freeze_amount) != 0))");
                params.add(accountLogMember.getAmountc());
                params.add(accountLogMember.getAmountc());
            }
            if (Utils.isNotEmpty(accountLogMember.getAmountd())) {
                sql.append(" and ((((abs(account.bonus_amount) + abs(account.recharge_amount) + abs(account.present_amount))<=?) and "
                        + "((abs(account.bonus_amount) + abs(account.recharge_amount) + abs(account.present_amount))!=0)) " + "or (abs(account.freeze_amount)<=? and abs(account.freeze_amount) != 0))");
                params.add(accountLogMember.getAmountd());
                // params.add(accountLogMember.getAmountd());
            }
        }
        Object[] objectArray = new Object[params.size()];
        int index = 0;
        for (Object obj : params) {
            objectArray[index] = obj;
            index++;
        }
        return jdbcTemplate.queryForList(sql.toString(), objectArray);
    }

    @Override
    public PageBean getAccountLogForAdjust(AccountLogMember account, Integer page, Integer pageSize) {
        StringBuffer sql = new StringBuffer("From AccountLog account,Member m,AdjustAccount ad where account.userCode=m.userCode and account.orderId=ad.adjustId ");
        List<HibernatePara> hibernateParas = new ArrayList<HibernatePara>();
        if (Utils.isNotEmpty(account)) {
            if (Utils.isNotEmpty(account.getOperator())) {
                sql.append(" and ad.operator like ?");
                hibernateParas.add(new HibernatePara("%" + account.getOperator() + "%"));
            }
            if (Utils.isNotEmpty(account.getMember())) {
                if (Utils.isNotEmpty(account.getMember().getName())) {
                    sql.append(" and m.name like ?");
                    hibernateParas.add(new HibernatePara("%" + account.getMember().getName() + "%"));
                }
                if (Utils.isNotEmpty(account.getMember().getSid())) {
                    sql.append(" and m.sid=?");
                    hibernateParas.add(new HibernatePara(account.getMember().getSid()));
                }
            }
            if (Utils.isNotEmpty(account.getStartTime())) {
                sql.append(" and account.createTime>=?");
                hibernateParas.add(new HibernatePara(account.getStartTime()));
            }
            if (Utils.isNotEmpty(account.getEndTime())) {
                sql.append(" and account.createTime<?");
                hibernateParas.add(new HibernatePara(account.getEndTime()));
            }
            if (Utils.isNotEmpty(account.getAccountLog())) {
                if (Utils.isNotEmpty(account.getAccountLog().getEventCode())) {
                    sql.append(" and ( ");
                    if (account.getAccountLog().getEventCode().equals("0")) {
                        sql.append("account.eventCode = ? or account.eventCode = ? or account.eventCode = ? or account.eventCode = ? or account.eventCode = ? or account.eventCode = ? or account.eventCode = ?");
                        hibernateParas.add(new HibernatePara("00000"));
                        hibernateParas.add(new HibernatePara("00200"));
                        hibernateParas.add(new HibernatePara("00400"));
                        hibernateParas.add(new HibernatePara("00101"));
                        hibernateParas.add(new HibernatePara("00103"));
                        hibernateParas.add(new HibernatePara("00100"));
                        hibernateParas.add(new HibernatePara("00500"));
                    } else if (account.getAccountLog().getEventCode().equals("1")) {
                        sql.append("account.eventCode = ? or account.eventCode = ? or account.eventCode = ? or account.eventCode = ? or account.eventCode = ? or account.eventCode = ? or account.eventCode = ? ");
                        hibernateParas.add(new HibernatePara("20001"));
                        hibernateParas.add(new HibernatePara("10000"));
                        hibernateParas.add(new HibernatePara("10001"));
                        hibernateParas.add(new HibernatePara("10003"));
                        hibernateParas.add(new HibernatePara("20000"));
                        hibernateParas.add(new HibernatePara("10400"));
                        hibernateParas.add(new HibernatePara("10200"));
                    } else if (account.getAccountLog().getEventCode().equals("2")) {
                        sql.append("account.eventCode = ? or account.eventCode = ? or account.eventCode = ? or account.eventCode = ? ");
                        hibernateParas.add(new HibernatePara("10300"));
                        hibernateParas.add(new HibernatePara("00600"));
                        hibernateParas.add(new HibernatePara("10301"));
                        hibernateParas.add(new HibernatePara("00601"));
                    } else {
                        String[] codeArray = account.getAccountLog().getEventCode().split(",");
                        for (int i = 0; i < codeArray.length; i++) {
                            if (i == 0) {
                                sql.append("account.eventCode=? ");
                            } else {
                                sql.append(" or account.eventCode=? ");
                            }
                            hibernateParas.add(new HibernatePara(codeArray[i]));
                        }
                    }
                    sql.append(" ) ");
                }
                if (Utils.isNotEmpty(account.getAccountLog().getUserCode())) {
                    sql.append(" and account.userCode=?");
                    hibernateParas.add(new HibernatePara(account.getAccountLog().getUserCode()));
                }
                if (Utils.isNotEmpty(account.getAccountLog().getType())) {
                    sql.append(" and account.type=?");
                    hibernateParas.add(new HibernatePara(account.getAccountLog().getType()));
                }
                if (Utils.isNotEmpty(account.getAccountLog().getEventType())) {
                    sql.append(" and account.eventType=?");
                    hibernateParas.add(new HibernatePara(account.getAccountLog().getEventType()));
                }
                if (Utils.isNotEmpty(account.getAccountLog().getOrderId())) {
                    sql.append(" and account.orderId=?");
                    hibernateParas.add(new HibernatePara(account.getAccountLog().getOrderId()));
                }
                if (Utils.isNotEmpty(account.getAccountLog().getEventCodeArr())) {
                    String[] arr = account.getAccountLog().getEventCodeArr();
                    sql.append(" and (");
                    for (int i = 0; i < arr.length; i++) {
                        if (i == 0) {
                            sql.append("account.eventCode=?");
                        } else {
                            sql.append(" or account.eventCode=?");
                        }
                        hibernateParas.add(new HibernatePara(arr[i]));
                    }
                    sql.append(")");
                }

                if (Utils.isNotEmpty(account.getAccountLog().getBackup1())) {
                    if (account.getAccountLog().getBackup1().equals("1")) {
                        sql.append(" and account.backup1 is null ");
                    } else {
                        sql.append(" and account.backup1=?");
                        hibernateParas.add(new HibernatePara(account.getAccountLog().getBackup1()));
                    }
                }

            }
            if (Utils.isNotEmpty(account.getAmountc())) {
                sql.append(" and ((((abs(account.bonusAmount) + abs(account.rechargeAmount) + abs(account.presentAmount))>=?) and "
                        + "((abs(account.bonusAmount) + abs(account.rechargeAmount) + abs(account.presentAmount))!=0)) " + "or (abs(account.freezeAmount)>=? and abs(account.freezeAmount) != 0))");
                hibernateParas.add(new HibernatePara(account.getAmountc()));
                hibernateParas.add(new HibernatePara(account.getAmountc()));
            }
            if (Utils.isNotEmpty(account.getAmountd())) {
                sql.append(" and ((((abs(account.bonusAmount) + abs(account.rechargeAmount) + abs(account.presentAmount))<=?) and "
                        + "((abs(account.bonusAmount) + abs(account.rechargeAmount) + abs(account.presentAmount))!=0)) " + "or (abs(account.freezeAmount)<=? and abs(account.freezeAmount) != 0))");
                hibernateParas.add(new HibernatePara(account.getAmountd()));
                hibernateParas.add(new HibernatePara(account.getAmountd()));
            }
        }
        sql.append(" order by account.createTime desc");

        return getPageBeanByPara(sql.toString(), hibernateParas, page, pageSize);
    }
}
