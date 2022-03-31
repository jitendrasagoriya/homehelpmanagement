package com.jitendra.homehelp.batch.tasklet;

import com.jitendra.homehelp.constants.AppConstants;
import com.jitendra.homehelp.enums.BatchEvent;
import com.jitendra.homehelp.service.AttendanceService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Component
public class CleanupAttendanceTasklet implements Tasklet {

    @Autowired
    AttendanceService attendanceService;


    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        String strBatchEvent = (String) chunkContext.getStepContext().getJobParameters().get(AppConstants.JOB_NAME);
        BatchEvent batchEvent = BatchEvent.getByValue(strBatchEvent);
        Date dtBatchDate = (Date) chunkContext.getStepContext().getJobParameters().get(AppConstants.JOB_DATE);
        if(batchEvent == BatchEvent.ADHOCATTENDANCE) {
           // Date requestedDate = DateUtils.parseDate(strBatchDate,"yyyy-MM-dd");
            attendanceService.getAttendanceDao().getRepository().deleteAll(attendanceService.getByDate(dtBatchDate));
        }
        return RepeatStatus.FINISHED;
    }
}
