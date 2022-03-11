package com.jitendra.homehelp.batch.tasklet;

import com.jitendra.homehelp.constants.AppConstants;
import com.jitendra.homehelp.entity.BatchMonitored;
import com.jitendra.homehelp.enums.BatchEvent;
import com.jitendra.homehelp.service.BatchMonitoredService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BatchStartTasklet implements Tasklet, InitializingBean, StepExecutionListener {

    private static final Logger logger =   LogManager.getLogger(BatchStartTasklet.class);

    @Autowired
    BatchMonitoredService batchMonitoredService;

    private StepExecution stepExecution;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        logger.info("Batch Execution Started");
        BatchMonitored batchMonitored = new BatchMonitored.Builder().buildDefault();
        BatchMonitored batchMonitored1 = batchMonitoredService
                .getByTypeAndDateBySuccess(BatchEvent.HOMEHELPATTENDANCE,batchMonitored.getDate());
        batchMonitored.setStartBy((String) chunkContext.getStepContext().getJobParameters().get(AppConstants.START_BY_KEY));
        if (logger.isDebugEnabled())
            logger.debug("Batch Already Executed :" +batchMonitored1!=null?Boolean.TRUE:Boolean.FALSE);
        if (batchMonitored1!=null) {
            initilizeBatchContext(chunkContext, batchMonitored);
            throw new RuntimeException("Batch is already executed for " + batchMonitored.getDate() + " Date.");
        }
        initilizeBatchContext(chunkContext, batchMonitored);
        return RepeatStatus.FINISHED;
    }

    private void initilizeBatchContext(ChunkContext chunkContext, BatchMonitored batchMonitored) {
        batchMonitored.setBatchExecutionId(chunkContext.getStepContext().getJobInstanceId());
        batchMonitored = batchMonitoredService.save(batchMonitored);
        if (logger.isDebugEnabled())
            logger.debug("BatchMonitored Save Successfully::Entity::"+ batchMonitored);

        chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put(AppConstants.BATCH_MONITORED, batchMonitored);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}
