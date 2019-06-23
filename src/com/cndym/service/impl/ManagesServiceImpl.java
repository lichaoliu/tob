package com.cndym.service.impl;

import com.cndym.bean.sys.Manages;
import com.cndym.bean.sys.ManagesToPurviewGroup;
import com.cndym.bean.user.Member;
import com.cndym.dao.IManagesDao;
import com.cndym.dao.IManagesToPurviewGroupDao;
import com.cndym.dao.IMemberDao;
import com.cndym.lottery.lotteryInfo.LotteryList;
import com.cndym.service.IManagesService;
import com.cndym.servlet.ElTagUtils;
import com.cndym.utils.Utils;
import com.cndym.utils.hibernate.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: 邓玉明
 * Date: 11-3-27 下午11:02
 */
@Service
public class ManagesServiceImpl extends GenericServiceImpl<Manages, IManagesDao> implements IManagesService {
    @Autowired
    private IManagesDao managesDao;
    @Autowired
    private IManagesToPurviewGroupDao managesToPurviewGroupDao;
    @Autowired
    private IMemberDao memberDao;

    public IMemberDao getMemberDao() {
        return memberDao;
    }

    public void setMemberDao(IMemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public IManagesDao getManagesDao() {
        return managesDao;
    }

    public void setManagesDao(IManagesDao managesDao) {
        this.managesDao = managesDao;
    }

    @Override
    public Manages getAdminById(String adminId) {
        return managesDao.getAdminById(adminId);
    }

    @Override
    public Manages getManagesById(Long id) {
        return managesDao.getManagesById(id);
    }

    @Override
    public PageBean getAdminList(Manages manages, Integer page, Integer pageSize) {
        return managesDao.getAdminList(manages, page, pageSize);
    }

    @Override
    public PageBean getManagesList(Manages manages, Integer page, Integer pageSize, String purviewCode) {
        return managesDao.getManagesList(manages, page, pageSize, purviewCode);
    }

    @Override
    public boolean save(Manages manages) {
        return managesDao.save(manages);
    }

    @Override
    public boolean update(Manages manages) {
        return managesDao.update(manages);
    }

    @Override
    public int updateAdmin(Manages manages) {
        return managesDao.updateAdmin(manages);
    }

    @Override
    public Manages adminLogin(String adminName, String password) {
        return managesDao.adminLogin(adminName, password);
    }

    @Override
    public Manages adminLoginByType(String adminName, String password, String adminType) {
        return managesDao.adminLoginByType(adminName, password, adminType);
    }

    @Override
    public boolean saveAllObject(List list) {
        return managesDao.saveAllObject(list);
    }

    @Override
    public boolean doDelete(Long managesId) {
        Manages manages = managesDao.getManagesById(managesId);
        if (!Utils.isNotEmpty(manages)) {
            return false;
        }
        ManagesToPurviewGroup managesToPurviewGroup = new ManagesToPurviewGroup();
        managesToPurviewGroup.setManagerId(managesId);
        List<Object[]> list = managesToPurviewGroupDao.getPageBeanByPara(managesToPurviewGroup);
        boolean deleteResult = false;
        if (!Utils.isNotEmpty(list)) {
            deleteResult = true;
        }
        for (Object[] obj : list) {
            ManagesToPurviewGroup mtpg = (ManagesToPurviewGroup) obj[0];
            deleteResult = managesToPurviewGroupDao.delete(mtpg);
        }
        boolean result = managesDao.delete(manages);
        return deleteResult && result;
    }

    public Integer doSaveManages(Manages manages) {
        boolean isExis = managesDao.getManagsForKey(manages);
        //信息重复
        if (isExis) {
            return 2;
        }
        boolean success = managesDao.save(manages);
        if (!success) {
            return 0;
        }
        return 1;
    }

    @Override
    public Manages getManagesByUserName(String userName) {
        return managesDao.getManagesByUserName(userName);
    }

    @Override
    public String getUserNameById(String managesId) {
        return managesDao.getUserNameById(managesId);
    }

    @Override
    public List<String> doGetAdminLoginMsg() {

        return null;
    }
}
