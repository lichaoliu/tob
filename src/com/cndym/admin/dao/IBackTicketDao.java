package com.cndym.admin.dao;

import com.cndym.bean.query.TicketQueryBean;
import com.cndym.bean.tms.Ticket;
import com.cndym.dao.IGenericDao;
import com.cndym.utils.hibernate.PageBean;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IBackTicketDao extends IGenericDao<Ticket> {

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
