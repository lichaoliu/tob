package com.cndym.servlet.manages;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.cndym.admin.service.IAdminManagesService;
import com.cndym.bean.admin.AlertAmountTable;
import com.cndym.cache.XMemcachedClientWrapper;
import com.cndym.constant.Constants;
import com.cndym.control.PostMap;
import com.cndym.control.WeightMap;
import com.cndym.control.bean.Weight;
import com.cndym.servlet.ElTagUtils;
import com.cndym.utils.JsonBinder;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;

public class ReloadControlWeightServlet extends BaseManagesServlet {

	private static final long serialVersionUID = -4446490338696217193L;
	
	private Logger logger = Logger.getLogger(getClass());
	
	private static final String RELOAD_CACHE_WEIGHT = "reload_cache_weight";
	private static final String RELOAD_FILE_WEIGHT = "reload_file_weight";
	private static final String VIEW_CACHE_WEIGHT = "view_cache_weight";
    
	private static XMemcachedClientWrapper memcachedClientWrapper = (XMemcachedClientWrapper)SpringUtils.getBean("xmemcachedClientWrapper");
    private static IAdminManagesService postTicketService = (IAdminManagesService) SpringUtils.getBean("adminManagesServiceImpl");
	
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = Utils.formatStr(request.getParameter("action"));
        response.setContentType("text/html");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        logger.info("action="+action);
        List<AlertAmountTable> alertAmountList = postTicketService.getAlertAmountList();
        if(VIEW_CACHE_WEIGHT.equals(action)) {
        	List<Weight> weightList = ElTagUtils.getWeightList();
        	if (Utils.isNotEmpty(weightList)) {
        		request.setAttribute("weightList", weightList);
        		request.setAttribute("alertAmountList", alertAmountList);
        	} else {
        		request.setAttribute("msg", "取缓冲数据失败");
        	}
        	request.getRequestDispatcher("/manages/system/viewControlWeight.jsp").forward(request, response);
        	return;
        } else if (RELOAD_CACHE_WEIGHT.equals(action)){
        	String myCheckbox[] = request.getParameterValues("myCheckbox");
        	if (Utils.isNotEmpty(myCheckbox)) {
        		boolean flushCache = false;
        		Map<String, Weight> weightMap = ElTagUtils.getWeightMap();
        		Weight weight = null;
        		for (String lottery_code : myCheckbox) {
        			weight = weightMap.get(lottery_code);
        			String newPostCode = Utils.formatStr(request.getParameter("postCode" + lottery_code), weight.getDefaultPostCode());
        			if (Utils.isNotEmpty(weight) && !weight.getDefaultPostCode().equals(newPostCode)) {
        				setManagesLog(request, action, weight.getName()+"("+lottery_code+")从<span style=\"color:#f00\">" + PostMap.getPost(weight.getDefaultPostCode()).getName() + "(" + weight.getDefaultPostCode() +")</span>切换到" + "<span style=\"color:#f00\">" + PostMap.getPost(newPostCode).getName() + "(" + newPostCode +")</span>", Constants.MANAGER_LOG_SYS_MESSAGE);
        				weight.setDefaultPostCode(newPostCode);
        				flushCache = true;
        			}
        		}
        		if (flushCache) {
	        		String weightMapJsonStr = JsonBinder.buildNonDefaultBinder().toJson(weightMap);
	        		memcachedClientWrapper.set("weight_map_value", 0, weightMapJsonStr);
        		}
        		List<Weight> weightList = ElTagUtils.getWeightList();
            	if (Utils.isNotEmpty(weightList)) {
            		request.setAttribute("weightList", weightList);
            		request.setAttribute("alertAmountList", alertAmountList);
            	} else {
            		request.setAttribute("msg", "取缓冲数据失败");
            	}
        	}
        	request.getRequestDispatcher("/manages/system/viewControlWeight.jsp").forward(request, response);
        	return;
        } else if (RELOAD_FILE_WEIGHT.equals(action)) {
        	WeightMap.forInstance();
    		List<Weight> weightList = ElTagUtils.getWeightList();
        	if (Utils.isNotEmpty(weightList)) {
        		request.setAttribute("weightList", weightList);
        		request.setAttribute("alertAmountList", alertAmountList);
				setManagesLog(request, action, "<span style=\"color:#f00\">加载出票口配置文件</span>", Constants.MANAGER_LOG_SYS_MESSAGE);
        	} else {
        		request.setAttribute("msg", "加载配置文件数据失败");
        	}
        	request.getRequestDispatcher("/manages/system/viewControlWeight.jsp").forward(request, response);
        	return;
        }
    }
}
