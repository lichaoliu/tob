package com.cndym.admin.dao.impl;

import com.cndym.bean.admin.AlertAmountTable;
import com.cndym.bean.user.Member;
import com.cndym.admin.dao.IAdminManagesDao;
import com.cndym.dao.impl.GenericDaoImpl;
import com.cndym.utils.Utils;
import com.cndym.utils.hibernate.HibernatePara;
import com.cndym.utils.hibernate.PageBean;

import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

/**
 * User: 梁桂立 Date: 14-4-14   下午10:53
 */
@Repository
public class AdminManagesDaoImpl extends GenericDaoImpl<AlertAmountTable> implements IAdminManagesDao {
    @Resource
    private SessionFactory sessionFactoryTemp;
    @Resource
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void sessionFactoryInit() {
        super.setSessionFactory(sessionFactoryTemp);
    }

    @Override
    public List<AlertAmountTable> getAlertAmountList() {
    	List<AlertAmountTable> list = find(" From AlertAmountTable where 1 = ? order by postCode asc ",new Object[]{1});
		if (list.isEmpty()) {
			return null;
		}
        return list;
    }
    
    @Override
    public PageBean getPageBeanToMember(Member member, Integer page, Integer pageSize,Map<String,Object> map){
    	StringBuffer sqlHb1 = new StringBuffer(" from Member m,Account a where m.userCode = a.userCode ");
    	StringBuffer sqlSum = new StringBuffer(" SELECT COUNT(*) COUNT, SUM(A.RECHARGE_AMOUNT + A.BONUS_AMOUNT) AMOUNT  FROM USER_MEMBER M,USER_ACCOUNT A WHERE M.USER_CODE = A.USER_CODE ");
    	StringBuffer sqlPara = new StringBuffer("");
    	StringBuffer sqlOrderby = new StringBuffer("");
    	List<HibernatePara> paraList = new ArrayList<HibernatePara>();
    	List<Object> paraSql = new ArrayList<Object>();
        if (Utils.isNotEmpty(member)) {
            if (Utils.isNotEmpty(member.getName())) {
            	sqlPara.append(" and m.name like ? ");
                paraList.add(new HibernatePara("%" + member.getName() + "%"));
                paraSql.add("%" + member.getName() + "%");
            }
            if (Utils.isNotEmpty(member.getSid())) {
            	sqlPara.append(" and m.sid = ? ");
                paraList.add(new HibernatePara(member.getSid()));
                paraSql.add(member.getSid());
            }
            if (Utils.isNotEmpty(member.getStatus())) {
            	sqlPara.append(" and m.status = ? ");
                paraList.add(new HibernatePara(member.getStatus()));
                paraSql.add(member.getStatus());
            }
        }
        sqlOrderby.append(" order by a.rechargeAmount + a.bonusAmount desc ");
       
        Map<String,Object> localMap = jdbcTemplate.queryForMap(sqlSum.toString() + sqlPara.toString(), paraSql.toArray());
        Object amount = localMap.get("AMOUNT");
        map.put("AMOUNT", amount);
        
        
        return getPageBeanByPara(sqlHb1.toString()+sqlPara.toString()+sqlOrderby.toString(), paraList, page, pageSize);
    }
    
    public boolean updateAlertAmount(AlertAmountTable alertAmountTable){
    	List<AlertAmountTable> list = find(" From AlertAmountTable where postCode = ? order by postCode asc ",new Object[]{alertAmountTable.getPostCode()});
    	if (list.isEmpty()) {
			return false;
		}
    	AlertAmountTable tempAlertAmount = list.get(0);
    	tempAlertAmount.setAlertAmount(alertAmountTable.getAlertAmount());
    	tempAlertAmount.setLotteryCode(alertAmountTable.getLotteryCode());
    	tempAlertAmount.setStatus(alertAmountTable.getStatus());
    	tempAlertAmount.setStatusTime(new Date());
    	
    	boolean flag = update(tempAlertAmount);
    	return flag;
    }
    
    public void updateAlertAmountStatus(final String[] postCode, final String status){
    	String tmpPostCode = "";
    	for(int i = 0; i < postCode.length; i++){
    		if(i != postCode.length - 1 ){
    			tmpPostCode = tmpPostCode + postCode[i] + "," ;
    		}else{
    			tmpPostCode = tmpPostCode + postCode[i] ;
    		}
    	}
    	
    	
    	StringBuilder sql = new StringBuilder();
    	sql.append(" update tms_alert_amount set status = ?, status_time = sysdate ");
    	sql.append(" where post_code =  ?  ");
    	
    	
    	BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter(){  
    		@Override
    		public void setValues(PreparedStatement ps,int i) throws SQLException{  
               ps.setString(1, status);
               ps.setString(2, postCode[i]);  
              }  
              public int getBatchSize(){  
                 return postCode.length;  
              }
        };  
    	
    	int count[] = jdbcTemplate.batchUpdate(sql.toString(), setter);
    	
    	System.out.println("count:" + count.length);
    	
    }

    public SessionFactory getSessionFactoryTemp() {
        return sessionFactoryTemp;
    }

    public void setSessionFactoryTemp(SessionFactory sessionFactoryTemp) {
        this.sessionFactoryTemp = sessionFactoryTemp;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
