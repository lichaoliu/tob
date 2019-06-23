package com.cndym.dao;

import com.cndym.bean.tms.SubTicket;
import com.cndym.utils.hibernate.PageBean;

import java.util.Date;
import java.util.List;

/**
 * User: mcs Date: 12-12-4 Time: 下午4:07
 */
public interface ISubTicketDao extends IGenericDao<SubTicket> {

	public List<SubTicket> findSubTicketList(String ticketId);

	public SubTicket getSubTicketForUpdate(String subTicketId);

	public List<SubTicket> findSubTicketListEx(String ticketId);

	public SubTicket findSubTicketBySubTicketId(String subTicketId);

	public int doNoBonus(String lotteryCode, String issue, String postCode);

	public PageBean getSendedSubTicket(String lotteryCode, String postCode, Date sendTime, int page, int pageSize);

    public PageBean getSendedSubTicket(String lotteryCode,String postCode,int page,int pageSize);
}
