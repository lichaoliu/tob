package com.cndym.bean.sys;

import java.io.Serializable;
import java.util.Date;

/**
 * 作者：邓玉明 时间：11-5-26 下午2:42 QQ：757579248 email：cndym@163.com
 */
public class DistributionLock implements Serializable {
    private static final long serialVersionUID = 5009897502506644494L;
    public static final String LOCK_FOR_ISSUE_QUARTZ = "issueQuartz";
    public static final String LOCK_FOR_SUB_ISSUE_QUARTZ = "subIssueQuartz";
    public static final String LOCK_FOR_ORDER_QUARTZ = "orderQuartz";
    public static final String LOCK_FOR_CANCEL_ORDER_QUARTZ = "cancelOrderQuartz";
    public static final String LOCK_FOR_TICKET_FAILED_QUARTZ = "ticketFailedQuartz";
    public static final String LOCK_FOR_ACCOUNT_OPERATOR = "accountOperator";
    public static final String LOCK_FOR_SEND_EMAIL_QUARTZ = "sendEmailQuartz";
    public static final String LOCK_FOR_SEND_SMS_QUARTZ = "sendSmsQuartz";
    public static final String LOCK_FOR_COUNT_TABLE_QUARTZ = "accountTableQuartz";
    public static final String LOCK_FOR_DALETE_MEMBER_QUARTZ = "delteUserQuartz";
    public static final String LOCK_FOR_DALETE_Fill_QUARTZ = "delteFillQuartz";
    public static final String LOCK_FOR_JX_BONUS_QUERY = "jxBonusQuery";
    public static final String LOCK_FOR_LY_BONUS_QUERY = "lyBonusQuery";
    public static final String LOCK_FOR_JX_BONUS_INFO_QUERY = "jxBonusInfoQuery";
    public static final String LOCK_FOR_LY_BONUS_INFO_QUERY = "lyBonusInfoQuery";
    public static final String LOCK_FOR_JX_ISSUE_QUERY = "jxIssueQuery";
    public static final String LOCK_FOR_DYJ_ISSUE_QUERY = "dyjIssueQuery";
    public static final String LOCK_FOR_LC_ISSUE_QUERY = "lcIssueQuery";
    public static final String LOCK_FOR_LY_ISSUE_QUERY = "lyIssueQuery";
    public static final String LOCK_FOR_LC_BONUS_INFO_QUERY = "lcBonusInfoQuery";
    public static final String LOCK_FOR_DYJ_BONUS_INFO_QUERY = "dyjBonusInfoQuery";
    public static final String LOCK_FOR_JX_ORDER_QUERY = "jxOrderQuery";
    public static final String LOCK_FOR_LY_ORDER_QUERY = "lyOrderQuery";
    public static final String LOCK_FOR_ORDER_TOP = "orderTopQuartz";
    public static final String LOCK_FOR_CMB_FILL_QUERY = "cmbFillQuery";

    public static final String LOCK_FOR_CQ_ISSUE_QUERY = "cqIssueQuery";
    public static final String LOCK_FOR_CQ_GP_ISSUE_QUERY = "cqGpIssueQuery";
    public static final String LOCK_FOR_CQ_BONUS_QUERY = "cqBonusQuery";
    public static final String LOCK_FOR_CQ_ORDER_QUERY = "cqOrderQuery";
    public static final String LOCK_FOR_CQ_GP_ORDER_QUERY = "cqGpOrderQuery";
    public static final String LOCK_FOR_CQ_GP_BONUS_INFO_QUERY = "cqGpBonusInfoQuery";

    public static final String LOCK_FOR_SL_ISSUE_QUERY = "slIssueQuery";
    // public static final String LOCK_FOR_CQ_GP_ISSUE_QUERY = "slGpIssueQuery";
    public static final String LOCK_FOR_SL_BONUS_QUERY = "slBonusQuery";
    public static final String LOCK_FOR_SL_ORDER_QUERY = "slOrderQuery";

    public static final String LOCK_FOR_ZZC_ISSUE_QUERY = "zzcIssueQuery";
    public static final String LOCK_FOR_ZZC_GP_ISSUE_QUERY = "zzcGpIssueQuery";
    public static final String LOCK_FOR_ZZC_ORDER_QUERY = "zzcOrderQuery";
    public static final String LOCK_FOR_ZZC_GP_ORDER_QUERY = "zzcGpOrderQuery";
    public static final String LOCK_FOR_ZZC_BONUS_QUERY = "zzcBonusQuery";
    public static final String LOCK_FOR_ZZC_GP_BONUS_QUERY = "zzcGpBonusQuery";
    public static final String LOCK_FOR_ZZC_GP_BONUS_INFO_QUERY = "zzcGpBonusInfoQuery";

    //
    public static final String LOCK_FOR_FCBY_ISSUE_QUERY = "fcbyIssueQuery";
    public static final String LOCK_FOR_FCBY_GP_ISSUE_QUERY = "fcbyGpIssueQuery";
    public static final String LOCK_FOR_FCBY_ORDER_QUERY = "fcbyOrderQuery";
    public static final String LOCK_FOR_FCBY_GP_BONUS_INFO_QUERY = "fcbyGpBonusInfoQuery";
    public static final String LOCK_FOR_FCBY_GP_BONUS_QUERY = "fcbyGpBonusQuery";

    public static final String LOCK_FOR_NEW_FCBY_ISSUE_QUERY = "newFcbyIssueQuery";
    public static final String LOCK_FOR_NEW_FCBY_GP_ISSUE_QUERY = "newFcbyGpIssueQuery";
    public static final String LOCK_FOR_NEW_FCBY_ORDER_QUERY = "newFcbyOrderQuery";
    public static final String LOCK_FOR_NEW_FCBY_GP_ORDER_QUERY = "newFcbyGpOrderQuery";
    public static final String LOCK_FOR_NEW_FCBY_GP_BONUS_INFO_QUERY = "newFcbyGpBonusInfoQuery";
    public static final String LOCK_FOR_NEW_FCBY_GP_BONUS_QUERY = "newFcbyGpBonusQuery";

    public static final String LOCK_FOR_WHTC_ORDER_QUERY = "whtcOrderQuery";
    public static final String LOCK_FOR_WHTC_GP_ORDER_QUERY = "whtcGpOrderQuery";
    public static final String LOCK_FOR_WHTC_ISSUE_QUERY = "whtcIssueQuery";
    public static final String LOCK_FOR_WHTC_GP_ISSUE_QUERY = "whtcGpIssueQuery";
    public static final String LOCK_FOR_WHTC_GP_BONUS_QUERY = "whtcGpBonusQuery";
    public static final String LOCK_FOR_WHTC_JCZQ_BONUS_QUERY = "whtcJczqBonusQuery";
    public static final String LOCK_FOR_WHTC_JCLQ_BONUS_QUERY = "whtcJclqBonusQuery";
    public static final String LOCK_FOR_WHTC_GP_BONUS_INFO_QUERY = "whtcGpBonusInfoQuery";

    public static final String LOCK_FOR_AHTC_ORDER_QUERY = "ahtcOrderQuery";
    public static final String LOCK_FOR_AHTC_ISSUE_QUERY = "ahtcIssueQuery";
    public static final String LOCK_FOR_AHTC_GP_ISSUE_QUERY = "ahtcGpIssueQuery";
    public static final String LOCK_FOR_AHTC_GP_BONUS_QUERY = "ahtcGpBonusQuery";
    public static final String LOCK_FOR_AHTC_GP_BONUS_INFO_QUERY = "ahtcGpBonusInfoQuery";

    public static final String LOCK_FOR_AUTO_GP_BONUS_QUERY = "autoGpBonusQuery";
    public static final String LOCK_FOR_AUTO_GP_BONUS_INFO_QUERY = "autoGpBonusInfoQuery";

    public static final String LOCK_FOR_HP_ORDER_QUERY = "hpOrderQuery";
    public static final String LOCK_FOR_HP_ISSUE_QUERY = "hpIssueQuery";

    public static final String LOCK_FOR_RC_TC_ORDER_QUERY = "rctcOrderQuery";
    public static final String LOCK_FOR_RC_TC_ISSUE_QUERY = "rctcIssueQuery";
    public static final String LOCK_FOR_RC_TC_GP_BONUS_QUERY = "rctcGpBonusQuery";
    public static final String LOCK_FOR_RC_TC_GP_ISSUE_QUERY = "rctcGpIssueQuery";
    public static final String LOCK_FOR_RC_TC_GP_BONUS_INFO_QUERY = "rctcGpBonusInfoQuery";
    public static final String LOCK_FOR_RC_TC_GP_ORDER_QUERY = "rctcGpOrderQuery";

    public static final String LOCK_FOR_HP_SSC_GP_BONUS_QUERY = "hpsscGpBonusQuery";
    public static final String LOCK_FOR_HP_SSC_GP_ISSUE_QUERY = "hpsscGpIssueQuery";
    public static final String LOCK_FOR_HP_SSC_GP_BONUS_INFO_QUERY = "hpsscGpBonusInfoQuery";
    public static final String LOCK_FOR_HP_SSC_GP_ORDER_QUERY = "hpsscGpOrderQuery";

    public static final String LOCK_FOR_BTC_ISSUE_QUERY = "btcIssueQuery";
    // public static final String LOCK_FOR_BTC_ISSUE_QUERY = "btcIssueQuery";
    // public static final String LOCK_FOR_BTC_ISSUE_QUERY = "btcIssueQuery";

    public static final String LOCK_FOR_HC_ISSUE_QUERY = "hcIssueQuery";
    public static final String LOCK_FOR_HC_GP_ISSUE_QUERY = "hcGpIssueQuery";
    public static final String LOCK_FOR_HC_ORDER_QUERY = "hcOrderQuery";
    public static final String LOCK_FOR_HC_GP_ORDER_QUERY = "hcGpOrderQuery";
    public static final String LOCK_FOR_HC_GP_ORDER_QUERY_DESC = "hcGpOrderQueryDesc";
    public static final String LOCK_FOR_HC_BONUS_INFO_QUERY = "hcBonusInfoQuery";
    public static final String LOCK_FOR_HC_GP_BONUS_INFO_QUERY = "hcGpBonusInfoQuery";
    public static final String LOCK_FOR_HC_BONUS_QUERY = "hcBonusQuery";
    public static final String LOCK_FOR_HC_GP_BONUS_QUERY = "hcGpBonusQuery";

    // 雪缘园场次状态刷新
    public static final String LOCK_FOR_XYY_MATCH_STATUS_QUARTZ = "xyyMatchStatusQuartz";
    
    //甘肃益天
    public static final String LOCK_FOR_YT_GP_ISSUE_QUERY ="ytGpIssueQuery";
    public static final String LOCK_FOR_YT_GP_BONUS_QUERY ="ytGpBonusQuery";
    public static final String LOCK_FOR_YT_GP_BONUS_INFO_QUERY ="ytGpBonusInfoQuery";
    public static final String LOCK_FOR_YT_GP_ORDER_QUERY ="ytGpOrderQuery";
    public static final String LOCK_FOR_YT_ISSUE_QUERY ="ytIssueQuery";
    public static final String LOCK_FOR_YT_ORDER_QUERY ="ytOrderQuery";
    
    //一源科技
    public static final String LOCK_FOR_YYKJ_GP_ISSUE_QUERY ="yykjGpIssueQuery";
    public static final String LOCK_FOR_YYKJ_GP_BONUS_QUERY ="yykjGpBonusQuery";
    public static final String LOCK_FOR_YYKJ_GP_BONUS_INFO_QUERY ="yykjGpBonusInfoQuery";
    public static final String LOCK_FOR_YYKJ_GP_ORDER_QUERY ="yykjGpOrderQuery";

    // 亿起发会员订单查询
    public static final String LOCK_FOR_YQF_FILL_QUERY = "yqfFillQuery";
    public static final String LOCK_FOR_PCDD_REGISTER_QUERY = "pcddRegisterQuery";

    public static final String LOCK_FOR_DYJ_ORDER_QUERY = "dyjOrderQuery";
    public static final String LOCK_FOR_LC_ORDER_QUERY = "lcOrderQuery";
    public static final String LOCK_FOR_DYJ_JING_JI_ORDER_QUERY = "dyjJingJiOrderQuery";
    public static final String LOCK_FOR_JING_JI_ISSUE_END = "jingJiIssueEndQuartz";
    public static final String LOCK_FOR_DOUDOU_REGISTER_QUERY = "doudouRegisterQuery";

    // 支付宝批量支付，下载支付结果
    public static final String LOCK_ALIPAY_UPLOAD_FIEL = "doAlipayUploadFileQuartz";
    // 佣金结算派发
    public static final String LOCK_FOR_COMMISSION_SEND = "commissionSend";

    // 穗彩相关job
    public static final String LOCK_FOR_SC_ORDER_QUERY = "scOrderQuery";
    public static final String LOCK_FOR_SC_ISSUE_QUERY = "scIssueQuery";
    public static final String LOCK_FOR_SC_BONUS_INFO_QUERY = "scBonusInfoQuery";
    public static final String LOCK_FOR_SC_BONUS_QUERY = "scBonusQuery";

    // 思乐兑奖
    public static final String LOCK_FOR_SL_DUI_JIANG = "slDuiJiang";

    //黑龙江-正好博大
    public static final String LOCK_FOR_HLJ_ISSUE_QUERY = "hljIssueQuery";
    public static final String LOCK_FOR_HLJ_ORDER_QUERY = "hljOrderQuery";

    //江苏-鸿博
    public static final String LOCK_FOR_HB_ISSUE_QUERY = "hbIssueQuery";
    public static final String LOCK_FOR_HB_ORDER_QUERY = "hbOrderQuery";
    public static final String LOCK_FOR_HB_GP_ISSUE_QUERY = "hbGpIssueQuery";
    public static final String LOCK_FOR_HB_GP_ORDER_QUERY = "hbGpOrderQuery";
    public static final String LOCK_FOR_HB_GP_ORDER_QUERY_DESC = "hbGpOrderQueryDesc";
    public static final String LOCK_FOR_HB_GP_BONUS_QUERY = "hbGpBonusQuery";
    public static final String LOCK_FOR_HB_GP_BONUS_INFO_QUERY = "hbGpBonusInfoQuery";

    //睿朗阳光
    public static final String LOCK_FOR_RLYG_ISSUE_QUERY = "rlygIssueQuery";
    public static final String LOCK_FOR_RLYG_ORDER_QUERY = "rlygOrderQuery";
    public static final String LOCK_FOR_RLYG_GP_ISSUE_QUERY = "rlygGpIssueQuery";
    public static final String LOCK_FOR_RLYG_GP_ORDER_QUERY = "rlygGpOrderQuery";
    public static final String LOCK_FOR_RLYG_GP_BONUS_QUERY = "rlygGpBonusQuery";
    public static final String LOCK_FOR_RLYG_GP_BONUS_INFO_QUERY = "rlygGpBonusInfoQuery";
    public static final String LOCK_FOR_RLYG_MATCH_QUERY_FOR_BEI_DAN = "rlygMatchQueryForBeiDan";
    public static final String LOCK_FOR_RLYG_MATCH_QUERY_FOR_JC = "rlygMatchQueryForJc";
    public static final String LOCK_FOR_RLYG_MATCH_QUERY_FOR_JC_SP = "rlygMatchQueryForJcSp";
    public static final String LOCK_FOR_RLYG_BONUS_QUERY_FOR_BEI_DAN = "rlygBonusQueryForBeiDan";

    //华阳创美
    public static final String LOCK_FOR_HYCM_ORDER_QUERY = "hycmOrderQuery";
    public static final String LOCK_FOR_HYCM_GP_ISSUE_QUERY = "hycmGpIssueQuery";
    public static final String LOCK_FOR_HYCM_GP_ORDER_QUERY = "hycmGpOrderQuery";
    public static final String LOCK_FOR_HYCM_GP_BONUS_QUERY = "hycmGpBonusQuery";
    public static final String LOCK_FOR_HYCM_GP_BONUS_INFO_QUERY = "hycmGpBonusInfoQuery";
    public static final String LOCK_FOR_HYCM_ISSUE_QUERY = "hycmIssueQuery";

    public static final String LOCK_FOR_LHC_ORDER_QUERY = "lhcOrderQuery";
    public static final String LOCK_FOR_LHC_ISSUE_QUERY = "lhcIssueQuery";
    public static final String LOCK_FOR_LHC_GP_BONUS_QUERY = "lhcGpBonusQuery";
    public static final String LOCK_FOR_LHC_GP_ORDER_QUERY = "lhcGpOrderQuery";
    public static final String LOCK_FOR_LHC_GP_BONUS_INFO_QUERY = "lhcGpBonusInfoQuery";
    public static final String LOCK_FOR_LHC_GP_ISSUE_QUERY = "lhcGpIssueQuery";

    public static final String LOCK_FOR_YC_GP_ISSUE_QUERY = "ycGpIssueQuery";
    public static final String LOCK_FOR_YC_GP_ORDER_QUERY = "ycGpOrderQuery";
    public static final String LOCK_FOR_YC_ORDER_QUERY = "ycOrderQuery";
    public static final String LOCK_FOR_YC_GP_BONUS_QUERY = "ycGpBonusQuery";
    public static final String LOCK_FOR_YC_GP_BONUS_INFO_QUERY = "ycGpBonusInfoQuery";

    public static final String LOCK_FOR_COMMON_ORDER_QUERY = "commonOrderQuery";

    //湖北中航
    public static final String LOCK_FOR_HBZH_ISSUE_QUERY = "hbzhIssueQuery";
    public static final String LOCK_FOR_HBZH_ORDER_QUERY = "hbzhOrderQuery";
    public static final String LOCK_FOR_HBZH_GP_ISSUE_QUERY = "hbzhGpIssueQuery";
    public static final String LOCK_FOR_HBZH_GP_ORDER_QUERY = "hbzhGpOrderQuery";
    public static final String LOCK_FOR_HBZH_GP_BONUS_QUERY = "hbzhGpBonusQuery";
    public static final String LOCK_FOR_HBZH_GP_BONUS_INFO_QUERY = "hbzhGpBonusInfoQuery";
    
    //乐透科技
    public static final String LOCK_FOR_LTKJ_ORDER_QUERY = "ltkjOrderQuery";
    public static final String LOCK_FOR_LTKJ_GP_ISSUE_QUERY = "ltkjGpIssueQuery";
    public static final String LOCK_FOR_LTKJ_ISSUE_QUERY = "ltkjIssueQuery";
    public static final String LOCK_FOR_LTKJ_GP_ORDER_QUERY = "ltkjGpOrderQuery";
    public static final String LOCK_FOR_LTKJ_GP_BONUS_INFO_QUERY = "ltkjGpBonusInfoQuery";
    public static final String LOCK_FOR_LTKJ_GP_BONUS_QUERY = "ltkjGpBonusQuery";
    public static final String LOCK_FOR_LTKJ_SZC_BONUS_QUERY = "ltkjSzcBonusQuery";
    
    //联移合通
    public static final String LOCK_FOR_LYHT_ISSUE_QUERY = "lyhtIssueQuery";
    public static final String LOCK_FOR_LYHT_ORDER_QUERY = "lyhtOrderQuery";
    public static final String LOCK_FOR_LYHT_GP_BONUS_QUERY = "lyhtGpBonusQuery";
    public static final String LOCK_FOR_LYHT_GP_ORDER_QUERY = "lyhtGpOrderQuery";
    public static final String LOCK_FOR_LYHT_GP_BONUS_INFO_QUERY = "lyhtGpBonusInfoQuery";
    public static final String LOCK_FOR_LYHT_GP_ISSUE_QUERY = "lyhtGpIssueQuery";
    
    //致胜盈彩
    public static final String LOCK_FOR_ZSYC_ISSUE_QUERY = "zsycIssueQuery";
    public static final String LOCK_FOR_ZSYC_GP_ISSUE_QUERY = "zsycGpIssueQuery";
    public static final String LOCK_FOR_ZSYC_GP_ORDER_QUERY = "zsycGpOrderQuery";
    public static final String LOCK_FOR_ZSYC_ORDER_QUERY = "zsycOrderQuery";
    public static final String LOCK_FOR_ZSYC_GP_BONUS_INFO_QUERY = "zsycGpBonusInfoQuery";
    public static final String LOCK_FOR_ZSYC_GP_BONUS_QUERY = "zsycGpBonusQuery";
    
    public static final String LOCK_FOR_ARZY_ORDER_QUERY = "arzyOrderQuery";
    public static final String LOCK_FOR_ARZY_ISSUE_QUERY = "arzyIssueQuery";
    public static final String LOCK_FOR_ARZY_GP_ISSUE_QUERY = "arzyGpIssueQuery";
    public static final String LOCK_FOR_ARZY_GP_ORDER_QUERY = "arzyGpOrderQuery";
    public static final String LOCK_FOR_ARZY_GP_BONUS_QUERY = "arzyGpBonusQuery";
    public static final String LOCK_FOR_ARZY_GP_BONUS_INFO_QUERY = "arzyGpBonusInfoQuery";
    //中文传媒
    public static final String LOCK_FOR_ZWCM_GP_ISSUE_QUERY = "zwcmGpIssueQuery";
    public static final String LOCK_FOR_ZWCM_GP_ORDER_QUERY = "zwcmGpOrderQuery";
    public static final String LOCK_FOR_ZWCM_GP_BONUS_QUERY = "zwcmGpBonusQuery";
    public static final String LOCK_FOR_ZWCM_GP_BONUS_INFO_QUERY = "zwcmGpBonusInfoQuery";

    public static final String LOCK_FOR_AUTO_CALCULATE_AWARD_QUARTZ = "autoCalculateAwardQuartz";
    
    //尊傲
    public static final String LOCK_FOR_ZA_ISSUE_QUERY = "zaIssueQuery";
    public static final String LOCK_FOR_ZA_ORDER_QUERY = "zaOrderQuery";
    
    //中润阳光
    public static final String LOCK_FOR_ZRYG_ORDER_QUERY = "zrygOrderQuery";
    
    //青海体彩
    public static final String LOCK_FOR_QHTC_ORDER_QUERY = "qhtcOrderQuery";
    public static final String LOCK_FOR_QHTC_GP_ISSUE_QUERY = "qhtcGpIssueQuery";
    public static final String LOCK_FOR_QHTC_ISSUE_QUERY = "qhtcIssueQuery";
    public static final String LOCK_FOR_QHTC_GP_ORDER_QUERY = "qhtcGpOrderQuery";
    public static final String LOCK_FOR_QHTC_GP_BONUS_INFO_QUERY = "qhtcGpBonusInfoQuery";
    public static final String LOCK_FOR_QHTC_GP_BONUS_QUERY = "qhtcGpBonusQuery";

    private Long id;
    private String name;
    private String code;
    private String userName;
    private Date createTime;
    private Integer status;
    private Long interval;
    private Integer stop;

    public DistributionLock(String name) {
        this.name = name;
    }

    public DistributionLock(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public DistributionLock() {
    }

    public DistributionLock(Long id, String name, String code, String userName, Date createTime, Integer status, Long interval, Integer stop) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.userName = userName;
        this.createTime = createTime;
        this.status = status;
        this.interval = interval;
        this.stop = stop;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public Long getInterval() {
		return interval;
	}

	public void setInterval(Long interval) {
		this.interval = interval;
	}

	public Integer getStop() {
		return stop;
	}

	public void setStop(Integer stop) {
		this.stop = stop;
	}
    
}
