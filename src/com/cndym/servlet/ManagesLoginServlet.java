package com.cndym.servlet;

import com.cndym.bean.sys.Manages;
import com.cndym.bean.sys.ManagesToPurviewGroup;
import com.cndym.constant.Constants;
import com.cndym.service.IManagesService;
import com.cndym.service.IManagesToPurviewGroupService;
import com.cndym.service.SubIssueBonus.ISubIssueBonusOperator;
import com.cndym.servlet.manages.BaseManagesServlet;
import com.cndym.utils.Md5;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import com.cndym.utils.hibernate.PageBean;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

/**
 * User: mcs
 * Date: 12-11-11
 * Time: 下午6:54
 */
public class ManagesLoginServlet extends BaseManagesServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IManagesService managesService = (IManagesService) SpringUtils.getBean("managesServiceImpl");
        String adminName = Utils.formatStr(request.getParameter("adminName"));
        String password = Utils.formatStr(request.getParameter("password"));
        String adminType = Utils.formatStr(request.getParameter("adminType"));
        String verifyCode = Utils.formatStr(request.getParameter("verifyCode"));
        String loginPath = "/manages/login.jsp";
        if (adminType.equals(Constants.COOP_STATUS.toString())) {
            loginPath = "/coop/login.jsp";
        }

        Cookie[] c = request.getCookies();// 获取cookie[]组
        String cooCode = Utils.getCookieValue(c, "code", "");//获取cookie值
        if (Utils.isNotEmpty(verifyCode)) {
            verifyCode = Md5.Md5(verifyCode);
            if (!verifyCode.equals(cooCode)) {
                request.setAttribute("msg", "验证码错误!");
                request.getRequestDispatcher(loginPath).forward(request, response);
                return;
            }
        } else {
            request.setAttribute("msg", "验证码错误!");
            request.getRequestDispatcher(loginPath).forward(request, response);
            return;
        }
        if (Utils.isNotEmpty(adminName) && Utils.isNotEmpty(password)) {
            Manages manages = managesService.adminLoginByType(adminName, Md5.Md5(password), adminType);
            if (Utils.isNotEmpty(manages)) {
                Date loginTime = manages.getLoginTime();
                request.getSession(true).setAttribute("loginTime", loginTime);
                if (manages.getType().intValue() == Constants.MANAGES_STATUS) {
                    manages.setLoginTime(new Date());
                    manages.setLoginIp(Utils.getIpAddress(request));
                    managesService.update(manages);
                    request.getSession().setAttribute("adminUser", manages);
                    setManagesLog(request, "adminLogin", "管理员<span style=\"color:#f00\">" + adminName + "</span>登录成功", Constants.MANAGER_LOG_LONGIN);
                    response.sendRedirect("/manages/default.jsp");
                } else {
                    request.getSession().setAttribute("coopUser", manages);
                    response.sendRedirect("/coop/default.jsp");
                }
                return;
            }
        }
        request.setAttribute("msg", "用户名或密码错误!");
        request.getRequestDispatcher(loginPath).forward(request, response);
        return;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
