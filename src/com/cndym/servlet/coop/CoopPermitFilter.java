package com.cndym.servlet.coop;

import com.cndym.bean.sys.Manages;
import com.cndym.bean.sys.ManagesToPurviewGroup;
import com.cndym.bean.sys.PurviewGroupToPurview;
import com.cndym.bean.sys.PurviewUrl;
import com.cndym.service.IManagesToPurviewGroupService;
import com.cndym.service.IPurviewGroupToPurviewService;
import com.cndym.service.IPurviewUrlService;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import com.cndym.utils.hibernate.PageBean;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：MengJingyi
 * 时间：11-6-30 下午21:45
 * 权限拦截
 */

public class CoopPermitFilter extends HttpServlet implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(true);
        Manages coopUser = (Utils.isNotEmpty(session.getAttribute("coopUser")) ? (Manages) session.getAttribute("coopUser") : null);
        if (!Utils.isNotEmpty(coopUser)) {
            request.getRequestDispatcher("/coop/login.jsp").forward(request, response);
            return;
        }
        chain.doFilter(request, response);
        return;
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
