package com.cndym.sendClient.service;

import com.cndym.bean.tms.BonusJcLog;
import com.cndym.bean.tms.BonusLog;
import com.cndym.service.IBonusJcLogService;
import com.cndym.service.IBonusLogService;
import com.cndym.utils.Utils;
import com.cndym.utils.xml.parse.XmlBean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: MengJingyi
 * QQ: 116741034
 * Date: 13-5-30
 * Time: 上午9:48
 * To change this template use File | Settings | File Templates.
 */
@Component
public class PostBonusJxOperator extends BasePostOperator {
    @Resource
    private IBonusLogService bonusLogService;
    @Resource
    private IBonusJcLogService bonusJcLogService;

    private Logger logger = Logger.getLogger(getClass());

    @Override
    public void execute(XmlBean xmlBean) {
        logger.info("进入竞彩返奖文件存储");
        if (null == xmlBean) return;
        String lotteryCode = Utils.formatStr(xmlBean.attribute("lotteryCode"));
        String sn = Utils.formatStr(xmlBean.attribute("sn"));
        String issue = Utils.formatStr(xmlBean.attribute("date"));
        String week = Utils.formatStr(xmlBean.attribute("week"));
        String postCode = Utils.formatStr(xmlBean.attribute("postCode"));
        Long forJcId = Utils.formatLong(xmlBean.attribute("forJcId"));
        List<XmlBean> ticketBean = xmlBean.getAll("ticket");
        List<BonusLog> bonusLogs = new ArrayList<BonusLog>();
        BonusJcLog bonusJcLog = bonusJcLogService.getBonusJcLogForJcId(forJcId,lotteryCode);
        if (bonusJcLog != null) {
            logger.info(lotteryCode + "此场次已经处理过返奖文件" + issue + week + sn);
            return;
        } else {
            bonusJcLog = new BonusJcLog();
        }
        if (Utils.isEmpty(ticketBean)) {
            logger.info(lotteryCode + "此场次没有中奖票" + issue + week + sn);
            return;
        }
        for (XmlBean ticket : ticketBean) {
            String ticketId = Utils.formatStr(ticket.attribute("ticketId"));
            BonusLog bonusLog = bonusLogService.getBonusLogByTicketId(ticketId);
            if (bonusLog != null) {
                continue;
            }
            Double bonusAmount = Utils.formatDouble(ticket.attribute("bonusAmount"), 0d);
            Double fixBonusAmount = Utils.formatDouble(ticket.attribute("fixBonusAmount"), 0d);
            String bonusClass = Utils.formatStr(ticket.attribute("bonusClass"));
            int bigBonus = Utils.formatInt(ticket.attribute("bigBonus"), 0);

            bonusLog = new BonusLog();
            bonusLog.setTicketId(ticketId);
            bonusLog.setLotteryCode(lotteryCode);
            bonusLog.setIssue(issue);
            bonusLog.setBonusAmount(bonusAmount);
            bonusLog.setBonusClass(bonusClass);
            bonusLog.setFixBonusAmount(fixBonusAmount);
            bonusLog.setBigBonus(bigBonus);
            bonusLog.setCreateTime(new Date());
            bonusLog.setPostCode(postCode);
            bonusLog.setDuiJiangStatus(0);

            bonusLogs.add(bonusLog);
        }
        bonusJcLog.setIssue(issue);
        bonusJcLog.setLotteryCode(lotteryCode);
        bonusJcLog.setCreateTime(new Date());
        bonusJcLog.setPostCode(postCode);
        bonusJcLog.setWeek(week);
        bonusJcLog.setSn(sn);
        bonusJcLog.setSubForIssueId(forJcId);

        bonusLogService.doSaveAll(bonusLogs, bonusJcLog);
        logger.info("竞彩返奖文件处理结束");
    }
}
