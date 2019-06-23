package com.cndym.servlet.manages.bulidIssue.impl;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cndym.bean.tms.MainIssue;
import com.cndym.service.IMainIssueService;
import com.cndym.servlet.manages.bulidIssue.IBulidIssueFun;
import com.cndym.utils.DateUtils;
import com.cndym.utils.Utils;

@Component
public class L012BulidIssueFunImpl implements IBulidIssueFun {
	@Autowired
	private IMainIssueService mainIssueService;

	@Override
	public int bulid(String lotteryCode, String startDate, int days) {
		try {
			Date inputDate = Utils.formatDate(startDate + " 10:00:00", "yyyy-MM-dd HH:mm:ss");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(inputDate);

			for (int j = 0; j < days; j++) {
				if (j > 0) {
					calendar.add(Calendar.DAY_OF_MONTH, 1);
					calendar.set(Calendar.HOUR_OF_DAY, 10);
					calendar.set(Calendar.MINUTE, 0);
					calendar.set(Calendar.SECOND, 0);
					calendar.set(Calendar.MILLISECOND, 0);
				}

				String issueFirst = Utils.formatDate2Str(calendar.getTime(), "yyMMdd");
				for (int i = 1; i <=72 ; i++) {
					String issue = issueFirst + Utils.fullByZero(i, 3);
					MainIssue mainIssue = new MainIssue();
					mainIssue.setName(issue);

					Date startTime = null;
					Date endTime = null;

					startTime = calendar.getTime();
					calendar.add(Calendar.MINUTE, 10);

					endTime = calendar.getTime();

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
	
	public static void main(String args[]) {
		L012BulidIssueFunImpl issue = new L012BulidIssueFunImpl();
		issue.bulid("012", "2013-07-02", 3);
//		Date inputDate = Utils.formatDate("2013-10-25" + " 08:30:00", "yyyy-MM-dd HH:mm:ss");
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(inputDate);
//		System.out.println(Utils.formatDate2Str(calendar.getTime(), "yyMMdd"));
	}
}
