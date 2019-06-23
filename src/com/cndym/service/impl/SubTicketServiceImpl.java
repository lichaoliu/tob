package com.cndym.service.impl;

import com.cndym.bean.tms.SubTicket;
import com.cndym.bean.tms.Ticket;
import com.cndym.bean.user.AccountOperatorTemp;
import com.cndym.constant.Constants;
import com.cndym.dao.IAccountOperatorTempDao;
import com.cndym.dao.ISubTicketDao;
import com.cndym.dao.ITicketDao;
import com.cndym.lottery.lotteryInfo.LotteryList;
import com.cndym.lottery.lotteryInfo.bean.LotteryBean;
import com.cndym.sendClient.ISplitTicket;
import com.cndym.service.IAccountService;
import com.cndym.service.ISubTicketService;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import com.cndym.utils.hibernate.PageBean;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: mcs Date: 12-12-4 Time: 下午4:11
 */
@Service
public class SubTicketServiceImpl extends GenericServiceImpl<SubTicket, ISubTicketDao> implements ISubTicketService {

	private static Logger logger = Logger.getLogger(SubTicketServiceImpl.class);

	@Autowired
	private ISubTicketDao subTicketDao;

	@Autowired
	private ITicketDao ticketDao;

	@Autowired
	private IAccountService accountService;
	
	@Autowired
	private IAccountOperatorTempDao accountOperatorTempDao;

	@PostConstruct
	public void initDao() {
		super.setDaoImpl(subTicketDao);
	}

	@Override
	public List<SubTicket> doSaveSubTicket(Ticket temp) {
		try {
			Ticket ticket = ticketDao.getTicketByTicketIdForUpdate(temp.getTicketId());
			if (!Utils.isNotEmpty(ticket)) {
				logger.info("票:" + ticket.getTicketId() + "不存在");
				return null;
			}
			if (ticket.getTicketStatus() != Constants.TICKET_STATUS_SENDING) {
				logger.info("票" + ticket.getTicketId() + "状态(" + ticket.getTicketStatus() + ")不为(2)");
				return null;
			}
			String[] numberArray = null;
			String lotteryCode = ticket.getLotteryCode();
			String pollCode = ticket.getPollCode();
			int item = ticket.getItem();
			if (!"200,201,400".contains(lotteryCode) && pollCode.equals("01")) {
				item = 1;
			}
			if ("201,200,400".contains(ticket.getLotteryCode())) {
				numberArray = new String[] { ticket.getNumberInfo() };
			} else {
				numberArray = ticket.getNumberInfo().split(";");
			}
			int index = 0;
			String lotteryCodeStr = new StringBuffer(lotteryCode).append(ticket.getPlayCode()).append(ticket.getPollCode()).toString();
			// 校验号码格式
			LotteryBean lotteryBean = LotteryList.getLotteryBean(lotteryCodeStr);
			double amount = lotteryBean.getLotteryPlay().getEachamount() * item * ticket.getMultiple();
			List<SubTicket> subTicketList = new ArrayList<SubTicket>();
			for (String number : numberArray) {
				SubTicket subTicket = new SubTicket();
				subTicket.setTicketId(ticket.getTicketId());
				subTicket.setSubTicketId(ticket.getTicketId() + index);
				subTicket.setLotteryCode(ticket.getLotteryCode());
				subTicket.setIssue(ticket.getIssue());
				subTicket.setPlayCode(ticket.getPlayCode());
				subTicket.setPollCode(ticket.getPollCode());
				subTicket.setItem(item);
				subTicket.setMultiple(ticket.getMultiple());
				subTicket.setAmount(amount);
				subTicket.setNumberInfo(number);
				subTicket.setPostCode(ticket.getPostCode());
				subTicket.setTicketStatus(ticket.getTicketStatus());
				subTicket.setBonusStatus(ticket.getBonusStatus());
				subTicket.setBonusAmount(ticket.getBonusAmount());
				subTicket.setFixBonusAmount(ticket.getFixBonusAmount());
				subTicket.setBigBonus(ticket.getBigBonus());
				subTicket.setCreateTime(new Date());
				subTicket.setSendTime(new Date());
				subTicketList.add(subTicket);
				subTicketDao.save(subTicket);
				index++;
			}
			ticket.setSumTicket(index);
			ticket.setSuccessTicket(0);
			ticket.setFailureTicket(0);
			ticket.setSuccessAmount(0d);
			ticket.setFailureAmount(0d);
			ticketDao.update(ticket);
			return subTicketList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<SubTicket> doSaveSubTicketEx(Ticket temp, String postCode) {
		try {
			Ticket ticket = ticketDao.getTicketByTicketIdForUpdate(temp.getTicketId());
			if (!Utils.isNotEmpty(ticket)) {
				logger.info("票:" + ticket.getTicketId() + "不存在");
				return null;
			}
			if (ticket.getTicketStatus() != Constants.TICKET_STATUS_SENDING) {
				logger.info("票" + ticket.getTicketId() + "状态(" + ticket.getTicketStatus() + ")不为(2)");
				return null;
			}

			List<SubTicket> subTicketList = new ArrayList<SubTicket>();

			List<SubTicket> existSubTicketList = subTicketDao.findSubTicketListEx(temp.getTicketId());
			if (Utils.isEmpty(existSubTicketList)) {
				ISplitTicket splitTicket = (ISplitTicket) SpringUtils.getBean("split" + postCode);
				subTicketList = splitTicket.splitTicket(ticket);

				subTicketDao.saveAllObject(subTicketList);

				ticket.setSumTicket(subTicketList.size());
				ticket.setSuccessTicket(0);
				ticket.setFailureTicket(0);
				ticket.setSuccessAmount(0d);
				ticket.setFailureAmount(0d);
				ticketDao.update(ticket);
			} else {
				for (SubTicket subTicket : existSubTicketList) {
					int status = subTicket.getTicketStatus();
					if (status == Constants.TICKET_STATUS_SENDING) {
						subTicketList.add(subTicket);
					} else if (status == Constants.TICKET_STATUS_FAILURE) {
						logger.info("票" + subTicket.getTicketId() + "状态(" + subTicket.getTicketStatus() + ")不失败");
						return null;
					}
				}
			}

			return subTicketList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<SubTicket> doSaveSubTicket(Ticket temp, String postCode) {
		try {
			Ticket ticket = ticketDao.getTicketByTicketIdForUpdate(temp.getTicketId());
			if (!Utils.isNotEmpty(ticket)) {
				logger.info("票:" + ticket.getTicketId() + "不存在");
				return null;
			}
			if (ticket.getTicketStatus() != Constants.TICKET_STATUS_SENDING) {
				logger.info("票" + ticket.getTicketId() + "状态(" + ticket.getTicketStatus() + ")不为(2)");
				return null;
			}

			List<SubTicket> subTicketList = new ArrayList<SubTicket>();

			List<SubTicket> existSubTicketList = subTicketDao.findSubTicketListEx(temp.getTicketId());
			if (Utils.isEmpty(existSubTicketList)) {
				ISplitTicket splitTicket = (ISplitTicket) SpringUtils.getBean("split" + postCode);
				subTicketList = splitTicket.splitTicket(ticket);
				subTicketDao.saveAllObject(subTicketList);
				ticket.setSumTicket(subTicketList.size());
				ticket.setSuccessTicket(0);
				ticket.setFailureTicket(0);
				ticket.setSuccessAmount(0d);
				ticket.setFailureAmount(0d);
				String messageId = temp.getBackup1();
				if (Utils.isEmpty(messageId)) {
					messageId = ticket.getBackup1();
				}
				ticket.setBackup1(messageId);
				ticketDao.update(ticket);
			} else {
				for (SubTicket subTicket : existSubTicketList) {
					int status = subTicket.getTicketStatus();
					if (status == Constants.TICKET_STATUS_SENDING) {
						subTicketList.add(subTicket);
					} else if (status == Constants.TICKET_STATUS_FAILURE) {
						logger.info("票" + subTicket.getTicketId() + "状态(" + subTicket.getTicketStatus() + ")不失败");
						return null;
					}
				}
			}

			return subTicketList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<SubTicket> findSubTicketList(String ticketId) {
		return subTicketDao.findSubTicketList(ticketId);
	}

	@Override
	public List<SubTicket> findSubTicketListEx(String ticketId) {
		return subTicketDao.findSubTicketListEx(ticketId);
	}

	@Override
	public int doUpdateSubTicketForSended(SubTicket temp) {
		try {
			SubTicket subTicket = subTicketDao.getSubTicketForUpdate(temp.getSubTicketId());
			if (null == subTicket) {
				return 0;
			}
			if (subTicket.getTicketStatus() != Constants.TICKET_STATUS_SENDING) {
				logger.error("当前票状态(" + subTicket.getSubTicketId() + ")不为2");
				return 0;
			}

			subTicket.setTicketStatus(temp.getTicketStatus());
			subTicket.setSaleInfo(temp.getSaleInfo());

			Ticket ticket = ticketDao.getTicketByTicketIdForUpdate(subTicket.getTicketId());
			if (null == ticket) {
				return 0;
			}
			if (ticket.getTicketStatus() != Constants.TICKET_STATUS_SENDING) {
				logger.error("当前票状态(" + ticket.getTicketId() + ")不为2");
				return 0;
			}

			int sumTicket = ticket.getSumTicket();
			int successTicket = ticket.getSuccessTicket();
			int failureTicket = ticket.getFailureTicket();

			if (subTicket.getTicketStatus() == Constants.TICKET_STATUS_SUCCESS) {
				ticket.setSuccessTicket(successTicket + 1);
				ticket.setSuccessAmount(ticket.getSuccessAmount() + subTicket.getAmount());

				String saleCode = temp.getSaleCode();
				if (Utils.isNotEmpty(saleCode)) {
					String saleCodeOld = ticket.getSaleCode();
					if (Utils.isNotEmpty(saleCodeOld)) {
						if (saleCodeOld.indexOf(saleCode) == -1) {
							saleCodeOld = saleCodeOld + "," + saleCode;
						}
					} else {
						saleCodeOld = saleCode;
					}
					ticket.setSaleCode(saleCodeOld);
					subTicket.setSaleCode(saleCode);
				}
			}

			if (subTicket.getTicketStatus() == Constants.TICKET_STATUS_FAILURE) {
				ticket.setFailureTicket(failureTicket + 1);
				ticket.setFailureAmount(ticket.getFailureAmount() + subTicket.getAmount());
			}

			// 全部成功
			if (sumTicket == ticket.getSuccessTicket().intValue()) {
				ticket.setTicketStatus(Constants.TICKET_STATUS_SUCCESS);
			} else if (sumTicket == ticket.getFailureTicket().intValue()) {
				ticket.setTicketStatus(Constants.TICKET_STATUS_FAILURE);
			} else if (sumTicket == ticket.getFailureTicket().intValue() + ticket.getSuccessTicket().intValue()) {
				// 部分成交当失败处理
				ticket.setTicketStatus(Constants.TICKET_STATUS_FAILURE);
			}

			// 出票口票状态返加控制为success和failure不成功即失败
			if (ticket.getTicketStatus() == Constants.TICKET_STATUS_FAILURE) {// 退款处理
				logger.info("票(" + ticket.getTicketId() + ")出票失败，进行退款处理，金额(" + ticket.getAmount() + ")");
				
				AccountOperatorTemp accountOperatorTemp = new AccountOperatorTemp();
                accountOperatorTemp.setOrderId(ticket.getOrderId());
                accountOperatorTemp.setTicketId(ticket.getTicketId());
                accountOperatorTemp.setSid(ticket.getSid());
                accountOperatorTemp.setResources(Constants.OPERATOR_FROM_SYS);
                accountOperatorTemp.setUserCode(ticket.getUserCode());
                accountOperatorTemp.setAmount(ticket.getAmount());
                accountOperatorTemp.setEventCode(Constants.R00100);
                accountOperatorTemp.setStatus(Constants.ACCOUNT_OPERATOR_TEMP_STATUS_WAIT);
                accountOperatorTemp.setCreateTime(new Date());
                accountOperatorTemp.setAcceptTime(accountOperatorTemp.getCreateTime());
                accountOperatorTempDao.save(accountOperatorTemp);
				
				//accountService.doAccount(ticket.getUserCode(), Constants.R00100, ticket.getAmount(), ticket.getOrderId(), Constants.P10000, ticket.getTicketId(), ticket.getSid());
				ticket.setReturnTime(new Date());
			} else if (ticket.getTicketStatus() == Constants.TICKET_STATUS_SUCCESS) {
				ticket.setReturnTime(new Date());
			}
			ticket.setSaleInfo(subTicket.getSaleInfo());
			ticket.setAcceptTime(new Date());

			subTicket.setAcceptTime(new Date());
			subTicket.setReturnTime(new Date());
			subTicketDao.update(subTicket);
			ticketDao.update(ticket);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public int doUpdateSubTicketForSended(List<SubTicket> subList) {
		Map<String, String> messageIdMap = new HashMap<String, String>();
		Map<String, String> saleCodeMap = new HashMap<String, String>();
		Map<String, Integer> successMap = new HashMap<String, Integer>();
		Map<String, Double> successAmountMap = new HashMap<String, Double>();
		Map<String, Integer> failedMap = new HashMap<String, Integer>();
		Map<String, Double> failedAmountMap = new HashMap<String, Double>();
		for (SubTicket temp : subList) {
			SubTicket subTicket = subTicketDao.getSubTicketForUpdate(temp.getSubTicketId());
			if (null == subTicket) {
				continue;
			}
			if (subTicket.getTicketStatus() != Constants.TICKET_STATUS_SENDING) {
				logger.error("当前票状态(" + subTicket.getSubTicketId() + "|" + subTicket.getTicketStatus() + ")不为2");
				continue;
			}
			String ticketId = subTicket.getTicketId();

			subTicket.setTicketStatus(temp.getTicketStatus());
			subTicket.setSaleInfo(temp.getSaleInfo());

			if (subTicket.getTicketStatus() == Constants.TICKET_STATUS_SUCCESS) {
				String saleCode = temp.getSaleCode();
				if (Utils.isNotEmpty(saleCode)) {
					subTicket.setSaleCode(saleCode);

					String oldSaleCode = saleCodeMap.get(ticketId);
					if (Utils.isNotEmpty(oldSaleCode)) {
						oldSaleCode = oldSaleCode + "," + saleCode;
					} else {
						oldSaleCode = saleCode;
					}

					saleCodeMap.put(ticketId, oldSaleCode);
				}

				Integer os = successMap.get(ticketId);
				if (os == null) {
					successMap.put(ticketId, 1);
				} else {
					successMap.put(ticketId, os + 1);
				}

				Double osa = successAmountMap.get(ticketId);
				if (osa == null) {
					successAmountMap.put(ticketId, subTicket.getAmount());
				} else {
					successAmountMap.put(ticketId, osa + subTicket.getAmount());
				}
			} else if (subTicket.getTicketStatus() == Constants.TICKET_STATUS_FAILURE) {
				Integer of = failedMap.get(ticketId);
				if (of == null) {
					failedMap.put(ticketId, 1);
				} else {
					failedMap.put(ticketId, of + 1);
				}

				Double ofa = failedAmountMap.get(ticketId);
				if (ofa == null) {
					failedAmountMap.put(ticketId, subTicket.getAmount());
				} else {
					failedAmountMap.put(ticketId, ofa + subTicket.getAmount());
				}
			}

			String messageId = temp.getBackup1();
			if (Utils.isNotEmpty(messageId)) {
				subTicket.setBackup1(messageId);
			} else {
				messageId = subTicket.getBackup1();
			}
			messageIdMap.put(ticketId, messageId);

			subTicket.setAcceptTime(new Date());
			subTicket.setReturnTime(new Date());
			subTicketDao.update(subTicket);
		}

//		Map<String, Double> refundMap = new HashMap<String, Double>();
		for (String ticketId : messageIdMap.keySet()) {
			Ticket ticket = ticketDao.getTicketByTicketIdForUpdate(ticketId);
			if (null == ticket) {
				continue;
			}
			if (ticket.getTicketStatus() != Constants.TICKET_STATUS_SENDING) {
				logger.error("当前票状态(" + ticket.getTicketId() + "|" + ticket.getTicketStatus() + ")不为2");
				continue;
			}
			int subTicket = ticket.getSumTicket();//
			int os = ticket.getSuccessTicket();//
			double osa = ticket.getSuccessAmount();//
			int of = ticket.getFailureTicket();//
			double ofa = ticket.getFailureAmount();

			int s = successMap.get(ticketId) == null ? 0 : successMap.get(ticketId);
			double sa = successAmountMap.get(ticketId) == null ? 0d : successAmountMap.get(ticketId);
			int f = failedMap.get(ticketId) == null ? 0 : failedMap.get(ticketId);
			double fa = failedAmountMap.get(ticketId) == null ? 0d : failedAmountMap.get(ticketId);

			ticket.setSuccessAmount(osa + sa);
			ticket.setSuccessTicket(s + os);
			ticket.setFailureAmount(ofa + fa);
			ticket.setFailureTicket(of + f);
			if (subTicket == s + os) {
				ticket.setTicketStatus(Constants.TICKET_STATUS_SUCCESS);
			} else if (subTicket == f + of) {
				ticket.setTicketStatus(Constants.TICKET_STATUS_FAILURE);
			} else if (subTicket == s + os + f + of) {
				ticket.setTicketStatus(Constants.TICKET_STATUS_FAILURE);
			}

			if (ticket.getTicketStatus() == Constants.TICKET_STATUS_FAILURE) {// 退款处理

//				String code = ticket.getOrderId() + "," + ticket.getSid() + "," + ticket.getUserCode();
//				double ra = refundMap.get(code) == null ? 0 : refundMap.get(code);
//				refundMap.put(code, ra + ticket.getAmount());
				
				AccountOperatorTemp accountOperatorTemp = new AccountOperatorTemp();
                accountOperatorTemp.setOrderId(ticket.getOrderId());
                accountOperatorTemp.setTicketId(ticket.getTicketId());
                accountOperatorTemp.setSid(ticket.getSid());
                accountOperatorTemp.setResources(Constants.OPERATOR_FROM_SYS);
                accountOperatorTemp.setUserCode(ticket.getUserCode());
                accountOperatorTemp.setAmount(ticket.getAmount());
                accountOperatorTemp.setEventCode(Constants.R00100);
                accountOperatorTemp.setStatus(Constants.ACCOUNT_OPERATOR_TEMP_STATUS_WAIT);
                accountOperatorTemp.setCreateTime(new Date());
                accountOperatorTemp.setAcceptTime(accountOperatorTemp.getCreateTime());
                accountOperatorTempDao.save(accountOperatorTemp);

				ticket.setReturnTime(new Date());
			} else if (ticket.getTicketStatus() == Constants.TICKET_STATUS_SUCCESS) {
				ticket.setReturnTime(new Date());
			}

			ticket.setAcceptTime(new Date());
			ticketDao.update(ticket);
		}

		return 1;
	}

	@Override
	public SubTicket findSubTicketBySubTicketId(String subTicketId) {
		return subTicketDao.findSubTicketBySubTicketId(subTicketId);
	}

	@Override
	public int doNoBonus(String lotteryCode, String issue, String postCode) {
		return subTicketDao.doNoBonus(lotteryCode, issue, postCode);
	}

	@Override
	public PageBean getSendedSubTicket(String lotteryCode, String postCode, Date sendTime, int page, int pageSize) {
		return subTicketDao.getSendedSubTicket(lotteryCode, postCode, sendTime, page, pageSize);
	}

	@Override
	public PageBean getSendedSubTicket(String lotteryCode, String postCode, int page, int pageSize) {
		return subTicketDao.getSendedSubTicket(lotteryCode, postCode, page, pageSize);
	}
}
