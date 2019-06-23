package com.cndym.admin.service;

import com.cndym.bean.admin.AlertAmountTable;
import com.cndym.bean.user.Member;
import com.cndym.service.IGenericeService;
import com.cndym.utils.hibernate.PageBean;

import java.util.List;
import java.util.Map;

/**
 * User: 梁桂立 Date: 14-4-14   下午18:00
 */
public interface IAdminManagesService extends IGenericeService<AlertAmountTable> {
	
    public List<AlertAmountTable> getAlertAmountList();
    
    public PageBean getPageBeanToMember(Member member, Integer page, Integer pageSize,Map<String,Object> map);
    
    public boolean updateAlertAmount(AlertAmountTable alertAmountTable);
    
    public void updateAlertAmountStatus(String[] postCode, String status);

}