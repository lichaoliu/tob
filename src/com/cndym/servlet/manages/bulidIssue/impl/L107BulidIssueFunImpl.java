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
public class L107BulidIssueFunImpl implements IBulidIssueFun {
	@Autowired
	private IMainIssueService mainIssueService;

	@Override
	public int bulid(String lotteryCode, String startDate, int days) {
		try {
			Date inputDate = Utils.formatDate(startDate + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(inputDate);

			for (int j = 0; j < days; j++) {
				
				if (j > 0) {
					calendar.add(Calendar.DAY_OF_MONTH, 1);
				}
				
				String issueFirst = Utils.formatDate2Str(calendar.getTime(), "yyMMdd");
				
				for (int i = 1; i <= 87; i++) {
					String issue = issueFirst + Utils.fullByZero(i, 2);
					MainIssue mainIssue = new MainIssue();
					mainIssue.setName(issue);

					Date startTime = null;
					Date endTime = null;
					
					if(i == 1){
						calendar.add(Calendar.DAY_OF_MONTH, -1);
						calendar.set(Calendar.HOUR_OF_DAY, 22);
						calendar.set(Calendar.MINUTE, 55);
						calendar.set(Calendar.SECOND, 50);
						calendar.set(Calendar.MILLISECOND, 0);
					}

					startTime = calendar.getTime();
					if (i == 1) {
						calendar.add(Calendar.MINUTE, 580);
					} else {
						calendar.add(Calendar.MINUTE, 10);
					}

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
	
	public static void main(String[] args) {
		L107BulidIssueFunImpl l107 = new L107BulidIssueFunImpl();
		l107.bulid("107", "2017-04-06", 2);
	}

}
