package com.cndym.bean.tms;

import java.io.Serializable;
import java.util.Date;

/**
 * User: MengJingyi
 * Date: 11-5-18 下午3:42
 */
public class SubGame implements Serializable{

    private static final long serialVersionUID = -9170187568993057008L;
    private Long id;
    private String lotteryCode;
    private String issue;
    private String leageName;
    private String masterName;
    private String guestName;
    private Date startTime;
    private Date endTime;
    private Integer index;
    private String result;//赛果
    private String resultDes;//对阵双方得分
    private String finalScore;//最终比分
    private String scoreAtHalf;//上半场比分
    private String secondHalfTheScore;//下半场比分
    
    
    
    public SubGame(Long id, String lotteryCode, String issue,
			String leageName, String masterName, String guestName,
			Date startTime, Date endTime) {
		super();
		this.id = id;
		this.lotteryCode = lotteryCode;
		this.issue = issue;
		this.leageName = leageName;
		this.masterName = masterName;
		this.guestName = guestName;
		this.startTime = startTime;
		this.endTime = endTime;
	}

    public SubGame(Long id, String lotteryCode, String issue,String leageName, String masterName, String guestName, Date startTime, Date endTime,Integer index, String result, String resultDes, String finalScore, String scoreAtHalf, String secondHalfTheScore) {
        this.id = id;
        this.lotteryCode = lotteryCode;
        this.issue = issue;
        this.leageName = leageName;
        this.masterName = masterName;
        this.guestName = guestName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.index = index;
        this.result = result;
        this.resultDes = resultDes;
        this.finalScore = finalScore;
        this.scoreAtHalf = scoreAtHalf;
        this.secondHalfTheScore = secondHalfTheScore;
    }

    public SubGame() {
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

	public String getLeageName() {
		return leageName;
	}


	public void setLeageName(String leageName) {
		this.leageName = leageName;
	}


	public String getMasterName() {
		return masterName;
	}


	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}


    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getGuestName() {
		return guestName;
	}


	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}


	public Date getStartTime() {
		return startTime;
	}


	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}


	public Date getEndTime() {
		return endTime;
	}


	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}


	public String getResultDes() {
		return resultDes;
	}


	public void setResultDes(String resultDes) {
		this.resultDes = resultDes;
	}

	public String getFinalScore() {
		return finalScore;
	}


	public void setFinalScore(String finalScore) {
		this.finalScore = finalScore;
	}


	public String getScoreAtHalf() {
		return scoreAtHalf;
	}


	public void setScoreAtHalf(String scoreAtHalf) {
		this.scoreAtHalf = scoreAtHalf;
	}


	public String getSecondHalfTheScore() {
		return secondHalfTheScore;
	}


	public void setSecondHalfTheScore(String secondHalfTheScore) {
		this.secondHalfTheScore = secondHalfTheScore;
	}

}