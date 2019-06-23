package com.cndym.dao;

import com.cndym.bean.sys.Purview;
import com.cndym.utils.hibernate.PageBean;

/**
 * User: 邓玉明
 * Date: 11-3-27 下午10:24
 */
public interface IPurviewDao extends IGenericDao<Purview> {
	public Purview getPurview(String adminName,String code);
	public PageBean getPurviewList(Purview purview);
	public PageBean getAdminPurviewUrlList(Purview purview);
	public boolean deleteByAdminId(String adminId);
	public boolean deleteByAdminIdAndIndex(String adminId, String codeFather);
}
