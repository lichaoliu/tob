package com.cndym.service.impl;

import com.cndym.adapter.tms.bean.MatchTimeInfo;
import com.cndym.adapter.tms.bean.NumberInfo;
import com.cndym.bean.query.TicketMainIssueBean;
import com.cndym.bean.query.TicketQueryBean;
import com.cndym.bean.tms.*;
import com.cndym.bean.user.AccountOperatorTemp;
import com.cndym.bean.user.Member;
import com.cndym.constant.Constants;
import com.cndym.control.PostMap;
import com.cndym.control.WeightMap;
import com.cndym.control.bean.Post;
import com.cndym.dao.*;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import com.cndym.lottery.lotteryInfo.LotteryList;
import com.cndym.lottery.lotteryInfo.bean.LotteryClass;
import com.cndym.sendClient.ISendClient;
import com.cndym.service.IAccountService;
import com.cndym.service.ITicketService;
import com.cndym.service.subIssue.ISubIssueOperator;
import com.cndym.service.subIssue.bean.SubIssueComm;
import com.cndym.utils.ConfigUtils;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.TicketIdBuildUtils;
import com.cndym.utils.Utils;
import com.cndym.utils.hibernate.PageBean;
import com.cndym.utils.xml.parse.ByteCodeUtil;
import com.cndym.utils.xml.parse.XmlBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.*;

/**
 * User: 邓玉明 Date: 11-3-27 下午11:02
 */
@Service
public class TicketServiceImpl extends GenericServiceImpl<Ticket, ITicketDao> implements ITicketService {
    @Autowired
    private ITicketDao ticketDao;
    @Autowired
    private ISidOrderDao sidOrderDao;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IMemberDao memberDao;
    @Autowired
    private IMainIssueDao mainIssueDao;
    @Autowired
    private ITicketReCodeDao ticketReCodeDao;
    @Autowired
    private IBonusTicketDao bonusTicketDao;
    @Autowired
    private ISubIssueForBeiDanDao subIssueForBeiDanDao;
    @Autowired
    private ISubIssueForJingCaiZuQiuDao subIssueForJingCaiZuQiuDao;
    @Autowired
    private ISubIssueForJingCaiLanQiuDao subIssueForJingCaiLanQiuDao;
    @Autowired
    private IBonusLogDao bonusLogDao;
    @Autowired
    private ISubTicketDao subTicketDao;
    @Autowired
    private IAccountOperatorTempDao accountOperatorTempDao;

    private Logger logger = Logger.getLogger(getClass());

    @PostConstruct
    public void initDao() {
        super.setDaoImpl(ticketDao);
    }

    @Override
    public int doUpdateTicketForSended(Ticket ticket) {
        Ticket subTicket = ticketDao.getTicketByTicketIdForUpdate(ticket.getTicketId());
        if (subTicket.getTicketStatus() != Constants.TICKET_STATUS_SENDING) {
            return -1;// 如果票状态不是等待回执，不处理
        }
        subTicket.setTicketStatus(ticket.getTicketStatus());
        subTicket.setErrCode(ticket.getErrCode());
        subTicket.setErrMsg(ticket.getErrMsg());
        subTicket.setSaleCode(ticket.getSaleCode());
        subTicket.setSaleInfo(ticket.getSaleInfo());
        if (Utils.isNotEmpty(ticket.getBackup1())) {
            subTicket.setBackup1(ticket.getBackup1());
        }

        subTicket.setSumTicket(1);
        subTicket.setSuccessTicket(0);
        subTicket.setSuccessAmount(0d);
        subTicket.setFailureTicket(0);
        subTicket.setFailureAmount(0d);
        // 出票口票状态返加控制为success和failure不成功即失败
        if (ticket.getTicketStatus() == Constants.TICKET_STATUS_FAILURE) {// 退款处理
            logger.info("票(" + subTicket.getTicketId() + ")出票失败，进行退款处理，金额(" + subTicket.getAmount() + ")");
            accountService.doAccount(subTicket.getUserCode(), Constants.R00100, subTicket.getAmount(), subTicket.getOrderId(), Constants.P10000, subTicket.getTicketId(), subTicket.getSid());
            subTicket.setReturnTime(new Date());
            subTicket.setFailureAmount(subTicket.getAmount());
            subTicket.setFailureTicket(1);

        } else if (ticket.getTicketStatus() == Constants.TICKET_STATUS_SUCCESS) {
            subTicket.setReturnTime(new Date());
            subTicket.setSuccessAmount(subTicket.getAmount());
            subTicket.setSuccessTicket(1);
        }
        subTicket.setAcceptTime(new Date());
        ticketDao.update(subTicket);
        // TODO 外发通知
        return 0;
    }

    @Override
    public int doBonusAmountToAccount(String lotteryCode, String issue) {
        MainIssue mainIssue = mainIssueDao.getMainIssueForUpdate(lotteryCode, issue);
        if (null == mainIssue) {
            throw new IllegalArgumentException("返奖的彩种(" + lotteryCode + "),期次(" + issue + ")不存在");
        }
        if (mainIssue.getStatus() != Constants.ISSUE_STATUS_END) {
            throw new IllegalArgumentException("返奖的彩种(" + lotteryCode + "),期次(" + issue + ")状态(" + mainIssue.getStatus() + ")不为结期(3)");
        }
        if (mainIssue.getOperatorsAward() != Constants.OPERATORS_AWARD_COMPLETE) {
            throw new IllegalArgumentException("返奖的彩种(" + lotteryCode + "),期次(" + issue + ")算奖状态(" + mainIssue.getOperatorsAward() + ")不为完成算奖(2)");
        }
        if (mainIssue.getBonusStatus() != Constants.BONUS_STATUS_WAIT) {
            throw new IllegalArgumentException("返奖的彩种(" + lotteryCode + "),期次(" + issue + ")返奖状态(" + mainIssue.getBonusStatus() + ")不为静待未返奖(0)");
        }

        int pageSize = 200;
        int page = 1;
        int pageTotal;
        PageBean pageBean = ticketDao.getTicketForBonus(lotteryCode, issue, null, pageSize, page);
        List<Ticket> ticketList = pageBean.getPageContent();
        pageTotal = pageBean.getPageTotal();
        for (Ticket ticket : ticketList) {
            saveBonusTicket(ticket);
        }
        page++;
        while (pageTotal >= page) {
            PageBean subPageBean = ticketDao.getTicketForBonus(lotteryCode, issue, null, pageSize, page);
            page++;
            List<Ticket> subTicketList = subPageBean.getPageContent();
            for (Ticket ticket : subTicketList) {
                saveBonusTicket(ticket);
            }
        }
        
        List<Map<String, Object>> mapList = ticketDao.countBonusAmount(lotteryCode, issue, null);
        double totalBonusAmount = 0d;
        for (Map<String, Object> map : mapList) {
            String userCode = (String) map.get("userCode");
            BigDecimal bigDecimal = (BigDecimal) map.get("total");
            double bonusAmount = bigDecimal.doubleValue();
            totalBonusAmount += bonusAmount;
            String sid = (String) map.get("sid");
            accountService.doAccount(userCode, Constants.R00200, bonusAmount, lotteryCode + "." + issue, sid);
        }
        mainIssue.setBonusTotal(totalBonusAmount);
        mainIssue.setBonusStatus(Constants.ISSUE_BONUS_STATUS_YES);
        mainIssue.setSendBonusTime(new Date());
        mainIssueDao.update(mainIssue);
        
        return 0;
    }

    private void saveBonusTicket(Ticket ticket) {
        logger.info(ticket.getSid() + ticket.getOutTicketId());
        BonusTicket bonusTicket = new BonusTicket();
        bonusTicket.setOutTicketId(ticket.getOutTicketId());
        bonusTicket.setSid(ticket.getSid());
        bonusTicket.setUserCode(ticket.getUserCode());
        bonusTicket.setOrderId(ticket.getOrderId());
        bonusTicket.setTicketId(ticket.getTicketId());
        bonusTicket.setLotteryCode(ticket.getLotteryCode());
        bonusTicket.setPlayCode(ticket.getPlayCode());
        bonusTicket.setPollCode(ticket.getPollCode());
        bonusTicket.setIssue(ticket.getIssue());
        bonusTicket.setPostCode(ticket.getPostCode());
        bonusTicket.setAmount(ticket.getAmount());
        bonusTicket.setBonusAmount(ticket.getBonusAmount());
        bonusTicket.setBonusClass(ticket.getBonusClass());
        bonusTicket.setFixBonusAmount(ticket.getFixBonusAmount());
        bonusTicket.setStartGameId(ticket.getStartGameId());
        bonusTicket.setEndGameId(ticket.getEndGameId());
        bonusTicket.setCreateTime(ticket.getCreateTime());
        bonusTicket.setSendTime(ticket.getSendTime());
        bonusTicket.setReturnTime(ticket.getReturnTime());
        bonusTicket.setBonusTime(new Date());
        bonusTicket.setBigBonus(ticket.getBigBonus());
        bonusTicket.setBackup1(ticket.getBackup1());
        bonusTicket.setBackup2(ticket.getBackup2());
        bonusTicket.setBackup3(ticket.getBackup3());
        bonusTicketDao.save(bonusTicket);
    }

    @Override
    public int doBonusAmountToAccount(String lotteryCode, String issue, String sn) {
        if ("200".equals(lotteryCode)) {
            SubIssueForJingCaiZuQiu subIssueForJingCaiZuQiu = subIssueForJingCaiZuQiuDao.getSubIssueForJingCaiZuQiuForUpdate(issue, sn);
            if (null == subIssueForJingCaiZuQiu) {
                throw new IllegalArgumentException("返奖的彩种(" + lotteryCode + "),期次(" + issue + "),场次(" + sn + ")不存在");
            }
            if (!(subIssueForJingCaiZuQiu.getEndOperator() != Constants.SUB_ISSUE_END_OPERATOR_END || subIssueForJingCaiZuQiu.getEndOperator() != Constants.SUB_ISSUE_END_OPERATOR_CANCEL)) {
                throw new IllegalArgumentException("返奖的彩种(" + lotteryCode + "),期次(" + issue + ")状态(" + subIssueForJingCaiZuQiu.getEndOperator() + ")不为结期(3)或者取消(2)");
            }
            if (subIssueForJingCaiZuQiu.getOperatorsAward() != Constants.OPERATORS_AWARD_COMPLETE) {
                throw new IllegalArgumentException("返奖的彩种(" + lotteryCode + "),期次(" + issue + ")算奖状态(" + subIssueForJingCaiZuQiu.getOperatorsAward() + ")不为完成算奖(2)");
            }
            if (subIssueForJingCaiZuQiu.getBonusOperator() != Constants.SUB_ISSUE_BONUS_OPERATOR_NO) {
                throw new IllegalArgumentException("返奖的彩种(" + lotteryCode + "),期次(" + issue + ")返奖状态(" + subIssueForJingCaiZuQiu.getBonusOperator() + ")不为静待未返奖(0)");
            }

            subIssueForJingCaiZuQiu.setBonusOperator(Constants.SUB_ISSUE_BONUS_OPERATOR_YES);
            subIssueForJingCaiZuQiuDao.update(subIssueForJingCaiZuQiu);
        }
        if ("201".equals(lotteryCode)) {
            SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiu = subIssueForJingCaiLanQiuDao.getSubIssueForJingCaiLanQiuForUpdate(issue, sn);
            if (null == subIssueForJingCaiLanQiu) {
                throw new IllegalArgumentException("返奖的彩种(" + lotteryCode + "),期次(" + issue + "),场次(" + sn + ")不存在");
            }
            if (!(subIssueForJingCaiLanQiu.getEndOperator() == Constants.SUB_ISSUE_END_OPERATOR_END || subIssueForJingCaiLanQiu.getEndOperator() == Constants.SUB_ISSUE_END_OPERATOR_CANCEL)) {
                throw new IllegalArgumentException("返奖的彩种(" + lotteryCode + "),期次(" + issue + ")状态(" + subIssueForJingCaiLanQiu.getEndOperator() + ")不为结期(3)或者取消(2)");
            }
            if (subIssueForJingCaiLanQiu.getOperatorsAward() != Constants.OPERATORS_AWARD_COMPLETE) {
                throw new IllegalArgumentException("返奖的彩种(" + lotteryCode + "),期次(" + issue + ")算奖状态(" + subIssueForJingCaiLanQiu.getOperatorsAward() + ")不为完成算奖(2)");
            }
            if (subIssueForJingCaiLanQiu.getBonusOperator() != Constants.SUB_ISSUE_BONUS_OPERATOR_NO) {
                throw new IllegalArgumentException("返奖的彩种(" + lotteryCode + "),期次(" + issue + ")返奖状态(" + subIssueForJingCaiLanQiu.getBonusOperator() + ")不为静待未返奖(0)");
            }

            subIssueForJingCaiLanQiu.setBonusOperator(Constants.SUB_ISSUE_BONUS_OPERATOR_YES);
            subIssueForJingCaiLanQiuDao.update(subIssueForJingCaiLanQiu);
        }
        if ("400".equals(lotteryCode)) {
            SubIssueForBeiDan subIssueForBeiDan = subIssueForBeiDanDao.getSubIssueForBeiDanForUpdate(issue, sn);
            if (null == subIssueForBeiDan) {
                throw new IllegalArgumentException("返奖的彩种(" + lotteryCode + "),期次(" + issue + "),场次(" + sn + ")不存在");
            }
            if (!(subIssueForBeiDan.getEndOperator() == Constants.SUB_ISSUE_END_OPERATOR_END || subIssueForBeiDan.getEndOperator() == Constants.SUB_ISSUE_END_OPERATOR_CANCEL)) {
                throw new IllegalArgumentException("返奖的彩种(" + lotteryCode + "),期次(" + issue + ")状态(" + subIssueForBeiDan.getEndOperator() + ")不为结期(3)或者取消(2)");
            }
            if (subIssueForBeiDan.getOperatorsAward() != Constants.OPERATORS_AWARD_COMPLETE) {
                throw new IllegalArgumentException("返奖的彩种(" + lotteryCode + "),期次(" + issue + ")算奖状态(" + subIssueForBeiDan.getOperatorsAward() + ")不为完成算奖(2)");
            }
            if (subIssueForBeiDan.getBonusOperator() != Constants.SUB_ISSUE_BONUS_OPERATOR_NO) {
                throw new IllegalArgumentException("返奖的彩种(" + lotteryCode + "),期次(" + issue + ")返奖状态(" + subIssueForBeiDan.getBonusOperator() + ")不为静待未返奖(0)");
            }

            subIssueForBeiDan.setBonusOperator(Constants.SUB_ISSUE_BONUS_OPERATOR_YES);
            subIssueForBeiDanDao.update(subIssueForBeiDan);
        }

        int pageSize = 200;
        int page = 1;
        int pageTotal;
        PageBean pageBean = ticketDao.getTicketForBonus(lotteryCode, "", issue + sn, pageSize, page);
        List<Ticket> ticketList = pageBean.getPageContent();
        pageTotal = pageBean.getPageTotal();
        for (Ticket ticket : ticketList) {
            saveBonusTicket(ticket);
        }
        page++;
        while (pageTotal >= page) {
            PageBean subPageBean = ticketDao.getTicketForBonus(lotteryCode, "", issue + sn, pageSize, page);
            page++;
            List<Ticket> subTicketList = subPageBean.getPageContent();
            for (Ticket ticket : subTicketList) {
                saveBonusTicket(ticket);
            }
        }
        
        List<Map<String, Object>> mapList = ticketDao.countBonusAmount(lotteryCode, issue, sn);
        for (Map<String, Object> map : mapList) {
            String userCode = (String) map.get("userCode");
            BigDecimal bigDecimal = (BigDecimal) map.get("total");
            double bonusAmount = bigDecimal.doubleValue();
            String sid = (String) map.get("sid");
            accountService.doAccount(userCode, Constants.R00200, bonusAmount, lotteryCode + "." + issue + "." + sn, sid);
        }

        return 0;
    }

    @Override
    public void doSendTicket(String orderId, String sid, Integer ticketStatus) {
        Map<String, List<Ticket>> listMap = new HashMap<String, List<Ticket>>();
        List<Ticket> ticketList = ticketDao.getTicketByOrderId(orderId, sid, ticketStatus);
        for (Ticket ticket : ticketList) {
        	String postCode = WeightMap.getPostCode(ticket.getLotteryCode(), ticket.getPlayCode(),ticket.getPollCode(), ticket.getAmount(), ticket.getSid(), ticket.getIssue());
        	//String postCode = WeightMap.getPostCode(ticket.getLotteryCode(), ticket.getPlayCode(), ticket.getAmount(), ticket.getIssue());
            if (listMap.containsKey(postCode)) {
                List<Ticket> tickets = listMap.get(postCode);
                tickets.add(ticket);
                listMap.put(postCode, tickets);
            } else {
                List<Ticket> tickets = new ArrayList<Ticket>();
                tickets.add(ticket);
                listMap.put(postCode, tickets);
            }
        }
        for (Map.Entry<String, List<Ticket>> entry : listMap.entrySet()) {
            String postCode = entry.getKey();
            final List<Ticket> tickets = entry.getValue();
            final Post subPost = PostMap.getPost(postCode);

            for (Ticket ticket : tickets) {
                ticketDao.updateTicketStatusForOrderId(Constants.TICKET_STATUS_SENDING, ticket.getTicketId(), ticketStatus, postCode);
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ISendClient sendClient = (ISendClient) SpringUtils.getBean(subPost.getPostClass());
                    sendClient.sendOrder(tickets);
                }
            }).start();

        }
    }

    @Override
    public void doReSendTicket(String ticketId) {
        Ticket ticket = ticketDao.getTicketByTicketId(ticketId);
        if (ticket == null) {
            logger.error("票不存在");
            throw new CndymException("票不存在");
        }
        int ticketStatus = ticket.getTicketStatus();
        if (ticketStatus != Constants.TICKET_STATUS_RESEND) {
            logger.error(ticket.getTicketId() + "发送失败，状态不是待重发");
            throw new CndymException("状态不是待重发");
        }

        String postCode = ticket.getPostCode();
        final List<Ticket> tickets = new ArrayList<Ticket>();
        tickets.add(ticket);
        final Post subPost = PostMap.getPost(postCode);

        new Thread(new Runnable() {
            @Override
            public void run() {
                ISendClient sendClient = (ISendClient) SpringUtils.getBean(subPost.getPostClass());
                sendClient.sendOrder(tickets);
            }
        }).start();
    }

    /**
     * <order userInfo="真实姓名_身份证号码_手机号码" lotId="彩种编号(彩种编号)" issue="期次" playId="玩法编号" orderId="批次号"> <ticket ticketId="票号" item="注数" multiple="倍数" amount="金额" pollId="购买方式"><![CDATA[投注号码]]></ticket>
     * <ticket ticketId="票号" item="注数" multiple="倍数" amount="金额" pollId="购买方式"><![CDATA[投注号码]]></ticket> </order>
     *
     * @param xmlBean
     * @return
     */
    @Override
    public Map<String, Object> doSaveTicket(XmlBean xmlBean, List<NumberInfo> numberInfoList, List<String> ticketIdList, Integer totalAmount) {
        XmlBean orderBean = xmlBean.getFirst("order");
        String sid = Utils.formatStr(xmlBean.getFirst("sid").attribute("text"));
        String lotteryCode = Utils.formatStr(orderBean.attribute("lotteryId"));
        String issue = Utils.formatStr(orderBean.attribute("issue"));
        String playCode = Utils.formatStr(orderBean.attribute("playId"));
        String userInfo = orderBean.attribute("userInfo");
        String orderId = Utils.formatStr(orderBean.attribute("orderId"));

        // 接入商+批次号唯一索引
        SidOrder sidOrder = new SidOrder();
        sidOrder.setSid(sid);
        sidOrder.setOrderId(orderId);
        sidOrder.setSidOrderId(sid + "." + orderId);
        sidOrder.setCreateTime(new Date());
        sidOrderDao.save(sidOrder);

        Member member = memberDao.getMemberBySid(sid);

        List<Ticket> saveTicketList = new ArrayList<Ticket>();

        for (NumberInfo numberInfo : numberInfoList) {
            Ticket ticket = new Ticket();
            ticket.setSid(sid);
            ticket.setUserCode(member.getUserCode());
            ticket.setUserInfo(userInfo);
            ticket.setOrderId(orderId);
            ticket.setOutTicketId(numberInfo.getTicketId());
            ticket.setSidOutTicketId(sid + "." + ticket.getOutTicketId());
            ticket.setTicketId(TicketIdBuildUtils.buildTicketId());
            ticket.setLotteryCode(lotteryCode);
            ticket.setIssue(issue);
            ticket.setPlayCode(playCode);
            ticket.setPollCode(numberInfo.getPollCode());
            ticket.setNumberInfo(numberInfo.getNumber());
            ticket.setItem(numberInfo.getItem());
            ticket.setMultiple(numberInfo.getMultiple());
            ticket.setAmount(((double) numberInfo.getAmount()));
            ticket.setPostCode(Constants.NO_POST_CODE);

            if (Utils.isSendTicket(lotteryCode) && Utils.isSendTicketByDiy(lotteryCode)) {
                ticket.setTicketStatus(Constants.TICKET_STATUS_DOING);
            } else {
                ticket.setTicketStatus(Constants.TICKET_STATUS_WAIT);
            }

            ticket.setBonusStatus(Constants.BONUS_STATUS_WAIT);
            ticket.setBonusAmount(Constants.DOUBLE_ZERO);
            ticket.setFixBonusAmount(Constants.DOUBLE_ZERO);
            ticket.setBigBonus(Constants.INT_ZERO);
            ticket.setDuiJiangStatus(Constants.DUI_JIANG_STATUS_NO);
            ticket.setCreateTime(new Date());
            ticket.setAcceptTime(ticket.getCreateTime());
            // 如果是竞彩和北单，保存场次信息
            String lotteryCodeStr = "200,201,400";
            if (lotteryCodeStr.contains(lotteryCode)) {
                setTicketReCode(ticket);
            }
            saveTicketList.add(ticket);
        }
        ticketDao.saveAllObject(saveTicketList);
        
        // 扣款
        boolean pay = false;
        try {
            LotteryClass lotteryClass = LotteryList.getLotteryClass(lotteryCode);
            Map<String, String> memo = new HashMap<String, String>();
            memo.put("memo", lotteryClass.getName() + Constants.ORDER_SUCCESS_MEMO);
            pay = accountService.doAccount(member.getUserCode(), Constants.P10000, totalAmount.doubleValue(), orderId, sid);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CndymException(ErrCode.E8004);
        }
        if (!pay) {
            throw new CndymException(ErrCode.E8004);
        }
        
        Map<String, Object> res = new HashMap<String, Object>();
        if (Utils.isSendTicket(lotteryCode)) {
            res.put("orderId", orderId);
        }
        res.put("returnBean", buildTicketXmlBean(saveTicketList, orderId));
        return res;
    }
    
    /**
     * <order userInfo="真实姓名_身份证号码_手机号码" lotId="彩种编号(彩种编号)" issue="期次" playId="玩法编号" orderId="批次号"> <ticket ticketId="票号" item="注数" multiple="倍数" amount="金额" pollId="购买方式"><![CDATA[投注号码]]></ticket>
     * <ticket ticketId="票号" item="注数" multiple="倍数" amount="金额" pollId="购买方式"><![CDATA[投注号码]]></ticket> </order>
     *
     * @param xmlBean
     * @return
     */
    @Override
    public Map<String, Object> doSaveTicket(XmlBean xmlBean, List<NumberInfo> numberInfoList, List<String> ticketIdList,Map<String, List<MatchTimeInfo>> matchTimeInfoMap,Integer totalAmount) {
        XmlBean orderBean = xmlBean.getFirst("order");
        String sid = Utils.formatStr(xmlBean.getFirst("sid").attribute("text"));
        String lotteryCode = Utils.formatStr(orderBean.attribute("lotteryId"));
        String issue = Utils.formatStr(orderBean.attribute("issue"));
        String playCode = Utils.formatStr(orderBean.attribute("playId"));
        String userInfo = orderBean.attribute("userInfo");
        String orderId = Utils.formatStr(orderBean.attribute("orderId"));

        // 接入商+批次号唯一索引
        SidOrder sidOrder = new SidOrder();
        sidOrder.setSid(sid);
        sidOrder.setOrderId(orderId);
        sidOrder.setSidOrderId(sid + "." + orderId);
        sidOrder.setCreateTime(new Date());
        sidOrderDao.save(sidOrder);

        Member member = memberDao.getMemberBySid(sid);

        List<Ticket> saveTicketList = new ArrayList<Ticket>();

        for (NumberInfo numberInfo : numberInfoList) {
            Ticket ticket = new Ticket();
            ticket.setSid(sid);
            ticket.setUserCode(member.getUserCode());
            ticket.setUserInfo(userInfo);
            ticket.setOrderId(orderId);
            ticket.setOutTicketId(numberInfo.getTicketId());
            ticket.setSidOutTicketId(sid + "." + ticket.getOutTicketId());
            ticket.setTicketId(TicketIdBuildUtils.buildTicketId());
            ticket.setLotteryCode(lotteryCode);
            ticket.setIssue(issue);
            ticket.setPlayCode(playCode);
            ticket.setPollCode(numberInfo.getPollCode());
            ticket.setNumberInfo(numberInfo.getNumber());
            ticket.setItem(numberInfo.getItem());
            ticket.setMultiple(numberInfo.getMultiple());
            ticket.setAmount(((double) numberInfo.getAmount()));
            ticket.setPostCode(Constants.NO_POST_CODE);

            if (Utils.isSendTicket(lotteryCode) && Utils.isSendTicketByDiy(lotteryCode)) {
                ticket.setTicketStatus(Constants.TICKET_STATUS_DOING);
            } else {
                ticket.setTicketStatus(Constants.TICKET_STATUS_WAIT);
            }

            ticket.setBonusStatus(Constants.BONUS_STATUS_WAIT);
            ticket.setBonusAmount(Constants.DOUBLE_ZERO);
            ticket.setFixBonusAmount(Constants.DOUBLE_ZERO);
            ticket.setBigBonus(Constants.INT_ZERO);
            ticket.setDuiJiangStatus(Constants.DUI_JIANG_STATUS_NO);
            ticket.setCreateTime(new Date());
            ticket.setAcceptTime(ticket.getCreateTime());
            // 如果是竞彩和北单，保存场次信息
            String lotteryCodeStr = "200,201,400";
            if (lotteryCodeStr.contains(lotteryCode)) {
                setTicketReCode(ticket,matchTimeInfoMap);
            }
            saveTicketList.add(ticket);
        }
        ticketDao.saveAllObject(saveTicketList);
        
        // 扣款
        boolean pay = false;
        try {
            LotteryClass lotteryClass = LotteryList.getLotteryClass(lotteryCode);
            Map<String, String> memo = new HashMap<String, String>();
            memo.put("memo", lotteryClass.getName() + Constants.ORDER_SUCCESS_MEMO);
            pay = accountService.doAccount(member.getUserCode(), Constants.P10000, totalAmount.doubleValue(), orderId, sid);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CndymException(ErrCode.E8004);
        }
        if (!pay) {
            throw new CndymException(ErrCode.E8004);
        }
        
        Map<String, Object> res = new HashMap<String, Object>();
        if (Utils.isSendTicket(lotteryCode)) {
            res.put("orderId", orderId);
        }
        res.put("returnBean", buildTicketXmlBean(saveTicketList, orderId));
        return res;
    }

    private XmlBean buildTicketXmlBean(List<Ticket> ticketList, String orderId) {
        StringBuffer xml = new StringBuffer();
        xml.append("<order orderId=\"").append(orderId).append("\">");
        for (Ticket ticket : ticketList) {
            xml.append("<ticket ticketId=\"").append(ticket.getOutTicketId()).append("\"");
            xml.append(" sysId=\"").append(ticket.getTicketId()).append("\"");
            xml.append("></ticket>");
        }
        xml.append("</order>");
        return ByteCodeUtil.xmlToObject(xml.toString());
    }
    
    public void setTicketReCode(Ticket ticket,Map<String, List<MatchTimeInfo>> MatchTimeInfoMap){
    	String outTicketId = ticket.getOutTicketId();
    	List<MatchTimeInfo> matchList = MatchTimeInfoMap.get(outTicketId);
    	List<TicketReCode> ticketReCodeList = new ArrayList<TicketReCode>();
    	for(MatchTimeInfo matchTimeInfo : matchList){
    		 TicketReCode ticketReCode = new TicketReCode();
             ticketReCode.setTicketId(ticket.getTicketId());
             ticketReCode.setLotteryCode(ticket.getLotteryCode());
             ticketReCode.setIssue(ticket.getIssue());
             ticketReCode.setMatchId(matchTimeInfo.getMatchId());
             ticketReCode.setEndTime(matchTimeInfo.getTime());
             ticketReCode.setCreateTime(new Date());
             ticketReCodeList.add(ticketReCode);
    	}
    	ticketReCodeDao.saveAllObject(ticketReCodeList);
    	
    	MatchTimeInfo start = matchList.get(0);
        MatchTimeInfo end = matchList.get(matchList.size() - 1);
        ticket.setStartGameId(start.getMatchId());
        ticket.setEndGameId(end.getMatchId());
        ticket.setGameStartTime(start.getTime());
        ticket.setGameEndTime(end.getTime());
    }
    
    public void setTicketReCode(Ticket ticket) {
        ISubIssueOperator subIssueOperator = (ISubIssueOperator) SpringUtils.getBean("comm" + ticket.getLotteryCode() + "SubIssueOperator");
        String numberInfo = ticket.getNumberInfo();
        String[] arr = numberInfo.split("\\|");
        if (arr.length != 2) {
            throw new CndymException(ErrCode.E8111);
        }
        String[] matchIdArr = arr[0].split(";");
        String guoGuan = arr[1];
        String pollCode = ticket.getPollCode();
        if (guoGuan.equals("1*1")) {
            pollCode = "01";
        }

        List<MatchTimeInfo> matchList = new ArrayList<MatchTimeInfo>();
        for (String number : matchIdArr) {
            String[] subArr = number.split(":");
            if ((!"10".equals(ticket.getPlayCode()) && subArr.length != 2) || ("10".equals(ticket.getPlayCode()) && subArr.length != 3)) {
                throw new CndymException(ErrCode.E8111);
            }
            MatchTimeInfo matchTimeInfo = new MatchTimeInfo();
            String palyCode = "";
            if ("10".equals(ticket.getPlayCode())) {
                palyCode = subArr[1];
            } else {
                palyCode = ticket.getPlayCode();
            }
            SubIssueComm subIssueComm = subIssueOperator.getSubIssueComm(ticket.getIssue(), subArr[0], palyCode, pollCode);
            matchTimeInfo.setMatchId(subArr[0]);
            matchTimeInfo.setIssue(subIssueComm.getIssue());
            matchTimeInfo.setTime(subIssueComm.getEndTime());
            matchTimeInfo.setDanShiEndTime(subIssueComm.getDanShiEndTime());
            matchTimeInfo.setFuShiEndTime(subIssueComm.getFuShiEndTime());
            matchList.add(matchTimeInfo);

            TicketReCode ticketReCode = new TicketReCode();
            ticketReCode.setTicketId(ticket.getTicketId());
            ticketReCode.setLotteryCode(ticket.getLotteryCode());
            ticketReCode.setIssue(ticket.getIssue());
            ticketReCode.setMatchId(matchTimeInfo.getMatchId());
            ticketReCode.setEndTime(matchTimeInfo.getTime());
            ticketReCode.setCreateTime(new Date());

            ticket.setPollCode(subIssueComm.getPollCode());
            ticketReCodeDao.save(ticketReCode);
        }
        Collections.sort(matchList);
        MatchTimeInfo start = matchList.get(0);
        if (start.getFuShiEndTime().getTime() <= new Date().getTime()) {
            throw new CndymException(ErrCode.E8118);
        }
        MatchTimeInfo end = matchList.get(matchList.size() - 1);
        ticket.setStartGameId(start.getMatchId());
        ticket.setEndGameId(end.getMatchId());
        ticket.setGameStartTime(start.getTime());
        ticket.setGameEndTime(end.getTime());
    }

    @Override
    public List<Ticket> getTicketByOutTicketId(List<String> ticketIdList, String sid) {
        return ticketDao.getTicketByOutTicketId(ticketIdList, sid);
    }

    @Override
    public List<Ticket> getTicketByOrderId(String orderId, String sid) {
        return ticketDao.getTicketByOrderId(orderId, sid);
    }

    @Override
    public PageBean getBounsTicketByIssue(String sid, String lotteryCode, String issue, int page, int pageSize) {
        return ticketDao.getBounsTicketByIssue(sid, lotteryCode, issue, page, pageSize);
    }

    @Override
    public int updateForSubIssueUpdate(String ticketId, String sn, Date date) {
        Ticket ticket = ticketDao.getTicketByTikcetId(ticketId);
        long oldStartTime = ticket.getGameStartTime().getTime();
        long oldendTime = ticket.getGameEndTime().getTime();
        String oldStartId = ticket.getStartGameId();
        String oldEndId = ticket.getEndGameId();
        long curr = date.getTime();
        if (curr < oldStartTime) {
            ticket.setStartGameId(sn);
            ticket.setGameStartTime(date);
        } else if (curr == oldStartTime) {
            if (Utils.formatInt(sn, 0) < Utils.formatInt(oldStartId, 0)) {
                ticket.setStartGameId(sn);
                ticket.setGameStartTime(date);
            }
        }
        if (curr > oldendTime) {
            ticket.setEndGameId(sn);
            ticket.setGameEndTime(date);
        } else if (curr == oldendTime) {
            if (Utils.formatInt(sn, 0) < Utils.formatInt(oldEndId, 0)) {
                ticket.setEndGameId(sn);
                ticket.setGameEndTime(date);
            }
        }
        if ((curr >= oldStartTime && curr <= oldendTime && (sn.equals(oldEndId) || sn.equals(oldStartId))) || (curr < oldStartTime && sn.equals(oldEndId))
                || (curr > oldendTime && sn.equals(oldStartId))) {
            List<Map<String, Object>> subList = null;
            if ("200".equals(ticket.getLotteryCode())) {
                subList = subIssueForJingCaiZuQiuDao.getSortListByTicketId(ticketId);
            } else if ("201".equals(ticket.getLotteryCode())) {
                subList = subIssueForJingCaiLanQiuDao.getSortListByTicketId(ticketId);
            } else if ("400".equals(ticket.getLotteryCode())) {
                subList = subIssueForBeiDanDao.getSortListByTicketId(ticketId);
            }
            if (null != subList) {
                Map<String, Object> startMap = subList.get(0);
                Map<String, Object> endMap = subList.get(subList.size() - 1);
                ticket.setStartGameId(("400".equals(ticket.getLotteryCode()) ? "" : startMap.get("issue")) + "" + startMap.get("sn"));
                ticket.setGameStartTime((Date) startMap.get("end_time"));
                ticket.setEndGameId(("400".equals(ticket.getLotteryCode()) ? "" : endMap.get("issue")) + "" + endMap.get("sn"));
                ticket.setGameEndTime((Date) endMap.get("end_time"));
            }
        }
        return ticketDao.updateForSubIssueUpdate(ticket);
    }

    public int updateBonusAmount(BonusLog bonusLog) {
        return ticketDao.updateBonusAmount(bonusLog);
    }

    @Override
    public int doEndIssueOperator(String lotteryCode, String issue) {
        List<Ticket> ticketList = ticketDao.getTicketsForEndIssue(lotteryCode, issue);
        for (Ticket ticket : ticketList) {
            logger.info("出票过期取消订单(" + ticket.getTicketId() + ")");
            Ticket subTicket = ticketDao.getTicketByTicketIdForUpdate(ticket.getTicketId());
            subTicket.setAcceptTime(new Date());
            subTicket.setTicketStatus(Constants.TICKET_STATUS_CANCEL);
            accountService.doAccount(subTicket.getUserCode(), Constants.R00100, subTicket.getAmount(), subTicket.getOrderId(), Constants.P10000, subTicket.getTicketId(), subTicket.getSid());
            ticketDao.update(subTicket);
        }
        return 0;
    }

    @Override
    public int doEndIssueOperator() {
        List<Ticket> ticketList = ticketDao.getTicketsForEndIssue();
        for (Ticket ticket : ticketList) {
            logger.info("出票过期取消订单(" + ticket.getTicketId() + ")");
            Ticket subTicket = ticketDao.getTicketByTicketIdForUpdate(ticket.getTicketId());
            subTicket.setAcceptTime(new Date());
            subTicket.setTicketStatus(Constants.TICKET_STATUS_CANCEL);
            accountService.doAccount(subTicket.getUserCode(), Constants.R00100, subTicket.getAmount(), subTicket.getOrderId(), Constants.P10000, subTicket.getTicketId(), subTicket.getSid());
            ticketDao.update(subTicket);
        }
        return 0;
    }

    /**
     * 手工处理票失败
     */
    @Override
    public int doHhandTicketFailed(String ticketId) {
        if (Utils.isNotEmpty(ticketId)) {
            // logger.info("取消订单(" + ticketId + ")");
            Ticket subTicket = ticketDao.getTicketByTicketIdForUpdate(ticketId);
            if (subTicket.getTicketStatus() == Constants.TICKET_STATUS_DOING || subTicket.getTicketStatus() == Constants.TICKET_STATUS_WAIT
                    || subTicket.getTicketStatus() == Constants.TICKET_STATUS_SENDING || subTicket.getTicketStatus() == Constants.TICKET_STATUS_RESEND) {
                subTicket.setAcceptTime(new Date());
                subTicket.setTicketStatus(Constants.TICKET_STATUS_FAILURE);

                List<SubTicket> subTicketList = subTicketDao.findSubTicketListEx(ticketId);
                if (Utils.isNotEmpty(subTicketList)) {
                    for (SubTicket sTicket : subTicketList) {
                        sTicket.setTicketStatus(Constants.TICKET_STATUS_FAILURE);

                        subTicketDao.update(sTicket);
                    }
                }

                logger.info("票(" + subTicket.getTicketId() + ")手动取消，进行退款处理，金额(" + subTicket.getAmount() + ")");
                accountService.doAccount(subTicket.getUserCode(), Constants.R00100, subTicket.getAmount(), subTicket.getOrderId(), Constants.P10000, subTicket.getTicketId(), subTicket.getSid());
                ticketDao.update(subTicket);

                return 0;
            } else {
                logger.error("票状态不对,ticketId:" + ticketId + ",status:" + subTicket.getTicketStatus());
            }

        }
        return -1;
    }

    /**
     * 手工处理票失败
     */
    @Override
    public int doHhandTicketFailedByTicketId(String ticketId) {
        if (Utils.isNotEmpty(ticketId)) {
            // logger.info("取消订单(" + ticketId + ")");
            Ticket ticket = ticketDao.getTicketByTicketIdForUpdate(ticketId);
            logger.error("票状态,ticketId:" + ticketId + ",status:" + ticket.getTicketStatus());
            if (ticket.getTicketStatus() == Constants.TICKET_STATUS_FAILURE) {

                AccountOperatorTemp accountOperatorTemp = new AccountOperatorTemp();
                accountOperatorTemp.setOrderId(ticket.getOrderId());
                accountOperatorTemp.setTicketId(ticket.getTicketId());
                accountOperatorTemp.setSid(ticket.getSid());
                accountOperatorTemp.setResources(Constants.OPERATOR_FROM_SYS);
                accountOperatorTemp.setUserCode(ticket.getUserCode());
                accountOperatorTemp.setAmount(ticket.getAmount());
                accountOperatorTemp.setEventCode(Constants.R00100);
                accountOperatorTemp.setStatus(Constants.ACCOUNT_OPERATOR_TEMP_STATUS_WAIT);
                accountOperatorTemp.setCreateTime(new Date());
                accountOperatorTemp.setAcceptTime(accountOperatorTemp.getCreateTime());
                accountOperatorTempDao.save(accountOperatorTemp);

                return 0;
            } else {
                logger.error("票状态不对,ticketId:" + ticketId + ",status:" + ticket.getTicketStatus());
            }

        }

        logger.error("end");
        return -1;
    }

    @Override
    public Ticket getTicketByTicketId(String ticketId) {
        return ticketDao.getTicketByTicketId(ticketId);
    }
    @Override
    public Ticket getTicketById(long id) {
    	return ticketDao.getTicketById(id);
    }

    @Override
    public Ticket getTicketByBackup1(String backup1, String postCode) {
        return ticketDao.getTicketByBackup1(backup1, postCode);
    }

    @Override
    public int updateSpInfo(String ticketId, String spInfo) {
        return ticketDao.updateSpInfo(ticketId, spInfo);
    }

    @Override
    public List<Map<String, Object>> getSendTicket(String lotteryCode, String issue) {
        return ticketDao.getSendTicket(lotteryCode, issue);
    }

    @Override
    public List<Map<String, Object>> getSendTicket(String lotteryCode) {
        return ticketDao.getSendTicket(lotteryCode);
    }

    @Override
    public PageBean getSendedTicket(String lotteryCode, String postCode, int page, int pageSize) {
        return ticketDao.getSendedTicket(lotteryCode, postCode, page, pageSize);
    }

    @Override
    public PageBean getSendedTicket(String lotteryCode, String postCode, int page, int pageSize, String desc) {
        return ticketDao.getSendedTicket(lotteryCode, postCode, page, pageSize, desc);
    }

    @Override
    public PageBean getSendedTicketOrderByIssue(String lotteryCode, String postCode, int page, int pageSize) {
        return ticketDao.getSendedTicketOrderByIssue(lotteryCode, postCode, page, pageSize);
    }

    @Override
    public PageBean getSendedTicket(String lotteryCode, String playCode, String pollCode, String postCode, int page, int pageSize) {
        return ticketDao.getSendedTicket(lotteryCode, playCode, pollCode, postCode, page, pageSize);
    }

    @Override
    public PageBean getSendedTicket(String postCode, int page, int pageSize) {
        return ticketDao.getSendedTicket(postCode, page, pageSize);
    }
    
    @Override
    public PageBean getSendedDelayTicket(String lotteryCode, String postCode,Date sendTime,Date createTime, int page, int pageSize){
    	return ticketDao.getSendedDelayTicket(lotteryCode, postCode, sendTime, createTime, page, pageSize);
    }

    @Override
    public int doOutBonus(String lotteryCode, String issue, List<BonusLog> bonusLogList) {
        try {
            MainIssue mainIssue = mainIssueDao.getMainIssueForUpdate(lotteryCode, issue);
            if (!Utils.isNotEmpty(mainIssue)) {
                return 0;
            }
            if (mainIssue.getStatus() != Constants.ISSUE_STATUS_END) {
                return 0;
            }
            if (mainIssue.getCenterBonusStatus() == Constants.ISSUE_CENTER_BONUS_STATUS_YES) {
                return 0;
            }
            String allowOutBonusLottery = ConfigUtils.getValue("ALLOW_OUT_BONUS_LOTTERY");
            if (allowOutBonusLottery.contains(lotteryCode)) {
                // 更新票和期次算奖状态
                for (BonusLog bonusLog : bonusLogList) {
                    ticketDao.updateBonusAmount(bonusLog);
                }
                mainIssue.setOperatorsAward(Constants.OPERATORS_AWARD_COMPLETE);
            }
            mainIssue.setCenterBonusStatus(Constants.ISSUE_CENTER_BONUS_STATUS_YES);
            mainIssueDao.update(mainIssue);
            bonusLogDao.saveAllObject(bonusLogList);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int doOutBonus(String lotteryCode, String issue, String sn, List<BonusLog> bonusLogList) {
        try {
            SubIssueForBeiDan subIssueForBeiDan = subIssueForBeiDanDao.getSubIssueForBeiDanByIssueSn(issue, sn);
            if (!Utils.isNotEmpty(subIssueForBeiDan)) {
                return 0;
            }
            if (subIssueForBeiDan.getEndOperator() != Constants.SUB_ISSUE_END_OPERATOR_END) {
                return 0;
            }
            if (subIssueForBeiDan.getOperatorsAward() == Constants.OPERATORS_AWARD_COMPLETE) {
                return 0;
            }
            String allowOutBonusLottery = ConfigUtils.getValue("ALLOW_OUT_BONUS_LOTTERY");
            if (allowOutBonusLottery.contains(lotteryCode)) {
                // 更新票和期次算奖状态
                for (BonusLog bonusLog : bonusLogList) {
                    ticketDao.updateBonusAmount(bonusLog);
                }
                subIssueForBeiDan.setOperatorsAward(Constants.OPERATORS_AWARD_COMPLETE);
            }
            subIssueForBeiDanDao.update(subIssueForBeiDan);
            bonusLogDao.saveAllObject(bonusLogList);
            return 1;
        } catch (Exception e) {
            logger.error("doOutBonus sn", e);
            return 0;
        }
    }

    @Override
    public int doNoBonus(String lotteryCode, String issue) {
        return ticketDao.doNoBonus(lotteryCode, issue);
    }

    @Override
    public int doNoBonus(String lotteryCode, String issue, String sn) {
        return ticketDao.doNoBonus(lotteryCode, issue, sn);
    }

    @Override
    public int doUpdateTicketForDuiJiang(Ticket ticket) {
        return ticketDao.updateTicketForDuiJiang(ticket);
    }

    @Override
    public List<Ticket> getNoDuiJiangTicket(String lotteryCode, String postCode) {
        return ticketDao.getNoDuiJiangTicket(lotteryCode, postCode);
    }

    @Override
    public PageBean getPageBeanByTicket(TicketQueryBean ticketQueryBean, Integer page, Integer pageSize) {
        return ticketDao.getPageBeanByTicket(ticketQueryBean, page, pageSize);
    }

    @Override
    public int updateSaleInfo(String ticketId, String saleInfo) {
        return ticketDao.updateSaleInfo(ticketId, saleInfo);
    }

    @Override
    public int doUpdateSendingToSuccess(String lotteryCode, String issue) {
        try {
            MainIssue mainIssue = mainIssueDao.getMainIssueForUpdate(lotteryCode, issue);
            if (!Utils.isNotEmpty(mainIssue)) {
                return 0;
            }
            if (mainIssue.getStatus() != Constants.ISSUE_STATUS_END) {
                return 0;
            }
            ticketDao.updateSendingTicketToSuccess(lotteryCode, issue);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public PageBean getTicketCountByDay(Map<String, Object> cond) {
        int page = (Integer) cond.get("page");
        int pageSize = (Integer) cond.get("pageSize");

        int count = ticketDao.getTicketCountByDayCount(cond);
        int mod = count % pageSize;

        int pageTotal = 0;
        if (mod == 0) {
            pageTotal = count / pageSize;
        } else {
            pageTotal = count / pageSize + 1;
        }

        List<Map<String, Object>> rsList = new ArrayList<Map<String, Object>>();
        if (page <= pageTotal) {
            int start = (page - 1) * pageSize;
            int end = page * pageSize;

            cond.put("start", start);
            cond.put("end", end);

            rsList = ticketDao.getTicketCountByDay(cond);
        }

        PageBean pageBean = new PageBean();
        pageBean.setPageId(page);
        pageBean.setItemTotal(new Long(count));
        pageBean.setPageContent(rsList);
        pageBean.setPageTotal(pageTotal);

        return pageBean;
    }

    @Override
    public void doBonusToAccount(String ticketId, double amount) {
        Ticket ticket = ticketDao.getTicketByTicketIdForUpdate(ticketId);
        ticket.setBonusAmount(amount);
        ticket.setFixBonusAmount(amount);
        ticket.setBigBonus(0);
        ticket.setBonusStatus(Constants.BONUS_STATUS_YES);
        ticketDao.update(ticket);

        saveBonusTicket(ticket);

        accountService.doAccount(ticket.getUserCode(), Constants.R00200, ticket.getBonusAmount(), ticket.getLotteryCode() + "." + ticket.getIssue(), ticket.getSid());
    }

    @Override
    public Ticket getTicketBySaleCode(String lotteryCode, String issue, String saleCode, String postCode) {
        return ticketDao.getTicketBySaleCode(lotteryCode, issue, saleCode, postCode);
    }

    @Override
    public int getTicketCountByIssue(String lotteryCode, String issue) {
        return ticketDao.getTicketCountByIssue(lotteryCode, issue);
    }

    @Override
    public Map<String, Object> getTicketMainIssueCountByIssue(TicketMainIssueBean ticketMainIssueBean) {
        return ticketDao.getTicketMainIssueCountByIssue(ticketMainIssueBean);
    }

    @Override
    public PageBean getSendedTicketEx(String lotteryCode, String postCode, Date sendTime, int page, int pageSize) {
        return ticketDao.getSendedTicketEx(lotteryCode, postCode, sendTime, page, pageSize);
    }

    @Override
    public PageBean getPageBeanByParaSending(Ticket ticket, Integer page, Integer pageSize) {
        return ticketDao.getPageBeanByParaSending(ticket, page, pageSize);
    }

    @Override
    public PageBean getSendedTicketEXEX(String lotteryCode, String postCode, String issue, int page, int pageSize) {
        return ticketDao.getSendedTicketEXEX(lotteryCode, postCode, issue, page, pageSize);
    }

    @Override
    public List<Map<String, Object>> findNoSendTicket(Integer ticketStatus, String postCode) {
        return ticketDao.findNoSendTicket(ticketStatus, postCode);
    }

    @Override
    public List<Ticket> getTicketsForEndIssue(String lotteryCode, String issue) {
        return ticketDao.getTicketsForEndIssue(lotteryCode, issue);
    }

    @Override
    public PageBean getPageBeanByParaLottery(TicketQueryBean queryBean) {
        return ticketDao.getPageBeanByParaLottery(queryBean);
    }

    @Override
    public int updateTicketForWait(String ticketId) {
        return ticketDao.updateTicketForWait(ticketId);
    }

    @Override
    public PageBean getIssueLotteryCount(TicketMainIssueBean ticketMainIssueBean, Integer page, Integer pageSize) {
        return ticketDao.getIssueLotteryCount(ticketMainIssueBean, page, pageSize);
    }

    @Override
    public List<Map<String, Object>> findNoSendTicketList(Integer ticketStatus, Date createStartTime, Date createEndTime, String postCode) {
        return ticketDao.findNoSendTicketList(ticketStatus, createStartTime, createEndTime, postCode);
    }

	@Override
    public List<Ticket> getTicketForHandSend(String lotteryCode, String issue, Integer ticketStatus) {
        return ticketDao.getTicketForHandSend(lotteryCode, issue, ticketStatus);
    }

    @Override
    public PageBean getDateOrIssueCount(TicketQueryBean ticketQueryBean, String type, Integer page, Integer pageSize) {
        return ticketDao.getDateOrIssueCount(ticketQueryBean, type, page, pageSize);
    }

    @Override
    public List<Map<String, Object>> getDateOrIssueCountByMsg(TicketQueryBean ticketQueryBean, String type) {
        return ticketDao.getDateOrIssueCountByMsg(ticketQueryBean, type);
    }

    /**
     * 手工处理票失败
     */
    @Override
    public int doHhandTicketSuccess(String ticketId) {
        if (Utils.isNotEmpty(ticketId)) {
            // logger.info("取消订单(" + ticketId + ")");
            Ticket subTicket = ticketDao.getTicketByTicketIdForUpdate(ticketId);
            if (subTicket.getTicketStatus() == Constants.TICKET_STATUS_SUCCESS) {
                subTicket.setAcceptTime(new Date());
                subTicket.setTicketStatus(Constants.TICKET_STATUS_FAILURE);

                List<SubTicket> subTicketList = subTicketDao.findSubTicketListEx(ticketId);
                if (Utils.isNotEmpty(subTicketList)) {
                    for (SubTicket sTicket : subTicketList) {
                        sTicket.setTicketStatus(Constants.TICKET_STATUS_FAILURE);

                        subTicketDao.update(sTicket);
                    }
                }

                logger.info("票(" + subTicket.getTicketId() + ")手动取消，进行退款处理，金额(" + subTicket.getAmount() + ")");
                accountService.doAccount(subTicket.getUserCode(), Constants.R00100, subTicket.getAmount(), subTicket.getOrderId(), Constants.P10000, subTicket.getTicketId(), subTicket.getSid());
                ticketDao.update(subTicket);

                return 0;

            } else {
                logger.error("票状态不对,ticketId:" + ticketId + ",status:" + subTicket.getTicketStatus() + ",此处只允许成功票退款");
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        ITicketDao ticketDao = (ITicketDao) SpringUtils.getBean("ticketDaoImpl");
        Ticket ticket = ticketDao.getTicketByTicketId("31022103912371647887");
        ticket.setPollCode("00");
        TicketServiceImpl serviceImpl = new TicketServiceImpl();
        serviceImpl.setTicketReCode(ticket);
    }

}
