package com.cndym.servlet.manages;

import com.cndym.bean.sys.*;
import com.cndym.bean.tms.MainIssue;
import com.cndym.bean.user.ManagesLog;
import com.cndym.constant.Constants;
import com.cndym.service.*;
import com.cndym.utils.DateUtils;
import com.cndym.utils.Md5;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import com.cndym.utils.hibernate.PageBean;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * *USER:MengJingyi
 * *TIME:2011 2011-5-24 下午10:24:22
 */

public class SystemManagesServlet extends BaseManagesServlet {
    private Logger logger = Logger.getLogger(getClass());

    private final static String ADMIN_LOGIN = "adminLogin";//管理登录
    private final static String EXIT = "exit";//用户退出
    private final static String UPDATE_PURVIEW = "updatePurview";//更新权限
    private final static String GET_PURVIEW_URL_LIST = "getPurviewUrlList";//获取权限列表
    private final static String INDEX_MESSAGE = "indexMessage";//获取首页数据
    private final static String LIST_PURVIEW_GROUP = "listPurviewGroup";
    private final static String ADD_PURVIEW_GROUP = "addPurviewGroup";
    private final static String DEL_PURVIEW_GROUP = "delPurviewGroup";
    private final static String DALETE_MANAGES = "deleteManages";
    private final static String GET_BUSINESS_LIST = "getBusinessMList";
    private final static String GET_ADMIN_BY_ID = "getAdminById";
    private final static String UPDATE_PURVIEW_GROUP = "updatePurviewGroup";

    private final static String UPDATE_COOPERATION_PWD = "updateCooperationPwd";

    private final static String PRE_ASSIGN_ROLE = "preCooperationAssignRole";

    private final static String ASSIGN_ROLE = "cooperationAssignRole";

    private final static String CHANGE_TICKET_OUTLET = "changeTicketOutlet";

    private final static String UNLOCK_JOB_STATUS = "unlockJobStatus";
    private final static String LOCK_JOB_STATUS = "lockJobStatus";
    private final static String START_JOB = "startJob";
    private final static String STOP_JOB = "stopJob";
    private final static String DELETE_LOCK = "deleteLock";
    private final static String GET_LOCK = "getLock";
    private final static String UPDATE_LOCK = "updateLock";
    private final static String SAVE_LOCK = "saveLock";

    private final static String EXCEPTION_HANDLE_11X5_OPEN = "exceptionHandle11x5Open";
    private final static String EXCEPTION_HANDLE_11X5_DO = "exceptionHandle11x5ODo";

    private final static String ADD_PURVIEW = "addPurview";

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = Utils.formatStr(request.getParameter("action"));

        Integer page = Utils.formatInt(request.getParameter("page"), 1);
        Integer pageSize = Utils.formatInt(request.getParameter("pageSize"), Constants.PAGE_SIZE_50);
        request.setAttribute("page", page);
        request.setAttribute("pageSize", pageSize);

        if ("getLogList".equals(action)) {
            IManagesLogService managesLogService = (IManagesLogService) SpringUtils.getBean("managesLogServiceImpl");
            ManagesLog managesLog = new ManagesLog();
            String startTime = Utils.formatStr(request.getParameter("startTime"));
            String endTime = Utils.formatStr(request.getParameter("endTime"));
            String adminName = Utils.formatStr(request.getParameter("adminName"));
            String ip = Utils.formatStr(request.getParameter("ip"));
            String descriptionMsg = Utils.formatStr(request.getParameter("descriptionMsg"));
            Integer operatingType = Utils.formatInt(request.getParameter("operatingType"), null);

            if (Utils.isNotEmpty(ip)) {
                managesLog.setIp(ip);
                request.setAttribute("ip", ip);
            }
            if (Utils.isNotEmpty(adminName)) {
                managesLog.setAdminName(adminName);
                request.setAttribute("adminName", adminName);
            }
            if (Utils.isNotEmpty(startTime)) {
                request.setAttribute("startTime", startTime);
                startTime = startTime + " 00:00:00";

            }
            Date endDate = null;
            if (Utils.isNotEmpty(endTime)) {
                request.setAttribute("endTime", endTime);
                Calendar calendar = Calendar.getInstance();
                Date endT = DateUtils.formatStr2Date(endTime + " 00:00:00");
                calendar.setTime(endT);
                calendar.add(Calendar.DATE, 1);
                endDate = calendar.getTime();

            }
            if (Utils.isNotEmpty(operatingType)) {
                managesLog.setOperatingType(operatingType);
                request.setAttribute("operatingType", operatingType);
            }
            if (Utils.isNotEmpty(descriptionMsg)) {
                managesLog.setDescription(descriptionMsg);
                request.setAttribute("descriptionMsg", descriptionMsg);
            }

            request.setAttribute("page", page);
            PageBean pageBean = managesLogService.findManagesLogLogList(managesLog, Utils.formatDate(startTime), endDate, page, pageSize);
            request.setAttribute("pageBean", pageBean);
            request.getRequestDispatcher("systemMonitoring/getLogList.jsp").forward(request, response);
            return;
        } else if ("getAdminList".equals(action)) {
            IManagesService managesService = (IManagesService) SpringUtils.getBean("managesServiceImpl");
            String adminName = Utils.formatStr(request.getParameter("adminName"));
            String purviewGroupCode = Utils.formatStr(request.getParameter("purviewGroupCode"));
            String mobile = Utils.formatStr(request.getParameter("mobile"));
            String realName = Utils.formatStr(request.getParameter("realName"));
            String status = Utils.formatStr(request.getParameter("status"));
            String post = Utils.formatStr(request.getParameter("post"));
            String departments = Utils.formatStr(request.getParameter("departments"));
            String adminType = Utils.formatStr(request.getParameter("adminType"));
            Manages manages = new Manages();
            if (Utils.isNotEmpty(adminName)) {
                request.setAttribute("adminName", adminName);
                manages.setUserName(adminName);
            }
            if (Utils.isNotEmpty(mobile)) {
                request.setAttribute("mobile", mobile);
                manages.setMobile(mobile);
            }
            if (Utils.isNotEmpty(realName)) {
                request.setAttribute("realName", realName);
                manages.setRealName(realName);
            }
            if (Utils.isNotEmpty(status)) {
                request.setAttribute("status", status);
                manages.setStatus(new Integer(status));
            }
            if (Utils.isNotEmpty(post)) {
                request.setAttribute("post", post);
                manages.setPost(post);
            }
            if (Utils.isNotEmpty(departments)) {
                request.setAttribute("departments", departments);
                manages.setDepartments(departments);
            }
            if (Utils.isNotEmpty(purviewGroupCode)) {
                request.setAttribute("purviewGroupCode", purviewGroupCode);
            }
            String resultMsg = request.getParameter("resultMsg");
            if (Utils.isNotEmpty(resultMsg)) {
                if ("true".equals(resultMsg)) {
                    request.setAttribute("resultMsg", "修改成功");
                } else {
                    request.setAttribute("resultMsg", "修改失败");
                }
            }
            request.setAttribute("page", page);
            PageBean pageBean = managesService.getManagesList(manages, page, pageSize, purviewGroupCode);
            request.setAttribute("pageBean", pageBean);
            IManagesToPurviewGroupService managesToPurviewGroupService = (IManagesToPurviewGroupService) SpringUtils.getBean("managesToPurviewGroupServiceImpl");
            List<PurviewGroup> purviewGroupList = managesToPurviewGroupService.getPurviewGroupList();
            request.setAttribute("purviewGroupList", purviewGroupList);
            request.getRequestDispatcher("systemMonitoring/getManagesList.jsp").forward(request, response);
            return;
        } else if (GET_ADMIN_BY_ID.equals(action)) {
            IManagesService managesService = (IManagesService) SpringUtils.getBean("managesServiceImpl");
            IManagesToPurviewGroupService managesToPurviewGroupService = (IManagesToPurviewGroupService) SpringUtils.getBean("managesToPurviewGroupServiceImpl");
            String adminId = Utils.formatStr(request.getParameter("adminId"));
            if (Utils.isNotEmpty(adminId)) {
                Manages manages = managesService.getAdminById(adminId);
                if (Utils.isNotEmpty(manages)) {
                    ManagesToPurviewGroup para = new ManagesToPurviewGroup();
                    para.setManagerId(manages.getId());
                    PageBean pageBean = managesToPurviewGroupService.getPageBeanByPara(para, 1, Constants.PAGE_SIZE_50);
                    List<PurviewGroup> purviewGroupList = managesToPurviewGroupService.getPurviewGroupList();
                    request.setAttribute("manages", manages);
                    request.setAttribute("pageBean", pageBean);
                    String purviewCode = "";
                    if (Utils.isNotEmpty(pageBean) && Utils.isNotEmpty(pageBean.getPageContent())) {
                        Object[] objs = (Object[]) pageBean.getPageContent().get(0);
                        ManagesToPurviewGroup managesToPurviewGroup = (ManagesToPurviewGroup) objs[0];
                        purviewCode = managesToPurviewGroup.getPurviewGroupCode();
                    }
                    request.setAttribute("purviewCode", purviewCode);
                    request.setAttribute("purviewGroupList", purviewGroupList);
                }
                request.getRequestDispatcher("systemMonitoring/updateManagesMessage.jsp").forward(request, response);
                return;
            }
        } else if ("updateManages".equals(action)) {
            IManagesService managesService = (IManagesService) SpringUtils.getBean("managesServiceImpl");
            String adminId = Utils.formatStr(request.getParameter("adminId"));
            String post = Utils.formatStr(request.getParameter("post"));
//                post = Utils.encodStr(post);
            String departments = Utils.formatStr(request.getParameter("departments"));
//                departments = Utils.encodStr(departments);
            String phone = Utils.formatStr(request.getParameter("phone"));
            String mobile = Utils.formatStr(request.getParameter("mobile"));
            String email = Utils.formatStr(request.getParameter("email"));
            String password = Utils.formatStr(request.getParameter("password"));
            String oldPassword = Utils.formatStr(request.getParameter("oldPassword"));
            String status = Utils.formatStr(request.getParameter("status"));
            String type = Utils.formatStr(request.getParameter("type"));
            String userName = Utils.formatStr(request.getParameter("userName"));
            String realName = Utils.formatStr(request.getParameter("realName"));
            if ("cz".equals(type)) {
                password = "123456";
            }
            Manages manages = new Manages();
            if (Utils.isNotEmpty(adminId)) {
                manages.setId(new Long(adminId));
            }
            if (Utils.isNotEmpty(post)) {
                manages.setPost(post);
                request.setAttribute("post", post);
            }
            if (Utils.isNotEmpty(departments)) {
                manages.setDepartments(departments);
                request.setAttribute("departments", departments);
            }
            if (Utils.isNotEmpty(phone)) {
                manages.setPhone(phone);
                request.setAttribute("phone", phone);
            }
            if (Utils.isNotEmpty(mobile)) {
                manages.setMobile(mobile);
                request.setAttribute("mobile", mobile);
            }
            if (Utils.isNotEmpty(email)) {
                manages.setEmail(email);
                request.setAttribute("email", email);
            }
            if (Utils.isNotEmpty(realName)) {
                manages.setRealName(realName);
                request.setAttribute("realName", realName);
            }

            if (Utils.isNotEmpty(password)) {
                manages.setPassWord(Md5.Md5(password));
            }
            if (Utils.isNotEmpty(status)) {
                manages.setStatus(new Integer(status));
            }
            if (Utils.isNotEmpty(oldPassword)) {
                Manages admin = (Manages) request.getSession().getAttribute("adminUser");
                if (!admin.getPassWord().equals(Md5.Md5(oldPassword))) {
                    request.setAttribute("msg", "原密码错误");
                    request.getRequestDispatcher("systemMonitoring/updateAdminPassword.jsp").forward(request, response);
                    return;
                }
            }
            int count = managesService.updateAdmin(manages);
            String description = "";
            String resultMsg = "";

            if (count > 0) {
                resultMsg = "true";
                request.setAttribute("msg", "修改管理员信息成功");
                String uName = managesService.getUserNameById(adminId);
                if ("cz".equals(type) || "xgmm".equals(type)) {
                    description = "用户<span style=\"color:#f00\">" + uName + "</span>密码被修改";
                } else {
                    description = "用户<span style=\"color:#f00\">" + uName + "</span>信息被编辑";
                }
            } else {
                resultMsg = "false";
                request.setAttribute("msg", "修改管理员信息失败");
                description = "修改失败";
            }
            setManagesLog(request, action, description, Constants.MANAGER_LOG_SYS_MESSAGE);
            PageBean pageBean = managesService.getAdminList(manages, 1, Constants.PAGE_SIZE_50);
            if ("xgzl".equals(type)) {
                if (Utils.isNotEmpty(pageBean.getPageContent().get(0))) {
                    IManagesToPurviewGroupService managesToPurviewGroupService = (IManagesToPurviewGroupService) SpringUtils.getBean("managesToPurviewGroupServiceImpl");
                    ManagesToPurviewGroup para = new ManagesToPurviewGroup();
                    para.setManagerId(manages.getId());
                    PageBean pageBeanM = managesToPurviewGroupService.getPageBeanByPara(para, 1, Constants.PAGE_SIZE_50);
                    List<PurviewGroup> purviewGroupList = managesToPurviewGroupService.getPurviewGroupNo(manages.getId());
                    request.setAttribute("pageBean", pageBeanM);
                    request.setAttribute("purviewGroupList", purviewGroupList);
                }
                request.getSession().setAttribute("manages", pageBean.getPageContent().get(0));
                String purviewGroupCode = Utils.formatStr(request.getParameter("purviewGroupCode"));
                response.sendRedirect("/manages/systemManagesServlet?action=updatePurviewGroup&purviewGroupCode=" + purviewGroupCode + "&id=" + adminId);
                return;
            } else if ("xg".equals(type)) {
                request.getSession().setAttribute("adminUser", pageBean.getPageContent().get(0));
                if (Utils.isNotEmpty(oldPassword)) {
                    request.getRequestDispatcher("systemMonitoring/updateAdminPassword.jsp").forward(request, response);
                    return;
                }
                request.getRequestDispatcher("systemMonitoring/updateManages.jsp").forward(request, response);
            } else {
                response.sendRedirect("/manages/systemManagesServlet?action=getAdminList&resultMsg=" + resultMsg);
            }
            return;
        } else if ("addAdmin".equals(action)) {
            IManagesService managesService = (IManagesService) SpringUtils.getBean("managesServiceImpl");
            String post = Utils.formatStr(request.getParameter("post"));
//                post = Utils.encodStr(post);
            String departments = Utils.formatStr(request.getParameter("departments"));
//                departments = Utils.encodStr(departments);
            String phone = Utils.formatStr(request.getParameter("phone"));
            String mobile = Utils.formatStr(request.getParameter("mobile"));
            String email = Utils.formatStr(request.getParameter("email"));
            String password = Utils.formatStr(request.getParameter("password"));
            String status = Utils.formatStr(request.getParameter("status"));
            String realName = Utils.formatStr(request.getParameter("realName"));
            String userName = Utils.formatStr(request.getParameter("adminName"));

            Manages manages = new Manages();
            StringBuffer sb = new StringBuffer();
            if (Utils.isNotEmpty(post)) {
                manages.setPost(post);
                request.setAttribute("post", post);
                sb.append(",post=" + post);
            }
            if (Utils.isNotEmpty(userName)) {
                manages.setUserName(userName);
                request.setAttribute("userName", userName);
                sb.append(",userName=" + userName);
            }
            if (Utils.isNotEmpty(departments)) {
                manages.setDepartments(departments);
                request.setAttribute("departments", departments);
                sb.append(",departments=" + departments);
            }
            if (Utils.isNotEmpty(phone)) {
                manages.setPhone(phone);
                request.setAttribute("phone", phone);
                sb.append(",phone=" + phone);
            }
            if (Utils.isNotEmpty(mobile)) {
                manages.setMobile(mobile);
                request.setAttribute("mobile", mobile);
                sb.append(",mobile=" + mobile);
            }
            if (Utils.isNotEmpty(email)) {
                manages.setEmail(email);
                request.setAttribute("email", email);
                sb.append(",email=" + email);
            }
            if (Utils.isNotEmpty(password)) {
                manages.setPassWord(Md5.Md5(password));
            }
            if (Utils.isNotEmpty(status)) {
                manages.setStatus(new Integer(status));
                request.setAttribute("status", status);
                sb.append(",status=" + status);
            }
            if (Utils.isNotEmpty(realName)) {
                manages.setRealName(realName);
                request.setAttribute("realName", realName);
                sb.append(",realName=" + realName);
            }

            manages.setCreateTime(new Date());
            manages.setStatus(1);
            manages.setType(Constants.MANAGES_STATUS);
            Integer result = managesService.doSaveManages(manages);
            if (result == 1) {
//                setManagesLog(request, action, "新建管理员信息成功msg=" + sb.toString(), Constants.MANAGER_LOG_SYS_MESSAGE);
                response.sendRedirect("/manages/systemManagesServlet?action=getAdminList");
            } else if (result == 2) {
                request.setAttribute("msg", "信息有重复，请您重新输入！");
//                setManagesLog(request, action, "新建管理员失败", Constants.MANAGER_LOG_SYS_MESSAGE);
                request.getRequestDispatcher("/manages/systemMonitoring/addAdmin.jsp").forward(request, response);
            } else {
                request.setAttribute("msg", "新建管理员失败");
//                setManagesLog(request, action, "新建管理员失败", Constants.MANAGER_LOG_SYS_MESSAGE);
                request.getRequestDispatcher("/manages/systemMonitoring/addAdmin.jsp").forward(request, response);
            }
            return;
        } else if (ADMIN_LOGIN.equals(action)) {
            IManagesService managesService = (IManagesService) SpringUtils.getBean("managesServiceImpl");
            String adminName = Utils.formatStr(request.getParameter("adminName"));
            String password = Utils.formatStr(request.getParameter("password"));
            String adminType = Utils.formatStr(request.getParameter("adminType"));
            String verifyCode = Utils.formatStr(request.getParameter("verifyCode"));
            //String cooCode = (String) request.getSession().getAttribute("code");

            Cookie[] c = request.getCookies();// 获取cookie[]组
            String cooCode = Utils.getCookieValue(c, "code", "");// 获取cookie值
            if (Utils.isNotEmpty(verifyCode)) {
                verifyCode = Md5.Md5(verifyCode);
                if (!verifyCode.equals(cooCode)) {
                    request.setAttribute("msg", "验证码错误!");
                    request.getRequestDispatcher("/manages/login.jsp").forward(request, response);
                    return;
                }
            } else {
                request.setAttribute("msg", "验证码错误!");
                request.getRequestDispatcher("/manages/login.jsp").forward(request, response);
                return;
            }
            if (Utils.isNotEmpty(adminName) && Utils.isNotEmpty(password)) {
                Manages manages = managesService.adminLoginByType(adminName, Md5.Md5(password), adminType);
                if (Utils.isNotEmpty(manages)) {
                    if (!manages.getUserName().equals("ycadministrator")) {
                        IManagesToPurviewGroupService managesToPurviewGroupService = (IManagesToPurviewGroupService) SpringUtils.getBean("managesToPurviewGroupServiceImpl");
                        ManagesToPurviewGroup para = new ManagesToPurviewGroup();
                        para.setManagerId(manages.getId());
                        PageBean pageBean = managesToPurviewGroupService.getPageBeanByPara(para, 1, Constants.PAGE_SIZE_50);
                        if (Utils.isNotEmpty(pageBean) && Utils.isNotEmpty(pageBean.getPageContent())) {
                            Object[] objs = (Object[]) pageBean.getPageContent().get(0);
                            ManagesToPurviewGroup managesToPurviewGroup = (ManagesToPurviewGroup) objs[0];
                            String purviewCode = managesToPurviewGroup.getPurviewGroupCode();
                            if (purviewCode.equals("785451")) {
                                request.setAttribute("msg", "用户不存在!");
                                request.getRequestDispatcher("/manages/login.jsp").forward(request, response);
                                return;
                            }
                        } else if (manages.getType() != 2) {
                            request.setAttribute("msg", "用户无操作权限!");
                            request.getRequestDispatcher("/manages/login.jsp").forward(request, response);
                            return;
                        }
                    }
                    Date loginTime = manages.getLoginTime();
                    request.getSession(true).setAttribute("loginTime", loginTime);
                    request.getSession().setAttribute("adminUser", manages);
                    manages.setLoginTime(new Date());
                    manages.setLoginIp(Utils.getIpAddress(request));
                    managesService.update(manages);
                    setManagesLog(request, action, "管理员<span style=\"color:#f00\">" + adminName + "</span>登录成功", Constants.MANAGER_LOG_LONGIN);
                    List<String> adminMsg = managesService.doGetAdminLoginMsg();
                    request.getSession().setAttribute("adminMsg", adminMsg);
                    request.getSession().setAttribute("adminName", adminName);
                    if (manages.getType() == Constants.MANAGES_STATUS) {
                        response.sendRedirect("default.jsp");
                    } else {
                        response.sendRedirect("/coop/default.jsp");
                    }

                    return;
                }
            }
            request.setAttribute("msg", "用户名或密码错误!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        } else if (UPDATE_PURVIEW.equals(action)) {
            IManagesService managesService = (IManagesService) SpringUtils.getBean("managesServiceImpl");
            IPurviewService purviewService = (IPurviewService) SpringUtils.getBean("purviewServiceImpl");
            String[] newCodes = request.getParameterValues("newCodes");
            String adminId = Utils.formatStr(request.getParameter("adminId"));
            request.setAttribute("adminId", adminId);
            List list = new ArrayList();
            String codeFather = "";
            String index = "1-";
            String i = "";
            if (!Utils.isNotEmpty(newCodes)) {
                boolean deleteResult = purviewService.deleteByAdminId(adminId);
                if (deleteResult) {
                    logger.info("删除用户所有权限");
                }
            } else {
                for (String newCode : newCodes) {
                    if (!i.equals(index + codeFather)) {
                        codeFather = newCode.substring(0, 1);
                        boolean deleteResult = purviewService.deleteByAdminIdAndIndex(adminId, codeFather);
                        if (deleteResult) {
                            logger.info("删除用户" + codeFather + "权限");
                        }
                        i = index + codeFather;
                    }
                    Purview p = purviewService.getPurview(adminId, newCode);
                    if (!Utils.isNotEmpty(p)) {
                        Purview purview = new Purview();
                        purview.setCreateTime(new Date());
                        purview.setManagerId(new Long(adminId));
                        purview.setPurviewCode(newCode);
                        list.add(purview);
                    }
                }
            }
            boolean result = false;
            if (Utils.isNotEmpty(list)) {
                result = managesService.saveAllObject(list);
            }
            Purview purview = new Purview();
            if (Utils.isNotEmpty(adminId)) {
                request.setAttribute("adminId", adminId);
                purview.setManagerId(new Long(adminId));
            }
            IPurviewUrlService purviewUrlService = (IPurviewUrlService) SpringUtils.getBean("purviewUrlServiceImpl");
            PageBean pageBean = purviewUrlService.getPurviewUrlList("");
            request.setAttribute("pageBean", pageBean);
            PageBean purviewUrlBean = purviewService.getAdminPurviewUrlList(purview);
            request.setAttribute("purviewUrlBean", purviewUrlBean);
            request.getRequestDispatcher("systemMonitoring/getPurviewList.jsp").forward(request, response);
            return;
        } else if (GET_PURVIEW_URL_LIST.equals(action)) {
            String codeIndex1 = Utils.formatStr(request.getParameter("codeIndex1"));
            String adminId = Utils.formatStr(request.getParameter("adminId"));
            request.setAttribute("adminId", adminId);
            IPurviewUrlService purviewUrlService = (IPurviewUrlService) SpringUtils.getBean("purviewUrlServiceImpl");
            IPurviewService purviewService = (IPurviewService) SpringUtils.getBean("purviewServiceImpl");
            PageBean pageBean = purviewUrlService.getPurviewUrlList(codeIndex1);
            request.setAttribute("pageBean", pageBean);
            Purview purview = new Purview();
            if (Utils.isNotEmpty(adminId)) {
                request.setAttribute("adminId", adminId);
                purview.setManagerId(new Long(adminId));
            }
            PageBean purviewUrlBean = purviewService.getAdminPurviewUrlList(purview);
            request.setAttribute("purviewUrlBean", purviewUrlBean);
            request.getRequestDispatcher("systemMonitoring/getPurviewList.jsp").forward(request, response);
            return;
        } else if (INDEX_MESSAGE.equals(action)) {
            IManagesService managesService = (IManagesService) SpringUtils.getBean("managesServiceImpl");
            List<String> adminMsg = managesService.doGetAdminLoginMsg();
            request.setAttribute("adminMsg", adminMsg);
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        } else if (LIST_PURVIEW_GROUP.equals(action)) {
            Long id = Utils.formatLong(request.getParameter("id"));
            String type = Utils.formatStr(request.getParameter("type"));
            IManagesService managesService = (IManagesService) SpringUtils.getBean("managesServiceImpl");
            IManagesToPurviewGroupService managesToPurviewGroupService = (IManagesToPurviewGroupService) SpringUtils.getBean("managesToPurviewGroupServiceImpl");
            Manages manages = managesService.getManagesById(id);
            if (Utils.isNotEmpty(manages)) {
                ManagesToPurviewGroup para = new ManagesToPurviewGroup();
                para.setManagerId(manages.getId());
                PageBean pageBean = managesToPurviewGroupService.getPageBeanByPara(para, page, pageSize);
                List<PurviewGroup> purviewGroupList = managesToPurviewGroupService.getPurviewGroupNo(manages.getId());
                request.setAttribute("manages", manages);
                request.setAttribute("pageBean", pageBean);
                request.setAttribute("purviewGroupList", purviewGroupList);
                if ("update".equals(type)) {
                    request.getRequestDispatcher("systemMonitoring/updateManagesMessage.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("systemMonitoring/editPurview.jsp").forward(request, response);

                }
                return;
            }
        } else if (ADD_PURVIEW_GROUP.equals(action)) {
            IManagesToPurviewGroupService managesToPurviewGroupService = (IManagesToPurviewGroupService) SpringUtils.getBean("managesToPurviewGroupServiceImpl");
            Long id = Utils.formatLong(request.getParameter("id"));
            String type = Utils.formatStr(request.getParameter("type"));
            String purviewGroupCode = Utils.formatStr(request.getParameter("purviewGroupCode"));
            ManagesToPurviewGroup managesToPurviewGroup = new ManagesToPurviewGroup();
            managesToPurviewGroup.setManagerId(id);
            managesToPurviewGroup.setCreateTime(new Date());
            managesToPurviewGroup.setPurviewGroupCode(purviewGroupCode);
            managesToPurviewGroupService.save(managesToPurviewGroup);
            response.sendRedirect("/manages/systemManagesServlet?action=listPurviewGroup&id=" + id + "&type=" + type);
            return;
        } else if (DEL_PURVIEW_GROUP.equals(action)) {
            IManagesToPurviewGroupService managesToPurviewGroupService = (IManagesToPurviewGroupService) SpringUtils.getBean("managesToPurviewGroupServiceImpl");
            Long id = Utils.formatLong(request.getParameter("id"));
            Long sid = Utils.formatLong(request.getParameter("sid"));
            String type = Utils.formatStr(request.getParameter("type"));
            managesToPurviewGroupService.deleteForId(sid);
            response.sendRedirect("/manages/systemManagesServlet?action=listPurviewGroup&id=" + id + "&type=" + type);
            return;
        } else if (DALETE_MANAGES.equals(action)) {
            IManagesService managesService = (IManagesService) SpringUtils.getBean("managesServiceImpl");
            Long managesId = Utils.formatLong(request.getParameter("id"));
            if (!Utils.isNotEmpty(managesId)) {
                request.getRequestDispatcher("error.jsp").forward(request, response);
                return;
            }
            String uName = managesService.getUserNameById(managesId + "");
            boolean result = managesService.doDelete(managesId);

            String description = "";
            if (result) {
                description = "用户<span style=\"color:#f00\">" + uName + "</span>信息被删除";
            } else {
                description = "删除失败";
            }
            setManagesLog(request, action, description, Constants.MANAGER_LOG_SYS_MESSAGE);
            request.getRequestDispatcher("systemManagesServlet?action=getAdminList").forward(request, response);
            return;
//        } else if (GET_BUSINESS_LIST.equals(action)) {
//            IProgramsService programsService = (ProgramsServiceImpl) SpringUtils.getBean("programsServiceImpl");
//            Map<String, Object> map = programsService.getBusinessMList();
//            request.setAttribute("drawCount", map.get("drawCount"));
//            request.setAttribute("fillCount", map.get("fillCount"));
//            request.setAttribute("winCount", map.get("winCount"));
//            request.setAttribute("lotteryList", map.get("lotteryList"));
//            request.getRequestDispatcher("systemMonitoring/getBusinessMList.jsp").forward(request, response);
//            return;
        } else if (UPDATE_COOPERATION_PWD.equals(action)) {
            IManagesService managesService = (IManagesService) SpringUtils.getBean("managesServiceImpl");
            String userName = Utils.formatStr(request.getParameter("userName"), null);
            String password = Utils.formatStr(request.getParameter("password"));
            String confirmPwd = Utils.formatStr(request.getParameter("confirmPwd"));
            if (!password.equals(confirmPwd)) {
                request.setAttribute("userName", userName);
                request.setAttribute("msg", "修改联盟管理员密码失败");
                request.getRequestDispatcher("/manages/cooperationManagesServlet?action=queryCooperation").forward(request, response);
                return;
            }
            Manages manages = null;
            if (Utils.isNotEmpty(userName)) {
                manages = managesService.getManagesByUserName(userName);
            }
            boolean success = false;
            if (Utils.isNotEmpty(manages)) {
                manages.setPassWord(Md5.Md5(password));
                success = managesService.update(manages);
            }

            if (success) {
                request.setAttribute("msg", "修改联盟管理员密码成功");
                setManagesLog(request, action, "联盟<span style=\"color:#f00\">" + userName + "</span>密码被重置", Constants.MANAGER_LOG_USER_MESSAGE);
            } else {
                request.setAttribute("msg", "修改联盟管理员密码失败");
                setManagesLog(request, action, "密码重置失败", Constants.MANAGER_LOG_USER_MESSAGE);
            }
            request.getRequestDispatcher("/manages/cooperationManagesServlet?action=queryCooperation").forward(request, response);
            return;
        } else if (UPDATE_PURVIEW_GROUP.equals(action)) {
            IManagesToPurviewGroupService managesToPurviewGroupService = (IManagesToPurviewGroupService) SpringUtils.getBean("managesToPurviewGroupServiceImpl");
            Long id = Utils.formatLong(request.getParameter("id"));
            if (!Utils.isNotEmpty(id)) {
                response.sendRedirect("/manages/systemManagesServlet?action=getAdminList");
                return;
            }
            managesToPurviewGroupService.deleteForManagesId(id);
            String purviewGroupCode = Utils.formatStr(request.getParameter("purviewGroupCode"));
            ManagesToPurviewGroup managesToPurviewGroup = new ManagesToPurviewGroup();
            managesToPurviewGroup.setManagerId(id);
            managesToPurviewGroup.setCreateTime(new Date());
            managesToPurviewGroup.setPurviewGroupCode(purviewGroupCode);
            boolean result = managesToPurviewGroupService.save(managesToPurviewGroup);
            response.sendRedirect("/manages/systemManagesServlet?action=getAdminList&resultMsg=" + result);
            return;
        } else if (EXIT.equals(action)) {
            Manages managesUser = (Utils.isNotEmpty(request.getSession().getAttribute("adminUser")) ? (Manages) request.getSession().getAttribute("adminUser") : null);
            setManagesLog(request, action, "管理员<span style=\"color:#f00\">" + managesUser.getUserName() + "</span>退出系统", Constants.MANAGER_LOG_LONGIN);
            request.getSession().removeAttribute("adminUser");
            request.getSession().removeAttribute("purviewMap");
            response.sendRedirect("/manages/index.jsp");
            return;
        } else if (UNLOCK_JOB_STATUS.equals(action)) {
            String name = Utils.formatStr(request.getParameter("name"));
            IDistributionLockService distributionLockService = (IDistributionLockService) SpringUtils.getBean("distributionLockServiceImpl");
            distributionLockService.updateStatus(name);

            DistributionLock param = new DistributionLock();
            param.setName(name);
            List<DistributionLock> list = distributionLockService.getDistributionLockList(param);
            if (null != list && list.size() > 0) {
                setManagesLog(request, action, list.get(0).getCode() + "解锁", Constants.MANAGER_LOG_SYS_MESSAGE);
            } else {
                setManagesLog(request, action, name + "解锁", Constants.MANAGER_LOG_SYS_MESSAGE);
            }
            request.getRequestDispatcher("system/sysQuartz.jsp").forward(request, response);
            return;
        } else if (LOCK_JOB_STATUS.equals(action)) {
            String name = Utils.formatStr(request.getParameter("name"));
            IDistributionLockService distributionLockService = (IDistributionLockService) SpringUtils.getBean("distributionLockServiceImpl");
            int result = distributionLockService.updateStatus(name, 1);

            DistributionLock param = new DistributionLock();
            param.setName(name);
            List<DistributionLock> list = distributionLockService.getDistributionLockList(param);
            if (null != list && list.size() > 0 && 1 == result) {
                setManagesLog(request, action, list.get(0).getCode() + "锁定成功", Constants.MANAGER_LOG_SYS_MESSAGE);
            } else {
                setManagesLog(request, action, name + "锁定失败", Constants.MANAGER_LOG_SYS_MESSAGE);
            }
            request.getRequestDispatcher("system/sysQuartz.jsp").forward(request, response);
            return;
        } else if (START_JOB.equals(action)) {
            String name = Utils.formatStr(request.getParameter("name"));
            IDistributionLockService distributionLockService = (IDistributionLockService) SpringUtils.getBean("distributionLockServiceImpl");
            distributionLockService.updateStop(name, 0);

            DistributionLock param = new DistributionLock();
            param.setName(name);
            List<DistributionLock> list = distributionLockService.getDistributionLockList(param);
            if (null != list && list.size() > 0) {
                setManagesLog(request, action, list.get(0).getCode() + "启用成功", Constants.MANAGER_LOG_SYS_MESSAGE);
            } else {
                setManagesLog(request, action, name + "启用成功", Constants.MANAGER_LOG_SYS_MESSAGE);
            }
            request.getRequestDispatcher("system/sysQuartz.jsp").forward(request, response);
            return;
        } else if (STOP_JOB.equals(action)) {
            String name = Utils.formatStr(request.getParameter("name"));
            IDistributionLockService distributionLockService = (IDistributionLockService) SpringUtils.getBean("distributionLockServiceImpl");
            int result = distributionLockService.updateStop(name, 1);

            DistributionLock param = new DistributionLock();
            param.setName(name);
            List<DistributionLock> list = distributionLockService.getDistributionLockList(param);
            if (null != list && list.size() > 0 && 1 == result) {
                setManagesLog(request, action, list.get(0).getCode() + "停用成功", Constants.MANAGER_LOG_SYS_MESSAGE);
            } else {
                setManagesLog(request, action, name + "停用失败", Constants.MANAGER_LOG_SYS_MESSAGE);
            }
            request.getRequestDispatcher("system/sysQuartz.jsp").forward(request, response);
            return;
        } else if (DELETE_LOCK.equals(action)) {
            String name = Utils.formatStr(request.getParameter("name"));
            IDistributionLockService distributionLockService = (IDistributionLockService) SpringUtils.getBean("distributionLockServiceImpl");
            DistributionLock param = new DistributionLock();
            param.setName(name);
            List<DistributionLock> list = distributionLockService.getDistributionLockList(param);
            if (Utils.isNotEmpty(list)) {
                boolean flag = distributionLockService.delete(list.get(0));
                if (flag) {
                    setManagesLog(request, action, "删除JOB(" + name + ")成功", Constants.MANAGER_LOG_SYS_MESSAGE);
                }
                request.getRequestDispatcher("system/sysQuartzManages.jsp").forward(request, response);
                return;
            }
        } else if (GET_LOCK.equals(action)) {
            String name = Utils.formatStr(request.getParameter("name"));
            if (Utils.isNotEmpty(name)) {
                IDistributionLockService distributionLockService = (IDistributionLockService) SpringUtils.getBean("distributionLockServiceImpl");
                DistributionLock param = new DistributionLock();
                param.setName(name);
                List<DistributionLock> list = distributionLockService.getDistributionLockList(param);
                if (Utils.isNotEmpty(list)) {
                    DistributionLock distributionLock = list.get(0);
                    request.setAttribute("distributionLock", distributionLock);
                }
            }
            request.getRequestDispatcher("system/updateQuartz.jsp").forward(request, response);
            return;
        } else if (UPDATE_LOCK.equals(action)) {
            String id = Utils.formatStr(request.getParameter("id"));
            String name = Utils.formatStr(request.getParameter("name"));
            String code = Utils.formatStr(request.getParameter("code"));
            IDistributionLockService distributionLockService = (IDistributionLockService) SpringUtils.getBean("distributionLockServiceImpl");
            DistributionLock param = new DistributionLock();
            boolean flag = false;
            String msg = "修改JOB(" + name + ")成功";
            if (Utils.isNotEmpty(id)) {
                DistributionLock distributionLock = new DistributionLock();
                param.setId(new Long(id));
                List<DistributionLock> list = distributionLockService.getDistributionLockList(param);
                if (Utils.isNotEmpty(list)) {
                    distributionLock = list.get(0);
                    distributionLock.setName(name);
                    distributionLock.setCode(code);
                }
                flag = distributionLockService.update(distributionLock);
            } else {
                param.setName(name);
                param.setCode(code);
                param.setStatus(0);
                param.setCreateTime(new Date());
                param.setUserName("127.0.0.1");
                flag = distributionLockService.save(param);
                msg = "添加JOB(" + name + ")成功";
            }
            if (flag) {
                setManagesLog(request, action, msg, Constants.MANAGER_LOG_SYS_MESSAGE);
            }
            request.getRequestDispatcher("system/sysQuartzManages.jsp").forward(request, response);
            return;
        } else if (EXCEPTION_HANDLE_11X5_OPEN.equals(action)) {
            IMainIssueService mainIssueService = (IMainIssueService) SpringUtils.getBean("mainIssueServiceImpl");
            String lotteryCode = "107";
            MainIssue nowParams = new MainIssue();
            nowParams.setLotteryCode(lotteryCode);
            nowParams.setStatus(Constants.ISSUE_STATUS_START);
            PageBean nowPage = mainIssueService.getIssueList(nowParams, 1, 1);
//            MainIssue sendParams = new MainIssue();
//            sendParams.setLotteryCode(lotteryCode);
//            sendParams.setEndTimeStart(new Date());
//            PageBean sendPage = mainIssueService.getIssueList(sendParams,1,1);
            MainIssue webParams = new MainIssue();
            webParams.setLotteryCode(lotteryCode);
            webParams.setEndTime(new Date());
            PageBean webPage = mainIssueService.getIssueList(webParams, 1, 1);
            if (null != nowPage && null != nowPage.getPageContent() && nowPage.getPageContent().size() > 0) {
                request.setAttribute("nowIssue", nowPage.getPageContent().get(0));
            }
//            if(null != sendPage && null != sendPage.getPageContent() && sendPage.getPageContent().size() > 0){
//                request.setAttribute("sendIssue",sendPage.getPageContent().get(0));
//            }
            if (null != webPage && null != webPage.getPageContent() && webPage.getPageContent().size() > 0) {
                request.setAttribute("webIssue", webPage.getPageContent().get(0));
            }
            request.getRequestDispatcher("system/exception11x5.jsp").forward(request, response);
            return;
//        } else if(EXCEPTION_HANDLE_11X5_DO.equals(action)){
//            IProgramsService programsService = (IProgramsService) SpringUtils.getBean("programsServiceImpl");
//            programsService.setAutoOrderToSendByDiy("107");
//            setManagesLog(request, action, "执行11选5追号异常处理", Constants.MANAGER_LOG_SYS_MESSAGE);
//
//            response.setContentType("text/html");
//            response.setCharacterEncoding("utf-8");
//            response.getWriter().print("<script>alert('操作成功!');window.close();</script>");
//            return;
        } else if (ADD_PURVIEW.equals(action)) {
            String fatherName = Utils.formatStr(request.getParameter("fatherName"));
            String name = Utils.formatStr(request.getParameter("name"));
            String code = Utils.formatStr(request.getParameter("code"));
            PurviewUrl purviewUrl = new PurviewUrl();
            IPurviewUrlService purviewUrlService = (IPurviewUrlService) SpringUtils.getBean("purviewUrlServiceImpl");
            Map<String, String> map = purviewUrlService.getPurviewUrlBycode(code);
            if (Utils.isNotEmpty(map)) {
                request.setAttribute("msg", "权限code已经存在");
            } else {
                if (Utils.isNotEmpty(fatherName)) {
                    String[] msg = fatherName.split("-");
                    purviewUrl.setCodeFather(msg[0]);
                    purviewUrl.setNameFather(msg[1]);
                }
                if (Utils.isNotEmpty(name)) {
                    purviewUrl.setName(name);
                }
                if (Utils.isNotEmpty(code)) {
                    purviewUrl.setCode(code);
                    purviewUrl.setSort(new Integer(code));
                }
                boolean flag = purviewUrlService.save(purviewUrl);
                if (flag) {
                    request.setAttribute("msg", "添加成功");
                } else {
                    request.setAttribute("msg", "添加失败");
                }
            }
            request.getRequestDispatcher("/manages/systemMonitoring/addPurview.jsp").forward(request, response);
            return;
        }
        request.getRequestDispatcher("error.jsp").forward(request, response);
    }

}
