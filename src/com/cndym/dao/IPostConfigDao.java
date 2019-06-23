/**
 * 
 */
package com.cndym.dao;

import java.util.List;

import com.cndym.bean.tms.PostConfig;

/**
 * @author 朱林虎
 *
 */
public interface IPostConfigDao extends IGenericDao<PostConfig> {
	
	public List<PostConfig> getAllPostConfigList();
}
