package com.cndym.dao;

import com.cndym.bean.user.Member;
import com.cndym.utils.hibernate.PageBean;

import java.util.List;

/**
 * User: 邓玉明 Date: 11-3-27 下午10:22
 */
public interface IMemberDao extends IGenericDao<Member> {

    public Member getMemberBySid(String sid);

    public Member getMemberByUserCode(String userCode);

    public String getMaxCooperationSidOfMask(String mask);

    public PageBean getPageBeanByPara(Member member, Integer page, Integer pageSize);

    public List getMemberList();
    
    public List<Member> getMemberListForLotteryCode(Member member);
    
    public int doUpdateLotteryCodeToMember(String lotteryCode, String sid);
}
