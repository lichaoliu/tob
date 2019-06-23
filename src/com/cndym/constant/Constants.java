package com.cndym.constant;

public class Constants {
    public static final String DEFAULT_XSD = "DEFAULT_XSD";// header校验
    public static final String XSD_PATH = "XSD_PATH";// schame文件根地址
    public static final String MD5_DIGEST_TYPE = "md5";
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DAY_FORMAT = "yyyy-MM-dd";
    // 入帐类型
    public static final String R00100 = "00100";// 出票失败退款
    public static final String R00200 = "00200";// 返奖
    public static final String R00400 = "00400";// 返佣
    public static final String R00500 = "00500";// 手工调整加
    // 出帐类型
    public static final String P10000 = "10000";// 代购支付
    public static final String P10400 = "10400";// 手工调整减

    // 特殊金额
    public static final double DOUBLE_ZERO = 0d;
    public static final int INT_ZERO = 0;

    // 未分配出票口
    public static final String NO_POST_CODE = "00";

    // 会员状态
    public static final int MEMBER_STATUS_LIVE = 1;// 活动状态
    public static final int MEMBER_STATUS_LOCK = 2;// 锁定状态

    // memo
    public static final String ORDER_FAILURE_MEMO = "投注退款";
    public static final String ORDER_SUCCESS_MEMO = "投注支付";
    public static final String BONUS_SUCCESS_MEMO = "返奖完成";

    // 取消模式
    public static final String OPERATOR_FROM_SYS = "01";// 系统
    public static final String OPERATOR_FROM_DIY = "02";// 用户
    public static final String OPERATOR_FROM_ADM = "03";// 管理员

    // 中奖状态
    public static final int BONUS_STATUS_WAIT = 0;// 等待开奖
    public static final int BONUS_STATUS_YES = 1;// 中奖
    public static final int BONUS_STATUS_NO = 2;// 未中奖

    // 是否派奖
    public static final int BONUS_TO_ACCOUNT_NO = 0;// 未派奖
    public static final int BONUS_TO_ACCOUNT_YES = 1;// 派奖

    // 手工返奖状态
    public static final int HANDWORK_BONUS_WAIT = 0;// 等待
    public static final int HANDWORK_BONUS_SUCCESS = 1;// 已返
    public static final int HANDWORK_BONUS_CANCEL = 2;// 取消
    public static final int HANDWORK_BONUS_NOT = 3;// 线下派奖

    // 返奖状态
    public static final int ISSUE_BONUS_STATUS_NO = 0;// 未返奖
    public static final int ISSUE_BONUS_STATUS_YES = 1;// 已返奖

    // 中心返奖状态
    public static final int ISSUE_CENTER_BONUS_STATUS_NO = 0;// 未返奖
    public static final int ISSUE_CENTER_BONUS_STATUS_YES = 1;// 已返奖

    // 是否兑奖
    public static final int DUI_JIANG_STATUS_NO = 0;// 未兑奖
    public static final int DUI_JIANG_STATUS_YES = 1;// 已兑奖
    public static final int DUI_JIANG_STATUS_FALSE = 2;// 兑奖失败

    // 票状态
    public static final int TICKET_STATUS_DOING = 0;// 已发消息队列等待出票
    public static final int TICKET_STATUS_WAIT = 1;// 需要程序查询出来
    public static final int TICKET_STATUS_SENDING = 2;// 票已发送出去但是没有收到回执
    public static final int TICKET_STATUS_SUCCESS = 3;// 出票成功
    public static final int TICKET_STATUS_FAILURE = 4;// 出票失败
    public static final int TICKET_STATUS_CANCEL = 5;// 取消出票
    public static final int TICKET_STATUS_RESEND = 6;// 可更换出票口重新发
    public static final int TICKET_STATUS_SOME = 7;// 部分成交

    // 期次相关状态
    public static final int ISSUE_STATUS_WAIT = 0;// 默认期次状态/预约/追号
    public static final int ISSUE_STATUS_START = 1;// 开售，前台当前期
    public static final int ISSUE_STATUS_PAUSE = 2;// 暂停
    public static final int ISSUE_STATUS_END = 3;// 结期
    public static final int ISSUE_SALE_WAIT = 0;// 等待出票
    public static final int ISSUE_SALE_SEND = 1;// 出票

    // 排序
    public static final String DESC = "desc";// 降序
    public static final String ASC = "asc";// 升序
    public static final String DEFAULT_PARTNERS_CODE = "80001";

    // memcached 当前期次
    public static final String MEMCACHED_CURRENT_ISSUE_LIST = "memcached.current.issue.list";
    public static final String MEMCACHED_SALE_ISSUE_LIST = "memcached.sale.issue.list";
    // memcached 控制是否发票
    public static final String MEMCACHED_SEND_ORDER = "memcached.send.order.";
    // memcached 控制竞彩赛程是否停售
    public static final String MEMCACHED_MATCH_LOG = "memcached.match.log.";

    // 北单,竞彩场次状态
    public static final int SUB_ISSUE_END_OPERATOR_DEFAULT = 0;// 默认状态
    public static final int SUB_ISSUE_END_OPERATOR_END = 1;// 结期处理
    public static final int SUB_ISSUE_END_OPERATOR_CANCEL = 2;// 中途取消
    public static final int SUB_ISSUE_END_OPERATOR_NON_DEFAULT = -9;// 除默认态之外的（也就是非销售中） 仅供查询使用，库里不会有此值

    // 北单，竞彩场次是不否有查询最终SP
    public static final int SUB_ISSUE_END_QUERY_NO = 0;// 未查询
    public static final int SUB_ISSUE_END_QUERY_YES = 1;// 已查询
    // 北单，竞彩场次返奖状态
    public static final int SUB_ISSUE_BONUS_OPERATOR_NO = 0;// 未做返奖处理
    public static final int SUB_ISSUE_BONUS_OPERATOR_YES = 1;// 已做返奖处理
    // 彩种期次处理类型
    public static final int ALLOW_RESERVE_ISSUE_TYPE = 0;// 可预约类期次,数字彩
    public static final int NOT_ALLOW_RESERVE_ISSUE_TYPE = 1;// 不可预约类期次,竞技彩，北单，竞彩

    // 算奖状态
    public static final int OPERATORS_AWARD_WAIT = 0;
    public static final int OPERATORS_AWARD_DOING = 1;
    public static final int OPERATORS_AWARD_COMPLETE = 2;

    // 赛果录入状态
    public static final int INPUT_AWARD_NO = 0;
    public static final int INPUT_AWARD_YES = 1;

    // 查询条件默认每页条数
    public static final int PAGE_SIZE_50 = 50;
    public static final int PAGE_SIZE_20 = 20;
    public static final int PAGE_SIZE_30 = 30;
    public static final int PAGE_SIZE_10 = 10;
    public static final int PAGE_SIZE_5 = 5;

    // 日志操作类型
    public static final int MANAGER_LOG_DEFAULT = 0;// 默认类型
    public static final int MANAGER_LOG_LONGIN = 1;// 登录/退出
    public static final int MANAGER_LOG_DATA = 2;// 日终数据处理操作
    public static final int MANAGER_LOG_USER_MESSAGE = 3;// 接入商管理
    public static final int MANAGER_LOG_BUY_ORDER_MESSAGE = 4;// 投注管理
    public static final int MANAGER_LOG_WIN_MESSAGE = 5;// 派奖处理
    public static final int MANAGER_LOG_REMIND_MESSAGE = 6;// 提醒管理
    public static final int MANAGER_LOG_SYS_MESSAGE = 7;// 系统管理
    public static final int MANAGER_LOG_EDIT_FOOTBALL_ISSUE = 8;// 录入足彩彩期
    public static final int MANAGER_LOG_START_CAL_AWARD = 9;// 启动算奖
    public static final int MANAGER_LOG_OPEN_AWARD_MESSAGE = 10;//  录入开奖号码
    public static final int MANAGER_LOG_EDIT_BONUS_INFO = 11;// 录入开奖公告
    public static final int MANAGER_LOG_EDIT_SIMPLE_ISSUE = 12;// 录入普通彩期
    public static final int MANAGER_LOG_BATCH_EDIT_SIMPLE_ISSUE = 13;// 批量录入普通彩期
    public static final int MANAGER_LOG_JC_ISSUE_MANAGES = 17; // 竞彩彩期管理

    // 调度模型：constant:固定，amount:金额，proportion:比例
    public static final String CONTROL_WEIGHT_CONSTANT = "constant";
    public static final String CONTROL_WEIGHT_AMOUNT = "amount";
    public static final String CONTROL_WEIGHT_PROPORTION = "proportion";

    // 后台管理员状态
    public static final Integer MANAGES_STATUS = 0; // 普通管理
    public static final Integer COOP_STATUS = 2; // 接入商管理

    // 送票未回执置为成功标志
    public static final String TICKET_ERROR_CODE = "9999";

    // 帐户临时操作状态
    public static final int ACCOUNT_OPERATOR_TEMP_STATUS_WAIT = 0;
    public static final int ACCOUNT_OPERATOR_TEMP_STATUS_DOING = 1;
    public static final int ACCOUNT_OPERATOR_TEMP_STATUS_SUCCESS = 2;
    public static final int ACCOUNT_OPERATOR_TEMP_STATUS_FAILURE = 3;
}
