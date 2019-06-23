package com.cndym.bean.query;

import com.cndym.bean.tms.SubIssueForJingCaiZuQiu;

import java.io.Serializable;

/**
 * User: mcs Date: 12-2-18 Time: 下午5:32
 */
public class SubIssueJingCaiZuQiuResultBean implements Serializable {

	private SubIssueForJingCaiZuQiu subIssueForJingCaiZuQiu;

	private String rqSpfResult;

	private String spfResult;

	private String bfResult;

	private String zjqsResult;

	private String bqcspfResult;

	public SubIssueJingCaiZuQiuResultBean() {
	}

	public SubIssueJingCaiZuQiuResultBean(SubIssueForJingCaiZuQiu subIssueForJingCaiZuQiu, String spfResult, String bfResult, String zjqsResult, String bqcspfResult, String rqSpfResult) {
		this.subIssueForJingCaiZuQiu = subIssueForJingCaiZuQiu;
		this.spfResult = spfResult;
		this.bfResult = bfResult;
		this.zjqsResult = zjqsResult;
		this.bqcspfResult = bqcspfResult;
		this.rqSpfResult = rqSpfResult;
	}

	public SubIssueForJingCaiZuQiu getSubIssueForJingCaiZuQiu() {
		return subIssueForJingCaiZuQiu;
	}

	public void setSubIssueForJingCaiZuQiu(SubIssueForJingCaiZuQiu subIssueForJingCaiZuQiu) {
		this.subIssueForJingCaiZuQiu = subIssueForJingCaiZuQiu;
	}

	public String getSpfResult() {
		return spfResult;
	}

	public void setSpfResult(String spfResult) {
		this.spfResult = spfResult;
	}

	public String getBfResult() {
		return bfResult;
	}

	public void setBfResult(String bfResult) {
		this.bfResult = bfResult;
	}

	public String getZjqsResult() {
		return zjqsResult;
	}

	public void setZjqsResult(String zjqsResult) {
		this.zjqsResult = zjqsResult;
	}

	public String getBqcspfResult() {
		return bqcspfResult;
	}

	public void setBqcspfResult(String bqcspfResult) {
		this.bqcspfResult = bqcspfResult;
	}

	public String getRqSpfResult() {
		return rqSpfResult;
	}

	public void setRqSpfResult(String rqSpfResult) {
		this.rqSpfResult = rqSpfResult;
	}
}
