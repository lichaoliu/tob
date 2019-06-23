package com.cndym.servlet.manages.lottery;

import com.cndym.bean.tms.MainIssue;
import com.cndym.service.IMainIssueService;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * 作者：邓玉明
 * 时间：10-12-23 下午3:42
 * QQ：757579248
 * email：cndym@163.com
 */
public class LotteryList {
	
	private static final Logger logger = Logger.getLogger(LotteryList.class);
	
    public static void lottery115(String maxIssue,Date maxDate,String startTime, String currentIssue) {

        String date = "2011-01-08";
        date = startTime;
        IMainIssueService mainIssueService = (IMainIssueService) SpringUtils.getBean("mainIssueServiceImpl");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
        SimpleDateFormat issue = new SimpleDateFormat("yyMMdd");
        Date dateTime = null;
        Calendar calendar = Calendar.getInstance();
        try {
        	if(startTime != null && !"".equals(startTime)) {
        		dateTime = simpleDateFormat.parse(startTime);
        		  calendar.setTime(dateTime);
        	} else if(maxDate != null) {
        		date = sdf.format(maxDate)+" 09:00:00";
        		dateTime = simpleDateFormat.parse(date);
        		calendar.setTime(dateTime);
        		calendar.add(Calendar.DATE, 1);
        	}
        }catch(Exception e) {
        	logger.error("11选5 期次时间错误");
        }

       
        try {
        	int staIssue = new Integer(currentIssue.substring(currentIssue.length() - 2, currentIssue.length()));
        	if(staIssue == 65){
        		staIssue = 1;
        		 if(calendar.getTime().toString().indexOf("21:48:00") > -1){
	                calendar.add(Calendar.HOUR, 11);
	                calendar.add(Calendar.MINUTE, 12);
             	}
        	}
        	int j = 0;
            for (int i = staIssue; i <= 65;) {
            	String code = "107";
	  	        String issueName = issue.format(calendar.getTime()) + Utils.fullByZero(i, 2);
	  	        boolean isRepeat = isRepeat(code,issueName);
	  	        if(staIssue < 65){
	  	        	i++;
	  	        }
	  	        if(!isRepeat){
	  	        	if(j == 0){
            			logger.info("批量添加期次开始期次=" + issueName);
            		}else if(i == 66){
            			logger.info("批量添加期次最后期次=" + issueName);
            		}
	  	        	j++;
	                MainIssue mainIssue = new MainIssue();
	                mainIssue.setBonusClass("");
	                mainIssue.setBonusNumber("-");
	                mainIssue.setBonusStatus(0);
	                mainIssue.setBonusTime(null);
	                mainIssue.setLotteryCode(code);
	                mainIssue.setName(issueName);
	                mainIssue.setSendStatus(0);
	                mainIssue.setStatus(0);;
	                mainIssue.setStartTime(calendar.getTime());
	                calendar.add(Calendar.MINUTE, 8);
	                mainIssue.setEndTime(calendar.getTime());
	                mainIssueService.save(mainIssue);
	                calendar.add(Calendar.MINUTE, 4);
	  	        }else{
	  	        	logger.info("批量添加跳过期次=" + issueName);
	  	        	if(calendar.getTime().toString().indexOf("21:48:00") > -1){
		                calendar.add(Calendar.HOUR, 11);
	             	}
	  	        		calendar.add(Calendar.MINUTE, 12);
	  	        }
	  	      if(i == 66){
	            	logger.info("添加期次总数=" + j);
	            }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    
    /**
     * 生肖乐  大乐透 
     */
    public static void lotteryLotto(String maxIssue,Date maxDate,String startTime,String currentIssue) {
    //	一、三、六
    	
    	IMainIssueService mainIssueService = (IMainIssueService) SpringUtils.getBean("mainIssueServiceImpl");
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sub = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat issuesdf = new SimpleDateFormat("yy");
        try {
        	if(startTime != null && !"".equals(startTime)) {
        		calendar.setTimeInMillis(sub.parse(startTime).getTime());
        	} else if(maxDate != null) {
        		calendar.setTime(maxDate);
        	}
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
        int start = 3;
        try {
        	if(currentIssue != null && !"".equals(currentIssue)) {
        		start = Integer.parseInt(currentIssue.substring(2, currentIssue.length()));
        		start-- ;
        	} else if(maxIssue != null && !"".equals(maxIssue)) {
        		start = Integer.parseInt(maxIssue.substring(2, maxIssue.length()));
        	}
        	
        }catch(Exception e) {
        	e.printStackTrace();
        	logger.error("生成大乐透期次错误 ");
        }
        
        MainIssue mainIssue = null;
        int j = 0;
        for (int i = 0; i < 100; i++) {
            int week = calendar.get(Calendar.DAY_OF_WEEK);
            if (week == 2 || week == 4 || week == 7) {
                int year = calendar.get(Calendar.YEAR);
                String code = "113";
                String time = simpleDateFormat.format(calendar.getTime());
                start++;
                String issue =issuesdf.format(calendar.getTime()) + "" + Utils.fullByZero(start, 3);
                boolean isRepeat = isRepeat(code, issue);
                if(!isRepeat){
                	if(j == 0){
            			logger.info("批量添加期次开始期次=" + issue);
            		}
                	j++;
                	if (null != mainIssue) {
                        try {
                        	mainIssue.setEndTime(sub.parse(time + " 19:25:00"));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        mainIssueService.save(mainIssue);
                    }

                    time = time + " 21:00:00";
                    mainIssue = new MainIssue();
                    mainIssue.setBonusClass("");
	                mainIssue.setBonusNumber("-");
	                mainIssue.setBonusStatus(0);
	                mainIssue.setBonusTime(null);
	                mainIssue.setLotteryCode(code);
	                mainIssue.setName(issue);
	                mainIssue.setSendStatus(0);
	                mainIssue.setStatus(0);;
                    try {
                    	mainIssue.setStartTime(sub.parse(time));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }else{
                	logger.info("批量添加跳过期次=" + issue);
                }
               
            }
            if(i == 99){
            	logger.info("添加期次总数=" + j);
            }
            calendar.add(Calendar.DATE, 1);
        }
    }

    /**
     * 
     * @param maxIssue 上期期次
     * @param maxDate 上期截期时间
     * @param startTime 本期开期时间
     * @param currentIssue 本期期次
     */
    public static void lotteryArray3(String maxIssue,Date maxDate,String startTime,String currentIssue) {
    	String date = "2011-01-15";
        date = startTime;
       
        int startIssue = 0;
        try {
        	 if(currentIssue!=null && !"".equals(currentIssue)) {
        		 startIssue = Integer.parseInt(currentIssue.substring(2, currentIssue.length()));
        	 } else if(maxIssue != null && !"".equals(maxIssue)) {
        		 startIssue = Integer.parseInt(maxIssue.substring(2, maxIssue.length()));
        		 startIssue++;
        	 }
        	
        }catch(Exception e) {
        	logger.error("生成 排列3 期次 错误");
        	e.printStackTrace();
        	return;
        }
        
        IMainIssueService mainIssueService = (IMainIssueService) SpringUtils.getBean("mainIssueServiceImpl");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat issue = new SimpleDateFormat("yy");
        Date dateTime = null;
        Calendar calendar = Calendar.getInstance();
      
       
        try {
       	 if(startTime!=null && !"".equals(startTime)) {
       		 dateTime = simpleDateFormat.parse(startTime);
       		 calendar.setTime(dateTime);
       	 } else if(maxDate != null && !"".equals(maxDate)) {
       		 calendar.setTime(maxDate);
       	 }
       }catch(Exception e) {
       	logger.error("生成 排列3 开期时间 错误");
       	e.printStackTrace();
       	return;
       }
         try {
        	 int j = 0;
             for (int i = 1; i <= 100; i++) {
            	 String code = "108";
	             String issueName = issue.format(calendar.getTime()) + Utils.fullByZero(startIssue++, 3);
	             boolean isRepeat = isRepeat(code,issueName);
	             if(!isRepeat){
	            	 if(j == 0){
	            		logger.info("批量添加期次开始期次=" + issueName);
	            		
	            	}else if(i == 100){
	            		logger.info("批量添加期次最后期次=" + issueName);
	            	}
	            	 j++;
	            	 MainIssue mainIssue = new MainIssue();
		             mainIssue.setBonusClass("");
		                mainIssue.setBonusNumber("-");
		                mainIssue.setBonusStatus(0);
		                mainIssue.setBonusTime(null);
		                mainIssue.setLotteryCode(code);
		                mainIssue.setName(issueName);
		                mainIssue.setSendStatus(0);
		                mainIssue.setStatus(0);;
	                 mainIssue.setStartTime(calendar.getTime());
	                 calendar.add(Calendar.MINUTE, 24*60 - 5);
	                 mainIssue.setEndTime(calendar.getTime());
	                 calendar.add(Calendar.MINUTE,5);
	                 mainIssueService.save(mainIssue);
	             }else{
	            	 calendar.add(Calendar.MINUTE, 24*60);
	            	 logger.info("批量添加跳过期次=" + issueName);
	             }
	             if(i == 100){
	             	logger.info("添加期次总数=" + j);
	             }
             }
         } catch (Exception e) {
             e.printStackTrace();
         }
    }
    
    public static void lotteryArray5(String maxIssue,Date maxDate,String startTime,String currentIssue) {
    	String date = "2011-01-15";
         date = startTime;
    	 IMainIssueService mainIssueService = (IMainIssueService) SpringUtils.getBean("mainIssueServiceImpl");
         SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         SimpleDateFormat issue = new SimpleDateFormat("yy");
         Date dateTime = null;
         Calendar calendar = Calendar.getInstance();
         
         int start = 0;
         try {
        	 if(currentIssue != null && !"".equals(currentIssue)) {
        		 start = Integer.parseInt(currentIssue.substring(2, currentIssue.length()));	 
        	 } else if(maxIssue != null && !"".equals(maxIssue)) {
        		 start = Integer.parseInt(maxIssue.substring(2, maxIssue.length()));
        		 start ++;
        	 }
         }catch(Exception e) {
        	 logger.error("生成排列5 期次错误");
        	 e.printStackTrace();
        	 return ;
         }
         try {
        	 if(startTime != null && !"".equals(startTime)){
        		 dateTime = simpleDateFormat.parse(startTime);
        	 } else if(maxDate != null) {
        		 dateTime = maxDate;
        	 }
        	 calendar.setTime(dateTime);
         }catch(Exception e) {
        	 logger.error("生成排列5开期时间错误");
        	 e.printStackTrace();
        	 return ;
         }
         try {
             int j = 0;
             for (int i =1; i <= 100; i++) {
            	 String code = "109";
            	 String issueName = issue.format(calendar.getTime()) + Utils.fullByZero(start++, 3);
                 boolean isRepeat = isRepeat(code, issueName);
                 if(!isRepeat){
                	 if(j == 0){
               			logger.info("批量添加期次开始期次=" + issue);
               		}else if(i == 100){
               			logger.info("批量添加期次最后期次=" + issue);
               		}
                   	j++;
                   	MainIssue mainIssue = new MainIssue();
	                mainIssue.setBonusClass("");
	                mainIssue.setBonusNumber("-");
	                mainIssue.setBonusStatus(0);
	                mainIssue.setBonusTime(null);
	                mainIssue.setLotteryCode(code);
	                mainIssue.setName(issueName);
	                mainIssue.setSendStatus(0);
	                mainIssue.setStatus(0);
                    mainIssue.setStartTime(calendar.getTime());
                    calendar.add(Calendar.MINUTE, 24*60 - 5);
                    mainIssue.setEndTime(calendar.getTime());
                    calendar.add(Calendar.MINUTE,5);
                    mainIssueService.save(mainIssue);
                 }else{
                	 logger.info("批量添加跳过期次=" + issueName);
                	 calendar.add(Calendar.MINUTE, 24*60);
                 }
                 if(i == 100){
                 	logger.info("添加期次总数=" + j);
                 }
             }
         } catch (Exception e) {
             e.printStackTrace();
         }
    }

    
    /**
     * 
     * @param maxIssue
     * @param maxDate
     * @param startTime
     * @param currentIssue
     */
    public static void lottery22X5(String maxIssue,Date maxDate,String startTime,String currentIssue) {
    	String date = "2011-01-06";
        date = startTime;
   	 IMainIssueService mainIssueService = (IMainIssueService) SpringUtils.getBean("mainIssueServiceImpl");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat issue = new SimpleDateFormat("yy");
        Date dateTime = null;
        int start = 0;
        try {
        	if(currentIssue !=null && !"".equals(currentIssue) ) {
        		start = Integer.parseInt(currentIssue.substring(2, currentIssue.length()));
        	} else if(maxIssue!=null && !"".equals(maxIssue)) {
        		start = Integer.parseInt(maxIssue.substring(2, maxIssue.length()));
        		start++;
        	}
        }catch(Exception e) {
        	logger.error("生成 22选 5期次错误");
        	e.printStackTrace();
        	return;
        }
        Calendar calendar = Calendar.getInstance();
        
        try {
        	if(startTime != null && !"".equals(startTime)) {
        		  dateTime = simpleDateFormat.parse(date);
        	} else if(maxDate != null) {
        		 dateTime = maxDate;
        	}
        	calendar.setTime(dateTime);
        }catch(Exception e) {
        	logger.error("生成22选5开期时间错误");
        	e.printStackTrace();
        	return ;
        }
     
        try {
        	int j = 0;
            for (int i = 1; i <= 100; i++) {
            	String code = "111";
            	String issueName = issue.format(calendar.getTime()) + Utils.fullByZero(start++, 3);
            	boolean isRepeat = isRepeat(code, issueName);
            	if(!isRepeat){
            		if(j == 0){
              			logger.info("批量添加期次开始期次=" + issue);
              		}else if(i == 100){
              			logger.info("批量添加期次最后期次=" + issue);
              		}
                  	j++;
                  	MainIssue mainIssue = new MainIssue();
	                mainIssue.setBonusClass("");
	                mainIssue.setBonusNumber("-");
	                mainIssue.setBonusStatus(0);
	                mainIssue.setBonusTime(null);
	                mainIssue.setLotteryCode(code);
	                mainIssue.setName(issueName);
	                mainIssue.setSendStatus(0);
	                mainIssue.setStatus(0);
            		mainIssue.setStartTime(calendar.getTime());
                    calendar.add(Calendar.MINUTE, 24*60 - 15);
                    mainIssue.setEndTime(calendar.getTime());
                    calendar.add(Calendar.MINUTE,15);
                    mainIssueService.save(mainIssue);
            	}else{
            		logger.info("批量添加跳过期次=" + issueName);
            		calendar.add(Calendar.MINUTE, 24*60);
            	}
            	if(i == 100){
                	logger.info("添加期次总数=" + j);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 七星彩
     * 二、五、日
     * ★★★★★★★
     */
    public static void lottery7(String maxIssue,Date maxDate,String startTime,String currentIssue) {
    	IMainIssueService mainIssueService = (IMainIssueService) SpringUtils.getBean("mainIssueServiceImpl");
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sub = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat yearsdf = new SimpleDateFormat("yy");
        try {
        	if(startTime != null && !"".equals(startTime)) {
        		calendar.setTimeInMillis(sub.parse(startTime).getTime());
        	} else if(maxDate != null) {
        		calendar.setTime(maxDate);
        	}
		} catch (ParseException e1) {
			logger.error("生成7星彩 开期时间错误");
			e1.printStackTrace();
			return ;
		}
        int start = 3;
        try {
        	if(currentIssue != null && !"".equals(currentIssue)) {
        		start = Integer.parseInt(currentIssue.substring(2, currentIssue.length()));
        	} else if(maxIssue != null) {
        		start = Integer.parseInt(maxIssue.substring(2, maxIssue.length()));
        		start++;
        	}
        }catch(Exception e) {
        	logger.error("生成7星彩 期次错误");
        	e.printStackTrace();
        	return ;
        }
       
        MainIssue mainIssue = null;
        int j = 0;
        for (int i = 1; i <= 100; i++) {
            int week = calendar.get(Calendar.DAY_OF_WEEK);
            if (week == 3 || week == 6 || week == 1) {
                String time = simpleDateFormat.format(calendar.getTime());
                String issue = yearsdf.format(calendar.getTime()) + "" + Utils.fullByZero(start++, 3);
                String code = "110";
                boolean isRepeat = isRepeat(code, issue);
                if(!isRepeat){
                	if(j == 0){
              			logger.info("批量添加期次开始期次=" + issue);
              		}
                  	j++;
                	if (null != mainIssue) {
                        try {
                        	mainIssue.setEndTime(sub.parse(time + " 19:25:00"));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        mainIssueService.save(mainIssue);
                    }

                    time = time + " 19:35:00";
                    
                    mainIssue = new MainIssue();
	                mainIssue.setBonusClass("");
	                mainIssue.setBonusNumber("-");
	                mainIssue.setBonusStatus(0);
	                mainIssue.setBonusTime(null);
	                mainIssue.setLotteryCode(code);
	                mainIssue.setName(issue);
	                mainIssue.setSendStatus(0);
	                mainIssue.setStatus(0);;
                    try {
                    	mainIssue.setStartTime(sub.parse(time));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }else{
                	logger.info("批量添加跳过期次=" + issue);
                }
                
            }
            calendar.add(Calendar.DATE, 1);
            if(i == 100){
      			logger.info("添加期次总数=" + j);
      		}
        }
    	
    	
    	
    }


	public static boolean generateIssue(String lotteryClass, String startTime,
			 String total,String currentIssue) {
		IMainIssueService mainIssueService = (IMainIssueService) SpringUtils.getBean("mainIssueServiceImpl");
        List list = mainIssueService.find(" select name,endTime from MainIssue t where t.lotteryCode=? order by t.startTime desc ",new String[]{lotteryClass});
        
        String  maxIssue = null;
        Date maxDate = null;
        if(null != list && list.size()>0) {
        	Object [] obj = (Object [])list.get(0);
        	maxIssue = (String)obj[0];
        	maxDate = (Date)obj[1];
        }
        
        
        
		LotteryClass lc = LotteryClass.valueOf("lottery" + lotteryClass);
			try {
					switch(lc) {
					case lottery107:
						lottery115(maxIssue,maxDate,startTime,currentIssue);
						break;
					case lottery113:
						lotteryLotto(maxIssue,maxDate,startTime,currentIssue);
						break;
					case lottery400:
						break;
					case lottery200:
						break;
					case lottery201:
						break;
					case lottery110:
						lottery7(maxIssue,maxDate,startTime,currentIssue);
						break;
					case lottery302:
						break;
					case lottery300:
						break;
					case lottery111:
						lottery22X5(maxIssue,maxDate,startTime,currentIssue);
						break;
					case lottery303:
						break;
					case lottery109:
						lotteryArray5(maxIssue,maxDate,startTime,currentIssue);
						break;
					case lottery108:
						lotteryArray3(maxIssue,maxDate,startTime,currentIssue);
						break;
					}
			}catch(Exception e) {
				e.printStackTrace();
				return false;
		}
		
		return true;
	}
	
	/**
     * 添加期次是否有重复
     * @param code 彩种
     * @param issueName 期次
     * @return
     */
    public static boolean isRepeat(String code, String issueName){
    	IMainIssueService mainIssueService = (IMainIssueService) SpringUtils.getBean("mainIssueServiceImpl");
		List<MainIssue> mainIssues = mainIssueService.find("from MainIssue where lotteryCode=? and name=?", new String []{code,issueName});
    	if(null == mainIssues || mainIssues.size() <= 0){
    		return false;
    	}
    	return true;
    }
    
	public enum LotteryClass {
		lottery006,lottery108,lottery119,lottery107,lottery113,lottery400,lottery009,
		lottery002,lottery001,lottery200,lottery017,lottery003,lottery201,lottery010,lottery016,
		lottery110,lottery112,lottery302,lottery300,lottery111,lottery303,lottery109
	}

}
