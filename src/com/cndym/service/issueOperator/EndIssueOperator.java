package com.cndym.service.issueOperator;

import com.cndym.bean.tms.MainIssue;
import com.cndym.cache.XMemcachedClientWrapper;
import com.cndym.constant.Constants;
import com.cndym.service.IMainIssueService;
import com.cndym.service.IPostIssueService;
import com.cndym.service.ITicketService;
import com.cndym.utils.SpringUtils;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * 作者：邓玉明 时间：11-4-30 下午5:55 QQ：757579248 email：cndym@163.com
 */
@Repository
public class EndIssueOperator implements IIssueOperator {

	private Logger logger = Logger.getLogger(getClass());
	@Resource
	private IMainIssueService mainIssueService;
	@Resource
	private ITicketService ticketService;
	@Resource
	private IPostIssueService postIssueService;

	@Override
	public void operator(MainIssue mainIssue) {
		logger.info("进入彩种(" + mainIssue.getLotteryCode() + ")期次(" + mainIssue.getName() + ")结期处理");
		// 更新期次状态
		mainIssueService.doUpdateStatus(Constants.ISSUE_STATUS_END, mainIssue.getSendStatus(), mainIssue.getBonusStatus(), mainIssue.getId());

		postIssueService.doUpdateStatus(Constants.ISSUE_STATUS_END, mainIssue.getLotteryCode(), mainIssue.getName());
		try {
			// 过期处理
			//List<Ticket> ticketList = ticketService.getTicketsForEndIssue(mainIssue.getLotteryCode(), mainIssue.getName());
			//for (Ticket ticket : ticketList) {
			//	ticketService.doHhandTicketFailed(ticket.getTicketId());
			//}
		} catch (Exception e) {
			logger.error("ticket", e);
		}

		XMemcachedClientWrapper memcachedClientWrapper = (XMemcachedClientWrapper) SpringUtils.getBean("xmemcachedClientWrapper");
		try {
			memcachedClientWrapper.getMemcachedClient().delete(mainIssue.getLotteryCode() + mainIssue.getName());
			memcachedClientWrapper.getMemcachedClient().delete("RESENDDENDLINE" + mainIssue.getLotteryCode() + mainIssue.getName());
		} catch (Exception e) {
			logger.error("cache", e);
		}
		logger.info("彩种(" + mainIssue.getLotteryCode() + ")期次(" + mainIssue.getName() + ")结期处理结束");
	}
}
