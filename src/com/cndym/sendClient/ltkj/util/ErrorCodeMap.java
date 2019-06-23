package com.cndym.sendClient.ltkj.util;

import java.util.HashMap;
import java.util.Map;

public class ErrorCodeMap {
	
    private static Map<String, String> errorCodeMap = new HashMap<String, String>();
    public static final String RETURN_SUCCESS = "0000";// 受理成功
    public static final String PARAM_ERROR = "0002";// 传入参数为空 失败
    public static final String MD5_ERROR = "0003";// MD5校验码不存在 重发
    public static final String ID_ERROR = "0004";// 商户ID不存在 重发
    public static final String TIME_OUT_ERROR = "1008";// 当前时间已经超过最迟出票时间（投注时间不能大于球队开赛时间!）失败
    public static final String NODATA_ERROR = "1009";// 没有对应数据(期次) 重发
    public static final String LTSYS_ERROR = "1111";// 乐透系统错误 重发
    public static final String AMOUNT_LIMIT_ERROR = "2003";// 投注金额超过限制 失败
    public static final String BETS_ERROR = "2009";// 投注串错误 失败
    public static final String BALANCE_ERROR = "2010";// 用户余额不足 重发
    public static final String AMOUNT_ERROR = "2012";// 用户投注金额错误 失败
    public static final String ISSUEEND_ERROR = "2013";// 已截期 失败
    public static final String ISSUE_ERROR = "2014";// 期号错误 失败
    public static final String TICKETCOUNT_ERROR = "2014";// 票张数与声明不符 失败
    public static final String TICKET_ID_ERROR = "5003";// 商家流水号重复 
	
    static {
        forInstance();
    }

    public static void forInstance() {
    	errorCodeMap.put("0000","成功");                                                                      
    	errorCodeMap.put("0002","传入参数为空");                                                               
    	errorCodeMap.put("0003","MD5校验码不正确 ");                                                           
    	errorCodeMap.put("0004","商户ID不存在,请与乐透公司技术员联系");                                        
    	errorCodeMap.put("1111","系统错误，请和乐透公司运营人员联系");                                         
    	errorCodeMap.put("1005","用户名错误(商家资料不存在)");                                                 
    	errorCodeMap.put("1006","没有投注信息");                                                               
    	errorCodeMap.put("1007","商家流水号在数据库中已经有记录存在");                                         
    	errorCodeMap.put("1008","当前时间已经超过最迟出票时间（投注时间不能大于球队开赛时间!）");              
    	errorCodeMap.put("1009","没有对应数据");                                                               
    	errorCodeMap.put("2003","投注失败（投注金额超过限制）");                                               
    	errorCodeMap.put("2008","没有数据记录(查询的时候返回)");                                             
    	errorCodeMap.put("2009","投注失败（投注号码串信息错误）(不用再尝试出票,联系乐透技术人员核对错误原因)");
    	errorCodeMap.put("2010","投注失败（用户余额不足）(不用再尝试出票,通知财务充值)");
    	errorCodeMap.put("2012","投注失败（用户投注金额错误）");
    	errorCodeMap.put("2013","已截期(乐透返回)");
    	errorCodeMap.put("2014","投注失败（期号错误）(不用再尝试出票,重取新的期号数据)");
    	errorCodeMap.put("2015","获取不到对应赛事的赔率");
    	errorCodeMap.put("2016","获取不到对应赛果");
    	errorCodeMap.put("5001","票张数与声明不符");
    	errorCodeMap.put("5002","商家流水号在数据库中已经有记录存在");
    	errorCodeMap.put("5003","本批票有商家流水号重复");
    	errorCodeMap.put("5005","待验奖状态");
    	errorCodeMap.put("7721","商户流水号长度超出限制的16位");

    }

    public static String getErrorCodeMsg(String errorCode) {
        String msg = errorCodeMap.get(errorCode);
        if (msg == null) {
            return "";
        } else {
            return msg;
        }
    }
}
