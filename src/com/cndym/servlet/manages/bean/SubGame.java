package com.cndym.servlet.manages.bean;

import com.cndym.utils.xml.parse.XmlBean;

import java.io.Serializable;

public class SubGame implements Serializable {
	private static final long serialVersionUID = 5671358457998121715L;

	private String leageName;
	private String gameStartDate;
	private String masterName;
	private String guestName;
	private String renqiInfo;
    private String finalScore;

	public SubGame() {

	}

	public SubGame(XmlBean bean) {
		this.leageName = bean.attribute("leageName");
		this.gameStartDate = bean.attribute("startTime");
		this.masterName = bean.attribute("masterName");
		this.guestName = bean.attribute("guestName");
        this.finalScore = bean.attribute("finalScore");
	}

	public String getLeageName() {
		return leageName;
	}

	public void setLeageName(String leageName) {
		this.leageName = leageName;
	}

	public String getGameStartDate() {
		return gameStartDate;
	}

	public void setGameStartDate(String gameStartDate) {
		this.gameStartDate = gameStartDate;
	}

	public String getMasterName() {
		return masterName;
	}

	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	public String getRenqiInfo() {
		return renqiInfo;
	}

	public void setRenqiInfo(String renqiInfo) {
		this.renqiInfo = renqiInfo;
	}

    public String getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(String finalScore) {
        this.finalScore = finalScore;
    }
}
