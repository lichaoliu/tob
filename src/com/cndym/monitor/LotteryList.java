package com.cndym.monitor;

import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import com.cndym.monitor.bean.LotteryClass;
import com.cndym.utils.ConfigUtils;
import com.cndym.utils.Utils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.*;

/**
 * 作者：邓玉明 时间：11-3-13 下午4:27 QQ：757579248 email：cndym@163.com
 */

public class LotteryList {
    private static Map<String, LotteryClass> map = new HashMap<String, LotteryClass>();
    private static List<LotteryClass> lotteryClassList = new ArrayList<LotteryClass>();

    static {
        forInstance();
    }

    public static void forInstance() {
        try {
            SAXReader xmlReader = new SAXReader();
            Document document = xmlReader.read(Utils.getClassPath() + ConfigUtils.getValue("TIKCET_MONITOR"));
            List list = document.selectNodes("/lotterys/lottery");
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                Element element = (Element) iterator.next();
                LotteryClass lotteryClass = new LotteryClass();
                lotteryClass.setCode(element.attributeValue("code"));
                lotteryClass.setName(element.attributeValue("name"));
                lotteryClass.setStartTime(element.attributeValue("startTime"));
                lotteryClass.setEndTime(element.attributeValue("endTime"));
                lotteryClass.setTrue(element.attributeValue("isTrue"));
                lotteryClass.setType(element.attributeValue("type"));
                lotteryClass.setIssue(element.attributeValue("issue"));
                lotteryClassList.add(lotteryClass);
                map.put(lotteryClass.getCode(), lotteryClass);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static LotteryClass getLotteryClass(String code) {
        if (map.containsKey(code)) {
            return map.get(code);
        }
        throw new CndymException(ErrCode.E0999);
    }

    public static Map<String, LotteryClass> getLotteryCodeMap() {
        Map<String, LotteryClass> lotteryClassMap = new HashMap<String, LotteryClass>();
        lotteryClassMap.putAll(map);
        return lotteryClassMap;
    }

    public static List<LotteryClass> getLotteryClassList() {
        List<LotteryClass> lotteryClasses = new ArrayList<LotteryClass>();
        lotteryClasses.addAll(lotteryClassList);
        return lotteryClasses;
    }

    public static void main(String[] args) {
        for (LotteryClass lotteryClass : getLotteryClassList()) {
            System.out.println(lotteryClass.getName());
        }
    }


}
