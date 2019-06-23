package com.cndym.service;

import com.cndym.bean.tms.SubIssueForBeiDan;
import com.cndym.utils.hibernate.PageBean;

import java.util.List;

/**
 * User: 邓玉明
 * Date: 11-3-27 下午11:00
 */
public interface ISubIssueForBeiDanService extends IGenericeService<SubIssueForBeiDan> {

    public int doUpdateBonus(String issue, String sn);

    public List<SubIssueForBeiDan> getSubIssueForBeiDanListByIssue(String issue);

    public SubIssueForBeiDan getSubIssueForBeiDanByIssueSn(String issue, String sn);

    public PageBean getPageBeanForJob(Integer page, Integer pageSize);

    public int updateForJob(String issue, String sn, Integer endOperator);

    public int updateEndOperator();

    public PageBean getSubIssueForBeiDanResultListByParaDesc(SubIssueForBeiDan subIssueForBeiDan, Integer page, Integer pageSize);

    public SubIssueForBeiDan getSubIssueForBeiDan(Long id);

    public int doUpdateOperatorForSn(String issue, String sn, Integer operator);

    public SubIssueForBeiDan getSubIssueForBeiDanByIssueSn(String issue, String sn, int endOperator);
}
