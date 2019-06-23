package com.cndym.service.SubIssueBonus;

/**
 * 作者：邓玉明 email：cndym@163.com QQ：757579248 时间: 12-2-15 下午10:58
 */
public interface ISubIssueBonusOperator {
	public int bonusOperator(String issue, String sn);

	public void noticeBonus(String issueOrLotteryCode, String sn);

	public void noticeBonusGp(String lotteryCode, String issue);

	public void noticeBonusNumber(String lotteryCode, String issue, String sn);

}
