package com.cndym.sendClient;

import com.cndym.adapter.tms.bean.MatchItemInfo;
import com.cndym.adapter.tms.bean.NumberInfo;
import com.cndym.adapter.tms.bean.SubNumberInfo;
import com.cndym.bean.tms.SubTicket;
import com.cndym.bean.tms.Ticket;
import com.cndym.utils.CombinationUtils;
import com.cndym.utils.Utils;

import java.util.*;

/**
 * 作者：邓玉明 email：cndym@163.com QQ：757579248 时间: 12-2-8 下午5:13
 */
public class BaseSplitTicket implements ISplitTicket {
	@Override
	public List<SubTicket> splitTicket(Ticket ticket) {
		List<SubTicket> ticketList = new ArrayList<SubTicket>();
		SubTicket subTicket = new SubTicket();
		subTicket.setTicketId(ticket.getTicketId());
		subTicket.setSubTicketId(ticket.getTicketId() + 0);
		subTicket.setLotteryCode(ticket.getLotteryCode());
		subTicket.setIssue(ticket.getIssue());
		subTicket.setPlayCode(ticket.getPlayCode());
		subTicket.setPollCode(ticket.getPollCode());
		subTicket.setItem(ticket.getItem());
		subTicket.setMultiple(ticket.getMultiple());
		subTicket.setAmount(ticket.getAmount());
		subTicket.setNumberInfo(ticket.getNumberInfo());
		subTicket.setPostCode(ticket.getPostCode());
		subTicket.setTicketStatus(ticket.getTicketStatus());
		subTicket.setBonusStatus(ticket.getBonusStatus());
		subTicket.setBonusAmount(ticket.getBonusAmount());
		subTicket.setFixBonusAmount(ticket.getFixBonusAmount());
		subTicket.setBigBonus(ticket.getBigBonus());
		subTicket.setCreateTime(new Date());
		subTicket.setSendTime(new Date());

		ticketList.add(subTicket);
		return ticketList;
	}

	@Override
	public List<NumberInfo> splitTicket(NumberInfo numberInfo) {
		List<NumberInfo> numberInfos = new ArrayList<NumberInfo>();
		numberInfos.add(numberInfo);
		return numberInfos;
	}

	@Override
	public List<SubNumberInfo> splitTicket(String numberInfo) {
		// 场次1:选项1,选项2,选项3:设胆;场次2:选项1,选项2,选项3:设胆|过关方式（1*1,n*1,n*m)
		String[] arr = numberInfo.split("\\|");
		if (arr.length != 2) {
			throw new IllegalArgumentException("号码(" + numberInfo + ")格式不正确（1）");
		}
		List<SubNumberInfo> subNumberInfos = new ArrayList<SubNumberInfo>();
		List<MatchItemInfo> matchItemInfosForDan = new ArrayList<MatchItemInfo>();
		List<MatchItemInfo> matchItemInfosForNoDan = new ArrayList<MatchItemInfo>();
		String[] matchIdArr = arr[0].split(";");
		String[] modeArr = arr[1].split(",");
		for (String number : matchIdArr) {
			MatchItemInfo matchItemInfo = MatchItemInfo.str2Object(number);
			if (matchItemInfo.getDan() == MatchItemInfo.DAN) {
				matchItemInfosForDan.add(matchItemInfo);
			} else {
				matchItemInfosForNoDan.add(matchItemInfo);
			}
		}

		for (String mode : modeArr) {
			String[] temp = mode.split("\\*");
			if (temp.length != 2) {
				throw new IllegalArgumentException("对阵(" + mode + ")格式不正确(3)");
			}
			int a = Utils.formatInt(temp[0], 1);
			int b = Utils.formatInt(temp[1], 1);
			if (matchItemInfosForDan.size() >= a) {
				throw new IllegalArgumentException("串关（" + a + "*" + b + "）与设胆不一致(4)");
			}
			CombinationUtils combinationUtils = new CombinationUtils();
			int len = a - matchItemInfosForDan.size();
			List<int[]> list = combinationUtils.combination(Utils.forInstance(matchItemInfosForNoDan.size()), len);
			for (int[] ints : list) {
				List<MatchItemInfo> subList = new ArrayList<MatchItemInfo>();
				subList.addAll(matchItemInfosForDan);
				for (int i : ints) {
					subList.add(matchItemInfosForNoDan.get(i));
				}
				SubNumberInfo subNumberInfo = new SubNumberInfo(subList, a, b);
				if ("1*1".equals(mode)) {
					subNumberInfo.setPollCode("01");
				} else {
					subNumberInfo.setPollCode("02");
				}
				subNumberInfos.add(subNumberInfo);
			}
		}
		return subNumberInfos;
	}

	public static void main(String[] args) {
		BaseSplitTicket baseSplitTicket = new BaseSplitTicket();
		List<SubNumberInfo> subNumberInfos = baseSplitTicket.splitTicket("20120820001:3:0;20120820002:3,1:1;20120820005:3:0;20120820007:3:0;20120820010:3:0|4*1,5*1");
		for (SubNumberInfo subNumberInfo : subNumberInfos) {
			System.out.println(subNumberInfo);
		}
	}
}
