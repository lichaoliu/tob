package com.cndym.adapter.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cndym.IAdapter;
import com.cndym.adapter.BaseAdapter;
import com.cndym.bean.user.Account;
import com.cndym.exception.ErrCode;
import com.cndym.service.IAccountService;
import com.cndym.utils.Utils;
import com.cndym.utils.xml.parse.ByteCodeUtil;
import com.cndym.utils.xml.parse.XmlBean;

@Component
public class QueryAccountBalanceAdapter extends BaseAdapter implements IAdapter {
	@Autowired
	private IAccountService accountService;

	@Override
	public XmlBean execute(XmlBean xmlBean) {
		XmlBean sidBean = xmlBean.getFirst("sid");
		String sid = "";
		if (sidBean != null) {
			sid = sidBean.attribute("text");
		} else {
			return returnErrorCodeBody(ErrCode.E0003);
		}

		Account account = accountService.getAccountBySid(sid);
		XmlBean bodyBean = getZuQiuXml(account);

		return bodyBean;
	}

	private XmlBean getZuQiuXml(Account account) {
		double totalAmount = account.getBonusAmount() + account.getRechargeAmount() + account.getPresentAmount();

		StringBuffer xml = new StringBuffer();
		xml.append("<body>");
		xml.append("<response code=\"" + ErrCode.E0000 + "\" msg=\"" + ErrCode.codeToMsg(ErrCode.E0000) + "\">");
		xml.append("<account ");
//		xml.append("bonusAmount=\"" + Utils.formatMoney(account.getBonusAmount()) + "\" ");
//		xml.append("rechargeAmount=\"" + Utils.formatMoney(account.getRechargeAmount()) + "\" ");
//		xml.append("presentAmount=\"" + Utils.formatMoney(account.getBonusAmount()) + "\" ");
		xml.append("amount=\"" + Utils.formatMoney(totalAmount) + "\" ");
		xml.append("/>");
		xml.append("</response>");
		xml.append("</body>");

		return ByteCodeUtil.xmlToObject(xml.toString());
	}
}
