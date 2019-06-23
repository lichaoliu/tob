package com.cndym.admin.dao;

import com.cndym.bean.query.TicketQueryBean;
import com.cndym.bean.tms.Ticket;
import com.cndym.dao.IGenericDao;
import com.cndym.utils.hibernate.PageBean;

import java.util.Map;

/**
 * User: 梁桂立 Date: 14-04-14 下午10:52
 */
public interface IPostTicketDao extends IGenericDao<Ticket> {

    public PageBean getPageBeanByPara(TicketQueryBean queryBean, Integer page, Integer pageSize,Map<String,Object> map);

    public Map<String, Object> getTicketCount(TicketQueryBean queryBean);
    
    public PageBean getPageBeanByParaToMember(TicketQueryBean queryBean, Integer page, Integer pageSize,Map<String,Object> map);

    public Map<String, Object> getTicketCountToMember(TicketQueryBean queryBean);
}
