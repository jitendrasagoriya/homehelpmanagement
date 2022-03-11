package com.jitendra.homehelp.batch.listener;

import com.jitendra.homehelp.dto.HomeHelpDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeJob;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@JobScope
public class StepItemWriteListener implements ItemWriteListener<HomeHelpDto> {

    private static final Logger logger =   LogManager.getLogger(StepItemWriteListener.class);


    @BeforeJob
    public void beforeStep(StepExecution stepExecution) {
    }


    @Override
    public void beforeWrite(List<? extends HomeHelpDto> items) {
        logger.info("ItemWriteListener - beforeWrite");
    }

    @Override
    public void afterWrite(List<? extends HomeHelpDto> items) {
        logger.info("ItemWriteListener - afterWrite");
    }

    @Override
    public void onWriteError(Exception exception, List<? extends HomeHelpDto> items) {
        logger.info("ItemWriteListener - onWriteError");
    }
}