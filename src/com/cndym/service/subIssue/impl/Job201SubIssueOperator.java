package com.cndym.service.subIssue.impl;

import com.cndym.bean.tms.SubIssueForJingCaiLanQiu;
import com.cndym.cache.XMemcachedClientWrapper;
import com.cndym.constant.Constants;
import com.cndym.lottery.lotteryInfo.LotteryList;
import com.cndym.lottery.lotteryInfo.bean.LotteryClass;
import com.cndym.service.ISubIssueForJingCaiLanQiuService;
import com.cndym.service.subIssue.BaseSubIssueOperator;
import com.cndym.service.subIssue.bean.SubIssueComm;
import com.cndym.utils.SpringUtils;
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
public class Job201SubIssueOperator extends BaseSubIssueOperator {

    private static final Logger logger = Logger.getLogger(Job201SubIssueOperator.class);

    @Resource
    private ISubIssueForJingCaiLanQiuService subIssueForJingCaiLanQiuService;
    @Resource
    private XMemcachedClientWrapper memcachedClientWrapper;

    @Override
    public void operator() {
        int page = 1, pageSize = 50;//一次不可能超过10场结期
        PageBean pageBean = subIssueForJingCaiLanQiuService.getPageBeanForJob(page, pageSize);
        List<SubIssueForJingCaiLanQiu> subIssueForJingCaiLanQiuList = pageBean.getPageContent();
        for (SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiu : subIssueForJingCaiLanQiuList) {
            long curr = new Date().getTime();
            long endTime = subIssueForJingCaiLanQiu.getEndFuShiTime().getTime();
            if (curr >= endTime) {
                subIssueForJingCaiLanQiuService.updateForJob(subIssueForJingCaiLanQiu.getIssue(), subIssueForJingCaiLanQiu.getSn(), Constants.SUB_ISSUE_END_OPERATOR_END);
                clearMatch(subIssueForJingCaiLanQiu.getLotteryCode(), subIssueForJingCaiLanQiu.getIssue(), subIssueForJingCaiLanQiu.getSn());
            }
        }
    }

    private void clearMatch(String lotteryCode, String issue, String sn) {

        Map<String, String> lotteryMap = new HashMap<String, String>();
        lotteryMap.put("00", "02");
        lotteryMap.put("01", "01");
        lotteryMap.put("02", "01");
        lotteryMap.put("04", "01");
        for (String lotteryKey : lotteryMap.keySet()) {
            StringBuffer key = new StringBuffer();
            key.append(lotteryCode).append(".").append(issue).append(".");
            key.append(lotteryKey).append(".").append(lotteryMap.get(lotteryKey)).append(".");
            key.append(sn);
            memcachedClientWrapper.delete(key.toString());
        }
    }

    public ISubIssueForJingCaiLanQiuService getSubIssueForJingCaiLanQiuService() {
        return subIssueForJingCaiLanQiuService;
    }

    public void setSubIssueForJingCaiLanQiuService(ISubIssueForJingCaiLanQiuService subIssueForJingCaiLanQiuService) {
        this.subIssueForJingCaiLanQiuService = subIssueForJingCaiLanQiuService;
    }
}
