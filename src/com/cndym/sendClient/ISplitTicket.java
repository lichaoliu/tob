package com.cndym.sendClient;

import com.cndym.adapter.tms.bean.NumberInfo;
import com.cndym.adapter.tms.bean.SubNumberInfo;
import com.cndym.bean.tms.SubTicket;
import com.cndym.bean.tms.Ticket;

import java.util.List;

/**
 * 作者：邓玉明 email：cndym@163.com QQ：757579248 时间: 12-2-8 下午5:12
 */
public interface ISplitTicket {
	public List<SubTicket> splitTicket(Ticket ticket);

	public List<NumberInfo> splitTicket(NumberInfo numberInfo);

	public List<SubNumberInfo> splitTicket(String numberInfo);
}
