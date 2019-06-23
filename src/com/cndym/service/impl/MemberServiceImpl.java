package com.cndym.service.impl;

import com.cndym.bean.sys.Manages;
import com.cndym.bean.user.Account;
import com.cndym.bean.user.Member;
import com.cndym.constant.Constants;
import com.cndym.dao.IAccountDao;
import com.cndym.dao.IManagesDao;
import com.cndym.dao.IMemberDao;
import com.cndym.service.IMemberService;
import com.cndym.utils.ConfigUtils;
import com.cndym.utils.Md5;
import com.cndym.utils.UserCodeBuildUtils;
import com.cndym.utils.Utils;
import com.cndym.utils.hibernate.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

/**
 * User: mcs
 * Date: 12-10-27
 * Time: 下午11:34
 */

@Service
public class MemberServiceImpl extends GenericServiceImpl<Member, IMemberDao> implements IMemberService {

    @Autowired
    private IMemberDao memberDao;

    @Autowired
    private IAccountDao accountDao;

    @Autowired
    private IManagesDao managesDao;

    @PostConstruct
    public void initDao() {
        super.setDaoImpl(memberDao);
    }

    @Override
    public Member getMemberBySid(String sid) {
        return memberDao.getMemberBySid(sid);
    }

    @Override
    public Member getMemberByUserCode(String userCode) {
        return memberDao.getMemberByUserCode(userCode);
    }

    @Override
    public String getMaxCooperationSidOfMask(String mask) {
        return memberDao.getMaxCooperationSidOfMask(mask);
    }

    @Override
    public PageBean getPageBeanByPara(Member member, Integer page, Integer pageSize) {
        return memberDao.getPageBeanByPara(member, page, pageSize);
    }

    @Override
    public int doSaveMember(Member member) {
        try {
            String userCode = UserCodeBuildUtils.userCode();
            member.setUserCode(userCode);
            member.setCreateTime(new Date());
            member.setStatus(Constants.MEMBER_STATUS_LIVE);
            memberDao.save(member);

            Account account = new Account();
            account.setUserCode(member.getUserCode());
            account.setBonusAmount(Constants.DOUBLE_ZERO);
            account.setRechargeAmount(Constants.DOUBLE_ZERO);
            account.setPresentAmount(Constants.DOUBLE_ZERO);
            account.setFreezeAmount(Constants.DOUBLE_ZERO);
            account.setBonusTotal(Constants.DOUBLE_ZERO);
            account.setRechargeTotal(Constants.DOUBLE_ZERO);
            account.setPresentTotal(Constants.DOUBLE_ZERO);
            account.setPayTotal(Constants.DOUBLE_ZERO);
            account.setDrawTotal(Constants.DOUBLE_ZERO);
            accountDao.save(account);


            Manages manages = new Manages();
            manages.setUserName(member.getSid());
            manages.setPassWord(Md5.Md5(ConfigUtils.getValue("COOPERATION_INIT_PWD")));
            manages.setType(Constants.COOP_STATUS);
            manages.setStatus(1);
            manages.setCreateTime(new Date());

            managesDao.save(manages);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int doUpdateMember(Member member) {
        try {
            Member temp = memberDao.getMemberBySid(member.getSid());
            temp.setCompanyAddress(member.getCompanyAddress());
            temp.setContactPerson(member.getContactPerson());
            temp.setCardCode(member.getCardCode());
            temp.setMobile(member.getMobile());
            temp.setEmail(member.getEmail());
            memberDao.update(temp);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int doUpdateMember(String sid, Integer status) {
        try {
            Member temp = memberDao.getMemberBySid(sid);
            temp.setStatus(status);
            memberDao.update(temp);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    @Override
    public int doUpdateMemberLotteryCode(String sid, String lotteryCode){
    	try {
            Member temp = memberDao.getMemberBySid(sid);
            temp.setBackup1(lotteryCode);
            memberDao.update(temp);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public int doUpdateLotteryCodeToMember(String lotteryCode, String sid){
    	return memberDao.doUpdateLotteryCodeToMember(lotteryCode, sid);
    }

    @Override
    public List getMemberList() {
        return memberDao.getMemberList();
    }
    
    @Override
    public List<Member> getMemberListForLotteryCode(Member member){
    	return memberDao.getMemberListForLotteryCode(member);
    }
}
