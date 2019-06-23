package com.cndym.sendClient.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.cndym.bean.tms.MainIssue;
import com.cndym.bean.tms.PostIssue;
import com.cndym.bean.tms.SubGame;
import com.cndym.cache.XMemcachedClientWrapper;
import com.cndym.constant.Constants;
import com.cndym.lottery.lotteryInfo.LotteryList;
import com.cndym.lottery.lotteryInfo.bean.LotteryClass;
import com.cndym.service.IMainIssueService;
import com.cndym.service.IPostIssueService;
import com.cndym.service.ISubGameService;
import com.cndym.utils.ConfigUtils;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import com.cndym.utils.xml.parse.XmlBean;

/**
 * 作者：邓玉明 时间：11-7-2 下午2:20 QQ：757579248 email：cndym@163.com
 */
@Component
public class PostIssueOperator extends BasePostOperator {
    @Resource
    private IMainIssueService mainIssueService;
	@Resource
	private IPostIssueService postIssueService;
	@Resource
	private ISubGameService subGameService;

	private Logger logger = Logger.getLogger(getClass());

	@Override
	public void execute(XmlBean xmlBean) {
		if (null == xmlBean)
			return;
		List<XmlBean> issueInfos = xmlBean.getAll("issueInfo");
		logger.info("进入期次通知处理:" + xmlBean.toString());
		logger.info("进入期次通知处理:" + issueInfos.size());
		for (XmlBean issueInfo : issueInfos) {
			String lotteryCode = issueInfo.attribute("lotteryCode");
			String issue = issueInfo.attribute("issue");
			String startTime = issueInfo.attribute("startTime");
			String endTime = issueInfo.attribute("endTime");
			String bonusTime = issueInfo.attribute("bonusTime");
			String postCode = issueInfo.attribute("postCode");
			int status = Utils.formatInt(issueInfo.attribute("status"), 0);
			if (null == bonusTime) {
				bonusTime = endTime;
			}

			LotteryClass lotteryClass = LotteryList.getLotteryClass(lotteryCode);
			Date endDate = Utils.formatDate(endTime, "yyyy-MM-dd HH:mm:ss");
			Calendar endCalender = Calendar.getInstance();
			endCalender.setTime(endDate);
			endCalender.add(Calendar.SECOND, lotteryClass.getEarly().intValue() * -1);
			Calendar SimplexEndCalender = Calendar.getInstance();
			SimplexEndCalender.setTime(endDate);
			SimplexEndCalender.add(Calendar.SECOND, lotteryClass.getDashi().intValue() * -1);

			XMemcachedClientWrapper memcachedClientWrapper = (XMemcachedClientWrapper) SpringUtils.getBean("xmemcachedClientWrapper");
			Date startTimeD = Utils.formatDate(startTime, "yyyy-MM-dd HH:mm:ss");
			Date nowDate = new Date();
			if (status != 2) {// 取消
				if (nowDate.after(startTimeD) && nowDate.before(endDate)) {
					try {
						String canSellPost = (String) memcachedClientWrapper.getMemcachedClient().get(lotteryCode + issue);
						if (Utils.isNotEmpty(canSellPost)) {
							if (canSellPost.indexOf(postCode) <= -1) {
								canSellPost = canSellPost + "," + postCode;
								memcachedClientWrapper.getMemcachedClient().set(lotteryCode + issue, 0, canSellPost);
							}
						} else {
							memcachedClientWrapper.getMemcachedClient().set(lotteryCode + issue, 0, postCode);
						}
					} catch (Exception e) {
						logger.error("error" + e.getMessage());
						throw new IllegalArgumentException("Memcached 错误：" + e.getMessage());
					}
					status = 1;
				} else if (nowDate.before(startTimeD)) {
					status = 0;
				} else {
					status = 3;
				}
			}

	        try {
	        	String lotteryCodeS = ConfigUtils.getValue("ALLOW_UPDATE_MAIN_ISSUE_LOTTERY");
	        	if (Utils.isNotEmpty(lotteryCodeS) && lotteryCodeS.indexOf(lotteryCode) != -1) {
		        	MainIssue oldIssue = mainIssueService.getMainIssueByLotteryIssue(lotteryCode, issue);
		            if (oldIssue != null && (oldIssue.getStartTime().getTime() != startTimeD.getTime() || oldIssue.getEndTime().getTime() != endDate.getTime())) {
	                    oldIssue.setStartTime(startTimeD);
	                	oldIssue.setEndTime(endDate);
	                    oldIssue.setSimplexTime(SimplexEndCalender.getTime());
	                	oldIssue.setDuplexTime(endCalender.getTime());
	                	oldIssue.setBonusTime(endDate);
	                	mainIssueService.update(oldIssue);
		            }
	        	}
	        } catch (Exception e) {
	        	logger.error("postIssue update mainIssue", e);
	        }
			
			PostIssue postIssue = postIssueService.getPostIssueByLotteryCodeAndIssue(lotteryCode, issue, postCode);
			if (Utils.isNotEmpty(postIssue)) {
				postIssue.setStartTime(startTimeD);
				postIssue.setEndTime(endDate);
				postIssue.setDuplexTime(endCalender.getTime());
				postIssue.setBonusTime(Utils.formatDate(bonusTime, "yyyy-MM-dd HH:mm:ss"));
				postIssue.setStatus(status);
				postIssueService.update(postIssue);
			} else {
				postIssue = new PostIssue();
				postIssue.setStartTime(startTimeD);
				postIssue.setEndTime(Utils.formatDate(endTime, "yyyy-MM-dd HH:mm:ss"));
				postIssue.setStatus(status);
				postIssue.setBonusStatus(Constants.ISSUE_BONUS_STATUS_NO);
				postIssue.setBonusTime(Utils.formatDate(bonusTime, "yyyy-MM-dd HH:mm:ss"));
				postIssue.setDuplexTime(endCalender.getTime());
				postIssue.setLotteryCode(lotteryCode);
				postIssue.setName(issue);
				postIssue.setPostCode(postCode);
				postIssue.setBackup1(Utils.formatDate2Str(new Date(), "yyyy-MM-dd HH:mm:ss"));
				postIssueService.save(postIssue);
			}

			// 如果彩种停售 则去掉相关的出票口
			if (status == 2) {
				try {
					String canSellPost = (String) memcachedClientWrapper.getMemcachedClient().get(lotteryCode + issue);
					if (Utils.isNotEmpty(canSellPost)) {
						String[] postArray = canSellPost.split(",");
						String postStr = "";
						for (String postCodeStr : postArray) {
							if (postCodeStr.equals(postCode)) {
								continue;
							}
							if (postStr == "") {
								postStr = postCodeStr;
							} else {
								postStr = postStr + "," + postCodeStr;
							}
						}
						memcachedClientWrapper.getMemcachedClient().set(lotteryCode + issue, 0, postStr);
					}
				} catch (Exception e) {
					logger.error("error" + e.getMessage());
				}
			}

			List<XmlBean> xmlBeans = issueInfo.getAll("item");
			if (Utils.isNotEmpty(xmlBeans)) {
				subGameService.deleteByLottery(lotteryCode, issue);
				for (XmlBean bean : xmlBeans) {
					String index = bean.attribute("index");
					String master = bean.attribute("master");
					String guest = bean.attribute("guest");
					String time = bean.attribute("time");
					String league = bean.attribute("league");
					SubGame subGame = new SubGame();
					subGame.setIndex(Integer.parseInt(index));
					subGame.setIssue(issue);
					subGame.setLeageName(league);
					subGame.setLotteryCode(lotteryCode);
					subGame.setMasterName(master);
					subGame.setGuestName(guest);
					subGame.setStartTime(Utils.formatDate(time, "yyyy-MM-dd HH:mm:ss"));
					subGameService.save(subGame);
				}
			}
		}
	}
}
