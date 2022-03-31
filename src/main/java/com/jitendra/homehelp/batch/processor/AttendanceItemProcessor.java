package com.jitendra.homehelp.batch.processor;

import com.jitendra.homehelp.constants.AppConstants;
import com.jitendra.homehelp.dto.HomeHelpDto;
import com.jitendra.homehelp.enums.ProgressStatus;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

@Service
@StepScope
public class AttendanceItemProcessor implements ItemProcessor<HomeHelpDto,HomeHelpDto>  {
    StepExecution
            stepExecution = null;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }


    @Override
    public HomeHelpDto process(HomeHelpDto homeHelpDto) throws Exception {
        Date date = stepExecution.getJobExecution().getJobParameters().getDate(AppConstants.JOB_DATE);
        homeHelpDto.setStatus(ProgressStatus.NOTSTARTED.getValue());
        if(date!=null) {
            homeHelpDto.setDate(new java.sql.Date(date.getTime()));
        }else {
            homeHelpDto.setDate(java.sql.Date.valueOf(LocalDate.now().plusDays(1l)));
        }
        return homeHelpDto;
    }


}
