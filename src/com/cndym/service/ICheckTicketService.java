/**
 * 
 */
package com.cndym.service;

import java.util.Map;

import com.cndym.bean.query.CheckTicketQueryBean;
import com.cndym.bean.tms.CheckTicket;
import com.cndym.utils.hibernate.PageBean;

/**
 * @author 朱林虎
 *
 */
public interface ICheckTicketService {
	
	/**
	 * 根据票号查询校验票信息
	 * @param ticketId
	 * @return
	 */
	public CheckTicket getCheckTicketByTicketId(String ticketId);
	
	/**
	 * 查询校验票列表
	 * @param checkTicket
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public PageBean getCheckTicketListByPara(CheckTicketQueryBean queryBean, Integer page, Integer pageSize);
	
	/**
	 * 获取票的中奖金额等
	 * @param queryBean
	 * @return
	 */
	public Map<String, Object> getCheckTicketCount(CheckTicketQueryBean queryBean);
}
