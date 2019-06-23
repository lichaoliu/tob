package com.cndym.notice.impl;

import com.cndym.constant.Constants;
import com.cndym.notice.INoticeToClient;
import com.cndym.serviceMap.ServiceList;
import com.cndym.utils.ConfigUtils;
import com.cndym.utils.Md5;
import com.cndym.utils.xml.parse.ByteCodeUtil;
import com.cndym.utils.xml.parse.XmlBean;
import com.cndym.xmlObject.BuilderXml;
import com.cndym.xmlObject.Header;
import com.sun.xml.internal.ws.wsdl.writer.document.soap.Body;
import org.apache.log4j.Logger;

/**
 * 作者：邓玉明
 * 时间：11-7-17 下午7:22
 * QQ：757579248
 * email：cndym@163.com
 */
public class BaseNoticeToClient implements INoticeToClient {

    private Logger logger = Logger.getLogger(this.getClass());

    @Override
    public XmlBean issueToClient(XmlBean xmlBean) {
        return null;
    }

    @Override
    public XmlBean programsToClient(XmlBean xmlBean) {
        return null;
    }

    @Override
    public XmlBean bonusToClient(XmlBean xmlBean) {
        return null;
    }

    @Override
    public XmlBean bonusInfoToClient(XmlBean xmlBean) {
        return null;
    }

    /**
     * 加密发送内容
     *
     * @param digestType
     * @param key
     * @param text
     * @return
     */
    private String digestText(String digestType, String key, String text) {
        if (Constants.MD5_DIGEST_TYPE.equals(digestType.toLowerCase())) {
            String keyStr = key + text;
            text = Md5.Md5(keyStr);
        }
        return text;
    }
}
