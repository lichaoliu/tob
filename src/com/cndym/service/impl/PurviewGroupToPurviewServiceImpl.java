package com.cndym.service.impl;

import com.cndym.bean.sys.PurviewGroupToPurview;
import com.cndym.dao.IPurviewGroupToPurviewDao;
import com.cndym.service.IPurviewGroupToPurviewService;
import com.cndym.utils.hibernate.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * User: 邓玉明
 * Date: 11-3-27 下午11:02
 */
@Service
public class PurviewGroupToPurviewServiceImpl extends GenericServiceImpl<PurviewGroupToPurview, IPurviewGroupToPurviewDao> implements IPurviewGroupToPurviewService {
    @Autowired
    private IPurviewGroupToPurviewDao purviewGroupToPurviewDao;

    @PostConstruct
    private void daoImplInit() {
        super.setDaoImpl(purviewGroupToPurviewDao);
    }


    @Override
    public PageBean getPageBeanByPara(PurviewGroupToPurview purviewGroupToPurview, Integer page, Integer pageSize) {
        return purviewGroupToPurviewDao.getPageBeanByPara(purviewGroupToPurview, page, pageSize);
    }

    public IPurviewGroupToPurviewDao getPurviewGroupToPurviewDao() {
        return purviewGroupToPurviewDao;
    }

    public void setPurviewGroupToPurviewDao(IPurviewGroupToPurviewDao purviewGroupToPurviewDao) {
        this.purviewGroupToPurviewDao = purviewGroupToPurviewDao;
    }

    @Override
    public void deleteById(Long id) {
        purviewGroupToPurviewDao.deleteById(id);
    }

    @Override
    public void deleteByPurviewGroupCode(String purviewGroupCode) {
        purviewGroupToPurviewDao.deleteByPurviewGroupCode(purviewGroupCode);
    }
    
}
