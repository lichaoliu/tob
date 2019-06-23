package com.cndym.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.cndym.bean.tms.PostIssue;
import com.cndym.dao.IPostIssueDao;
import com.cndym.utils.Utils;

@Component
public class PostIssueDaoImpl extends GenericDaoImpl<PostIssue> implements IPostIssueDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Resource
	private SessionFactory sessionFactoryTemp;

	@PostConstruct
	private void sessionFactoryInit() {
		super.setSessionFactory(sessionFactoryTemp);
	}

	@Override
	public List<PostIssue> getPostIssueByLotteryCodeAndIssue(String lotteryCode, String issue) {
		String sql = "from PostIssue where lotteryCode = ? and name = ? ";
		return find(sql, new Object[] { lotteryCode, issue });
	}

	@Override
	public PostIssue getPostIssueByLotteryCodeAndIssue(String lotteryCode, String issue, String postCode) {
		String sql = "from PostIssue where lotteryCode = ? and name = ? and postCode = ?";
		List<PostIssue> issueList = find(sql, new Object[] { lotteryCode, issue, postCode });
		if (Utils.isNotEmpty(issueList)) {
			return issueList.get(0);
		}
		return null;
	}

	@Override
	public void doUpdateStatus(int status, String lotteryCode, String issue) {
		jdbcTemplate.update("update tms_post_issue m set m.status=? where m.lottery_code=? and m.name=?", new Object[] { status, lotteryCode, issue });
	}
	
	@Override
	public List<Map<String,Object>> getLotteryPostIssue(String lotteryCode, String postCode) {
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT T.LOTTERY_CODE lotteryCode,T.NAME name,T.POST_CODE postCode,T.START_TIME startTime,T.DUPLEX_TIME duplexTime,T.END_TIME endTime, ");
		sql.append(" T.STATUS status,T.BONUS_TIME bonusTime,T.BONUS_STATUS bonusStatus,T.BACKUP1 backup1 ");
		sql.append(" FROM TMS_POST_ISSUE T WHERE NAME = (SELECT MAX(NAME)  ");
		sql.append(" FROM TMS_POST_ISSUE WHERE LOTTERY_CODE = T.LOTTERY_CODE)  ");
		
		List<Object> paramList = new ArrayList<Object>();
		if (Utils.isNotEmpty(lotteryCode)) {
			sql.append(" AND LOTTERY_CODE=? ");
			paramList.add(lotteryCode);
		}
		if (Utils.isNotEmpty(postCode)) {
			sql.append(" AND POST_CODE=? ");
			paramList.add(postCode);
		}
		sql.append(" AND LOTTERY_CODE != '200' AND LOTTERY_CODE != '201' AND LOTTERY_CODE != '400' ");
		sql.append(" ORDER BY T.LOTTERY_CODE ");
		Integer paramSize = paramList.size();
		Object[] objArray = new Object[paramSize];

		for (int index = 0; index < paramSize; index++) {
			objArray[index] = paramList.get(index);
		}
		return jdbcTemplate.queryForList(sql.toString(), objArray);
	}
}
