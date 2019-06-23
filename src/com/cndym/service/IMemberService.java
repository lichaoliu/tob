package com.cndym.service;

import com.cndym.bean.user.Member;
import com.cndym.utils.hibernate.PageBean;

import java.util.List;

/**
 * User: mcs
 * Date: 12-10-27
 * Time: 下午11:32
 */
public interface IMemberService extends IGenericeService<Member> {

    public Member getMemberBySid(String sid);

    public Member getMemberByUserCode(String userCode);

    public String getMaxCooperationSidOfMask(String mask);

    public PageBean getPageBeanByPara(Member member, Integer page, Integer pageSize);

    public int doSaveMember(Member member);

    public int doUpdateMember(Member member);

    public int doUpdateMember(String sid, Integer status);
    
    public int doUpdateMemberLotteryCode(String sid, String lotteryCode);

    public List getMemberList();
    
    public List<Member> getMemberListForLotteryCode(Member member);
    
    public int doUpdateLotteryCodeToMember(String lotteryCode, String sid);
}
