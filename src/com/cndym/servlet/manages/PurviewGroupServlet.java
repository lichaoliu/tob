package com.cndym.servlet.manages;

import com.cndym.bean.sys.Manages;
import com.cndym.bean.sys.PurviewGroup;
import com.cndym.bean.sys.PurviewGroupToPurview;
import com.cndym.bean.sys.PurviewUrl;
import com.cndym.constant.Constants;
import com.cndym.service.IManagesToPurviewGroupService;
import com.cndym.service.IPurviewGroupService;
import com.cndym.service.IPurviewGroupToPurviewService;
import com.cndym.service.IPurviewUrlService;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import com.cndym.utils.hibernate.PageBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * 作者：邓玉明
 * 时间：11-6-28 下午3:10
 * QQ：757579248
 * email：cndym@163.com
 */
public class PurviewGroupServlet extends BaseManagesServlet {
    private final static String LIST = "list";
    private final static String ADD = "add";
    private final static String EDIT = "edit";
    private final static String EDIT_NEW1 = "editNew1";
    private final static String EDIT_NEW = "editNew";
    private final static String DELETE = "del";
    private final static String ADD_PURVIEW = "addPurview";
    private final static String ADD_PURVIEW_NEW = "addPurviewNew";
    private final static String DEL_PURVIEW = "delPurview";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.addHeader("cache-control", "no-cache");
        response.addHeader("expires", "thu,  01 jan   1970 00:00:01 gmt");
        PrintWriter out = response.getWriter();
        String action = Utils.formatStr(request.getParameter("action"));
        IPurviewGroupService purviewGroupService = (IPurviewGroupService) SpringUtils.getBean("purviewGroupServiceImpl");
        IPurviewGroupToPurviewService purviewGroupToPurviewService = (IPurviewGroupToPurviewService) SpringUtils.getBean("purviewGroupToPurviewServiceImpl");
        IPurviewUrlService purviewUrlService = (IPurviewUrlService) SpringUtils.getBean("purviewUrlServiceImpl");

        if (LIST.equals(action)) {
            Integer page = Utils.formatInt(request.getParameter("page"), 1);
            request.setAttribute("page", page);
            Integer pageSize = Utils.formatInt(request.getParameter("pageSize"), 50);
            request.setAttribute("pageSize", pageSize);
            PageBean pageBean = purviewGroupService.getPageBeanByPara(null, page, pageSize);
            request.setAttribute("pageBean", pageBean);
            String resultMsg = Utils.formatStr(request.getParameter("resultMsg"));
            if (Utils.isNotEmpty(resultMsg)) {
                request.setAttribute("resultMsg", "true".equals(resultMsg) ? "修改成功" : "修改失败");
            }
            request.getRequestDispatcher("systemMonitoring/getPurviewGroupList.jsp").forward(request, response);
        } else if (EDIT.equals(action)) {
            Long aLong = Utils.formatLong(request.getParameter("id"));
            PurviewGroup purviewGroup = purviewGroupService.findPurviewGroup(aLong);
            if (Utils.isNotEmpty(purviewGroup)) {
                List fatherList = purviewUrlService.getPurviewUrlFatherList();
                PurviewGroupToPurview para = new PurviewGroupToPurview();
                para.setPurviewGroupCode(purviewGroup.getPurviewGroupCode());
                List<Map> purviews = purviewUrlService.getPurviewUrlListByGroup(purviewGroup.getPurviewGroupCode());
                Map<String, List<Map>> mapList = new HashMap<String, List<Map>>();
                for (Map map : purviews) {
                    String key = map.get("fcode").toString() + "," + map.get("name_father").toString();
                    if (mapList.containsKey(key)) {
                        mapList.get(key).add(map);
                    } else {
                        List<Map> maps = new ArrayList<Map>();
                        maps.add(map);
                        mapList.put(key, maps);
                    }
                }
                request.setAttribute("purviewGroup", purviewGroup);
                request.setAttribute("purviewsGroup", mapList);
                request.setAttribute("purviews", purviews);
                request.setAttribute("fatherList", fatherList);
                request.getRequestDispatcher("systemMonitoring/editPurviewGroup.jsp").forward(request, response);
            }
        } else if (EDIT_NEW1.equals(action)) {
            Long aLong = Utils.formatLong(request.getParameter("id"));
            //获取角色
            PurviewGroup purviewGroup = purviewGroupService.findPurviewGroup(aLong);
            if (Utils.isNotEmpty(purviewGroup)) {
                //获取父类列表
                PurviewGroupToPurview para = new PurviewGroupToPurview();
                para.setPurviewGroupCode(purviewGroup.getPurviewGroupCode());
                //获取权限列表
                List<Map> purviews = purviewUrlService.getPurviewUrlListByGroupNew(purviewGroup.getPurviewGroupCode());
                request.setAttribute("purviewGroup", purviewGroup);
                request.setAttribute("purviews", purviews);
                request.getRequestDispatcher("systemMonitoring/editPurviewGroupNew1.jsp").forward(request, response);
            }
        } else if (EDIT_NEW.equals(action)) {
            Long aLong = Utils.formatLong(request.getParameter("id"));
            Integer page = Utils.formatInt(request.getParameter("page"), 1);
            Integer pageSize = Utils.formatInt(request.getParameter("pageSize"), 50);
            PurviewGroup purviewGroup = purviewGroupService.findPurviewGroup(aLong);
            if (Utils.isNotEmpty(purviewGroup)) {
                PurviewGroupToPurview para = new PurviewGroupToPurview();
                para.setPurviewGroupCode(purviewGroup.getPurviewGroupCode());
                PageBean pageBean = purviewGroupToPurviewService.getPageBeanByPara(para, page, pageSize);
                List purviewUrlList = purviewUrlService.getPurviewUrlList();
                List purviewFathers = purviewUrlService.getPurviewUrlFatherList();
                request.setAttribute("pageBean", pageBean);
                StringBuffer adminPurview = new StringBuffer("");
                for (Object obj : pageBean.getPageContent()) {
                    Object[] o = (Object[]) obj;
                    PurviewUrl purviewUrl = (PurviewUrl) o[1];
                    adminPurview.append(purviewUrl.getCode());
                    adminPurview.append(",");
                }
                request.setAttribute("adminPurview", adminPurview.toString());
                request.setAttribute("purviewFathers", purviewFathers);
                request.setAttribute("purviewGroup", purviewGroup);
                request.setAttribute("purviewUrlList", purviewUrlList);
                request.getRequestDispatcher("systemMonitoring/editPurviewGroupNew.jsp").forward(request, response);
            }
        } else if (ADD.equals(action)) {
//            String name = Utils.formatStr(Utils.encodStr(request.getParameter("name")));
            String name = Utils.formatStr(request.getParameter("name"));
            PurviewGroup purviewGroup = new PurviewGroup();
            purviewGroup.setName(name);
            purviewGroup.setCreateTime(new Date());
            purviewGroup.setPurviewGroupCode(Utils.random(6));
            purviewGroupService.save(purviewGroup);
            this.setManagesLog(request, action, "角色<span style=\"color:#f00\">" + name + "</span>被添加", Constants.MANAGER_LOG_SYS_MESSAGE);
            response.sendRedirect("/manages/purviewGroupServlet?action=list");
        } else if (DELETE.equals(action)) {
//            String name = Utils.formatStr(request.getParameter("name"));
            String purviewGroupCode = Utils.formatStr(request.getParameter("purviewGroupCode"));
            if (Utils.isNotEmpty(purviewGroupCode)) {
                IManagesToPurviewGroupService managesToPurviewGroupService = (IManagesToPurviewGroupService) SpringUtils.getBean("managesToPurviewGroupServiceImpl");
                List<Object[]> objectList = managesToPurviewGroupService.getPageBeanByPara(purviewGroupCode);
                if (Utils.isNotEmpty(objectList) && objectList.size() > 0) {
                    StringBuffer managesNames = new StringBuffer("");
                    int i = 1;
                    for (Object[] obj : objectList) {
                        Manages manages = (Manages) obj[1];
                        managesNames.append(manages.getUserName());
                        if (i < objectList.size()) {
                            managesNames.append(" \\n");
                        }
                        i++;
                    }
                    request.setAttribute("managesNames", managesNames.toString());
                } else {
                    String name = purviewGroupService.findPurviewGroupBuyCode(purviewGroupCode);
                    this.setManagesLog(request, action, "角色<span style=\"color:#f00\">" + java.net.URLDecoder.decode(name, "UTF-8") + "</span>信息被删除", Constants.MANAGER_LOG_SYS_MESSAGE);
                    purviewGroupService.deletePurviewGroupByPurviewGroupCode(purviewGroupCode);
                }
            }
            PageBean pageBean = purviewGroupService.getPageBeanByPara(null, 1, Constants.PAGE_SIZE_50);
            request.setAttribute("page", 1);
            request.setAttribute("pageBean", pageBean);
            request.getRequestDispatcher("systemMonitoring/getPurviewGroupList.jsp").forward(request, response);
        } else if (ADD_PURVIEW.equals(action)) {
            String purviewCode = Utils.formatStr(request.getParameter("purviewCode"));
            Long aLong = Utils.formatLong(request.getParameter("id"));
            if (Utils.isNotEmpty(aLong) && Utils.isNotEmpty(purviewCode)) {
                PurviewGroup purviewGroup = purviewGroupService.findPurviewGroup(aLong);
                if (Utils.isNotEmpty(purviewGroup)) {
                    PurviewGroupToPurview purviewGroupToPurview = new PurviewGroupToPurview();
                    purviewGroupToPurview.setPurviewGroupCode(purviewGroup.getPurviewGroupCode());
                    purviewGroupToPurview.setPurviewCode(purviewCode);
                    purviewGroupToPurview.setCreateTime(new Date());
                    purviewGroupToPurviewService.save(purviewGroupToPurview);
                }
            }
            response.sendRedirect("/manages/purviewGroupServlet?action=edit&id=" + aLong + "");
        } else if (ADD_PURVIEW_NEW.equals(action)) {
            String[] qxList = request.getParameterValues("purviews");
            Long aLong = Utils.formatLong(request.getParameter("id"));
            if (Utils.isNotEmpty(aLong)) {
                PurviewGroup purviewGroup = purviewGroupService.findPurviewGroup(aLong);
                String purviewGroupCode = Utils.formatStr(request.getParameter("purviewGroupCode"));
                if (Utils.isNotEmpty(purviewGroup)) {
                    List list = new ArrayList();

                    purviewGroupToPurviewService.deleteByPurviewGroupCode(purviewGroupCode);
                    for (String purviewCode : qxList) {
                        PurviewGroupToPurview purviewGroupToPurview = new PurviewGroupToPurview();
                        purviewGroupToPurview.setPurviewGroupCode(purviewGroup.getPurviewGroupCode());
                        purviewGroupToPurview.setPurviewCode(purviewCode);
                        purviewGroupToPurview.setCreateTime(new Date());
                        list.add(purviewGroupToPurview);

                    }
                    purviewGroupToPurviewService.saveAllObject(list);
                    String name = purviewGroupService.findPurviewGroupBuyCode(purviewGroupCode);
                    this.setManagesLog(request, ADD_PURVIEW_NEW, "角色<span style=\"color:#f00\">" + java.net.URLDecoder.decode(name, "UTF-8") + "</span>被配置权限", Constants.MANAGER_LOG_SYS_MESSAGE);
                }
            }
            response.sendRedirect("/manages/purviewGroupServlet?action=list&resultMsg=true");
        } else if (DEL_PURVIEW.equals(action)) {
            Long id = Utils.formatLong(request.getParameter("id"));
            Long sid = Utils.formatLong(request.getParameter("sid"));
            purviewGroupToPurviewService.deleteById(sid);
            response.sendRedirect("/manages/purviewGroupServlet?action=edit&id=" + id + "");
        }
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
