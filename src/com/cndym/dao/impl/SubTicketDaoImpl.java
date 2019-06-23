package com.cndym.dao.impl;

import com.cndym.bean.tms.SubTicket;
import com.cndym.bean.tms.Ticket;
import com.cndym.constant.Constants;
import com.cndym.dao.ISubTicketDao;
import com.cndym.dao.impl.rowMapperBean.SubTicketRowMapper;
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
 * User: mcs Date: 12-12-4 Time: 下午4:08
 */

@Repository
public class SubTicketDaoImpl extends GenericDaoImpl<SubTicket> implements ISubTicketDao {

	@Resource
	private SessionFactory sessionFactoryTemp;
	@Resource
	private JdbcTemplate jdbcTemplate;

	@PostConstruct
	private void sessionFactoryInit() {
		super.setSessionFactory(sessionFactoryTemp);
	}

	@Override
	public List<SubTicket> findSubTicketList(String ticketId) {
		String sql = "select new SubTicket(subTicketId) from SubTicket where ticketId = ? ";
		return find(sql, new Object[] { ticketId });
	}

	@Override
	public List<SubTicket> findSubTicketListEx(String ticketId) {
		String sql = "from SubTicket where ticketId = ? ";
		return find(sql, new Object[] { ticketId });
	}

	@Override
	public SubTicket getSubTicketForUpdate(String subTicketId) {
		String sql = "select * from tms_sub_ticket t where t.sub_ticket_id=? for update";
		try {
			return jdbcTemplate.queryForObject(sql, new Object[] { subTicketId }, new SubTicketRowMapper());
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public SubTicket findSubTicketBySubTicketId(String subTicketId) {
		String sql = "From SubTicket t where subTicketId=?";
		List<SubTicket> ticketList = find(sql, new Object[] { subTicketId });
		if (ticketList.size() > 0) {
			return ticketList.get(0);
		}
		return null;
	}

	@Override
	public int doNoBonus(String lotteryCode, String issue, String postCode) {
		String sql = "update tms_sub_ticket set bonus_status = ?,bonus_time = ? where lottery_code = ? and issue = ? and ticket_status = ? and bonus_status = ? and post_code=? ";
		return jdbcTemplate.update(sql, new Object[] { Constants.BONUS_STATUS_NO, new Date(), lotteryCode, issue, Constants.TICKET_STATUS_SUCCESS, Constants.BONUS_STATUS_WAIT, postCode });
	}

	@Override
	public PageBean getSendedSubTicket(String lotteryCode, String postCode, Date sendTime, int page, int pageSize) {
		StringBuffer sql = new StringBuffer("From SubTicket t where t.ticketStatus = " + Constants.TICKET_STATUS_SENDING);
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

	@Override
	public PageBean getSendedSubTicket(String lotteryCode, String postCode, int page, int pageSize) {
		StringBuffer sql = new StringBuffer("From SubTicket t where t.ticketStatus = " + Constants.TICKET_STATUS_SENDING);
		List<HibernatePara> hibernateParas = new ArrayList<HibernatePara>();
		sql.append(" and t.lotteryCode=? ");
		hibernateParas.add(new HibernatePara(lotteryCode));
		sql.append(" and t.postCode=? ");
		hibernateParas.add(new HibernatePara(postCode));
		sql.append(" order by t.id ");
		return getPageBeanByPara(sql.toString(), hibernateParas, page, pageSize);
	}
}
