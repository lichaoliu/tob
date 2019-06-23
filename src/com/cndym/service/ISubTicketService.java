package com.cndym.service;

import com.cndym.bean.tms.SubTicket;
import com.cndym.bean.tms.Ticket;
import com.cndym.utils.hibernate.PageBean;

import java.util.Date;
import java.util.List;

/**
 * User: mcs Date: 12-12-4 Time: 下午4:10
 */
public interface ISubTicketService extends IGenericeService<SubTicket> {

	public List<SubTicket> doSaveSubTicket(Ticket ticket);

	public List<SubTicket> findSubTicketList(String ticketId);

    public List<SubTicket> findSubTicketListEx(String ticketId);

	public int doUpdateSubTicketForSended(SubTicket subTicket);

	public List<SubTicket> doSaveSubTicketEx(Ticket temp, String postCode);

	public List<SubTicket> doSaveSubTicket(Ticket temp, String postCode);

	public int doUpdateSubTicketForSended(List<SubTicket> subList);

	public SubTicket findSubTicketBySubTicketId(String subTicketId);

	public int doNoBonus(String lotteryCode, String issue, String postCode);

	public PageBean getSendedSubTicket(String lotteryCode, String postCode, Date sendTime, int page, int pageSize);

	public PageBean getSendedSubTicket(String lotteryCode, String postCode, int page, int pageSize);
}
