package com.cndym.adapter.tms.bean;

/**
 * 作者：邓玉明
 * email：cndym@163.com
 * QQ：757579248
 * 时间: 12-2-19 上午11:37
 */
public class JingJiChuanGuanKey {
    private int matchA;
    private int matchB;


    public JingJiChuanGuanKey(int matchA, int matchB) {
        this.matchA = matchA;
        this.matchB = matchB;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JingJiChuanGuanKey that = (JingJiChuanGuanKey) o;

        if (matchA != that.matchA) return false;
        if (matchB != that.matchB) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = matchA;
        result = 31 * result + matchB;
        return result;
    }

    @Override
    public String toString() {
        return  matchA + ":" + matchB;
    }
}
