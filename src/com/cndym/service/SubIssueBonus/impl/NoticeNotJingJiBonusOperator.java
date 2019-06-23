package com.cndym.service.SubIssueBonus.impl;

import com.cndym.service.IMainIssueService;
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
public class NoticeNotJingJiBonusOperator extends BaseSubIssueBonusOperator {
    @Resource
    private IMainIssueService mainIssueService;

}
