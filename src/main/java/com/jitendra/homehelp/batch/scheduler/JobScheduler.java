package com.jitendra.homehelp.batch.scheduler;

import com.jitendra.homehelp.constants.AppConstants;
import com.jitendra.homehelp.entity.HomeHelp;
import com.jitendra.homehelp.service.AttendanceService;
import com.jitendra.homehelp.service.HomeHelpService;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class JobScheduler {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private HomeHelpService homeHelpService;

    @Autowired
    AttendanceService attendanceService;

    @Autowired
    private Job nextDayJob;

    @Scheduled(cron="0 0 23 * * *")
    public void executeBatch() {
        System.out.println("Start Batch Job");
        JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                .addString(AppConstants.START_BY_KEY,AppConstants.START_BY_VALUE_AUTORUN)
                .toJobParameters();
        try {
            JobExecution jobExecution = jobLauncher.run(nextDayJob, jobParameters);
            System.out.println("Job's Status:::"+jobExecution.getStatus());
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
                | JobParametersInvalidException e) {
            e.printStackTrace();
        }
    }

    public void cleanUpJob() {

    }
}
