package com.cndym.dao;

import com.cndym.bean.tms.SubIssueForGjb;
import com.cndym.utils.hibernate.PageBean;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: luna
 * QQ: 116741034
 * Date: 14-5-20
 * Time: 下午1:33
 * To change this template use File | Settings | File Templates.
 */
public interface ISubIssueForGjbDao extends IGenericDao<SubIssueForGjb> {
    public List<SubIssueForGjb> getListByIssue(String issue);

    public SubIssueForGjb getSubIssueForGjbByIssue(String issue);

    public SubIssueForGjb getSubIssueForGjbByIssue(String issue, String sn);
    
    public PageBean getSubIssueForGjbList(String issue, Integer page, Integer pageSize);

    public int updateSubIssueForGjbList(SubIssueForGjb subIssueForGjb);

    public boolean saveAllSubGame(List<SubIssueForGjb> subIssueForGjbList);
}
