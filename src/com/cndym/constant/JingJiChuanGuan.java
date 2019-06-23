package com.cndym.constant;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: MengJingyi
 * QQ: 116741034
 * Date: 12-11-15
 * Time: 下午6:04
 * To change this template use File | Settings | File Templates.
 */
public class JingJiChuanGuan {
    private static List zuQiuSpf;
    private static List zuQiuZjq;
    private static List zuQiuBf;
    private static List zuQiuSfBqc;
    private static List lanQiuRfsf;
    private static List lanQiuSf;
    private static List lanQiuSfc;
    private static List lanQiuDxf;

    public static Integer jingJiCcCount8 = 8;
    public static Integer jingJiCcCount6 = 6;
    public static Integer jingJiCcCount4 = 4;
//    public static Integer jingJiCcCount3 = 3;


    static {
        init();
    }

    public static void init() {
        /**
         * 竞彩足球胜负彩
         */
        zuQiuSpf = new ArrayList();
        zuQiuSpf.add("1*1");
        zuQiuSpf.add("2*1");
        zuQiuSpf.add("3*1");
        zuQiuSpf.add("3*3");
        zuQiuSpf.add("3*4");
        zuQiuSpf.add("4*1");
        zuQiuSpf.add("4*4");
        zuQiuSpf.add("4*5");
        zuQiuSpf.add("4*6");
        zuQiuSpf.add("4*11");
        zuQiuSpf.add("5*1");
        zuQiuSpf.add("5*5");
        zuQiuSpf.add("5*6");
        zuQiuSpf.add("5*10");
        zuQiuSpf.add("5*16");
        zuQiuSpf.add("5*20");
        zuQiuSpf.add("5*26");
        zuQiuSpf.add("6*1");
        zuQiuSpf.add("6*6");
        zuQiuSpf.add("6*7");
        zuQiuSpf.add("6*15");
        zuQiuSpf.add("6*20");
        zuQiuSpf.add("6*22");
        zuQiuSpf.add("6*35");
        zuQiuSpf.add("6*42");
        zuQiuSpf.add("6*50");
        zuQiuSpf.add("6*57");
        zuQiuSpf.add("7*1");
        zuQiuSpf.add("7*7");
        zuQiuSpf.add("7*8");
        zuQiuSpf.add("7*21");
        zuQiuSpf.add("7*35");
        zuQiuSpf.add("7*120");
        zuQiuSpf.add("8*1");
        zuQiuSpf.add("8*8");
        zuQiuSpf.add("8*9");
        zuQiuSpf.add("8*28");
        zuQiuSpf.add("8*56");
        zuQiuSpf.add("8*70");
        zuQiuSpf.add("8*247");

        /**
         * 竞彩足球总进球
         */
        zuQiuZjq = new ArrayList();
        zuQiuZjq.add("1*1");
        zuQiuZjq.add("2*1");
        zuQiuZjq.add("3*1");
        zuQiuZjq.add("3*3");
        zuQiuZjq.add("3*4");
        zuQiuZjq.add("4*1");
        zuQiuZjq.add("4*4");
        zuQiuZjq.add("4*5");
        zuQiuZjq.add("4*6");
        zuQiuZjq.add("4*11");
        zuQiuZjq.add("5*1");
        zuQiuZjq.add("5*5");
        zuQiuZjq.add("5*6");
        zuQiuZjq.add("5*10");
        zuQiuZjq.add("5*16");
        zuQiuZjq.add("5*20");
        zuQiuZjq.add("5*26");
        zuQiuZjq.add("6*1");
        zuQiuZjq.add("6*6");
        zuQiuZjq.add("6*7");
        zuQiuZjq.add("6*15");
        zuQiuZjq.add("6*20");
        zuQiuZjq.add("6*22");
        zuQiuZjq.add("6*35");
        zuQiuZjq.add("6*42");
        zuQiuZjq.add("6*50");
        zuQiuZjq.add("6*57");
        /*zuQiuZjq.add("7*1");
        zuQiuZjq.add("7*7");
        zuQiuZjq.add("7*8");
        zuQiuZjq.add("7*21");
        zuQiuZjq.add("7*35");
        zuQiuZjq.add("7*120");
        zuQiuZjq.add("8*1");
        zuQiuZjq.add("8*8");
        zuQiuZjq.add("8*9");
        zuQiuZjq.add("8*28");
        zuQiuZjq.add("8*56");
        zuQiuZjq.add("8*70");
        zuQiuZjq.add("8*247");*/

        /**
         * 竞彩足球比分
         */
        zuQiuBf = new ArrayList();
        zuQiuBf.add("1*1");
        zuQiuBf.add("2*1");
        zuQiuBf.add("3*1");
        zuQiuBf.add("3*3");
        zuQiuBf.add("3*4");
        zuQiuBf.add("4*1");
        zuQiuBf.add("4*4");
        zuQiuBf.add("4*5");
        zuQiuBf.add("4*6");
        zuQiuBf.add("4*11");

        /**
         * 竞彩足球胜负半全场
         */
        zuQiuSfBqc = new ArrayList();
        zuQiuSfBqc.add("1*1");
        zuQiuSfBqc.add("2*1");
        zuQiuSfBqc.add("3*1");
        zuQiuSfBqc.add("3*3");
        zuQiuSfBqc.add("3*4");
        zuQiuSfBqc.add("4*1");
        zuQiuSfBqc.add("4*4");
        zuQiuSfBqc.add("4*5");
        zuQiuSfBqc.add("4*6");
        zuQiuSfBqc.add("4*11");

        /**
         * 竞彩篮球让分胜负
         */
        lanQiuRfsf = new ArrayList();
        lanQiuRfsf.add("1*1");
        lanQiuRfsf.add("2*1");
        lanQiuRfsf.add("3*1");
        lanQiuRfsf.add("3*3");
        lanQiuRfsf.add("3*4");
        lanQiuRfsf.add("4*1");
        lanQiuRfsf.add("4*4");
        lanQiuRfsf.add("4*5");
        lanQiuRfsf.add("4*6");
        lanQiuRfsf.add("4*11");
        lanQiuRfsf.add("5*1");
        lanQiuRfsf.add("5*5");
        lanQiuRfsf.add("5*6");
        lanQiuRfsf.add("5*10");
        lanQiuRfsf.add("5*16");
        lanQiuRfsf.add("5*20");
        lanQiuRfsf.add("5*26");
        lanQiuRfsf.add("6*1");
        lanQiuRfsf.add("6*6");
        lanQiuRfsf.add("6*7");
        lanQiuRfsf.add("6*15");
        lanQiuRfsf.add("6*20");
        lanQiuRfsf.add("6*22");
        lanQiuRfsf.add("6*35");
        lanQiuRfsf.add("6*42");
        lanQiuRfsf.add("6*50");
        lanQiuRfsf.add("6*57");
        lanQiuRfsf.add("7*1");
        lanQiuRfsf.add("7*7");
        lanQiuRfsf.add("7*8");
        lanQiuRfsf.add("7*21");
        lanQiuRfsf.add("7*35");
        lanQiuRfsf.add("7*120");
        lanQiuRfsf.add("8*1");
        lanQiuRfsf.add("8*8");
        lanQiuRfsf.add("8*9");
        lanQiuRfsf.add("8*28");
        lanQiuRfsf.add("8*56");
        lanQiuRfsf.add("8*70");
        lanQiuRfsf.add("8*247");

        /**
         * 竞彩篮球胜负
         */
        lanQiuSf = new ArrayList();
        lanQiuSf.add("1*1");
        lanQiuSf.add("2*1");
        lanQiuSf.add("3*1");
        lanQiuSf.add("3*3");
        lanQiuSf.add("3*4");
        lanQiuSf.add("4*1");
        lanQiuSf.add("4*4");
        lanQiuSf.add("4*5");
        lanQiuSf.add("4*6");
        lanQiuSf.add("4*11");
        lanQiuSf.add("5*1");
        lanQiuSf.add("5*5");
        lanQiuSf.add("5*6");
        lanQiuSf.add("5*10");
        lanQiuSf.add("5*16");
        lanQiuSf.add("5*20");
        lanQiuSf.add("5*26");
        lanQiuSf.add("6*1");
        lanQiuSf.add("6*6");
        lanQiuSf.add("6*7");
        lanQiuSf.add("6*15");
        lanQiuSf.add("6*20");
        lanQiuSf.add("6*22");
        lanQiuSf.add("6*35");
        lanQiuSf.add("6*42");
        lanQiuSf.add("6*50");
        lanQiuSf.add("6*57");
        lanQiuSf.add("7*1");
        lanQiuSf.add("7*7");
        lanQiuSf.add("7*8");
        lanQiuSf.add("7*21");
        lanQiuSf.add("7*35");
        lanQiuSf.add("7*120");
        lanQiuSf.add("8*1");
        lanQiuSf.add("8*8");
        lanQiuSf.add("8*9");
        lanQiuSf.add("8*28");
        lanQiuSf.add("8*56");
        lanQiuSf.add("8*70");
        lanQiuSf.add("8*247");

        /**
         * 竞彩篮球胜分差
         */
        lanQiuSfc = new ArrayList();
        lanQiuSfc.add("1*1");
        lanQiuSfc.add("2*1");
        lanQiuSfc.add("3*1");
        lanQiuSfc.add("3*3");
        lanQiuSfc.add("3*4");
        lanQiuSfc.add("4*1");
        lanQiuSfc.add("4*4");
        lanQiuSfc.add("4*5");
        lanQiuSfc.add("4*6");
        lanQiuSfc.add("4*11");

        /**
         * 竞彩篮球大小分
         */
        lanQiuDxf = new ArrayList();
        lanQiuDxf.add("1*1");
        lanQiuDxf.add("2*1");
        lanQiuDxf.add("3*1");
        lanQiuDxf.add("3*3");
        lanQiuDxf.add("3*4");
        lanQiuDxf.add("4*1");
        lanQiuDxf.add("4*4");
        lanQiuDxf.add("4*5");
        lanQiuDxf.add("4*6");
        lanQiuDxf.add("4*11");
        lanQiuDxf.add("5*1");
        lanQiuDxf.add("5*5");
        lanQiuDxf.add("5*6");
        lanQiuDxf.add("5*10");
        lanQiuDxf.add("5*16");
        lanQiuDxf.add("5*20");
        lanQiuDxf.add("5*26");
        lanQiuDxf.add("6*1");
        lanQiuDxf.add("6*6");
        lanQiuDxf.add("6*7");
        lanQiuDxf.add("6*15");
        lanQiuDxf.add("6*20");
        lanQiuDxf.add("6*22");
        lanQiuDxf.add("6*35");
        lanQiuDxf.add("6*42");
        lanQiuDxf.add("6*50");
        lanQiuDxf.add("6*57");
        lanQiuDxf.add("7*1");
        lanQiuDxf.add("7*7");
        lanQiuDxf.add("7*8");
        lanQiuDxf.add("7*21");
        lanQiuDxf.add("7*35");
        lanQiuDxf.add("7*120");
        lanQiuDxf.add("8*1");
        lanQiuDxf.add("8*8");
        lanQiuDxf.add("8*9");
        lanQiuDxf.add("8*28");
        lanQiuDxf.add("8*56");
        lanQiuDxf.add("8*70");
        lanQiuDxf.add("8*247");
    }


    public static boolean getZuQiuSpf(String cg) {
        return zuQiuSpf.contains(cg);
    }

    public static boolean getZuQiuBf(String cg) {
        return zuQiuBf.contains(cg);
    }

    public static boolean getZuQiuSfBqc(String cg) {
        return zuQiuSfBqc.contains(cg);
    }

    public static boolean getZuQiuZjq(String cg) {
        return zuQiuZjq.contains(cg);
    }

    public static boolean getLanQiuDxf(String cg) {
        return lanQiuDxf.contains(cg);
    }

    public static boolean getLanQiuRfsf(String cg) {
        return lanQiuRfsf.contains(cg);
    }

    public static boolean getLanQiuSf(String cg) {
        return lanQiuSf.contains(cg);
    }

    public static boolean getLanQiuSfc(String cg) {
        return lanQiuSfc.contains(cg);
    }
}
