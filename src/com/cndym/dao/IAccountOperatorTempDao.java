package com.cndym.dao;

import com.cndym.bean.user.AccountOperatorTemp;
import com.cndym.utils.hibernate.PageBean;

import java.util.List;

/**
 * User: 邓玉明
 * Date: 11-3-27 下午10:24
 */
public interface IAccountOperatorTempDao extends IGenericDao<AccountOperatorTemp>{
    public PageBean getAccountOperatorTempList(AccountOperatorTemp accountOperatorTemp,Integer page,Integer pageSize);
}
