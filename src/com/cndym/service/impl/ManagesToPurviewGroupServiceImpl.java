package com.cndym.service.impl;

import com.cndym.bean.sys.ManagesToPurviewGroup;
import com.cndym.bean.sys.PurviewGroup;
import com.cndym.dao.IManagesToPurviewGroupDao;
import com.cndym.service.IManagesToPurviewGroupService;
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
public class ManagesToPurviewGroupServiceImpl extends GenericServiceImpl<ManagesToPurviewGroup, IManagesToPurviewGroupDao> implements IManagesToPurviewGroupService {
    @Autowired
    private IManagesToPurviewGroupDao managesToPurviewGroupDao;

    @PostConstruct
    private void daoImplInit() {
        super.setDaoImpl(managesToPurviewGroupDao);
    }

    @Override
    public PageBean getPageBeanByPara(ManagesToPurviewGroup managesToPurviewGroup, Integer page, Integer pageSize) {
        return managesToPurviewGroupDao.getPageBeanByPara(managesToPurviewGroup, page, pageSize);
    }

    @Override
    public List getPageBeanByPara(ManagesToPurviewGroup managesToPurviewGroup) {
        return managesToPurviewGroupDao.getPageBeanByPara(managesToPurviewGroup);
    }

    @Override
    public List getPageBeanByPara(String code) {
        return managesToPurviewGroupDao.getPageBeanByPara(code);
    }

    public IManagesToPurviewGroupDao getManagesToPurviewGroupDao() {
        return managesToPurviewGroupDao;
    }

    public void setManagesToPurviewGroupDao(IManagesToPurviewGroupDao managesToPurviewGroupDao) {
        this.managesToPurviewGroupDao = managesToPurviewGroupDao;
    }

    @Override
    public List<PurviewGroup> getPurviewGroupNo(Long memberId) {
        return managesToPurviewGroupDao.getPurviewGroupNo(memberId);
    }


    @Override
    public List<PurviewGroup> getPurviewGroupList() {
        return managesToPurviewGroupDao.getPurviewGroupList();
    }

    @Override
    public void deleteForId(Long id) {
        managesToPurviewGroupDao.deleteForId(id);
    }

    @Override
    public void deleteForManagesId(Long managesId){
        managesToPurviewGroupDao.deleteForManagesId(managesId);
    }
}
