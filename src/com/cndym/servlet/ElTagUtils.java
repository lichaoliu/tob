package com.cndym.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.cndym.bean.user.Member;
import com.cndym.control.SidMap;
import com.cndym.service.*;
import com.cndym.service.impl.MemberServiceImpl;
import org.codehaus.jackson.type.TypeReference;

import com.cndym.accountOperator.AccountOperator;
import com.cndym.accountOperator.AccountOperatorList;
import com.cndym.adapter.tms.bean.MatchItemInfo;
import com.cndym.bean.sys.DistributionLock;
import com.cndym.bean.sys.Manages;
import com.cndym.bean.sys.PurviewUrl;
import com.cndym.bean.tms.SubIssueForBeiDan;
import com.cndym.bean.tms.SubIssueForJingCaiLanQiu;
import com.cndym.bean.tms.SubIssueForJingCaiZuQiu;
import com.cndym.bean.tms.Ticket;
import com.cndym.cache.XMemcachedClientWrapper;
import com.cndym.constant.Constants;
import com.cndym.constant.ConstantsStateList;
import com.cndym.control.PostMap;
import com.cndym.control.bean.Post;
import com.cndym.control.bean.Weight;
import com.cndym.lottery.lotteryInfo.LotteryList;
import com.cndym.lottery.lotteryInfo.bean.LotteryBean;
import com.cndym.lottery.lotteryInfo.bean.LotteryClass;
import com.cndym.lottery.lotteryInfo.bean.LotteryPlay;
import com.cndym.servlet.manages.ManagesList;
import com.cndym.servlet.manages.bean.BonusClass;
import com.cndym.servlet.manages.bean.MatchBean;
import com.cndym.servlet.manages.bean.SubTicketBean;
import com.cndym.utils.ConfigUtils;
import com.cndym.utils.DateUtils;
import com.cndym.utils.HttpClientUtils;
import com.cndym.utils.JsonBinder;
import com.cndym.utils.OrderForJCNumberUtils;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import com.cndym.utils.bonusClass.BonusClassManager;
import com.cndym.utils.bonusClass.BonusClassUtils;
import com.cndym.utils.xml.ReflectUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * User: 邓玉明 Date: 11-4-11 上午12:40
 */
public class ElTagUtils {
    public static String Test() {
        return "电话购彩平台";
    }

    public static String getBigBonus(String bonusAmount) {
        Double boDouble = new Double(bonusAmount);
        String result = "";
        String amount = ConfigUtils.getValue("BIG_BONUS_AMOUNT");
        if (boDouble >= Double.parseDouble(amount)) {
            result = "是";
        } else {
            result = "否";
        }
        return result;
    }

    public static String userStatus(int status) {
        if (Constants.MEMBER_STATUS_LIVE == status) {
            return "活动状态";
        }
        if (Constants.MEMBER_STATUS_LOCK == status) {
            return "锁定状态";
        }
        return "未知";
    }

    public static Map findAllLotteryCode() {
        Map<String, LotteryClass> codeMap = LotteryList.getLotteryCodeMap();
        return codeMap;
    }

    public static Map findNonJingCaiLotteryCode() {
        Map<String, LotteryClass> codeMap = LotteryList.getLotteryCodeMap();
        if (null != codeMap) {
            codeMap.remove("200");
            codeMap.remove("201");
        }
        return codeMap;
    }

    public static String filterBonusClassToString(String bonusClass, Integer[] levelArr, String[] levelTitleArr) {
        if (null == levelArr || levelArr.length == 0 || null == levelTitleArr || levelTitleArr.length == 0) {
            return BonusClassManager.filterLvMoneyToString(bonusClass);
        } else {
            return BonusClassManager.filterLvMoneyToString(bonusClass, levelArr, levelTitleArr);
        }
    }

    public static BonusClassManager getDefaultBonusClassManagerByLotteryCode(String lotteryCode) {
        return BonusClassUtils.getDefaultBonusClassManagerByLotteryCode(lotteryCode);
    }

    public static BonusClassManager formatBonusClassManages(String bonusClass) {
        return new BonusClassManager(bonusClass);
    }

    public static String formatDltBonusClassShow(String bonusClass) {
        BonusClassManager dlt = new BonusClassManager(bonusClass);
        StringBuffer str = new StringBuffer();

        str.append("一等奖<span style=\"color:red;\">").append(dlt.getLevelOfNum(1).getAmount()).append("</span>元,追加<span style=\"color:red;\">")
                .append(Double.parseDouble(dlt.getLevelOfNum(11).getAmount()) - Double.parseDouble(dlt.getLevelOfNum(1).getAmount())).append("</span>元,");
        str.append("二等奖<span style=\"color:red;\">").append(dlt.getLevelOfNum(2).getAmount()).append("</span>元,追加<span style=\"color:red;\">")
                .append(Double.parseDouble(dlt.getLevelOfNum(12).getAmount()) - Double.parseDouble(dlt.getLevelOfNum(2).getAmount())).append("</span>元,");
        str.append("三等奖<span style=\"color:red;\">").append(dlt.getLevelOfNum(3).getAmount()).append("</span>元,追加<span style=\"color:red;\">")
                .append(Double.parseDouble(dlt.getLevelOfNum(13).getAmount()) - Double.parseDouble(dlt.getLevelOfNum(3).getAmount())).append("</span>元,");
        str.append("四等奖<span style=\"color:red;\">").append(dlt.getLevelOfNum(4).getAmount()).append("</span>元,追加<span style=\"color:red;\">")
                .append(Double.parseDouble(dlt.getLevelOfNum(14).getAmount()) - Double.parseDouble(dlt.getLevelOfNum(4).getAmount())).append("</span>元,");
        str.append("五等奖<span style=\"color:red;\">").append(dlt.getLevelOfNum(5).getAmount()).append("</span>元,追加<span style=\"color:red;\">")
                .append(Double.parseDouble(dlt.getLevelOfNum(15).getAmount()) - Double.parseDouble(dlt.getLevelOfNum(5).getAmount())).append("</span>元,");
        str.append("六等奖<span style=\"color:red;\">").append(dlt.getLevelOfNum(6).getAmount()).append("</span>元,追加<span style=\"color:red;\">")
                .append(Double.parseDouble(dlt.getLevelOfNum(16).getAmount()) - Double.parseDouble(dlt.getLevelOfNum(6).getAmount())).append("</span>元,");
        str.append("七等奖<span style=\"color:red;\">").append(dlt.getLevelOfNum(7).getAmount()).append("</span>元,追加<span style=\"color:red;\">")
                .append(Double.parseDouble(dlt.getLevelOfNum(17).getAmount()) - Double.parseDouble(dlt.getLevelOfNum(7).getAmount())).append("</span>元,");
        str.append("八等奖<span style=\"color:red;\">").append(dlt.getLevelOfNum(8).getAmount()).append("</span>元,");
        str.append("生肖乐<span style=\"color:red;\">").append(dlt.getLevelOfNum(10).getAmount()).append("</span>元");

        return str.toString();
    }

    public static Double parseDouble(String string) {
        return Double.parseDouble(string);
    }

    public static LotteryBean getLotteryBeanByLotteryCode(String code) {
        return LotteryList.getLotteryBean(code);
    }

    public static LotteryBean getLotteryBeanByLotteryCode(String lotteryCode, String playCode) {
        return LotteryList.getLotteryBean(lotteryCode + "" + playCode);
    }

    public static String getPollName(String lotteryCode, String playCode, String pollCode) {
        LotteryBean lotteryBean = null;
        String pollName = "";
        if (pollCode.contains(",")) {
            lotteryBean = LotteryList.getLotteryBean(lotteryCode + playCode);
            pollName = "混投";
        } else {
            lotteryBean = LotteryList.getLotteryBean(lotteryCode + playCode + pollCode);
            pollName = lotteryBean.getLotteryPoll().getName();
            if (Utils.isEmpty(pollName)) {
                pollName = "-";
            }
        }
        return pollName;
    }

    public static String getIssueStatus(String status) {
        if (null == status) {
            return null;
        }
        String result = "";
        try {
            if (Constants.ISSUE_STATUS_WAIT == Integer.parseInt(status)) {
                result = "预售";
            } else if (Constants.ISSUE_STATUS_START == Integer.parseInt(status)) {
                result = "销售中";
            } else if (Constants.ISSUE_STATUS_PAUSE == Integer.parseInt(status)) {
                result = "暂停";
            } else if (Constants.ISSUE_STATUS_END == Integer.parseInt(status)) {
                result = "结期";
            }
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    private static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();

    }

    public static String getEventCode(String eventCode) {
        try {
            AccountOperator accountOperator = AccountOperatorList.getAccountOperator(eventCode);
            if (Utils.isNotEmpty(accountOperator)) {
                return accountOperator.getNickName();
            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }
    }

    public static String getEventCodeType(String eventCode) {
        return getEventCode(eventCode);
    }

    /**
     * 操作时间日期lfx
     *
     * @param date
     * @param field
     * @param num
     * @return
     */
    public static Date operationDate(Date date, String field, int num) {
        if (null == date || null == field || "".equals(field.trim())) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        int fieldNum = Calendar.YEAR;
        if (field.equals("M")) {
            fieldNum = Calendar.MONTH;
        }
        if (field.equals("d")) {
            fieldNum = Calendar.DATE;
        }
        if (field.equals("H")) {
            fieldNum = Calendar.HOUR;
        }
        if (field.equals("m")) {
            fieldNum = Calendar.MINUTE;
        }
        if (field.equals("s")) {
            fieldNum = Calendar.SECOND;
        }
        cal.setTime(date);
        cal.add(fieldNum, num);
        return cal.getTime();
    }

    /**
     * 判断date是否是今天的时间
     *
     * @param date
     * @return
     */
    public static Boolean isToday(Date date) {
        Calendar cal = Calendar.getInstance();
        Date nowTime = new Date();
        cal.setTime(nowTime);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date startTime = cal.getTime();
        cal.set(Calendar.HOUR, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        Date endTime = cal.getTime();
        if (date != null && date.getTime() >= startTime.getTime() && date.getTime() <= endTime.getTime()) {
            return true;
        } else {
            return false;
        }
    }

    public static String addDate(String field, int num) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int fieldNum = Calendar.YEAR;
        if (field.equals("m")) {
            fieldNum = Calendar.MONTH;
        }
        if (field.equals("d")) {
            fieldNum = Calendar.DATE;
        }
        Calendar cal = Calendar.getInstance();
        cal.add(fieldNum, num);
        return simpleDateFormat.format(cal.getTime());
    }

    public static String formatIdCode(String idCode) {
        if (Utils.isNotEmpty(idCode)) {
            if (idCode.length() > 4) {
                return idCode.substring(0, idCode.length() - 4) + "****";
            }
        }
        return idCode;
    }

    public static String formatMobile(String mobile) {
        if (Utils.isNotEmpty(mobile)) {
            if (mobile.length() == 11) {
                mobile = mobile.substring(0, 3) + "****" + mobile.substring(7, mobile.length());
            }
        }
        return mobile;
    }

    public static String formatEmail(String email) {
        if (Utils.isNotEmpty(email)) {
            try {
                String[] array1 = email.split("@");
                String mail1 = array1[0];
                String mail2 = array1[1];
                return mail1.substring(0, 3) + "****@" + mail2;
            } catch (Exception e) {
                return "";
            }
        }
        return email;
    }

    public static String formatBankCode(String bankCode) {
        if (Utils.isNotEmpty(bankCode)) {
            try {
                return bankCode.substring(0, 8) + "****" + bankCode.substring(bankCode.length() - 4, bankCode.length());
            } catch (Exception e) {
                return bankCode;
            }
        }
        return bankCode;
    }

    public static String formatRealName(String realName) {
        if (Utils.isNotEmpty(realName)) {
            return realName.substring(0, 1) + "**";
        }
        return realName;
    }

    public static String getWeekStr(String dateStr, String dateFmt) {
        try {
            return DateUtils.getWeekStr(dateStr, dateFmt);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getLotteryChinaName(String lotteryCode) {
        LotteryBean lotterybean = null;
        try {
            lotterybean = LotteryList.getLotteryBean(lotteryCode);
            if (null == lotterybean) {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
        return lotterybean.getLotteryClass().getName();
    }

    /**
     * 拦截胜负彩解析为14场和任9场
     *
     * @param lotteryCode
     * @param playCode
     * @return
     */
    public static String getLotteryChinaNameFilter300(String lotteryCode, String playCode) {
        if ("300".equals(lotteryCode)) {
            if ("01".equals(playCode)) {
                return "14场";
            } else if ("02".equals(playCode)) {
                return "任九场";
            } else {
                return "胜负彩";
            }
        } else if ("200".equals(lotteryCode)) {
            if ("01".equals(playCode)) {
                return "竞足胜平负";
            }
            if ("02".equals(playCode)) {
                return "竞足总进球数";
            }
            if ("03".equals(playCode)) {
                return "竞足半全场";
            }
            if ("04".equals(playCode)) {
                return "竞足比分";
            }
        } else if ("201".equals(lotteryCode)) {
            if ("01".equals(playCode)) {
                return "篮彩胜负";
            }
            if ("02".equals(playCode)) {
                return "篮彩让分胜负";
            }
            if ("03".equals(playCode)) {
                return "篮彩胜分差";
            }
            if ("04".equals(playCode)) {
                return "篮彩大小分";
            }
        } else if ("400".equals(lotteryCode)) {
            if ("01".equals(playCode)) {
                return "单场胜平负";
            }
            if ("02".equals(playCode)) {
                return "单场上下单双";
            }
            if ("03".equals(playCode)) {
                return "单场总进球数";
            }
            if ("04".equals(playCode)) {
                return "单场半全场";
            }
            if ("05".equals(playCode)) {
                return "单场比分";
            }
        } else {
            return getLotteryChinaName(lotteryCode);
        }
        return "";
    }

    public static String URLEncode(String str) {
        try {
            return java.net.URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String bonusClass(String lotteryCode, int index) {
        if (lotteryCode.equals("001") || lotteryCode.equals("004") || lotteryCode.equals("005")) {
            if (index == 0) {
                return "特等奖";
            }
            if (index == 1) {
                return "一等奖";
            }

            if (index == 2) {
                return "二等奖";
            }

            if (index == 3) {
                return "三等奖";
            }

            if (index == 4) {
                return "四等奖";
            }

            if (index == 5) {
                return "五等奖";
            }

            if (index == 6) {
                return "六等奖";
            }

            if (index == 7) {
                return "七等奖";
            }

            if (index == 8) {
                return "八等奖";
            }
        } else if (lotteryCode.equals("002")) {
            if (index == 1) {
                return "单选奖";
            }

            if (index == 2) {
                return "组三奖";
            }

            if (index == 3) {
                return "组六奖";
            }
        } else if (lotteryCode.equals("003")) {
            if (index == 1) {
                return "特等奖";
            }

            if (index == 2) {
                return "一等奖";
            }

            if (index == 3) {
                return "二等奖";
            }

            if (index == 4) {
                return "加奖";
            }
        } else if (lotteryCode.equals("006")) {
            if (index == 1) {
                return "五星奖";
            }

            if (index == 2) {
                return "四星一等奖";
            }

            if (index == 3) {
                return "四星二等奖";
            }

            if (index == 4) {
                return "三星奖";
            }

            if (index == 5) {
                return "二星奖";
            }

            if (index == 6) {
                return "一星奖";
            }

            if (index == 7) {
                return "大小单双";
            }

            if (index == 8) {
                return "二星组选";
            }
            if (index == 9) {
                return "五星通选一等奖";
            }
            if (index == 10) {
                return "五星通选二等奖";
            }
            if (index == 11) {
                return "五星通选三等奖";
            }
            if (index == 12) {
                return "任选一";
            }
            if (index == 13) {
                return "任选二";
            }
            if (index == 14) {
                return "三星组选三";
            }
            if (index == 15) {
                return "三星组选六";
            }
        }
        if (lotteryCode.equals("113")) {
            if (index == 0) {
                return "特等奖";
            }
            if (index == 1) {
                return "一等奖";
            }

            if (index == 2) {
                return "二等奖";
            }

            if (index == 3) {
                return "三等奖";
            }

            if (index == 4) {
                return "四等奖";
            }

            if (index == 5) {
                return "五等奖";
            }

            if (index == 6) {
                return "六等奖";
            }

            if (index == 7) {
                return "七等奖";
            }

            if (index == 18 || index == 8) {
                return "八等奖";
            }
            if (index == 10) {
                return "生肖乐";
            }
            if (index == 11) {
                return "一等奖追加";
            }
            if (index == 12) {
                return "二等奖追加";
            }
            if (index == 13) {
                return "三等奖追加";
            }
            if (index == 14) {
                return "四等奖追加";
            }
            if (index == 15) {
                return "五等奖追加";
            }
            if (index == 16) {
                return "六等奖追加";
            }
            if (index == 17) {
                return "七等奖追加";
            }
        } else if (lotteryCode.equals("107")) {

            if (index == 1) {
                return "选前三奖";
            }

            if (index == 2) {
                return "任选五奖";
            }

            if (index == 3) {
                return "选前三组选奖";
            }

            if (index == 4) {
                return "选前二奖";
            }

            if (index == 5) {
                return "任选六奖";
            }

            if (index == 6) {
                return "任选四奖";
            }

            if (index == 7) {
                return "选前二组选奖";
            }

            if (index == 8) {
                return "任选七奖";
            }
            if (index == 9) {
                return "任选三奖";
            }
            if (index == 10) {
                return "任选一奖";
            }
            if (index == 11) {
                return "任选八奖";
            }
            if (index == 12) {
                return "任选二奖";
            }
        } else if (lotteryCode.equals("108") || lotteryCode.equals("109")) {
            if (lotteryCode.equals("108")) {
                if (index == 1) {
                    return "直选奖";
                }

                if (index == 2) {
                    return "组三奖";
                }

                if (index == 3) {
                    return "组六奖";
                }
            }
            if (lotteryCode.equals("109")) {
                if (index == 1) {
                    return "一等奖";
                }
            }
        } else if (lotteryCode.equals("300")) {
            if (index == 1) {
                return "一等奖";
            }

            if (index == 2) {
                return "二等奖";
            }

            if (index == 3) {
                return "任九场";
            }
        } else if (lotteryCode.equals("301")) {
            if (index == 1) {
                return "一等奖";
            }
        } else if (lotteryCode.equals("302")) {
            if (index == 1) {
                return "一等奖";
            }
        } else {
            if (index == 0) {
                return "特等奖";
            }
            if (index == 1) {
                return "一等奖";
            }

            if (index == 2) {
                return "二等奖";
            }

            if (index == 3) {
                return "三等奖";
            }

            if (index == 4) {
                return "四等奖";
            }

            if (index == 5) {
                return "五等奖";
            }

            if (index == 6) {
                return "六等奖";
            }

            if (index == 7) {
                return "七等奖";
            }

            if (index == 8) {
                return "八等奖";
            }
            if (index == 9) {
                return "九等奖";
            }
        }
        return "中奖";
    }

    public static List<Manages> findAllAdminName() {
        List<Manages> list = ManagesList.getManagesList();
        return list;
    }

    public static List<PurviewUrl> purviewUrlList() {
        List<PurviewUrl> list = ManagesList.getPurviewUrlsList();
        return list;
    }

    public static List<PurviewUrl> purviewUrlFatherList() {
        List<PurviewUrl> list = ManagesList.getPurviewUrlsFatherList();
        return list;
    }

    public static String getPlayChinaName(String lotteryCode, String playCode) {
        if (Utils.isNotEmpty(playCode)) {
            LotteryBean lotteryBean = LotteryList.getLotteryBean(lotteryCode + "" + playCode);
            return lotteryBean.getLotteryPlay().getName();
        } else {
            LotteryBean lotteryBean = LotteryList.getLotteryBean(lotteryCode);
            return lotteryBean.getLotteryClass().getName();
        }
    }

    public static Map<String, String> findAllLotteryPlayCode() {
        Map<String, String> lotteryPlayMap = new LinkedHashMap<String, String>();
        Map<String, LotteryClass> lotteryClassMap = LotteryList.getLotteryCodeMap();
        LotteryClass lotteryClass = null;
        List<LotteryPlay> lotteryPlayList = null;
        for (String lotteryCode : lotteryClassMap.keySet()) {
            if (Utils.isNotEmpty(lotteryCode)) {
                lotteryClass = lotteryClassMap.get(lotteryCode);
                lotteryPlayList = lotteryClass.getLotteryPlayList();
                for (LotteryPlay lotteryPlay : lotteryPlayList) {
                    if (Utils.isNotEmpty(lotteryPlay) && Utils.isNotEmpty(lotteryPlay.getName())) {
                        lotteryPlayMap.put(lotteryCode + lotteryPlay.getCode(), lotteryPlay.getName());
                    }
                }
            }
        }
        return lotteryPlayMap;
    }

    public static Boolean havePurview(Map map, String code) {
        boolean falg = false;
        if (null != map && map.size() > 0) {
            if (map.containsKey(code)) {
                falg = true;
            }
        }
        return falg;
    }

    public static String fullTag(String str, int item) {
        if (null == str) {
            return str;
        }
        int len = str.length();
        StringBuffer temp = new StringBuffer();
        while (len < item) {
            temp.append("&nbsp;");
            temp.append("&nbsp;");
            len++;
        }
        return temp.append(str).toString();
    }

    public static List<DistributionLock> getLock() {
        IDistributionLockService distributionLockService = (IDistributionLockService) SpringUtils.getBean("distributionLockServiceImpl");
        return distributionLockService.getDistributionLockList(null);
    }

    public static void updateLock(String name) {
        IDistributionLockService distributionLockService = (IDistributionLockService) SpringUtils.getBean("distributionLockServiceImpl");
        distributionLockService.updateStatus(name);
    }

    public static Double stringToDouble(String value) {
        if (Utils.isNotEmpty(value)) {
            return Utils.formatDouble(value, 0D);
        }
        return 0D;
    }

    public static int lotteryControl(String lotteryCode) {
        StringBuffer key = new StringBuffer("lottery.").append(lotteryCode).append(".control");
        XMemcachedClientWrapper memcachedClientWrapper = (XMemcachedClientWrapper) SpringUtils.getBean("xmemcachedClientWrapper");
        String value = memcachedClientWrapper.get(key.toString());
        if (Utils.isNotEmpty(value)) {
            return 1;
        }
        return 0;
    }

    public static int lotterySendControl(String lotteryCode) {
        String key = Constants.MEMCACHED_SEND_ORDER + lotteryCode;
        XMemcachedClientWrapper memcachedClientWrapper = (XMemcachedClientWrapper) SpringUtils.getBean("xmemcachedClientWrapper");
        String value = memcachedClientWrapper.get(key);
        if (Utils.isNotEmpty(value)) {
            return 1;
        }
        return 0;
    }

    public static Map<Integer, String> getAllmanagesLogType() {
        return ConstantsStateList.getAllManagesLogType();
    }

    public static String getMangesLogType(Integer key) {
        return ConstantsStateList.getManagesLogType(key);
    }

    public static String getBonusDj(String bonusClass) {
        String result = "";
        if (Utils.isNotEmpty(bonusClass)) {
            bonusClass.split(";");
        }
        return result;
    }

    public static List<BonusClass> getIssueBonusClass(String bonusClassStr) {
        List<BonusClass> bonusClasses = new ArrayList<BonusClass>();
        if (Utils.isNotEmpty(bonusClassStr)) {
            bonusClassStr = bonusClassStr.replaceAll("'", "\"");
            try {
                List<Map> maps = JsonBinder.buildNonDefaultBinder().getMapper().readValue(bonusClassStr, new TypeReference<List<Map>>() {
                });
                for (Map sub : maps) {
                    Object n1 = sub.get("n1");
                    Integer n1Int = Utils.isNotEmpty(n1) ? Integer.parseInt(n1.toString()) : 0;
                    Object m1 = sub.get("m1");
                    Long m1Int = Utils.isNotEmpty(m1) ? Long.parseLong(m1.toString()) : 0;
                    Object c1 = sub.get("c1");
                    String c1Str = Utils.isNotEmpty(c1) ? c1.toString() : "0";
                    Object a1 = sub.get("a1");
                    Long a1Int = Utils.isNotEmpty(a1) ? Long.parseLong(a1.toString()) : 0;
                    bonusClasses.add(new BonusClass(n1Int, m1Int, c1Str, a1Int));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(bonusClasses);
        return bonusClasses;
    }

    public static String isBonusDouble(String bonusNumber) {
        String isBonusDo = "";
        String[] number = bonusNumber.split(",");
        if (number[0].equals(number[1]) || number[0].equals(number[2]) || number[1].equals(number[2])) {
            isBonusDo = "2";
        } else {
            isBonusDo = "3";
        }
        return isBonusDo;
    }

    public static Double abs(Double value) {
        return Math.abs(value);
    }

    public static Map<String, String> buildSScIssue(Integer num) {
        if (null == num || num <= 0)
            return null;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -(num - 1));
        Map<String, String> stringMap = new HashMap<String, String>();
        StringBuffer date = new StringBuffer();
        String year = new SimpleDateFormat("yy").format(new Date());
        date.append(Utils.fullByZero(calendar.get(Calendar.MONTH) + 1, 2));
        date.append(Utils.fullByZero(calendar.get(Calendar.DATE), 2));
        stringMap.put("startIssue", year + date.append("001").toString());
        calendar = Calendar.getInstance();
        date = new StringBuffer();
        date.append(Utils.fullByZero(calendar.get(Calendar.MONTH) + 1, 2));
        date.append(Utils.fullByZero(calendar.get(Calendar.DATE), 2));
        stringMap.put("endIssue", year + date.append("084").toString());
        return stringMap;
    }

    public static Map<String, String> build115Issue(Integer num) {
        if (null == num || num <= 0)
            return null;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -(num - 1));
        Map<String, String> stringMap = new HashMap<String, String>();
        StringBuffer date = new StringBuffer();
        date.append(calendar.get(Calendar.YEAR));
        date.append(Utils.fullByZero(calendar.get(Calendar.MONTH) + 1, 2));
        date.append(Utils.fullByZero(calendar.get(Calendar.DATE), 2));
        stringMap.put("startIssue", date.append("01").toString());
        calendar = Calendar.getInstance();
        date = new StringBuffer();
        date.append(calendar.get(Calendar.YEAR));
        date.append(Utils.fullByZero(calendar.get(Calendar.MONTH) + 1, 2));
        date.append(Utils.fullByZero(calendar.get(Calendar.DATE), 2));
        stringMap.put("endIssue", date.append("78").toString());
        return stringMap;
    }

    public static Map getSort(List purviews) {
        Map<String, List<Map>> mapList = new HashMap<String, List<Map>>();
        for (Object obj : purviews) {
            Map map = (HashMap) obj;
            String key = map.get("FCODE").toString();
            if (mapList.containsKey(key)) {
                mapList.get(key).add(map);
            } else {
                List<Map> maps = new ArrayList<Map>();
                maps.add(map);
                mapList.put(key, maps);
            }
        }
        return mapList;
    }

    public static List getPurviewList(String key, List purviews) {
        Map map = getSort(purviews);
        List list = (ArrayList) map.get(key);
        return list;
    }

    public static List<BonusClass> getBonusClass(String bonusClass) {
        if (!Utils.isNotEmpty(bonusClass)) {
            return null;
        }
        Map bounsLogMap = null;
        String[] bonusDetailArray = null;
        Map<Integer, Long> bonusDetailMap = new HashMap<Integer, Long>();
        String[] bonusArray = bonusClass.split(",");
        int index = 0;
        for (String bonus : bonusArray) {
            if (new Integer(bonus) > 0) {
                if (bonusDetailMap.containsKey(index)) {
                    bonusDetailMap.put(index, bonusDetailMap.get(index) + new Integer(bonus));
                } else {
                    bonusDetailMap.put(index, new Long(bonus));
                }
            }
            index++;
        }
        List<BonusClass> bonusClasses = new ArrayList<BonusClass>();
        for (Integer bonusIndex : bonusDetailMap.keySet()) {
            bonusClasses.add(new BonusClass(bonusIndex, bonusDetailMap.get(bonusIndex), "0.00"));
        }
        Collections.sort(bonusClasses);
        return bonusClasses;
    }

    public static String bonusLogClass(Integer index, String lotteryCode) {
        return ConstantsStateList.getBonusLevel(lotteryCode, index);
    }

    public static String enCode(String str) {
        String enCodeStr = "";
        try {
            enCodeStr = java.net.URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return enCodeStr;
    }

    public static List<Map<String, String>> queryBanlance() {
        List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
        try {
            String url = ConfigUtils.getValue("POST_CENTER_URL");
            HttpClientUtils hcu = new HttpClientUtils(url);
            String restr = hcu.httpClientGet();

            if (Utils.isNotEmpty(restr)) {
                Type type = new TypeToken<List<Map<String, String>>>() {
                }.getType();

                mapList = new Gson().fromJson(restr, type);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapList;
    }

    public static Map<String, Double> getZzcSaleInfo(String lotteryCode, String issue) {
        Map<String, Double> saleInfoMap = new HashMap<String, Double>();
        try {
            StringBuffer url = new StringBuffer();
            url.append(ConfigUtils.getValue("POST_ZZC_SALE_URL")).append("?lottery=").append(lotteryCode).append("&issue=").append(issue);
            HttpClientUtils hcu = new HttpClientUtils(url.toString());
            String reStr = hcu.httpClientGet();
            if (Utils.isNotEmpty(reStr)) {
                Type type = new TypeToken<Map<String, Double>>() {
                }.getType();
                saleInfoMap = new Gson().fromJson(reStr, type);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return saleInfoMap;
    }

    public static String sscDxdsNumber(String number) {
        String str = "";
        str = number.replace("9", "大");
        str = str.replace("0", "小");
        str = str.replace("1", "单");
        str = str.replace("2", "双");
        return str;
    }

    // 自主算奖
    public static Map getOperatorsAwardMap() {
        return ConstantsStateList.getOperatorsAwardMap();
    }

    public static String getOperatorsAward(String code) {
        if (Utils.isNotEmpty(code)) {
            return ConstantsStateList.getOperatorsAward(code);
        } else {
            return "--";
        }
    }

    // 自主算奖
    public static Map getBonusOperatorsMap() {
        return ConstantsStateList.getBonusOperatorsMap();
    }

    public static String getBonusOperators(String code) {
        if (Utils.isNotEmpty(code)) {
            return ConstantsStateList.getBonusOperators(code);
        } else {
            return "--";
        }
    }

    public static String spfScore(Integer mainTeamScore, Integer guestTeamScore, String letBall) {
        if (!Utils.isNotEmpty(mainTeamScore) || !Utils.isNotEmpty(guestTeamScore)) {
            return "";
        }
        if (!Utils.isNotEmpty(letBall)) {
            letBall = "0";
        }
        // 主让客
        if (letBall.startsWith("-")) {
            Double score = Utils.formatDouble(letBall.replaceFirst("-", ""), 0.0);
            if (mainTeamScore.doubleValue() - guestTeamScore.doubleValue() > score.doubleValue()) {
                return "胜";
            } else if (mainTeamScore.doubleValue() - guestTeamScore.doubleValue() == score.doubleValue()) {
                return "平";
            } else if (mainTeamScore.doubleValue() - guestTeamScore < score.doubleValue()) {
                return "负";
            }
        } else if (letBall.startsWith("+")) { // 客让主
            Double score = Utils.formatDouble(letBall.replace("+", ""), 0.0);
            if (mainTeamScore.doubleValue() + score > guestTeamScore.doubleValue()) {
                return "胜";
            } else if (mainTeamScore.doubleValue() + score == guestTeamScore.doubleValue()) {
                return "平";
            } else if (mainTeamScore.doubleValue() + score < guestTeamScore.doubleValue()) {
                return "负";
            }
        } else {
            Double score = Utils.formatDouble(letBall, 0.0);
            if (mainTeamScore.doubleValue() + score.doubleValue() > guestTeamScore.doubleValue()) {
                return "胜";
            } else if (mainTeamScore.doubleValue() + score.doubleValue() == guestTeamScore.doubleValue()) {
                return "平";
            } else if (mainTeamScore.doubleValue() + score.doubleValue() < guestTeamScore.doubleValue()) {
                return "负";
            }
        }
        return "";
    }

    public static String sfcScore(Integer mainTeamScore, Integer guestTeamScore) {
        if (!Utils.isNotEmpty(mainTeamScore) || !Utils.isNotEmpty(guestTeamScore) || 0 == mainTeamScore.intValue() || 0 == guestTeamScore.intValue()) {
            return "";
        }
        int score = 0;
        String result = "";
        if (mainTeamScore.intValue() > guestTeamScore.intValue()) {
            score = mainTeamScore.intValue() - guestTeamScore.intValue();
            result = "主";
        } else {
            score = guestTeamScore.intValue() - mainTeamScore.intValue();
            result = "客";
        }
        if (score >= 1 && score <= 5) {
            result += "1-5";
        } else if (score >= 6 && score <= 10) {
            result += "6-10";
        } else if (score >= 11 && score <= 15) {
            result += "11-15";
        } else if (score >= 16 && score <= 20) {
            result += "16-20";
        } else if (score >= 21 && score <= 25) {
            result += "21-25";
        } else if (score >= 26) {
            result += "26+";
        }
        return result;
    }

    public static String dxfScore(Integer mainTeamScore, Integer guestTeamScore, String preCast) {
        if (!Utils.isNotEmpty(mainTeamScore) || !Utils.isNotEmpty(guestTeamScore) || !Utils.isNotEmpty(preCast)) {
            return "";
        }
        Double score = Utils.formatDouble(preCast.replace("+", ""), 0.0);
        if (mainTeamScore.doubleValue() + guestTeamScore.doubleValue() > score.doubleValue()) {
            return "大";
        }
        if (mainTeamScore.doubleValue() + guestTeamScore.doubleValue() < score.doubleValue()) {
            return "小";
        }
        return "";
    }

    public static String sxdsScore(Integer mainTeamScore, Integer guestTeamScore) {
        if (!Utils.isNotEmpty(mainTeamScore) || !Utils.isNotEmpty(guestTeamScore)) {
            return "";
        }
        int num = mainTeamScore.intValue() + guestTeamScore.intValue();
        StringBuffer sb = new StringBuffer();
        if (num >= 3) {
            sb.append("上");
        } else {
            sb.append("下");
        }
        if (num % 2 == 0) {
            sb.append("双");
        } else {
            sb.append("单");
        }
        return sb.toString();
    }

    public static String bfScore(Integer mainTeamScore, Integer guestTeamScore) {
        if (!Utils.isNotEmpty(mainTeamScore) || !Utils.isNotEmpty(guestTeamScore)) {
            return "";
        }
        String result = "";
        // 胜
        if (mainTeamScore.intValue() > guestTeamScore.intValue()) {
            if (mainTeamScore.intValue() <= 5 && guestTeamScore.intValue() <= 2) {
                return mainTeamScore + ":" + guestTeamScore;
            } else {
                return "胜其他";
            }
        } else if (mainTeamScore.equals(guestTeamScore)) {
            if (mainTeamScore.intValue() <= 3 && guestTeamScore.intValue() <= 3) {
                return mainTeamScore + ":" + guestTeamScore;
            } else {
                return "平其他";
            }
        } else if (mainTeamScore.intValue() < guestTeamScore.intValue()) {
            if (mainTeamScore.intValue() <= 2 && guestTeamScore.intValue() <= 5) {
                return mainTeamScore + ":" + guestTeamScore;
            } else {
                return "负其他";
            }
        }
        return result;
    }

    public static String bfScoreForBeiDan(Integer mainTeamScore, Integer guestTeamScore) {
        if (!Utils.isNotEmpty(mainTeamScore) || !Utils.isNotEmpty(guestTeamScore)) {
            return "";
        }
        String result = "";
        // 胜
        if (mainTeamScore.intValue() > guestTeamScore.intValue()) {
            if (mainTeamScore.intValue() <= 4 && guestTeamScore.intValue() <= 2) {
                return mainTeamScore + ":" + guestTeamScore;
            } else {
                return "胜其他";
            }
        } else if (mainTeamScore.equals(guestTeamScore)) {
            if (mainTeamScore.intValue() <= 3 && guestTeamScore.intValue() <= 3) {
                return mainTeamScore + ":" + guestTeamScore;
            } else {
                return "平其他";
            }
        } else if (mainTeamScore.intValue() < guestTeamScore.intValue()) {
            if (mainTeamScore.intValue() <= 2 && guestTeamScore.intValue() <= 4) {
                return mainTeamScore + ":" + guestTeamScore;
            } else {
                return "负其他";
            }
        }
        return result;
    }

    public static String zjqsScore(Integer mainTeamScore, Integer guestTeamScore) {
        if (!Utils.isNotEmpty(mainTeamScore) || !Utils.isNotEmpty(guestTeamScore)) {
            return "";
        }
        String result = "";
        if (mainTeamScore.intValue() + guestTeamScore.intValue() >= 7) {
            result = "7+";
        } else {
            result = (mainTeamScore + guestTeamScore) + "";
        }
        return result;
    }

    public static String formatDateStr(String dateStr, String soureFormat, String format) {
        try {
            if (soureFormat.equals("MMdd")) {
                Date date = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int year = calendar.get(Calendar.YEAR);
                dateStr = year + dateStr;
                soureFormat = "yyyy" + soureFormat;
            }
            return Utils.formatDate2Str(Utils.formatDate(dateStr, soureFormat), format);
        } catch (Exception e) {
            return dateStr;
        }
    }

    private static String[] parseNumber(String number) {
        if (Utils.isNotEmpty(number) && !number.equals("-")) {
            if (number.endsWith("*")) {
                number += " ";
            }
            return number.split("\\*");
        }
        return null;
    }

    /**
     * 某时间与当前时间差，显示多少分多少秒
     *
     * @param time
     * @return
     */
    public static String diffNowTime(Date time) {
        Calendar c = Calendar.getInstance();
        long diff = Math.abs((c.getTime().getTime() - time.getTime())) / 1000;
        StringBuffer sb = new StringBuffer();
        if (diff / 60 >= 1) {
            sb.append(diff / 60).append("分");
        }
        sb.append(diff % 60).append("秒");
        return sb.toString();
    }

    public static int diffNowTimeSeconds(Date time) {
        Calendar c = Calendar.getInstance();
        long diff = Math.abs((c.getTime().getTime() - time.getTime())) / 1000;
        return (int) diff;
    }

    public static List<Post> getPostMaps() {
        return PostMap.postList();
    }

    public static Map<String, Weight> getWeightMap() {
		Map<String, Weight> weightMap = null;
		XMemcachedClientWrapper memcachedClientWrapper = (XMemcachedClientWrapper) SpringUtils.getBean("xmemcachedClientWrapper");
		try {
        	String weightJson = (String) memcachedClientWrapper.getMemcachedClient().get("weight_map_value");
    		weightMap = JsonBinder.buildNormalBinder().getMapper().readValue(weightJson, new TypeReference<Map<String, Weight>>() { } );
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	return weightMap;
    }

    public static List<Weight> getWeightList() {
		Map<String, Weight> weightMap = getWeightMap();
		if (Utils.isNotEmpty(weightMap)) {
	    	List<Weight> weightList = new ArrayList<Weight>();
			Iterator<String> iter = weightMap.keySet().iterator();
			Weight weight = null;
			while (iter.hasNext()) {
			    String key = iter.next();
			    weight = weightMap.get(key);
			    weightList.add(weight);
			}
			return weightList;
		}
		return null;
    }
    
    public static List<Member> getSidNameList() {
        return SidMap.getSidNameList();
    }

    public static String getPostCodeName(String postCode) {
        try {
            return PostMap.getPost(postCode).getName();
        } catch (Exception e) {
            return "待分配";
        }
    }

    public static String getTicketStatus(String status) {
        String statusName = "";
        if (Constants.TICKET_STATUS_CANCEL == Utils.formatInt(status, null)) {
            statusName = "取消出票";
        } else if (Constants.TICKET_STATUS_DOING == Utils.formatInt(status, null)) {
            statusName = "未送票";
        } else if (Constants.TICKET_STATUS_FAILURE == Utils.formatInt(status, null)) {
            statusName = "出票失败";
        } else if (Constants.TICKET_STATUS_SENDING == Utils.formatInt(status, null)) {
            statusName = "送票未回执";
        } else if (Constants.TICKET_STATUS_SUCCESS == Utils.formatInt(status, null)) {
            statusName = "出票成功";
        } else if (Constants.TICKET_STATUS_WAIT == Utils.formatInt(status, null)) {
            statusName = "调度中";
        } else if (Constants.TICKET_STATUS_RESEND == Utils.formatInt(status, null)) {
            statusName = "重发";
        }
        return statusName;
    }

    public static Integer getExportMaxPage() {
        return Utils.formatInt(ConfigUtils.getValue("EXPORT_MAX_PAGE"), 60);
    }

    public static SubTicketBean subOrderMatchList(String ticketId) {
        if (!Utils.isNotEmpty(ticketId)) {
            return null;
        }
        ITicketService ticketService = (ITicketService) SpringUtils.getBean("ticketServiceImpl");
        ISubIssueForJingCaiZuQiuService subIssueForJingCaiZuQiuService = (ISubIssueForJingCaiZuQiuService) SpringUtils.getBean("subIssueForJingCaiZuQiuServiceImpl");
        ISubIssueForJingCaiLanQiuService subIssueForJingCaiLanQiuService = (ISubIssueForJingCaiLanQiuService) SpringUtils.getBean("subIssueForJingCaiLanQiuServiceImpl");
        ISubIssueForBeiDanService subIssueForBeiDanService = (ISubIssueForBeiDanService) SpringUtils.getBean("subIssueForBeiDanServiceImpl");

        Ticket ticket = ticketService.getTicketByTicketId(ticketId);

        String lotteryCode = ticket.getLotteryCode();
        String playCode = ticket.getPlayCode();
        String pollCode = ticket.getPollCode();
        String saleInfo = ticket.getSaleInfo();

        Map<String, Object> matchNumberMap = OrderForJCNumberUtils.matchList(ticket.getNumberInfo(), ticket.getPlayCode());
        String guoGuan = matchNumberMap.get("guoGuan") + "";
        List<MatchItemInfo> matchItemInfoList = (List<MatchItemInfo>) matchNumberMap.get("matchItemInfoList");
        // 单关
        boolean single = false;
        // 单关浮动
        boolean singleFloat = false;
        if (guoGuan.equals("1*1")) {
            single = true;
        }
        // 竞彩足球
        if (lotteryCode.equals("200") && single) {
            if (playCode.equals("01") || playCode.equals("02") || playCode.equals("03")) {
                singleFloat = true;
            }
        }
        // 竞彩篮球
        if (lotteryCode.equals("201") && single) {
            if (playCode.equals("01") || playCode.equals("02") || playCode.equals("04")) {
                singleFloat = true;
            }
        }

        Map<String, Object> returnNumberMap = null;
        int ticketStatus = ticket.getTicketStatus();
        boolean sendSuccess = false;
        if (ticketStatus == Constants.TICKET_STATUS_SUCCESS) {
            sendSuccess = true;
            if (lotteryCode.equals("201")) {
                returnNumberMap = OrderForJCNumberUtils.formatBasketballReturnNumber(saleInfo, playCode);
            } else if (lotteryCode.equals("200")) {
                returnNumberMap = OrderForJCNumberUtils.formatFootballReturnNumber(saleInfo, playCode);
            } else if (lotteryCode.equals("400")) {
                //returnNumberMap = OrderForJCNumberUtils.formatBeiDanReturnNumber(saleInfo);
            }
        }

        StringBuffer matchXml = new StringBuffer();
        String issue = "";
        String sn = "";

        List<MatchBean> matchBeanList = new ArrayList<MatchBean>();
        if (lotteryCode.equals("200")) {
            SubIssueForJingCaiZuQiu subIssueForJingCaiZuQiu = null;
            for (MatchItemInfo matchItemInfo : matchItemInfoList) {
                MatchBean matchBean = new MatchBean();
                if (Utils.isNotEmpty(matchItemInfo)) {
                    issue = matchItemInfo.getMatchId().substring(0, 8);
                    sn = matchItemInfo.getMatchId().substring(8);
                    String playC = playCode;
                    matchBean.setPlayCode(matchItemInfo.getPlayCode());
                    subIssueForJingCaiZuQiu = subIssueForJingCaiZuQiuService.getSubIssueForJingCaiZuQiuBySnDate(sn, issue, "00", "02");

                    if (Utils.isNotEmpty(subIssueForJingCaiZuQiu)) {
                        Integer mainTeamScore = subIssueForJingCaiZuQiu.getMainTeamScore();
                        Integer guestTeamScore = subIssueForJingCaiZuQiu.getGuestTeamScore();
                        if (subIssueForJingCaiZuQiu.getEndOperator().equals(Constants.SUB_ISSUE_END_OPERATOR_CANCEL)) {
                            matchBean.setMatchNo(subIssueForJingCaiZuQiu.getWeek() + sn + "(已取消)");
                        } else {
                            matchBean.setMatchNo(subIssueForJingCaiZuQiu.getWeek() + sn);
                        }
                        matchBean.setOpenTime(subIssueForJingCaiZuQiu.getEndTime());
                        matchBean.setGuestTeam(subIssueForJingCaiZuQiu.getGuestTeam());
                        matchBean.setMainTeam(subIssueForJingCaiZuQiu.getMainTeam());
                        if (null == subIssueForJingCaiZuQiu.getMainTeamHalfScore()) {
                            matchBean.setMainTeamHalfScore("");
                        } else {
                            matchBean.setMainTeamHalfScore(subIssueForJingCaiZuQiu.getMainTeamHalfScore() + "");
                        }
                        if (null == subIssueForJingCaiZuQiu.getGuestTeamHalfScore()) {
                            matchBean.setGuestTeamHalfScore("");
                        } else {
                            matchBean.setGuestTeamHalfScore(subIssueForJingCaiZuQiu.getGuestTeamHalfScore() + "");
                        }
                        if (null == mainTeamScore) {
                            matchBean.setMainTeamScore("");
                        } else {
                            matchBean.setMainTeamScore(mainTeamScore + "");
                        }
                        if (null == guestTeamScore) {
                            matchBean.setGuestTeamScore("");
                        } else {
                            matchBean.setGuestTeamScore(guestTeamScore + "");
                        }
                        String letBall = "";
                        // 赛果
                        String result = "";
                        if ("10".equals(playCode)) {
                            playC = matchItemInfo.getPlayCode();
                        }
                        if (sendSuccess && Utils.isNotEmpty(returnNumberMap)) {
                            // 让分胜平负
                            if (playC.equals("01")) {
                                letBall = Utils.formatStr(subIssueForJingCaiZuQiu.getLetBall(), "");
                                result = ElTagUtils.spfScore(mainTeamScore, guestTeamScore, letBall);
                            }
                            // 总进球数
                            if (playC.equals("02")) {
                                result = ElTagUtils.zjqsScore(mainTeamScore, guestTeamScore);
                            }
                            // 半场胜平负
                            if (playC.equals("03")) {
                                result = ElTagUtils.spfScore(subIssueForJingCaiZuQiu.getMainTeamHalfScore(), subIssueForJingCaiZuQiu.getGuestTeamHalfScore(), "0")
                                        + ElTagUtils.spfScore(mainTeamScore, guestTeamScore, "0");
                            }
                            // 比分
                            if (playC.equals("04")) {
                                result = ElTagUtils.bfScore(mainTeamScore, guestTeamScore);
                            }
                            // 胜平负
                            if (playC.equals("05")) {
                                result = ElTagUtils.spfScore(mainTeamScore, guestTeamScore, letBall);
                            }
                        }/* else {
                            // 让分胜平负
                            if (playC.equals("01")) {
                                letBall = Utils.formatStr(subIssueForJingCaiZuQiu.getLetBall(), "");
                                result = ElTagUtils.spfScore(mainTeamScore, guestTeamScore, letBall);
                            }
                            // 总进球数
                            if (playC.equals("02")) {
                                result = ElTagUtils.zjqsScore(mainTeamScore, guestTeamScore);
                            }
                            // 半场胜平负
                            if (playC.equals("03")) {
                                result = ElTagUtils.spfScore(subIssueForJingCaiZuQiu.getMainTeamHalfScore(), subIssueForJingCaiZuQiu.getGuestTeamHalfScore(), "0")
                                        + ElTagUtils.spfScore(mainTeamScore, guestTeamScore, "0");
                            }
                            // 比分
                            if (playC.equals("04")) {
                                result = ElTagUtils.bfScore(mainTeamScore, guestTeamScore);
                            }

                            // 胜平负
                            if (playC.equals("05")) {
                                result = ElTagUtils.spfScore(mainTeamScore, guestTeamScore, letBall);
                            }
                        }*/

                        matchBean.setLetBall(letBall);
                        matchBean.setResult(result);

                        String number = matchItemInfo.getValue();
                        String[] numberArray = number.split(",");
                        StringBuffer chinaNumber = new StringBuffer();

                        StringBuffer chinaNumberTemp = new StringBuffer();
                        for (String str : numberArray) {
                            chinaNumber.append(OrderForJCNumberUtils.getNumberChinaValue(lotteryCode + playC + str));
                            String subChinaNumber = OrderForJCNumberUtils.getNumberChinaValue(lotteryCode + playC + str);
                            chinaNumberTemp.append(subChinaNumber).append(",");
                            Double sp = 0d;
                            if (sendSuccess) {
                                // 出票成功，取出票口返回的sp值
                                if (single) {
                                    // 固定奖
//                                    if (playC.equals("04")) {
//                                        sp = Utils.formatDouble(returnNumberMap.get(issue.substring(2) + sn + str) + "", 1.00);
//                                    } else {
//                                        SubIssueForJingCaiZuQiu subIssueForJingCaiZuQiuTemp = subIssueForJingCaiZuQiuService.getSubIssueForJingCaiZuQiuBySnDate(subIssueForJingCaiZuQiu.getSn(),
//                                                subIssueForJingCaiZuQiu.getIssue(), playC, "01");
//                                        // 判断是否有赛果(如果有赛果取最终sp)
//                                        if (Utils.isNotEmpty(result) && subChinaNumber.equals(result)) {
//                                            if (playC.equals("01") || playC.equals("05")) {
//                                                sp = subIssueForJingCaiZuQiuTemp.getWinOrNegaSp();
//                                            } else if (playC.equals("02")) {
//                                                sp = subIssueForJingCaiZuQiuTemp.getTotalGoalSp();
//                                            } else if (playC.equals("03")) {
//                                                sp = subIssueForJingCaiZuQiuTemp.getHalfCourtSp();
//                                            }
//                                        } else {
//                                            sp = (Double) ReflectUtil.attributeValue(subIssueForJingCaiZuQiuTemp, OrderForJCNumberUtils.getSpName(lotteryCode + playC + str));
//                                        }
//                                    }
                                    if (subIssueForJingCaiZuQiu.getEndOperator().equals(Constants.SUB_ISSUE_END_OPERATOR_CANCEL)) {
                                        sp = 2.00;
                                    }else {
                                    	sp = Utils.formatDouble(returnNumberMap.get(issue.substring(2) + sn + str) + "", 1.00);
									}
                                } else {
                                    if (subIssueForJingCaiZuQiu.getEndOperator().equals(Constants.SUB_ISSUE_END_OPERATOR_CANCEL)) {
                                        sp = 1.00;
                                    } else {
                                        sp = Utils.formatDouble(returnNumberMap.get(issue.substring(2) + sn + str) + "", 1.00);
                                    }
                                }
                            } else {
                                // 出票未成功，取场次表中的sp值
                                if (single) {
                                    // 固定奖
//                                    if (playC.equals("04")) {
//                                        sp = (Double) ReflectUtil.attributeValue(subIssueForJingCaiZuQiu, OrderForJCNumberUtils.getSpName(lotteryCode + playC + str));
//                                    } else {
//                                        SubIssueForJingCaiZuQiu subIssueForJingCaiZuQiuTemp = subIssueForJingCaiZuQiuService.getSubIssueForJingCaiZuQiuBySnDate(subIssueForJingCaiZuQiu.getSn(),
//                                                subIssueForJingCaiZuQiu.getIssue(), playC, "01");
//                                        // 判断是否有赛果(如果有赛果取最终sp)
//                                        if (Utils.isNotEmpty(result) && subChinaNumber.equals(result)) {
//                                            if (playC.equals("01") || playC.equals("05")) {
//                                                sp = subIssueForJingCaiZuQiuTemp.getWinOrNegaSp();
//                                            } else if (playC.equals("02")) {
//                                                sp = subIssueForJingCaiZuQiuTemp.getTotalGoalSp();
//                                            } else if (playC.equals("03")) {
//                                                sp = subIssueForJingCaiZuQiuTemp.getHalfCourtSp();
//                                            }
//                                        } else {
//                                            sp = (Double) ReflectUtil.attributeValue(subIssueForJingCaiZuQiuTemp, OrderForJCNumberUtils.getSpName(lotteryCode + playC + str));
//                                        }
//                                    }
                                	sp = 0.00;
                                    if (subIssueForJingCaiZuQiu.getEndOperator().equals(Constants.SUB_ISSUE_END_OPERATOR_CANCEL)) {
                                        sp = 2.00;
                                    }
                                } else {
                                    if (subIssueForJingCaiZuQiu.getEndOperator().equals(Constants.SUB_ISSUE_END_OPERATOR_CANCEL)) {
                                        sp = 1.00;
                                    } else {
                                        sp = (Double) ReflectUtil.attributeValue(subIssueForJingCaiZuQiu, OrderForJCNumberUtils.getSpName(lotteryCode + playC + str));
                                    }
                                }
                            }
                            if (Utils.isNotEmpty(sp) && sp > 0d) {
                                chinaNumber.append("(").append(Utils.formatNumberZ(sp)).append("),");
                            } else {
                                chinaNumber.append(",");
                            }
                        }
                        if (Utils.isNotEmpty(result) && chinaNumberTemp.indexOf(result) > -1) {
                            matchBean.setSelect(1);
                        } else if (subIssueForJingCaiZuQiu.getEndOperator().equals(Constants.SUB_ISSUE_END_OPERATOR_CANCEL)) {
                            matchBean.setSelect(1);
                        } else {
                            matchBean.setSelect(0);
                        }
                        matchBean.setNumberInfo(chinaNumber.substring(0, chinaNumber.length() - 1));
                        matchBeanList.add(matchBean);
                    }
                }
            }
        } else if (lotteryCode.equals("201")) {
            SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiu = null;
            for (MatchItemInfo matchItemInfo : matchItemInfoList) {
                MatchBean matchBean = new MatchBean();
                if (Utils.isNotEmpty(matchItemInfo)) {
                    issue = matchItemInfo.getMatchId().substring(0, 8);
                    sn = matchItemInfo.getMatchId().substring(8);
                    String playC = playCode;
                    matchBean.setPlayCode(matchItemInfo.getPlayCode());
                    subIssueForJingCaiLanQiu = subIssueForJingCaiLanQiuService.getSubIssueForJingCaiLanQiuBySnDate(sn, issue, "00", "02");
                    if (Utils.isNotEmpty(subIssueForJingCaiLanQiu)) {
                        Integer mainTeamScore = subIssueForJingCaiLanQiu.getMainTeamScore();
                        Integer guestTeamScore = subIssueForJingCaiLanQiu.getGuestTeamScore();
                        if (subIssueForJingCaiLanQiu.getEndOperator().equals(Constants.SUB_ISSUE_END_OPERATOR_CANCEL)) {
                            matchBean.setMatchNo(subIssueForJingCaiLanQiu.getWeek() + sn + "(已取消)");

                        } else {
                            matchBean.setMatchNo(subIssueForJingCaiLanQiu.getWeek() + sn);
                        }
                        matchBean.setOpenTime(subIssueForJingCaiLanQiu.getEndTime());
                        matchBean.setGuestTeam(subIssueForJingCaiLanQiu.getGuestTeam());
                        matchBean.setMainTeam(subIssueForJingCaiLanQiu.getMainTeam());
                        if (null == subIssueForJingCaiLanQiu.getMainTeamHalfScore()) {
                            matchBean.setMainTeamHalfScore("");
                        } else {
                            matchBean.setMainTeamHalfScore(subIssueForJingCaiLanQiu.getMainTeamHalfScore() + "");
                        }
                        if (null == subIssueForJingCaiLanQiu.getGuestTeamHalfScore()) {
                            matchBean.setGuestTeamHalfScore("");
                        } else {
                            matchBean.setGuestTeamHalfScore(subIssueForJingCaiLanQiu.getGuestTeamHalfScore() + "");
                        }
                        if (null == mainTeamScore) {
                            matchBean.setMainTeamScore("");
                            matchXml.append("mainTeamScore=\"").append("\" ");
                        } else {
                            matchBean.setMainTeamScore(mainTeamScore + "");
                        }
                        if (null == guestTeamScore) {
                            matchBean.setGuestTeamScore("");
                        } else {
                            matchBean.setGuestTeamScore(guestTeamScore + "");
                        }
                        String letBall = "";
                        String preCast = "";
                        // 赛果
                        String result = "";
                        if ("10".equals(playCode)) {
                            playC = matchItemInfo.getPlayCode();
                        }
                        if (sendSuccess && Utils.isNotEmpty(returnNumberMap)) {
                            // 胜负
                            if (playC.equals("01")) {
                                result = ElTagUtils.spfScore(mainTeamScore, guestTeamScore, "0");
                                if (Utils.isNotEmpty(result)) {
                                    result = "主" + result;
                                }
                            }
                            // 让分胜负
                            if (playC.equals("02")) {
                                if (single) {
                                    SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiuTemp = subIssueForJingCaiLanQiuService.getSubIssueForJingCaiLanQiuBySnDate(subIssueForJingCaiLanQiu.getSn(),
                                            subIssueForJingCaiLanQiu.getIssue(), playC, "01");
                                    letBall = Utils.formatStr(subIssueForJingCaiLanQiuTemp.getLetBall(), "");
                                } else {
                                    letBall = Utils.formatStr(returnNumberMap.get(issue.substring(2) + sn) + "", "0");
                                }
                                result = ElTagUtils.spfScore(mainTeamScore, guestTeamScore, letBall);
                                if (Utils.isNotEmpty(result)) {
                                    result = "主" + result;
                                }
                            }
                            // 胜分差
                            if (playC.equals("03")) {
                                result = ElTagUtils.sfcScore(mainTeamScore, guestTeamScore);
                            }
                            // 大小分
                            if (playC.equals("04")) {
                                if (single) {
                                    SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiuTemp = subIssueForJingCaiLanQiuService.getSubIssueForJingCaiLanQiuBySnDate(subIssueForJingCaiLanQiu.getSn(),
                                            subIssueForJingCaiLanQiu.getIssue(), playC, "01");
                                    preCast = Utils.formatStr(subIssueForJingCaiLanQiuTemp.getPreCast(), "");
                                } else {
                                    preCast = Utils.formatStr(returnNumberMap.get(issue.substring(2) + sn) + "", "0");
                                }
                                result = ElTagUtils.dxfScore(mainTeamScore, guestTeamScore, preCast);
                            }
                        } else {
                            // 胜负
                            if (playC.equals("01")) {
                                result = ElTagUtils.spfScore(mainTeamScore, guestTeamScore, "0");
                                if (Utils.isNotEmpty(result)) {
                                    result = "主" + result;
                                }
                            }
                            // 让分胜负
                            if (playC.equals("02")) {
                                letBall = Utils.formatStr(subIssueForJingCaiLanQiu.getLetBall(), "");
                                result = ElTagUtils.spfScore(mainTeamScore, guestTeamScore, letBall);
                                if (Utils.isNotEmpty(result)) {
                                    result = "主" + result;
                                }
                            }
                            // 胜分差
                            if (playC.equals("03")) {
                                result = ElTagUtils.sfcScore(mainTeamScore, guestTeamScore);
                            }
                            // 大小分
                            if (playC.equals("04")) {
                                preCast = Utils.formatStr(subIssueForJingCaiLanQiu.getPreCast(), "");
                                result = ElTagUtils.dxfScore(mainTeamScore, guestTeamScore, preCast);
                            }
                        }
                        matchBean.setPreCast(preCast);
                        matchBean.setLetBall(letBall);
                        matchBean.setResult(result);

                        String number = matchItemInfo.getValue();
                        String[] numberArray = number.split(",");

                        StringBuffer chinaNumber = new StringBuffer();
                        StringBuffer chinaNumberTemp = new StringBuffer();
                        Map<String, Double> spMap = new HashMap<String, Double>();
                        for (String str : numberArray) {
                            chinaNumber.append(OrderForJCNumberUtils.getNumberChinaValue(lotteryCode + playC + str));
                            String subChinaNumber = OrderForJCNumberUtils.getNumberChinaValue(lotteryCode + playC + str);
                            chinaNumberTemp.append(subChinaNumber).append(",");
                            Double sp = 0d;
                            if (sendSuccess) {
                                // 出票成功，取出票口返回的sp值
//                                if (single) {
//                                    // 固定奖
//                                    if (playC.equals("03")) {
//                                        sp = Utils.formatDouble(returnNumberMap.get(issue.substring(2) + sn + str) + "", 1.00);
//                                    } else {
//                                        SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiuTemp = subIssueForJingCaiLanQiuService.getSubIssueForJingCaiLanQiuBySnDate(subIssueForJingCaiLanQiu.getSn(),
//                                                subIssueForJingCaiLanQiu.getIssue(), playC, "01");
//                                        // 判断是否有赛果(如果有赛果取最终sp)
//                                        if (Utils.isNotEmpty(result) && subChinaNumber.equals(result)) {
//                                            if (playC.equals("01")) {
//                                                sp = subIssueForJingCaiLanQiu.getWinOrNegaSp();
//                                            } else if (playC.equals("02")) {
//                                                sp = subIssueForJingCaiLanQiuTemp.getLetWinOrNegaSp();
//                                            } else if (playC.equals("04")) {
//                                                sp = subIssueForJingCaiLanQiuTemp.getBigOrLittleSp();
//                                            }
//                                        } else {
//                                            sp = (Double) ReflectUtil.attributeValue(subIssueForJingCaiLanQiuTemp, OrderForJCNumberUtils.getSpName(lotteryCode + playC + str));
//                                        }
//                                    }
//                                    if (subIssueForJingCaiLanQiu.getEndOperator().equals(Constants.SUB_ISSUE_END_OPERATOR_CANCEL)) {
//                                        sp = 2.00;
//                                    }
//                                } else {
                                    if (subIssueForJingCaiLanQiu.getEndOperator().equals(Constants.SUB_ISSUE_END_OPERATOR_CANCEL)) {
                                        sp = 1.00;
                                    } else {
                                        sp = Utils.formatDouble(returnNumberMap.get(issue.substring(2) + sn + str) + "", 1.00);
                                    }
//                                }
                            } else {
                                // 出票未成功，取场次表中的sp值
//                                if (single) {
//                                    if (playC.equals("03")) {
//                                        sp = (Double) ReflectUtil.attributeValue(subIssueForJingCaiLanQiu, OrderForJCNumberUtils.getSpName(lotteryCode + playC + str));
//                                    } else {
//                                        SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiuTemp = subIssueForJingCaiLanQiuService.getSubIssueForJingCaiLanQiuBySnDate(subIssueForJingCaiLanQiu.getSn(),
//                                                subIssueForJingCaiLanQiu.getIssue(), playC, "01");
//                                        // 判断是否有赛果(如果有赛果取最终sp)
//                                        if (Utils.isNotEmpty(result) && subChinaNumber.equals(result)) {
//                                            if (playC.equals("01")) {
//                                                sp = subIssueForJingCaiLanQiu.getWinOrNegaSp();
//                                            } else if (playC.equals("02")) {
//                                                sp = subIssueForJingCaiLanQiuTemp.getLetWinOrNegaSp();
//                                            } else if (playC.equals("04")) {
//                                                sp = subIssueForJingCaiLanQiuTemp.getBigOrLittleSp();
//                                            }
//                                        } else {
//                                            sp = (Double) ReflectUtil.attributeValue(subIssueForJingCaiLanQiuTemp, OrderForJCNumberUtils.getSpName(lotteryCode + playC + str));
//                                        }
//                                    }
//                                    if (subIssueForJingCaiLanQiu.getEndOperator().equals(Constants.SUB_ISSUE_END_OPERATOR_CANCEL)) {
//                                        sp = 2.00;
//                                    }
//                                } else {
                                    if (subIssueForJingCaiLanQiu.getEndOperator().equals(Constants.SUB_ISSUE_END_OPERATOR_CANCEL)) {
                                        sp = 1.00;
                                    } else {
                                        sp = (Double) ReflectUtil.attributeValue(subIssueForJingCaiLanQiu, OrderForJCNumberUtils.getSpName(lotteryCode + ticket.getPlayCode() + str));
                                    }
//                                }
                            }
                            if (sp > 0d) {
                                chinaNumber.append("(").append(Utils.formatNumberZ(sp)).append("),");
                            } else {
                                chinaNumber.append(",");
                            }
                        }
                        if (Utils.isNotEmpty(result) && chinaNumberTemp.indexOf(result) > -1) {
                            matchBean.setSelect(1);
                        } else if (subIssueForJingCaiLanQiu.getEndOperator().equals(Constants.SUB_ISSUE_END_OPERATOR_CANCEL)) {
                            matchBean.setSelect(1);
                        } else {
                            matchBean.setSelect(0);
                        }
                        matchBean.setNumberInfo(chinaNumber.substring(0, chinaNumber.length() - 1));
                        matchBeanList.add(matchBean);
                    }
                }
            }
        } else if (lotteryCode.equals("400")) {
            SubIssueForBeiDan subIssueForBeiDan = null;
            for (MatchItemInfo matchItemInfo : matchItemInfoList) {
                MatchBean matchBean = new MatchBean();
                if (Utils.isNotEmpty(matchItemInfo)) {
                    issue = ticket.getIssue();
                    sn = matchItemInfo.getMatchId();
                    String playC = playCode;
                    matchBean.setPlayCode(ticket.getPlayCode());
                    subIssueForBeiDan = subIssueForBeiDanService.getSubIssueForBeiDanByIssueSn(issue, sn);

                    if (Utils.isNotEmpty(subIssueForBeiDan)) {
                        Integer mainTeamScore = subIssueForBeiDan.getMainTeamScore();
                        Integer guestTeamScore = subIssueForBeiDan.getGuestTeamScore();
                        if (subIssueForBeiDan.getEndOperator().equals(Constants.SUB_ISSUE_END_OPERATOR_CANCEL)) {
                            matchBean.setMatchNo(subIssueForBeiDan.getWeek() + sn + "(已取消)");
                        } else {
                            matchBean.setMatchNo(subIssueForBeiDan.getWeek() + sn);
                        }

                        matchBean.setOpenTime(subIssueForBeiDan.getEndTime());
                        matchBean.setGuestTeam(subIssueForBeiDan.getGuestTeam());
                        matchBean.setMainTeam(subIssueForBeiDan.getMainTeam());
                        if (null == subIssueForBeiDan.getMainTeamHalfScore()) {
                            matchBean.setMainTeamHalfScore("");
                        } else {
                            matchBean.setMainTeamHalfScore(subIssueForBeiDan.getMainTeamHalfScore() + "");
                        }
                        if (null == subIssueForBeiDan.getGuestTeamHalfScore()) {
                            matchBean.setGuestTeamHalfScore("");
                        } else {
                            matchBean.setGuestTeamHalfScore(subIssueForBeiDan.getGuestTeamHalfScore() + "");
                        }
                        if (null == mainTeamScore) {
                            matchBean.setMainTeamScore("");
                            matchXml.append("mainTeamScore=\"").append("\" ");
                        } else {
                            matchBean.setMainTeamScore(mainTeamScore + "");
                        }
                        if (null == guestTeamScore) {
                            matchBean.setGuestTeamScore("");
                        } else {
                            matchBean.setGuestTeamScore(guestTeamScore + "");
                        }

                        String letBall = "";
                        // 赛果
                        String result = "";
                        if ("10".equals(playCode)) {
                            playC = matchItemInfo.getPlayCode();
                        }
                        if (sendSuccess) {
                            // 让球胜平负
                            if (playC.equals("01")) {
                                letBall = Utils.formatStr(subIssueForBeiDan.getLetBall(), "");
                                result = ElTagUtils.spfScore(mainTeamScore, guestTeamScore, letBall);
                            }
                            // 上下单双
                            if (playC.equals("02")) {
                                result = ElTagUtils.zjqsScore(mainTeamScore, guestTeamScore);
                            }
                            // 总进球
                            if (playC.equals("03")) {
                                result = ElTagUtils.spfScore(subIssueForBeiDan.getMainTeamHalfScore(), subIssueForBeiDan.getGuestTeamHalfScore(), "0")
                                        + ElTagUtils.spfScore(mainTeamScore, guestTeamScore, "0");
                            }
                            // 半全场胜平负
                            if (playC.equals("04")) {
                                result = ElTagUtils.bfScoreForBeiDan(mainTeamScore, guestTeamScore);
                            }
                            // 单场比分
                            if (playC.equals("05")) {
                                result = ElTagUtils.spfScore(mainTeamScore, guestTeamScore, letBall);
                            }
                        }
                        matchBean.setLetBall(letBall);
                        matchBean.setResult(result);

                        String number = matchItemInfo.getValue();
                        String[] numberArray = number.split(",");
                        StringBuffer chinaNumber = new StringBuffer();

                        StringBuffer chinaNumberTemp = new StringBuffer();
                        for (String str : numberArray) {
                            chinaNumber.append(OrderForJCNumberUtils.getNumberChinaValue(lotteryCode + playC + str));
                            String subChinaNumber = OrderForJCNumberUtils.getNumberChinaValue(lotteryCode + playC + str);
                            chinaNumberTemp.append(subChinaNumber).append(",");
                            Double sp = 0d;
                            if (sendSuccess) {
                                if (Utils.isNotEmpty(result) && subChinaNumber.equals(result)) {
                                    if (playC.equals("01")) {
                                        sp = subIssueForBeiDan.getWinOrNegaSp();
                                    } else if (playC.equals("02")) {
                                        sp = subIssueForBeiDan.getShangXiaPanSp();
                                    } else if (playC.equals("03")) {
                                        sp = subIssueForBeiDan.getTotalGoalSp();
                                    } else if (playC.equals("04")) {
                                        sp = subIssueForBeiDan.getHalfCourtSp();
                                    } else if (playC.equals("05")) {
                                        sp = subIssueForBeiDan.getScoreSp();
                                    }
                                } else {
                                    sp = (Double) ReflectUtil.attributeValue(subIssueForBeiDan, OrderForJCNumberUtils.getSpName(lotteryCode + playC + str));
                                }
                            }
                            if (sp > 0d) {
                                chinaNumber.append("(").append(Utils.formatNumberZ(sp)).append("),");
                            } else {
                                chinaNumber.append(",");
                            }
                        }
                        if (Utils.isNotEmpty(result) && chinaNumberTemp.indexOf(result) > -1) {
                            matchBean.setSelect(1);
                        } else if (subIssueForBeiDan.getEndOperator().equals(Constants.SUB_ISSUE_END_OPERATOR_CANCEL)) {
                            matchBean.setSelect(1);
                        } else {
                            matchBean.setSelect(0);
                        }
                        matchBean.setNumberInfo(chinaNumber.substring(0, chinaNumber.length() - 1));
                        matchBeanList.add(matchBean);
                    }
                }
            }
        }
        SubTicketBean subTicketBean = new SubTicketBean();
        subTicketBean.setGuoGuan(guoGuan);
        subTicketBean.setSingle(single);
        subTicketBean.setSingleFloat(singleFloat);
        subTicketBean.setItem(ticket.getItem());
        subTicketBean.setMultiple(ticket.getMultiple());
        subTicketBean.setMatchBeanList(matchBeanList);
        return subTicketBean;
    }

    public static String formatDate2Str(Date date, String format) {
        try {
            return Utils.formatDate2Str(date, format);
        } catch (Exception e) {
            return Utils.formatDate2Str(new Date(), format);
        }
    }

    public static List getMemberList() {
        IMemberService memberService = (IMemberService) SpringUtils.getBean("memberServiceImpl");
        return memberService.getMemberList();
    }

    public static Boolean getStiTime(String time, String lotteryCode) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d1 = df.parse(time);
            Date d2 = new Date();
            long diff = d2.getTime() - d1.getTime();
            long day = diff / (24 * 60 * 60 * 1000);
            long hour = (diff / (60 * 60 * 1000) - day * 24);
            long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            com.cndym.monitor.bean.LotteryClass lotteryClass = com.cndym.monitor.LotteryList.getLotteryClass(lotteryCode);
            String type = lotteryClass.getType();
            if (day > 0 || hour > 0 || min > 5) {
                return true;
            }
            if ("5".equals(type)) {
                if (day > 0 || hour > 0 || min > 0 || sec > 59) {
                    return true;
                }
            } else if ("2".equals(type)) {
                if (day > 0 || hour > 0 || min > 5) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Boolean getStiTime2(String time, String lotteryCode) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat df1 = new SimpleDateFormat("HH:mm:ss");
        try {
            Date d1 = df.parse(time);
            Date d2 = new Date();
            long diff = d2.getTime() - d1.getTime();
            long day = diff / (24 * 60 * 60 * 1000);
            long hour = (diff / (60 * 60 * 1000) - day * 24);
            long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            com.cndym.monitor.bean.LotteryClass lotteryClass = com.cndym.monitor.LotteryList.getLotteryClass(lotteryCode);
            String type = lotteryClass.getType();
            String isTrue = lotteryClass.getTrue();
            String stratTime = lotteryClass.getStartTime();
            String enTime = lotteryClass.getEndTime();
            if ("true".equals(isTrue)) {
                Calendar nowTime = Calendar.getInstance();
                Calendar sTime = Calendar.getInstance();
                Calendar eTime = Calendar.getInstance();
                nowTime.setTime(df1.parse(df1.format(d2)));
                sTime.setTime(df1.parse(stratTime));
                eTime.setTime(df1.parse(enTime));
                int result1 = nowTime.compareTo(sTime);
                int result2 = nowTime.compareTo(eTime);
                //a>b=1  a<b=-1
                if (stratTime.equals(enTime) || (result1 >= 0 && result2 <= 0)) {
                    if (day > 0 || hour > 0 || min > 30) {
                        return true;
                    }
                    if ("5".equals(type)) {
                        if (day > 0 || hour > 0 || min > 0 || sec > 59) {
                            return true;
                        }
                    } else if ("2".equals(type)) {
                        if (day > 0 || hour > 0 || min > 5) {
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getSyTime(String time) {
        String result = "";
        if (Utils.isEmpty(time)) {
            return result;
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = null;
        try {
            d1 = df.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        Date d2 = new Date();
        long diff = d1.getTime() - d2.getTime();
        long day = diff / (24 * 60 * 60 * 1000);
        long hour = (diff / (60 * 60 * 1000) - day * 24);
        long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        StringBuffer s = new StringBuffer("剩余");
        if (day > 0) {
            s.append(day);
            s.append("天");
        }
        if (hour > 0) {
            s.append(hour);
            s.append("小时");
        }
        if (min > 0) {
            s.append(min);
            s.append("分");
        }
        if (sec > 0) {
            s.append(sec);
            s.append("秒");
        }
        if (s.toString().equals("剩余")) {
            result = "已结期";
        } else {
            s.append("结期");
            result = s.toString();
        }
        return result;
    }

    public static Boolean getEndTime(String time, String sendTime, String lotteryCode) {
        Boolean result = false;
        if (Utils.isEmpty(time) || Utils.isEmpty(sendTime)) {
            return result;
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat df1 = new SimpleDateFormat("HH:mm:ss");
        Date d3 = null;
        try {
            d3 = df.parse(time);
            Date d1 = df.parse(time);
            Date d2 = new Date();
            long diff = d2.getTime() - d1.getTime();
            long day = diff / (24 * 60 * 60 * 1000);
            long hour = (diff / (60 * 60 * 1000) - day * 24);
            long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            com.cndym.monitor.bean.LotteryClass lotteryClass = com.cndym.monitor.LotteryList.getLotteryClass(lotteryCode);
            String type = lotteryClass.getType();
            String isTrue = lotteryClass.getTrue();
            String stratTime = lotteryClass.getStartTime();
            String enTime = lotteryClass.getEndTime();

            Date d4 = new Date();
            long diff1 = d3.getTime() - d4.getTime();
            long day1 = diff1 / (24 * 60 * 60 * 1000);
            long hour1 = (diff1 / (60 * 60 * 1000) - day1 * 24);
            long min1 = ((diff1 / (60 * 1000)) - day1 * 24 * 60 - hour1 * 60);
            if (day1 <= 0 && hour1 < 2) {   //距结期2小时
                if ("5".equals(type)) {
                    if (hour1 <= 0 && min1 <= 4) {  //距结期4分钟
                        return true;
                    }
                } else {
                    return true;
                }
            }
            if ("true".equals(isTrue)) {
                Calendar nowTime = Calendar.getInstance();
                Calendar sTime = Calendar.getInstance();
                Calendar eTime = Calendar.getInstance();
                nowTime.setTime(df1.parse(df1.format(d2)));
                sTime.setTime(df1.parse(stratTime));
                eTime.setTime(df1.parse(enTime));
                int result1 = nowTime.compareTo(sTime);
                int result2 = nowTime.compareTo(eTime);
                if (stratTime.equals(enTime) || (result1 >= 0 && result2 <= 0)) {
                    if (day > 0 || hour > 0 || min > 30) {
                        return true;
                    }
                    if ("5".equals(type)) {
                        if ("107".equals(lotteryCode) && (day > 0 || hour > 0 || min >= 3)) {
                            return true;
                        }
                        if (day > 0 || hour > 0 || min > 0 || sec > 59) {
                            return true;
                        }
                    } else if ("2".equals(type)) {
                        if (day > 0 || hour > 0 || min > 5) {
                            return true;
                        }
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        Calendar c3 = Calendar.getInstance();
        String st = "09:00:00";
        String end = "23:59:59";
        String now = "09:00:00";
        try {
            c1.setTime(formatter.parse(st));
            c2.setTime(formatter.parse(end));
            c3.setTime(formatter.parse(now));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int result1 = c3.compareTo(c1);
        int result2 = c3.compareTo(c2);
        //result1 >= 0
        if (st.equals(end) || (result1 >= 0 && result2 <= 0)) {
            System.out.println("取出");
        }
        System.out.println(result1);
        System.out.println(result2);
    }
}