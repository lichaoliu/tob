package com.cndym.service;

import com.cndym.bean.tms.SubIssueForJingCaiZuQiu;
import com.cndym.utils.hibernate.PageBean;

import java.util.List;

/**
 * User: 邓玉明 Date: 11-3-27 下午11:00
 */
public interface ISubIssueForJingCaiZuQiuService extends IGenericeService<SubIssueForJingCaiZuQiu> {

    public int doUpdateBonus(String issue, String sn);

    public SubIssueForJingCaiZuQiu getSubIssueForJingCaiZuQiuByIssueSn(String issue, String sn);

    public List getSubIssueForJingCaiZuQiuListByDateBonusOperator(Integer status);

    public SubIssueForJingCaiZuQiu getSubIssueForJingCaiZuQiuByIssueSn(String issue, String sn, String pollCode, String playId);

    public List<SubIssueForJingCaiZuQiu> getSubIssueForJingCaiZuQiuListByDate(String date);

    public List<SubIssueForJingCaiZuQiu> getSubIssueForJingCaiZuQiuListByPera(String day, String endTime, String playCode, String pollCode);

    public PageBean getPageBeanForJob(Integer page, Integer pageSize);

    public int updateForJob(String issue, String sn, Integer endOperator);

    public int updateEndOperator();

    public PageBean getSubIssueForJingCaiZuQiuResultListByParaDesc(SubIssueForJingCaiZuQiu subIssueForJingCaiZuQiu, Integer page, Integer pageSize);

    public PageBean getSubIssueForJingCaiZuQiuListByParaDesc(SubIssueForJingCaiZuQiu subIssueForJingCaiZuQiu, Integer page, Integer pageSize);

    public SubIssueForJingCaiZuQiu getSubIssueForJingCaiZuQiuBySnDate(String sn, String date, String playCode, String pollCode);

    public PageBean getSubIssueForJingCaiZuQiuListByPara(SubIssueForJingCaiZuQiu subIssueForJingCaiZuQiu, Integer page, Integer pageSize);

    public SubIssueForJingCaiZuQiu getSubIssueForJingCaiZuQiu(Long id);

    public int doUpdateOperatorForSn(String issue, String sn, Integer operator);

    public SubIssueForJingCaiZuQiu getSubIssueForJingCaiZuQiuBySnDate(String sn, String date, String playCode, String pollCode, int endOperator);
}
