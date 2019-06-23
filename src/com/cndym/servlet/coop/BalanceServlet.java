package com.cndym.servlet.coop;

import com.cndym.bean.other.BalanceAlarm;
import com.cndym.bean.sys.Manages;
import com.cndym.service.IBalanceAlarmService;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: MengJingyi
 * QQ: 116741034
 * Date: 13-4-16
 * Time: 下午1:55
 * To change this template use File | Settings | File Templates.
 */
public class BalanceServlet extends HttpServlet {
    private final static String UPDATE = "update";
    private final static String GET = "get";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = Utils.formatStr(request.getParameter("action"));
        Manages coopUser = (Utils.isNotEmpty(request.getSession().getAttribute("coopUser")) ? (Manages) request.getSession().getAttribute("coopUser") :
                null);
        String sid = coopUser.getUserName();

        IBalanceAlarmService balanceAlarmService = (IBalanceAlarmService) SpringUtils.getBean("balanceAlarmServiceImpl");
        if (GET.equals(action)) {
            List<BalanceAlarm> list = balanceAlarmService.getBalanceAlarmList(sid);
            if (Utils.isNotEmpty(list)) {
                BalanceAlarm balanceAlarm = list.get(0);
                request.getSession().setAttribute("amount", balanceAlarm.getBalanceAmount());
                request.getSession().setAttribute("context", balanceAlarm.getContext());
                request.getSession().setAttribute("email", balanceAlarm.getEmail());
                request.getSession().setAttribute("mobile", balanceAlarm.getMobile());
            }
            request.getRequestDispatcher("/coop/member/balanceAlarm.jsp").forward(request, response);
        } else if (UPDATE.equals(action)) {
            BalanceAlarm balanceAlarm = new BalanceAlarm();
            String amount = Utils.formatStr(request.getParameter("amount"));
            String mobile = Utils.formatStr(request.getParameter("mobile"));
            String email = Utils.formatStr(request.getParameter("email"));
            String context = Utils.formatStr(request.getParameter("context"));
            if (Utils.isNotEmpty(sid)) {
                balanceAlarm.setSid(sid);
            }

            if (Utils.isNotEmpty(amount)) {
                balanceAlarm.setBalanceAmount(new Double(amount));
                request.setAttribute("amount", amount);
            }

            if (Utils.isNotEmpty(mobile)) {
                balanceAlarm.setMobile(mobile);
                request.setAttribute("mobile", mobile);
            }

            if (Utils.isNotEmpty(email)) {
                balanceAlarm.setEmail(email);
                request.setAttribute("email", email);
            }

            if (Utils.isNotEmpty(context)) {
                balanceAlarm.setContext(context);
                request.setAttribute("context", context);
            } else {
                balanceAlarm.setContext("您在中彩汇的账户余额，已小于" + amount + "元，请及时充值");
            }
            int count = balanceAlarmService.updateBal(balanceAlarm);
            if (count > 0) {
                request.setAttribute("msg", "修改成功");
            } else {
                request.setAttribute("msg", "修改失败！");
            }
            request.getRequestDispatcher("/coop/member/balanceAlarm.jsp").forward(request, response);
        }
    }
}
