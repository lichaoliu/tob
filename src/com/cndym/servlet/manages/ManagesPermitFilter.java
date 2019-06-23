package com.cndym.servlet.manages;

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

public class ManagesPermitFilter extends HttpServlet implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(true);
        IManagesToPurviewGroupService managesToPurviewGroupService = (IManagesToPurviewGroupService) SpringUtils.getBean("managesToPurviewGroupServiceImpl");
        IPurviewGroupToPurviewService purviewGroupToPurviewService = (IPurviewGroupToPurviewService) SpringUtils.getBean("purviewGroupToPurviewServiceImpl");

        Manages managesUser = (Utils.isNotEmpty(session.getAttribute("adminUser")) ? (Manages) session.getAttribute("adminUser") : null);
        if (Utils.isNotEmpty(managesUser)) {
            String adminId = "0";
            ManagesToPurviewGroup managesToPurviewGroup = null;
            Map purviewMap = new HashMap();
            if (Utils.isNotEmpty(managesUser)) {
                //超级管理员登录
                if (managesUser.getUserName().equals("ycadministrator")) {
                    IPurviewUrlService purviewUrlService = (IPurviewUrlService) SpringUtils.getBean("purviewUrlServiceImpl");
                    List<PurviewUrl> purviewList = purviewUrlService.getPurviewUrlList();
                    for(PurviewUrl purviewUrl : purviewList){
                       purviewMap.put(purviewUrl.getCode(), purviewUrl.getCode());
                    }
                } else {
                    managesToPurviewGroup = new ManagesToPurviewGroup();
                    adminId = managesUser.getId().toString();
                    managesToPurviewGroup.setManagerId(new Long(adminId));
                }
            }

            //普通管理员登录
            if (Utils.isNotEmpty(managesToPurviewGroup)) {
                List<Object[]> managesToPurviewGroups = managesToPurviewGroupService.getPageBeanByPara(managesToPurviewGroup);
                if (Utils.isNotEmpty(managesToPurviewGroups)) {
                    for (Object[] mtpg : managesToPurviewGroups) {
                        PurviewGroupToPurview purviewGroupToPurview = new PurviewGroupToPurview();
                        ManagesToPurviewGroup managesToPurviewGroup2 = (ManagesToPurviewGroup) mtpg[0];
                        purviewGroupToPurview.setPurviewGroupCode(managesToPurviewGroup2.getPurviewGroupCode());
                        PageBean pagesBean = purviewGroupToPurviewService.getPageBeanByPara(purviewGroupToPurview, 1, 100);
                        List<Object[]> purviewGroupToPurviews = pagesBean.getPageContent();
                        for (Object[] obj : purviewGroupToPurviews) {
                            PurviewGroupToPurview pgup = (PurviewGroupToPurview) obj[0];
                            if (!purviewMap.containsKey(pgup.getPurviewCode())) {
                                purviewMap.put(pgup.getPurviewCode(), pgup.getPurviewCode());
                            }
                        }
                    }

                }
            }
            session.setAttribute("purviewMap", purviewMap);
            chain.doFilter(request, response);
            return;
        }
        if (!Utils.isNotEmpty(managesUser)) {
            session.removeAttribute("purviewMap");
            request.getRequestDispatcher("/manages/login.jsp").forward(request, response);
            return;
        }
        chain.doFilter(request, response);
        return;
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
