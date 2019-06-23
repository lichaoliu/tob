package com.cndym.admin.dao;

import java.util.List;

import com.cndym.bean.admin.AlertAmountTable;
import com.cndym.bean.tms.SendClientPostCode;
import com.cndym.dao.IGenericDao;

public interface SendClientPostCodeDao extends IGenericDao<SendClientPostCode> {

	List<SendClientPostCode> getSendClientPostCodeByPostCode(String sql,String postCode);

}
