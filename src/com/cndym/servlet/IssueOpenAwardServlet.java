package com.cndym.servlet;

import com.cndym.bean.tms.MainIssue;
import com.cndym.service.IMainIssueService;
import com.cndym.servlet.manages.bean.BonusClass;
import com.cndym.utils.JsonBinder;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.type.TypeReference;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * User: mcs
 * Date: 12-12-5
 * Time: 上午10:41
 */
public class IssueOpenAwardServlet extends HttpServlet {


    private Logger logger = Logger.getLogger(getClass());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.addHeader("cache-control", "no-cache");
        response.addHeader("expires", "thu,  01 jan   1970 00:00:01 gmt");
        PrintWriter out = response.getWriter();
        String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
        String issue = Utils.formatStr(request.getParameter("issue"));
        if (!Utils.isNotEmpty(lotteryCode) || !Utils.isNotEmpty(issue)) {
            return;
        }
        IMainIssueService mainIssueService = (IMainIssueService) SpringUtils.getBean("mainIssueServiceImpl");
        MainIssue mainIssue = mainIssueService.getMainIssueByLotteryIssue(lotteryCode, issue);
        if (null==mainIssue || "-".equals(mainIssue.getBonusNumber()) || "".equals(mainIssue.getBonusNumber())) {
            mainIssue = mainIssueService.getMainIssueByLotteryIssue(lotteryCode);
        }
        Map<String, Object> returnMap = new HashMap<String, Object>();

        returnMap.put("lotteryCode", mainIssue.getLotteryCode());
        returnMap.put("lotteryName", ElTagUtils.getLotteryChinaName(mainIssue.getLotteryCode()));
        returnMap.put("issue", mainIssue.getName());
        returnMap.put("startTime", Utils.formatDate2Str(mainIssue.getStartTime(), "yyyy-MM-dd HH:mm:ss"));
        returnMap.put("endTime", Utils.formatDate2Str(mainIssue.getEndTime(), "yyyy-MM-dd HH:mm:ss"));
        returnMap.put("bonusTime", Utils.formatDate2Str(mainIssue.getBonusTime(), "yyyy-MM-dd HH:mm:ss"));
        returnMap.put("bonusNumber", mainIssue.getBonusNumber());
        returnMap.put("prizePool", mainIssue.getPrizePool());
        returnMap.put("globalSaleTotal", mainIssue.getGlobalSaleTotal());
        //大乐透生肖乐销量
        if (lotteryCode.equals("113")) {
            returnMap.put("backup2", mainIssue.getBackup2());
        }
        String bonusClassStr = mainIssue.getBonusClass();
        List<BonusClass> bonusClasses = new ArrayList<BonusClass>();
        if (Utils.isNotEmpty(bonusClassStr)) {
            bonusClassStr = bonusClassStr.replaceAll("'", "\"");
            try {
                List<Map> maps = JsonBinder.buildNonDefaultBinder().getMapper().readValue(bonusClassStr, new TypeReference<List<Map>>() {
                });
                for (Map sub : maps) {
                    Object n1 = sub.get("n");
                    Integer n1Int = Utils.isNotEmpty(n1) ? Integer.parseInt(n1.toString()) : 0;
                    Object m1 = sub.get("m");
                    Long m1Int = Utils.isNotEmpty(m1) ? Long.parseLong(m1.toString()) : 0;
                    Object c1 = sub.get("c");
                    String c1Str = Utils.isNotEmpty(c1) ? c1.toString() : "0";
                    Object a1 = sub.get("a");
                    Long a1Int = Utils.isNotEmpty(a1) ? Long.parseLong(a1.toString()) : 0;
                    Object t1 = sub.get("t");
                    BonusClass bonusClass = new BonusClass(n1Int, m1Int, c1Str, a1Int, t1.toString());
                    bonusClasses.add(bonusClass);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(bonusClasses);
        returnMap.put("bonusClass", bonusClasses);

        out.print(JsonBinder.buildNonDefaultBinder().toJson(returnMap));
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

}
