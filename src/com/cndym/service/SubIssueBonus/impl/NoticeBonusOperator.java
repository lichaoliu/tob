package com.cndym.service.SubIssueBonus.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.cndym.bean.tms.MainIssue;
import com.cndym.constant.Constants;
import com.cndym.service.IMainIssueService;
import com.cndym.service.ITicketService;
import com.cndym.service.SubIssueBonus.BaseSubIssueBonusOperator;

@Component
public class NoticeBonusOperator extends BaseSubIssueBonusOperator {
	@Resource
	private ITicketService ticketService;

	@Override
	public void noticeBonusGp(String lotteryCode, String issue) {
		try {
			ticketService.doBonusAmountToAccount(lotteryCode, issue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
