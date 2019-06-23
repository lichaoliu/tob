package com.cndym.bean.query;

import com.cndym.bean.tms.SubIssueForJingCaiLanQiu;

import java.io.Serializable;

/**
 * User: mcs
 * Date: 12-2-18
 * Time: 下午5:32
 */
public class SubIssueJingCaiLanQiuResultBean implements Serializable {

    private SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiu;

    private String sfResult;

    private String rfshResult; //让分胜负过关

    private String sfcResult;

    private String dxfResult; //大小分过关


    public SubIssueJingCaiLanQiuResultBean() {
    }

    public SubIssueJingCaiLanQiuResultBean(SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiu, String sfResult, String rfshResult, String sfcResult, String dxfResult) {
        this.subIssueForJingCaiLanQiu = subIssueForJingCaiLanQiu;
        this.sfResult = sfResult;
        this.rfshResult = rfshResult;
        this.sfcResult = sfcResult;
        this.dxfResult = dxfResult;
    }

    public SubIssueForJingCaiLanQiu getSubIssueForJingCaiLanQiu() {
        return subIssueForJingCaiLanQiu;
    }

    public void setSubIssueForJingCaiLanQiu(SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiu) {
        this.subIssueForJingCaiLanQiu = subIssueForJingCaiLanQiu;
    }

    public String getSfResult() {
        return sfResult;
    }

    public void setSfResult(String sfResult) {
        this.sfResult = sfResult;
    }

    public String getRfshResult() {
        return rfshResult;
    }

    public void setRfshResult(String rfshResult) {
        this.rfshResult = rfshResult;
    }

    public String getSfcResult() {
        return sfcResult;
    }

    public void setSfcResult(String sfcResult) {
        this.sfcResult = sfcResult;
    }

    public String getDxfResult() {
        return dxfResult;
    }

    public void setDxfResult(String dxfResult) {
        this.dxfResult = dxfResult;
    }
}
