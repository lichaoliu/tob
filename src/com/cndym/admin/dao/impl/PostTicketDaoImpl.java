package com.cndym.admin.dao.impl;

import com.cndym.bean.query.TicketQueryBean;
import com.cndym.bean.tms.Ticket;
import com.cndym.admin.dao.IPostTicketDao;
import com.cndym.dao.impl.GenericDaoImpl;
import com.cndym.utils.DateUtils;
import com.cndym.utils.JdbcPageUtil;
import com.cndym.utils.Utils;
import com.cndym.utils.hibernate.PageBean;

import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import java.util.*;

/**
 * User: 梁桂立 Date: 14-4-14   下午10:53
 */
@Repository
public class PostTicketDaoImpl extends GenericDaoImpl<Ticket> implements IPostTicketDao {
    @Resource
    private SessionFactory sessionFactoryTemp;
    @Resource
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void sessionFactoryInit() {
        super.setSessionFactory(sessionFactoryTemp);
    }

    @Override
    public PageBean getPageBeanByPara(TicketQueryBean queryBean, Integer page, Integer pageSize,Map<String,Object> map) {
        StringBuffer sql = new StringBuffer(" FROM ( ");
        sql.append(" SELECT T.POST_CODE,T.LOTTERY_CODE,SUM(T.AMOUNT) AMOUNT FROM TMS_TICKET T WHERE 1 = 1 ");
        List<Object> paramList = new ArrayList<Object>();
        if (Utils.isNotEmpty(queryBean)) {
        	Ticket ticket = queryBean.getTicket();
        	if (Utils.isNotEmpty(ticket.getLotteryCode())) {
                sql.append(" AND T.LOTTERY_CODE = ? ");
                paramList.add(ticket.getLotteryCode());
            }
        	
        	if (Utils.isNotEmpty(ticket.getPostCode())) {
                sql.append(" AND T.POST_CODE = ? ");
                paramList.add(ticket.getPostCode());
            }
        	
        	if (Utils.isNotEmpty(queryBean.getCreateStartTime())){
        		sql.append(" AND T.CREATE_TIME >= ? ");
                paramList.add(queryBean.getCreateStartTime());
        	}
        	
        	if (Utils.isNotEmpty(queryBean.getCreateEndTime())){
        		sql.append(" AND T.CREATE_TIME <= ? ");
                paramList.add(queryBean.getCreateEndTime());
        	}
        	
        }
        
        sql.append(" GROUP BY T.POST_CODE,T.LOTTERY_CODE ");
        sql.append(" ORDER BY T.POST_CODE,T.LOTTERY_CODE ASC ");
        sql.append(" ) ");

        logger.info("SELECT COUNT(*) " + sql.toString());
        
        int count = 0;
        Map<String,Object> localMap = jdbcTemplate.queryForMap("SELECT COUNT(*) COUNT, SUM(AMOUNT) AMOUNT " + sql.toString(), paramList.toArray());
        count = ((java.math.BigDecimal)localMap.get("COUNT")).intValue();
        Object amount = localMap.get("AMOUNT");
        map.put("AMOUNT", amount);
        
        int pageTotal = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;

        PageBean pageBean = new PageBean();
        List resultList = new ArrayList();
        if (page <= pageTotal) {
            StringBuffer select = new StringBuffer("SELECT T.LOTTERY_CODE AS LOTTERYCODE,T.POST_CODE AS POSTCODE ,T.AMOUNT AS AMOUNT  ");
            select.append(sql);
            select.append(" T ");
            resultList = jdbcTemplate.queryForList(JdbcPageUtil.getOraclePageSQL(select.toString(), page, pageSize), paramList.toArray());
        }
        pageBean.setPageId(page);
        pageBean.setPageTotal(pageTotal);
        pageBean.setItemTotal(new Long(count));
        pageBean.setPageContent(resultList);
        return pageBean;
    }

    @Override
    public Map<String, Object> getTicketCount(TicketQueryBean queryBean) {
        StringBuffer sql = new StringBuffer( "select count(1) as ticketNum from ( ");
        sql.append(" SELECT T.POST_CODE,T.LOTTERY_CODE,SUM(T.AMOUNT) AMOUNT FROM TMS_TICKET T WHERE 1 = 1 ");
        List<Object> paramList = new ArrayList<Object>();
        if (Utils.isNotEmpty(queryBean)) {
        	Ticket ticket = queryBean.getTicket();
        	if (Utils.isNotEmpty(ticket.getLotteryCode())) {
                sql.append(" AND T.LOTTERY_CODE = ? ");
                paramList.add(ticket.getLotteryCode());
            }
        	
        	if (Utils.isNotEmpty(ticket.getPostCode())) {
                sql.append(" AND T.POST_CODE = ? ");
                paramList.add(ticket.getPostCode());
            }
        	
        	if (Utils.isNotEmpty(queryBean.getCreateStartTime())){
        		sql.append(" AND T.CREATE_TIME >= ? ");
                paramList.add(queryBean.getCreateStartTime());
        	}
        	
        	if (Utils.isNotEmpty(queryBean.getCreateEndTime())){
        		sql.append(" AND T.CREATE_TIME <= ? ");
                paramList.add(queryBean.getCreateEndTime());
        	}
        }
        sql.append(" GROUP BY T.POST_CODE,T.LOTTERY_CODE ");
        sql.append(" ORDER BY T.POST_CODE,T.LOTTERY_CODE ASC ");
        sql.append(" ) ");
        List<Map<String, Object>> dataList = jdbcTemplate.queryForList(sql.toString(), paramList.toArray());
        if (Utils.isNotEmpty(dataList)) {
            return dataList.get(0);
        }
        return null;
    }
    
    public PageBean getPageBeanByParaToMember(TicketQueryBean queryBean, Integer page, Integer pageSize, Map<String,Object> map){
    	 StringBuffer sql = new StringBuffer(" FROM ( ");
         
         sql.append("  SELECT T.SID,T.NAME,T.LOTTERY_CODE,SUM(T.AMOUNT) AMOUNT FROM ( ");
         sql.append("    SELECT T.SID,T.LOTTERY_CODE,T.AMOUNT,T.CREATE_TIME,TU.NAME FROM TMS_TICKET T  ");
         sql.append("    LEFT JOIN USER_MEMBER TU ON T.USER_CODE = TU.USER_CODE  WHERE 1 = 1 ");
         List<Object> paramList = new ArrayList<Object>();
         if (Utils.isNotEmpty(queryBean)) {
         	Ticket ticket = queryBean.getTicket();
         	if (Utils.isNotEmpty(ticket.getLotteryCode())) {
                 sql.append(" AND T.LOTTERY_CODE = ? ");
                 paramList.add(ticket.getLotteryCode());
             }
         	if (Utils.isNotEmpty(ticket.getSid())) {
                 sql.append(" AND T.SID = ? ");
                 paramList.add(ticket.getSid());
             }
         	
         	if (Utils.isNotEmpty(queryBean.getCreateStartTime())){
        		sql.append(" AND T.CREATE_TIME >= ? ");
                paramList.add(queryBean.getCreateStartTime());
        	}
        	
        	if (Utils.isNotEmpty(queryBean.getCreateEndTime())){
        		sql.append(" AND T.CREATE_TIME <= ? ");
                paramList.add(queryBean.getCreateEndTime());
        	}
         }
         
         sql.append("  ) T GROUP BY T.SID,T.NAME,T.LOTTERY_CODE ");
         sql.append("  ORDER BY T.SID,T.LOTTERY_CODE ASC ");
         sql.append(" ) ");
         

         logger.info("SELECT COUNT(*) " + sql.toString());
         int count = 0;
         Map<String,Object> localMap = jdbcTemplate.queryForMap("SELECT COUNT(*) COUNT, SUM(AMOUNT) AMOUNT " + sql.toString(), paramList.toArray());
         count = ((java.math.BigDecimal)localMap.get("COUNT")).intValue();
         Object amount = localMap.get("AMOUNT");
         map.put("AMOUNT", amount);
         
         int pageTotal = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;

         PageBean pageBean = new PageBean();
         List resultList = new ArrayList();
         if (page <= pageTotal) {
             StringBuffer select = new StringBuffer("SELECT T.LOTTERY_CODE AS LOTTERYCODE,T.SID AS SID ,T.AMOUNT AS AMOUNT ,T.NAME NAME  ");
             select.append(sql);
             select.append(" T ");
             resultList = jdbcTemplate.queryForList(JdbcPageUtil.getOraclePageSQL(select.toString(), page, pageSize), paramList.toArray());
         }
         pageBean.setPageId(page);
         pageBean.setPageTotal(pageTotal);
         pageBean.setItemTotal(new Long(count));
         pageBean.setPageContent(resultList);
         return pageBean;
    }

    public Map<String, Object> getTicketCountToMember(TicketQueryBean queryBean){
		StringBuffer sql = new StringBuffer("SELECT COUNT(1) AS TICKETNUM FROM ( ");
		sql.append("  SELECT T.SID,T.NAME,T.LOTTERY_CODE,SUM(T.AMOUNT) AMOUNT FROM ( ");
		sql.append("    SELECT T.SID,T.LOTTERY_CODE,T.AMOUNT,T.CREATE_TIME,TU.NAME FROM TMS_TICKET T  ");
		sql.append("    LEFT JOIN USER_MEMBER TU ON T.USER_CODE = TU.USER_CODE  WHERE 1 = 1 ");
		List<Object> paramList = new ArrayList<Object>();
		if (Utils.isNotEmpty(queryBean)) {
			Ticket ticket = queryBean.getTicket();
			if (Utils.isNotEmpty(ticket.getLotteryCode())) {
				sql.append(" AND T.LOTTERY_CODE = ? ");
				paramList.add(ticket.getLotteryCode());
			}
			if (Utils.isNotEmpty(ticket.getSid())) {
				sql.append(" AND T.SID = ? ");
				paramList.add(ticket.getSid());
			}
			
			if (Utils.isNotEmpty(queryBean.getCreateStartTime())){
        		sql.append(" AND T.CREATE_TIME >= ? ");
                paramList.add(queryBean.getCreateStartTime());
        	}
        	
        	if (Utils.isNotEmpty(queryBean.getCreateEndTime())){
        		sql.append(" AND T.CREATE_TIME <= ? ");
                paramList.add(queryBean.getCreateEndTime());
        	}
		}
		sql.append("  ) T GROUP BY T.SID,T.NAME,T.LOTTERY_CODE ");
		sql.append("  ORDER BY T.SID,T.LOTTERY_CODE ASC ");
		sql.append(" ) ");
        List<Map<String, Object>> dataList = jdbcTemplate.queryForList(sql.toString(), paramList.toArray());
        if (Utils.isNotEmpty(dataList)) {
            return dataList.get(0);
        }
        return null;
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
