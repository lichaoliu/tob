package com.cndym.adapter.tms.bean;

import com.cndym.utils.CombinationUtils;
import com.cndym.utils.Utils;

import java.util.List;

/**
 * 作者：邓玉明
 * email：cndym@163.com
 * QQ：757579248
 * 时间: 12-2-8 下午11:28
 */
public class SubNumberInfo {
    private List<MatchItemInfo> matchItemInfos;
    private int matchA;
    private int matchB;
    private int item;
    private String pollCode;


    public SubNumberInfo() {
    }

    public SubNumberInfo(List<MatchItemInfo> matchItemInfos, int matchA, int matchB) {
        this.matchItemInfos = matchItemInfos;
        this.matchA = matchA;
        this.matchB = matchB;
        comItem();
    }

    public List<MatchItemInfo> getMatchItemInfos() {
        return matchItemInfos;
    }

    public void setMatchItemInfos(List<MatchItemInfo> matchItemInfos) {
        this.matchItemInfos = matchItemInfos;
    }

    public String getMode() {
        return matchA + "*" + matchB;
    }

    public int getMatchA() {
        return matchA;
    }

    public void setMatchA(int matchA) {
        this.matchA = matchA;
    }

    public int getMatchB() {
        return matchB;
    }

    public void setMatchB(int matchB) {
        this.matchB = matchB;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public String getPollCode() {
        return pollCode;
    }

    public void setPollCode(String pollCode) {
        this.pollCode = pollCode;
    }

    public void comItem() {
        CombinationUtils combinationUtils = new CombinationUtils();
        int i = 0;
        int subItem = 0;
        int j = getMatchA();
        Integer[] arr = JingJiChuanGuan.getIntegers(new JingJiChuanGuanKey(matchA, matchB));
        while (i < this.getMatchB()) {
            if (arr[j] > 0) {
                List<int[]> list = combinationUtils.combination(Utils.forInstance(getMatchItemInfos().size()), j);
                for (int[] groups : list) {
                    int temp = 1;
                    for (int subInt : groups) {
                        //得到具体场次
                        temp *= getMatchItemInfos().get(subInt).getItem();
                    }
                    subItem += temp;
                    i++;
                }
            }
            j--;
        }
        setItem(subItem);
    }

    @Override
    public String toString() {
        StringBuilder temp = new StringBuilder();
        java.util.Collections.sort(matchItemInfos);
        for (MatchItemInfo matchItemInfo : matchItemInfos) {
            temp.append(";");
            temp.append(matchItemInfo);
        }
        temp.append("|");
        temp.append(getMode());
        return temp.substring(1);
    }
}
