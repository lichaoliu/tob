package com.cndym.service.impl;

import com.cndym.bean.tms.BonusJcLog;
import com.cndym.bean.tms.BonusLog;
import com.cndym.dao.*;
import com.cndym.service.IBonusLogService;
import com.cndym.utils.hibernate.PageBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: 邓玉明 Date: 11-3-27 下午11:02
 */
@Service
public class BonusLogServiceImpl extends GenericServiceImpl<BonusLog, IBonusLogDao> implements IBonusLogService {
	@Autowired
	private IBonusLogDao bonusLogDao;
	@Autowired
	private IBonusJcLogDao bonusJcLogDao;

	@Override
	public BonusLog getBonusLogByTicketId(String ticketId) {
		return bonusLogDao.getBonusLogByTicketId(ticketId);
	}

	@Override
	public List<BonusLog> getNoDuiJiangTicket(String lotteryCode, String postCode) {
		return bonusLogDao.getNoDuiJiangTicket(lotteryCode, postCode);
	}

	@Override
	public int updateBonusLogForDuiJiang(BonusLog bonusLog) {
		return bonusLogDao.updateBonusLogForDuiJiang(bonusLog);
	}

	@Override
	public boolean doSaveAll(List<BonusLog> bonusLog, BonusJcLog bonusJcLog) {
		return bonusLogDao.saveAllObject(bonusLog) && bonusJcLogDao.save(bonusJcLog);
	}

	@Override
	public PageBean getNoDuiJiangTicket(String lotteryCode, String postCode, Integer pageId, Integer pageSize) {
		return bonusLogDao.getNoDuiJiangTicket(lotteryCode, postCode, pageId, pageSize);
	}
}
