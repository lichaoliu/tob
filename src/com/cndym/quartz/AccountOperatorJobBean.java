package com.cndym.quartz;

import com.cndym.bean.sys.DistributionLock;
import com.cndym.bean.user.AccountOperatorTemp;
import com.cndym.constant.Constants;
import com.cndym.dao.IAccountOperatorTempDao;
import com.cndym.service.IAccountOperatorTempService;
import com.cndym.service.IDistributionLockService;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.hibernate.PageBean;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;
import java.util.List;

/**
 * 作者：邓玉明
 * 时间：11-5-26 下午4:20
 * QQ：757579248
 * email：cndym@163.com
 */
public class AccountOperatorJobBean extends QuartzJobBean {
    private Logger logger = Logger.getLogger(getClass());
    private IDistributionLockService distributionLockService = (IDistributionLockService) SpringUtils.getBean("distributionLockServiceImpl");

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        long startTime = new Date().getTime();
        boolean lock = distributionLockService.doStartLock(new DistributionLock(DistributionLock.LOCK_FOR_ACCOUNT_OPERATOR));
        if (!lock) return;
        logger.info("running account operator JOB start");
        
        try{
        	IAccountOperatorTempService accountOperatorTempService = (IAccountOperatorTempService) SpringUtils.getBean("accountOperatorTempServiceImpl");
            int page = 1;
            int pageSize = 50;
            AccountOperatorTemp param = new AccountOperatorTemp();
            param.setStatus(Constants.ACCOUNT_OPERATOR_TEMP_STATUS_WAIT);
            PageBean pageBean = accountOperatorTempService.getAccountOperatorTempList(param, page, pageSize);
            List<AccountOperatorTemp> accountOperatorTemps = pageBean.getPageContent();
            for (AccountOperatorTemp accountOperatorTemp : accountOperatorTemps) {
                accountOperatorTemp.setStatus(Constants.ACCOUNT_OPERATOR_TEMP_STATUS_DOING);
                accountOperatorTempService.update(accountOperatorTemp);
                accountOperatorTempService.doUpdateAccount(accountOperatorTemp);
            }
        }catch(Exception e){
        	e.printStackTrace();
        }
        distributionLockService.doEndLock(new DistributionLock(DistributionLock.LOCK_FOR_ACCOUNT_OPERATOR));
        logger.info("running account operator JOB end (" + (new Date().getTime() - startTime) + "ms)");

    }
}
