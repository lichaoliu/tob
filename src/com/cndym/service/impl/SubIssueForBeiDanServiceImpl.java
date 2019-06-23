package com.cndym.service.impl;

import com.cndym.bean.tms.SubIssueForBeiDan;
import com.cndym.dao.*;
import com.cndym.service.ISubIssueForBeiDanService;
import com.cndym.utils.hibernate.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * User: 邓玉明
 * Date: 11-3-27 下午11:02
 */
@Service
public class SubIssueForBeiDanServiceImpl extends GenericServiceImpl<SubIssueForBeiDan, ISubIssueForBeiDanDao> implements ISubIssueForBeiDanService {
    @Resource
    private ISubIssueForBeiDanDao subIssueForBeiDanDao;
    @Resource
    private IAccountDao accountDao;

    @PostConstruct
    private void daoImplInit() {
        super.setDaoImpl(subIssueForBeiDanDao);
    }

    @Override
    public int doUpdateBonus(String issue, String sn) {
        return 0;
    }

    @Override
    public List<SubIssueForBeiDan> getSubIssueForBeiDanListByIssue(String issue) {
        return subIssueForBeiDanDao.getSubIssueForBeiDanListByIssue(issue);
    }

    @Override
    public SubIssueForBeiDan getSubIssueForBeiDanByIssueSn(String issue, String sn) {
        return subIssueForBeiDanDao.getSubIssueForBeiDanByIssueSn(issue, sn);
    }

    @Override
    public PageBean getPageBeanForJob(Integer page, Integer pageSize) {
        return subIssueForBeiDanDao.getPageBeanForJob(page, pageSize);
    }

    @Override
    public int updateForJob(String issue, String sn, Integer endOperator) {
        return subIssueForBeiDanDao.updateForJob(issue, sn, endOperator);
    }

    @Override
    public int updateEndOperator() {
        return subIssueForBeiDanDao.updateEndOperator();
    }


    @Override
    public SubIssueForBeiDan getSubIssueForBeiDan(Long id) {
        return subIssueForBeiDanDao.getSubIssueForBeiDan(id);
    }

    @Override
    public PageBean getSubIssueForBeiDanResultListByParaDesc(SubIssueForBeiDan subIssueForBeiDan, Integer page, Integer pageSize) {
        return subIssueForBeiDanDao.getSubIssueForBeiDanResultListByParaDesc(subIssueForBeiDan, page, pageSize);
    }

    @Override
    public int doUpdateOperatorForSn(String issue, String sn, Integer operator) {
        return subIssueForBeiDanDao.doUpdateOperatorForSn(issue, sn, operator);
    }

    @Override
    public SubIssueForBeiDan getSubIssueForBeiDanByIssueSn(String issue, String sn, int endOperator) {
        return subIssueForBeiDanDao.getSubIssueForBeiDanByIssueSn(issue,sn,endOperator);
    }
}
