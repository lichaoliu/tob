package com.cndym.servlet.manages;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.cndym.servlet.manages.BaseManagesServlet;
import com.cndym.utils.HttpClientUtils;
import com.cndym.utils.Utils;

public class ReloadConfigServlet extends BaseManagesServlet {

	private static final long serialVersionUID = -3219264302608758712L;
	private static final String RELOAD_FILE = "reloadFile";
	private static final String VIEW_VALUE = "viewValue";
	private static final String PACKAGE = "com.cndym.";
	private Logger logger = Logger.getLogger(getClass());

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");

		String host = Utils.formatStr(request.getParameter("host"));
		String name = Utils.formatStr(request.getParameter("name"));
		logger.info("action="+action+",host="+host+",name="+name);
		
		if (Utils.isNotEmpty(name) && RELOAD_FILE.equals(action)) {
			String path = PACKAGE + name;
			String keyMessage = read(host, path, "1");
			request.setAttribute("keyMessage", keyMessage);
		} else if (Utils.isNotEmpty(name) && VIEW_VALUE.equals(action)) {
			String path = PACKAGE + name;
			String keyMessage = read(host, path, "0");
			request.setAttribute("keyMessage", keyMessage);
		}
		request.setAttribute("name", name);
		request.setAttribute("host", host);
		request.getRequestDispatcher("/manages/system/reloadConfigView.jsp").forward(request, response);
		return;
	}

	public String read(String host, String path, String load) {
		String url = "http://" + host + ":8080/reloadConfig.jsp" + "?name=" + path + "&load="+load;
		logger.info("url="+url);
		try {
			HttpClientUtils hcu = new HttpClientUtils(url, "utf-8");
			String message = hcu.httpClientGet();
			logger.info("message=" + message);
			return message;
		} catch (Exception e) {
			logger.error(null, e);
		}
		return null;
	}
}