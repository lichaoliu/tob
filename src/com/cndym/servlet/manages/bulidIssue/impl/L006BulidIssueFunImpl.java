package com.cndym.servlet.manages.bulidIssue.impl;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cndym.bean.tms.MainIssue;
import com.cndym.service.IMainIssueService;
import com.cndym.servlet.manages.bulidIssue.IBulidIssueFun;
import com.cndym.utils.Utils;

@Component
public class L006BulidIssueFunImpl implements IBulidIssueFun {
	@Autowired
	private IMainIssueService mainIssueService;

	@Override
	public int bulid(String lotteryCode, String startDate, int days) {
		try {
			Date inputDate = Utils.formatDate(startDate, "yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(inputDate);

			for (int j = 0; j < days; j++) {
				String issueFirst = Utils.formatDate2Str(calendar.getTime(), "yyMMdd");
				for (int i = 1; i <= 120; i++) {
					String issue = issueFirst + Utils.fullByZero(i, 3);
					MainIssue mainIssue = new MainIssue();
					mainIssue.setName(issue);

					Date startTime = null;
					Date endTime = null;
					if (i < 24) {
						startTime = calendar.getTime();
						calendar.add(Calendar.MINUTE, 5);
						endTime = calendar.getTime();
					} else if (i == 24) {
						startTime = calendar.getTime();
						calendar.add(Calendar.HOUR_OF_DAY, 8);
						calendar.add(Calendar.MINUTE, 5);
						endTime = calendar.getTime();
					} else if (i > 24 && i < 97) {
						startTime = calendar.getTime();
						calendar.add(Calendar.MINUTE, 10);
						endTime = calendar.getTime();
					} else {
						startTime = calendar.getTime();
						calendar.add(Calendar.MINUTE, 5);
						endTime = calendar.getTime();
					}

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
