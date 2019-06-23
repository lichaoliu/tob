package com.cndym.service.chart.impl;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cndym.bean.query.TicketQueryBean;
import com.cndym.bean.tms.Ticket;
import com.cndym.dao.chart.IChartDao;
import com.cndym.dao.chart.impl.IChartDaoImpl;
import com.cndym.service.chart.IChartService;
import com.cndym.servlet.manages.chart.LotteryCodeDeacMap;
import com.cndym.servlet.manages.chart.bean.TicketsDetail;
import com.cndym.utils.Utils;
import com.google.gson.Gson;
import com.sun.org.apache.bcel.internal.generic.GOTO;
/**
 * 作者：刘力超 
 * 时间：2014-6-7 下午4:01:17 
 * QQ：1147149597
 */
public class IChartServiceImpl implements IChartService {
	private IChartDao iChartDao=new IChartDaoImpl();
	
	//票量实时监控，查询前20个点的票量,每个点0.5分钟
	public List<Map<String, Object>> getTicketCountBefore20() {
		Date date =new Date();
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
		String nowString=dateFormat.format(date);
		
		StringBuffer sql=new StringBuffer("select ta.rnum \"TIME\",nvl(tb.cnt,0) \"COUNT\" from ");
		sql.append("(select rownum rnum from dual connect by rownum<21) ta, ");
		sql.append("(select m5,count(ticket_id) cnt from ");
		sql.append("(select ticket_id,trunc((to_date('") ;
		sql.append(nowString);
		sql.append("','yyyymmddhh24miss') - cast(create_time as date))*24*120)+1 m5 ");
		sql.append("from tms_ticket where create_time<=to_date('");
		sql.append(nowString);
		sql.append("','yyyymmddhh24miss') and create_time>=to_date ('");
		sql.append(nowString);
		sql.append("','yyyymmddhh24miss')-1/144) ");
		sql.append("group by m5 order by m5) tb ");
		sql.append("where ta.rnum = tb.m5(+) ");
		sql.append("order by ta.rnum ");
		return iChartDao.queryList(sql.toString());
	}
	
	//查询现在时间点前5分钟的票量
	public List<Map<String, Object>> getTicketCountBefore5Min(){
		String sql="select 'shijian' time,count(*) count from tms_ticket where create_time>=sysdate - 1/2880";
		return iChartDao.queryList(sql);
	}
	
	//查询各种彩票占总票量的百分比
	public List<Map<String, Object>> getTicketPercent(TicketQueryBean queryBean) {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer sql=new StringBuffer("select lottery_code code,count(ticket_id) percent from ");
		sql.append("(select ticket_id,t.lottery_code from tms_ticket t,tms_main_issue tmi where  t.lottery_code = tmi.lottery_code and t.issue = tmi.name and 1=1  ");
		if (Utils.isNotEmpty(queryBean)) {
			Ticket ticket = queryBean.getTicket();
			if(Utils.isNotEmpty(ticket)){
				if(Utils.isNotEmpty(ticket.getSid())){
					sql.append(" and t.sid = ? ");
					paramList.add(ticket.getSid());
				}
				if(Utils.isNotEmpty(ticket.getTicketStatus())){
					sql.append(" and t.ticket_status = ? ");
					paramList.add(ticket.getTicketStatus());
				}
				if(Utils.isNotEmpty(ticket.getPostCode())){
					sql.append(" and t.post_code = ? ");
					paramList.add(ticket.getPostCode());
				}
				if (Utils.isNotEmpty(ticket.getBonusStatus())) {
	                sql.append(" and t.bonus_status = ? ");
	                paramList.add(ticket.getBonusStatus());
	            }
				if (Utils.isNotEmpty(ticket.getBigBonus())) {
                    sql.append(" and t.big_bonus = ? ");
                    paramList.add(ticket.getBigBonus());
                }
			}
			if (Utils.isNotEmpty(queryBean.getCreateStartTime())) {
                sql.append(" and t.create_time >= ? ");
                paramList.add(queryBean.getCreateStartTime());
            }
            if (Utils.isNotEmpty(queryBean.getCreateEndTime())) {
                sql.append(" and t.create_time < ? ");
                paramList.add(queryBean.getCreateEndTime());
            }
            if (Utils.isNotEmpty(queryBean.getSendStartTime())) {
                sql.append(" and t.send_time >= ? ");
                paramList.add(queryBean.getSendStartTime());
            }
            if (Utils.isNotEmpty(queryBean.getSendEndTime())) {
                sql.append(" and t.send_time < ? ");
                paramList.add(queryBean.getSendEndTime());
            }
            if (Utils.isNotEmpty(queryBean.getReturnStartTime())) {
                sql.append(" and t.return_time >= ? ");
                paramList.add(queryBean.getReturnStartTime());
            }
            if (Utils.isNotEmpty(queryBean.getReturnEndTime())) {
                sql.append(" and t.return_time < ? ");
                paramList.add(queryBean.getReturnEndTime());
            }
            if (Utils.isNotEmpty(queryBean.getBonusStartTime())) {
                sql.append(" and t.bonus_time >= ? ");
                paramList.add(queryBean.getBonusStartTime());
            }
            if (Utils.isNotEmpty(queryBean.getBonusEndTime())) {
                sql.append(" and t.bonus_time < ? ");
                paramList.add(queryBean.getBonusEndTime());
            }
            if (Utils.isNotEmpty(queryBean.getIssueStatus())) {
                sql.append(" and tmi.status = ? ");
                paramList.add(queryBean.getIssueStatus());
            }
            if (Utils.isNotEmpty(queryBean.getIssueBonusStatus())) {
                sql.append(" and tmi.bonus_status = ? ");
                paramList.add(queryBean.getIssueBonusStatus());
            }
            
            if (Utils.isNotEmpty(queryBean.getOperatorsAward())) {
                sql.append(" and tmi.operators_award = ? ");
                paramList.add(queryBean.getOperatorsAward());
            }
		}
		
		sql.append(")group by lottery_code");
		return iChartDao.queryList(sql.toString(),paramList.toArray());
	}

	/**
	 * 查询商户30天的票量
	 */
	public List<Map<String, Object>> getTicketCountFor30() {
		StringBuffer sql=new StringBuffer("select sidday.day, sidday.sid, nvl(t.amount, 0) amount from (select sid, day from (select trunc(sysdate) - rownum day from dual connect by level <= 30),(select distinct sid from COUNT_CHART_TICKET ");
		sql.append(" where count_date >= trunc(sysdate) - 30 and count_date < trunc(sysdate))) sidday,(select sid, trunc(count_date) day, sum(amount) amount from COUNT_CHART_TICKET ");
		sql.append(" where count_date >= trunc(sysdate) - 30 and count_date < trunc(sysdate) group by sid, trunc(count_date)) t where sidday.sid = t.sid(+) and sidday.day = t.day(+) order by day, sid ");
		return iChartDao.queryList(sql.toString());
	}
	
	public static void main(String[] args) {
		/**
		IChartServiceImpl i=new IChartServiceImpl();
		List<Map<String, Object>> list=i.getTicketCountFor30();
		Map<String, List<Double>> countMap = null;//new HashMap<String, List<String>>();
    	List<Double> countList = null;
    	if(Utils.isNotEmptyObject(list)){
    		countMap = new HashMap<String, List<Double>>();
    		for(Map<String, Object> map :list){
    			String str=(String)map.get("SID");
    			BigDecimal big=(BigDecimal) map.get("AMOUNT");
        		Double amount=big.doubleValue();
        		System.out.println(str);
        		System.out.println(LotteryCodeDeacMap.getCodeDescMsg(str));
        		if(countMap.get(str) == null){
        			countList = new ArrayList<Double>();
        			countList.add(amount);
        			countMap.put(LotteryCodeDeacMap.getCodeDescMsg(str), countList);
        		}else {
        			countMap.get(LotteryCodeDeacMap.getCodeDescMsg(str)).add(amount);
				}
    		}
    	}
    	Gson gson=new Gson();
    	System.out.println(gson.toJson(countMap));
    	if(Utils.isNotEmpty(countMap)){
    		List<TicketsDetail> ticketsDetailList=new ArrayList<TicketsDetail>();
    		  for (String key : countMap.keySet()) {
    			   List<Double> ticketsAmountList= countMap.get(key);
    			   TicketsDetail ticketsDetail=new TicketsDetail();
    			   ticketsDetail.setName(LotteryCodeDeacMap.getCodeDescMsg(key));
    			   ticketsDetail.setData(ticketsAmountList.toArray());
    			   ticketsDetailList.add(ticketsDetail);
    		  }
    		  System.out.println(ticketsDetailList.size());
    		  System.out.println(gson.toJson(ticketsDetailList));
    	}*/
    	IChartServiceImpl i=new IChartServiceImpl();
    	List<Map<String, Object>> list=i.getTicketCountFor30ByCode();
    	Gson gson=new Gson();
    	System.out.println(gson.toJson(list));
	}

	/**
	 * 查询商户30天的票量详细
	 */
	public List<Map<String, Object>> getTicketCountFor30Detail(String sid) {
		StringBuffer sql=new StringBuffer("select lcday.lottery_code, lcday.day, nvl(t.amount, 0) count from (select lottery_code, day from (select trunc(sysdate) - rownum day ");
		sql.append("from dual connect by level <= 30),(select distinct lottery_code from COUNT_CHART_TICKET where count_date >= trunc(sysdate) - 30 ");
		sql.append("and count_date < trunc(sysdate) and sid = '");
		sql.append(sid);
		sql.append("')) lcday,(select lottery_code, trunc(count_date) day, sum(amount) amount from COUNT_CHART_TICKET ");
		sql.append("where count_date >= trunc(sysdate) - 30 and count_date < trunc(sysdate) and sid = '");
		sql.append(sid);
		sql.append("' group by lottery_code, trunc(count_date)) t ");
		sql.append("where lcday.lottery_code = t.lottery_code(+) and lcday.day = t.day(+) order by day");
		return iChartDao.queryList(sql.toString());
	}

	public List<Map<String, Object>> getTicketCountFor30ByCode() {
		StringBuffer sql=new StringBuffer("select lcday.lottery_code, lcday.day, nvl(t.amount, 0) amount from (select lottery_code,day from (select trunc(sysdate) - rownum day from dual connect by level <= 30),(select distinct lottery_code from COUNT_CHART_TICKET ");
		sql.append(" where count_date >= trunc(sysdate) - 30 and count_date < trunc(sysdate))) lcday,(select lottery_code, trunc(count_date) day, sum(amount) amount from COUNT_CHART_TICKET ");
		sql.append(" where count_date >= trunc(sysdate) - 30 and count_date < trunc(sysdate) group by lottery_code, trunc(count_date)) t where lcday.lottery_code = t.lottery_code(+) and lcday.day = t.day(+) order by lottery_code,day");
		return iChartDao.queryList(sql.toString());
	}
	

}
