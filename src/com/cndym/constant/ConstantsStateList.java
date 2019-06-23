package com.cndym.constant;

import java.util.*;

public class ConstantsStateList {
    private static Map<Integer, String> bonusStatusMap = new HashMap<Integer, String>();// 中奖状态
    private static Map<Integer, String> ticketMap = new HashMap<Integer, String>();//票状态
    //    private static Map<String, String> shuX = new HashMap<String, String>();//属相
    private static Map<Integer, String> managesLogType = new HashMap<Integer, String>();//操作日志类型
    private static Map<String, Integer> issueStatusMap = new LinkedHashMap<String, Integer>();
    //奖级
    private static Map<String, Map<Integer, String>> bonusLevleMap = new HashMap<String, Map<Integer, String>>();
    //双色球
    private static Map<Integer, String> ssqMap = new HashMap<Integer, String>();
    //3D
    private static Map<Integer, String> d3Map = new HashMap<Integer, String>();
    //15选5
    private static Map<Integer, String> s5x5Map = new HashMap<Integer, String>();
    //七乐彩
    private static Map<Integer, String> qlcMap = new HashMap<Integer, String>();
    //东方6
    private static Map<Integer, String> df61Map = new HashMap<Integer, String>();
    //时时彩
    private static Map<Integer, String> sscMap = new HashMap<Integer, String>();
    //大乐透
    private static Map<Integer, String> dltMap = new HashMap<Integer, String>();
    //排列三
    private static Map<Integer, String> p3Map = new HashMap<Integer, String>();
    //排列五
    private static Map<Integer, String> p5Map = new HashMap<Integer, String>();
    //22选5
    private static Map<Integer, String> e2x5Map = new HashMap<Integer, String>();
    //七星彩
    private static Map<Integer, String> qxcMap = new HashMap<Integer, String>();
    //胜负彩
    private static Map<Integer, String> sfcMap = new HashMap<Integer, String>();
    //任九场
    private static Map<Integer, String> r9Map = new HashMap<Integer, String>();
    //六场半全
    private static Map<Integer, String> bqcMap = new HashMap<Integer, String>();
    //进球彩
    private static Map<Integer, String> jqcMap = new HashMap<Integer, String>();
    //11选5
    private static Map<Integer, String> l115Map = new HashMap<Integer, String>();
    //是否自主算奖
    private static Map<String, String> operatorsAwardMap = new LinkedHashMap<String, String>();

    private static Map<String, String> bonusOperatorsMap = new LinkedHashMap<String, String>();

    static {
        init();
    }

    public static void init() {

        issueStatusMap.put("预售", Constants.ISSUE_STATUS_WAIT);
        issueStatusMap.put("销售中", Constants.ISSUE_STATUS_START);
        issueStatusMap.put("暂停", Constants.ISSUE_STATUS_PAUSE);
        issueStatusMap.put("结期", Constants.ISSUE_STATUS_END);

        bonusStatusMap.put(0, "等待开奖");
        bonusStatusMap.put(1, "中奖");
        bonusStatusMap.put(2, "未中奖");

        ticketMap.put(0, "已发消息队列");
        ticketMap.put(1, "需要程序查询出来");
        ticketMap.put(2, "票已发送出去但是没有收到回执");
        ticketMap.put(3, "出票成功");
        ticketMap.put(4, "出票失败");
        ticketMap.put(5, "取消出票");
        ticketMap.put(6, "流单");

        managesLogType.put(0, "默认类型");
        managesLogType.put(1, "登录/退出");
        managesLogType.put(2, "日终处理");
        managesLogType.put(3, "会员管理");
        managesLogType.put(4, "投注管理");
        managesLogType.put(5, "派奖处理");
        managesLogType.put(6, "提醒管理");
        managesLogType.put(7, "系统管理");
        managesLogType.put(8, "录入足彩彩期");
        managesLogType.put(9, "启动算奖");
        managesLogType.put(10, "录入开奖号码");
        managesLogType.put(11, "录入体彩开奖公告");
        managesLogType.put(12, "接入代理管理");
        managesLogType.put(13, "批量录入彩期");
        managesLogType.put(14, "德彩福彩出票口");
        managesLogType.put(15, "德彩体彩出票口");
        managesLogType.put(16, "北京单场出票口");
        managesLogType.put(17, "竞彩彩期管理");


        ssqMap.put(0, "一等奖");
        ssqMap.put(1, "二等奖");
        ssqMap.put(2, "三等奖");
        ssqMap.put(3, "四等奖");
        ssqMap.put(4, "五等奖");
        ssqMap.put(5, "六等奖");
        ssqMap.put(6, "七等奖");
        ssqMap.put(7, "八等奖");

        d3Map.put(0, "单选奖");
        d3Map.put(1, "组三奖");
        d3Map.put(2, "组六奖");

        s5x5Map.put(0, "特等奖");
        s5x5Map.put(1, "一等奖");
        s5x5Map.put(2, "二等奖");


        qlcMap.put(0, "一等奖");
        qlcMap.put(1, "二等奖");
        qlcMap.put(2, "三等奖");
        qlcMap.put(3, "四等奖");
        qlcMap.put(4, "五等奖");
        qlcMap.put(5, "六等奖");
        qlcMap.put(6, "七等奖");

        df61Map.put(0, "一等奖");
        df61Map.put(1, "二等奖");
        df61Map.put(2, "三等奖");
        df61Map.put(3, "四等奖");
        df61Map.put(4, "五等奖");
        df61Map.put(5, "六等奖");

        sscMap.put(0, "五星奖");
        sscMap.put(1, "四星一等奖");
        sscMap.put(2, "四星二等奖");
        sscMap.put(3, "三星奖");
        sscMap.put(4, "二星奖");
        sscMap.put(5, "一星奖");
        sscMap.put(6, "大小单双奖");
        sscMap.put(7, "二星组选");
        sscMap.put(8, "五星通选一等奖");
        sscMap.put(9, "五星通选二等奖");
        sscMap.put(10, "五星通选三等奖");
        sscMap.put(11, "任选一奖");
        sscMap.put(12, "任选二奖");
        sscMap.put(13, "三星组三奖");
        sscMap.put(14, "三星组六奖");

        dltMap.put(0, "一等奖");
        dltMap.put(1, "二等奖");
        dltMap.put(2, "三等奖");
        dltMap.put(3, "四等奖");
        dltMap.put(4, "五等奖");
        dltMap.put(5, "六等奖");
        dltMap.put(6, "七等奖");
        dltMap.put(7, "八等奖");
        dltMap.put(17, "八等奖");
        dltMap.put(9, "幸运奖");
        dltMap.put(10, "一等奖追加");
        dltMap.put(11, "二等奖追加");
        dltMap.put(12, "三等奖追加");
        dltMap.put(13, "四等奖追加");
        dltMap.put(14, "五等奖追加");
        dltMap.put(15, "六等奖追加");
        dltMap.put(16, "七等奖追加");

        p3Map.put(0, "直选奖");
        p3Map.put(1, "组三奖");
        p3Map.put(2, "组六奖");

        p5Map.put(0, "一等奖");

        qxcMap.put(0, "一等奖");
        qxcMap.put(1, "二等奖");
        qxcMap.put(2, "三等奖");
        qxcMap.put(3, "四等奖");
        qxcMap.put(4, "五等奖");
        qxcMap.put(5, "六等奖");

        e2x5Map.put(0, "一等奖");
        e2x5Map.put(1, "二等奖");
        e2x5Map.put(2, "三等奖");

        sfcMap.put(0, "一等奖");
        sfcMap.put(1, "二等奖");

        r9Map.put(0, "任九奖");

        bqcMap.put(0, "一等奖");

        jqcMap.put(0, "一等奖");

        l115Map.put(0, "选前三奖");
        l115Map.put(1, "任选五奖");
        l115Map.put(2, "选前三组选奖");
        l115Map.put(3, "选前二奖");
        l115Map.put(4, "任选六奖");
        l115Map.put(5, "任选四奖");
        l115Map.put(6, "选前二组选奖");
        l115Map.put(7, "任选七奖");
        l115Map.put(8, "任选三奖");
        l115Map.put(9, "任选一奖");
        l115Map.put(10, "任选八奖");
        l115Map.put(11, "任选二奖");

        bonusLevleMap.put("001", ssqMap);
        bonusLevleMap.put("002", d3Map);
        bonusLevleMap.put("003", s5x5Map);
        bonusLevleMap.put("004", qlcMap);
        bonusLevleMap.put("005", df61Map);
        bonusLevleMap.put("006", sscMap);

        bonusLevleMap.put("113", dltMap);
        bonusLevleMap.put("108", p3Map);
        bonusLevleMap.put("109", p5Map);
        bonusLevleMap.put("110", qxcMap);
        bonusLevleMap.put("111", e2x5Map);
        bonusLevleMap.put("107", l115Map);

        bonusLevleMap.put("300", sfcMap);
        bonusLevleMap.put("303", r9Map);
        bonusLevleMap.put("301", bqcMap);
        bonusLevleMap.put("302", jqcMap);


        operatorsAwardMap.put(Constants.OPERATORS_AWARD_WAIT + "", "未算奖");
        operatorsAwardMap.put(Constants.OPERATORS_AWARD_DOING + "", "算奖中");
        operatorsAwardMap.put(Constants.OPERATORS_AWARD_COMPLETE + "", "已算奖");

        bonusOperatorsMap.put(Constants.ISSUE_BONUS_STATUS_NO + "", "未返奖");
        bonusOperatorsMap.put(Constants.ISSUE_BONUS_STATUS_YES + "", "已返奖");
    }

    public static Map<String, Integer> getIssueStatusMap() {
        Map<String, Integer> issueStatus = new HashMap<String, Integer>();
        issueStatus.putAll(issueStatusMap);
        return issueStatus;
    }

    /**
     * 中奖状态
     *
     * @param state
     * @return
     */
    public static String getBonusStatus(Integer state) {
        if (bonusStatusMap.containsKey(state)) {
            return bonusStatusMap.get(state);
        }
        // "没有找到适合（" + state + "）的处理器"
        return "";
    }

    public static String getTicketMap(Integer state) {
        if (ticketMap.containsKey(state)) {
            return ticketMap.get(state);
        }
        return "";
    }

    public static String getManagesLogType(Integer key) {
        if (managesLogType.containsKey(key)) {
            return managesLogType.get(key);
        }
        return "";
    }

    public static String getBonusLevel(String lotteryCode, Integer index) {
        if (bonusLevleMap.containsKey(lotteryCode)) {
            if (bonusLevleMap.get(lotteryCode).containsKey(index)) {
                return bonusLevleMap.get(lotteryCode).get(index);
            }
        }
        return "";
    }

    public static Map<Integer, String> getAllManagesLogType() {
        return managesLogType;
    }

    public static Map<String, String> getOperatorsAwardMap() {
        return operatorsAwardMap;
    }

    public static String getOperatorsAward(String code) {
        if (operatorsAwardMap.containsKey(code)) {
            return operatorsAwardMap.get(code);
        }
        return "";
    }

    public static Map<String, String> getBonusOperatorsMap() {
        return bonusOperatorsMap;
    }

    public static String getBonusOperators(String code) {
        if (bonusOperatorsMap.containsKey(code)) {
            return bonusOperatorsMap.get(code);
        }
        return "";
    }
}
