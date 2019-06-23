package com.cndym.service;

import com.cndym.bean.sys.Manages;
import com.cndym.utils.hibernate.PageBean;

import java.util.List;

/**
 * User: 邓玉明
 * Date: 11-3-27 下午11:00
 */
public interface IManagesService extends IGenericeService<Manages> {
    public PageBean getAdminList(Manages manages, Integer page, Integer pageSize);

    public PageBean getManagesList(Manages manages, Integer page, Integer pageSize, String purviewCode);

    public Manages getAdminById(String adminId);

    public Manages getManagesById(Long id);

    public int updateAdmin(Manages manages);

    public Manages adminLogin(String adminName, String password);

    public Manages adminLoginByType(String adminName, String password, String adminType);

    public List<String> doGetAdminLoginMsg();

    public boolean doDelete(Long managesId);

    /**
     * 添加管理员
     * * @param manages 管理员对象
     *
     * @return
     */
    public Integer doSaveManages(Manages manages);

    /**
     * 根据用户名获取管理员
     *
     * @param userName
     * @return
     */
    public Manages getManagesByUserName(String userName);

    /**
     * 根据管理员id获取管理员名称
     *
     * @param managesId 管理员id
     * @return
     */
    public String getUserNameById(String managesId);
}
