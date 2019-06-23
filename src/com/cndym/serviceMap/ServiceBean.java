package com.cndym.serviceMap;

import org.dom4j.Element;

/**
 * 作者：邓玉明 时间：10-12-22 上午8:48 QQ：757579248 email：cndym@163.com
 */
public class ServiceBean {
    private String cmd;
    private String xsd;//校验文件名
    private String adapter;//适配器
    private String response;//响应

    public static ServiceBean toServiceBean(Element element) {
        return new ServiceBean(element.attributeValue("cmd"),
                element.attributeValue("xsd"),
                element.attributeValue("adapter"),
                element.attributeValue("response"));

    }

    public ServiceBean(String cmd, String xsd) {
        this.cmd = cmd;
        this.xsd = xsd;
        this.adapter = cmd;

    }

    public ServiceBean(String cmd, String xsd, String adapter,String response) {
        this.cmd = cmd;
        this.xsd = xsd;
        this.adapter = adapter;
        this.response = response;

    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getXsd() {
        return xsd;
    }

    public void setXsd(String xsd) {
        this.xsd = xsd;
    }

    public String getAdapter() {
        return adapter;
    }

    public void setAdapter(String adapter) {
        this.adapter = adapter;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
