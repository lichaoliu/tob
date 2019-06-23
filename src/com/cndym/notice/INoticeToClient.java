package com.cndym.notice;

import com.cndym.utils.xml.parse.XmlBean;

/**
 * 作者：邓玉明
 * 时间：11-7-17 下午7:16
 * QQ：757579248
 * email：cndym@163.com
 */
public interface INoticeToClient {
    //期次通知到合作方

    /**
     * @param xmlBean 期次彩种，期次号，状态，开期时间，结期时间
     * @return
     */
    public XmlBean issueToClient(XmlBean xmlBean);
    //方案出票结果通知到合作方

    /**
     * @param xmlBean 方案号，金额，成交情况，票号
     * @return
     */
    public XmlBean programsToClient(XmlBean xmlBean);
    //中奖期情况通知到合作方

    /**
     * @param xmlBean 中奖方案号，中奖总金额，单票中奖情况
     * @return
     */
    public XmlBean bonusToClient(XmlBean xmlBean);

    /**
     * 开奖信息
     * @return
     */
    public XmlBean bonusInfoToClient(XmlBean xmlBean);
}
