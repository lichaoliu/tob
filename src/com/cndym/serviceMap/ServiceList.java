package com.cndym.serviceMap;

import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import com.cndym.utils.ConfigUtils;
import com.cndym.utils.Utils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 作者：邓玉明 时间：11-3-13 上午12:50 QQ：757579248 email：cndym@163.com
 */

public class ServiceList {

    public static final String ORDER = "001";

    private static Map<String, ServiceBean> map = new HashMap<String, ServiceBean>();

    static {
        forInstance();
    }

    public static void forInstance() {
        try {
            SAXReader xmlReader = new SAXReader();
            Document document = xmlReader.read(Utils.getClassPath() + ConfigUtils.getValue("SERVICE.CONFIG.PATH"));
            List list = document.selectNodes("/services/service");
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                Element element = (Element) iterator.next();
                ServiceBean lotteryBean = ServiceBean.toServiceBean(element);
                map.put(lotteryBean.getCmd(), lotteryBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ServiceBean getServiceBean(String cmd) {
        if (map.containsKey(cmd)) {
            return map.get(cmd);
        }
        throw new CndymException(ErrCode.E0003);
    }

}
