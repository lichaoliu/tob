package com.cndym.dao.impl;

import com.cndym.bean.tms.MainIssue;
import com.cndym.cache.XMemcachedClientWrapper;
import com.cndym.constant.Constants;
import com.cndym.dao.IMainIssueDao;
import com.cndym.dao.impl.rowMapperBean.MainIssueRowMapper;
import com.cndym.utils.DateUtils;
import com.cndym.utils.JdbcPageUtil;
import com.cndym.utils.JsonBinder;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import com.cndym.utils.hibernate.HibernatePara;
import com.cndym.utils.hibernate.PageBean;

import org.codehaus.jackson.type.TypeReference;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

/**
 * User: 邓玉明 Date: 11-3-27 下午10:47
 */
@Repository
public class MainIssueDaoImpl extends GenericDaoImpl<MainIssue> implements IMainIssueDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Resource
    private SessionFactory sessionFactoryTemp;

    @PostConstruct
    private void sessionFactoryInit() {
        super.setSessionFactory(sessionFactoryTemp);
    }

    @Override
    public MainIssue getMainIssueForUpdate(String lotteryCode, String issue) {
        String sql = "select * from tms_main_issue t where t.lottery_code=? and t.name=? for update";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{lotteryCode, issue}, new MainIssueRowMapper());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<MainIssue> getStartMainIssueByLotteryIssue(String lotteryCode, String issue) {
    	
    	List<MainIssue> issueList = null;
    	if(Utils.isNotEmpty(issue)){//从数据库查询历史期次
    		StringBuffer sql = new StringBuffer("select * from tms_main_issue t where t.lottery_code=?");
	        List para = new ArrayList();
	        para.add(lotteryCode);
	        sql.append(" and t.name=?");
	        para.add(issue);
	        sql.append(" order by t.start_time asc");
	        issueList = jdbcTemplate.query(sql.toString(), para.toArray(), ParameterizedBeanPropertyRowMapper.newInstance(MainIssue.class));
    	     
    	}else {//从缓存查询当前期次
    		try {
        		XMemcachedClientWrapper memcachedClientWrapper = (XMemcachedClientWrapper) SpringUtils.getBean("xmemcachedClientWrapper");
        		String issueJson = (String) memcachedClientWrapper.getMemcachedClient().get(Constants.MEMCACHED_CURRENT_ISSUE_LIST);
        		if (null != issueJson) {
        			List<MainIssue> mainIssueList = JsonBinder.buildNormalBinder().getMapper().readValue(issueJson, new TypeReference<List<MainIssue>>() {
        			});
        			if(mainIssueList != null){
        				for(MainIssue mainIssue : mainIssueList){
        					if(mainIssue.getLotteryCode().equals(lotteryCode) && mainIssue.getStatus().equals(Constants.ISSUE_STATUS_START) && mainIssue.getSendStatus().equals(Constants.ISSUE_SALE_SEND)){
        						logger.info("select mainissue from Memcached lotteryCode:" + lotteryCode + ",issue:" + mainIssue.getName());
        						issueList = new ArrayList<MainIssue>();
        						issueList.add(mainIssue);
        						break;
        					}
        				}
        			}
        		}
        		
    		} catch (Exception e) {
    			logger.error("getStartMainIssueByLotteryIssue error:",e);
    		}
    		if(issueList == null){
    			StringBuffer sql = new StringBuffer("select * from tms_main_issue t where t.lottery_code=? and t.status = ? and t.send_status = ?");
    	        List para = new ArrayList();
    	        para.add(lotteryCode); 
                para.add(Constants.ISSUE_STATUS_START);
                para.add(Constants.ISSUE_SALE_SEND);
    	        sql.append(" order by t.start_time asc");
    	        issueList = jdbcTemplate.query(sql.toString(), para.toArray(), ParameterizedBeanPropertyRowMapper.newInstance(MainIssue.class));
    		}
		}
        
        return issueList;
    }

    @Override
    public MainIssue getMainIssueByLotteryIssue(String lotteryCode, String issue) {
        StringBuffer sql = new StringBuffer("select * from tms_main_issue t where t.lottery_code=?");
        List para = new ArrayList();
        para.add(lotteryCode);
        if (Utils.isNotEmpty(issue)) {
            sql.append(" and t.name=?");
            para.add(issue);
        } else {
            sql.append(" and t.status in (?,?) and t.send_status = ?");
            para.add(Constants.ISSUE_STATUS_WAIT);
            para.add(Constants.ISSUE_STATUS_START);
            para.add(Constants.ISSUE_SALE_SEND);
        }
        sql.append(" order by t.start_time asc");
        List<MainIssue> issueList = jdbcTemplate.query(sql.toString(), para.toArray(), ParameterizedBeanPropertyRowMapper.newInstance(MainIssue.class));
        if (Utils.isNotEmpty(issueList)) {
            return issueList.get(0);
        }
        return null;
    }

    @Override
    public MainIssue getMainIssueByLotteryIssue(String lotteryCode) {
        StringBuffer sql = new StringBuffer("select * from tms_main_issue t where t.lottery_code=?");
        List para = new ArrayList();
        para.add(lotteryCode);
        sql.append(" and status=3 and t.bonus_number is not null and t.bonus_number !='-'");
        sql.append(" order by t.start_time desc");
        List<MainIssue> issueList = jdbcTemplate.query(sql.toString(), para.toArray(), ParameterizedBeanPropertyRowMapper.newInstance(MainIssue.class));
        if (Utils.isNotEmpty(issueList)) {
            return issueList.get(0);
        }
        return null;
    }

    @Override
    public void doUpdateStatus(int status, int sendStatus, int bonusStatus, long id) {
        jdbcTemplate.update("update tms_main_issue m set m.status=?,m.send_status=?,m.bonus_status=? where m.id=?", new Object[]{status, sendStatus, bonusStatus, id});
    }

    @Override
    public void doUpdateBonusStatus(String lotteryCode, String issue) {
        jdbcTemplate.update("update tms_main_issue m set m.bonus_status=1,m.center_bonus_status=1,m.operators_award=2 where m.lottery_code=? and m.name=?", new Object[]{lotteryCode, issue});
    }

    @Override
    public List<MainIssue> getMainIssuesByStatusForBonusQuery(MainIssue mainIssue) {
        StringBuilder sql = new StringBuilder("From MainIssue where 1=1");
        List<HibernatePara> hibernateParas = new ArrayList<HibernatePara>();
        if (Utils.isNotEmpty(mainIssue)) {
            if (Utils.isNotEmpty(mainIssue.getStatus())) {
                sql.append(" and status=?");
                hibernateParas.add(new HibernatePara(mainIssue.getStatus()));
            }
            if (Utils.isNotEmpty(mainIssue.getLotteryCode())) {
                sql.append(" and lotteryCode=?");
                hibernateParas.add(new HibernatePara(mainIssue.getLotteryCode()));
            }
            if (Utils.isNotEmpty(mainIssue.getBonusStatus())) {
                sql.append(" and bonusStatus=?");
                hibernateParas.add(new HibernatePara(mainIssue.getBonusStatus()));
            }
            if (Utils.isNotEmpty(mainIssue.getCenterBonusStatus())) {
                sql.append(" and centerBonusStatus=?");
                hibernateParas.add(new HibernatePara(mainIssue.getCenterBonusStatus()));
            }
            if (Utils.isNotEmpty(mainIssue.getEndTime())) {
                sql.append(" and endTime <=?");
                hibernateParas.add(new HibernatePara(mainIssue.getEndTime()));
            }
            if ("no".equals(mainIssue.getBonusNumber())) {
                sql.append(" and centerBonusStatus=?");
                hibernateParas.add(new HibernatePara(mainIssue.getCenterBonusStatus()));
            }
        }
        return findList(sql.toString(), hibernateParas);
    }

    @Override
    public List<MainIssue> getMainIssuesByStatusForGpBonusQuery(MainIssue mainIssue) {
        StringBuilder sql = new StringBuilder("From MainIssue where 1=1");
        List<HibernatePara> hibernateParas = new ArrayList<HibernatePara>();
        sql.append(" and bonusNumber<>'-' and bonusNumber is not null ");
        if (Utils.isNotEmpty(mainIssue)) {
            if (Utils.isNotEmpty(mainIssue.getStatus())) {
                sql.append(" and status=?");
                hibernateParas.add(new HibernatePara(mainIssue.getStatus()));
            }
            if (Utils.isNotEmpty(mainIssue.getLotteryCode())) {
                sql.append(" and lotteryCode=?");
                hibernateParas.add(new HibernatePara(mainIssue.getLotteryCode()));
            }
            if (Utils.isNotEmpty(mainIssue.getBonusStatus())) {
                sql.append(" and bonusStatus=?");
                hibernateParas.add(new HibernatePara(mainIssue.getBonusStatus()));
            }
            if (Utils.isNotEmpty(mainIssue.getOperatorsAward())) {
                sql.append(" and operatorsAward=?");
                hibernateParas.add(new HibernatePara(mainIssue.getBonusStatus()));
            }
        }
        return findList(sql.toString(), hibernateParas);
    }

    @Override
    public PageBean findIssueForStatus(MainIssue mainIssue, int pageSize, int page, String order) {
        StringBuffer sql = new StringBuffer("From MainIssue where 1=1");
        List<HibernatePara> hibernateParas = new ArrayList<HibernatePara>();
        if (Utils.isNotEmpty(mainIssue)) {
            if (Utils.isNotEmpty(mainIssue.getLotteryCode())) {
                sql.append(" and lotteryCode=?");
                hibernateParas.add(new HibernatePara(mainIssue.getLotteryCode()));
            }
            if (Utils.isNotEmpty(mainIssue.getStatuses())) {
                StringBuffer temp = new StringBuffer();
                for (Integer status : mainIssue.getStatuses()) {
                    temp.append(",").append(status);
                }
                sql.append(" and status in (" + temp.substring(1) + ")");
            }

            if (Utils.isNotEmpty(mainIssue.getBonusNumber())) {
                sql.append(" and bonusNumber<>'-'");
            }
        }
        sql.append(" order by startTime " + order + ",name " + order);
        PageBean pageBean = getPageBeanByPara(sql.toString(), hibernateParas, page, pageSize);
        return pageBean;
    }

    @Override
    public PageBean findIssueForGetBonusNumber(MainIssue mainIssue, int pageSize, int page) {
        StringBuffer sql = new StringBuffer("From MainIssue where 1=1");
        List<HibernatePara> hibernateParas = new ArrayList<HibernatePara>();
        if (Utils.isNotEmpty(mainIssue)) {
            if (Utils.isNotEmpty(mainIssue.getLotteryCode())) {
                sql.append(" and lotteryCode=?");
                hibernateParas.add(new HibernatePara(mainIssue.getLotteryCode()));
            }

            sql.append(" and status=?");
            hibernateParas.add(new HibernatePara(Constants.ISSUE_STATUS_END));

            sql.append(" and (bonusNumber='-' or bonusNumber is null) ");
        }
        PageBean pageBean = getPageBeanByPara(sql.toString(), hibernateParas, page, pageSize);
        return pageBean;
    }

    public List getIssueListByLotteryAndTime(String lotteryCode) {
        StringBuffer sql = new StringBuffer("select name from tms_main_issue t where t.duplex_time>=SYSDATE and t.status in (0,1) ");
        int pageSize = 10;
        if (Utils.isNotEmpty(lotteryCode)) {
            if (lotteryCode.indexOf("30") > -1) {// 足彩
                sql.append(" and t.lottery_code=?");
            } else {// 非足彩
                sql.append(" and t.lottery_code=?");
                pageSize = 1;
            }
        }
        sql.append("order by t.start_time asc");
        return jdbcTemplate.queryForList(JdbcPageUtil.getOraclePageSQL(sql.toString(), 1, pageSize), new Object[]{lotteryCode});
    }

    @Override
    public int updateIssue(MainIssue issue) {
        StringBuffer sql = new StringBuffer("");
        List<Object> list = new ArrayList<Object>();
        if (Utils.isNotEmpty(issue)) {
            if (null == issue.getId()) {
                return 0;
            }
            sql.append("update tms_main_issue set id=?");
            list.add(issue.getId());
            if (Utils.isNotEmpty(issue.getName())) {
                sql.append(" ,name=?");
                list.add(issue.getName());
            }
            if (Utils.isNotEmpty(issue.getStatus())) {
                sql.append(" ,status=?");
                list.add(issue.getStatus());
            }
            if (Utils.isNotEmpty(issue.getLotteryCode())) {
                sql.append(" ,lottery_code=?");
                list.add(issue.getLotteryCode());
            }
            if (Utils.isNotEmpty(issue.getSendStatus())) {
                sql.append(" ,send_status=?");
                list.add(issue.getSendStatus());
            }
            if (Utils.isNotEmpty(issue.getStartTime())) {
                sql.append(" ,start_time=?");
                list.add(issue.getStartTime());
            }
            if (Utils.isNotEmpty(issue.getEndTime())) {
                sql.append(" ,end_time=?");
                list.add(issue.getEndTime());
            }
            if (Utils.isNotEmpty(issue.getSimplexTime())) {
                sql.append(" ,simplex_time=?");
                list.add(issue.getSimplexTime());
            }
            if (Utils.isNotEmpty(issue.getDuplexTime())) {
                sql.append(" ,duplex_time=?");
                list.add(issue.getDuplexTime());
            }
            if (Utils.isNotEmpty(issue.getBonusNumber())) {
                sql.append(" ,bonus_number=?");
                list.add(issue.getBonusNumber());
            }
            if (Utils.isNotEmpty(issue.getBonusStatus())) {
                sql.append(" ,bonus_status=?");
                list.add(issue.getBonusStatus());
            }
            if (Utils.isNotEmpty(issue.getPrizePool())) {
                sql.append(" ,prize_pool=?");
                list.add(issue.getPrizePool());
            }
            if (Utils.isNotEmpty(issue.getBonusTime())) {
                sql.append(" ,bonus_time=?");
                list.add(issue.getBonusTime());
            }
            if (Utils.isNotEmpty(issue.getBonusClass())) {
                sql.append(" ,bonus_class=?");
                list.add(issue.getBonusClass());
            }
            if (Utils.isNotEmpty(issue.getSaleTotal())) {
                sql.append(" ,sale_total=?");
                list.add(issue.getSaleTotal());
            }
            if (Utils.isNotEmpty(issue.getGlobalSaleTotal())) {
                sql.append(" ,global_sale_total=?");
                list.add(issue.getGlobalSaleTotal());
            }
            if (Utils.isNotEmpty(issue.getBackup1())) {
                sql.append(" ,backup1=?");
                list.add(issue.getBackup1());
            }
            if (Utils.isNotEmpty(issue.getId())) {
                sql.append(" where id=?");
                list.add(issue.getId());
            }
            return jdbcTemplate.update(sql.toString(), list.toArray());
        }
        return 0;
    }

    @Override
    public MainIssue getMainIssueEndForCreate(String lotteryCode) {
        List<MainIssue> mainIssues = find("From MainIssue where lotteryCode=? order by name desc", new String[]{lotteryCode});
        if (mainIssues.isEmpty()) {
            return null;
        }
        return mainIssues.get(0);
    }

    @Override
    public List getIssueListByStatusAndAward(String lotteryCode, Integer status, Integer operatorsAward) {
        StringBuilder sql = new StringBuilder("From MainIssue where 1=1");
        List<HibernatePara> hibernateParas = new ArrayList<HibernatePara>();
        if (Utils.isNotEmpty(status)) {
            sql.append(" and status=?");
            hibernateParas.add(new HibernatePara(status));
        }
        if (Utils.isNotEmpty(lotteryCode)) {
            sql.append(" and lotteryCode=?");
            hibernateParas.add(new HibernatePara(lotteryCode));
        }
        if (Utils.isNotEmpty(operatorsAward)) {
            sql.append(" and operatorsAward=?");
            hibernateParas.add(new HibernatePara(operatorsAward));
        }
        sql.append(" order by name asc");
        return findList(sql.toString(), hibernateParas);
    }

    @Override
    public List<MainIssue> getIssueListByAwardAndBonus(String lotteryCode, Integer operatorsAward, Integer bonusStatus) {
        StringBuilder sql = new StringBuilder("From MainIssue where 1=1");
        List<HibernatePara> hibernateParas = new ArrayList<HibernatePara>();
        if (Utils.isNotEmpty(lotteryCode)) {
            sql.append(" and lotteryCode=?");
            hibernateParas.add(new HibernatePara(lotteryCode));
        }
        if (Utils.isNotEmpty(operatorsAward)) {
            sql.append(" and operatorsAward=?");
            hibernateParas.add(new HibernatePara(operatorsAward));
        }
        if (Utils.isNotEmpty(bonusStatus)) {
            sql.append(" and bonusStatus=?");
            hibernateParas.add(new HibernatePara(bonusStatus));
        }
        sql.append(" order by name asc");
        return findList(sql.toString(), hibernateParas);
    }

    @Override
    public MainIssue getIssueOfLastBonus(String lotteryCode) {
        if (Utils.isEmpty(lotteryCode)) {
            return null;
        }
        StringBuilder sql = new StringBuilder("From MainIssue where 1=1");
        sql.append(" and lotteryCode=?");
        sql.append(" and status=?");
        sql.append(" and operatorsAward=? and bonus_status = ? order by name desc ");
        List<MainIssue> list = find(sql.toString(), new Object[]{lotteryCode, Constants.ISSUE_STATUS_END, Constants.OPERATORS_AWARD_COMPLETE, Constants.ISSUE_BONUS_STATUS_YES});
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List getIssueListForCalAward(String lotteryCode) {
        StringBuilder sql = new StringBuilder("From MainIssue where 1=1");
        List<HibernatePara> hibernateParas = new ArrayList<HibernatePara>();
        sql.append(" and status=?");
        hibernateParas.add(new HibernatePara(Constants.ISSUE_STATUS_END));

        if (Utils.isNotEmpty(lotteryCode)) {
            sql.append(" and lotteryCode=?");
            hibernateParas.add(new HibernatePara(lotteryCode));
        }
        sql.append(" and (operatorsAward=? or operatorsAward=?)");
        hibernateParas.add(new HibernatePara(Constants.OPERATORS_AWARD_WAIT));
        hibernateParas.add(new HibernatePara(Constants.OPERATORS_AWARD_DOING));

        // sql.append(" and bonusNumber is not null and bonusClass is not null ");
        sql.append(" order by name asc");
        return findList(sql.toString(), hibernateParas);
    }

    @Override
    public MainIssue getIssueOfLastAward(String lotteryCode) {
        if (Utils.isEmpty(lotteryCode)) {
            return null;
        }
        StringBuilder sql = new StringBuilder("From MainIssue where 1=1");
        sql.append(" and lotteryCode=?");
        sql.append(" and status=?");
        sql.append(" and operatorsAward=? order by name desc ");
        List<MainIssue> list = find(sql.toString(), new Object[]{lotteryCode, Constants.ISSUE_STATUS_END, Constants.OPERATORS_AWARD_COMPLETE});
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public PageBean getIssueList(MainIssue issue, Integer page, Integer pageSize) {

        StringBuffer sql = new StringBuffer("From MainIssue issue where 1=1 ");
        List<HibernatePara> hibernateParas = new ArrayList<HibernatePara>();
        if (Utils.isNotEmpty(issue)) {
            if (Utils.isNotEmpty(issue.getStatus())) {
                sql.append(" and issue.status=?");
                hibernateParas.add(new HibernatePara(issue.getStatus()));
            }
            if (Utils.isNotEmpty(issue.getLotteryCode())) {
                sql.append(" and issue.lotteryCode=?");
                hibernateParas.add(new HibernatePara(issue.getLotteryCode()));
            }
            if (Utils.isNotEmpty(issue.getName())) {
                sql.append(" and issue.name=?");
                hibernateParas.add(new HibernatePara(issue.getName()));
            }
            if (Utils.isNotEmpty(issue.getId())) {
                sql.append(" and issue.id=?");
                hibernateParas.add(new HibernatePara(issue.getId()));
            }
        }
        sql.append(" order by issue.startTime desc ");
        return getPageBeanByPara(sql.toString(), hibernateParas, page, pageSize);
    }

    @Override
    public List getIssueListByEndTime(String statusTime, String endTime, String issueStatus) {
        List<Object> paramList = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select t.lottery_code lotteryCode from tms_main_issue t where 1=1 ");
        if (Utils.isNotEmpty(statusTime)) {
            sql.append(" and t.end_time>=?");
            paramList.add(DateUtils.formatStr2Date(statusTime));
        }
        if (Utils.isNotEmpty(endTime)) {
            sql.append(" and t.end_time<=?");
            paramList.add(DateUtils.formatStr2Date(endTime));
        }
        if (Utils.isNotEmpty(issueStatus)) {
            sql.append(" and t.status=?");
            paramList.add(issueStatus);
        }
        sql.append(" group by t.lottery_code");

        Integer paramSize = paramList.size();
        Object[] objArray = new Object[paramSize];

        for (int index = 0; index < paramSize; index++) {
            objArray[index] = paramList.get(index);
        }
        return jdbcTemplate.queryForList(sql.toString(), objArray);
    }

    @Override
    public PageBean getIssueList(MainIssue issue, Integer page, Integer pageSize, String order) {
        StringBuffer sql = new StringBuffer("From MainIssue where 1=1 and lotteryCode <> '200' and lotteryCode <> '201' ");
        List<HibernatePara> hibernateParas = new ArrayList<HibernatePara>();
        if (Utils.isNotEmpty(issue)) {
            if (Utils.isNotEmpty(issue.getLotteryCode())) {
                sql.append(" and lotteryCode=? ");
                hibernateParas.add(new HibernatePara(issue.getLotteryCode()));
            }
            if (Utils.isNotEmpty(issue.getStatus())) {
                sql.append(" and status=?");
                hibernateParas.add(new HibernatePara(issue.getStatus()));
            }
            if (Utils.isNotEmpty(issue.getStatuses())) {
                StringBuffer temp = new StringBuffer();
                for (Integer status : issue.getStatuses()) {
                    temp.append(",").append(status);
                }
                sql.append(" and status in (" + temp.substring(1) + ")");
            }
            if (Utils.isNotEmpty(issue.getIssueNameStart())) {
                sql.append(" and name>=?");
                hibernateParas.add(new HibernatePara(issue.getIssueNameStart()));
            }
            if (Utils.isNotEmpty(issue.getIssueNameEnd())) {
                sql.append(" and name<=?");
                hibernateParas.add(new HibernatePara(issue.getIssueNameEnd()));
            }
            if (Utils.isNotEmpty(issue.getStartTime())) {
                sql.append(" and startTime>=?");
                hibernateParas.add(new HibernatePara(issue.getStartTime()));
            }
            if (Utils.isNotEmpty(issue.getEndTime())) {
                sql.append(" and startTime<?");
                hibernateParas.add(new HibernatePara(issue.getEndTime()));
            }
            if (Utils.isNotEmpty(issue.getBonusStatus())) {
                sql.append(" and bonusStatus=?");
                hibernateParas.add(new HibernatePara(issue.getBonusStatus()));
            }
            if (Utils.isNotEmpty(issue.getOperatorsAward())) {
                sql.append(" and operatorsAward=?");
                hibernateParas.add(new HibernatePara(issue.getOperatorsAward()));
            }
        }
        sql.append(" order by startTime " + order + ",name " + order);
        PageBean pageBean = getPageBeanByPara(sql.toString(), hibernateParas, page, pageSize);
        return pageBean;
    }

    @Override
    public Boolean deleteAll(String[] ids) {
        try {
            StringBuffer sql = new StringBuffer("delete from tms_main_issue where id in(");
            for (String id : ids) {
                sql.append(id);
                sql.append(",");
            }
            String str = sql.toString();
            str = str.substring(0, str.length() - 1);
            int count = jdbcTemplate.update(str + ")");
            if (count > 0) {
                logger.info("删除了count=" + count);
                return true;
            }
            logger.info("没有删除成功");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List getLotteryTotalIssue(String lotteryCode, Integer status, String issueNameStart, String issueNameEnd, String startTime, String endTime) {
        StringBuffer sql = new StringBuffer(
                "select lottery_code as lotteryCode,sum(case when end_time <= SYSDATE then 1 else 0 end) as historyTotal,sum(case when end_time >= SYSDATE and status = 0 then 1 else 0 end) as nextTotal FROM tms_main_issue where 1=1 ");
        List<Object> paramList = new ArrayList<Object>();
        if (Utils.isNotEmpty(lotteryCode)) {
            sql.append(" and lottery_code=?");
            paramList.add(lotteryCode);
        }
        if (Utils.isNotEmpty(status)) {
            sql.append(" and status=?");
            paramList.add(status);
        }
        if (Utils.isNotEmpty(issueNameEnd)) {
            sql.append(" and name>=?");
            paramList.add(issueNameStart);
        }
        if (Utils.isNotEmpty(issueNameEnd)) {
            sql.append(" and name<=?");
            paramList.add(issueNameEnd);
        }
        if (Utils.isNotEmpty(startTime)) {
            sql.append(" and start_time>=?");
            paramList.add(DateUtils.formatStr2Date(startTime + " 00:00:00"));
        }
        if (Utils.isNotEmpty(endTime)) {
            sql.append(" and end_Time<?");
            Calendar calendar = Calendar.getInstance();
            Date endT = DateUtils.formatStr2Date(endTime + " 00:00:00");
            calendar.setTime(endT);
            calendar.add(Calendar.DATE, 1);
            paramList.add(calendar.getTime());
        }
        sql.append(" and lottery_code != '200' and lottery_code != '201' and lottery_code != '400' ");
        sql.append(" group by lottery_code order by lottery_code asc");
        Integer paramSize = paramList.size();
        Object[] objArray = new Object[paramSize];

        for (int index = 0; index < paramSize; index++) {
            objArray[index] = paramList.get(index);
        }
        return jdbcTemplate.queryForList(sql.toString(), objArray);
    }

    public List getIssueListByLotteryAndStatus(String lotteryCode, Integer status) {
        StringBuffer sql = new StringBuffer("select name from tms_main_issue where 1=1 ");
        if (Utils.isNotEmpty(lotteryCode)) {
            sql.append(" and lottery_code=?");
        }
        if (Utils.isNotEmpty(status)) {
            sql.append(" and status=?");
            if (status == 3) {
                sql.append(" and bonus_number = '-'");
            }
        }
        return jdbcTemplate.queryForList(sql.toString(), new Object[]{lotteryCode, status});
    }

    @Override
    public List<MainIssue> getOnTimeIssue(String lotteryCode) {
        if (Utils.isNotEmpty(lotteryCode)) {
            StringBuffer sql = new StringBuffer();
            sql.append("From MainIssue where 1=1 ");

            List<HibernatePara> hibernateParas = new ArrayList<HibernatePara>();

            sql.append(" and lotteryCode=?");
            hibernateParas.add(new HibernatePara(lotteryCode));

            Date date = new Date();
            sql.append(" and startTime<=?");
            hibernateParas.add(new HibernatePara(date));

            sql.append(" and endTime<=?");
            hibernateParas.add(new HibernatePara(date));

            List<MainIssue> issueList = findList(sql.toString(), hibernateParas);
            return issueList;
        }
        return null;
    }

    public List getStartIssueNameForEditFootball(String lotteryCode) {
        String sql = "select name from tms_main_issue tmi where tmi.lottery_code = ? and tmi.status = ? and tmi.send_status = ?  order by name asc";
        return jdbcTemplate.queryForList(sql, new Object[]{lotteryCode, Constants.ISSUE_STATUS_START, Constants.ISSUE_SALE_SEND});
    }

    public List getWaitIssueNameForEditFootBall(String lotteryCode) {
        String sql = "select name from tms_main_issue tmi where tmi.lottery_code = ? and tmi.status = ? and tmi.send_status = ? and tmi.start_time is not null order by name asc";
        return jdbcTemplate.queryForList(sql, new Object[]{lotteryCode, Constants.ISSUE_STATUS_START, Constants.ISSUE_SALE_WAIT});
    }

    @Override
    public List getPreIssueNameForEditFootBall(String lotteryCode) {
        String sql = "select name from tms_main_issue tmi where tmi.lottery_code = ? and tmi.status = ? and tmi.send_status = ? and tmi.start_time is not null order by name asc";
        return jdbcTemplate.queryForList(sql, new Object[]{lotteryCode, Constants.ISSUE_STATUS_WAIT, Constants.ISSUE_SALE_WAIT});
    }

    public List getNotIssueNameForEditFootBall(String lotteryCode) {
        String sql = "select name from tms_main_issue tmi where tmi.lottery_code = ?  and tmi.start_time is null order by name asc";
        return jdbcTemplate.queryForList(JdbcPageUtil.getOraclePageSQL(sql, 1, 3), new Object[]{lotteryCode});
    }

    @Override
    public MainIssue getStartIssueByLottery(String lotteryCode) {
        List<MainIssue> mainIssues = find("From MainIssue where lotteryCode=? and status=? order by name ", new Object[]{lotteryCode, Constants.ISSUE_STATUS_START});
        if (mainIssues.isEmpty()) {
            return null;
        }
        return mainIssues.get(0);
    }

    @Override
    public List<Map<String, Object>> getnoWinIssueList(int issueStatus, int bonusStatus) {
        StringBuffer sql = new StringBuffer();
        sql.append("select t.lottery_code,count(t.id) count from tms_main_issue t where 1=1 ");

        List<Object> paramList = new ArrayList<Object>();
        if (issueStatus > -1) {
            sql.append(" and t.status = ? ");
            paramList.add(issueStatus);
        }

        if (bonusStatus > -1) {
            sql.append(" and t.bonus_status = ? ");
            paramList.add(bonusStatus);
        }
        sql.append("group by t.lottery_code ");
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql.toString(), paramList.toArray());
        return resultList;
    }
}
