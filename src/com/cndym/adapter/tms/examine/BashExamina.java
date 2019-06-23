package com.cndym.adapter.tms.examine;

import com.cndym.lottery.lotteryInfo.LotteryList;
import com.cndym.lottery.lotteryInfo.bean.LotteryBean;

/**
 * 作者：邓玉明 时间：11-4-30 下午1:37 QQ：757579248 email：cndym@163.com
 */
public class BashExamina {
    // protected int ticketCount = 5;
    protected String ztag = ";";

    protected int C(int n, int m) {
        int n1 = 1, n2 = 1;
        for (int i = n, j = 1; j <= m; n1 *= i--, n2 *= j++)
            ;
        return n1 / n2;
    }

    protected int A(int n, int m) {
        int n1 = 1, n2 = 1;
        for (int i = 1; i <= n; i++) {
            n1 = n1 * i;
        }
        for (int j = 1; j <= (n - m); j++) {
            n2 = n2 * j;
        }
        return n1 / n2;
    }

    protected int getTicketCount(String lotteryCode) {
        LotteryBean lotteryBean = LotteryList.getLotteryBean(lotteryCode);
        int ticket = lotteryBean.getLotteryPoll().getTicket();
        return ticket;
    }

    public static int C(int num) {
        return num * (num - 1);
    }

    public static int P(int num) {
        int n = num;
        for (int i = 1; i < 3; i++) {
            num *= n - i;
        }
        return num;
    }
}
