package com.cndym.adapter.tms.bean;

import com.cndym.utils.Utils;

import java.io.Serializable;

/**
 * 作者：邓玉明
 * email：cndym@163.com
 * QQ：757579248
 * 时间: 12-2-8 下午11:48
 */
public class MatchItemInfo implements Serializable, Comparable<MatchItemInfo> {
    public static final int DAN = 1;
    private String matchId;
    private String value;
    private String playCode;
    private int dan;
    private int item;

    public static MatchItemInfo str10Object(String number) {
        //场次1:选项1,选项2,选项3:设胆;场次2:选项1,选项2,选项3:设胆|过关方式（1*1,n*1,n*m)
        String[] arr = number.split(":");
        if (arr.length != 3) {
            throw new IllegalArgumentException("号码(" + number + ")格式有误(2)");
        }
        MatchItemInfo matchItemInfo = new MatchItemInfo();
        matchItemInfo.setMatchId(arr[0]);
        matchItemInfo.setPlayCode(arr[1]);
        matchItemInfo.setValue(arr[2]);
        matchItemInfo.setDan(0);
        matchItemInfo.setItem(arr[2].split(",").length);
        return matchItemInfo;
    }

     public static MatchItemInfo str2Object(String number) {
        //场次1:选项1,选项2,选项3:设胆;场次2:选项1,选项2,选项3:设胆|过关方式（1*1,n*1,n*m)
        String[] arr = number.split(":");
        if (arr.length != 2) {
            throw new IllegalArgumentException("号码(" + number + ")格式有误(2)");
        }
        MatchItemInfo matchItemInfo = new MatchItemInfo();
        matchItemInfo.setMatchId(arr[0]);
        matchItemInfo.setValue(arr[1]);
        matchItemInfo.setDan(0);
        matchItemInfo.setItem(arr[1].split(",").length);
        return matchItemInfo;
    }

    public static MatchItemInfo str2ObjectNew(String number) {
        //场次1:选项1,选项2,选项3:设胆;场次2:选项1,选项2,选项3:设胆|过关方式（1*1,n*1,n*m)
        String[] arr = number.split(":");
        int arrLen = arr.length;
        if (arrLen != 2 && arrLen != 3) {
            throw new IllegalArgumentException(arrLen + "号码(" + number + ")格式有误(2)");
        }
        MatchItemInfo matchItemInfo = new MatchItemInfo();
        matchItemInfo.setMatchId(arr[0]);
        String num = "";
        if (arrLen == 3) {
            num = arr[1] + ":" + arr[2];
        } else {
            num = arr[1];
        }
        matchItemInfo.setValue(num);
        matchItemInfo.setItem(num.split(",").length);
        matchItemInfo.setDan(0);
        return matchItemInfo;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getDan() {
        return dan;
    }

    public void setDan(int dan) {
        this.dan = dan;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public String getPlayCode() {
        return playCode;
    }

    public void setPlayCode(String playCode) {
        this.playCode = playCode;
    }

    @Override
    public String toString() {
        return getMatchId() + ":" + getValue();
    }

    @Override
    public int compareTo(MatchItemInfo matchItemInfo) {
        if (Utils.formatLong(matchId, 0) > Utils.formatLong(matchItemInfo.getMatchId(), 0)) {
            return 1;
        } else if (Utils.formatLong(matchId, 0) < Utils.formatLong(matchItemInfo.getMatchId(), 0)) {
            return -1;
        } else {
            return 0;
        }
    }
}
