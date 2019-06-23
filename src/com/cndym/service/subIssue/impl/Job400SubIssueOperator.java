package com.cndym.service.subIssue.impl;

import com.cndym.bean.tms.SubIssueForBeiDan;
import com.cndym.bean.tms.SubIssueForJingCaiLanQiu;
import com.cndym.cache.XMemcachedClientWrapper;
import com.cndym.constant.Constants;
import com.cndym.lottery.lotteryInfo.LotteryList;
import com.cndym.lottery.lotteryInfo.bean.LotteryClass;
import com.cndym.service.ISubIssueForBeiDanService;
import com.cndym.service.subIssue.BaseSubIssueOperator;
import com.cndym.service.subIssue.bean.SubIssueComm;
import com.cndym.utils.Utils;
import com.cndym.utils.hibernate.PageBean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：邓玉明
 * email：cndym@163.com
 * QQ：757579248
 * 时间: 12-2-8 上午11:42
 */
@Repository
public class Job400SubIssueOperator extends BaseSubIssueOperator {
    private static final Logger logger = Logger.getLogger(Job400SubIssueOperator.class);
    @Resource
    private ISubIssueForBeiDanService subIssueForBeiDanService;
    @Resource
    private XMemcachedClientWrapper memcachedClientWrapper;

    @Override
    public void operator() {
        int page = 1, pageSize = 50;//一次不可能超过10场结期
        PageBean pageBean = subIssueForBeiDanService.getPageBeanForJob(page, pageSize);
        List<SubIssueForBeiDan> subIssueForBeiDanList = pageBean.getPageContent();
        for (SubIssueForBeiDan subIssueForBeiDan : subIssueForBeiDanList) {
            long curr = new Date().getTime();
            long endTime = subIssueForBeiDan.getEndFuShiTime().getTime();
            if (curr >= endTime) {
                subIssueForBeiDanService.updateForJob(subIssueForBeiDan.getIssue(), subIssueForBeiDan.getSn(), Constants.SUB_ISSUE_END_OPERATOR_END);
                clearMatch(subIssueForBeiDan.getLotteryCode(), subIssueForBeiDan.getIssue(), subIssueForBeiDan.getSn());
            }
        }
    }

    private void clearMatch(String lotteryCode, String issue, String sn) {
        StringBuffer key = new StringBuffer();
        key.append(lotteryCode).append(".").append(issue).append(".");
        key.append(sn);
        memcachedClientWrapper.delete(key.toString());
    }

    public ISubIssueForBeiDanService getSubIssueForBeiDanService() {
        return subIssueForBeiDanService;
    }

    public void setSubIssueForBeiDanService(ISubIssueForBeiDanService subIssueForBeiDanService) {
        this.subIssueForBeiDanService = subIssueForBeiDanService;
    }
}
