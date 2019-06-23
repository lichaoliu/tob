package com.cndym.servlet.manages;

import com.cndym.bean.sys.Manages;
import com.cndym.bean.user.ManagesLog;
import com.cndym.service.IManagesLogService;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * User:MengJingyi
 * Date:2011-5-26
 * Time:下午05:55:10
 * Description:
 */
public class BaseManagesServlet extends HttpServlet {
    public void setManagesLog(HttpServletRequest request, String action, String description, Integer operatingType) {
        IManagesLogService managesLogService = (IManagesLogService) SpringUtils.getBean("managesLogServiceImpl");
        ManagesLog managesLog = new ManagesLog();
        managesLog.setCreateTime(new Date());
        managesLog.setIp(Utils.getIpAddress(request));
        String adminName = null;
        Manages manages = (Manages) request.getSession().getAttribute("adminUser");
        if (Utils.isNotEmpty(manages)) {
            adminName = ((Manages) request.getSession().getAttribute("adminUser")).getUserName();
        }
        managesLog.setType(action);
        managesLog.setAdminName(adminName);
        managesLog.setDescription(description);
        managesLog.setOperatingType(operatingType);
        managesLogService.save(managesLog);
    }

    public void setCooperationLog(HttpServletRequest request, String action, String description, Integer operatingType) {
        IManagesLogService managesLogService = (IManagesLogService) SpringUtils.getBean("managesLogServiceImpl");
        ManagesLog managesLog = new ManagesLog();
        managesLog.setCreateTime(new Date());
        managesLog.setIp(Utils.getIpAddress(request));
        String adminName = null;
        Manages manages = (Manages) request.getSession().getAttribute("unionUser");
        if (Utils.isNotEmpty(manages)) {
            adminName = manages.getUserName();
        }
        managesLog.setType(action);
        managesLog.setAdminName(adminName);
        managesLog.setDescription(description);
        managesLog.setOperatingType(operatingType);
        managesLogService.save(managesLog);
    }
}
