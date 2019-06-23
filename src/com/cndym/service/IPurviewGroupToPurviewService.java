package com.cndym.service;

import com.cndym.bean.sys.PurviewGroupToPurview;
import com.cndym.utils.hibernate.PageBean;

/**
 * User: 邓玉明
 * Date: 11-3-27 下午11:00
 */
public interface IPurviewGroupToPurviewService extends IGenericeService<PurviewGroupToPurview> {
    public PageBean getPageBeanByPara(PurviewGroupToPurview purviewGroupToPurview, Integer page, Integer pageSize);
    public void deleteById(Long id);
    public void deleteByPurviewGroupCode(String purviewGroupCode);
}
