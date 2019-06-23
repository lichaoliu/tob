package com.cndym.servlet.manages;

import com.cndym.bean.sys.Manages;
import com.cndym.bean.sys.Purview;
import com.cndym.bean.sys.PurviewUrl;
import com.cndym.service.IManagesService;
import com.cndym.service.IPurviewService;
import com.cndym.service.IPurviewUrlService;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.hibernate.PageBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 **USER:MengJingyi
 **TIME:2011 2011-6-21 下午10:24:52
 */

public class ManagesList {
	private static Map<String, Manages> map = new HashMap<String, Manages>();
	private static Map<String, Purview> purviewMap = new HashMap<String, Purview>();
	private static List<PurviewUrl> purviewUrlList = new ArrayList<PurviewUrl>();
	private static List<PurviewUrl> purviewUrlFatherList = new ArrayList<PurviewUrl>();
	private static List<Manages> managesList = new ArrayList<Manages>();
	static {
		forInstance(20);
	}
	public static void forInstance(Integer pageSize) {
		IManagesService managesService = (IManagesService) SpringUtils.getBean("managesServiceImpl");
		IPurviewUrlService purviewUrlService = (IPurviewUrlService) SpringUtils.getBean("purviewUrlServiceImpl");
		IPurviewService purviewService = (IPurviewService) SpringUtils.getBean("purviewServiceImpl");
		PageBean pageBean = managesService.getAdminList(null, 1, pageSize);
		managesList = pageBean.getPageContent();
		for(Manages manages : managesList){
			Purview purview = new Purview();
			purview.setManagerId(manages.getId());
			PageBean pb = purviewService.getPurviewList(purview);
			List list = pb.getPageContent();
			for(Object obj : list){
				Purview p = (Purview)obj;
				purviewMap.put(manages.getUserName() + p.getPurviewCode(), p );
			}
		}
		PageBean pbean = purviewUrlService.getPurviewUrlList(new PurviewUrl());
		purviewUrlList = pbean.getPageContent();
		purviewUrlFatherList = purviewUrlService.getPurviewUrlFatherList();
	}
	
	public static List<Manages> getManagesList(){
		return managesList;
	}
	
	public static List<PurviewUrl> getPurviewUrlsList(){
		return purviewUrlList;
	}
	
	public static Map<String, Purview> getMap(){
		return purviewMap;
	}

	public static List<PurviewUrl> getPurviewUrlsFatherList() {
		return purviewUrlFatherList;
	}
}
