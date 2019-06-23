package com.cndym.dao.impl;

import com.cndym.bean.user.Member;
import com.cndym.dao.IMemberDao;
import com.cndym.utils.Utils;
import com.cndym.utils.hibernate.HibernatePara;
import com.cndym.utils.hibernate.PageBean;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: 邓玉明 Date: 11-3-27 下午10:22
 */
@Repository
public class MemberDaoImpl extends GenericDaoImpl<Member> implements IMemberDao {
    @Resource
    private SessionFactory sessionFactoryTemp;
    @Resource
    private JdbcTemplate jdbcTemplate;


    @PostConstruct
    private void sessionFactoryInit() {
        super.setSessionFactory(sessionFactoryTemp);
    }

    @Override
    public Member getMemberBySid(String sid) {
        String sql = "from Member where sid = ? ";
        List<Member> memberList = find(sql, new Object[]{sid});
        if (Utils.isNotEmpty(memberList)) {
            return memberList.get(0);
        }
        return null;
    }

    @Override
    public Member getMemberByUserCode(String userCode) {
        String sql = "from Member where userCode = ? ";
        List<Member> memberList = find(sql, new Object[]{userCode});
        if (Utils.isNotEmpty(memberList)) {
            return memberList.get(0);
        }
        return null;
    }

    @Override
    public PageBean getPageBeanByPara(Member member, Integer page, Integer pageSize) {
        StringBuffer sql = new StringBuffer("from Member m,Account a where m.userCode = a.userCode ");
        List<HibernatePara> paraList = new ArrayList<HibernatePara>();
        if (Utils.isNotEmpty(member)) {
            if (Utils.isNotEmpty(member.getName())) {
                sql.append(" and m.name like ? ");
                paraList.add(new HibernatePara("%" + member.getName() + "%"));
            }
            if (Utils.isNotEmpty(member.getSid())) {
                sql.append(" and m.sid = ? ");
                paraList.add(new HibernatePara(member.getSid()));
            }
            if (Utils.isNotEmpty(member.getStatus())) {
                sql.append(" and m.status = ? ");
                paraList.add(new HibernatePara(member.getStatus()));
            }
            if (Utils.isNotEmpty(member.getBackup1())){
            	if (Utils.isNotEmpty(member.getBackup2())){
                	if ("1".equals(member.getBackup2())){
                		sql.append(" and m.backup1  like  ? ");
                		paraList.add(new HibernatePara("%" + member.getBackup1() + "%"));
                	}else if  ("2".equals(member.getBackup2())){
                		sql.append(" and ( m.backup1 not like  ?  or m.backup1 is null ) ");
                		paraList.add(new HibernatePara("%" + member.getBackup1() + "%"));
                	}
                }
            }
        }
        sql.append(" order by m.sid desc ");
        return getPageBeanByPara(sql.toString(), paraList, page, pageSize);
    }

    public String getMaxCooperationSidOfMask(String mask) {
        String sql = "select max(sid) as sid from user_member where 1=1 ";
        if (Utils.isNotEmpty(mask)) {
            sql += "and sid LIKE '" + mask + "'";
        }
        Map<String, Object> map = jdbcTemplate.queryForMap(sql);
        try {
            return String.valueOf(map.get("sid"));
        } catch (Exception e) {
            return null;
        }
    }

    public List getMemberList() {
        String sql = "select sid,name from user_member order by sid desc";
        return jdbcTemplate.queryForList(sql);
    }
    
    @Override
    public List<Member> getMemberListForLotteryCode(Member member){
    	String sql = "from Member where status = 1 and  backup1 like  ? order by sid desc";
        List<Member> memberList = find(sql, new Object[]{"%"+member.getBackup1()+"%"});
        if (Utils.isNotEmpty(memberList)) {
            return memberList;
        }
        return null;
    }
    
    public int doUpdateLotteryCodeToMember(String lotteryCode, String sid){
    	StringBuffer sql = new StringBuffer("update user_member t set t.backup1 = replace( replace(t.backup1,'"+lotteryCode+",',''),'"+lotteryCode+"','') ");
    	sql.append(" where t.sid in ( "+sid+" ) ");
    	
        return jdbcTemplate.update(sql.toString());
    }
}
