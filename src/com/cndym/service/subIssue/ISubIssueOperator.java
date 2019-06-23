package com.cndym.service.subIssue;

import com.cndym.service.subIssue.bean.SubIssueComm;

/**
 * 作者：邓玉明
 * email：cndym@163.com
 * QQ：757579248
 * 时间: 12-2-1 下午4:19
 */
public interface ISubIssueOperator {
    public void operator(String xml);

    public void operator();

    public SubIssueComm getSubIssueComm(String issue, String matchId, String playCode, String pollCode);
}
