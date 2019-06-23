package com.cndym.sendClient;

import com.cndym.bean.tms.BonusLog;
import com.cndym.bean.tms.SubTicket;
import com.cndym.bean.tms.Ticket;
import com.cndym.utils.Utils;
import com.cndym.utils.xml.parse.ByteCodeUtil;
import com.cndym.utils.xml.parse.XmlBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：邓玉明 时间：11-5-26 下午4:32 QQ：757579248 email：cndym@163.com
 */
public class BaseSendClient implements ISendClient {
    @Override
    public XmlBean sendOrder(List<Ticket> tickets) {
        return null;
    }

    @Override
    public XmlBean bonusQuery(XmlBean xmlBean) {
        return null;
    }

    @Override
    public XmlBean bonusJcQuery(XmlBean xmlBean) {
        return null;
    }

    @Override
    public XmlBean issueQuery(XmlBean xmlBean) {
        return null;
    }

    @Override
    public XmlBean gpIssueQuery(XmlBean xmlBean) {
        return null;
    }

    @Override
    public XmlBean bonusInfoQuery(XmlBean xmlBean) {
        return null;
    }

    @Override
    public XmlBean orderQuery(XmlBean xmlBean) {
        return null;
    }

    @Override
    public XmlBean issueSaleQuery(XmlBean xmlBean) {
        return null;
    }

    @Override
    public XmlBean sendBonus(List<BonusLog> bonusLogs) {
        return null;
    }

    @Override
    public double accountQuery() {
        return 0d;
    }

    @Override
    public double accountQuery(String type) {
        return 0d;
    }

    public static void main(String[] args) {
        String tt = "DXF|120428301=1,120428302=2,120428303=2,120428304=1,120429301=1,120429302=1,120429303=2|7*1";
        BaseSendClient sendClient = new BaseSendClient();
        System.out.println(sendClient.build(tt, 0));
    }

    // 以下两个程序段为测试竞彩投注返回使用
    public String build(String str, int type) {
        // 玩法|比赛编号1=投注号码1(固定奖金)/投注号码2(固定奖金),比赛编号2=投注号码1(固定奖金)/投注号码2(固定奖金)|过关方式
        Map<String, String> doubleMap = new HashMap<String, String>();
        String arr[] = str.split(";");
        StringBuffer reStr = new StringBuffer();
        for (String sub : arr) {
            String arrA[] = sub.split("\\|");
            String arrB[] = arrA[1].split(",");
            StringBuffer buffer = new StringBuffer(arrA[0]).append("|");
            StringBuffer subBuffer = new StringBuffer();
            int i = 0;
            for (String s : arrB) {
                String arrC[] = s.split("=");
                String arrD[] = arrC[1].split("/");

                String value = doubleMap.get(arrC[0]);
                if (null == value) {
                    StringBuffer val = new StringBuffer();
                    for (String s1 : arrD) {
                        val.append(",").append(randomD1Build());
                    }
                    if (type == 1) {// 大小分
                        val.append(",").append(randomD2Build());
                    } else if (type == 2) {// 让分胜负
                        val.append(",").append(randomD3Build());
                    }
                    value = val.substring(1);
                    doubleMap.put(arrC[0], value);
                }
                String[] valueArr = value.split(",");
                subBuffer.append(",");
                if (type == 0) {
                    subBuffer.append(arrC[0]).append("=");
                } else if (type == 1) {// 大小分
                    subBuffer.append(arrC[0]).append("(").append(valueArr[valueArr.length - 1]).append(")").append("=");
                } else if (type == 2) {// 让分胜负
                    subBuffer.append(arrC[0]).append("(").append(valueArr[valueArr.length - 1]).append(")").append("=");
                }
                int j = 0;
                for (String s1 : arrD) {
                    if (j == 0) {
                        subBuffer.append(s1).append("(").append(valueArr[j]).append(")");
                    } else {
                        subBuffer.append("/").append(s1).append("(").append(valueArr[j]).append(")");
                    }
                    j++;
                }
                i++;
            }

            buffer.append(subBuffer.substring(1)).append("|").append(arrA[2]);
            reStr.append(";").append(buffer);
        }
        return reStr.substring(1);
    }

    private double randomD1Build() {
        double temp[] = {1.00d, 1.00d, 1.00d, 1.00d, 1.00d, 1.00d, 1.00d, 1.00d, 1.00d};
        return temp[Utils.intRandom()];
    }

    private double randomD2Build() {
        double temp[] = {1.00d, 1.00d, 1.00d, 1.00d, 1.00d, 1.00d, 1.00d, 1.00d, 1.00d, 1.00d};
        return temp[Utils.intRandom()];
    }

    private double randomD3Build() {
        double temp[] = {1.00d, 1.00d, 1.00d, 1.00d, 1.00d, 1.00d, 1.00d, 1.00d, 1.00d, 1.00d};
        return temp[Utils.intRandom()];
    }

    // 测试竞彩投注返回使用结束

    @Override
    public XmlBean orderQuery(List<Ticket> ticketList) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public XmlBean orderQuery(List<Ticket> ticketList, String lotteryCode) {
        return null;
    }

    @Override
    public XmlBean orderQueryForJc(List<Map<String, Object>> messageIdList) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public XmlBean getSendOrderErrorMsg(List<Ticket> ticketList, int ticketStatus, String errorCode, String errMsg) {
        StringBuffer ticketXml = new StringBuffer("<tickets>");
        StringBuffer temp = new StringBuffer();
        for (Ticket ticket : ticketList) {
            temp.append("<ticket ticketId=\"");
            temp.append(ticket.getTicketId());
            temp.append("\" status=\"");
            temp.append(ticketStatus);
            temp.append("\" errCode=\"");
            temp.append(errorCode);
            temp.append("\" errMsg=\"");
            temp.append(errMsg);
            temp.append("\" />");
        }
        ticketXml.append(temp);
        ticketXml.append("</tickets>");
        return ByteCodeUtil.xmlToObject(ticketXml.toString());
    }

    @Override
    public XmlBean subOrderQuery(List<SubTicket> subTicketList) {
        return null;
    }

    @Override
    public XmlBean matchQueryForBeiDan(XmlBean xmlBean) {
        return null;
    }

    @Override
    public XmlBean matchResultQueryForBeiDan(XmlBean xmlBean) {
        return null;
    }

    @Override
    public XmlBean matchQueryForJc(String lotteryCode, String issue) {
        return null;
    }

    @Override
    public XmlBean matchQueryForJcSp(String lotteryCode, String issue) {
        return null;
    }

    @Override
    public XmlBean bonusQueryForBeiDan(XmlBean xmlBean) {
        return null;
    }

}
