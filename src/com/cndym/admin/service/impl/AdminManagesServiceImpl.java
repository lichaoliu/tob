package com.cndym.admin.service.impl;

import com.cndym.bean.admin.AlertAmountTable;
import com.cndym.bean.user.Member;
import com.cndym.admin.dao.IAdminManagesDao;
import com.cndym.admin.service.IAdminManagesService;
import com.cndym.service.impl.GenericServiceImpl;
import com.cndym.utils.hibernate.PageBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * User: 梁桂立Date: 14-4-15 下午11:02
 */
@Service
public class AdminManagesServiceImpl extends GenericServiceImpl<AlertAmountTable, IAdminManagesDao> implements IAdminManagesService {
    @Autowired
    private IAdminManagesDao adminDao;

    @PostConstruct
    public void initDao() {
        super.setDaoImpl(adminDao);
    }
    
    @Override
    public List<AlertAmountTable> getAlertAmountList(){
        return adminDao.getAlertAmountList();
    }
    
    @Override
    public PageBean getPageBeanToMember(Member member, Integer page, Integer pageSize, Map<String,Object> map){
    	return adminDao.getPageBeanToMember(member, page, pageSize, map);
    }
    
    @Override
    public boolean updateAlertAmount(AlertAmountTable alertAmountTable){
    	return adminDao.updateAlertAmount(alertAmountTable);
    }
    
    @Override
    public void updateAlertAmountStatus(String[] postCode, String status){
    	adminDao.updateAlertAmountStatus(postCode, status);
    }
}
