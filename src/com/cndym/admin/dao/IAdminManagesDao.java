package com.cndym.admin.dao;

import com.cndym.bean.admin.AlertAmountTable;
import com.cndym.bean.user.Member;
import com.cndym.dao.IGenericDao;
import com.cndym.utils.hibernate.PageBean;

import java.util.List;
import java.util.Map;

/**
 * User: 梁桂立 Date: 14-04-14 下午10:52
 */
public interface IAdminManagesDao extends IGenericDao<AlertAmountTable> {

	public List<AlertAmountTable> getAlertAmountList();
	
	public PageBean getPageBeanToMember(Member member, Integer page, Integer pageSize, Map<String,Object> map);
	
	public boolean updateAlertAmount(AlertAmountTable alertAmountTable);
	
	public void updateAlertAmountStatus(String[] postCode, String status);
}
