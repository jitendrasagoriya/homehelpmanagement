package com.jitendra.homehelp.batch.listener;

import com.jitendra.homehelp.constants.AppConstants;
import com.jitendra.homehelp.entity.BatchMonitored;
import com.jitendra.homehelp.enums.Status;
import com.jitendra.homehelp.service.BatchMonitoredService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Time;


@Component
public class JobResultListener implements JobExecutionListener {

    @Autowired
    private BatchMonitoredService batchMonitoredService;

    private static final Logger logger =   LogManager.getLogger(JobResultListener.class);

    public void beforeJob(JobExecution jobExecution) {
        logger.info("Called beforeJob().");
    }

    public void afterJob(JobExecution jobExecution) {
        BatchMonitored batchMonitored = (BatchMonitored)jobExecution.getExecutionContext().get(AppConstants.BATCH_MONITORED);

        jobExecution.getStepExecutions().forEach(stepExecution -> {
            if(StringUtils.equalsAnyIgnoreCase( stepExecution.getStepName(),AppConstants.NEXT_DAY_STEP)){
                if(batchMonitored!=null) {
                    batchMonitored.setSkipRecord(stepExecution.getSkipCount());
                    batchMonitored.setTotalRecord(stepExecution.getReadCount());
                    batchMonitored.setInsertedRecord(stepExecution.getWriteCount());
                }
            }
        });
        if (jobExecution.getStatus() == BatchStatus.COMPLETED ) {
            logger.info("SUCCESS");
            batchMonitored.setStatus(Status.SUCCESS);
        }
        else if (jobExecution.getStatus() == BatchStatus.FAILED) {
            logger.info("FAILED");
            batchMonitored.setStatus(Status.FAILED);
            batchMonitored.setFailedReason(StringUtils.substring(jobExecution.getExitStatus().getExitDescription(),0,1000) );
        }
        batchMonitored.setEndTime(new Time(System.currentTimeMillis()));
        if(logger.isDebugEnabled())
            logger.debug("BatchMonitored::"+batchMonitored);

        batchMonitoredService.save(batchMonitored);
    }
}
