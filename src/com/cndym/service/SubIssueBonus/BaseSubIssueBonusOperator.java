package com.cndym.service.SubIssueBonus;

import com.cndym.bean.tms.MainIssue;
import com.cndym.service.IMainIssueService;
import com.cndym.utils.ConfigUtils;
import com.cndym.utils.HttpClientUtils;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：邓玉明 email：cndym@163.com QQ：757579248 时间: 12-2-16 上午12:13
 */
public class BaseSubIssueBonusOperator implements ISubIssueBonusOperator {
	@Override
	public int bonusOperator(String issue, String sn) {
		return 0;
	}

	@Override
	public void noticeBonus(String issue, String sn) {
	}

	@Override
	public void noticeBonusNumber(String lotteryCode, String issue, String sn) {
		IMainIssueService mainIssueService = (IMainIssueService) SpringUtils.getBean("mainIssueServiceImpl");
		MainIssue mainIssue = mainIssueService.getMainIssueByLotteryIssue(lotteryCode, issue);
		if (null != mainIssue) {
			HttpClientUtils httpClientUtils = new HttpClientUtils(ConfigUtils.getValue("DATACENTER_API") + "configServlet");
			Map<String, String> map = new HashMap<String, String>();
			map.put("operator", "save");
			map.put("lotteryCode", lotteryCode);
			map.put("issue", issue);
			map.put("bonusNumber", mainIssue.getBonusNumber());
			map.put("bonusTime", Utils.formatDate2Str(mainIssue.getBonusTime(), "yyyy-MM-dd HH:mm:ss"));
			httpClientUtils.setPara(map);
			httpClientUtils.httpClientRequest();
		}
	}

	@Override
	public void noticeBonusGp(String lotteryCode, String issue) {

	}

}
