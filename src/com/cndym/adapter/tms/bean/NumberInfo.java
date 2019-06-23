package com.cndym.adapter.tms.bean;

import com.cndym.utils.JsonBinder;
import com.cndym.utils.Utils;
import com.cndym.utils.xml.parse.XmlBean;

/**
 * 作者：邓玉明 时间：11-4-6 下午3:51 QQ：757579248 email：cndym@163.com
 */
public class NumberInfo {
    private String playCode;
    private String pollCode;
    private String number;
    private int item;
    private int multiple;
    private int amount;
    private String ticketId;

    public NumberInfo() {
    }


    public static NumberInfo forInstance(XmlBean ticketBean, String playCode) {
        NumberInfo numberInfo = new NumberInfo();
        numberInfo.setTicketId(ticketBean.attribute("ticketId"));
        numberInfo.setPollCode(ticketBean.attribute("pollId"));
        numberInfo.setNumber(ticketBean.attribute("text"));
        numberInfo.setItem(Utils.formatInt(ticketBean.attribute("item"), 0));
        numberInfo.setPlayCode(playCode);
        numberInfo.setMultiple(Utils.formatInt(ticketBean.attribute("multiple"), 0));
        numberInfo.setAmount(Utils.formatInt(ticketBean.attribute("amount"), 0));
        return numberInfo;

    }

    public String getPlayCode() {
        return playCode;
    }

    public void setPlayCode(String playCode) {
        this.playCode = playCode;
    }

    public String getPollCode() {
        return pollCode;
    }

    public void setPollCode(String pollCode) {
        this.pollCode = pollCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public int getMultiple() {
        return multiple;
    }

    public void setMultiple(int multiple) {
        this.multiple = multiple;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }
}
