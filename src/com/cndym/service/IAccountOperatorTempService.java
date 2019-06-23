package com.cndym.service;

import com.cndym.bean.user.AccountOperatorTemp;
import com.cndym.utils.hibernate.PageBean;

/**
 * User: 邓玉明
 * Date: 11-3-27 下午11:00
 */
public interface IAccountOperatorTempService extends IGenericeService<AccountOperatorTemp> {
	
    public PageBean getAccountOperatorTempList(AccountOperatorTemp accountOperatorTemp,Integer page,Integer pageSize);
	
    public int doUpdateAccount(AccountOperatorTemp accountOperatorTemp);

    public int operatorFromDataBase();
}
