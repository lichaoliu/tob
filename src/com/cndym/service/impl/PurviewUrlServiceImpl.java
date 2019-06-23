package com.cndym.service.impl;

import java.util.List;
import java.util.Map;

import com.cndym.bean.sys.PurviewUrl;
import com.cndym.dao.IPurviewUrlDao;
import com.cndym.service.IPurviewUrlService;
import com.cndym.utils.hibernate.PageBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * User: 邓玉明
 * Date: 11-3-27 下午11:02
 */
@Service
public class PurviewUrlServiceImpl extends GenericServiceImpl<PurviewUrl, IPurviewUrlDao> implements IPurviewUrlService {
    @Autowired
    private IPurviewUrlDao purviewDao;

    @PostConstruct
    public void initDao() {
        super.setDaoImpl(purviewDao);
    }

    @Override
    public PurviewUrl getPurviewUrl(PurviewUrl purviewUrl) {
        return purviewDao.getPurviewUrl(purviewUrl);
    }

    @Override
    public PageBean getPurviewUrlList(PurviewUrl purviewUrl) {
        return purviewDao.getPurviewUrlList(purviewUrl);
    }

    @Override
    public PageBean getPurviewUrlList(String codeIndex1) {
        return purviewDao.getPurviewUrlList(codeIndex1);
    }

    @Override
    public List getPurviewUrlFatherList() {
        return purviewDao.getPurviewUrlFatherList();
    }

    @Override
    public List<PurviewUrl> getPurviewUrlList() {
        return purviewDao.getPurviewUrlList();
    }

    @Override
    public List getPurviewUrlListByGroup(String groupCode) {
        return purviewDao.getPurviewUrlListByGroup(groupCode);
    }

    @Override
    public List getPurviewUrlListByGroupNew(String groupCode) {
        return purviewDao.getPurviewUrlListByGroupNew(groupCode);
    }

    @Override
    public Map<String, String> getPurviewUrlBycode(String code) {
        return purviewDao.getPurviewUrlBycode(code);
    }
}
