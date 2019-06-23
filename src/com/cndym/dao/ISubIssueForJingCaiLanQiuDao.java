package com.cndym.dao;

import com.cndym.bean.tms.SubIssueForJingCaiLanQiu;
import com.cndym.utils.hibernate.PageBean;

import java.util.List;
import java.util.Map;

/**
 * User: 邓玉明 Date: 11-3-27 下午10:22
 */
public interface ISubIssueForJingCaiLanQiuDao extends IGenericDao<SubIssueForJingCaiLanQiu> {

    public SubIssueForJingCaiLanQiu getSubIssueForJingCaiLanQiuByIssueSn(String issue, String sn);

    public SubIssueForJingCaiLanQiu getSubIssueForJingCaiLanQiuByIssueSn(String issue, String sn, String playCode, String pollCode);

    public List<SubIssueForJingCaiLanQiu> getSubIssueForJingCaiLanQiuListByDate(String date);

    public List<SubIssueForJingCaiLanQiu> getSubIssueForJingCaiLanQiuListByPera(String date, String endTime, String playCode, String pollCode);

    public List getSubIssueForJingCaiLanQiuListByDateBonusOperator(Integer status);

    public PageBean getPageBeanForJob(Integer page, Integer pageSize);

    public int updateForJob(String issue, String sn, Integer endOperator);

    public int updateEndOperator();

    public SubIssueForJingCaiLanQiu getSubIssueForJingCaiLanQiuForUpdate(String issue, String sn);

    public List<Map<String, Object>> getSortListByTicketId(String ticketId);

    public PageBean getSubIssueForJingCaiLanQiuListByParaDesc(SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiu, Integer page, Integer pageSize);

    public SubIssueForJingCaiLanQiu getSubIssueForJingCaiLanQiuBySnDate(String sn, String date, String playCode, String pollCode);

    public PageBean getSubIssueForJingCaiLanQiuListByPara(SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiu, Integer page, Integer pageSize);

    public SubIssueForJingCaiLanQiu getSubIssueForJingCaiLanQiu(Long id);

    public int doUpdateOperatorForSn(String issue, String sn, Integer operator);

    public SubIssueForJingCaiLanQiu getSubIssueForJingCaiLanQiuBySnDate(String sn, String date, String playCode, String pollCode, int endOperator);
}
