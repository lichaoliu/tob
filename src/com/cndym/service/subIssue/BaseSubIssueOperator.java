package com.cndym.service.subIssue;

import com.cndym.lottery.lotteryInfo.LotteryList;
import com.cndym.lottery.lotteryInfo.bean.LotteryClass;
import com.cndym.lottery.lotteryInfo.bean.LotteryTime;
import com.cndym.service.subIssue.bean.SubIssueComm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 作者：邓玉明
 * email：cndym@163.com
 * QQ：757579248
 * 时间: 12-2-8 上午11:50
 */
public class BaseSubIssueOperator implements ISubIssueOperator {
    public static Logger logger = Logger.getLogger(BaseSubIssueOperator.class);
    protected final int UPDATE = 1;

    protected final static String END_TIME_DAN_SHI = "danShi";
    protected final static String END_TIME_FU_SHI = "fuShi";

    @Override
    public void operator(String xml) {
    }

    @Override
    public void operator() {
    }

    @Override
    public SubIssueComm getSubIssueComm(String issue, String matchId, String playCode, String pollCode){
        return null;
    }

    public Map<String, Date> getEndTime(String lotteryCode, Date endTime) {
        LotteryClass lotteryClass = LotteryList.getLotteryClass(lotteryCode);

        long earlyFuShi = lotteryClass.getEarly() * 1000;
        long earlyDanShi = lotteryClass.getDashi() * 1000;

        String saleStartTime = "00:00:00";
        String saleEndTime = "23:59:59";

        //复式
        long duplexEndTime = 0;
        //单式
        long simplexEndTime = 0;
        //允许往前移1天
        boolean flag = true;
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(endTime);
            int currWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            while (true) {
                LotteryTime lotteryTime = LotteryList.getLotteryTime(lotteryCode, LotteryTime.ACTION_SALE, currWeek);
                int addDate = 0;
                if (null != lotteryTime) {
                	LotteryTime lt = SpecialDayOperator.getLotteryTime(lotteryCode, endTime, lotteryTime);
                    saleStartTime = lt.getStartTime();
                    saleEndTime = lt.getEndTime();
                    addDate = lt.getAddDate();
                    lt = null;
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date saleStartDate = dateFormat.parse(format.format(calendar.getTime()) + " " + saleStartTime);

                Calendar saleEndCal = Calendar.getInstance();
                saleEndCal.setTime(calendar.getTime());
                saleEndCal.add(Calendar.DATE, addDate);
                Date saleEndDate = dateFormat.parse(format.format(saleEndCal.getTime()) + " " + saleEndTime);

                if ((haveTime(lotteryCode, endTime, currWeek - 1 == -1 ? 6 : currWeek - 1, -1) && flag) || (endTime.getTime() - earlyDanShi < saleStartDate.getTime() && flag)) {
                    currWeek--;
                    currWeek = currWeek == -1 ? 6 : currWeek;
                    flag = false;
                    calendar.add(Calendar.DATE, -1);
                } else if (haveTime(lotteryCode, endTime, currWeek, flag == false ? -1 : 0)) {
                    simplexEndTime = endTime.getTime() - earlyDanShi;
                    break;
                } else if (endTime.getTime() >= saleEndDate.getTime()) {
                    simplexEndTime = saleEndDate.getTime() - earlyDanShi;
                    break;
                } else {
                    simplexEndTime = endTime.getTime() - earlyDanShi;
                    break;
                }
            }

            calendar = Calendar.getInstance();
            calendar.setTime(endTime);
            currWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            flag = true;
            while (true) {
                LotteryTime lotteryTime = LotteryList.getLotteryTime(lotteryCode, LotteryTime.ACTION_SALE, currWeek);
                int addDate = 0;
                if (null != lotteryTime) {
                	LotteryTime lt = SpecialDayOperator.getLotteryTime(lotteryCode, endTime, lotteryTime);
                    saleStartTime = lt.getStartTime();
                    saleEndTime = lt.getEndTime();
                    addDate = lt.getAddDate();
                    lt = null;
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date saleStartDate = dateFormat.parse(format.format(calendar.getTime()) + " " + saleStartTime);

                Calendar saleEndCal = Calendar.getInstance();
                saleEndCal.setTime(calendar.getTime());
                saleEndCal.add(Calendar.DATE, addDate);
                Date saleEndDate = dateFormat.parse(format.format(saleEndCal.getTime()) + " " + saleEndTime);

                if ((haveTime(lotteryCode, endTime, currWeek - 1 == -1 ? 6 : currWeek - 1, -1) && flag) || (endTime.getTime() - earlyFuShi < saleStartDate.getTime() && flag)) {
                    currWeek--;
                    currWeek = currWeek == -1 ? 6 : currWeek;
                    flag = false;
                    calendar.add(Calendar.DATE, -1);
                } else if (haveTime(lotteryCode, endTime, currWeek, flag == false ? -1 : 0)) {
                    duplexEndTime = endTime.getTime() - earlyFuShi;
                    break;
                } else if (endTime.getTime() >= saleEndDate.getTime()) {
                    duplexEndTime = saleEndDate.getTime() - earlyFuShi;
                    break;
                } else {
                    duplexEndTime = endTime.getTime() - earlyFuShi;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Date> dateMap = new HashMap<String, Date>();
        dateMap.put(END_TIME_FU_SHI, new Date(duplexEndTime));
        dateMap.put(END_TIME_DAN_SHI, new Date(simplexEndTime));
        return dateMap;
    }

    /**
     * 时间date是否包含在周currWeek的销售时间内
     *
     * @param lotteryCode
     * @param date        场次时间
     * @param currWeek    周0-6
     * @return
     * @throws ParseException
     */
    private boolean haveTime(String lotteryCode, Date date, int currWeek, int d) throws ParseException {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        c.add(Calendar.DATE, d);

        Date tempDate = c.getTime();
        String saleStartTime = "00:00:00";
        String saleEndTime = "23:59:59";
        Date saleStartDate = null;
        Date saleEndDate = null;
        Integer addDate = 0;
        LotteryTime lotteryTime = LotteryList.getLotteryTime(lotteryCode, LotteryTime.ACTION_SALE, currWeek);
        if (null != lotteryTime) {
        	LotteryTime lt = SpecialDayOperator.getLotteryTime(lotteryCode, date, lotteryTime);
            saleStartTime = lt.getStartTime();
            saleEndTime = lt.getEndTime();
            addDate = lt.getAddDate();
            lt = null;
        }
        lotteryTime = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        saleStartDate = dateFormat.parse(format.format(tempDate) + " " + saleStartTime);

        Calendar saleEndCal = Calendar.getInstance();
        saleEndCal.setTime(tempDate);
        saleEndCal.add(Calendar.DATE, addDate);
        saleEndDate = dateFormat.parse(format.format(saleEndCal.getTime()) + " " + saleEndTime);
        if (date.getTime() >= saleStartDate.getTime() && date.getTime() < saleEndDate.getTime()) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        BaseSubIssueOperator baseSubIssueOperator = new BaseSubIssueOperator();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Map<String, Date> dateMap = baseSubIssueOperator.getEndTime("201", simpleDateFormat.parse("2012-09-17 01:00:01"));
            System.out.println(simpleDateFormat.format(dateMap.get(END_TIME_DAN_SHI)));
            System.out.println(simpleDateFormat.format(dateMap.get(END_TIME_FU_SHI)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
