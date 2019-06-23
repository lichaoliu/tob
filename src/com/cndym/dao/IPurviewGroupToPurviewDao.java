package com.cndym.dao;

import com.cndym.bean.sys.PurviewGroupToPurview;
import com.cndym.utils.hibernate.PageBean;

import java.util.List;

/**
 * User: 邓玉明
 * Date: 11-3-27 下午10:24
 */
public interface IPurviewGroupToPurviewDao extends IGenericDao<PurviewGroupToPurview> {
    public PageBean getPageBeanByPara(PurviewGroupToPurview purviewGroupToPurview, Integer page, Integer pageSize);

    public List<PurviewGroupToPurview> getPageBeanByPara(PurviewGroupToPurview purviewGroupToPurview);

    public void deleteByPurviewGroupCode(String purviewGroupCode);

    public void deleteById(Long id);
}
