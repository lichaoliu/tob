package com.cndym.service;

import com.cndym.bean.query.BonusTicketQueryBean;
import com.cndym.utils.hibernate.PageBean;

import java.util.Map;

public interface IBonusTicketService {
    /**
     * 查询代理商数字彩一期的中奖票
     *
     * @param sid
     * @param lotteryCode
     * @param issue
     * @param page
     * @param pageSize
     * @return
     */
    public PageBean getBounsTicketByIssue(String sid, String lotteryCode, String issue, int page, int pageSize);

    public PageBean getBounsTicketByIssue(String sid, String lotteryCode, String issue, String sn, int page, int pageSize);

    public PageBean getPageBeanByPara(BonusTicketQueryBean queryBean, Integer page, Integer pageSize);

    public Map<String, Object> getBonusTicketCount(BonusTicketQueryBean queryBean);

}
