package com.cndym.service.impl;

import com.cndym.bean.sys.PurviewGroup;
import com.cndym.bean.sys.PurviewUrl;
import com.cndym.dao.IPurviewGroupDao;
import com.cndym.service.IPurviewGroupService;
import com.cndym.utils.hibernate.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * User: 邓玉明
 * Date: 11-3-27 下午11:02
 */
@Service
public class PurviewGroupServiceImpl extends GenericServiceImpl<PurviewGroup, IPurviewGroupDao> implements IPurviewGroupService {
    @Autowired
    private IPurviewGroupDao purviewGroupDao;

    @PostConstruct
    private void daoImplInit() {
        super.setDaoImpl(purviewGroupDao);
    }

    @Override
    public PageBean getPageBeanByPara(PurviewGroup purviewGroup, Integer page, Integer pageSize) {
        return purviewGroupDao.getPageBeanByPara(purviewGroup, page, pageSize);
    }

    public IPurviewGroupDao getPurviewGroupDao() {
        return purviewGroupDao;
    }

    public void setPurviewGroupDao(IPurviewGroupDao purviewGroupDao) {
        this.purviewGroupDao = purviewGroupDao;
    }

    @Override
    public void deletePurviewGroupById(Long id) {
        this.purviewGroupDao.deletePurviewGroupById(id);
    }

    @Override
    public void deletePurviewGroupByPurviewGroupCode(String purviewGroupCode) {
        this.purviewGroupDao.deletePurviewGroupByPurviewGroupCode(purviewGroupCode);
    }

    @Override
    public PurviewGroup findPurviewGroup(Long id) {
        return purviewGroupDao.findPurviewGroup(id);
    }

    @Override
    public String findPurviewGroupBuyCode(String purviewGroupCode) {
        return purviewGroupDao.findPurviewGroupBuyCode(purviewGroupCode);
    }

    @Override
    public List<PurviewUrl> findPurviewUrlNo(Long id) {
        return purviewGroupDao.findPurviewUrlNo(id);
    }
}
