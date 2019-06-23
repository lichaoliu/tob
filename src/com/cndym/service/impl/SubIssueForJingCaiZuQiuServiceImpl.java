package com.cndym.service.impl;

import com.cndym.bean.query.SubIssueJingCaiZuQiuResultBean;
import com.cndym.bean.tms.SubIssueForJingCaiZuQiu;
import com.cndym.dao.*;
import com.cndym.service.ISubIssueForJingCaiZuQiuService;
import com.cndym.servlet.ElTagUtils;
import com.cndym.utils.hibernate.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * User: 邓玉明 Date: 11-3-27 下午11:02
 */
@Service
public class SubIssueForJingCaiZuQiuServiceImpl extends GenericServiceImpl<SubIssueForJingCaiZuQiu, ISubIssueForJingCaiZuQiuDao> implements ISubIssueForJingCaiZuQiuService {
    @Resource
    private ISubIssueForJingCaiZuQiuDao subIssueForJingCaiZuQiuDao;
    @Resource
    private IAccountDao accountDao;

    @PostConstruct
    private void daoImplInit() {
        super.setDaoImpl(subIssueForJingCaiZuQiuDao);
    }

    @Override
    public int doUpdateBonus(String issue, String sn) {
        return 0;
    }

    @Override
    public SubIssueForJingCaiZuQiu getSubIssueForJingCaiZuQiuByIssueSn(String issue, String sn) {
        return subIssueForJingCaiZuQiuDao.getSubIssueForJingCaiZuQiuByIssueSn(issue, sn);
    }

    @Override
    public List getSubIssueForJingCaiZuQiuListByDateBonusOperator(Integer status) {
        return subIssueForJingCaiZuQiuDao.getSubIssueForJingCaiZuQiuListByDateBonusOperator(status);
    }

    @Override
    public List<SubIssueForJingCaiZuQiu> getSubIssueForJingCaiZuQiuListByDate(String date) {
        return subIssueForJingCaiZuQiuDao.getSubIssueForJingCaiZuQiuListByDate(date);
    }

    @Override
    public List<SubIssueForJingCaiZuQiu> getSubIssueForJingCaiZuQiuListByPera(String day, String endTime, String playCode, String pollCode) {
        return subIssueForJingCaiZuQiuDao.getSubIssueForJingCaiZuQiuListByPera(day, endTime, playCode, pollCode);
    }

    @Override
    public PageBean getPageBeanForJob(Integer page, Integer pageSize) {
        return subIssueForJingCaiZuQiuDao.getPageBeanForJob(page, pageSize);
    }

    @Override
    public int updateForJob(String issue, String sn, Integer endOperator) {
        return subIssueForJingCaiZuQiuDao.updateForJob(issue, sn, endOperator);
    }

    @Override
    public int updateEndOperator() {
        return subIssueForJingCaiZuQiuDao.updateEndOperator();
    }

    @Override
    public SubIssueForJingCaiZuQiu getSubIssueForJingCaiZuQiuByIssueSn(String issue, String sn, String pollCode, String playId) {
        return subIssueForJingCaiZuQiuDao.getSubIssueForJingCaiZuQiuByIssueSn(issue, sn, pollCode, playId);
    }

    @Override
    public PageBean getSubIssueForJingCaiZuQiuResultListByParaDesc(SubIssueForJingCaiZuQiu subIssueForJingCaiZuQiu, Integer page, Integer pageSize) {
        PageBean pageBean = subIssueForJingCaiZuQiuDao.getSubIssueForJingCaiZuQiuListByParaDesc(subIssueForJingCaiZuQiu, page, pageSize);
        List<SubIssueJingCaiZuQiuResultBean> subIssueJingCaiZuQiuResultBeanList = new ArrayList<SubIssueJingCaiZuQiuResultBean>();
        List<SubIssueForJingCaiZuQiu> subIssueForJingCaiZuQiuList = pageBean.getPageContent();
        SubIssueJingCaiZuQiuResultBean subIssueJingCaiZuQiuResultBean = null;
        for (SubIssueForJingCaiZuQiu subIssueForJingCaiZuQiuObj : subIssueForJingCaiZuQiuList) {
            subIssueJingCaiZuQiuResultBean = new SubIssueJingCaiZuQiuResultBean();

            Integer mainTeamScore = subIssueForJingCaiZuQiuObj.getMainTeamScore();
            Integer guestTeamScore = subIssueForJingCaiZuQiuObj.getGuestTeamScore();
            // 胜平负赛果
            subIssueJingCaiZuQiuResultBean.setSpfResult(ElTagUtils.spfScore(mainTeamScore, guestTeamScore, ""));
            // 让球胜平负赛果
            subIssueJingCaiZuQiuResultBean.setRqSpfResult(ElTagUtils.spfScore(mainTeamScore, guestTeamScore, subIssueForJingCaiZuQiuObj.getLetBall()));
            // 比分赛果
            subIssueJingCaiZuQiuResultBean.setBfResult(ElTagUtils.bfScore(mainTeamScore, guestTeamScore));
            // 总进球数 赛果
            subIssueJingCaiZuQiuResultBean.setZjqsResult(ElTagUtils.zjqsScore(mainTeamScore, guestTeamScore));
            // 半全场胜平负 赛果
            subIssueJingCaiZuQiuResultBean.setBqcspfResult(ElTagUtils.spfScore(subIssueForJingCaiZuQiuObj.getMainTeamHalfScore(), subIssueForJingCaiZuQiuObj.getGuestTeamHalfScore(), "0")
                    + ElTagUtils.spfScore(mainTeamScore, guestTeamScore, "0"));
            subIssueJingCaiZuQiuResultBean.setSubIssueForJingCaiZuQiu(subIssueForJingCaiZuQiuObj);
            subIssueJingCaiZuQiuResultBeanList.add(subIssueJingCaiZuQiuResultBean);
        }
        pageBean.setPageContent(subIssueJingCaiZuQiuResultBeanList);
        return pageBean;
    }

    @Override
    public PageBean getSubIssueForJingCaiZuQiuListByParaDesc(SubIssueForJingCaiZuQiu subIssueForJingCaiZuQiu, Integer page, Integer pageSize) {
        return subIssueForJingCaiZuQiuDao.getSubIssueForJingCaiZuQiuListByParaDesc(subIssueForJingCaiZuQiu, page, pageSize);
    }

    @Override
    public SubIssueForJingCaiZuQiu getSubIssueForJingCaiZuQiuBySnDate(String sn, String date, String playCode, String pollCode) {
        return subIssueForJingCaiZuQiuDao.getSubIssueForJingCaiZuQiuBySnDate(sn, date, playCode, pollCode);
    }

    @Override
    public PageBean getSubIssueForJingCaiZuQiuListByPara(SubIssueForJingCaiZuQiu subIssueForJingCaiZuQiu, Integer page, Integer pageSize) {
        return subIssueForJingCaiZuQiuDao.getSubIssueForJingCaiZuQiuListByPara(subIssueForJingCaiZuQiu, page, pageSize);
    }

    @Override
    public SubIssueForJingCaiZuQiu getSubIssueForJingCaiZuQiu(Long id) {
        return subIssueForJingCaiZuQiuDao.getSubIssueForJingCaiZuQiu(id);
    }

    @Override
    public int doUpdateOperatorForSn(String issue, String sn, Integer operator) {
        return subIssueForJingCaiZuQiuDao.doUpdateOperatorForSn(issue, sn, operator);
    }

    @Override
    public SubIssueForJingCaiZuQiu getSubIssueForJingCaiZuQiuBySnDate(String sn, String date, String playCode, String pollCode, int endOperator) {
        return subIssueForJingCaiZuQiuDao.getSubIssueForJingCaiZuQiuBySnDate(sn, date, playCode, pollCode, endOperator);
    }

}
