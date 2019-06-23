/**
 * 
 */
package com.cndym.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cndym.bean.query.CheckTicketQueryBean;
import com.cndym.bean.tms.CheckTicket;
import com.cndym.dao.ICheckTicketDao;
import com.cndym.utils.JdbcPageUtil;
import com.cndym.utils.Utils;
import com.cndym.utils.hibernate.PageBean;

/**
 * @author 朱林虎
 *
 */
@Repository
public class CheckTicketDaoImpl extends GenericDaoImpl<CheckTicket> implements ICheckTicketDao {
	
	private static Logger logger = Logger.getLogger(CheckTicketDaoImpl.class);
	
	@Resource
    private SessionFactory sessionFactoryTemp;
    @Resource
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void sessionFactoryInit() {
        super.setSessionFactory(sessionFactoryTemp);
    }
	
    @Override
	public CheckTicket getCheckTicketByTicketId(String ticketId) {
		String sql = "From CheckTicket t where ticketId=?";
        List<CheckTicket> checkTicketList = find(sql, new Object[]{ticketId});
        if (checkTicketList.size() > 0) {
            return checkTicketList.get(0);
        }
        return null;
	}

	@Override
	public PageBean getCheckTicketListByPara(CheckTicketQueryBean queryBean,
			Integer page, Integer pageSize) {
		
		StringBuffer sql = new StringBuffer(" from tms_check_ticket t ");
		    		 sql.append(" left join tms_main_issue tmi on t.lottery_code = tmi.lottery_code and t.issue = tmi.name ");
		  			 sql.append(" left join user_member m on m.user_code = t.user_code where 1=1 ");
		  			 
	    List<Object> paramList = new ArrayList<Object>();
	    if (Utils.isNotEmpty(queryBean)) {
	    	CheckTicket checkTicket = queryBean.getCheckTicket();
		    if (Utils.isNotEmpty(checkTicket)) {
		        if (Utils.isNotEmpty(checkTicket.getSid())) {
		            sql.append(" and t.sid = ? ");
		            paramList.add(checkTicket.getSid());
		        }
		
		        if (Utils.isNotEmpty(checkTicket.getLotteryCode())) {
		            sql.append(" and t.lottery_code = ? ");
		            paramList.add(checkTicket.getLotteryCode());
		        }
		        if (Utils.isNotEmpty(checkTicket.getIssue())) {
		            sql.append(" and t.issue = ? ");
		            paramList.add(checkTicket.getIssue());
		        }
		        if (Utils.isNotEmpty(checkTicket.getOrderId())) {
		            sql.append(" and t.order_id = ? ");
		            paramList.add(checkTicket.getOrderId());
		        }
		
		        if (Utils.isNotEmpty(checkTicket.getOutTicketId())) {
		            sql.append(" and t.out_ticket_id = ? ");
		            paramList.add(checkTicket.getOutTicketId());
		        }
		
		        if (Utils.isNotEmpty(checkTicket.getTicketId())) {
		            sql.append(" and t.ticket_id = ? ");
		            paramList.add(checkTicket.getTicketId());
		        }
		
		        if (Utils.isNotEmpty(checkTicket.getPostCode())) {
		            sql.append(" and t.post_code = ? ");
		            paramList.add(checkTicket.getPostCode());
		        }
		
		        if (Utils.isNotEmpty(checkTicket.getTicketStatus())) {
		            sql.append(" and t.ticket_status = ? ");
		            paramList.add(checkTicket.getTicketStatus());
		        }
		
		        if (Utils.isNotEmpty(checkTicket.getBonusStatus())) {
		            sql.append(" and t.bonus_status = ? ");
		            paramList.add(checkTicket.getBonusStatus());
		        }
		
		        if (Utils.isNotEmpty(checkTicket.getBigBonus())) {
		            sql.append(" and t.big_bonus = ? ");
		            paramList.add(checkTicket.getBigBonus());
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
            if (Utils.isNotEmpty(queryBean.getAmountDiffStatus()) && queryBean.getAmountDiffStatus() != 0) {
            	if(queryBean.getAmountDiffStatus() == 1){
            		sql.append(" and t.amount_diff = 0 ");
            	}else {
            		sql.append(" and t.amount_diff > 0 ");
				}
            }
            if (Utils.isNotEmpty(queryBean.getFixBonusDiffStatus()) && queryBean.getFixBonusDiffStatus() != 0) {
            	if(queryBean.getFixBonusDiffStatus() == 1){
            		sql.append(" and t.fix_bonus_amount_diff = 0 ");
            	}else {
            		sql.append(" and t.fix_bonus_amount_diff > 0 ");
				}
            }
            if (Utils.isNotEmpty(queryBean.getBonusDiffStatus()) && queryBean.getBonusDiffStatus() != 0) {
            	if(queryBean.getBonusDiffStatus() == 1){
            		sql.append(" and t.bonus_amount_diff = 0 ");
            	}else {
            		sql.append(" and t.bonus_amount_diff > 0 ");
				}
            }
            
            if (Utils.isNotEmpty(queryBean.getTicketDiffStatus()) && queryBean.getTicketDiffStatus() != 0) {
            	if(queryBean.getTicketDiffStatus() == 1){
            		sql.append(" and t.ticket_status_diff = 1 ");
            	}else {
            		sql.append(" and t.ticket_status_diff = 0 ");
				}
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
            select.append("t.multiple as multiple,t.post_code as postCode,t.amount as amount,t.up_amount as upAmount,t.ticket_status as ticketStatus,");
            select.append("t.bonus_status as bonusStatus,t.big_bonus as bigBonus,t.bonus_amount as bonusAmount,t.up_bonus_amount as upBonusAmount,t.up_fix_bonus_amount as upFixBonusAmount,t.fix_bonus_amount as fixBonusAmount,");
            select.append("t.create_time as createTime,t.send_time as sendTime,t.return_time as returnTime,t.bonus_time as bonusTime,t.amount_diff as amountDiff,t.fix_bonus_amount_diff as fixBonusAmountDiff,t.bonus_amount_diff as bonusAmountDiff,");
            select.append("t.up_ticket_status as upTicketStatus,t.up_ticket_status_desc as upTicketStatusDesc,t.ticket_status_diff as ticketStatusDiff,m.sid as sid,m.name as name");
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
	public Map<String, Object> getCheckTicketCount(CheckTicketQueryBean queryBean) {
		StringBuffer sql = new StringBuffer("select count(t.id) as ticketNum,sum(t.amount_diff) as orderAmountDiff,sum(t.bonus_amount_diff) as bonusAmountDiff,sum(t.fix_bonus_amount_diff) as fixBonusAmountDiff from tms_check_ticket t ");
		 			 sql.append(" left join tms_main_issue tmi on t.lottery_code = tmi.lottery_code and t.issue = tmi.name ");
		 			 sql.append(" left join user_member m on m.user_code = t.user_code where 1=1 ");
			 
		List<Object> paramList = new ArrayList<Object>();
		if (Utils.isNotEmpty(queryBean)) {
			CheckTicket checkTicket = queryBean.getCheckTicket();
			if (Utils.isNotEmpty(checkTicket)) {
			   if (Utils.isNotEmpty(checkTicket.getSid())) {
			       sql.append(" and t.sid = ? ");
			       paramList.add(checkTicket.getSid());
			   }
			
			   if (Utils.isNotEmpty(checkTicket.getLotteryCode())) {
			       sql.append(" and t.lottery_code = ? ");
			       paramList.add(checkTicket.getLotteryCode());
			   }
			   if (Utils.isNotEmpty(checkTicket.getIssue())) {
			       sql.append(" and t.issue = ? ");
			       paramList.add(checkTicket.getIssue());
			   }
			   if (Utils.isNotEmpty(checkTicket.getOrderId())) {
			       sql.append(" and t.order_id = ? ");
			       paramList.add(checkTicket.getOrderId());
			   }
			
			   if (Utils.isNotEmpty(checkTicket.getOutTicketId())) {
			       sql.append(" and t.out_ticket_id = ? ");
			       paramList.add(checkTicket.getOutTicketId());
			   }
			
			   if (Utils.isNotEmpty(checkTicket.getTicketId())) {
			       sql.append(" and t.ticket_id = ? ");
			       paramList.add(checkTicket.getTicketId());
			   }
			
			   if (Utils.isNotEmpty(checkTicket.getPostCode())) {
			       sql.append(" and t.post_code = ? ");
			       paramList.add(checkTicket.getPostCode());
			   }
			
			   if (Utils.isNotEmpty(checkTicket.getTicketStatus())) {
			       sql.append(" and t.ticket_status = ? ");
			       paramList.add(checkTicket.getTicketStatus());
			   }
			
			   if (Utils.isNotEmpty(checkTicket.getBonusStatus())) {
			       sql.append(" and t.bonus_status = ? ");
			       paramList.add(checkTicket.getBonusStatus());
			   }
			
			   if (Utils.isNotEmpty(checkTicket.getBigBonus())) {
			       sql.append(" and t.big_bonus = ? ");
			       paramList.add(checkTicket.getBigBonus());
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
			if (Utils.isNotEmpty(queryBean.getAmountDiffStatus()) && queryBean.getAmountDiffStatus() != 0) {
            	if(queryBean.getAmountDiffStatus() == 1){
            		sql.append(" and t.amount_diff = 0 ");
            	}else {
            		sql.append(" and t.amount_diff > 0 ");
				}
            }
            if (Utils.isNotEmpty(queryBean.getFixBonusDiffStatus()) && queryBean.getFixBonusDiffStatus() != 0) {
            	if(queryBean.getFixBonusDiffStatus() == 1){
            		sql.append(" and t.fix_bonus_amount_diff = 0 ");
            	}else {
            		sql.append(" and t.fix_bonus_amount_diff > 0 ");
				}
            }
            if (Utils.isNotEmpty(queryBean.getBonusDiffStatus()) && queryBean.getBonusDiffStatus() != 0) {
            	if(queryBean.getBonusDiffStatus() == 1){
            		sql.append(" and t.bonus_amount_diff = 0 ");
            	}else {
            		sql.append(" and t.bonus_amount_diff > 0 ");
				}
            }
            
            if (Utils.isNotEmpty(queryBean.getTicketDiffStatus()) && queryBean.getTicketDiffStatus() != 0) {
            	if(queryBean.getTicketDiffStatus() == 1){
            		sql.append(" and t.ticket_status_diff = 1 ");
            	}else {
            		sql.append(" and t.ticket_status_diff = 0 ");
				}
            }
		}
		List<Map<String, Object>> dataList = jdbcTemplate.queryForList(sql.toString(), paramList.toArray());
        if (Utils.isNotEmpty(dataList)) {
            return dataList.get(0);
        }
		return null;
	}
	
	
}
