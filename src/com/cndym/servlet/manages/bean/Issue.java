package com.cndym.servlet.manages.bean;

import com.cndym.utils.Utils;
import com.cndym.utils.xml.parse.XmlBean;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Issue implements Serializable, Comparable<Issue> {
    /**
     *
     */
    private static final long serialVersionUID = 5630127184024509094L;
    private String issue;// 期号
    private String leftTime;// 投注剩余时间，单位为秒
    private String startTime;// 当期开始时间
    private String endTime;// 当期截至时间
    private String simplexTime;//单式截止时间
    private String simplexLeftTime;//单式投注剩余时间
    private String openAwardTime;//
    private String nowTime;// 当前系统时间
    private String status;// 状态，1当前期，0，预售期，2已停售
    private String bonusNumber;//开奖号码
    private String prizePool;//滚入下期奖池金额
    private String bonusTime;//
    private String lotteryCode;
    private String bonusClass;

    public String getBonusClass() {
        return bonusClass;
    }

    public void setBonusClass(String bonusClass) {
        this.bonusClass = bonusClass;
    }

    public String getprizePool() {
        return prizePool;
    }

    public void setLotteryCode(String lotteryCode) {
        this.lotteryCode = lotteryCode;
    }

    public String getLotteryCode() {
        return lotteryCode;
    }

    public void setprizePool(String prizePool) {
        this.prizePool = prizePool;
    }

    public Issue() {

    }

    public Issue(XmlBean bean) {
        Date nowDate = new Date();
        Date duplexTime = Utils.formatDate(bean.attribute("duplexTime"), "yyyy-MM-dd HH:mm:ss");
        long leftDuplex = (duplexTime.getTime() - nowDate.getTime()) / 1000;
        Date simplexTime = Utils.formatDate(bean.attribute("simplexTime"), "yyyy-MM-dd HH:mm:ss");
        long leftSimplex = (simplexTime.getTime() - nowDate.getTime()) / 1000;
        this.issue = bean.attribute("name");
        this.leftTime = String.valueOf(leftDuplex);
        this.startTime = bean.attribute("startTime");
        this.endTime = bean.attribute("duplexTime");
        this.simplexTime = bean.attribute("simplexTime");
        this.simplexLeftTime = String.valueOf(leftSimplex);;
        this.nowTime = Utils.today("yyyy-MM-dd");
        this.openAwardTime = "";
        this.status = bean.attribute("status");
        this.prizePool = bean.attribute("prizePool");
        this.bonusTime = bean.attribute("bonusTime");
    }

    public Issue(String issue, String bonusNumber, String startTime, String prizePool, String temp) {
        this.issue = issue;
        this.bonusNumber = bonusNumber;
        this.startTime = startTime;
        this.prizePool = prizePool;
    }

    public Issue(String issue, String lotteryCode) {
        this.issue = issue;
        this.lotteryCode = lotteryCode;
    }

    public Issue(String issue, String bonusNumber, String startTime, String prizePool, String temp, String lotteryCode, String bonusClass) {
        this.issue = issue;
        this.bonusNumber = bonusNumber;
        this.startTime = startTime;
        this.prizePool = prizePool;
        this.lotteryCode = lotteryCode;
        this.bonusClass = bonusClass;
    }

    public Issue(String issue, String bonusNumber, String startTime, String prizePool, String temp, String lotteryCode, String bonusClass, String bonusTime) {
        this.issue = issue;
        this.bonusNumber = bonusNumber;
        this.startTime = startTime;
        this.prizePool = prizePool;
        this.lotteryCode = lotteryCode;
        this.bonusClass = bonusClass;
        this.bonusTime = bonusTime;
    }

    public Issue(String issue, String bonusNumber, String startTime, String bonusTime) {
        this.issue = issue;
        this.bonusNumber = bonusNumber;
        this.startTime = startTime;
        this.bonusTime = bonusTime;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getLeftTime() {
        return leftTime;
    }

    public void setLeftTime(String leftTime) {
        this.leftTime = leftTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getOpenAwardTime() {
        return openAwardTime;
    }

    public void setOpenAwardTime(String openAwardTime) {
        this.openAwardTime = openAwardTime;
    }

    public String getNowTime() {
        return nowTime;
    }

    public void setNowTime(String nowTime) {
        this.nowTime = nowTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBonusNumber() {
        return bonusNumber;
    }

    public void setBonusNumber(String bonusNumber) {
        this.bonusNumber = bonusNumber;
    }

    public String getBonusTime() {
        return bonusTime;
    }

    public void setBonusTime(String bonusTime) {
        this.bonusTime = bonusTime;
    }

    public String getSimplexTime() {
        return simplexTime;
    }

    public void setSimplexTime(String simplexTime) {
        this.simplexTime = simplexTime;
    }

    public String getSimplexLeftTime() {
        return simplexLeftTime;
    }

    public void setSimplexLeftTime(String simplexLeftTime) {
        this.simplexLeftTime = simplexLeftTime;
    }

    @Override
    public int compareTo(Issue issue) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            if (simpleDateFormat.parse(this.getStartTime()).getTime() > simpleDateFormat.parse(issue.getStartTime()).getTime()) {
                return 0;
            }
        } catch (ParseException e) {
            return 0;
        }
        return 0;
    }
}
