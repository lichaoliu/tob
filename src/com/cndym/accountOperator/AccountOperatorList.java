package com.cndym.accountOperator;

import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import com.cndym.service.accountOperator.IAccountOperator;
import com.cndym.utils.ConfigUtils;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 作者：邓玉明 时间：11-3-28 下午3:32 QQ：757579248 email：cndym@163.com
 */

public class AccountOperatorList {
    private static Map<String, AccountOperator> map = new HashMap<String, AccountOperator>();

    static {
        forInstance();
    }

    public static void forInstance() {
        try {
            SAXReader xmlReader = new SAXReader();
            System.out.println(Utils.getClassPath() + ConfigUtils.getValue("ACCOUNT_OPERATOR_TYPE"));
            Document document = xmlReader.read(Utils.getClassPath() + ConfigUtils.getValue("ACCOUNT_OPERATOR_TYPE"));
            List list = document.selectNodes("/accountOperator/first");
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                Element element = (Element) iterator.next();
                String firstEventCode = Utils.formatStr(element.attributeValue("type"));
                String firstName = Utils.formatStr(element.attributeValue("name"));
                List<Element> secondList = element.elements();
                for (Element second : secondList) {
                    String secondEventCode = Utils.formatStr(second.attributeValue("type"));
                    String secondName = Utils.formatStr(second.attributeValue("name"));
                    List<Element> thirdList = second.elements();
                    for (Element third : thirdList) {
                        String thirdEventCode = Utils.formatStr(third.attributeValue("type"));
                        String thirdName = Utils.formatStr(third.attributeValue("name"));
                        AccountOperator accountOperator = new AccountOperator();
                        accountOperator.setEventCode(firstEventCode + "" + secondEventCode + "" + thirdEventCode);
                        accountOperator.setEventType(Integer.parseInt(element.attributeValue("type").trim()));
                        accountOperator.setName(firstName + "-" + secondName + "-" + thirdName);
                        accountOperator.setNickName(third.attributeValue("nickName").trim());
                        accountOperator.setType(secondEventCode);
                        accountOperator.setOperator(third.attributeValue("operator"));
                        map.put(accountOperator.getEventCode(), accountOperator);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static AccountOperator getAccountOperator(String eventCode) {
        if (map.containsKey(eventCode)) {
            return map.get(eventCode);
        }
        throw new CndymException(ErrCode.E0003);
    }

    public static IAccountOperator getOperatorService(String eventCode) {
        return (IAccountOperator) SpringUtils.getBean(getAccountOperator(eventCode).getOperator() + "Operator");
    }


}
