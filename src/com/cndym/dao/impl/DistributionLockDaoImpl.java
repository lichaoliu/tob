package com.cndym.dao.impl;

import com.cndym.bean.sys.DistributionLock;
import com.cndym.dao.IDistributionLockDao;
import com.cndym.utils.Utils;
import com.cndym.utils.hibernate.HibernatePara;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: 邓玉明
 * Date: 11-3-27 下午10:25
 */
@Repository
public class DistributionLockDaoImpl extends GenericDaoImpl<DistributionLock> implements IDistributionLockDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Resource
    private SessionFactory sessionFactoryTemp;

    @PostConstruct
    private void sessionFactoryInit() {
        super.setSessionFactory(sessionFactoryTemp);
    }

    public int forInstance() {
        String sql = "update sys_distribution_lock t set t.status=0";
        return jdbcTemplate.update(sql);
    }

    public int startLock(DistributionLock distributionLock) {
        if (Utils.isNotEmpty(distributionLock.getCode())) {
            String sql = "update sys_distribution_lock t set t.status=1,t.create_time=sysdate,t.user_name=? where t.name=? and t.code=? and t.status=0";
            return jdbcTemplate.update(sql, new String[]{localhostIp(), distributionLock.getName(), distributionLock.getCode()});
        } else {
            String sql = "update sys_distribution_lock t set t.status=1,t.create_time=sysdate,t.user_name=? where t.name=? and t.status=0";
            return jdbcTemplate.update(sql, new String[]{localhostIp(), distributionLock.getName()});

        }
    }

    public int endLock(DistributionLock distributionLock) {
        if (Utils.isNotEmpty(distributionLock.getCode())) {
            String sql = "update sys_distribution_lock t set t.status=0,t.create_time=sysdate where t.name=? and t.code=?";
            return jdbcTemplate.update(sql, new String[]{distributionLock.getName(), distributionLock.getCode()});
        } else {
            String sql = "update sys_distribution_lock t set t.status=0,t.create_time=sysdate where t.name=?";
            return jdbcTemplate.update(sql, new String[]{distributionLock.getName()});
        }
    }

    @Override
    public List<DistributionLock> getDistributionLockList(DistributionLock distributionLock) {
        StringBuffer sql = new StringBuffer("From DistributionLock where 1=1");
        List<HibernatePara> paras = new ArrayList<HibernatePara>();
        if (Utils.isNotEmpty(distributionLock)) {
            if (Utils.isNotEmpty(distributionLock.getName())) {
                sql.append(" and name=?");
                paras.add(new HibernatePara(distributionLock.getName()));
            }
            if (Utils.isNotEmpty(distributionLock.getStatus())) {
                sql.append(" and status=?");
                paras.add(new HibernatePara(distributionLock.getStatus()));
            }
            if (Utils.isNotEmpty(distributionLock.getStop())) {
                sql.append(" and stop=?");
                paras.add(new HibernatePara(distributionLock.getStop()));
            }
            if (Utils.isNotEmpty(distributionLock.getInterval())) {
                sql.append(" and interval=?");
                paras.add(new HibernatePara(distributionLock.getInterval()));
            }
            if (Utils.isNotEmpty(distributionLock.getUserName())) {
                sql.append(" and user=?");
                paras.add(new HibernatePara(distributionLock.getUserName()));
            }
            if (Utils.isNotEmpty(distributionLock.getCode())) {
                sql.append(" and code=?");
                paras.add(new HibernatePara(distributionLock.getCode()));
            }
            if (Utils.isNotEmpty(distributionLock.getCreateTime())) {
                sql.append(" and createTime>");
                paras.add(new HibernatePara(distributionLock.getCreateTime()));
            }
            if (Utils.isNotEmpty(distributionLock.getCreateTime())) {
                sql.append(" and id=");
                paras.add(new HibernatePara(distributionLock.getId()));
            }
        }
        
        sql.append(" order by name");
        return findList(sql.toString(), paras);
    }

    public int updateStatus(String name) {
        DistributionLock distributionLock = new DistributionLock();
        distributionLock.setName(name);
        List<DistributionLock> distributionLocks = getDistributionLockList(distributionLock);
        Date date = new Date();
        if (!distributionLocks.isEmpty()) {
            distributionLock = distributionLocks.get(0);
            if ((date.getTime() - distributionLock.getCreateTime().getTime()) > 30000) {
                distributionLock.setStatus(0);
                return update(distributionLock) ? 1 : 0;
            }
        }
        return 0;
    }

    @Override
    public int updateStatus(String name, int status) {
        DistributionLock distributionLock = new DistributionLock();
        distributionLock.setName(name);
        List<DistributionLock> distributionLocks = getDistributionLockList(distributionLock);
        Date date = new Date();
        if (!distributionLocks.isEmpty()) {
            distributionLock = distributionLocks.get(0);
            int statusDb = distributionLock.getStatus();
            distributionLock.setStatus(status);
            if (1 == status && statusDb != status) {
                return update(distributionLock) ? 1 : 0;
            } else if (0 == status && statusDb != status && (date.getTime() - distributionLock.getCreateTime().getTime()) > 30000) {
                return update(distributionLock) ? 1 : 0;
            }
        }
        return 0;
    }
    
    
    @Override
	public int updateStop(String name, int stop) {
    	 DistributionLock distributionLock = new DistributionLock();
         distributionLock.setName(name);
         List<DistributionLock> distributionLocks = getDistributionLockList(distributionLock);
         Date date = new Date();
         if (!distributionLocks.isEmpty()) {
             distributionLock = distributionLocks.get(0);
             int stopDb = distributionLock.getStop();
             int status = distributionLock.getStatus();
             distributionLock.setStop(stop);
             if(stop == 0 && stopDb != stop){
            	 return update(distributionLock) ? 1 : 0;
             }else if(stop == 1) {//如果想要停用
            	 if(status == 0){//如果未锁定，则直接停止
                	 return update(distributionLock) ? 1 : 0;
                 }else if(status == 1 && (date.getTime() - distributionLock.getCreateTime().getTime()) > 30000){//如果已锁定但是锁定时间超过30秒，直接停止
                	 return update(distributionLock) ? 1 : 0;
                 }
			 }
         }
         return 0;
	}

	public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public SessionFactory getSessionFactoryTemp() {
        return sessionFactoryTemp;
    }

    public void setSessionFactoryTemp(SessionFactory sessionFactoryTemp) {
        this.sessionFactoryTemp = sessionFactoryTemp;
    }

    public String localhostIp() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }
}
