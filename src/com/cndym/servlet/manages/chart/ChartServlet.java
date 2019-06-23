package com.cndym.servlet.manages.chart;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cndym.bean.query.TicketQueryBean;
import com.cndym.bean.tms.MainIssue;
import com.cndym.bean.tms.Ticket;
import com.cndym.bean.user.Member;
import com.cndym.constant.Constants;
import com.cndym.lottery.lotteryInfo.bean.LotteryClass;
import com.cndym.service.IMemberService;
import com.cndym.service.chart.IChartService;
import com.cndym.service.chart.impl.IChartServiceImpl;
import com.cndym.servlet.ElTagUtils;
import com.cndym.servlet.manages.chart.bean.TicketsDetail;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import com.cndym.utils.hibernate.PageBean;
import com.google.gson.Gson;
/**
 * 作者：刘力超 
 * 时间：2014-6-7 上午10:06:19 
 * QQ：1147149597
 */
public class ChartServlet extends HttpServlet{
	private final String TICKETS_COUNT_5="ticketsCount_5";//实时监控，查询现在时间点5分钟的票量
	private final String TICKETS_COUNT_20="ticketsCount_20";//实时监控，查询初始化的20个点
	private final String TICKETS_CLASSIFY_BY_TYPE="ticketsClassifyByType";//查询各种彩票占总票量的百分比
	private final String MEMBER_LIST="memberList";//查询商户
	private final String LOTTERY_LIST="lotteryList";//查询彩种
	private final String TICKETS_MUN_30_DAYS="ticketsNum30Days";//查询商户票量30天
	private final String TICKETS_FOR_30_DAYS_DETAIL="ticketsFor30daysDetail";//查询商户票量30天详细
	private final String TICKET_FOR_30_DAYS_BY_CODE="ticketFor30DaysByCode";//查询商户票量30天详细,按彩种查询
	
	private IChartService iChartService=new IChartServiceImpl();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doPost(request, response);
    }
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		IMemberService memberService = (IMemberService) SpringUtils.getBean("memberServiceImpl");
		String action=request.getParameter("action");
		response.setContentType("text/html");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        
        if(LOTTERY_LIST.equals(action)){
        	String lotteryCode=Utils.formatStr(request.getParameter("lotteryCode"));
        	PageBean pageBean = new PageBean();
        	List<MainIssue> list=new ArrayList<MainIssue>();
        	pageBean.setPageContent(list);
        	if(Utils.isNotEmpty(lotteryCode)){
        		MainIssue mainIssue=new MainIssue();
        		mainIssue.setLotteryCode(lotteryCode);
        		pageBean.getPageContent().add(mainIssue);
        	}else {
        		Map<String, LotteryClass> lotteryMap=ElTagUtils.findAllLotteryCode();
				for(String key : lotteryMap.keySet()){
					MainIssue mainIssue=new MainIssue();
					mainIssue.setLotteryCode(key);
					pageBean.getPageContent().add(mainIssue);
				}
			}
            request.setAttribute("pageBean", pageBean);
            request.getRequestDispatcher("/chart/ticketFor30DaysByCode.jsp").forward(request, response);
        }
        
        if(TICKET_FOR_30_DAYS_BY_CODE.equals(action)){
        	List<Map<String, Object>> list=iChartService.getTicketCountFor30ByCode();
        	Map<String, List<Double>> countMap = null;
        	List<Double> countList = null;
        	if(Utils.isNotEmptyObject(list)){
        		countMap = new HashMap<String, List<Double>>();
        		for(Map<String, Object> map :list){
        			String str="code"+(String)map.get("LOTTERY_CODE");
        			BigDecimal big=(BigDecimal) map.get("AMOUNT");
            		Double amount=big.doubleValue();
            		
            		if(countMap.get(str) == null){
            			countList = new ArrayList<Double>();
            			countList.add(amount);
            			countMap.put(str, countList);
            		}else {
            			countMap.get(str).add(amount);
    				}
        		}
        	  Gson gson=new Gson();
      		  String json=gson.toJson(countMap);
      		  try {
      			  response.getWriter().write(json);
      		  } catch (IOException e) {
      			  e.printStackTrace();
      		  }
        	}
        	
        }
        
        if(TICKETS_FOR_30_DAYS_DETAIL.equals(action)){
        	String sid=Utils.formatStr(request.getParameter("sid"));
        	List<Map<String, Object>> list=iChartService.getTicketCountFor30Detail(sid);
        	Map<String, List<Integer>> countMap = null;
        	List<Integer> countList = null;
        	if(Utils.isNotEmptyObject(list)){
        		countMap = new HashMap<String, List<Integer>>();
        		for(Map<String, Object> map :list){
        			String str=(String)map.get("LOTTERY_CODE");
        			BigDecimal big=(BigDecimal) map.get("COUNT");
            		Integer count=big.intValue();
            		
            		if(countMap.get(str) == null){
            			countList = new ArrayList<Integer>();
            			countList.add(count);
            			countMap.put(str, countList);
            		}else {
            			countMap.get(str).add(count);
    				}
        		}
        	}
        	if(Utils.isNotEmpty(countMap)){
        		List<TicketsDetail> ticketsDetailList=new ArrayList<TicketsDetail>();
        		  for (String key : countMap.keySet()) {
        			   List<Integer> ticketsAmountList= countMap.get(key);
        			   TicketsDetail ticketsDetail=new TicketsDetail();
        			   ticketsDetail.setName(LotteryCodeDeacMap.getCodeDescMsg(key));
        			   ticketsDetail.setData(ticketsAmountList.toArray());
        			   ticketsDetailList.add(ticketsDetail);
        		  }
        		Gson gson=new Gson();
				try {
					response.getWriter().write(gson.toJson(ticketsDetailList));
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        	
        }
		
        if(TICKETS_MUN_30_DAYS.equals(action)){
        	List<Map<String, Object>> list=iChartService.getTicketCountFor30();
    		Map<String, List<Double>> countMap = null;
        	List<Double> countList = null;
        	if(Utils.isNotEmptyObject(list)){
        		countMap = new HashMap<String, List<Double>>();
        		for(Map<String, Object> map :list){
        			String str="code"+(String)map.get("SID");
        			BigDecimal big=(BigDecimal) map.get("AMOUNT");
            		Double amount=big.doubleValue();
            		
            		if(countMap.get(str) == null){
            			countList = new ArrayList<Double>();
            			countList.add(amount);
            			countMap.put(str, countList);
            		}else {
            			countMap.get(str).add(amount);
    				}
        		}
        	  Gson gson=new Gson();
      		  String json=gson.toJson(countMap);
      		  try {
      			  response.getWriter().write(json);
      		  } catch (IOException e) {
      			  e.printStackTrace();
      		  }
        	}
        }
		if(MEMBER_LIST.equals(action)){
			Integer page = Utils.formatInt(request.getParameter("page"), 1);
			Integer pageSize = Utils.formatInt(request.getParameter("pageSize"), Constants.PAGE_SIZE_50);
			request.setAttribute("page", page);
			request.setAttribute("pageSize", pageSize);
			
			String sid = Utils.formatStr(request.getParameter("sid"));
            String name = Utils.formatStr(request.getParameter("name"));
            String status = Utils.formatStr(request.getParameter("status"));

            Member member = new Member();
            if (Utils.isNotEmpty(sid)) {
                member.setSid(sid);
                request.setAttribute("sid", sid);
            }
            if (Utils.isNotEmpty(name)) {
                member.setName(name);
                request.setAttribute("name", name);
            }
            if (Utils.isNotEmpty(status)) {
                member.setStatus(new Integer(status));
                request.setAttribute("status", status);
            }
            PageBean pageBean = memberService.getPageBeanByPara(member, page, pageSize);
            request.setAttribute("pageBean", pageBean);
            request.getRequestDispatcher("/chart/ticketFor30Days.jsp").forward(request, response);
		}
		if(TICKETS_COUNT_20.equals(action)){
			List<Map<String, Object>> list=iChartService.getTicketCountBefore20();
			Gson gson=new Gson();
			String json=gson.toJson(list);
			try {
				response.getWriter().write(json);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(TICKETS_COUNT_5.equals(action)){
			List<Map<String, Object>> list=iChartService.getTicketCountBefore5Min();
			Gson gson=new Gson();
			String json=gson.toJson(list);
			try {
				response.getWriter().write(json);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(TICKETS_CLASSIFY_BY_TYPE.equals(action)){
			SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
			
			String sid=Utils.formatStr(request.getParameter("sid"));
			Integer ticketStatus=Utils.formatInt(request.getParameter("ticketStatus"), null);
			String postCode=Utils.formatStr(request.getParameter("postCode"));
			String createStartTime=Utils.formatStr(request.getParameter("createStartTime"));
			String createEndTime=Utils.formatStr(request.getParameter("createEndTime"));
			String sendStartTime=Utils.formatStr(request.getParameter("sendStartTime"));
			String sendEndTime=Utils.formatStr(request.getParameter("sendEndTime"));
			String bonusStartTime=Utils.formatStr(request.getParameter("bonusStartTime"));
			String bonusEndTime=Utils.formatStr(request.getParameter("bonusEndTime"));
			String returnStartTime=Utils.formatStr(request.getParameter("returnStartTime"));
			String returnEndTime=Utils.formatStr(request.getParameter("returnEndTime"));
			Integer bonusStatus= Utils.formatInt(request.getParameter("bonusStatus"), null);
			Integer bigBonus = Utils.formatInt(request.getParameter("bigBonus"), null);
			
			// 彩期
            Integer issueStatus = Utils.formatInt(request.getParameter("issueStatus"), null);
            // 彩期是否返奖
            Integer issueBonusStatus = Utils.formatInt(request.getParameter("issueBonusStatus"), null);
            // 彩期算奖状态
            Integer operatorsAward = Utils.formatInt(request.getParameter("operatorsAward"), null);
            
			TicketQueryBean queryBean=new TicketQueryBean();
			Ticket ticket =new Ticket();
			if (Utils.isNotEmpty(sid)) {
			      ticket.setSid(sid);
			}
			if (Utils.isNotEmpty(ticketStatus)) {
				ticket.setTicketStatus(ticketStatus);
			}
			if (Utils.isNotEmpty(postCode)) {
				ticket.setPostCode(postCode);
			}
			if (Utils.isNotEmpty(bonusStatus)) {
				ticket.setBonusStatus(bonusStatus);
			}
			if (Utils.isNotEmpty(bigBonus)) {
				ticket.setBigBonus(bigBonus);
			}
			queryBean.setTicket(ticket);
			if (Utils.isNotEmpty(issueStatus)) {
				queryBean.setIssueStatus(issueStatus);
			}
			if (Utils.isNotEmpty(issueBonusStatus)) {
				queryBean.setIssueBonusStatus(issueBonusStatus);
			}
			if (Utils.isNotEmpty(operatorsAward)) {
				queryBean.setOperatorsAward(operatorsAward);
			}

            if (Utils.isNotEmpty(createStartTime)) {
            	Date date=null;
            	try {
					date=simpleDateFormat.parse(createStartTime);
				} catch (ParseException e) {
					e.printStackTrace();
				}
            	queryBean.setCreateStartTime(date);
            } 
            if (Utils.isNotEmpty(createEndTime)) {
            	Date date=null;
            	try {
					date=simpleDateFormat.parse(createEndTime);
				} catch (ParseException e) {
					e.printStackTrace();
				}
            	queryBean.setCreateEndTime(date);
            }
            if (Utils.isNotEmpty(sendStartTime)) {
            	Date date=null;
            	try {
					date=simpleDateFormat.parse(sendStartTime);
				} catch (ParseException e) {
					e.printStackTrace();
				}
            	queryBean.setSendStartTime(date);
            } 
            if (Utils.isNotEmpty(sendEndTime)) {
            	Date date=null;
            	try {
					date=simpleDateFormat.parse(sendEndTime);
				} catch (ParseException e) {
					e.printStackTrace();
				}
            	queryBean.setSendEndTime(date);
            } 
            if (Utils.isNotEmpty(bonusStartTime)) {
            	Date date=null;
            	try {
					date=simpleDateFormat.parse(bonusStartTime);
				} catch (ParseException e) {
					e.printStackTrace();
				}
            	queryBean.setBonusStartTime(date);
            } 
            if (Utils.isNotEmpty(bonusEndTime)) {
            	Date date=null;
            	try {
					date=simpleDateFormat.parse(bonusEndTime);
				} catch (ParseException e) {
					e.printStackTrace();
				}
            	queryBean.setBonusEndTime(date);
            } 
            if (Utils.isNotEmpty(returnStartTime)) {
            	Date date=null;
            	try {
					date=simpleDateFormat.parse(returnStartTime);
				} catch (ParseException e) {
					e.printStackTrace();
				}
            	queryBean.setReturnStartTime(date);
            } 
            if (Utils.isNotEmpty(returnEndTime)) {
            	Date date=null;
            	try {
					date=simpleDateFormat.parse(returnEndTime);
				} catch (ParseException e) {
					e.printStackTrace();
				}
            	queryBean.setReturnEndTime(date);
            } 

			
			List<Map<String, Object>> list=iChartService.getTicketPercent(queryBean);
			
			for (int i = 0; i < list.size(); i++) {
				list.get(i).put("CODE", LotteryCodeDeacMap.getCodeDescMsg((String)list.get(i).get("CODE")));
			}
			
			Gson gson=new Gson();
			String json=gson.toJson(list);
			try {
				response.getWriter().write(json);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			
	}
	
}
