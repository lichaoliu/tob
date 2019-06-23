package com.cndym.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.cndym.admin.service.IAdminManagesService;
import com.cndym.bean.admin.AlertAmountTable;
import com.cndym.control.PostMap;
import com.cndym.control.bean.Post;
import com.cndym.sendClient.ISendClient;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import com.google.gson.Gson;

public class BalanceServlet extends HttpServlet {
    private static final long serialVersionUID = 1964463607240517674L;
    private Logger logger = Logger.getLogger(getClass());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.addHeader("cache-control", "no-cache");
        response.addHeader("expires", "thu,  01 jan   1970 00:00:01 gmt");
        PrintWriter out = response.getWriter();

        IAdminManagesService postTicketService = (IAdminManagesService) SpringUtils.getBean("adminManagesServiceImpl");
    	List<AlertAmountTable> alertAmountList = postTicketService.getAlertAmountList();
    	Map<String, Integer> postWorkMap = new HashMap<String, Integer>();
    	for(AlertAmountTable alertPost : alertAmountList) {
    		if (alertPost.getStatus().equals("1")) {
    			postWorkMap.put(alertPost.getPostCode(), 1);
    		}
    	}
    	
        List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
        logger.info("!!!!!blanace start !!!!!!!");
        List<Post> postList = PostMap.postList();
        for(Post post : postList){
        	if (postWorkMap.containsKey(post.getCode())) {
	        	 ISendClient sendClient = (ISendClient) SpringUtils.getBean(post.getPostClass());
	        	 Map<String, String> cqMap = new HashMap<String, String>();
	             cqMap.put("name", post.getName());
	             cqMap.put("code", post.getCode());
	             cqMap.put("balance", Utils.formatNumberZD(sendClient.accountQuery()));
	             mapList.add(cqMap);
        	}
        }
        ISendClient sendClient = (ISendClient) SpringUtils.getBean("ltkjSendClientImpl");
        Map<String, String> cqMap = new HashMap<String, String>();
        cqMap.put("name", "乐透科技高频出票口");
        cqMap.put("code", "26-1");
        cqMap.put("balance", Utils.formatNumberZD(sendClient.accountQuery("GP")));
        mapList.add(cqMap);
        
        Gson gson = new Gson();
        out.print(gson.toJson(mapList));
        logger.info(gson.toJson(mapList));
        out.flush();
        out.close();
        logger.info("!!!!!blanace end !!!!!!!");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
