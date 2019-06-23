package com.cndym.service.impl;

import com.cndym.bean.tms.SubIssueForGjb;
import com.cndym.dao.ISubIssueForGjbDao;
import com.cndym.service.ISubIssueForGjbService;
import com.cndym.utils.hibernate.PageBean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: luna
 * QQ: 116741034
 * Date: 14-5-20
 * Time: 下午1:42
 * To change this template use File | Settings | File Templates.
 */
@Service
public class SubIssueForGjbServiceImpl extends GenericServiceImpl<SubIssueForGjb, ISubIssueForGjbDao> implements ISubIssueForGjbService {
    @Resource
    private ISubIssueForGjbDao subIssueForGjbDao;
    
    @PostConstruct
    private void daoImplInit() {
        super.setDaoImpl(subIssueForGjbDao);
    }
    @Override
    public SubIssueForGjb getSubIssueForGjbByIssue(String issue) {
        return subIssueForGjbDao.getSubIssueForGjbByIssue(issue);
    }

    @Override
    public PageBean getSubIssueForGjbList(String issue, Integer page, Integer pageSize) {
        return subIssueForGjbDao.getSubIssueForGjbList(issue, page, pageSize);
    }

    @Override
    public int updateSubIssueForGjbList(SubIssueForGjb subIssueForGjb) {
        return subIssueForGjbDao.updateSubIssueForGjbList(subIssueForGjb);
    }

    @Override
    public boolean saveAllSubGame(List<SubIssueForGjb> subIssueForGjbList) {
        return subIssueForGjbDao.saveAllSubGame(subIssueForGjbList);
    }

	@Override
	public List<SubIssueForGjb> getListByIssue(String issue) {
		return subIssueForGjbDao.getListByIssue(issue);
	}
}
