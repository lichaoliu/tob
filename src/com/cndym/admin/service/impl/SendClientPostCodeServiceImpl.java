package com.cndym.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cndym.admin.dao.SendClientPostCodeDao;
import com.cndym.admin.service.SendClientPostCodeService;
import com.cndym.bean.tms.SendClientPostCode;
import com.cndym.service.impl.GenericServiceImpl;
import com.cndym.utils.SpringUtils;
@Service
public class SendClientPostCodeServiceImpl extends GenericServiceImpl<SendClientPostCode, SendClientPostCodeDao> implements SendClientPostCodeService{
	
	@Autowired
	private SendClientPostCodeDao sendClientDao;

	public List<SendClientPostCode> getSendClientPostCodeByPostCode(String postCode) {
		StringBuffer sql=new StringBuffer(" From SendClientPostCode  where postCode = ?");
		return sendClientDao.getSendClientPostCodeByPostCode(sql.toString(),postCode);
	}
	
	public static void main(String[] args) {
		SendClientPostCodeServiceImpl imp=(SendClientPostCodeServiceImpl) SpringUtils.getBean("sendClientPostCodeServiceImpl");
		List<SendClientPostCode> list=imp.getSendClientPostCodeByPostCode("23");
		for (SendClientPostCode sendClientPostCode : list) {
			System.out.println(sendClientPostCode.getLotteryCode());
		}
	}
}
