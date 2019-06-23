package com.cndym.sendClient.service;

import com.cndym.bean.tms.SubIssueForGjb;
import com.cndym.service.ISubIssueForGjbService;
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
 * User: luna
 * QQ: 116741034
 * Date: 14-5-20
 * Time: 下午2:29
 * To change this template use File | Settings | File Templates.
 */
@Component
public class PostMatchOperator extends BasePostOperator {
    @Resource
    private ISubIssueForGjbService subIssueForGjbService;

    private Logger logger = Logger.getLogger(getClass());

    @Override
    public void execute(XmlBean xmlBean) {
        if (null == xmlBean)
            return;
        logger.info("获取冠军杯对阵:" + xmlBean.getFirst("issu"));

        XmlBean issueInfo = xmlBean.getFirst("issu");
        List<SubIssueForGjb> list = new ArrayList<SubIssueForGjb>();
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
                subIssueForGjb.setEndTime(Utils.formatDate(endtime, "yyyyMMddHHmmss"));
                subIssueForGjb.setStatus(new Integer(status));
                subIssueForGjb.setLeague(league);
                subIssueForGjb.setTeam(name);
                subIssueForGjb.setCreateTime(new Date());
                subIssueForGjb.setSn(sn);
                subIssueForGjb.setSp(new Double(sp));
                subIssueForGjb.setUpdateTime(new Date());
                list.add(subIssueForGjb);
            }
        }
        subIssueForGjbService.saveAllSubGame(list);
    }

    public static void main(String[]ages){
        System.out.println(Utils.formatDate("20140519234500", "yyyyMMddHHmmss"));
    }
}
