package com.cndym.service.subIssue.impl;

import com.cndym.bean.tms.SubIssueForBeiDan;
import com.cndym.cache.XMemcachedClientWrapper;
import com.cndym.constant.Constants;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import com.cndym.service.ISubIssueForBeiDanService;
import com.cndym.service.subIssue.BaseSubIssueOperator;
import com.cndym.service.subIssue.bean.SubIssueComm;
import com.cndym.utils.Utils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * 作者：邓玉明
 * email：cndym@163.com
 * QQ：757579248
 * 时间: 12-2-8 上午11:42
 */
@Repository
public class Comm400SubIssueOperator extends BaseSubIssueOperator {

    private static final Logger logger = Logger.getLogger(Comm400SubIssueOperator.class);

    @Resource
    private ISubIssueForBeiDanService subIssueForBeiDanService;

    @Resource
    private XMemcachedClientWrapper memcachedClientWrapper;

    private static final String LOTTERY_CODE = "400";


    @Override
    public SubIssueComm getSubIssueComm(String issue, String sn, String playCode, String pollCode) {

        StringBuffer key = new StringBuffer();
        key.append(LOTTERY_CODE).append(".").append(issue).append(".");
        key.append(sn);

        SubIssueForBeiDan subIssueForBeiDan = null;
        if (Utils.isNotEmpty(memcachedClientWrapper.get(key.toString()))) {
            subIssueForBeiDan = (SubIssueForBeiDan) memcachedClientWrapper.get(key.toString());
        }
        if (!Utils.isNotEmpty(subIssueForBeiDan)) {
	        subIssueForBeiDan = subIssueForBeiDanService.getSubIssueForBeiDanByIssueSn(issue, sn, Constants.SUB_ISSUE_END_OPERATOR_DEFAULT);
	        if (null == subIssueForBeiDan) {
	            throw new CndymException(ErrCode.E8119);
	        }
	        memcachedClientWrapper.set(key.toString(), 0, subIssueForBeiDan);
        }
        SubIssueComm subIssueComm = new SubIssueComm();
        subIssueComm.setLotteryCode(LOTTERY_CODE);
        subIssueComm.setPollCode("00");
        subIssueComm.setIssue(issue);
        subIssueComm.setMatchId(sn);
        subIssueComm.setWeek(subIssueForBeiDan.getWeek());
        subIssueComm.setEndTime(subIssueForBeiDan.getEndTime());
        subIssueComm.setEndOperator(subIssueForBeiDan.getEndOperator());
        subIssueComm.setBonusOperator(subIssueForBeiDan.getBonusOperator());
        subIssueComm.setDanShiEndTime(subIssueForBeiDan.getEndDanShiTime());
        subIssueComm.setFuShiEndTime(subIssueForBeiDan.getEndFuShiTime());
        memcachedClientWrapper.set(key.toString(), 0, subIssueForBeiDan);
        return subIssueComm;
    }

    public ISubIssueForBeiDanService getSubIssueForBeiDanService() {
        return subIssueForBeiDanService;
    }

    public void setSubIssueForBeiDanService(ISubIssueForBeiDanService subIssueForBeiDanService) {
        this.subIssueForBeiDanService = subIssueForBeiDanService;
    }
}
