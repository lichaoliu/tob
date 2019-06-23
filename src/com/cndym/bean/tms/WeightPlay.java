package com.cndym.bean.tms;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author 朱林虎
 *
 */
public class WeightPlay implements Serializable {
	
	private static final long serialVersionUID = 4081793933512338107L;
	
	private Integer id;
	private Integer weightId;
    private String lotteryCode;
    private String playCodes;
    private String pollCodes;//出票口
    private String userCode;
    private Date createTime;
    private Date updateTime;
    private String backup1;
    private String backup2;
    private String backup3;
    
    private List<WeightSid> weightSidList;
    private List<WeightTime> weightTimeList;

    public WeightPlay() {
    }

    public WeightPlay(Integer weightId,String lotteryCode, String playCodes, String pollCodes) {
        this.weightId = weightId;
    	this.lotteryCode = lotteryCode;
    	this.playCodes = playCodes;
    	this.pollCodes = pollCodes;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLotteryCode() {
		return lotteryCode;
	}

	public void setLotteryCode(String lotteryCode) {
		this.lotteryCode = lotteryCode;
	}
	
	public Integer getWeightId() {
		return weightId;
	}

	public void setWeightId(Integer weightId) {
		this.weightId = weightId;
	}

	public String getPlayCodes() {
		return playCodes;
	}

	public void setPlayCodes(String playCodes) {
		this.playCodes = playCodes;
	}

	public String getPollCodes() {
		return pollCodes;
	}

	public void setPollCodes(String pollCodes) {
		this.pollCodes = pollCodes;
	}
	
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
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

	public List<WeightSid> getWeightSidList() {
		return weightSidList;
	}

	public void setWeightSidList(List<WeightSid> weightSidList) {
		this.weightSidList = weightSidList;
	}

	public List<WeightTime> getWeightTimeList() {
		return weightTimeList;
	}

	public void setWeightTimeList(List<WeightTime> weightTimeList) {
		this.weightTimeList = weightTimeList;
	}
    
	
}
