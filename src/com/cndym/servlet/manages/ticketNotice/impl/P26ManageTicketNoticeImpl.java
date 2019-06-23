/**
 * 
 */
package com.cndym.servlet.manages.ticketNotice.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;

import com.cndym.bean.tms.Ticket;
import com.cndym.constant.Constants;
import com.cndym.sendClient.ISendClientOperator;
import com.cndym.sendClient.ltkj.LtkjSendClientConfig;
import com.cndym.sendClient.ltkj.util.LtkjSendClientUtil;
import com.cndym.service.ITicketService;
import com.cndym.servlet.manages.ticketNotice.IManageTicketNotice;
import com.cndym.utils.Md5;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import com.cndym.utils.xml.parse.ByteCodeUtil;

/**
 * @author 朱林虎
 *
 */
@Component
public class P26ManageTicketNoticeImpl implements IManageTicketNotice {
	
	private static Logger logger = Logger.getLogger(P26ManageTicketNoticeImpl.class);
	private static String MKEY = LtkjSendClientConfig.getValue("MKEY");
	private static String MERCHANT_ID = LtkjSendClientConfig.getValue("MERCHANT_ID");
	private static String POST_CODE = LtkjSendClientConfig.getValue("POST_CODE");
	
	@Override
	public String manageTicketNotice(HttpServletRequest request) {
		
		String json = request.getParameter("param");
		if(Utils.isEmpty(json)){
			logger.info("乐透科技传入的出票通知json为空，无法处理");
			return "1111";
		}
		logger.info("乐透科技传入的出票通知json为：" + json);
		String returnResult = "0000";
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree(json);
			//String jsonType = rootNode.path("JsonType").getTextValue();
			JsonNode dataList = rootNode.path("DataList");
			ISendClientOperator postOrderOperator = (ISendClientOperator) SpringUtils.getBean("postOrderOperator");
			ITicketService ticketService = (ITicketService) SpringUtils.getBean("ticketServiceImpl");
			StringBuffer xml = new StringBuffer("<tickets>");
			logger.error("此次通知的总票数是：" + dataList.size());
			for(int i = 0; i < dataList.size(); i ++){
				JsonNode data = dataList.get(i);
				String transcode = data.path("Transcode").getTextValue();
				Ticket ticket = ticketService.getTicketByTicketId(transcode);
				
				if(ticket == null){
					logger.error("票号[" + transcode + "]找不到对应的票信息！");
					continue;
				}
				
				if(!POST_CODE.equals(ticket.getPostCode())){
					logger.error("票号[" + transcode + "]不是在出票口["+POST_CODE+"]出的票！");
					continue;
				}
				
				if (ticket.getTicketStatus() != Constants.TICKET_STATUS_SENDING) {
					continue;
				}
				
				String lotteryCode = ticket.getLotteryCode();
				String ticketTrans = data.path("ticketTrans").getTextValue();
				String mac = data.path("Mac").getTextValue();
				String merchant_id = data.path("Merchant_id").getTextValue();
				String printTag = data.path("Printstatus").getTextValue();
				JsonNode odds = data.path("Odds");
				String odd = odds.toString();
				
				//String datatype = data.path("datatype").getTextValue();
				//String ticketingDate = odds.path("ticketingDate").getTextValue();
				JsonNode bet = odds.path("bet");
				//校验商户号
				if(!MERCHANT_ID.equals(merchant_id)){
					logger.error("错误的商户号[" + merchant_id + "]");
					returnResult = "0002";
					break;
				}
				//校验mac
				String localMac = Md5.Md5(merchant_id + transcode + printTag + odd + MKEY);
				if(!localMac.equals(mac)){
					logger.error("错误的MAC[" + mac + "]，当前MAC是[" + localMac + "]");
					returnResult = "0001";
					break;
				}
				
				String errCode = "";
				String errMsg = "";
				String bets = "";
				
				int status = Constants.TICKET_STATUS_SENDING;
				if("4".equals(printTag)){
					if("200".equals(lotteryCode) || "201".equals(lotteryCode)){
						if(Utils.isNotEmpty(odd)){
							status = Constants.TICKET_STATUS_SUCCESS;
							errCode = "0";
							bets = LtkjSendClientUtil.covertNoticeOddsToNumber(bet, ticket);
							logger.error("赔率[" + bet.toString() + "]转换后的赔率是[" + bets + "]");
							errMsg = "出票成功";
						}
					}else {
						status = Constants.TICKET_STATUS_SUCCESS;
						errCode = "0";
						errMsg = "出票成功";
					}
					
				}else if("3".equals(printTag)){
					status = Constants.TICKET_STATUS_FAILURE;
					errCode = "-1";
					errMsg = "出票失败";
				}else {
					continue;
				}
				xml.append("<ticket ticketId=\"").append(transcode);
				xml.append("\" status=\"").append(status);
				xml.append("\" errCode=\"").append(errCode);
				xml.append("\" errMsg=\"").append(errMsg);
				xml.append("\" saleCode=\"").append("");
				xml.append("\" saleInfo=\"").append(bets);
				xml.append("\" messageId=\"").append(ticketTrans);
				xml.append("\"/>");
			}
			
			xml.append("</tickets>");
			logger.info("乐透出票通知结果 xml:" + xml.toString());
			postOrderOperator.execute(ByteCodeUtil.xmlToObject(xml.toString()));
			
		} catch (JsonProcessingException je) {
			je.printStackTrace();
			logger.error("json解析错误:" + je.getMessage());
			returnResult = "0003";
		}catch (Exception ie) {
			ie.printStackTrace();
			logger.error("乐透出票通知转换赔率及其他异常:" + ie.getMessage());
			returnResult = "0003";
		}
		logger.info("returnResult:" + returnResult);
		return returnResult;
	}

}
