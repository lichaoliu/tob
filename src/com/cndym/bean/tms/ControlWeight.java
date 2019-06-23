package com.cndym.bean.tms;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author 朱林虎
 *
 */
public class ControlWeight implements Serializable {
	
	private static final long serialVersionUID = 4081793933512338107L;
	
	private Integer id;
    private String lotteryCode;
    private String lotteryName;
    private String postCode;//出票口
    private String lastPostCode;
    private Integer active;//是否可用 0 不可用 1 可用
    private String userCode;
    private Date createTime;
    private Date updateTime;
    private String backup1;
    private String backup2;
    private String backup3;
    
    private List<PostLottery> postLotteryList;
    
    private List<WeightPlay> weightPlayList;

    public ControlWeight() {
    }

    public ControlWeight(String lotteryCode, String lotteryName, String postCode, String lastPostCode,Integer active,String userCode) {
        this.lotteryCode = lotteryCode;
        this.lotteryName = lotteryName;
        this.postCode = postCode;
        this.lastPostCode = lastPostCode;
        this.active = active;
        this.userCode = userCode;
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

	public String getLotteryName() {
		return lotteryName;
	}

	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getLastPostCode() {
		return lastPostCode;
	}

	public void setLastPostCode(String lastPostCode) {
		this.lastPostCode = lastPostCode;
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
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

	public List<PostLottery> getPostLotteryList() {
		return postLotteryList;
	}

	public void setPostLotteryList(List<PostLottery> postLotteryList) {
		this.postLotteryList = postLotteryList;
	}

	public List<WeightPlay> getWeightPlayList() {
		return weightPlayList;
	}

	public void setWeightPlayList(List<WeightPlay> weightPlayList) {
		this.weightPlayList = weightPlayList;
	}
	
}
