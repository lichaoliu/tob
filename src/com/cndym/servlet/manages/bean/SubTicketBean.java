package com.cndym.servlet.manages.bean;

import java.util.List;

/**
 * User: mcs
 * Date: 12-11-14
 * Time: 下午4:21
 */
public class SubTicketBean {

    private String guoGuan;

    private Integer multiple;

    private Integer item;

    private boolean single;

    private boolean singleFloat;

    private List<MatchBean> matchBeanList;

    public SubTicketBean() {
    }

    public String getGuoGuan() {
        return guoGuan;
    }

    public void setGuoGuan(String guoGuan) {
        this.guoGuan = guoGuan;
    }

    public Integer getMultiple() {
        return multiple;
    }

    public void setMultiple(Integer multiple) {
        this.multiple = multiple;
    }

    public Integer getItem() {
        return item;
    }

    public void setItem(Integer item) {
        this.item = item;
    }

    public boolean isSingle() {
        return single;
    }

    public void setSingle(boolean single) {
        this.single = single;
    }

    public boolean isSingleFloat() {
        return singleFloat;
    }

    public void setSingleFloat(boolean singleFloat) {
        this.singleFloat = singleFloat;
    }

    public List<MatchBean> getMatchBeanList() {
        return matchBeanList;
    }

    public void setMatchBeanList(List<MatchBean> matchBeanList) {
        this.matchBeanList = matchBeanList;
    }
}
