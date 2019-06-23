package com.cndym.servlet.manages.bean;

import java.util.Date;

/**
 * User: mcs
 * Date: 12-11-14
 * Time: 下午4:16
 */
public class MatchBean {

    public String matchNo;

    private Date openTime;

    private String guestTeam;

    private String mainTeam;

    private String mainTeamHalfScore;

    private String guestTeamHalfScore;

    private String mainTeamScore;

    private String guestTeamScore;

    private String letBall;

    private String preCast;

    private String result;

    private String numberInfo;

    private String playCode;

    private int select;


    public MatchBean() {
    }

    public String getMatchNo() {
        return matchNo;
    }

    public void setMatchNo(String matchNo) {
        this.matchNo = matchNo;
    }

    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    public String getGuestTeam() {
        return guestTeam;
    }

    public void setGuestTeam(String guestTeam) {
        this.guestTeam = guestTeam;
    }

    public String getMainTeam() {
        return mainTeam;
    }

    public void setMainTeam(String mainTeam) {
        this.mainTeam = mainTeam;
    }

    public String getMainTeamHalfScore() {
        return mainTeamHalfScore;
    }

    public void setMainTeamHalfScore(String mainTeamHalfScore) {
        this.mainTeamHalfScore = mainTeamHalfScore;
    }

    public String getGuestTeamHalfScore() {
        return guestTeamHalfScore;
    }

    public void setGuestTeamHalfScore(String guestTeamHalfScore) {
        this.guestTeamHalfScore = guestTeamHalfScore;
    }

    public String getMainTeamScore() {
        return mainTeamScore;
    }

    public void setMainTeamScore(String mainTeamScore) {
        this.mainTeamScore = mainTeamScore;
    }

    public String getGuestTeamScore() {
        return guestTeamScore;
    }

    public void setGuestTeamScore(String guestTeamScore) {
        this.guestTeamScore = guestTeamScore;
    }

    public String getLetBall() {
        return letBall;
    }

    public void setLetBall(String letBall) {
        this.letBall = letBall;
    }

    public String getPreCast() {
        return preCast;
    }

    public void setPreCast(String preCast) {
        this.preCast = preCast;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getNumberInfo() {
        return numberInfo;
    }

    public void setNumberInfo(String numberInfo) {
        this.numberInfo = numberInfo;
    }

    public int getSelect() {
        return select;
    }

    public void setSelect(int select) {
        this.select = select;
    }

    public String getPlayCode() {
        return playCode;
    }

    public void setPlayCode(String playCode) {
        this.playCode = playCode;
    }
}
