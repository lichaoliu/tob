package com.cndym.service.SubIssueBonus.impl;

import com.cndym.sendClient.ISendClientOperator;
import com.cndym.service.ISubIssueForJingCaiZuQiuService;
import com.cndym.service.ITicketService;
import com.cndym.service.SubIssueBonus.BaseSubIssueBonusOperator;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * 作者：邓玉明
 * email：cndym@163.com
 * QQ：757579248
 * 时间: 12-2-16 上午12:59
 */
@Repository
public class Notice200BonusOperator extends BaseSubIssueBonusOperator {
    @Resource
    private ISubIssueForJingCaiZuQiuService subIssueForJingCaiZuQiuService;
    @Resource
    private ITicketService ticketService;





}
