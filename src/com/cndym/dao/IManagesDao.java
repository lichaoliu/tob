package com.cndym.dao;

import com.cndym.bean.sys.Manages;
import com.cndym.utils.hibernate.PageBean;

/**
 * User: 邓玉明
 * Date: 11-3-27 下午10:24
 */
public interface IManagesDao extends IGenericDao<Manages>{
	
	public PageBean getAdminList(Manages manages,Integer page,Integer pageSize);
    public PageBean getManagesList(Manages manages, Integer page, Integer pageSize,String purviewCode);
	public Manages getAdminById(String adminId);
	public int updateAdmin(Manages manages);
	public Manages adminLogin(String adminName, String password);
    public Manages adminLoginByType(String adminName, String password, String adminType);
    public Manages getManagesById(Long id);

    /**
     * 判断管理员是否存在
     * @param manages 管理员对象
     * @return 是否存在
     */
    public boolean getManagsForKey(Manages manages);

    /**
     * 根据用户名获取管理员
     * @param userName
     * @return
     */
    public Manages getManagesByUserName(String userName);

    public String getUserNameById(String managesId);
}
