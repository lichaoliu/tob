package com.cndym.admin.service;

import com.cndym.bean.query.TicketQueryBean;
import com.cndym.bean.tms.Ticket;
import com.cndym.service.IGenericeService;
import com.cndym.utils.hibernate.PageBean;
import java.util.Map;

/**
 * User: 梁桂立 Date: 14-4-14   下午18:00
 */
public interface IPostTicketService extends IGenericeService<Ticket> {
	
    public PageBean getPageBeanByPara(TicketQueryBean queryBean, Integer page, Integer pageSize, Map<String,Object> map);

    public Map<String, Object> getTicketCount(TicketQueryBean queryBean);
    
    public PageBean getPageBeanByParaToMember(TicketQueryBean queryBean, Integer page, Integer pageSize, Map<String,Object> map);

    public Map<String, Object> getTicketCountToMember(TicketQueryBean queryBean);

}