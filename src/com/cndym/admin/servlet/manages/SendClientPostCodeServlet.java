package com.cndym.admin.servlet.manages;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cndym.admin.service.SendClientPostCodeService;
import com.cndym.bean.tms.SendClientPostCode;
import com.cndym.control.bean.Post;
import com.cndym.servlet.ElTagUtils;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import com.google.gson.Gson;

public class SendClientPostCodeServlet extends HttpServlet{
	
	 private final static String POST_CODE_LIST = "postCodeList";
	
    public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {

        this.doPost(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
    	String action = Utils.formatStr(request.getParameter("action"));
        response.setContentType("text/html");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        
        if(POST_CODE_LIST.equals(action)){
        	List<Post> postCodeList=ElTagUtils.getPostMaps();
        	Map<String, List<SendClientPostCode>> postMap=new HashMap<String, List<SendClientPostCode>>();
        	SendClientPostCodeService sendClientService= (SendClientPostCodeService) SpringUtils.getBean("sendClientPostCodeServiceImpl");
        	
        	for (Post post : postCodeList) {
				postMap.put(post.getName(), sendClientService.getSendClientPostCodeByPostCode(post.getCode()));
			}
        	Gson gson=new Gson();
        	System.out.println("gson====="+gson.toJson(postMap));
        	request.setAttribute("postMap", postMap);
        	request.getRequestDispatcher("/manages/system/sendClientPostCode.jsp").forward(request, response);
        }
    }
}
