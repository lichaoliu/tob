package com.cndym.sendClient.service;

import com.cndym.bean.tms.SubIssueForGjb;
import com.cndym.service.ISubIssueForGjbService;
import com.cndym.utils.Utils;
import com.cndym.utils.xml.parse.XmlBean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: luna
 * QQ: 116741034
 * Date: 14-5-20
 * Time: 下午2:29
 * To change this template use File | Settings | File Templates.
 */
@Component
public class PostOddsOperator extends BasePostOperator {
    @Resource
    private ISubIssueForGjbService subIssueForGjbService;

    private Logger logger = Logger.getLogger(getClass());

    @Override
    public void execute(XmlBean xmlBean) {
        if (null == xmlBean)
            return;
        logger.info("获取冠军杯对sp:" + xmlBean.getFirst("issu"));

        XmlBean issueInfo = xmlBean.getFirst("issu");
        String endtime = issueInfo.attribute("endtime");
        String status = issueInfo.attribute("status");
        List<XmlBean> matchList = issueInfo.getAll("match");
        String issue = issueInfo.attribute("issue");
        logger.info("结束时间：" + endtime + ",状态：" + status + ",期次：" + issue);
        if (Utils.isNotEmpty(matchList)) {
            for (XmlBean xml : matchList) {
                String sn = xml.attribute("sn");
                String sp = xml.attribute("sp");
                String name = xml.attribute("name");
                String league = xml.attribute("league");
                SubIssueForGjb subIssueForGjb = new SubIssueForGjb();
                subIssueForGjb.setIssue(issue);
                subIssueForGjb.setStatus(new Integer(status));
                subIssueForGjb.setLeague(league);
                subIssueForGjb.setTeam(name);
                subIssueForGjb.setSn(sn);
                subIssueForGjb.setEndTime(Utils.formatDate(endtime, "yyyyMMddhhmmss"));
                subIssueForGjb.setSp(new Double(sp));
                subIssueForGjb.setUpdateTime(new Date());
                subIssueForGjbService.updateSubIssueForGjbList(subIssueForGjb);
            }
        }
        logger.info("结束");
    }
}
