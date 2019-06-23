package com.cndym.bean.query;

import com.cndym.bean.tms.MainIssue;
import com.cndym.bean.tms.Ticket;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: MengJingyi
 * QQ: 116741034
 * Date: 13-6-13
 * Time: 下午12:33
 * To change this template use File | Settings | File Templates.
 */
public class TicketMainIssueBean implements Serializable {
    public Ticket ticket;
    public MainIssue mainIssue;
    public Date endTimeStart;
    public Date endTimeEnd;

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public MainIssue getMainIssue() {
        return mainIssue;
    }

    public void setMainIssue(MainIssue mainIssue) {
        this.mainIssue = mainIssue;
    }

    public Date getEndTimeStart() {
        return endTimeStart;
    }

    public void setEndTimeStart(Date endTimeStart) {
        this.endTimeStart = endTimeStart;
    }

    public Date getEndTimeEnd() {
        return endTimeEnd;
    }

    public void setEndTimeEnd(Date endTimeEnd) {
        this.endTimeEnd = endTimeEnd;
    }
}
