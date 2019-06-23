package com.cndym.servlet.manages.bulidIssue.impl;

import com.cndym.bean.tms.MainIssue;
import com.cndym.service.IMainIssueService;
import com.cndym.servlet.manages.bulidIssue.IBulidIssueFun;
import com.cndym.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class L112BulidIssueFunImpl implements IBulidIssueFun {
	@Autowired
	private IMainIssueService mainIssueService;

	@Override
	public int bulid(String lotteryCode, String startDate, int days) {
		try {
			Date inputDate = Utils.formatDate(startDate + " 09:05:30", "yyyy-MM-dd HH:mm:ss");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(inputDate);
			for (int j = 0; j < days; j++) {
				if (j > 0) {
					calendar.add(Calendar.DAY_OF_MONTH, 1);
					calendar.set(Calendar.HOUR_OF_DAY, 9);
					calendar.set(Calendar.MINUTE, 5);
					calendar.set(Calendar.SECOND, 30);
					calendar.set(Calendar.MILLISECOND, 0);
				}
				String issueFirst = Utils.formatDate2Str(calendar.getTime(), "yyMMdd");
				for (int i = 1; i <= 82; i++) {
					String issue = issueFirst + Utils.fullByZero(i, 2);
					MainIssue mainIssue = new MainIssue();
					mainIssue.setName(issue);
					Date startTime = calendar.getTime();
					calendar.add(Calendar.MINUTE, 10);
					Date endTime = calendar.getTime();
					mainIssue.setLotteryCode(lotteryCode);
					mainIssue.setStartTime(startTime);
					mainIssue.setEndTime(endTime);
					mainIssue.setBonusTime(endTime);
					mainIssueService.doSaveIssueEx(mainIssue);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
}
