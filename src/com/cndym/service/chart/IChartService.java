package com.cndym.service.chart;

import java.util.List;
import java.util.Map;

import com.cndym.bean.query.TicketQueryBean;

/**
 * 作者：刘力超 
 * 时间：2014-6-7 下午4:01:40 
 * QQ：1147149597
 */
public interface IChartService {
	
	/**
	 * 票量实时监控，查询前20个点的票量,每个点0.5分钟
	 */
	List<Map<String, Object>> getTicketCountBefore20();
	
	/**
	 *查询现在时间点前5分钟的票量
	 */
	List<Map<String, Object>> getTicketCountBefore5Min();
	
	/**
	 *查询各种彩票占总票量的百分比
	 */
	List<Map<String, Object>> getTicketPercent(TicketQueryBean queryBean);
	
	/**
	 * 查询商户30天的票量
	 */
	List<Map<String, Object>> getTicketCountFor30();
	
	/**
	 * 查询商户30天的票量详细,具体一天的详细
	 */
	List<Map<String, Object>> getTicketCountFor30Detail(String sid);
	
	/**
	 * 查询商户30天的票量详细,按彩种查询
	 */
	List<Map<String, Object>> getTicketCountFor30ByCode();
}
