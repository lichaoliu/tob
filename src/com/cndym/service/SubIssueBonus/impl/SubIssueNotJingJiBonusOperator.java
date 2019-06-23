package com.cndym.service.SubIssueBonus.impl;

import com.cndym.service.ISubIssueForBeiDanService;
import com.cndym.service.SubIssueBonus.BaseSubIssueBonusOperator;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * 作者：邓玉明
 * email：cndym@163.com
 * QQ：757579248
 * 时间: 12-2-15 下午10:59
 */
@Repository
public class SubIssueNotJingJiBonusOperator extends BaseSubIssueBonusOperator {
    @Resource
    private ISubIssueForBeiDanService subIssueForBeiDanService;

    @Override
    public int bonusOperator(String lotteryCode, String issue) {
        return subIssueForBeiDanService.doUpdateBonus(issue, issue);
    }
}
