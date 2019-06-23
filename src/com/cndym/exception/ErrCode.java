package com.cndym.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * User：邓玉明 Date：Nov 25, 2010 Time：11:13:27 AM 0开头是系统错误 1开头是账户错误 2开头是投注 3开头是查询,7开头是支付宝批量付款(70 开头的是支付宝放回异常错误码，71是本系统放回查询异常)
 */

public class ErrCode {
	private static Map<String, String> map = new HashMap<String, String>();
	private static Map<String, String> alarm = new HashMap<String, String>();
	private static Map<String, Integer> level = new HashMap<String, Integer>();

	/**
	 * 成功
	 */
	public static final String E0000 = "0000";
	/**
	 * MD5验证失败
	 */
	public static final String E0001 = "0001";
	/**
	 * schema验证失败
	 */
	public static final String E0002 = "0002";
	/**
	 * 参数错误
	 */
	public static final String E0003 = "0003";

	/**
	 * 网络连接异常
	 */
	public static final String E0005 = "0005";

	/**
	 * 系统未知异常
	 */
	public static final String E0999 = "0999";

	// 以8开头的都是接入商相关的错误码，80**为商户信息相关，81**为投注相关，82**为查询相关

	/**
	 * 账户不存在
	 */
	public static final String E8002 = "8002";
	/**
	 * 帐户已被锁定
	 */
	public static final String E8003 = "8003";
	/**
	 * 帐户余额不足
	 */
	public static final String E8004 = "8004";
	/**
	 * 彩种不存在
	 */
	public static final String E8101 = "8101";
	/**
	 * 投注类型不正确
	 */
	public static final String E8102 = "8102";
	/**
	 * 实际金额与传入金额不符
	 */
	public static final String E8103 = "8103";
	/**
	 * 票号重复
	 */
	public static final String E8104 = "8104";
	/**
	 * 投注期次已截期
	 */
	public static final String E8105 = "8105";
	/**
	 * 投注期次不存在
	 */
	public static final String E8106 = "8106";
	/**
	 * 投注期次未开售
	 */
	public static final String E8107 = "8107";
	/**
	 * 投注失败
	 */
	public static final String E8108 = "8108";
	/**
	 * 投注彩种暂停销售
	 */
	public static final String E8109 = "8109";

	/**
	 * 单票金额过20000限额
	 */
	public static final String E8110 = "8100";

	/**
	 * 投注号码格式不正确
	 */
	public static final String E8111 = "8111";

	/**
	 * 发单票数超过上限
	 */
	public static final String E8112 = "8112";

	/**
	 * 单票倍数超过上限
	 */
	public static final String E8113 = "8113";

	/**
	 * 扣款失败
	 */
	public static final String E8114 = "8114";

	/**
	 * 金额不能小于1
	 */
	public static final String E8115 = "8115";

	/**
	 * 传入注数错误
	 */
	public static final String E8116 = "8116";

	/**
	 * 该玩法暂不支持
	 */
	public static final String E8117 = "8117";

	/**
	 * 投注场次已截止
	 */
	public static final String E8118 = "8118";

	/**
	 * 投注场次不存在
	 */
	public static final String E8119 = "8119";
	/**
	 * 投注场次玩法未开
	 */
	public static final String E8120 = "8120";

	/**
	 * 订单号重复
	 */
	public static final String E8121 = "8121";
	
	/**
	 * 查询的方案不存在
	 */
	public static final String E8201 = "8201";
	/**
	 * 查询的期次不存在
	 */
	public static final String E8202 = "8202";
	/**
	 * 查询的期次还未返奖
	 */
	public static final String E8203 = "8203";

	/**
	 * 按票查询超出接口限制
	 */
	public static final String E8204 = "8204";

	/**
	 * 查询的赛事不存在
	 */
	public static final String E8205 = "8205";

	/**
	 * 此彩种暂停销售
	 */
	public static final String E8206 = "8206";

	/**
	 * 此订单已返奖
	 */
	public static final String E8207 = "8207";
	/**
	 * 此彩种暂无开售彩期
	 */
	public static final String E8208 = "8208";
	
	/**
	 * 复试玩法不能进行单式投注
	 */
	public static final String E8209 = "8209";

	// //////////////联盟相关错误码end/////////////////////

	static {
		init();
	}

	public static void init() {
		map.put(E0000, "系统处理成功");
		map.put(E0999, "系统未知异常");
		map.put(E0001, "MD5验证失败");
		map.put(E0002, "schema验证失败");
		map.put(E0003, "参数错误");
		map.put(E0005, "网络连接异常");

		map.put(E8002, "账户不存在");
		map.put(E8003, "帐户已被锁定");
		map.put(E8004, "帐户余额不足");
		map.put(E8101, "彩种不存在");
		map.put(E8102, "投注类型不正确");
		map.put(E8103, "实际金额与传入金额不符");
		map.put(E8104, "票号重复");
		map.put(E8105, "投注期次已截期");
		map.put(E8106, "投注期次不存在");
		map.put(E8107, "投注期次未开售");
		map.put(E8108, "投注失败");
		map.put(E8109, "投注彩种暂停销售");
		map.put(E8110, "单票金额过20000限额");
		map.put(E8111, "投注号码格式不正确");
		map.put(E8112, "发单票数超过上限");
		map.put(E8113, "单票倍数超过上限");
		map.put(E8114, "扣款失败");
		map.put(E8115, "金额不能小于1");
		map.put(E8116, "传入注数错误");
		map.put(E8117, "该玩法暂不支持");
		map.put(E8118, "投注场次已截止");
		map.put(E8119, "投注场次不存在");
		map.put(E8120, "投注场次玩法未开");
		map.put(E8121, "订单号重复");

		map.put(E8201, "订单不存在");
		map.put(E8202, "查询的期次不存在");
		map.put(E8203, "查询的期次还未返奖");
		map.put(E8204, "超出接口查询条数限制");
		map.put(E8205, "查询的赛事不存在");
		map.put(E8206, "此彩种暂停销售");
		map.put(E8207, "订单已返奖");
		map.put(E8208, "此彩种暂无开售彩期");
		map.put(E8209, "复试玩法不能进行单式投注");
	}

	public static String codeToMsg(String code) {
		if (map.containsKey(code)) {
			return map.get(code);
		}
		throw new CndymException(E0999);
	}

	public static String codeToAlarm(String code) {
		if (alarm.containsKey(code)) {
			return alarm.get(code);
		}
		return null;
	}

	public static Integer codeToLevel(String code) {
		if (level.containsKey(code)) {
			return level.get(code);
		}
		return -1;
	}
}
