package com.cndym.adapter.tms.bean;

/**
 * 作者：邓玉明
 * email：cndym@163.com
 * QQ：757579248
 * 时间: 12-3-5 下午3:39
 */
public class SubProgramsForTicket {
    private String subProgramsOrderId;
    private int ticketCount;
    private int ticketItem;
    private double  amount;

    public SubProgramsForTicket() {
    }

    public SubProgramsForTicket(String subProgramsOrderId) {
        this.subProgramsOrderId = subProgramsOrderId;
    }

    public SubProgramsForTicket(String subProgramsOrderId, int ticketCount, int ticketItem, double amount) {
        this.subProgramsOrderId = subProgramsOrderId;
        this.ticketCount = ticketCount;
        this.ticketItem = ticketItem;
        this.amount = amount;
    }

    public String getSubProgramsOrderId() {
        return subProgramsOrderId;
    }

    public void setSubProgramsOrderId(String subProgramsOrderId) {
        this.subProgramsOrderId = subProgramsOrderId;
    }

    public int getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }

    public int getTicketItem() {
        return ticketItem;
    }

    public void setTicketItem(int ticketItem) {
        this.ticketItem = ticketItem;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}
