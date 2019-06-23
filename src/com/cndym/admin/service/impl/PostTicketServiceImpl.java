package com.cndym.admin.service.impl;

import com.cndym.bean.query.TicketQueryBean;
import com.cndym.bean.tms.*;
import com.cndym.admin.dao.IPostTicketDao;
import com.cndym.admin.service.IPostTicketService;
import com.cndym.service.impl.GenericServiceImpl;
import com.cndym.utils.hibernate.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * User: 梁桂立Date: 14-4-15 下午11:02
 */
@Service
public class PostTicketServiceImpl extends GenericServiceImpl<Ticket, IPostTicketDao> implements IPostTicketService {
    @Autowired
    private IPostTicketDao ticketDao;

    @PostConstruct
    public void initDao() {
        super.setDaoImpl(ticketDao);
    }
    
    @Override
    public PageBean getPageBeanByPara(TicketQueryBean queryBean, Integer page, Integer pageSize,Map<String,Object> map) {
        return ticketDao.getPageBeanByPara(queryBean, page, pageSize, map);
    }

    @Override
    public Map<String, Object> getTicketCount(TicketQueryBean queryBean) {
        return ticketDao.getTicketCount(queryBean);
    }
    
    @Override
    public PageBean getPageBeanByParaToMember(TicketQueryBean queryBean, Integer page, Integer pageSize, Map<String,Object> map){
    	return ticketDao.getPageBeanByParaToMember(queryBean, page, pageSize,map);
    }
    
    @Override
    public Map<String, Object> getTicketCountToMember(TicketQueryBean queryBean){
    	return ticketDao.getTicketCountToMember(queryBean);
    }

}
