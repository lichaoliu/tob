package com.cndym.servlet.manages;

import com.cndym.bean.tms.*;
import com.cndym.constant.Constants;
import com.cndym.service.*;
import com.cndym.servlet.ElTagUtils;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * User: mcs
 * desc:国际版派奖
 * Date: 12-10-30
 * Time: 下午4:16
 */
public class SendWinIntlServlet extends BaseManagesServlet {


    private Logger logger = Logger.getLogger(getClass());

    private final String SPORT_SEND_WIN = "sportSendWin";

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	PrintWriter out = response.getWriter();
        String action = Utils.formatStr(request.getParameter("action"));
        response.setContentType("text/html");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        if (action.equals(SPORT_SEND_WIN)) {
            String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
            String lotteryName = ElTagUtils.getLotteryChinaName(lotteryCode);
            String issue = Utils.formatStr(request.getParameter("issue"));

            IMainIssueService mainIssueService = (IMainIssueService) SpringUtils.getBean("mainIssueServiceImpl");
            MainIssue mainIssue = mainIssueService.getMainIssueByLotteryIssue(lotteryCode, issue);

            if (mainIssue.getOperatorsAward() != Constants.OPERATORS_AWARD_COMPLETE) {
                logger.error(lotteryName + "(" + lotteryCode + ")第" + issue + "期派奖失败,算奖未完成");
                setManagesLog(request, action, lotteryName + "(" + lotteryCode + ")第" + issue + "期派奖失败,算奖未完成", Constants.MANAGER_LOG_WIN_MESSAGE);
                out.print("fail");
                return ;
            }

            try {
                ITicketService ticketService = (ITicketService) SpringUtils.getBean("ticketServiceImpl");
                ticketService.doBonusAmountToAccount(lotteryCode, issue);

                //期销售报表
                ISaleTableService saleTableService = (ISaleTableService) SpringUtils.getBean("saleTableServiceImpl");
                saleTableService.countSaleTable(lotteryCode, issue);

                //期出票报表
                ITicketTableService ticketTableService = (ITicketTableService) SpringUtils.getBean("ticketTableServiceImpl");
                ticketTableService.countTicketTable(lotteryCode, issue);

                setManagesLog(request, action, "启动" + lotteryName + "(" + lotteryCode + ")第" + issue + "期派奖成功", Constants.MANAGER_LOG_WIN_MESSAGE);
            } catch (Exception e) {
                e.printStackTrace();
                setManagesLog(request, action, "启动" + lotteryName + "(" + lotteryCode + ")第" + issue + "期派奖失败", Constants.MANAGER_LOG_WIN_MESSAGE);
                out.print("fail");
            }
            out.print("success");
            return ;
        }
    }
}