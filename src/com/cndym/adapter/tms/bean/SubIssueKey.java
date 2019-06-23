package com.cndym.adapter.tms.bean;

/**
 * 作者：邓玉明
 * email：cndym@163.com
 * QQ：757579248
 * 时间: 12-2-11 下午11:58
 */
public class SubIssueKey {
    private String date;
    private String week;
    private String issue;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubIssueKey that = (SubIssueKey) o;

        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (issue != null ? !issue.equals(that.issue) : that.issue != null) return false;
        if (week != null ? !week.equals(that.week) : that.week != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + (week != null ? week.hashCode() : 0);
        result = 31 * result + (issue != null ? issue.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return this.getIssue()+"_"+this.getDate()+"_"+this.getWeek();
    }
}
