package com.cndym.service.impl;

import com.cndym.bean.user.AccountOperatorTemp;
import com.cndym.constant.Constants;
import com.cndym.dao.IAccountOperatorTempDao;
import com.cndym.service.IAccountOperatorTempService;
import com.cndym.service.IAccountService;
import com.cndym.utils.hibernate.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

/**
 * User: 邓玉明 Date: 11-3-27 下午11:02
 */
@Service
public class AccountOperatorTempServiceImpl extends GenericServiceImpl<AccountOperatorTemp, IAccountOperatorTempDao> implements IAccountOperatorTempService {
	@Autowired
	private IAccountOperatorTempDao accountOperatorTempDao;
	@Autowired
	private IAccountService accountService;

	@PostConstruct
	public void initDao() {
		super.setDaoImpl(accountOperatorTempDao);
	}

	@Override
	public int operatorFromDataBase() {
		int page = 1;
		int pageSize = 50;
		AccountOperatorTemp param = new AccountOperatorTemp();
		param.setStatus(Constants.ACCOUNT_OPERATOR_TEMP_STATUS_WAIT);
		PageBean pageBean = accountOperatorTempDao.getAccountOperatorTempList(param, page, pageSize);
		List<AccountOperatorTemp> accountOperatorTemps = pageBean.getPageContent();
		for (AccountOperatorTemp accountOperatorTemp : accountOperatorTemps) {
			accountOperatorTemp.setStatus(Constants.ACCOUNT_OPERATOR_TEMP_STATUS_DOING);
			accountOperatorTempDao.update(accountOperatorTemp);
			doUpdateAccount(accountOperatorTemp);
		}
		return 1;
	}

	@Override
	public int doUpdateAccount(AccountOperatorTemp accountOperatorTemp) {
		accountOperatorTemp.setStatus(Constants.ACCOUNT_OPERATOR_TEMP_STATUS_SUCCESS);
		accountOperatorTemp.setAcceptTime(new Date());
		boolean temp = false;
		if (Constants.R00100.equals(accountOperatorTemp.getEventCode())) {
			accountService.doAccount(accountOperatorTemp.getUserCode(), accountOperatorTemp.getEventCode(), accountOperatorTemp.getAmount(), accountOperatorTemp.getOrderId(), Constants.P10000,
					accountOperatorTemp.getTicketId(), accountOperatorTemp.getSid());
		}
		if (temp) {
			accountOperatorTempDao.update(accountOperatorTemp);
			return 1;
		}
		return 0;
	}

	public IAccountOperatorTempDao getAccountOperatorTempDao() {
		return accountOperatorTempDao;
	}

	public void setAccountOperatorTempDao(IAccountOperatorTempDao accountOperatorTempDao) {
		this.accountOperatorTempDao = accountOperatorTempDao;
	}

	@Override
	public PageBean getAccountOperatorTempList(AccountOperatorTemp accountOperatorTemp, Integer page, Integer pageSize) {
		return accountOperatorTempDao.getAccountOperatorTempList(accountOperatorTemp, page, pageSize);
	}

}
