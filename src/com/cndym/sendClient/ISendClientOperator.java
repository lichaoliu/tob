package com.cndym.sendClient;

import com.cndym.utils.xml.parse.XmlBean;

/**
 * 作者：邓玉明
 * 时间：11-6-3 下午3:58
 * QQ：757579248
 * email：cndym@163.com
 */
public interface ISendClientOperator {
    public void execute(XmlBean xmlBean);

    public void execute(String action, XmlBean xmlBean);
}
