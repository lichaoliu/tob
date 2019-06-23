package com.cndym.adapter.tms.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：邓玉明
 * email：cndym@163.com
 * QQ：757579248
 * 时间: 12-2-19 上午11:37
 */
public class JingJiChuanGuan {
    private static Map<JingJiChuanGuanKey, Integer[]> jingJiChuanGuanKeyMap = new HashMap<JingJiChuanGuanKey, Integer[]>();

    static {
        forInstance();
    }

    public static void forInstance() {
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(1, 1), new Integer[]{0, 1});
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(2, 1), new Integer[]{0, 0, 1});
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(2, 3), new Integer[]{0, 1, 1});//bd
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(3, 1), new Integer[]{0, 0, 0, 1});
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(3, 3), new Integer[]{0, 0, 1, 0});
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(3, 4), new Integer[]{0, 0, 1, 1});
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(3, 7), new Integer[]{0, 1, 1, 1});//bd
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(4, 1), new Integer[]{0, 0, 0, 0, 1});
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(4, 4), new Integer[]{0, 0, 0, 1, 0});
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(4, 5), new Integer[]{0, 0, 0, 1, 1});
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(4, 6), new Integer[]{0, 0, 1, 0, 0});
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(4, 11), new Integer[]{0, 0, 1, 1, 1});
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(4, 15), new Integer[]{0, 1, 1, 1, 1});//bd
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(5, 1), new Integer[]{0, 0, 0, 0, 0, 1});
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(5, 5), new Integer[]{0, 0, 0, 0, 1, 0});
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(5, 6), new Integer[]{0, 0, 0, 0, 1, 1});
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(5, 10), new Integer[]{0, 0, 1, 0, 0, 0});
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(5, 16), new Integer[]{0, 0, 0, 1, 1, 1});
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(5, 20), new Integer[]{0, 0, 1, 1, 0, 0});
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(5, 26), new Integer[]{0, 0, 1, 1, 1, 1});
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(5, 31), new Integer[]{0, 1, 1, 1, 1, 1});//bd
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(6, 1), new Integer[]{0, 0, 0, 0, 0, 0, 1});
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(6, 6), new Integer[]{0, 0, 0, 0, 0, 1, 0});
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(6, 7), new Integer[]{0, 0, 0, 0, 0, 1, 1});
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(6, 15), new Integer[]{0, 0, 1, 0, 0, 0, 0});
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(6, 20), new Integer[]{0, 0, 0, 1, 0, 0, 0});
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(6, 22), new Integer[]{0, 0, 0, 0, 1, 1, 1});
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(6, 35), new Integer[]{0, 0, 1, 1, 0, 0, 0});
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(6, 42), new Integer[]{0, 0, 0, 1, 1, 1, 1});
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(6, 50), new Integer[]{0, 0, 1, 1, 1, 0, 0});
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(6, 57), new Integer[]{0, 0, 1, 1, 1, 1, 1});
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(6, 63), new Integer[]{0, 1, 1, 1, 1, 1, 1});//bd
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(7, 1), new Integer[]{0, 0, 0, 0, 0, 0, 0, 1});
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(7, 7), new Integer[]{0, 0, 0, 0, 0, 0, 1, 0});
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(7, 8), new Integer[]{0, 0, 0, 0, 0, 0, 1, 1});
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(7, 21), new Integer[]{0, 0, 0, 0, 0, 1, 0, 0});
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(7, 35), new Integer[]{0, 0, 0, 0, 1, 0, 0, 0});
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(7, 120), new Integer[]{0, 0, 1, 1, 1, 1, 1, 1});
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(8, 1), new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 1});
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(8, 8), new Integer[]{0, 0, 0, 0, 0, 0, 0, 1, 0});
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(8, 9), new Integer[]{0, 0, 0, 0, 0, 0, 0, 1, 1});
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(8, 28), new Integer[]{0, 0, 0, 0, 0, 0, 1, 0, 0});
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(8, 56), new Integer[]{0, 0, 0, 0, 0, 1, 0, 0, 0});
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(8, 70), new Integer[]{0, 0, 0, 0, 1, 0, 0, 0, 0});
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(8, 247), new Integer[]{0, 0, 1, 1, 1, 1, 1, 1, 1});
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(9, 1), new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 1});//bd
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(10, 1), new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1});//bd
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(11, 1), new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1});//bd
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(12, 1), new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1});//bd
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(13, 1), new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1});//bd
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(14, 1), new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1});//bd
        jingJiChuanGuanKeyMap.put(new JingJiChuanGuanKey(15, 1), new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1});//bd


    }

    public static Integer[] getIntegers(JingJiChuanGuanKey jingJiChuanGuanKey) {
        if (jingJiChuanGuanKeyMap.containsKey(jingJiChuanGuanKey)) {
            return jingJiChuanGuanKeyMap.get(jingJiChuanGuanKey);
        }
        throw new IllegalArgumentException("无法找到(" + jingJiChuanGuanKey + ")对应的串关序列");
    }



}
