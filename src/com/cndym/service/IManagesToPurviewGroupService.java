package com.cndym.service;

import com.cndym.bean.sys.ManagesToPurviewGroup;
import com.cndym.bean.sys.PurviewGroup;
import com.cndym.utils.hibernate.PageBean;

import java.util.List;

/**
 * User: 邓玉明
 * Date: 11-3-27 下午11:00
 */
public interface IManagesToPurviewGroupService extends IGenericeService<ManagesToPurviewGroup> {
    public PageBean getPageBeanByPara(ManagesToPurviewGroup managesToPurviewGroup, Integer page, Integer pageSize);
    public List<Object[]> getPageBeanByPara(ManagesToPurviewGroup managesToPurviewGroup);
    public List<Object[]> getPageBeanByPara(String code);
    public List<PurviewGroup> getPurviewGroupNo(Long memberId);
    public List<PurviewGroup> getPurviewGroupList();
    public void deleteForId(Long id);
    public void deleteForManagesId(Long managesId);
    
}
