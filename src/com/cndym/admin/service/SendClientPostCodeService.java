package com.cndym.admin.service;

import java.util.List;

import com.cndym.bean.tms.SendClientPostCode;
import com.cndym.bean.tms.Ticket;
import com.cndym.service.IGenericeService;

public interface SendClientPostCodeService extends IGenericeService<SendClientPostCode> {
	List<SendClientPostCode> getSendClientPostCodeByPostCode(String postCode);
}
