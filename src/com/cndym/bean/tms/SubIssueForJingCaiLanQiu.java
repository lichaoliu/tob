package com.cndym.bean.tms;

import java.io.Serializable;
import java.util.Date;

/**
 * 作者：邓玉明
 * email：cndym@163.com
 * QQ：757579248
 * 时间: 12-1-31 上午10:24
 */
public class SubIssueForJingCaiLanQiu implements Serializable {
    private static final long serialVersionUID = 1067014391997277511L;
    private Long id;
    private String lotteryCode;
    private String playCode;
    private String pollCode;
    private String issue;//当天时间
    private String sn;//当天场次序号
    private String week;//星期
    private String matchId;//场次号
    private String matchColor;//背景色
    private Date endTime;//收单结束时间，开赛时间
    private String matchName;//联赛名
    private String mainTeam;//主队
    private String guestTeam;//客队
    private Integer endOperator;//是否结期处理;0:未处理，1:处理，2:取消
    private Integer bonusOperator;//是否返奖处理;0:未处理，1:处理
    private Date createTime;//创建时间
    private Date updateTime;//更新时间

    private Integer mainTeamHalfScore;//主队半场比分
    private Integer guestTeamHalfScore;//客队半场比分
    private Integer mainTeamScore;//主队比分
    private Integer guestTeamScore;//客队比分
    private String letBall;//让球/让分

    private Double negaSp;//胜负-负
    private Double winSp;//胜负-胜
    private Double winOrNegaSp;

    private Double letWinOrNegaSp;
    private Double letNegaSp;//让负
    private Double letWinSp;//让胜

    private Double winNegaDiffSp;
    private Double winNegaDiffM1T5Sp;//胜差分-主胜
    private Double winNegaDiffM6T10Sp;
    private Double winNegaDiffM11T15Sp;
    private Double winNegaDiffM16T20Sp;
    private Double winNegaDiffM21T25Sp;
    private Double winNegaDiffM26Sp;
    private Double winNegaDiffG1T5Sp;//胜差分-客胜
    private Double winNegaDiffG6T10Sp;
    private Double winNegaDiffG11T15Sp;
    private Double winNegaDiffG16T20Sp;
    private Double winNegaDiffG21T25Sp;
    private Double winNegaDiffG26Sp;


    private Double bigOrLittleSp;
    private Double bigSp;
    private Double littleSp;

    private String preCast;//预设总分

    private Integer operatorsAward;//是否算奖（0等待算奖，1算奖中，2完成算奖）

    private Date endDanShiTime;
    private Date endFuShiTime;

    private Integer endStatus;//1

    private String backup1;//是否自动更新，0自动更新，1不自动更新
    private String backup2;//是否隐藏对阵，0不隐藏，1隐藏对阵
    private String backup3;

    /**
     * 是否已录入赛果和开奖号码，后台管理录入或修改赛果信息后将置为1，赛果抓取程序将不考虑为1的场次（该字段只是赛果录入程序使用）
     * 0未录入赛果，1已录入赛果，默认为0
     */
    private Integer inputAwardStatus;

    
    public SubIssueForJingCaiLanQiu() {
    }

    public SubIssueForJingCaiLanQiu(String issue, String sn, String matchId) {
        this.issue = issue;
        this.sn = sn;
        this.matchId = matchId;
    }

    public SubIssueForJingCaiLanQiu(String issue, String sn, Date endDanShiTime, Date endFuShiTime) {
        this.issue = issue;
        this.sn = sn;
        this.endDanShiTime = endDanShiTime;
        this.endFuShiTime = endFuShiTime;
    }

    public SubIssueForJingCaiLanQiu(Long id, String lotteryCode, String playCode, String pollCode, String issue, String sn, String week, String matchId, String matchColor, Date endTime, String matchName, String mainTeam, String guestTeam, Integer endOperator, Integer bonusOperator, Date createTime, Date updateTime, Integer mainTeamHalfScore, Integer guestTeamHalfScore, Integer mainTeamScore, Integer guestTeamScore, String letBall, Double negaSp, Double winSp, Double winOrNegaSp, Double letWinOrNegaSp, Double letNegaSp, Double letWinSp, Double winNegaDiffSp, Double winNegaDiffM1T5Sp, Double winNegaDiffM6T10Sp, Double winNegaDiffM11T15Sp, Double winNegaDiffM16T20Sp, Double winNegaDiffM21T25Sp, Double winNegaDiffM26Sp, Double winNegaDiffG1T5Sp, Double winNegaDiffG6T10Sp, Double winNegaDiffG11T15Sp, Double winNegaDiffG16T20Sp, Double winNegaDiffG21T25Sp, Double winNegaDiffG26Sp, Double bigOrLittleSp, Double bigSp, Double littleSp, String preCast, Integer operatorsAward, Date endDanShiTime, Date endFuShiTime) {
        this.id = id;
        this.lotteryCode = lotteryCode;
        this.playCode = playCode;
        this.pollCode = pollCode;
        this.issue = issue;
        this.sn = sn;
        this.week = week;
        this.matchId = matchId;
        this.matchColor = matchColor;
        this.endTime = endTime;
        this.matchName = matchName;
        this.mainTeam = mainTeam;
        this.guestTeam = guestTeam;
        this.endOperator = endOperator;
        this.bonusOperator = bonusOperator;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.mainTeamHalfScore = mainTeamHalfScore;
        this.guestTeamHalfScore = guestTeamHalfScore;
        this.mainTeamScore = mainTeamScore;
        this.guestTeamScore = guestTeamScore;
        this.letBall = letBall;
        this.negaSp = negaSp;
        this.winSp = winSp;
        this.winOrNegaSp = winOrNegaSp;
        this.letWinOrNegaSp = letWinOrNegaSp;
        this.letNegaSp = letNegaSp;
        this.letWinSp = letWinSp;
        this.winNegaDiffSp = winNegaDiffSp;
        this.winNegaDiffM1T5Sp = winNegaDiffM1T5Sp;
        this.winNegaDiffM6T10Sp = winNegaDiffM6T10Sp;
        this.winNegaDiffM11T15Sp = winNegaDiffM11T15Sp;
        this.winNegaDiffM16T20Sp = winNegaDiffM16T20Sp;
        this.winNegaDiffM21T25Sp = winNegaDiffM21T25Sp;
        this.winNegaDiffM26Sp = winNegaDiffM26Sp;
        this.winNegaDiffG1T5Sp = winNegaDiffG1T5Sp;
        this.winNegaDiffG6T10Sp = winNegaDiffG6T10Sp;
        this.winNegaDiffG11T15Sp = winNegaDiffG11T15Sp;
        this.winNegaDiffG16T20Sp = winNegaDiffG16T20Sp;
        this.winNegaDiffG21T25Sp = winNegaDiffG21T25Sp;
        this.winNegaDiffG26Sp = winNegaDiffG26Sp;
        this.bigOrLittleSp = bigOrLittleSp;
        this.bigSp = bigSp;
        this.littleSp = littleSp;
        this.preCast = preCast;
        this.operatorsAward = operatorsAward;
        this.endDanShiTime = endDanShiTime;
        this.endFuShiTime = endFuShiTime;
    }

    public SubIssueForJingCaiLanQiu(Long id, String lotteryCode, String playCode, String pollCode, String issue, String sn, String week, String matchId, String matchColor, Date endTime, String matchName, String mainTeam, String guestTeam, Integer endOperator, Integer bonusOperator, Date createTime, Date updateTime, Integer mainTeamHalfScore, Integer guestTeamHalfScore, Integer mainTeamScore, Integer guestTeamScore, String letBall, Double negaSp, Double winSp, Double winOrNegaSp, Double letWinOrNegaSp, Double letNegaSp, Double letWinSp, Double winNegaDiffSp, Double winNegaDiffM1T5Sp, Double winNegaDiffM6T10Sp, Double winNegaDiffM11T15Sp, Double winNegaDiffM16T20Sp, Double winNegaDiffM21T25Sp, Double winNegaDiffM26Sp, Double winNegaDiffG1T5Sp, Double winNegaDiffG6T10Sp, Double winNegaDiffG11T15Sp, Double winNegaDiffG16T20Sp, Double winNegaDiffG21T25Sp, Double winNegaDiffG26Sp, Double bigOrLittleSp, Double bigSp, Double littleSp, String preCast, Integer operatorsAward, Date endDanShiTime, Date endFuShiTime, Integer endStatus, Integer inputAwardStatus) {
        this.id = id;
        this.lotteryCode = lotteryCode;
        this.playCode = playCode;
        this.pollCode = pollCode;
        this.issue = issue;
        this.sn = sn;
        this.week = week;
        this.matchId = matchId;
        this.matchColor = matchColor;
        this.endTime = endTime;
        this.matchName = matchName;
        this.mainTeam = mainTeam;
        this.guestTeam = guestTeam;
        this.endOperator = endOperator;
        this.bonusOperator = bonusOperator;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.mainTeamHalfScore = mainTeamHalfScore;
        this.guestTeamHalfScore = guestTeamHalfScore;
        this.mainTeamScore = mainTeamScore;
        this.guestTeamScore = guestTeamScore;
        this.letBall = letBall;
        this.negaSp = negaSp;
        this.winSp = winSp;
        this.winOrNegaSp = winOrNegaSp;
        this.letWinOrNegaSp = letWinOrNegaSp;
        this.letNegaSp = letNegaSp;
        this.letWinSp = letWinSp;
        this.winNegaDiffSp = winNegaDiffSp;
        this.winNegaDiffM1T5Sp = winNegaDiffM1T5Sp;
        this.winNegaDiffM6T10Sp = winNegaDiffM6T10Sp;
        this.winNegaDiffM11T15Sp = winNegaDiffM11T15Sp;
        this.winNegaDiffM16T20Sp = winNegaDiffM16T20Sp;
        this.winNegaDiffM21T25Sp = winNegaDiffM21T25Sp;
        this.winNegaDiffM26Sp = winNegaDiffM26Sp;
        this.winNegaDiffG1T5Sp = winNegaDiffG1T5Sp;
        this.winNegaDiffG6T10Sp = winNegaDiffG6T10Sp;
        this.winNegaDiffG11T15Sp = winNegaDiffG11T15Sp;
        this.winNegaDiffG16T20Sp = winNegaDiffG16T20Sp;
        this.winNegaDiffG21T25Sp = winNegaDiffG21T25Sp;
        this.winNegaDiffG26Sp = winNegaDiffG26Sp;
        this.bigOrLittleSp = bigOrLittleSp;
        this.bigSp = bigSp;
        this.littleSp = littleSp;
        this.preCast = preCast;
        this.operatorsAward = operatorsAward;
        this.endDanShiTime = endDanShiTime;
        this.endFuShiTime = endFuShiTime;
        this.endStatus = endStatus;
        this.inputAwardStatus = inputAwardStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLotteryCode() {
        return lotteryCode;
    }

    public void setLotteryCode(String lotteryCode) {
        this.lotteryCode = lotteryCode;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getMatchColor() {
        return matchColor;
    }

    public void setMatchColor(String matchColor) {
        this.matchColor = matchColor;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public String getMainTeam() {
        return mainTeam;
    }

    public void setMainTeam(String mainTeam) {
        this.mainTeam = mainTeam;
    }

    public String getGuestTeam() {
        return guestTeam;
    }

    public void setGuestTeam(String guestTeam) {
        this.guestTeam = guestTeam;
    }

    public Integer getEndOperator() {
        return endOperator;
    }

    public void setEndOperator(Integer endOperator) {
        this.endOperator = endOperator;
    }

    public Integer getBonusOperator() {
        return bonusOperator;
    }

    public void setBonusOperator(Integer bonusOperator) {
        this.bonusOperator = bonusOperator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getMainTeamHalfScore() {
        return mainTeamHalfScore;
    }

    public void setMainTeamHalfScore(Integer mainTeamHalfScore) {
        this.mainTeamHalfScore = mainTeamHalfScore;
    }

    public Integer getGuestTeamHalfScore() {
        return guestTeamHalfScore;
    }

    public void setGuestTeamHalfScore(Integer guestTeamHalfScore) {
        this.guestTeamHalfScore = guestTeamHalfScore;
    }

    public Integer getMainTeamScore() {
        return mainTeamScore;
    }

    public void setMainTeamScore(Integer mainTeamScore) {
        this.mainTeamScore = mainTeamScore;
    }

    public Integer getGuestTeamScore() {
        return guestTeamScore;
    }

    public void setGuestTeamScore(Integer guestTeamScore) {
        this.guestTeamScore = guestTeamScore;
    }

    public String getLetBall() {
        return letBall;
    }

    public void setLetBall(String letBall) {
        this.letBall = letBall;
    }

    public Double getNegaSp() {
        return negaSp;
    }

    public void setNegaSp(Double negaSp) {
        this.negaSp = negaSp;
    }

    public Double getWinSp() {
        return winSp;
    }

    public void setWinSp(Double winSp) {
        this.winSp = winSp;
    }

    public Double getLetNegaSp() {
        return letNegaSp;
    }

    public void setLetNegaSp(Double letNegaSp) {
        this.letNegaSp = letNegaSp;
    }

    public Double getLetWinSp() {
        return letWinSp;
    }

    public void setLetWinSp(Double letWinSp) {
        this.letWinSp = letWinSp;
    }

    public Double getWinNegaDiffM1T5Sp() {
        return winNegaDiffM1T5Sp;
    }

    public void setWinNegaDiffM1T5Sp(Double winNegaDiffM1T5Sp) {
        this.winNegaDiffM1T5Sp = winNegaDiffM1T5Sp;
    }

    public Double getWinNegaDiffM6T10Sp() {
        return winNegaDiffM6T10Sp;
    }

    public void setWinNegaDiffM6T10Sp(Double winNegaDiffM6T10Sp) {
        this.winNegaDiffM6T10Sp = winNegaDiffM6T10Sp;
    }

    public Double getWinNegaDiffM11T15Sp() {
        return winNegaDiffM11T15Sp;
    }

    public void setWinNegaDiffM11T15Sp(Double winNegaDiffM11T15Sp) {
        this.winNegaDiffM11T15Sp = winNegaDiffM11T15Sp;
    }

    public Double getWinNegaDiffM16T20Sp() {
        return winNegaDiffM16T20Sp;
    }

    public void setWinNegaDiffM16T20Sp(Double winNegaDiffM16T20Sp) {
        this.winNegaDiffM16T20Sp = winNegaDiffM16T20Sp;
    }

    public Double getWinNegaDiffM21T25Sp() {
        return winNegaDiffM21T25Sp;
    }

    public void setWinNegaDiffM21T25Sp(Double winNegaDiffM21T25Sp) {
        this.winNegaDiffM21T25Sp = winNegaDiffM21T25Sp;
    }

    public Double getWinNegaDiffM26Sp() {
        return winNegaDiffM26Sp;
    }

    public void setWinNegaDiffM26Sp(Double winNegaDiffM26Sp) {
        this.winNegaDiffM26Sp = winNegaDiffM26Sp;
    }

    public Double getWinNegaDiffG1T5Sp() {
        return winNegaDiffG1T5Sp;
    }

    public void setWinNegaDiffG1T5Sp(Double winNegaDiffG1T5Sp) {
        this.winNegaDiffG1T5Sp = winNegaDiffG1T5Sp;
    }

    public Double getWinNegaDiffG6T10Sp() {
        return winNegaDiffG6T10Sp;
    }

    public void setWinNegaDiffG6T10Sp(Double winNegaDiffG6T10Sp) {
        this.winNegaDiffG6T10Sp = winNegaDiffG6T10Sp;
    }

    public Double getWinNegaDiffG11T15Sp() {
        return winNegaDiffG11T15Sp;
    }

    public void setWinNegaDiffG11T15Sp(Double winNegaDiffG11T15Sp) {
        this.winNegaDiffG11T15Sp = winNegaDiffG11T15Sp;
    }

    public Double getWinNegaDiffG16T20Sp() {
        return winNegaDiffG16T20Sp;
    }

    public void setWinNegaDiffG16T20Sp(Double winNegaDiffG16T20Sp) {
        this.winNegaDiffG16T20Sp = winNegaDiffG16T20Sp;
    }

    public Double getWinNegaDiffG21T25Sp() {
        return winNegaDiffG21T25Sp;
    }

    public void setWinNegaDiffG21T25Sp(Double winNegaDiffG21T25Sp) {
        this.winNegaDiffG21T25Sp = winNegaDiffG21T25Sp;
    }

    public Double getWinNegaDiffG26Sp() {
        return winNegaDiffG26Sp;
    }

    public void setWinNegaDiffG26Sp(Double winNegaDiffG26Sp) {
        this.winNegaDiffG26Sp = winNegaDiffG26Sp;
    }

    public Double getBigSp() {
        return bigSp;
    }

    public void setBigSp(Double bigSp) {
        this.bigSp = bigSp;
    }

    public Double getLittleSp() {
        return littleSp;
    }

    public void setLittleSp(Double littleSp) {
        this.littleSp = littleSp;
    }

    public String getPreCast() {
        return preCast;
    }

    public void setPreCast(String preCast) {
        this.preCast = preCast;
    }


    public Integer getOperatorsAward() {
        return operatorsAward;
    }

    public void setOperatorsAward(Integer operatorsAward) {
        this.operatorsAward = operatorsAward;
    }

    public Double getLetWinOrNegaSp() {
        return letWinOrNegaSp;
    }

    public void setLetWinOrNegaSp(Double letWinOrNegaSp) {
        this.letWinOrNegaSp = letWinOrNegaSp;
    }

    public Double getBigOrLittleSp() {
        return bigOrLittleSp;
    }

    public void setBigOrLittleSp(Double bigOrLittleSp) {
        this.bigOrLittleSp = bigOrLittleSp;
    }

    public Double getWinNegaDiffSp() {
        return winNegaDiffSp;
    }

    public void setWinNegaDiffSp(Double winNegaDiffSp) {
        this.winNegaDiffSp = winNegaDiffSp;
    }

    public Double getWinOrNegaSp() {
        return winOrNegaSp;
    }

    public void setWinOrNegaSp(Double winOrNegaSp) {
        this.winOrNegaSp = winOrNegaSp;
    }

    public Date getEndDanShiTime() {
        return endDanShiTime;
    }

    public void setEndDanShiTime(Date endDanShiTime) {
        this.endDanShiTime = endDanShiTime;
    }

    public Date getEndFuShiTime() {
        return endFuShiTime;
    }

    public void setEndFuShiTime(Date endFuShiTime) {
        this.endFuShiTime = endFuShiTime;
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

    public Integer getEndStatus() {
        return endStatus;
    }

    public void setEndStatus(Integer endStatus) {
        this.endStatus = endStatus;
    }

    public Integer getInputAwardStatus() {
        return inputAwardStatus;
    }

    public void setInputAwardStatus(Integer inputAwardStatus) {
        this.inputAwardStatus = inputAwardStatus;
    }

    public String getBackup1() {
        return backup1;
    }

    public void setBackup1(String backup1) {
        this.backup1 = backup1;
    }

    public String getBackup2() {
        return backup2;
    }

    public void setBackup2(String backup2) {
        this.backup2 = backup2;
    }

    public String getBackup3() {
        return backup3;
    }

    public void setBackup3(String backup3) {
        this.backup3 = backup3;
    }
}
