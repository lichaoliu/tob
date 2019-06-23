/**
 * 
 */
package com.cndym.servlet.manages.ticketNotice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 朱林虎
 * 根据不同的出票口处理不同的出票通知，
 * 如果通知需要返回结果，则返回相应的结果
 * 否则，返回null
 */
public interface IManageTicketNotice {
	public String manageTicketNotice(HttpServletRequest request);
}
