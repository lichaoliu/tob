/**
 * 
 */
package com.cndym.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cndym.bean.query.CheckTicketQueryBean;
import com.cndym.bean.tms.CheckTicket;
import com.cndym.dao.ICheckTicketDao;
import com.cndym.service.ICheckTicketService;
import com.cndym.utils.hibernate.PageBean;

/**
 * @author 朱林虎
 *
 */
@Service
public class CheckTicketServiceImpl implements ICheckTicketService {
	
	@Autowired
	private ICheckTicketDao checkTicketDao;

	@Override
	public CheckTicket getCheckTicketByTicketId(String ticketId) {
		return checkTicketDao.getCheckTicketByTicketId(ticketId);
	}

	@Override
	public PageBean getCheckTicketListByPara(CheckTicketQueryBean queryBean,
			Integer page, Integer pageSize) {
		return checkTicketDao.getCheckTicketListByPara(queryBean, page, pageSize);
	}

	@Override
	public Map<String, Object> getCheckTicketCount(CheckTicketQueryBean queryBean) {
		return checkTicketDao.getCheckTicketCount(queryBean);
	}

}
