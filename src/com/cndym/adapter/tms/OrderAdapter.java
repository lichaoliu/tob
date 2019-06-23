package com.cndym.adapter.tms;

import com.cndym.IAdapter;
import com.cndym.adapter.BaseAdapter;
import com.cndym.adapter.tms.bean.MatchTimeInfo;
import com.cndym.adapter.tms.bean.NumberInfo;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.bean.tms.MainIssue;
import com.cndym.bean.tms.TicketReCode;
import com.cndym.bean.user.Member;
import com.cndym.constant.Constants;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import com.cndym.jms.producer.GateWayOrderMessageProducer;
import com.cndym.lottery.lotteryInfo.LotteryList;
import com.cndym.lottery.lotteryInfo.bean.LotteryBean;
import com.cndym.service.IMainIssueService;
import com.cndym.service.IMemberService;
import com.cndym.service.ITicketService;
import com.cndym.service.subIssue.ISubIssueOperator;
import com.cndym.service.subIssue.bean.SubIssueComm;
import com.cndym.utils.ConfigUtils;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import com.cndym.utils.xml.parse.ByteCodeUtil;
import com.cndym.utils.xml.parse.XmlBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: mcs Date: 12-10-27 Time: 下午11:15
 * <p/>
 * 投注接口
 */

@Service
public class OrderAdapter extends BaseAdapter implements IAdapter {

    private static Logger logger = Logger.getLogger(OrderAdapter.class);

    @Autowired
    private IMainIssueService mainIssueService;

    @Autowired
    private ITicketService ticketService;

    @Resource
    private GateWayOrderMessageProducer gatewayOrderMessageProducer;
    
    @Autowired
    private IMemberService memberService;

    /**
     * <order userInfo="真实姓名+身份证号码+手机号码" lotId="彩种编号(彩种编号)" issue="期次" playId="玩法编号" orderId="批次号"> <ticket item="注数" multiple="倍数" amount="金额" pollId="购买方式"><![CDATA[投注号码]]></ticket> <ticket
     * item="注数" multiple="倍数" amount="金额" pollId="购买方式"><![CDATA[投注号码]]></ticket> </order>
     *
     * @param xmlBean
     * @return
     */
    @Override
    public XmlBean execute(XmlBean xmlBean) {
        logger.info("接受投注请求: " + xmlBean.toXml());
        String sid = Utils.formatStr(xmlBean.getFirst("sid").attribute("text"));
        XmlBean orderBean = xmlBean.getFirst("order");
        String lotteryCode = Utils.formatStr(orderBean.attribute("lotteryId"));
        String issue = Utils.formatStr(orderBean.attribute("issue"));
        String playCode = Utils.formatStr(orderBean.attribute("playId"));
        String orderId = Utils.formatStr(orderBean.attribute("orderId"));

        List<XmlBean> ticketListBean = orderBean.getAll("ticket");

        // 验证彩种
        if (lotteryControl(lotteryCode) == 1) {
            return returnSubTagBody(ErrCode.E8109, buildTicketXmlBean(orderId).toXml());
        }
        
        Member member = memberService.getMemberBySid(sid);
        //验证彩种是否该代理商可售
        if (lotteryControl(member.getBackup1(),lotteryCode) == 0) {
            return returnSubTagBody(ErrCode.E8109, buildTicketXmlBean(orderId).toXml());
        }

        if (!Utils.isNotEmpty(orderId)) {
            return returnErrorCodeBody(ErrCode.E0003);
        }

        if (orderId.length() > 32) {
            return returnErrorCodeBody(ErrCode.E0003);
        }

        // 校验期次信息
        MainIssue mainIssue = mainIssueService.getMainIssueByLotteryIssue(lotteryCode, issue);
        if (null == mainIssue) {
            return returnSubTagBody(ErrCode.E8106, buildTicketXmlBean(orderId).toXml());
        } else {
            String lotteryCodeStr = "200,201,400";
            if (!lotteryCodeStr.contains(lotteryCode)) {
                // 判断开期时间
                if (mainIssue.getStartTime().getTime() > new Date().getTime()) {
                	logger.info(orderId+",lotteryCode="+lotteryCode+",issue="+issue+",start="+ErrCode.E8107);
                    return returnSubTagBody(ErrCode.E8107, buildTicketXmlBean(orderId).toXml());
                }
                // 判断复式时间
                if (mainIssue.getDuplexTime().getTime() <= new Date().getTime()) {
                	logger.info(orderId+",lotteryCode="+lotteryCode+",issue="+issue+",end="+ErrCode.E8105);
                    return returnSubTagBody(ErrCode.E8105, buildTicketXmlBean(orderId).toXml());
                }

                // 判断期次是否允许发单
                if (!(mainIssue.getStatus() == Constants.ISSUE_STATUS_START) || !(mainIssue.getSendStatus() == Constants.ISSUE_SALE_SEND)) {
                	logger.info(orderId+",lotteryCode="+lotteryCode+",issue="+issue+",status="+mainIssue.getStatus()+"sendStatus="+mainIssue.getSendStatus());
                    return returnSubTagBody(ErrCode.E8107, buildTicketXmlBean(orderId).toXml());
                }
            }
        }

        int maxTicket = Utils.formatInt(ConfigUtils.getValue("ORDER_MAX_TICKET"), 50);
        // 发单票数超过上限
        if (ticketListBean.size() > maxTicket) {
            return returnSubTagBody(ErrCode.E8112, buildTicketXmlBean(orderId).toXml());
        }

        int totalAmount = 0;
        Map<String, List<MatchTimeInfo>> matchTimeInfoMap = new HashMap<String, List<MatchTimeInfo>>();
        List<String> ticketIdList = new ArrayList<String>();
        // 校验没票的注数
        List<NumberInfo> numberInfos = new ArrayList<NumberInfo>();
        for (XmlBean ticketBean : ticketListBean) {
            String ticketId = Utils.formatStr(ticketBean.attribute("ticketId"));
            if (!Utils.isNotEmpty(ticketId)) {
                return returnErrorCodeBody(ErrCode.E0003);
            }
            // 判断票号的长度
            if (Utils.isNotEmpty(ticketId) && ticketId.length() > 32) {
                return returnErrorCodeBody(ErrCode.E0003);
            }
            // 票号是否重复
            if (ticketIdList.contains(ticketId)) {
                return returnSubTagBody(ErrCode.E8104, buildTicketXmlBean(orderId).toXml());
            } else {
                ticketIdList.add(ticketId);
            }
            NumberInfo numberInfo = NumberInfo.forInstance(ticketBean, playCode);
            if (null != numberInfo) {
                int singleTicketMaxAmount = Utils.formatInt(ConfigUtils.getValue("SINGLE_TICKET_MAX_AMOUNT"), 20000);
                // 单票金额超过上限
                int amount = numberInfo.getAmount();
                if (amount > singleTicketMaxAmount) {
                    return returnSubTagBody(ErrCode.E8110, buildTicketXmlBean(orderId).toXml());
                }

                String lotteryCodeStr = new StringBuffer(lotteryCode).append(playCode).append(numberInfo.getPollCode()).toString();
                // 校验号码格式
                LotteryBean lotteryBean = null;
                try {
                    lotteryBean = LotteryList.getLotteryBean(lotteryCodeStr);
                } catch (Exception e) {
                    return returnSubTagBody(ErrCode.E8101, buildTicketXmlBean(orderId).toXml());
                }
                if (lotteryBean == null) {
                    return returnSubTagBody(ErrCode.E8101, buildTicketXmlBean(orderId).toXml());
                }

                // 单票倍数超过上限
                int multiple = numberInfo.getMultiple();
                if (multiple > lotteryBean.getLotteryPoll().getDb()) {
                    return returnSubTagBody(ErrCode.E8113, buildTicketXmlBean(orderId).toXml());
                }

                String examineClass = lotteryBean.getLotteryPoll().getExamina();
                if (!Utils.isNotEmpty(examineClass)) {
                    examineClass = new StringBuffer("ex").append(lotteryCodeStr).toString();
                }
                try {
                    IExamine examine = (IExamine) SpringUtils.getBean(examineClass);
                    examine.examina(numberInfo.getNumber(), numberInfo.getItem());
                } catch (Exception e) {
                    if (e.getMessage().equals(ErrCode.E8116) || e.getMessage().equals(ErrCode.E8117)) {
                        return returnSubTagBody(e.getMessage(), buildTicketXmlBean(orderId).toXml());
                    }
                    return returnSubTagBody(ErrCode.E8111, buildTicketXmlBean(orderId).toXml());
                }
                // 校验金额
                int comAmount = lotteryBean.getLotteryPlay().getEachamount() * numberInfo.getItem() * multiple;
                if (comAmount != amount) {
                    return returnSubTagBody(ErrCode.E8103, buildTicketXmlBean(orderId).toXml());
                }
                String jcLotteryCodeStr = "200,201,400";
                if (jcLotteryCodeStr.contains(lotteryCode)) {//为竞彩的场次排序并进行校验
                	try {
                		List<MatchTimeInfo> matchInfoList = getMatchInfoList(numberInfo, lotteryCode, issue);
                    	matchTimeInfoMap.put(ticketId, matchInfoList);
					} catch (Exception e) {//判断场次是否有问题
						logger.error("JC Match Sort Error:",e);
						if ( e.getMessage().endsWith(ErrCode.E8111) || e.getMessage().equals(ErrCode.E8118) 
								|| e.getMessage().endsWith(ErrCode.E8119) || e.getMessage().endsWith(ErrCode.E8120)) {
				                return returnSubTagBody(e.getMessage(), buildTicketXmlBean(orderId).toXml());
				        }
						return returnSubTagBody(ErrCode.E0999, buildTicketXmlBean(orderId).toXml());
					}
                	
                }
                numberInfos.add(numberInfo);
                totalAmount += amount;
            }
        }

        try {
            Map<String, Object> resMap = ticketService.doSaveTicket(xmlBean, numberInfos, ticketIdList,matchTimeInfoMap, totalAmount);
            String sendOrderId = resMap.get("orderId") == null ? "" : resMap.get("orderId") + "";
            if (Utils.isNotEmpty(sendOrderId) && Utils.isSendTicket(lotteryCode) && Utils.isSendTicketByDiy(lotteryCode)) {
                logger.info("批次" + sendOrderId + "有" + numberInfos.size() + "张票发送");
                gatewayOrderMessageProducer.sendMessage(lotteryCode, sendOrderId, sid);
            }
            XmlBean returnBean = (XmlBean) resMap.get("returnBean");
            return returnSubTagBody(returnBean);
        } catch (DataIntegrityViolationException dve) {
            logger.error("order:", dve);
            if (dve.getMessage().contains("TMS_SID_ORDER")) {
                return returnSubTagBody(ErrCode.E8121, buildTicketXmlBean(orderId).toXml());
            } else if (dve.getMessage().contains("TMS_TICKET")) {
                return returnSubTagBody(ErrCode.E8104, buildTicketXmlBean(orderId).toXml());
            }
            return returnSubTagBody(ErrCode.E0999, buildTicketXmlBean(orderId).toXml());
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof DataIntegrityViolationException) {
                return returnSubTagBody(ErrCode.E8104, buildTicketXmlBean(orderId).toXml());
            }
            if (e.getMessage().equals(ErrCode.E8004) || e.getMessage().equals(ErrCode.E8106) || e.getMessage().equals(ErrCode.E8105) || e.getMessage().endsWith(ErrCode.E8111)
                    || e.getMessage().equals(ErrCode.E8118) || e.getMessage().endsWith(ErrCode.E8119) || e.getMessage().endsWith(ErrCode.E8120) || e.getMessage().equals(ErrCode.E8121)) {
                return returnSubTagBody(e.getMessage(), buildTicketXmlBean(orderId).toXml());
            }
            return returnSubTagBody(ErrCode.E0999, buildTicketXmlBean(orderId).toXml());
        }
    }
    
    /**
     * 竞彩场次排序及校验
     * @param numberInfo
     * @param lotteryCode
     * @param issue
     * @return
     */
    private List<MatchTimeInfo> getMatchInfoList(NumberInfo numberInfo,String lotteryCode,String issue){
    	 ISubIssueOperator subIssueOperator = (ISubIssueOperator) SpringUtils.getBean("comm" + lotteryCode + "SubIssueOperator");
    	 String number = numberInfo.getNumber();
         String[] arr = number.split("\\|");
         if (arr.length != 2) {
             throw new CndymException(ErrCode.E8111);
         }
         String[] matchIdArr = arr[0].split(";");
         String guoGuan = arr[1];
         String pollCode = numberInfo.getPollCode();
         if (guoGuan.equals("1*1")) {
             pollCode = "01";
         }

         List<MatchTimeInfo> matchInfoList = new ArrayList<MatchTimeInfo>();
         for (String match : matchIdArr) {
             String[] subArr = match.split(":");
             if ((!"10".equals(numberInfo.getPlayCode()) && subArr.length != 2) || ("10".equals(numberInfo.getPlayCode()) && subArr.length != 3)) {
                 throw new CndymException(ErrCode.E8111);
             }
             MatchTimeInfo matchTimeInfo = new MatchTimeInfo();
             String palyCode = "";
             if ("10".equals(numberInfo.getPlayCode())) {
                 palyCode = subArr[1];
             } else {
                 palyCode = numberInfo.getPlayCode();
             }
             SubIssueComm subIssueComm = subIssueOperator.getSubIssueComm(issue, subArr[0], palyCode, pollCode);
             matchTimeInfo.setMatchId(subArr[0]);
             matchTimeInfo.setIssue(subIssueComm.getIssue());
             matchTimeInfo.setTime(subIssueComm.getEndTime());
             matchTimeInfo.setDanShiEndTime(subIssueComm.getDanShiEndTime());
             matchTimeInfo.setFuShiEndTime(subIssueComm.getFuShiEndTime());
             matchInfoList.add(matchTimeInfo);
             numberInfo.setPollCode(subIssueComm.getPollCode());
         }
         Collections.sort(matchInfoList);
         MatchTimeInfo start = matchInfoList.get(0);
         if (start.getFuShiEndTime().getTime() <= new Date().getTime()) {
             throw new CndymException(ErrCode.E8118);
         }
    	 return matchInfoList;
    }

    private XmlBean buildTicketXmlBean(String orderId) {
        StringBuffer xml = new StringBuffer();
        xml.append("<order orderId=\"").append(orderId).append("\">");
        xml.append("</order>");
        return ByteCodeUtil.xmlToObject(xml.toString());
    }
}
