package com.cndym.sendClient.ltkj.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.cndym.bean.tms.Ticket;
import com.cndym.cache.XMemcachedClientWrapper;
import com.cndym.constant.Constants;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import com.cndym.sendClient.BaseSendClient;
import com.cndym.sendClient.ISendClientOperator;
import com.cndym.sendClient.ltkj.LtkjSendClientConfig;
import com.cndym.sendClient.ltkj.bean.TraceInfo;
import com.cndym.sendClient.ltkj.util.ErrorCodeMap;
import com.cndym.sendClient.ltkj.util.LtkjSendClientUtil;
import com.cndym.sendClient.ltkj.util.ToSendCode;
import com.cndym.sendClient.ltkj.util.BonusNumberFormat;
import com.cndym.service.ITicketService;
import com.cndym.utils.DateUtils;
import com.cndym.utils.Md5;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import com.cndym.utils.xml.parse.ByteCodeUtil;
import com.cndym.utils.xml.parse.XmlBean;

@Component
public class LtkjSendClientImpl extends BaseSendClient {
	
	@Resource(name = "postOrderOperator") 
	private ISendClientOperator postOrderOperator;
	@Resource(name = "postOrderAhOperator")
	private ISendClientOperator postOrderAhOperator;
	@Resource(name = "postBonusOperator")
    private ISendClientOperator postBonusOperator;
    @Resource(name = "postBonusInfoOperator")
    private ISendClientOperator postBonusInfoOperator;
    @Resource(name = "postIssueOperator")
    private ISendClientOperator postIssueOperator;
    
	@Resource
	private ITicketService ticketService;
    
	private static Logger logger = Logger.getLogger(LtkjSendClientImpl.class);

	private static String URL = LtkjSendClientConfig.getValue("URL");
	private static String GP_URL = LtkjSendClientConfig.getValue("GP_URL");
	private static String MKEY = LtkjSendClientConfig.getValue("MKEY");
	private static String MERCHANT_ID = LtkjSendClientConfig.getValue("MERCHANT_ID");
	private static String POST_CODE = LtkjSendClientConfig.getValue("POST_CODE");
	private static String GP_LOTTERY_CODE = LtkjSendClientConfig.getValue("POST_CODE_GP_LOTTERY");

	@Override
    public XmlBean issueQuery(XmlBean xmlBean) {
		try {
			String lotteryCode = xmlBean.attribute("lotteryCode");
			String lotteryType = ToSendCode.postToSys(lotteryCode);
			String mac =  Md5.Md5(MERCHANT_ID + lotteryType + MKEY);
			printInfoLog("ltkj  response：" + "lotteryCode:"+lotteryCode+"  lotteryType:"+lotteryType+"  url:"+getUrl(lotteryCode));
			String resStr = LtkjSendClientUtil.invokeWebservice(getUrl(lotteryCode),"FetchIssueData",new Object[]{MERCHANT_ID,lotteryType,mac});
			printInfoLog("ltkj issueQuery response：" + resStr);
			if(Utils.isNotEmpty(resStr)){
				String preErrCode = resStr.substring(resStr.indexOf("|") + 1, resStr.indexOf("|") + 5);
				if(ErrorCodeMap.RETURN_SUCCESS.equals(preErrCode)){
					String resXml = resStr.substring(resStr.indexOf("<"), resStr.lastIndexOf(">") + 1);
					// 返回处理
	                XmlBean responseBean = LtkjSendClientUtil.getResponse(resXml);
	                List<XmlBean> issueBeans = responseBean.getAll("Issue");
	                
	                if(Utils.isNotEmpty(issueBeans)){
	                	
	                	int len = issueBeans.size();
	                	for(int i = len - 1; i >= 0; i --){
			                 XmlBean issueBean = issueBeans.get(i);
			                 Date nowDate = new Date();
			                 Date startTime = Utils.formatDate(issueBean.getFirst("StartDateTime").attribute("text"), "yyyy-MM-dd HH:mm:ss");
			                 Date endTime = Utils.formatDate(issueBean.getFirst("EndDateTime").attribute("text"), "yyyy-MM-dd HH:mm:ss");
			                 if(nowDate.getTime() >= startTime.getTime() && nowDate.getTime() <= endTime.getTime()){
			                	 StringBuffer xml = new StringBuffer("<body>");
			                	 String issueNum = issueBean.getFirst("IssueNum").attribute("text");
			 	                 String startTimeStr = Utils.formatDate2Str(startTime, "yyyy-MM-dd HH:mm:ss");
			                     String endTimeStr = Utils.formatDate2Str(endTime, "yyyy-MM-dd HH:mm:ss");
			                     xml.append("<issueInfo lotteryCode=\"").append(lotteryCode);
			                     String issue=LtkjSendClientUtil.ltkjToUsIssueFormat(lotteryCode, issueNum);
			                     logger.info("ltkj insert to db issue----------->"+"lotteryCode:"+lotteryCode+"   issueNum:"+issueNum+" issue:"+issue);
			                     xml.append("\" issue=\"").append(issue);
			                     xml.append("\" startTime=\"").append(startTimeStr);
			                     xml.append("\" endTime=\"").append(endTimeStr);
			                     xml.append("\" postCode=\"").append(POST_CODE);
			                     xml.append("\" status=\"").append(0).append("\"/>");
			                     xml.append("</body>");
				                 logger.info("ltkj insert to db issue：" + xml.toString());
				                 postIssueOperator.execute(ByteCodeUtil.xmlToObject(xml.toString()));
				                 break;
			                 }
		                }
	                   
	                } 
	               
				}
			}
		} catch (Exception e) {
			 printErrorLog("ltkj issueQuery error:", e);
		}
        return null;
    }
	
	/**
	 * 投注
	 */
	@Override
	public XmlBean sendOrder(List<Ticket> tickets) {
		
		if (Utils.isNotEmpty(tickets)) {
			String resStr = null;
			
			for(Ticket ticket : tickets){
				try {
					String bet_Numbers = LtkjSendClientUtil.bulidSendOrder(ticket);
					printInfoLog("ltkj order send request bet_Numbers:" + bet_Numbers);
					String lotteryCode = ticket.getLotteryCode();
					String lotteryType = "";
					if("200".equals(lotteryCode) || "201".equals(lotteryCode)){//竞彩
						lotteryType = ToSendCode.playToPost(ticket.getLotteryCode(), ticket.getPlayCode());
					}else{//数字彩
						lotteryType = ToSendCode.postToSys(lotteryCode);
					}
					 
					//多票投注
					Integer ticketcount = 1;
					Integer totalAmount = Integer.parseInt(new java.text.DecimalFormat("0").format(ticket.getAmount())) * 100;
					String issueNo =LtkjSendClientUtil.usToLtkjIssueFormat(lotteryCode, ticket.getIssue());
					logger.info("   ltk  sendOrder:"+issueNo);
					String mac =  Md5.Md5(MERCHANT_ID + lotteryType + issueNo +ticketcount + totalAmount + bet_Numbers + MKEY);
					resStr = LtkjSendClientUtil.invokeWebservice(getUrl(lotteryCode),"addMTicket",new Object[]{MERCHANT_ID,lotteryType,issueNo,ticketcount,totalAmount,bet_Numbers,mac});
				}catch (Exception e) {
					printErrorLog("ltkj sendOrder: ", e);
		            if (e instanceof CndymException) {
		                if (ErrCode.E0005.equals(e.getMessage())) {
		                    orderOperator(ticket, Constants.TICKET_STATUS_WAIT, "-1", "网络连接异常，重投");
		                } else {
		                    orderOperator(ticket, Constants.TICKET_STATUS_RESEND, "-1", "异常，投注失败");
		                }
		            } else {
		                orderOperator(ticket, Constants.TICKET_STATUS_RESEND, "-1", "异常，投注失败");
		            }
				}
				
				if (Utils.isNotEmpty(resStr)) {
					try {
						printInfoLog("ltkj sendOrder response xml：" + resStr);
						String preErrCode = resStr.substring(resStr.indexOf("|") + 1, resStr.indexOf("|") + 5);
						if(ErrorCodeMap.PARAM_ERROR.equals(preErrCode) || ErrorCodeMap.TIME_OUT_ERROR.equals(preErrCode) ||
								ErrorCodeMap.BETS_ERROR.equals(preErrCode) || ErrorCodeMap.AMOUNT_LIMIT_ERROR.equals(preErrCode)
								|| ErrorCodeMap.AMOUNT_ERROR.equals(preErrCode) || ErrorCodeMap.ISSUE_ERROR.equals(preErrCode)
								|| ErrorCodeMap.ISSUEEND_ERROR.equals(preErrCode) || ErrorCodeMap.TICKETCOUNT_ERROR.equals(preErrCode)){
							printInfoLog("order send errCode:" + preErrCode + " errMsg:" + ErrorCodeMap.getErrorCodeMsg(preErrCode));
							orderOperator(ticket, Constants.TICKET_STATUS_FAILURE, preErrCode, ErrorCodeMap.getErrorCodeMsg(preErrCode));
							continue;
						}else if(ErrorCodeMap.MD5_ERROR.equals(preErrCode) || ErrorCodeMap.ID_ERROR.equals(preErrCode)
								||  ErrorCodeMap.LTSYS_ERROR.equals(preErrCode) 
								|| ErrorCodeMap.BALANCE_ERROR.equals(preErrCode)){
							printInfoLog("order send errCode:" + preErrCode + " errMsg:" + ErrorCodeMap.getErrorCodeMsg(preErrCode));
							orderOperator(ticket, Constants.TICKET_STATUS_RESEND, preErrCode, ErrorCodeMap.getErrorCodeMsg(preErrCode));
							continue;
						}
						String xml = resStr.substring(resStr.indexOf("<"), resStr.lastIndexOf(">") + 1);
						Element ticketElement = LtkjSendClientUtil.getOrderResponse(xml);
						String errCode = ticketElement.elementTextTrim("ErrCode");
						if(!ErrorCodeMap.RETURN_SUCCESS.equals(errCode) && !ErrorCodeMap.TICKET_ID_ERROR.equals(errCode)){//如果投注失败
							int tickeStatus = Constants.TICKET_STATUS_FAILURE;
							if(ErrorCodeMap.NODATA_ERROR.equals(errCode)){
								tickeStatus = Constants.TICKET_STATUS_RESEND;
							}
							printInfoLog("order send errCode:" + errCode + " errMsg:" + ErrorCodeMap.getErrorCodeMsg(errCode));
							orderOperator(ticket, tickeStatus, errCode, ErrorCodeMap.getErrorCodeMsg(errCode));
						}
					}catch (Exception e) {
						printErrorLog("parse sendOrder response", e);
						orderOperator(ticket, Constants.TICKET_STATUS_RESEND, "-1", "异常，投注失败");
					}
				}else {
					orderOperator(ticket, Constants.TICKET_STATUS_WAIT, "-1", "链接超时，重投");
				}
			}
		}		
			
		return null;
	}
	
	/**
	 * 出票状态查询
	 */
	@Override
	public XmlBean orderQuery(List<Ticket> tickets) {
		if (Utils.isNotEmpty(tickets)) {
			String lotteryCode = tickets.get(0).getLotteryCode();
			List<TraceInfo> traceInfoList = LtkjSendClientUtil.buildQueryOrder(tickets);
			for(TraceInfo traceInfo : traceInfoList){
				String resStr = null;
				try {
					Integer ticketCount = traceInfo.getTicketCount();
					String traceCodes = traceInfo.getTraceCodes();
					printInfoLog("order query request msg:" + traceCodes);
					String mac =  Md5.Md5(MERCHANT_ID + ticketCount + traceCodes + MKEY);
					resStr = LtkjSendClientUtil.invokeWebservice(getUrl(lotteryCode),"CheckTicket",new Object[]{MERCHANT_ID,ticketCount,traceCodes,mac});
				} catch (Exception e) {
					printErrorLog("WebService Post", e);
				}
				
				if (Utils.isNotEmpty(resStr)) {
					printInfoLog("order query response xml:" + resStr);
					String preErrCode = resStr.substring(resStr.indexOf("|") + 1, resStr.indexOf("|") + 5);
					if(ErrorCodeMap.BETS_ERROR.equals(preErrCode) || ErrorCodeMap.LTSYS_ERROR.equals(preErrCode) 
							|| ErrorCodeMap.MD5_ERROR.equals(preErrCode) || ErrorCodeMap.ID_ERROR.equals(preErrCode)){
						printInfoLog("order query errCode:" + preErrCode + " errMsg:" + ErrorCodeMap.getErrorCodeMsg(preErrCode));
						continue;
					}
					String resXml = resStr.substring(resStr.indexOf("<"), resStr.lastIndexOf(">") + 1);
					List<Element> ticketElements = LtkjSendClientUtil.getQueryResponse(resXml);
					StringBuffer xml = new StringBuffer("<tickets>");
					for(Element element : ticketElements){
						String ticketId = element.elementTextTrim("TranceCode");
						Ticket ticket = ticketService.getTicketByTicketId(ticketId);
						if (ticket.getTicketStatus() != Constants.TICKET_STATUS_SENDING) {
							continue;
						}
						
						lotteryCode = ticket.getLotteryCode();
						String printTag = element.elementTextTrim("PrintTag");
						String anylottoCode = element.elementTextTrim("AnylottoCode");
						String spValue  = element.elementTextTrim("Spvalue");
						String saleCode  = element.elementTextTrim("Ticketid");
						int status = Constants.TICKET_STATUS_SENDING;
						String errCode = "";
						String errMsg = "";
						String odds = "";
						if("0".equals(printTag) || "1".equals(printTag) || "10".equals(printTag)){
							continue;
						}
						if("2".equals(printTag) || "3".equals(printTag) || "4".equals(printTag)){
							if("200".equals(lotteryCode) || "201".equals(lotteryCode)){
								if(Utils.isNotEmpty(spValue)){
									status = Constants.TICKET_STATUS_SUCCESS;
									errCode = "0";
									odds = LtkjSendClientUtil.covertOddsToNumber(spValue, ticket);
									errMsg = "出票成功";
								}
							}else {
								status = Constants.TICKET_STATUS_SUCCESS;
								errCode = "0";
								errMsg = "出票成功";
							}
							
						}else if("-1".equals(printTag) || "-2".equals(printTag)){
							status = Constants.TICKET_STATUS_FAILURE;
							errCode = "-1";
							errMsg = "出票失败";
						}else {
							continue;
						}
						xml.append("<ticket ticketId=\"").append(ticketId);
						xml.append("\" status=\"").append(status);
						xml.append("\" errCode=\"").append(errCode);
						xml.append("\" errMsg=\"").append(errMsg);
						xml.append("\" saleCode=\"").append(saleCode);
						xml.append("\" saleInfo=\"").append(odds);
						xml.append("\" messageId=\"").append(anylottoCode);
						xml.append("\"/>");
					}
					xml.append("</tickets>");
					printInfoLog("order query result xml:" + xml.toString());
					postOrderAhOperator.execute(ByteCodeUtil.xmlToObject(xml.toString()));
				}
			}
		}
		return null;
	}
	
	@Override
	public XmlBean bonusInfoQuery(XmlBean xmlBean) {
		
		try {
            String lotteryCode = xmlBean.attribute("lotteryCode");
            String issue = xmlBean.attribute("issue");
            String lotteryType = ToSendCode.postToSys(lotteryCode);
			String mac =  Md5.Md5(MERCHANT_ID + lotteryType + issue + MKEY);
			String resStr = LtkjSendClientUtil.invokeWebservice(getUrl(lotteryCode),"FetchAwardData",new Object[]{MERCHANT_ID,lotteryType,issue,mac});
            printInfoLog("ltkj bonusInfoQuery response lotteryCode:"+lotteryCode+",issue:"+issue+": " + resStr);
            if (Utils.isNotEmpty(resStr)) {
            	String preErrCode = resStr.substring(resStr.indexOf("|") + 1, resStr.indexOf("|") + 5);
    			if(ErrorCodeMap.BETS_ERROR.equals(preErrCode) || ErrorCodeMap.LTSYS_ERROR.equals(preErrCode) 
    					|| ErrorCodeMap.MD5_ERROR.equals(preErrCode) || ErrorCodeMap.ID_ERROR.equals(preErrCode)){
    				printInfoLog("bonusInfo query errCode:" + preErrCode + " errMsg:" + ErrorCodeMap.getErrorCodeMsg(preErrCode));
    				return null;
    			}
    			if(ErrorCodeMap.RETURN_SUCCESS.equals(preErrCode)){
    				String resXml = resStr.substring(resStr.indexOf("<"), resStr.lastIndexOf(">") + 1);
					// 返回处理
	                XmlBean responseBean = LtkjSendClientUtil.getResponse(resXml);
                    String bonusNumber = responseBean.getFirst("AwardIndex").getFirst("LuckNum").attribute("text");
                    StringBuffer xml = new StringBuffer("<body>");
                    xml.append("<bonusInfo lotteryCode=\"").append(lotteryCode);
                    xml.append("\" issue=\"").append(issue);
                    xml.append("\" bonusNumber=\"").append(BonusNumberFormat.format(lotteryCode, bonusNumber));
                    xml.append("\" saleTotal=\"").append("");
                    xml.append("\" prizePool=\"").append("");
                    xml.append("\" plussalevalue=\"").append("");
                    xml.append("\" bonusClass=\"").append("").append("\"/>");
                    xml.append("</body>");
                    logger.info("ltkj bonusInfo bonusNumber：" + xml.toString());
                    postBonusInfoOperator.execute(ByteCodeUtil.xmlToObject(xml.toString()));
    			}
            }
        } catch (Exception e) {
            printErrorLog("ltkj bonusInfoQuery:", e);
        }
        return null;
	}
	
	@Override
    public XmlBean bonusQuery(XmlBean xmlBean) {
        try {
            String lotteryCode = xmlBean.attribute("lotteryCode");
            String issue = xmlBean.attribute("issue");
            String lotteryType = ToSendCode.postToSys(lotteryCode);
			String mac =  Md5.Md5(MERCHANT_ID + lotteryType + issue + MKEY);
			String resStr = LtkjSendClientUtil.invokeWebservice(getUrl(lotteryCode),"FetchIssueAwardTicket",new Object[]{MERCHANT_ID,lotteryType,issue,mac});
            printInfoLog("ltkj bonusQuery response lotteryCode:"+lotteryCode+",issue:"+issue+": " + resStr);
            if (Utils.isNotEmpty(resStr)) {
            	String preErrCode = resStr.substring(resStr.indexOf("|") + 1, resStr.indexOf("|") + 5);
				if(ErrorCodeMap.RETURN_SUCCESS.equals(preErrCode)){
					String resXml = resStr.substring(resStr.indexOf("<"), resStr.lastIndexOf(">") + 1);
					XmlBean responseBean = LtkjSendClientUtil.getResponse(resXml);
					String status = responseBean.getFirst("Issue").getFirst("Status").attribute("text");
					if("3".equals(status)){
						StringBuffer xml = new StringBuffer("<bonus lotteryCode=\"").append(lotteryCode);
	                    xml.append("\" issue=\"").append(issue);
	                    xml.append("\" postCode=\"").append(POST_CODE).append("\">");
	                    List<XmlBean> bonusDetailList = responseBean.getFirst("Tickets").getAll("Ticket");
	                    if (Utils.isNotEmpty(bonusDetailList)) {
	                        for (XmlBean bonusDetail : bonusDetailList) {
	                            String ticketId = bonusDetail.getFirst("TranceCode").attribute("text");
	                            Double money = Utils.formatDouble(bonusDetail.getFirst("TaxAwardBets").attribute("text"), 0d) / 100;
	                            xml.append("<ticket ticketId=\"").append(ticketId);
	                            xml.append("\" bonusAmount=\"").append(money);
	                            xml.append("\" fixBonusAmount=\"").append(money);
	                            xml.append("\" bonusClass=\"").append("").append("\"/>");
	                        }
	                    }
	                    xml.append("</bonus>");
	                    postBonusOperator.execute(ByteCodeUtil.xmlToObject(xml.toString()));
					}
                }
            }
        } catch (Exception e) {
            logger.error("ltkj bonusQuery:", e);
        }
        return null;
    }

	
	/**
	 * 余额查询
	 */
	@Override
    public double accountQuery() {
		double account = 0d;
		String accountStr = null;
		try {
			String startTime = DateUtils.formatDate2Str(new Date(), "yyyyMMddHHmmss");
			String endTime = DateUtils.formatDate2Str(new Date(), "yyyyMMddHHmmss");
//			String startTime = "0";
//			String endTime = "0";
			//printInfoLog("account query request msg:" + traceCodes);
			String mac =  Md5.Md5(MERCHANT_ID + startTime + endTime + MKEY);
			accountStr = LtkjSendClientUtil.invokeWebservice(URL,"QueryAccount",new Object[]{MERCHANT_ID,startTime,endTime,mac});
			
		} catch (Exception e) {
			printErrorLog("account query WebService Post", e);
		}
		if (Utils.isNotEmpty(accountStr)) {
			printInfoLog("account query response xml:" + accountStr);
			String preErrCode = accountStr.substring(accountStr.indexOf("|") + 1, accountStr.indexOf("|") + 5);
			if(ErrorCodeMap.BETS_ERROR.equals(preErrCode) || ErrorCodeMap.LTSYS_ERROR.equals(preErrCode) 
					|| ErrorCodeMap.MD5_ERROR.equals(preErrCode) || ErrorCodeMap.ID_ERROR.equals(preErrCode)){
				printInfoLog("order query errCode:" + preErrCode + " errMsg:" + ErrorCodeMap.getErrorCodeMsg(preErrCode));
				return account;
			}
			String resXml = accountStr.substring(accountStr.indexOf("<"), accountStr.lastIndexOf(">") + 1);
			Element rootElement = LtkjSendClientUtil.getAccountResponse(resXml);
			String balance = rootElement.elementText("Balance");
			account = Utils.formatDouble(balance,0d);
		}
		
		return account;
	}
	
	@Override
    public double accountQuery(String type) {
		double account = 0d;
		String accountStr = null;
		try {
			String startTime = DateUtils.formatDate2Str(new Date(), "yyyyMMddHHmmss");
			String endTime = DateUtils.formatDate2Str(new Date(), "yyyyMMddHHmmss");
//			String startTime = "0";
//			String endTime = "0";
			//printInfoLog("account query request msg:" + traceCodes);
			String mac =  Md5.Md5(MERCHANT_ID + startTime + endTime + MKEY);
			accountStr = LtkjSendClientUtil.invokeWebservice(GP_URL,"QueryAccount",new Object[]{MERCHANT_ID,startTime,endTime,mac});
			
		} catch (Exception e) {
			printErrorLog("account query WebService Post", e);
		}
		if (Utils.isNotEmpty(accountStr)) {
			printInfoLog("account query response xml:" + accountStr);
			String preErrCode = accountStr.substring(accountStr.indexOf("|") + 1, accountStr.indexOf("|") + 5);
			if(ErrorCodeMap.BETS_ERROR.equals(preErrCode) || ErrorCodeMap.LTSYS_ERROR.equals(preErrCode) 
					|| ErrorCodeMap.MD5_ERROR.equals(preErrCode) || ErrorCodeMap.ID_ERROR.equals(preErrCode)){
				printInfoLog("order query errCode:" + preErrCode + " errMsg:" + ErrorCodeMap.getErrorCodeMsg(preErrCode));
				return account;
			}
			String resXml = accountStr.substring(accountStr.indexOf("<"), accountStr.lastIndexOf(">") + 1);
			Element rootElement = LtkjSendClientUtil.getAccountResponse(resXml);
			String balance = rootElement.elementText("Balance");
			account = Utils.formatDouble(balance,0d);
		}
		
		return account;
    }
	
	public static void printInfoLog (String log) {
		logger.info(log);
	}
	
	public static void printErrorLog (String log, Throwable t) {
		logger.error(log, t);
	}
	
	private void orderOperator(Ticket ticket, int ticketStatus, String errorCode, String errMsg) {
		try {
			if (Constants.TICKET_STATUS_RESEND == ticketStatus) {
				XMemcachedClientWrapper xmcw = (XMemcachedClientWrapper) SpringUtils.getBean("xmemcachedClientWrapper");
				Long dendline = Utils.formatLong((Long)xmcw.get("RESENDDENDLINE" + ticket.getLotteryCode() + ticket.getIssue()), 0);
				if (new Date().getTime() > dendline && dendline > 0) {
					ticketStatus = Constants.TICKET_STATUS_FAILURE;
				}
			}
		} catch (Exception e) {
			printErrorLog("Memcache Exception", e);
		}

		StringBuffer ticketXml = new StringBuffer("<tickets>");
		ticketXml.append("<ticket ticketId=\"");
		ticketXml.append(ticket.getTicketId());
		ticketXml.append("\" status=\"");
		ticketXml.append(ticketStatus);
		ticketXml.append("\" errCode=\"");
		ticketXml.append(errorCode);
		ticketXml.append("\" errMsg=\"");
		ticketXml.append(errMsg);
		ticketXml.append("\" />");
		ticketXml.append("</tickets>");
		postOrderOperator.execute(ByteCodeUtil.xmlToObject(ticketXml.toString()));
	}
	
	/**
	 * 根据彩种获取URL
	 * @param lotteryCode
	 * @return
	 */
	private String getUrl(String lotteryCode) {
		if(GP_LOTTERY_CODE.contains(lotteryCode)){
			return GP_URL;
		}
		return URL;
	}
	
	public static void main(String args[]) {
		LtkjSendClientImpl ltkjSendClient = new LtkjSendClientImpl();
		List<Ticket> tickets = new ArrayList<Ticket>();
		Ticket ticket = new Ticket();
		ticket.setTicketId("3091e91031kl483rrhte2");
		ticket.setNumberInfo("0*1*2*3*1*2*3*1;1*1*3*3*1*2*3*1");
		ticket.setIssue("15028");
		ticket.setMultiple(1);
		ticket.setItem(2);
		ticket.setLotteryCode("302");
		ticket.setPlayCode("01");
		ticket.setPollCode("01");
		ticket.setAmount(4d);
		tickets.add(ticket);
		
		
		ticket = new Ticket();
		ticket.setTicketId("3063015061yrloy1r16r581");
		ticket.setNumberInfo("0*01*0123*3*1*2*3*1");
		ticket.setIssue("15028");
		ticket.setMultiple(1);
		ticket.setItem(8);
		ticket.setLotteryCode("302");
		ticket.setPlayCode("01");
		ticket.setPollCode("02");
		ticket.setAmount(16d);
		//tickets.add(ticket);
		
		ticket = new Ticket();
		ticket.setTicketId("30630309606c4153y4ut12");
		ticket.setNumberInfo("01,02,06,09");
		ticket.setIssue("14091044");
		ticket.setMultiple(1);
		ticket.setItem(4);
		ticket.setLotteryCode("103");
		ticket.setPlayCode("12");
		ticket.setPollCode("02");
		ticket.setAmount(8d);
		//tickets.add(ticket);
		
		ticket = new Ticket();
		ticket.setTicketId("3030810300j186t2me6");
		ticket.setNumberInfo("01,02@08,05,09");
		ticket.setIssue("14091044");
		ticket.setMultiple(1);
		ticket.setItem(3);
		ticket.setLotteryCode("103");
		ticket.setPlayCode("12");
		ticket.setPollCode("03");
		ticket.setAmount(6d);
		//tickets.add(ticket);
		
		//ltkjSendClient.sendOrder(tickets);
		ltkjSendClient.issueQuery(ByteCodeUtil.xmlToObject("<body lotteryCode=\"109\"/>"));
		//ltkjSendClient.orderQuery(tickets);
		//ltkjSendClient.bonusInfoQuery(ByteCodeUtil.xmlToObject("<body lotteryCode=\"103\" issue=\"14092352\"/>"));
		//ltkjSendClient.bonusQuery(ByteCodeUtil.xmlToObject("<body lotteryCode=\"103\" issue=\"14092352\"/>"));
		//double account = ltkjSendClient.accountQuery();
		//logger.info("account:" + account);
	}
}
