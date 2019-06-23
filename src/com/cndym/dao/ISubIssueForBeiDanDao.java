package com.cndym.dao;

import com.cndym.bean.tms.SubIssueForBeiDan;
import com.cndym.utils.hibernate.PageBean;

import java.util.List;
import java.util.Map;

/**
 * User: 邓玉明 Date: 11-3-27 下午10:22
 */
public interface ISubIssueForBeiDanDao extends IGenericDao<SubIssueForBeiDan> {
    public SubIssueForBeiDan getSubIssueForBeiDanByIssueSn(String issue, String sn);

    public List<SubIssueForBeiDan> getSubIssueForBeiDanListByIssue(String issue);

    public PageBean getPageBeanForJob(Integer page, Integer pageSize);

    public int updateForJob(String issue, String sn, Integer endOperator);

    public int updateEndOperator();

    public SubIssueForBeiDan getSubIssueForBeiDanForUpdate(String issue, String sn);

    public List<Map<String, Object>> getSortListByTicketId(String ticketId);

    public SubIssueForBeiDan getSubIssueForBeiDan(Long id);

    public PageBean getSubIssueForBeiDanResultListByParaDesc(SubIssueForBeiDan subIssueForBeiDan, Integer page, Integer pageSize);

    public int doUpdateOperatorForSn(String issue, String sn, Integer operator);

    public SubIssueForBeiDan getSubIssueForBeiDanByIssueSn(String issue, String sn, int endOperator);
}
