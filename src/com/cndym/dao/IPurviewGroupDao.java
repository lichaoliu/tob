package com.cndym.dao;

import com.cndym.bean.sys.PurviewGroup;
import com.cndym.bean.sys.PurviewUrl;
import com.cndym.utils.hibernate.PageBean;

import java.util.List;

/**
 * User: 邓玉明
 * Date: 11-3-27 下午10:24
 */
public interface IPurviewGroupDao extends IGenericDao<PurviewGroup> {
    public PageBean getPageBeanByPara(PurviewGroup purviewGroup, Integer page, Integer pageSize);

    public void deletePurviewGroupById(Long id);
    public void deletePurviewGroupByPurviewGroupCode(String purviewGroupCode);

    public PurviewGroup findPurviewGroup(Long id);
    public String findPurviewGroupBuyCode(String purviewGroupCode);
    public PurviewGroup findPurviewGroupByCode(String code);

    public List<PurviewUrl> findPurviewUrlNo(Long id);
}
