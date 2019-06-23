/**
 * 
 */
package com.cndym.sendClient.ltkj.bean;

/**
 * 查询出票状态bean
 * @author 朱林虎
 *
 */
public class TraceInfo {
	
	private Integer ticketCount;
	private String traceCodes;
	
	/**
	 * @param ticketCount
	 * @param traceCodes
	 */
	public TraceInfo(Integer ticketCount, String traceCodes) {
		super();
		this.ticketCount = ticketCount;
		this.traceCodes = traceCodes;
	}
	public Integer getTicketCount() {
		return ticketCount;
	}
	public void setTicketCount(Integer ticketCount) {
		this.ticketCount = ticketCount;
	}
	public String getTraceCodes() {
		return traceCodes;
	}
	public void setTraceCodes(String traceCodes) {
		this.traceCodes = traceCodes;
	}
	
	
}
