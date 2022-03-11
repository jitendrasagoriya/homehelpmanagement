package com.jitendra.homehelp.batch.listener;

import com.jitendra.homehelp.batch.reader.HomeHelpRowMapper;
import com.jitendra.homehelp.dao.HomeHelpDao;
import com.jitendra.homehelp.dto.HomeHelpDto;
import com.jitendra.homehelp.entity.HomeHelp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeJob;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class StepSkipListener implements SkipListener<String, HomeHelpDto> {

    private static final Logger logger =   LogManager.getLogger(StepSkipListener.class);

    private HomeHelpDao homeHelpDao;

    private BufferedWriter bw = null;

    public StepSkipListener(File file, HomeHelpDao homeHelpDao) throws IOException {
        this.homeHelpDao = homeHelpDao;
        bw= new BufferedWriter(new FileWriter(file, true));
        logger.info("MySkipListener =========> :"+file);
    }
    @BeforeJob
    public void beforeJob(JobExecution jobExecution) {
        logger.info(String.format("Setting Job Context {}",jobExecution));
    }

    @Override
    public void onSkipInRead(Throwable throwable) {
        logger.info("StepSkipListener - onSkipInRead");
    }

    @Override
    public void onSkipInWrite(HomeHelpDto homeHelpDto, Throwable throwable) {
        logger.info("StepSkipListener - afterWrite");
        String error = throwable.getMessage();
        System.out.println("onSkipInRead =========> :");
        HomeHelp homeHelp =  homeHelpDao.getById(homeHelpDto.getId());

        if(throwable instanceof DuplicateKeyException) {
               error = "Duplicate Key Exception";
        }

        try {
            bw.write("ERROR : " + error +" | "+homeHelp.toPipeSaperatedString());
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            logger.error("Unable to write skipped line to error file");
        }
    }

    @Override
    public void onSkipInProcess(String s, Throwable throwable) {
        logger.info("StepSkipListener - onWriteError");
    }
}
