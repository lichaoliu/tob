package com.cndym.sendClient.service;

import com.cndym.bean.tms.MainIssue;
import com.cndym.jms.producer.GateWayDataOutMessageProducer;
import com.cndym.jms.producer.SendToClientBonusInfoMessageProducer;
import com.cndym.sendClient.ISendClientOperator;
import com.cndym.sendClient.ltkj.LtkjSendClientConfig;
import com.cndym.service.IMainIssueService;
import com.cndym.utils.Utils;
import com.cndym.utils.xml.parse.XmlBean;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 作者：邓玉明
 * 时间：11-6-3 下午3:56
 * QQ：757579248
 * email：cndym@163.com
 */
@Component
public class PostBonusInfoOperator extends BasePostOperator {
    @Resource
    private IMainIssueService mainIssueService;
    @Resource
    private GateWayDataOutMessageProducer gateWayDataOutMessageProducer;
    @Resource
    private SendToClientBonusInfoMessageProducer sendToClientBonusInfoMessageProducer;

    private Logger logger = Logger.getLogger(getClass());
    /*public static Object init(Object o){
        return new Object();
    }*/

    public static Object init(Object o){
       o=new  Object();
       return o;
    }

    public static void main(String[] args) {
        //Object o = new Object();
       // Object c=init(o);
      // System.out.print(c.hashCode());





    }
    @Override
    public void execute(XmlBean xmlBean) {
        if (null == xmlBean) return;
        logger.info("bonus_info:" + xmlBean.toString());
        List<XmlBean> ticketBean = xmlBean.getAll("bonusInfo");
        logger.info("ticketBean---"+ticketBean);
        for (XmlBean bean : ticketBean) {
            String lotteryCode = Utils.formatStr(bean.attribute("lotteryCode"));
            String issue = Utils.formatStr(bean.attribute("issue"));
            String bonusNumber = Utils.formatStr(bean.attribute("bonusNumber"));
            String prizePool = Utils.formatStr(bean.attribute("prizePool"));
            String plussalevalue = Utils.formatStr(bean.attribute("plussalevalue"));
            String bonusClass = Utils.formatStr(bean.attribute("bonusClass"));
            String backup1 = Utils.formatStr(bean.attribute("backup1"));
            String saleTotal = Utils.formatStr(bean.attribute("saleTotal"));
            String globalSaleTotal = Utils.formatStr(bean.attribute("globalSaleTotal"));
            JSONArray bonusDetailArray=new JSONArray();
            logger.info("lotteryCode--------->"+lotteryCode+"  xmlBean:"+xmlBean);
            if(LtkjSendClientConfig.getValue("POST_CODE_LOTTERY").contains(lotteryCode)) {
                List<XmlBean> bonusDetailList =bean.getFirst("awardGroup").getAll("awardDetail");
                logger.info("bonusDetailList--------------->"+bonusDetailList+"  saleTotal:"+saleTotal);
                if (Utils.isNotEmpty(bonusDetailList)) {
                    Integer  oneAmount=0;
                    Integer  twoAmount=0;
                    Integer  threeAmount=0;
                    Integer  fourAmount=0;
                    Integer  fiveAmount=0;
                    Integer  sixAmount=0;
                    for (XmlBean bonusDetail : bonusDetailList) {
                        String awardName=bonusDetail.attribute("awardName");
                        Integer awardTotalCount=Integer.valueOf(bonusDetail.attribute("awardTotalCount"));
                        Integer awardAmount=Integer.valueOf(bonusDetail.attribute("awardAmount"));
                        JSONObject obj=new JSONObject();
                        obj.put("c",awardAmount);
                        obj.put("m",awardTotalCount);
                        obj.put("t",awardName);
                        obj.put("a",0);
                        if(awardName.equals("一等奖") || awardName.equals("直选")){
                            obj.put("n",1);
                            oneAmount=awardAmount;
                        }
                        if(awardName.equals("二等奖") || awardName.equals("组三") ){
                            obj.put("n",2);
                            twoAmount=awardAmount;
                        }
                        if(awardName.equals("三等奖") || awardName.equals("组六")){
                            obj.put("n",3);
                            threeAmount=awardAmount;
                        }
                        if(awardName.equals("四等奖")){
                            obj.put("n",4);
                            fourAmount=awardAmount;
                        }
                        if(awardName.equals("五等奖")){
                            obj.put("n",5);
                            fiveAmount=awardAmount;
                        }
                        if(awardName.equals("六等奖")){
                            obj.put("n",6);
                            sixAmount=awardAmount;
                        }
                       /* if(awardName.equals("七等奖")){
                            obj.put("n",7);
                        }
                        if(awardName.equals("八等奖")){
                            obj.put("n",8);
                        }
                        if(awardName.equals("生肖乐")){
                            obj.put("n",10);
                        }*/

                        if(awardName.equals("十等奖")){
                            obj.put("c",awardAmount+oneAmount);
                            obj.put("n",11);
                            obj.put("t","一等奖追加");
                        }
                        if(awardName.equals("十一等奖")){
                            obj.put("c",awardAmount+twoAmount);
                            obj.put("n",12);
                            obj.put("t","二等奖追加");
                        }
                        if(awardName.equals("十二等奖")){
                            obj.put("c",awardAmount+threeAmount);
                            obj.put("n",13);
                            obj.put("t","三等奖追加");
                        }
                        if(awardName.equals("十三等奖")){
                            obj.put("c",awardAmount+fourAmount);
                            obj.put("n",14);
                            obj.put("t","四等奖追加");
                        }
                        if(awardName.equals("十四等奖")){
                            obj.put("c",awardAmount+fiveAmount);
                            obj.put("n",15);
                            obj.put("t","五等奖追加");
                        }
                        if(awardName.equals("十五等奖")){
                            obj.put("c",awardAmount+sixAmount);
                            obj.put("n",16);
                            obj.put("t","六等奖追加");
                        }
                        /*if(awardName.equals("七等奖追加")){
                            obj.put("n",17);
                        }
                        if(awardName.equals("八等奖追加")){
                            obj.put("n",18);
                        }*/

                        bonusDetailArray.add(obj);
                    }
                    logger.info("bonusDetailArray:"+bonusDetailArray.toJSONString().replace("\"","\'"));
                }
            }
            MainIssue mainIssue = mainIssueService.getMainIssueByLotteryIssue(lotteryCode, issue);
            if (null != mainIssue) {
                MainIssue sub = new MainIssue();
                sub.setId(mainIssue.getId());
                if (Utils.isNotEmpty(bonusNumber)) {
                    sub.setBonusNumber(bonusNumber);
                }
                if (Utils.isNotEmpty(prizePool)) {
                    sub.setPrizePool(prizePool);
                }
                if (Utils.isNotEmpty(bonusClass)) {
                    sub.setBonusClass(bonusClass);
                }
                if (Utils.isNotEmpty(saleTotal)) {
                    sub.setSaleTotal(saleTotal);
                }
                if (Utils.isNotEmpty(globalSaleTotal)) {
                    sub.setGlobalSaleTotal(globalSaleTotal);
                }
                if (Utils.isNotEmpty(backup1)) {
                    sub.setBackup1(backup1);
                }
                if (Utils.isNotEmpty(plussalevalue)) {
                    sub.setBackup2(plussalevalue);
                }

                if(bonusDetailArray!=null && bonusDetailArray.size()>0){
                    sub.setBonusClass(bonusDetailArray.toJSONString().replace("\"","\'"));
                }
                mainIssueService.updateIssue(sub);
                if (Utils.isNotEmpty(bonusNumber) && !"-".equals(bonusNumber)) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("lotteryCode", lotteryCode);
                    map.put("issue", issue);
                    map.put("bonusNumber", bonusNumber);
                    map.put("bonusTime", Utils.today("yyyy-MM-dd HH:mm:ss"));
                    gateWayDataOutMessageProducer.sendMessage("bonusInfo", map);
                }
            }

        }
    }

    public IMainIssueService getMainIssueService() {
        return mainIssueService;
    }

    public void setMainIssueService(IMainIssueService mainIssueService) {
        this.mainIssueService = mainIssueService;
    }

    public GateWayDataOutMessageProducer getGateWayDataOutMessageProducer() {
        return gateWayDataOutMessageProducer;
    }

    public void setGateWayDataOutMessageProducer(GateWayDataOutMessageProducer gateWayDataOutMessageProducer) {
        this.gateWayDataOutMessageProducer = gateWayDataOutMessageProducer;
    }

    public SendToClientBonusInfoMessageProducer getSendToClientBonusInfoMessageProducer() {
        return sendToClientBonusInfoMessageProducer;
    }

    public void setSendToClientBonusInfoMessageProducer(SendToClientBonusInfoMessageProducer sendToClientBonusInfoMessageProducer) {
        this.sendToClientBonusInfoMessageProducer = sendToClientBonusInfoMessageProducer;
    }
}
