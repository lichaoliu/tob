package com.cndym.service.impl;

import com.cndym.bean.query.SubIssueJingCaiLanQiuResultBean;
import com.cndym.bean.tms.SubIssueForJingCaiLanQiu;
import com.cndym.dao.*;
import com.cndym.service.ISubIssueForJingCaiLanQiuService;
import com.cndym.servlet.ElTagUtils;
import com.cndym.utils.Utils;
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
public class SubIssueForJingCaiLanQiuServiceImpl extends GenericServiceImpl<SubIssueForJingCaiLanQiu, ISubIssueForJingCaiLanQiuDao> implements ISubIssueForJingCaiLanQiuService {
    @Resource
    private ISubIssueForJingCaiLanQiuDao subIssueForJingCaiLanQiuDao;
    @Resource
    private IAccountDao accountDao;

    @PostConstruct
    private void daoImplInit() {
        super.setDaoImpl(subIssueForJingCaiLanQiuDao);
    }

    @Override
    public int doUpdateBonus(String issue, String sn) {
        return 0; // To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<SubIssueForJingCaiLanQiu> getSubIssueForJingCaiLanQiuListByDate(String date) {
        return subIssueForJingCaiLanQiuDao.getSubIssueForJingCaiLanQiuListByDate(date);
    }

    @Override
    public List<SubIssueForJingCaiLanQiu> getSubIssueForJingCaiLanQiuListByPera(String date, String endTime, String playCode, String pollCode) {
        return subIssueForJingCaiLanQiuDao.getSubIssueForJingCaiLanQiuListByPera(date, endTime, playCode, pollCode);
    }

    @Override
    public List getSubIssueForJingCaiLanQiuListByDateBonusOperator(Integer status) {
        return subIssueForJingCaiLanQiuDao.getSubIssueForJingCaiLanQiuListByDateBonusOperator(status);
    }

    @Override
    public SubIssueForJingCaiLanQiu getSubIssueForJingCaiLanQiuByIssueSn(String issue, String sn) {
        return subIssueForJingCaiLanQiuDao.getSubIssueForJingCaiLanQiuByIssueSn(issue, sn);
    }

    @Override
    public PageBean getPageBeanForJob(Integer page, Integer pageSize) {
        return subIssueForJingCaiLanQiuDao.getPageBeanForJob(page, pageSize);
    }

    @Override
    public int updateForJob(String issue, String sn, Integer endOperator) {
        return subIssueForJingCaiLanQiuDao.updateForJob(issue, sn, endOperator);
    }

    @Override
    public int updateEndOperator() {
        return subIssueForJingCaiLanQiuDao.updateEndOperator();
    }

    @Override
    public PageBean getSubIssueForJingCaiLanQiuResultListByParaDesc(SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiu, Integer page, Integer pageSize) {
        PageBean pageBean = subIssueForJingCaiLanQiuDao.getSubIssueForJingCaiLanQiuListByParaDesc(subIssueForJingCaiLanQiu, page, pageSize);
        List<SubIssueJingCaiLanQiuResultBean> subIssueForJingCaiLanQiuTempList = new ArrayList<SubIssueJingCaiLanQiuResultBean>();
        List<SubIssueForJingCaiLanQiu> subIssueForJingCaiLanQiuList = pageBean.getPageContent();
        SubIssueJingCaiLanQiuResultBean subIssueJingCaiLanQiuResultBean = null;
        for (SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiuObj : subIssueForJingCaiLanQiuList) {
            subIssueJingCaiLanQiuResultBean = new SubIssueJingCaiLanQiuResultBean();
            // 单关(让分胜负)
            SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiuForRFSF = subIssueForJingCaiLanQiuDao.getSubIssueForJingCaiLanQiuBySnDate(subIssueForJingCaiLanQiuObj.getSn(),
                    subIssueForJingCaiLanQiuObj.getIssue(), "02", "01");
            // 单关(大小分)
            SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiuForDXF = subIssueForJingCaiLanQiuDao.getSubIssueForJingCaiLanQiuBySnDate(subIssueForJingCaiLanQiuObj.getSn(),
                    subIssueForJingCaiLanQiuObj.getIssue(), "04", "01");

            Integer mainTeamScore = subIssueForJingCaiLanQiuObj.getMainTeamScore();
            Integer guestTeamScore = subIssueForJingCaiLanQiuObj.getGuestTeamScore();
            if (Utils.isNotEmpty(mainTeamScore) && Utils.isNotEmpty(guestTeamScore)) {
                // 赛果
                if (mainTeamScore > guestTeamScore) {
                    // 胜
                    subIssueJingCaiLanQiuResultBean.setSfResult("胜");
                } else if (mainTeamScore < guestTeamScore) {
                    // 负
                    subIssueJingCaiLanQiuResultBean.setSfResult("负");
                }

                if (Utils.isNotEmpty(subIssueForJingCaiLanQiuForRFSF)) {
                    // 让球
                    String letBall = subIssueForJingCaiLanQiuForRFSF.getLetBall();
                    // 赛果
                    String rfshResult = "胜";
                    if (mainTeamScore + Float.valueOf(letBall) < guestTeamScore) {
                        rfshResult = "负";
                    }
                    subIssueJingCaiLanQiuResultBean.setRfshResult(rfshResult);
                    subIssueForJingCaiLanQiuObj.setLetBall(letBall);
                }
                subIssueJingCaiLanQiuResultBean.setSfcResult(ElTagUtils.sfcScore(mainTeamScore, guestTeamScore));

                if (Utils.isNotEmpty(subIssueForJingCaiLanQiuForDXF)) {
                    String preCast = subIssueForJingCaiLanQiuForDXF.getPreCast();
                    String dxfResult = ElTagUtils.dxfScore(mainTeamScore, guestTeamScore, preCast);
                    subIssueJingCaiLanQiuResultBean.setDxfResult(dxfResult);
                    subIssueForJingCaiLanQiuObj.setPreCast(preCast);
                }
            }
            subIssueForJingCaiLanQiuObj.setIssue(Utils.formatDate2Str(Utils.formatDate(subIssueForJingCaiLanQiuObj.getIssue(), "yyyyMMdd"), "yyyy-MM-dd"));
            subIssueJingCaiLanQiuResultBean.setSubIssueForJingCaiLanQiu(subIssueForJingCaiLanQiuObj);
            subIssueForJingCaiLanQiuTempList.add(subIssueJingCaiLanQiuResultBean);
        }
        pageBean.setPageContent(subIssueForJingCaiLanQiuTempList);
        return pageBean;
    }

    @Override
    public PageBean getSubIssueForJingCaiLanQiuListByParaDesc(SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiu, Integer page, Integer pageSize) {
        PageBean pageBean = subIssueForJingCaiLanQiuDao.getSubIssueForJingCaiLanQiuListByParaDesc(subIssueForJingCaiLanQiu, page, pageSize);
        List<SubIssueForJingCaiLanQiu> subIssueForJingCaiLanQiuTempList = new ArrayList<SubIssueForJingCaiLanQiu>();
        List<SubIssueForJingCaiLanQiu> subIssueForJingCaiLanQiuList = pageBean.getPageContent();
        for (SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiuObj : subIssueForJingCaiLanQiuList) {
            // 单关(让分胜负)
            SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiuForRFSF = subIssueForJingCaiLanQiuDao.getSubIssueForJingCaiLanQiuBySnDate(subIssueForJingCaiLanQiuObj.getSn(),
                    subIssueForJingCaiLanQiuObj.getIssue(), "02", "01");
            // 单关(大小分)
            SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiuForDXF = subIssueForJingCaiLanQiuDao.getSubIssueForJingCaiLanQiuBySnDate(subIssueForJingCaiLanQiuObj.getSn(),
                    subIssueForJingCaiLanQiuObj.getIssue(), "04", "01");

            if (Utils.isNotEmpty(subIssueForJingCaiLanQiuForRFSF)) {
                subIssueForJingCaiLanQiuObj.setLetBall(subIssueForJingCaiLanQiuForRFSF.getLetBall());
            }
            if (Utils.isNotEmpty(subIssueForJingCaiLanQiuForDXF)) {
                subIssueForJingCaiLanQiuObj.setPreCast(subIssueForJingCaiLanQiuForDXF.getPreCast());
            }
            subIssueForJingCaiLanQiuObj.setIssue(Utils.formatDate2Str(Utils.formatDate(subIssueForJingCaiLanQiuObj.getIssue(), "yyyyMMdd"), "yyyy-MM-dd"));
            subIssueForJingCaiLanQiuTempList.add(subIssueForJingCaiLanQiuObj);
        }
        pageBean.setPageContent(subIssueForJingCaiLanQiuTempList);
        return pageBean;
    }

    @Override
    public SubIssueForJingCaiLanQiu getSubIssueForJingCaiLanQiuBySnDate(String sn, String date, String playCode, String pollCode) {
        return subIssueForJingCaiLanQiuDao.getSubIssueForJingCaiLanQiuBySnDate(sn, date, playCode, pollCode);
    }

    @Override
    public PageBean getSubIssueForJingCaiLanQiuListByPara(SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiu, Integer page, Integer pageSize) {
        return subIssueForJingCaiLanQiuDao.getSubIssueForJingCaiLanQiuListByPara(subIssueForJingCaiLanQiu, page, pageSize);
    }

    @Override
    public SubIssueForJingCaiLanQiu getSubIssueForJingCaiLanQiu(Long id) {
        return subIssueForJingCaiLanQiuDao.getSubIssueForJingCaiLanQiu(id);
    }

    @Override
    public int doUpdateOperatorForSn(String issue, String sn, Integer operator) {
        return subIssueForJingCaiLanQiuDao.doUpdateOperatorForSn(issue, sn, operator);
    }

    @Override
    public SubIssueForJingCaiLanQiu getSubIssueForJingCaiLanQiuBySnDate(String sn, String date, String playCode, String pollCode, int endOperator) {
        return subIssueForJingCaiLanQiuDao.getSubIssueForJingCaiLanQiuBySnDate(sn, date, playCode, pollCode, endOperator);
    }

    @Override
    public SubIssueForJingCaiLanQiu getSubIssueForJingCaiLanQiuByIssueSn(String issue, String sn, String playCode, String pollCode) {
        return subIssueForJingCaiLanQiuDao.getSubIssueForJingCaiLanQiuByIssueSn(issue, sn, playCode, pollCode);
    }

}
