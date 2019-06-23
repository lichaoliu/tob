package com.cndym.sendClient.autotest.impl;

import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cndym.bean.tms.SubIssueForJingCaiLanQiu;
import com.cndym.bean.tms.SubIssueForJingCaiZuQiu;
import com.cndym.bean.tms.Ticket;
import com.cndym.sendClient.BaseSendClient;
import com.cndym.sendClient.ISendClientOperator;
import com.cndym.sendClient.autotest.AutoSendClientConfig;
import com.cndym.sendClient.autotest.util.ToSendCode;
import com.cndym.service.IMainIssueService;
import com.cndym.service.ISubIssueForJingCaiLanQiuService;
import com.cndym.service.ISubIssueForJingCaiZuQiuService;
import com.cndym.utils.ConfigUtils;
import com.cndym.utils.HttpClientUtils;
import com.cndym.utils.Utils;
import com.cndym.utils.xml.parse.ByteCodeUtil;
import com.cndym.utils.xml.parse.XmlBean;

@Component
public class AutoTestSendClientImpl extends BaseSendClient {
    private Logger logger = Logger.getLogger(getClass());
    @Resource(name = "postOrderOperator")
    private ISendClientOperator postOrderOperator;

    @Autowired
    private ISubIssueForJingCaiZuQiuService subIssueForJingCaiZuQiuService;

    @Autowired
    private ISubIssueForJingCaiLanQiuService subIssueForJingCaiLanQiuService;

    @Autowired
    private IMainIssueService mainIssueService;

    /**
     * 发送请求接口101
     */
    @Override
    public XmlBean sendOrder(List<Ticket> ticketList) {

        StringBuffer ticketXml = new StringBuffer("<tickets>");
        StringBuffer temp = new StringBuffer();
        for (Ticket ticket : ticketList) {
            String saleInfo = "";
            if ("200".equals(ticket.getLotteryCode()) || "201".equals(ticket.getLotteryCode())) {
                saleInfo = covertOddsToNumber(ticket);
            }
            temp.append("<ticket ticketId=\"");
            temp.append(ticket.getTicketId());
            temp.append("\" status=\"");
            temp.append("3");
            temp.append("\" saleCode=\"");
            temp.append(ticket.getTicketId());
            temp.append("\" errCode=\"");
            temp.append("0");
            temp.append("\" errMsg=\"");
            temp.append("交易成功");
            temp.append("\" saleInfo=\"");
            temp.append(saleInfo);
            temp.append("\"/>");
        }
        ticketXml.append(temp);
        ticketXml.append("</tickets>");
        postOrderOperator.execute(ByteCodeUtil.xmlToObject(ticketXml.toString()));

        return null;
    }

    public String covertOddsToNumber(Ticket ticket) {
        String lotteryCode = ticket.getLotteryCode();
        String playCode = ticket.getPlayCode();
        String numberInfo = ticket.getNumberInfo();

        String returnodds = "";
        String passType = numberInfo.split("\\|")[1];
        if ("1*1".equals(passType) && !"20004".equals(lotteryCode + playCode) && !"20103".equals(lotteryCode + playCode)) {
            returnodds = covertOddsDanGuan(numberInfo, lotteryCode, playCode);
        } else {
            returnodds = covertOdds(ticket);
        }

        return returnodds;
    }
    
    /**
	 * 余额查询
	 */
	@Override
    public double accountQuery() {
		return 0d;
	}

    /**
     * @param odds 20120534*4*001|0(+2.5):12|1(1):12|^20120534*4*002|0(+2.5):12|1(-2.5):12|^
     * @return
     */
//    public String covertOddsDanGuan(String numberInfo, String lotteryCode, String playCode) {
//
//        String oddsLottery = ToSendCode.getOddsLottery(lotteryCode, playCode) + "|";
//
//        String number = numberInfo.split("\\|")[0];
//        String passType = numberInfo.split("\\|")[1];
//
//        String[] numbers = number.split(";");
//        String numberStr = "";
//        for (String num : numbers) {
//            String matchNo = num.split(":")[0];
//            String betInfo = num.split(":")[1];
//
//            String[] bets = betInfo.split(",");
//            String betStr = "";
//            for (String bet : bets) {
//                if (Utils.isNotEmpty(betStr)) {
//                    betStr = betStr + "/" + bet + "(1.00)";
//                } else {
//                    betStr = bet + "(1.00)";
//                }
//            }
//
//            if ("200".equals(lotteryCode)) {
//                matchNo = matchNo.substring(2);
//            } else {
//                matchNo = matchNo.substring(2) + "(0)";
//            }
//
//            if (Utils.isNotEmpty(numberStr)) {
//                numberStr = numberStr + "," + matchNo + "=" + betStr;
//            } else {
//                numberStr = matchNo + "=" + betStr;
//            }
//        }
//
//        String returnodds = oddsLottery + numberStr + "|" + passType;
//        return returnodds;
//    }
    
    public String covertOddsDanGuan(String numberInfo, String lotteryCode, String playCode) {
		String oddsLottery = ToSendCode.getOddsLottery(lotteryCode, playCode) + "|";

		String number = numberInfo.split("\\|")[0];
		String passType = numberInfo.split("\\|")[1];

		String[] numbers = number.split(";");
		String numberStr = "";
		for (String num : numbers) {
			String matchNo = num.split(":")[0];
			String betInfo = num.split(":")[1];

			String[] bets = betInfo.split(",");
			String betStr = "";
			for (String bet : bets) {
				if ("20004".equals(lotteryCode + playCode)) {
					char[] bt = bet.toCharArray();
					StringBuffer sb = new StringBuffer();
					for (int j = 0; j < bt.length; j++) {
						if (j == 0) {
							sb.append(bt[j]);
						} else {
							sb.append(":").append(bt[j]);
						}
					}

					bet = sb.toString();
				} else if ("20003".equals(lotteryCode + playCode)) {
					char[] bt = bet.toCharArray();
					StringBuffer sb = new StringBuffer();
					for (int j = 0; j < bt.length; j++) {
						if (j == 0) {
							sb.append(bt[j]);
						} else {
							sb.append("-").append(bt[j]);
						}
					}

					bet = sb.toString();
				}

				if (Utils.isNotEmpty(betStr)) {
					betStr = betStr + "/" + bet + "("+getPeilv()+")";
				} else {
					betStr = bet + "("+getPeilv()+")";
				}
			}

			if ("200".equals(lotteryCode)) {
				matchNo = matchNo.substring(2);
			} else {
				matchNo = matchNo.substring(2) + "(0)";
			}

			if (Utils.isNotEmpty(numberStr)) {
				numberStr = numberStr + "," + matchNo + "=" + betStr;
			} else {
				numberStr = matchNo + "=" + betStr;
			}
		}

		String returnodds = oddsLottery + numberStr + "|" + passType;
		return returnodds;
	}

    /**
     * @param odds
     * @return
     */
    public String covertOdds(Ticket ticket) {
        String lotteryCode = ticket.getLotteryCode();
        String playCodeE = ticket.getPlayCode();
        String numberInfo = ticket.getNumberInfo();

        String oddsLottery = ToSendCode.getOddsLottery(lotteryCode, playCodeE) + "|";

        String number = numberInfo.split("\\|")[0];
        String passType = numberInfo.split("\\|")[1];

        String[] numbers = number.split(";");
        String numberStr = "";
        for (String num : numbers) {
            String matchNo = "";
            String betInfo = "";
            String playCode = "";
            if (playCodeE.equals("10")) {
                playCode = num.split(":")[1];
                matchNo = num.split(":")[0];
                betInfo = num.split(":")[2];
            } else {
                matchNo = num.split(":")[0];
                betInfo = num.split(":")[1];
                playCode = playCodeE;
            }

            String letBall = "0";
            String preCast = "";
            if ("200".equals(lotteryCode)) {
                SubIssueForJingCaiZuQiu zuQiu = subIssueForJingCaiZuQiuService.getSubIssueForJingCaiZuQiuByIssueSn(matchNo.substring(0, 8), matchNo.substring(8));
                letBall = zuQiu.getLetBall();
            } else if ("201".equals(lotteryCode)) {
                SubIssueForJingCaiLanQiu lanQiu = subIssueForJingCaiLanQiuService.getSubIssueForJingCaiLanQiuByIssueSn(matchNo.substring(0, 8), matchNo.substring(8));
                letBall = lanQiu.getLetBall();
                preCast = lanQiu.getPreCast();
            }

            String[] bets = betInfo.split(",");
            String betStr = "";
            for (String bet : bets) {
                if ("20004".equals(lotteryCode + playCode)) {
                    char[] bt = bet.toCharArray();
                    StringBuffer sb = new StringBuffer();
                    for (int j = 0; j < bt.length; j++) {
                        if (j == 0) {
                            sb.append(bt[j]);
                        } else {
                            sb.append(":").append(bt[j]);
                        }
                    }
                    bet = sb.toString();
                } else if ("20003".equals(lotteryCode + playCode)) {
                    char[] bt = bet.toCharArray();
                    StringBuffer sb = new StringBuffer();
                    for (int j = 0; j < bt.length; j++) {
                        if (j == 0) {
                            sb.append(bt[j]);
                        } else {
                            sb.append("-").append(bt[j]);
                        }
                    }
                    bet = sb.toString();
                }
                if (Utils.isNotEmpty(betStr)) {
                    betStr = betStr + "/" + bet + "(" + getPeilv() + ")";
                } else {
                    betStr = bet + "(" + getPeilv() + ")";
                }
            }

            letBall = letBall == null ? "0" : letBall;
            if ("201".equals(lotteryCode)) {
                if ("01".equals(playCode) || "03".equals(playCode)) {
                    letBall = "0";
                } else if ("04".equals(playCode)) {
                    letBall = preCast;
                }
                if ("10".equals(playCodeE)) {
                    matchNo = matchNo.substring(2) + ":" + playCode + "(" + letBall + ")";
                } else {
                    matchNo = matchNo.substring(2) + "(" + letBall + ")";
                }
            } else if ("20001".equals(lotteryCode + playCodeE)) {
                matchNo = matchNo.substring(2) + "(" + letBall + ")";
            }else if("20005".equals(lotteryCode + playCodeE)){
            	matchNo = matchNo.substring(2) + "(0)";
            } else if ("20010".equals(lotteryCode + playCodeE)) {
                if ("20001".equals(lotteryCode + playCode)) {
                    matchNo = matchNo.substring(2) + ":" + playCode + "(" + letBall + ")";
                } else if ("20005".equals(lotteryCode + playCode)) {
                    matchNo = matchNo.substring(2) + ":" + playCode + "(0)";
                } else {
                    matchNo = matchNo.substring(2) + ":" + playCode;
                }
            } else {
                matchNo = matchNo.substring(2);
            }

            if (Utils.isNotEmpty(numberStr)) {
                numberStr = numberStr + "," + matchNo + "=" + betStr;
            } else {
                numberStr = matchNo + "=" + betStr;
            }
        }

        String returnodds = oddsLottery + numberStr + "|" + passType;
        return returnodds;
    }

    private String getPeilv() {
        Random rd1 = new Random();
        double aa = 1 + rd1.nextInt(900) / 100.0;
        return Utils.formatNumberZ(aa);
    }

    @Override
    public XmlBean bonusInfoQuery(XmlBean xmlBean) {
        try {
            String lotteryCode = xmlBean.attribute("lotteryCode");
            String issue = xmlBean.attribute("issue");

            String bonusNumber = "";
            if ("107".equals(lotteryCode)) {
                bonusNumber = AutoSendClientConfig.getValue("POST_CODE_GP_LOTTERY");
            } else if ("006".equals(lotteryCode)) {
                bonusNumber = AutoSendClientConfig.getValue("POST_CODE_GP_LOTTERY");
            }

            mainIssueService.doAutoBonusGpIssue(lotteryCode, issue, bonusNumber);

            StringBuffer interfaceUrl = new StringBuffer(ConfigUtils.getValue("CALCULATE_AWARD_REQUEST"));
            interfaceUrl.append("?lotteryCode=").append(lotteryCode);
            interfaceUrl.append("&issue=").append(issue);

            logger.info(lotteryCode + "." + issue + "开始算奖");
            HttpClientUtils httpClientUtils = new HttpClientUtils(interfaceUrl.toString());
            logger.info("发送到URL:" + interfaceUrl);
            String reStr = httpClientUtils.httpClientGet();
            logger.error("返回信息:" + reStr);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("高频自动开奖错误：" + e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        ticket.setLotteryCode("200");
        ticket.setPlayCode("10");
        ticket.setPollCode("02");
        ticket.setNumberInfo("20140326001:04:21,02;20140326002:02:6|2*1");

        AutoTestSendClientImpl clientImpl = new AutoTestSendClientImpl();
        System.out.println(clientImpl.covertOddsToNumber(ticket));

//		List<Ticket> ticketList = new ArrayList<Ticket>();
//		ticketList.add(ticket);

//		ISendClient autoTestSendClientImpl = (ISendClient) SpringUtils.getBean("autoTestSendClientImpl");
//		System.out.println(autoTestSendClientImpl.sendOrder(ticketList));
    }
}
