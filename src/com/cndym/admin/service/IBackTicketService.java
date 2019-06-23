package com.cndym.admin.service;

import com.cndym.adapter.tms.bean.NumberInfo;
import com.cndym.bean.query.TicketMainIssueBean;
import com.cndym.bean.query.TicketQueryBean;
import com.cndym.bean.tms.BonusLog;
import com.cndym.bean.tms.Ticket;
import com.cndym.service.IGenericeService;
import com.cndym.utils.hibernate.PageBean;
import com.cndym.utils.xml.parse.XmlBean;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IBackTicketService extends IGenericeService<Ticket> {
	/**
     * ticketList
     * @param queryBean
     * @param page
     * @param pageSize
     * @return
     */
	public PageBean getPageBeanByPara(TicketQueryBean queryBean, Integer page, Integer pageSize);

    public Map<String, Object> getTicketCount(TicketQueryBean queryBean);
    
    /**
     * ticketList NoSend
     * @param queryBean
     * @param page
     * @param pageSize
     * @return
     */
    public PageBean getPageBeanByParaForNoSend(TicketQueryBean queryBean, Integer page, Integer pageSize);
    
    public Map<String, Object> getTicketCountNoSend(TicketQueryBean queryBean);

    /**
     * sti
     * @param createStartTime
     * @param createEndTime
     * @param postCode
     * @return
     */
    public List<Map<String, Object>> findNoSendTickets(Date createStartTime, Date createEndTime, String postCode);
    
    public List<Map<String, Object>> findJcNoSendTickets(Date createStartTime, Date createEndTime, String postCode);
    
    public PageBean getPageBeanByPara(Ticket ticket, Integer page, Integer pageSize);
}