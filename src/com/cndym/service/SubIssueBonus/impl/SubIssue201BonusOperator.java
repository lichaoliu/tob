package com.cndym.service.SubIssueBonus.impl;

import com.cndym.bean.tms.BonusLog;
import com.cndym.service.ISubIssueForJingCaiLanQiuService;
import com.cndym.service.SubIssueBonus.BaseSubIssueBonusOperator;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 作者：邓玉明
 * email：cndym@163.com
 * QQ：757579248
 * 时间: 12-2-15 下午10:59
 */
@Repository
public class SubIssue201BonusOperator extends BaseSubIssueBonusOperator {
    @Resource
    private ISubIssueForJingCaiLanQiuService subIssueForJingCaiLanQiuService;

    @Override
    public int bonusOperator(String issue, String sn) {
        return subIssueForJingCaiLanQiuService.doUpdateBonus(issue, sn);
    }
}
