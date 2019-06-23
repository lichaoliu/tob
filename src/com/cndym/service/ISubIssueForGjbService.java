package com.cndym.service;

import com.cndym.bean.tms.SubIssueForGjb;
import com.cndym.utils.hibernate.PageBean;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: luna
 * QQ: 116741034
 * Date: 14-5-20
 * Time: 下午1:41
 * To change this template use File | Settings | File Templates.
 */
public interface ISubIssueForGjbService extends IGenericeService<SubIssueForGjb> {
    public SubIssueForGjb getSubIssueForGjbByIssue(String issue);

    public PageBean getSubIssueForGjbList(String issue, Integer page, Integer pageSize);

    public int updateSubIssueForGjbList(SubIssueForGjb subIssueForGjb);

    public boolean saveAllSubGame(List<SubIssueForGjb> subIssueForGjbList);

	public List<SubIssueForGjb> getListByIssue(String issue);
}
